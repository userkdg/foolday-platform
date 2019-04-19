/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : foolday_platform

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2019-04-17 01:25:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `id` int(11) NOT NULL,
  `account` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号目前约定为手机号码',
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码 md5加密和加盐',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1为有效,2为无效，3为禁用，4为拉黑，-1为删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `telphone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '手机号码',
  `nickname` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '名称',
  PRIMARY KEY (`id`),
  KEY `account_password_status_index` (`account`,`password`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='账号表';

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES ('1', '18813975053', 'PASSWORD_HEX_e10adc3949ba59abbe56e057f20f883e', '1', '2019-04-07 19:03:19', null, null, '');

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `id` int(11) NOT NULL,
  `shop_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '店铺id',
  `img_ids` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片ids 多个用 英文逗号隔开,最多在3-5张5*36=180',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '有效(1),无效(2),删除(3)',
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '评论内容 目前约定最多200字',
  `order_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单id',
  `goods_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品id',
  `user_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户id',
  `user_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名称，前端需要限制不显示全名',
  `comment_type` tinyint(2) DEFAULT '1' COMMENT '客户(1),店长(2),超级管理员(3)',
  `admin_id` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '若店内人员回复则记录回复人id',
  `admin_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '若店内人员回复则记录回复人名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `shopId` (`shop_id`),
  KEY `userId_status_orderId_goodsId` (`user_id`,`status`,`order_id`,`goods_id`),
  KEY `commentType` (`comment_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评论表';

-- ----------------------------
-- Records of t_comment
-- ----------------------------

-- ----------------------------
-- Table structure for t_coupon
-- ----------------------------
DROP TABLE IF EXISTS `t_coupon`;
CREATE TABLE `t_coupon` (
  `id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `create_time` datetime NOT NULL,
  `update_tme` datetime DEFAULT NULL,
  `common_used` tinyint(1) DEFAULT '0' COMMENT '1可共用 0不可公用_默认',
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '使用说明',
  `type` tinyint(2) DEFAULT '1' COMMENT '折扣券1,满减券2,其他优惠券0',
  `status` tinyint(2) DEFAULT '1' COMMENT '有效1, 无效0, 删除-1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='优惠券明确商品可以使用那些券';

-- ----------------------------
-- Records of t_coupon
-- ----------------------------

-- ----------------------------
-- Table structure for t_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `shop_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '店铺id',
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品名称',
  `price` float NOT NULL COMMENT '商品价格',
  `discnt_price` float NOT NULL COMMENT '折扣价格',
  `img_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '图片id',
  `kccnt` int(11) DEFAULT '0' COMMENT '库存',
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '描述内容',
  `status` int(2) DEFAULT '1' COMMENT '1上架 0下架 -1删除',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `category_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分类id，目前一对一关系',
  `unit` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商品单位 0份 1杯 2包 3件 4打 5半打 6瓶',
  PRIMARY KEY (`id`),
  KEY `t_tagId_imagId_shopId` (`shop_id`,`img_id`,`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品表';

-- ----------------------------
-- Records of t_goods
-- ----------------------------
INSERT INTO `t_goods` VALUES ('70b1d215e122ab0182b62eb98a525057', '53bcdf2893264db6afda26696a500e41', '雪碧', '5', '4', '21f855db321b49fc97d8a10838b62d63', '100', '雪的口感', '1', '2019-04-08 23:54:52', null, '5273afad6930f06cb63a4e13113520fd', '6');
INSERT INTO `t_goods` VALUES ('a1ac4181fa6e3e319ac8af0aee4ec8cb', '22ea0531f5aa4d49b6b7238e9e22957c', '大雪碧', '10', '8', '35978f754af2453e837a8509b7c73099', '100', '大雪的口感', '1', '2019-04-09 00:09:16', null, '5273afad6930f06cb63a4e13113520fe', '6');
INSERT INTO `t_goods` VALUES ('a702b6d2e0951ed8893832df3ebb81fe', 'a7a30102ce454a85b949f8008d6f63e0', '可乐', '5', '4.5', '44f395e6cef444d58656de99c3ef9619', '100', '可口可乐的口感', '1', '2019-04-08 23:13:04', null, '5273afad6930f06cb63a4e13113520fd', '6');
INSERT INTO `t_goods` VALUES ('b12b4867b38ddfbf3fce925ac49e98e8', 'testShopId', '雪碧', '12', '10', '1cd5e00450924ed49e545f830935f118', '100', 'string', '1', '2019-04-16 01:21:20', null, 'd7bf960e8951ff618c157ecbea5ad3bd', '0');

-- ----------------------------
-- Table structure for t_goods_category
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_category`;
CREATE TABLE `t_goods_category` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分类名称',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT '1' COMMENT '状态有效(1),无效(0),删除(-1)',
  `top_down_status` tinyint(4) DEFAULT '5' COMMENT '优先排序 9 置顶 5默认按最新来排 0 置底 可以+更新时间进行排序控制',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分类表，目前主要针对商品分类';

-- ----------------------------
-- Records of t_goods_category
-- ----------------------------
INSERT INTO `t_goods_category` VALUES ('d7bf960e8951ff618c157ecbea5ad3bd', '热门推荐', '2019-04-12 23:50:56', '2019-04-12 23:50:57', '1', '9');
INSERT INTO `t_goods_category` VALUES ('ee57963c22ad23201e60720c4a58f04f', '今日热推', '2019-04-12 23:50:57', '2019-04-12 23:50:58', '1', '9');

-- ----------------------------
-- Table structure for t_image
-- ----------------------------
DROP TABLE IF EXISTS `t_image`;
CREATE TABLE `t_image` (
  `id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `type` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `origin` tinyint(4) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `size` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_image
-- ----------------------------
INSERT INTO `t_image` VALUES ('1cd5e00450924ed49e545f830935f118', 'jpeg', null, null, null, '26558', '2019-04-14 21:41:35', null, 'login2.jpg', '400', '350');
INSERT INTO `t_image` VALUES ('a3c7970fbf044b5ebff7674e5534318b', 'jpeg', null, null, null, '323682', '2019-04-16 00:08:44', null, 'DSC_5808.jpg', '1428', '2087');
INSERT INTO `t_image` VALUES ('b3cd2daf689b4ab8954b13abb14853c9', 'jpeg', null, null, null, '24099', '2019-04-14 21:39:44', null, 'login2.jpg', '400', '300');
INSERT INTO `t_image` VALUES ('e9c29317845243ed80b79a51acd8f216', 'jpeg', null, null, null, '168128', '2019-04-14 21:39:07', null, 'login2.jpg', '960', '600');

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `shop_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '店铺id',
  `shop_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '店铺名称，冗余字段，便于显示',
  `shop_address` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '店铺地址，冗余字段，便于显示',
  `eat_type` tinyint(4) DEFAULT '1' COMMENT '1 堂吃 0外带',
  `goods_num` tinyint(4) DEFAULT '0' COMMENT '订单商品数量',
  `all_price` float DEFAULT '0' COMMENT '订单总价格',
  `discnt_price` float DEFAULT '0' COMMENT '折扣价格 减多少或打几折后减多少',
  `other_discnt_price` float DEFAULT '0' COMMENT '其他优惠价',
  `real_pay_price` float DEFAULT '0' COMMENT '实付价格',
  `remark` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '订单备注',
  `people_cnt` tinyint(4) DEFAULT '1' COMMENT '默认为1人用餐',
  `user_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '微信小程序用户系统id,非wxid',
  `groupbuy_id` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '拼团id',
  `coupon_id` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '使用优惠券的id,控制优惠券的状态',
  `status` tinyint(2) DEFAULT '0' COMMENT '普通订单类型:0待付款,1待确认,2待评价,3已完成(已评价);通用:4退款,-1删除,拼团类型:10拼团中,11拼团成功,12拼团失败',
  `order_no` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '订单编号',
  `order_type` tinyint(2) DEFAULT '0' COMMENT '0点餐订单 1拼团订单',
  `seat_no` int(10) DEFAULT '0' COMMENT '座位号，目前只录入数值 不计号,为了按数值排序',
  `queue_no` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '排队号',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '状态更新时间',
  `other_coupon_id` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '其他优惠价对应的优惠标识',
  PRIMARY KEY (`id`),
  KEY `shopId_userId_eatType_orderType` (`shop_id`,`user_id`,`eat_type`,`order_type`,`status`) USING BTREE,
  KEY `groupbuy_id` (`groupbuy_id`),
  KEY `couponId` (`coupon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- ----------------------------
-- Records of t_order
-- ----------------------------

-- ----------------------------
-- Table structure for t_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail`;
CREATE TABLE `t_order_detail` (
  `id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `order_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单id',
  `goods_desc` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '商品描述',
  `goods_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品名称',
  `goods_img_id` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '商品图片id',
  `goods_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品id',
  `all_price` float DEFAULT '0' COMMENT '订单总价格',
  `price` float DEFAULT '0' COMMENT '实际价格',
  `cnt` tinyint(4) DEFAULT '0' COMMENT '数量',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '订单状态更新时间',
  PRIMARY KEY (`id`),
  KEY `orderId_goodsId` (`order_id`,`goods_id`) COMMENT '订单+商品id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单商品列表';

-- ----------------------------
-- Records of t_order_detail
-- ----------------------------

-- ----------------------------
-- Table structure for t_tags
-- ----------------------------
DROP TABLE IF EXISTS `t_tags`;
CREATE TABLE `t_tags` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标签名称',
  `type` int(2) NOT NULL DEFAULT '1' COMMENT '1为商品类型 0为订单类 -1为删除类',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `priority_level` int(5) DEFAULT '0' COMMENT '定义标签优先级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标签表，目前主要针对商品分类';

-- ----------------------------
-- Records of t_tags
-- ----------------------------
INSERT INTO `t_tags` VALUES ('5273afad6930f06cb63a4e13113520fd', '热门推荐', '1', '2019-04-08 22:34:27', null, '2');
INSERT INTO `t_tags` VALUES ('5273afad6930f06cb63a4e13113520fe', '今日优惠', '1', '2019-04-08 22:34:27', null, '1');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名称',
  `img_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户头像id，无默认取..需定义',
  `wxid` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '微信id',
  `longitude` float DEFAULT '0' COMMENT '经度',
  `latitude` float DEFAULT '0' COMMENT '纬度',
  `status` tinyint(2) DEFAULT '1' COMMENT ' 在线(0),有效(1),无效(2),禁用(3),拉黑(4)',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '状态更新时间',
  PRIMARY KEY (`id`),
  KEY `wxid` (`wxid`),
  KEY `name_imgId_status` (`status`,`img_id`,`name`) USING BTREE,
  KEY `long_lat` (`longitude`,`latitude`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='微信用户信息';

-- ----------------------------
-- Records of t_user
-- ----------------------------
