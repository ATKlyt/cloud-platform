package cn.cat.platform.common;

/**
 * @author linlt
 * @createTime 2020/7/14 8:31
 * @description TODO
 */
public class DeviceConstant {
    /**
     * 测试结果
     */
    /**
     * 合格/不合格
     */
    public static final String RESULT_JUDGMENT_ALL = "ALL";
    public static final String RESULT_JUDGMENT_ACCEPT = "Accept";
    public static final String RESULT_JUDGMENT_REJECT = "Reject";
    /**
     * 异常说明
     */
    public static final String RESULT_JUDGMENT_TEST_STOP = "Test_Stop";
    public static final String RESULT_JUDGMENT_HIGH_TEST_P = "High_Test_P";
    public static final String RESULT_JUDGMENT_LOW_TEST_P = "Low_Test_P";
    public static final String RESULT_JUDGMENT_HIGHER_MAX_P = "Higher_Max_P";
    public static final String RESULT_JUDGMENT_LOWER_MIN_P = "Lower_Min_P";
    public static final String RESULT_JUDGMENT_SEVERE_LEAK = "Severe_Leak";
    public static final String RESULT_JUDGMENT_TEST_ERROR = "Test_Error";
    public static final String RESULT_JUDGMENT_HIGH_P_RANGE = "High_P_Range";
    public static final String RESULT_JUDGMENT_TEST_TYPE_ERR = "Test_Type_Err";



    /**
     * 测试模式
     */
    public static final String TEST_TYPE_PLR = "PLR";
    public static final String TEST_TYPE_PDL = "PDL";
    public static final String TEST_TYPE_CPDL = "CPDL";
    public static final String TEST_TYPE_OCC = "OCC";
    public static final String TEST_TYPE_VXXX = "VXXX";
    public static final String TEST_TYPE_MFR = "MFR";
    public static final String TEST_TYPE_ALL = "ALL";
    public static final String TEST_TYPE_DPD = "DPD";
    public static final String TEST_TYPE_DPR = "DPR";
    public static final String TEST_TYPE_VDPD = "VDPD";
    public static final String TEST_TYPE_VDPR = "VDPR";
    public static final String TEST_TYPE_CDPD = "CDPD";
    public static final String TEST_TYPE_CVDPD = "CVDPD";
    public static final String TEST_TYPE_VPLR = "VPLR";
    public static final String TEST_TYPE_VPDL = "VPDL";
    public static final String TEST_TYPE_CVPDL = "CVPDL";
    public static final String TEST_TYPE_VSFD = "VSFD";
    public static final String TEST_TYPE_SPD = "SPD";
    public static final String TEST_TYPE_SPR = "SPR";
    public static final String TEST_TYPE_VSPD= "VSPD";
    public static final String TEST_TYPE_VSPR= "VSPR";


    public static final String ATTRIBUTE_GROUP_ID_EN = "groupId";
    public static final String ATTRIBUTE_GROUP_ID_CN = "组号";
    public static final String ATTRIBUTE_DATE_EN = "date";
    public static final String ATTRIBUTE_DATE_CN = "日期";
    public static final String ATTRIBUTE_TIME_EN = "time";
    public static final String ATTRIBUTE_TIME_CN = "时间";
    public static final String ATTRIBUTE_RESULT_JUDGMENT_EN = "resultJudgment";
    public static final String ATTRIBUTE_RESULT_JUDGMENT_CN = "结果";
    public static final String ATTRIBUTE_TEST_MODE_EN = "testMode";
    public static final String ATTRIBUTE_TEST_MODE_CN = "测试模式";

    public static final String ATTRIBUTE_TEST_PRESSURE_CN = "测试压";
    public static final String ATTRIBUTE_TEST_PRESSURE_UNIT_CN = "测试压单位";
    public static final String ATTRIBUTE_PRESSURE_DROP_CN = "压降";
    public static final String ATTRIBUTE_PRESSURE_DROP_UNIT_CN = "压降单位";
    public static final String ATTRIBUTE_PRESSURE_DROP_AFTER_COMPENSATION_CN = "补偿后压降";
    public static final String ATTRIBUTE_PRESSURE_DROP_AFTER_COMPENSATION_UNIT_CN = "补偿后压降单位";
    public static final String ATTRIBUTE_NEGATIVE_PRESSURE_MODE_CN = "负压模式";
    public static final String ATTRIBUTE_NEGATIVE_PRESSURE_MODE_UNIT_CN = "负压模式单位";
    public static final String ATTRIBUTE_LEAK_RATE_CN = "泄漏率";
    public static final String ATTRIBUTE_LEAK_RATE_UNIT_CN = "泄漏率单位";
    public static final String ATTRIBUTE_FLOW_VALUE_CN = "流量值";
    public static final String ATTRIBUTE_FLOW_VALUE_UNIT_CN = "流量值单位";
    public static final String ATTRIBUTE_SMALL_LEAK_CN = "小漏";
    public static final String ATTRIBUTE_SMALL_LEAK_UNIT_CN = "小漏值单位";
    public static final String ATTRIBUTE_BIG_LEAK_CN = "大漏";
    public static final String ATTRIBUTE_BIG_LEAK_UNIT_CN = "大漏值单位";


    public static final String ATTRIBUTE_TEST_PRESSURE_EN = "testPressure";
    public static final String ATTRIBUTE_TEST_PRESSURE_UNIT_EN = "testPressureUnit";
    public static final String ATTRIBUTE_PRESSURE_DROP_EN = "pressureDrop";
    public static final String ATTRIBUTE_PRESSURE_DROP_UNIT_EN = "pressureDropUnit";
    public static final String ATTRIBUTE_PRESSURE_DROP_AFTER_COMPENSATION_EN = "pressureDropAfterCompensation";
    public static final String ATTRIBUTE_PRESSURE_DROP_AFTER_COMPENSATION_UNIT_EN = "pressureDropAfterCompensationUnit";
    public static final String ATTRIBUTE_NEGATIVE_PRESSURE_MODE_EN = "negativePressureMode";
    public static final String ATTRIBUTE_NEGATIVE_PRESSURE_MODE_UNIT_EN = "negativePressureModeUnit";
    public static final String ATTRIBUTE_LEAK_RATE_EN = "leakRate";
    public static final String ATTRIBUTE_LEAK_RATE_UNIT_EN = "leakRateUnit";
    public static final String ATTRIBUTE_FLOW_VALUE_EN = "flowValue";
    public static final String ATTRIBUTE_FLOW_VALUE_UNIT_EN = "flowValueUnit";
    public static final String ATTRIBUTE_SMALL_LEAK_EN = "smallLeak";
    public static final String ATTRIBUTE_SMALL_LEAK_UNIT_EN = "smallLeakUnit";
    public static final String ATTRIBUTE_BIG_LEAK_EN = "bigLeak";
    public static final String ATTRIBUTE_BIG_LEAK_UNIT_EN = "bigLeakUnit";
    
    public static final String ATTRIBUTE_DATA_ITEM_1_CN = "数据项1";
    public static final String ATTRIBUTE_DATA_ITEM_1_UNIT_CN = "数据项1单位";
    public static final String ATTRIBUTE_DATA_ITEM_2_CN = "数据项2";
    public static final String ATTRIBUTE_DATA_ITEM_2_UNIT_CN = "数据项2单位";
    public static final String ATTRIBUTE_DATA_ITEM_3_CN = "数据项3";
    public static final String ATTRIBUTE_DATA_ITEM_3_UNIT_CN = "数据项3单位";

    public static final String ATTRIBUTE_DATA_ITEM_1_EN = "dataItem1";
    public static final String ATTRIBUTE_DATA_ITEM_1_UNIT_EN = "dataItem1Unit";
    public static final String ATTRIBUTE_DATA_ITEM_2_EN = "dataItem2";
    public static final String ATTRIBUTE_DATA_ITEM_2_UNIT_EN = "dataItem2Unit";
    public static final String ATTRIBUTE_DATA_ITEM_3_EN = "dataItem3";
    public static final String ATTRIBUTE_DATA_ITEM_3_UNIT_EN = "dataItem3Unit";




    public static final String ATTRIBUTE_RUN_NUMBER_EN = "runNumber";
    public static final String ATTRIBUTE_RUN_NUMBER_CN = "运行次数";
    public static final String ATTRIBUTE_TESTED_BAR_CODE_EN = "testedBarCode";
    public static final String ATTRIBUTE_TESTED_BAR_CODE_CN = "被测试条码";
    public static final String ATTRIBUTE_BEAT_OUTPUT_EN = "beatOutput";
    public static final String ATTRIBUTE_BEAT_OUTPUT_CN = "节拍输出";
    public static final String ATTRIBUTE_RESERVERD_MESSAGE_CN = "预留项";
    public static final String ATTRIBUTE_RESERVERD_MESSAGE_EN = "reservedMessage";
}
