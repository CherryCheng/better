package top.cherrycheng.study.test.type;

import lombok.Getter;

@Getter
public enum ReferenceType {

    FIELD(1, "字段"),
    FUTURE(2, "特征"),
    ASSEMBLY(3, "组件"),
    STRATEGY(4, "策略"),
    ACCESS(5, "审批入口"),
    PROJECT(6, "项目"),
    RULE(7, "规则"),
    VARIABLE(8, "变量"),
    NAMELIST(9, "名单"),
    MODEL(10, "模型"),
    COLLECTOR(11, "采集器"),
    STRATEGY_PACKAGE(11, "策略包"),
    ;

    private int code;

    private String message;

    ReferenceType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ReferenceType findByValue(int code) {
        for (ReferenceType channel : values()) {
            if (channel.getCode() == code) {
                return channel;
            }
        }
        return null;
    }
}
