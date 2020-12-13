package cn.cat.platform.service;

import gnu.io.SerialPort;

import java.util.List;

/**
 * @author linlt
 * @createTime 2020/8/11 18:53
 * @description TODO
 */
public interface SerialPortService {

    List<String> getSerialPortNameList();

    void connectSerialPort(String remoteAddr, String serialPortName, int baudrate, int dataBits, int stopBits, int parity);

    void sendToSerialPort(String remoteAddr, byte[] processedSendMessage);
}
