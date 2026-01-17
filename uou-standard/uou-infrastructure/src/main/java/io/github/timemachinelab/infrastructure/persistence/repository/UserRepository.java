package io.github.timemachinelab.infrastructure.persistence.repository;

import io.github.timemachinelab.domain.credential.model.CredentialModel;
import io.github.timemachinelab.infrastructure.persistence.mapper.UserCredentialMapper;
import io.github.timemachinelab.infrastructure.persistence.mapper.UserIdentityMapper;
import io.github.timemachinelab.service.model.UserModel;
import io.github.timemachinelab.service.port.out.UserRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRepository implements UserRepositoryPort {

    @Autowired
    private UserIdentityMapper userIdentityMapper;

    @Autowired
    private UserCredentialMapper userCredentialMapper;

    @Override
    public UserModel saveByCredential(UserModel model) {
        return null;
    }

    @Override
    public UserModel selectByCredential(UserModel model) {
        return null;
    }
}
