package top.cherrycheng.better.lamda.java8inaction.chapter1.defaultmethod;

/**
 * JDK8默认方法
 *
 * @author ChengRu
 * @date 2020/2/19 9:50
 * @Desc
 */
public interface MyInterface {
    void sayHi(String name);

    default void showMe(String words) {
        System.out.println("showMe方法：words=" + words);
    }
}
