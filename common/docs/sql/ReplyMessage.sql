DROP TABLE IF EXISTS c_reply_news_message;
CREATE TABLE c_reply_news_message (
  sid            BIGINT PRIMARY KEY                                                                                                                          AUTO_INCREMENT
  COMMENT '图文消息主键',
  message_status VARCHAR(4)   NOT NULL DEFAULT '0'
  COMMENT '是否有效标志位：0无效、1有效',
  title          VARCHAR(100) NOT NULL DEFAULT '敬请期待。。。'
  COMMENT '图文消息的标题',
  description    TEXT COMMENT '图文消息的描述',
  pic_url        TEXT COMMENT '图文消息图片链接',
  url            TEXT COMMENT '图文消息跳转链接',
  create_time    DATE COMMENT '创建时间',
  effect_time    DATE COMMENT '生效时间',
  invalid_time   DATE COMMENT '失效时间'
);

DROP TABLE IF EXISTS c_reply_text_message;
CREATE TABLE c_reply_text_message (
  sid            BIGINT PRIMARY KEY  AUTO_INCREMENT
  COMMENT '文本消息主键',
  message_status VARCHAR(4) NOT NULL DEFAULT '0'
  COMMENT '是否最新标志位',
  content        TEXT       NOT NULL
  COMMENT '文本消息的内容',
  create_time    DATE COMMENT '创建时间',
  effect_time    DATE COMMENT '生效时间',
  invalid_time   DATE COMMENT '失效时间'
);