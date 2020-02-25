package top.cherrycheng.better.lamda.java8inaction.chapter2;

import top.cherrycheng.better.lamda.java8inaction.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 值参数，利用方法重载，将参数类型固定，需求变更时有大量重复代码
 * 比如，要再按产地，尺寸等又是一顿复制
 *
 * @author ChengRu
 * @date 2020/2/25 21:40
 * @Desc
 */
public class Method2 {

    public static List<Apple> filterByColor(List<Apple> list, Color targetColor) {
        final ArrayList<Apple> apples = new ArrayList<>();
        for (Apple apple : list) {
            if (apple.getColor().equals(targetColor.name())) {
                apples.add(apple);
            }
        }
        return apples;
    }

    public static List<Apple> filterByWeight(List<Apple> list, int weight) {
        final ArrayList<Apple> apples = new ArrayList<>();
        for (Apple apple : list) {
            if (apple.getWeight() > weight) {
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
        System.out.println(filterByColor(appleList, Color.RED));
        System.out.println(filterByWeight(appleList, 100));
    }
}
