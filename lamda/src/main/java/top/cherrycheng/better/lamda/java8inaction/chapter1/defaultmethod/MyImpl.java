package top.cherrycheng.better.lamda.java8inaction.chapter1.defaultmethod;

/**
 * @author ChengRu
 * @date 2020/2/19 9:52
 * @Desc
 */
public class MyImpl implements MyInterface {
    @Override
    public void sayHi(String name) {
        System.out.println("hi，" + name);
        showMe("MyImpl没有重写MyInterface就能直接用其default方法");
    }
}
