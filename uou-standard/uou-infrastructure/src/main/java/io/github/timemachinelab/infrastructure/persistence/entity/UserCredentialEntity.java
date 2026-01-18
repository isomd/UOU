package io.github.timemachinelab.infrastructure.persistence.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_credential")
public class UserCredentialEntity {
    @TableId(value = "credential_id")
    private String credentialId;

    private String userId;

    private String credentialAccount;

    private String credentialType;

    private String credentialContent;

    private String extInfo;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
