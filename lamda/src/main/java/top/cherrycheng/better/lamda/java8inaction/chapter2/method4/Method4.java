package top.cherrycheng.better.lamda.java8inaction.chapter2.method4;

import top.cherrycheng.better.lamda.java8inaction.Color;
import top.cherrycheng.better.lamda.java8inaction.chapter2.Apple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 行为参数化，不再传值参数。
 * 将行为抽像，除了入参list以外，其他参数都是一个行为，
 * 用于校验是否符合某个规则，符合就返回。
 *
 * @author ChengRu
 * @date 2020/2/25 22:03
 * @Desc
 */
public class Method4 {
    public static List<Apple> filterApples(List<Apple> list, ApplePredicate applePredicate) {
        final ArrayList<Apple> apples = new ArrayList<>();
        for (Apple apple : list) {
            if (applePredicate.test(apple)) {
                apples.add(apple);
            }
        }
        return apples;
    }

    public static void main(String[] args) {
        List<Apple> appleList = Arrays.asList(
                new Apple(80, Color.GREEN.name()),
                new Apple(155, Color.RED.name()),
                new Apple(180, Color.YELLOW.name()));
        System.out.println(filterApples(appleList, new AppleRedAndHeavyPredicate()));
        System.out.println(filterApples(appleList, new AppleSmallPredicate()));
    }
}
