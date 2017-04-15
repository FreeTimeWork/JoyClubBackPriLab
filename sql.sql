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

-- 导出  表 joyclub.client 结构
DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `real_name` varchar(15) DEFAULT NULL COMMENT '真实姓名',
  `sex` varchar(15) DEFAULT NULL COMMENT '性别',
  `status` varchar(15) DEFAULT NULL COMMENT '用户状态',
  `tel` varchar(15) NOT NULL COMMENT '联系电话',
  `type` varchar(15) DEFAULT NULL COMMENT '用户类型 预留',
  `birthday` bigint(20) DEFAULT NULL COMMENT '生日',
  `vip_grade` int(11) DEFAULT NULL COMMENT '会员等级 目前似乎没用',
  `vip_point` int(11) DEFAULT '0' COMMENT '会员积分',
  `card_no` varchar(32) DEFAULT NULL COMMENT '会员卡号',
  `id_card` varchar(32) DEFAULT NULL COMMENT '会员身份证号码',
  `vip_code` varchar(32) DEFAULT NULL COMMENT '会员号',
  `vip_card_grade` varchar(4) DEFAULT NULL COMMENT '会员卡等级',
  `edu_his` varchar(8) DEFAULT NULL COMMENT '学历',
  `wx_city` varchar(50) DEFAULT NULL COMMENT '微信信息所在城市',
  `wx_country` varchar(50) DEFAULT NULL COMMENT '微信信息所在国家',
  `wx_gender` varchar(10) DEFAULT NULL COMMENT '微信信息性别',
  `wx_head_img_url` varchar(250) DEFAULT NULL COMMENT '微信信息头像',
  `wx_language` varchar(50) DEFAULT NULL COMMENT '微信信息语言',
  `wx_nick_name` varchar(50) DEFAULT NULL COMMENT '微信信息昵称',
  `wx_province` varchar(50) DEFAULT NULL COMMENT '微信信息省份',
  `credit_card_project` varchar(16) DEFAULT NULL COMMENT '会员的发卡项目',
  `group13` varchar(32) DEFAULT NULL COMMENT 'CRM的会员分摊号',
  `home_address` varchar(64) DEFAULT NULL COMMENT '家庭住址',
  `home_post_code` varchar(8) DEFAULT NULL COMMENT '家庭住址邮编',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='沿用商通的设计';

-- 数据导出被取消选择。
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
-- 导出  表 joyclub.client_wx_openid 结构
DROP TABLE IF EXISTS `client_wx_openid`;
CREATE TABLE IF NOT EXISTS `client_wx_openid` (
  `open_id` varchar(32) NOT NULL COMMENT '微信 openid',
  `client_id` bigint(20) NOT NULL COMMENT '客户id',
  `project_id` bigint(20) NOT NULL COMMENT '项目id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户和项目相关的微信openid';

-- 数据导出被取消选择。
-- 导出  表 joyclub.coupon 结构
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE IF NOT EXISTS `coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL COMMENT '项目id',
  `name` varchar(50) NOT NULL COMMENT '卡券名称',
  `type` int(11) NOT NULL COMMENT '类别，1 系统自身卡券 2 第三方卡券',
  `show_start_time` datetime NOT NULL COMMENT '开始投放时间',
  `show_end_time` datetime NOT NULL COMMENT '结束投放时间',
  `available_start_time` datetime NOT NULL COMMENT '有效期开始时间',
  `available_end_time` datetime NOT NULL COMMENT '有效期结束时间',
  `point_cost` int(11) NOT NULL COMMENT '领卡券所需积分',
  `num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '总数，应该根据导入来及时更新',
  `portrait` varchar(200) DEFAULT NULL COMMENT '卡券图片',
  `info` varchar(500) DEFAULT NULL COMMENT '卡券介绍',
  `use_flag` tinyint(1) DEFAULT '0' COMMENT '开始投放标志，默认未投放',
  `use_time` datetime DEFAULT NULL COMMENT '开始投放时间',
  `forbid_flag` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '强制下架标志',
  `forbid_time` datetime DEFAULT NULL COMMENT '强制下架时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='卡券表，优惠券等';

-- 数据导出被取消选择。
-- 导出  表 joyclub.coupon_card_type 结构
DROP TABLE IF EXISTS `coupon_card_type`;
CREATE TABLE IF NOT EXISTS `coupon_card_type` (
  `coupon_id` bigint(20) NOT NULL COMMENT '卡券id',
  `card_type` varchar(10) NOT NULL COMMENT '允许领取的会员卡类型'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='能领取卡券的会员卡类型表';

-- 数据导出被取消选择。
-- 导出  表 joyclub.coupon_code 结构
DROP TABLE IF EXISTS `coupon_code`;
CREATE TABLE IF NOT EXISTS `coupon_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `coupon_id` bigint(20) NOT NULL COMMENT '卡券id',
  `code` varchar(50) NOT NULL COMMENT '卡券号码',
  `use_status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否被领取',
  `use_time` datetime DEFAULT NULL COMMENT '领取时间',
  `client_id` bigint(20) DEFAULT NULL COMMENT '领取人的clientId',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10105 DEFAULT CHARSET=utf8 COMMENT='卡券号码表';

-- 数据导出被取消选择。
-- 导出  表 joyclub.sale_act 结构
DROP TABLE IF EXISTS `sale_act`;
CREATE TABLE IF NOT EXISTS `sale_act` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `store_id` bigint(20) NOT NULL COMMENT '商户id',
  `category_id` bigint(20) NOT NULL COMMENT '分类id',
  `name` varchar(20) DEFAULT NULL COMMENT '商品名',
  `display_weight` int(11) DEFAULT '0' COMMENT '显示权重，默认为0，越大越靠前',
  `base_price` int(11) DEFAULT NULL COMMENT '基础价格',
  `portrait` varchar(200) DEFAULT NULL COMMENT '封面图',
  `carousel` varchar(800) DEFAULT NULL COMMENT '轮播图,JSON数组',
  `refund_forbid_flag` tinyint(1) DEFAULT '0' COMMENT '不可退货标志',
  `reserve_need_flag` tinyint(1) DEFAULT '0' COMMENT '需要预约标志',
  `delivery_flag` tinyint(1) DEFAULT '0' COMMENT '需要快递物品标志',
  `html_content` mediumtext COMMENT '商品详情在React draftJs里的data对象',
  `json_content` mediumtext COMMENT '商品详情的html',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='活动表';

-- 数据导出被取消选择。
-- 导出  表 joyclub.sale_act_attr 结构
DROP TABLE IF EXISTS `sale_act_attr`;
CREATE TABLE IF NOT EXISTS `sale_act_attr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `act_id` bigint(20) NOT NULL COMMENT '活动id',
  `name` varchar(20) NOT NULL COMMENT '活动属性名称',
  `num` int(11) NOT NULL DEFAULT '0' COMMENT '数量',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='活动属性表，同商品属性';

-- 数据导出被取消选择。
-- 导出  表 joyclub.sale_act_price 结构
DROP TABLE IF EXISTS `sale_act_price`;
CREATE TABLE IF NOT EXISTS `sale_act_price` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `act_id` bigint(20) NOT NULL COMMENT '互动id',
  `price` int(11) NOT NULL COMMENT '价格',
  `point_rate` float DEFAULT NULL COMMENT '消费金钱后，兑换积分比例 如果不赋值则继承商品分类的比例，也可以赋值',
  `start_time` datetime(3) NOT NULL COMMENT '开始时间',
  `end_time` datetime(3) NOT NULL COMMENT '结束时间',
  `forbid_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '下架标志',
  `review_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '审核状态 0 待审核，1 审核通过 ，2 审核拒绝',
  `review_info` varchar(50) DEFAULT NULL COMMENT '审核相关文本，主要用于拒绝，无法记录历史',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='活动价格表，同商品价格';

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品购物车表';

-- 数据导出被取消选择。
-- 导出  表 joyclub.sale_product 结构
DROP TABLE IF EXISTS `sale_product`;
CREATE TABLE IF NOT EXISTS `sale_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `store_id` bigint(20) NOT NULL COMMENT '商户id',
  `category_id` bigint(20) NOT NULL COMMENT '分类id',
  `designer_id` bigint(20) DEFAULT NULL COMMENT '设计师id，可以为空',
  `name` varchar(20) DEFAULT NULL COMMENT '商品名',
  `display_weight` int(11) DEFAULT '0' COMMENT '显示权重，默认为0，越大越靠前',
  `base_price` int(11) DEFAULT NULL COMMENT '基础价格',
  `portrait` varchar(200) DEFAULT NULL COMMENT '封面图',
  `carousel` varchar(800) DEFAULT NULL COMMENT '轮播图,JSON数组',
  `html_content` mediumtext COMMENT '商品详情在React draftJs里的data对象',
  `json_content` mediumtext COMMENT '商品详情的html',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='商品的属性表，基于属性（比如尺码、颜色，一维，如果同时有颜色、尺码，需要展开为一维）进行库存管理\r\n';

-- 数据导出被取消选择。
-- 导出  表 joyclub.sale_product_order 结构
DROP TABLE IF EXISTS `sale_product_order`;
CREATE TABLE IF NOT EXISTS `sale_product_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) NOT NULL COMMENT '目前是nanoTime加三位随机数',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品订单表\r\n系统定时删除未支付订单，恢复库存，防止库存一直被占用\r\n记录各个阶段时间\r\n';

-- 数据导出被取消选择。
-- 导出  表 joyclub.sale_product_order_detail 结构
DROP TABLE IF EXISTS `sale_product_order_detail`;
CREATE TABLE IF NOT EXISTS `sale_product_order_detail` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品订单详情表';

-- 数据导出被取消选择。
-- 导出  表 joyclub.sale_product_price 结构
DROP TABLE IF EXISTS `sale_product_price`;
CREATE TABLE IF NOT EXISTS `sale_product_price` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) NOT NULL COMMENT '商品id',
  `price` int(11) NOT NULL COMMENT '价格',
  `point_rate` float DEFAULT NULL COMMENT '消费金钱后，兑换积分比例 如果不赋值则继承商品分类的比例，也可以赋值',
  `start_time` datetime(3) NOT NULL COMMENT '开始时间',
  `end_time` datetime(3) NOT NULL COMMENT '结束时间',
  `forbid_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '下架标志',
  `review_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '审核状态 0 待审核，1 审核通过 ，2 审核拒绝',
  `review_info` varchar(50) DEFAULT NULL COMMENT '审核相关文本，主要用于拒绝，无法记录历史',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='商品价格管理\r\nstartTime endTime 决定了生效时间，forbid_flag用于强行下架';

-- 数据导出被取消选择。
-- 导出  表 joyclub.sale_store_designer 结构
DROP TABLE IF EXISTS `sale_store_designer`;
CREATE TABLE IF NOT EXISTS `sale_store_designer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `store_id` bigint(20) NOT NULL COMMENT '商户id',
  `head_img` varchar(200) DEFAULT NULL COMMENT '顶部背景',
  `portrait` varchar(200) DEFAULT NULL COMMENT '头像',
  `name` varchar(20) DEFAULT NULL,
  `intro` varchar(200) DEFAULT NULL COMMENT '简短介绍',
  `html_content` mediumtext COMMENT '详细介绍的html',
  `json_content` mediumtext COMMENT '详细介绍的dractjs json',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delate_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='商户设计师';

-- 数据导出被取消选择。
-- 导出  表 joyclub.sys_act_category 结构
DROP TABLE IF EXISTS `sys_act_category`;
CREATE TABLE IF NOT EXISTS `sys_act_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `point_rate` float NOT NULL COMMENT '积分兑换比例',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delate_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='活动分类';

-- 数据导出被取消选择。
-- 导出  表 joyclub.sys_msg_auth_code 结构
DROP TABLE IF EXISTS `sys_msg_auth_code`;
CREATE TABLE IF NOT EXISTS `sys_msg_auth_code` (
  `phone` varchar(15) NOT NULL COMMENT '手机号码',
  `code` varchar(15) NOT NULL COMMENT '验证码，目前是6位',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信验证码表';

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='商品分类';

-- 数据导出被取消选择。
-- 导出  表 joyclub.sys_project 结构
DROP TABLE IF EXISTS `sys_project`;
CREATE TABLE IF NOT EXISTS `sys_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL COMMENT '项目类型 0 平台 1 商业项目 2写字楼项目项目 3 住宅项目 10 第三方合作项目',
  `name` varchar(20) DEFAULT NULL COMMENT '项目名称',
  `headquarters` varchar(10) DEFAULT NULL COMMENT '留给总部，目前都为27',
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='系统项目表 1 商业项目 2写字楼项目项目 3 住宅项目 10 第三方合作项目  0 平台（平台也当作一个项目来处理，但是平台类型项目有且只能有一个）';

-- 数据导出被取消选择。
-- 导出  表 joyclub.sys_project_vip_card_range 结构
DROP TABLE IF EXISTS `sys_project_vip_card_range`;
CREATE TABLE IF NOT EXISTS `sys_project_vip_card_range` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL,
  `type` varchar(10) NOT NULL COMMENT '号段类型 01璀璨卡 02缤纷卡 74电子卡',
  `min` bigint(20) NOT NULL COMMENT '号段开始值',
  `max` bigint(20) NOT NULL COMMENT '号段结束值',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delate_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='项目卡号段';

-- 数据导出被取消选择。
-- 导出  表 joyclub.sys_store 结构
DROP TABLE IF EXISTS `sys_store`;
CREATE TABLE IF NOT EXISTS `sys_store` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL COMMENT '所属的商业项目或者平台项目',
  `head_img` varchar(200) DEFAULT NULL COMMENT '顶部背景',
  `portrait` varchar(200) DEFAULT NULL COMMENT '头像',
  `intro` varchar(500) DEFAULT NULL COMMENT '介绍',
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='商户表，归属于商城项目或者平台';

-- 数据导出被取消选择。
-- 导出  表 joyclub.sys_user 结构
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `info_id` bigint(20) unsigned DEFAULT NULL COMMENT '关联者id,平台或项目账户关联项目id,商户账户关联商户id',
  `account` varchar(15) NOT NULL COMMENT '账户',
  `password` varchar(32) NOT NULL COMMENT '密码 32位MD5加密',
  `type` int(11) NOT NULL COMMENT '账户类型（1：平台，2：项目账户，3：商户账户）',
  `sub_type` int(11) DEFAULT NULL COMMENT '账户子类型，如果是项目账户，则是提供项目类型',
  `auth_type` int(11) DEFAULT NULL COMMENT '账户权限类型，预留',
  `forbid_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '禁止使用标志，默认不禁止',
  `forbid_time` datetime DEFAULT NULL COMMENT '最近一次禁用时间',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- 数据导出被取消选择。
-- 导出  表 joyclub.sys_vip_card_num 结构
DROP TABLE IF EXISTS `sys_vip_card_num`;
CREATE TABLE IF NOT EXISTS `sys_vip_card_num` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL COMMENT '号段开始值',
  `num` bigint(20) NOT NULL,
  `type` varchar(10) NOT NULL COMMENT '号段类型 01璀璨卡 02缤纷卡 74电子卡',
  `batch` varchar(50) NOT NULL COMMENT '批次号',
  `status` tinyint(4) NOT NULL COMMENT '状态',
  `use_time` datetime DEFAULT NULL COMMENT '卡领取时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delate_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='vip卡号\r\n';

-- 数据导出被取消选择。
-- 导出  表 joyclub.test 结构
DROP TABLE IF EXISTS `test`;
CREATE TABLE IF NOT EXISTS `test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `test` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='test';

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
