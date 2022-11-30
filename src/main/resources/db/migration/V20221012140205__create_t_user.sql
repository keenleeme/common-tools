# 创建用户信息表
DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
    `user_id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_name` varchar(255) NOT NULL COMMENT '用户名称',
    `real_name` varchar(255) DEFAULT NULL COMMENT '真实姓名',
    `password` varchar(255) NOT NULL COMMENT '账号密码',
    `gender` tinyint(2) NOT NULL DEFAULT '1' COMMENT '性别 1 男 2 女',
    `phone_number` varchar(32) DEFAULT NULL COMMENT '手机号码',
    `last_active_time` datetime DEFAULT NULL COMMENT '最近活跃时间',
    `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `removed` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除标识 0 未被删除 1 已被删除',
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `USER_NAME_IDX` (`user_name`) USING BTREE COMMENT '用户名称唯一键'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户信息表';
