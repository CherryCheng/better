package top.cherrycheng.study.mybatis.model;

import lombok.Data;

import java.util.Date;

/**
 * 功能结果
 *
 * @author ChengRu
 * @date 2020/3/30 8:15
 * @Desc
 */
@Data
public class FeatureResult {
    /**
     * id
     */
    private Long id;
    /**
     * 的名字
     */
    private String name;
    /**
     * 证书没有
     */
    private String certNo;
    /**
     * 年龄
     */
    private String age;
    /**
     * 移动
     */
    private String mobile;
    /**
     * bk certnovalidday我
     */
    private Integer bk_certnovalidday_i;
    /**
     * 申请时间
     */
    private Date apply_time;
    /**
     * 结婚的状态
     */
    private String marry_status;
    /**
     * 工作的公司
     */
    private String work_company;
    /**
     * 收入范围
     */
    private String income_range;
    /**
     * 接触name1
     */
    private String contact_name1;
    /**
     * 接触relation1
     */
    private String contact_relation1;
    /**
     * 接触mobile1
     */
    private String contact_mobile1;
    /**
     * 接触remark1
     */
    private String contact_remark1;
    /**
     * 接触name2
     */
    private String contact_name2;
    /**
     * 接触relation2
     */
    private String contact_relation2;
    /**
     * 接触mobile2
     */
    private String contact_mobile2;
    /**
     * 接触remark2
     */
    private String contact_remark2;
    /**
     * 请求的ip
     */
    private String request_ip;
    /**
     * 设备类型
     */
    private String device_type;
    /**
     * 设备的mac
     */
    private String device_mac;
    /**
     * 设备风险
     */
    private String device_risk;
    /**
     * 民族
     */
    private String nation;
    /**
     * 出生
     */
    private Date birth;
    /**
     * 身份证有效期
     */
    private Date id_card_valid_end;
    /**
     * 身份证地址
     */
    private String id_card_address;
    /**
     * 身份证签署办公室
     */
    private String id_card_sign_office;

}
