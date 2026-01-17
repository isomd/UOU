package io.github.timemachinelab.adapter.web.delegate;

import io.github.timemachinelab.api.req.GetUserReq;
import io.github.timemachinelab.api.resp.UserResp;
import io.github.timemachinelab.service.model.GetUserModel;
import io.github.timemachinelab.service.model.UserModel;
import io.github.timemachinelab.service.process.UserProcessService;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class UserDelegate {

    @Resource
    private UserProcessService userProcessService;

    /*
    *
    * */
    public UserResp getUser(GetUserReq req){
        GetUserModel getUserModel = GetUserModel.builder().userId(req.getUserId()).build();

        UserModel userdDTO = userProcessService.getUser(getUserModel);

        UserResp userResp = UserResp.builder()
                .userId(userdDTO.getUserId())
                .userName(userdDTO.getUserName())
                .build();
        return userResp;
    }
}
