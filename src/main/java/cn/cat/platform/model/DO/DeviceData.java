package cn.cat.platform.model.DO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

/**
 * @author linlt
 * @CreateTime 2020/3/15 8:21
 */
@Data
public class DeviceData implements Serializable {

    public DeviceData() {
    }

    public DeviceData(@NonNull Integer deviceId,
                      @NonNull String groupId,
                      @NonNull Date date,
                      @NonNull Time time,
                      @NonNull String resultJudgment,
                      @NonNull String testMode,
                      @NonNull String runNumber,
                      String testedBarCode,
                      String beatOutput) {
        this.deviceId = deviceId;
        this.groupId = groupId;
        this.date = date;
        this.time = time;
        this.resultJudgment = resultJudgment;
        this.testMode = testMode;
        this.runNumber = runNumber;
        this.testedBarCode = testedBarCode;
        this.beatOutput = beatOutput;
    }

    public void setDataItem(@NonNull BigDecimal dataItem1,
                            @NonNull String dataItem1Unit,
                            @NonNull BigDecimal dataItem2,
                            @NonNull String dataItem2Unit,
                            @NonNull BigDecimal dataItem3,
                            @NonNull String dataItem3Unit) {
        this.dataItem1 = dataItem1;
        this.dataItem1Unit = dataItem1Unit;
        this.dataItem2 = dataItem2;
        this.dataItem2Unit = dataItem2Unit;
        this.dataItem3 = dataItem3;
        this.dataItem3Unit = dataItem3Unit;
    }

    public void setDataItem(@NonNull BigDecimal dataItem1,
                            @NonNull String dataItem1Unit,
                            @NonNull BigDecimal dataItem2,
                            @NonNull String dataItem2Unit) {
        this.dataItem1 = dataItem1;
        this.dataItem1Unit = dataItem1Unit;
        this.dataItem2 = dataItem2;
        this.dataItem2Unit = dataItem2Unit;

    }

    public void setDataItem(@NonNull BigDecimal dataItem1,
                            @NonNull String dataItem1Unit) {
        this.dataItem1 = dataItem1;
        this.dataItem1Unit = dataItem1Unit;


    }


    /**
     * 主键
     */
    private Integer dataId;

    /**
     * (外键，指向t_device表主键device_id)
     */
    @NotNull(message = "设备ID不能为空")
    private Integer deviceId;

    /**
     * 组号
     */
    @NotNull(message = "组号不能为空")
    private String groupId;

    /**
     * 日期
     */
    @NotNull(message = "日期不能为空")
    private Date date;

    /**
     * 时间
     */
    @NotNull(message = "时间不能为空")
    private Time time;

    /**
     * 结果判断
     */
    @NotNull(message = "结果判断不能为空")
    private String resultJudgment;


    /**
     * 测试类型/模式
     */
    @NotNull(message = "测试模式不能为空")
    private String testMode;

    /**
     * 数据项1
     */
    private BigDecimal dataItem1;

    /**
     * 数据项1单位
     */
    private String dataItem1Unit;

    /**
     * 数据项2
     */
    private BigDecimal dataItem2;

    /**
     * 数据项2单位
     */
    private String dataItem2Unit;

    /**
     * 数据项3
     */
    private BigDecimal dataItem3;

    /**
     * 数据项3单位
     */
    private String dataItem3Unit;


    /**
     * 运行次数
     */
    @NotNull(message = "运行次数不能为空")
    private String runNumber;

    /**
     * 被测试条码
     */
    private String testedBarCode;

    /**
     * 节拍输出
     */
    private String beatOutput;

    /**
     * 预留项
     */
    private String reservedMessage;

    private static final long serialVersionUID = 1L;
}