package top.cherrycheng.better.lamda.java8inaction.chapter1;

import lombok.Data;
import top.cherrycheng.better.lamda.java8inaction.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ChengRu
 * @date 2020/2/19 8:16
 * @Desc
 */
public class FilterApple {
    /**
     * 需求：按重量或颜色过滤出苹果
     */
    public static void main(String[] args) {
        List<Apple> appleList = Arrays.asList(
                new Apple(80, Color.GREEN.name()),
                new Apple(120, Color.RED.name()),
                new Apple(180, Color.YELLOW.name()));

//        方法一：JDK8之前，整两个函数,代码只有一行不一样
        System.out.println((filterGreenApples(appleList)));
        System.out.println((filterHeavyApples(appleList)));
        System.out.println("方法一结束-----------------------------");
//        方法二：JDK8 方法引用
        System.out.println(filterApples(appleList, Apple::isGreenApple));
        System.out.println(filterApples(appleList, Apple::isHeavyApple));
        System.out.println("方法二结束-----------------------------");
//        方法三：JDK8 方法引用，且不用写isGreenApple，isHeavyApple这种只用一次的代码
        System.out.println(filterApples(appleList, (Apple a) -> Color.GREEN.name().equals(a.color)));
        System.out.println(filterApples(appleList, (Apple a) -> a.getWeight() > 150));
        System.out.println("方法三结束-----------------------------");
//        方法四：连filterApples和Predicate都不用写，直接用流式表达式
//        顺序处理：
        System.out.println(appleList.stream()
                .filter((Apple a) -> a.getWeight() > 150).collect(Collectors.toList()));
//        并行处理：
        System.out.println(appleList.parallelStream()
                .filter((Apple a) -> Color.GREEN.name().equals(a.color)).collect(Collectors.toList()));
        System.out.println("方法四结束-----------------------------");
        System.out.println("方法五参见FilterApple1-----------------------------");

    }

    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (Color.GREEN.name().equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterHeavyApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > 150) {
                result.add(apple);
            }
        }
        return result;
    }

    public interface Predicate<T> {
        boolean test(T t);
    }

    static List<Apple> filterApples(List<Apple> inventory,
                                    Predicate<Apple> p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    @Data
    public static class Apple {
        private int weight = 0;
        private String color = "";

        public Apple(int weight, String color) {
            this.weight = weight;
            this.color = color;
        }

        public static boolean isGreenApple(Apple apple) {
            return Color.GREEN.name().equals(apple.getColor());
        }

        public static boolean isHeavyApple(Apple apple) {
            return apple.getWeight() > 150;
        }
    }
}
