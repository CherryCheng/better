package top.cherrycheng.better.fileoperation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"top.*"})
public class FileOperationApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileOperationApplication.class, args);
    }

}
