package top.cherrycheng.study.test.repository;


import top.cherrycheng.study.test.entity.Node;
import top.cherrycheng.study.test.type.*;

import java.util.List;

/**
 * @author ：libo
 * @date ：Created in 2021/6/15 19:15
 * @description ：
 */
public interface NodeRepository {

    /**
     * 新增节点
     *
     * @param node
     * @return
     */
    Long addNode(Node node);

    /**
     * 删除引用主节点，比如字段
     *
     * @param nodeId
     * @return
     */
    int removeNode(NodeId nodeId);


    /**
     * 查询节点信息
     *
     * @param system
     * @param code
     * @param type
     * @return
     */
    Node find(ReferenceSystem system, ReferenceCode code, ReferenceType type, NodeVersion version);

    /**
     * 查询所有节点信息
     *
     * @return
     */
    List<Node> findBySystem(ReferenceSystem system);
}
