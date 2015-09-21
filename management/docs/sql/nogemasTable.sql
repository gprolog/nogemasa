DROP TABLE IF EXISTS c_store;
CREATE TABLE IF NOT EXISTS c_store (
  sid            BIGINT(11) PRIMARY KEY AUTO_INCREMENT
  COMMENT '编号',
  name           VARCHAR(50) NOT NULL
  COMMENT '店名',
  address        VARCHAR(100) COMMENT '地址',
  phone          VARCHAR(20) COMMENT '联系电话',
  business_model VARCHAR(4)             DEFAULT '0'
  COMMENT '经营模式：0分店、1加盟',
  shopowner      VARCHAR(50) COMMENT '店长',
  enabled        BOOLEAN COMMENT '是否有效'
)
  COMMENT '门店表'
  DEFAULT CHARSET utf8
  ENGINE InnoDB;

DROP TABLE IF EXISTS c_employee;
CREATE TABLE IF NOT EXISTS c_employee (
  sid         BIGINT(11) PRIMARY KEY AUTO_INCREMENT
  COMMENT '员工编号',
  name        VARCHAR(50) NOT NULL
  COMMENT '姓名',
  entry_time  DATE        NOT NULL
  COMMENT '入职时间',
  fired_time  DATE COMMENT '离职时间',
  identity_no VARCHAR(20) NOT NULL
  COMMENT '身份证号',
  birthday    DATE COMMENT '生日',
  phone       VARCHAR(20) COMMENT '联系电话',
  qq          VARCHAR(20) COMMENT 'QQ号',
  store_sid   BIGINT(11)  NOT NULL
  COMMENT '所属门店',
  user_sid    BIGINT(11) COMMENT '用户编号',
  status      VARCHAR(4) COMMENT '员工状态：在职、离职',
  INDEX store_user_sid (store_sid ASC, user_sid ASC)
)
  COMMENT '员工表'
  DEFAULT CHARSET utf8
  ENGINE InnoDB;
