package top.cherrycheng.study.test.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cherrycheng.study.test.entity.Node;
import top.cherrycheng.study.test.entity.NodeReference;
import top.cherrycheng.study.test.repository.NodeReferenceRepository;
import top.cherrycheng.study.test.type.*;
import top.cherrycheng.study.test.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class NodeReferenceRepositoryImpl implements NodeReferenceRepository {

    @Autowired
    private DBUtil dbUtil;

    @Override
    public void addReference(List<NodeReference> nodeReferences) throws Exception {
        final String insertSql = "insert into node_reference(node_id,reference_node_id) values(?,?)";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dbUtil.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(insertSql);
            for (NodeReference nodeReference : nodeReferences) {
                statement.setLong(1, nodeReference.getNodeId().getValue());
                statement.setLong(2, nodeReference.getReferenceId().getValue());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
        } catch (Exception e) {
            log.error("插入引用数据异常", e);
            throw new Exception("添加引用关系异常");
        } finally {
            dbUtil.closeCollection(connection, statement);
        }
    }

    @Override
    public void removeReference(NodeId nodeId) throws Exception {
        String sql = "delete from node_reference where node_id = ?";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = dbUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, nodeId.getValue());
            ps.executeUpdate();
        } catch (Exception e) {
            log.error("删除引用异常", e);
            throw new Exception("删除引用关系异常");
        } finally {
            dbUtil.closeCollection(connection, ps);
        }
    }

    @Override
    public List<Node> listReference(NodeId nodeId, QuoteType quoteType) {
        //node被谁引用了
        String sql = "select id,system,code,type,business_name,version,business_org_code,create_by,create_at " +
                "from node where id in (" +
                "select node_id from node_reference where reference_node_id = " + nodeId.getValue() + ")";
        //node引用了谁
        if (quoteType.getCode() == QuoteType.QUOTE.getCode()) {
            sql = "select id,system,code,type,business_name,version,business_org_code,create_by,create_at " +
                    "from node where id in (" +
                    "select reference_node_id from node_reference where node_id = " + nodeId.getValue() + ")";
        }
        Connection connection = null;
        Statement statement = null;
        List<Node> references = new ArrayList<Node>();
        try {
            connection = dbUtil.getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Node reference = createNodeFromResultSet(resultSet);
                references.add(reference);
            }
            resultSet.close();
        } catch (Exception e) {
            log.error("查询节点被引用关系异常", e);
        } finally {
            dbUtil.closeCollection(connection, statement);
        }
        return references;
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
