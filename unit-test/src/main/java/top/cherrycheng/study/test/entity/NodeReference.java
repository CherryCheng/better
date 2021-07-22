package top.cherrycheng.study.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.cherrycheng.study.test.type.NodeId;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeReference implements Serializable {

    private static final long serialVersionUID = 4017167207171789124L;

    private NodeId nodeId;

    private NodeId referenceId;
}
