package cn.cat.platform.service.impl;

import cn.cat.platform.common.Constant;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import cn.cat.platform.model.DO.Role;
import cn.cat.platform.dao.RoleMapper;
import cn.cat.platform.service.RoleService;

import java.util.Iterator;
import java.util.List;

/**
  * @author linlt
  * @createTime 2020/4/3 11:10
  * @description TODO
  */
@Service
public class RoleServiceImpl implements RoleService{

    @Resource
    private RoleMapper roleMapper;

    @Override
    public int deleteByPrimaryKey(Integer roleId) {
        return roleMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public int insert(Role record) {
        return roleMapper.insert(record);
    }

    @Override
    public int insertSelective(Role record) {
        return roleMapper.insertSelective(record);
    }

    @Override
    public Role selectByPrimaryKey(Integer roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public int updateByPrimaryKeySelective(Role record) {
        return roleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Role record) {
        return roleMapper.updateByPrimaryKey(record);
    }

    @Override
    public Role findByUserId(Integer userId) {
        return roleMapper.findByUserId(userId);
    }

    @Override
    public List<Role> findRole() {
        List<Role> roles = roleMapper.findAll();
        Iterator<Role> iterator = roles.iterator();
        while (iterator.hasNext()){
            Role role = iterator.next();
            if (Constant.ROLE_ID_ADMIN.equals(role.getRoleId())){
                iterator.remove();
                break;
            }
        }
        return roles;
    }

}
