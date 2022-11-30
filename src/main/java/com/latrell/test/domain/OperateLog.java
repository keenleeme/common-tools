package com.latrell.test.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 系统操作日志记录
 * </p>
 *
 * @author liz
 * @since 2022-10-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_operate_log")
@NoArgsConstructor
@ApiModel(value="OperateLog对象", description="系统操作日志记录")
public class OperateLog implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "op_log_id", type = IdType.AUTO)
    private Long opLogId;

    @ApiModelProperty(value = "操作用户名称")
    private String opUserName;

    @ApiModelProperty(value = "接口URI")
    private String apiUri;

    @ApiModelProperty(value = "接口描述信息")
    private String description;

    @ApiModelProperty(value = "操作模块")
    private String opModule;

    @ApiModelProperty(value = "操作类型")
    private String opType;

    @ApiModelProperty(value = "请求参数信息")
    private String requestParams;

    @ApiModelProperty(value = "接口返回值内容")
    private String responseContent;

    @ApiModelProperty(value = "接口操作详细信息")
    private String detailMessage;

    @ApiModelProperty(value = "操作结果 0 失败 1 成功")
    private Integer opResult;

    @ApiModelProperty(value = "操作IP地址")
    private String opIpAddress;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime opTime;

    @ApiModelProperty(value = "创建时间")
    @JsonIgnore
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    public OperateLog(String opUserName, String apiUri, String description, String opModule, String opType,
                      String requestParams, String detailMessage, String opIpAddress, LocalDateTime opTime) {
        this.opUserName = opUserName;
        this.apiUri = apiUri;
        this.description = description;
        this.opModule = opModule;
        this.opType = opType;
        this.requestParams = requestParams;
        this.detailMessage = detailMessage;
        this.opIpAddress = opIpAddress;
        this.opTime = opTime;
    }
}
