package top.cherrycheng.better.lamda.java8inaction.chapter2.method7;

import top.cherrycheng.better.lamda.java8inaction.Color;
import top.cherrycheng.better.lamda.java8inaction.chapter2.Apple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 基于方法六，再深度抽像，不仅能过滤苹果，所有的集合内容都能过滤。
 * <p>
 * 再抽一层，结合泛型，达到传什么过滤什么的效果！
 *
 * @author ChengRu
 * @date 2020/2/25 22:23
 * @Desc
 */
public class Method7 {
    public static <T> List<T> filter(List<T> data, Predicate<T> p) {
        final ArrayList<T> filterdList = new ArrayList<>();
        for (T obj : data) {
            if (p.test(obj)) {
                filterdList.add(obj);
            }
        }
        return filterdList;
    }

    public static void main(String[] args) {
        List<Apple> appleList = Arrays.asList(
                new Apple(80, Color.GREEN.name()),
                new Apple(155, Color.RED.name()),
                new Apple(180, Color.YELLOW.name()));
        System.out.println(filter(appleList
                , (Apple apple) -> apple.getColor().equals(Color.GREEN.name())));

        List<String> stringList = Arrays.asList("1", "2", "3");
        System.out.println(filter(stringList, (String s) -> s.equals("2")));
    }
}
