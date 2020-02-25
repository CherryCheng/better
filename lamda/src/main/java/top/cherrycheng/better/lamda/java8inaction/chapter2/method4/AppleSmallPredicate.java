package top.cherrycheng.better.lamda.java8inaction.chapter2.method4;

import top.cherrycheng.better.lamda.java8inaction.chapter2.Apple;

/**
 * 小苹果
 *
 * @author ChengRu
 * @date 2020/2/25 22:09
 * @Desc
 */
public class AppleSmallPredicate implements ApplePredicate {
    @Override
    public boolean test(Apple apple) {
        return apple.getWeight() < 100;
    }
}
