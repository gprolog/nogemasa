DROP TABLE IF EXISTS c_group;
CREATE TABLE IF NOT EXISTS c_group (
  sid   BIGINT(11) PRIMARY KEY
  COMMENT '用户分组编号',
  id    VARCHAR(20) NOT NULL
  COMMENT '分组id，由微信分配',
  name  VARCHAR(50) NOT NULL
  COMMENT '分组名字，UTF8编码',
  count BIGINT DEFAULT 0
  COMMENT '分组内用户数量'
)
  COMMENT '会员分组表'
  DEFAULT CHARSET utf8
  ENGINE InnoDB;

DROP TABLE IF EXISTS c_member_info;
CREATE TABLE IF NOT EXISTS c_member_info (
  sid            BIGINT(11) PRIMARY KEY AUTO_INCREMENT
  COMMENT '会员编号',
  subscribe      INT         NOT NULL
  COMMENT '用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。',
  openid         VARCHAR(32) NOT NULL
  COMMENT '用户的标识，对当前公众号唯一',
  nickname       VARCHAR(50) COMMENT '用户的昵称',
  sex            INT COMMENT '用户的性别，值为1时是男性，值为2时是女性，值为0时是未知',
  city           VARCHAR(20) COMMENT '用户所在城市',
  country        VARCHAR(20) COMMENT '用户所在国家',
  province       VARCHAR(20) COMMENT '用户所在省份',
  language       VARCHAR(20) COMMENT '用户的语言，简体中文为zh_CN',
  headimgurl     VARCHAR(512) COMMENT '用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。',
  subscribe_time BIGINT(20) COMMENT '用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间',
  unionid        VARCHAR(20) COMMENT '只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）',
  remark         VARCHAR(200) COMMENT '公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注',
  groupid        BIGINT(11) COMMENT '用户所在的分组ID',
  INDEX group_id_index (groupid ASC)
)
  COMMENT '会员表'
  DEFAULT CHARSET utf8
  ENGINE InnoDB;

DROP TABLE IF EXISTS c_member;
CREATE TABLE IF NOT EXISTS c_member (
  sid BIGINT PRIMARY KEY  AUTO_INCREMENT COMMENT '编号',
  openid VARCHAR(32) NOT NULL COMMENT '用户的标识，对当前公众号唯一',
  points         BIGINT                 DEFAULT 0
  COMMENT '用户积分',
  card_no        VARCHAR(20) COMMENT '会员卡号',
  INDEX openid_index (openid ASC)
)
  COMMENT '会员表'
  DEFAULT CHARSET utf8
  ENGINE InnoDB;