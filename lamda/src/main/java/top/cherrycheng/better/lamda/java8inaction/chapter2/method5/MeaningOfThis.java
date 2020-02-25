package top.cherrycheng.better.lamda.java8inaction.chapter2.method5;

/**
 * @author ChengRu
 * @date 2020/2/25 8:01
 * @Desc
 */
public class MeaningOfThis {
    public final int value = 4;

    public void doIt() {
        int value = 6;
        Runnable r = new Runnable() {
            public final int value = 5;

            @Override
            public void run() {
                int value = 10;
                System.out.println(this.value);
            }
        };
        r.run();
    }

    public static void main(String... args) {
        MeaningOfThis m = new MeaningOfThis();
//        因为 this 指的是包含它的 Runnable ，而不是外面的类 MeaningOfThis 。
        m.doIt();
    }
}
