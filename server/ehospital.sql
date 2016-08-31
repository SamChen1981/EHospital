/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50710
Source Host           : localhost:3306
Source Database       : ehospital

Target Server Type    : MYSQL
Target Server Version : 50710
File Encoding         : 65001

Date: 2016-03-18 20:32:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `case_table`
-- ----------------------------
DROP TABLE IF EXISTS `case_table`;
CREATE TABLE `case_table` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `userId` int(20) DEFAULT NULL,
  `create_time` varchar(20) DEFAULT NULL,
  `change_time` varchar(20) DEFAULT NULL,
  `content` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of case_table
-- ----------------------------
INSERT INTO `case_table` VALUES ('3', '3', '2016-03-10 14:17:25', '2016-03-18 19:47:10', '新增病例测试二\n\n李超    2016-03-14 11:14:48\n\n李超    2016-03-14 11:17:06\n新增病例测试三\n\n李超    2016-03-14 11:17:18\n新增病例测试四\n\n主治医师： 李超    就诊时间： 2016-03-14 11:22:06\n新增病例测试五\n\n主治医师： 李超    科室： 妇产科    就诊时间： 2016-03-14 11:24:08\n添加科室新增病例测试六\n\n主治医师： 李超    科室： 妇产科    就诊时间： 2016-03-14 11:37:33\n新增病例七测试\n\n主治医师： 李超    科室： 妇产科    就诊时间： 2016-03-14 11:38:24\n新增病例测试八\n\n主治医师： 李超    科室： 妇产科    就诊时间： 2016-03-14 11:39:16\n新增病例测试九\n\n主治医师： 李超    科室： 妇产科    就诊时间： 2016-03-14 11:40:04\n新增病例测试十\n\n主治医师： 李超    科室： 妇产科    就诊时间： 2016-03-14 16:49:36\n新增病例测试\n\n主治医师： 李超    科室： 妇产科    就诊时间： 2016-03-16 13:40:37\n牙疼\r主治医师： 李超    科室： 妇产科    就诊时间： 2016-03-18 19:34:04\r换行测试 换行测试 换行测试\n换行测试\n主治医师： 李超    科室： 妇产科    就诊时间： 2016-03-18 19:41:44\n换行测试换行测试\n换行测试\n主治医师： 李超    科室： 妇产科    就诊时间： 2016-03-18 19:44:28\n 测试测试\n换行测试/hh主治医师： 李超    科室： 妇产科    就诊时间： 2016-03-18 19:47:10/hh测试测试 /hh换行测试/hh');
INSERT INTO `case_table` VALUES ('4', '4', '2016-03-10 14:17:25', '2016-03-14 16:49:36', '新增病例测试二\n\n李超    2016-03-14 11:14:48\n\n李超    2016-03-14 11:17:06\n新增病例测试三\n\n李超    2016-03-14 11:17:18\n新增病例测试四\n\n主治医师： 李超    就诊时间： 2016-03-14 11:22:06\n新增病例测试五\n\n主治医师： 李超    科室： 妇产科    就诊时间： 2016-03-14 11:24:08\n添加科室新增病例测试六\n\n主治医师： 李超    科室： 妇产科    就诊时间： 2016-03-14 11:37:33\n新增病例七测试\n\n主治医师： 李超    科室： 妇产科    就诊时间： 2016-03-14 11:38:24\n新增病例测试八\n\n主治医师： 李超    科室： 妇产科    就诊时间： 2016-03-14 11:39:16\n新增病例测试九\n\n主治医师： 李超    科室： 妇产科    就诊时间： 2016-03-14 11:40:04\n新增病例测试十\n\n主治医师： 李超    科室： 妇产科    就诊时间： 2016-03-14 16:49:36\n新增病例测试');
INSERT INTO `case_table` VALUES ('5', '5', '2016-03-10 14:17:25', '2016-03-18 19:49:43', '新增病例测试二\n\n李超    2016-03-14 11:14:48\n\n李超    2016-03-14 11:17:06\n新增病例测试三\n\n李超    2016-03-14 11:17:18\n新增病例测试四\n\n主治医师： 李超    就诊时间： 2016-03-14 11:22:06\n新增病例测试五\n\n主治医师： 李超    科室： 妇产科    就诊时间： 2016-03-14 11:24:08\n添加科室新增病例测试六\n\n主治医师： 李超    科室： 妇产科    就诊时间： 2016-03-14 11:37:33\n新增病例七测试\n\n主治医师： 李超    科室： 妇产科    就诊时间： 2016-03-14 11:38:24\n新增病例测试八\n\n主治医师： 李超    科室： 妇产科    就诊时间： 2016-03-14 11:39:16\n新增病例测试九\n\n主治医师： 李超    科室： 妇产科    就诊时间： 2016-03-14 11:40:04\n新增病例测试十\n\n主治医师： 李超    科室： 妇产科    就诊时间： 2016-03-14 16:49:36\n新增病例测试\n\n主治医师： 李超    科室： 妇产科    就诊时间： 2016-03-14 16:53:44\ntiajiaceshi/hh主治医师： 李超    科室： 妇产科    就诊时间： 2016-03-18 19:49:43/hhceshiceshiceshiceshi');

-- ----------------------------
-- Table structure for `doctor`
-- ----------------------------
DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `phone` varchar(11) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `department` varchar(20) DEFAULT NULL,
  `head_img` varchar(20) DEFAULT NULL,
  `work_time` varchar(20) DEFAULT NULL,
  `register_num` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of doctor
-- ----------------------------
INSERT INTO `doctor` VALUES ('1', '15506580795', '123', '李超', '男', '妇产科', 'lichao.jpg', '周五上午', '1');
INSERT INTO `doctor` VALUES ('2', '17862827313', '123', '郭春鹏', '男', '外科', 'guo.jpg', '周一上午', '0');
INSERT INTO `doctor` VALUES ('3', '13256386963', '123', '宫晓蕊', '女', '内科', 'gong.jpg', '周一下午', '0');

-- ----------------------------
-- Table structure for `register_table`
-- ----------------------------
DROP TABLE IF EXISTS `register_table`;
CREATE TABLE `register_table` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `doctor_id` varchar(20) DEFAULT NULL,
  `user_id` varchar(20) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `time` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of register_table
-- ----------------------------
INSERT INTO `register_table` VALUES ('5', '1', '3', 'finish', '2016-03-10 14:17:36');
INSERT INTO `register_table` VALUES ('6', '1', '3', 'finish', '2016-03-10 14:17:36');
INSERT INTO `register_table` VALUES ('7', '1', '5', 'finish', '2016-03-10 14:17:36');
INSERT INTO `register_table` VALUES ('8', '1', '3', 'finish', '2016-03-16 13:39:22');
INSERT INTO `register_table` VALUES ('9', '2', '3', 'finish', '2016-03-10 14:17:36');
INSERT INTO `register_table` VALUES ('10', '1', '3', 'finish', '2016-03-18 19:33:21');
INSERT INTO `register_table` VALUES ('11', '1', '3', 'finish', '2016-03-18 19:40:17');
INSERT INTO `register_table` VALUES ('12', '1', '3', 'finish', '2016-03-18 19:41:06');
INSERT INTO `register_table` VALUES ('13', '1', '3', 'finish', '2016-03-18 19:42:03');
INSERT INTO `register_table` VALUES ('14', '1', '3', 'finish', '2016-03-18 19:43:53');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `phone` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `sex` varchar(20) DEFAULT NULL,
  `age` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('3', '17862827313', '123', '郭春鹏', '男', '18');
INSERT INTO `user` VALUES ('4', '13280928213', '123', '郭春鹏2', '男', '18');
INSERT INTO `user` VALUES ('5', '165688049', '123', '郭春鹏3', '男', '18');
