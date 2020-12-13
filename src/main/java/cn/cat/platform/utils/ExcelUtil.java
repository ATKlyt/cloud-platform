package cn.cat.platform.utils;

import cn.cat.platform.common.DeviceConstant;
import cn.cat.platform.exception.BusinessException;
import cn.cat.platform.model.DO.DeviceData;
import cn.cat.platform.enums.ResultCode;
import cn.cat.platform.model.VO.Header;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author linlt
 * @createTime 2020/3/7 20:28
 */
public class ExcelUtil {

    private static final String EXCEL_XLS = ".xls";
    private static final String EXCEL_XLSX = ".xlsx";
    private static final String EXCEL_CSV = ".csv";

    /**
     * 格式化时间
     *
     * @param workbook
     * @param cell
     */
    public static void timeFormat(Workbook workbook, Cell cell) {
        //创建样式
        CellStyle cellStyle = workbook.createCellStyle();
        //格式化
        DataFormat format = workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("HH:mm:ss"));
        //水平向左对齐
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        //应用样式
        cell.setCellStyle(cellStyle);
    }

    /**
     * 格式化日期
     *
     * @param workbook
     * @param cell
     */
    public static void dateFormat(Workbook workbook, Cell cell) {
        //创建样式
        CellStyle cellStyle = workbook.createCellStyle();
        //格式化
        DataFormat format = workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("yyyy-MM-dd"));
        //水平向左对齐
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        //应用样式
        cell.setCellStyle(cellStyle);
    }

    /**
     * 格式化日期
     *
     * @param workbook
     * @param cell
     */
    public static void numberFormat(Workbook workbook, Cell cell) {
        //创建样式
        CellStyle cellStyle = workbook.createCellStyle();
        //格式化
        DataFormat format = workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("#.######"));
        //水平向左对齐
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        //应用样式
        cell.setCellStyle(cellStyle);
    }

    /**
     * 导出Excel文件
     *
     * @param exportParameters 导出参数
     * @return
     * @throws IllegalAccessException
     */
    @SuppressWarnings("unchecked")
    public static Workbook exportExcel(Map<String, Object> exportParameters) throws IllegalAccessException {
        List<Header> headers = (List<Header>) exportParameters.get("headers");
        List<DeviceData> deviceData = (List<DeviceData>) exportParameters.get("deviceData");
        Boolean isNeedBarCode = (Boolean) exportParameters.get("isNeedBarCode");
        Boolean isNeedBeatOutput = (Boolean) exportParameters.get("isNeedBeatOutput");
        String deviceName = (String) exportParameters.get("deviceName");
        String deviceNumber = (String) exportParameters.get("deviceNumber");
        //创建excel对象
        Workbook workbook = new HSSFWorkbook();
        //在excel创建sheet页
        Sheet sheet = workbook.createSheet(deviceName + "数据");
        //表头
        Row headerRow = sheet.createRow(0);
        //列数
        int columns = headers.size();
        //第一行第一列留空，填入excel表头名称
        for (int columnIndex = 1; columnIndex <= columns; columnIndex++) {
            Header header = headers.get(columnIndex - 1);
            headerRow.createCell(columnIndex).setCellValue(header.getLabel());
        }
        //获取类对象
        Class<DeviceData> clazz = DeviceData.class;
        //获取所有成员变量
        Field[] declaredFields = clazz.getDeclaredFields();
        //行索引
        int rowIndex = 1;
        for (DeviceData deviceDatum : deviceData) {
            //处理关键数据
            deviceDatum.setDataId(null);
            deviceDatum.setDeviceId(null);
            //处理可选数据
            if (!isNeedBarCode) {
                deviceDatum.setTestedBarCode(null);
            }
            if (!isNeedBeatOutput) {
                deviceDatum.setBeatOutput(null);
            }
            Row row = sheet.createRow(rowIndex);
            //处理每一行的第一二列
            row.createCell(0).setCellValue("testResult:");
            row.createCell(1).setCellValue(deviceNumber);
            //需要填入数据的列的索引
            int columnIndex = 2;
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                //获取属性名称
                String fieldName = declaredField.getName();
                //获取属性值
                Object value = declaredField.get(deviceDatum);
                Cell cell = row.createCell(columnIndex);
                // 某些测试模式下的设备数据没有某些成员变量
                switch (fieldName) {
                    case "dataId":
                    case "deviceId":
                    case "serialVersionUID":
                        continue;
                    case "date":
                        cell.setCellValue((Date) value);
                        dateFormat(workbook, cell);
                        break;
                    case "time":
                        cell.setCellValue((Time) value);
                        timeFormat(workbook, cell);
                        break;
                    case "dataItem1":
                    case "dataItem2":
                    case "dataItem3":
                        if (value != null) {
                            cell.setCellValue(((BigDecimal) value).doubleValue());
                            numberFormat(workbook, cell);
                        } else {
                            cell.setCellValue("--");
                        }
                        break;
                    case "dataItem1Unit":
                    case "dataItem2Unit":
                    case "dataItem3Unit":
                        if (value != null) {
                            cell.setCellValue(value.toString());
                        } else {
                            cell.setCellValue("--");
                        }
                        break;
                    case "testedBarCode":
                        if (isNeedBarCode) {
                            if (value != null) {
                                cell.setCellValue(value.toString());
                            } else {
                                cell.setCellValue("--");
                            }
                        } else {
                            columnIndex--;
                        }
                        break;
                    case "beatOutput":
                        if (isNeedBeatOutput) {
                            if (value != null) {
                                cell.setCellValue(value.toString());
                            } else {
                                cell.setCellValue("--");
                            }
                        } else {
                            columnIndex--;
                        }
                        break;
                    default:
                        //填入数据
                        if (value != null) {
                            cell.setCellValue(value.toString());
                        }
                        break;
                }

                columnIndex++;
            }
            //换行
            rowIndex++;
        }
        //必须在单元格设值以后进行，设置为根据内容自动调整列宽
        for (int i = 0; i < headers.size(); i++) {
            sheet.autoSizeColumn(i);
        }
        //处理中文不能自动调整列宽的问题
        setSizeColumn(sheet, headers.size());
        return workbook;
    }

    @SuppressWarnings("unchecked")
    public static File exportCsv(Map<String, Object> exportParameters) throws IOException, IllegalAccessException {
        String outPutPath = "cloud-platform/temp/";
        String fileName = "tempCsv" + System.nanoTime();
        List<Header> headers = (List<Header>) exportParameters.get("headers");
        List<DeviceData> deviceData = (List<DeviceData>) exportParameters.get("deviceData");
        Boolean isNeedBarCode = (Boolean) exportParameters.get("isNeedBarCode");
        Boolean isNeedBeatOutput = (Boolean) exportParameters.get("isNeedBeatOutput");
        String deviceName = (String) exportParameters.get("deviceName");
        String deviceNumber = (String) exportParameters.get("deviceNumber");
        File file = new File(outPutPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        //定义文件名格式并创建
        File csvFile = new File(outPutPath + fileName);
        // UTF-8使正确读取分隔符","
        BufferedWriter csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                csvFile), StandardCharsets.UTF_8), 1024);
        // 写入文件头部
        for (Header header : headers) {
            csvFileOutputStream.write(header.getLabel());
            csvFileOutputStream.write(",");
        }
        //换行
        csvFileOutputStream.newLine();
        List<String[]> orderlyFieldsValueList = getOrderlyFieldsValueList(headers, deviceData);
        for (String[] orderlyFieldsValue : orderlyFieldsValueList) {
            orderlyFieldsValue[0] = deviceNumber;
            for (String orderlyFieldValue : orderlyFieldsValue) {
                csvFileOutputStream.write(orderlyFieldValue);
                csvFileOutputStream.write(",");
            }
            csvFileOutputStream.newLine();
        }
        csvFileOutputStream.flush();
        return csvFile;
    }

    private static List<String[]> getOrderlyFieldsValueList(List<Header> headers, List<DeviceData> deviceData) throws IllegalAccessException {
        List<String[]> orderlyFieldsValueList = new ArrayList<>(deviceData.size());
        Map<String, Integer> fieldNameIndexMap = new HashMap<>(headers.size());
        for (int i = 0; i < headers.size(); i++) {
            fieldNameIndexMap.put(headers.get(i).getProp(), i);
        }
        Class<DeviceData> deviceDataClass = DeviceData.class;
        Field[] deviceDataFields = deviceDataClass.getDeclaredFields();
        for (DeviceData deviceDatum : deviceData) {
            String[] orderlyFieldsValue = new String[headers.size()];
            for (Field deviceDataField : deviceDataFields) {
                deviceDataField.setAccessible(true);
                String fieldName = deviceDataField.getName();
                Integer index = fieldNameIndexMap.get(fieldName);
                if (index != null) {
                    Object fieldValue = deviceDataField.get(deviceDatum);
                    if (fieldValue == null){
                        orderlyFieldsValue[index] = "--";
                    }else {
                        orderlyFieldsValue[index] = fieldValue.toString();
                    }
                }
            }
            orderlyFieldsValueList.add(orderlyFieldsValue);
        }
        return orderlyFieldsValueList;
    }

    /**
     * 自适应宽度(中文支持)
     *
     * @param sheet
     * @param size
     */
    public static void setSizeColumn(Sheet sheet, int size) {
        for (int columnNum = 0; columnNum < size; columnNum++) {
            //int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            int columnWidth = sheet.getColumnWidth(columnNum) / 200;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                Row currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }
                if (currentRow.getCell(columnNum) != null) {
                    Cell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellType() == CellType.STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            sheet.setColumnWidth(columnNum, columnWidth * 256);
        }
    }


    /**
     * 导入Excel文件
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static Map<String, Object> getDataFromFile(String url) throws IOException {
        Map<String, Object> data;
        //获取表格文件
        File formFile = new File(url);
        checkExcelOrCsv(formFile);
        if (isExcel(formFile)) {
            data = resolveExcel(formFile);
        } else {
            data = resolveCsv(formFile);
        }
        return data;
    }

    private static Map<String, Object> resolveCsv(File csvFile) {
        Map<String, Object> data = new HashMap<>(2);
        List<Map<String, Integer>> headerIndexMapList = new ArrayList<>();
        List<List<List<Object>>> dataList = new ArrayList<>();
        List<List<Object>> deviceData = new ArrayList<>(10);
        dataList.add(deviceData);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile))) {
            String headerLine = bufferedReader.readLine();
            Map<String, Integer> headerIndexMap = analyseHeader(headerLine);
            headerIndexMapList.add(headerIndexMap);
            String line = null;
            String[] items = null;
            while ((line = bufferedReader.readLine()) != null) {
                //数据行
                items = line.split(",", Integer.MAX_VALUE);
                deviceData.add(Arrays.asList(items));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        data.put("dataList", dataList);
        data.put("headers", headerIndexMapList);
        return data;
    }

    private static Map<String, Object> resolveExcel(File excelFile) throws IOException {
        Map<String, Object> data = new HashMap<>(2);
        //每一个sheet对应的表头
        List<Map<String, Integer>> headerIndexMapList = new ArrayList<>();
        FileInputStream fis = new FileInputStream(excelFile);
        String fileName = excelFile.getName();
        Workbook workbook = getExcelVersion(fis, fileName);
        int sheetNum = workbook.getNumberOfSheets();
        //创建三维数组保存所有读取到的行列数据，外层储存sheet也数据，中间层存行数据，内层存单元格数据
        List<List<List<Object>>> dataList = new ArrayList<>();
        //遍历excel中的sheet,循环所有sheet表
        for (int index = 0; index < sheetNum; index++) {
            Sheet sheet = workbook.getSheetAt(index);
            if (sheet == null) {
                continue;
            }
            //处理表头
            Row headerRow = sheet.getRow(0);
            Map<String, Integer> headerIndexMap = analyseHeader(headerRow);
            headerIndexMapList.add(headerIndexMap);
            List<List<Object>> sheetData = new ArrayList<>();
            //循环sheet表中所有非表头行
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                //如果当前行没有数据则跳过
                if (row == null) {
                    continue;
                }
                //遍历每一行的每一列，循环行中所有单元格
                List<Object> rowData = new ArrayList<>();
                for (int cellIndex = 1; cellIndex < row.getLastCellNum(); cellIndex++) {
                    Cell cell = row.getCell(cellIndex);
                    Object cellValue = getCellValue(cell);
                    if ("--".equals(cellValue)) {
                        rowData.add(null);
                    } else {
                        rowData.add(cellValue);
                    }
                }
                sheetData.add(rowData);
            }
            dataList.add(sheetData);
        }
        fis.close();
        data.put("dataList", dataList);
        data.put("headers", headerIndexMapList);
        return data;
    }

    /**
     * 获取单元格里的值
     *
     * @param cell
     * @return
     */
    public static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellType();
        Object cellValue = "";
        if (cell == null || "".equals(cell.toString().trim())) {
            return null;
        }
        if (cellType == CellType.STRING) {
            cellValue = cell.getStringCellValue();
        } else if (cellType == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                cellValue = cell.getDateCellValue().getTime();
            } else {
                cellValue = new DecimalFormat("#.######").format(cell.getNumericCellValue());
            }
        }
        return cellValue;
    }

    /**
     * 解析表头
     *
     * @param headerRow
     * @return 返回为表头对应变量和列索引集合
     */
    public static Map<String, Integer> analyseHeader(Row headerRow) {
        Map<String, Integer> headerIndexMap = new HashMap<>(16);
        for (int cellIndex = 1; cellIndex < headerRow.getLastCellNum(); cellIndex++) {
            Cell cell = headerRow.getCell(cellIndex);
            String headerLabel = (String) getCellValue(cell);
            String headerProp = doAnalyseHeader(headerLabel);
            headerIndexMap.put(headerProp, cellIndex - 1);
        }
        return headerIndexMap;
    }

    /**
     * 解析表头
     *
     * @param headerLine
     * @return 返回为表头对应变量和列索引集合
     */
    public static Map<String, Integer> analyseHeader(String headerLine) {
        List<String> headerLabelList = Arrays.asList(headerLine.split(","));
        Map<String, Integer> headerIndexMap = new HashMap<>(16);
        int index = 0;
        for (String headerLabel : headerLabelList) {
            String headerProp = doAnalyseHeader(headerLabel);
            headerIndexMap.put(headerProp, index++);
        }
        return headerIndexMap;
    }

    /**
     * 获取表头及其列索引
     *
     * @param headerName
     * @return
     */
    public static String doAnalyseHeader(String headerName) {
        String errorHeader = null;
        String attributeName = null;
        switch (headerName) {
            case "全球唯一ID":
                attributeName = "";
                break;
            case DeviceConstant.ATTRIBUTE_GROUP_ID_CN:
                attributeName = DeviceConstant.ATTRIBUTE_GROUP_ID_EN;
                break;
            case DeviceConstant.ATTRIBUTE_DATE_CN:
                attributeName = DeviceConstant.ATTRIBUTE_DATE_EN;
                break;
            case DeviceConstant.ATTRIBUTE_TIME_CN:
                attributeName = DeviceConstant.ATTRIBUTE_TIME_EN;
                break;
            case DeviceConstant.ATTRIBUTE_RESULT_JUDGMENT_CN:
                attributeName = DeviceConstant.ATTRIBUTE_RESULT_JUDGMENT_EN;
                break;
            case DeviceConstant.ATTRIBUTE_TEST_MODE_CN:
                attributeName = DeviceConstant.ATTRIBUTE_TEST_MODE_EN;
                break;
            case DeviceConstant.ATTRIBUTE_DATA_ITEM_1_CN:
                attributeName = DeviceConstant.ATTRIBUTE_DATA_ITEM_1_EN;
                break;
            case DeviceConstant.ATTRIBUTE_DATA_ITEM_1_UNIT_CN:
                attributeName = DeviceConstant.ATTRIBUTE_DATA_ITEM_1_UNIT_EN;
                break;
            case DeviceConstant.ATTRIBUTE_DATA_ITEM_2_CN:
                attributeName = DeviceConstant.ATTRIBUTE_DATA_ITEM_2_EN;
                break;
            case DeviceConstant.ATTRIBUTE_DATA_ITEM_2_UNIT_CN:
                attributeName = DeviceConstant.ATTRIBUTE_DATA_ITEM_2_UNIT_EN;
                break;
            case DeviceConstant.ATTRIBUTE_DATA_ITEM_3_CN:
                attributeName = DeviceConstant.ATTRIBUTE_DATA_ITEM_3_EN;
                break;
            case DeviceConstant.ATTRIBUTE_DATA_ITEM_3_UNIT_CN:
                attributeName = DeviceConstant.ATTRIBUTE_DATA_ITEM_3_UNIT_EN;
                break;
            case DeviceConstant.ATTRIBUTE_RUN_NUMBER_CN:
                attributeName = DeviceConstant.ATTRIBUTE_RUN_NUMBER_EN;
                break;
            case DeviceConstant.ATTRIBUTE_TESTED_BAR_CODE_CN:
                attributeName = DeviceConstant.ATTRIBUTE_TESTED_BAR_CODE_EN;
                break;
            case DeviceConstant.ATTRIBUTE_BEAT_OUTPUT_CN:
                attributeName = DeviceConstant.ATTRIBUTE_BEAT_OUTPUT_EN;
                break;
            default:
                errorHeader = headerName;
        }
        if (errorHeader != null) {
            //表头不规范
            throw new BusinessException(ResultCode.EXCEL_HEADER_IS_INVALID, errorHeader);
        }
        return attributeName;
    }

    /**
     * 获取Excel文件版本
     *
     * @param fis
     * @param fileName
     * @return
     * @throws IOException
     */
    public static Workbook getExcelVersion(FileInputStream fis, String fileName) throws IOException {
        Workbook workbook = null;
        if (fileName.endsWith(EXCEL_XLS)) {
            workbook = new HSSFWorkbook(fis);
        } else if (fileName.endsWith(EXCEL_XLSX)) {
            workbook = new XSSFWorkbook(fis);
        }
        return workbook;
    }

    /**
     * 判断是否为Excel文件
     *
     * @param file
     * @return
     */
    public static void checkExcelOrCsv(File file) {
        if (!file.isFile() || !(file.getName().endsWith(EXCEL_CSV) || file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX))) {
            //文件不是excel文件
            throw new BusinessException(ResultCode.NOT_EXCEL);
        }
    }

    /**
     * 判断是否为Excel文件
     *
     * @param file
     * @return
     */
    public static boolean isExcel(File file) {
        return file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX);
    }

    /**
     * 判断是否为csv文件
     *
     * @param file
     * @return
     */
    public static boolean isCsv(File file) {
        return file.getName().endsWith(EXCEL_CSV);
    }


}
