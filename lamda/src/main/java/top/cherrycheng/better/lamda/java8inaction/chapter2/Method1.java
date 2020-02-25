package top.cherrycheng.better.lamda.java8inaction.chapter2;

import top.cherrycheng.better.lamda.java8inaction.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 值参数，将查询条件写死，完全没有灵活性
 *
 * @author ChengRu
 * @date 2020/2/25 21:40
 * @Desc
 */
public class Method1 {

    public static List<Apple> filterGreenApples(List<Apple> list) {
        final ArrayList<Apple> apples = new ArrayList<>();
        for (Apple apple : list) {
            if (apple.getColor().equals(Color.GREEN.name())) {
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
        System.out.println(filterGreenApples(appleList));
    }
}
