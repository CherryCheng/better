package top.cherrycheng;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import top.cherrycheng.study.mybatis.mapper.EmployeeMapper;
import top.cherrycheng.study.mybatis.model.Employee;

import java.io.IOException;
import java.io.InputStream;

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

}
