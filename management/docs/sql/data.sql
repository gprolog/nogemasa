INSERT INTO nogemasa.c_promotion (sid, store_sid, promotion_type, count_method) VALUES (1, 1, '1', '300,50');
INSERT INTO nogemasa.c_promotion (sid, store_sid, promotion_type, count_method) VALUES (2, 1, '2', '0.75');
INSERT INTO nogemasa.c_promotion (sid, store_sid, promotion_type, count_method) VALUES (3, 1, '3', '4,1');
INSERT INTO nogemasa.c_promotion (sid, store_sid, promotion_type, count_method) VALUES (4, 1, '1', '400,');
INSERT INTO nogemasa.c_promotion (sid, store_sid, promotion_type, count_method) VALUES (5, 1, '1', '400');
INSERT INTO nogemasa.c_promotion (sid, store_sid, promotion_type, count_method) VALUES (6, 1, '1', 'abc');

INSERT INTO nogemasa.c_goods (sid, name, goods_size, color, fabrics, style, sn)
VALUES (1, '蝙蝠盔甲', 'L', '黑色', '高纤维', '复古', '1');
INSERT INTO nogemasa.c_goods (sid, name, goods_size, color, fabrics, style, sn)
VALUES (2, '钢铁战衣', 'L', '红色', '金属', '现代', '2');

DELETE FROM c_store;
DELETE FROM c_employee;

INSERT INTO c_store (sid, name, address, business_model, shopowner, enabled)
VALUES (1, '天下第一店', '山东省禹城市', '1', '店长一号', TRUE);
INSERT INTO c_employee (name, entry_time, identity_no, birthday, store_sid, user_sid)
VALUES ('员工一号', sysdate(), '370000000000000000', sysdate(), 1, 1);
INSERT INTO c_employee (name, entry_time, identity_no, birthday, store_sid, user_sid)
VALUES ('员工二号', sysdate(), '100000000000000000', sysdate(), 1, 1);

DELETE FROM `s_group_authority`;
DELETE FROM `s_group_user`;
DELETE FROM `s_authority`;
DELETE FROM `s_group`;
DELETE FROM `s_users`;

INSERT INTO `s_users` (`sid`, `username`, `password`, `enabled`) VALUES (1, 'admin', 'admin', 1);
INSERT INTO `s_group` (`sid`, `group_name`, `group_desc`) VALUES (1, 'Admin', '管理员');
INSERT INTO `s_authority` (`sid`, `authority`, `authority_desc`) VALUES (1, 'ADMIN', '管理员权限');
INSERT INTO `s_group_authority` (`group_sid`, `authority_sid`) VALUES (1, 1);
INSERT INTO `s_group_user` (`group_sid`, `user_sid`) VALUES (1, 1);