package io.github.timemachinelab.infrastructure.persistence.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("user_credential")
public class UserCredentialEntity {
    @TableId("credential_id")
    private String credentialId;

    private String userId;

    private String credentialAccount;

    private String credentialType;

    private String credentialContent;

    private String extInfo;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
