package io.github.timemachinelab.domain.user.service;

import io.github.timemachinelab.domain.credential.model.VerifyCredentialModel;
import io.github.timemachinelab.domain.user.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserDomainService {

    public boolean verifyCredential(VerifyCredentialModel verifyCredentialModel, UserModel userModel){
        return true;
    }


}
