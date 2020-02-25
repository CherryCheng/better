package top.cherrycheng.better.lamda.java8inaction.chapter2;

import top.cherrycheng.better.lamda.java8inaction.Color;
import top.cherrycheng.better.lamda.java8inaction.chapter2.method4.ApplePredicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 方法五虽然比方法四更进一步，但模板代码太多，影响阅读体验，且this指向不明，容易误导开发人员。
 * 用lambda来提炼核心内容。
 *
 * @author ChengRu
 * @date 2020/2/25 22:17
 * @Desc
 */
public class Method6 {
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
        System.out.println(filterApples(appleList
                , (Apple apple) -> apple.getColor().equals(Color.GREEN.name())));
        System.out.println(filterApples(appleList
                , (Apple apple) -> apple.getWeight() < 100));
    }
}
