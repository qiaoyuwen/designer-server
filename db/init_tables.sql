SET NAMES utf8mb4;

use designer;

CREATE TABLE `user` (
  `id` varchar(32) NOT NULL COMMENT '用户ID',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `ctime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_root` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否为超级管理员, 0: 否, 1: 是',
  `deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除, 0: 未删除, 1: 删除',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username` (`username`)
)COMMENT '用户表';

CREATE TABLE `role` (
  `id` varchar(32) NOT NULL COMMENT '角色ID',
  `name` varchar(32) NOT NULL COMMENT '角色名',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '角色描述',
  `ctime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `permission` LONGTEXT COMMENT '角色权限',
  `deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除, 0: 未删除, 1: 删除',
  PRIMARY KEY (`id`)
)COMMENT '角色表';

CREATE TABLE `user_role` (
  `id` varchar(32) NOT NULL COMMENT '用户角色ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `role_id` varchar(32) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `user_role` (`user_id`, `role_id`)
)COMMENT '用户角色表';

CREATE TABLE `project` (
  `id` varchar(32) NOT NULL COMMENT '项目ID',
  `name` varchar(32) NOT NULL COMMENT '项目名',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '项目描述',
  `menu_config` LONGTEXT COMMENT '项目菜单配置',
  `ctime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除, 0: 未删除, 1: 删除',
  PRIMARY KEY (`id`)
)COMMENT '项目表';

CREATE TABLE `project_page` (
  `id` varchar(32) NOT NULL COMMENT '页面ID',
  `name` varchar(32) NOT NULL COMMENT '页面名称',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '页面描述',
  `ctime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `schema_json` LONGTEXT COMMENT '页面 schema json',
  `status` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '上线状态, 0: 未上线, 1: 已上线',
  `deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除, 0: 未删除, 1: 删除',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目ID',
  PRIMARY KEY (`id`),
  INDEX `project_id` (`project_id`)
)
