package top.cherrycheng.better.javavalidation.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cherrycheng.better.javavalidation.vo.JavaxTestDto;

/**
 * @author ChengRu
 * @date 2020/2/8 10:06
 * @Desc
 */
@RestController
@RequestMapping("validation/javax")
public class JavaxValidationController {
    @PostMapping("/test")
    public void test(@RequestBody @Validated JavaxTestDto reqDto) {
        System.out.println(reqDto);
    }
}
