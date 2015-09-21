DROP TABLE IF EXISTS c_inventory;
CREATE TABLE IF NOT EXISTS c_inventory (
  sid       BIGINT(11) PRIMARY KEY AUTO_INCREMENT
  COMMENT '编号',
  store_sid BIGINT(11) NOT NULL
  COMMENT '分店编号',
  goods_sid BIGINT(11) NOT NULL
  COMMENT '商品编号',
  count     BIGINT COMMENT '库存量',
  UNIQUE INDEX store_goods_sid (store_sid ASC, goods_sid ASC)
)
  COMMENT '库存表'
  DEFAULT CHARSET utf8
  ENGINE InnoDB;

DROP TABLE IF EXISTS c_inventory_check_content;
CREATE TABLE IF NOT EXISTS c_inventory_check_content (
  sid             BIGINT PRIMARY KEY AUTO_INCREMENT
  COMMENT '编号',
  store_sid       BIGINT NOT NULL
  COMMENT '门店编号',
  status          VARCHAR(4) COMMENT '盘点单状态',
  begin_time      TIMESTAMP COMMENT '盘点单新增时间',
  end_time        TIMESTAMP COMMENT '盘点单完成时间',
  create_user_sid BIGINT NOT NULL
  COMMENT '创建人员编号',
  commit_user_sid BIGINT COMMENT '完成人员编号'
)
  COMMENT '盘点单'
  DEFAULT CHARSET utf8
  ENGINE InnoDB;

DROP TABLE IF EXISTS c_inventory_check_detail;
CREATE TABLE IF NOT EXISTS c_inventory_check_detail (
  sid         BIGINT(11) PRIMARY KEY AUTO_INCREMENT
  COMMENT '编号',
  content_sid BIGINT(11) NOT NULL
  COMMENT '入库单编号',
  goods_sid   BIGINT(11) NOT NULL
  COMMENT '商品编号',
  pre_count   BIGINT     NOT NULL
  COMMENT '盘点前数量',
  post_count  BIGINT     NOT NULL
  COMMENT '盘点后数量',
  delta_count BIGINT     NOT NULL
  COMMENT '盘点数量差'
)
  COMMENT '盘点单明细'
  DEFAULT CHARSET utf8
  ENGINE InnoDB;