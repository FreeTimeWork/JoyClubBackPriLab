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
CREATE DATABASE IF NOT EXISTS `joyclub` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `joyclub`;

-- 导出  表 joyclub.client 结构
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='沿用商通的设计';

-- 正在导出表  joyclub.client 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` (`id`, `real_name`, `sex`, `status`, `tel`, `type`, `birthday`, `vip_grade`, `vip_point`, `card_no`, `id_card`, `vip_code`, `vip_card_grade`, `edu_his`, `wx_city`, `wx_country`, `wx_gender`, `wx_head_img_url`, `wx_language`, `wx_nick_name`, `wx_province`, `credit_card_project`, `group13`, `home_address`, `home_post_code`, `create_time`, `last_update`, `delete_flag`, `delete_time`) VALUES
	(2, '王俊', NULL, NULL, '15001060933', NULL, NULL, NULL, 29027, '1010100020010004', NULL, '0500307360', '74', NULL, NULL, '中国', '1', 'http://wx.qlogo.cn/mmopen/rzhskjsG3Qz2343T1gAjSKh9geuwdsGT9BKyhgKK1RcVghSTDKdruRhTNWib3tMkAWpoh7k9A5cKzsFJQWQrsN8Lf1A4UunFc/0', 'zh_CN', 'Til', NULL, '05', '00', NULL, NULL, '2017-04-11 16:01:34', '2017-05-08 17:49:56', 0, NULL);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;

-- 导出  表 joyclub.client_login_log 结构
CREATE TABLE IF NOT EXISTS `client_login_log` (
  `client_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `time` datetime DEFAULT CURRENT_TIMESTAMP,
  `sub_project_id` bigint(20) DEFAULT NULL COMMENT '当是其他项目使用平台商城的时候，需要提供其他项目的id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户登录日志\r\n';

-- 正在导出表  joyclub.client_login_log 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `client_login_log` DISABLE KEYS */;
INSERT INTO `client_login_log` (`client_id`, `project_id`, `time`, `sub_project_id`) VALUES
	(2, 1, '2017-04-17 00:55:21', NULL);
/*!40000 ALTER TABLE `client_login_log` ENABLE KEYS */;

-- 导出  表 joyclub.client_post_address 结构
CREATE TABLE IF NOT EXISTS `client_post_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `client_id` bigint(20) NOT NULL,
  `name` varchar(20) DEFAULT NULL COMMENT '收货人姓名',
  `phone` varchar(20) DEFAULT NULL COMMENT '收货人手机号',
  `address` varchar(100) DEFAULT NULL COMMENT '收获人地址',
  `postcode` varchar(10) DEFAULT NULL COMMENT '收货人邮编',
  `last_use_time` datetime DEFAULT NULL COMMENT '最后使用时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='客户收货地址表';

-- 正在导出表  joyclub.client_post_address 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `client_post_address` DISABLE KEYS */;
INSERT INTO `client_post_address` (`id`, `client_id`, `name`, `phone`, `address`, `postcode`, `last_use_time`, `create_time`, `last_update`, `delete_flag`, `delete_time`) VALUES
	(7, 2, '测试', '12311231231', '12312312312312312313', NULL, NULL, '2017-04-20 00:30:59', '2017-04-20 00:30:59', 0, NULL),
	(8, 2, '123', '12312312312', '123', NULL, NULL, '2017-04-20 04:23:42', '2017-04-20 04:23:42', 0, NULL),
	(9, 2, '1', '12312312312', '123123123', NULL, NULL, '2017-04-20 04:47:32', '2017-04-20 04:47:32', 0, NULL);
/*!40000 ALTER TABLE `client_post_address` ENABLE KEYS */;

-- 导出  表 joyclub.client_wx_openid 结构
CREATE TABLE IF NOT EXISTS `client_wx_openid` (
  `open_id` varchar(32) NOT NULL COMMENT '微信 openid',
  `client_id` bigint(20) NOT NULL COMMENT '客户id',
  `project_id` bigint(20) NOT NULL COMMENT '项目id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户和项目相关的微信openid\r\nz注意一个项目一个客户只能有一个微信openid';

-- 正在导出表  joyclub.client_wx_openid 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `client_wx_openid` DISABLE KEYS */;
INSERT INTO `client_wx_openid` (`open_id`, `client_id`, `project_id`, `create_time`, `last_update`) VALUES
	('oBDYawi1UjKoNXeDDeunHbqUn3As', 2, 1, '2017-04-11 15:05:36', '2017-04-20 09:46:52');
/*!40000 ALTER TABLE `client_wx_openid` ENABLE KEYS */;

-- 导出  表 joyclub.coupon 结构
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='卡券表，优惠券等';

-- 正在导出表  joyclub.coupon 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` (`id`, `project_id`, `name`, `type`, `show_start_time`, `show_end_time`, `available_start_time`, `available_end_time`, `point_cost`, `num`, `portrait`, `info`, `use_flag`, `use_time`, `forbid_flag`, `forbid_time`, `create_time`, `last_update`, `delete_flag`, `delete_time`) VALUES
	(1, 1, '1', 2, '2017-04-12 22:04:49', '2017-04-12 22:04:50', '2017-04-12 22:04:52', '2017-05-12 22:04:53', 10000000, 1, 'null', '活动日期：2017年4月28日—2017年5月31日;  \r\n活动内容：新注册会员免费领取500积分，每位用户限领一次：\r\n注意事项：积分会在3个工作日内充入您的账户。', 1, '2017-04-20 13:08:13', 0, '2017-04-20 13:14:57', '2017-04-12 22:04:56', '2017-04-28 16:42:27', 0, NULL),
	(2, 1, '2', 2, '2017-04-20 00:00:00', '2018-05-25 00:00:00', '2017-04-13 00:00:00', '2018-05-17 00:00:00', 1, 27, 'http://bjmallback.stcl365.com:8071/image/507554daf8fa433da4b1cdf0813a2980.jpg', '活动日期：2017年4月28日—2017年5月31日;  ', 1, '2017-04-20 13:12:35', 0, '2017-04-20 15:33:52', '2017-04-20 12:48:47', '2017-04-28 18:02:16', 0, NULL),
	(3, 1, '3', 2, '2017-04-20 00:00:00', '2017-05-25 00:00:00', '2017-04-13 00:00:00', '2017-05-17 00:00:00', 1, 24, 'http://bjmallback.stcl365.com:8071/image/507554daf8fa433da4b1cdf0813a2980.jpg', '活动日期：2017年4月28日—2017年5月31日;  \r\n活动内容：新注册会员免费领取500积分，每位用户限领一次：\r\n注意事项：积分会在3个工作日内充入您的账户。', 1, '2017-04-20 13:12:35', 0, '2017-04-20 15:33:52', '2017-04-20 12:48:47', '2017-04-28 16:42:26', 0, NULL),
	(4, 1, 'test', 2, '2017-04-06 00:00:00', '2017-04-06 23:59:59', '2017-04-13 00:00:00', '2017-04-13 23:59:59', 1, 0, 'http://bjmallback.stcl365.com:8071/image/f6e22f834fb44bdfa753aa8194a5ab67.jpg', '活动日期：2017年4月28日—2017年5月31日;  \r\n活动内容：新注册会员免费领取500积分，每位用户限领一次：\r\n注意事项：积分会在3个工作日内充入您的账户。', 0, NULL, 0, NULL, '2017-04-24 11:09:23', '2017-04-28 16:42:29', 0, NULL),
	(5, 1, '11123', 2, '2017-04-26 00:00:00', '2017-04-26 23:59:59', '2017-04-26 00:00:00', '2017-04-26 23:59:59', 1, 0, 'http://bjmallback.stcl365.com:8071/image/dc8ea758cc174cc3b6fd2d6f4ae88637.jpg', '活动日期：2017年4月28日—2017年5月31日;  \r\n活动内容：新注册会员免费领取500积分，每位用户限领一次：\r\n注意事项：积分会在3个工作日内充入您的账户。', 0, NULL, 0, NULL, '2017-04-24 12:49:43', '2017-04-28 16:42:28', 0, NULL);
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;

-- 导出  表 joyclub.coupon_card_type 结构
CREATE TABLE IF NOT EXISTS `coupon_card_type` (
  `coupon_id` bigint(20) NOT NULL COMMENT '卡券id',
  `card_type` varchar(10) NOT NULL COMMENT '允许领取的会员卡类型'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='能领取卡券的会员卡类型表';

-- 正在导出表  joyclub.coupon_card_type 的数据：~7 rows (大约)
/*!40000 ALTER TABLE `coupon_card_type` DISABLE KEYS */;
INSERT INTO `coupon_card_type` (`coupon_id`, `card_type`) VALUES
	(2, '01'),
	(2, '02'),
	(3, '74'),
	(2, '74'),
	(4, '74'),
	(5, '74'),
	(5, '01');
/*!40000 ALTER TABLE `coupon_card_type` ENABLE KEYS */;

-- 导出  表 joyclub.coupon_code 结构
CREATE TABLE IF NOT EXISTS `coupon_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `coupon_id` bigint(20) NOT NULL COMMENT '卡券id',
  `code` varchar(50) NOT NULL COMMENT '卡券号码',
  `use_status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否被领取',
  `use_time` datetime DEFAULT NULL COMMENT '领取时间',
  `client_id` bigint(20) DEFAULT NULL COMMENT '领取人的clientId',
  `check_flag` tinyint(1) DEFAULT '0' COMMENT '核销标志',
  `checker_id` bigint(20) DEFAULT NULL COMMENT '核销人id，记录核销的管理员id',
  `check_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='卡券号码表';

-- 正在导出表  joyclub.coupon_code 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `coupon_code` DISABLE KEYS */;
INSERT INTO `coupon_code` (`id`, `coupon_id`, `code`, `use_status`, `use_time`, `client_id`, `check_flag`, `checker_id`, `check_time`, `create_time`, `last_update`, `delete_flag`, `delete_time`) VALUES
	(4, 2, '0', 1, '2017-04-21 21:31:49', 2, 1, 2, '2017-04-21 21:50:30', '2017-04-20 14:23:43', '2017-04-21 21:50:30', 0, NULL);
/*!40000 ALTER TABLE `coupon_code` ENABLE KEYS */;

-- 导出  过程 joyclub.p_update_product_price_point 结构
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `p_update_product_price_point`()
    COMMENT '修改sal_product_price的积分比例，大于500为继承分类null，150~500为120，小于150为150'
BEGIN
	declare p_id bigint;
	declare p_price int;
	declare p_pointRate int;
	declare p_tag int default 0;
	declare  cur cursor for select id,price from sale_product_price ;
	declare continue handler for not found  set p_tag=1;
	open cur;
		myloop:loop
			if p_tag=1 then leave myloop;end if;
			fetch cur into p_id,p_price;
			if p_price<=150  then set p_pointRate = 150;
			elseif p_price>150 and p_price<=500 then  set p_pointRate = 120;
			else set p_pointRate = null;
			end if;
			update sale_product_price set point_rate=p_pointRate where id =p_id;
		end loop;
	close cur;
END//
DELIMITER ;

-- 导出  表 joyclub.sale_act 结构
CREATE TABLE IF NOT EXISTS `sale_act` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `store_id` bigint(20) NOT NULL COMMENT '项目id',
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='活动表\r\n将活动上升到项目';

-- 正在导出表  joyclub.sale_act 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `sale_act` DISABLE KEYS */;
INSERT INTO `sale_act` (`id`, `store_id`, `category_id`, `name`, `display_weight`, `base_price`, `portrait`, `carousel`, `refund_forbid_flag`, `reserve_need_flag`, `delivery_flag`, `html_content`, `json_content`, `create_time`, `last_update`, `delete_flag`, `delete_time`) VALUES
	(1, 14, 1, 'test', 5, 1, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '["http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg"]', 0, 1, 0, '<p>爱要大声说出来，不要错过才后悔没有珍惜，对朋友的关心和呵护要用行动去证明，哪怕是一句问候，微信不该让彼此疏远，而是更亲近知道彼此的思念。</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/6fa221d8fee2406abdd64ea508cb387a.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/e28831b2bc1c4c4787a91dcb6d9023dc.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p>&nbsp;</p>\r\n<p>我们相识于干花</p>\r\n<p>体验押花香薰蜡过程</p>\r\n<p>制作简单漂亮的包装圆柱形礼物</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/d09dc0b6b03946fd88d94ae457762663.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<ol>\r\n<li>活动时长：3小时左右</li>\r\n<li>参与凭证：购买订单        </li>\r\n<li>活动时间：2017年2月25日至2017年5月31日 </li>\r\n<li>预约电话：15910816157</li>\r\n<li>参加活动时需提前三天预约</li>\r\n<li>团购后请致电预约参加时间和参加地点<br>\r\n</li>\r\n</ol>\r\n<p>​&nbsp;</p>\r\n\r\n', '{"entityMap":{},"blocks":[{"key":"b0h56","text":"123","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]}', '2017-04-05 17:13:26', '2017-04-13 19:42:25', 0, NULL);
/*!40000 ALTER TABLE `sale_act` ENABLE KEYS */;

-- 导出  表 joyclub.sale_act_attr 结构
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

-- 正在导出表  joyclub.sale_act_attr 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `sale_act_attr` DISABLE KEYS */;
INSERT INTO `sale_act_attr` (`id`, `act_id`, `name`, `num`, `remark`, `create_time`, `last_update`, `delete_flag`, `delete_time`) VALUES
	(3, 1, '1', 123, NULL, '2017-04-05 17:30:28', '2017-04-05 17:30:28', 0, NULL);
/*!40000 ALTER TABLE `sale_act_attr` ENABLE KEYS */;

-- 导出  表 joyclub.sale_act_price 结构
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

-- 正在导出表  joyclub.sale_act_price 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `sale_act_price` DISABLE KEYS */;
INSERT INTO `sale_act_price` (`id`, `act_id`, `price`, `point_rate`, `start_time`, `end_time`, `forbid_flag`, `review_status`, `review_info`, `create_time`, `last_update`, `delete_flag`, `delete_time`) VALUES
	(5, 1, 1, NULL, '2017-04-07 00:00:00.000', '2017-07-07 00:00:00.000', 0, 1, NULL, '2017-04-07 17:57:46', '2017-04-13 18:34:14', 0, NULL);
/*!40000 ALTER TABLE `sale_act_price` ENABLE KEYS */;

-- 导出  表 joyclub.sale_cart 结构
CREATE TABLE IF NOT EXISTS `sale_cart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `client_id` bigint(20) NOT NULL COMMENT '客户id',
  `project_id` bigint(20) NOT NULL COMMENT '项目id，其实根据商品属性找到商品后可以找到项目，但是为了避免过渡连接',
  `attr_id` bigint(20) NOT NULL COMMENT '商品属性id',
  `num` int(11) NOT NULL COMMENT '数量',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='商品购物车表';

-- 正在导出表  joyclub.sale_cart 的数据：~5 rows (大约)
/*!40000 ALTER TABLE `sale_cart` DISABLE KEYS */;
INSERT INTO `sale_cart` (`id`, `client_id`, `project_id`, `attr_id`, `num`, `create_time`, `last_update`, `delete_flag`, `delete_time`) VALUES
	(1, 2, 1, 3, 0, '2017-04-18 02:03:31', '2017-04-28 03:34:01', 1, '2017-04-26 11:55:47'),
	(2, 2, 1, 5, 1, '2017-04-26 11:42:50', '2017-05-10 15:10:22', 0, NULL),
	(3, 2, 1, 2, 0, '2017-04-26 11:56:13', '2017-05-10 17:47:28', 0, NULL),
	(4, 2, 1, 3, 0, '2017-04-26 12:17:53', '2017-05-08 19:09:01', 1, '2017-05-08 19:09:01'),
	(5, 2, 1, 4, 0, '2017-04-26 12:18:00', '2017-05-08 19:09:01', 1, '2017-05-08 19:09:01');
/*!40000 ALTER TABLE `sale_cart` ENABLE KEYS */;

-- 导出  表 joyclub.sale_product 结构
CREATE TABLE IF NOT EXISTS `sale_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `store_id` bigint(20) NOT NULL COMMENT '商户id',
  `category_id` bigint(20) NOT NULL COMMENT '分类id',
  `designer_id` bigint(20) DEFAULT NULL COMMENT '设计师id，可以为空',
  `name` varchar(20) DEFAULT NULL COMMENT '商品名',
  `display_weight` int(11) DEFAULT '0' COMMENT '显示权重，默认为0，越大越靠前',
  `base_price` float DEFAULT NULL COMMENT '基础价格',
  `portrait` varchar(200) DEFAULT NULL COMMENT '封面图',
  `carousel` varchar(800) DEFAULT NULL COMMENT '轮播图,JSON数组',
  `html_content` mediumtext COMMENT '商品详情在React draftJs里的data对象',
  `json_content` mediumtext COMMENT '商品详情的html',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- 正在导出表  joyclub.sale_product 的数据：~24 rows (大约)
/*!40000 ALTER TABLE `sale_product` DISABLE KEYS */;
INSERT INTO `sale_product` (`id`, `store_id`, `category_id`, `designer_id`, `name`, `display_weight`, `base_price`, `portrait`, `carousel`, `html_content`, `json_content`, `create_time`, `last_update`, `delete_flag`, `delete_time`) VALUES
	(1, 14, 2, 1, '测试商品名称', 3, 1, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '["http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg"]', '<p>爱要大声说出来，不要错过才后悔没有珍惜，对朋友的关心和呵护要用行动去证明，哪怕是一句问候，微信不该让彼此疏远，而是更亲近知道彼此的思念。</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/6fa221d8fee2406abdd64ea508cb387a.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/e28831b2bc1c4c4787a91dcb6d9023dc.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p>&nbsp;</p>\r\n<p>我们相识于干花</p>\r\n<p>体验押花香薰蜡过程</p>\r\n<p>制作简单漂亮的包装圆柱形礼物</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/d09dc0b6b03946fd88d94ae457762663.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<ol>\r\n<li>活动时长：3小时左右</li>\r\n<li>参与凭证：购买订单        </li>\r\n<li>活动时间：2017年2月25日至2017年5月31日 </li>\r\n<li>预约电话：15910816157</li>\r\n<li>参加活动时需提前三天预约</li>\r\n<li>团购后请致电预约参加时间和参加地点<br>\r\n</li>\r\n</ol>\r\n<p>​&nbsp;</p>\r\n\r\n\r\n\r\n', '{"entityMap":{"0":{"type":"IMAGE","mutability":"MUTABLE","data":{"src":"http://bjmallback.stcl365.com:8071/image/659ce78a885b4cb1a8d37af2cadd53fe.png","height":"auto","width":"100%"}}},"blocks":[{"key":"9n1b5","text":"123","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}},{"key":"cvh5v","text":" ","type":"atomic","depth":0,"inlineStyleRanges":[],"entityRanges":[{"offset":0,"length":1,"key":0}],"data":{}},{"key":"8kcfl","text":"","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]}', '2017-03-29 20:06:49', '2017-05-09 21:10:38', 0, NULL),
	(9, 14, 2, 1, '测试商品名称', 3, 1, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '["http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg"]', '<p>爱要大声说出来，不要错过才后悔没有珍惜，对朋友的关心和呵护要用行动去证明，哪怕是一句问候，微信不该让彼此疏远，而是更亲近知道彼此的思念。</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/6fa221d8fee2406abdd64ea508cb387a.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/e28831b2bc1c4c4787a91dcb6d9023dc.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p>&nbsp;</p>\r\n<p>我们相识于干花</p>\r\n<p>体验押花香薰蜡过程</p>\r\n<p>制作简单漂亮的包装圆柱形礼物</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/d09dc0b6b03946fd88d94ae457762663.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<ol>\r\n<li>活动时长：3小时左右</li>\r\n<li>参与凭证：购买订单        </li>\r\n<li>活动时间：2017年2月25日至2017年5月31日 </li>\r\n<li>预约电话：15910816157</li>\r\n<li>参加活动时需提前三天预约</li>\r\n<li>团购后请致电预约参加时间和参加地点<br>\r\n</li>\r\n</ol>\r\n<p>​&nbsp;</p>\r\n\r\n\r\n\r\n', '{"entityMap":{"0":{"type":"IMAGE","mutability":"MUTABLE","data":{"src":"http://bjmallback.stcl365.com:8071/image/659ce78a885b4cb1a8d37af2cadd53fe.png","height":"auto","width":"100%"}}},"blocks":[{"key":"9n1b5","text":"123","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}},{"key":"cvh5v","text":" ","type":"atomic","depth":0,"inlineStyleRanges":[],"entityRanges":[{"offset":0,"length":1,"key":0}],"data":{}},{"key":"8kcfl","text":"","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]}', '2017-03-29 20:06:49', '2017-05-09 21:10:38', 0, NULL),
	(10, 14, 2, 1, '测试商品名称', 3, 1, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '["http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg"]', '<p>爱要大声说出来，不要错过才后悔没有珍惜，对朋友的关心和呵护要用行动去证明，哪怕是一句问候，微信不该让彼此疏远，而是更亲近知道彼此的思念。</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/6fa221d8fee2406abdd64ea508cb387a.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/e28831b2bc1c4c4787a91dcb6d9023dc.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p>&nbsp;</p>\r\n<p>我们相识于干花</p>\r\n<p>体验押花香薰蜡过程</p>\r\n<p>制作简单漂亮的包装圆柱形礼物</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/d09dc0b6b03946fd88d94ae457762663.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<ol>\r\n<li>活动时长：3小时左右</li>\r\n<li>参与凭证：购买订单        </li>\r\n<li>活动时间：2017年2月25日至2017年5月31日 </li>\r\n<li>预约电话：15910816157</li>\r\n<li>参加活动时需提前三天预约</li>\r\n<li>团购后请致电预约参加时间和参加地点<br>\r\n</li>\r\n</ol>\r\n<p>​&nbsp;</p>\r\n\r\n\r\n\r\n', '{"entityMap":{"0":{"type":"IMAGE","mutability":"MUTABLE","data":{"src":"http://bjmallback.stcl365.com:8071/image/659ce78a885b4cb1a8d37af2cadd53fe.png","height":"auto","width":"100%"}}},"blocks":[{"key":"9n1b5","text":"123","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}},{"key":"cvh5v","text":" ","type":"atomic","depth":0,"inlineStyleRanges":[],"entityRanges":[{"offset":0,"length":1,"key":0}],"data":{}},{"key":"8kcfl","text":"","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]}', '2017-03-29 20:06:49', '2017-05-09 21:10:38', 0, NULL),
	(11, 14, 2, 1, '测试商品名称', 3, 1, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '["http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg"]', '<p>爱要大声说出来，不要错过才后悔没有珍惜，对朋友的关心和呵护要用行动去证明，哪怕是一句问候，微信不该让彼此疏远，而是更亲近知道彼此的思念。</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/6fa221d8fee2406abdd64ea508cb387a.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/e28831b2bc1c4c4787a91dcb6d9023dc.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p>&nbsp;</p>\r\n<p>我们相识于干花</p>\r\n<p>体验押花香薰蜡过程</p>\r\n<p>制作简单漂亮的包装圆柱形礼物</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/d09dc0b6b03946fd88d94ae457762663.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<ol>\r\n<li>活动时长：3小时左右</li>\r\n<li>参与凭证：购买订单        </li>\r\n<li>活动时间：2017年2月25日至2017年5月31日 </li>\r\n<li>预约电话：15910816157</li>\r\n<li>参加活动时需提前三天预约</li>\r\n<li>团购后请致电预约参加时间和参加地点<br>\r\n</li>\r\n</ol>\r\n<p>​&nbsp;</p>\r\n\r\n\r\n\r\n', '{"entityMap":{"0":{"type":"IMAGE","mutability":"MUTABLE","data":{"src":"http://bjmallback.stcl365.com:8071/image/659ce78a885b4cb1a8d37af2cadd53fe.png","height":"auto","width":"100%"}}},"blocks":[{"key":"9n1b5","text":"123","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}},{"key":"cvh5v","text":" ","type":"atomic","depth":0,"inlineStyleRanges":[],"entityRanges":[{"offset":0,"length":1,"key":0}],"data":{}},{"key":"8kcfl","text":"","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]}', '2017-03-29 20:06:49', '2017-05-09 21:10:38', 0, NULL),
	(12, 14, 2, 1, '测试商品名称', 3, 1, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '["http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg"]', '<p>爱要大声说出来，不要错过才后悔没有珍惜，对朋友的关心和呵护要用行动去证明，哪怕是一句问候，微信不该让彼此疏远，而是更亲近知道彼此的思念。</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/6fa221d8fee2406abdd64ea508cb387a.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/e28831b2bc1c4c4787a91dcb6d9023dc.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p>&nbsp;</p>\r\n<p>我们相识于干花</p>\r\n<p>体验押花香薰蜡过程</p>\r\n<p>制作简单漂亮的包装圆柱形礼物</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/d09dc0b6b03946fd88d94ae457762663.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<ol>\r\n<li>活动时长：3小时左右</li>\r\n<li>参与凭证：购买订单        </li>\r\n<li>活动时间：2017年2月25日至2017年5月31日 </li>\r\n<li>预约电话：15910816157</li>\r\n<li>参加活动时需提前三天预约</li>\r\n<li>团购后请致电预约参加时间和参加地点<br>\r\n</li>\r\n</ol>\r\n<p>​&nbsp;</p>\r\n\r\n\r\n\r\n', '{"entityMap":{"0":{"type":"IMAGE","mutability":"MUTABLE","data":{"src":"http://bjmallback.stcl365.com:8071/image/659ce78a885b4cb1a8d37af2cadd53fe.png","height":"auto","width":"100%"}}},"blocks":[{"key":"9n1b5","text":"123","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}},{"key":"cvh5v","text":" ","type":"atomic","depth":0,"inlineStyleRanges":[],"entityRanges":[{"offset":0,"length":1,"key":0}],"data":{}},{"key":"8kcfl","text":"","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]}', '2017-03-29 20:06:49', '2017-05-09 21:10:38', 0, NULL),
	(13, 14, 2, 1, '测试商品名称', 3, 1, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '["http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg"]', '<p>爱要大声说出来，不要错过才后悔没有珍惜，对朋友的关心和呵护要用行动去证明，哪怕是一句问候，微信不该让彼此疏远，而是更亲近知道彼此的思念。</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/6fa221d8fee2406abdd64ea508cb387a.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/e28831b2bc1c4c4787a91dcb6d9023dc.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p>&nbsp;</p>\r\n<p>我们相识于干花</p>\r\n<p>体验押花香薰蜡过程</p>\r\n<p>制作简单漂亮的包装圆柱形礼物</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/d09dc0b6b03946fd88d94ae457762663.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<ol>\r\n<li>活动时长：3小时左右</li>\r\n<li>参与凭证：购买订单        </li>\r\n<li>活动时间：2017年2月25日至2017年5月31日 </li>\r\n<li>预约电话：15910816157</li>\r\n<li>参加活动时需提前三天预约</li>\r\n<li>团购后请致电预约参加时间和参加地点<br>\r\n</li>\r\n</ol>\r\n<p>​&nbsp;</p>\r\n\r\n\r\n\r\n', '{"entityMap":{"0":{"type":"IMAGE","mutability":"MUTABLE","data":{"src":"http://bjmallback.stcl365.com:8071/image/659ce78a885b4cb1a8d37af2cadd53fe.png","height":"auto","width":"100%"}}},"blocks":[{"key":"9n1b5","text":"123","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}},{"key":"cvh5v","text":" ","type":"atomic","depth":0,"inlineStyleRanges":[],"entityRanges":[{"offset":0,"length":1,"key":0}],"data":{}},{"key":"8kcfl","text":"","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]}', '2017-03-29 20:06:49', '2017-05-09 21:10:38', 0, NULL),
	(14, 14, 2, 1, '测试商品名称', 3, 1, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '["http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg"]', '<p>爱要大声说出来，不要错过才后悔没有珍惜，对朋友的关心和呵护要用行动去证明，哪怕是一句问候，微信不该让彼此疏远，而是更亲近知道彼此的思念。</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/6fa221d8fee2406abdd64ea508cb387a.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/e28831b2bc1c4c4787a91dcb6d9023dc.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p>&nbsp;</p>\r\n<p>我们相识于干花</p>\r\n<p>体验押花香薰蜡过程</p>\r\n<p>制作简单漂亮的包装圆柱形礼物</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/d09dc0b6b03946fd88d94ae457762663.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<ol>\r\n<li>活动时长：3小时左右</li>\r\n<li>参与凭证：购买订单        </li>\r\n<li>活动时间：2017年2月25日至2017年5月31日 </li>\r\n<li>预约电话：15910816157</li>\r\n<li>参加活动时需提前三天预约</li>\r\n<li>团购后请致电预约参加时间和参加地点<br>\r\n</li>\r\n</ol>\r\n<p>​&nbsp;</p>\r\n\r\n\r\n\r\n', '{"entityMap":{"0":{"type":"IMAGE","mutability":"MUTABLE","data":{"src":"http://bjmallback.stcl365.com:8071/image/659ce78a885b4cb1a8d37af2cadd53fe.png","height":"auto","width":"100%"}}},"blocks":[{"key":"9n1b5","text":"123","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}},{"key":"cvh5v","text":" ","type":"atomic","depth":0,"inlineStyleRanges":[],"entityRanges":[{"offset":0,"length":1,"key":0}],"data":{}},{"key":"8kcfl","text":"","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]}', '2017-03-29 20:06:49', '2017-05-09 21:10:38', 0, NULL),
	(15, 14, 2, 1, '测试商品名称', 3, 1, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '["http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg"]', '<p>爱要大声说出来，不要错过才后悔没有珍惜，对朋友的关心和呵护要用行动去证明，哪怕是一句问候，微信不该让彼此疏远，而是更亲近知道彼此的思念。</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/6fa221d8fee2406abdd64ea508cb387a.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/e28831b2bc1c4c4787a91dcb6d9023dc.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p>&nbsp;</p>\r\n<p>我们相识于干花</p>\r\n<p>体验押花香薰蜡过程</p>\r\n<p>制作简单漂亮的包装圆柱形礼物</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/d09dc0b6b03946fd88d94ae457762663.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<ol>\r\n<li>活动时长：3小时左右</li>\r\n<li>参与凭证：购买订单        </li>\r\n<li>活动时间：2017年2月25日至2017年5月31日 </li>\r\n<li>预约电话：15910816157</li>\r\n<li>参加活动时需提前三天预约</li>\r\n<li>团购后请致电预约参加时间和参加地点<br>\r\n</li>\r\n</ol>\r\n<p>​&nbsp;</p>\r\n\r\n\r\n\r\n', '{"entityMap":{"0":{"type":"IMAGE","mutability":"MUTABLE","data":{"src":"http://bjmallback.stcl365.com:8071/image/659ce78a885b4cb1a8d37af2cadd53fe.png","height":"auto","width":"100%"}}},"blocks":[{"key":"9n1b5","text":"123","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}},{"key":"cvh5v","text":" ","type":"atomic","depth":0,"inlineStyleRanges":[],"entityRanges":[{"offset":0,"length":1,"key":0}],"data":{}},{"key":"8kcfl","text":"","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]}', '2017-03-29 20:06:49', '2017-05-09 21:10:38', 0, NULL),
	(16, 14, 2, 1, '测试商品名称', 3, 1, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '["http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg"]', '<p>爱要大声说出来，不要错过才后悔没有珍惜，对朋友的关心和呵护要用行动去证明，哪怕是一句问候，微信不该让彼此疏远，而是更亲近知道彼此的思念。</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/6fa221d8fee2406abdd64ea508cb387a.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/e28831b2bc1c4c4787a91dcb6d9023dc.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p>&nbsp;</p>\r\n<p>我们相识于干花</p>\r\n<p>体验押花香薰蜡过程</p>\r\n<p>制作简单漂亮的包装圆柱形礼物</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/d09dc0b6b03946fd88d94ae457762663.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<ol>\r\n<li>活动时长：3小时左右</li>\r\n<li>参与凭证：购买订单        </li>\r\n<li>活动时间：2017年2月25日至2017年5月31日 </li>\r\n<li>预约电话：15910816157</li>\r\n<li>参加活动时需提前三天预约</li>\r\n<li>团购后请致电预约参加时间和参加地点<br>\r\n</li>\r\n</ol>\r\n<p>​&nbsp;</p>\r\n\r\n\r\n\r\n', '{"entityMap":{"0":{"type":"IMAGE","mutability":"MUTABLE","data":{"src":"http://bjmallback.stcl365.com:8071/image/659ce78a885b4cb1a8d37af2cadd53fe.png","height":"auto","width":"100%"}}},"blocks":[{"key":"9n1b5","text":"123","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}},{"key":"cvh5v","text":" ","type":"atomic","depth":0,"inlineStyleRanges":[],"entityRanges":[{"offset":0,"length":1,"key":0}],"data":{}},{"key":"8kcfl","text":"","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]}', '2017-03-29 20:06:49', '2017-05-09 21:10:38', 0, NULL),
	(17, 14, 2, 1, '测试商品名称', 3, 1, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '["http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg"]', '<p>爱要大声说出来，不要错过才后悔没有珍惜，对朋友的关心和呵护要用行动去证明，哪怕是一句问候，微信不该让彼此疏远，而是更亲近知道彼此的思念。</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/6fa221d8fee2406abdd64ea508cb387a.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/e28831b2bc1c4c4787a91dcb6d9023dc.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p>&nbsp;</p>\r\n<p>我们相识于干花</p>\r\n<p>体验押花香薰蜡过程</p>\r\n<p>制作简单漂亮的包装圆柱形礼物</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/d09dc0b6b03946fd88d94ae457762663.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<ol>\r\n<li>活动时长：3小时左右</li>\r\n<li>参与凭证：购买订单        </li>\r\n<li>活动时间：2017年2月25日至2017年5月31日 </li>\r\n<li>预约电话：15910816157</li>\r\n<li>参加活动时需提前三天预约</li>\r\n<li>团购后请致电预约参加时间和参加地点<br>\r\n</li>\r\n</ol>\r\n<p>​&nbsp;</p>\r\n\r\n\r\n\r\n', '{"entityMap":{"0":{"type":"IMAGE","mutability":"MUTABLE","data":{"src":"http://bjmallback.stcl365.com:8071/image/659ce78a885b4cb1a8d37af2cadd53fe.png","height":"auto","width":"100%"}}},"blocks":[{"key":"9n1b5","text":"123","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}},{"key":"cvh5v","text":" ","type":"atomic","depth":0,"inlineStyleRanges":[],"entityRanges":[{"offset":0,"length":1,"key":0}],"data":{}},{"key":"8kcfl","text":"","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]}', '2017-03-29 20:06:49', '2017-05-09 21:10:38', 0, NULL),
	(18, 14, 2, 1, '测试商品名称', 3, 1, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '["http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg"]', '<p>爱要大声说出来，不要错过才后悔没有珍惜，对朋友的关心和呵护要用行动去证明，哪怕是一句问候，微信不该让彼此疏远，而是更亲近知道彼此的思念。</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/6fa221d8fee2406abdd64ea508cb387a.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/e28831b2bc1c4c4787a91dcb6d9023dc.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p>&nbsp;</p>\r\n<p>我们相识于干花</p>\r\n<p>体验押花香薰蜡过程</p>\r\n<p>制作简单漂亮的包装圆柱形礼物</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/d09dc0b6b03946fd88d94ae457762663.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<ol>\r\n<li>活动时长：3小时左右</li>\r\n<li>参与凭证：购买订单        </li>\r\n<li>活动时间：2017年2月25日至2017年5月31日 </li>\r\n<li>预约电话：15910816157</li>\r\n<li>参加活动时需提前三天预约</li>\r\n<li>团购后请致电预约参加时间和参加地点<br>\r\n</li>\r\n</ol>\r\n<p>​&nbsp;</p>\r\n\r\n\r\n\r\n', '{"entityMap":{"0":{"type":"IMAGE","mutability":"MUTABLE","data":{"src":"http://bjmallback.stcl365.com:8071/image/659ce78a885b4cb1a8d37af2cadd53fe.png","height":"auto","width":"100%"}}},"blocks":[{"key":"9n1b5","text":"123","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}},{"key":"cvh5v","text":" ","type":"atomic","depth":0,"inlineStyleRanges":[],"entityRanges":[{"offset":0,"length":1,"key":0}],"data":{}},{"key":"8kcfl","text":"","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]}', '2017-03-29 20:06:49', '2017-05-09 21:10:38', 0, NULL),
	(19, 14, 2, 1, '测试商品名称', 3, 1, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '["http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg"]', '<p>爱要大声说出来，不要错过才后悔没有珍惜，对朋友的关心和呵护要用行动去证明，哪怕是一句问候，微信不该让彼此疏远，而是更亲近知道彼此的思念。</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/6fa221d8fee2406abdd64ea508cb387a.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/e28831b2bc1c4c4787a91dcb6d9023dc.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p>&nbsp;</p>\r\n<p>我们相识于干花</p>\r\n<p>体验押花香薰蜡过程</p>\r\n<p>制作简单漂亮的包装圆柱形礼物</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/d09dc0b6b03946fd88d94ae457762663.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<ol>\r\n<li>活动时长：3小时左右</li>\r\n<li>参与凭证：购买订单        </li>\r\n<li>活动时间：2017年2月25日至2017年5月31日 </li>\r\n<li>预约电话：15910816157</li>\r\n<li>参加活动时需提前三天预约</li>\r\n<li>团购后请致电预约参加时间和参加地点<br>\r\n</li>\r\n</ol>\r\n<p>​&nbsp;</p>\r\n\r\n\r\n\r\n', '{"entityMap":{"0":{"type":"IMAGE","mutability":"MUTABLE","data":{"src":"http://bjmallback.stcl365.com:8071/image/659ce78a885b4cb1a8d37af2cadd53fe.png","height":"auto","width":"100%"}}},"blocks":[{"key":"9n1b5","text":"123","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}},{"key":"cvh5v","text":" ","type":"atomic","depth":0,"inlineStyleRanges":[],"entityRanges":[{"offset":0,"length":1,"key":0}],"data":{}},{"key":"8kcfl","text":"","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]}', '2017-03-29 20:06:49', '2017-05-09 21:10:38', 0, NULL),
	(20, 14, 2, 1, '测试商品名称', 3, 1, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '["http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg"]', '<p>爱要大声说出来，不要错过才后悔没有珍惜，对朋友的关心和呵护要用行动去证明，哪怕是一句问候，微信不该让彼此疏远，而是更亲近知道彼此的思念。</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/6fa221d8fee2406abdd64ea508cb387a.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/e28831b2bc1c4c4787a91dcb6d9023dc.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p>&nbsp;</p>\r\n<p>我们相识于干花</p>\r\n<p>体验押花香薰蜡过程</p>\r\n<p>制作简单漂亮的包装圆柱形礼物</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/d09dc0b6b03946fd88d94ae457762663.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<ol>\r\n<li>活动时长：3小时左右</li>\r\n<li>参与凭证：购买订单        </li>\r\n<li>活动时间：2017年2月25日至2017年5月31日 </li>\r\n<li>预约电话：15910816157</li>\r\n<li>参加活动时需提前三天预约</li>\r\n<li>团购后请致电预约参加时间和参加地点<br>\r\n</li>\r\n</ol>\r\n<p>​&nbsp;</p>\r\n\r\n\r\n\r\n', '{"entityMap":{"0":{"type":"IMAGE","mutability":"MUTABLE","data":{"src":"http://bjmallback.stcl365.com:8071/image/659ce78a885b4cb1a8d37af2cadd53fe.png","height":"auto","width":"100%"}}},"blocks":[{"key":"9n1b5","text":"123","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}},{"key":"cvh5v","text":" ","type":"atomic","depth":0,"inlineStyleRanges":[],"entityRanges":[{"offset":0,"length":1,"key":0}],"data":{}},{"key":"8kcfl","text":"","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]}', '2017-03-29 20:06:49', '2017-05-09 21:10:38', 0, NULL),
	(21, 14, 2, 1, '测试商品名称', 3, 1, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '["http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg"]', '<p>爱要大声说出来，不要错过才后悔没有珍惜，对朋友的关心和呵护要用行动去证明，哪怕是一句问候，微信不该让彼此疏远，而是更亲近知道彼此的思念。</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/6fa221d8fee2406abdd64ea508cb387a.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/e28831b2bc1c4c4787a91dcb6d9023dc.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p>&nbsp;</p>\r\n<p>我们相识于干花</p>\r\n<p>体验押花香薰蜡过程</p>\r\n<p>制作简单漂亮的包装圆柱形礼物</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/d09dc0b6b03946fd88d94ae457762663.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<ol>\r\n<li>活动时长：3小时左右</li>\r\n<li>参与凭证：购买订单        </li>\r\n<li>活动时间：2017年2月25日至2017年5月31日 </li>\r\n<li>预约电话：15910816157</li>\r\n<li>参加活动时需提前三天预约</li>\r\n<li>团购后请致电预约参加时间和参加地点<br>\r\n</li>\r\n</ol>\r\n<p>​&nbsp;</p>\r\n\r\n\r\n\r\n', '{"entityMap":{"0":{"type":"IMAGE","mutability":"MUTABLE","data":{"src":"http://bjmallback.stcl365.com:8071/image/659ce78a885b4cb1a8d37af2cadd53fe.png","height":"auto","width":"100%"}}},"blocks":[{"key":"9n1b5","text":"123","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}},{"key":"cvh5v","text":" ","type":"atomic","depth":0,"inlineStyleRanges":[],"entityRanges":[{"offset":0,"length":1,"key":0}],"data":{}},{"key":"8kcfl","text":"","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]}', '2017-03-29 20:06:49', '2017-05-09 21:10:38', 0, NULL),
	(22, 14, 2, 1, '测试商品名称', 3, 1, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '["http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg"]', '<p>爱要大声说出来，不要错过才后悔没有珍惜，对朋友的关心和呵护要用行动去证明，哪怕是一句问候，微信不该让彼此疏远，而是更亲近知道彼此的思念。</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/6fa221d8fee2406abdd64ea508cb387a.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/e28831b2bc1c4c4787a91dcb6d9023dc.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p>&nbsp;</p>\r\n<p>我们相识于干花</p>\r\n<p>体验押花香薰蜡过程</p>\r\n<p>制作简单漂亮的包装圆柱形礼物</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/d09dc0b6b03946fd88d94ae457762663.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<ol>\r\n<li>活动时长：3小时左右</li>\r\n<li>参与凭证：购买订单        </li>\r\n<li>活动时间：2017年2月25日至2017年5月31日 </li>\r\n<li>预约电话：15910816157</li>\r\n<li>参加活动时需提前三天预约</li>\r\n<li>团购后请致电预约参加时间和参加地点<br>\r\n</li>\r\n</ol>\r\n<p>​&nbsp;</p>\r\n\r\n\r\n\r\n', '{"entityMap":{"0":{"type":"IMAGE","mutability":"MUTABLE","data":{"src":"http://bjmallback.stcl365.com:8071/image/659ce78a885b4cb1a8d37af2cadd53fe.png","height":"auto","width":"100%"}}},"blocks":[{"key":"9n1b5","text":"123","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}},{"key":"cvh5v","text":" ","type":"atomic","depth":0,"inlineStyleRanges":[],"entityRanges":[{"offset":0,"length":1,"key":0}],"data":{}},{"key":"8kcfl","text":"","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]}', '2017-03-29 20:06:49', '2017-05-09 21:10:38', 0, NULL),
	(23, 14, 2, 1, '测试商品名称', 3, 1, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '["http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg"]', '<p>爱要大声说出来，不要错过才后悔没有珍惜，对朋友的关心和呵护要用行动去证明，哪怕是一句问候，微信不该让彼此疏远，而是更亲近知道彼此的思念。</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/6fa221d8fee2406abdd64ea508cb387a.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/e28831b2bc1c4c4787a91dcb6d9023dc.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p>&nbsp;</p>\r\n<p>我们相识于干花</p>\r\n<p>体验押花香薰蜡过程</p>\r\n<p>制作简单漂亮的包装圆柱形礼物</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/d09dc0b6b03946fd88d94ae457762663.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<ol>\r\n<li>活动时长：3小时左右</li>\r\n<li>参与凭证：购买订单        </li>\r\n<li>活动时间：2017年2月25日至2017年5月31日 </li>\r\n<li>预约电话：15910816157</li>\r\n<li>参加活动时需提前三天预约</li>\r\n<li>团购后请致电预约参加时间和参加地点<br>\r\n</li>\r\n</ol>\r\n<p>​&nbsp;</p>\r\n\r\n\r\n\r\n', '{"entityMap":{"0":{"type":"IMAGE","mutability":"MUTABLE","data":{"src":"http://bjmallback.stcl365.com:8071/image/659ce78a885b4cb1a8d37af2cadd53fe.png","height":"auto","width":"100%"}}},"blocks":[{"key":"9n1b5","text":"123","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}},{"key":"cvh5v","text":" ","type":"atomic","depth":0,"inlineStyleRanges":[],"entityRanges":[{"offset":0,"length":1,"key":0}],"data":{}},{"key":"8kcfl","text":"","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]}', '2017-03-29 20:06:49', '2017-05-09 21:10:38', 0, NULL),
	(24, 14, 2, 1, '测试商品名称', 3, 1, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '["http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg"]', '<p>爱要大声说出来，不要错过才后悔没有珍惜，对朋友的关心和呵护要用行动去证明，哪怕是一句问候，微信不该让彼此疏远，而是更亲近知道彼此的思念。</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/6fa221d8fee2406abdd64ea508cb387a.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/e28831b2bc1c4c4787a91dcb6d9023dc.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p>&nbsp;</p>\r\n<p>我们相识于干花</p>\r\n<p>体验押花香薰蜡过程</p>\r\n<p>制作简单漂亮的包装圆柱形礼物</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/d09dc0b6b03946fd88d94ae457762663.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<ol>\r\n<li>活动时长：3小时左右</li>\r\n<li>参与凭证：购买订单        </li>\r\n<li>活动时间：2017年2月25日至2017年5月31日 </li>\r\n<li>预约电话：15910816157</li>\r\n<li>参加活动时需提前三天预约</li>\r\n<li>团购后请致电预约参加时间和参加地点<br>\r\n</li>\r\n</ol>\r\n<p>​&nbsp;</p>\r\n\r\n\r\n\r\n', '{"entityMap":{"0":{"type":"IMAGE","mutability":"MUTABLE","data":{"src":"http://bjmallback.stcl365.com:8071/image/659ce78a885b4cb1a8d37af2cadd53fe.png","height":"auto","width":"100%"}}},"blocks":[{"key":"9n1b5","text":"123","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}},{"key":"cvh5v","text":" ","type":"atomic","depth":0,"inlineStyleRanges":[],"entityRanges":[{"offset":0,"length":1,"key":0}],"data":{}},{"key":"8kcfl","text":"","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]}', '2017-03-29 20:06:49', '2017-05-09 21:10:38', 0, NULL),
	(25, 14, 2, 1, '测试商品名称', 3, 1, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '["http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg"]', '<p>爱要大声说出来，不要错过才后悔没有珍惜，对朋友的关心和呵护要用行动去证明，哪怕是一句问候，微信不该让彼此疏远，而是更亲近知道彼此的思念。</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/6fa221d8fee2406abdd64ea508cb387a.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/e28831b2bc1c4c4787a91dcb6d9023dc.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p>&nbsp;</p>\r\n<p>我们相识于干花</p>\r\n<p>体验押花香薰蜡过程</p>\r\n<p>制作简单漂亮的包装圆柱形礼物</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/d09dc0b6b03946fd88d94ae457762663.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<ol>\r\n<li>活动时长：3小时左右</li>\r\n<li>参与凭证：购买订单        </li>\r\n<li>活动时间：2017年2月25日至2017年5月31日 </li>\r\n<li>预约电话：15910816157</li>\r\n<li>参加活动时需提前三天预约</li>\r\n<li>团购后请致电预约参加时间和参加地点<br>\r\n</li>\r\n</ol>\r\n<p>​&nbsp;</p>\r\n\r\n\r\n\r\n', '{"entityMap":{"0":{"type":"IMAGE","mutability":"MUTABLE","data":{"src":"http://bjmallback.stcl365.com:8071/image/659ce78a885b4cb1a8d37af2cadd53fe.png","height":"auto","width":"100%"}}},"blocks":[{"key":"9n1b5","text":"123","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}},{"key":"cvh5v","text":" ","type":"atomic","depth":0,"inlineStyleRanges":[],"entityRanges":[{"offset":0,"length":1,"key":0}],"data":{}},{"key":"8kcfl","text":"","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]}', '2017-03-29 20:06:49', '2017-05-09 21:10:38', 0, NULL),
	(26, 14, 2, 1, '测试商品名称', 3, 1, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '["http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg"]', '<p>爱要大声说出来，不要错过才后悔没有珍惜，对朋友的关心和呵护要用行动去证明，哪怕是一句问候，微信不该让彼此疏远，而是更亲近知道彼此的思念。</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/6fa221d8fee2406abdd64ea508cb387a.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/e28831b2bc1c4c4787a91dcb6d9023dc.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p>&nbsp;</p>\r\n<p>我们相识于干花</p>\r\n<p>体验押花香薰蜡过程</p>\r\n<p>制作简单漂亮的包装圆柱形礼物</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/d09dc0b6b03946fd88d94ae457762663.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<ol>\r\n<li>活动时长：3小时左右</li>\r\n<li>参与凭证：购买订单        </li>\r\n<li>活动时间：2017年2月25日至2017年5月31日 </li>\r\n<li>预约电话：15910816157</li>\r\n<li>参加活动时需提前三天预约</li>\r\n<li>团购后请致电预约参加时间和参加地点<br>\r\n</li>\r\n</ol>\r\n<p>​&nbsp;</p>\r\n\r\n\r\n\r\n', '{"entityMap":{"0":{"type":"IMAGE","mutability":"MUTABLE","data":{"src":"http://bjmallback.stcl365.com:8071/image/659ce78a885b4cb1a8d37af2cadd53fe.png","height":"auto","width":"100%"}}},"blocks":[{"key":"9n1b5","text":"123","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}},{"key":"cvh5v","text":" ","type":"atomic","depth":0,"inlineStyleRanges":[],"entityRanges":[{"offset":0,"length":1,"key":0}],"data":{}},{"key":"8kcfl","text":"","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]}', '2017-03-29 20:06:49', '2017-05-09 21:10:38', 0, NULL),
	(27, 14, 2, 1, '测试商品名称', 3, 1, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '["http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg"]', '<p>爱要大声说出来，不要错过才后悔没有珍惜，对朋友的关心和呵护要用行动去证明，哪怕是一句问候，微信不该让彼此疏远，而是更亲近知道彼此的思念。</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/6fa221d8fee2406abdd64ea508cb387a.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/e28831b2bc1c4c4787a91dcb6d9023dc.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p>&nbsp;</p>\r\n<p>我们相识于干花</p>\r\n<p>体验押花香薰蜡过程</p>\r\n<p>制作简单漂亮的包装圆柱形礼物</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/d09dc0b6b03946fd88d94ae457762663.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<ol>\r\n<li>活动时长：3小时左右</li>\r\n<li>参与凭证：购买订单        </li>\r\n<li>活动时间：2017年2月25日至2017年5月31日 </li>\r\n<li>预约电话：15910816157</li>\r\n<li>参加活动时需提前三天预约</li>\r\n<li>团购后请致电预约参加时间和参加地点<br>\r\n</li>\r\n</ol>\r\n<p>​&nbsp;</p>\r\n\r\n\r\n\r\n', '{"entityMap":{"0":{"type":"IMAGE","mutability":"MUTABLE","data":{"src":"http://bjmallback.stcl365.com:8071/image/659ce78a885b4cb1a8d37af2cadd53fe.png","height":"auto","width":"100%"}}},"blocks":[{"key":"9n1b5","text":"123","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}},{"key":"cvh5v","text":" ","type":"atomic","depth":0,"inlineStyleRanges":[],"entityRanges":[{"offset":0,"length":1,"key":0}],"data":{}},{"key":"8kcfl","text":"","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]}', '2017-03-29 20:06:49', '2017-05-09 21:10:38', 0, NULL),
	(28, 14, 2, 1, '测试商品名称', 3, 1, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '["http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg"]', '<p>爱要大声说出来，不要错过才后悔没有珍惜，对朋友的关心和呵护要用行动去证明，哪怕是一句问候，微信不该让彼此疏远，而是更亲近知道彼此的思念。</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/6fa221d8fee2406abdd64ea508cb387a.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/e28831b2bc1c4c4787a91dcb6d9023dc.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p>&nbsp;</p>\r\n<p>我们相识于干花</p>\r\n<p>体验押花香薰蜡过程</p>\r\n<p>制作简单漂亮的包装圆柱形礼物</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/d09dc0b6b03946fd88d94ae457762663.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<ol>\r\n<li>活动时长：3小时左右</li>\r\n<li>参与凭证：购买订单        </li>\r\n<li>活动时间：2017年2月25日至2017年5月31日 </li>\r\n<li>预约电话：15910816157</li>\r\n<li>参加活动时需提前三天预约</li>\r\n<li>团购后请致电预约参加时间和参加地点<br>\r\n</li>\r\n</ol>\r\n<p>​&nbsp;</p>\r\n\r\n\r\n\r\n', '{"entityMap":{"0":{"type":"IMAGE","mutability":"MUTABLE","data":{"src":"http://bjmallback.stcl365.com:8071/image/659ce78a885b4cb1a8d37af2cadd53fe.png","height":"auto","width":"100%"}}},"blocks":[{"key":"9n1b5","text":"123","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}},{"key":"cvh5v","text":" ","type":"atomic","depth":0,"inlineStyleRanges":[],"entityRanges":[{"offset":0,"length":1,"key":0}],"data":{}},{"key":"8kcfl","text":"","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]}', '2017-03-29 20:06:49', '2017-05-09 21:10:38', 0, NULL),
	(29, 14, 2, 1, '测试商品名称', 3, 1, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '["http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg"]', '<p>爱要大声说出来，不要错过才后悔没有珍惜，对朋友的关心和呵护要用行动去证明，哪怕是一句问候，微信不该让彼此疏远，而是更亲近知道彼此的思念。</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/6fa221d8fee2406abdd64ea508cb387a.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/e28831b2bc1c4c4787a91dcb6d9023dc.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p>&nbsp;</p>\r\n<p>我们相识于干花</p>\r\n<p>体验押花香薰蜡过程</p>\r\n<p>制作简单漂亮的包装圆柱形礼物</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/d09dc0b6b03946fd88d94ae457762663.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<ol>\r\n<li>活动时长：3小时左右</li>\r\n<li>参与凭证：购买订单        </li>\r\n<li>活动时间：2017年2月25日至2017年5月31日 </li>\r\n<li>预约电话：15910816157</li>\r\n<li>参加活动时需提前三天预约</li>\r\n<li>团购后请致电预约参加时间和参加地点<br>\r\n</li>\r\n</ol>\r\n<p>​&nbsp;</p>\r\n\r\n\r\n\r\n', '{"entityMap":{"0":{"type":"IMAGE","mutability":"MUTABLE","data":{"src":"http://bjmallback.stcl365.com:8071/image/659ce78a885b4cb1a8d37af2cadd53fe.png","height":"auto","width":"100%"}}},"blocks":[{"key":"9n1b5","text":"123","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}},{"key":"cvh5v","text":" ","type":"atomic","depth":0,"inlineStyleRanges":[],"entityRanges":[{"offset":0,"length":1,"key":0}],"data":{}},{"key":"8kcfl","text":"","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]}', '2017-03-29 20:06:49', '2017-05-09 21:10:38', 0, NULL),
	(30, 14, 2, 1, '测试商品名称', 3, 1, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '["http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg"]', '<p>爱要大声说出来，不要错过才后悔没有珍惜，对朋友的关心和呵护要用行动去证明，哪怕是一句问候，微信不该让彼此疏远，而是更亲近知道彼此的思念。</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/6fa221d8fee2406abdd64ea508cb387a.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/e28831b2bc1c4c4787a91dcb6d9023dc.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p>&nbsp;</p>\r\n<p>我们相识于干花</p>\r\n<p>体验押花香薰蜡过程</p>\r\n<p>制作简单漂亮的包装圆柱形礼物</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/d09dc0b6b03946fd88d94ae457762663.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<ol>\r\n<li>活动时长：3小时左右</li>\r\n<li>参与凭证：购买订单        </li>\r\n<li>活动时间：2017年2月25日至2017年5月31日 </li>\r\n<li>预约电话：15910816157</li>\r\n<li>参加活动时需提前三天预约</li>\r\n<li>团购后请致电预约参加时间和参加地点<br>\r\n</li>\r\n</ol>\r\n<p>​&nbsp;</p>\r\n\r\n\r\n\r\n', '{"entityMap":{"0":{"type":"IMAGE","mutability":"MUTABLE","data":{"src":"http://bjmallback.stcl365.com:8071/image/659ce78a885b4cb1a8d37af2cadd53fe.png","height":"auto","width":"100%"}}},"blocks":[{"key":"9n1b5","text":"123","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}},{"key":"cvh5v","text":" ","type":"atomic","depth":0,"inlineStyleRanges":[],"entityRanges":[{"offset":0,"length":1,"key":0}],"data":{}},{"key":"8kcfl","text":"","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]}', '2017-03-29 20:06:49', '2017-05-09 21:10:38', 0, NULL),
	(31, 14, 2, 1, '测试商品名称', 3, 1.12, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '["http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg","http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg"]', '<p>123</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/659ce78a885b4cb1a8d37af2cadd53fe.png" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n', '{"entityMap":{"0":{"type":"IMAGE","mutability":"MUTABLE","data":{"src":"http://bjmallback.stcl365.com:8071/image/659ce78a885b4cb1a8d37af2cadd53fe.png","height":"auto","width":"100%"}}},"blocks":[{"key":"9n1b5","text":"123","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}},{"key":"cvh5v","text":" ","type":"atomic","depth":0,"inlineStyleRanges":[],"entityRanges":[{"offset":0,"length":1,"key":0}],"data":{}},{"key":"8kcfl","text":"","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]}', '2017-03-29 20:06:49', '2017-05-09 21:10:38', 0, NULL);
/*!40000 ALTER TABLE `sale_product` ENABLE KEYS */;

-- 导出  表 joyclub.sale_product_attr 结构
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='商品的属性表，基于属性（比如尺码、颜色，一维，如果同时有颜色、尺码，需要展开为一维）进行库存管理\r\n';

-- 正在导出表  joyclub.sale_product_attr 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `sale_product_attr` DISABLE KEYS */;
INSERT INTO `sale_product_attr` (`id`, `product_id`, `name`, `num`, `remark`, `create_time`, `last_update`, `delete_flag`, `delete_time`) VALUES
	(2, 1, 'shuxing属性11', -3, '21', '2017-03-30 01:18:39', '2017-05-10 18:53:33', 0, NULL),
	(3, 1, 'shuxing属性11', 122, '21', '2017-03-30 01:18:39', '2017-04-28 03:34:01', 0, NULL),
	(4, 1, 'shuxing属性11', 122, '21', '2017-03-30 01:18:39', '2017-04-28 03:34:01', 0, NULL),
	(5, 21, 'shuxing属性11', 109, '21', '2017-03-30 01:18:39', '2017-04-28 03:45:24', 0, NULL);
/*!40000 ALTER TABLE `sale_product_attr` ENABLE KEYS */;

-- 导出  表 joyclub.sale_product_order 结构
CREATE TABLE IF NOT EXISTS `sale_product_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) NOT NULL COMMENT '目前是nanoTime加三位随机数',
  `out_pay_code` varchar(64) DEFAULT NULL COMMENT '支付成功的外部单号,如果是纯积分，可以为空',
  `project_id` bigint(20) NOT NULL COMMENT '项目id',
  `client_id` bigint(20) NOT NULL COMMENT '客户id',
  `receive_type` tinyint(4) NOT NULL COMMENT '收货类型 0自提 1快递',
  `status` tinyint(4) NOT NULL COMMENT '0 待支付 1已取消 2 已支付 ',
  `money_sum` float NOT NULL COMMENT '用户为该单所需支付的总金钱，注意是订单下所有选择现金支付的商品金钱总额',
  `point_sum` int(11) NOT NULL COMMENT '用户为该单所需支付的总积分，注意是订单下所有选择积分支付的商品积分总额',
  `pay_type` tinyint(4) DEFAULT NULL COMMENT '0微信 1支付宝',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `cancel_time` datetime DEFAULT NULL COMMENT '取消时间',
  `canceler` tinyint(4) DEFAULT NULL COMMENT '用来标记谁取消了，0是用户，1是系统自动（用于系统定时删除未支付订单）',
  `receiver_name` varchar(20) DEFAULT NULL COMMENT '如果是快递，则提供收货者姓名',
  `receiver_phone` varchar(20) DEFAULT NULL COMMENT '如果是快递，则提供收货者手机号',
  `receiver_address` varchar(100) DEFAULT NULL COMMENT '如果是快递，则提供收货者地址',
  `receiver_postcode` varchar(10) DEFAULT NULL COMMENT '如果是快递，则提供收货者邮编',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8 COMMENT='商品订单表\r\n系统定时删除未支付订单，恢复库存，防止库存一直被占用\r\n\r\n';

-- 正在导出表  joyclub.sale_product_order 的数据：~20 rows (大约)
/*!40000 ALTER TABLE `sale_product_order` DISABLE KEYS */;
INSERT INTO `sale_product_order` (`id`, `code`, `out_pay_code`, `project_id`, `client_id`, `receive_type`, `status`, `money_sum`, `point_sum`, `pay_type`, `pay_time`, `cancel_time`, `canceler`, `receiver_name`, `receiver_phone`, `receiver_address`, `receiver_postcode`, `create_time`, `last_update`, `delete_flag`, `delete_time`) VALUES
	(1, '62174599612522678842480', '123123123', 1, 2, 1, 0, 1, 1, 1, '2017-04-25 10:25:03', '2017-04-26 16:08:24', 0, '1', '15001060933', NULL, NULL, '2017-04-25 10:25:02', '2017-04-28 14:58:02', 0, NULL),
	(17, '1232141234', '123123123', 1, 2, 1, 2, 1, 1, 1, '2017-04-25 10:25:03', NULL, NULL, '1', '15001060933', NULL, NULL, '2017-04-25 10:25:02', '2017-04-27 19:39:19', 0, NULL),
	(33, '1232141234', '123123123', 1, 2, 1, 2, 1, 1, 1, '2017-04-25 10:25:03', NULL, NULL, '1', '15001060933', NULL, NULL, '2017-04-25 10:25:02', '2017-04-27 19:39:19', 0, NULL),
	(34, '1232141234', '123123123', 1, 2, 1, 2, 1, 1, 1, '2017-04-25 10:25:03', NULL, NULL, '1', '15001060933', NULL, NULL, '2017-04-25 10:25:02', '2017-04-27 19:39:19', 0, NULL),
	(35, '1232141234', '123123123', 1, 2, 1, 2, 1, 1, 1, '2017-04-25 10:25:03', NULL, NULL, '1', '15001060933', NULL, NULL, '2017-04-25 10:25:02', '2017-04-27 19:39:19', 0, NULL),
	(36, '1232141234', '123123123', 1, 2, 1, 2, 1, 1, 1, '2017-04-25 10:25:03', NULL, NULL, '1', '15001060933', NULL, NULL, '2017-04-25 10:25:02', '2017-04-27 19:39:19', 0, NULL),
	(37, '1232141234', '123123123', 1, 2, 1, 2, 1, 1, 1, '2017-04-25 10:25:03', NULL, NULL, '1', '15001060933', NULL, NULL, '2017-04-25 10:25:02', '2017-04-27 19:39:19', 0, NULL),
	(38, '1232141234', '123123123', 1, 2, 1, 2, 1, 1, 1, '2017-04-25 10:25:03', NULL, NULL, '1', '15001060933', NULL, NULL, '2017-04-25 10:25:02', '2017-04-27 19:39:19', 0, NULL),
	(39, '1232141234', '123123123', 1, 2, 1, 2, 1, 1, 1, '2017-04-25 10:25:03', NULL, NULL, '1', '15001060933', NULL, NULL, '2017-04-25 10:25:02', '2017-04-27 19:39:19', 0, NULL),
	(40, '1232141234', '123123123', 1, 2, 1, 2, 1, 1, 1, '2017-04-25 10:25:03', NULL, NULL, '1', '15001060933', NULL, NULL, '2017-04-25 10:25:02', '2017-04-27 19:39:19', 0, NULL),
	(41, '1232141234', '123123123', 1, 2, 1, 2, 1, 1, 1, '2017-04-25 10:25:03', NULL, NULL, '1', '15001060933', NULL, NULL, '2017-04-25 10:25:02', '2017-04-27 19:39:19', 0, NULL),
	(42, '1232141234', '123123123', 1, 2, 1, 2, 1, 1, 1, '2017-04-25 10:25:03', NULL, NULL, '1', '15001060933', NULL, NULL, '2017-04-25 10:25:02', '2017-04-27 19:39:19', 0, NULL),
	(43, '1232141234', '123123123', 1, 2, 1, 2, 1, 1, 1, '2017-04-25 10:25:03', NULL, NULL, '1', '15001060933', NULL, NULL, '2017-04-25 10:25:02', '2017-04-27 19:39:19', 0, NULL),
	(44, '1232141234', '123123123', 1, 2, 1, 2, 1, 1, 1, '2017-04-25 10:25:03', NULL, NULL, '1', '15001060933', NULL, NULL, '2017-04-25 10:25:02', '2017-04-27 19:39:19', 0, NULL),
	(45, '1232141234', '123123123', 1, 2, 1, 2, 1, 1, 1, '2017-04-25 10:25:03', NULL, NULL, '1', '15001060933', NULL, NULL, '2017-04-25 10:25:02', '2017-04-27 19:39:19', 0, NULL),
	(46, '1232141234', '123123123', 1, 2, 1, 2, 1, 1, 1, '2017-04-25 10:25:03', NULL, NULL, '1', '15001060933', NULL, NULL, '2017-04-25 10:25:02', '2017-04-27 19:39:19', 0, NULL),
	(47, '1232141234', '123123123', 1, 2, 1, 2, 1, 1, 1, '2017-04-25 10:25:03', NULL, NULL, '1', '15001060933', NULL, NULL, '2017-04-25 10:25:02', '2017-04-27 19:39:19', 0, NULL),
	(48, '58043713704373951514288', NULL, 1, 2, 0, 2, 0, 1, 0, '2017-04-28 03:29:57', NULL, NULL, NULL, NULL, NULL, NULL, '2017-04-28 03:29:57', '2017-04-28 03:29:57', 0, NULL),
	(49, '58068170170310682521568', NULL, 1, 2, 0, 2, 0, 8, 0, '2017-04-28 03:34:02', NULL, NULL, NULL, NULL, NULL, NULL, '2017-04-28 03:34:01', '2017-04-28 03:34:02', 0, NULL),
	(50, '904767155620085721004258', '123', 1, 2, 0, 2, 0, 1, 0, '2017-04-28 04:42:56', NULL, NULL, NULL, NULL, NULL, NULL, '2017-04-28 03:45:24', '2017-04-28 04:42:56', 0, NULL),
	(51, '11432187051677288291469', NULL, 1, 2, 0, 0, 0, 1, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-10 17:47:27', '2017-05-10 17:47:27', 0, NULL),
	(52, '11432936191177839928352', NULL, 1, 2, 0, 0, 0, 1, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-10 17:47:35', '2017-05-10 17:47:35', 0, NULL),
	(53, '11433646910225646215492', NULL, 1, 2, 0, 0, 0, 1, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-10 17:47:42', '2017-05-10 17:47:42', 0, NULL),
	(54, '11828712230801099587384', NULL, 1, 2, 0, 0, 0, 1, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-10 18:53:33', '2017-05-10 18:53:33', 0, NULL);
/*!40000 ALTER TABLE `sale_product_order` ENABLE KEYS */;

-- 导出  表 joyclub.sale_product_order_detail 结构
CREATE TABLE IF NOT EXISTS `sale_product_order_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `store_order_id` bigint(20) NOT NULL COMMENT '商户子订单号',
  `product_attr` bigint(20) NOT NULL COMMENT '商品属性id',
  `num` int(11) NOT NULL COMMENT '购买数量',
  `price` float NOT NULL COMMENT '购买时的价格',
  `point_rate` float NOT NULL COMMENT '购买时的积分比例',
  `money_paid` float NOT NULL COMMENT '为该单支付的金钱，和积分二者有一个为0',
  `point_paid` int(11) unsigned NOT NULL COMMENT '为该单支付的积分，和积分二者有一个为0',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COMMENT='商品订单详情表';

-- 正在导出表  joyclub.sale_product_order_detail 的数据：~28 rows (大约)
/*!40000 ALTER TABLE `sale_product_order_detail` DISABLE KEYS */;
INSERT INTO `sale_product_order_detail` (`id`, `store_order_id`, `product_attr`, `num`, `price`, `point_rate`, `money_paid`, `point_paid`, `create_time`, `last_update`, `delete_flag`, `delete_time`) VALUES
	(1, 1, 2, 1, 1, 1, 0, 123100, '2017-04-25 10:26:11', '2017-04-26 11:14:10', 0, NULL),
	(4, 1, 2, 1, 1, 1, 1, 0, '2017-04-25 10:26:11', '2017-04-26 11:13:52', 0, NULL),
	(5, 2, 2, 1, 1, 1, 0, 432400, '2017-04-25 10:26:11', '2017-04-26 11:14:20', 0, NULL),
	(6, 2, 2, 1, 1, 1, 1, 0, '2017-04-25 10:26:11', '2017-04-26 11:13:54', 0, NULL),
	(7, 2, 2, 1, 1, 1, 1, 0, '2017-04-25 10:26:11', '2017-04-26 11:13:55', 0, NULL),
	(8, 10, 2, 1, 1, 1, 0, 872000, '2017-04-25 10:26:11', '2017-04-28 02:17:56', 0, NULL),
	(9, 11, 2, 1, 1, 1, 1, 1, '2017-04-25 10:26:11', '2017-04-28 02:17:58', 0, NULL),
	(10, 12, 2, 1, 1, 1, 0, 98000, '2017-04-25 10:26:11', '2017-04-28 02:17:59', 0, NULL),
	(11, 13, 2, 1, 1, 1, 1, 0, '2017-04-25 10:26:11', '2017-04-28 02:18:02', 0, NULL),
	(23, 14, 2, 1, 1, 1, 0, 89123, '2017-04-25 10:26:11', '2017-04-28 02:18:03', 0, NULL),
	(24, 15, 2, 1, 1, 1, 1, 1, '2017-04-25 10:26:11', '2017-04-28 02:18:04', 0, NULL),
	(25, 16, 2, 1, 1, 1, 1, 0, '2017-04-25 10:26:11', '2017-04-28 02:18:06', 0, NULL),
	(26, 17, 2, 1, 1, 1, 0, 1, '2017-04-25 10:26:11', '2017-04-28 02:18:08', 0, NULL),
	(27, 18, 2, 1, 1, 1, 0, 89123, '2017-04-25 10:26:11', '2017-04-28 02:18:10', 0, NULL),
	(28, 18, 5, 1, 1, 1, 1, 0, '2017-04-26 11:46:40', '2017-04-26 11:46:40', 0, NULL),
	(29, 19, 5, 3, 1, 1, 3, 0, '2017-04-27 20:25:48', '2017-04-27 20:25:48', 0, NULL),
	(30, 20, 5, 3, 1, 1, 3, 0, '2017-04-27 20:27:50', '2017-04-27 20:27:50', 0, NULL),
	(31, 21, 5, 3, 1, 1, 3, 0, '2017-04-27 20:27:50', '2017-04-28 02:19:33', 0, NULL),
	(32, 22, 5, 3, 1, 1, 3, 0, '2017-04-27 20:27:50', '2017-04-28 02:19:36', 0, NULL),
	(33, 23, 5, 3, 1, 1, 3, 0, '2017-04-27 20:27:50', '2017-04-28 02:19:37', 0, NULL),
	(34, 24, 5, 3, 1, 1, 3, 0, '2017-04-27 20:27:50', '2017-04-28 02:19:39', 0, NULL),
	(35, 25, 5, 3, 1, 1, 3, 0, '2017-04-27 20:27:50', '2017-04-28 02:19:39', 0, NULL),
	(36, 48, 5, 1, 1, 1, 0, 1, '2017-04-28 03:29:57', '2017-04-28 03:29:57', 0, NULL),
	(37, 49, 4, 1, 1, 1, 0, 1, '2017-04-28 03:34:01', '2017-04-28 03:34:01', 0, NULL),
	(38, 49, 3, 1, 1, 1, 0, 1, '2017-04-28 03:34:01', '2017-04-28 03:34:01', 0, NULL),
	(39, 49, 2, 1, 1, 1, 0, 1, '2017-04-28 03:34:02', '2017-04-28 03:34:02', 0, NULL),
	(40, 49, 5, 5, 1, 1, 0, 5, '2017-04-28 03:34:02', '2017-04-28 03:34:02', 0, NULL),
	(41, 50, 5, 1, 1, 1, 0, 1, '2017-04-28 03:45:24', '2017-04-28 03:45:24', 0, NULL),
	(42, 32, 2, 1, 1, 1, 0, 1, '2017-05-10 17:47:28', '2017-05-10 17:47:28', 0, NULL),
	(43, 33, 2, 1, 1, 1, 0, 1, '2017-05-10 17:47:35', '2017-05-10 17:47:35', 0, NULL),
	(44, 34, 2, 1, 1, 1, 0, 1, '2017-05-10 17:47:42', '2017-05-10 17:47:42', 0, NULL),
	(45, 35, 2, 1, 1, 1, 0, 1, '2017-05-10 18:53:33', '2017-05-10 18:53:33', 0, NULL);
/*!40000 ALTER TABLE `sale_product_order_detail` ENABLE KEYS */;

-- 导出  表 joyclub.sale_product_order_store 结构
CREATE TABLE IF NOT EXISTS `sale_product_order_store` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `store_id` bigint(20) NOT NULL COMMENT '商户id',
  `money_sum` float NOT NULL COMMENT '金钱总和',
  `point_sum` int(11) NOT NULL COMMENT '积分总和',
  `status` tinyint(4) NOT NULL COMMENT '0 待支付 1已取消 2 已支付 3已发货  4 已收货 5 退款中 6已退款',
  `delivery_time` datetime DEFAULT NULL COMMENT '发货时间',
  `delivery_company` varchar(20) DEFAULT NULL COMMENT '如果是快递并且已发货，提供快递公司名称',
  `delivery_code` varchar(50) DEFAULT NULL COMMENT '如果是快递并且已发货，提供快递单号',
  `receive_time` datetime DEFAULT NULL COMMENT '收获时间（自提和快递）',
  `notice_refund_time` datetime DEFAULT NULL COMMENT '商户确认退款时间',
  `refund_time` datetime DEFAULT NULL COMMENT '财务退款时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='商品订单表-商户子订单\r\n\r\n';

-- 正在导出表  joyclub.sale_product_order_store 的数据：~27 rows (大约)
/*!40000 ALTER TABLE `sale_product_order_store` DISABLE KEYS */;
INSERT INTO `sale_product_order_store` (`id`, `order_id`, `store_id`, `money_sum`, `point_sum`, `status`, `delivery_time`, `delivery_company`, `delivery_code`, `receive_time`, `notice_refund_time`, `refund_time`, `create_time`, `last_update`, `delete_flag`, `delete_time`) VALUES
	(1, 1, 14, 1, 1, 2, NULL, NULL, NULL, NULL, NULL, NULL, '2017-04-25 10:25:43', '2017-04-28 04:46:46', 0, NULL),
	(6, 33, 14, 1, 1, 2, '2017-04-27 19:37:02', 'test', '123', '2017-04-27 19:37:48', NULL, NULL, '2017-04-25 10:25:43', '2017-04-28 04:46:11', 0, NULL),
	(7, 18, 14, 0, 1, 2, NULL, NULL, NULL, NULL, NULL, NULL, '2017-04-26 11:46:40', '2017-04-28 04:46:46', 0, NULL),
	(8, 19, 14, 0, 3, 2, NULL, NULL, NULL, NULL, NULL, NULL, '2017-04-27 20:25:48', '2017-04-28 04:46:46', 0, NULL),
	(9, 20, 14, 0, 3, 2, NULL, NULL, NULL, NULL, NULL, NULL, '2017-04-27 20:27:50', '2017-04-28 04:46:46', 0, NULL),
	(10, 34, 14, 1, 1, 2, '2017-04-27 19:37:02', 'test', '123', '2017-04-27 19:37:48', NULL, NULL, '2017-04-25 10:25:43', '2017-04-28 04:46:46', 0, NULL),
	(11, 35, 14, 1, 1, 2, '2017-04-27 19:37:02', 'test', '123', '2017-04-27 19:37:48', NULL, NULL, '2017-04-25 10:25:43', '2017-04-28 04:46:46', 0, NULL),
	(12, 36, 14, 1, 1, 2, '2017-04-27 19:37:02', 'test', '123', '2017-04-27 19:37:48', NULL, NULL, '2017-04-25 10:25:43', '2017-04-28 04:46:46', 0, NULL),
	(13, 37, 14, 1, 1, 2, '2017-04-27 19:37:02', 'test', '123', '2017-04-27 19:37:48', NULL, NULL, '2017-04-25 10:25:43', '2017-04-28 04:46:46', 0, NULL),
	(14, 38, 14, 1, 1, 2, '2017-04-27 19:37:02', 'test', '123', '2017-04-27 19:37:48', NULL, NULL, '2017-04-25 10:25:43', '2017-04-28 04:46:46', 0, NULL),
	(15, 39, 14, 1, 1, 2, '2017-04-27 19:37:02', 'test', '123', '2017-04-27 19:37:48', NULL, NULL, '2017-04-25 10:25:43', '2017-04-28 04:46:46', 0, NULL),
	(16, 40, 14, 1, 1, 2, '2017-04-27 19:37:02', 'test', '123', '2017-04-27 19:37:48', NULL, NULL, '2017-04-25 10:25:43', '2017-04-28 04:46:46', 0, NULL),
	(17, 41, 14, 1, 1, 2, '2017-04-27 19:37:02', 'test', '123', '2017-04-27 19:37:48', NULL, NULL, '2017-04-25 10:25:43', '2017-04-28 04:46:46', 0, NULL),
	(18, 42, 14, 1, 1, 2, '2017-04-27 19:37:02', 'test', '123', '2017-04-27 19:37:48', NULL, NULL, '2017-04-25 10:25:43', '2017-04-28 04:46:46', 0, NULL),
	(19, 43, 14, 1, 1, 2, '2017-04-27 19:37:02', 'test', '123', '2017-04-27 19:37:48', NULL, NULL, '2017-04-25 10:25:43', '2017-04-28 04:46:46', 0, NULL),
	(20, 44, 14, 1, 1, 2, '2017-04-27 19:37:02', 'test', '123', '2017-04-27 19:37:48', NULL, NULL, '2017-04-25 10:25:43', '2017-04-28 04:46:46', 0, NULL),
	(21, 45, 14, 1, 1, 2, '2017-04-27 19:37:02', 'test', '123', '2017-04-27 19:37:48', NULL, NULL, '2017-04-25 10:25:43', '2017-04-28 04:46:46', 0, NULL),
	(22, 46, 14, 1, 1, 2, '2017-04-27 19:37:02', 'test', '123', '2017-04-27 19:37:48', NULL, NULL, '2017-04-25 10:25:43', '2017-04-28 04:46:46', 0, NULL),
	(23, 47, 14, 1, 1, 2, '2017-04-27 19:37:02', 'test', '123', '2017-04-27 19:37:48', NULL, NULL, '2017-04-25 10:25:43', '2017-04-28 04:46:46', 0, NULL),
	(24, 17, 14, 1, 1, 2, NULL, NULL, NULL, NULL, NULL, NULL, '2017-04-25 10:25:43', '2017-04-28 04:46:46', 0, NULL),
	(25, 33, 14, 1, 1, 2, NULL, NULL, NULL, NULL, NULL, NULL, '2017-04-25 10:25:43', '2017-04-28 04:46:46', 0, NULL),
	(26, 48, 14, 1, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, '2017-04-28 03:29:57', '2017-04-28 03:29:58', 0, NULL),
	(27, 49, 14, 1, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, '2017-04-28 03:34:01', '2017-04-28 03:34:02', 0, NULL),
	(28, 49, 14, 2, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, '2017-04-28 03:34:01', '2017-04-28 03:34:02', 0, NULL),
	(29, 49, 14, 3, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, '2017-04-28 03:34:01', '2017-04-28 03:34:02', 0, NULL),
	(30, 49, 14, 8, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, '2017-04-28 03:34:02', '2017-04-28 03:34:02', 0, NULL),
	(31, 50, 14, 1, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, '2017-04-28 03:45:24', '2017-04-28 03:45:25', 0, NULL),
	(32, 51, 14, 0, 1, 0, NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-10 17:47:27', '2017-05-10 17:47:27', 0, NULL),
	(33, 52, 14, 0, 1, 0, NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-10 17:47:35', '2017-05-10 17:47:35', 0, NULL),
	(34, 53, 14, 0, 1, 0, NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-10 17:47:42', '2017-05-10 17:47:42', 0, NULL),
	(35, 54, 14, 0, 1, 0, NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-10 18:53:33', '2017-05-10 18:53:33', 0, NULL);
/*!40000 ALTER TABLE `sale_product_order_store` ENABLE KEYS */;

-- 导出  表 joyclub.sale_product_price 结构
CREATE TABLE IF NOT EXISTS `sale_product_price` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) NOT NULL COMMENT '商品id',
  `price` float NOT NULL COMMENT '价格',
  `point_rate` float DEFAULT NULL COMMENT '消费金钱后，兑换积分比例 如果不赋值则继承商品分类的比例，也可以赋值',
  `start_time` datetime(3) NOT NULL COMMENT '开始时间',
  `end_time` datetime(3) NOT NULL COMMENT '结束时间',
  `forbid_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '下架标志',
  `special_price_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '特价标志，目前对应秒杀',
  `review_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '审核状态 0 待审核，1 审核通过 ，2 审核拒绝 ',
  `review_info` varchar(50) DEFAULT NULL COMMENT '审核相关文本，主要用于拒绝，无法记录历史',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='商品价格管理\r\nstartTime endTime 决定了生效时间，forbid_flag用于强行下架\r\n';

-- 正在导出表  joyclub.sale_product_price 的数据：~14 rows (大约)
/*!40000 ALTER TABLE `sale_product_price` DISABLE KEYS */;
INSERT INTO `sale_product_price` (`id`, `product_id`, `price`, `point_rate`, `start_time`, `end_time`, `forbid_flag`, `special_price_flag`, `review_status`, `review_info`, `create_time`, `last_update`, `delete_flag`, `delete_time`) VALUES
	(9, 1, 1, 1, '2017-04-07 00:00:00.000', '2017-09-07 23:59:59.999', 0, 0, 1, NULL, '2017-04-17 17:56:40', '2017-04-26 11:42:09', 0, NULL),
	(10, 9, 1, 1, '2017-04-07 00:00:00.000', '2017-09-07 23:59:59.999', 0, 0, 1, NULL, '2017-04-07 17:56:40', '2017-04-13 21:44:31', 0, NULL),
	(11, 10, 1, 1, '2017-04-07 00:00:00.000', '2017-09-07 23:59:59.999', 0, 0, 1, NULL, '2017-04-07 17:56:40', '2017-04-13 21:44:31', 0, NULL),
	(12, 11, 1, 1, '2017-04-07 00:00:00.000', '2017-09-07 23:59:59.999', 0, 0, 1, NULL, '2017-04-07 17:56:40', '2017-04-16 14:34:18', 0, NULL),
	(13, 12, 1, 1, '2017-04-07 00:00:00.000', '2017-09-07 23:59:59.999', 0, 0, 1, NULL, '2017-04-07 17:56:40', '2017-04-16 14:34:18', 0, NULL),
	(14, 13, 1, 1, '2017-04-07 00:00:00.000', '2017-09-07 23:59:59.999', 0, 0, 1, NULL, '2017-04-07 17:56:40', '2017-04-16 14:34:18', 0, NULL),
	(15, 14, 1, 1, '2017-04-07 00:00:00.000', '2017-09-07 23:59:59.999', 0, 0, 1, NULL, '2017-04-07 17:56:40', '2017-04-16 14:34:18', 0, NULL),
	(16, 15, 1, 1, '2017-04-07 00:00:00.000', '2017-09-07 23:59:59.999', 0, 0, 1, NULL, '2017-04-07 17:56:40', '2017-04-16 14:34:18', 0, NULL),
	(17, 16, 1, 1, '2017-04-07 00:00:00.000', '2017-09-07 23:59:59.999', 0, 0, 1, NULL, '2017-04-07 17:56:40', '2017-04-16 14:34:18', 0, NULL),
	(18, 17, 1, 1, '2017-04-07 00:00:00.000', '2017-09-07 23:59:59.999', 0, 0, 1, NULL, '2017-04-07 17:56:40', '2017-04-16 14:34:18', 0, NULL),
	(19, 18, 1, 1, '2017-04-07 00:00:00.000', '2017-09-07 23:59:59.999', 0, 0, 1, NULL, '2017-04-07 17:56:40', '2017-04-16 14:34:18', 0, NULL),
	(20, 19, 1, 1, '2017-04-07 00:00:00.000', '2017-09-07 23:59:59.999', 0, 1, 1, NULL, '2017-04-07 17:56:40', '2017-05-08 16:55:36', 0, NULL),
	(21, 20, 1, 1, '2017-04-07 00:00:00.000', '2017-09-07 23:59:59.999', 0, 1, 1, NULL, '2017-04-07 17:56:40', '2017-05-08 16:55:35', 0, NULL),
	(22, 21, 1, 1, '2017-04-07 00:00:00.000', '2017-09-07 23:59:59.999', 0, 1, 1, NULL, '2017-04-07 17:56:40', '2017-05-08 16:55:34', 0, NULL),
	(23, 31, 123, 123, '2017-05-08 00:00:00.000', '2017-05-09 23:59:59.000', 0, 1, 1, NULL, '2017-05-08 16:25:24', '2017-05-08 16:26:25', 0, NULL),
	(24, 31, 1, NULL, '2017-05-11 14:51:18.000', '2017-06-07 14:52:19.000', 0, 1, 0, NULL, '2017-05-10 11:48:26', '2017-05-10 11:48:26', 0, NULL);
/*!40000 ALTER TABLE `sale_product_price` ENABLE KEYS */;

-- 导出  表 joyclub.sale_store_designer 结构
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

-- 正在导出表  joyclub.sale_store_designer 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `sale_store_designer` DISABLE KEYS */;
INSERT INTO `sale_store_designer` (`id`, `store_id`, `head_img`, `portrait`, `name`, `intro`, `html_content`, `json_content`, `create_time`, `last_update`, `delete_flag`, `delate_time`) VALUES
	(1, 14, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '张三啊', '我是一个好人阿萨德发生的阿斯顿发生的发生地方阿斯蒂芬阿斯蒂芬', '<p>爱要大声说出来，不要错过才后悔没有珍惜，对朋友的关心和呵护要用行动去证明，哪怕是一句问候，微信不该让彼此疏远，而是更亲近知道彼此的思念。</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/6fa221d8fee2406abdd64ea508cb387a.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/e28831b2bc1c4c4787a91dcb6d9023dc.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p>&nbsp;</p>\r\n<p>我们相识于干花</p>\r\n<p>体验押花香薰蜡过程</p>\r\n<p>制作简单漂亮的包装圆柱形礼物</p>\r\n<img src="http://bjmallback.stcl365.com:8071/image/d09dc0b6b03946fd88d94ae457762663.jpg" style="float:none;height: auto;width: 100%"/>\r\n<p></p>\r\n<ol>\r\n<li>活动时长：3小时左右</li>\r\n<li>参与凭证：购买订单        </li>\r\n<li>活动时间：2017年2月25日至2017年5月31日 </li>\r\n<li>预约电话：15910816157</li>\r\n<li>参加活动时需提前三天预约</li>\r\n<li>团购后请致电预约参加时间和参加地点<br>\r\n</li>\r\n</ol>\r\n<p>​&nbsp;</p>\r\n\r\n\r\n', '{"entityMap":{},"blocks":[{"key":"cdkur","text":"123123","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]}', '2017-03-30 13:00:25', '2017-05-09 20:39:45', 0, NULL);
/*!40000 ALTER TABLE `sale_store_designer` ENABLE KEYS */;

-- 导出  表 joyclub.sys_act_category 结构
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

-- 正在导出表  joyclub.sys_act_category 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `sys_act_category` DISABLE KEYS */;
INSERT INTO `sys_act_category` (`id`, `name`, `point_rate`, `create_time`, `last_update`, `delete_flag`, `delate_time`) VALUES
	(1, 'act', 12, '2017-04-05 17:12:40', '2017-04-05 17:12:44', 0, NULL);
/*!40000 ALTER TABLE `sys_act_category` ENABLE KEYS */;

-- 导出  表 joyclub.sys_msg_auth_code 结构
CREATE TABLE IF NOT EXISTS `sys_msg_auth_code` (
  `phone` varchar(15) NOT NULL COMMENT '手机号码',
  `code` varchar(15) NOT NULL COMMENT '验证码，目前是6位',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信验证码表';

-- 正在导出表  joyclub.sys_msg_auth_code 的数据：~9 rows (大约)
/*!40000 ALTER TABLE `sys_msg_auth_code` DISABLE KEYS */;
INSERT INTO `sys_msg_auth_code` (`phone`, `code`, `create_time`) VALUES
	('15001060933', '895819', '2017-04-11 10:41:12'),
	('15001060933', '870405', '2017-04-12 16:53:08'),
	('17600690737', '870405', '2017-04-12 19:53:08'),
	('15001060933', '363855', '2017-04-16 17:22:07'),
	('15001060933', '457650', '2017-04-16 17:23:03'),
	('15001060933', '366167', '2017-04-16 17:23:56'),
	('15001060933', '111111', '2017-04-17 01:26:20'),
	('17600690739', '111111', '2017-04-17 17:30:31'),
	('13641047453', '111111', '2017-04-19 17:30:31');
/*!40000 ALTER TABLE `sys_msg_auth_code` ENABLE KEYS */;

-- 导出  表 joyclub.sys_product_category 结构
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

-- 正在导出表  joyclub.sys_product_category 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `sys_product_category` DISABLE KEYS */;
INSERT INTO `sys_product_category` (`id`, `name`, `point_rate`, `create_time`, `last_update`, `delete_flag`, `delate_time`) VALUES
	(1, 'c11', 100, '2017-03-29 16:17:08', '2017-04-20 10:28:36', 0, NULL),
	(2, 'c2', 1, '2017-03-29 16:31:33', '2017-03-29 16:37:30', 0, NULL),
	(3, 'c3', 1.1, '2017-03-29 16:37:05', '2017-03-29 16:37:58', 0, NULL),
	(4, '11', 1, '2017-03-30 14:43:37', '2017-03-30 14:43:37', 0, NULL);
/*!40000 ALTER TABLE `sys_product_category` ENABLE KEYS */;

-- 导出  表 joyclub.sys_project 结构
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统项目表 1 商业项目 2写字楼项目项目 3 住宅项目 10 第三方合作项目  0 平台（平台也当作一个项目来处理，但是平台类型项目有且只能有一个）';

-- 正在导出表  joyclub.sys_project 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `sys_project` DISABLE KEYS */;
INSERT INTO `sys_project` (`id`, `type`, `name`, `headquarters`, `code`, `vip_share`, `card_resource_project`, `address`, `remark`, `contact_name`, `contact_phone`, `wechat_app_id`, `wechat_app_secret`, `wechat_token_address`, `create_time`, `last_update`, `delete_flag`, `delete_time`) VALUES
	(1, 0, '11', '27', '1', '00', '05', '1', '1', '1231', '1', 'wx567b2af01e4b31a1', 'b2ba4d5a6d1467cd1fc1fd2da6a3a29a', 'http://bjmall.stcl365.com:20000/wechat/public/getToken', '2017-03-28 19:10:16', '2017-04-11 17:03:44', 0, NULL);
/*!40000 ALTER TABLE `sys_project` ENABLE KEYS */;

-- 导出  表 joyclub.sys_project_vip_card_range 结构
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='项目卡号段';

-- 正在导出表  joyclub.sys_project_vip_card_range 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `sys_project_vip_card_range` DISABLE KEYS */;
INSERT INTO `sys_project_vip_card_range` (`id`, `project_id`, `type`, `min`, `max`, `create_time`, `last_update`, `delete_flag`, `delate_time`) VALUES
	(8, 1, '74', 1, 1000, '2017-04-17 13:49:59', '2017-04-17 13:55:02', 0, NULL);
/*!40000 ALTER TABLE `sys_project_vip_card_range` ENABLE KEYS */;

-- 导出  表 joyclub.sys_store 结构
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

-- 正在导出表  joyclub.sys_store 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `sys_store` DISABLE KEYS */;
INSERT INTO `sys_store` (`id`, `project_id`, `head_img`, `portrait`, `intro`, `name`, `contact_name`, `contact_phone`, `pickup_address`, `service_phone`, `create_time`, `last_update`, `delete_flag`, `delete_time`) VALUES
	(1, 1, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '介绍', '哈哈', ' 1', 't2', '43', '3', '2017-03-29 14:42:42', '2017-04-25 18:34:06', 0, NULL),
	(14, 1, 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', 'http://bjmallback.stcl365.com:8071/image/2aa851cd2441402bb7e4be4e8c645484.jpg', '介绍', '哈哈', ' 1', 't2', '43', '3', '2017-03-29 14:42:42', '2017-04-25 18:34:06', 0, NULL);
/*!40000 ALTER TABLE `sys_store` ENABLE KEYS */;

-- 导出  表 joyclub.sys_user 结构
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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- 正在导出表  joyclub.sys_user 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`id`, `info_id`, `account`, `password`, `type`, `sub_type`, `auth_type`, `forbid_flag`, `forbid_time`, `remark`, `create_time`, `last_update`, `delete_flag`, `delete_time`) VALUES
	(2, 1, 'Test', 'cd191756c445232b02d1feb94ef59309', 1, 0, NULL, 0, NULL, '平台管理者', '2017-02-23 15:24:34', '2017-04-01 13:37:53', 0, NULL),
	(8, 14, 'Test1', 'cd191756c445232b02d1feb94ef59309', 3, NULL, NULL, 0, NULL, '1', '2017-03-29 15:29:05', '2017-03-29 19:00:57', 0, NULL),
	(20, 14, '0', 'cd191756c445232b02d1feb94ef59309', 3, NULL, NULL, 0, NULL, NULL, '2017-03-31 17:36:24', '2017-03-31 17:36:24', 0, NULL),
	(21, 1, 'Test2', 'cd191756c445232b02d1feb94ef59309', 2, 0, NULL, 0, NULL, '项目管理者', '2017-02-23 15:24:34', '2017-04-27 11:48:13', 0, NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;

-- 导出  表 joyclub.sys_vip_card_num 结构
CREATE TABLE IF NOT EXISTS `sys_vip_card_num` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL COMMENT '号段开始值',
  `num` bigint(20) NOT NULL,
  `type` varchar(10) NOT NULL COMMENT '号段类型 01璀璨卡 02缤纷卡 74电子卡',
  `batch` varchar(50) NOT NULL COMMENT '批次号',
  `status` tinyint(4) NOT NULL COMMENT '状态  1未使用 2 已使用 3 已换卡',
  `use_time` datetime DEFAULT NULL COMMENT '卡领取时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delate_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8 COMMENT='vip卡号\r\n';

-- 正在导出表  joyclub.sys_vip_card_num 的数据：~130 rows (大约)
/*!40000 ALTER TABLE `sys_vip_card_num` DISABLE KEYS */;
INSERT INTO `sys_vip_card_num` (`id`, `project_id`, `num`, `type`, `batch`, `status`, `use_time`, `create_time`, `last_update`, `delete_flag`, `delate_time`) VALUES
	(1, 1, 1, '74', '123', 1, '2017-04-17 13:55:07', '2017-04-17 13:55:07', '2017-04-27 14:04:29', 0, NULL),
	(2, 1, 2, '74', '123', 1, NULL, '2017-04-17 13:55:07', '2017-04-17 13:55:07', 0, NULL),
	(3, 1, 3, '74', '123', 1, NULL, '2017-04-17 13:55:07', '2017-04-17 13:55:07', 0, NULL),
	(4, 1, 4, '74', '123', 1, NULL, '2017-04-17 13:55:07', '2017-04-17 13:55:07', 0, NULL),
	(5, 1, 5, '74', '123', 1, NULL, '2017-04-17 13:55:07', '2017-04-17 13:55:07', 0, NULL),
	(6, 1, 6, '74', '123', 1, NULL, '2017-04-17 13:55:07', '2017-04-17 13:55:07', 0, NULL),
	(7, 1, 7, '74', '123', 1, NULL, '2017-04-17 13:55:07', '2017-04-17 13:55:07', 0, NULL),
	(8, 1, 8, '74', '123', 1, NULL, '2017-04-17 13:55:07', '2017-04-17 13:55:07', 0, NULL),
	(9, 1, 9, '74', '123', 1, NULL, '2017-04-17 13:55:07', '2017-04-17 13:55:07', 0, NULL),
	(10, 1, 10, '74', '123', 1, NULL, '2017-04-17 13:55:07', '2017-04-17 13:55:07', 0, NULL),
	(11, 1, 2, '74', '1234', 1, NULL, '2017-04-17 13:55:18', '2017-04-17 13:55:18', 0, NULL),
	(12, 1, 3, '74', '1234', 1, NULL, '2017-04-17 13:55:18', '2017-04-17 13:55:18', 0, NULL),
	(13, 1, 4, '74', '1234', 1, NULL, '2017-04-17 13:55:18', '2017-04-17 13:55:18', 0, NULL),
	(14, 1, 5, '74', '1234', 1, NULL, '2017-04-17 13:55:18', '2017-04-17 13:55:18', 0, NULL),
	(15, 1, 6, '74', '1234', 1, NULL, '2017-04-17 13:55:18', '2017-04-17 13:55:18', 0, NULL),
	(16, 1, 7, '74', '1234', 1, NULL, '2017-04-17 13:55:18', '2017-04-17 13:55:18', 0, NULL),
	(17, 1, 8, '74', '1234', 1, NULL, '2017-04-17 13:55:18', '2017-04-17 13:55:18', 0, NULL),
	(18, 1, 9, '74', '1234', 1, NULL, '2017-04-17 13:55:18', '2017-04-17 13:55:18', 0, NULL),
	(19, 1, 10, '74', '1234', 1, NULL, '2017-04-17 13:55:18', '2017-04-17 13:55:18', 0, NULL),
	(20, 1, 11, '74', '1234', 1, NULL, '2017-04-17 13:55:18', '2017-04-17 13:55:18', 0, NULL),
	(21, 1, 12, '74', '12344', 1, NULL, '2017-04-17 13:59:24', '2017-04-17 13:59:24', 0, NULL),
	(22, 1, 13, '74', '12344', 1, NULL, '2017-04-17 13:59:24', '2017-04-17 13:59:24', 0, NULL),
	(23, 1, 14, '74', '12344', 1, NULL, '2017-04-17 13:59:24', '2017-04-17 13:59:24', 0, NULL),
	(24, 1, 15, '74', '12344', 1, NULL, '2017-04-17 13:59:24', '2017-04-17 13:59:24', 0, NULL),
	(25, 1, 16, '74', '12344', 1, NULL, '2017-04-17 13:59:24', '2017-04-17 13:59:24', 0, NULL),
	(26, 1, 17, '74', '12344', 1, NULL, '2017-04-17 13:59:24', '2017-04-17 13:59:24', 0, NULL),
	(27, 1, 18, '74', '12344', 1, NULL, '2017-04-17 13:59:24', '2017-04-17 13:59:24', 0, NULL),
	(28, 1, 19, '74', '12344', 1, NULL, '2017-04-17 13:59:24', '2017-04-17 13:59:24', 0, NULL),
	(29, 1, 20, '74', '12344', 1, NULL, '2017-04-17 13:59:24', '2017-04-17 13:59:24', 0, NULL),
	(30, 1, 21, '74', '12344', 1, NULL, '2017-04-17 13:59:24', '2017-04-17 13:59:24', 0, NULL),
	(31, 1, 22, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(32, 1, 23, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(33, 1, 24, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(34, 1, 25, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(35, 1, 26, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(36, 1, 27, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(37, 1, 28, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(38, 1, 29, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(39, 1, 30, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(40, 1, 31, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(41, 1, 32, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(42, 1, 33, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(43, 1, 34, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(44, 1, 35, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(45, 1, 36, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(46, 1, 37, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(47, 1, 38, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(48, 1, 39, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(49, 1, 40, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(50, 1, 41, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(51, 1, 42, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(52, 1, 43, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(53, 1, 44, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(54, 1, 45, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(55, 1, 46, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(56, 1, 47, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(57, 1, 48, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(58, 1, 49, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(59, 1, 50, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(60, 1, 51, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(61, 1, 52, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(62, 1, 53, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(63, 1, 54, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(64, 1, 55, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(65, 1, 56, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(66, 1, 57, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(67, 1, 58, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(68, 1, 59, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(69, 1, 60, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(70, 1, 61, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(71, 1, 62, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(72, 1, 63, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(73, 1, 64, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(74, 1, 65, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(75, 1, 66, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(76, 1, 67, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(77, 1, 68, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(78, 1, 69, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(79, 1, 70, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(80, 1, 71, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(81, 1, 72, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(82, 1, 73, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(83, 1, 74, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(84, 1, 75, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(85, 1, 76, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(86, 1, 77, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(87, 1, 78, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(88, 1, 79, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(89, 1, 80, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(90, 1, 81, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(91, 1, 82, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(92, 1, 83, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(93, 1, 84, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(94, 1, 85, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(95, 1, 86, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(96, 1, 87, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(97, 1, 88, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(98, 1, 89, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(99, 1, 90, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(100, 1, 91, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(101, 1, 92, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(102, 1, 93, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(103, 1, 94, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(104, 1, 95, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(105, 1, 96, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(106, 1, 97, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(107, 1, 98, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(108, 1, 99, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(109, 1, 100, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(110, 1, 101, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(111, 1, 102, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(112, 1, 103, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(113, 1, 104, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(114, 1, 105, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(115, 1, 106, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(116, 1, 107, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(117, 1, 108, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(118, 1, 109, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(119, 1, 110, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(120, 1, 111, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(121, 1, 112, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(122, 1, 113, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(123, 1, 114, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(124, 1, 115, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(125, 1, 116, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(126, 1, 117, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(127, 1, 118, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(128, 1, 119, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(129, 1, 120, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL),
	(130, 1, 121, '74', '12345', 1, NULL, '2017-04-27 14:22:05', '2017-04-27 14:22:05', 0, NULL);
/*!40000 ALTER TABLE `sys_vip_card_num` ENABLE KEYS */;

-- 导出  表 joyclub.test 结构
CREATE TABLE IF NOT EXISTS `test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `test` float(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='test';

-- 正在导出表  joyclub.test 的数据：~5 rows (大约)
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` (`id`, `test`) VALUES
	(1, 12.00),
	(2, 12.00),
	(3, 3.00),
	(4, 4.00),
	(5, 4.00);
/*!40000 ALTER TABLE `test` ENABLE KEYS */;

-- 导出  表 joyclub.test_time 结构
CREATE TABLE IF NOT EXISTS `test_time` (
  `test` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  joyclub.test_time 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `test_time` DISABLE KEYS */;
INSERT INTO `test_time` (`test`) VALUES
	('2017-04-24 10:49:45'),
	('2017-04-25 00:00:00');
/*!40000 ALTER TABLE `test_time` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
