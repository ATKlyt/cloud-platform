package cn.cat.platform.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import cn.cat.platform.model.DO.UserRole;
import cn.cat.platform.dao.UserRoleMapper;
import cn.cat.platform.service.UserRoleService;
/**
 * @author linlt
 * @createTime 2020/5/15 5:33
 * @description TODO
 */
@Service
public class UserRoleServiceImpl implements UserRoleService{

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public int insert(UserRole record) {
        return userRoleMapper.insert(record);
    }

    @Override
    public int insertSelective(UserRole record) {
        return userRoleMapper.insertSelective(record);
    }

}
