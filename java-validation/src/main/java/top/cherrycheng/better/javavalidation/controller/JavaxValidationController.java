package top.cherrycheng.better.javavalidation.controller;

import com.alibaba.fastjson.JSONObject;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cherrycheng.better.javavalidation.vo.JavaxTestDto;

import java.util.Map;

/**
 * @author ChengRu
 * @date 2020/2/8 10:06
 * @Desc
 */
@RestController
@RequestMapping("validation/javax")
public class JavaxValidationController {
    @Value("#{${applyDetail.maskFields:}}")
    private Map<String, String> maskFields;

    @Value("${mypassword}")
    private String mypwd;

    @PostMapping("/test")
    public void test(@RequestBody @Validated JavaxTestDto reqDto) {
        System.out.println(reqDto);
    }

    /**
     * 测试加解密
     */
    @GetMapping("/testJasypt")
    public void testJasypt() {

        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐)
        textEncryptor.setPassword("cr345");
        //要加密的数据（数据库的用户名或密码）
        String username = textEncryptor.encrypt("root");
        String password = textEncryptor.encrypt("myadmin");
        System.out.println("username:" + username + "," + textEncryptor.decrypt(username));
        System.out.println("password:" + password + "," + textEncryptor.decrypt(password));

        System.out.println(mypwd);

        //        Demo2
        //        String key = "datasource-jasypt-key";
        //        String password2 = "ts@123";
        //        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        //        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        //        config.setAlgorithm("PBEWithMD5AndDES");
        //        config.setPassword(key);
        //        encryptor.setConfig(config);
        //        String encryptPwd = encryptor.encrypt(password2);
        //        System.out.println(encryptPwd );
        //        System.out.println(encryptor.decrypt(encryptPwd));
    }

    /**
     * 自动脱敏
     */
    @PostMapping("/mask")
    public JSONObject mask(@RequestBody JavaxTestDto reqDto) throws ClassNotFoundException {
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(reqDto);
        Class<?> aClass = Class.forName("top.cherrycheng.better.javavalidation.util.MaskUtil");

        maskFields.entrySet().forEach(x -> {
            if (null != jsonObject.getString(x.getKey())) {
                try {
                    jsonObject.put(x.getKey(), aClass.getMethod(x.getValue(), String.class)
                            .invoke(null, jsonObject.getString(x.getKey())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return jsonObject;
    }
}
