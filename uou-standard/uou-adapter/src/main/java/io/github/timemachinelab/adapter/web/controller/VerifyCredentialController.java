package io.github.timemachinelab.adapter.web.controller;

import io.github.timemachinelab.adapter.web.delegate.VerifyCredentialWebDelegate;
import io.github.timemachinelab.api.req.VerifyCredentialReq;
import io.github.timemachinelab.api.resp.VerifyCredentialResp;
import io.github.timemachinelab.common.annotation.AutoResp;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Valid
@RestController
@RequestMapping("uou/api/credentials")
public class VerifyCredentialController {

    private final VerifyCredentialWebDelegate delegate;

    // 注入 VerifyCredentialWebDelegate（适配器）
    public VerifyCredentialController(VerifyCredentialWebDelegate delegate) {
        this.delegate = delegate;
    }

    // 核验凭证接口
    @AutoResp
    @PostMapping("/verify")
    public VerifyCredentialResp verifyCredential(@RequestBody VerifyCredentialReq req) {
        return delegate.verifyCredential(req);  // 调用适配器中的验证逻辑
    }
}
