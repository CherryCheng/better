package top.cherrycheng.study.test.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.cherrycheng.study.test.entity.Node;
import top.cherrycheng.study.test.repository.NodeRepository;
import top.cherrycheng.study.test.type.*;
import top.cherrycheng.study.test.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：libo
 * @date ：Created in 2021/6/15 19:18
 * @description ：
 */
@Slf4j
@Service
public class NodeRepositoryImpl implements NodeRepository {

    @Autowired
    private DBUtil dbUtil;

    @Override
    public Long addNode(Node node) {
        final String insertSql = "insert into node(system,code,type,business_name,version,business_org_code,create_by) values(?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dbUtil.getConnection();
            statement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, node.getSystem().getCode());
            statement.setString(2, node.getCode().getValue());
            statement.setInt(3, node.getType().getCode());
            statement.setString(4, node.getBusinessName());
            statement.setString(5, node.getVersion());
            statement.setString(6, node.getBusinessOrgCode());
            statement.setString(7, node.getCreateBy());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            while (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            }
        } catch (Exception e) {
            log.error("插入节点数据异常", e);
        } finally {
            dbUtil.closeCollection(connection, statement);
        }
        return null;
    }

    @Override
    public int removeNode(NodeId nodeId) {
        String sql = "delete from node where id = ?";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = dbUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, nodeId.getValue());
            return ps.executeUpdate();
        } catch (Exception e) {
            log.error("删除节点异常", e);
        } finally {
            dbUtil.closeCollection(connection, ps);
        }
        return 0;
    }

    @Override
    public Node find(ReferenceSystem system, ReferenceCode code, ReferenceType type, NodeVersion version) {
        String sql = "select id,system,code,type,business_name,version,business_org_code,create_by,create_at from node " +
                "where system = ? and code = ? and type = ?";
        if (version != null && !StringUtils.isEmpty(version.getValue())) {
            sql += " and version = ?";
        }
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = dbUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, system.getCode());
            ps.setString(2, code.getValue());
            ps.setLong(3, type.getCode());
            if (version != null && !StringUtils.isEmpty(version.getValue())) {
                ps.setString(4, version.getValue());
            }
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return createNodeFromResultSet(resultSet);
            }
            resultSet.close();
        } catch (Exception e) {
            log.error("删除节点异常", e);
        } finally {
            dbUtil.closeCollection(connection, ps);
        }
        return null;
    }

    @Override
    public List<Node> findBySystem(ReferenceSystem system) {
        String sql = "select id,system,code,type,business_name,version,business_org_code,create_by,create_at from node " +
                "where system = ?";
        Connection connection = null;
        PreparedStatement ps = null;
        List<Node> nodes = new ArrayList<Node>();
        try {
            connection = dbUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, system.getCode());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Node node = createNodeFromResultSet(resultSet);
                nodes.add(node);
            }
            resultSet.close();
        } catch (Exception e) {
            log.error("删除节点异常", e);
        } finally {
            dbUtil.closeCollection(connection, ps);
        }
        return nodes;
    }

    private Node createNodeFromResultSet(ResultSet resultSet) throws Exception {
        Node node = new Node();
        node.setId(new NodeId(resultSet.getLong("id")));
        node.setSystem(ReferenceSystem.findByValue(resultSet.getString("system")));
        node.setCode(new ReferenceCode(resultSet.getString("code")));
        node.setType(ReferenceType.findByValue(resultSet.getInt("type")));
        node.setBusinessName(resultSet.getString("business_name"));
        node.setBusinessOrgCode(resultSet.getString("business_org_code"));
        node.setCreateBy(resultSet.getString("create_by"));
        node.setCreateAt(resultSet.getDate("create_at"));
        node.setVersion(resultSet.getString("version"));
        return node;
    }
}
