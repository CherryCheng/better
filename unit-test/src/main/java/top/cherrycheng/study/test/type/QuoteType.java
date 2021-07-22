package top.cherrycheng.study.test.type;

import lombok.Getter;

@Getter
public enum QuoteType {

    QUOTE(1, "引用"),
    QUOTED(2, "被引用");

    private int code;

    private String message;

    QuoteType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static QuoteType findByValue(int code) {
        for (QuoteType channel : values()) {
            if (channel.getCode() == code) {
                return channel;
            }
        }
        return null;
    }
}
