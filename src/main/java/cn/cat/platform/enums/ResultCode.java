package cn.cat.platform.enums;

import cn.cat.platform.common.Constant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author linlt
 * @CreateTime 2020/3/4 9:31
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    /**
     * 结果枚举类，统一管理
     */
    SUCCESS(0, "成功"),
    UNKNOWN_ERROR(-1, "未知错误"),
    DELETE_FAIL(10001, "删除失败"),
    UPDATE_FAIL(10002, "更新失败"),
    INSERT_FAIL(10003, "保存失败"),
    UPLOAD_FAIL(10004, "文件上传失败"),
    NOT_EXCEL(10005, "无法导入非表格格式文件"),
    EXCEL_HEADER_IS_INVALID(10006, "表头不符合规范"),
    FILE_READ_FAIL(10007, "文件读取失败"),
    EXCEL_DATA_ERROR(10008, "EXCEL数据错误"),
    NOT_FOUND(10009, "未发现相关数据"),
    LOGIN_FAIL(10010, "用户名或密码错误"),
    EXPORT_FAIL(10011, "EXCEL导出失败"),
    DEVICE_DATA_IS_INVALID(10012, "设备数据与测试模式不匹配"),
    TEST_MODE_NOT_EXIST(10013, "测试模式不存在"),
    PARAM_IS_NULL(10014, "参数为空"),
    USER_NOT_LOGIN(10015, "用户未登录"),
    USER_ACCOUNT_EXPIRED(10016, "账号过期"),
    USER_CREDENTIALS_ERROR(10017, "密码错误"),
    USER_CREDENTIALS_EXPIRED(10018, "密码过期"),
    USER_ACCOUNT_DISABLE(10019, "账号不可用"),
    USER_ACCOUNT_LOCKED(10020, "账号锁定"),
    USER_ACCOUNT_NOT_EXIST(10021, "用户不存在"),
    COMMON_FAIL(10022, "登录发生错误"),
    USER_ACCOUNT_USE_BY_OTHERS(10023, "您的账号在异地登录,可能由于密码泄露，建议修改密码"),
    PERMISSION_DENIED(10024, "权限不足"),
    TOKEN_INVALID(10025, "token无效"),
    LOGIN_USERNAME_IS_NULL(10026, "用户名为空"),
    USERNAME_IS_EXIST(10027, "登录账号已存在"),
    PARSE_TIME_ERROR(10028, "转换异常：时间格式不正确"),
    PARSE_DATA_ERROR(10029, "转换异常：日期格式不正确"),
    SERIAL_PORT_IS_NULL(10030, "串口对象为空，监听失败"),
    PLEASE_CONNECT_SERIAL_PORT(10031, "尚未连接串口，请先连接串口"),
    UNKNOWN_SEND_METHOD(10032, "未知的串口发送方式"),
    TWO_PASSWORDS_INCONSISTENT(10033, "两次密码不一致"),
    PARAM_IS_BLANK(10034, "参数为空字符串或为空"), OLD_PASSWORD_IS_WRONG(10035, "旧密码错误"), UNLAWFUL_TEST_MODE(10036, "非法的结果判断");

    private Integer code;
    private String message;
}
