package top.cherrycheng.better.lamda.java8inaction.chapter1;

import lombok.Data;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.cglib.core.Predicate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChengRu
 * @date 2020/2/19 8:16
 * @Desc
 */
public class FilterApple1 {
    /**
     * 需求：按重量或颜色过滤出苹果
     */
    public static void main(String[] args) {
        List<Apple> appleList = new ArrayList<>();
        appleList.add(new Apple(80, Color.GREEN.name()));
        appleList.add(new Apple(120, Color.RED.name()));
        appleList.add(new Apple(180, Color.YELLOW.name()));

//        TODO 用这种写法就不行，不知原因
//        List<Apple> appleList = Arrays.asList(
//                new Apple(80, Color.GREEN.name()),
//                new Apple(120, Color.RED.name()),
//                new Apple(180, Color.YELLOW.name()));

        CollectionUtils.filter(appleList, new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                Apple apple = (Apple) o;
                return Color.GREEN.name().equals(apple.color);
            }
        });
        System.out.println(appleList);

    }

    @Data
    public static class Apple {
        private int weight = 0;
        private String color = "";

        public Apple(int weight, String color) {
            this.weight = weight;
            this.color = color;
        }

    }

    private enum Color {
        GREEN, RED, YELLOW;
    }
}
