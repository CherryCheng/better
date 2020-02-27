package top.cherrycheng.better.lamda.java8inaction.chapter3;


import top.cherrycheng.better.lamda.java8inaction.Color;
import top.cherrycheng.better.lamda.java8inaction.chapter2.Apple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author ChengRu
 * @date 2020/2/26 22:00
 * @Desc
 */
public class Page37_lamdas {
    public static void main(String[] args) throws Exception {
        Runnable r = () -> System.out.println("Hello!");
        r.run();

// 布尔表达式  (List<String> list) -> list.isEmpty()
        Predicate<List<String>> p = (List<String> list) -> list.isEmpty();
        System.out.println(p.test(new ArrayList<>()));
        System.out.println(p.test(Arrays.asList("1", "2", "3")));

//        创建对象  () -> new Apple(10)
        Supplier<Apple> supplier = () -> new Apple(111, Color.RED.name());
        System.out.println(supplier.get());

//消费一个对象 (Apple a) -> { System.out.println(a.getWeight()); }
        Consumer<Apple> consumer = (Apple a) -> System.out.println(a.getColor());
        consumer.accept(new Apple(222, Color.GREEN.name()));

//        从一个对象中选择/抽取 (String s) -> s.length()
        Function<Apple, String> function = (Apple a) -> "就要随便写，怎地！";
        System.out.println(function.apply(new Apple()));

//        组合两个值  (int a, int b) -> a * b

//        比较两个对象  (Apple a1, Apple a2) ->a1.getWeight().compareTo(a2.getWeight())

//        为了这两种方式一样
        Callable<String> callable = () -> "Tricky example ;-)";
        System.out.println(callable.call());
        System.out.println(fetch().call());

//        () -> void 无参无返回值
        Runnable rr = () -> {
            System.out.println("Tricky example");
        };
        rr.run();


    }

    /**
     * 这个写法也是对的，同：
     * Callable<String> callable = () -> "Tricky example ;-)";
     */
    public static Callable<String> fetch() {
        return () -> "Tricky example ;-)";
    }
}
