-- user_credential
CREATE TABLE `user_credential` (
  `credential_id`      BIGINT AUTO_INCREMENT COMMENT '凭证ID',
  `user_id`            BIGINT COMMENT '用户ID',
  `credential_account` VARCHAR(255) COMMENT '凭证账号',
  `credential_type`    VARCHAR(255) COMMENT '凭证类型',
  `credential_content` VARCHAR(255) COMMENT '凭证内容',
  `ext_info`           VARCHAR(255) COMMENT '扩展信息',
  `create_time`        DATETIME COMMENT '创建时间',
  `update_time`        DATETIME COMMENT '更新时间',
  PRIMARY KEY (`credential_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户凭证表';