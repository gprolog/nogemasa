DROP TABLE IF EXISTS c_order_purchase;
CREATE TABLE IF NOT EXISTS c_order_purchase (
  sid             BIGINT(11) PRIMARY KEY AUTO_INCREMENT
  COMMENT '编号',
  store_sid       BIGINT(11) NOT NULL
  COMMENT '门店编号',
  status          VARCHAR(4) COMMENT '采购单状态',
  begin_time      TIMESTAMP COMMENT '采购单新增时间',
  end_time        TIMESTAMP COMMENT '采购单完成时间',
  create_user_sid BIGINT(11) NOT NULL
  COMMENT '录入人员编号',
  commit_user_sid BIGINT(11) COMMENT '完成人员编号'
)
  COMMENT '采购单'
  DEFAULT CHARSET utf8
  ENGINE InnoDB;

DROP TABLE IF EXISTS c_order_purchase_list;
CREATE TABLE IF NOT EXISTS c_order_purchase_list (
  sid                BIGINT(11) PRIMARY KEY AUTO_INCREMENT
  COMMENT '编号',
  order_purchase_sid BIGINT(11) NOT NULL
  COMMENT '采购单编号',
  goods_sid          BIGINT(11) NOT NULL
  COMMENT '商品编号',
  count              BIGINT     NOT NULL
  COMMENT '采购申请数量'
)
  COMMENT '采购单明细'
  DEFAULT CHARSET utf8
  ENGINE InnoDB;

DROP TABLE IF EXISTS c_order_godown;
CREATE TABLE IF NOT EXISTS c_order_godown (
  sid             BIGINT(11) PRIMARY KEY AUTO_INCREMENT
  COMMENT '编号',
  store_sid       BIGINT(11) NOT NULL
  COMMENT '门店编号',
  status          VARCHAR(4) COMMENT '入库单状态',
  begin_time      TIMESTAMP COMMENT '入库单新增时间',
  end_time        TIMESTAMP COMMENT '入库单完成时间',
  create_user_sid BIGINT(11) NOT NULL
  COMMENT '创建人员编号',
  commit_user_sid BIGINT(11) COMMENT '完成人员编号'
)
  COMMENT '入库单'
  DEFAULT CHARSET utf8
  ENGINE InnoDB;

DROP TABLE IF EXISTS c_order_godown_list;
CREATE TABLE IF NOT EXISTS c_order_godown_list (
  sid              BIGINT(11) PRIMARY KEY AUTO_INCREMENT
  COMMENT '编号',
  order_godown_sid BIGINT(11) NOT NULL
  COMMENT '入库单编号',
  goods_sid        BIGINT(11) NOT NULL
  COMMENT '商品编号',
  count            BIGINT     NOT NULL
  COMMENT '入库数量',
  cost_price       FLOAT                  DEFAULT 0
  COMMENT '成本价'
)
  COMMENT '入库单明细'
  DEFAULT CHARSET utf8
  ENGINE InnoDB;