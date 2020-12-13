package cn.cat.platform.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import cn.cat.platform.dao.PermissionMapper;
import cn.cat.platform.model.DO.Permission;
import cn.cat.platform.service.PermissionService;

import java.util.List;

/**
  * @author linlt
  * @createTime 2020/4/3 11:25
  * @description TODO
  */
@Service
public class PermissionServiceImpl implements PermissionService{

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public int deleteByPrimaryKey(Integer permissionId) {
        return permissionMapper.deleteByPrimaryKey(permissionId);
    }

    @Override
    public int insert(Permission record) {
        return permissionMapper.insert(record);
    }

    @Override
    public int insertSelective(Permission record) {
        return permissionMapper.insertSelective(record);
    }

    @Override
    public Permission selectByPrimaryKey(Integer permissionId) {
        return permissionMapper.selectByPrimaryKey(permissionId);
    }

    @Override
    public int updateByPrimaryKeySelective(Permission record) {
        return permissionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Permission record) {
        return permissionMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Permission> findByPermissionUrl(String permissionUrl) {
        return permissionMapper.findByPermissionUrl(permissionUrl);
    }

    @Override
    public List<Permission> findPermissionByUserId(Integer userId) {
        return permissionMapper.findPermissionByUserId(userId);
    }

}
