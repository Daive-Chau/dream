package com.demo.model;

/**
 * 员工表
 */

/*CREATE TABLE `staff` (
  `ID` bigint(64) NOT NULL AUTO_INCREMENT,
  `ID_CARD` varchar(255) NOT NULL COMMENT '身份证号码',
  `LEVEL` varchar(255) DEFAULT NULL COMMENT '职级',
  `USERNAME` varchar(255) DEFAULT NULL COMMENT '用户名',
  `PASSWORD` varchar(255) DEFAULT NULL COMMENT '密码',
  `REAL_NAME` varchar(255) DEFAULT NULL COMMENT '真名',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工表';

*/

public class Staff {
    private Long id;
    private String idCard;
    private String level;
    private String username;
    private String password;
    private String realName;

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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
