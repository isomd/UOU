package io.github.timemachinelab.domain.user.service;

import io.github.timemachinelab.domain.user.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserDomainService {

    public boolean verifyCredential(UserModel verifyCredentialModel, UserModel userModel){
        if (userModel == null || verifyCredentialModel == null){
            return false;
        }

        boolean result = verifyCredentialModel.getCredentialAccount().equals(userModel.getCredentialAccount())
                && verifyCredentialModel.getCredentialType().equals(userModel.getCredentialType());

        if (!isThirdCredential(verifyCredentialModel.getCredentialType())){
            result = result && verifyCredentialModel.getCredentialContent().equals(userModel.getCredentialContent());
        }

        return result;
    }

    protected boolean isThirdCredential(String credentialType){
        return credentialType.equals("4") || credentialType.equals("5");
    }
}