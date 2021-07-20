package top.cherrycheng;

import cn.binarywang.tools.generator.ChineseIDCardNumberGenerator;
import cn.binarywang.tools.generator.ChineseNameGenerator;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import top.cherrycheng.study.mybatis.mapper.EmployeeMapper;
import top.cherrycheng.study.mybatis.mapper.FeatureResultMapper;
import top.cherrycheng.study.mybatis.model.Employee;
import top.cherrycheng.study.mybatis.model.FeatureResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * 指定命名空间+语句id，手动传入object类型的参数
     */
    @Test
    public void Demo1() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Employee employee = (Employee) session.selectOne("org.mybatis.example.BlogMapper.selectById", 1);
            System.out.println(employee);
        }
    }

    /**
     * 接口式编程
     * 1、把命名空间定义为*Mapper.java类的全类名
     */
    @Test
    public void Demo2() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            final EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            final Employee employee = mapper.selectById(1L);
            System.out.println(employee);
        }
    }

    /**
     * 改姓名和身份证号
     */
    @Test
    public void updateInfo() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            final FeatureResultMapper mapper = session.getMapper(FeatureResultMapper.class);
            final List<Long> allIds = mapper.getAllIds();
            FeatureResult result = new FeatureResult();
            for (Long id : allIds) {
                result.setId(id);
                result.setName(ChineseNameGenerator.getInstance().generate());
                result.setCertNo(ChineseIDCardNumberGenerator.getInstance().generate());
                mapper.update(result);
            }
        }
    }

    /**
     * 生成数据:用DB默认值
     */
    @Test
    public void insertDefaultInfo() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            final FeatureResultMapper mapper = session.getMapper(FeatureResultMapper.class);
            for (int i = 0; i < 10; i++) {
                final FeatureResult result = new FeatureResult();
                result.setName(ChineseNameGenerator.getInstance().generate());
                result.setCertNo(ChineseIDCardNumberGenerator.getInstance().generate());
                mapper.insertByDefault(result);
            }
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成数据：用SQL里定义的值
     */
    @Test
    public void insertInfo() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            final FeatureResultMapper mapper = session.getMapper(FeatureResultMapper.class);
            for (int i = 0; i < 10; i++) {
                final FeatureResult result = new FeatureResult();
                result.setName(ChineseNameGenerator.getInstance().generate());
                result.setCertNo(ChineseIDCardNumberGenerator.getInstance().generate());
                mapper.insert(result);
            }
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
