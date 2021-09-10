package top.cherrycheng.better.lamda.myTest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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

        List<Person> list = new ArrayList<Person>() {{
            add(new Person().setName("niki").setRace(""));
            add(new Person().setName("bili").setRace(""));
            add(new Person().setName("mary").setRace(""));
            add(new Person().setName("yahoo").setRace(""));
        }};

        System.out.println(list2String(list, Person::getName));
        //        拆开就是如下匿名内部类，以上是lambda的简写
        //        System.out.println(list2String(list,new Function<Person, String>(){
        //            @Override
        //            public String apply(Person person) {
        //                return person.getName();
        //            }
        //        }));

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

    public static String list2String(List<Person> list, Function<Person, String> function) {
        return list.parallelStream().map(function).collect(Collectors.joining(","));
    }
}
