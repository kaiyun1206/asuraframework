/*
 * Copyright (c) 2018. sunkaiyun1206@163.com.
 */
package org.asuraframework.commons.crypt;

import org.apache.commons.codec.binary.Hex;
import org.asuraframework.commons.exception.AsuraCryptException;
import org.asuraframework.commons.utils.Check;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * <p>DES-加解密</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2018/7/14 下午4:45
 * @since 1.0
 */
public class DESCrypt implements ICrypt {

    private static volatile DESCrypt desCrypt;

    private DESCrypt() {

    }

    public static DESCrypt getInstance() {
        if (Check.isNull(desCrypt)) {
            synchronized (DESCrypt.class) {
                if (Check.isNull(desCrypt)) {
                    desCrypt = new DESCrypt();
                }
            }
        }
        return desCrypt;
    }

    /**
     * DES 加密
     *
     * @param input     待加密字符串
     * @param keyString 秘钥
     * @param ivString  初始化向量参数，AES 为16bytes. DES 为8bytes.
     *
     * @return 加密后字符串
     */
    public String encrypt(final String input, final String keyString, final String ivString) {
        return this.encryptBySalt(input, keyString, ivString, null);
    }

    /**
     * DES 加密（默认加盐）
     *
     * @param input     待加密字符串
     * @param keyString 秘钥
     * @param ivString  初始化向量参数，AES 为16bytes. DES 为8bytes.
     *
     * @return 加密后字符串
     */
    public String encryptByDefaultSalt(final String input, final String keyString, final String ivString) {
        try {
            return this.encryptBySalt(input, keyString, ivString, new String(CryptConstant.DEFAULT_SALT_BYTE, CryptConstant.DEFAULT_CHARSET_NAME));
        } catch (Exception e) {
            throw new AsuraCryptException("fail to encrypt by default salt.", e);
        }
    }

    /**
     * DES 加密（指定加盐字符串）
     *
     * @param input     待加密字符串
     * @param keyString 秘钥
     * @param ivString  初始化向量参数，AES 为16bytes. DES 为8bytes.
     * @param salt      盐
     *
     * @return 加密后字符串
     */
    public String encryptBySalt(final String input, final String keyString, final String ivString, final String salt) {
        if (Check.isNullOrEmpty(input, keyString, ivString)) {
            return null;
        }
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivString.getBytes(CryptConstant.DEFAULT_CHARSET_NAME));
            DESKeySpec desKeySpec = new DESKeySpec(keyString.getBytes(CryptConstant.DEFAULT_CHARSET_NAME));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(CryptConstant.DES_CRYPT);
            SecretKey key = keyFactory.generateSecret(desKeySpec);
            Cipher cipher = Cipher.getInstance(CryptConstant.DES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
            byte[] saltEncodeBytes;
            if (Check.isStrictNullOrEmpty(salt)) {
                saltEncodeBytes = input.getBytes(CryptConstant.DEFAULT_CHARSET_NAME);
            } else {
                saltEncodeBytes = saltInputEncode(input.getBytes(CryptConstant.DEFAULT_CHARSET_NAME), salt.getBytes(CryptConstant.DEFAULT_CHARSET_NAME));
            }
            byte[] encodeBytes = cipher.doFinal(saltEncodeBytes);
            return Hex.encodeHexString(encodeBytes).trim();
        } catch (Exception e) {
            throw new AsuraCryptException("fail to encrypt by default salt.", e);
        }
    }

    /**
     * DES 解密
     *
     * @param input     待解密字符串
     * @param keyString 秘钥
     * @param ivString  初始化向量参数，AES 为16bytes. DES 为8bytes.
     *
     * @return 解密后字符串
     */
    public String decrypt(final String input, final String keyString, final String ivString) {
        return this.decryptBySalt(input, keyString, ivString, false);
    }

    /**
     * DES 解密（指定加盐字符串）
     *
     * @param input     待解密字符串
     * @param keyString 秘钥
     * @param ivString  初始化向量参数，AES 为16bytes. DES 为8bytes.
     * @param isSalt    是否有盐
     *
     * @return 解密后字符串
     */
    public String decryptBySalt(final String input, final String keyString, final String ivString, final Boolean isSalt) {
        if (Check.isNullOrEmpty(input, keyString, ivString)) {
            return null;
        }
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivString.getBytes(CryptConstant.DEFAULT_CHARSET_NAME));
            DESKeySpec desKeySpec = new DESKeySpec(keyString.getBytes(CryptConstant.DEFAULT_CHARSET_NAME));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(CryptConstant.DES_CRYPT);
            SecretKey key = keyFactory.generateSecret(desKeySpec);
            Cipher cipher = Cipher.getInstance(CryptConstant.DES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
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
