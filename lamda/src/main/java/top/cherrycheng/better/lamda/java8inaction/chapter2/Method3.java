package top.cherrycheng.better.lamda.java8inaction.chapter2;

import top.cherrycheng.better.lamda.java8inaction.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 值参数，利用增加参数或者将N个参数封装成对象实现聚合搜索
 * 对你能想到的每个属性做筛选...
 *
 * @author ChengRu
 * @date 2020/2/25 21:52
 * @Desc
 */
public class Method3 {
    public static List<Apple> filterApples(List<Apple> list, Color targetColor, int weight, boolean isCheckColor) {
        final ArrayList<Apple> apples = new ArrayList<>();
        for (Apple apple : list) {
            if ((isCheckColor && apple.getColor().equals(targetColor.name())) ||
                    (!isCheckColor && apple.getWeight() > weight)) {
                apples.add(apple);
            }
        }
        return apples;
    }

    public static void main(String[] args) {
        List<Apple> appleList = Arrays.asList(
                new Apple(80, Color.GREEN.name()),
                new Apple(120, Color.RED.name()),
                new Apple(180, Color.YELLOW.name()));
        System.out.println(filterApples(appleList, Color.GREEN, 0, true));
        System.out.println(filterApples(appleList, null, 0, false));
    }
}
