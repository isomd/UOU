package io.github.timemachinelab.service.port.in;

import io.github.timemachinelab.service.model.VerifyCredentialDto;
import io.github.timemachinelab.api.resp.VerifyCredentialResp;

public interface VerifyCredentialUseCase {
    VerifyCredentialResp verifyCredential(VerifyCredentialDto verifyCredentialDto);
}
