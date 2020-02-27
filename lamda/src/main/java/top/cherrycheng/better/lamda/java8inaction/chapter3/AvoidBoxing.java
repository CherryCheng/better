package top.cherrycheng.better.lamda.java8inaction.chapter3;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

/**
 * 避免自动装箱
 *
 * @author ChengRu
 * @date 2020/2/26 23:11
 * @Desc
 */
public class AvoidBoxing {
    public static void main(String[] args) {
//        true（无装箱）
        IntPredicate evenNumbers = (int i) -> i % 2 == 0;
        System.out.println(evenNumbers.test(1000));
//         false（装箱）
        Predicate<Integer> oddNumbers = (Integer i) -> i % 2 == 1;
        System.out.println(oddNumbers.test(1000));
    }
}
