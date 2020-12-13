package cn.cat.platform.model.param;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

/**
 * @author linlt
 * @createTime 2020/8/2 19:44
 * @description TODO
 */
@ApiModel
@Data
public class DeviceDataParam {


    /**
     * 主键
     */
    @ApiModelProperty(name = "dataId",value = "设备数据Id",required = true, example = "3")
    private Integer dataId;

    /**
     * (外键，指向t_device表主键device_id)
     */
    @ApiModelProperty(name = "deviceId",value = "设备Id",required = true, example = "3")
    @NotNull(message = "设备ID不能为空")
    private Integer deviceId;

    /**
     * 组号
     */
    @ApiModelProperty(name = "groupId",value = "组号",required = true, example = "P1")
    @NotNull(message = "组号不能为空")
    private String groupId;

    /**
     * 日期
     */
    @ApiModelProperty(name = "date",value = "日期",required = true, example = "2020-03-27")
    @NotNull(message = "日期不能为空")
    private String date;

    /**
     * 时间
     */

    @ApiModelProperty(name = "time",value = "时间",required = true, example = "22:19:57")
    @NotNull(message = "时间不能为空")
    private String time;

    /**
     * 结果判断
     */

    @ApiModelProperty(name = "resultJudgment",value = "结果判断",required = true, example = "ACCEPT")
    @NotNull(message = "结果判断不能为空")
    private String resultJudgment;


    /**
     * 测试类型/模式
     */
    @ApiModelProperty(name = "testMode",value = "测试类型/模式",required = true, example = "PLR")
    @NotNull(message = "测试模式不能为空")
    private String testMode;

    /**
     * 数据项1
     */
    @ApiModelProperty(name = "dataItem1",value = "数据项1", example = "176.564")

    private BigDecimal dataItem1;

    /**
     * 数据项1单位
     */
    @ApiModelProperty(name = "dataItem1Unit",value = "数据项1单位", example = "pa")

    private String dataItem1Unit;

    /**
     * 数据项2
     */
    @ApiModelProperty(name = "dataItem2",value = "数据项2", example = "15.3")
    private BigDecimal dataItem2;

    /**
     * 数据项2单位
     */
    @ApiModelProperty(name = "dataItem2Unit",value = "数据项2单位", example = "pa")
    private String dataItem2Unit;

    /**
     * 数据项3
     */
    @ApiModelProperty(name = "dataItem3",value = "数据项3", example = "121.242")
    private BigDecimal dataItem3;

    /**
     * 数据项3单位
     */
    @ApiModelProperty(name = "dataItem3Unit",value = "数据项3单位", example = "pa")
    private String dataItem3Unit;


    /**
     * 运行次数
     */
    @ApiModelProperty(name = "runNumber",value = "运行次数",required = true, example = "120")
    @NotNull(message = "运行次数不能为空")
    private String runNumber;

    /**
     * 被测试条码
     */
    @ApiModelProperty(name = "testedBarCode",value = "被测试条码", example = "SN:AK182")
    private String testedBarCode;

    /**
     * 节拍输出
     */
    @ApiModelProperty(name = "beatOutput",value = "节拍输出", example = "P5.0-F3.0-S3.0-T2.0-E1.0")
    private String beatOutput;

}
