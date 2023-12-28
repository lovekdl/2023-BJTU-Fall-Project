/*
 Navicat Premium Data Transfer

 Source Server         : liqisdatabase
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : localhost:3306
 Source Schema         : peer_learning_system

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 22/12/2023 03:07:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for assignment
-- ----------------------------
DROP TABLE IF EXISTS `assignment`;
CREATE TABLE `assignment`  (
  `uuid` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `assignmentID` int NOT NULL,
  `courseUUID` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `content` varchar(20000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `filePath` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `deadline` datetime NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `excellent` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `answer` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`uuid`) USING BTREE,
  UNIQUE INDEX `assignmentID`(`assignmentID` ASC) USING BTREE,
  INDEX `assignment_ibfk_1`(`courseUUID` ASC) USING BTREE,
  CONSTRAINT `assignment_ibfk_1` FOREIGN KEY (`courseUUID`) REFERENCES `course` (`uuid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of assignment
-- ----------------------------
INSERT INTO `assignment` VALUES ('0b1e38d3-3940-443c-9fc9-69fd5d72d8e6', 500010, '9d89912a-0712-461a-80c6-d3de51c21061', 'test assignment', 'test assignment for allocation', NULL, '2023-11-18 00:00:00', '互评结束', '600007', 'assignmentAnswer');
INSERT INTO `assignment` VALUES ('1efcdc8a-2aa7-48b0-8eeb-c64818871066', 500015, '4e56f09f-6463-4f24-9f56-2e21cd69994c', '实训课作业3', 'sbsbsbsb', NULL, '2023-12-22 02:56:51', '未开始互评', NULL, 'biaozhundaan');
INSERT INTO `assignment` VALUES ('339dc4f3-7ba7-4b22-9dac-fb8b35ee7175', 500004, 'd9d0f812-1366-406f-91af-2dc84fa18f45', '作业4', '3333333', '', '2023-11-18 00:00:00', '互评结束', '600002', NULL);
INSERT INTO `assignment` VALUES ('547e05fd-9e70-4a12-88bd-ac3d8296c0f4', 500012, 'd9d0f812-1366-406f-91af-2dc84fa18f45', '实验2', '实验作业', NULL, '2021-12-10 00:08:00', '互评中', '600014', '这是标准答案');
INSERT INTO `assignment` VALUES ('83b27f0b-eb4c-4623-b5ab-1d1ed8392b9a', 500008, 'd9d0f812-1366-406f-91af-2dc84fa18f45', 'assignment5', '123456', '', '2023-11-15 12:05:05', '互评中', '600004', '阿米诺斯');
INSERT INTO `assignment` VALUES ('8f3f4afe-5672-43b2-aecb-b393999b9050', 500009, 'd9d0f812-1366-406f-91af-2dc84fa18f45', '作业2', 'qwe', '', '2021-12-02 00:00:01', '互评中', NULL, '123');
INSERT INTO `assignment` VALUES ('c4479155-420c-475b-8eca-b848e6fec0e8', 500016, '4e56f09f-6463-4f24-9f56-2e21cd69994c', '实训课作业4', '123123123123123', NULL, '2023-12-22 03:01:26', '互评结束', NULL, '哈哈哈哈哈哈哈哈哈哈哈');
INSERT INTO `assignment` VALUES ('c968b998-681c-4dda-a501-dbb44f01a324', 500014, '4e56f09f-6463-4f24-9f56-2e21cd69994c', '实训课作业2', '实训课作业2的sb内容', NULL, '2023-12-22 02:40:01', '互评结束', NULL, '\n上课对于学生来说是获取知识和发展技能的重要途径。在课堂上，学生有机会直接与老师互动，提问疑惑，深入理解学科内容。同时，上课也促使学生培养自律和团队协作的能力，通过合作项目和讨论活动提高沟通技巧。课堂上的互动和实践使得抽象的理论更具体，更易于理解和记忆。此外，上课也为学生提供了建立人际关系和分享经验的机会，培养了团队协作和社交技能。总体而言，上课不仅仅是获取知识的手段，更是全面发展学生个人和社会能力的关键路径。');
INSERT INTO `assignment` VALUES ('d0431695-3cb2-44f7-b1f1-55cf1f930ba2', 500013, '4e56f09f-6463-4f24-9f56-2e21cd69994c', '作业1', '这是作业1的内容', NULL, '2024-02-02 00:00:00', '互评结束', '600016', '标准答案是:1+1=3!');
INSERT INTO `assignment` VALUES ('d065a165-067c-417d-9169-73653b754085', 500005, 'd9d0f812-1366-406f-91af-2dc84fa18f45', 'assignment4', 'haha', '', '2023-11-15 12:05:05', '未开始互评', NULL, '123123');
INSERT INTO `assignment` VALUES ('eee02b07-e7ff-4353-add5-113749f05386', 500011, 'd9d0f812-1366-406f-91af-2dc84fa18f45', '实验1', '实验描述', NULL, '2021-12-02 00:00:00', '未开始互评', NULL, NULL);

-- ----------------------------
-- Table structure for counter
-- ----------------------------
DROP TABLE IF EXISTS `counter`;
CREATE TABLE `counter`  (
  `filedName` int NOT NULL,
  `uid` int NOT NULL,
  PRIMARY KEY (`filedName`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of counter
-- ----------------------------
INSERT INTO `counter` VALUES (1, 100003);
INSERT INTO `counter` VALUES (2, 200013);
INSERT INTO `counter` VALUES (3, 300031);
INSERT INTO `counter` VALUES (4, 400013);
INSERT INTO `counter` VALUES (5, 500017);
INSERT INTO `counter` VALUES (6, 600021);
INSERT INTO `counter` VALUES (7, 700068);

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `uuid` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `courseID` int NOT NULL,
  `userUUID` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `courseName` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `intro` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `number` int(10) UNSIGNED ZEROFILL NOT NULL,
  PRIMARY KEY (`uuid`) USING BTREE,
  UNIQUE INDEX `courseID`(`courseID` ASC) USING BTREE,
  INDEX `userUUID`(`userUUID` ASC) USING BTREE,
  CONSTRAINT `course_ibfk_1` FOREIGN KEY (`userUUID`) REFERENCES `user` (`uuid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('4e56f09f-6463-4f24-9f56-2e21cd69994c', 400012, '0c9dc230-cea3-44b2-a811-910528fc53e3', '可恶的实训课', '可恶的实训课！！！', 0000000002);
INSERT INTO `course` VALUES ('79e7fbcd-9ced-48af-a9b0-efae180d6d29', 400010, '00ab9208-70a1-4c55-9c74-987076f8d6ec', '课程实训', '课程实训', 0000000000);
INSERT INTO `course` VALUES ('87dcf58c-1d9c-4555-b5cb-4f7b47589634', 400011, '00ab9208-70a1-4c55-9c74-987076f8d6ec', '物理', '123', 0000000000);
INSERT INTO `course` VALUES ('9d89912a-0712-461a-80c6-d3de51c21061', 400009, '0c9dc230-cea3-44b2-a811-910528fc53e3', 'testCourse1', 'test course for allocation', 0000000003);
INSERT INTO `course` VALUES ('d9d0f812-1366-406f-91af-2dc84fa18f45', 400002, '0c9dc230-cea3-44b2-a811-910528fc53e3', '计算机网络', '123', 0000000004);

-- ----------------------------
-- Table structure for homework
-- ----------------------------
DROP TABLE IF EXISTS `homework`;
CREATE TABLE `homework`  (
  `uuid` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `homeworkID` int NOT NULL,
  `assignmentUUID` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `userUUID` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `score` int NULL DEFAULT NULL,
  `submitTime` datetime NULL DEFAULT NULL,
  `content` varchar(20000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `filePath` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `argument` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`uuid`, `assignmentUUID`, `userUUID`) USING BTREE,
  INDEX `homework_ibfk_1`(`assignmentUUID` ASC) USING BTREE,
  INDEX `userUUID`(`userUUID` ASC) USING BTREE,
  CONSTRAINT `homework_ibfk_1` FOREIGN KEY (`assignmentUUID`) REFERENCES `assignment` (`uuid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `homework_ibfk_2` FOREIGN KEY (`userUUID`) REFERENCES `user` (`uuid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of homework
-- ----------------------------
INSERT INTO `homework` VALUES ('0cf782a8-e7e0-463f-b0bd-3515272d315e', 600020, 'c4479155-420c-475b-8eca-b848e6fec0e8', '1bb616e5-a78c-4058-814b-926e4430b8b1', 100, '2023-12-22 03:00:52', '<p><span style=\'color: rgb(55, 65, 81); font-family: Söhne, ui-sans-serif, system-ui, -apple-system, \"Segoe UI\", Roboto, Ubuntu, Cantarell, \"Noto Sans\", sans-serif, \"Helvetica Neue\", Arial, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\", \"Noto Color Emoji\"; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: pre-wrap; text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\' id=\"isPasted\">上课对于学生来说是获取知识和发展技能的重要途径。在课堂上，学生有机会直接与老师互动，提问疑惑，深入理解学科内容。同时，上课也促使学生培养自律和团队协作的能力，通过合作项目和讨论活动提高沟通技巧。课堂上的互动和实践使得抽象的理论更具体，更易于理解和记忆。此外，上课也为学生提供了建立人际关系和分享经验的机会，培养了团队协作和社交技能。总体而言，上课不仅仅是获取知识的手段，更是全面发展学生个人和社会能力的关键路径。</span></p>', NULL, '已处理');
INSERT INTO `homework` VALUES ('0d735119-e72f-494a-9328-1ab42f794c8b', 600004, '83b27f0b-eb4c-4623-b5ab-1d1ed8392b9a', '163fb59f-2ffc-4ab8-ac98-44c8fe99803b', NULL, '2023-11-17 13:10:00', 'yxh和drj的本子', NULL, NULL);
INSERT INTO `homework` VALUES ('19a67ede-4510-48c5-9bcd-5efeb0d5910e', 600019, 'c4479155-420c-475b-8eca-b848e6fec0e8', '053088f3-9ced-47f4-b1f6-09e00cf651b0', 60, '2023-12-22 03:00:34', '<p><span style=\'color: rgb(55, 65, 81); font-family: Söhne, ui-sans-serif, system-ui, -apple-system, \"Segoe UI\", Roboto, Ubuntu, Cantarell, \"Noto Sans\", sans-serif, \"Helvetica Neue\", Arial, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\", \"Noto Color Emoji\"; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: pre-wrap; text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\' id=\"isPasted\">上课对于学生来说是获取知识和发展技能的重要途径。在课堂上，学生有机会直接与老师互动，提问疑惑，深入理解学科内容。同时，上课也促使学生培养自律和团队协作的能力，通过合作项目和讨论活动提高沟通技巧。课堂上的互动和实践使得抽象的理论更具体，更易于理解和记忆。此外，上课也为学生提供了建立人际关系和分享经验的机会，培养了团队协作和社交技能。总体而言，上课不仅仅是获取知识的手段，更是全面发展学生个人和社会能力的关键路径。</span></p>', NULL, NULL);
INSERT INTO `homework` VALUES ('32eab4bc-5e0e-4a8e-ab6f-a4046df48044', 600013, '339dc4f3-7ba7-4b22-9dac-fb8b35ee7175', '053088f3-9ced-47f4-b1f6-09e00cf651b0', 20, '2023-12-08 02:55:29', '<p>12312213123123123123123123123123123123123123123123123123123</p>', NULL, '已处理');
INSERT INTO `homework` VALUES ('3b5c7019-b726-46ac-97f5-cb7404017643', 600002, '339dc4f3-7ba7-4b22-9dac-fb8b35ee7175', '163fb59f-2ffc-4ab8-ac98-44c8fe99803b', 60, '2023-11-17 13:10:00', 'yxh的本子', NULL, '已处理');
INSERT INTO `homework` VALUES ('51fbf2ad-7756-4731-89a8-9a1905d11a26', 600014, '547e05fd-9e70-4a12-88bd-ac3d8296c0f4', '053088f3-9ced-47f4-b1f6-09e00cf651b0', NULL, '2023-12-08 03:04:50', '<p>123123</p>', NULL, NULL);
INSERT INTO `homework` VALUES ('57ce92c6-9eb1-4798-ad22-3c1d787d902d', 600005, '8f3f4afe-5672-43b2-aecb-b393999b9050', '163fb59f-2ffc-4ab8-ac98-44c8fe99803b', 90, '2023-11-17 13:10:00', 'yxh和drj和guuuu的本子', NULL, NULL);
INSERT INTO `homework` VALUES ('655c075e-df18-4e53-9e0f-fecc639b43ef', 600016, 'd0431695-3cb2-44f7-b1f1-55cf1f930ba2', '1bb616e5-a78c-4058-814b-926e4430b8b1', 60, '2023-12-22 01:44:36', '<p><br id=\"isPasted\"><span style=\'color: rgb(55, 65, 81); font-family: Söhne, ui-sans-serif, system-ui, -apple-system, \"Segoe UI\", Roboto, Ubuntu, Cantarell, \"Noto Sans\", sans-serif, \"Helvetica Neue\", Arial, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\", \"Noto Color Emoji\"; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: pre-wrap; text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\'>睡觉对于人体健康至关重要。睡眠是身体和大脑恢复的关键时期，有助于维持生理平衡和促进身体各系统的正常运作。在睡眠中，身体进行修复和细胞再生，有助于强化免疫系统，减轻疲劳和恢复能量。此外，睡眠对于认知功能和学习能力的提升至关重要，有助于巩固记忆和加强学习效果。良好的睡眠还与情绪稳定和心理健康密切相关，有助于缓解压力和焦虑。因此，充足的睡眠不仅有助于身体健康，还对提高生活质量和整体幸福感有积极影响。维持规律的睡眠模式是保持身体和心理健康的基石。</span></p>', NULL, NULL);
INSERT INTO `homework` VALUES ('690f82f9-9eb5-41f2-b09a-8898e42329af', 600011, '8f3f4afe-5672-43b2-aecb-b393999b9050', '1bb616e5-a78c-4058-814b-926e4430b8b1', NULL, '2023-11-24 13:30:28', '<p>哈哈<em>哈<strong>哈哈</strong></em></p>', NULL, NULL);
INSERT INTO `homework` VALUES ('7e510688-6ba9-4391-ba75-6cb2649a6690', 600007, '0b1e38d3-3940-443c-9fc9-69fd5d72d8e6', '053088f3-9ced-47f4-b1f6-09e00cf651b0', 60, '2023-11-17 13:10:00', 'liqi的作业', NULL, NULL);
INSERT INTO `homework` VALUES ('946207a7-0037-4c13-b1df-9d30639f8a6a', 600006, '0b1e38d3-3940-443c-9fc9-69fd5d72d8e6', '1bb616e5-a78c-4058-814b-926e4430b8b1', 60, '2023-11-24 13:29:08', '<p>这是作业内容</p>', NULL, NULL);
INSERT INTO `homework` VALUES ('9478edda-f417-4993-93a3-30bb2a266a0e', 600018, 'c968b998-681c-4dda-a501-dbb44f01a324', '1bb616e5-a78c-4058-814b-926e4430b8b1', 60, '2023-12-22 02:39:31', '<p><span style=\'color: rgb(55, 65, 81); font-family: Söhne, ui-sans-serif, system-ui, -apple-system, \"Segoe UI\", Roboto, Ubuntu, Cantarell, \"Noto Sans\", sans-serif, \"Helvetica Neue\", Arial, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\", \"Noto Color Emoji\"; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: pre-wrap; text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\' id=\"isPasted\">上课对于学生来说是获取知识和发展技能的重要途径。在课堂上，学生有机会直接与老师互动，提问疑惑，深入理解学科内容。同时，上课也促使学生培养自律和团队协作的能力，通过合作项目和讨论活动提高沟通技巧。课堂上的互动和实践使得抽象的理论更具体，更易于理解和记忆。此外，上课也为学生提供了建立人际关系和分享经验的机会，培养了团队协作和社交技能。总体而言，上课不仅仅是获取知识的手段，更是全面发展学生个人和社会能力的关键路径。</span></p>', NULL, NULL);
INSERT INTO `homework` VALUES ('a5a8cbb6-4e13-4dbc-8b9d-a70d59809a1d', 600012, '547e05fd-9e70-4a12-88bd-ac3d8296c0f4', '1bb616e5-a78c-4058-814b-926e4430b8b1', NULL, '2023-11-24 14:20:04', '<p>作业</p>', NULL, NULL);
INSERT INTO `homework` VALUES ('a6d36709-4ead-40cd-a218-c229ae9a1e2e', 600010, '83b27f0b-eb4c-4623-b5ab-1d1ed8392b9a', '1bb616e5-a78c-4058-814b-926e4430b8b1', NULL, '2023-11-24 01:29:01', 'drj和yxh的本子', NULL, NULL);
INSERT INTO `homework` VALUES ('a87be740-dcf8-4958-bda6-c97e40e5bfd7', 600015, 'd0431695-3cb2-44f7-b1f1-55cf1f930ba2', '053088f3-9ced-47f4-b1f6-09e00cf651b0', 100, '2023-12-22 01:44:11', '<p><span style=\'color: rgb(55, 65, 81); font-family: Söhne, ui-sans-serif, system-ui, -apple-system, \"Segoe UI\", Roboto, Ubuntu, Cantarell, \"Noto Sans\", sans-serif, \"Helvetica Neue\", Arial, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\", \"Noto Color Emoji\"; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: pre-wrap; text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\' id=\"isPasted\">上课对于学生来说是获取知识和发展技能的重要途径。在课堂上，学生有机会直接与老师互动，提问疑惑，深入理解学科内容。同时，上课也促使学生培养自律和团队协作的能力，通过合作项目和讨论活动提高沟通技巧。课堂上的互动和实践使得抽象的理论更具体，更易于理解和记忆。此外，上课也为学生提供了建立人际关系和分享经验的机会，培养了团队协作和社交技能。总体而言，上课不仅仅是获取知识的手段，更是全面发展学生个人和社会能力的关键路径。</span></p>', NULL, NULL);
INSERT INTO `homework` VALUES ('b8726d5d-a61b-4be9-b156-868e942a89a5', 600003, 'd065a165-067c-417d-9169-73653b754085', '163fb59f-2ffc-4ab8-ac98-44c8fe99803b', NULL, '2023-11-17 13:10:00', 'yxh和drh的本子', NULL, NULL);
INSERT INTO `homework` VALUES ('c3cdbab4-59a0-4f2e-b5b4-6b3890fef24b', 600009, '0b1e38d3-3940-443c-9fc9-69fd5d72d8e6', '163fb59f-2ffc-4ab8-ac98-44c8fe99803b', 60, '2023-11-17 13:10:00', 'yxh的作业', NULL, NULL);
INSERT INTO `homework` VALUES ('c6483dca-dbc9-4cfa-91c0-29e441b2a381', 600001, '339dc4f3-7ba7-4b22-9dac-fb8b35ee7175', '1bb616e5-a78c-4058-814b-926e4430b8b1', 60, '2023-11-24 14:19:55', '<p>哈哈</p>', 'src\\main\\resources\\static\\homework\\toefl.md', '谁tm判的');
INSERT INTO `homework` VALUES ('fc62307f-66da-4b6c-9513-6cb68edef8fe', 600017, 'c968b998-681c-4dda-a501-dbb44f01a324', '053088f3-9ced-47f4-b1f6-09e00cf651b0', 0, '2023-12-22 02:39:18', '<p><span style=\'color: rgb(55, 65, 81); font-family: Söhne, ui-sans-serif, system-ui, -apple-system, \"Segoe UI\", Roboto, Ubuntu, Cantarell, \"Noto Sans\", sans-serif, \"Helvetica Neue\", Arial, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\", \"Noto Color Emoji\"; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: pre-wrap; text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\' id=\"isPasted\">上课对于学生来说是获取知识和发展技能的重要途径。在课堂上，学生有机会直接与老师互动，提问疑惑，深入理解学科内容。同时，上课也促使学生培养自律和团队协作的能力，通过合作项目和讨论活动提高沟通技巧。课堂上的互动和实践使得抽象的理论更具体，更易于理解和记忆。此外，上课也为学生提供了建立人际关系和分享经验的机会，培养了团队协作和社交技能。总体而言，上课不仅仅是获取知识的手段，更是全面发展学生个人和社会能力的关键路径。</span></p>', NULL, NULL);

-- ----------------------------
-- Table structure for peer
-- ----------------------------
DROP TABLE IF EXISTS `peer`;
CREATE TABLE `peer`  (
  `uuid` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `peerID` int NULL DEFAULT NULL,
  `userUUID` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `homeworkUUID` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `assignmentUUID` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `homeworkID` int NULL DEFAULT NULL,
  `assignmentID` int NULL DEFAULT NULL,
  `score` int NULL DEFAULT NULL,
  `comment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`uuid`, `userUUID`, `homeworkUUID`, `assignmentUUID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of peer
-- ----------------------------
INSERT INTO `peer` VALUES ('000fd810-2d6c-4e38-a8b8-d5feb7c0b3b4', 700064, '053088f3-9ced-47f4-b1f6-09e00cf651b0', '9478edda-f417-4993-93a3-30bb2a266a0e', 'c968b998-681c-4dda-a501-dbb44f01a324', 'liqi', 600018, 500014, 0, '写得不是很好，还要加油！', '互评结束');
INSERT INTO `peer` VALUES ('002dad6f-68ca-4313-800d-e3d75ccf80a6', 700061, '1bb616e5-a78c-4058-814b-926e4430b8b1', 'a87be740-dcf8-4958-bda6-c97e40e5bfd7', 'd0431695-3cb2-44f7-b1f1-55cf1f930ba2', 'student', 600015, 500013, NULL, NULL, '互评结束');
INSERT INTO `peer` VALUES ('108751f8-6dfa-494b-a6d8-22f99f305461', 700056, '053088f3-9ced-47f4-b1f6-09e00cf651b0', 'a5a8cbb6-4e13-4dbc-8b9d-a70d59809a1d', '547e05fd-9e70-4a12-88bd-ac3d8296c0f4', 'liqi', 600012, 500012, 60, 'nbnbnbn', '已互评');
INSERT INTO `peer` VALUES ('161bdd8c-56a7-41d5-9b85-266abdb7f5d8', 700062, '163fb59f-2ffc-4ab8-ac98-44c8fe99803b', 'a6d36709-4ead-40cd-a218-c229ae9a1e2e', '83b27f0b-eb4c-4623-b5ab-1d1ed8392b9a', 'yxh', 600010, 500008, NULL, NULL, '正在互评中');
INSERT INTO `peer` VALUES ('19847c16-efb2-4403-bd69-63a932950633', 700050, '053088f3-9ced-47f4-b1f6-09e00cf651b0', '946207a7-0037-4c13-b1df-9d30639f8a6a', '0b1e38d3-3940-443c-9fc9-69fd5d72d8e6', 'liqi', 600006, 500010, NULL, NULL, '互评结束');
INSERT INTO `peer` VALUES ('2dfa70c2-96de-496f-b81e-0c648c87a441', 700052, '163fb59f-2ffc-4ab8-ac98-44c8fe99803b', '7e510688-6ba9-4391-ba75-6cb2649a6690', '0b1e38d3-3940-443c-9fc9-69fd5d72d8e6', 'yxh', 600007, 500010, NULL, NULL, '互评结束');
INSERT INTO `peer` VALUES ('371e893a-0439-4d3d-bea2-1749d20bf9c1', 700053, '163fb59f-2ffc-4ab8-ac98-44c8fe99803b', '946207a7-0037-4c13-b1df-9d30639f8a6a', '0b1e38d3-3940-443c-9fc9-69fd5d72d8e6', 'yxh', 600006, 500010, NULL, NULL, '互评结束');
INSERT INTO `peer` VALUES ('467738a3-a3d1-40bf-bffa-61489de23b89', 700054, '1bb616e5-a78c-4058-814b-926e4430b8b1', 'c3cdbab4-59a0-4f2e-b5b4-6b3890fef24b', '0b1e38d3-3940-443c-9fc9-69fd5d72d8e6', 'student', 600009, 500010, NULL, NULL, '互评结束');
INSERT INTO `peer` VALUES ('5159f2ff-1fbf-4b09-a458-6bd9faa5a4a3', 700048, '1bb616e5-a78c-4058-814b-926e4430b8b1', '3b5c7019-b726-46ac-97f5-cb7404017643', '339dc4f3-7ba7-4b22-9dac-fb8b35ee7175', 'student', 600002, 500004, NULL, NULL, '互评结束');
INSERT INTO `peer` VALUES ('6287e309-c023-4222-8d4d-a5ba620fc773', 700057, '1bb616e5-a78c-4058-814b-926e4430b8b1', '51fbf2ad-7756-4731-89a8-9a1905d11a26', '547e05fd-9e70-4a12-88bd-ac3d8296c0f4', 'student', 600014, 500012, NULL, NULL, '正在互评中');
INSERT INTO `peer` VALUES ('861e7f5b-e4bd-4455-8be7-73e81b8b53cb', 700066, '053088f3-9ced-47f4-b1f6-09e00cf651b0', '0cf782a8-e7e0-463f-b0bd-3515272d315e', 'c4479155-420c-475b-8eca-b848e6fec0e8', 'liqi', 600020, 500016, 0, '太烂了', '互评结束');
INSERT INTO `peer` VALUES ('87ac67cf-ce9c-4683-92e7-3d0baba7e948', 700058, '163fb59f-2ffc-4ab8-ac98-44c8fe99803b', '690f82f9-9eb5-41f2-b09a-8898e42329af', '8f3f4afe-5672-43b2-aecb-b393999b9050', 'yxh', 600011, 500009, NULL, NULL, '正在互评中');
INSERT INTO `peer` VALUES ('8afe2533-29f2-4bed-8707-535623012198', 700049, '1bb616e5-a78c-4058-814b-926e4430b8b1', '32eab4bc-5e0e-4a8e-ab6f-a4046df48044', '339dc4f3-7ba7-4b22-9dac-fb8b35ee7175', 'student', 600013, 500004, NULL, NULL, '互评结束');
INSERT INTO `peer` VALUES ('8cb6572d-e68d-4476-b2b7-6e825f241a03', 700051, '053088f3-9ced-47f4-b1f6-09e00cf651b0', 'c3cdbab4-59a0-4f2e-b5b4-6b3890fef24b', '0b1e38d3-3940-443c-9fc9-69fd5d72d8e6', 'liqi', 600009, 500010, NULL, NULL, '互评结束');
INSERT INTO `peer` VALUES ('91942196-16ad-49b5-8bba-3e371ed8d368', 700060, '053088f3-9ced-47f4-b1f6-09e00cf651b0', '655c075e-df18-4e53-9e0f-fecc639b43ef', 'd0431695-3cb2-44f7-b1f1-55cf1f930ba2', 'liqi', 600016, 500013, 100, '写得好', '互评结束');
INSERT INTO `peer` VALUES ('9c40eaeb-4608-486c-8b7b-6e61873aa979', 700046, '163fb59f-2ffc-4ab8-ac98-44c8fe99803b', '32eab4bc-5e0e-4a8e-ab6f-a4046df48044', '339dc4f3-7ba7-4b22-9dac-fb8b35ee7175', 'yxh', 600013, 500004, NULL, NULL, '互评结束');
INSERT INTO `peer` VALUES ('c2fead30-4d58-47e1-9728-7fbbc2121695', 700044, '053088f3-9ced-47f4-b1f6-09e00cf651b0', 'c6483dca-dbc9-4cfa-91c0-29e441b2a381', '339dc4f3-7ba7-4b22-9dac-fb8b35ee7175', 'liqi', 600001, 500004, NULL, NULL, '互评结束');
INSERT INTO `peer` VALUES ('c3c37a86-8b69-4842-911b-335ede6a304c', 700047, '163fb59f-2ffc-4ab8-ac98-44c8fe99803b', 'c6483dca-dbc9-4cfa-91c0-29e441b2a381', '339dc4f3-7ba7-4b22-9dac-fb8b35ee7175', 'yxh', 600001, 500004, NULL, NULL, '互评结束');
INSERT INTO `peer` VALUES ('c54a6edc-3afd-40c6-95f4-1e4401d6da47', 700063, '1bb616e5-a78c-4058-814b-926e4430b8b1', '0d735119-e72f-494a-9328-1ab42f794c8b', '83b27f0b-eb4c-4623-b5ab-1d1ed8392b9a', 'student', 600004, 500008, NULL, NULL, '正在互评中');
INSERT INTO `peer` VALUES ('cde6c079-103f-42d6-ba92-548f8602d6c4', 700045, '053088f3-9ced-47f4-b1f6-09e00cf651b0', '3b5c7019-b726-46ac-97f5-cb7404017643', '339dc4f3-7ba7-4b22-9dac-fb8b35ee7175', 'liqi', 600002, 500004, NULL, NULL, '互评结束');
INSERT INTO `peer` VALUES ('d08f58ec-32df-44e1-9f6d-e380c3f409c5', 700067, '1bb616e5-a78c-4058-814b-926e4430b8b1', '19a67ede-4510-48c5-9bcd-5efeb0d5910e', 'c4479155-420c-475b-8eca-b848e6fec0e8', 'student', 600019, 500016, NULL, NULL, '互评结束');
INSERT INTO `peer` VALUES ('e0cfbffe-969e-4ae5-b9eb-d7e1c682fd4d', 700065, '1bb616e5-a78c-4058-814b-926e4430b8b1', 'fc62307f-66da-4b6c-9513-6cb68edef8fe', 'c968b998-681c-4dda-a501-dbb44f01a324', 'student', 600017, 500014, NULL, NULL, '互评结束');
INSERT INTO `peer` VALUES ('f3624c5d-c275-43a3-9153-8d58907e49cd', 700059, '1bb616e5-a78c-4058-814b-926e4430b8b1', '57ce92c6-9eb1-4798-ad22-3c1d787d902d', '8f3f4afe-5672-43b2-aecb-b393999b9050', 'student', 600005, 500009, NULL, NULL, '正在互评中');
INSERT INTO `peer` VALUES ('f9afd43d-71d3-4586-9564-aff9955e31a6', 700055, '1bb616e5-a78c-4058-814b-926e4430b8b1', '7e510688-6ba9-4391-ba75-6cb2649a6690', '0b1e38d3-3940-443c-9fc9-69fd5d72d8e6', 'student', 600007, 500010, NULL, NULL, '互评结束');

-- ----------------------------
-- Table structure for sc
-- ----------------------------
DROP TABLE IF EXISTS `sc`;
CREATE TABLE `sc`  (
  `userUUID` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `courseUUID` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `studentName` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `courseID` int NULL DEFAULT NULL,
  PRIMARY KEY (`userUUID`, `courseUUID`) USING BTREE,
  INDEX `sc_ibfk_2`(`courseUUID` ASC) USING BTREE,
  CONSTRAINT `sc_ibfk_1` FOREIGN KEY (`userUUID`) REFERENCES `user` (`uuid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sc_ibfk_2` FOREIGN KEY (`courseUUID`) REFERENCES `course` (`uuid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sc
-- ----------------------------
INSERT INTO `sc` VALUES ('053088f3-9ced-47f4-b1f6-09e00cf651b0', '4e56f09f-6463-4f24-9f56-2e21cd69994c', 'liqi', 400012);
INSERT INTO `sc` VALUES ('053088f3-9ced-47f4-b1f6-09e00cf651b0', '9d89912a-0712-461a-80c6-d3de51c21061', 'liqi', 400009);
INSERT INTO `sc` VALUES ('053088f3-9ced-47f4-b1f6-09e00cf651b0', 'd9d0f812-1366-406f-91af-2dc84fa18f45', 'liqi', 400002);
INSERT INTO `sc` VALUES ('163fb59f-2ffc-4ab8-ac98-44c8fe99803b', '9d89912a-0712-461a-80c6-d3de51c21061', 'yxh', 400009);
INSERT INTO `sc` VALUES ('163fb59f-2ffc-4ab8-ac98-44c8fe99803b', 'd9d0f812-1366-406f-91af-2dc84fa18f45', 'yxh', 400002);
INSERT INTO `sc` VALUES ('1bb616e5-a78c-4058-814b-926e4430b8b1', '4e56f09f-6463-4f24-9f56-2e21cd69994c', 'student', 400012);
INSERT INTO `sc` VALUES ('1bb616e5-a78c-4058-814b-926e4430b8b1', '9d89912a-0712-461a-80c6-d3de51c21061', 'student', 400009);
INSERT INTO `sc` VALUES ('1bb616e5-a78c-4058-814b-926e4430b8b1', 'd9d0f812-1366-406f-91af-2dc84fa18f45', 'student', 400002);
INSERT INTO `sc` VALUES ('73a99c30-d342-463b-a9bb-9b66ed9a1796', 'd9d0f812-1366-406f-91af-2dc84fa18f45', 'testtest', 400002);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `uuid` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `uid` int NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `authority` int(1) UNSIGNED ZEROFILL NOT NULL,
  PRIMARY KEY (`uuid`) USING BTREE,
  UNIQUE INDEX `email`(`email` ASC) USING BTREE,
  UNIQUE INDEX `uid`(`uid` ASC) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('00ab9208-70a1-4c55-9c74-987076f8d6ec', 200002, 'lhm', '242d12bb1a150d26b9e6b313da14b200', '2412162744@qq.com', 2);
INSERT INTO `user` VALUES ('053088f3-9ced-47f4-b1f6-09e00cf651b0', 300001, 'liqi', '242d12bb1a150d26b9e6b313da14b200', '21301034@bjtu.edu.cn', 3);
INSERT INTO `user` VALUES ('0c9dc230-cea3-44b2-a811-910528fc53e3', 200005, 'teacher', '242d12bb1a150d26b9e6b313da14b200', '21301032@bjtu.edu.cn', 2);
INSERT INTO `user` VALUES ('163fb59f-2ffc-4ab8-ac98-44c8fe99803b', 300002, 'yxh', '242d12bb1a150d26b9e6b313da14b200', '21301114@bjtu.edu.cn', 3);
INSERT INTO `user` VALUES ('1bb616e5-a78c-4058-814b-926e4430b8b1', 300025, 'student', '242d12bb1a150d26b9e6b313da14b200', 'student@qq.com', 3);
INSERT INTO `user` VALUES ('21ffe423-f14a-4d16-930f-53b9bbf6297e', 200007, 'test', '68f842ee18001acd9de0f9982f4f85e6', 'test@163.com', 2);
INSERT INTO `user` VALUES ('4867c8b0-84f5-4ef8-a405-2234591135a2', 100002, 'admin', 'c6847e9dda1405b4f9fb30243e530c3c', '1832271620@qq.com', 1);
INSERT INTO `user` VALUES ('73a99c30-d342-463b-a9bb-9b66ed9a1796', 300023, 'testtest', '242d12bb1a150d26b9e6b313da14b200', '123@qq.com', 3);
INSERT INTO `user` VALUES ('afc8a60c-ef10-4fb3-ba2f-b396b5e1209a', 200009, 'abcabc', '242d12bb1a150d26b9e6b313da14b200', '123213@qq.com', 2);

SET FOREIGN_KEY_CHECKS = 1;
