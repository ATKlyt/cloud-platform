package cn.cat.platform.utils;

import cn.cat.platform.enums.ResultCode;
import cn.cat.platform.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author linlt
 * @createTime 2020/6/3 15:01
 * @description TODO
 */
@Slf4j
public class DateTransformUtil {
    public static Time stringToTime(String timeStr) {
        Time time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            time = new Time(sdf.parse(timeStr).getTime());
        } catch (ParseException e) {
            log.error("转换异常：时间转换异常", e);
            throw new BusinessException(ResultCode.PARSE_TIME_ERROR);
        }
        return time;
    }

    public static Date stringToDate(String dateStr){
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = new Date(sdf.parse(dateStr).getTime());
        } catch (ParseException e) {
            log.error("转换异常：时间转换异常", e);
            throw new BusinessException(ResultCode.PARSE_DATA_ERROR);
        }
        return date;
    }


    public static Date stringToDateNetty(String dateStr){
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = new Date(sdf.parse(dateStr).getTime());
        } catch (ParseException e) {
            log.error("转换异常：时间转换异常", e);
            throw new BusinessException(ResultCode.PARSE_DATA_ERROR);
        }
        return date;
    }
}
