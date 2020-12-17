package cn.cat.platform.service.impl;

import cn.cat.platform.common.Constant;
import cn.cat.platform.common.DeviceConstant;
import cn.cat.platform.dao.DeviceMapper;
import cn.cat.platform.exception.BusinessException;
import cn.cat.platform.model.DO.Device;
import cn.cat.platform.enums.ResultCode;
import cn.cat.platform.model.VO.Header;
import cn.cat.platform.service.DeviceDataService;
import cn.cat.platform.utils.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import cn.cat.platform.dao.DeviceDataMapper;
import cn.cat.platform.model.DO.DeviceData;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.*;

/**
 * @author linlt
 * @CreateTime 2020/3/4 4:18
 */
@Service
@Slf4j
public class DeviceDataServiceImpl implements DeviceDataService {

    @Resource
    private DeviceDataMapper deviceDataMapper;
    @Resource
    private DeviceMapper deviceMapper;

    @Override
    public int deleteByPrimaryKey(Integer dataId) {
        return deviceDataMapper.deleteByPrimaryKey(dataId);
    }


    @Override
    public int insertSelective(DeviceData record) {
        boolean isValid = ValidateUtil.validateDeviceData(record);
        if (isValid) {
            return deviceDataMapper.insertSelective(record);
        } else {
            throw new BusinessException(ResultCode.DEVICE_DATA_IS_INVALID);
        }
    }

    @Override
    public DeviceData selectByPrimaryKey(Integer dataId) {
        return deviceDataMapper.selectByPrimaryKey(dataId);
    }

    @Override
    public int updateByPrimaryKeySelective(DeviceData record) {
        boolean isValid = ValidateUtil.validateDeviceData(record);
        if (isValid) {
            return deviceDataMapper.updateByPrimaryKeySelective(record);
        } else {
            throw new BusinessException(ResultCode.DEVICE_DATA_IS_INVALID);
        }
    }

    @Override
    public List<Header> getDataItemHeaders(String testMode, String resultJudgment) {
        switch (resultJudgment) {
            case DeviceConstant.RESULT_JUDGMENT_ACCEPT:
            case DeviceConstant.RESULT_JUDGMENT_REJECT:
            case DeviceConstant.RESULT_JUDGMENT_ALL:
                return getDataItemHeaders(testMode);
            default:
                return null;
        }
    }


    private List<Header> getDataItemHeaders(String testMode) {
        List<Header> dataItemHeaders = new ArrayList<>();
        if (DeviceConstant.TEST_TYPE_PLR.equals(testMode)) {
            //"压降", "压降单位", "泄漏率", "泄漏率单位", "测试压", "测试压单位"
            Collections.addAll(dataItemHeaders,
                    new Header(DeviceConstant.ATTRIBUTE_PRESSURE_DROP_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_1_EN),
                    new Header(DeviceConstant.ATTRIBUTE_PRESSURE_DROP_UNIT_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_1_UNIT_EN),
                    new Header(DeviceConstant.ATTRIBUTE_LEAK_RATE_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_2_EN),
                    new Header(DeviceConstant.ATTRIBUTE_LEAK_RATE_UNIT_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_2_UNIT_EN),
                    new Header(DeviceConstant.ATTRIBUTE_TEST_PRESSURE_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_3_EN),
                    new Header(DeviceConstant.ATTRIBUTE_TEST_PRESSURE_UNIT_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_3_UNIT_EN));
        } else if (DeviceConstant.TEST_TYPE_PDL.equals(testMode)) {
            //"测试压", "测试压单位", "压降", "压降单位"
            Collections.addAll(dataItemHeaders,
                    new Header(DeviceConstant.ATTRIBUTE_TEST_PRESSURE_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_1_EN),
                    new Header(DeviceConstant.ATTRIBUTE_TEST_PRESSURE_UNIT_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_1_UNIT_EN),
                    new Header(DeviceConstant.ATTRIBUTE_PRESSURE_DROP_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_2_EN),
                    new Header(DeviceConstant.ATTRIBUTE_PRESSURE_DROP_UNIT_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_2_UNIT_EN));
        } else if (DeviceConstant.TEST_TYPE_CPDL.equals(testMode)) {
            //"压降", "压降单位", "补偿后压降", "补偿后压降单位", "测试压", "测试压单位"
            Collections.addAll(dataItemHeaders,
                    new Header(DeviceConstant.ATTRIBUTE_PRESSURE_DROP_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_1_EN),
                    new Header(DeviceConstant.ATTRIBUTE_PRESSURE_DROP_UNIT_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_1_UNIT_EN),
                    new Header(DeviceConstant.ATTRIBUTE_PRESSURE_DROP_AFTER_COMPENSATION_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_2_EN),
                    new Header(DeviceConstant.ATTRIBUTE_PRESSURE_DROP_AFTER_COMPENSATION_UNIT_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_2_UNIT_EN),
                    new Header(DeviceConstant.ATTRIBUTE_TEST_PRESSURE_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_3_EN),
                    new Header(DeviceConstant.ATTRIBUTE_TEST_PRESSURE_UNIT_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_3_UNIT_EN));
        } else if (DeviceConstant.TEST_TYPE_OCC.equals(testMode)) {
            //"测试压", "测试压单位"
            Collections.addAll(dataItemHeaders,
                    new Header(DeviceConstant.ATTRIBUTE_TEST_PRESSURE_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_1_EN),
                    new Header(DeviceConstant.ATTRIBUTE_TEST_PRESSURE_UNIT_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_1_UNIT_EN));
        } else if (DeviceConstant.TEST_TYPE_VXXX.equals(testMode)) {
            //"负压模式", "负压模式单位"
            Collections.addAll(dataItemHeaders,
                    new Header(DeviceConstant.ATTRIBUTE_NEGATIVE_PRESSURE_MODE_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_1_EN),
                    new Header(DeviceConstant.ATTRIBUTE_NEGATIVE_PRESSURE_MODE_UNIT_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_1_UNIT_EN));
        } else if (DeviceConstant.TEST_TYPE_MFR.equals(testMode)) {
            //"测试压力", "测试压力单位", "流量值", "流量值单位"
            Collections.addAll(dataItemHeaders,
                    new Header(DeviceConstant.ATTRIBUTE_TEST_PRESSURE_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_1_EN),
                    new Header(DeviceConstant.ATTRIBUTE_TEST_PRESSURE_UNIT_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_1_UNIT_EN),
                    new Header(DeviceConstant.ATTRIBUTE_FLOW_VALUE_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_2_EN),
                    new Header(DeviceConstant.ATTRIBUTE_FLOW_VALUE_UNIT_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_2_UNIT_EN));
        } else if (DeviceConstant.TEST_TYPE_ALL.equals(testMode)) {
            //"数据项1", "数据项1单位", "数据项2", "数据项2单位", "数据项3", "数据项3单位"
            Collections.addAll(dataItemHeaders,
                    new Header(DeviceConstant.ATTRIBUTE_DATA_ITEM_1_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_1_EN),
                    new Header(DeviceConstant.ATTRIBUTE_DATA_ITEM_1_UNIT_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_1_UNIT_EN),
                    new Header(DeviceConstant.ATTRIBUTE_DATA_ITEM_2_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_2_EN),
                    new Header(DeviceConstant.ATTRIBUTE_DATA_ITEM_2_UNIT_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_2_UNIT_EN),
                    new Header(DeviceConstant.ATTRIBUTE_DATA_ITEM_3_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_3_EN),
                    new Header(DeviceConstant.ATTRIBUTE_DATA_ITEM_3_UNIT_CN, DeviceConstant.ATTRIBUTE_DATA_ITEM_3_UNIT_EN));
        }
        return dataItemHeaders;
    }

    @Override
    public List<Header> getSimpleHeaders() {
        List<Header> headers = new ArrayList<>();
        //"全球唯一ID", "组号", "日期", "时间", "结果", "测试模式"
        Collections.addAll(headers,
                new Header("全球唯一ID", "deviceNumber"),
                new Header(DeviceConstant.ATTRIBUTE_GROUP_ID_CN, DeviceConstant.ATTRIBUTE_GROUP_ID_EN),
                new Header(DeviceConstant.ATTRIBUTE_DATE_CN, DeviceConstant.ATTRIBUTE_DATE_EN),
                new Header(DeviceConstant.ATTRIBUTE_TIME_CN, DeviceConstant.ATTRIBUTE_TIME_EN),
                new Header(DeviceConstant.ATTRIBUTE_RESULT_JUDGMENT_CN, DeviceConstant.ATTRIBUTE_RESULT_JUDGMENT_EN),
                new Header(DeviceConstant.ATTRIBUTE_TEST_MODE_CN, DeviceConstant.ATTRIBUTE_TEST_MODE_EN));

        Collections.addAll(headers,
                new Header(DeviceConstant.ATTRIBUTE_RUN_NUMBER_CN, DeviceConstant.ATTRIBUTE_RUN_NUMBER_EN),
                new Header(DeviceConstant.ATTRIBUTE_TESTED_BAR_CODE_CN, DeviceConstant.ATTRIBUTE_TESTED_BAR_CODE_EN),
                new Header(DeviceConstant.ATTRIBUTE_BEAT_OUTPUT_CN, DeviceConstant.ATTRIBUTE_BEAT_OUTPUT_EN)
        );
        return headers;
    }

    /**
     * 保存仪器上传数据
     *
     * @param message
     * @return
     */
    @Override
    public String saveDataFromDevice(String message) {
        // TestResult: 设备ID 组号 日期 时间 结果判断 测试模式 0.132322 Pa 0.000078 sccm -66.423820 kPa null null C=12
        // TestResult: 317--02013 P1 05-30-2020 15:49:32 Accept PLR 0.132322 Pa C=12 null null
        String returnMsg = "device data error, please check the device data";
        if (!message.startsWith("TestResult")) {
            return returnMsg;
        }

        DeviceData deviceData = new DeviceData();
        String[] fields = message.replaceAll("\\r\\n", "").replaceAll(" +", " ").split(" ");
        try {
            deviceData.setDate(DateTransformUtil.stringToDateNetty(fields[3]));
            deviceData.setTime(DateTransformUtil.stringToTime(fields[4]));

            String deviceNumber = fields[1];
            String testMode = fields[6];

            deviceData.setGroupId(fields[2]);
            deviceData.setResultJudgment(fields[5]);
            deviceData.setTestMode(testMode);
            int index = 7;
            // 当结果判断为ACCEPT/REJECT时有数据
            if (deviceData.getResultJudgment().equals(DeviceConstant.RESULT_JUDGMENT_ACCEPT)
                    || deviceData.getResultJudgment().equals(DeviceConstant.RESULT_JUDGMENT_REJECT)) {
                if (DeviceConstant.TEST_TYPE_PLR.equals(testMode) || DeviceConstant.TEST_TYPE_CPDL.equals(testMode)) {
                    //"压降", "压降单位", "泄漏率", "泄漏率单位", "测试压", "测试压单位"
                    BigDecimal dataItem1 = new BigDecimal(fields[7]);
                    BigDecimal dataItem2 = new BigDecimal(fields[9]);
                    BigDecimal dataItem3 = new BigDecimal(fields[11]);
                    deviceData.setDataItem(dataItem1, fields[8], dataItem2, fields[10], dataItem3, fields[12]);
                    index = 13;
                } else if (DeviceConstant.TEST_TYPE_PDL.equals(testMode) || DeviceConstant.TEST_TYPE_MFR.equals(testMode)) {
                    //"测试压", "测试压单位", "压降", "压降单位"
                    //"测试压力", "测试压力单位", "流量值", "流量值单位"
                    BigDecimal dataItem1 = new BigDecimal(fields[7]);
                    BigDecimal dataItem2 = new BigDecimal(fields[9]);
                    deviceData.setDataItem(dataItem1, fields[8], dataItem2, fields[10]);
                    index = 11;
                } else if (DeviceConstant.TEST_TYPE_OCC.equals(testMode) || DeviceConstant.TEST_TYPE_VXXX.equals(testMode)) {
                    //"测试压", "测试压单位"
                    BigDecimal dataItem1 = new BigDecimal(fields[7]);
                    deviceData.setDataItem(dataItem1, fields[8]);
                    index = 9;
                }
            }

            String runNumber = fields[index++];
            deviceData.setRunNumber(runNumber);
            deviceData.setBeatOutput("");
            deviceData.setTestedBarCode("");
            for (int i = 0; i < 2; i++) {
                if (fields.length > index) {
                    String field = fields[index++];
                    if (field.startsWith("SN:")) {
                        deviceData.setTestedBarCode(field);
                    } else if (field.startsWith("P")) {
                        deviceData.setBeatOutput(field);
                    }
                }
            }


            Device device = deviceMapper.findByDeviceNumber(deviceNumber);
            if (device != null) {
                deviceData.setDeviceId(device.getDeviceId());
                int i = deviceDataMapper.insertSelective(deviceData);
                if (i > 0) {
                    returnMsg = "save device data success";
                } else {
                    returnMsg = "save device data failed";
                }
            } else {
                returnMsg = "save device data failed, because device id is not exist";
            }

        } catch (Exception e) {
            return returnMsg;
        }
        return returnMsg;
    }

    @Override
    public List<String> getResultJudgments() {
        List<String> resultJudgment = new ArrayList<>();
        resultJudgment.add(DeviceConstant.RESULT_JUDGMENT_ALL);
        resultJudgment.add(DeviceConstant.RESULT_JUDGMENT_ACCEPT);
        resultJudgment.add(DeviceConstant.RESULT_JUDGMENT_REJECT);
        resultJudgment.add(DeviceConstant.RESULT_JUDGMENT_TEST_STOP);
        resultJudgment.add(DeviceConstant.RESULT_JUDGMENT_HIGH_TEST_P);
        resultJudgment.add(DeviceConstant.RESULT_JUDGMENT_LOW_TEST_P);
        resultJudgment.add(DeviceConstant.RESULT_JUDGMENT_HIGHER_MAX_P);
        resultJudgment.add(DeviceConstant.RESULT_JUDGMENT_LOWER_MIN_P);
        resultJudgment.add(DeviceConstant.RESULT_JUDGMENT_SEVERE_LEAK);
        resultJudgment.add(DeviceConstant.RESULT_JUDGMENT_TEST_ERROR);
        resultJudgment.add(DeviceConstant.RESULT_JUDGMENT_HIGH_P_RANGE);
        resultJudgment.add(DeviceConstant.RESULT_JUDGMENT_TEST_TYPE_ERR);
        return resultJudgment;
    }

    @Override
    public List<String> getTestModes() {
        List<String> testModes = new ArrayList<>();
        testModes.add(DeviceConstant.TEST_TYPE_PLR);
        testModes.add(DeviceConstant.TEST_TYPE_PDL);
        testModes.add(DeviceConstant.TEST_TYPE_CPDL);
        testModes.add(DeviceConstant.TEST_TYPE_OCC);
        testModes.add(DeviceConstant.TEST_TYPE_VXXX);
        testModes.add(DeviceConstant.TEST_TYPE_MFR);
        testModes.add(DeviceConstant.TEST_TYPE_ALL);
        return testModes;
    }

    @Override
    public List<Header> getHeaders(String testMode, String resultJudgment) {
        List<Header> headers = new ArrayList<>();
        //"全球唯一ID", "组号", "日期", "时间", "结果", "测试模式"
        Collections.addAll(headers,
                new Header("全球唯一ID", "deviceNumber"),
                new Header(DeviceConstant.ATTRIBUTE_GROUP_ID_CN, DeviceConstant.ATTRIBUTE_GROUP_ID_EN),
                new Header(DeviceConstant.ATTRIBUTE_DATE_CN, DeviceConstant.ATTRIBUTE_DATE_EN),
                new Header(DeviceConstant.ATTRIBUTE_TIME_CN, DeviceConstant.ATTRIBUTE_TIME_EN),
                new Header(DeviceConstant.ATTRIBUTE_RESULT_JUDGMENT_CN, DeviceConstant.ATTRIBUTE_RESULT_JUDGMENT_EN),
                new Header(DeviceConstant.ATTRIBUTE_TEST_MODE_CN, DeviceConstant.ATTRIBUTE_TEST_MODE_EN));


        switch (resultJudgment) {
            case DeviceConstant.RESULT_JUDGMENT_ACCEPT:
            case DeviceConstant.RESULT_JUDGMENT_REJECT:
            case DeviceConstant.RESULT_JUDGMENT_ALL:
                headers.addAll(getDataItemHeaders(testMode));
            default:
                break;
        }

        Collections.addAll(headers,
                new Header(DeviceConstant.ATTRIBUTE_RUN_NUMBER_CN, DeviceConstant.ATTRIBUTE_RUN_NUMBER_EN),
                new Header(DeviceConstant.ATTRIBUTE_TESTED_BAR_CODE_CN, DeviceConstant.ATTRIBUTE_TESTED_BAR_CODE_EN),
                new Header(DeviceConstant.ATTRIBUTE_BEAT_OUTPUT_CN, DeviceConstant.ATTRIBUTE_BEAT_OUTPUT_EN)
        );
        return headers;
    }


    @Override
    public Map<String, Object> findDeviceData(Integer pn, Integer deviceId, String testMode, String resultJudgment, String testedBarCode) {
        Map<String, Object> data = new HashMap<>(16);

        // 获取全部字段，缺省值为"--"
        List<Header> headers = getSimpleHeaders();
        // 删除三个
        headers.remove(headers.size() - 1);
        headers.remove(headers.size() - 1);
        headers.remove(headers.size() - 1);

        List<Header> dataItemHeaders = getDataItemHeaders(testMode);
        headers.addAll(dataItemHeaders);
        // 调整顺序
        Collections.addAll(headers,
                new Header(DeviceConstant.ATTRIBUTE_RUN_NUMBER_CN, DeviceConstant.ATTRIBUTE_RUN_NUMBER_EN),
                new Header(DeviceConstant.ATTRIBUTE_TESTED_BAR_CODE_CN, DeviceConstant.ATTRIBUTE_TESTED_BAR_CODE_EN),
                new Header(DeviceConstant.ATTRIBUTE_BEAT_OUTPUT_CN, DeviceConstant.ATTRIBUTE_BEAT_OUTPUT_EN)
        );

        //分页查询
        PageHelper.startPage(pn, Constant.PAGE_SIZE);
        //根据测试模式和设备ID和组号获取设备数据（包含设备ID和设备数据ID）
        List<DeviceData> deviceData;
        if (DeviceConstant.TEST_TYPE_ALL.equals(testMode)) {
            if (DeviceConstant.RESULT_JUDGMENT_ALL.equals(resultJudgment)) {
                //查找所有数据
                deviceData = deviceDataMapper.selectByDeviceIdAndTestedBarCode(deviceId, testedBarCode);
            } else {
                deviceData = deviceDataMapper.selectByDeviceIdAndResultJudgmentAndTestedBarCode(deviceId, resultJudgment, testedBarCode);
            }
        } else {
            if (DeviceConstant.RESULT_JUDGMENT_ALL.equals(resultJudgment)) {
                //查找所有数据
                deviceData = deviceDataMapper.selectByDeviceIdAndTestModeAndTestedBarCode(deviceId, testMode, testedBarCode);
            } else {
                //查找所有数据
                deviceData = deviceDataMapper.selectByDeviceIdAndTestModeAndResultJudgmentAndTestedBarCode(deviceId, testMode, resultJudgment, testedBarCode);
            }
        }
        PageInfo<DeviceData> pageInfo = new PageInfo<DeviceData>(deviceData);
        data.put("headers", headers);
        data.put("pageInfo", pageInfo);
        return data;
    }

    @Override
    public void exportDeviceData(HttpServletResponse response, Integer deviceId, Boolean isNeedBarCode, Boolean isNeedBeatOutput, String exportType) throws IllegalAccessException, IOException {
        //获取全部测试模式的数据
        List<Header> headers = getHeaders(DeviceConstant.TEST_TYPE_ALL, DeviceConstant.RESULT_JUDGMENT_ALL);
        //根据设备ID获取设备数据（包含设备ID和设备数据ID）
        List<DeviceData> deviceData = deviceDataMapper.findByDeviceId(deviceId);
        //处理可选表头
        if (!isNeedBarCode) {
            headers.remove(new Header(DeviceConstant.ATTRIBUTE_TESTED_BAR_CODE_CN, DeviceConstant.ATTRIBUTE_TESTED_BAR_CODE_EN));
        }
        if (!isNeedBeatOutput) {
            headers.remove(new Header(DeviceConstant.ATTRIBUTE_BEAT_OUTPUT_CN, DeviceConstant.ATTRIBUTE_BEAT_OUTPUT_EN));
        }
        //所属设备
        Device device = deviceMapper.selectByPrimaryKey(deviceId);
        Map<String, Object> exportParameters = new HashMap<>(8);
        exportParameters.put("headers", headers);
        exportParameters.put("deviceData", deviceData);
        exportParameters.put("deviceName", device.getDeviceName());
        exportParameters.put("deviceNumber", device.getDeviceNumber());
        exportParameters.put("isNeedBarCode", isNeedBarCode);
        exportParameters.put("isNeedBeatOutput", isNeedBeatOutput);
        if ("xls".equals(exportType)) {
            //导出
            Workbook workbook = ExcelUtil.exportExcel(exportParameters);
            ResponseUtil.exportExcel(response, workbook, "data.xls");
        } else if ("csv".equals(exportType)) {
            File file = ExcelUtil.exportCsv(exportParameters);
            ResponseUtil.exportCsv(response, file, "data.csv");
        }
    }

    @Override
    public void importDeviceData(MultipartFile uploadExcel, Integer deviceId) {
        String uploadExcelPath = UploadFileUtil.uploadFile(uploadExcel, "uploadExcel");
        if (uploadExcelPath == null) {
            //文件上传失败
            throw new BusinessException(ResultCode.UPLOAD_FAIL);
        }
        Map<String, Object> data = null;
        try {
            data = ExcelUtil.getDataFromFile(uploadExcelPath);
        } catch (IOException e) {
            //文件读取失败
            throw new BusinessException(ResultCode.FILE_READ_FAIL);
        }
        //删除文件
        File file = new File(uploadExcelPath);
        boolean delete = file.delete();
        if (!delete) {
            log.error("文件" + uploadExcelPath + "删除失败");
        }

        List<Map<String, Integer>> headers = (List<Map<String, Integer>>) data.get("headers");
        List<List<List<Object>>> dataList = (List<List<List<Object>>>) data.get("dataList");

        int sheetNumber = headers.size();
        for (int i = 0; i < sheetNumber; i++) {
            List<List<Object>> lists = dataList.get(i);
            Map<String, Integer> header = headers.get(i);
            //第一行是表头
            int rowIndex = 2;
            for (List<Object> list : lists) {
                DeviceData deviceData;
                try {
                    deviceData = packageDeviceData(deviceId, header, list);
                } catch (RuntimeException e) {
                    throw new BusinessException(ResultCode.EXCEL_DATA_ERROR, "第" + rowIndex + "行数据有错误，请进行检查.");
                }
                deviceDataMapper.insertSelective(deviceData);
                rowIndex++;
            }
        }
    }

    private static Date getData(Object dateObj) {
        Date date = null;
        if (dateObj instanceof String) {
            date = DateTransformUtil.stringToDate((String) dateObj);
        } else {
            date = new Date((Long) dateObj);
        }
        return date;
    }

    private static Time getTime(Object timeObj) {
        Time time = null;
        if (timeObj instanceof String) {
            time = DateTransformUtil.stringToTime((String) timeObj);
        } else {
            time = new Time((Long) timeObj);
        }
        return time;
    }

    /**
     * 将excel数据行封装成对象
     *
     * @param deviceId
     * @param headers
     * @param list
     * @return
     */
    public DeviceData packageDeviceData(Integer deviceId, Map<String, Integer> headers, List<Object> list) throws IndexOutOfBoundsException {
        String testMode = (String) list.get(headers.get(DeviceConstant.ATTRIBUTE_TEST_MODE_EN));

        Date date = getData(list.get(headers.get(DeviceConstant.ATTRIBUTE_DATE_EN)));
        Time time = getTime(list.get(headers.get(DeviceConstant.ATTRIBUTE_TIME_EN)));
        String runNumber = (String) list.get(headers.get(DeviceConstant.ATTRIBUTE_RUN_NUMBER_EN));
        String testedBarCode = null;
        //可选可能为null
        if (headers.get(DeviceConstant.ATTRIBUTE_TESTED_BAR_CODE_EN) != null) {
            testedBarCode = (String) list.get(headers.get(DeviceConstant.ATTRIBUTE_TESTED_BAR_CODE_EN));
        }
        String beatOutput = null;
        if (headers.get(DeviceConstant.ATTRIBUTE_BEAT_OUTPUT_EN) != null) {
            beatOutput = (String) list.get(headers.get(DeviceConstant.ATTRIBUTE_BEAT_OUTPUT_EN));
        }
        String resultJudgment = (String) list.get(headers.get(DeviceConstant.ATTRIBUTE_RESULT_JUDGMENT_EN));
        DeviceData deviceData = new DeviceData(deviceId, (String) list.get(headers.get(DeviceConstant.ATTRIBUTE_GROUP_ID_EN)), date, time, resultJudgment, testMode, runNumber, testedBarCode, beatOutput);
        if (!ValidateUtil.validateResultJudgment(resultJudgment)) {
            // 非法的
            throw new BusinessException(ResultCode.UNLAWFUL_TEST_MODE);
        }
        switch (resultJudgment) {
            case DeviceConstant.RESULT_JUDGMENT_ACCEPT:
            case DeviceConstant.RESULT_JUDGMENT_REJECT:
                break;
            default:
                return deviceData;
        }
        if (DeviceConstant.TEST_TYPE_PLR.equals(testMode) || DeviceConstant.TEST_TYPE_CPDL.equals(testMode)) {
            BigDecimal dataItem1 = new BigDecimal((String) list.get(headers.get(DeviceConstant.ATTRIBUTE_DATA_ITEM_1_EN)));
            BigDecimal dataItem2 = new BigDecimal((String) list.get(headers.get(DeviceConstant.ATTRIBUTE_DATA_ITEM_2_EN)));
            BigDecimal dataItem3 = new BigDecimal((String) list.get(headers.get(DeviceConstant.ATTRIBUTE_DATA_ITEM_3_EN)));
            deviceData.setDataItem(dataItem1, (String) list.get(headers.get(DeviceConstant.ATTRIBUTE_DATA_ITEM_1_UNIT_EN)),
                    dataItem2, (String) list.get(headers.get(DeviceConstant.ATTRIBUTE_DATA_ITEM_2_UNIT_EN)),
                    dataItem3, (String) list.get(headers.get(DeviceConstant.ATTRIBUTE_DATA_ITEM_3_UNIT_EN)));
        } else if (DeviceConstant.TEST_TYPE_PDL.equals(testMode)) {
            BigDecimal dataItem1 = new BigDecimal((String) list.get(headers.get(DeviceConstant.ATTRIBUTE_DATA_ITEM_1_EN)));
            BigDecimal dataItem2 = new BigDecimal((String) list.get(headers.get(DeviceConstant.ATTRIBUTE_DATA_ITEM_2_EN)));
            deviceData.setDataItem(dataItem1, (String) list.get(headers.get(DeviceConstant.ATTRIBUTE_DATA_ITEM_1_UNIT_EN)),
                    dataItem2, (String) list.get(headers.get(DeviceConstant.ATTRIBUTE_DATA_ITEM_2_UNIT_EN)));
        } else if (DeviceConstant.TEST_TYPE_OCC.equals(testMode) || DeviceConstant.TEST_TYPE_VXXX.equals(testMode)) {
            BigDecimal dataItem1 = new BigDecimal((String) list.get(headers.get(DeviceConstant.ATTRIBUTE_DATA_ITEM_1_EN)));
            deviceData.setDataItem(dataItem1, (String) list.get(headers.get(DeviceConstant.ATTRIBUTE_DATA_ITEM_1_UNIT_EN)));
        }
        return deviceData;
    }

}
