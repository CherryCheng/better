package top.cherrycheng.better.javavalidation.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * @author ChengRu
 * @date 2020/2/8 10:08
 * @Desc
 */
@Data
public class JavaxTestDto {
    /**
     * 非空限制
     */
    @NotBlank
    private String notNullStr;
    /**
     * 限制长度1-5
     */
    @Size(max = 5, min = 1)
    private String limitLength1_5Str;

    /**
     * 限制只能输入M,F两个值
     */
//    @Size(max = 1) // 成茹: todo 这个可以去掉？不填会怎样？
    @Pattern(regexp = "^[MF]$", message = "性别只能是M,F")
    private String limitPatternStr;

    /**
     * 限制是正数（大于0）
     */
    @Positive
    private Long limitGreaterThan0;

    /**
     * 限制手机号：正则校验
     */
    @Pattern(regexp = "^([0-9]{11})?$")
    private String mobileNo;

    private String certNo;
    private String name;
}
