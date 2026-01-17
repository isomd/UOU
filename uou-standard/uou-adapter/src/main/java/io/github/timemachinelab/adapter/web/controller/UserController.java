package io.github.timemachinelab.adapter.web.controller;

import io.github.timemachinelab.adapter.web.delegate.UserDelegate;
import io.github.timemachinelab.api.req.GetUserReq;
import io.github.timemachinelab.api.resp.UserResp;

import io.github.timemachinelab.common.annotation.AutoResp;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserDelegate userDelegate;

    @AutoResp
    public UserResp getUser(@RequestBody GetUserReq req) {
        return userDelegate.getUser(req);
    }
}
