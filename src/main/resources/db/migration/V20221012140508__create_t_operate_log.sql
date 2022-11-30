# 创建操作日志记录表
DROP TABLE IF EXISTS `t_operate_log`;

CREATE TABLE `t_operate_log` (
     `op_log_id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
     `op_user_name` varchar(255) DEFAULT NULL COMMENT '操作用户名称',
     `api_uri` varchar(512) DEFAULT NULL COMMENT '接口URI',
     `description` varchar(255) DEFAULT NULL COMMENT '接口描述信息',
     `op_module` varchar(255) DEFAULT NULL COMMENT '操作模块',
     `op_type` varchar(255) DEFAULT NULL COMMENT '操作类型',
     `request_params` text COMMENT '请求参数信息',
     `response_content` text COMMENT '接口返回值内容',
     `detail_message` text COMMENT '接口操作详细信息',
     `op_result` tinyint(1) NOT NULL DEFAULT '1' COMMENT '操作结果 0 失败 1 成功',
     `op_ip_address` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
     `op_time` datetime DEFAULT NULL COMMENT '操作时间',
     `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     PRIMARY KEY (`op_log_id`) USING BTREE,
     KEY `OP_LOG_IDX` (`api_uri`,`op_user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='系统操作日志记录';
