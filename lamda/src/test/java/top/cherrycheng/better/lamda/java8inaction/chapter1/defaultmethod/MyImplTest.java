package top.cherrycheng.better.lamda.java8inaction.chapter1.defaultmethod;

import org.junit.jupiter.api.Test;

/**
 * @author ChengRu
 * @date 2020/2/19 9:54
 * @Desc
 */
class MyImplTest {

    @Test
    void sayHi() {
//        如果在好几个接口里有多个默认实现，意味着Java中有了某种形式的多重继承
        MyImpl my = new MyImpl();
        my.sayHi("cherrycheng");
        my.showMe("也可以用MyImpl对象直接在外面调~");
    }
}