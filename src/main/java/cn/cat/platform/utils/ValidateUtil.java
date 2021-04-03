package cn.cat.platform.utils;

import cn.cat.platform.common.Constant;
import cn.cat.platform.common.DeviceConstant;
import cn.cat.platform.enums.ResultCode;
import cn.cat.platform.exception.BusinessException;
import cn.cat.platform.model.DO.DeviceData;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

/**
 * @author linlt
 * @createTime 2020/3/31 13:46
 */
public class ValidateUtil {

    public static boolean validateParams(Object... params) {
        for (Object param : params) {
            if (param == null || "".equals(param)) {
                // 空白的
                throw new BusinessException(ResultCode.PARAM_IS_BLANK);
            }
        }
        return true;
    }

    /**
     * 参数校验
     */
    public static boolean validate(Object... param){
        for (Object obj : param) {
            //字符串参数校验
            if (obj instanceof String){
                //等于null，去除首位空格符号
                if ("".equals(((String) obj).trim())){
                    return false;
                }
            }else if(obj instanceof List){
                if (((List) obj).size() == 0){
                    return false;
                }
            }else if(obj instanceof MultipartFile){
                if (((MultipartFile) obj).isEmpty()){
                    return false;
                }
            }else {
                if (obj == null){
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean validateDeviceData(DeviceData deviceData) {
        String testMode = deviceData.getTestMode();
        if (deviceData.getDataItem1() == null || deviceData.getDataItem1Unit() == null) {
            return false;
        }
        if (DeviceConstant.TEST_TYPE_PLR.equals(testMode)) {
            //"压降", "压降单位", "泄漏率", "泄漏率单位", "测试压", "测试压单位"
            return deviceData.getDataItem2() != null && deviceData.getDataItem2Unit() != null
                    && deviceData.getDataItem3() != null && deviceData.getDataItem3Unit() != null;
        } else if (DeviceConstant.TEST_TYPE_PDL.equals(testMode)) {
            //"测试压", "测试压单位", "压降", "压降单位"
            return deviceData.getDataItem2() != null && deviceData.getDataItem2Unit() != null
                    && deviceData.getDataItem3() == null && deviceData.getDataItem3Unit() == null;
        } else if (DeviceConstant.TEST_TYPE_CPDL.equals(testMode)) {
            //"压降", "压降单位", "补偿后压降", "补偿后压降单位", "测试压", "测试压单位"
            return deviceData.getDataItem2() != null && deviceData.getDataItem2Unit() != null
                    && deviceData.getDataItem3() != null && deviceData.getDataItem3Unit() != null;
        } else if (DeviceConstant.TEST_TYPE_OCC.equals(testMode)) {
            //"测试压", "测试压单位"
            return deviceData.getDataItem2() == null && deviceData.getDataItem2Unit() == null
                    && deviceData.getDataItem3() == null && deviceData.getDataItem3Unit() == null;
        } else if (DeviceConstant.TEST_TYPE_VXXX.equals(testMode)) {
            //"负压模式", "负压模式单位"
            return deviceData.getDataItem2() == null && deviceData.getDataItem2Unit() == null
                    && deviceData.getDataItem3() == null && deviceData.getDataItem3Unit() == null;
        } else if (DeviceConstant.TEST_TYPE_MFR.equals(testMode)) {
            //"流量值", "流量值单位"
            return deviceData.getDataItem2() != null && deviceData.getDataItem2Unit() != null
                    && deviceData.getDataItem3() == null && deviceData.getDataItem3Unit() == null;
        } else {
            throw new BusinessException(ResultCode.TEST_MODE_NOT_EXIST);
        }
    }

    public static void validateParam(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BusinessException(ResultCode.PARAM_IS_NULL, Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
    }

    public static boolean validateResultJudgment(String testMode) {
        return DeviceConstant.RESULT_JUDGMENT_ACCEPT.equals(testMode)
                || DeviceConstant.RESULT_JUDGMENT_REJECT.equals(testMode)
                || DeviceConstant.RESULT_JUDGMENT_TEST_STOP.equals(testMode)
                || DeviceConstant.RESULT_JUDGMENT_HIGH_TEST_P.equals(testMode)
                || DeviceConstant.RESULT_JUDGMENT_LOW_TEST_P.equals(testMode)
                || DeviceConstant.RESULT_JUDGMENT_HIGHER_MAX_P.equals(testMode)
                || DeviceConstant.RESULT_JUDGMENT_LOWER_MIN_P.equals(testMode)
                || DeviceConstant.RESULT_JUDGMENT_SEVERE_LEAK.equals(testMode)
                || DeviceConstant.RESULT_JUDGMENT_TEST_ERROR.equals(testMode)
                || DeviceConstant.RESULT_JUDGMENT_HIGH_P_RANGE.equals(testMode)
                || DeviceConstant.RESULT_JUDGMENT_TEST_TYPE_ERR.equals(testMode);
    }
}
