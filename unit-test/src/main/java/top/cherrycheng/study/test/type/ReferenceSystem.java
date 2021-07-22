package top.cherrycheng.study.test.type;

import lombok.Getter;

@Getter
public enum ReferenceSystem {

    PORTAL("portal", "portal"),
    UAC("uac", "统一接入中心"),
    FCC("fcc", "特征计算中心"),
    SMP("smp", "决策中心-SMP"),
    DMP("dmp", "决策中心-DMP");

    private String code;

    private String message;

    ReferenceSystem(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ReferenceSystem findByValue(String code) {
        for (ReferenceSystem channel : values()) {
            if (channel.getCode().equals(code)) {
                return channel;
            }
        }
        return null;
    }
}
