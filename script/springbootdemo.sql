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
INSERT INTO `Dept` VALUES ('1', 'XX信息中心', '0', NULL, 1);
INSERT INTO `Dept` VALUES ('13', 'XX信息中心团队', '1', NULL, 1);
INSERT INTO `Dept` VALUES ('12', 'XX电子政务外网团队', '1', NULL, 1);

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
INSERT INTO `EmailRecord` VALUES (2, 'sicadmin@sic.gov.cn', NULL, '测试发送邮件', '<html>\r\n<body>\r\n<p>你好：<span>边维阳</span></p>\r\n<p><span>暂时没有内容没有大事发生的方式都会发生大佛is大回复哈搜if哈收到货按时发送到发送到发的说法是哒</span></p>\r\n</body>\r\n</html>', NULL, NULL, NULL, '2019-01-03 11:19:50', 2, 0, NULL, '8888', 2);
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
INSERT INTO `MemberBaseInfo` VALUES (1, '王小毛', '13910860672', NULL, '2019-01-03 17:28:44', '0', '714ab6fae79245d9a3d1593cebad485c', '自定义客户', NULL, NULL, NULL, NULL, '0', NULL, 'wantxm@nic.cn', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '8111', 'WXM', NULL, NULL, 'A111', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `MemberBaseInfo` VALUES (2, '杨立昂', '13501311909', NULL, '2019-01-03 17:28:44', '0', '60cb52e9d4804bed810a15e81c09712b', '自定义客户', NULL, NULL, NULL, NULL, '0', NULL, 'yang8102@sic.gov.cn', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '8102', 'YLA', NULL, NULL, 'A111', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1);
 
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
INSERT INTO `MemberDept` VALUES ('60cb52e9d4804bed810a15e81c09712b', '--', '96af7147b71d417db9cf86fd7d9e7139', NULL, 1);
INSERT INTO `MemberDept` VALUES ('714ab6fae79245d9a3d1593cebad485c', '处长', '96af7147b71d417db9cf86fd7d9e7139', NULL, 1);
INSERT INTO `MemberDept` VALUES ('aaa3c668ec384f8c8edbb230c62db350', '副处长', '96af7147b71d417db9cf86fd7d9e7139', NULL, 1);
INSERT INTO `MemberDept` VALUES ('a08e6ae064f04dbb8442c2cab698c54a', '博士后工作站', 'cb93c64de20d40f88ddfaa834b0821be', NULL, 1);
INSERT INTO `MemberDept` VALUES ('8f612b816200493fb1848d247f425c71', '--', 'a08e6ae064f04dbb8442c2cab698c54a', NULL, 1);
INSERT INTO `MemberDept` VALUES ('c7ff9a782c32434d8a48c41ffd3e8e7d', '副处长', 'a08e6ae064f04dbb8442c2cab698c54a', NULL, 1);
INSERT INTO `MemberDept` VALUES ('27e1e1f270ea43fd86887385c9fd3dc2', '财务处', 'cb93c64de20d40f88ddfaa834b0821be', NULL, 1);
INSERT INTO `MemberDept` VALUES ('1067226f523e4fd68be9b6ea988dde56', '--', '27e1e1f270ea43fd86887385c9fd3dc2', NULL, 1);
INSERT INTO `MemberDept` VALUES ('9f068548c9f84618a387aa0862fce247', '处长', '27e1e1f270ea43fd86887385c9fd3dc2', NULL, 1);
INSERT INTO `MemberDept` VALUES ('1fb0f75d72784ad5843681ad3db605b4', '副处长', '27e1e1f270ea43fd86887385c9fd3dc2', NULL, 1);
INSERT INTO `MemberDept` VALUES ('da997cee61c64494871235e3c7740e58', '行政处', 'cb93c64de20d40f88ddfaa834b0821be', NULL, 1);
INSERT INTO `MemberDept` VALUES ('b33efdaa2d604f6182b5243c8339b61e', '--', 'da997cee61c64494871235e3c7740e58', NULL, 1);
INSERT INTO `MemberDept` VALUES ('4a850a2eee764716b83cf880343e6f5e', '处长', 'da997cee61c64494871235e3c7740e58', NULL, 1);
INSERT INTO `MemberDept` VALUES ('8a69c16d4fe9430d97b1ff7937569f8c', '副处长', 'da997cee61c64494871235e3c7740e58', NULL, 1);
INSERT INTO `MemberDept` VALUES ('6d394cf4f7464db4b9e76f4a6a7bf868', '基建办公室', 'cb93c64de20d40f88ddfaa834b0821be', NULL, 1);
INSERT INTO `MemberDept` VALUES ('ab7aafb0f8c243c391dda1240838602d', '--', '6d394cf4f7464db4b9e76f4a6a7bf868', NULL, 1);
INSERT INTO `MemberDept` VALUES ('9bb6d245a1c94ae1b8298d913f9ceb0c', '处长', '6d394cf4f7464db4b9e76f4a6a7bf868', NULL, 1);
INSERT INTO `MemberDept` VALUES ('e711955e4884402e9a3fe5f33337019b', '副处长', '6d394cf4f7464db4b9e76f4a6a7bf868', NULL, 1);
INSERT INTO `MemberDept` VALUES ('1fefb6701d8243c4a613f3674d007d68', '离退休干部处', 'cb93c64de20d40f88ddfaa834b0821be', NULL, 1);
INSERT INTO `MemberDept` VALUES ('3c310dff61f3412da93de37387372d8c', '--', '1fefb6701d8243c4a613f3674d007d68', NULL, 1);
INSERT INTO `MemberDept` VALUES ('24f091eb89024579a8b47c374802ed5b', '处长', '1fefb6701d8243c4a613f3674d007d68', NULL, 1);
INSERT INTO `MemberDept` VALUES ('5b242ea92b7e4bc3a1f36e72d118dd38', '秘书处', 'cb93c64de20d40f88ddfaa834b0821be', NULL, 1);
INSERT INTO `MemberDept` VALUES ('72293b3d00f34e788394d2df3de43076', '--', '5b242ea92b7e4bc3a1f36e72d118dd38', NULL, 1);
INSERT INTO `MemberDept` VALUES ('025f8ceb19a146359ac29f2f5a612142', '副处长', '5b242ea92b7e4bc3a1f36e72d118dd38', NULL, 1);
INSERT INTO `MemberDept` VALUES ('464734748c5a485eb22b629cecfdb116', '处长', '5b242ea92b7e4bc3a1f36e72d118dd38', NULL, 1);
INSERT INTO `MemberDept` VALUES ('e1050b4b5e9941ff9008246f27dd6327', '人事教育处', 'cb93c64de20d40f88ddfaa834b0821be', NULL, 1);
INSERT INTO `MemberDept` VALUES ('bb353c067cb34d2fb9da128538c984d7', '--', 'e1050b4b5e9941ff9008246f27dd6327', NULL, 1);
INSERT INTO `MemberDept` VALUES ('250ec1afa7d542cc906812289619d4b6', '副处长', 'e1050b4b5e9941ff9008246f27dd6327', NULL, 1);
INSERT INTO `MemberDept` VALUES ('7706f0fd16964476b67cdc7cf9d68160', '处长', 'e1050b4b5e9941ff9008246f27dd6327', NULL, 1);
INSERT INTO `MemberDept` VALUES ('568f086175d94394b3910ed9956c8c00', '设备管理处', 'cb93c64de20d40f88ddfaa834b0821be', NULL, 1);
INSERT INTO `MemberDept` VALUES ('0da794f4a5024dd8b806de5b061b0523', '--', '568f086175d94394b3910ed9956c8c00', NULL, 1);
INSERT INTO `MemberDept` VALUES ('27728755b6cb4db7a2c6651202ea2042', '处长', '568f086175d94394b3910ed9956c8c00', NULL, 1);
INSERT INTO `MemberDept` VALUES ('f0a8ba1756534807884cd0f480f911e4', '办公室', 'cb93c64de20d40f88ddfaa834b0821be', NULL, 1);
INSERT INTO `MemberDept` VALUES ('4965d1ee45304b229dc88559d0629c95', '--', 'f0a8ba1756534807884cd0f480f911e4', NULL, 1);
INSERT INTO `MemberDept` VALUES ('b1fc730378b747b18984a4f21818015a', '主任', 'f0a8ba1756534807884cd0f480f911e4', NULL, 1);
INSERT INTO `MemberDept` VALUES ('ab2ac3557d0343b49abd31bdc49ddae0', '副主任', 'f0a8ba1756534807884cd0f480f911e4', NULL, 1);
INSERT INTO `MemberDept` VALUES ('cd34ad4155374446ba6ff87ef52343fd', '综合管理部', '0', NULL, 1);
INSERT INTO `MemberDept` VALUES ('cc1eea2a65944276985adb227e0f7fc5', '综合管理部', 'cd34ad4155374446ba6ff87ef52343fd', NULL, 1);
INSERT INTO `MemberDept` VALUES ('77daad484c91461c85335727b9d64ff9', '--', 'cc1eea2a65944276985adb227e0f7fc5', NULL, 1);
INSERT INTO `MemberDept` VALUES ('dda7474499d0464ba51d9035321ac379', '主任', 'cc1eea2a65944276985adb227e0f7fc5', NULL, 1);
INSERT INTO `MemberDept` VALUES ('b09258f44af343bea0d6eed3761dc7fb', '副主任', 'cc1eea2a65944276985adb227e0f7fc5', NULL, 1);
INSERT INTO `MemberDept` VALUES ('0035556614af4a33b3bbf55bf61b38b5', '综合处', 'cd34ad4155374446ba6ff87ef52343fd', NULL, 1);
INSERT INTO `MemberDept` VALUES ('8fa9d11907a048cb9fb3e06ecd896e50', '--', '0035556614af4a33b3bbf55bf61b38b5', NULL, 1);
INSERT INTO `MemberDept` VALUES ('a1c967093aa3478baa11db05d3ee5b3e', '处长', '0035556614af4a33b3bbf55bf61b38b5', NULL, 1);
INSERT INTO `MemberDept` VALUES ('77e80678938b4761ab98c330c52421d3', '副处长', '0035556614af4a33b3bbf55bf61b38b5', NULL, 1);
INSERT INTO `MemberDept` VALUES ('02efcc65ce4c423695a2dc6f17f2ee34', '代秘书', '0035556614af4a33b3bbf55bf61b38b5', NULL, 1);
INSERT INTO `MemberDept` VALUES ('99d307cefbce4bb986da434524ee8d10', '政研室', 'cd34ad4155374446ba6ff87ef52343fd', NULL, 1);
INSERT INTO `MemberDept` VALUES ('d350bc43b89c4e758cd4b665d7818502', '--', '99d307cefbce4bb986da434524ee8d10', NULL, 1);
INSERT INTO `MemberDept` VALUES ('614a99c1929243beaf10435974317e2e', '主任', '99d307cefbce4bb986da434524ee8d10', NULL, 1);
INSERT INTO `MemberDept` VALUES ('9bb61c960e3e4c768d820115ec24cae4', '副主任', '99d307cefbce4bb986da434524ee8d10', NULL, 1);
INSERT INTO `MemberDept` VALUES ('c56364c7608b48cf89f44a2ddea25a11', '预算计划处', 'cd34ad4155374446ba6ff87ef52343fd', NULL, 1);
INSERT INTO `MemberDept` VALUES ('f9dd4dac74b448d38115141e20bac67d', '--', 'c56364c7608b48cf89f44a2ddea25a11', NULL, 1);
INSERT INTO `MemberDept` VALUES ('672a0f507164484eb6a28a1ef8073c9b', '处长', 'c56364c7608b48cf89f44a2ddea25a11', NULL, 1);
INSERT INTO `MemberDept` VALUES ('f5492afffa9d40fab13df7f2f441ce25', '副处长', 'c56364c7608b48cf89f44a2ddea25a11', NULL, 1);
INSERT INTO `MemberDept` VALUES ('02b047afa94649b296014346a0a370cc', '资产及企业管理处', 'cd34ad4155374446ba6ff87ef52343fd', NULL, 1);
INSERT INTO `MemberDept` VALUES ('00dc4c13a8264616a41cc1a45e32d119', '--', '02b047afa94649b296014346a0a370cc', NULL, 1);
INSERT INTO `MemberDept` VALUES ('4f318e4231fc452d9eea9973ccfe4ae6', '处长', '02b047afa94649b296014346a0a370cc', NULL, 1);
INSERT INTO `MemberDept` VALUES ('b265110e633d4bb9a7d5d97133ac6f6d', '副处长', '02b047afa94649b296014346a0a370cc', NULL, 1);
INSERT INTO `MemberDept` VALUES ('13e8067551cc4ed7befe40d852edb0ca', '科研管理处', 'cd34ad4155374446ba6ff87ef52343fd', NULL, 1);
INSERT INTO `MemberDept` VALUES ('430700b41b4b4652b6694f27c6aeb5f9', '--', '13e8067551cc4ed7befe40d852edb0ca', NULL, 1);
INSERT INTO `MemberDept` VALUES ('ac952cfed40048dca8a59ad0e08a339b', '处长', '13e8067551cc4ed7befe40d852edb0ca', NULL, 1);
INSERT INTO `MemberDept` VALUES ('3d7b2d4e28594eada99d5a90d620c5e9', '副处长', '13e8067551cc4ed7befe40d852edb0ca', NULL, 1);
INSERT INTO `MemberDept` VALUES ('ef64f994f54b498895b09639e20c47d2', '国际合作处', 'cd34ad4155374446ba6ff87ef52343fd', NULL, 1);
INSERT INTO `MemberDept` VALUES ('53e3c3349e334bb68fbabe538703ae40', '--', 'ef64f994f54b498895b09639e20c47d2', NULL, 1);
INSERT INTO `MemberDept` VALUES ('cd14c9485c564dd8b27b93eb63f413d1', '处长', 'ef64f994f54b498895b09639e20c47d2', NULL, 1);
INSERT INTO `MemberDept` VALUES ('6840ede334874d048e1c2df3f950c1db', '副处长', 'ef64f994f54b498895b09639e20c47d2', NULL, 1);
INSERT INTO `MemberDept` VALUES ('1c7fda9debb148469ff7833e9f25cc66', '国信新创', 'cd34ad4155374446ba6ff87ef52343fd', NULL, 1);
INSERT INTO `MemberDept` VALUES ('b4a801e4f7674fdd8e5e8ba354541de1', '--', '1c7fda9debb148469ff7833e9f25cc66', NULL, 1);
INSERT INTO `MemberDept` VALUES ('e3b381dd395e487c96664268643ec6fe', '公共技术服务部', '0', NULL, 1);
INSERT INTO `MemberDept` VALUES ('68939f59601d44fdac4f13b38951c2ba', '公共技术服务部', 'e3b381dd395e487c96664268643ec6fe', NULL, 1);
INSERT INTO `MemberDept` VALUES ('1edbf39110044bef9e5d2ea4283da78f', '--', '68939f59601d44fdac4f13b38951c2ba', NULL, 1);
INSERT INTO `MemberDept` VALUES ('a833f8713cdc4597a9c8b7e7922a18f9', '主任', '68939f59601d44fdac4f13b38951c2ba', NULL, 1);
INSERT INTO `MemberDept` VALUES ('e9a540c8539f4eeaa976c2455bee8225', '副主任', '68939f59601d44fdac4f13b38951c2ba', NULL, 1);
INSERT INTO `MemberDept` VALUES ('29d68e22128249dfbc5118553bfbaee9', '综合处', 'e3b381dd395e487c96664268643ec6fe', NULL, 1);
INSERT INTO `MemberDept` VALUES ('43e87659de36453d99c5a46de2abaae4', '--', '29d68e22128249dfbc5118553bfbaee9', NULL, 1);
INSERT INTO `MemberDept` VALUES ('ceabd0c2b5ce41af8af555862b832c82', '处长', '29d68e22128249dfbc5118553bfbaee9', NULL, 1);
INSERT INTO `MemberDept` VALUES ('8480ff2be16b465ca51cebf0430efd67', '秘书', '29d68e22128249dfbc5118553bfbaee9', NULL, 1);
INSERT INTO `MemberDept` VALUES ('eaef1c3a4d524b0aa5619ed0b9935845', '内网运行管理处', 'e3b381dd395e487c96664268643ec6fe', NULL, 1);
INSERT INTO `MemberDept` VALUES ('5703433b388c4918b8fc4ac3b8888394', '--', 'eaef1c3a4d524b0aa5619ed0b9935845', NULL, 1);
INSERT INTO `MemberDept` VALUES ('39ba914c72054919934928e093eaad47', '处长', 'eaef1c3a4d524b0aa5619ed0b9935845', NULL, 1);
INSERT INTO `MemberDept` VALUES ('ecd21e15f35d4cfeb66e8d35bd7b6891', '副处长', 'eaef1c3a4d524b0aa5619ed0b9935845', NULL, 1);
INSERT INTO `MemberDept` VALUES ('111e2ef9d0874d1584711ea29f7ac2e8', '纵网运行管理处', 'e3b381dd395e487c96664268643ec6fe', NULL, 1);
INSERT INTO `MemberDept` VALUES ('e65a338234ff48fa98c20c82ed9548eb', '--', '111e2ef9d0874d1584711ea29f7ac2e8', NULL, 1);
INSERT INTO `MemberDept` VALUES ('32c2333c48254c04a67e02dfef1ed054', '处长', '111e2ef9d0874d1584711ea29f7ac2e8', NULL, 1);
INSERT INTO `MemberDept` VALUES ('ba1a48713ca74f87a6565576375c6762', '平台应用管理处', 'e3b381dd395e487c96664268643ec6fe', NULL, 1);
INSERT INTO `MemberDept` VALUES ('350ac9e3195c41f88e37bfc11cdefa12', '--', 'ba1a48713ca74f87a6565576375c6762', NULL, 1);
INSERT INTO `MemberDept` VALUES ('9c0b2f7d65004ae6802193b735fc9dbe', '处长', 'ba1a48713ca74f87a6565576375c6762', NULL, 1);
INSERT INTO `MemberDept` VALUES ('ec0fed957a99434297af00c3fb6a95c3', '副处长', 'ba1a48713ca74f87a6565576375c6762', NULL, 1);
INSERT INTO `MemberDept` VALUES ('231ee3b63f58437e927b6a067fe4d677', '政务大厅管理处', 'e3b381dd395e487c96664268643ec6fe', NULL, 1);
INSERT INTO `MemberDept` VALUES ('24d72fa1a5ae4129b7d879f21f7509bc', '--', '231ee3b63f58437e927b6a067fe4d677', NULL, 1);
INSERT INTO `MemberDept` VALUES ('0348b404cb7440e29fab12f85f1c9a60', '处长', '231ee3b63f58437e927b6a067fe4d677', NULL, 1);
INSERT INTO `MemberDept` VALUES ('67afb6b61f5c4ee1a23315bc068312d6', '政务大厅服务处', 'e3b381dd395e487c96664268643ec6fe', NULL, 1);
INSERT INTO `MemberDept` VALUES ('1e8ed88adfc447f88b0e449296232fb3', '--', '67afb6b61f5c4ee1a23315bc068312d6', NULL, 1);
INSERT INTO `MemberDept` VALUES ('8c904d7e06cc4f18bbd6b47cfb1134b2', '处长', '67afb6b61f5c4ee1a23315bc068312d6', NULL, 1);
INSERT INTO `MemberDept` VALUES ('451eb6764a8546428755919fefd9154a', '副处长', '67afb6b61f5c4ee1a23315bc068312d6', NULL, 1);
INSERT INTO `MemberDept` VALUES ('93540de1125d4a6789fb5ff87b41145f', '政务外网发展规划处', 'e3b381dd395e487c96664268643ec6fe', NULL, 1);
INSERT INTO `MemberDept` VALUES ('c6570e387e574ea9a56cf932fd39b9ca', '--', '93540de1125d4a6789fb5ff87b41145f', NULL, 1);
INSERT INTO `MemberDept` VALUES ('e215ed1c9684479791f618a0583385e5', '处长', '93540de1125d4a6789fb5ff87b41145f', NULL, 1);
INSERT INTO `MemberDept` VALUES ('e2c29196a281405797e27635915f2c5e', '副处长', '93540de1125d4a6789fb5ff87b41145f', NULL, 1);
INSERT INTO `MemberDept` VALUES ('b05409e92ccd4985bb49870c55f044a2', '政务外网运行管理处', 'e3b381dd395e487c96664268643ec6fe', NULL, 1);
INSERT INTO `MemberDept` VALUES ('b401d1f949064f2caada705893ba9649', '--', 'b05409e92ccd4985bb49870c55f044a2', NULL, 1);
INSERT INTO `MemberDept` VALUES ('5a30d16aa08b4569a8d76c551de79e8a', '处长', 'b05409e92ccd4985bb49870c55f044a2', NULL, 1);
INSERT INTO `MemberDept` VALUES ('ede114a92df44e99817c48de689847f0', '副处长', 'b05409e92ccd4985bb49870c55f044a2', NULL, 1);
INSERT INTO `MemberDept` VALUES ('025d33cbe8ea49c0b93d401565cead87', '政务外网安全管理处', 'e3b381dd395e487c96664268643ec6fe', NULL, 1);
INSERT INTO `MemberDept` VALUES ('d98181877ebb49db8f1f1aafa514b7ef', '--', '025d33cbe8ea49c0b93d401565cead87', NULL, 1);
INSERT INTO `MemberDept` VALUES ('e840625dac3e455ca9fc2a3b66c96fc8', '处长', '025d33cbe8ea49c0b93d401565cead87', NULL, 1);
INSERT INTO `MemberDept` VALUES ('a38f3b94cbc141a5b8fc11c7c3e42420', '政务外网技术管理处', 'e3b381dd395e487c96664268643ec6fe', NULL, 1);
INSERT INTO `MemberDept` VALUES ('013e9b98b7e74c95a0a5849f86fe3caf', '--', 'a38f3b94cbc141a5b8fc11c7c3e42420', NULL, 1);
INSERT INTO `MemberDept` VALUES ('b7894d1e67bb45d3927ccc3dd1d12982', '处长', 'a38f3b94cbc141a5b8fc11c7c3e42420', NULL, 1);
INSERT INTO `MemberDept` VALUES ('6a4cc7424b044d4584bf8cb54ea377a8', '政务外网政务云管理处（政务云XX工程实验室）', 'e3b381dd395e487c96664268643ec6fe', NULL, 1);
INSERT INTO `MemberDept` VALUES ('21fe3843f3164e6fa492f4f573d16f99', '--', '6a4cc7424b044d4584bf8cb54ea377a8', NULL, 1);
INSERT INTO `MemberDept` VALUES ('f6e594c10cc94057857ec85f20dd429d', '处长', '6a4cc7424b044d4584bf8cb54ea377a8', NULL, 1);
INSERT INTO `MemberDept` VALUES ('cf0b50653cb94c4480868cb602611cab', '政务外网公共应用推进处', 'e3b381dd395e487c96664268643ec6fe', NULL, 1);
INSERT INTO `MemberDept` VALUES ('f1b31f2be33b475baede7d845bd53155', '--', 'cf0b50653cb94c4480868cb602611cab', NULL, 1);
INSERT INTO `MemberDept` VALUES ('26fed6bf29304b31836270de90c35407', '处长', 'cf0b50653cb94c4480868cb602611cab', NULL, 1);
INSERT INTO `MemberDept` VALUES ('a11e130bfea7491dae45b6f3b3a105c8', '政务信息资源管理处', 'e3b381dd395e487c96664268643ec6fe', NULL, 1);
INSERT INTO `MemberDept` VALUES ('1a76a62bd47e40ef84c1e1613a90f686', '--', 'a11e130bfea7491dae45b6f3b3a105c8', NULL, 1);
INSERT INTO `MemberDept` VALUES ('95d16a2cec884836a77de41124a9f526', '副处长', 'a11e130bfea7491dae45b6f3b3a105c8', NULL, 1);
INSERT INTO `MemberDept` VALUES ('e4d232a980ce48938f067670095dff0e', '经济预测部', '0', NULL, 1);
INSERT INTO `MemberDept` VALUES ('f9fb33d709f841ca83b65b569fa4fd0a', '经济预测部', 'e4d232a980ce48938f067670095dff0e', NULL, 1);
INSERT INTO `MemberDept` VALUES ('d3a36d7764ef46d5bd5d6493a7d3410d', '--', 'f9fb33d709f841ca83b65b569fa4fd0a', NULL, 1);
INSERT INTO `MemberDept` VALUES ('4dea61a158d14b5d8fc40e61814ae782', '主任', 'f9fb33d709f841ca83b65b569fa4fd0a', NULL, 1);
INSERT INTO `MemberDept` VALUES ('256e702eb6d94c12bfeef83efd0c1697', '副主任', 'f9fb33d709f841ca83b65b569fa4fd0a', NULL, 1);
INSERT INTO `MemberDept` VALUES ('7db6dfa1b0ff4877bad6f6798ff2f6de', '综合处', 'e4d232a980ce48938f067670095dff0e', NULL, 1);
INSERT INTO `MemberDept` VALUES ('c50ca7f8dbbe4d8b8598d36933d6c0ba', '--', '7db6dfa1b0ff4877bad6f6798ff2f6de', NULL, 1);
INSERT INTO `MemberDept` VALUES ('c051d3effb6f40e6a689349eb9f45788', '处长', '7db6dfa1b0ff4877bad6f6798ff2f6de', NULL, 1);
INSERT INTO `MemberDept` VALUES ('09951d60eb194fe38596c50ff2fd330e', '副处长', '7db6dfa1b0ff4877bad6f6798ff2f6de', NULL, 1);
INSERT INTO `MemberDept` VALUES ('752018c06d93491590b6660210572c57', '秘书', '7db6dfa1b0ff4877bad6f6798ff2f6de', NULL, 1);
INSERT INTO `MemberDept` VALUES ('dcd4ca3ed3684a4c9b18dd678915473c', '宏观经济研究室', 'e4d232a980ce48938f067670095dff0e', NULL, 1);
INSERT INTO `MemberDept` VALUES ('63fb60416ef14a61b226bfb6f956f639', '--', 'dcd4ca3ed3684a4c9b18dd678915473c', NULL, 1);
INSERT INTO `MemberDept` VALUES ('1ee4bceda8e242f481500a03e803a4b0', '主任（正处级）', 'dcd4ca3ed3684a4c9b18dd678915473c', NULL, 1);
INSERT INTO `MemberDept` VALUES ('fefc619db20e44e1b166ab13f8f9d8d6', '政策仿真实验室', 'e4d232a980ce48938f067670095dff0e', NULL, 1);
INSERT INTO `MemberDept` VALUES ('3c809918e2be49cab49a0c02949342f9', '--', 'fefc619db20e44e1b166ab13f8f9d8d6', NULL, 1);
INSERT INTO `MemberDept` VALUES ('7dc1ea76cd334e659f290c546b66f62f', '副主任（副处级）', 'fefc619db20e44e1b166ab13f8f9d8d6', NULL, 1);
INSERT INTO `MemberDept` VALUES ('e97d51dc9c4d4b4fb0ef67fa487484eb', '世界经济研究室', 'e4d232a980ce48938f067670095dff0e', NULL, 1);
INSERT INTO `MemberDept` VALUES ('c60b0fd067eb485898382ee233e822e3', '--', 'e97d51dc9c4d4b4fb0ef67fa487484eb', NULL, 1);
INSERT INTO `MemberDept` VALUES ('3fff7ac7ea5b4372ad25ff530579c670', '主任（正处级）', 'e97d51dc9c4d4b4fb0ef67fa487484eb', NULL, 1);
INSERT INTO `MemberDept` VALUES ('214c78858fe54ce09a2129837a9628d4', '副主任（副处级）', 'e97d51dc9c4d4b4fb0ef67fa487484eb', NULL, 1);
INSERT INTO `MemberDept` VALUES ('c223178bbb1e49af95162d2c8c81e3a3', '财政金融研究室', 'e4d232a980ce48938f067670095dff0e', NULL, 1);
INSERT INTO `MemberDept` VALUES ('608a0d1ebf0440bf9f9a60592c0f24ac', '--', 'c223178bbb1e49af95162d2c8c81e3a3', NULL, 1);
INSERT INTO `MemberDept` VALUES ('9832e16ad2df452ebdc119bf7dcb5772', '主任（正处级）', 'c223178bbb1e49af95162d2c8c81e3a3', NULL, 1);
INSERT INTO `MemberDept` VALUES ('b00003f22f3f476b972c861f6a559f70', '副主任（副处级）', 'c223178bbb1e49af95162d2c8c81e3a3', NULL, 1);
INSERT INTO `MemberDept` VALUES ('81807270f8a14ed5951ed541a2a8b4bf', '产业经济研究室', 'e4d232a980ce48938f067670095dff0e', NULL, 1);
INSERT INTO `MemberDept` VALUES ('f0fb21329a1b4182bdb1ed39ed5eae25', '--', '81807270f8a14ed5951ed541a2a8b4bf', NULL, 1);
INSERT INTO `MemberDept` VALUES ('6d1909cc64d04c1cafc83ce1da502dd0', '副主任（副处级）', '81807270f8a14ed5951ed541a2a8b4bf', NULL, 1);
INSERT INTO `MemberDept` VALUES ('ea506f815ec34689bd4d9184973b2ff4', '新动能（创新创业）研究室', 'e4d232a980ce48938f067670095dff0e', NULL, 1);
INSERT INTO `MemberDept` VALUES ('58a1fcf7580b48c682e7d69365827e11', '--', 'ea506f815ec34689bd4d9184973b2ff4', NULL, 1);
INSERT INTO `MemberDept` VALUES ('5ac7c4514867489a877014505762c427', '主任（正处级）', 'ea506f815ec34689bd4d9184973b2ff4', NULL, 1);
INSERT INTO `MemberDept` VALUES ('f53ac001a4744fe489d67d4fe313828e', '副主任（副处级）', 'ea506f815ec34689bd4d9184973b2ff4', NULL, 1);
INSERT INTO `MemberDept` VALUES ('7a83d8e3363947b88c0813c32842a607', '信息化和产业发展部', '0', NULL, 1);
INSERT INTO `MemberDept` VALUES ('d37fe458d4844e1697e3882d55ecb2ed', '信息化和产业发展部', '7a83d8e3363947b88c0813c32842a607', NULL, 1);
INSERT INTO `MemberDept` VALUES ('91d59d123d1544c19bc12dbda9f2066e', '--', 'd37fe458d4844e1697e3882d55ecb2ed', NULL, 1);
INSERT INTO `MemberDept` VALUES ('dbe0a1149139458ea24fcddd4f19f96c', '主任', 'd37fe458d4844e1697e3882d55ecb2ed', NULL, 1);
INSERT INTO `MemberDept` VALUES ('243b662c77f3423ea36eefb98595e252', '副主任', 'd37fe458d4844e1697e3882d55ecb2ed', NULL, 1);
INSERT INTO `MemberDept` VALUES ('eadf35002fc04c25b842c33ecd9281e6', '综合处', '7a83d8e3363947b88c0813c32842a607', NULL, 1);
INSERT INTO `MemberDept` VALUES ('0f269117d95c48708d2dfe9de68a75a0', '--', 'eadf35002fc04c25b842c33ecd9281e6', NULL, 1);
INSERT INTO `MemberDept` VALUES ('dba1cc2aa2c74610afc56e4b49f1361c', '处长', 'eadf35002fc04c25b842c33ecd9281e6', NULL, 1);
INSERT INTO `MemberDept` VALUES ('9d662efb29b1403dbaa205ec27b2d7e8', '副处长', 'eadf35002fc04c25b842c33ecd9281e6', NULL, 1);
INSERT INTO `MemberDept` VALUES ('9a08a9e667ce4b6e94c9d29ca03911d0', '秘书', 'eadf35002fc04c25b842c33ecd9281e6', NULL, 1);
INSERT INTO `MemberDept` VALUES ('a7c407e8f8a24ff3b370e29dddf4cea6', '信息化发展政策处', '7a83d8e3363947b88c0813c32842a607', NULL, 1);
INSERT INTO `MemberDept` VALUES ('86d8187a87c7422786f9ea7bbcb351ad', '--', 'a7c407e8f8a24ff3b370e29dddf4cea6', NULL, 1);
INSERT INTO `MemberDept` VALUES ('3c4faff2ce8540d5a919fa50dfccee0c', '处长', 'a7c407e8f8a24ff3b370e29dddf4cea6', NULL, 1);
INSERT INTO `MemberDept` VALUES ('53b6dc6e9f514405bac45790d23ec6b6', '副处长', 'a7c407e8f8a24ff3b370e29dddf4cea6', NULL, 1);
INSERT INTO `MemberDept` VALUES ('01f304fe783445b7991404f6c7b2d4fc', '分享经济处', '7a83d8e3363947b88c0813c32842a607', NULL, 1);
INSERT INTO `MemberDept` VALUES ('c20738e059af493e90c9e0796916bbc5', '--', '01f304fe783445b7991404f6c7b2d4fc', NULL, 1);
INSERT INTO `MemberDept` VALUES ('c1ba7814ab8645f5b0e831146d1a32f8', '处长', '01f304fe783445b7991404f6c7b2d4fc', NULL, 1);
INSERT INTO `MemberDept` VALUES ('4df8ac61088a4390a672ffb218fb4865', '副处长', '01f304fe783445b7991404f6c7b2d4fc', NULL, 1);
INSERT INTO `MemberDept` VALUES ('2462fedb1c40423a937a5b6197ca33c9', '战略规划处', '7a83d8e3363947b88c0813c32842a607', NULL, 1);
INSERT INTO `MemberDept` VALUES ('29bf977d37d5416686a08187969b424b', '--', '2462fedb1c40423a937a5b6197ca33c9', NULL, 1);
INSERT INTO `MemberDept` VALUES ('cebec21f880e4f00a1947be3c917fff2', '副处长', '2462fedb1c40423a937a5b6197ca33c9', NULL, 1);
INSERT INTO `MemberDept` VALUES ('7be35b43e59f47fc8b5b3fa132bf6bb1', '智慧城市处', '7a83d8e3363947b88c0813c32842a607', NULL, 1);
INSERT INTO `MemberDept` VALUES ('713840bde0bd421fb6a845587aa2a715', '--', '7be35b43e59f47fc8b5b3fa132bf6bb1', NULL, 1);
INSERT INTO `MemberDept` VALUES ('6e23eb8fe85747cda0ed1a57358ab3dc', '副处长', '7be35b43e59f47fc8b5b3fa132bf6bb1', NULL, 1);
INSERT INTO `MemberDept` VALUES ('d0e01da7814a469cb79bdfef132e1292', '《年鉴》编辑部', '7a83d8e3363947b88c0813c32842a607', NULL, 1);
INSERT INTO `MemberDept` VALUES ('28c1c90a484746179f6c65e271441c60', '--', 'd0e01da7814a469cb79bdfef132e1292', NULL, 1);
INSERT INTO `MemberDept` VALUES ('674f96ae7f81468596f73424af43c089', '制造业研究处', '7a83d8e3363947b88c0813c32842a607', NULL, 1);
INSERT INTO `MemberDept` VALUES ('6c81220ed8264719bffba5400b99678e', '--', '674f96ae7f81468596f73424af43c089', NULL, 1);
INSERT INTO `MemberDept` VALUES ('053905219e944a0b899ab39e552aeb99', '处长', '674f96ae7f81468596f73424af43c089', NULL, 1);
INSERT INTO `MemberDept` VALUES ('d772f12a9cf246e68811b4e7e915d1bc', '副处长', '674f96ae7f81468596f73424af43c089', NULL, 1);
INSERT INTO `MemberDept` VALUES ('3a1090081fc34730b9669771f58fa908', '新兴产业处', '7a83d8e3363947b88c0813c32842a607', NULL, 1);
INSERT INTO `MemberDept` VALUES ('5805b30ee4f14fe4a0e782da1bb871c6', '--', '3a1090081fc34730b9669771f58fa908', NULL, 1);
INSERT INTO `MemberDept` VALUES ('358ee59160e949e6aaf2e0e911efc306', '处长', '3a1090081fc34730b9669771f58fa908', NULL, 1);
INSERT INTO `MemberDept` VALUES ('b823bc8b8c4a4983a908cef91c52354f', '副处长', '3a1090081fc34730b9669771f58fa908', NULL, 1);
INSERT INTO `MemberDept` VALUES ('709c7a23bfd248fcbc5cb0a3fbe40427', '汽车产业处', '7a83d8e3363947b88c0813c32842a607', NULL, 1);
INSERT INTO `MemberDept` VALUES ('79f139ab6a86430dadd174c579e4f8ec', '--', '709c7a23bfd248fcbc5cb0a3fbe40427', NULL, 1);
INSERT INTO `MemberDept` VALUES ('92378145ce6d4d0f8b8f13705ff0d16c', '处长', '709c7a23bfd248fcbc5cb0a3fbe40427', NULL, 1);
INSERT INTO `MemberDept` VALUES ('94608336237743a884bce4be706681f2', '副处长', '709c7a23bfd248fcbc5cb0a3fbe40427', NULL, 1);
INSERT INTO `MemberDept` VALUES ('4a0e413cd31445d9a5939e6abcc039fa', '汽车市场处', '7a83d8e3363947b88c0813c32842a607', NULL, 1);
INSERT INTO `MemberDept` VALUES ('ea23376abaed4423b140fcd7c52b6cdb', '--', '4a0e413cd31445d9a5939e6abcc039fa', NULL, 1);
INSERT INTO `MemberDept` VALUES ('cd414fddb1df4741b377d4436672c406', '处长', '4a0e413cd31445d9a5939e6abcc039fa', NULL, 1);
INSERT INTO `MemberDept` VALUES ('cec0d87365244034a349d2cd3cffda36', '副处长', '4a0e413cd31445d9a5939e6abcc039fa', NULL, 1);
INSERT INTO `MemberDept` VALUES ('f2405d59119244ffb19abccf8f39e3fc', '产业调查处', '7a83d8e3363947b88c0813c32842a607', NULL, 1);
INSERT INTO `MemberDept` VALUES ('f884fec6760a4c14b75ac7cf73ae991e', '--', 'f2405d59119244ffb19abccf8f39e3fc', NULL, 1);
INSERT INTO `MemberDept` VALUES ('a5c33b3e417f4dd1abfc9178ee353e55', '处长', 'f2405d59119244ffb19abccf8f39e3fc', NULL, 1);
INSERT INTO `MemberDept` VALUES ('05d66065eb4745e59e2ea45e3f4f6fac', '副处长', 'f2405d59119244ffb19abccf8f39e3fc', NULL, 1);
INSERT INTO `MemberDept` VALUES ('e582fc8ffadf449c871b7624331490b1', '资源与数据处', '7a83d8e3363947b88c0813c32842a607', NULL, 1);
INSERT INTO `MemberDept` VALUES ('dd174e1e1c9e463795920fd4d548171b', '--', 'e582fc8ffadf449c871b7624331490b1', NULL, 1);
INSERT INTO `MemberDept` VALUES ('c701db86d786403bad584c4c2708b273', '处长', 'e582fc8ffadf449c871b7624331490b1', NULL, 1);
INSERT INTO `MemberDept` VALUES ('f268b64b737740799d7781469ac71298', '副处长', 'e582fc8ffadf449c871b7624331490b1', NULL, 1);
INSERT INTO `MemberDept` VALUES ('f4433c94df4e42c9983338e4c0e559eb', '大数据发展部', '0', NULL, 1);
INSERT INTO `MemberDept` VALUES ('25a29f27fcab4f27a6fd66d3864faf0d', '大数据发展部', 'f4433c94df4e42c9983338e4c0e559eb', NULL, 1);
INSERT INTO `MemberDept` VALUES ('631287c421bc4e828714249bac99d95d', '--', '25a29f27fcab4f27a6fd66d3864faf0d', NULL, 1);
INSERT INTO `MemberDept` VALUES ('9060b59683c849b1bfafefdaf5a5cec8', '主任', '25a29f27fcab4f27a6fd66d3864faf0d', NULL, 1);
INSERT INTO `MemberDept` VALUES ('268f7dd4bc36486085432861a6ab1e5d', '副主任', '25a29f27fcab4f27a6fd66d3864faf0d', NULL, 1);
INSERT INTO `MemberDept` VALUES ('aa5c78f33dfd40dea42ac75351c8fbb9', '综合处', 'f4433c94df4e42c9983338e4c0e559eb', NULL, 1);
INSERT INTO `MemberDept` VALUES ('1f425c344e46437a905b73bbef83463f', '--', 'aa5c78f33dfd40dea42ac75351c8fbb9', NULL, 1);
INSERT INTO `MemberDept` VALUES ('ea0b4807acca460f8cfc42592c573c38', '副处长', 'aa5c78f33dfd40dea42ac75351c8fbb9', NULL, 1);
INSERT INTO `MemberDept` VALUES ('f493503757b74e9fb0a9827a6fba63ef', '大数据分析处', 'f4433c94df4e42c9983338e4c0e559eb', NULL, 1);
INSERT INTO `MemberDept` VALUES ('ef4db2dea0ab465c85b888887dc746b7', '--', 'f493503757b74e9fb0a9827a6fba63ef', NULL, 1);
INSERT INTO `MemberDept` VALUES ('fce8af0c4e734fe2ad69bcf44027aa26', '处长', 'f493503757b74e9fb0a9827a6fba63ef', NULL, 1);
INSERT INTO `MemberDept` VALUES ('55fe22b0af3b4927b5067ce35082dc9a', '副处长', 'f493503757b74e9fb0a9827a6fba63ef', NULL, 1);
INSERT INTO `MemberDept` VALUES ('9b094da7232846efbc4c7522bd5715fa', '互联网信息分析处', 'f4433c94df4e42c9983338e4c0e559eb', NULL, 1);
INSERT INTO `MemberDept` VALUES ('e69c2c2eff2f43e28442ad5e81333a0c', '--', '9b094da7232846efbc4c7522bd5715fa', NULL, 1);
INSERT INTO `MemberDept` VALUES ('4fc7675490ac4b96938e8eefefd4785a', '副处长', '9b094da7232846efbc4c7522bd5715fa', NULL, 1);
INSERT INTO `MemberDept` VALUES ('741dda4e483f4c69b1bce240ab417101', '规划与应用处', 'f4433c94df4e42c9983338e4c0e559eb', NULL, 1);
INSERT INTO `MemberDept` VALUES ('5b1092c69d0648358110c568599e7686', '--', '741dda4e483f4c69b1bce240ab417101', NULL, 1);
INSERT INTO `MemberDept` VALUES ('22b4431184be4267b354ecc88626c62c', '处长', '741dda4e483f4c69b1bce240ab417101', NULL, 1);
INSERT INTO `MemberDept` VALUES ('bf56d527e291448588f69266acef754c', '副处长', '741dda4e483f4c69b1bce240ab417101', NULL, 1);
INSERT INTO `MemberDept` VALUES ('a3e809ae2b154fff90a84cc2aa1ef80c', '数据管理与开放处', 'f4433c94df4e42c9983338e4c0e559eb', NULL, 1);
INSERT INTO `MemberDept` VALUES ('fa96e2fde70b4ea6bb3faee0154fdbd9', '--', 'a3e809ae2b154fff90a84cc2aa1ef80c', NULL, 1);
INSERT INTO `MemberDept` VALUES ('ab63fe44001246af9707890a22291fe7', '处长', 'a3e809ae2b154fff90a84cc2aa1ef80c', NULL, 1);
INSERT INTO `MemberDept` VALUES ('dacc8e974c1d46cbab4b6a5e5638ee57', '副处长', 'a3e809ae2b154fff90a84cc2aa1ef80c', NULL, 1);
INSERT INTO `MemberDept` VALUES ('6b062ec5cc3b4097874f1f939547e238', '“一带一路”官网运行处', 'f4433c94df4e42c9983338e4c0e559eb', NULL, 1);
INSERT INTO `MemberDept` VALUES ('5e61f0f329154f7e89061a09c1a69d73', '--', '6b062ec5cc3b4097874f1f939547e238', NULL, 1);
INSERT INTO `MemberDept` VALUES ('a74ec963fd1c4e9092b5a622c0c7e833', '处长', '6b062ec5cc3b4097874f1f939547e238', NULL, 1);
INSERT INTO `MemberDept` VALUES ('47bf2bfd91c24eadaafb77bb175f1992', '副处长', '6b062ec5cc3b4097874f1f939547e238', NULL, 1);
INSERT INTO `MemberDept` VALUES ('908690f13878434f8f89536b50217271', '信息与网络安全部', '0', NULL, 1);
INSERT INTO `MemberDept` VALUES ('c2d0d96ed4cd4de9838b6077fe4fd290', '信息与网络安全部', '908690f13878434f8f89536b50217271', NULL, 1);
INSERT INTO `MemberDept` VALUES ('381199f6254d45b893437446827d627c', '--', 'c2d0d96ed4cd4de9838b6077fe4fd290', NULL, 1);
INSERT INTO `MemberDept` VALUES ('585aec9e484b4c4ea589350c5e26fe70', '主任', 'c2d0d96ed4cd4de9838b6077fe4fd290', NULL, 1);
INSERT INTO `MemberDept` VALUES ('07c2d8812b0a41309d91321984a05900', '副主任', 'c2d0d96ed4cd4de9838b6077fe4fd290', NULL, 1);
INSERT INTO `MemberDept` VALUES ('6df6b9d1d0c84b27a67067dce275679d', '综合处', '908690f13878434f8f89536b50217271', NULL, 1);
INSERT INTO `MemberDept` VALUES ('8bd7ca3b75fc47c98619f39ddb1f73e9', '--', '6df6b9d1d0c84b27a67067dce275679d', NULL, 1);
INSERT INTO `MemberDept` VALUES ('1e1d3f6b4cfd4e8a8c491520ee8fc9fc', '秘书', '6df6b9d1d0c84b27a67067dce275679d', NULL, 1);
INSERT INTO `MemberDept` VALUES ('0e3880a06eae4e2ba717168f9cfca820', '政务外网认证管理处', '908690f13878434f8f89536b50217271', NULL, 1);
INSERT INTO `MemberDept` VALUES ('de38963f008b41d6a61e8e0a1c853409', '--', '0e3880a06eae4e2ba717168f9cfca820', NULL, 1);
INSERT INTO `MemberDept` VALUES ('ceb4e844d3954df1900629260e4ecf25', '副处长', '0e3880a06eae4e2ba717168f9cfca820', NULL, 1);
INSERT INTO `MemberDept` VALUES ('de5a128c1334436d844f0991c3e226c8', '政务外网监测处', '908690f13878434f8f89536b50217271', NULL, 1);
INSERT INTO `MemberDept` VALUES ('325581cad3104eebac40039f09c07a92', '--', 'de5a128c1334436d844f0991c3e226c8', NULL, 1);
INSERT INTO `MemberDept` VALUES ('748657ca79f745f88fb276135dc23092', '处长', 'de5a128c1334436d844f0991c3e226c8', NULL, 1);
INSERT INTO `MemberDept` VALUES ('53046d8d41f348cfa02ec8139d28e530', '副处长', 'de5a128c1334436d844f0991c3e226c8', NULL, 1);
INSERT INTO `MemberDept` VALUES ('45ea03e9b9ab4d109e9c301aea6ee1c4', '办公信息化安全处', '908690f13878434f8f89536b50217271', NULL, 1);
INSERT INTO `MemberDept` VALUES ('bb21f6c445aa4057a65cb7f71a63766b', '--', '45ea03e9b9ab4d109e9c301aea6ee1c4', NULL, 1);
INSERT INTO `MemberDept` VALUES ('6d6a745e0ea94c60b4cf991c9079f296', '处长', '45ea03e9b9ab4d109e9c301aea6ee1c4', NULL, 1);
INSERT INTO `MemberDept` VALUES ('81bf63a0564442219bdf00905efeb619', '副处长', '45ea03e9b9ab4d109e9c301aea6ee1c4', NULL, 1);
INSERT INTO `MemberDept` VALUES ('22d934c2522a4f42b1e03f6f75c836b6', '信息安全评估处', '908690f13878434f8f89536b50217271', NULL, 1);
INSERT INTO `MemberDept` VALUES ('f72ac3dcc2514ab790f7ac66b424d00c', '--', '22d934c2522a4f42b1e03f6f75c836b6', NULL, 1);
INSERT INTO `MemberDept` VALUES ('90510137a6de47b4890aee65ccc9f423', '处长', '22d934c2522a4f42b1e03f6f75c836b6', NULL, 1);
INSERT INTO `MemberDept` VALUES ('e128a5021ab84bdea66f09e8b74d3a48', '副处长', '22d934c2522a4f42b1e03f6f75c836b6', NULL, 1);
INSERT INTO `MemberDept` VALUES ('63a85d5a566f4e2dbc798dd64f452836', '数据安全处', '908690f13878434f8f89536b50217271', NULL, 1);
INSERT INTO `MemberDept` VALUES ('c73c637d739d43d2b5b31baa8acf75ca', '--', '63a85d5a566f4e2dbc798dd64f452836', NULL, 1);
INSERT INTO `MemberDept` VALUES ('3e152c364d9f4259a76cf5b641c6b901', '处长', '63a85d5a566f4e2dbc798dd64f452836', NULL, 1);
INSERT INTO `MemberDept` VALUES ('5b3a465925ab4ec0929cf92c32caddf8', '研究院', '908690f13878434f8f89536b50217271', NULL, 1);
INSERT INTO `MemberDept` VALUES ('26f00059c9ab4da89b0fc9589fddef71', '--', '5b3a465925ab4ec0929cf92c32caddf8', NULL, 1);
INSERT INTO `MemberDept` VALUES ('98ba794e60b14ea0bc614af5479577f4', '业务发展中心', '908690f13878434f8f89536b50217271', NULL, 1);
INSERT INTO `MemberDept` VALUES ('d9a9ee8e44574efeb09132154de07313', '--', '98ba794e60b14ea0bc614af5479577f4', NULL, 1);
INSERT INTO `MemberDept` VALUES ('ff683a7dda804d398dcc54a1c7bc0519', '副处长', '98ba794e60b14ea0bc614af5479577f4', NULL, 1);
INSERT INTO `MemberDept` VALUES ('75d0e6e34ada4ff5b9e07e00e9e7d588', '安委会', '908690f13878434f8f89536b50217271', NULL, 1);
INSERT INTO `MemberDept` VALUES ('b90b56fbe0664424b28fa7b75e5ade94', '--', '75d0e6e34ada4ff5b9e07e00e9e7d588', NULL, 1);
INSERT INTO `MemberDept` VALUES ('389e0d8438584a0588fe1a12c9abbdcc', '党委办公室', '0', NULL, 1);
INSERT INTO `MemberDept` VALUES ('4c1949e589624cff946df2b047a9c918', '党委办公室', '389e0d8438584a0588fe1a12c9abbdcc', NULL, 1);
INSERT INTO `MemberDept` VALUES ('3499aa2eb53c4d45a8cca0ed0d02844b', '--', '4c1949e589624cff946df2b047a9c918', NULL, 1);
INSERT INTO `MemberDept` VALUES ('fd8047991aba4880b3e52a7d3b543a45', '主任', '4c1949e589624cff946df2b047a9c918', NULL, 1);
INSERT INTO `MemberDept` VALUES ('6da3970808f34268a59bcf08ad80596d', '副主任', '4c1949e589624cff946df2b047a9c918', NULL, 1);
INSERT INTO `MemberDept` VALUES ('db71ed6e002544f8bb7f874cdf65e5af', '组织委员(副处级)', '4c1949e589624cff946df2b047a9c918', NULL, 1);
INSERT INTO `MemberDept` VALUES ('1a7e2c04c6ef4391b887e8e3fb6955cf', '宣传委员(正处级)', '4c1949e589624cff946df2b047a9c918', NULL, 1);
INSERT INTO `MemberDept` VALUES ('d24ce36ffa074964b3ca34dc113761a2', '纪检委员(正处级)', '4c1949e589624cff946df2b047a9c918', NULL, 1);
INSERT INTO `MemberDept` VALUES ('a269b2ec543649ab8bb3814898c7b251', '纪检委员(副处级)', '4c1949e589624cff946df2b047a9c918', NULL, 1);
INSERT INTO `MemberDept` VALUES ('d172d8acfc6f436087a1d70ebb9eb58a', '工会委员(正处级)', '4c1949e589624cff946df2b047a9c918', NULL, 1);
INSERT INTO `MemberDept` VALUES ('d5be63bdfdc84c01a01d8ccb9e7db7e5', '电子政务工程中心', '0', NULL, 1);
INSERT INTO `MemberDept` VALUES ('3da38b2eb10846b78f5725bcd42e3e6a', '电子政务工程中心', 'd5be63bdfdc84c01a01d8ccb9e7db7e5', NULL, 1);
INSERT INTO `MemberDept` VALUES ('594df2f636ff4d95a538d4005c88bd14', '--', '3da38b2eb10846b78f5725bcd42e3e6a', NULL, 1);
INSERT INTO `MemberDept` VALUES ('0f04aafd54cc415a902ba3205413788f', '副主任', '3da38b2eb10846b78f5725bcd42e3e6a', NULL, 1);
INSERT INTO `MemberDept` VALUES ('8be2c04f572646c5892b9f2cb578c59b', '主任助理', '3da38b2eb10846b78f5725bcd42e3e6a', NULL, 1);
INSERT INTO `MemberDept` VALUES ('fda0bc03cb50433b8182d3e43c9947c9', '总工程师', '3da38b2eb10846b78f5725bcd42e3e6a', NULL, 1);
INSERT INTO `MemberDept` VALUES ('4e5ee87c27d445d98a62e0079242f028', '咨询评估处', 'd5be63bdfdc84c01a01d8ccb9e7db7e5', NULL, 1);
INSERT INTO `MemberDept` VALUES ('3d84169abf4f4a0f9d3500cd58ca7646', '--', '4e5ee87c27d445d98a62e0079242f028', NULL, 1);
INSERT INTO `MemberDept` VALUES ('7d263a9a86364e279f0ce3f1628ff9b1', '处长', '4e5ee87c27d445d98a62e0079242f028', NULL, 1);
INSERT INTO `MemberDept` VALUES ('4e3ae184c17b466b8d2e5e64ba08ca55', '应用服务处', 'd5be63bdfdc84c01a01d8ccb9e7db7e5', NULL, 1);
INSERT INTO `MemberDept` VALUES ('f2600039a6ed4618b596f0b9a1b44fea', '--', '4e3ae184c17b466b8d2e5e64ba08ca55', NULL, 1);
INSERT INTO `MemberDept` VALUES ('a2ab065027d449c19138521f5a115091', '处长', '4e3ae184c17b466b8d2e5e64ba08ca55', NULL, 1);
INSERT INTO `MemberDept` VALUES ('e2446e1557df4576b83dd7937bc13a3a', '工程技术处', 'd5be63bdfdc84c01a01d8ccb9e7db7e5', NULL, 1);
INSERT INTO `MemberDept` VALUES ('49a86aa570014df694cb75cf780ba47f', '--', 'e2446e1557df4576b83dd7937bc13a3a', NULL, 1);
INSERT INTO `MemberDept` VALUES ('a239d09096f34d1ab501ddf795f56aeb', '秘书', 'e2446e1557df4576b83dd7937bc13a3a', NULL, 1);
INSERT INTO `MemberDept` VALUES ('fd2491479a6d4be891556cb4facab33c', '中国国信信息总公司', '0', NULL, 1);
INSERT INTO `MemberDept` VALUES ('5715ba84f4194ec18ec550d09de028bd', '公司领导', 'fd2491479a6d4be891556cb4facab33c', NULL, 1);
INSERT INTO `MemberDept` VALUES ('aee0566184fb4a4498cf85970ee4f62a', '--', '5715ba84f4194ec18ec550d09de028bd', NULL, 1);
INSERT INTO `MemberDept` VALUES ('3a0a918490494397850b92e88e929ca6', '总经理', '5715ba84f4194ec18ec550d09de028bd', NULL, 1);
INSERT INTO `MemberDept` VALUES ('440ef93f964640d4a0a4e45c830c8740', '副总经理', '5715ba84f4194ec18ec550d09de028bd', NULL, 1);
INSERT INTO `MemberDept` VALUES ('5602fc7e9d31414781ed134013fe92b8', '高管团队', 'fd2491479a6d4be891556cb4facab33c', NULL, 1);
INSERT INTO `MemberDept` VALUES ('b9f007a7e90044a690a4bb790792cdb4', '--', '5602fc7e9d31414781ed134013fe92b8', NULL, 1);
INSERT INTO `MemberDept` VALUES ('94ad28c0f3714a05b354b2556ff30bac', '财务总监', '5602fc7e9d31414781ed134013fe92b8', NULL, 1);
INSERT INTO `MemberDept` VALUES ('e5900acccb4f49f5b2941b14e0164c7a', '战略发展总监', '5602fc7e9d31414781ed134013fe92b8', NULL, 1);
INSERT INTO `MemberDept` VALUES ('2982df6d3d3d4cd6ae801b453d8d5ef3', '总经济师', '5602fc7e9d31414781ed134013fe92b8', NULL, 1);
INSERT INTO `MemberDept` VALUES ('ca5327b65f7c4beba1f6c1ac3cf0c12a', '副总工程师', '5602fc7e9d31414781ed134013fe92b8', NULL, 1);
INSERT INTO `MemberDept` VALUES ('ce4ef84163d54a76b93f74dfba305d0f', '行政办公室', 'fd2491479a6d4be891556cb4facab33c', NULL, 1);
INSERT INTO `MemberDept` VALUES ('254b55a6771b421e9628f7717e9130c9', '--', 'ce4ef84163d54a76b93f74dfba305d0f', NULL, 1);
INSERT INTO `MemberDept` VALUES ('ada359fb7c0241f3ad77fa3fb5e8683c', '部门副主任', 'ce4ef84163d54a76b93f74dfba305d0f', NULL, 1);
INSERT INTO `MemberDept` VALUES ('eb0dca21783f406aa96e9b25de18ec80', '秘书', 'ce4ef84163d54a76b93f74dfba305d0f', NULL, 1);
INSERT INTO `MemberDept` VALUES ('03f54c74ace748778ee6d24c5709a13e', '资产财务部', 'fd2491479a6d4be891556cb4facab33c', NULL, 1);
INSERT INTO `MemberDept` VALUES ('ad8e8fa99c3b4a6e9ad731a661fb43aa', '--', '03f54c74ace748778ee6d24c5709a13e', NULL, 1);
INSERT INTO `MemberDept` VALUES ('57108a34fa574d4d951cfb2da3b50958', '部门副经理', '03f54c74ace748778ee6d24c5709a13e', NULL, 1);
INSERT INTO `MemberDept` VALUES ('fb37133bc30040d3bb1fb2b911b41a31', '商务风控部', 'fd2491479a6d4be891556cb4facab33c', NULL, 1);
INSERT INTO `MemberDept` VALUES ('dac7e842521b4764879c81e8c9c49e5f', '--', 'fb37133bc30040d3bb1fb2b911b41a31', NULL, 1);
INSERT INTO `MemberDept` VALUES ('aad099cf49044a8382614b44b01e0a99', '部门副经理', 'fb37133bc30040d3bb1fb2b911b41a31', NULL, 1);
INSERT INTO `MemberDept` VALUES ('f2aae670fc3845abb40fe83f2f239c69', '大客户服务部', 'fd2491479a6d4be891556cb4facab33c', NULL, 1);
INSERT INTO `MemberDept` VALUES ('1896fc52f9b348e29a8f664362d34c4a', '--', 'f2aae670fc3845abb40fe83f2f239c69', NULL, 1);
INSERT INTO `MemberDept` VALUES ('57b5980d2c87445a871ab0d9911cc666', '部门经理', 'f2aae670fc3845abb40fe83f2f239c69', NULL, 1);
INSERT INTO `MemberDept` VALUES ('1475b3779b35460287520599d467a9f2', '市场拓展部', 'fd2491479a6d4be891556cb4facab33c', NULL, 1);
INSERT INTO `MemberDept` VALUES ('55d07d57f3524e53afe0b284d5544d47', '--', '1475b3779b35460287520599d467a9f2', NULL, 1);
INSERT INTO `MemberDept` VALUES ('4fafed1ec3bc407dbd4a436b45fc9569', '部门副经理', '1475b3779b35460287520599d467a9f2', NULL, 1);
INSERT INTO `MemberDept` VALUES ('181cd13ad272429080f2cff9ba8b26e6', '智慧政务咨询部', 'fd2491479a6d4be891556cb4facab33c', NULL, 1);
INSERT INTO `MemberDept` VALUES ('2b575bd7e02b42ebaf22bc6af54ef633', '--', '181cd13ad272429080f2cff9ba8b26e6', NULL, 1);
INSERT INTO `MemberDept` VALUES ('6ad8d7bd668b4ae68ca96bb29e115d72', '部门经理', '181cd13ad272429080f2cff9ba8b26e6', NULL, 1);
INSERT INTO `MemberDept` VALUES ('0d4a1bce01e84c4385068f563864c8d3', '电子商务部', 'fd2491479a6d4be891556cb4facab33c', NULL, 1);
INSERT INTO `MemberDept` VALUES ('7d3d13705a7f41308612a04c969a6a54', '--', '0d4a1bce01e84c4385068f563864c8d3', NULL, 1);
INSERT INTO `MemberDept` VALUES ('b2c7164c94664ca8af4858206929f883', '部门主任', '0d4a1bce01e84c4385068f563864c8d3', NULL, 1);
INSERT INTO `MemberDept` VALUES ('609621ca724e45809ecec09bc07447d1', '中经网', '0', NULL, 1);
INSERT INTO `MemberDept` VALUES ('b48054d599f54ea790db8b5bbc2b0720', '公司领导', '609621ca724e45809ecec09bc07447d1', NULL, 1);
INSERT INTO `MemberDept` VALUES ('ef78433a8faa4825848c3732242d7316', '--', 'b48054d599f54ea790db8b5bbc2b0720', NULL, 1);
INSERT INTO `MemberDept` VALUES ('194be80fc4bd43759f8244b0faf79180', '董事长', 'b48054d599f54ea790db8b5bbc2b0720', NULL, 1);
INSERT INTO `MemberDept` VALUES ('a528034900a84a86a6236e8bbfb20d1f', '公司常务副总经理', 'b48054d599f54ea790db8b5bbc2b0720', NULL, 1);
INSERT INTO `MemberDept` VALUES ('436043bfe376488a89e5455cc3e34cac', '公司副总经理、总支书记', 'b48054d599f54ea790db8b5bbc2b0720', NULL, 1);
INSERT INTO `MemberDept` VALUES ('5de70af341714a4bb49a12c52cb9bf84', '公司副总经理', 'b48054d599f54ea790db8b5bbc2b0720', NULL, 1);
INSERT INTO `MemberDept` VALUES ('e8f2f55f878c45d8a11f14b3692b7a38', '公司技术总监', 'b48054d599f54ea790db8b5bbc2b0720', NULL, 1);
INSERT INTO `MemberDept` VALUES ('4be2a9b0450d48f9bdffe227ee3016a5', '公司总工程师', 'b48054d599f54ea790db8b5bbc2b0720', NULL, 1);
INSERT INTO `MemberDept` VALUES ('1785f47655574eb28dceacd8b32a4ae6', '公司总会计师', 'b48054d599f54ea790db8b5bbc2b0720', NULL, 1);
INSERT INTO `MemberDept` VALUES ('294744632e9d4c7fa121300288e1c09a', '公司副总工程师', 'b48054d599f54ea790db8b5bbc2b0720', NULL, 1);
INSERT INTO `MemberDept` VALUES ('b286e3cb733e46babd662b642c47902b', '公司销售总监', 'b48054d599f54ea790db8b5bbc2b0720', NULL, 1);
INSERT INTO `MemberDept` VALUES ('c67afd530d5440ca99f124de49ab6a5e', '总经理助理', 'b48054d599f54ea790db8b5bbc2b0720', NULL, 1);
INSERT INTO `MemberDept` VALUES ('6082bc7ff11c490989fb556ca5f2467c', '办公室', '609621ca724e45809ecec09bc07447d1', NULL, 1);
INSERT INTO `MemberDept` VALUES ('ecd1f20e1b474cebab2aa516915fb3e8', '--', '6082bc7ff11c490989fb556ca5f2467c', NULL, 1);
INSERT INTO `MemberDept` VALUES ('43462e3c2df4411fa15bf8a9eb4f0f15', '办公室副主任', '6082bc7ff11c490989fb556ca5f2467c', NULL, 1);
INSERT INTO `MemberDept` VALUES ('08ebecb2af374fa9b486f80b85b09c03', '秘书', '6082bc7ff11c490989fb556ca5f2467c', NULL, 1);
INSERT INTO `MemberDept` VALUES ('1769d0738f634d0d94f57bbae163bb37', '投资与资产管理部', '609621ca724e45809ecec09bc07447d1', NULL, 1);
INSERT INTO `MemberDept` VALUES ('bcef5fa3661142c3959c41869342acc2', '--', '1769d0738f634d0d94f57bbae163bb37', NULL, 1);
INSERT INTO `MemberDept` VALUES ('92ea9537803f40fa875d1fff82971d3b', '部门经理', '1769d0738f634d0d94f57bbae163bb37', NULL, 1);
INSERT INTO `MemberDept` VALUES ('31f5595cf18547a392606ceeb1d6480b', '市场客服部', '609621ca724e45809ecec09bc07447d1', NULL, 1);
INSERT INTO `MemberDept` VALUES ('f246fe6b5ac049cbb92517debb36f8f1', '--', '31f5595cf18547a392606ceeb1d6480b', NULL, 1);
INSERT INTO `MemberDept` VALUES ('a0f823cca19f4902b19e1d3733cc94d7', '部门经理', '31f5595cf18547a392606ceeb1d6480b', NULL, 1);
INSERT INTO `MemberDept` VALUES ('bd7015a3d1c5467194e366004df0a77f', '中经网软件公司', '609621ca724e45809ecec09bc07447d1', NULL, 1);
INSERT INTO `MemberDept` VALUES ('c0a1f58578d04dda9a4ce569abe15afb', '--', 'bd7015a3d1c5467194e366004df0a77f', NULL, 1);
INSERT INTO `MemberDept` VALUES ('648c7ae3e96645ec8040d2d62701419f', '总经理', 'bd7015a3d1c5467194e366004df0a77f', NULL, 1);
INSERT INTO `MemberDept` VALUES ('0f7bc6f1dcfe4771b1a838326a3b57cd', '物业公司', '0', NULL, 1);
INSERT INTO `MemberDept` VALUES ('35741155836645a398a475bc33e876fd', '物业公司', '0f7bc6f1dcfe4771b1a838326a3b57cd', NULL, 1);
INSERT INTO `MemberDept` VALUES ('cc48f163672d4673a0bd9596a59342a0', '--', '35741155836645a398a475bc33e876fd', NULL, 1);
INSERT INTO `MemberDept` VALUES ('191ee64e8c21430a8af5df5b0907d291', '总经理', '35741155836645a398a475bc33e876fd', NULL, 1);
INSERT INTO `MemberDept` VALUES ('1dce547193ff4f3a992c12ac48e86460', '出纳', '35741155836645a398a475bc33e876fd', NULL, 1);
INSERT INTO `MemberDept` VALUES ('0646c64ed20647679f200b041babeab4', '信用中心', '0', NULL, 1);
INSERT INTO `MemberDept` VALUES ('fd7034754eeb4874b4c7dd07bcd9d1b3', '信用中心', '0646c64ed20647679f200b041babeab4', NULL, 1);
INSERT INTO `MemberDept` VALUES ('3dd4aba4c1d94bd69a64e21dcb7e4bc6', '--', 'fd7034754eeb4874b4c7dd07bcd9d1b3', NULL, 1);
INSERT INTO `MemberDept` VALUES ('4ca4e5c0ba6245e1bd86781a9f6ca8af', '主任', 'fd7034754eeb4874b4c7dd07bcd9d1b3', NULL, 1);
INSERT INTO `MemberDept` VALUES ('2b5bd6837cb34dd387b39a7e3a50a980', '副主任', 'fd7034754eeb4874b4c7dd07bcd9d1b3', NULL, 1);
INSERT INTO `MemberDept` VALUES ('136f8bc0901d47af9f75c7ef8562891b', '办公室（人事处）', '0646c64ed20647679f200b041babeab4', NULL, 1);
INSERT INTO `MemberDept` VALUES ('230800f8527c4c6eba9535ae307cf1df', '--', '136f8bc0901d47af9f75c7ef8562891b', NULL, 1);
INSERT INTO `MemberDept` VALUES ('8b9dd9362101410da1ca601fc120a40b', '综合规划处', '0646c64ed20647679f200b041babeab4', NULL, 1);
INSERT INTO `MemberDept` VALUES ('e74b7b67d1e548ecb1192df589daa822', '--', '8b9dd9362101410da1ca601fc120a40b', NULL, 1);
INSERT INTO `MemberDept` VALUES ('4ad47d3c542344ecb65f68457b491ed4', '信息资源管理处', '0646c64ed20647679f200b041babeab4', NULL, 1);
INSERT INTO `MemberDept` VALUES ('6f9b9682aa754f158582348be7ae6c22', '--', '4ad47d3c542344ecb65f68457b491ed4', NULL, 1);
INSERT INTO `MemberDept` VALUES ('1ec96db9064d4993b27c13a16bd1b52d', '信息监测分析处', '0646c64ed20647679f200b041babeab4', NULL, 1);
INSERT INTO `MemberDept` VALUES ('4ac9b22a287f4fa7ab73155cb6efb9d8', '--', '1ec96db9064d4993b27c13a16bd1b52d', NULL, 1);
INSERT INTO `MemberDept` VALUES ('e13e1f5b90b54d888e706cdd9659b82c', '信息应用与服务处', '0646c64ed20647679f200b041babeab4', NULL, 1);
INSERT INTO `MemberDept` VALUES ('d15f90763b224ebe9c2fb43d89f0fcd6', '--', 'e13e1f5b90b54d888e706cdd9659b82c', NULL, 1);
INSERT INTO `MemberDept` VALUES ('7fb14c9dde784a87aa3266d6950151bb', '信息安全管理处', '0646c64ed20647679f200b041babeab4', NULL, 1);
INSERT INTO `MemberDept` VALUES ('ddbdb78be5b94cc2a8ae166133a329a3', '--', '7fb14c9dde784a87aa3266d6950151bb', NULL, 1);
INSERT INTO `MemberDept` VALUES ('bf1266e5470e464398d4bae23e6d7ea1', '信息公开处', '0646c64ed20647679f200b041babeab4', NULL, 1);
INSERT INTO `MemberDept` VALUES ('cc4b92f548e24efbbb6e5cef1ac82d8c', '--', 'bf1266e5470e464398d4bae23e6d7ea1', NULL, 1);
INSERT INTO `MemberDept` VALUES ('14e6d9ed995f4ae892a4bbcd9d7ce753', '中国信息协会', '0', NULL, 1);
INSERT INTO `MemberDept` VALUES ('37704734edf5490b82d2d034fc7e9a26', '中国信息协会', '14e6d9ed995f4ae892a4bbcd9d7ce753', NULL, 1);
INSERT INTO `MemberDept` VALUES ('acbba5152c2d4f9898f94657bb2ce2ee', '--', '37704734edf5490b82d2d034fc7e9a26', NULL, 1);
INSERT INTO `MemberDept` VALUES ('3a879de38931491c8ce0dd0bc0813c63', '会长', '37704734edf5490b82d2d034fc7e9a26', NULL, 1);
INSERT INTO `MemberDept` VALUES ('ea554f0fb79e4ac6b2ce7e9448a8d931', '办公室', '14e6d9ed995f4ae892a4bbcd9d7ce753', NULL, 1);
INSERT INTO `MemberDept` VALUES ('28a9ae3c8f4a430f9b2d39e6491e57f4', '--', 'ea554f0fb79e4ac6b2ce7e9448a8d931', NULL, 1);
INSERT INTO `MemberDept` VALUES ('a3fa2b795d4e49c7b7e00aac31c52180', '主任', 'ea554f0fb79e4ac6b2ce7e9448a8d931', NULL, 1);
INSERT INTO `MemberDept` VALUES ('dafbc740422847bcbcb89f9022bc605d', '测试部门', 'cb93c64de20d40f88ddfaa834b0821be', NULL, 0);
INSERT INTO `MemberDept` VALUES ('757d101169854fcc8b638d8eafc53ef2', '--', 'dafbc740422847bcbcb89f9022bc605d', NULL, 0);
INSERT INTO `MemberDept` VALUES ('f49622cf256e4681821a030c833274b9', '测试部门', 'cb93c64de20d40f88ddfaa834b0821be', NULL, 0);
INSERT INTO `MemberDept` VALUES ('5818a5e383cc450ba211606dbe4857a8', '--', 'f49622cf256e4681821a030c833274b9', NULL, 0);

-- ----------------------------
-- Table structure for MemberFollowRecord
-- ----------------------------
DROP TABLE IF EXISTS `MemberFollowRecord`;
CREATE TABLE `MemberFollowRecord`  (
  `autoId` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `recordId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '记录ID',
  `memberId` bigint(20) NOT NULL COMMENT '客户ID',
  `workerId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '员工ID',
  `recordTime` datetime  NULL DEFAULT NULL COMMENT '处理时间',
  `nextContactTime` datetime NULL DEFAULT NULL COMMENT '下次联系时间/下次跟进时间',
  `recordIp` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理Ip',
  `isValidContact` smallint(2) NULL DEFAULT NULL COMMENT '有效联系(0否，1是)',
  `recordContent` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '备注',
  `intentionality` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '意向度',
  `recordType` int(11) NULL DEFAULT NULL COMMENT '记录类型1.普通、2.电话、3.短信、4.系统',
  `companyId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前客户所属客户ID',
  PRIMARY KEY (`autoId`, `recordId`) USING BTREE,
  UNIQUE INDEX `UN_FollowRecord_RecordId`(`recordId`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 45 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '跟进记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of MemberFollowRecord
-- ----------------------------
INSERT INTO `MemberFollowRecord` VALUES (1, '1543817384542', 10001, '8888', '2018-12-03 14:09:12', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (2, '1543817400071', 10001, '8888', '2018-12-03 14:09:27', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (3, '1543817525709', 10001, '8888', '2018-12-03 14:11:33', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (4, '1543820706287', 10001, '8888', '2018-12-03 15:05:06', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (5, '1543820772788', 10001, '8888', '2018-12-03 15:06:12', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (6, '1543822586090', 10001, '8888', '2018-12-03 15:36:26', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (7, '1543826387133', 10001, '8888', '2018-12-03 16:39:47', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (8, '1543826427487', 10001, '8888', '2018-12-03 16:40:27', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (9, '1543826526546', 10001, '8888', '2018-12-03 16:42:06', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (10, '1543910510317', 10001, '8888', '2018-12-04 16:01:44', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (11, '1543996088428', 26, '8888', '2018-12-05 15:48:08', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (12, '1544008149051', 29, '8888', '2018-12-05 19:09:09', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (13, '1544659347659', 269, '122', '2018-12-13 08:02:27', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (14, '1544677772622', 355, '112', '2018-12-13 13:09:32', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (15, '1544682382839', 320, '122', '2018-12-13 14:26:22', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (16, '1544682384506', 320, '122', '2018-12-13 14:26:24', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (17, '1544691746537', 715, '122', '2018-12-13 17:02:26', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (18, '1544744255859', 268, '122', '2018-12-14 07:37:35', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (19, '1544744288005', 268, '122', '2018-12-14 07:38:08', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (20, '1544744288852', 268, '122', '2018-12-14 07:38:08', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (21, '1544772030718', 715, '112', '2018-12-14 15:20:30', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (22, '1544772033607', 715, '112', '2018-12-14 15:20:33', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (23, '1544772034121', 715, '112', '2018-12-14 15:20:34', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (24, '1544772034268', 715, '112', '2018-12-14 15:20:34', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (25, '1544777551012', 274, '122', '2018-12-14 16:52:31', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (26, '1544778763863', 715, '112', '2018-12-14 17:12:43', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (27, '1544779420542', 715, '112', '2018-12-14 17:23:40', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (28, '1544779643600', 715, '112', '2018-12-14 17:27:23', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (29, '1544779731312', 716, '112', '2018-12-14 17:28:51', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (30, '1544779940781', 715, '112', '2018-12-14 17:32:20', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (31, '1544779950246', 715, '112', '2018-12-14 17:32:30', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (32, '1544780177121', 716, '112', '2018-12-14 17:36:17', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (33, '1544780184364', 716, '112', '2018-12-14 17:36:24', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (34, '1544780202910', 716, '112', '2018-12-14 17:36:42', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (35, '1544780251089', 702, '112', '2018-12-14 17:37:31', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (36, '1545010365365', 274, '122', '2018-12-17 09:32:45', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (37, '1545010419165', 268, '122', '2018-12-17 09:33:39', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (38, '1545017038538', 715, '112', '2018-12-17 11:23:58', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (39, '1545204041265', 717, '112', '2018-12-19 15:20:41', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (40, '1545206277277', 704, '112', '2018-12-19 15:57:57', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (41, '1545206277943', 704, '112', '2018-12-19 15:57:57', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (42, '1545206278116', 704, '112', '2018-12-19 15:57:58', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (43, '1545206291403', 693, '112', '2018-12-19 15:58:11', NULL, NULL, 0, NULL, NULL, 2, '0');
INSERT INTO `MemberFollowRecord` VALUES (44, '1545206291609', 693, '112', '2018-12-19 15:58:11', NULL, NULL, 0, NULL, NULL, 2, '0');

-- ----------------------------
-- Table structure for OperateLog
-- ----------------------------
DROP TABLE IF EXISTS `OperateLog`;
CREATE TABLE `OperateLog`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `moduleName` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '操作模块',
  `operateName` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '操作',
  `operateParams` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '操作参数',
  `createTime` datetime  NULL DEFAULT NULL COMMENT '操作时间',
  `workerId` int(11) NOT NULL DEFAULT 0 COMMENT '员工ID',
  `workerName` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '员工姓名,冗余字段',
  `operateStatus` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '操作状态码',
  `operateDesc` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '操作状态码说明',
  `companyId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '所属客户',
  `operateData` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '操作返回数据',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1164 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '操作记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for organ_mid
-- ----------------------------
DROP TABLE IF EXISTS `organ_mid`;
CREATE TABLE `organ_mid`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `organId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `organParentId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '组织机构表中间表' ROW_FORMAT = Dynamic;

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
-- Table structure for Post
-- ----------------------------
DROP TABLE IF EXISTS `Post`;
CREATE TABLE `Post`  (
  `postId` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增Id,岗位Id',
  `postName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '岗位名称',
  `orderNo` smallint(2) NOT NULL DEFAULT 0 COMMENT '排序',
  `companyId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '所属客户',
  PRIMARY KEY (`postId`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '岗位表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of Post
-- ----------------------------
INSERT INTO `Post` VALUES (1, '服务', 0, '0');
INSERT INTO `Post` VALUES (2, '销售', 0, '0');

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
-- Table structure for RecordQuality
-- ----------------------------
DROP TABLE IF EXISTS `RecordQuality`;
CREATE TABLE `RecordQuality`  (
  `autoId` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `recordId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '录音编号',
  `memberId` bigint(20) NULL DEFAULT NULL COMMENT '客户ID',
  `workerId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '录音归属人',
  `workerName` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `deptId` int(11) NULL DEFAULT NULL,
  `recordScore` smallint(4) NULL DEFAULT NULL COMMENT '录音得分',
  `qualityReview` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '质检点评',
  `improvedIdea` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '改进意见',
  `qualityWorker` int(11) NULL DEFAULT NULL COMMENT '录音评分人',
  `qualityTime` datetime NULL DEFAULT NULL COMMENT '评分时间',
  `qualityWorkerName` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `linkTime` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通话时长',
  `callType` smallint(2) NOT NULL DEFAULT 1 COMMENT '0:callout,1:callin',
  `companyId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '所属客户',
  PRIMARY KEY (`autoId`) USING BTREE,
  INDEX `idx_qualityTime`(`qualityTime`) USING BTREE,
  INDEX `idx_workerId`(`workerId`) USING BTREE,
  INDEX `idx_recordId`(`recordId`) USING BTREE,
  INDEX `deptId`(`deptId`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '录音质检表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of RecordQuality
-- ----------------------------
INSERT INTO `RecordQuality` VALUES (1, '19', 100313, '8888', '', NULL, 97, '来第一条评分', '暂时木有效率低下,', 8888, '2017-12-20 15:11:52', '超级管理员1', '', 0, '0');
INSERT INTO `RecordQuality` VALUES (2, '19', 100313, '8888', '', NULL, 97, '来第一条评分', '暂时木有效率低下,', 8888, '2017-12-20 15:12:27', '超级管理员1', '', 0, '0');
INSERT INTO `RecordQuality` VALUES (3, '18', 100314, '8888', '', NULL, 80, 'ghiufguyfuy', 'giugiug,', 8888, '2017-12-23 18:28:52', '超级管理员', '', 0, '0');
INSERT INTO `RecordQuality` VALUES (4, '18', 100314, '8888', '', NULL, 80, 'ghiufguyfuy', 'giugiug,', 8888, '2017-12-23 18:28:52', '超级管理员', '', 0, '0');

-- ----------------------------
-- Table structure for RecordQualityDetail
-- ----------------------------
DROP TABLE IF EXISTS `RecordQualityDetail`;
CREATE TABLE `RecordQualityDetail`  (
  `autoId` int(11) NOT NULL AUTO_INCREMENT,
  `recordId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '录音编号',
  `itemId` int(11) NULL DEFAULT NULL COMMENT '质检代码ID(评分标准细项)',
  `detailId` int(11) NULL DEFAULT NULL COMMENT '质检扣分明细ID(评分明细细项',
  `callType` smallint(2) NOT NULL DEFAULT 1 COMMENT '0:callout,1:callin',
  PRIMARY KEY (`autoId`) USING BTREE,
  INDEX `idx_recordId`(`recordId`) USING BTREE,
  INDEX `idx_itemId`(`itemId`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '录音评分明细' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of RecordQualityDetail
-- ----------------------------
INSERT INTO `RecordQualityDetail` VALUES (2, '19', 31, NULL, 0);
INSERT INTO `RecordQualityDetail` VALUES (3, '18', 1, NULL, 0);
INSERT INTO `RecordQualityDetail` VALUES (4, '18', 1, NULL, 0);
INSERT INTO `RecordQualityDetail` VALUES (5, '18', 2, NULL, 0);
INSERT INTO `RecordQualityDetail` VALUES (6, '18', 2, NULL, 0);

-- ----------------------------
-- Table structure for RecordScore
-- ----------------------------
DROP TABLE IF EXISTS `RecordScore`;
CREATE TABLE `RecordScore`  (
  `autoId` int(11) NOT NULL AUTO_INCREMENT,
  `detailName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `score` int(11) NULL DEFAULT NULL COMMENT '扣除分值',
  `createTime` datetime NULL DEFAULT NULL,
  `companyId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '所属客户',
  `display` tinyint(4) NULL DEFAULT 1 COMMENT '是(1)否(0)显示',
  PRIMARY KEY (`autoId`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '录音扣分项目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of RecordScore
-- ----------------------------
INSERT INTO `RecordScore` VALUES (1, '语气态度不好', 10, '2017-12-20 16:04:59', '0', 0);
INSERT INTO `RecordScore` VALUES (2, '业务不熟练', 88, '2017-12-20 16:05:13', '0', 0);
INSERT INTO `RecordScore` VALUES (41, '44', 19, '2018-01-09 19:10:03', '0', 1);
INSERT INTO `RecordScore` VALUES (40, 'eagv', 4, '2018-01-06 16:44:13', '0', 1);
INSERT INTO `RecordScore` VALUES (39, 'qq', 50, '2018-01-06 15:02:48', '0', 1);
INSERT INTO `RecordScore` VALUES (38, '测试', 25, '2018-01-06 15:02:32', '0', 1);
INSERT INTO `RecordScore` VALUES (37, '99', 2, '2018-01-06 14:58:16', '0', 0);
INSERT INTO `RecordScore` VALUES (36, '13', 2, '2018-01-06 14:53:35', '0', 1);
INSERT INTO `RecordScore` VALUES (35, '2', 12, '2018-01-06 14:52:19', '0', 0);
INSERT INTO `RecordScore` VALUES (34, '1', 10, '2018-01-06 14:49:42', '0', 0);
INSERT INTO `RecordScore` VALUES (33, '再加一个', 92, '2018-01-06 14:44:03', '0', 0);

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
-- Table structure for Subscribe
-- ----------------------------
DROP TABLE IF EXISTS `Subscribe`;
CREATE TABLE `Subscribe`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `workOrderId` int(11) NOT NULL DEFAULT 100 COMMENT '工单ID',
  `memberIds` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '客户ids',
  `workerIds` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '员工ids',
  `type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '1邮件2短信 可以1,2',
  `createTime` datetime NULL DEFAULT NULL,
  `isUse` tinyint(2) NULL DEFAULT NULL COMMENT '0删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sub_workOrderId`(`workOrderId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '通知订阅表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of Subscribe
-- ----------------------------
INSERT INTO `Subscribe` VALUES (8, 127, NULL, NULL, NULL, NULL, NULL);

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
INSERT INTO `SysDict` VALUES (131, '9265', '办公室 主任', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (132, '9967', '办公室 副主任', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (133, '6383', '办公室秘书处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (134, '4577', '办公室秘书处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (135, '7061', '办公室秘书处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (136, '8489', '办公室人事教育处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (137, '2937', '办公室人事教育处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (138, '2486', '办公室人事教育处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (139, '8661', '办公室财务处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (140, '7710', '办公室财务处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (141, '7981', '办公室财务处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (142, '5230', '办公室保卫保密处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (143, '4487', '办公室保卫保密处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (144, '2362', '办公室保卫保密处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (145, '8854', '办公室行政处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (146, '9037', '办公室行政处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (147, '5710', '办公室行政处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (148, '2684', '办公室离退休干部处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (149, '8237', '办公室离退休干部处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (150, '3132', '办公室基建办公室 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (151, '2824', '办公室基建办公室 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (152, '5479', '办公室设备管理处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (153, '8517', '办公室设备管理处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (154, '2053', '办公室博士后工作站 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (155, '6963', '办公室博士后工作站', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (156, '3453', '综合管理部 主任', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (157, '4445', '综合管理部 副主任', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (158, '7489', '综合管理部', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (159, '6716', '综合管理部综合处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (160, '6750', '综合管理部综合处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (161, '4379', '综合管理部综合处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (162, '9141', '综合管理部综合处 秘书', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (163, '5801', '综合管理部政研室 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (164, '8926', '综合管理部政研室 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (165, '8654', '综合管理部政研室', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (166, '4674', '综合管理部预算计划处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (167, '2775', '综合管理部预算计划处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (168, '1415', '综合管理部预算计划处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (169, '6939', '综合管理部资产及企业管理处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (170, '4924', '综合管理部资产及企业管理处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (171, '3204', '综合管理部资产及企业管理处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (172, '7227', '综合管理部科研管理处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (173, '6761', '综合管理部科研管理处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (174, '5580', '综合管理部科研管理处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (175, '2743', '综合管理部国际合作处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (176, '4099', '综合管理部国际合作处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (177, '9619', '综合管理部国际合作处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (178, '6213', '公共部 主  任', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (179, '3519', '公共部 副主任', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (180, '1683', '公共部综合处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (181, '9818', '公共部综合处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (182, '4449', '公共部综合处 秘书', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (183, '6968', '公共部内网运行管理处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (184, '7485', '公共部内网运行管理处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (185, '5352', '公共部内网运行管理处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (186, '7493', '公共部纵网运行管理处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (187, '1995', '公共部纵网运行管理处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (188, '1188', '公共部纵网运行管理处 　', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (189, '4104', '公共部纵网运行管理处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (190, '5252', '公共部平台应用管理处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (191, '9304', '公共部平台应用管理处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (192, '3452', '公共部平台应用管理处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (193, '3078', '公共部政务大厅管理处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (194, '1527', '公共部政务大厅管理处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (195, '2879', '公共部政务大厅服务处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (196, '8468', '公共部政务大厅服务处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (197, '7826', '公共部政务大厅服务处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (198, '8477', '公共部政务外网发展规划处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (199, '1823', '公共部政务外网发展规划处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (200, '3554', '公共部政务外网发展规划处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (201, '1869', '公共部政务外网运行管理处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (202, '1809', '公共部政务外网运行管理处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (203, '9361', '公共部政务外网运行管理处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (204, '1471', '公共部政务外网安全管理处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (205, '5490', '公共部政务外网安全管理处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (206, '5272', '公共部政务外网技术管理处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (207, '1255', '公共部政务外网技术管理处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (208, '4460', '公共部政务外网政务云管理处（政务云XX工程实验室） 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (209, '3901', '公共部政务外网公共应用推进处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (210, '6663', '公共部政务信息资源管理处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (211, '6312', '公共部政务信息资源管理处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (212, '7885', '公共部政务信息资源管理处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (213, '1663', '预测部 主任', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (214, '2628', '预测部 副主任', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (215, '8533', '预测部综合处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (216, '3639', '预测部综合处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (217, '5692', '预测部综合处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (218, '3935', '预测部综合处 秘书', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (219, '9297', '预测部宏观经济研究室 主任（正处级）', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (220, '8376', '预测部宏观经济研究室', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (221, '1395', '预测部政策仿真实验室 副主任（副处级）', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (222, '3682', '预测部政策仿真实验室', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (223, '7098', '预测部世界经济研究室 主任（正处级）', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (224, '8018', '预测部世界经济研究室 副主任（副处级）', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (225, '6099', '预测部世界经济研究室', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (226, '1235', '预测部财政金融研究室 主任（正处级）', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (227, '2878', '预测部财政金融研究室 副主任（副处级）', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (228, '9064', '预测部财政金融研究室', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (229, '9825', '预测部产业经济研究室 副主任（副处级）', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (230, '6731', '预测部产业经济研究室', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (231, '8408', '预测部新动能（创新创业）研究室 主任（正处级）', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (232, '2449', '预测部新动能（创新创业）研究室 副主任（副处级）', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (233, '5475', '预测部新动能（创新创业）研究室', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (234, '5284', '信产部 主任', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (235, '3189', '信产部 副主任', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (236, '3435', '信产部综合处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (237, '5208', '信产部综合处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (238, '2556', '信产部综合处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (239, '3276', '信产部综合处 秘书', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (240, '3407', '信产部信息化发展政策处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (241, '3352', '信产部信息化发展政策处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (242, '3575', '信产部信息化发展政策处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (243, '5458', '信产部分享经济处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (244, '7680', '信产部分享经济处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (245, '5922', '信产部分享经济处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (246, '5863', '信产部战略规划处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (247, '7500', '信产部战略规划处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (248, '6446', '信产部智慧城市处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (249, '9100', '信产部智慧城市处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (250, '7619', '信产部《年鉴》编辑部', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (251, '2346', '信产部制造业研究处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (252, '3537', '信产部制造业研究处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (253, '5996', '信产部制造业研究处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (254, '5691', '信产部新兴产业处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (255, '4821', '信产部新兴产业处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (256, '6876', '信产部新兴产业处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (257, '6820', '信产部汽车产业处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (258, '9420', '信产部汽车产业处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (259, '8278', '信产部汽车产业处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (260, '8332', '信产部汽车市场处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (261, '2854', '信产部汽车市场处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (262, '2491', '信产部汽车市场处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (263, '6787', '信产部产业调查处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (264, '7760', '信产部产业调查处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (265, '5589', '信产部产业调查处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (266, '6271', '信产部资源与数据处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (267, '8046', '信产部资源与数据处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (268, '7519', '信产部资源与数据处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (269, '6723', '大数据部 主任', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (270, '6119', '大数据部 副主任', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (271, '2658', '大数据部综合处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (272, '1937', '大数据部综合处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (273, '3152', '大数据部综合处 秘书', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (274, '5929', '大数据部大数据分析处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (275, '4632', '大数据部大数据分析处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (276, '7593', '大数据部大数据分析处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (277, '5832', '大数据部互联网信息分析处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (278, '9049', '大数据部互联网信息分析处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (279, '9839', '大数据部规划与应用处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (280, '1845', '大数据部规划与应用处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (281, '4093', '大数据部规划与应用处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (282, '6733', '大数据部数据管理与开放处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (283, '2022', '大数据部数据管理与开放处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (284, '9906', '大数据部数据管理与开放处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (285, '1077', '大数据部“一带一路”官网运行处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (286, '1904', '大数据部“一带一路”官网运行处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (287, '4405', '大数据部“一带一路”官网运行处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (288, '8578', '网安部 主任', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (289, '4700', '网安部 副主任', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (290, '4904', '网安部综合处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (291, '6245', '网安部综合处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (292, '1555', '网安部综合处 秘书', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (293, '7442', '网安部政务外网认证管理处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (294, '8170', '网安部政务外网认证管理处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (295, '3531', '网安部政务外网监测处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (296, '9606', '网安部政务外网监测处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (297, '9472', '网安部政务外网监测处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (298, '4808', '网安部办公信息化安全处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (299, '2921', '网安部办公信息化安全处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (300, '5200', '网安部办公信息化安全处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (301, '1685', '网安部信息安全\n评估处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (302, '8469', '网安部信息安全\n评估处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (303, '8383', '网安部信息安全\n评估处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (304, '6660', '网安部数据安全处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (305, '7636', '网安部数据安全处 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (306, '8787', '网安部数据安全处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (307, '8792', '网安部研究院 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (308, '2061', '网安部研究院', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (309, '7282', '网安部业务发展中心 副处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (310, '2889', '网安部业务发展中心', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (311, '5931', '网安部安委会', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (312, '3595', '党办 主任', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (313, '1057', '党办 副主任', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (314, '8484', '党办 组织委员(副处级)', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (315, '7518', '党办', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (316, '1798', '党办 宣传委员(正处级)', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (317, '4672', '党办 工会委员(正处级)', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (318, '1806', '党办 纪检委员(正处级)', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (319, '9691', '工程中心 副主任', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (320, '8358', '工程中心 主任助理', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (321, '7558', '工程中心 总工程师', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (322, '4564', '工程中心咨询评估处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (323, '8623', '工程中心咨询评估处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (324, '4542', '工程中心应用服务处 处长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (325, '3392', '工程中心应用服务处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (326, '2357', '工程中心工程技术处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (327, '7658', '工程中心工程技术处 秘书', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (328, '2735', '总公司 总经理', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (329, '7002', '总公司 副总经理', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (330, '5313', '总公司 副总工程师', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (331, '4637', '总公司 副总经济师', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (332, '9150', '总公司综合办公室', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (333, '6325', '中经网公司领导 董事长', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (334, '2123', '中经网公司领导 常务副总经理', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (335, '2890', '中经网公司领导 副总经理、总支书记', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (336, '9218', '中经网公司领导 副总经理', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (337, '2866', '中经网公司领导 技术总监', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (338, '1618', '中经网公司领导 总工程师', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (339, '3302', '中经网公司领导 总会计师', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (340, '2625', '中经网公司领导 销售总监', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (341, '8430', '中经网公司领导 总经理助理', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (342, '5505', '中经网办公室 办公室副主任', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (343, '1174', '中经网办公室 秘书', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (344, '9927', '中经网投资与资产管理部 部门经理', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (345, '1632', '中经网市场客服部 部门经理', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (346, '8652', '中经网软件公司 总经理', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (347, '8628', '物业公司 总经理', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (348, '3036', '物业公司 出纳', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (349, '4972', '中国信息协会', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (350, '7912', '中经网公司', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (351, '4983', '中经网公司 办公室', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (352, '8409', '中经网公司 网络运行部', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (353, '3987', '中经网公司 财经界', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (354, '6321', '中心领导 主任', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (355, '4186', '中心领导 副主任', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (356, '1804', '首席经济师', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (357, '2510', '首席信息师', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (358, '7268', '首席工程师', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (359, '8025', '公共部运行管理处', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (360, '92', '模拟话机', 'wohj', 0, 1, NULL, 4, 0);
INSERT INTO `SysDict` VALUES (361, '10', 'IP国际', 'wophone', 0, 1, NULL, 5, 0);
INSERT INTO `SysDict` VALUES (362, '46', 'IP国内', 'wophone', 0, 1, NULL, 5, 0);
INSERT INTO `SysDict` VALUES (363, '41', '市话', 'wophone', 0, 1, NULL, 5, 0);
INSERT INTO `SysDict` VALUES (364, '55', '内线', 'wophone', 0, 1, NULL, 5, 0);
INSERT INTO `SysDict` VALUES (365, '87', '代应答', 'wophonebussiness', 0, 1, NULL, 6, 0);
INSERT INTO `SysDict` VALUES (366, '71', '显示器不亮', 'wofault', 0, 1, NULL, 9, 0);
INSERT INTO `SysDict` VALUES (367, '69', '邮箱不能使用', 'wofault', 0, 1, NULL, 9, 0);
INSERT INTO `SysDict` VALUES (368, '36', '其他', 'wofault', 0, 1, NULL, 9, 0);
INSERT INTO `SysDict` VALUES (369, '5125', '测试啦啦啦', 'wodept', 15, 0, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (370, '1688', '你好呀', 'wodept', 15, 0, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (371, '1913', '国信新创', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (372, '74', 'test', 'wotype', 0, 0, NULL, 1, 0);
INSERT INTO `SysDict` VALUES (373, '9548', '复印机', 'womodel', 0, 1, NULL, 8, 0);
INSERT INTO `SysDict` VALUES (374, '1793', '传真机', 'womodel', 0, 1, NULL, 8, 0);
INSERT INTO `SysDict` VALUES (375, '5595', '扫描仪', 'womodel', 0, 1, NULL, 8, 0);
INSERT INTO `SysDict` VALUES (376, '3791', '碎纸机', 'womodel', 0, 1, NULL, 8, 0);
INSERT INTO `SysDict` VALUES (377, '8311', '投影仪', 'womodel', 0, 1, NULL, 8, 0);
INSERT INTO `SysDict` VALUES (378, '3766', '一体机', 'womodel', 0, 1, NULL, 8, 0);
INSERT INTO `SysDict` VALUES (379, '3319', '测试2组', 'wodept', 15, 1, NULL, 3, 0);
INSERT INTO `SysDict` VALUES (380, '3879', '测试3组', 'wodept', 15, 1, NULL, 3, 0);
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
-- Table structure for TaskOfCall
-- ----------------------------
DROP TABLE IF EXISTS `TaskOfCall`;
CREATE TABLE `TaskOfCall`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `taskNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务编号',
  `callId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '录音ID',
  `workerId` int(11) NULL DEFAULT NULL COMMENT '员工工号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 225 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '通话录音与任务编号关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of TaskOfCall
-- ----------------------------
INSERT INTO `TaskOfCall` VALUES (50, '0', '237', 8888);
INSERT INTO `TaskOfCall` VALUES (51, '0', '244', 1001);
INSERT INTO `TaskOfCall` VALUES (52, '0', '275', 112);

-- ----------------------------
-- Table structure for TaskOfCallAndSMS
-- ----------------------------
DROP TABLE IF EXISTS `TaskOfCallAndSMS`;
CREATE TABLE `TaskOfCallAndSMS`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memberNo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户编号',
  `taskNo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务编号',
  `messageType` smallint(2) NOT NULL COMMENT '消息类型:0电话,1短信',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `workerId` smallint(20) NULL DEFAULT NULL COMMENT '创建人',
  `companyId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属公司',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `tcs_createTime`(`createTime`) USING BTREE,
  INDEX `tcs_taskNo`(`taskNo`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '任务与通话记录和短信的关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for TaskProcHistory
-- ----------------------------
DROP TABLE IF EXISTS `TaskProcHistory`;
CREATE TABLE `TaskProcHistory`  (
  `procId` bigint(20) NOT NULL AUTO_INCREMENT,
  `taskNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务Id',
  `memberId` bigint(20) NULL DEFAULT NULL COMMENT '客户ID',
  `workerId` int(11) NOT NULL DEFAULT 0 COMMENT '工号',
  `workerName` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '员工姓名',
  `procTime` datetime  NULL DEFAULT NULL COMMENT '处理时间',
  `procIp` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理Ip',
  `isValidContact` smallint(2) NULL DEFAULT NULL COMMENT '接触是否成功(0否，1是)',
  `procItem` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '处理记录',
  `postscript` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '备注(如:相关任务记录)',
  `nextContactTime` datetime NULL DEFAULT NULL COMMENT '下次联系时间/下次跟进时间',
  `field8` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '可扰属性 1一般 ...',
  `field4` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '免扰时段',
  `field5` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '偏好沟通方式 多个以逗号分隔',
  `field6` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '偏好沟通时段1',
  `field7` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '偏好沟通时段2',
  `field9` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户标签,多个以英文逗号分隔',
  `field10` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `memberAttribute2` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '价值属性json数据',
  `workerDept` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所在组信息',
  `touchFrequency` smallint(2) NULL DEFAULT NULL COMMENT '接触频率',
  `attentions` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关注点',
  `clientType` tinyint(2) NULL DEFAULT NULL COMMENT '客户类别',
  `clear` tinyint(2) NULL DEFAULT NULL COMMENT '是否清洗成功,0:否,1:是',
  `validway` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接触方式',
  `newAppointTime` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预约类型,json数组',
  PRIMARY KEY (`procId`) USING BTREE,
  INDEX `taskId`(`taskNo`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '任务处理小记表' ROW_FORMAT = Dynamic;

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
INSERT INTO `Worker` VALUES (2, '1001', '1122', 'UARSAldQ3949ba59abbe56e057f20f88', NULL, 0, 0, 'test', '2018-11-16 15:23:24', '2018-12-27 09:52:19', '2018-12-27 09:52:36', 7003, 1, 0, NULL, NULL, NULL, 0, 13, 'gaohui', NULL, 1, NULL);
INSERT INTO `Worker` VALUES (13, '113', '123456', 'UARSAldQ3949ba59abbe56e057f20f88', NULL, 0, 1, 'test', '2018-11-26 09:53:24', '2018-12-05 18:30:19', NULL, 1005, 1, 0, NULL, NULL, NULL, 0, 13, NULL, NULL, 1, NULL);
INSERT INTO `Worker` VALUES (20, '8000', '第三方用户', 'UARSAldQ3949ba59abbe56e057f20f88', NULL, 0, 0, 'test', NULL, '2018-12-25 10:59:09', '2018-12-19 17:43:28', 8570, 1, 0, NULL, NULL, NULL, 0, 12, 'thrid', NULL, 1, '');
INSERT INTO `Worker` VALUES (21, '127', 'dabai', 'UARSAldQ3949ba59abbe56e057f20f88', NULL, 0, 0, NULL, '2018-12-27 09:53:03', NULL, '2018-12-27 13:35:25', NULL, 1, 0, NULL, NULL, NULL, 0, 12, 'dabaii', NULL, 1, NULL);

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

-- ----------------------------
-- Table structure for WorkerService
-- ----------------------------
DROP TABLE IF EXISTS `WorkerService`;
CREATE TABLE `WorkerService`  (
  `writeDate` date NOT NULL COMMENT '写入日期',
  `writeHour` smallint(2) NOT NULL DEFAULT -1 COMMENT '写入小时,-1为按天统计',
  `workerId` int(11) NOT NULL COMMENT '工号ID',
  `companyId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属客户',
  `firstLoginTime` datetime NULL DEFAULT NULL COMMENT '首次登录时间',
  `firstCallTime` datetime NULL DEFAULT NULL COMMENT '首次通话时间',
  `callInAllNum` int(11) NULL DEFAULT 0 COMMENT '呼入数(总),含呼入接通与未接通',
  `callInNum` int(11) NULL DEFAULT 0 COMMENT '呼入接通数',
  `callInClientAllNum` int(11) NULL DEFAULT 0 COMMENT '呼入客户数(总),含呼入接通与未接通',
  `callInClientNum` int(11) NULL DEFAULT 0 COMMENT '呼入客户数(已接通)',
  `callInTimeCount` float(18, 2) NULL DEFAULT 0.00 COMMENT '呼入总通话时长',
  `callOutAllNum` int(11) NULL DEFAULT 0 COMMENT '呼出数(总),含呼出接通与未接通',
  `callOutNum` int(11) NULL DEFAULT 0 COMMENT '呼出接通数',
  `callOutClientAllNum` int(11) NULL DEFAULT 0 COMMENT '呼出客户数(总),含呼出接通与未接通',
  `callOutClientNum` int(11) NULL DEFAULT 0 COMMENT '呼出客户数(已接通)',
  `callOutTimeCount` float(18, 2) NULL DEFAULT 0.00 COMMENT '呼出总通话时长',
  `callNum` int(11) NULL DEFAULT 0 COMMENT '通话总接通数,含呼入与呼出的接通数',
  `callAllNum` int(11) NULL DEFAULT 0 COMMENT '通话总数,含呼入与呼出的(接通与未接通)数',
  `callTimeCount` float(18, 2) NULL DEFAULT 0.00 COMMENT '总通话时长,含呼入与呼出',
  `callClientAllNum` int(11) NULL DEFAULT 0 COMMENT '总接通客户数,含呼入与呼出接通数',
  `intervalTime` int(11) NULL DEFAULT 0 COMMENT '累计间隔时长',
  `overdueContact` int(11) NULL DEFAULT 0,
  `orders` int(11) NULL DEFAULT 0,
  `ordersCallOutNum` int(11) NULL DEFAULT 0,
  `ordersMoney` float(10, 2) NULL DEFAULT 0.00,
  PRIMARY KEY (`writeDate`, `workerId`, `companyId`, `writeHour`) USING BTREE,
  INDEX `IX_WorkerService_WorkerId`(`workerId`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '员工电话汇总表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of WorkerService
-- ----------------------------
INSERT INTO `WorkerService` VALUES ('2018-12-03', 15, 8888, '0', NULL, NULL, 0, 0, 0, 0, 0.00, 1, 0, 1, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-03', -1, 8888, '0', NULL, NULL, 0, 0, 0, 0, 0.00, 4, 0, 1, 0, 0.00, 0, 4, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-03', 16, 8888, '0', NULL, NULL, 0, 0, 0, 0, 0.00, 3, 0, 1, 0, 0.00, 0, 3, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-03', 17, 0, '0', NULL, NULL, 3, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 3, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-03', -1, 0, '0', NULL, NULL, 3, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 3, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-04', 14, 0, '0', NULL, NULL, 1, 0, 0, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-04', -1, 0, '0', NULL, NULL, 3, 0, 0, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 3, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-04', 15, 0, '0', NULL, NULL, 2, 0, 0, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 2, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-04', 16, 8888, '0', NULL, NULL, 1, 1, 1, 1, 8.00, 1, 0, 1, 0, 0.00, 1, 2, 8.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-04', -1, 8888, '0', NULL, NULL, 4, 4, 3, 3, 67.00, 1, 0, 1, 0, 0.00, 4, 5, 67.00, 3, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-04', 17, 8888, '0', NULL, '2018-12-04 17:07:13', 1, 1, 1, 1, 47.00, 0, 0, 0, 0, 0.00, 1, 1, 47.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-05', 10, 8888, '0', NULL, '2018-12-05 10:47:01', 1, 1, 1, 1, 32.00, 0, 0, 0, 0, 0.00, 1, 1, 32.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-05', -1, 8888, '0', NULL, '2018-12-05 10:47:01', 29, 19, 1, 1, 353.00, 2, 0, 2, 0, 0.00, 19, 31, 353.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-05', 13, 8888, '0', NULL, '2018-12-05 13:40:50', 6, 2, 1, 1, 19.00, 0, 0, 0, 0, 0.00, 2, 6, 19.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-05', 13, 0, '0', NULL, NULL, 4, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 4, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-05', -1, 0, '0', NULL, NULL, 11, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 11, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-05', 14, 8888, '0', NULL, '2018-12-05 14:02:56', 19, 13, 1, 1, 261.00, 0, 0, 0, 0, 0.00, 13, 19, 261.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-05', 14, 0, '0', NULL, NULL, 7, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 7, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-05', 15, 8888, '0', NULL, NULL, 0, 0, 0, 0, 0.00, 1, 0, 1, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-05', 16, 8888, '0', NULL, '2018-12-05 16:01:19', 1, 1, 1, 1, 7.00, 0, 0, 0, 0, 0.00, 1, 1, 7.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-05', 17, 8888, '0', NULL, '2018-12-05 17:13:49', 2, 2, 1, 1, 34.00, 0, 0, 0, 0, 0.00, 2, 2, 34.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-05', 19, 8888, '0', NULL, NULL, 0, 0, 0, 0, 0.00, 1, 0, 1, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-15', 20, 8888, '0', NULL, NULL, 2, 0, 2, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 2, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-15', -1, 8888, '0', NULL, NULL, 3, 0, 3, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 3, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-16', 10, 1001, '0', NULL, NULL, 2, 0, 2, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 2, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-16', -1, 1001, '0', NULL, NULL, 2, 0, 2, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 2, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-20', 15, 112, '0', NULL, NULL, 2, 0, 2, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 2, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-20', -1, 112, '0', NULL, NULL, 2, 0, 2, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 2, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-27', 10, 1001, '0', NULL, '2018-11-27 10:06:27', 2, 2, 2, 2, 14.00, 0, 0, 0, 0, 0.00, 2, 2, 14.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-27', -1, 1001, '0', NULL, '2018-11-27 10:06:27', 2, 2, 2, 2, 14.00, 0, 0, 0, 0, 0.00, 2, 2, 14.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-04', 15, 1001, '0', NULL, NULL, 4, 0, 2, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 4, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-04', -1, 1001, '0', NULL, NULL, 4, 0, 2, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 4, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-04', 15, 8888, '0', NULL, '2018-12-04 15:48:04', 2, 2, 2, 2, 12.00, 0, 0, 0, 0, 0.00, 2, 2, 12.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-06', 9, 1001, '0', NULL, NULL, 6, 0, 4, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 6, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-06', -1, 1001, '0', NULL, NULL, 8, 0, 4, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 8, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-06', 22, 1001, '0', NULL, NULL, 2, 0, 2, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 2, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-10', 8, 1001, '0', NULL, NULL, 4, 0, 2, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 4, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-10', -1, 1001, '0', NULL, NULL, 8, 4, 4, 2, 32.00, 0, 0, 0, 0, 0.00, 4, 8, 32.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-10', 8, 8888, '0', NULL, '2018-12-10 08:19:51', 0, 0, 0, 0, 0.00, 2, 2, 2, 2, 8.00, 2, 2, 8.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-10', 9, 1001, '0', NULL, '2018-12-10 09:59:57', 2, 2, 2, 2, 12.00, 0, 0, 0, 0, 0.00, 2, 2, 12.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-10', 10, 122, '0', NULL, '2018-12-10 10:13:13', 0, 0, 0, 0, 0.00, 4, 4, 2, 2, 82.00, 4, 4, 82.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-10', 14, 1001, '0', NULL, '2018-12-10 14:30:29', 2, 2, 2, 2, 20.00, 0, 0, 0, 0, 0.00, 2, 2, 20.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-11', 14, 1001, '0', NULL, '2018-12-11 14:38:45', 2, 2, 2, 2, 90.00, 0, 0, 0, 0, 0.00, 2, 2, 90.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-11', -1, 1001, '0', NULL, '2018-12-11 14:38:45', 2, 2, 2, 2, 90.00, 0, 0, 0, 0, 0.00, 2, 2, 90.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-12', 10, 112, '0', NULL, NULL, 2, 0, 2, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 2, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-12', -1, 112, '0', NULL, NULL, 2, 0, 2, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 2, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-12', 14, 1001, '0', NULL, NULL, 6, 0, 4, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 6, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-12', -1, 1001, '0', NULL, NULL, 6, 0, 4, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 6, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-13', 8, 122, '0', NULL, '2018-12-13 08:02:34', 0, 0, 0, 0, 0.00, 2, 2, 2, 2, 44.00, 2, 2, 44.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-13', 9, 1001, '0', NULL, '2018-12-13 09:32:57', 2, 2, 2, 2, 118.00, 0, 0, 0, 0, 0.00, 2, 2, 118.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-13', -1, 1001, '0', NULL, '2018-12-13 09:32:57', 7, 5, 3, 2, 118.00, 0, 0, 0, 0, 0.00, 5, 7, 118.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-13', 11, 112, '0', NULL, NULL, 4, 0, 4, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 4, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-13', -1, 112, '0', NULL, NULL, 6, 2, 4, 0, 0.00, 0, 0, 0, 0, 0.00, 2, 6, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-13', 14, 122, '0', NULL, '2018-12-13 14:26:33', 0, 0, 0, 0, 0.00, 2, 2, 2, 2, 78.00, 2, 2, 78.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-13', 17, 122, '0', NULL, '2018-12-13 17:02:31', 0, 0, 0, 0, 0.00, 2, 2, 2, 2, 32.00, 2, 2, 32.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-08', 10, 1001, '0', NULL, '2018-11-08 10:27:15', 5, 2, 3, 1, 21.00, 0, 0, 0, 0, 0.00, 2, 5, 21.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-08', -1, 1001, '0', NULL, '2018-11-08 10:27:15', 13, 3, 4, 2, 52.00, 0, 0, 0, 0, 0.00, 3, 13, 52.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-08', 10, 0, '0', NULL, NULL, 3, 0, 2, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 3, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-08', -1, 0, '0', NULL, NULL, 17, 0, 3, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 17, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-08', 11, 1001, '0', NULL, NULL, 7, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 7, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-08', 11, 0, '0', NULL, NULL, 12, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 12, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-08', 11, 122, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-08', -1, 122, '0', NULL, NULL, 2, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 2, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-08', 16, 0, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-08', 16, 1001, '0', NULL, '2018-11-08 16:54:28', 1, 1, 1, 1, 31.00, 0, 0, 0, 0, 0.00, 1, 1, 31.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-08', 22, 122, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-08', 22, 0, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-09', 6, 122, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-09', -1, 122, '0', NULL, NULL, 4, 3, 2, 1, 17.00, 0, 0, 0, 0, 0.00, 3, 4, 17.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-09', 6, 0, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-09', -1, 0, '0', NULL, NULL, 9, 0, 4, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 9, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-09', 10, 122, '0', NULL, '2018-11-09 10:12:42', 3, 3, 2, 2, 17.00, 0, 0, 0, 0, 0.00, 3, 3, 17.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-09', 10, 1001, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-09', -1, 1001, '0', NULL, NULL, 4, 1, 3, 0, 11.00, 0, 0, 0, 0, 0.00, 1, 4, 11.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-09', 10, 0, '0', NULL, NULL, 6, 0, 3, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 6, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-09', 10, 8888, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-09', -1, 8888, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-09', 10, 112, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-09', -1, 112, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-09', 14, 1001, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-09', 14, 0, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-09', 15, 1001, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-09', 15, 0, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-09', 16, 1001, '0', NULL, '2018-11-09 16:16:45', 1, 1, 1, 1, 11.00, 0, 0, 0, 0, 0.00, 1, 1, 11.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-12', 10, 1001, '0', NULL, NULL, 3, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 3, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-12', -1, 1001, '0', NULL, NULL, 3, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 3, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-12', 10, 0, '0', NULL, NULL, 3, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 3, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-12', -1, 0, '0', NULL, NULL, 3, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 3, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-13', 10, 1001, '0', NULL, '2018-11-13 10:05:00', 2, 1, 2, 1, 1.00, 0, 0, 0, 0, 0.00, 1, 2, 1.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-13', -1, 1001, '0', NULL, '2018-11-13 10:05:00', 2, 1, 2, 1, 1.00, 0, 0, 0, 0, 0.00, 1, 2, 1.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-14', 11, 1001, '0', NULL, '2018-11-14 11:33:33', 1, 1, 1, 1, 16.00, 0, 0, 0, 0, 0.00, 1, 1, 16.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-14', -1, 1001, '0', NULL, '2018-11-14 11:33:33', 2, 1, 1, 1, 16.00, 0, 0, 0, 0, 0.00, 1, 2, 16.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-14', 15, 8888, '0', NULL, '2018-11-14 15:08:57', 1, 1, 1, 1, 10.00, 0, 0, 0, 0, 0.00, 1, 1, 10.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-14', -1, 8888, '0', NULL, '2018-11-14 15:08:57', 1, 1, 1, 1, 10.00, 0, 0, 0, 0, 0.00, 1, 1, 10.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-14', 16, 1001, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-15', 10, 8888, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-15', 10, 1001, '0', NULL, '2018-11-15 10:27:13', 2, 2, 2, 2, 8.00, 0, 0, 0, 0, 0.00, 2, 2, 8.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-15', -1, 1001, '0', NULL, '2018-11-15 10:27:13', 4, 3, 3, 3, 19.00, 0, 0, 0, 0, 0.00, 3, 4, 19.00, 3, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-15', 13, 1001, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-11-15', 14, 1001, '0', NULL, '2018-11-15 14:12:08', 1, 1, 1, 1, 11.00, 0, 0, 0, 0, 0.00, 1, 1, 11.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-13', 17, 1001, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-13', 18, 1001, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-13', 19, 1001, '0', NULL, '2018-12-13 19:16:52', 3, 3, 1, 1, 0.00, 0, 0, 0, 0, 0.00, 3, 3, 0.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-13', 19, 112, '0', NULL, '2018-12-13 19:26:51', 2, 2, 1, 1, 0.00, 0, 0, 0, 0, 0.00, 2, 2, 0.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-14', 7, 122, '0', NULL, '2018-12-14 07:37:41', 0, 0, 0, 0, 0.00, 2, 2, 1, 1, 28.00, 2, 2, 28.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-14', 8, 1001, '0', NULL, NULL, 3, 2, 3, 2, 35.00, 0, 0, 0, 0, 0.00, 2, 3, 35.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-14', -1, 1001, '0', NULL, NULL, 28, 13, 15, 7, 102.00, 0, 0, 0, 0, 0.00, 13, 28, 102.00, 7, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-14', 9, 122, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-14', -1, 122, '0', NULL, NULL, 15, 14, 3, 2, 230.00, 0, 0, 0, 0, 0.00, 14, 15, 230.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-14', 10, 1001, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-14', 13, 1001, '0', NULL, NULL, 2, 0, 2, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 2, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-14', 13, 122, '0', NULL, '2018-12-14 13:29:14', 1, 1, 1, 1, 0.00, 0, 0, 0, 0, 0.00, 1, 1, 0.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-14', 14, 1001, '0', NULL, NULL, 5, 3, 5, 3, 35.00, 0, 0, 0, 0, 0.00, 3, 5, 35.00, 3, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-14', 14, 122, '0', NULL, '2018-12-14 14:16:46', 4, 4, 1, 1, 58.00, 0, 0, 0, 0, 0.00, 4, 4, 58.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-14', 14, 112, '0', NULL, '2018-12-14 14:17:19', 7, 5, 3, 1, 40.00, 0, 0, 0, 0, 0.00, 5, 7, 40.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-14', -1, 112, '0', NULL, '2018-12-14 14:17:19', 8, 6, 3, 1, 47.00, 0, 0, 0, 0, 0.00, 6, 8, 47.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-14', 14, 8888, '0', NULL, '2018-12-14 14:35:57', 2, 2, 2, 2, 14.00, 0, 0, 0, 0, 0.00, 2, 2, 14.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-14', -1, 8888, '0', NULL, '2018-12-14 14:35:57', 5, 5, 4, 4, 32.00, 0, 0, 0, 0, 0.00, 5, 5, 32.00, 4, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-14', 15, 122, '0', NULL, '2018-12-14 15:10:52', 7, 7, 2, 2, 168.00, 0, 0, 0, 0, 0.00, 7, 7, 168.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-14', 15, 1001, '0', NULL, NULL, 11, 6, 5, 3, 25.00, 0, 0, 0, 0, 0.00, 6, 11, 25.00, 3, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-14', 15, 8888, '0', NULL, '2018-12-14 15:46:25', 3, 3, 2, 2, 18.00, 0, 0, 0, 0, 0.00, 3, 3, 18.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-14', 16, 1001, '0', NULL, NULL, 4, 2, 1, 0, 7.00, 0, 0, 0, 0, 0.00, 2, 4, 7.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-14', 16, 122, '0', NULL, '2018-12-14 16:51:50', 1, 1, 1, 1, 4.00, 1, 1, 1, 1, 17.00, 2, 2, 21.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-14', 16, 112, '0', NULL, '2018-12-14 16:59:56', 1, 1, 1, 1, 7.00, 0, 0, 0, 0, 0.00, 1, 1, 7.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-17', 8, 1001, '0', NULL, NULL, 2, 1, 2, 1, 36.00, 0, 0, 0, 0, 0.00, 1, 2, 36.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-16', -1, 1001, '0', NULL, NULL, 2, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 2, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-16', 9, 1001, '0', NULL, NULL, 2, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 2, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-14', 18, 1001, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-17', -1, 1001, '0', NULL, NULL, 19, 12, 13, 9, 285.00, 0, 0, 0, 0, 0.00, 12, 19, 285.00, 9, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-17', 8, 122, '0', NULL, '2018-12-17 08:47:06', 1, 1, 1, 1, 4.00, 0, 0, 0, 0, 0.00, 1, 1, 4.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-17', -1, 122, '0', NULL, '2018-12-17 08:47:06', 8, 8, 7, 7, 119.00, 2, 2, 2, 2, 17.00, 10, 10, 136.00, 9, -17, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-17', 9, 1001, '0', NULL, NULL, 4, 1, 4, 1, 3.00, 0, 0, 0, 0, 0.00, 1, 4, 3.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-17', 9, 122, '0', NULL, '2018-12-17 09:32:49', 1, 1, 1, 1, 24.00, 2, 2, 2, 2, 17.00, 3, 3, 41.00, 3, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-17', 10, 1001, '0', NULL, '2018-12-17 10:20:46', 5, 5, 5, 5, 181.00, 0, 0, 0, 0, 0.00, 5, 5, 181.00, 5, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-17', 10, 122, '0', NULL, '2018-12-17 10:41:38', 1, 1, 1, 1, 0.00, 0, 0, 0, 0, 0.00, 1, 1, 0.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-17', 11, 1001, '0', NULL, NULL, 2, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 2, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-17', 11, 112, '0', NULL, '2018-12-17 11:24:07', 0, 0, 0, 0, 0.00, 1, 1, 1, 1, 2.00, 1, 1, 2.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-17', -1, 112, '0', NULL, '2018-12-17 11:24:07', 2, 2, 2, 2, 238.00, 1, 1, 1, 1, 2.00, 3, 3, 240.00, 3, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-17', 13, 122, '0', NULL, '2018-12-17 13:43:18', 1, 1, 1, 1, 12.00, 0, 0, 0, 0, 0.00, 1, 1, 12.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-17', 14, 1001, '0', NULL, '2018-12-17 14:42:25', 3, 3, 3, 3, 27.00, 0, 0, 0, 0, 0.00, 3, 3, 27.00, 3, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-17', 15, 122, '0', NULL, '2018-12-17 15:09:09', 3, 3, 3, 3, 51.00, 0, 0, 0, 0, 0.00, 3, 3, 51.00, 3, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-17', 15, 112, '0', NULL, '2018-12-17 15:10:48', 1, 1, 1, 1, 238.00, 0, 0, 0, 0, 0.00, 1, 1, 238.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-17', 15, 1001, '0', NULL, '2018-12-17 15:19:34', 2, 1, 2, 1, 0.00, 0, 0, 0, 0, 0.00, 1, 2, 0.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-17', 16, 1001, '0', NULL, '2018-12-17 16:20:49', 1, 1, 1, 1, 38.00, 0, 0, 0, 0, 0.00, 1, 1, 38.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-17', 16, 122, '0', NULL, '2018-12-17 16:51:12', 1, 1, 1, 1, 28.00, 0, 0, 0, 0, 0.00, 1, 1, 28.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-17', 17, 112, '0', NULL, '2018-12-17 17:28:59', 1, 1, 1, 1, 0.00, 0, 0, 0, 0, 0.00, 1, 1, 0.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-17', 17, 0, '0', NULL, '2018-12-17 17:46:33', 0, 0, 0, 0, 0.00, 1, 1, 1, 1, 125.00, 1, 1, 125.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-17', -1, 0, '0', NULL, '2018-12-17 17:46:33', 0, 0, 0, 0, 0.00, 1, 1, 1, 1, 125.00, 1, 1, 125.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-18', 8, 1001, '0', NULL, '2018-12-18 08:52:47', 1, 1, 1, 1, 9.00, 0, 0, 0, 0, 0.00, 1, 1, 9.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-18', -1, 1001, '0', NULL, '2018-12-18 08:52:47', 82, 67, 10, 8, 1189.00, 0, 0, 0, 0, 0.00, 67, 82, 1189.00, 8, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-18', 9, 1001, '0', NULL, '2018-12-18 09:04:04', 2, 2, 1, 1, 128.00, 0, 0, 0, 0, 0.00, 2, 2, 128.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-18', 10, 1001, '0', NULL, '2018-12-18 10:01:04', 2, 2, 2, 2, 30.00, 0, 0, 0, 0, 0.00, 2, 2, 30.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-18', 10, 112, '0', NULL, '2018-12-18 10:38:48', 2, 2, 2, 2, 0.00, 0, 0, 0, 0, 0.00, 2, 2, 0.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-18', -1, 112, '0', NULL, '2018-12-18 10:38:48', 5, 5, 4, 4, 61.00, 0, 0, 0, 0, 0.00, 5, 5, 61.00, 4, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-18', 10, 122, '0', NULL, '2018-12-18 10:48:30', 1, 1, 1, 1, 0.00, 0, 0, 0, 0, 0.00, 1, 1, 0.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-18', -1, 122, '0', NULL, '2018-12-18 10:48:30', 26, 26, 6, 6, 1006.00, 0, 0, 0, 0, 0.00, 26, 26, 1006.00, 6, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-18', 11, 1001, '0', NULL, '2018-12-18 11:11:35', 1, 1, 1, 1, 0.00, 0, 0, 0, 0, 0.00, 1, 1, 0.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-18', 13, 122, '0', NULL, '2018-12-18 13:37:26', 1, 1, 1, 1, 0.00, 0, 0, 0, 0, 0.00, 1, 1, 0.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-18', 13, 112, '0', NULL, '2018-12-18 13:47:53', 1, 1, 1, 1, 11.00, 0, 0, 0, 0, 0.00, 1, 1, 11.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-18', 14, 1001, '0', NULL, '2018-12-18 14:11:05', 3, 3, 3, 3, 77.00, 0, 0, 0, 0, 0.00, 3, 3, 77.00, 3, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-18', 14, 122, '0', NULL, '2018-12-18 14:49:16', 1, 1, 1, 1, 0.00, 0, 0, 0, 0, 0.00, 1, 1, 0.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-18', 15, 122, '0', NULL, '2018-12-18 15:03:17', 7, 7, 3, 3, 409.00, 0, 0, 0, 0, 0.00, 7, 7, 409.00, 3, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-18', 15, 112, '0', NULL, '2018-12-18 15:07:38', 1, 1, 1, 1, 50.00, 0, 0, 0, 0, 0.00, 1, 1, 50.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-18', 16, 1001, '0', NULL, '2018-12-18 16:34:48', 14, 14, 2, 2, 614.00, 0, 0, 0, 0, 0.00, 14, 14, 614.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-18', 17, 1001, '0', NULL, NULL, 30, 27, 2, 1, 298.00, 0, 0, 0, 0, 0.00, 27, 30, 298.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-18', 17, 8888, '0', NULL, '2018-12-18 17:36:02', 1, 1, 1, 1, 16.00, 0, 0, 0, 0, 0.00, 1, 1, 16.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-18', -1, 8888, '0', NULL, '2018-12-18 17:36:02', 1, 1, 1, 1, 16.00, 0, 0, 0, 0, 0.00, 1, 1, 16.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-18', 18, 1001, '0', NULL, NULL, 17, 8, 2, 1, 0.00, 0, 0, 0, 0, 0.00, 8, 17, 0.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-18', 18, 112, '0', NULL, '2018-12-18 18:59:21', 1, 1, 1, 1, 0.00, 0, 0, 0, 0, 0.00, 1, 1, 0.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-18', 19, 1001, '0', NULL, '2018-12-18 19:11:56', 2, 1, 1, 1, 0.00, 0, 0, 0, 0, 0.00, 1, 2, 0.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-19', 9, 122, '0', NULL, '2018-12-19 09:30:19', 1, 1, 1, 1, 0.00, 0, 0, 0, 0, 0.00, 1, 1, 0.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-19', -1, 122, '0', NULL, '2018-12-19 09:30:19', 17, 17, 9, 9, 377.00, 0, 0, 0, 0, 0.00, 17, 17, 377.00, 9, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-19', 9, 1001, '0', NULL, '2018-12-19 09:43:23', 4, 3, 3, 3, 40.00, 0, 0, 0, 0, 0.00, 3, 4, 40.00, 3, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-19', -1, 1001, '0', NULL, '2018-12-19 09:43:23', 54, 12, 10, 10, 250.00, 0, 0, 0, 0, 0.00, 12, 54, 250.00, 10, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-19', 10, 122, '0', NULL, '2018-12-19 10:06:35', 6, 6, 4, 4, 25.00, 0, 0, 0, 0, 0.00, 6, 6, 25.00, 4, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-19', 10, 1001, '0', NULL, '2018-12-19 10:10:21', 8, 5, 5, 5, 104.00, 0, 0, 0, 0, 0.00, 5, 8, 104.00, 5, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-19', 10, 0, '0', NULL, '2018-12-19 10:48:33', 0, 0, 0, 0, 0.00, 1, 1, 1, 1, 26.00, 1, 1, 26.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-19', -1, 0, '0', NULL, '2018-12-19 10:48:33', 0, 0, 0, 0, 0.00, 1, 1, 1, 1, 26.00, 1, 1, 26.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-19', 11, 1001, '0', NULL, '2018-12-19 11:00:18', 7, 1, 2, 1, 0.00, 0, 0, 0, 0, 0.00, 1, 7, 0.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-19', 14, 1001, '0', NULL, '2018-12-19 14:00:20', 11, 2, 3, 2, 106.00, 0, 0, 0, 0, 0.00, 2, 11, 106.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-19', 14, 112, '0', NULL, '2018-12-19 14:19:00', 2, 2, 2, 2, 83.00, 0, 0, 0, 0, 0.00, 2, 2, 83.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-19', -1, 112, '0', NULL, '2018-12-19 14:19:00', 4, 4, 4, 4, 111.00, 3, 3, 3, 3, 6.00, 7, 7, 117.00, 7, -6, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-19', 14, 122, '0', NULL, '2018-12-19 14:27:29', 2, 2, 2, 2, 260.00, 0, 0, 0, 0, 0.00, 2, 2, 260.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-19', 15, 112, '0', NULL, '2018-12-19 15:06:51', 2, 2, 2, 2, 28.00, 3, 3, 3, 3, 6.00, 5, 5, 34.00, 5, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-19', 15, 122, '0', NULL, '2018-12-19 15:19:35', 2, 2, 2, 2, 13.00, 0, 0, 0, 0, 0.00, 2, 2, 13.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-19', 16, 122, '0', NULL, '2018-12-19 16:40:41', 4, 4, 2, 2, 79.00, 0, 0, 0, 0, 0.00, 4, 4, 79.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-19', 17, 122, '0', NULL, '2018-12-19 17:39:48', 2, 2, 1, 1, 0.00, 0, 0, 0, 0, 0.00, 2, 2, 0.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-20', 8, 122, '0', NULL, '2018-12-20 08:12:32', 1, 1, 1, 1, 145.00, 0, 0, 0, 0, 0.00, 1, 1, 145.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-20', -1, 122, '0', NULL, '2018-12-20 08:12:32', 9, 9, 7, 7, 288.00, 0, 0, 0, 0, 0.00, 9, 9, 288.00, 7, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-20', 8, 1001, '0', NULL, '2018-12-20 08:52:17', 1, 1, 1, 1, 24.00, 0, 0, 0, 0, 0.00, 1, 1, 24.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-20', -1, 1001, '0', NULL, '2018-12-20 08:52:17', 8, 8, 7, 7, 204.00, 0, 0, 0, 0, 0.00, 8, 8, 204.00, 7, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-20', 9, 122, '0', NULL, '2018-12-20 09:03:16', 2, 2, 2, 2, 81.00, 0, 0, 0, 0, 0.00, 2, 2, 81.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-20', 9, 0, '0', NULL, '2018-12-20 09:57:43', 0, 0, 0, 0, 0.00, 1, 1, 1, 1, 309.00, 1, 1, 309.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-20', -1, 0, '0', NULL, '2018-12-20 09:57:43', 0, 0, 0, 0, 0.00, 1, 1, 1, 1, 309.00, 1, 1, 309.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-20', 10, 122, '0', NULL, '2018-12-20 10:04:01', 4, 4, 4, 4, 37.00, 0, 0, 0, 0, 0.00, 4, 4, 37.00, 4, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-20', 10, 1001, '0', NULL, '2018-12-20 10:04:04', 2, 2, 2, 2, 52.00, 0, 0, 0, 0, 0.00, 2, 2, 52.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-20', 11, 1001, '0', NULL, '2018-12-20 11:21:48', 1, 1, 1, 1, 26.00, 0, 0, 0, 0, 0.00, 1, 1, 26.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-20', 13, 1001, '0', NULL, '2018-12-20 13:49:27', 1, 1, 1, 1, 18.00, 0, 0, 0, 0, 0.00, 1, 1, 18.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-20', 14, 1001, '0', NULL, '2018-12-20 14:21:27', 1, 1, 1, 1, 6.00, 0, 0, 0, 0, 0.00, 1, 1, 6.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-20', 14, 122, '0', NULL, '2018-12-20 14:22:05', 2, 2, 2, 2, 25.00, 0, 0, 0, 0, 0.00, 2, 2, 25.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-20', 14, 112, '0', NULL, '2018-12-20 14:49:26', 1, 1, 1, 1, 96.00, 0, 0, 0, 0, 0.00, 1, 1, 96.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-20', -1, 112, '0', NULL, '2018-12-20 14:49:26', 1, 1, 1, 1, 96.00, 0, 0, 0, 0, 0.00, 1, 1, 96.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-20', 15, 1001, '0', NULL, '2018-12-20 15:06:50', 2, 2, 2, 2, 78.00, 0, 0, 0, 0, 0.00, 2, 2, 78.00, 2, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-20', 15, 8888, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-20', -1, 8888, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-18', 15, 1001, '0', NULL, '2018-12-18 15:23:26', 10, 8, 1, 1, 33.00, 0, 0, 0, 0, 0.00, 8, 10, 33.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-18', 16, 122, '0', NULL, '2018-12-18 16:16:45', 3, 3, 1, 1, 454.00, 0, 0, 0, 0, 0.00, 3, 3, 454.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-18', 17, 122, '0', NULL, '2018-12-18 17:06:26', 13, 13, 1, 1, 143.00, 0, 0, 0, 0, 0.00, 13, 13, 143.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-19', 13, 1001, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-19', 15, 1001, '0', NULL, NULL, 20, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 20, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-19', 16, 1001, '0', NULL, NULL, 3, 1, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 1, 3, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-24', 16, 1001, '0', NULL, '2018-12-24 16:22:49', 2, 2, 1, 1, 37.00, 0, 0, 0, 0, 0.00, 2, 2, 37.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-24', -1, 1001, '0', NULL, '2018-12-24 16:22:49', 2, 2, 1, 1, 37.00, 0, 0, 0, 0, 0.00, 2, 2, 37.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-25', 10, 8000, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-25', -1, 8000, '0', NULL, NULL, 1, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 1, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-25', 10, 1001, '0', NULL, '2018-12-25 10:15:04', 1, 1, 1, 1, 44.00, 0, 0, 0, 0, 0.00, 1, 1, 44.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-25', -1, 1001, '0', NULL, '2018-12-25 10:15:04', 1, 1, 1, 1, 44.00, 0, 0, 0, 0, 0.00, 1, 1, 44.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-26', 14, 0, '0', NULL, NULL, 2, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 2, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-26', -1, 0, '0', NULL, NULL, 37, 21, 1, 0, 672.00, 0, 0, 0, 0, 0.00, 21, 37, 672.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-26', 15, 0, '0', NULL, '2018-12-26 15:14:17', 5, 1, 1, 1, 32.00, 0, 0, 0, 0, 0.00, 1, 5, 32.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-26', 16, 0, '0', NULL, '2018-12-26 16:05:49', 11, 4, 1, 1, 128.00, 0, 0, 0, 0, 0.00, 4, 11, 128.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-26', 17, 0, '0', NULL, '2018-12-26 17:07:01', 17, 14, 1, 1, 448.00, 0, 0, 0, 0, 0.00, 14, 17, 448.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-26', 18, 0, '0', NULL, '2018-12-26 18:00:14', 2, 2, 1, 1, 64.00, 0, 0, 0, 0, 0.00, 2, 2, 64.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-27', 9, 0, '0', NULL, '2018-12-27 09:42:57', 5, 3, 1, 1, 96.00, 0, 0, 0, 0, 0.00, 3, 5, 96.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-27', -1, 0, '0', NULL, '2018-12-27 09:42:57', 50, 31, 1, 1, 994.00, 0, 0, 0, 0, 0.00, 31, 50, 994.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-27', 10, 0, '0', NULL, '2018-12-27 10:00:20', 20, 9, 1, 1, 289.00, 0, 0, 0, 0, 0.00, 9, 20, 289.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-27', 11, 8888, '0', NULL, NULL, 3, 1, 1, 0, 7.00, 0, 0, 0, 0, 0.00, 1, 3, 7.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-27', -1, 8888, '0', NULL, NULL, 3, 1, 1, 0, 7.00, 0, 0, 0, 0, 0.00, 1, 3, 7.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-27', 12, 112, '0', NULL, NULL, 2, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 2, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-27', -1, 112, '0', NULL, NULL, 2, 0, 1, 0, 0.00, 0, 0, 0, 0, 0.00, 0, 2, 0.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-27', 16, 0, '0', NULL, NULL, 14, 10, 1, 0, 320.00, 0, 0, 0, 0, 0.00, 10, 14, 320.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-27', 17, 0, '0', NULL, '2018-12-27 17:02:45', 9, 7, 1, 1, 224.00, 0, 0, 0, 0, 0.00, 7, 9, 224.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-27', 18, 0, '0', NULL, '2018-12-27 18:01:56', 2, 2, 1, 1, 65.00, 0, 0, 0, 0, 0.00, 2, 2, 65.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-28', 13, 0, '0', NULL, '2018-12-28 13:46:34', 8, 8, 1, 1, 256.00, 0, 0, 0, 0, 0.00, 8, 8, 256.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-28', -1, 0, '0', NULL, '2018-12-28 13:46:34', 54, 36, 1, 1, 1978.00, 0, 0, 0, 0, 0.00, 36, 54, 1978.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-28', 14, 0, '0', NULL, '2018-12-28 14:25:12', 6, 3, 1, 1, 70.00, 0, 0, 0, 0, 0.00, 3, 6, 70.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-28', 15, 0, '0', NULL, '2018-12-28 15:18:20', 12, 11, 1, 1, 1399.00, 0, 0, 0, 0, 0.00, 11, 12, 1399.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2018-12-28', 16, 0, '0', NULL, '2018-12-28 16:04:58', 28, 14, 1, 1, 253.00, 0, 0, 0, 0, 0.00, 14, 28, 253.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2019-01-02', 18, 0, '0', NULL, '2019-01-02 18:53:33', 1, 1, 1, 1, 39.00, 0, 0, 0, 0, 0.00, 1, 1, 39.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2019-01-02', -1, 0, '0', NULL, '2019-01-02 18:53:33', 7, 5, 1, 1, 183.00, 0, 0, 0, 0, 0.00, 5, 7, 183.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2019-01-02', 19, 0, '0', NULL, NULL, 6, 4, 1, 0, 144.00, 0, 0, 0, 0, 0.00, 4, 6, 144.00, 0, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2019-01-03', 10, 0, '0', NULL, '2019-01-03 10:50:19', 6, 1, 1, 1, 74.00, 0, 0, 0, 0, 0.00, 1, 6, 74.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2019-01-03', -1, 0, '0', NULL, '2019-01-03 10:50:19', 7, 2, 1, 1, 106.00, 0, 0, 0, 0, 0.00, 2, 7, 106.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2019-01-03', 11, 0, '0', NULL, '2019-01-03 11:02:34', 1, 1, 1, 1, 32.00, 0, 0, 0, 0, 0.00, 1, 1, 32.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2019-01-03', 11, 8000, '0', NULL, '2019-01-03 11:06:41', 1, 1, 1, 1, 22.00, 0, 0, 0, 0, 0.00, 1, 1, 22.00, 1, 0, 0, 0, 0, 0.00);
INSERT INTO `WorkerService` VALUES ('2019-01-03', -1, 8000, '0', NULL, '2019-01-03 11:06:41', 1, 1, 1, 1, 22.00, 0, 0, 0, 0, 0.00, 1, 1, 22.00, 1, 0, 0, 0, 0, 0.00);

-- ----------------------------
-- Table structure for WorkOrder
-- ----------------------------
DROP TABLE IF EXISTS `WorkOrder`;
CREATE TABLE `WorkOrder`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '--',
  `no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工单编号',
  `type` int(2) NULL DEFAULT NULL COMMENT '工单类型 字典',
  `status` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工单状态 字典或中文',
  `dbusinesstype` int(2) NULL DEFAULT NULL COMMENT '业务类型-字典',
  `ddept` int(2) NULL DEFAULT NULL COMMENT '部门-字典',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `dhjtype` int(2) NULL DEFAULT NULL COMMENT '话机类型',
  `contact` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `contactPhone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人电话',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子邮件',
  `installAddress` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '装机地址',
  `phonePermission` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话权限，隔开 字典',
  `phoneBusiness` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话业务，隔开 字典',
  `settleMoney` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算金额/工单金额',
  `monthRent` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '月租',
  `dispatchName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '派单人员',
  `dispatchTime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '派单时间',
  `dispatchRemake` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '派单备注',
  `installMoney` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '装机费用',
  `relocationAddress` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '移机地址',
  `relocationMoney` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '移机费用',
  `faultMessage` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '故障信息',
  `faultMoney` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '故障费用',
  `withdrawAddress` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '撤机地址',
  `withdrawMoney` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '撤机费用',
  `itModel` int(2) NULL DEFAULT NULL COMMENT '设备型号-字典',
  `itFaulePositioning` int(2) NULL DEFAULT NULL COMMENT '故障初步定位-字典',
  `itClass` int(2) NULL DEFAULT NULL COMMENT '所属类别-字典',
  `itRoom` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户房间',
  `workerId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `isUse` tinyint(1) NULL DEFAULT 1 COMMENT '0删除',
  `memberId` int(11) NOT NULL COMMENT '客户id',
  `dhjpro` int(2) NULL DEFAULT NULL COMMENT '话机处理-字典',
  `acceptWorker` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '受理人员',
  `jw` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机务人员',
  `xw` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '线务人员',
  `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `createTime` datetime  NULL DEFAULT NULL COMMENT '创建时间',
  `faultPhone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '故障号码',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `wd_no_index`(`no`) USING BTREE,
  INDEX `wd_type_index`(`type`) USING BTREE,
  INDEX `wd_status_index`(`status`) USING BTREE,
  INDEX `wd_create_index`(`updateTime`) USING BTREE,
  INDEX `wd_dispatchName_index`(`dispatchName`) USING BTREE,
  INDEX `wd_acceptWorker_index`(`acceptWorker`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '工单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of WorkOrder
-- ----------------------------
INSERT INTO `WorkOrder` VALUES (1, 'GD20190102100837354', 65, '5', 93, NULL, NULL, NULL, 97, NULL, NULL, NULL, '北京', '10', '19', '100', '100', '8888', '2019-01-02 10:09:11', NULL, '100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '8888', 1, 568, NULL, NULL, NULL, NULL, '2019-01-02 10:08:37', '2019-01-02 10:08:37', NULL);
INSERT INTO `WorkOrder` VALUES (2, 'GD20190102112737410', 37, '5', 42, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '', '0', NULL, '8888', '2019-01-02 11:28:17', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 67, 35, 9, NULL, '8888', 1, 568, NULL, NULL, NULL, NULL, '2019-01-02 11:27:37', '2019-01-02 11:27:37', NULL);
INSERT INTO `WorkOrder` VALUES (3, 'GD20190102120217425', 65, '5', 93, NULL, NULL, NULL, 97, NULL, NULL, NULL, '北京', '61,29', '35', '100', '101', '112', '', NULL, '100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '8888', 1, 568, NULL, NULL, NULL, NULL, '2019-01-02 12:02:17', '2019-01-02 12:02:17', NULL);
-- ----------------------------
-- Triggers structure for table member_mid
-- ----------------------------
DROP TRIGGER IF EXISTS `trigger_mm_i`;
delimiter ;;
CREATE TRIGGER `trigger_mm_i` AFTER INSERT ON `member_mid` FOR EACH ROW BEGIN
DECLARE f_ int(1); -- 判断是否已存在
set  f_=(select ifnull((select count(1) from MemberBaseInfo m where m.field4=NEW.memberNo),0));
if (f_>0) then
update MemberBaseInfo set memberName=new.name,mdeptName=NEW.organId,mobile=NEW.phone,field15=NEW.lineNum,field6=NEW.isUse where `no`=NEW.memberNo;
else
insert MemberBaseInfo (memberName,mdeptName,mobile,field15,field6,memberType) values
(NEW.name,NEW.organId,NEW.phone,NEW.lineNum,NEW.isUse,'系统客户');
end if;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table member_mid
-- ----------------------------
DROP TRIGGER IF EXISTS `trigger_mm_u`;
delimiter ;;
CREATE TRIGGER `trigger_mm_u` AFTER UPDATE ON `member_mid` FOR EACH ROW BEGIN
DECLARE f_ int(1); -- 判断是否已存在
set  f_=(select ifnull((select count(1) from MemberBaseInfo m where m.field4=NEW.memberNo),0));
if (f_>0) then
update MemberBaseInfo set memberName=new.name,mdeptName=NEW.organId,mobile=NEW.phone,field15=NEW.lineNum,isUse=NEW.isUse where `no`=NEW.memberNo;
else
insert MemberBaseInfo (memberName,mdeptName,mobile,field15,field6,memberType) values
(NEW.name,NEW.organId,NEW.phone,NEW.lineNum,NEW.isUse,'系统客户');
end if;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table member_mid
-- ----------------------------
DROP TRIGGER IF EXISTS `tigger_mm_d`;
delimiter ;;
CREATE TRIGGER `tigger_mm_d` AFTER DELETE ON `member_mid` FOR EACH ROW BEGIN
update MemberBaseInfo set isUse=0 where field4=old.memberNo;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table organ_mid
-- ----------------------------
DROP TRIGGER IF EXISTS `trigger_om_i`;
delimiter ;;
CREATE TRIGGER `trigger_om_i` AFTER INSERT ON `organ_mid` FOR EACH ROW BEGIN
DECLARE f_ int(1); -- 判断是否已存在
set  f_=(select ifnull((select count(1) from SysDict m where m.type='wodept' and m.value=NEW.organId),0));
if (f_>0) then
update SysDict set label=new.name,remark=new.organParentId  where `value`=NEW.organId and m.type='wodept';
else
insert SysDict (type,value,remark,label,sort,parent) values
('wodept',new.organId,new.organParentId,new.name,15,3);
end if;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table organ_mid
-- ----------------------------
DROP TRIGGER IF EXISTS `trigger_om_u`;
delimiter ;;
CREATE TRIGGER `trigger_om_u` AFTER UPDATE ON `organ_mid` FOR EACH ROW BEGIN
DECLARE f_ int(1); -- 判断是否已存在
set  f_=(select ifnull((select count(1) from SysDict m where m.type='wodept' and m.value=NEW.organId),0));
if (f_>0) then
update SysDict set label=new.name,remark=new.organParentId  where `value`=NEW.organId and m.type='wodept';
else
insert SysDict (type,value,remark,label,sort,parent) values
('wodept',new.organId,new.organParentId,new.name,15,3);
end if;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table organ_mid
-- ----------------------------
DROP TRIGGER IF EXISTS `trigger_om_d`;
delimiter ;;
CREATE TRIGGER `trigger_om_d` AFTER DELETE ON `organ_mid` FOR EACH ROW BEGIN
update SysDict set isUse=0  where `value`=old.organId and type='wodept';
END
;;
delimiter ;

