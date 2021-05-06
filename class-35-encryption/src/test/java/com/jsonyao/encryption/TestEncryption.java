package com.jsonyao.encryption;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 测试加解密相关
 *
 * @since 2021-05-06
 */
public class TestEncryption {

    public static void main(String[] args) {
        // 1、编码技术: 很多消息摘要、加解密算法都是针对二进制的

        try {
            // Base64编码: Q0FG => 6位一组, 3 * 8 = 4 * 6, 压缩率比HEX高, 可能会出现+、=符号
            String base64Str = Base64.getEncoder().encodeToString("CAF".getBytes("ASCII"));
//            System.err.println(base64Str);

            // Base64解码: CAF
            String base64Org = new String(Base64.getDecoder().decode(base64Str), "ASCII");
//            System.err.println(base64Org);
        } catch (UnsupportedEncodingException e) {
            // do nothing
        }

        // 2、消息摘要: 16字节, MD5、SHA, 哈希的算法, 单向的不能逆推, 优秀的哈希产生的结果是定长的, 碰撞率比较低, 少量Hash就千差万别
        // 作用: 用于验证原消息是否有改变、不同输入不同输出、相同输入相同输出、在数字签名中可以用来证明消息没被别人篡改过
        String input = "hello world";

        // MD5国内一般都使用HEX格式编码, 但事实上Base64也可
//        System.err.println(MD5(input));// HEX是一个字符占4位, 16个字节, 共32个字符 => 5EB63BBBE01EEED093CB22BB8F5ACDC3
//        System.err.println(MD5(MD5(input) + "加盐"));// MD5加盐(随机字符串), 极大加强安全性: 1FEED1AECF760C313879517FA3A8F2B6
//        System.err.println(SHA1(input));// HEX是一个字符占4位, 20个字节, 共40个字符 => 2AAE6C35C94FCFB415DBE95F408B9CE91EE846ED
//        System.err.println(SHA256(input));// HEX是一个字符占4位, 32个字节, 共64个字符 => B94D27B9934D3E08A52E52D7DA7DABFAC484EFE37A5380EE9088F7ACE2EFCDE9

        // 3、加密算法: RSA跨平台, 不推荐使用JAVA方式(PKCS8)来生成私钥和公钥, 推荐用OPEN SSL(Git Hub)方式生成(PKCS1 | PKCS8)
        //      1) 对称加密: 收发双方约定同一个Key, Key被劫持了就不安全了
        //      2) 非对称加密: 收发双发约定一对Key, 只把一半在网上流传, 另一半不流传
        // RSA非对称加密默认使用Base64格式编码
        String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDTse/HAlvTdgaUn4uCFiC6o++G\n" +
                "SPQ9XN3DjBOyitzOO0atlTG68KZnhEoUMZGJ2grKgWu49xjV8XY+8AUziAZfFJ5g\n" +
                "LXN/e9QuJ+yLm7hPfEmOAZorGLLxUV1ms266RqD9V9l2UJGlmVqo4ZV9pRnbxW8a\n" +
                "7sh2iR/2pIM5p3atiwIDAQAB";
        String privateKeyStr = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANOx78cCW9N2BpSf\n" +
                "i4IWILqj74ZI9D1c3cOME7KK3M47Rq2VMbrwpmeEShQxkYnaCsqBa7j3GNXxdj7w\n" +
                "BTOIBl8UnmAtc3971C4n7IubuE98SY4BmisYsvFRXWazbrpGoP1X2XZQkaWZWqjh\n" +
                "lX2lGdvFbxruyHaJH/akgzmndq2LAgMBAAECgYEAgKkZeNNXKdsGvteEu4hlVeoC\n" +
                "zpOSVaUWZx3Abvf0oSbnmuIdOme+SxXczA8gTC8H9fHYna8YGhdJ7ZCFKL+YVqA3\n" +
                "y3ytx3VPcUR/DIexfsUKTQwxWbmwFOXjimHd1EOWglhP16jX+JqJdcYO8WUDaYJY\n" +
                "fII+52w4IBqXQzV0ROECQQDsjP6eVx+ocT3vPWKuO4k68xNIbJYv0rI6OUirq+iW\n" +
                "fEyt+qQg46p7cVcwjVy+smTEOcALljGp3qvBF+c9cEgbAkEA5RnFOnG4OWfEkzPF\n" +
                "bJPqZ49E59Yt6Gh0EC163IzFtirB/GMumrT70Gs1vItrZb8iYuhaK1uZB0lJLJPB\n" +
                "ppdnUQJBAKVa2hHtbR/eKSE3k+efjoo6qNwTq9i6PAQfTwFSJkArm55yepDTFLU9\n" +
                "wWkbKB3VrkLM68Yts4G/Oei8wNRdzMkCQE6LwE/iTzv3NLEXLdek+teYihJGHyUw\n" +
                "MqKdRSM6bEqhbDKguoi2BiOVri2/SwnuNtbcPJXi6JtT5++NlPYNsJECQAsU+Ama\n" +
                "zDNyx8oq/s/JmB/jk6HmNMUaujsBd4N3yvO9awaLEgeghD02lIa0smd9qgqLVhm8\n" +
                "rl0xPQV91p5pcFU=";

        // 如果不采用读文件的, 需要手动清理IDE加的"\n"
        publicKeyStr = publicKeyStr.replace("\n", "");
        privateKeyStr = privateKeyStr.replace("\n", "");

        String rsaEncryptStr = rsaEncrypt(input, toPublicKey(publicKeyStr));
//        System.err.println(rsaEncryptStr);// UdYxJbZirWPDAHhIaTLA4q6jrdh0MWNu+OFZaAP5rZqvR9Vzynl53uyUe6OisyRxHS++q8EnHu6hEaFGdJNimuZ99yo0Lpq8AxudlUd7j9JvFd2EmAo+phA1KnC+SHn1BOF6qYVymhjxnsWnB2IHACIcFhWcHinC7txSVjZHQo0=
        String rsaDecryptStr = rsaDECRYPT(rsaEncryptStr, toPrivateKey(privateKeyStr));
//        System.err.println(rsaDecryptStr);// hello world

        // 4、数字签名: 散列+加密 = 消息摘要 + 非对称加密
        //      1) 证明消息没被别人篡改过 => 消息摘要
        //      2) 证明确实是发送方发过来的 => 非对称加密, 使用自己的私钥加密消息
        String sign = rsaSign(toPrivateKey(privateKeyStr), input);
//        System.err.println(sign);// PDhFYAx3FSIajHFYwP35PInipQxmFA/qtuCJXPALOoUf2nZlIC3Xt9qfSK/hovhhXBIuOSReTnKLCHDuvXJ0rfNVC1SqO4yYl5PXeiHgOjUj18VLxKyId0H9Z4+L47Uhb3JSsNv+X8trE6Q4dDj29xjVeVEBkfsKYdqjc8QxSPQ=
        boolean res = rsaVerifySign(toPublicKey(publicKeyStr), input, sign);
//        System.err.println(res);// true
    }

    // Java利用MD5WithRSA实现数字签名, 一定要用自己的私钥进行数字签名
    public static String rsaSign(PrivateKey privateKey, String str) {
        try {
            Signature signature = Signature.getInstance("MD5WithRSA");// 算法类型
            signature.initSign(privateKey);// 初始化私钥
            signature.update(str.getBytes("UTF-8"));// 数据字符集采用UTF-8
            byte[] sign = signature.sign();// 数字签名范围到此为止
            return Base64.getEncoder().encodeToString(sign);// 通常数字签名使用Base64编码
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Java利用MD5WithRSA实现数字验签, 一定要用对方的公钥进行验签
    public static boolean rsaVerifySign(PublicKey publicKey, String str, String sign) {
        try {
            Signature signature = Signature.getInstance("MD5WithRSA");// 算法类型
            signature.initVerify(publicKey);// 初始化公钥
            signature.update(str.getBytes("UTF-8"));// 数据字符集采用UTF-8
            byte[] bytes = Base64.getDecoder().decode(sign);// 通常数字签名使用Base64编码
            return signature.verify(bytes);// 数字验签范围到此为止
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Java生成RSA非对称加密公钥对象
    public static PublicKey toPublicKey(String str) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");// 算法类型
            byte[] bytes = Base64.getDecoder().decode(str);// OPENSSL 生成的RSA公钥采用Base64编码
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(bytes);// 公钥统一标准X509编码
            return keyFactory.generatePublic(x509EncodedKeySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Java生成RSA非对称加密私钥对象
    public static PrivateKey toPrivateKey(String str) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");// 算法类型
            byte[] bytes = Base64.getDecoder().decode(str);// OPENSSL 生成的RSA公钥采用Base64编码
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(bytes);// JAVA私钥只能读PKCS8格式
            return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return null;
    }

    // RSA非对称加密
    public static String rsaEncrypt(String str, Key key) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");// 算法类型
            cipher.init(Cipher.ENCRYPT_MODE, key);// 加密模式
            byte[] bytes = str.getBytes("UTF-8");// 字符集
            byte[] doFinal = cipher.doFinal(bytes);// RSA范围到此为止

            // RSA通常使用Base64编码
            return Base64.getEncoder().encodeToString(doFinal);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return null;
    }

    // RSA非对称解密
    public static String rsaDECRYPT(String str, Key key) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");// 算法类型
            cipher.init(Cipher.DECRYPT_MODE, key);// 解密模式
            byte[] bytes = Base64.getDecoder().decode(str);// RSA通常使用Base64编码
            byte[] doFinal = cipher.doFinal(bytes);// RSA范围到此为止

            // 这里采用UAT-8字符集
            return new String(doFinal,"UTF-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    // MD5消息摘要
    public static String MD5(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");// 算法类型
            md5.update(str.getBytes("UTF-8"));// 字符集
            byte[] digest = md5.digest();// MD5范围到此为止
            return DatatypeConverter.printHexBinary(digest);
        } catch (NoSuchAlgorithmException e) {
            // do nothing
        } catch (UnsupportedEncodingException e) {
            // do nothing
        }

        return null;
    }

    // SHA1不太安全
    public static String SHA1(String str) {
        try {
            MessageDigest SHA1 = MessageDigest.getInstance("SHA");
            SHA1.update(str.getBytes("UTF-8"));
            byte[] digest = SHA1.digest();// SHA1范围到此为止
            return DatatypeConverter.printHexBinary(digest);
        } catch (NoSuchAlgorithmException e) {
            // do nothing
        } catch (UnsupportedEncodingException e) {
            // do nothing
        }

        return null;
    }

    // SHA256比较安全
    public static String SHA256(String str) {
        try {
            MessageDigest SHA256 = MessageDigest.getInstance("SHA-256");
            SHA256.update(str.getBytes("UTF-8"));
            byte[] digest = SHA256.digest();// SHA256范围到此为止
            return DatatypeConverter.printHexBinary(digest);
        } catch (NoSuchAlgorithmException e) {
            // do nothing
        } catch (UnsupportedEncodingException e) {
            // do nothing
        }

        return null;
    }
}
