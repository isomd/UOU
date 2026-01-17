package io.github.timemachinelab.domain.user.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserModel {
    private String userId;

    private String userStatus;

    private String credentialId;

    private String credentialAccount;

    private String credentialType;

    private String credentialContent;
}
