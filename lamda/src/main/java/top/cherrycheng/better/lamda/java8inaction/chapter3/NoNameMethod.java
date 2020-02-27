package top.cherrycheng.better.lamda.java8inaction.chapter3;

/**
 * 函数式接口
 *
 * @author ChengRu
 * @date 2020/2/26 22:27
 * @Desc
 */
public class NoNameMethod {

    @FunctionalInterface
    public interface test {
        default void diff() {
            System.out.println("看到了哈，加无数默认方法都不算的！！");
        }

        void add();
    }
}
