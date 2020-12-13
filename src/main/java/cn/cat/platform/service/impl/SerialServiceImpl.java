package cn.cat.platform.service.impl;

import cn.cat.platform.enums.ResultCode;
import cn.cat.platform.exception.BusinessException;
import cn.cat.platform.service.SerialPortService;
import cn.cat.platform.test.SerialPortManager;
import cn.cat.platform.utils.ByteUtil;
import gnu.io.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author linlt
 * @createTime 2020/8/11 18:54
 * @description TODO
 */
@Service
public class SerialServiceImpl implements SerialPortService {

    private final Map<String, SerialPort> clients = new ConcurrentHashMap<>(512);

    @Override
    public List<String> getSerialPortNameList() {
        // 获得当前所有可用串口
        Enumeration<CommPortIdentifier> serialPortList = CommPortIdentifier.getPortIdentifiers();
        List<String> serialPortNameList = new ArrayList<>();
        // 将可用串口名添加到List并返回该List
        while (serialPortList.hasMoreElements()) {
            String serialPortName = serialPortList.nextElement().getName();
            serialPortNameList.add(serialPortName);
        }
        return serialPortNameList;
    }

    @Override
    public void connectSerialPort(String remoteAddr, String serialPortName,
                                  int baudrate, int dataBits, int stopBits, int parity) {
        SerialPort serialPort = null;
        try {
            // 通过端口名识别端口
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(serialPortName);
            // 打开端口，并给端口名字和一个timeout（打开操作的超时时间）
            CommPort commPort = portIdentifier.open(serialPortName, 2000);
            // 判断是不是串口
            if (commPort instanceof SerialPort) {
                serialPort = (SerialPort) commPort;
                // 设置一下串口的波特率等参数
                // 数据位：7/8
                // 停止位：1/2
                // 校验位：None/偶校验/奇校验/Mark/Space
                serialPort.setSerialPortParams(baudrate, dataBits, stopBits, parity);
            }
        } catch (NoSuchPortException | PortInUseException | UnsupportedCommOperationException e) {
            e.printStackTrace();
        }
        clients.put(remoteAddr, serialPort);
    }


    @Override
    public void sendToSerialPort(String remoteAddr, byte[] processedSendMessage) {
        SerialPort serialPort = clients.get(remoteAddr);
        if (serialPort == null) {
            //请先连接串口
            throw new BusinessException(ResultCode.PLEASE_CONNECT_SERIAL_PORT);
        }
        try(OutputStream os = serialPort.getOutputStream()){
            os.write(processedSendMessage);
            os.flush();
        }catch (IOException e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
