/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : message

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2016-12-18 19:13:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` varchar(48) NOT NULL COMMENT '消息id',
  `source` varchar(32) NOT NULL COMMENT '消息来源',
  `retry` int(11) NOT NULL COMMENT '已重试的次数',
  `status` int(11) NOT NULL COMMENT '消息状态: 0 待确认; 1 确认未发送; 2 发送中; 3 死亡; 4 发送成功',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `queue` varchar(32) NOT NULL COMMENT '消息队列',
  `content` varchar(4096) DEFAULT NULL COMMENT '消息内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
