package io.github.timemachinelab.service.application;

import io.github.timemachinelab.api.req.VerifyCredentialReq;
import io.github.timemachinelab.service.port.out.UserProfileRepositoryPort;
import io.github.timemachinelab.infrastructure.persistence.entity.UserProfileEntity;
import org.springframework.stereotype.Service;

@Service
public class UserProfileApplicationService {

    private final UserProfileRepositoryPort userProfileRepositoryPort;

    // 构造函数注入
    public UserProfileApplicationService(UserProfileRepositoryPort userProfileRepositoryPort) {
        this.userProfileRepositoryPort = userProfileRepositoryPort;
    }

    // 更新用户资料
    public void updateUserProfile(VerifyCredentialReq verifyCredentialReq) {
        // 根据凭证请求中的用户信息（昵称、头像等）更新用户资料
        UserProfileEntity userProfileEntity = new UserProfileEntity(
                verifyCredentialReq.getUserId(),
                verifyCredentialReq.getNickname(),
                verifyCredentialReq.getAvatar()
        );

        // 调用仓储层保存用户资料
        userProfileRepositoryPort.save(userProfileEntity);
    }
}
