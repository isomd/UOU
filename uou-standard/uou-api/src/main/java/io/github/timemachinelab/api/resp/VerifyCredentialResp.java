package io.github.timemachinelab.api.resp;

import lombok.Data;

@Data
public class VerifyCredentialResp {
    private String userId;

    private String userStatus;

    private String credentialAccount;

    private String credentialType;
}

