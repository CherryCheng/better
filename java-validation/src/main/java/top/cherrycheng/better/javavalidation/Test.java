package top.cherrycheng.better.javavalidation;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;

/**
 * @author ChengRu
 * @date 2020/4/24 13:17
 * @Desc
 */
public class Test {
    public static void main(String[] args) {
        String key = "datasource-jasypt-key";
        String password = "ts@123";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPassword(key);
        encryptor.setConfig(config);
        String encryptPwd = encryptor.encrypt(password);
        System.out.println(encryptPwd);
        System.out.println(encryptor.decrypt(encryptPwd));
    }
}
