package cn.cat.platform.service.impl;

import cn.cat.platform.common.Constant;
import cn.cat.platform.dao.DeviceDataMapper;
import cn.cat.platform.dao.UserMapper;
import cn.cat.platform.enums.ResultCode;
import cn.cat.platform.exception.BusinessException;
import cn.cat.platform.service.DeviceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import cn.cat.platform.dao.DeviceMapper;
import cn.cat.platform.model.DO.Device;

import java.util.List;

/**
 * @author linlt
 * @CreateTime 2020/3/4 4:19
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    @Resource
    private DeviceMapper deviceMapper;
    @Resource
    private DeviceDataMapper deviceDataMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public int deleteByPrimaryKey(Integer deviceId) {
        deviceDataMapper.deleteByDeviceId(deviceId);
        return deviceMapper.deleteByPrimaryKey(deviceId);
    }

    @Override
    public int insert(Device record) {
        return deviceMapper.insert(record);
    }

    @Override
    public int insertSelective(Device record) {
        return deviceMapper.insertSelective(record);
    }

    @Override
    public Device selectByPrimaryKey(Integer deviceId) {
        return deviceMapper.selectByPrimaryKey(deviceId);
    }

    @Override
    public int updateByPrimaryKeySelective(Device record) {
        if (userMapper.selectByUserId(record.getUserId()) == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在，无法更改设备所属用户");
        }
        return deviceMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Device record) {
        if (userMapper.selectByUserId(record.getUserId()) == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在，无法更改设备所属用户");
        }
        return deviceMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageInfo<Device>  findDeviceList(Integer pn, String deviceNumber, String deviceName, Integer userId) {
        PageHelper.startPage(pn, Constant.PAGE_SIZE);
        List<Device> deviceList = deviceMapper.findByDeviceNumberAndDeviceNameAndUserId(deviceNumber, deviceName, userId);
        return new PageInfo<Device>(deviceList);
    }

}
