package top.cherrycheng.better.encryptdecrypt.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA工具
 *
 * @author ChengRu
 * @date 2019/12/2 16:20
 * @Desc
 */
public class RSAUtil {
    private static final String signature_algorithm = "SHA256withRSA"; // 签名算法
    private static final String encryptAlgorithm = "RSA"; // 加密算法
    private static final String decryptAlgorithm = "RSA"; // 解密算法
    private static final String charset = "UTF-8";
    private static final int max_encrypt_block = 234; //2048位rsa单次最大加密长度
    private static final int max_decrypt_block = 256; //2048位rsa单次最大解密长度

    /**
     * 我方公钥，私钥
     */
    public static PublicKey my_public_key = null;
    public static PrivateKey my_private_key = null;

    /**
     * 合作方公钥，私钥
     */
    public static PublicKey cooperator_public_key = null;
    public static PrivateKey cooperator_private_key = null;


    public static void main(String[] args) {
        RSAUtil rsaUtil = new RSAUtil(cooperatorPubKey, cooperatorPriKey, myPubKey, myPriKey);
//        生成双方公钥，私钥
//        rsaUtil.generateKey();
//        rsaUtil.generateKey();

        String requestPlain = "{ \"data\":{ \"country\": \"CN\" } }";
        System.out.println("【360-->我方】发送前的明文：" + requestPlain);
//用对方公钥加密，自己私钥加签。
        String requestData = rsaUtil.requestDemo(requestPlain, my_public_key, cooperator_private_key);
        System.out.println("【360-->我方】的请求报文：" + requestData);
//用对方公钥验签，自己私钥解密。
        String plainData = rsaUtil.checkAndDecrypt(requestData, cooperator_public_key, my_private_key);
        System.out.println("【我方】收到请求，将360报文解密后明文为：" + plainData);

        String afterMyBusiness = RSAUtil.doMyBusiness(plainData);
        System.out.println("【我方】业务处理完，响应360的明文：" + afterMyBusiness);
//用对方公钥加密，自己私钥加签。
        String backData = rsaUtil.doBusinessAndReturn(afterMyBusiness, cooperator_public_key, my_private_key);
        System.out.println("【我方-->360】的实际报文：" + backData);
//用对方公钥验签，自己私钥解密。
        String wdPlainData = rsaUtil.checkAndDecrypt(backData, my_public_key, cooperator_private_key);
        System.out.println("【360】收到请求，解析我方报文后的明文：" + wdPlainData);
    }

    private static String doMyBusiness(String plainData) {
        JSONObject businessResult = JSONObject.parseObject(plainData);
        businessResult.put("remark", "已完成业务处理");
        return businessResult.toJSONString();
    }

    RSAUtil() {
    }

    RSAUtil(String cooperatorPubKey, String cooperatorPriKey, String myPubKey, String myPriKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            my_public_key = keyFactory.generatePublic(new X509EncodedKeySpec(Base64Utils.decodeFromString(myPubKey)));
            my_private_key = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64Utils.decodeFromString(myPriKey)));

            cooperator_public_key = keyFactory.generatePublic(new X509EncodedKeySpec(Base64Utils.decodeFromString(cooperatorPubKey)));
            cooperator_private_key = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64Utils.decodeFromString(cooperatorPriKey)));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String sign(String param, PrivateKey privateKey) {
        try {
            if (privateKey == null) {
                throw new RuntimeException("私钥未初始化");
            }
            Signature signature = Signature.getInstance(signature_algorithm);
            signature.initSign(privateKey);
            signature.update(param.getBytes(charset));
            return Base64Utils.encodeToString(signature.sign());
        } catch (Exception e) {
            throw new RuntimeException("签名异常", e);
        }
    }

    /**
     * 用公钥加密
     */
    public static String encrypt(String param, PublicKey publicKey) {
        if (publicKey == null) {
            throw new RuntimeException("公钥未初始化");
        }
        if (StringUtils.isEmpty(param)) {
            throw new IllegalArgumentException("待加密数据为空");
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Cipher cipher = Cipher.getInstance(encryptAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] data = param.getBytes(charset);
            int inputLen = data.length;

            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen > max_encrypt_block + offSet) {
                    cache = cipher.doFinal(data, offSet, max_encrypt_block);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * max_encrypt_block;
            }
            return Base64Utils.encodeToString(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("加密异常", e);
        }
    }


    public static boolean veriSign(String param, String sign, PublicKey publicKey) {
        try {
            if (publicKey == null) {
                throw new RuntimeException("公钥未初始化");
            }
            Signature signature = Signature.getInstance(signature_algorithm);
            signature.initVerify(publicKey);
            signature.update(param.getBytes(charset));
            return signature.verify(Base64Utils.decodeFromString(sign));
        } catch (Exception e) {
            throw new RuntimeException("验签失败", e);
        }
    }

    public static String decrypt(String param, PrivateKey privateKey) {
        if (privateKey == null) {
            throw new RuntimeException("私钥未初始化");
        }
        if (StringUtils.isEmpty(param)) {
            throw new IllegalArgumentException("待解密数据为空");
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Cipher cipher = Cipher.getInstance(decryptAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] data = Base64Utils.decodeFromString(param);
            int inputLen = data.length;
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen > max_decrypt_block + offSet) {
                    cache = cipher.doFinal(data, offSet, max_decrypt_block);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                i++;
                out.write(cache, 0, cache.length);
                offSet = i * max_decrypt_block;
            }
            return new String(out.toByteArray(), charset);
        } catch (Exception e) {
            throw new RuntimeException("解密处理异常", e);
        }
    }

    public String requestDemo(String requestPlain, PublicKey publicKey, PrivateKey privateKey) {
        String data = encrypt(requestPlain, publicKey);
        String sign = sign(data, privateKey);
        JSONObject jsonObject = new JSONObject().fluentPut("sign", sign).fluentPut("data", data);
        return jsonObject.toJSONString();
    }

    public String checkAndDecrypt(String requestData, PublicKey publicKey, PrivateKey privateKey) {
        // 接收请求获取参数
        JSONObject requestParams = JSONObject.parseObject(requestData);

        /*取参数及签名*/
        String data = requestParams.getString("data");
        String sign = requestParams.getString("sign");

        /*验签*/
        boolean f = veriSign(data, sign, publicKey);
        if (f) { // 验签通过才需要解密,否则认为非法请求
            /*解密*/
            return decrypt(data, privateKey);
        } else {
            return null;
        }
    }

    private String doBusinessAndReturn(String dec, PublicKey publicKey, PrivateKey privateKey) {
        /*模拟业务逻辑，在原JSON上加备注*/
        JSONObject businessResult = JSONObject.parseObject(dec);
        /*加密*/
        String responseParam = encrypt(businessResult.toJSONString(), publicKey);
        /*签名*/
        String responseSign = sign(responseParam, privateKey);
        Map<String, String> response = new HashMap<>(2);
        response.put("data", responseParam);
        response.put("sign", responseSign);
        /*返回*/
        return JSONObject.toJSONString(response);
    }

    public void generateKey() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(encryptAlgorithm);
            keyPairGen.initialize(2048);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            System.out.println("privateKey: " + Base64Utils.encodeToString(privateKey.getEncoded()));
            System.out.println("publicKey:  " + Base64Utils.encodeToString(publicKey.getEncoded()));
        } catch (NoSuchAlgorithmException ignore) {

        }
    }

    // 静态加载，提高效率，但是配置的修改都需要重启server才能生效
    // 对方base64后公钥字符串
    public static String cooperatorPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqRZGMQ4sqXenyetWB3dIDXtXQO6CbLsJxf4GrNjq00HbD3iKuz1sdrfchNfBY7kZDNl1W/WWiDEFMZcZvvIMCE8ej+vNPooqBJu9OwxnwgyfmxubvQWrP3YsPUjeonVJCBsb7oPFZFSDhB4xXMoAl0+7tmYKSXhT9IwPKpvhEwpUVaPx3F3oGvai5DFV0dxct4XLrb8iBste+53TIBvA7ZJCLHTNJ+DBW9c/SHDQrCok/oPWAkd36h3v84pUb4BXA5R3JI6ja5DcpqPpZIVBavVfpMeuAwSnHSzc1cYjtp5wcVv6pTxfLjiSkb3/slmRlm4qsM1VPH6NtE4C2pSUhwIDAQAB";
    // 对方base64后私钥字符串
    public static String cooperatorPriKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCpFkYxDiypd6fJ61YHd0gNe1dA7oJsuwnF/gas2OrTQdsPeIq7PWx2t9yE18FjuRkM2XVb9ZaIMQUxlxm+8gwITx6P680+iioEm707DGfCDJ+bG5u9Bas/diw9SN6idUkIGxvug8VkVIOEHjFcygCXT7u2ZgpJeFP0jA8qm+ETClRVo/HcXega9qLkMVXR3Fy3hcutvyIGy177ndMgG8DtkkIsdM0n4MFb1z9IcNCsKiT+g9YCR3fqHe/zilRvgFcDlHckjqNrkNymo+lkhUFq9V+kx64DBKcdLNzVxiO2nnBxW/qlPF8uOJKRvf+yWZGWbiqwzVU8fo20TgLalJSHAgMBAAECggEBAJ1Wa7REA00iWZBrmx7RYMuixPtXtSdmF98T1LRMGJ7B/gx0V2uJ6X7C8cDvXzXjTCsHwsvyR8OtEa8mcj+azZWBweecPN0ABb9KCBiFH5uS0YWseAu41bP0y4+vWfOD/ZQ8XY5hMXF9ru9mhPpZwZkLCZndc58QB9db6NT2PcbPYIA2z4vwSUPo3i4hzRwU5y8T3vGQi+EwYftHmqmj9QKnKbQCq9aZyw7ME+5lZlKIZBUk2d+r8MHSbACg0e6a+eHbLkZ8L9cP2S1c91dwuU3jdXzOAqTingPbTPIYNC4b1zfWg8LgBCcw31pKHtauMu3/OH0B43iOZPxmBlsO2vkCgYEA+/E17iWGeFHXA/Bjv0lmyWLmABkKALzxhJcPr5vICI2hPh64hA4jjMSPrbRm50kVrzWo9aZbuyY9xO3SKYBbMkDXSqKZ+WcCw4TGAduqpbcv7hv9l28tCNWWhL2FZ21dAjcqcmEe1Ix+rdpNDZXOpqBOpkLKJKAoLKxTFNCQ81UCgYEAq89w6o+/TmuDoY8qx7qHCI6HmTeKq7LBVAt+mBy/9tytSAeFYtuPvD02lt6tA3Xd31E8gbYSQ4kFPYjqtlChABWdC/3/TNtt/MzCFydcoWzzG0iJ4SsNq7X9kSApPAsgcB8152sEqAQf5l/CArxpKPaknRU/GJJa+1nI0hXDYGsCgYAIHn3ebtcmiXykY9rTD0qAsu82kZgA7hXacAPRZPPNUsMffV3043/ByZPXxTiLu483VkNbp0DNhy32arObIsfNlslZXmQX5XnxUxYOeUXGkWC6C6zgdHS/FCMFpBXof12/9IcpqHeV4fEzKRXSmkFOnVvrf7uD8qVYyzTiNtZ1AQKBgQCQ+Hm7wk1XG0s3kHk1jIOSScnLE92ToUKV6xkHuwKTh4NDSu1XDBLNcA1W4k+0osjzPTXD8WCZ0c01Lgl2irQ/QP2B9/Fq5aB59kAzj0gPAcJkHCITd+OSJv7tbOi+PFlDomI2gcutBcXw+o3XBynooa0myp2x2xSgEBNuiWSRPwKBgFypq1lLlODBkl0a9SIvkXaHLvJIdWadPT2u7Qel9l9Vk0gsOY3C/699E8pMxVGf9B5Q1m/RU+RMNFDNtbmmzoC4u7GZ9lWhLuBSg72H1PWN+Xntm+rjLz1AbvZicE1xn552y5ggW+wRfHMcDSdMmco7lwu9TGIYe4WCMuL1WE9g";

    // 己方base64后公钥字符串
    public static String myPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAukXhbNQdUkl1WTxYAELZLvoe/ohhIR1ujf9dTGJ4lnHcW5m+dCYn0V904OH+6rLAv9HgslTl626Ao6LGWk91nqHUEDHHHQiKAglTN+Io+q20hHxFfMVE7iF8rpiV0UT42uUhXyPvSEYhBp4cFIl0viJueerxfgl0H+IyH9CdoM1JMHhVei4lpjiYngKU87ZoRh0a3Sreh3eaz1UxxzJlAWDI9Uh78dP4yXn887kiQnku9gMgiwd30AWVNFy+tqRlSi2lQzST4x2FfljrxqdwsXXRwTEtYkt/bara41EIenZyHRGp4Jk71Z/OUKTdJlk1MQp+G4Int9oJpTyTEHCiPwIDAQAB";
    // 己方base64后私钥字符串
    public static String myPriKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC6ReFs1B1SSXVZPFgAQtku+h7+iGEhHW6N/11MYniWcdxbmb50JifRX3Tg4f7qssC/0eCyVOXrboCjosZaT3WeodQQMccdCIoCCVM34ij6rbSEfEV8xUTuIXyumJXRRPja5SFfI+9IRiEGnhwUiXS+Im556vF+CXQf4jIf0J2gzUkweFV6LiWmOJieApTztmhGHRrdKt6Hd5rPVTHHMmUBYMj1SHvx0/jJefzzuSJCeS72AyCLB3fQBZU0XL62pGVKLaVDNJPjHYV+WOvGp3CxddHBMS1iS39tqtrjUQh6dnIdEangmTvVn85QpN0mWTUxCn4bgie32gmlPJMQcKI/AgMBAAECggEAP+B632EV9QPv0/wXiW9jHvLy8gI+nKaFJPivO+SAycad9GOSpi33dZswb3XQfxdxddY5Ia5vcf7g6jyFnK6m/REtsagASfqFqREdkoph1yT+RHiBM7pKWwbsxMrahXsLg7UTbpbaFYTHCofpxTkl6ZXRIMnqqnf/dHeTDMnzZBKrAx7mkmqYQVyaAwjEreZbi/tLYutUV1Bceqp1QX02XX/6CPpA0BPWEZDQmnwMtG05N3IigYFd09PG/qcM968eI4OOijLNu/bpQYRE4ELSgP7JpmpNRzTGwSMKOpLs7IOPkdMVnu/KhhRF6QiWoS2YnusRmwZJtTKHFjezW8sUUQKBgQD1Ht979OP8SCAixkDSxgIPaq6DIm7h1NFEc5ZuKyi3NQAn5Pka4Ei0dTNrs1/Ujv/IAVA6wQNtztdazaKmCC35exMV1+lFCC6VBtGGDwfnnPK/p3szHlmlimpQu3E38gosAmbxbOfoGFAtW4VB+uBNwrs8vQzrRenw6eke/OjZUwKBgQDCil1keaKGXRstslzoCM8Nw6Nc284ywH4+W3vgnIifBw9HxUk6/wmovFsjMzgS8PDIheUnra0zwA07CMYhK7dlppQIWR8oUoIS1wNfMhRIrYXvEiusGzueirlb9WBg4qI4a1R/VmbhzwyPGrsM/aHvvYxrMgjU4m6fuordIRp55QKBgH/1kez/tlRown3kyMo/G6w4oMlMHF4/3s7BGHKCjUq2nFI3MeNZ2hp6ZFGA3jH0RIqARQWm5MdaBwob9yB9fKKUIS5x/BBRaR/a4138UsASKpbqM2JIIbxNOZrTfxMZgz3eMyctFxMOPiyvbi03bgip81yNxES9xpZBRbUridKJAoGABlr41Bh5K8SM58BE8rue8ActJhJ0OQo61ZSthlf2yqMzZ5fVXZ0JVr7Yh1zb/xpp81MUemhdGSy0AfK2bkz8T9DSUzd+IhYX87+cd5auPPWWQ4onbRbyqqpDhfKNNH0S6Z7wArzy3XujIGWyKKl8idV6aeIXkcTX8lXoxXKYZ9UCgYEAiHq7xp0gdu0BGOgnR89pW1sTabCWpoQ/H2Xf43isdNtlVyowmS5b5nKDSnonUImeqrenQcRkkozmDVWfTA54Fo5GArwPr5I5MJ+np1HQoSuhttzICSLcnceBoLiFHN1S1E221rRUjTUM9o5Ay8JZXxCGbWVclsrCYNV3kdvzriE=";

}
