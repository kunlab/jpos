package com.kunlab.jpos.security.jceadapter;

import org.jpos.iso.ISOUtil;
import org.jpos.security.CipherMode;
import org.jpos.security.SMAdapter;
import org.jpos.security.jceadapter.JCEHandlerException;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;


public class JCEHandlerTest {
    JCEHandler jceHandler;

    @Before
    public void setUp() throws Exception {
        jceHandler = new JCEHandler("com.sun.crypto.provider.SunJCE");
    }

    @Test
    public void generateDESKey() throws Exception {
        Key clearKey = jceHandler.generateDESKey(SMAdapter.LENGTH_DES3_2KEY);
        System.out.println("----- algorithm: " + clearKey.getAlgorithm());
        System.out.println("----- encoded: " + ISOUtil.byte2hex(clearKey.getEncoded()));
        System.out.println("----- format: " + clearKey.getFormat());
    }

    //加密
    @Test
    public void encryptDESKey() throws Exception {
        //被加密密钥(明文)
        Key clearKey = jceHandler.generateDESKey(SMAdapter.LENGTH_DES);
        System.out.println("---- clearKey: " + ISOUtil.byte2hex(clearKey.getEncoded()));

        //加密密钥(明文)
        Key encryptingKey = jceHandler.generateDESKey(SMAdapter.LENGTH_DES);
        System.out.println("---- encryptingKey: " + ISOUtil.byte2hex(encryptingKey.getEncoded()));

        //加密后密文
        byte[] encryptedKey = jceHandler.encryptDESKey(SMAdapter.LENGTH_DES, clearKey, encryptingKey);
        System.out.println("---- encryptedKey: " + ISOUtil.byte2hex(encryptedKey));
    }

    //解密
    @Test
    public void decryptDESKey() throws Exception {
        //被解密密文
        byte[] encryptedDESKey = ISOUtil.hex2byte("9DFD0EB5C71295A2");

        //解密密钥
        Key encryptingKey = formDESKey(SMAdapter.LENGTH_DES, ISOUtil.hex2byte("764C57A1FD8C4CE9"));

        //解密后的密钥明文
        Key clearKey = jceHandler.decryptDESKey(SMAdapter.LENGTH_DES, encryptedDESKey, encryptingKey, false);
        System.out.println("---- clearKey: " + ISOUtil.byte2hex(clearKey.getEncoded()));
    }

    private Key formDESKey(short keyLength, byte[] clearKeyBytes) throws JCEHandlerException {
        Key key = null;
        switch (keyLength) {
            case SMAdapter.LENGTH_DES: {
                key = new SecretKeySpec(clearKeyBytes, "DES");
            }
            break;
            case SMAdapter.LENGTH_DES3_2KEY: {
                // make it 3 components to work with JCE
                clearKeyBytes = ISOUtil.concat(clearKeyBytes, 0, getBytesLength(SMAdapter.LENGTH_DES3_2KEY), clearKeyBytes, 0,
                        getBytesLength(SMAdapter.LENGTH_DES));
            }
            case SMAdapter.LENGTH_DES3_3KEY: {
                key = new SecretKeySpec(clearKeyBytes, "DESede");
            }
        }
        if (key == null)
            throw new JCEHandlerException("Unsupported DES key length: " + keyLength + " bits");
        return key;
    }

    private int getBytesLength(short keyLength) throws JCEHandlerException {
        int bytesLength = 0;
        switch (keyLength) {
            case SMAdapter.LENGTH_DES:
                bytesLength = 8;
                break;
            case SMAdapter.LENGTH_DES3_2KEY:
                bytesLength = 16;
                break;
            case SMAdapter.LENGTH_DES3_3KEY:
                bytesLength = 24;
                break;
            default:
                throw new JCEHandlerException("Unsupported key length: " + keyLength + " bits");
        }
        return bytesLength;
    }
}