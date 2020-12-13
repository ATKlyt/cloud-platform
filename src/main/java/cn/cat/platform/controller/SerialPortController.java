package cn.cat.platform.controller;

import cn.cat.platform.common.Result;
import cn.cat.platform.enums.ResultCode;
import cn.cat.platform.service.SerialPortService;
import cn.cat.platform.utils.ResultUtil;
import gnu.io.SerialPort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static cn.cat.platform.utils.ByteUtil.hexToByteArray;

/**
 * @author linlt
 * @createTime 2020/8/10 20:03
 * @description TODO
 */
@RestController
@RequestMapping("serialPort")
@CrossOrigin
@Api(tags = "串口通信相关接口")
public class SerialPortController {

    @Autowired
    SerialPortService serialPortService;

    private final static String SEND_METHOD_BYTE_ARRAY = "byteArray";
    private final static String SEND_METHOD_HEX = "hex";


    @ApiOperation("获取串口列表")
    @GetMapping("getSerialPortNameList")
    public Result getSerialPortNameList() {
        List<String> serialPortNameList = serialPortService.getSerialPortNameList();
        return ResultUtil.success(serialPortNameList);
    }

    /**
     * 连接串口
     *
     * @param request
     * @param serialPortName 串口名称
     * @param baudrate 波特率
     * @param dataBits 数据位：7/8
     * @param stopBits 停止位：1/2
     * @param parity 校验位：None/偶校验/奇校验/Mark/Space
     * @return
     */
    @ApiOperation("连接串口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "serialPortName", value = "串口名称", example = "COM1", required = true, dataType = "string", paramType = "query"),
                    @ApiImplicitParam(name = "baudrate", value = "波特率：9600/19200...参考上位机软件", example = "9600", required = true, dataType = "int", paramType = "query"),
                    @ApiImplicitParam(name = "dataBits", value = "数据位：7/8（7的话发送7，8的话发送8）", example = "8", required = true, dataType = "int", paramType = "query"),
                    @ApiImplicitParam(name = "stopBits", value = "停止位：1/2（1的话发送1，2的话发送2）", example = "1", required = true, dataType = "int", paramType = "query"),
                    @ApiImplicitParam(name = "parity", value = "校验位：None/偶校验/奇校验/Mark/Space（对应发送0,1,2,3,4）", example = "0", required = true, dataType = "int", paramType = "query")
            }
    )
    @GetMapping("connectSerialPort")
    public Result connectSerialPort(HttpServletRequest request, String serialPortName,
                                    int baudrate, int dataBits, int stopBits, int parity) {
        /*
            public static final int DATABITS_7 = 7;
            public static final int DATABITS_8 = 8;
            public static final int PARITY_NONE = 0;
            public static final int PARITY_ODD = 1;
            public static final int PARITY_EVEN = 2;
            public static final int PARITY_MARK = 3;
            public static final int PARITY_SPACE = 4;
            public static final int STOPBITS_1 = 1;
            public static final int STOPBITS_2 = 2;
         */
        // 请求方的ip地址
        String remoteAddr = request.getRemoteAddr();
        serialPortService.connectSerialPort(remoteAddr, serialPortName, baudrate, dataBits, stopBits, parity);
        return ResultUtil.success();
    }

    @ApiOperation("往串口发送信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "sendMethod", value = "发送方式，有两种发送方式：byteArray（以字符串的形式发送数据）和hex（以十六进制的形式发送数据）", example = "byteArray", required = true, dataType = "string", paramType = "query"),
                    @ApiImplicitParam(name = "sendMessage", value = "需要发送的信息", example = "AA", required = true, dataType = "string", paramType = "query")
            }
    )
    @GetMapping("sendToSerialPort")
    public Result sendToSerialPort(HttpServletRequest request, String sendMethod, String sendMessage) {
        String remoteAddr = request.getRemoteAddr();
        if (sendMessage == null || "".equals(sendMessage)) {
            return ResultUtil.error(ResultCode.PARAM_IS_NULL, "请输入想要发送的数据");
        }

        if (SEND_METHOD_BYTE_ARRAY.equals(sendMethod)) {
            // 以字符串的形式发送数据
            serialPortService.sendToSerialPort(remoteAddr, sendMessage.getBytes());
        } else if (SEND_METHOD_HEX.equals(sendMethod)) {
            // 以十六进制的形式发送数据
            serialPortService.sendToSerialPort(remoteAddr, hexToByteArray(sendMessage));
        } else {
            return ResultUtil.error(ResultCode.UNKNOWN_SEND_METHOD);
        }
        return ResultUtil.success();
    }


}
