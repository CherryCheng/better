package top.cherrycheng.study.mybatis.model;

import lombok.Data;

/**
 * @author ChengRu
 * @date 2020/3/30 8:15
 * @Desc
 */
@Data
public class Employee {
    private Long id;
    private String lastName;
    private String gender;
    private String email;
}
