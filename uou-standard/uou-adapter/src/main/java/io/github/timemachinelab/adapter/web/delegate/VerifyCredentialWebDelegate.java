package io.github.timemachinelab.adapter.web.delegate;

import io.github.timemachinelab.api.req.VerifyCredentialReq;
import io.github.timemachinelab.api.resp.VerifyCredentialResp;
import io.github.timemachinelab.service.model.UserInfoModel;
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
        VerifyCredentialModel verifyCredentialModel = VerifyCredentialModel.builder()
                .credentialAccount(req.getCredentialAccount())
                .credentialType(req.getCredentialType())
                .credentialContent(req.getCredentialContent())
                .build();

        // 2. 调用 process 层：将 DTO 传递到 process 层进行业务处理
        UserInfoModel userModel = verifyCredentialProcessService.verifyCredential(verifyCredentialModel);
        // 3. process 层 -> DTO 映射：将 process 层返回的模型转换为响应对象
        VerifyCredentialResp res = VerifyCredentialResp.builder()
                .userId(userModel.getUserId())
                .userStatus(userModel.getUserStatus())
                .credentialAccount(userModel.getCredentialAccount())
                .credentialType(userModel.getCredentialType())
                .build();
        return res;
    }
}
