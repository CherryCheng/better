package top.cherrycheng.study.test;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.cherrycheng.study.test.entity.Node;
import top.cherrycheng.study.test.service.ReferenceService;
import top.cherrycheng.study.test.type.ReferenceCode;
import top.cherrycheng.study.test.type.ReferenceSystem;
import top.cherrycheng.study.test.type.ReferenceType;

import java.util.Random;

/**
 * 通过排除两个数据源实现效果
 *
 * @author ChengRu
 * @date 2021/7/22 10:31
 * @Desc
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:application-dev.properties")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
public class ReferenceServiceTest {

    @Autowired
    ReferenceService referenceService;

    private static Node node = new Node();

    @BeforeClass
    public static void beforeClass() throws Exception {
        node.setSystem(ReferenceSystem.PORTAL);
        node.setCode(new ReferenceCode(new Random().nextInt(1000000) + ""));
        node.setType(ReferenceType.FIELD);
        node.setVersion("1221");
        node.setBusinessName("1221");
        node.setBusinessOrgCode("1221");
        node.setCreateBy("1221");
    }

    @Test
    public void addNode() {
        try {
            referenceService.addNode(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}