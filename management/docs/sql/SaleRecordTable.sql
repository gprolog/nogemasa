DROP TABLE IF EXISTS c_sale_record_content;
CREATE TABLE IF NOT EXISTS c_sale_record_content (
  sid            BIGINT PRIMARY KEY AUTO_INCREMENT
  COMMENT '编号',
  store_sid      BIGINT(11) NOT NULL
  COMMENT '门店',
  employee_sid   BIGINT(11) COMMENT '员工编号',
  member_card_no VARCHAR(32) COMMENT '会员编号',
  promotion_sid  BIGINT(11) COMMENT '参加活动',
  sale_time      TIMESTAMP COMMENT '销售时间',
  cost_type      VARCHAR(4) COMMENT '收银方式',
  total_price    FLOAT COMMENT '商品总原价',
  total_cost     FLOAT COMMENT '商品总售价'
)
  COMMENT '销售单表'
  DEFAULT CHARSET utf8
  ENGINE InnoDB;

DROP TABLE IF EXISTS c_sale_record_detail;
CREATE TABLE IF NOT EXISTS c_sale_record_detail (
  sid         BIGINT PRIMARY KEY AUTO_INCREMENT
  COMMENT '编号',
  content_sid BIGINT      NOT NULL
  COMMENT '销售单编号',
  goods_sn    VARCHAR(32) NOT NULL
  COMMENT '商品编号',
  goods_price FLOAT COMMENT '商品原价',
  goods_cost  FLOAT COMMENT '商品售价',
  INDEX content_sid_index (content_sid ASC)
)
  COMMENT '销售明细表'
  DEFAULT CHARSET utf8
  ENGINE InnoDB;

DROP TABLE IF EXISTS c_sale_record;
CREATE TABLE IF NOT EXISTS c_sale_record (
  sid                 BIGINT(11) PRIMARY KEY AUTO_INCREMENT
  COMMENT '编号',
  store_sid      BIGINT(11)  NOT NULL
  COMMENT '门店',
  employee_sid        BIGINT(11) COMMENT '员工编号',
  goods_sn       VARCHAR(32) NOT NULL
  COMMENT '商品编号',
  member_card_no VARCHAR(32) COMMENT '会员编号',
  promotion_sid       BIGINT(11) COMMENT '参加活动',
  goods_original_cost FLOAT COMMENT '商品原价',
  goods_price         FLOAT COMMENT '商品售价',
  sale_time      TIMESTAMP COMMENT '销售时间',
  cost_type      VARCHAR(4) COMMENT '收银方式'
)
  COMMENT '商品表'
  DEFAULT CHARSET utf8
  ENGINE InnoDB;

DROP TABLE IF EXISTS c_finance_account;
CREATE TABLE IF NOT EXISTS c_finance_account (
  sid                   BIGINT(11) PRIMARY KEY AUTO_INCREMENT
  COMMENT '编号',
  account_type          VARCHAR(4) COMMENT '账户类型：1现金；2银行账户',
  account_remaining_sum FLOAT COMMENT '余额'
)
  COMMENT '财务账户'
  DEFAULT CHARSET utf8
  ENGINE InnoDB;

DROP TABLE IF EXISTS c_promotion;
CREATE TABLE IF NOT EXISTS c_promotion (
  sid            BIGINT(11) PRIMARY KEY AUTO_INCREMENT
  COMMENT '编号',
  store_sid      BIGINT(11) COMMENT '门店',
  promotion_type VARCHAR(4) COMMENT '活动类型：1满减，2打折，3买送',
  count_method   VARCHAR(128) COMMENT '计算方式：300,20，0.75，3,1'
)
  COMMENT '促销活动表'
  DEFAULT CHARSET utf8
  ENGINE InnoDB;