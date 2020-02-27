package top.cherrycheng.better.lamda.java8inaction.chapter3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 环绕执行模式
 *
 * @author ChengRu
 * @date 2020/2/26 22:43
 * @Desc
 */
public class ExecuteAround {
    /**
     * 开始这么写，只能读一行
     */
    public static String processFile() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            return br.readLine();
        }
    }

    /**
     * 将读的行为抽像成一个处理方法，每次调的时候传进来【行为参数化】
     * 从而每次调的时候决定读一行？两行？一个字符？
     */
    public static String processFile(BufferedReaderProcessor processor) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            return processor.process(br);
        }
    }

    @FunctionalInterface
    public interface BufferedReaderProcessor {
        String process(BufferedReader br) throws IOException;
    }

    public static void main(String[] args) throws IOException {
        /**
         * 方法一：死板
         */
//        processFile();

        /**
         * 行为参数化后：读一行
         */
//        processFile((BufferedReader br) -> br.readLine());
        /**
         * 行为参数化后：读二行，看心情呀
         */
//        processFile((BufferedReader br) -> br.readLine()+br.readLine());


    }
}
