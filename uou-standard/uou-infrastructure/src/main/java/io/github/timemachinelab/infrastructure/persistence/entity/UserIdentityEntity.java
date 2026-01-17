package io.github.timemachinelab.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_identity")
public class UserIdentityEntity {
    @TableId(value = "user_id")
    private String userId;

    private String userStatus;

    private String extInfo;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
