/*
 * Copyright (c) 2018. sunkaiyun1206@163.com.
 */
package org.asuraframework.commons.crypt;

import org.apache.commons.codec.binary.Hex;
import org.asuraframework.commons.exception.AsuraCryptException;
import org.asuraframework.commons.utils.Check;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * <p>AES-加解密</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2018/7/14 下午4:34
 * @since 1.0
 */
public class AESCrypt implements ICrypt {

    private static volatile AESCrypt aesCrypt;

    private AESCrypt() {

    }

    public static AESCrypt getInstance() {
        if (Check.isNull(aesCrypt)) {
            synchronized (AESCrypt.class) {
                if (Check.isNull(aesCrypt)) {
                    aesCrypt = new AESCrypt();
                }
            }
        }
        return aesCrypt;
    }

    /**
     * AES 加密
     *
     * @param input     待加密字符串
     * @param keyString 秘钥
     *
     * @return 加密后字符串
     */
    public String encrypt(final String input, final String keyString) {
        return this.encryptBySalt(input, keyString, null);
    }

    /**
     * AES 加密（默认加盐）
     *
     * @param input     待加密字符串
     * @param keyString 秘钥
     *
     * @return 加密后字符串
     */
    public String encryptByDefaultSalt(final String input, final String keyString) {
        try {
            return this.encryptBySalt(input, keyString, new String(CryptConstant.DEFAULT_SALT_BYTE, CryptConstant.DEFAULT_CHARSET_NAME));
        } catch (Exception e) {
            throw new AsuraCryptException("fail to encrypt by default salt.", e);
        }
    }

    /**
     * AES 加密（指定加盐字符串）
     *
     * @param input     待加密字符串
     * @param keyString 秘钥
     *
     * @return 加密后字符串
     */
    public String encryptBySalt(final String input, final String keyString, final String salt) {
        if (Check.isNullOrEmpty(input, keyString)) {
            return null;
        }
        try {
            Key key = new SecretKeySpec(keyString.getBytes(CryptConstant.DEFAULT_CHARSET_NAME), CryptConstant.AES_CRYPT);
            Cipher cipher = Cipher.getInstance(CryptConstant.AES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] saltEncodeBytes;
            if (Check.isStrictNullOrEmpty(salt)) {
                saltEncodeBytes = input.getBytes(CryptConstant.DEFAULT_CHARSET_NAME);
            } else {
                saltEncodeBytes = saltInputEncode(input.getBytes(CryptConstant.DEFAULT_CHARSET_NAME), salt.getBytes(CryptConstant.DEFAULT_CHARSET_NAME));
            }
            byte[] encodeBytes = cipher.doFinal(saltEncodeBytes);
            return Hex.encodeHexString(encodeBytes).trim();
        } catch (Exception e) {
            throw new AsuraCryptException("fail to encrypt by salt.", e);
        }
    }

    /**
     * DES 解密（指定加盐字符串）
     *
     * @param input     待解密字符串
     * @param keyString 秘钥
     *
     * @return 解密后字符串
     */
    public String decrypt(final String input, final String keyString) {
        return this.decryptBySalt(input, keyString, false);
    }

    /**
     * DES 解密（指定加盐字符串）
     *
     * @param input     待解密字符串
     * @param keyString 秘钥
     * @param isSalt    是否有盐
     *
     * @return 解密后字符串
     */
    public String decryptBySalt(final String input, final String keyString, final Boolean isSalt) {
        if (Check.isNullOrEmpty(input, keyString)) {
            return null;
        }
        try {
            Key key = new SecretKeySpec(keyString.getBytes(CryptConstant.DEFAULT_CHARSET_NAME), CryptConstant.AES_CRYPT);
            Cipher cipher = Cipher.getInstance(CryptConstant.AES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decodeHexBytes = Hex.decodeHex(input.toCharArray());
            byte[] decodeBytes = cipher.doFinal(decodeHexBytes);
            byte[] saltDecodeBytes;

            if (Check.isNull(isSalt) || isSalt.booleanValue() == false) {
                saltDecodeBytes = decodeBytes;
            } else {
                saltDecodeBytes = saltInputDecode(decodeBytes);
            }
            return new String(saltDecodeBytes, CryptConstant.DEFAULT_CHARSET_NAME);
        } catch (Exception e) {
            throw new AsuraCryptException("fail to decrypt by default salt.", e);
        }
    }
}
