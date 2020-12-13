package cn.cat.platform.dao;

import cn.cat.platform.model.DO.Device;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author linlt
 * @CreateTime 2020/3/4 4:19
 */
public interface DeviceMapper {
    int deleteByPrimaryKey(Integer deviceId);

    int insert(Device record);

    int insertSelective(Device record);

    Device selectByPrimaryKey(Integer deviceId);

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKey(Device record);


    List<Device> findByDeviceNumberAndDeviceNameAndUserId(@Param("deviceNumber") String deviceNumber,
                                                          @Param("deviceName") String deviceName,
                                                          @Param("userId") Integer userId);


     Device findByDeviceNumber(@Param("deviceNumber")String deviceNumber);


}