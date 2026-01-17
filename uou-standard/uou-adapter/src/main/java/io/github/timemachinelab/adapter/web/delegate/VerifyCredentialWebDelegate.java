package io.github.timemachinelab.adapter.web.delegate;

import io.github.timemachinelab.api.req.VerifyCredentialReq;
import io.github.timemachinelab.api.resp.VerifyCredentialResp;
import io.github.timemachinelab.service.model.VerifyCredentialDto;
import io.github.timemachinelab.service.port.in.VerifyCredentialUseCase;
import org.springframework.stereotype.Component;

@Component
public class VerifyCredentialWebDelegate {

    private final VerifyCredentialUseCase verifyCredentialUseCase;

    // 注入应用层的用例接口
    public VerifyCredentialWebDelegate(VerifyCredentialUseCase verifyCredentialUseCase) {
        this.verifyCredentialUseCase = verifyCredentialUseCase;
    }

    // 验证凭证并返回响应
    public VerifyCredentialResp verifyCredential(VerifyCredentialReq req) {
        // 将请求数据转换为内部模型
        VerifyCredentialDto verifyCredentialDto = new VerifyCredentialDto(req.getCredentialAccount(), req.getCredentialType(), req.getCredentialContent());

        // 调用应用层处理凭证验证逻辑
        return verifyCredentialUseCase.verifyCredential(verifyCredentialDto);
    }
}
