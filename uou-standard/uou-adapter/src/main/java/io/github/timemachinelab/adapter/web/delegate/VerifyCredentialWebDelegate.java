package io.github.timemachinelab.adapter.web.delegate;

import io.github.timemachinelab.api.req.VerifyCredentialReq;
import io.github.timemachinelab.api.resp.VerifyCredentialResp;
import io.github.timemachinelab.service.model.VerifyCredentialModel;
import io.github.timemachinelab.service.process.VerifyCredentialProcessService;
import org.springframework.stereotype.Component;

@Component
public class VerifyCredentialWebDelegate {

    private final VerifyCredentialProcessService verifyCredentialProcessService;

    // 注入 process 层的服务
    public VerifyCredentialWebDelegate(VerifyCredentialProcessService verifyCredentialProcessService) {
        this.verifyCredentialProcessService = verifyCredentialProcessService;
    }

    // 验证凭证并返回响应
    public VerifyCredentialResp verifyCredential(VerifyCredentialReq req) {
        // 1. Web 层 -> DTO 转换：将请求对象转换为内部模型（DTO）
        VerifyCredentialModel verifyCredentialDto = new VerifyCredentialModel(
                req.getCredentialAccount(),
                req.getCredentialType(),
                req.getCredentialContent(),
                req.getNickname(),  // 包含用户信息
                req.getAvatar()
        );

        // 2. 调用 process 层：将 DTO 传递到 process 层进行业务处理
        return verifyCredentialProcessService.process(verifyCredentialDto);
    }
}
