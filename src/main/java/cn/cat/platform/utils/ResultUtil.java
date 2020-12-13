package cn.cat.platform.utils;

import cn.cat.platform.common.Result;
import cn.cat.platform.enums.ResultCode;
import cn.cat.platform.exception.BusinessException;

/**
 * @author linlt
 * @createTime 2020/3/30 23:34
 */
public class ResultUtil {

    public static Result success(Object data, String message){
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.getCode());
        if (message != null){
            result.setMessage(message);
        }else {
            result.setMessage(ResultCode.SUCCESS.getMessage());
        }
        result.setData(data);
        return result;
    }

    public static Result success(Object data){
        return success(data,null);
    }

    public static Result success(){
        return success(null,null);
    }

    public static Result success(String message){
        return success(null,message);
    }

    public static Result error(ResultCode resultCode){
        return error(resultCode, null);
    }

    public static Result error(ResultCode resultCode, String extraMessage){
        Result result = new Result();
        if (extraMessage != null){
            result.setMessage(resultCode.getMessage() +": "+ extraMessage);
        }else {
            result.setMessage(resultCode.getMessage());
        }
        result.setCode(resultCode.getCode());
        result.setData(null);
        return result;
    }


    public static Result error(BusinessException businessException) {
        Result result = new Result();
        ResultCode resultCode = businessException.getResultCode();
        String extraMessage = businessException.getExtraMessage();
        if (extraMessage != null){
            result.setMessage(resultCode.getMessage() +": "+ extraMessage);
        }else {
            result.setMessage(resultCode.getMessage());
        }
        result.setCode(resultCode.getCode());
        return result;
    }

}
