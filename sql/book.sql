/*
 Navicat Premium Data Transfer

 Source Server         : localhost-mysql5.7.23
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3307
 Source Schema         : demo_test

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 05/01/2020 10:21:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `BUSINESS_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '业务主键',
  `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '书名称',
  `PRICE` float(10, 2) NULL DEFAULT NULL COMMENT '书价格',
  `CREATE_BY` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `UPDATE_BY` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `UPDATE_DATE` datetime(0) NOT NULL COMMENT '修改日期',
  `DEL_FLAG` int(1) NOT NULL DEFAULT 0 COMMENT '删除标识 1 删除 0 未删除',
  `FLAG` int(1) NOT NULL DEFAULT 1 COMMENT '启用标记（0：停用；1：启用）',
  PRIMARY KEY (`BUSINESS_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('4cd52455bcaff871495660df944513e9', '资本论', 59.30, '371', '2020-01-01 22:35:22', '371', '2020-01-01 22:35:22', 0, 1);

SET FOREIGN_KEY_CHECKS = 1;
