package cn.cat.platform.utils;

/**
 * @author linlt
 * @createTime 2020/8/10 20:10
 * @description 进制转换工具类
 */
public class ByteUtil {

    /**
     * byte数组转 hex 字符串
     *
     * @param bytes 待转换的byte数组
     * @return 转换后的Hex字符串
     */
    public static String byteArrayToHex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(aByte & 0xFF);
            if (hex.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hex);
        }
        return stringBuilder.toString();
    }

    /**
     * Hex字符串转byte
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte
     */
    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    /**
     * hex字符串转 byte 数组
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte数组
     */
    public static byte[] hexToByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            //奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            //偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }
}
