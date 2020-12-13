package cn.cat.platform.service;

import cn.cat.platform.model.DO.Device;
import com.github.pagehelper.PageInfo;

/**
 * @author linlt
 * @createTime 2020/3/4 9:04
 */
public interface DeviceService {
    int deleteByPrimaryKey(Integer deviceId);

    int insert(Device record);

    int insertSelective(Device record);

    Device selectByPrimaryKey(Integer deviceId);

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKey(Device record);

    PageInfo<Device> findDeviceList(Integer pn, String deviceNumber, String deviceName, Integer userId);

}
