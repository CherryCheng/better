package top.cherrycheng.study.test.service;


import top.cherrycheng.study.test.entity.Node;
import top.cherrycheng.study.test.type.*;

import java.util.List;

/**
 * @author ：libo
 * @date ：Created in 2021/6/11 14:06
 * @description ：
 */
public interface ReferenceService {

    /**
     * 新增节点
     *
     * @param node
     * @return
     */
    Long addNode(Node node) throws Exception;

    /**
     * 删除当前节点引用的所有关联关系，当前节点不删，引用当前节点的关联关系不删
     *
     * @param nodeId
     * @throws Exception
     */
    void removeAllSubReference(NodeId nodeId) throws Exception;

    /**
     * 删除引用主节点，如果该节点有被其他节点引用，不让删除
     * 删除该节点的主动引用关系
     *
     * @param channel
     * @param code
     * @param type
     * @return
     */
    int removeNode(ReferenceSystem channel, ReferenceCode code, ReferenceType type) throws Exception;

    /**
     * 删除引用主节点，如果该节点有被其他节点引用，不让删除
     * 删除该节点的主动引用关系
     *
     * @param channel
     * @param code
     * @param type
     * @param version
     * @return
     */
    int removeNode(ReferenceSystem channel, ReferenceCode code, ReferenceType type, NodeVersion version) throws Exception;

    /**
     * 新增引用关系,逻辑是先删除再新增
     *
     * @param references 都是全量关系,如果为空，则删除所有的主动引用关系
     */
    void addReference(Node node, List<Node> references) throws Exception;

    /**
     * 查询节点被引用关系(非递归)
     *
     * @param channel
     * @param code
     * @param type
     * @param quoteType 引用方向 QUOTE:节点引用了谁  QUOTED：节点被谁引用了
     * @return
     */
    List<Node> listReference(ReferenceSystem channel, ReferenceCode code, ReferenceType type, QuoteType quoteType);


    /**
     * 查询节点被引用关系(非递归)
     *
     * @param channel
     * @param code
     * @param type
     * @param quoteType 引用方向 QUOTE:节点引用了谁  QUOTED：节点被谁引用了
     * @return
     */
    List<Node> listReference(ReferenceSystem channel, ReferenceCode code, ReferenceType type, QuoteType quoteType,
                             NodeVersion version);
}
