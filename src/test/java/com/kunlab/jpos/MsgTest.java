package com.kunlab.jpos;

import com.kunlab.jpos.iso.channel.HEXChannel;
import com.kunlab.jpos.security.jceadapter.JCEHandler;
import com.kunlab.jpos.security.jceadapter.JCESecurityModule;
import com.kunlab.jpos.tlv.AsciiTLVList;
import com.kunlab.jpos.util.Util;
import org.jpos.core.CardHolder;
import org.jpos.iso.*;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.security.EncryptedPIN;
import org.jpos.security.SMAdapter;
import org.jpos.security.SecureDESKey;
import org.jpos.security.jceadapter.JCEHandlerException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author likun
 */
public class MsgTest {


    private static final byte[] DEV_ZPK = ISOUtil.hex2byte("1C4AFD92A4C123AED9FE0DF41A4545CB");
    private static final byte[] DEV_ZAK = ISOUtil.hex2byte("1C4AFD92A4C123AED9FE0DF41A4545CB");
    private static final String[][] data = {
            //f22,  卡号，                                 密码
//            {"021", "6226320111860679=49121206959520000", "182129"},
            {"021", "6226320111860679=49121206959520000", "123456"},
            {"022", "6226320111860679=49121206959520000", ""}
    };

    private static JCESecurityModule sm;
    private static JCEHandler jceHandler;
    private static ISOBasePackager packager;
    private static HEXChannel channel;

    static {
        try {
            sm = new JCESecurityModule("lmk");
            jceHandler = new JCEHandler("com.sun.crypto.provider.SunJCE");
            packager = new GenericPackager("src/test/resources/packager/iso87binary.xml");
            channel = new HEXChannel("localhost", 8080, packager, ISOUtil.str2bcd("6000030000000000000000", false));
        } catch (JCEHandlerException e) {
            e.printStackTrace();
        } catch (ISOException e) {
            e.printStackTrace();
        }
    }

    private static ISOMsg sendAndReceive(BaseChannel channel, ISOMsg msg) throws Exception {
        channel.setTimeout(60 * 1000);
        channel.connect();
        channel.send(msg);
        msg = channel.receive();
        return msg;
    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        channel.disconnect();
    }

    //回响测试
    @Test
    public void echo() throws Exception {
        ISOMsg m = new ISOMsg();
        m.setMTI("0820");
        m.set(41, "99998888");
        m.set(42, "801544520504178");
        m.set(60, "00000001301");

        ISOMsg resp = sendAndReceive(channel, m);
        resp.dump(System.out, "");
    }

    @Test
    public void checkin() throws Exception {
        ISOMsg m = new ISOMsg();
        m.setMTI("0800");
        m.set(11, "000006");
        m.set(41, "90127008");
        m.set(42, "857360153110001");
        m.set(60, "00000001");
        m.set(63, "001");
        m.set(120, Util.randomUUID());

        ISOMsg resp = sendAndReceive(channel, m);
        resp.dump(System.out, "");
    }


    public static ISOMsg sale() throws Exception {
        ISOMsg m = new ISOMsg();
        m.setMTI("0200");
        m.set(3, "000000");
        m.set(4, "103");
        m.set(11, Util.getRandomNumber(6));
        m.set(22, data[0][0]);
        m.set(25, "00");
        m.set(26, "06");
        m.set(35, data[0][1]);
        m.set(41, "90127008");
        m.set(42, "857360153110001");
        m.set(49, "156");
        m.set(52, encryptPIN(data[0]));
        m.set(53, "2600000000000000");
        m.set(60, "22000001000");
        m.set(64, new byte[8]);
        m.set(120, Util.randomUUID());

        AsciiTLVList list = new AsciiTLVList();
        list.append("42", "890000000012312");
        list.append("41", "00013360");
        m.set(122, list.pack(2));

        ISOMsg resp = sendAndReceive(channel, m);
        resp.dump(System.out, "sale ");

        return resp;
    }


    public void saleCancel(ISOMsg rt) throws Exception {
        ISOMsg m = new ISOMsg();
        m.setMTI("0200");
        m.set(3, "000000");
        m.set(4, "103");
        m.set(11, Util.getRandomNumber(6));
        m.set(22, data[0][0]);
        m.set(25, "00");
        m.set(26, "06");
        m.set(35, data[0][1]);
        m.set(37, "010900818823");
        m.set(41, "90127008");
        m.set(42, "857360153110001");
        m.set(49, "156");
        m.set(52, encryptPIN(data[0]));
        m.set(53, "2600000000000000");
        m.set(57, "7508000133607615890000000012312");
        m.set(60, "23000001000");
        m.set(61, "000001960402");
        m.set(64, new byte[8]);
        m.set(120, Util.randomUUID());

        ISOMsg resp = sendAndReceive(channel, m);
        resp.dump(System.out, "");
    }


    public static void saleReversal(ISOMsg rt) throws Exception {
        ISOMsg req = new ISOMsg();
        req.setMTI("0400");
        req.set(3, rt.getString(3));
        req.set(4, rt.getString(4));
        req.set(11, rt.getString(11));
        req.set(22, "021");
        req.set(25, rt.getString(25));
        req.set(39, "98");
        req.set(41, rt.getString(41));
        req.set(42, rt.getString(42));
        req.set(49, rt.getString(49));
        req.set(57, rt.getString(57));
        req.set(60, rt.getString(60).substring(0,8));
        req.set(64, new byte[8]);
        req.set(120, Util.randomUUID());

        ISOMsg resp = sendAndReceive(channel, req);
        resp.dump(System.out, "saleReversal ");
    }


    private String encryptTrack() {
        return data[0][1];
    }

    private static byte[] encryptPIN(String[] data) throws Exception{
        CardHolder cardHolder = new CardHolder(data[1]);
        byte[] pinBlock = calculatePINBlock(data[2], EncryptedPIN.extractAccountNumberPart(cardHolder.getPAN()));
        return jceHandler.encryptData(pinBlock, jceHandler.formDESKey(SMAdapter.LENGTH_DES3_2KEY, DEV_ZPK));
    }

    private static byte[] calculatePINBlock(String pin, String accountNumber) throws Exception {
        byte[] pinBlock = null;

        char[] block = "FFFFFFFFFFFFFFFF".toCharArray();
        char[] pinLenHex = String.format("%02X", pin.length()).toCharArray();
        System.arraycopy(pinLenHex, 0, block, 0, pinLenHex.length);
        System.arraycopy(pin.toCharArray(), 0, block, pinLenHex.length, pin.length());

        byte[] block1 = ISOUtil.hex2byte(new String(block));
        byte[] block2 = ISOUtil.hex2byte("0000" + accountNumber);
        pinBlock = ISOUtil.xor(block1, block2);

        return pinBlock;
    }


    private static byte[] mac(ISOMsg m) throws Exception {
        m.set(64, new byte[8]);
        return sm.generateECB_MACImpl(m.pack(), new SecureDESKey(SMAdapter.LENGTH_DES3_2KEY, SMAdapter.TYPE_TAK, DEV_ZAK, ISOUtil.hex2byte("9A2D7DAB")));
    }


    public static void main(String args[]) throws Exception {
        ISOMsg resp = sale();

//        Thread.sleep(20000);
//        saleReversal(resp);
    }

    @Test
    public void pubTest() throws Exception {

    }


}
