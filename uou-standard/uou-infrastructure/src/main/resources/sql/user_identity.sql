-- user_identity
CREATE TABLE `user_identity` (
  `user_id`        BIGINT AUTO_INCREMENT COMMENT '用户ID',
  `user_status`    VARCHAR(255) COMMENT '用户状态',
  `ext_info`       VARCHAR(255) COMMENT '扩展信息',
  `create_time`    DATETIME COMMENT '创建时间',
  `update_time`    DATETIME COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户身份表';