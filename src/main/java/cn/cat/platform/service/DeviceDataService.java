package cn.cat.platform.service;

import cn.cat.platform.model.VO.Header;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import cn.cat.platform.model.DO.DeviceData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author linlt
 * @CreateTime 2020/3/15 8:21
 */
@Service
public interface DeviceDataService {

    int deleteByPrimaryKey(Integer dataId);

    int insertSelective(DeviceData record);

    DeviceData selectByPrimaryKey(Integer dataId);

    int updateByPrimaryKeySelective(DeviceData record);

    /**
     * 根据测试模式和设备ID分页查询设备数据（包含设备ID和设备数据ID）
     *
     * @param pn       页数
     * @param deviceId 设备ID
     * @param testMode 测试模式
     * @param groupId  分组
     * @return
     */
    Map<String, Object> findDeviceData(Integer pn, Integer deviceId, String testMode, String resultJudgment, String testedBarCode);

    /**
     * 根据设备ID获取设备数据并导出数据（不包含设备ID和设备数据ID）
     *
     * @param deviceId         设备ID
     * @param isNeedBarCode    导出数据是否要包含被测试条码
     * @param isNeedBeatOutput 导出数据是否要包含节拍输出
     * @throws IllegalAccessException
     */
    void exportDeviceData(HttpServletResponse response, Integer deviceId, Boolean isNeedBarCode, Boolean isNeedBeatOutput, String exportType) throws IllegalAccessException, IOException;

    /**
     * 导入数据
     *
     * @param uploadExcel 数据
     * @param deviceId    导入的数据的设备ID
     */
    void importDeviceData(MultipartFile uploadExcel, Integer deviceId);


    /**
     * 获取公共表头
     * @return 表头
     */
    List<Header> getSimpleHeaders();

    /**
     * 获取特有表头
     * @param testMode 测试模式
     * @param resultJudgment 结果判断
     * @return
     */
    List<Header> getDataItemHeaders(String testMode, String resultJudgment);

    String saveDataFromDevice(String message);

    List<String> getResultJudgments();

    List<String> getTestModes();

    List<Header> getHeaders(String testMode, String resultJudgment);
}
