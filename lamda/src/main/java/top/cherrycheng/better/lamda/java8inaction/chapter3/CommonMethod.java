package top.cherrycheng.better.lamda.java8inaction.chapter3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author ChengRu
 * @date 2020/2/26 23:01
 * @Desc
 */
public class CommonMethod {
    public static void main(String[] args) {
//       【Predicate】 过滤非空集合
        Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
        List<String> nonEmpty = filter(Arrays.asList("", "1", "3", ""), nonEmptyStringPredicate);
        System.out.println(nonEmpty.size());

//        【Consumer】遍历所有集合
        forEach(Arrays.asList(1, 2, 3, 4, 5), (Integer i) -> System.out.println(i));

//        【Function】 取列表中每个项的长度
        List<Integer> l = map(Arrays.asList("lambdas", "in", "action"), (String s) -> s.length());
        System.out.println(l);
    }

    public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>();
        for (T s : list) {
            result.add(f.apply(s));
        }
        return result;
    }

    public static <T> void forEach(List<T> list, Consumer<T> c) {
        for (T i : list) {
            c.accept(i);
        }
    }

    /**
     * 根据规则p，过滤出符合p规则的新list
     */
    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> results = new ArrayList<>();
        for (T s : list) {
            if (p.test(s)) {
                results.add(s);
            }
        }
        return results;
    }
}
