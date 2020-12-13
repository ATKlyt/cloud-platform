package cn.cat.platform.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import cn.cat.platform.dao.UserManagementMapper;
import cn.cat.platform.model.DO.UserManagement;
import cn.cat.platform.service.UserManagementService;
/**
 * @author linlt
 * @createTime 2020/7/10 6:08
 * @description TODO
 */
@Service
public class UserManagementServiceImpl implements UserManagementService{

    @Resource
    private UserManagementMapper userManagementMapper;

    @Override
    public int insert(UserManagement record) {
        return userManagementMapper.insert(record);
    }

    @Override
    public int insertSelective(UserManagement record) {
        return userManagementMapper.insertSelective(record);
    }

}
