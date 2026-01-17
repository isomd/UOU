package io.github.timemachinelab.domain.credential.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CredentialModel {
    private String credentialAccount;
    private String credentialContent;
    private String credentialType;
}
