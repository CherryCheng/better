package top.cherrycheng.better.lamda.functionalthinking;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ChengRu
 * @date 2021/8/29 17:28
 * @Desc
 */
public class Test {
    public static void main(String[] args) {
        final List<List<Integer>> list = Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(3, 4, 5));
        //        需要引入这个库：functionaljava
        //        System.out.println(list.foldLeft(fj.function.Integers.add, 0) - list.last());
        //        System.out.println(list.stream().);
        Set<Integer> primeSet = new HashSet<Integer>() {{
            add(1);
            add(2);
        }};
        //        初始化List的快速写法，lambda
        //        System.out.println(new ArrayList<Integer>() {{add(1); add(2);}});
        //        多个列表合成一个不会去重：[1, 2, 3, 3, 4, 5]
        System.out.println(list.stream().flatMap(a -> a.stream()).collect(Collectors.toList()));
    }

}
