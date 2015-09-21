DROP TABLE IF EXISTS c_goods;
CREATE TABLE IF NOT EXISTS c_goods (
  sid           BIGINT(11) PRIMARY KEY AUTO_INCREMENT
  COMMENT '商品编号',
  name          VARCHAR(50) NOT NULL
  COMMENT '名称',
  goods_size    VARCHAR(4)  NOT NULL
  COMMENT '大小',
  color         VARCHAR(50) NOT NULL
  COMMENT '颜色',
  fabrics       VARCHAR(50) COMMENT '材质',
  style         VARCHAR(50) COMMENT '款式',
  sn            VARCHAR(32) UNIQUE
  COMMENT '条形码',
  put_in_status VARCHAR(4) COMMENT '入库状态：1禁入，2准入'
)
  COMMENT '商品表'
  DEFAULT CHARSET utf8
  ENGINE InnoDB;

DROP TABLE IF EXISTS c_category;
CREATE TABLE IF NOT EXISTS c_category (
  sid        BIGINT(11) PRIMARY KEY AUTO_INCREMENT
  COMMENT '分类编号',
  name       VARCHAR(50) NOT NULL
  COMMENT '分类名称',
  root_sid   BIGINT(11) COMMENT '根节点编号',
  parent_sid BIGINT(11) COMMENT '父节点编号',
  is_leaf    BOOLEAN COMMENT '是否叶子节点'
)
  COMMENT '分类表'
  DEFAULT CHARSET utf8
  ENGINE InnoDB;

DROP TABLE IF EXISTS c_category_goods;
CREATE TABLE IF NOT EXISTS c_category_goods (
  sid          BIGINT(11) PRIMARY KEY AUTO_INCREMENT
  COMMENT '编号',
  goods_sid    BIGINT(11) NOT NULL
  COMMENT '商品编号',
  category_sid BIGINT(11) NOT NULL
  COMMENT '分类编号',
  UNIQUE INDEX category_goods_sid (category_sid ASC, goods_sid ASC)
)
  COMMENT '分类-商品关系表'
  DEFAULT CHARSET utf8
  ENGINE InnoDB;

DROP TABLE IF EXISTS c_price;
CREATE TABLE IF NOT EXISTS c_price (
  sid       BIGINT(11) PRIMARY KEY AUTO_INCREMENT
  COMMENT '编号',
  store_sid BIGINT(11) NOT NULL
  COMMENT '分店编号',
  goods_sid BIGINT(11) NOT NULL
  COMMENT '商品编号',
  price     FLOAT COMMENT '售价',
  UNIQUE INDEX store_goods_price_sid (store_sid ASC, goods_sid ASC)
)
  COMMENT '商品价格表'
  DEFAULT CHARSET utf8
  ENGINE InnoDB;