package top.cherrycheng.study.test.repository;


import top.cherrycheng.study.test.entity.Node;
import top.cherrycheng.study.test.entity.NodeReference;
import top.cherrycheng.study.test.type.NodeId;
import top.cherrycheng.study.test.type.QuoteType;

import java.util.List;

public interface NodeReferenceRepository {

    /**
     * 新增引用关系
     *
     * @param nodeReferences
     * @return
     */
    void addReference(List<NodeReference> nodeReferences) throws Exception;

    /**
     * 删除nodeId的引用关系（被引用的不会删除）
     *
     * @param nodeId
     */
    void removeReference(NodeId nodeId) throws Exception;

    /**
     * 查询节点引用信息
     *
     * @param nodeId
     * @return
     */
    List<Node> listReference(NodeId nodeId, QuoteType quoteType);
}
