package top.cherrycheng.better.encryptdecrypt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 加解密controller：加密-加签-验签-解密过程
 *
 * @author ChengRu
 * @date 2019/12/2 16:25
 * @Desc
 */
@RestController
@RequestMapping("/test/360")
public class EncryptDecryptController {
//    private RsaService rsaService;
//    /**
//     * 构建【网贷平台-->360】请求
//     * 返回格式：{“data”:”dataStr…”, “sign”:”signStr…”}
//     */
//    @PostMapping("/buildRequest360")
//    public JSONObject buildRequest360(@RequestBody JSONObject reqDto) {
//        JSONObject resDto = new JSONObject();
//        rsaService = new RsaService("360");
//        String encryptResult = rsaService.encrypt(reqDto.toJSONString());
//        resDto.put("data", encryptResult);
//        resDto.put("sign", rsaService.sign(encryptResult));
//        return resDto;
//    }
//
//    /**
//     * 解析【360-->网贷平台】请求
//     * 请求格式：{“data”:”dataStr…”, “sign”:”signStr…”}
//     */
//    @PostMapping("/decodeRequest360")
//    public Object decodeRequest360(@RequestBody JSONObject requestData) {
//        rsaService = new RsaService("360");
//        String sign = requestData.getString("sign");
//        String data = requestData.getString("data");
//        rsaService.verify(data,sign);
//        String decrypt = rsaService.decrypt(data);
//        return StringUtil.isAllEmpty(decrypt) ? decrypt : JSONObject.parseObject(decrypt);
//    }
//
//    /**
//     * 构建请求【本地双方公钥私钥都有时使用】
//     * 返回格式：{“data”:”dataStr…”, “sign”:”signStr…”}
//     */
//    @PostMapping("/buildRequest")
//    public JSONObject buildRequest(@RequestBody JSONObject reqDto) {
//        RSAUtil rsaUtil = new RSAUtil(RSAUtil.cooperatorPubKey,RSAUtil.cooperatorPriKey,RSAUtil.myPubKey,RSAUtil.myPriKey);
//        //用对方公钥加密，自己私钥加签。
//        String entrypt = rsaUtil.requestDemo(JSONObject.toJSONString(reqDto),rsaUtil.my_public_key,rsaUtil.cooperator_private_key);
//        return JSONObject.parseObject(entrypt);
//    }
//
//    /**
//     * 解析请求【本地双方公钥私钥都有时使用】
//     * 请求格式：{“data”:”dataStr…”, “sign”:”signStr…”}
//     */
//    @PostMapping("/decodeRequest")
//    public Object decodeRequest(@RequestBody JSONObject requestData) {
//        RSAUtil rsaUtil = new RSAUtil(RSAUtil.cooperatorPubKey,RSAUtil.cooperatorPriKey,RSAUtil.myPubKey,RSAUtil.myPriKey);
//        //用对方公钥验签，自己私钥解密。
//        String plainData = rsaUtil.checkAndDecrypt(requestData.toJSONString(),rsaUtil.cooperator_public_key,rsaUtil.my_private_key);
//        return StringUtil.isAllEmpty(plainData) ? plainData : JSONObject.parseObject(plainData);
//    }

}
