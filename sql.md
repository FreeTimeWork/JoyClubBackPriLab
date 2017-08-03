# 数据库修改历史
### 功能名 作者名
- 修改内容1（sql语句）
- 修改内容2（sql语句）

### Quartz持久化 王俊
- 持久化表格
```sql
    #
    # In your Quartz properties file, you'll need to set
    # org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.StdJDBCDelegate
    #
    #
    # By: Ron Cordell - roncordell
    #  I didn't see this anywhere, so I thought I'd post it here. This is the script from Quartz to create the tables in a MySQL database, modified to use INNODB instead of MYISAM.

    DROP TABLE IF EXISTS QRTZ_FIRED_TRIGGERS;
    DROP TABLE IF EXISTS QRTZ_PAUSED_TRIGGER_GRPS;
    DROP TABLE IF EXISTS QRTZ_SCHEDULER_STATE;
    DROP TABLE IF EXISTS QRTZ_LOCKS;
    DROP TABLE IF EXISTS QRTZ_SIMPLE_TRIGGERS;
    DROP TABLE IF EXISTS QRTZ_SIMPROP_TRIGGERS;
    DROP TABLE IF EXISTS QRTZ_CRON_TRIGGERS;
    DROP TABLE IF EXISTS QRTZ_BLOB_TRIGGERS;
    DROP TABLE IF EXISTS QRTZ_TRIGGERS;
    DROP TABLE IF EXISTS QRTZ_JOB_DETAILS;
    DROP TABLE IF EXISTS QRTZ_CALENDARS;

    CREATE TABLE QRTZ_JOB_DETAILS(
    SCHED_NAME VARCHAR(120) NOT NULL,
    JOB_NAME VARCHAR(200) NOT NULL,
    JOB_GROUP VARCHAR(200) NOT NULL,
    DESCRIPTION VARCHAR(250) NULL,
    JOB_CLASS_NAME VARCHAR(250) NOT NULL,
    IS_DURABLE VARCHAR(1) NOT NULL,
    IS_NONCONCURRENT VARCHAR(1) NOT NULL,
    IS_UPDATE_DATA VARCHAR(1) NOT NULL,
    REQUESTS_RECOVERY VARCHAR(1) NOT NULL,
    JOB_DATA BLOB NULL,
    PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP))
    ENGINE=InnoDB;

    CREATE TABLE QRTZ_TRIGGERS (
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    JOB_NAME VARCHAR(200) NOT NULL,
    JOB_GROUP VARCHAR(200) NOT NULL,
    DESCRIPTION VARCHAR(250) NULL,
    NEXT_FIRE_TIME BIGINT(13) NULL,
    PREV_FIRE_TIME BIGINT(13) NULL,
    PRIORITY INTEGER NULL,
    TRIGGER_STATE VARCHAR(16) NOT NULL,
    TRIGGER_TYPE VARCHAR(8) NOT NULL,
    START_TIME BIGINT(13) NOT NULL,
    END_TIME BIGINT(13) NULL,
    CALENDAR_NAME VARCHAR(200) NULL,
    MISFIRE_INSTR SMALLINT(2) NULL,
    JOB_DATA BLOB NULL,
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
    REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP))
    ENGINE=InnoDB;

    CREATE TABLE QRTZ_SIMPLE_TRIGGERS (
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    REPEAT_COUNT BIGINT(7) NOT NULL,
    REPEAT_INTERVAL BIGINT(12) NOT NULL,
    TIMES_TRIGGERED BIGINT(10) NOT NULL,
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
    REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
    ENGINE=InnoDB;

    CREATE TABLE QRTZ_CRON_TRIGGERS (
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    CRON_EXPRESSION VARCHAR(120) NOT NULL,
    TIME_ZONE_ID VARCHAR(80),
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
    REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
    ENGINE=InnoDB;

    CREATE TABLE QRTZ_SIMPROP_TRIGGERS
      (
        SCHED_NAME VARCHAR(120) NOT NULL,
        TRIGGER_NAME VARCHAR(200) NOT NULL,
        TRIGGER_GROUP VARCHAR(200) NOT NULL,
        STR_PROP_1 VARCHAR(512) NULL,
        STR_PROP_2 VARCHAR(512) NULL,
        STR_PROP_3 VARCHAR(512) NULL,
        INT_PROP_1 INT NULL,
        INT_PROP_2 INT NULL,
        LONG_PROP_1 BIGINT NULL,
        LONG_PROP_2 BIGINT NULL,
        DEC_PROP_1 NUMERIC(13,4) NULL,
        DEC_PROP_2 NUMERIC(13,4) NULL,
        BOOL_PROP_1 VARCHAR(1) NULL,
        BOOL_PROP_2 VARCHAR(1) NULL,
        PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
        FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
        REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
    ENGINE=InnoDB;

    CREATE TABLE QRTZ_BLOB_TRIGGERS (
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    BLOB_DATA BLOB NULL,
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    INDEX (SCHED_NAME,TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
    REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
    ENGINE=InnoDB;

    CREATE TABLE QRTZ_CALENDARS (
    SCHED_NAME VARCHAR(120) NOT NULL,
    CALENDAR_NAME VARCHAR(200) NOT NULL,
    CALENDAR BLOB NOT NULL,
    PRIMARY KEY (SCHED_NAME,CALENDAR_NAME))
    ENGINE=InnoDB;

    CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS (
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP))
    ENGINE=InnoDB;

    CREATE TABLE QRTZ_FIRED_TRIGGERS (
    SCHED_NAME VARCHAR(120) NOT NULL,
    ENTRY_ID VARCHAR(95) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    INSTANCE_NAME VARCHAR(200) NOT NULL,
    FIRED_TIME BIGINT(13) NOT NULL,
    SCHED_TIME BIGINT(13) NOT NULL,
    PRIORITY INTEGER NOT NULL,
    STATE VARCHAR(16) NOT NULL,
    JOB_NAME VARCHAR(200) NULL,
    JOB_GROUP VARCHAR(200) NULL,
    IS_NONCONCURRENT VARCHAR(1) NULL,
    REQUESTS_RECOVERY VARCHAR(1) NULL,
    PRIMARY KEY (SCHED_NAME,ENTRY_ID))
    ENGINE=InnoDB;

    CREATE TABLE QRTZ_SCHEDULER_STATE (
    SCHED_NAME VARCHAR(120) NOT NULL,
    INSTANCE_NAME VARCHAR(200) NOT NULL,
    LAST_CHECKIN_TIME BIGINT(13) NOT NULL,
    CHECKIN_INTERVAL BIGINT(13) NOT NULL,
    PRIMARY KEY (SCHED_NAME,INSTANCE_NAME))
    ENGINE=InnoDB;

    CREATE TABLE QRTZ_LOCKS (
    SCHED_NAME VARCHAR(120) NOT NULL,
    LOCK_NAME VARCHAR(40) NOT NULL,
    PRIMARY KEY (SCHED_NAME,LOCK_NAME))
    ENGINE=InnoDB;

    CREATE INDEX IDX_QRTZ_J_REQ_RECOVERY ON QRTZ_JOB_DETAILS(SCHED_NAME,REQUESTS_RECOVERY);
    CREATE INDEX IDX_QRTZ_J_GRP ON QRTZ_JOB_DETAILS(SCHED_NAME,JOB_GROUP);

    CREATE INDEX IDX_QRTZ_T_J ON QRTZ_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
    CREATE INDEX IDX_QRTZ_T_JG ON QRTZ_TRIGGERS(SCHED_NAME,JOB_GROUP);
    CREATE INDEX IDX_QRTZ_T_C ON QRTZ_TRIGGERS(SCHED_NAME,CALENDAR_NAME);
    CREATE INDEX IDX_QRTZ_T_G ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);
    CREATE INDEX IDX_QRTZ_T_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE);
    CREATE INDEX IDX_QRTZ_T_N_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);
    CREATE INDEX IDX_QRTZ_T_N_G_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);
    CREATE INDEX IDX_QRTZ_T_NEXT_FIRE_TIME ON QRTZ_TRIGGERS(SCHED_NAME,NEXT_FIRE_TIME);
    CREATE INDEX IDX_QRTZ_T_NFT_ST ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);
    CREATE INDEX IDX_QRTZ_T_NFT_MISFIRE ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);
    CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);
    CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE_GRP ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);

    CREATE INDEX IDX_QRTZ_FT_TRIG_INST_NAME ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME);
    CREATE INDEX IDX_QRTZ_FT_INST_JOB_REQ_RCVRY ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);
    CREATE INDEX IDX_QRTZ_FT_J_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
    CREATE INDEX IDX_QRTZ_FT_JG ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_GROUP);
    CREATE INDEX IDX_QRTZ_FT_T_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);
    CREATE INDEX IDX_QRTZ_FT_TG ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);

    commit;

```

### 存入猫酷的商家表

- 线下商家表
```
  CREATE TABLE `sys_shop` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `project_id` bigint(20) NOT NULL COMMENT '项目id',
    `code` bigint(20) DEFAULT NULL COMMENT 'CRM系统商家ID',
    `name` varchar(50) NOT NULL COMMENT '商家名称',
    `logo` varchar(200) NOT NULL COMMENT 'logo照片',
    `shop_type` tinyint(4) NOT NULL COMMENT '商家类型，1,普通 2,餐饮 3,影院 4,KTV 5,冰场',
    `commercial_type_id` bigint(20) NOT NULL COMMENT '一级业态ID',
    `sub_commercial_type_name` varchar(50) NOT NULL COMMENT '二级业态名称',
    `floor_id` tinyint(4) NOT NULL COMMENT '楼层id',
    `floor_name` varchar(50) NOT NULL COMMENT '楼层名称',
    `door_no` varchar(50) NOT NULL COMMENT '铺位号',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `delete_flag` tinyint(1) unsigned DEFAULT '0',
    `delete_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_sys_shop_code_project_id` (`code`,`project_id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='线下商家表';
```

### 新卡券相关表
```
CREATE TABLE `card_coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL COMMENT '项目id',
  `name` varchar(50) NOT NULL COMMENT '卡券名称',
  `type` tinyint(4) NOT NULL COMMENT '卡券类别，1,满减券 2,代金券 3,第三方',
  `batch` varchar(50) DEFAULT NULL COMMENT '第三方卡券号批次号',
  `thirdparty_shop_id` bigint(20) DEFAULT NULL COMMENT '第三方商家id',
  `display_weight` int(11) DEFAULT '0' COMMENT '显示权重，默认为0，越大越靠前',
  `sysgen_flag` tinyint(1) unsigned DEFAULT '1',
  `portrait` varchar(200) DEFAULT NULL COMMENT '卡券图',
  `num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '总数，该卡券发行量',
  `amount` decimal(14,4) NOT NULL DEFAULT '0' COMMENT '满额',
  `subtract_amount` decimal(14,4) NOT NULL DEFAULT '0' COMMENT '减额',
  `support_refund_flag` tinyint(1) unsigned DEFAULT '1' COMMENT '该券是否可以退货',
  `info` varchar(500) DEFAULT NULL COMMENT '卡券介绍',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='卡券表';

CREATE TABLE `card_third_party_shop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL COMMENT '项目id',
  `name` varchar(50) NOT NULL COMMENT '商铺名称',
  `info` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='第三方商铺';

CREATE TABLE `card_thirdparty_coupon_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `thirdparty_shop_id` bigint(20) DEFAULT NULL COMMENT '第三方商家id',
  `code` varchar(20) DEFAULT NULL COMMENT '第三方卡券号',
  `batch` varchar(50) NOT NULL COMMENT '第三方卡券号批次号',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_card_thirdparty_coupon_code_thirdparty_shop` (`code`,`thirdparty_shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='第三方卡券号中间表';

CREATE TABLE `card_coupon_store_scope` (
  `coupon_id` bigint(20) NOT NULL COMMENT '卡券id',
  `store_id` bigint(20) NOT NULL COMMENT '商户id',
  `ratio` decimal(8,6) NOT NULL COMMENT '分担比例，小数点后六位',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='使用店铺范围';

CREATE TABLE `card_coupon_vip_scope` (
  `coupon_id` bigint(20) NOT NULL COMMENT '卡券id',
  `vip_type` varchar(4) NOT NULL COMMENT 'vip类型 01璀璨卡 02缤纷卡 74电子卡',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员等级范围';

CREATE TABLE `card_coupon_launch` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `coupon_id` bigint(20) NOT NULL COMMENT '卡券id',
  `name` varchar(50) NOT NULL COMMENT '投放名称',
  `type` tinyint(4) NOT NULL COMMENT '投放类型，1,条件投放 2,线上投放 3,批量投放',
  `vip_batch` varchar(50) DEFAULT NULL COMMENT '批量投放对应的vip号的批次号',
  `condition_amount` decimal(14,4) DEFAULT NULL COMMENT '触发条件投放的总额',
  `max_receive` int(11) unsigned DEFAULT NULL COMMENT '每人每日最大获劵量',
  `pay_type` tinyint(4) NOT NULL COMMENT '支付方式 1,金钱 2,积分',
  `pay_amount` decimal(14,4) NOT NULL COMMENT '支付值 0代表免费',
  `launch_num` int(11) unsigned NOT NULL COMMENT '投放数量',
  `launch_start_time` datetime DEFAULT NULL COMMENT '投放开始时间',
  `launch_end_time` datetime DEFAULT NULL COMMENT '投放截止时间',
  `effective_start_time` datetime DEFAULT NULL COMMENT '有效开始时间',
  `effective_end_time` datetime DEFAULT NULL COMMENT '有效截止时间',
  `review_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '审核状态 0 待审核，1 审核通过 ，2 审核拒绝 ',
  `review_info` varchar(50) DEFAULT NULL COMMENT '审核相关文本，主要用于拒绝，无法记录历史',
  `confirm_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '确认投放标志，默认未投放',
  `forbid_flag` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '强制下架标志',
  `confirm_time` datetime DEFAULT NULL COMMENT '确认投放时间',
  `forbid_time` datetime DEFAULT NULL COMMENT '强制下架时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='卡券投放表';

CREATE TABLE `card_coupon_trigger_scope` (
  `launch_id` bigint(20) NOT NULL COMMENT '卡券投放id',
  `store_id` bigint(20) NOT NULL COMMENT '商户id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='触发店铺范围';


CREATE TABLE `card_coupon_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) DEFAULT NULL COMMENT '卡券号',
  `launch_id` bigint(20) NOT NULL COMMENT '卡券投放id',
  `order_code` varchar(20) DEFAULT NULL COMMENT '订单号',
  `belong` bigint(20) NOT NULL COMMENT '卡券所属，-1 系统自身卡券， 第三方卡券中的商家id',
  `client_id` bigint(20) NOT NULL COMMENT '会员号id',
  `receive_time` datetime DEFAULT NULL COMMENT '领取时间',
  `use_time` datetime DEFAULT NULL COMMENT '使用时间',
  `use_status` tinyint(4) NOT NULL COMMENT '使用状态，1,未使用 2,已使用 3,已作废',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_card_coupon_code_belong` (`code`,`belong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='卡券号';


CREATE TABLE `card_vip_batch` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `client_id` bigint(20) NOT NULL COMMENT '会员号id',
  `batch` varchar(50) NOT NULL COMMENT '批次号',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_card_vip_batch` (`client_id`,`batch`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='批量插入会员号的中间表';

CREATE TABLE `pos_sale_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_code` varchar(20) NOT NULL COMMENT '订单号',
  `client_id` bigint(20) NOT NULL COMMENT '会员号id',
  `shop_id` bigint(20)  NOT NULL COMMENT '线下商户id'，
  `payable` decimal(14,4) NOT NULL COMMENT '应付款，订单总额，未减去优惠额',
  `payment` decimal(14,4) NOT NULL COMMENT '订单实付总额，实际支付(如有优惠，减去优惠额)',
  `balance` decimal(14,4) NOT NULL COMMENT '退款后余额，如果没有退款，和实际支付一样；参与退款逻辑',
  `refund_time` datetime DEFAULT NULL COMMENT '退款时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付流水,pos消费详情';

CREATE TABLE `card_coupon_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) NOT NULL COMMENT '目前是nanoTime加八位随机数',
  `out_pay_code` varchar(64) DEFAULT NULL COMMENT '支付成功的外部单号,如果是纯积分，可以为空',
  `project_id` bigint(20) NOT NULL COMMENT '项目id',
  `client_id` bigint(20) NOT NULL COMMENT '客户id',
  `launch_id` bigint(20) NOT NULL COMMENT '卡券投放id',
  `status` tinyint(4) NOT NULL COMMENT '0 待支付 1已取消 2 已支付 ',
  `money_sum` decimal(14,4) NOT NULL COMMENT '用户为该单所需支付的总金钱',
  `point_sum` decimal(14,4) NOT NULL COMMENT '用户为该单所需支付的总积分',
  `pay_type` tinyint(4) DEFAULT NULL COMMENT '0微信 1支付宝',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `cancel_time` datetime DEFAULT NULL COMMENT '取消时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) unsigned DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_card_coupon_order_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8 COMMENT='卡券订单表，系统定时删除未支付订单，恢复库存，防止库存一直被占用';




--  修改表 sys_user 的注释

ALTER TABLE `sys_user`
	CHANGE COLUMN `type` `type` INT(11) NOT NULL COMMENT '账户类型（1：平台，2：项目账户，3：商户账户, 4：第三方商户账户）' AFTER `password`;
ALTER TABLE `sys_user`
	CHANGE COLUMN `info_id` `info_id` BIGINT(20) UNSIGNED NULL DEFAULT NULL COMMENT '关联者id,平台或项目账户关联项目id,商户账户关联商户id，第三方商户账户关联第三方商户id' AFTER `id`;




```
