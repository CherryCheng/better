package top.cherrycheng.study.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.cherrycheng.study.test.type.NodeId;
import top.cherrycheng.study.test.type.ReferenceCode;
import top.cherrycheng.study.test.type.ReferenceSystem;
import top.cherrycheng.study.test.type.ReferenceType;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Node implements Serializable {

    private static final long serialVersionUID = 6346496782985045039L;

    private NodeId id;

    private ReferenceSystem system;

    private ReferenceCode code;

    private ReferenceType type;

    private String version;

    private String businessName;

    private String businessOrgCode;
    private String createBy;
    private Date createAt;
}
