package top.cherrycheng.better.javavalidation.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
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

    @PostMapping("/test")
    public void test(@RequestBody @Validated JavaxTestDto reqDto) {
        System.out.println(reqDto);
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
