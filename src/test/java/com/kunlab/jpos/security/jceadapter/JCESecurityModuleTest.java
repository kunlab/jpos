package com.kunlab.jpos.security.jceadapter;

import org.jpos.iso.ISOUtil;
import org.jpos.security.SMAdapter;
import org.jpos.security.SecureDESKey;
import org.junit.Before;
import org.junit.Test;


/**
 * @author likun
 */
public class JCESecurityModuleTest {

    JCESecurityModule jceSecurityModule;

    private static final byte[] testKEK = ISOUtil.hex2byte("10101010101010101010101010101010");  //明文
//    private static final byte[] testKey = ISOUtil.hex2byte("A12B3C4DE25FADFA23FADFA1243FAFD2");  //明文
//    private static final byte[] testKey = ISOUtil.hex2byte("3167EFE2C355299E5CD6C7D366B22CE0");  //深圳银联测试机构密钥明文
    private static final byte[] testKey = ISOUtil.hex2byte("BCC197D1B8E3B1ACFAD69B005BF83233");  //深圳银联生产机构密钥明文
//    private static final byte[] testMAK = ISOUtil.hex2byte("A12B3C4DE25FADFA");                  //明文
    private static final byte[] testMAK = ISOUtil.hex2byte("23a1cb5d1a294f54");                  //明文

    @Before
    public void setUp() throws Exception {
        jceSecurityModule = new JCESecurityModule("lmk");

    }

    //明文转本地主密钥加密的密文
    @Test
    public void importClearKeyImpl() throws Exception {
        SecureDESKey keyUnderLmk = jceSecurityModule.importKeyImpl(SMAdapter.LENGTH_DES3_2KEY, SMAdapter.TYPE_ZMK, testKey, false);
        System.out.println(ISOUtil.byte2hex(keyUnderLmk.getKeyBytes()).toUpperCase());
        System.out.println(ISOUtil.byte2hex(keyUnderLmk.getKeyCheckValue()).toUpperCase());
    }

    //被KEK加密的密文转本地主密钥加密的密文
    @Test
    public void importKeyImplByHexKEK() throws Exception {
        byte[] testKeyUnderTestKEK = ISOUtil.hex2byte("0DFE1348DABDF124F0810AED8B6A9296");
        SecureDESKey keyUnderLmk = jceSecurityModule.importKeyImpl(SMAdapter.LENGTH_DES3_2KEY, SMAdapter.TYPE_TMK, testKeyUnderTestKEK, testKEK, false);
        System.out.println("check value: " + ISOUtil.byte2hex(keyUnderLmk.getKeyCheckValue()).toUpperCase());
    }


    //将本地主密钥加密的密文转明文
    @Test
    public void exportClearKeyImpl() throws Exception {
//        SecureDESKey keyUnderLmk = jceSecurityModule.importKeyImpl(SMAdapter.LENGTH_DES3_2KEY, SMAdapter.TYPE_TMK, testKey, false);
//        byte[] clearKey = jceSecurityModule.exportClearKeyImpl(keyUnderLmk.getKeyLength(), keyUnderLmk.getKeyType(), ISOUtil.byte2hex(keyUnderLmk.getKeyBytes()), ISOUtil.byte2hex(keyUnderLmk.getKeyCheckValue()));
//        System.out.println(ISOUtil.byte2hex(clearKey));

        SecureDESKey keyUnderLmk = new SecureDESKey(SMAdapter.LENGTH_DES,SMAdapter.TYPE_TAK,ISOUtil.hex2byte("0BE0C516FE746F04"), ISOUtil.hex2byte("4845A4EA"));
        byte[] clearKey = jceSecurityModule.exportClearKeyImpl(keyUnderLmk.getKeyLength(), keyUnderLmk.getKeyType(), ISOUtil.byte2hex(keyUnderLmk.getKeyBytes()), ISOUtil.byte2hex(keyUnderLmk.getKeyCheckValue()));
        System.out.println(ISOUtil.byte2hex(clearKey));
    }

    //将本地主密钥加密的密文转KEK加密的密文
    @Test
    public void exportKeyImplByHexKEK() throws Exception {
        // testKeyUnderTestKEK是testKey被testKEK加密生成
        byte[] testKeyUnderTestKEK = ISOUtil.hex2byte("0DFE1348DABDF124F0810AED8B6A9296");
        SecureDESKey keyUnderLmk = jceSecurityModule.importKeyImpl(SMAdapter.LENGTH_DES3_2KEY, SMAdapter.TYPE_TMK, testKeyUnderTestKEK, testKEK, false);

        byte[] keyUnderTestKEK = jceSecurityModule.exportKeyImpl(keyUnderLmk, testKEK, SMAdapter.LENGTH_DES3_2KEY);
        System.out.println(ISOUtil.byte2hex(keyUnderTestKEK));
    }

    @Test
    public void generateECB_MACImpl() throws Exception {
//        SecureDESKey keyUnderLmk = jceSecurityModule.importKeyImpl(SMAdapter.LENGTH_DES, SMAdapter.TYPE_TAK, testMAK, false);
        SecureDESKey keyUnderLmk = new SecureDESKey(SMAdapter.LENGTH_DES, SMAdapter.TYPE_TAK, ISOUtil.hex2byte("3A18B2F706EDFA97"), ISOUtil.hex2byte("08316FBE"));
        byte[] data = ISOUtil.hex2byte("0200302004C020C0981100000000000000010373118102200006346226320111860679A9A86108D14D221D003939393938383838383031353434353230353034313738313536AC97A9AEDF3CDF06261000000000000000112200000100000000000000000000");
        byte[] mac = jceSecurityModule.generateECB_MACImpl(data, keyUnderLmk);
        System.out.println("----- hexMac: " + ISOUtil.hexString(mac));
    }

    public static void main(String args[]) {
        int i = 255;
        System.out.println(i>>8);
        System.out.println(i);
    }

}