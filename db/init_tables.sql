SET NAMES utf8mb4;

use designer;

CREATE TABLE `user` (
  `id` char(32) NOT NULL COMMENT '用户ID',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `ctime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username` (`username`)
)COMMENT '用户表';

CREATE TABLE `project` (
  `id` char(32) NOT NULL COMMENT '项目ID',
  `name` varchar(32) NOT NULL COMMENT '项目名',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '项目描述',
  `ctime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除, 0: 未删除, 1: 删除',
  PRIMARY KEY (`id`)
)COMMENT '项目表';

CREATE TABLE `project_page` (
  `id` char(32) NOT NULL COMMENT '页面ID',
  `name` varchar(32) NOT NULL COMMENT '页面名称',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '页面描述',
  `ctime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `schema_json` LONGTEXT COMMENT '页面 schema json',
  `status` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '上线状态, 0: 未上线, 1: 已上线',
  `deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除, 0: 未删除, 1: 删除'
)
