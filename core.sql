-- ----------------------------
-- 1、用户信息表
-- ----------------------------
drop table if exists sys_user;
CREATE TABLE `sys_user`
(
    `user_id`      bigint      NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `user_name`    varchar(30) NOT NULL COMMENT '用户账号',
    `nick_name`    varchar(30) NOT NULL COMMENT '用户昵称',
    `email`        varchar(50)  DEFAULT '' COMMENT '用户邮箱',
    `phone_number` varchar(11)  DEFAULT '' COMMENT '手机号码',
    `sex`          char(1)      DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
    `avatar`       varchar(100) DEFAULT '' COMMENT '头像地址',
    `password`     varchar(100) DEFAULT '' COMMENT '密码',
    `age`          tinyint unsigned DEFAULT '0' COMMENT '年龄',
    `introduction` varchar(500) DEFAULT NULL COMMENT '简介',
    `status`       char(1)      DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
    `del_flag`     char(1)      DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    `login_ip`     varchar(128) DEFAULT '' COMMENT '最后登录IP',
    `login_date`   datetime     DEFAULT NULL COMMENT '最后登录时间',
    `create_by`    varchar(64)  DEFAULT '' COMMENT '创建者',
    `create_time`  datetime     DEFAULT (NOW()) COMMENT '创建时间',
    `update_by`    varchar(64)  DEFAULT '' COMMENT '更新者',
    `update_time`  datetime NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark`       varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `uni_user_name` (`user_name`) USING BTREE COMMENT '用户名称'
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息表';


-- ----------------------------
-- 2、参数配置表
-- ----------------------------
drop table if exists sys_config;
create table sys_config
(
    config_id    int(5) not null auto_increment COMMENT '参数主键',
    config_name  varchar(100) default '' COMMENT '参数名称',
    config_key   varchar(100) default '' COMMENT '参数键名',
    config_value varchar(500) default '' COMMENT '参数键值',
    config_type  char(1)      default 'N' COMMENT '系统内置（Y是 N否）',
    create_by    varchar(64)  default '' COMMENT '创建者',
    create_time  datetime     DEFAULT (NOW()) comment '创建时间',
    update_by    varchar(64)  default '' COMMENT '更新者',
    update_time  datetime NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark       varchar(500) DEFAULT NULL COMMENT '备注',
    primary key (config_id)
) engine=innodb auto_increment=100 comment = '参数配置表';

insert into sys_config
values (1, '账号自助-验证码开关', 'sys.account.captchaEnabled', 'true', 'Y', 'admin', sysdate(), '', null,
        '是否开启验证码功能（true开启，false关闭）');
insert into sys_config
values (2, '账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'false', 'Y', 'admin', sysdate(), '', null,
        '是否开启注册用户功能（true开启，false关闭）');

-- ----------------------------
-- 3、通知公告表
-- ----------------------------
drop table if exists sys_notice;
create table sys_notice
(
    notice_id      int(4) not null auto_increment comment '公告ID',
    notice_title   varchar(50) not null comment '公告标题',
    notice_type    char(1)     not null comment '公告类型（1通知 2公告）',
    notice_content longblob     default null comment '公告内容',
    status         char(1)      default '0' comment '公告状态（0正常 1关闭）',
    create_by      varchar(64)  default '' comment '创建者',
    create_time    datetime     DEFAULT (NOW()) comment '创建时间',
    update_by      varchar(64)  default '' comment '更新者',
    update_time    datetime NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark         varchar(255) default null comment '备注',
    primary key (notice_id)
) engine=innodb auto_increment=10 comment = '通知公告表';

-- ----------------------------
-- 初始化-公告信息表数据
-- ----------------------------
insert into sys_notice
values ('1', '温馨提醒：2018-07-01 新版本发布啦', '2', '新版本内容', '0', 'admin', sysdate(), '', null, '管理员');
insert into sys_notice
values ('2', '注意我盯上你了', '1', '你被我发现了你被我发现了你被我发现了你被我发现了', '0', 'admin', sysdate(), '', null, '管理员');

-- ----------------------------
-- 4、通知用户表
-- ----------------------------
drop table if exists sys_notice_user;
create table sys_notice_user
(
    notice_id      int         not null comment '公告ID',
    user_id        int         not null comment '用户ID',
    is_read        char(1)      default '0' comment '是否已读（0未读 1已读）',
    create_by      varchar(64)  default '' comment '创建者',
    create_time    datetime     DEFAULT (NOW()) comment '创建时间',
    update_by      varchar(64)  default '' comment '更新者',
    update_time    datetime NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark         varchar(255) default null comment '备注',
    primary key (notice_id,user_id)
) engine=innodb auto_increment=10 comment = '通知用户表';

