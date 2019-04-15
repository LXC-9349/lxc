/*
 Navicat Premium Data Transfer

 Source Server         : 242
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : 192.168.10.242:3306
 Source Schema         : springbootdemo

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 07/01/2019 11:28:03
*/
-- ----------------------------
-- Table structure for Dept
-- ----------------------------
DROP TABLE IF EXISTS `Dept`;
CREATE TABLE `Dept`  (
  `deptId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `deptName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `deptParentId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0',
  `remark` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '说明',
  `isUse` tinyint(1) NULL DEFAULT 1 COMMENT '0删除',
  PRIMARY KEY (`deptId`) USING BTREE,
  UNIQUE INDEX `UK_Role_RoleName_CompanyId`(`deptName`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '客服部门' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of Dept
-- ----------------------------
INSERT INTO `Dept` VALUES ('1', 'XX中心', '0', NULL, 1);
INSERT INTO `Dept` VALUES ('13', 'XX中心团队', '1', NULL, 1);
INSERT INTO `Dept` VALUES ('12', 'XX外网团队', '1', NULL, 1);

-- ----------------------------
-- Table structure for EmailRecord
-- ----------------------------
DROP TABLE IF EXISTS `EmailRecord`;
CREATE TABLE `EmailRecord`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `from` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发件人',
  `to` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件人',
  `sub` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `chaosong` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '抄送',
  `fujianPath` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件地址，隔开',
  `fujianName` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件名称，隔开',
  `createTime` datetime NOT NULL COMMENT '发送时间',
  `type` tinyint(2) NULL DEFAULT NULL COMMENT '发送类型1发送给客户2发送给系统人员',
  `isUse` tinyint(2) NOT NULL DEFAULT 1 COMMENT '0删除',
  `memberId` int(11) NULL DEFAULT NULL COMMENT '客户编号',
  `workerId` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '员工编号',
  `status` int(2) NULL DEFAULT NULL COMMENT '发送状态1成功2失败3待发送',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '邮件记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of EmailRecord
-- ----------------------------
INSERT INTO `EmailRecord` VALUES (2, '123test@23.cn', NULL, '测试发送邮件', '<html>\r\n<body>\r\n<p>你好：<span>zs</span></p>\r\n<p><span>暂时没有内容没有大事发生的方式都会发生大佛is大回复哈搜if哈收到货按时发送到发送到发的说法是哒</span></p>\r\n</body>\r\n</html>', NULL, NULL, NULL, '2019-01-03 11:19:50', 2, 0, NULL, '8888', 2);
-- ----------------------------
-- Table structure for member_mid
-- ----------------------------
DROP TABLE IF EXISTS `member_mid`;
CREATE TABLE `member_mid`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '姓名',
  `memberNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号唯一',
  `organId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构ID',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `lineNum` int(10) NULL DEFAULT NULL COMMENT '分机号码',
  `isUse` tinyint(1) NULL DEFAULT 1 COMMENT '删除状态0删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `memberNo_un`(`memberNo`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of member_mid
-- ----------------------------
INSERT INTO `member_mid` VALUES (1, 'zzz', '123231233', '11', 'cc', 22, 0);

-- ----------------------------
-- Table structure for MemberBaseInfo
-- ----------------------------
DROP TABLE IF EXISTS `MemberBaseInfo`;
CREATE TABLE `MemberBaseInfo`  (
  `memberId` int(11) NOT NULL AUTO_INCREMENT COMMENT '客户id',
  `memberName` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `mobile` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `cardNo` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件号',
  `createTime` datetime  NULL DEFAULT NULL COMMENT '修改时间',
  `workerId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '创建人',
  `mdeptName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '所属部门',
  `memberType` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '自定义客户' COMMENT '客户类型 系统客户 自定义客户',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地区',
  `source` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '渠道来源',
  `promotionSite` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '推广点',
  `wechatId` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信号',
  `isCall` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否拨打',
  `field4` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户编号 上传文件id',
  `field5` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱mail',
  `field6` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户类型 0:机构 1:个人',
  `field7` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绑卡状态 C:未绑卡 N:绑卡',
  `field8` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机验证状态 C:未验证 N:已验证',
  `field9` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易密码',
  `field10` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户注册时间',
  `field11` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '累计错误次数',
  `field12` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当天错误次数',
  `field13` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '未定义',
  `field14` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后一次错误输入交易密码时间',
  `field15` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分机号',
  `field16` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录时间',
  `field17` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录次数',
  `field18` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `field19` datetime NULL DEFAULT NULL COMMENT '出生日期',
  `field20` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'office',
  `field21` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓(英文/拼音) 姓名首字母',
  `field22` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名(英文/拼音)',
  `field23` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住址home',
  `field24` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'fangjian',
  `field25` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出生地(英文/拼音)',
  `field26` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '纳税人居民国',
  `field27` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'fax',
  `field28` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户分类 A1 A2 B1 B2',
  `field29` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否留存 0否 1是',
  `field30` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'QQ',
  `field31` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户状态 1已注册 2已绑卡 3已评测 4已交易',
  `field32` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '持仓状态 1空仓 2首投 3复投',
  `field33` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '画像总结',
  `field34` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关注薛掌柜公众号的openId',
  `field35` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '0非高端 1高端',
  `field36` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最大持仓金额',
  `field37` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'beizhu',
  `field38` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备案人',
  `field39` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备案信息',
  `field40` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '总持仓市值',
  `field41` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '总盈亏',
  `nickName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `housePhone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'number',
  `companyName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司',
  `industry` int(10) NULL DEFAULT NULL COMMENT '行业',
  `position` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职位',
  `webSite` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网址',
  `detailedAddress` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `isUse` tinyint(1) NULL DEFAULT 1 COMMENT '0删除',
  PRIMARY KEY (`memberId`) USING BTREE,
  INDEX `IX_MemberBaseInfo_MemberName`(`memberName`) USING BTREE,
  INDEX `IX_MemberBaseInfo_CreateTime`(`createTime`) USING BTREE,
  INDEX `IX_MemberBaseInfo_Mobile`(`mobile`) USING BTREE,
  INDEX `IX_MemberBaseInfo_MemberId`(`memberId`) USING BTREE,
  INDEX `IX_MemberBaseInfo_Address`(`address`) USING BTREE,
  INDEX `IX_MemberBaseInfo_CompanyId`(`mdeptName`) USING BTREE,
  INDEX `IX_MemberBaseInfo_memberType`(`memberType`) USING BTREE,
  INDEX `IX_MemberBaseInfo_filed21`(`field21`) USING BTREE,
  UNIQUE INDEX `UX_MemberBaseInfo_MemberNo`(`field4`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 633 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '客户基础信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of MemberBaseInfo
-- ----------------------------
INSERT INTO `MemberBaseInfo` VALUES (1, '王小毛', '13912341234', NULL, '2019-01-03 17:28:44', '0', 'cb93c64de20d40f88ddfaa834b0821be', '自定义客户', NULL, NULL, NULL, NULL, '0', NULL, 'wantxm@nic.cn', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '8111', 'WXM', NULL, NULL, 'A111', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `MemberBaseInfo` VALUES (2, '杨立昂', '13512341234', NULL, '2019-01-03 17:28:44', '0', 'cb93c64de20d40f88ddfaa834b0821be', '自定义客户', NULL, NULL, NULL, NULL, '0', NULL, 'yang8102@sic.gov.cn', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '8102', 'YLA', NULL, NULL, 'A111', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1);
 
-- ----------------------------
-- Table structure for MemberDept
-- ----------------------------
DROP TABLE IF EXISTS `MemberDept`;
CREATE TABLE `MemberDept`  (
  `deptId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `deptName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `deptParentId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0',
  `remark` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '说明',
  `isUse` tinyint(1) NULL DEFAULT 1 COMMENT '0删除',
  PRIMARY KEY (`deptId`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '客服部门' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of MemberDept
-- ----------------------------
INSERT INTO `MemberDept` VALUES ('cb93c64de20d40f88ddfaa834b0821be', '办公室', '0', NULL, 1);
INSERT INTO `MemberDept` VALUES ('96af7147b71d417db9cf86fd7d9e7139', '保卫保密处', 'cb93c64de20d40f88ddfaa834b0821be', NULL, 1);


-- ----------------------------
-- Table structure for PhoneTag
-- ----------------------------
DROP TABLE IF EXISTS `PhoneTag`;
CREATE TABLE `PhoneTag`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tagValue` int(4) NULL DEFAULT 0,
  `remark` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '说明',
  `isUse` tinyint(1) NULL DEFAULT 1 COMMENT '0删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `phonet_phone`(`phone`) USING BTREE,
  UNIQUE INDEX `phonet_p_t`(`phone`, `tagValue`, `isUse`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '客服部门' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for PushLog
-- ----------------------------
DROP TABLE IF EXISTS `PushLog`;
CREATE TABLE `PushLog`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `createTime` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `logtext` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '日志',
  `type` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接收/发送',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 477 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '推送日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of PushLog
-- ----------------------------
INSERT INTO `PushLog` VALUES (1, '2018-11-27 11:10:09', 'WorkOrderPush [id=qwe123, status=发送中, process=成功, time=1542613515]', '接收');
INSERT INTO `PushLog` VALUES (2, '2018-11-27 11:11:32', 'WorkOrderPush [id=qw123, status=发送中, process=成功, time=1542613515]', '接收');
INSERT INTO `PushLog` VALUES (9, '2018-12-14 14:16:20', 'begin push callrecord. client ip:127.0.0.1, data:{\"aendtime\": \"2018-12-14 14:16:19\", \"disNumber\": \"8000\", \"recordFilename\": \"20181214141614_8000_8590.mp3\", \"aanswerTime\": \"2018-12-14 14:16:14\", \"duration\": 5, \"destNumber\": \"8590\", \"answerTime\": \"\", \"hangupCause\": 10020, \"id\": 568, \"customuuid\": \"0\", \"chengshudu\": \"\", \"callmethod\": 0, \"astarttime\": \"1970-01-01 08:00:00\", \"companycode\": 10000000, \"memberid\": \"\", \"downloadIp\": \"59.252.31.66\", \"startTime\": \"2018-12-14 14:16:14\", \"type\": \"callin\", \"hangupDirection\": 10031, \"crmid\": \"\", \"billsec\": 0, \"endTime\": \"2018-12-14 14:16:19\", \"extNumber\": \"7003\"} jsonparm:lka8ngBkQnExxs8GPvAIhW2/d1f0rvqcZ/H/GG83pjdZfAFaRcmKMdu/0DWuZ2a/LZ6wBnpBcLBVjriTutMq4/36vezFYtXk5eCb5soTw2pMUbhTI2HGCd/AcU1/s2yY/obNejsyy1fIRokQAqvQ0VBYEZP0GB4zQN2gVpHpmbjYmUZ4cKvUfQHKVVX6Rs6Mu0wZCI3Yu/yrJW9dGZjNfWc/wNxJpA1qVLqDL4OAWEqcLusqIue4CW1Rk7Ue3ToVC5x+RqimfFRngeZzgat1JDhOvENd/wg5+G3yWKoiepzqsqvvSLGY3n6uAMuD8PYqC77yCJ7hRA2N0eB++Qc0tJ0T6t3q+5hM6GjOmxutczq80b7287denjOn6ANtpxitX6rLVy3JTquRjWwp+6pPsy+Z9pz3upatLgJI9XcGQpWlEbc9BKLv24bc0AGwjKA+HXcFueUGGpJOQNDf0FQgoOYFt+D2lq090y+eAyArIqROqVliJrk/HYD4BzT5SzXSJGZ29XEY4b69DY+lv+oZ+WYK9c/gO4HfqgXif6a+pXYpl09uOkv3UDZQfoOcJ9AMN7Aca2gqSzhHXCEm4E/dnAYUOxQfAiLrKeEB8uqx55/yP+NaK51r+I02ADuilKifRjPUeW1nllJ3Q5FLujR+fXNQz0vF0tIrcgkSd6IwhJClHID53c3EBTEQAN+nq724cJqfNPO0XV8Z5fvmPOoI/iBM1mlkIg5UfKCFOgjLXmI=', '接收');
INSERT INTO `PushLog` VALUES (10, '2018-12-14 14:17:00', 'begin push callrecord. client ip:127.0.0.1, data:{\"aendtime\": \"2018-12-14 14:16:59\", \"disNumber\": \"7353\", \"recordFilename\": \"20181214141646_7353_7005.mp3\", \"aanswerTime\": \"2018-12-14 14:16:59\", \"duration\": 0, \"destNumber\": \"7005\", \"answerTime\": \"2018-12-14 14:16:59\", \"hangupCause\": 10002, \"id\": 569, \"customuuid\": \"\", \"chengshudu\": \"\", \"callmethod\": 1, \"astarttime\": \"2018-12-14 14:16:46\", \"companycode\": 10000000, \"memberid\": \"\", \"downloadIp\": \"59.252.31.67\", \"startTime\": \"2018-12-14 14:16:46\", \"type\": \"callin\", \"hangupDirection\": 10040, \"crmid\": \"\", \"billsec\": 0, \"endTime\": \"2018-12-14 14:16:59\", \"extNumber\": \"7005\"} jsonparm:lka8ngBkQnExxs8GPvAIhQRiaPMD6bwKgvgd/B6gBu93bfldTP2mrlY/P3S9I6i+l7GT6btf4tcbxmNyyOEyi3g9UvbAkPAzMHItyTfynjDOToRttdcUwLburAp9WuU1gQKsvoLQb/HRbQUOq1IBCLwSUjprUsNeZKSpFcNHzpgzyLuIahn767AtThlvbtJq+Kd71eR9ThywYq3Oux0jBEWaFSdl0STlvN59DI0xgmF86pLgirXA8uayInOD6ElUt8fCPhUNepe2uivk+cxvHgaev2iOatxcCqFZBqWXgJOJOlvUx1WG45/oXT9tV4x0zst9htnHXP/orP5naHsDvpcsOKb1WLbxzawS8Mk5PFGREnF7p7fxcnGnjCQ92OtmO+ryzlKsg76dN98LGKHz5eO6+M9qQ+kVstR0Zj+DMOa0pOUpmpQHPaCP+Noe1xN7qVZyRW9W/SncrrQv+6LP4G9NbiXPV19isGxD6k5GFdbgd4bw9OEFbRXP/nezDNaFKFG4IdcMAqIF6NnOxgUPqJyjLdkGpzU9zzZSeR4MjVO3XEWE3hzJ3FOo7juTT0pCekArVBHuAe6DJKE1oZETqEI0cEC+77kqV73a2ZqCpE2heBJgfqRF1T69zFnfZkkqzFu5KnrLxaq9UGj4Eu/89avUlZBUARzdSalYVJJkbLSam1FkWs/aWwYEZAVYKOPzJ+r2sNv4iIergcBgiwUTL4Xy7qAX5bQrHTkN4r+ZZu5rIYSnlsA9I5TSyE445et7', '接收');

-- ----------------------------
-- Table structure for Role
-- ----------------------------
DROP TABLE IF EXISTS `Role`;
CREATE TABLE `Role`  (
  `roleId` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `orderNo` int(11) NOT NULL DEFAULT 0,
  `remark` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色说明',
  PRIMARY KEY (`roleId`) USING BTREE,
  UNIQUE INDEX `UK_Role_RoleName_CompanyId`(`roleName`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of Role
-- ----------------------------
INSERT INTO `Role` VALUES (1, '管理员', 1, NULL);
INSERT INTO `Role` VALUES (2, 'IT', 2, NULL);
INSERT INTO `Role` VALUES (3, '线务', 3, NULL);
INSERT INTO `Role` VALUES (4, '机务', 4, NULL);
INSERT INTO `Role` VALUES (5, '基础', 5, NULL);

-- ----------------------------
-- Table structure for Seq
-- ----------------------------
DROP TABLE IF EXISTS `Seq`;
CREATE TABLE `Seq`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `workseq` int(11) NOT NULL DEFAULT 100 COMMENT 'worker序列',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'WorkId序列表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of Seq
-- ----------------------------
INSERT INTO `Seq` VALUES (8, 127);

-- ----------------------------
-- Table structure for SmsRecord
-- ----------------------------
DROP TABLE IF EXISTS `SmsRecord`;
CREATE TABLE `SmsRecord`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收信手机号码',
  `content` varchar(1500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容',
  `status` int(2) NULL DEFAULT NULL COMMENT '发送状态1成功2失败3待发送',
  `type` tinyint(2) NULL DEFAULT NULL COMMENT '发送类型1发送给客户2发送给系统人员',
  `createTime` datetime NOT NULL COMMENT '发送时间',
  `isUse` tinyint(2) NOT NULL DEFAULT 1 COMMENT '0删除',
  `memberId` int(11) NULL DEFAULT NULL COMMENT '客户编号',
  `workerId` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '员工编号',
  `backmsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '返回消息',
  `smsId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '短息记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of SmsRecord
-- ----------------------------
INSERT INTO `SmsRecord` VALUES (1, NULL, '短信通知工单派发', 2, 2, '2019-01-04 16:40:15', 1, NULL, '8888', '号码为空', '201901041640154556327');
INSERT INTO `SmsRecord` VALUES (2, NULL, '短信通知工单派发', 2, 2, '2019-01-04 17:30:00', 1, NULL, '8888', '号码为空', '201901041729595195781');
INSERT INTO `SmsRecord` VALUES (3, NULL, '短信通知工单派发', 2, 2, '2019-01-04 17:38:28', 1, NULL, '8888', '号码为空', '201901041738279615870');
INSERT INTO `SmsRecord` VALUES (4, NULL, '短信通知工单派发', 2, 2, '2019-01-04 17:44:49', 1, NULL, '8888', '号码为空', '201901041744492766348');


-- ----------------------------
-- Table structure for SysDict
-- ----------------------------
DROP TABLE IF EXISTS `SysDict`;
CREATE TABLE `SysDict`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `value` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据值',
  `label` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签名',
  `type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型',
  `sort` int(3) NOT NULL COMMENT '排序',
  `isUse` tinyint(1) NOT NULL DEFAULT 1 COMMENT '删除标记0删除',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `parent` int(11) NOT NULL DEFAULT 0,
  `isedit` tinyint(1) NULL DEFAULT 0 COMMENT '是否能编辑1否',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_dict_vt`(`value`, `type`) USING BTREE,
  INDEX `sys_dict_value`(`value`) USING BTREE,
  INDEX `sys_dict_label`(`label`) USING BTREE,
  INDEX `sys_dict_del_flag`(`isUse`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 385 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统字典' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of SysDict
-- ----------------------------
INSERT INTO `SysDict` VALUES (1, '0', '工单类型', 'wotype', 1, 1, NULL, 0, 1);
INSERT INTO `SysDict` VALUES (2, '0', '业务类型', 'wobusiness', 1, 1, NULL, 0, 1);
INSERT INTO `SysDict` VALUES (3, '0', '客户部门', 'wodept', 1, 0, NULL, 0, 0);
INSERT INTO `SysDict` VALUES (4, '0', '话机类型', 'wohj', 1, 1, NULL, 0, 0);
INSERT INTO `SysDict` VALUES (5, '0', '电话权限', 'wophone', 1, 1, NULL, 0, 0);
INSERT INTO `SysDict` VALUES (6, '0', '电话业务', 'wophonebussiness', 1, 1, NULL, 0, 0);
INSERT INTO `SysDict` VALUES (7, '0', '工单状态', 'wostatus', 1, 1, NULL, 0, 1);
INSERT INTO `SysDict` VALUES (8, '0', '设备型号', 'womodel', 1, 1, NULL, 0, 0);
INSERT INTO `SysDict` VALUES (9, '0', '故障初步定位', 'wofault', 1, 1, NULL, 0, 0);
INSERT INTO `SysDict` VALUES (10, '0', '所属类别', 'woclass', 1, 1, NULL, 0, 0);
INSERT INTO `SysDict` VALUES (38, '1', '等待机务', 'wostatus', 2, 1, NULL, 7, 1);
INSERT INTO `SysDict` VALUES (39, '2', '机务完成', 'wostatus', 3, 1, NULL, 7, 1);
INSERT INTO `SysDict` VALUES (40, '3', '线务完成', 'wostatus', 4, 1, NULL, 7, 1);
INSERT INTO `SysDict` VALUES (41, '4', '测试', 'wotype', 0, 0, NULL, 1, 0);
INSERT INTO `SysDict` VALUES (42, '4', '123', 'wophone', 0, 0, NULL, 5, 0);
INSERT INTO `SysDict` VALUES (43, '79', '阿凡达', 'wophone', 0, 0, NULL, 5, 0);
INSERT INTO `SysDict` VALUES (44, '2', '测试部', 'wodept', 1, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (46, '65', '电话工单', 'wotype', 0, 1, NULL, 1, 1);
INSERT INTO `SysDict` VALUES (47, '37', 'IT工单', 'wotype', 0, 1, NULL, 1, 1);
INSERT INTO `SysDict` VALUES (48, '97', '数字话机4039', 'wohj', 0, 1, NULL, 4, 0);
INSERT INTO `SysDict` VALUES (49, '60', '数字话机4029', 'wohj', 0, 1, NULL, 4, 0);
INSERT INTO `SysDict` VALUES (50, '61', '国际长途', 'wophone', 0, 1, NULL, 5, 0);
INSERT INTO `SysDict` VALUES (51, '29', '国内长途', 'wophone', 0, 1, NULL, 5, 0);
INSERT INTO `SysDict` VALUES (52, '85', '市话', 'wophone', 0, 0, NULL, 5, 0);
INSERT INTO `SysDict` VALUES (53, '35', '来电显示', 'wophonebussiness', 0, 1, NULL, 6, 0);
INSERT INTO `SysDict` VALUES (54, '53', '电话转接', 'wophonebussiness', 0, 1, NULL, 6, 0);
INSERT INTO `SysDict` VALUES (55, '19', '呼出限制', 'wophonebussiness', 0, 1, NULL, 6, 0);
INSERT INTO `SysDict` VALUES (56, '93', '装机', 'wobusiness', 0, 1, NULL, 2, 1);
INSERT INTO `SysDict` VALUES (57, '53', '移机', 'wobusiness', 0, 1, NULL, 2, 1);
INSERT INTO `SysDict` VALUES (58, '87', '电话故障', 'wobusiness', 0, 1, NULL, 2, 1);
INSERT INTO `SysDict` VALUES (59, '33', '撤机', 'wobusiness', 0, 1, NULL, 2, 1);
INSERT INTO `SysDict` VALUES (60, '9', '信息中心', 'woclass', 0, 1, NULL, 10, 0);
INSERT INTO `SysDict` VALUES (61, '54', '信息大厦', 'woclass', 0, 1, NULL, 10, 0);
INSERT INTO `SysDict` VALUES (62, '42', '其他单位', 'woclass', 0, 1, NULL, 10, 0);
INSERT INTO `SysDict` VALUES (63, '67', '台式机', 'womodel', 0, 1, NULL, 8, 0);
INSERT INTO `SysDict` VALUES (64, '35', '网络不通', 'wofault', 0, 1, NULL, 9, 0);
INSERT INTO `SysDict` VALUES (65, '7', '机修', 'wobusiness', 0, 0, NULL, 2, 1);
INSERT INTO `SysDict` VALUES (66, '8', '咨询', 'wobusiness', 0, 0, NULL, 2, 0);
INSERT INTO `SysDict` VALUES (67, '51', '没修的必要', 'wofault', 0, 0, NULL, 9, 0);
INSERT INTO `SysDict` VALUES (68, '26', '笔记本', 'womodel', 0, 1, NULL, 8, 0);
INSERT INTO `SysDict` VALUES (69, '5', '废弃', 'wostatus', 5, 1, NULL, 7, 1);
INSERT INTO `SysDict` VALUES (70, '99', '技术部', 'wodept', 0, 0, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (71, '22', '王大壮', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (72, '84', '测试是是是', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (73, '4137', '测试是是是ds', 'wodept', 15, 0, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (74, '9779', 'ssss', 'wodept', 15, 0, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (75, '4761', 'kjkjkjkjkj', 'wodept', 15, 0, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (76, '9549', '999部门', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (77, '2812', '666部门', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (78, '4797', '测试部门三', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (79, '6257', '科技大厦', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (121, '9929', '公共部政务外网政务云管理处（政务云XX工程实验室）', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (122, '2340', '公共部政务外网公共应用推进处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (123, '0', 'IT业务类型', 'woitbus', 1, 1, NULL, 0, 0);
INSERT INTO `SysDict` VALUES (124, '0', 'IT工单状态', 'woitstatus', 1, 1, NULL, 0, 0);
INSERT INTO `SysDict` VALUES (125, '28', '处理中', 'woitstatus', 0, 1, NULL, 124, 0);
INSERT INTO `SysDict` VALUES (126, '4', '处理结束', 'woitstatus', 0, 1, NULL, 124, 0);
INSERT INTO `SysDict` VALUES (127, '85', '转移给外部系统', 'woitstatus', 0, 1, NULL, 124, 0);
INSERT INTO `SysDict` VALUES (128, '42', '机修', 'woitbus', 0, 1, NULL, 123, 0);
INSERT INTO `SysDict` VALUES (129, '6', '网络维护', 'woitbus', 0, 1, NULL, 123, 0);
INSERT INTO `SysDict` VALUES (130, '8', '服务完成', 'wostatus', 5, 1, NULL, 7, 1);
INSERT INTO `SysDict` VALUES (381, '1', '电话标签', 'phonetag', 1, 1, NULL, 0, 0);
INSERT INTO `SysDict` VALUES (382, '4445', '中介', 'phonetag', 0, 1, NULL, 381, 0);
INSERT INTO `SysDict` VALUES (383, '7055', '广告', 'phonetag', 0, 1, NULL, 381, 0);
INSERT INTO `SysDict` VALUES (384, '43', '变更权限', 'wobusiness', 5, 1, NULL, 2, 1);

-- ----------------------------
-- Table structure for TaskInfo
-- ----------------------------
DROP TABLE IF EXISTS `TaskInfo`;
CREATE TABLE `TaskInfo`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memberno` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `workerid` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `slicno` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tasknode` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tasktime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `currentDate` datetime NULL DEFAULT NULL,
  `taskclass` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务分类',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UX_TaskInfo_Memberno_Tasknode`(`memberno`, `tasknode`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '待生成任务' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for Worker
-- ----------------------------
DROP TABLE IF EXISTS `Worker`;
CREATE TABLE `Worker`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `workerId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '员工ID',
  `workerName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '员工姓名',
  `psw` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '口令',
  `maxAllotTheDay` int(11) NULL DEFAULT NULL COMMENT '坐席等级',
  `isAllot` tinyint(2) NULL DEFAULT 0 COMMENT '是(1)否(0)分配',
  `workerStatus` tinyint(2) NULL DEFAULT 0 COMMENT '在职状态(0在职 1离职（删除） 2请假)',
  `workQueue` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所在队列',
  `createTime` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `updateTime` datetime NULL DEFAULT NULL COMMENT '更新日期',
  `workerOnlineTime` datetime NULL DEFAULT NULL COMMENT '最近登录系统时间',
  `lineNum` int(11) NULL DEFAULT NULL COMMENT '使用的分机号',
  `preset` tinyint(2) NULL DEFAULT 1 COMMENT '0:系统设置，不可删除；1：用户自定义',
  `signStatus` tinyint(1) NULL DEFAULT 0 COMMENT '签出签入状态,0为签出,1为签入',
  `headPic` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '员工头像URL',
  `birthday` date NULL DEFAULT NULL COMMENT '员工生日',
  `sex` tinyint(2) NULL DEFAULT NULL COMMENT '1:男,2:女',
  `online` tinyint(1) NULL DEFAULT 0 COMMENT '0不在线1在线',
  `deptId` int(11) NULL DEFAULT NULL COMMENT '组织架构',
  `no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三方编号',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `globalId` int(11) NULL DEFAULT 1 COMMENT '是否显示0不显示',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UX_Worker_CompanyId_WorkerId`(`workerId`) USING BTREE,
  INDEX `IX_Worker_WorkerId`(`workerId`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '员工表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of Worker
-- ----------------------------
INSERT INTO `Worker` VALUES (1, '8888', 'administrator', 'UARSAldQ3949ba59abbe56e057f20f88', NULL, 0, 0, 'test', NULL, '2019-01-04 14:39:40', '2019-01-07 10:42:16', 7001, 1, 0, NULL, NULL, NULL, 0, 13, 'administrator', NULL, 1, '934907236@qq.com');

-- ----------------------------
-- Table structure for WorkerOfRole
-- ----------------------------
DROP TABLE IF EXISTS `WorkerOfRole`;
CREATE TABLE `WorkerOfRole`  (
  `autoId` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` int(11) NOT NULL,
  `workerId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`autoId`) USING BTREE,
  UNIQUE INDEX `roleId`(`roleId`, `workerId`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 279 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '人员角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of WorkerOfRole
-- ----------------------------
INSERT INTO `WorkerOfRole` VALUES (278, 5, '8888');
INSERT INTO `WorkerOfRole` VALUES (8, 2, '101');
INSERT INTO `WorkerOfRole` VALUES (7, 1, '101');
INSERT INTO `WorkerOfRole` VALUES (9, 5, '101');
INSERT INTO `WorkerOfRole` VALUES (10, 1, '102');
INSERT INTO `WorkerOfRole` VALUES (11, 5, '102');
INSERT INTO `WorkerOfRole` VALUES (12, 2, '104');
INSERT INTO `WorkerOfRole` VALUES (13, 5, '104');
INSERT INTO `WorkerOfRole` VALUES (14, 0, '105');
INSERT INTO `WorkerOfRole` VALUES (15, 5, '105');
INSERT INTO `WorkerOfRole` VALUES (16, 0, '106');
INSERT INTO `WorkerOfRole` VALUES (17, 5, '106');
INSERT INTO `WorkerOfRole` VALUES (18, 0, '107');
INSERT INTO `WorkerOfRole` VALUES (19, 5, '107');
INSERT INTO `WorkerOfRole` VALUES (28, 3, '111');
INSERT INTO `WorkerOfRole` VALUES (21, 0, '109');
INSERT INTO `WorkerOfRole` VALUES (22, 5, '109');
INSERT INTO `WorkerOfRole` VALUES (23, 2, '110');
INSERT INTO `WorkerOfRole` VALUES (24, 5, '110');
INSERT INTO `WorkerOfRole` VALUES (273, 5, '127');
INSERT INTO `WorkerOfRole` VALUES (29, 4, '111');
INSERT INTO `WorkerOfRole` VALUES (30, 5, '111');
INSERT INTO `WorkerOfRole` VALUES (277, 4, '8888');
INSERT INTO `WorkerOfRole` VALUES (272, 5, '112');
INSERT INTO `WorkerOfRole` VALUES (268, 5, '1001');
INSERT INTO `WorkerOfRole` VALUES (87, 5, '113');
INSERT INTO `WorkerOfRole` VALUES (86, 4, '113');
INSERT INTO `WorkerOfRole` VALUES (276, 3, '8888');
INSERT INTO `WorkerOfRole` VALUES (275, 2, '8888');
INSERT INTO `WorkerOfRole` VALUES (88, 3, '113');
INSERT INTO `WorkerOfRole` VALUES (274, 1, '8888');
INSERT INTO `WorkerOfRole` VALUES (223, 5, '122');
INSERT INTO `WorkerOfRole` VALUES (222, 4, '122');
INSERT INTO `WorkerOfRole` VALUES (221, 3, '122');
INSERT INTO `WorkerOfRole` VALUES (220, 2, '122');
INSERT INTO `WorkerOfRole` VALUES (267, 4, '1001');
INSERT INTO `WorkerOfRole` VALUES (271, 4, '112');
INSERT INTO `WorkerOfRole` VALUES (270, 3, '112');
INSERT INTO `WorkerOfRole` VALUES (269, 2, '112');
INSERT INTO `WorkerOfRole` VALUES (219, 1, '122');
INSERT INTO `WorkerOfRole` VALUES (266, 3, '1001');
INSERT INTO `WorkerOfRole` VALUES (265, 2, '1001');
INSERT INTO `WorkerOfRole` VALUES (249, 5, '8000');


