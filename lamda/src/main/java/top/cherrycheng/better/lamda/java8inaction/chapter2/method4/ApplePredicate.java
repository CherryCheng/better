package top.cherrycheng.better.lamda.java8inaction.chapter2.method4;

import top.cherrycheng.better.lamda.java8inaction.chapter2.Apple;

/**
 * 断言/谓语
 *
 * @author ChengRu
 * @date 2020/2/25 22:04
 * @Desc
 */
public interface ApplePredicate {
    boolean test(Apple apple);
}
