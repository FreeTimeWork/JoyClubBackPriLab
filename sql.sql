-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.12-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 joyclub 的数据库结构
DROP DATABASE IF EXISTS `joyclub`;
CREATE DATABASE IF NOT EXISTS `joyclub` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `joyclub`;

-- 导出  表 joyclub.client_address 结构
DROP TABLE IF EXISTS `client_address`;
CREATE TABLE IF NOT EXISTS `client_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `name` varchar(20) DEFAULT NULL COMMENT '收货人姓名',
  `phone` varchar(20) DEFAULT NULL COMMENT '收货人手机号',
  `address` varchar(100) DEFAULT NULL COMMENT '收获人地址',
  `postcode` varchar(10) DEFAULT NULL COMMENT '收货人邮编',
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户收货地址表';

-- 数据导出被取消选择。
-- 导出  表 joyclub.sale_cart 结构
DROP TABLE IF EXISTS `sale_cart`;
CREATE TABLE IF NOT EXISTS `sale_cart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `client_id` bigint(20) NOT NULL COMMENT '客户id',
  `attr_id` bigint(20) NOT NULL COMMENT '商品属性id',
  `num` int(11) NOT NULL COMMENT '数量',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='购物车表';

-- 数据导出被取消选择。
-- 导出  表 joyclub.sale_order 结构
DROP TABLE IF EXISTS `sale_order`;
CREATE TABLE IF NOT EXISTS `sale_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL COMMENT '目前是nanoTime加三位随机数',
  `client_id` bigint(20) NOT NULL COMMENT '客户id',
  `receive_type` tinyint(4) NOT NULL COMMENT '收货类型 0自提 1快递',
  `status` tinyint(4) NOT NULL COMMENT '0 待支付 1已取消 2 已支付 3已发货  4 已收货 5 退款中 6已退款',
  `money_sum` int(11) NOT NULL COMMENT '用户为该单所需支付的总金钱，注意是订单下所有选择现金支付的商品金钱总额',
  `point_sum` int(11) NOT NULL COMMENT '用户为该单所需支付的总积分，注意是订单下所有选择积分支付的商品积分总额',
  `pay_type` tinyint(4) DEFAULT NULL COMMENT '0微信 1支付宝',
  `pay_id` bigint(20) DEFAULT NULL COMMENT '支付成功的结果单',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `cancel_time` datetime DEFAULT NULL COMMENT '取消时间',
  `canceler` tinyint(4) DEFAULT NULL COMMENT '用来标记谁取消了，0是用户，1是系统自动（用于系统定时删除未支付订单）',
  `delivery_time` datetime DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime DEFAULT NULL COMMENT '收获时间（自提和快递）',
  `notice_refund_time` datetime DEFAULT NULL COMMENT '商户确认退款时间',
  `refund_time` datetime DEFAULT NULL COMMENT '财务退款时间',
  `receiver_name` varchar(20) DEFAULT NULL COMMENT '如果是快递，则提供收货者姓名',
  `receiver_phone` varchar(20) DEFAULT NULL COMMENT '如果是快递，则提供收货者手机号',
  `receiver_address` varchar(100) DEFAULT NULL COMMENT '如果是快递，则提供收货者地址',
  `receive_postcode` varchar(10) DEFAULT NULL COMMENT '如果是快递，则提供收货者邮编',
  `delivery_company` varchar(20) DEFAULT NULL COMMENT '如果是快递，并且发货，快递公司名',
  `delivery_code` varchar(50) DEFAULT NULL COMMENT '如果是快递，并且发货，快递单号',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表\r\n系统定时删除未支付订单，恢复库存，防止库存一直被占用\r\n记录各个阶段时间\r\n';

-- 数据导出被取消选择。
-- 导出  表 joyclub.sale_order_detail 结构
DROP TABLE IF EXISTS `sale_order_detail`;
CREATE TABLE IF NOT EXISTS `sale_order_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL COMMENT '订单号',
  `product_attr` bigint(20) NOT NULL COMMENT '商品属性id',
  `num` int(11) NOT NULL COMMENT '购买数量',
  `price` int(11) NOT NULL COMMENT '购买时的价格',
  `pointRate` float NOT NULL COMMENT '购买时的积分比例',
  `money_paid` int(11) NOT NULL COMMENT '为该单支付的金钱，和积分二者有一个为0',
  `point_paid` int(11) NOT NULL COMMENT '为该单支付的积分，和积分二者有一个为0',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单详情表';

-- 数据导出被取消选择。
-- 导出  表 joyclub.sale_product 结构
DROP TABLE IF EXISTS `sale_product`;
CREATE TABLE IF NOT EXISTS `sale_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `store_id` bigint(20) NOT NULL COMMENT '商户id',
  `category_id` bigint(20) NOT NULL COMMENT '分类id',
  `designer_id` bigint(20) DEFAULT NULL COMMENT '设计师id，可以为空',
  `name` varchar(20) DEFAULT NULL COMMENT '商品名',
  `base_price` int(11) DEFAULT NULL COMMENT '基础价格',
  `portrait` varchar(200) DEFAULT NULL COMMENT '封面图',
  `carousel` json DEFAULT NULL COMMENT '轮播图,JSON数组',
  `html_content` mediumtext COMMENT '商品详情在React draftJs里的data对象',
  `json_content` mediumtext COMMENT '商品详情的html',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
-- 导出  表 joyclub.sale_product_attr 结构
DROP TABLE IF EXISTS `sale_product_attr`;
CREATE TABLE IF NOT EXISTS `sale_product_attr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) NOT NULL COMMENT '商品id',
  `name` varchar(20) NOT NULL COMMENT '商品属性名称',
  `num` int(11) NOT NULL DEFAULT '0' COMMENT '数量',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='商品的属性表，基于属性（比如尺码、颜色，一维，如果同时有颜色、尺码，需要展开为一维）进行库存管理\r\n';

-- 数据导出被取消选择。
-- 导出  表 joyclub.sale_product_price 结构
DROP TABLE IF EXISTS `sale_product_price`;
CREATE TABLE IF NOT EXISTS `sale_product_price` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) NOT NULL COMMENT '商品id',
  `price` int(11) NOT NULL COMMENT '价格',
  `point_rate` float DEFAULT NULL COMMENT '消费金钱后，兑换积分比例 如果不赋值则继承商品分类的比例，也可以赋值',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `forbid_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '下架标志',
  `review_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '审核状态 0 待审核，1 审核通过 ，2 审核拒绝',
  `review_info` varchar(50) DEFAULT NULL COMMENT '审核相关文本，主要用于拒绝，无法记录历史',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品价格管理\r\nstartTime endTime 决定了生效时间，forbid_flag用于强行下架';

-- 数据导出被取消选择。
-- 导出  表 joyclub.sale_store_designer 结构
DROP TABLE IF EXISTS `sale_store_designer`;
CREATE TABLE IF NOT EXISTS `sale_store_designer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `intro` varchar(100) DEFAULT NULL COMMENT '简短介绍',
  `html_content` mediumtext COMMENT '详细介绍的html',
  `json_content` mediumtext COMMENT '详细介绍的dractjs json',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delate_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户设计师';

-- 数据导出被取消选择。
-- 导出  表 joyclub.sys_product_category 结构
DROP TABLE IF EXISTS `sys_product_category`;
CREATE TABLE IF NOT EXISTS `sys_product_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `point_rate` float NOT NULL COMMENT '积分兑换比例',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delate_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品分类';

-- 数据导出被取消选择。
-- 导出  表 joyclub.sys_project 结构
DROP TABLE IF EXISTS `sys_project`;
CREATE TABLE IF NOT EXISTS `sys_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `headquarters` bigint(20) DEFAULT NULL COMMENT '留给总部，目前都为27',
  `type` int(11) NOT NULL COMMENT '项目类型  1 商业项目 2写字楼项目项目 3 住宅项目 10 第三方合作项目',
  `name` varchar(20) DEFAULT NULL COMMENT '项目名称',
  `code` varchar(10) DEFAULT NULL COMMENT '门店编号',
  `vip_share` varchar(10) DEFAULT NULL COMMENT 'vip分摊（实际是对应的科传的值）',
  `card_resource_project` varchar(10) DEFAULT NULL COMMENT '发卡项目编码，现在都是朝阳大悦城',
  `address` varchar(20) DEFAULT NULL COMMENT '地址',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `contact_name` varchar(20) DEFAULT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系人号码',
  `wechat_app_id` varchar(50) DEFAULT NULL COMMENT '微信公众号appid',
  `wechat_app_secret` varchar(50) DEFAULT NULL COMMENT '微信公众号appsecreat',
  `wechat_token_address` varchar(100) DEFAULT NULL COMMENT '微信公众号accesstoken获取地址',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='系统项目表 1 商业项目 2写字楼项目项目 3 住宅项目 10 第三方合作项目';

-- 数据导出被取消选择。
-- 导出  表 joyclub.sys_store 结构
DROP TABLE IF EXISTS `sys_store`;
CREATE TABLE IF NOT EXISTS `sys_store` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL COMMENT '所属的商业项目',
  `name` varchar(20) DEFAULT NULL COMMENT '商户名',
  `contact_name` varchar(20) DEFAULT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系人号码',
  `pickup_address` varchar(50) DEFAULT NULL COMMENT '取货地址',
  `service_phone` varchar(20) DEFAULT NULL COMMENT '客服电话',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户名，归属于商城项目';

-- 数据导出被取消选择。
-- 导出  表 joyclub.sys_user 结构
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `info_id` bigint(20) unsigned DEFAULT NULL COMMENT '关联者id,项目账户关联项目id,商户账户关联商户id',
  `account` varchar(15) NOT NULL COMMENT '账户',
  `password` varchar(32) NOT NULL COMMENT '密码 32位MD5加密',
  `type` int(11) NOT NULL COMMENT '账户类型（1：平台，2：项目账户，3：商户账户）',
  `sub_type` int(11) DEFAULT NULL COMMENT '账户子类型，预留',
  `project_type` int(11) DEFAULT NULL COMMENT '项目类型，当是项目账户时，需赋值。',
  `forbid_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '禁止使用标志，默认不禁止',
  `forbid_time` datetime DEFAULT NULL COMMENT '最近一次禁用时间',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
