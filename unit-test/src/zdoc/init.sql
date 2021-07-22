CREATE TABLE `node`
(
    `id`                bigint(20) NOT NULL AUTO_INCREMENT,
    `system`            varchar(30)  NOT NULL COMMENT '渠道',
    `code`              varchar(100) NOT NULL COMMENT '节点code',
    `type`              smallint(6) NOT NULL COMMENT '类型，如字段、特征',
    `business_name`     varchar(255)          DEFAULT NULL COMMENT '业务名称',
    `version`           varchar(50)           DEFAULT NULL COMMENT '版本号',
    `business_org_code` varchar(255)          DEFAULT NULL COMMENT '业务所属机构code',
    `create_at`         timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`         varchar(255)          DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`),
    UNIQUE KEY `index_reference_code` (`system`,`code`,`type`) USING BTREE COMMENT '被引用code索引'
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='节点表';

CREATE TABLE `node_reference`
(
    `id`                bigint(20) NOT NULL AUTO_INCREMENT,
    `node_id`           bigint(20) NOT NULL COMMENT '渠道',
    `reference_node_id` bigint(20) NOT NULL COMMENT '节点code',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='节点引用表';