package top.cherrycheng.better.lamda.java8inaction.chapter2;

import lombok.Data;
import top.cherrycheng.better.lamda.java8inaction.Color;

/**
 * @author ChengRu
 * @date 2020/2/25 21:41
 * @Desc
 */
@Data
public class Apple {
    /**
     * 重量
     */
    private Integer weight;
    /**
     * 尺寸
     */
    private Integer size;
    /**
     * 颜色
     *
     * @see Color
     */
    private String color;

    public Apple() {
    }

    public Apple(int weight, String color) {
        this.weight = weight;
        this.color = color;
    }
}
