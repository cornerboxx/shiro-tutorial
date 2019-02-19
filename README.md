# shiro-tutorial
一个shiro工程的测试



#数据库初始化脚本
drop database if exists shirodb;
create database shirodb character set utf8;
use shirodb;

CREATE TABLE `shiro_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(100) DEFAULT NULL COMMENT '盐值 shiro框架保存',
  `locked` tinyint(4) DEFAULT NULL COMMENT '该用户是否锁定 0否 1是',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`)
);

CREATE TABLE `shiro_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `description` varchar(100) DEFAULT NULL COMMENT '介绍',
  `pid` int(11) DEFAULT NULL COMMENT '父角色ID',
  `locked` tinyint(4) DEFAULT NULL COMMENT '该角色是否锁定 0否 1是',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_role_name` (`role_name`)
);

CREATE TABLE `shiro_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
);

CREATE TABLE `shiro_role_promission`(
 id INT(11) auto_increment,
 role_id INT(11) COMMENT '角色ID',
 promission_id INT(11) COMMENT '权限ID',
 PRIMARY KEY (id)
);

CREATE TABLE `shiro_promission` (
 id INT(11) auto_increment,
 name VARCHAR(50) COMMENT '权限名称',
 description VARCHAR(100) COMMENT '介绍',
 `locked` tinyint(4) DEFAULT NULL COMMENT '该权限是否锁定 0否 1是',
 PRIMARY KEY (`id`)
);
















