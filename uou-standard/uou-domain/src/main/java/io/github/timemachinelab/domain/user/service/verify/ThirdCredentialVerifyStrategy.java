package io.github.timemachinelab.domain.user.service.verify;


import io.github.timemachinelab.domain.user.model.UserModel;

public abstract class ThirdCredentialVerifyStrategy implements CredentialVerifyStrategy{
    @Override
    public boolean verify(UserModel verifyUserModel, UserModel userModel) {
        return verifyUserModel.getCredentialAccount().equals(userModel.getCredentialAccount())
                && verifyUserModel.getCredentialType().equals(userModel.getCredentialType());
    }
}
