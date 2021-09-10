package top.cherrycheng.better.lamda.myTest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author ChengRu
 * @date 2021/9/6 7:12
 * @Desc
 */
public class TestBase {
    public static void main(String[] args) {
        //        Person person = new Person().setName("jack").setRace("白种人");// 链式调用
        //        System.out.println(person);
        //        Person person = new Person();
        //        Person build = person.builder().name("tom").race("白种人").build();
        //        System.out.println(build);

        //        String s = null;
        Person s = new Person();
        if (s.getName() == null) {

            //        if (null == s.getName()) {
            System.out.println("null");
        }

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)//开启链式调用，不用构建对象，直接刷刷的整起
    //    @Builder //建造者模式，要对象，要builder(),build()同用
    public static class Person {
        /**
         * 姓名
         */
        private String name;
        /**
         * 人种
         */
        private String race;
    }
}
