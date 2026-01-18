package io.github.timemachinelab.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.timemachinelab.domain.user.model.UserModel;
import io.github.timemachinelab.infrastructure.persistence.entity.UserCredentialEntity;
import io.github.timemachinelab.infrastructure.persistence.entity.UserIdentityEntity;
import io.github.timemachinelab.infrastructure.persistence.mapper.UserCredentialMapper;
import io.github.timemachinelab.infrastructure.persistence.mapper.UserIdentityMapper;
import io.github.timemachinelab.service.model.VerifyCredentialModel;
import io.github.timemachinelab.service.port.out.UserRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserRepository implements UserRepositoryPort {

    @Autowired
    private UserIdentityMapper userIdentityMapper;

    @Autowired
    private UserCredentialMapper userCredentialMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserModel saveByCredential(UserModel model) {
        // 转换为实体
        UserIdentityEntity identityEntity = UserIdentityEntity.builder()
                .userStatus("normal")
                .build();

        userIdentityMapper.insert(identityEntity);

        UserCredentialEntity credentialEntity = UserCredentialEntity.builder()
                .userId(identityEntity.getUserId())
                .credentialAccount(model.getCredentialAccount())
                .credentialType(model.getCredentialType())
                .credentialContent(model.getCredentialContent())
                .build();
        userCredentialMapper.insert(credentialEntity);

        return UserModel.builder()
                .userId(identityEntity.getUserId())
                .userStatus(identityEntity.getUserStatus())
                .credentialId(credentialEntity.getCredentialId())
                .credentialAccount(credentialEntity.getCredentialAccount())
                .credentialType(credentialEntity.getCredentialType())
                .credentialContent(credentialEntity.getCredentialContent())
                .build();
    }

    @Override
    public UserModel selectByCredential(VerifyCredentialModel model) {
        LambdaQueryWrapper<UserCredentialEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCredentialEntity::getCredentialAccount, model.getCredentialAccount())
                .eq(UserCredentialEntity::getCredentialType, model.getCredentialType())
                .last("LIMIT 1");
        UserCredentialEntity entity = userCredentialMapper.selectOne(wrapper);
        if (entity == null) {
            return null;
        }

        LambdaQueryWrapper<UserIdentityEntity> identityWrapper = new LambdaQueryWrapper<>();
        identityWrapper.eq(UserIdentityEntity::getUserId, entity.getUserId())
                .last("LIMIT 1");
        UserIdentityEntity identityEntity = userIdentityMapper.selectOne(identityWrapper);
        if (identityEntity == null) {
            return null;
        }

        // 转换为领域模型
        return UserModel.builder()
                .userId(identityEntity.getUserId())
                .userStatus(identityEntity.getUserStatus())
                .credentialId(entity.getCredentialId())
                .credentialAccount(entity.getCredentialAccount())
                .credentialType(entity.getCredentialType())
                .credentialContent(entity.getCredentialContent())
                .build();
    }
}
