package cn.cat.platform.controller;

import cn.cat.platform.common.Result;
import cn.cat.platform.enums.ResultCode;
import cn.cat.platform.model.param.DeviceParam;
import cn.cat.platform.model.DO.Device;
import cn.cat.platform.service.DeviceService;
import cn.cat.platform.utils.ResultUtil;
import cn.cat.platform.utils.ValidateUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author linlt
 * @CreateTime 2020/3/4 11:46
 */
@RestController
@RequestMapping("/device")
@CrossOrigin
@Api(tags = "设备管理相关接口")
public class DeviceController {
    @Autowired
    DeviceService deviceService;

    /**
     * 通过用户id查找设备列表
     *
     * @param pn           页数
     * @param deviceNumber 查询条件：设备编号
     * @param deviceName   查询条件：设备名称
     * @param userId       查询条件：用户id
     * @return
     */
    @ApiOperation("查找用户的设备列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pn",value = "页数",example = "1", required = true, dataType = "int", paramType="query"),
            @ApiImplicitParam(name = "deviceNumber",value = "查询条件：设备编号",example = "317--02013", dataType = "string", paramType="query"),
            @ApiImplicitParam(name = "deviceName",value = "查询条件：设备名称",example = "T80-L-M-S1", dataType = "string", paramType="query"),
            @ApiImplicitParam(name = "userId",value = "查询条件：用户id",example = "2", required = true, dataType = "int", paramType="query")
    })
    @GetMapping("/findDeviceList")
    public Result findDeviceList(@RequestParam(name = "pn", defaultValue = "1") Integer pn,
                                 @RequestParam(name = "deviceNumber") String deviceNumber,
                                 @RequestParam(name = "deviceName") String deviceName,
                                 @RequestParam(name = "userId") Integer userId) {
        PageInfo<Device> pageInfo = deviceService.findDeviceList(pn, deviceNumber, deviceName, userId);
        return ResultUtil.success(pageInfo);
    }

    @ApiOperation("删除指定设备")
    @DeleteMapping("deleteDevice")
    public Result deleteDevice(@RequestBody Device device) {
        int effectRow = deviceService.deleteByPrimaryKey(device.getDeviceId());
        if (effectRow > 0) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultCode.DELETE_FAIL);
        }
    }

    @ApiOperation(value = "为用户添加设备",notes = "这个接口不需要传入deviceId")
    @PostMapping("addDevice")
    public Result addDevice(@RequestBody @Valid DeviceParam deviceParam, BindingResult bindingResult) {
        ValidateUtil.validateParam(bindingResult);
        Device device = new Device();
        BeanUtils.copyProperties(deviceParam, device);
        device.setDeviceId(null);
        int effectRow = deviceService.insertSelective(device);
        if (effectRow > 0) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultCode.INSERT_FAIL);
        }
    }

    @ApiOperation("更新设备信息")
    @PostMapping("updateDevice")
    public Result updateDevice(@RequestBody @Valid DeviceParam deviceParam, BindingResult bindingResult) {
        ValidateUtil.validateParam(bindingResult);
        Device device = new Device();
        BeanUtils.copyProperties(deviceParam, device);
        int effectRow = deviceService.updateByPrimaryKeySelective(device);
        if (effectRow > 0) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultCode.UPDATE_FAIL);
        }
    }

}
