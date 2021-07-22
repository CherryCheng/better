package top.cherrycheng.study.test.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author ：libo
 * @date ：Created in 2021/6/15 20:19
 * @description ：
 */
@Slf4j
@Component
public class DBUtil {

    @Value("${reference.url:111}")
    private String url;
    @Value("${reference.username:111}")
    private String userName;
    @Value("${reference.password:111}")
    private String password;
    @Value("${reference.initialSize:3}")
    private int initialSize;
    @Value("${reference.maxActive:10}")
    private int maxActive;
    @Value("${reference.maxWait:5000}")
    private int maxWait;

    private static DruidDataSource druidDataSource = null;

    @PostConstruct
    public void init() {
        try {
            Properties props = new Properties();
            props.setProperty("driverClassName", "com.mysql.jdbc.Driver");
            props.setProperty("url", url);
            props.setProperty("username", userName);
            props.setProperty("password", password);
            props.setProperty("initialSize", String.valueOf(initialSize));
            props.setProperty("maxActive", String.valueOf(maxActive));
            props.setProperty("maxWait", String.valueOf(maxWait));
            props.setProperty("validationQuery", "SELECT 'x'");
            props.setProperty("testWhileIdle", String.valueOf(true));
            druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(props);
            initCollection();
        } catch (Exception e) {
            log.error("获取数据源异常", e);
        }
    }

    /**
     * 先去拿一次连接，暂时解决第一连接慢的问题
     */
    private void initCollection() {
        Connection connection = getConnection();
        try {
            connection.close();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 获取连接
     *
     * @return
     */
    public DruidPooledConnection getConnection() {
        DruidPooledConnection connection = null;
        try {
            log.info("------------->当前活跃连接数：{}", druidDataSource.getActiveCount());
            connection = druidDataSource.getConnection();
            log.info("------------->当前活跃连接数：{}", druidDataSource.getActiveCount());
        } catch (SQLException e) {
            log.error("获取连接失败", e);
        }
        return connection;

    }

    /**
     * 关闭连接
     *
     * @param connection
     * @param ps
     */
    public void closeCollection(Connection connection, PreparedStatement ps) {
        try {
            ps.close();
        } catch (Exception ee) {
        }
        try {
            connection.close();
        } catch (Exception e) {
        }
    }

    /**
     * 关闭连接
     *
     * @param connection
     * @param statement
     */
    public void closeCollection(Connection connection, Statement statement) {
        try {
            statement.close();
        } catch (Exception ee) {
        }
        try {
            connection.close();
        } catch (Exception e) {
        }
    }
}
