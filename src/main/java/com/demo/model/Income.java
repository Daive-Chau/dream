package com.demo.model;

import com.demo.em.PayType;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 收入明细表
 */

/*CREATE TABLE `income` (
        `ID` bigint(64) NOT NULL,
        `ID_CARD` bigint(64) NOT NULL COMMENT '身份证号码',
        `REAL_NAME` varchar(255) DEFAULT NULL COMMENT '真实名称',
        `PAY` int(64) DEFAULT NULL COMMENT '工资',
        `TYPE` tinyint(4) DEFAULT NULL COMMENT '类型，1：基本工资 2：绩效工资，3：奖金',
        `TAX` int(64) DEFAULT NULL COMMENT '扣税',
        `DATE` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '导入时间',
        PRIMARY KEY (`ID`)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/
public class Income {
    private Long id;
    @ApiModelProperty("身份证")
    private String idCard;
    @ApiModelProperty("姓名")
    private String realName;
    @ApiModelProperty("职级")
    private String level;
    @ApiModelProperty("工资")
    private BigDecimal pay;
    @ApiModelProperty("工资类型")
    private Integer type;
    @ApiModelProperty("工资类型名称")
    private String typeStr;
    @ApiModelProperty("个税")
    private BigDecimal tax;

    private BigDecimal due;
    @ApiModelProperty("日期")
    private Date date ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public BigDecimal getPay() {
        return pay;
    }

    public void setPay(BigDecimal pay) {
        this.pay = pay;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTypeStr() {
        return PayType.getPayTypeByType(type).getName().toString();
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public BigDecimal getDue() {
        return pay.subtract(tax);
    }

    public void setDue(BigDecimal due) {
        this.due = due;
    }
}
