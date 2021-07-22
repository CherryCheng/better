package top.cherrycheng.study.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.cherrycheng.study.test.entity.Node;
import top.cherrycheng.study.test.repository.impl.NodeReferenceRepositoryImpl;
import top.cherrycheng.study.test.repository.impl.NodeRepositoryImpl;
import top.cherrycheng.study.test.service.ReferenceService;
import top.cherrycheng.study.test.service.impl.ReferenceServiceImpl;
import top.cherrycheng.study.test.type.ReferenceCode;
import top.cherrycheng.study.test.type.ReferenceSystem;
import top.cherrycheng.study.test.type.ReferenceType;
import top.cherrycheng.study.test.util.DBUtil;

import java.util.Random;

/**
 * @author ChengRu
 * @date 2021/7/22 10:31
 * @Desc
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:application-dev.properties")
@ComponentScan(basePackages = {"top.cherrycheng.study.test"})
@ContextConfiguration(classes =
        {ReferenceServiceImpl.class, NodeRepositoryImpl.class, NodeReferenceRepositoryImpl.class, DBUtil.class})
public class ReferenceServiceTest1 {

    @Autowired
    ReferenceService referenceService;

    private static Node node = new Node();

    @BeforeClass
    public static void beforeClass() throws Exception {
        node.setSystem(ReferenceSystem.PORTAL);
        node.setCode(new ReferenceCode(new Random().nextInt(1000000) + "T1"));
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