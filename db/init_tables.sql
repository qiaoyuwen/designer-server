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
