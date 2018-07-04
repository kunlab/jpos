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
    private static final byte[] testKey = ISOUtil.hex2byte("A12B3C4DE25FADFA23FADFA1243FAFD2");  //明文

    @Before
    public void setUp() throws Exception {
        jceSecurityModule = new JCESecurityModule("src/test/resources/lmk");
    }

    //明文转本地主密钥加密的密文
    @Test
    public void importClearKeyImpl() throws Exception {
        SecureDESKey keyUnderLmk = jceSecurityModule.importKeyImpl(SMAdapter.LENGTH_DES3_2KEY, SMAdapter.TYPE_TMK, testKey, false);
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
        SecureDESKey keyUnderLmk = jceSecurityModule.importKeyImpl(SMAdapter.LENGTH_DES3_2KEY, SMAdapter.TYPE_TMK, testKey, false);
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
}