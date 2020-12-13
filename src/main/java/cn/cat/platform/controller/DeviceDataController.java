package cn.cat.platform.controller;

import cn.cat.platform.common.Result;
import cn.cat.platform.enums.ResultCode;
import cn.cat.platform.model.param.DeviceDataParam;
import cn.cat.platform.model.DO.DeviceData;
import cn.cat.platform.model.VO.Header;
import cn.cat.platform.service.DeviceDataService;
import cn.cat.platform.utils.DateTransformUtil;
import cn.cat.platform.utils.ResultUtil;
import cn.cat.platform.utils.ValidateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;

/**
 * @author linlt
 * @CreateTime 2020/3/4 12:45
 */
@RestController
@RequestMapping("/deviceData")
@CrossOrigin
@Api(tags = "设备数据管理相关接口")
public class DeviceDataController {

    @Autowired
    DeviceDataService deviceDataService;

    @ApiOperation("获取结果判断")
    @GetMapping("/getResultJudgments")
    public Result getResultJudgments(){
        List<String> resultJudgments = deviceDataService.getResultJudgments();
        return ResultUtil.success(resultJudgments);
    }

    @ApiOperation("获取测试模式")
    @GetMapping("/getTestModes")
    public Result getTestModes(){
        List<String> testModes = deviceDataService.getTestModes();
        return ResultUtil.success(testModes);
    }

    /**
     * 获取表头
     * @return
     */
    @ApiOperation("获取表头")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "testMode",value = "测试模式",example = "PDL",required = true, dataType = "string", paramType="query"),
            @ApiImplicitParam(name = "resultJudgment",value = "结果判断",example = "Accept",required = true, dataType = "string", paramType="query")
    })
    @GetMapping("/getHeaders")
    public Result getHeaders(@RequestParam(name = "testMode") String testMode,
                             @RequestParam(name = "resultJudgment") String resultJudgment) {

        List<Header> headers = deviceDataService.getHeaders(testMode, resultJudgment);

        return ResultUtil.success(headers);
    }



    /**
     * 获取公共表头
     * @return
     */
    @ApiOperation("获取公共表头")
    @GetMapping("/getSimpleHeaders")
    public Result getSimpleHeaders() {
        List<Header> headers = deviceDataService.getSimpleHeaders();
        return ResultUtil.success(headers);
    }

    /**
     * 获取特有表头
     * @param testMode 测试模式
     * @param resultJudgment 结果判断
     * @return
     */
    @ApiOperation("获取特有表头")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "testMode",value = "测试模式",required = true, dataType = "string", paramType="query"),
            @ApiImplicitParam(name = "resultJudgment",value = "结果判断",required = true, dataType = "string", paramType="query")
    })
    @GetMapping("/getDataItemHeaders")
    public Result getDataItemHeaders(@RequestParam(name = "testMode") String testMode,
                              @RequestParam(name = "resultJudgment") String resultJudgment) {
        List<Header> headers = deviceDataService.getDataItemHeaders(testMode, resultJudgment);
        return ResultUtil.success(headers);
    }


    /**
     * 查询指定设备指定测试类型数据
     * @param pn
     * @param deviceId
     * @param testMode
     * @return
     */
    @ApiOperation("查询设备数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pn",value = "页数",example = "1",required = true, dataType = "int", paramType="query"),
            @ApiImplicitParam(name = "deviceId",value = "查询条件：设备Id",example = "3",required = true, dataType = "int", paramType="query"),
            @ApiImplicitParam(name = "testMode",value = "查询条件：测试模式",example = "PDL", dataType = "string", paramType="query"),
            @ApiImplicitParam(name = "resultJudgment",value = "查询条件：结果判断",example = "Accept", dataType = "string", paramType="query"),
            @ApiImplicitParam(name = "testedBarCode",value = "查询条件：测试条形码", example = "SN:119536", dataType = "string", paramType="query")
    })
    @GetMapping("/findDeviceData")
    public Result findDeviceData(@RequestParam(name = "pn", defaultValue = "1") Integer pn,
                                  @RequestParam(name = "deviceId") Integer deviceId,
                                  @RequestParam(name = "testMode") String testMode,
                                  @RequestParam(name = "resultJudgment") String resultJudgment,
                                  @RequestParam(name = "testedBarCode") String testedBarCode) {
        Map<String, Object> data = deviceDataService.findDeviceData(pn, deviceId, testMode, resultJudgment, testedBarCode);
        return ResultUtil.success(data);
    }

    @ApiOperation("以excel的形式导出设备数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId",value = "设备Id",example = "3",required = true, dataType = "int", paramType="query"),
            @ApiImplicitParam(name = "isNeedBarCode",value = "是否需要被测试条码",example = "true",required = true, dataType = "Boolean", paramType="query"),
            @ApiImplicitParam(name = "isNeedBeatOutput",value = "是否需要节拍输出",example = "true",required = true, dataType = "Boolean", paramType="query"),
            @ApiImplicitParam(name = "exportType",value = "导出类型",example = "true",required = true, dataType = "string", paramType="query")
    })
    @GetMapping("/exportDeviceData")
    public Result exportDeviceData(@RequestParam(name = "deviceId") Integer deviceId,
                                   @RequestParam(name = "isNeedBarCode")Boolean isNeedBarCode,
                                   @RequestParam(name = "isNeedBeatOutput") Boolean isNeedBeatOutput,
                                   @RequestParam(name = "exportType") String exportType,
                                   HttpServletResponse response) throws IllegalAccessException, IOException {
        deviceDataService.exportDeviceData(response, deviceId, isNeedBarCode, isNeedBeatOutput, exportType);

        return null;
    }


    /**
     * 导入数据
     * @param uploadExcel
     * @param deviceId
     * @return
     */
    @ApiOperation("为设备导入数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId",value = "设备Id",example = "3",required = true, dataType = "int", paramType="body"),
            @ApiImplicitParam(name = "uploadExcel",value = "excel文件",required = true, dataType = "MultipartFile", paramType="body")
    })
    @PostMapping("importDeviceData")
    public Result importDeviceData(MultipartFile uploadExcel, Integer deviceId) {
        deviceDataService.importDeviceData(uploadExcel ,deviceId);
        return ResultUtil.success();
    }


    @ApiOperation("删除指定设备数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataId",value = "设备数据Id",example = "3",required = true, dataType = "int", paramType="body")
    })
    @DeleteMapping("deleteDeviceData")
    public Result deleteDeviceData(@RequestBody Map<String,Object> parameters){
        Integer dataId = (Integer) parameters.get("dataId");
        int effectRow = deviceDataService.deleteByPrimaryKey(dataId);
        if (effectRow > 0){
            return ResultUtil.success();
        }else {
            return ResultUtil.error(ResultCode.DELETE_FAIL);
        }
    }

    @ApiOperation(value = "为用户添加设备数据",notes = "这个接口不需要传入dataId")
    @PostMapping("addDeviceData")
    public Result addDeviceData(@RequestBody @Valid DeviceDataParam deviceDataParam, BindingResult bindingResult){
        ValidateUtil.validateParam(bindingResult);
        DeviceData deviceData = new DeviceData();
        BeanUtils.copyProperties(deviceDataParam, deviceData);
        Time time = DateTransformUtil.stringToTime(deviceDataParam.getTime());
        deviceData.setTime(time);
        Date date = DateTransformUtil.stringToDate(deviceDataParam.getDate());
        deviceData.setDate(date);
        deviceData.setDataId(null);
        int effectRow = deviceDataService.insertSelective(deviceData);
        if (effectRow > 0){
            return ResultUtil.success();
        }else {
            return ResultUtil.error(ResultCode.INSERT_FAIL);
        }
    }

    @ApiOperation("更改设备数据")
    @PostMapping("updateDeviceData")
    public Result updateDeviceData(@RequestBody @Valid DeviceDataParam deviceDataParam, BindingResult bindingResult){
        ValidateUtil.validateParam(bindingResult);
        DeviceData deviceData = new DeviceData();
        BeanUtils.copyProperties(deviceDataParam, deviceData);
        Time time = DateTransformUtil.stringToTime(deviceDataParam.getTime());
        deviceData.setTime(time);
        Date date = DateTransformUtil.stringToDate(deviceDataParam.getDate());
        deviceData.setDate(date);
        int effectRow = deviceDataService.updateByPrimaryKeySelective(deviceData);
        if (effectRow > 0){
            return ResultUtil.success();
        }else {
            return ResultUtil.error(ResultCode.UPDATE_FAIL);
        }
    }


}
