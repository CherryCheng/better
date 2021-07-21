package top.cherrycheng.study.mybatis.model;

import lombok.Data;

import java.util.Date;

/**
 * @author ChengRu
 * @date 2020/3/30 8:15
 * @Desc
 */
@Data
public class FeatureResult {
    private Long id;
    private String name;
    private String certNo;
    private String age;
    private String mobile;
    private Date bk_certnovalidday_i;
    private Date apply_time;
    private String marry_status;
    private String work_company;
    private String income_range;
    private String contact_name1;
    private String contact_relation1;
    private String contact_mobile1;
    private String contact_remark1;
    private String contact_name2;
    private String contact_relation2;
    private String contact_mobile2;
    private String contact_remark2;
    private String request_ip;
    private String device_type;
    private String device_mac;
    private String device_risk;
    private String nation;
    private Date birth;
    private String id_card_address;
    private String id_card_sign_office;

}
