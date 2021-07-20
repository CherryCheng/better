package top.cherrycheng.better.encryptdecrypt.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringUtil extends StringUtils {

    public static String toStr(Object obj) {
        if (obj == null) {
            return EMPTY;
        } else {
            return obj.toString();
        }
    }

    public static String getMaxStr(String str, int maxLength) {
        if (StringUtils.isNotBlank(str) && str.length() > maxLength) {
            return str.substring(0, maxLength);
        } else {
            return str;
        }
    }

    public static String concat(String... params) {
        if (params == null) {
            return null;
        }

        return String.join(StringUtil.EMPTY,
                Arrays.stream(params).filter(x -> x != null).collect(Collectors.toList()));
    }
}
