CREATE TABLE sys_menu
(
    pkid VARCHAR(32) PRIMARY KEY NOT NULL COMMENT '主键id',
    name VARCHAR(20) NOT NULL COMMENT '菜单名',
    path VARCHAR(100) COMMENT '菜单访问路径',
    parent_id VARCHAR(32) DEFAULT '0' COMMENT '父菜单ID,顶级为0',
    weight INT(11) DEFAULT '0' COMMENT '排序权重',
    `show` TINYINT(1) DEFAULT '1' COMMENT '是否显示，0 隐藏 1 显示',
    description VARCHAR(100) COMMENT '描述',
    icon VARCHAR(32)
);
CREATE TABLE sys_right
(
    pkid VARCHAR(32) PRIMARY KEY NOT NULL,
    name VARCHAR(100) NOT NULL COMMENT '权限名称',
    `right` VARCHAR(100) NOT NULL COMMENT '权限代码',
    `delete` TINYINT(1) DEFAULT '1' COMMENT '0 删除 1 在用'
);
CREATE TABLE sys_role
(
    pkid VARCHAR(32) PRIMARY KEY NOT NULL,
    name VARCHAR(100),
    role VARCHAR(100),
    description VARCHAR(200),
    `delete` TINYINT(1) DEFAULT '1' COMMENT ' 0 删除 1 显示'
);
CREATE TABLE sys_role_menu
(
    pkid VARCHAR(32) PRIMARY KEY NOT NULL,
    role_id VARCHAR(32) NOT NULL,
    menu_id VARCHAR(32) NOT NULL
);
CREATE TABLE sys_role_menu_right
(
    pkid VARCHAR(32) PRIMARY KEY NOT NULL,
    role_menu_id VARCHAR(32) NOT NULL,
    right_id VARCHAR(32) NOT NULL,
    `delete` TINYINT(1) DEFAULT '1' COMMENT '0 删除 1 在用'
);
CREATE TABLE sys_user
(
    pkid VARCHAR(32) PRIMARY KEY NOT NULL,
    username VARCHAR(100),
    email VARCHAR(100),
    mobile_phone_number VARCHAR(20),
    password VARCHAR(100),
    salt VARCHAR(10),
    create_date TIMESTAMP DEFAULT 'CURRENT_TIMESTAMP' NOT NULL,
    status VARCHAR(50),
    deleted TINYINT(1),
    admin TINYINT(1)
);
CREATE INDEX idx_sys_user_status ON sys_user (status);
CREATE UNIQUE INDEX unique_sys_user_email ON sys_user (email);
CREATE UNIQUE INDEX unique_sys_user_mobile_phone_number ON sys_user (mobile_phone_number);
CREATE UNIQUE INDEX unique_sys_user_username ON sys_user (username);