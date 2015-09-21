DROP TABLE IF EXISTS s_group;
CREATE TABLE IF NOT EXISTS s_group (
  sid        BIGINT(11) PRIMARY KEY AUTO_INCREMENT
  COMMENT '用户组编号',
  group_name VARCHAR(50) NOT NULL UNIQUE
  COMMENT '用户组名称',
  group_desc VARCHAR(100) COMMENT '用户组描述'
)
  COMMENT '用户组表'
  DEFAULT CHARSET utf8
  ENGINE InnoDB;

DROP TABLE IF EXISTS s_users;
CREATE TABLE IF NOT EXISTS s_users (
  sid         BIGINT(11) PRIMARY KEY AUTO_INCREMENT
  COMMENT '用户编号',
  username    VARCHAR(50) NOT NULL UNIQUE
  COMMENT '用户名',
  password    VARCHAR(50) NOT NULL
  COMMENT '密码',
  enabled     BOOLEAN     NOT NULL
  COMMENT '是否有效',
  create_time TIMESTAMP   NOT NULL
  COMMENT '创建时间'
)
  COMMENT '用户表'
  DEFAULT CHARSET utf8
  ENGINE InnoDB;

DROP TABLE IF EXISTS s_authority;
CREATE TABLE IF NOT EXISTS s_authority (
  sid            BIGINT(11) PRIMARY KEY AUTO_INCREMENT
  COMMENT '权限编号',
  authority      VARCHAR(50) UNIQUE NOT NULL
  COMMENT '权限',
  authority_desc VARCHAR(100) COMMENT '权限描述'
)
  COMMENT '用户权限表'
  DEFAULT CHARSET utf8
  ENGINE InnoDB;

DROP TABLE IF EXISTS s_group_user;
CREATE TABLE IF NOT EXISTS s_group_user (
  sid       BIGINT(11) PRIMARY KEY AUTO_INCREMENT
  COMMENT 'ID',
  group_sid BIGINT(11) COMMENT '用户组编号',
  user_sid  BIGINT(11) COMMENT '用户编号',
  UNIQUE INDEX group_user_sid (group_sid ASC, user_sid ASC)
)
  COMMENT '用户组与用户关系表'
  DEFAULT CHARSET utf8
  ENGINE InnoDB;

DROP TABLE IF EXISTS s_group_authority;
CREATE TABLE IF NOT EXISTS s_group_authority (
  sid           BIGINT(11) PRIMARY KEY AUTO_INCREMENT
  COMMENT 'ID',
  group_sid     BIGINT(11) COMMENT '用户组编号',
  authority_sid BIGINT(11) COMMENT '权限编号',
  UNIQUE INDEX group_authorities_sid (group_sid ASC, authority_sid ASC)
)
  COMMENT '用户组与权限关系表'
  DEFAULT CHARSET utf8
  ENGINE InnoDB;