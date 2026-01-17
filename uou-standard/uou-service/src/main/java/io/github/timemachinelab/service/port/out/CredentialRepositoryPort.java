package io.github.timemachinelab.service.port.out;

import io.github.timemachinelab.service.model.VerifyCredentialDto;

public interface CredentialRepositoryPort {
    void save(VerifyCredentialDto model); // 使用业务模型，不依赖实体类
}
