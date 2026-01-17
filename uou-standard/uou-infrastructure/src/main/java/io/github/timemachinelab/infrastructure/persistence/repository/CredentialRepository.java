package io.github.timemachinelab.infrastructure.persistence.repository;

import io.github.timemachinelab.service.model.VerifyCredentialDto;
import io.github.timemachinelab.service.port.out.CredentialRepositoryPort;
import io.github.timemachinelab.infrastructure.persistence.entity.CredentialEntity;
import org.springframework.stereotype.Repository;

@Repository
public class CredentialRepository implements CredentialRepositoryPort {

    @Override
    public void save(VerifyCredentialDto model) {
        // 将业务模型转换为持久化实体
        CredentialEntity entity = new CredentialEntity(
                model.getCredentialAccount(),
                model.getCredentialContent(),
                model.getCredentialType()
        );

        // 执行数据库操作（比如 JPA）
        // credentialEntityRepository.save(entity);
    }
}
