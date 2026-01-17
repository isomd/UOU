package io.github.timemachinelab.infrastructure.persistence.entity;

import java.time.LocalDateTime;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@Data
@TableName("user_identity")
public class UserIdentityEntity {
    @TableId("user_id")
    private String userId;

    private String userStatus;

    private String extInfo;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
