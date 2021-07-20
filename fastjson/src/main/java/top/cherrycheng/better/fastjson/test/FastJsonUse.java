package top.cherrycheng.better.fastjson.test;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;

/**
 * @author ChengRu
 * @date 2020/3/3 14:26
 * @Desc
 */
public class FastJsonUse {
    public static void main(String[] args) {
        String a = "";
        HashMap<String, String> map = JSON.parseObject(jsonStr, new TypeReference<HashMap<String, String>>() {
        });
//        把字符串反序列化成对象，怎么把对象有的字段不序列化进去？
    }
}
