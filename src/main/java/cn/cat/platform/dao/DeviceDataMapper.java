package cn.cat.platform.dao;

import cn.cat.platform.model.DO.DeviceData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author linlt
 * @CreateTime 2020/3/15 8:21
 */
@Mapper
public interface DeviceDataMapper {
    int deleteByPrimaryKey(Integer dataId);

    int insert(DeviceData record);

    int insertSelective(DeviceData record);

    DeviceData selectByPrimaryKey(Integer dataId);

    int updateByPrimaryKeySelective(DeviceData record);

    int updateByPrimaryKey(DeviceData record);

    List<DeviceData> findByDeviceId(@Param("deviceId")Integer deviceId);

    int deleteByDeviceId(Integer deviceId);

    List<DeviceData> selectByDeviceIdAndResultJudgmentAndTestedBarCode(@Param("deviceId")Integer deviceId,@Param("resultJudgment")String resultJudgment,@Param("testedBarCode")String testedBarCode);


     List<DeviceData> selectByDeviceIdAndTestModeAndResultJudgmentAndTestedBarCode(@Param("deviceId")Integer deviceId,@Param("testMode")String testMode,@Param("resultJudgment")String resultJudgment,@Param("testedBarCode")String testedBarCode);


     List<DeviceData> selectByDeviceIdAndTestedBarCode(@Param("deviceId")Integer deviceId,@Param("testedBarCode")String testedBarCode);


     List<DeviceData> selectByDeviceIdAndTestModeAndTestedBarCode(@Param("deviceId")Integer deviceId,@Param("testMode")String testMode,@Param("testedBarCode")String testedBarCode);


}