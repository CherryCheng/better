package top.cherrycheng.study.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.cherrycheng.study.test.entity.Node;
import top.cherrycheng.study.test.entity.NodeReference;
import top.cherrycheng.study.test.repository.NodeReferenceRepository;
import top.cherrycheng.study.test.repository.NodeRepository;
import top.cherrycheng.study.test.service.ReferenceService;
import top.cherrycheng.study.test.type.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ：libo
 * @date ：Created in 2021/6/15 10:41
 * @description ：
 */
@Service
public class ReferenceServiceImpl implements ReferenceService {

    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private NodeReferenceRepository referenceRepository;

    @Override
    public Long addNode(Node node) throws Exception {
        try {
            return nodeRepository.addNode(node);
        } catch (DuplicateKeyException e) {
            throw new Exception("节点已存在");
        }
    }

    @Override
    public void removeAllSubReference(NodeId nodeId) throws Exception {
        referenceRepository.removeReference(nodeId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeNode(ReferenceSystem system, ReferenceCode code, ReferenceType type) throws Exception {
        //校验该节点是否被其他节点引用
        List<Node> quoted = listReference(system, code, type, QuoteType.QUOTED);
        if (!CollectionUtils.isEmpty(quoted)) {
            throw new Exception("该节点被其他节点引用，不能删除");
        }
        //删除节点引用其他节点的关系
        Node node = nodeRepository.find(system, code, type, null);
        if (node == null) {
            return 0;
        }
        referenceRepository.removeReference(node.getId());
        return nodeRepository.removeNode(node.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeNode(ReferenceSystem system, ReferenceCode code, ReferenceType type, NodeVersion version)
            throws Exception {
        //校验该节点是否被其他节点引用
        List<Node> quoted = listReference(system, code, type, QuoteType.QUOTED, version);
        if (!CollectionUtils.isEmpty(quoted)) {
            throw new Exception("该节点被其他节点引用，不能删除");
        }
        //删除节点引用其他节点的关系
        Node node = nodeRepository.find(system, code, type, version);
        if (node == null) {
            return 0;
        }
        referenceRepository.removeReference(node.getId());
        return nodeRepository.removeNode(node.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addReference(Node node, List<Node> references) throws Exception {
        Node existNode = nodeRepository.find(node.getSystem(), node.getCode(), node.getType(),
                new NodeVersion(node.getVersion()));
        if (existNode == null) {
            Long id = addNode(node);
            existNode = node;
            existNode.setId(new NodeId(id));
        }
        //先删除
        referenceRepository.removeReference(existNode.getId());
        List<NodeReference> nodeReferences = createNodeReference(existNode, references);
        if (CollectionUtils.isEmpty(nodeReferences)) {
            return;
        }
        referenceRepository.addReference(nodeReferences);
    }

    private List<NodeReference> createNodeReference(Node node, List<Node> references) throws Exception {
        List<NodeReference> nodeReferences = new ArrayList<NodeReference>();
        //按系统批量查询
        List<Node> systemNodes = new ArrayList<>();
        Set<ReferenceSystem> systems = references.stream().map(Node::getSystem).collect(Collectors.toSet());
        for (ReferenceSystem system : systems) {
            systemNodes.addAll(nodeRepository.findBySystem(system));
        }
        for (Node reference : references) {
            Node referNode = systemNodes.stream().filter(n ->
                    n.getSystem().equals(reference.getSystem()) && n.getCode().equals(reference.getCode())
                            && n.getType().equals(reference.getType())
            ).findFirst().orElse(null);
            Long referNodeId = null;
            if (referNode == null) {
                referNodeId = addNode(reference);
//                throw new Exception("没找到节点:" + reference.getCode() + ",请联系节点提供方重新构造");
            }
            NodeReference nodeReference = new NodeReference();
            nodeReference.setNodeId(node.getId());
            nodeReference.setReferenceId(referNode == null ? new NodeId(referNodeId) : referNode.getId());
            nodeReferences.add(nodeReference);
        }
        return nodeReferences;
    }

    @Override
    public List<Node> listReference(ReferenceSystem system, ReferenceCode code, ReferenceType type,
                                    QuoteType quoteType) {
        return listReference(system, code, type, quoteType, null);
    }

    @Override
    public List<Node> listReference(ReferenceSystem system, ReferenceCode code, ReferenceType type,
                                    QuoteType quoteType, NodeVersion version) {

        Node node = nodeRepository.find(system, code, type, version);
        if (node == null) {
            return new ArrayList<>();
        }
        return referenceRepository.listReference(node.getId(), quoteType);
    }
}
