package top.cherrycheng.better.lamda.java8inaction.chapter2.method5;

import top.cherrycheng.better.lamda.java8inaction.Color;
import top.cherrycheng.better.lamda.java8inaction.chapter2.Apple;
import top.cherrycheng.better.lamda.java8inaction.chapter2.method4.ApplePredicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 同方法四，但此处应用了匿名类，
 * 不用再写AppleRedAndHeavyPredicate、AppleSmallPredicate了
 * 代码更少,但是模板代码太多，有时候分不清this指的谁。
 *
 * @author ChengRu
 * @date 2020/2/25 22:11
 * @Desc
 * @see MeaningOfThis
 */
public class Method5 {
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
        System.out.println(filterApples(appleList, new ApplePredicate() {

            @Override
            public boolean test(Apple apple) {
                return apple.getWeight() > 150;
            }
        }));
        System.out.println(filterApples(appleList, new ApplePredicate() {

            @Override
            public boolean test(Apple apple) {
                return apple.getColor().equals(Color.YELLOW.name());
            }
        }));
    }
}
