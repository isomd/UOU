package io.github.timemachinelab.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.timemachinelab.infrastructure.persistence.entity.UserIdentityEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserIdentityMapper extends BaseMapper<UserIdentityEntity> {
}
