package top.cherrycheng.better.lamda.java8inaction.chapter2.method4;

import top.cherrycheng.better.lamda.java8inaction.Color;
import top.cherrycheng.better.lamda.java8inaction.chapter2.Apple;

/**
 * 大红苹果
 *
 * @author ChengRu
 * @date 2020/2/25 22:06
 * @Desc
 */
public class AppleRedAndHeavyPredicate implements ApplePredicate {


    @Override
    public boolean test(Apple apple) {
        return apple.getColor().equals(Color.RED.name()) && apple.getWeight() > 150;
    }
}
