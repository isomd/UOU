package io.github.timemachinelab.service.port.in;

import io.github.timemachinelab.service.model.VerifyCredentialModel;
import io.github.timemachinelab.api.resp.VerifyCredentialResp;

public interface VerifyCredentialUseCase {
    VerifyCredentialResp verifyCredential(VerifyCredentialModel verifyCredentialDto);
}
