-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: bcs_db
-- ------------------------------------------------------
-- Server version	5.5.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tbl_acc_acctype`
--

DROP TABLE IF EXISTS `tbl_acc_acctype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_acc_acctype` (
  `accTypeId` int(11) NOT NULL AUTO_INCREMENT,
  `accTypeName` varchar(200) NOT NULL,
  `isBankAccLedger` tinyint(1) NOT NULL,
  `groupId` int(11) NOT NULL,
  PRIMARY KEY (`accTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_acc_acctype`
--

LOCK TABLES `tbl_acc_acctype` WRITE;
/*!40000 ALTER TABLE `tbl_acc_acctype` DISABLE KEYS */;
INSERT INTO `tbl_acc_acctype` VALUES (1,'Furniture Fixture ',0,1),(2,'Receivable ',0,2),(3,'Bank',1,2),(4,'Cash ',0,2),(5,'Inventory ',0,2),(6,'Equity/Capital ',0,3),(7,'Secured Loans ',0,4),(8,'Unsecured Loans ',0,4),(9,'Payables',0,5),(10,'Short term loans ',0,5),(11,'Bank over draft ',0,5),(12,'Direct Income',0,6),(13,'Other Income',0,6),(14,'Direct Cost',0,7),(15,'Indirect Cost',0,7),(16,'Purchase',0,8),(17,'Sales',0,9),(18,'Employee Advance',0,2),(19,'Party Advance Paid',0,2),(20,'Party Advance Received',0,5),(21,'Plant and Machanary',0,1),(22,'Vehicles',0,1),(23,'Office Equiptment',0,1),(24,'Building and Amenities',0,1);
/*!40000 ALTER TABLE `tbl_acc_acctype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_acc_bank`
--

DROP TABLE IF EXISTS `tbl_acc_bank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_acc_bank` (
  `bankId` int(11) NOT NULL AUTO_INCREMENT,
  `accNo` varchar(45) DEFAULT NULL,
  `accHolderName` varchar(70) DEFAULT NULL,
  `bankName` varchar(100) DEFAULT NULL,
  `branch` varchar(45) DEFAULT NULL,
  `bankAccHolderDetail` varchar(100) DEFAULT NULL,
  `reconciliationDate` date DEFAULT NULL,
  PRIMARY KEY (`bankId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_acc_bank`
--

LOCK TABLES `tbl_acc_bank` WRITE;
/*!40000 ALTER TABLE `tbl_acc_bank` DISABLE KEYS */;
INSERT INTO `tbl_acc_bank` VALUES (1,'123456','Sonam','Bank of Bhutan','Thimphu ','','2021-04-19'),(2,'123123123','Sonam','Bank of Bhutan','Thimphu ','Sonam','2021-03-31'),(3,'1233345','Autga ','Bank of Bhutan','Thimphu ','','2021-03-31'),(4,'1234','TTPL','BOB ','Thimphu ','','2021-03-31'),(5,'123476','Autga ','BOB','Thimphu','','2021-03-31'),(6,'123','Sonam','BOB','Thimphu','','2021-03-31'),(7,'123578','Autga','BOB','Thimphu ','','2021-05-31'),(8,'123123','Sonam','Bank of Bhutan','Thimphu ','Sonam','2021-03-31'),(9,'123123','Sonam','Bank of Bhutan','Thimphu ','Sonam','2021-03-31'),(10,'12345','Autga Automobiles ','BNB ','Thimphu ','',NULL);
/*!40000 ALTER TABLE `tbl_acc_bank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_acc_bank_reconciliation`
--

DROP TABLE IF EXISTS `tbl_acc_bank_reconciliation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_acc_bank_reconciliation` (
  `bankReconciliationId` int(11) NOT NULL AUTO_INCREMENT,
  `bookBalance` double DEFAULT NULL,
  `chequeIssuedNotEncash` double DEFAULT NULL,
  `directDeposit` double DEFAULT NULL,
  `directTransfer` double DEFAULT NULL,
  `previousMonthChequeEncash` double DEFAULT NULL,
  `bankReconciliationAmount` double DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `setDate` date NOT NULL,
  `createdBy` varchar(250) NOT NULL,
  PRIMARY KEY (`bankReconciliationId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_acc_bank_reconciliation`
--

LOCK TABLES `tbl_acc_bank_reconciliation` WRITE;
/*!40000 ALTER TABLE `tbl_acc_bank_reconciliation` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_acc_bank_reconciliation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_acc_depreciation`
--

DROP TABLE IF EXISTS `tbl_acc_depreciation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_acc_depreciation` (
  `depreciationId` int(11) NOT NULL AUTO_INCREMENT,
  `voucherId` int(11) DEFAULT NULL,
  `particular` varchar(300) DEFAULT NULL,
  `dateOfPurchase` date DEFAULT NULL,
  `originalQty` int(11) DEFAULT NULL,
  `originalValue` double DEFAULT NULL,
  `additionalQty` int(11) DEFAULT NULL,
  `additional` double DEFAULT NULL,
  `disposalQty` int(11) DEFAULT NULL,
  `disposal` double DEFAULT NULL,
  `rateOfDepreciation` int(11) DEFAULT NULL,
  `companyId` int(11) DEFAULT NULL,
  `setDate` date DEFAULT NULL,
  `createdBy` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`depreciationId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_acc_depreciation`
--

LOCK TABLES `tbl_acc_depreciation` WRITE;
/*!40000 ALTER TABLE `tbl_acc_depreciation` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_acc_depreciation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_acc_depreciation_item_details`
--

DROP TABLE IF EXISTS `tbl_acc_depreciation_item_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_acc_depreciation_item_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `depreciationId` int(11) DEFAULT NULL,
  `voucherId` int(11) DEFAULT NULL,
  `dateOfPurchase` date DEFAULT NULL,
  `additionalQty` int(11) DEFAULT NULL,
  `additional` double DEFAULT NULL,
  `disposalQty` int(11) DEFAULT NULL,
  `disposal` double DEFAULT NULL,
  `companyId` int(11) DEFAULT NULL,
  `setDate` date DEFAULT NULL,
  `createdBy` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_acc_depreciation_item_details`
--

LOCK TABLES `tbl_acc_depreciation_item_details` WRITE;
/*!40000 ALTER TABLE `tbl_acc_depreciation_item_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_acc_depreciation_item_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_acc_depreciation_item_setup`
--

DROP TABLE IF EXISTS `tbl_acc_depreciation_item_setup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_acc_depreciation_item_setup` (
  `depreciationId` int(11) NOT NULL AUTO_INCREMENT,
  `itemName` varchar(250) DEFAULT NULL,
  `rateOfDepreciation` int(11) DEFAULT NULL,
  `openingBalance` double DEFAULT NULL,
  `companyId` int(11) DEFAULT NULL,
  `setDate` date DEFAULT NULL,
  `createdBy` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`depreciationId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_acc_depreciation_item_setup`
--

LOCK TABLES `tbl_acc_depreciation_item_setup` WRITE;
/*!40000 ALTER TABLE `tbl_acc_depreciation_item_setup` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_acc_depreciation_item_setup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_acc_group`
--

DROP TABLE IF EXISTS `tbl_acc_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_acc_group` (
  `groupId` int(11) NOT NULL AUTO_INCREMENT,
  `groupName` varchar(100) NOT NULL,
  `reportEffectType` char(2) DEFAULT NULL,
  PRIMARY KEY (`groupId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_acc_group`
--

LOCK TABLES `tbl_acc_group` WRITE;
/*!40000 ALTER TABLE `tbl_acc_group` DISABLE KEYS */;
INSERT INTO `tbl_acc_group` VALUES (1,'Non Current Assets ','BS'),(2,'Current Assets ','BS'),(3,'Capital','BS'),(4,'Non current Liability ','BS'),(5,'Current Liability','BS'),(6,'Income ','PL'),(7,'Expenditure','PL'),(8,'Purchase','PL'),(9,'Sales','PL');
/*!40000 ALTER TABLE `tbl_acc_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_acc_inv_voucherdetail`
--

DROP TABLE IF EXISTS `tbl_acc_inv_voucherdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_acc_inv_voucherdetail` (
  `purchaseVoucherDetailId` int(11) NOT NULL AUTO_INCREMENT,
  `voucherId` int(11) NOT NULL,
  `itemCategoryId` varchar(11) NOT NULL,
  `debitAmount` double DEFAULT NULL,
  `creditAmount` double DEFAULT NULL,
  PRIMARY KEY (`purchaseVoucherDetailId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_acc_inv_voucherdetail`
--

LOCK TABLES `tbl_acc_inv_voucherdetail` WRITE;
/*!40000 ALTER TABLE `tbl_acc_inv_voucherdetail` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_acc_inv_voucherdetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_acc_ledger`
--

DROP TABLE IF EXISTS `tbl_acc_ledger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_acc_ledger` (
  `ledgerId` varchar(11) NOT NULL,
  `ledgerName` varchar(200) NOT NULL,
  `accTypeId` int(11) NOT NULL,
  `openingBal` double DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `setDate` date NOT NULL,
  `createdBy` varchar(20) NOT NULL,
  `bankId` int(11) DEFAULT NULL,
  PRIMARY KEY (`ledgerId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_acc_ledger`
--

LOCK TABLES `tbl_acc_ledger` WRITE;
/*!40000 ALTER TABLE `tbl_acc_ledger` DISABLE KEYS */;
INSERT INTO `tbl_acc_ledger` VALUES ('1','Cash in Hand',4,0,1,'2021-02-24','admin',NULL),('2','Capital',6,0,1,'2021-02-24','admin',NULL),('3','Chair',1,0,1,'2021-02-24','admin',NULL),('4','Electricity charges ',15,0,1,'2021-02-24','admin',NULL),('5','Water ',15,0,1,'2021-02-24','admin',NULL),('6','Purchase',16,0,1,'2021-02-24','admin',NULL);
/*!40000 ALTER TABLE `tbl_acc_ledger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_acc_ledger_a`
--

DROP TABLE IF EXISTS `tbl_acc_ledger_a`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_acc_ledger_a` (
  `ledgerAuditId` int(11) NOT NULL AUTO_INCREMENT,
  `ledgerId` varchar(11) NOT NULL,
  `ledgerName` varchar(200) NOT NULL,
  `bankId` int(11) DEFAULT NULL,
  `accTypeId` int(11) NOT NULL,
  `openingBal` double DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `setDate` date NOT NULL,
  `createdBy` varchar(20) NOT NULL,
  PRIMARY KEY (`ledgerAuditId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_acc_ledger_a`
--

LOCK TABLES `tbl_acc_ledger_a` WRITE;
/*!40000 ALTER TABLE `tbl_acc_ledger_a` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_acc_ledger_a` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_acc_money_receipt`
--

DROP TABLE IF EXISTS `tbl_acc_money_receipt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_acc_money_receipt` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `receiptNo` varchar(5) NOT NULL,
  `receiptDate` date NOT NULL,
  `partyLedgerId` varchar(4) NOT NULL,
  `amount` double NOT NULL,
  `receivedIn` int(11) NOT NULL,
  `companyId` int(11) NOT NULL,
  `createdDate` date NOT NULL,
  `createdBy` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_acc_money_receipt`
--

LOCK TABLES `tbl_acc_money_receipt` WRITE;
/*!40000 ALTER TABLE `tbl_acc_money_receipt` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_acc_money_receipt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_acc_parenttype`
--

DROP TABLE IF EXISTS `tbl_acc_parenttype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_acc_parenttype` (
  `parentTypeId` int(11) NOT NULL AUTO_INCREMENT,
  `parentTypeName` varchar(45) NOT NULL,
  PRIMARY KEY (`parentTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_acc_parenttype`
--

LOCK TABLES `tbl_acc_parenttype` WRITE;
/*!40000 ALTER TABLE `tbl_acc_parenttype` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_acc_parenttype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_acc_party_detail`
--

DROP TABLE IF EXISTS `tbl_acc_party_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_acc_party_detail` (
  `partyId` int(11) NOT NULL,
  `partyName` varchar(100) NOT NULL,
  `partyAddress` varchar(250) NOT NULL,
  `partyContactNo` int(11) NOT NULL,
  `partyEmail` varchar(100) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  `setDate` date DEFAULT NULL,
  `createdBy` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`partyId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_acc_party_detail`
--

LOCK TABLES `tbl_acc_party_detail` WRITE;
/*!40000 ALTER TABLE `tbl_acc_party_detail` DISABLE KEYS */;
INSERT INTO `tbl_acc_party_detail` VALUES (1,'Hotel','thimphu',178888,'hapsdugay@gmail.com',2,'2021-02-24','admin');
/*!40000 ALTER TABLE `tbl_acc_party_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_acc_sale_invoice`
--

DROP TABLE IF EXISTS `tbl_acc_sale_invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_acc_sale_invoice` (
  `saleInvoiceId` int(11) NOT NULL AUTO_INCREMENT,
  `partyId` int(11) DEFAULT NULL,
  `invoiceNo` char(3) NOT NULL,
  `invoiceDate` date NOT NULL,
  `setDate` date NOT NULL,
  `createdBy` varchar(45) NOT NULL,
  PRIMARY KEY (`saleInvoiceId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_acc_sale_invoice`
--

LOCK TABLES `tbl_acc_sale_invoice` WRITE;
/*!40000 ALTER TABLE `tbl_acc_sale_invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_acc_sale_invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_acc_sale_invoice_counter`
--

DROP TABLE IF EXISTS `tbl_acc_sale_invoice_counter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_acc_sale_invoice_counter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `saleInvoiceNoCounter` varchar(3) DEFAULT NULL,
  `companyId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_acc_sale_invoice_counter`
--

LOCK TABLES `tbl_acc_sale_invoice_counter` WRITE;
/*!40000 ALTER TABLE `tbl_acc_sale_invoice_counter` DISABLE KEYS */;
INSERT INTO `tbl_acc_sale_invoice_counter` VALUES (1,'001',1),(2,'001',2);
/*!40000 ALTER TABLE `tbl_acc_sale_invoice_counter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_acc_sale_invoice_detail`
--

DROP TABLE IF EXISTS `tbl_acc_sale_invoice_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_acc_sale_invoice_detail` (
  `saleInvoiceDetailId` int(11) NOT NULL AUTO_INCREMENT,
  `saleInvoiceId` int(11) DEFAULT NULL,
  `particular` text,
  `amount` double DEFAULT NULL,
  PRIMARY KEY (`saleInvoiceDetailId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_acc_sale_invoice_detail`
--

LOCK TABLES `tbl_acc_sale_invoice_detail` WRITE;
/*!40000 ALTER TABLE `tbl_acc_sale_invoice_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_acc_sale_invoice_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_acc_voucher_count`
--

DROP TABLE IF EXISTS `tbl_acc_voucher_count`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_acc_voucher_count` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `voucherSerial` int(11) NOT NULL,
  `voucherTypeId` int(11) NOT NULL,
  `companyId` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_acc_voucher_count`
--

LOCK TABLES `tbl_acc_voucher_count` WRITE;
/*!40000 ALTER TABLE `tbl_acc_voucher_count` DISABLE KEYS */;
INSERT INTO `tbl_acc_voucher_count` VALUES (25,7,1,1),(26,1,2,1),(27,0,3,1),(28,0,4,1),(29,0,5,1),(30,1,6,1);
/*!40000 ALTER TABLE `tbl_acc_voucher_count` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_acc_voucher_entries`
--

DROP TABLE IF EXISTS `tbl_acc_voucher_entries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_acc_voucher_entries` (
  `voucherId` int(11) NOT NULL,
  `voucherTypeId` int(11) NOT NULL,
  `voucherNo` int(11) NOT NULL,
  `voucherEntryDate` date NOT NULL,
  `companyId` int(11) NOT NULL,
  `financialYearId` int(11) DEFAULT NULL,
  `setDate` date NOT NULL,
  `createdBy` varchar(45) NOT NULL,
  `narration` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`voucherId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_acc_voucher_entries`
--

LOCK TABLES `tbl_acc_voucher_entries` WRITE;
/*!40000 ALTER TABLE `tbl_acc_voucher_entries` DISABLE KEYS */;
INSERT INTO `tbl_acc_voucher_entries` VALUES (1,2,1,'2021-01-02',1,1,'2021-05-08','admin','Received Capital'),(2,1,1,'2021-03-03',1,1,'2021-05-08','admin','Brought Chair cash in hand'),(3,1,2,'2022-04-02',1,2,'2022-05-08','admin','Next Year Chair'),(4,1,3,'2022-01-01',1,2,'2021-05-08','admin',''),(5,1,4,'2021-01-31',1,9,'2021-05-12','admin',''),(6,1,5,'2021-01-31',1,9,'2021-05-12','admin',''),(7,1,6,'2020-02-25',1,9,'2021-05-12','admin',''),(8,1,7,'2021-02-28',1,9,'2021-05-12','admin',''),(9,6,1,'2021-05-12',1,9,'2021-05-12','admin','Purchase Entry');
/*!40000 ALTER TABLE `tbl_acc_voucher_entries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_acc_voucher_entries_detail`
--

DROP TABLE IF EXISTS `tbl_acc_voucher_entries_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_acc_voucher_entries_detail` (
  `voucherDetailId` int(11) NOT NULL AUTO_INCREMENT,
  `voucherId` int(11) NOT NULL,
  `ledgerId` varchar(11) NOT NULL,
  `drcrAmount` double NOT NULL,
  PRIMARY KEY (`voucherDetailId`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_acc_voucher_entries_detail`
--

LOCK TABLES `tbl_acc_voucher_entries_detail` WRITE;
/*!40000 ALTER TABLE `tbl_acc_voucher_entries_detail` DISABLE KEYS */;
INSERT INTO `tbl_acc_voucher_entries_detail` VALUES (1,1,'2',10000),(2,1,'1',-10000),(3,2,'3',-5000),(4,2,'1',5000),(5,3,'3',-1000),(6,3,'1',1000),(7,4,'4',-1500),(8,4,'1',1500),(9,5,'4',-1500),(10,5,'1',1500),(11,6,'5',-3000),(12,6,'1',3000),(13,7,'4',-10000),(14,7,'1',10000),(15,8,'4',-10000),(16,8,'1',10000),(17,9,'6',-2000),(18,9,'1',2000);
/*!40000 ALTER TABLE `tbl_acc_voucher_entries_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_acc_voucher_entries_detail_ledger`
--

DROP TABLE IF EXISTS `tbl_acc_voucher_entries_detail_ledger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_acc_voucher_entries_detail_ledger` (
  `voucherEntriesDetailLedgerId` int(11) NOT NULL AUTO_INCREMENT,
  `voucherDetailId` int(11) NOT NULL,
  `voucherId` int(11) NOT NULL,
  `ledgerId` varchar(11) NOT NULL,
  `drcrAmount` double NOT NULL,
  PRIMARY KEY (`voucherEntriesDetailLedgerId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_acc_voucher_entries_detail_ledger`
--

LOCK TABLES `tbl_acc_voucher_entries_detail_ledger` WRITE;
/*!40000 ALTER TABLE `tbl_acc_voucher_entries_detail_ledger` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_acc_voucher_entries_detail_ledger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_acc_voucher_entries_ledger`
--

DROP TABLE IF EXISTS `tbl_acc_voucher_entries_ledger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_acc_voucher_entries_ledger` (
  `voucherLedgerId` int(11) NOT NULL AUTO_INCREMENT,
  `voucherId` int(11) NOT NULL,
  `voucherTypeId` int(11) NOT NULL,
  `voucherNo` int(11) NOT NULL,
  `voucherEntryDate` date NOT NULL,
  `companyId` int(11) NOT NULL,
  `financialYearId` int(11) DEFAULT NULL,
  `setDate` date NOT NULL,
  `createdBy` varchar(45) NOT NULL,
  `narration` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`voucherLedgerId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_acc_voucher_entries_ledger`
--

LOCK TABLES `tbl_acc_voucher_entries_ledger` WRITE;
/*!40000 ALTER TABLE `tbl_acc_voucher_entries_ledger` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_acc_voucher_entries_ledger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_acc_vouchertype`
--

DROP TABLE IF EXISTS `tbl_acc_vouchertype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_acc_vouchertype` (
  `voucherTypeId` int(11) NOT NULL,
  `voucherTypeName` varchar(500) NOT NULL,
  PRIMARY KEY (`voucherTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_acc_vouchertype`
--

LOCK TABLES `tbl_acc_vouchertype` WRITE;
/*!40000 ALTER TABLE `tbl_acc_vouchertype` DISABLE KEYS */;
INSERT INTO `tbl_acc_vouchertype` VALUES (1,'Payment'),(2,'Receipt'),(3,'Contra'),(4,'Journal'),(5,'Sales'),(6,'Purchase');
/*!40000 ALTER TABLE `tbl_acc_vouchertype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_common_business_type`
--

DROP TABLE IF EXISTS `tbl_common_business_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_common_business_type` (
  `businessTypeId` int(11) NOT NULL,
  `businessTypeName` varchar(200) NOT NULL,
  PRIMARY KEY (`businessTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_common_business_type`
--

LOCK TABLES `tbl_common_business_type` WRITE;
/*!40000 ALTER TABLE `tbl_common_business_type` DISABLE KEYS */;
INSERT INTO `tbl_common_business_type` VALUES (1,'Trading'),(2,'Service'),(3,'Manufacturing'),(4,'Construction'),(5,'Hotel'),(6,'Restaurant');
/*!40000 ALTER TABLE `tbl_common_business_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_common_company`
--

DROP TABLE IF EXISTS `tbl_common_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_common_company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `companyName` varchar(200) DEFAULT NULL,
  `mailingAddress` varchar(45) DEFAULT NULL,
  `mobileNo` int(11) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `website` varchar(45) DEFAULT NULL,
  `fnYrStart` date DEFAULT NULL,
  `bookYrStart` date DEFAULT NULL,
  `businessType` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_common_company`
--

LOCK TABLES `tbl_common_company` WRITE;
/*!40000 ALTER TABLE `tbl_common_company` DISABLE KEYS */;
INSERT INTO `tbl_common_company` VALUES (1,'Jigme Autoworks','thimphu',848234,'hapsdugay@gmail.com','www.autgaautocares.com','2021-01-01','2021-12-31',1);
/*!40000 ALTER TABLE `tbl_common_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_financial_year_setup`
--

DROP TABLE IF EXISTS `tbl_financial_year_setup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_financial_year_setup` (
  `financialYearId` int(11) NOT NULL,
  `financialYearFrom` date NOT NULL,
  `financialYearTo` date NOT NULL,
  `status` char(1) NOT NULL,
  `createdBy` varchar(45) NOT NULL,
  `createdDate` datetime NOT NULL,
  `companyId` int(11) NOT NULL,
  PRIMARY KEY (`financialYearId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_financial_year_setup`
--

LOCK TABLES `tbl_financial_year_setup` WRITE;
/*!40000 ALTER TABLE `tbl_financial_year_setup` DISABLE KEYS */;
INSERT INTO `tbl_financial_year_setup` VALUES (2,'2020-01-01','2020-12-31','I','admin','2021-02-24 00:00:00',1),(9,'2021-01-01','2021-12-31','A','admin','2021-02-24 00:00:00',1);
/*!40000 ALTER TABLE `tbl_financial_year_setup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_hr_employeeadvance`
--

DROP TABLE IF EXISTS `tbl_hr_employeeadvance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_hr_employeeadvance` (
  `id` int(11) NOT NULL,
  `advanceDate` date NOT NULL,
  `empId` varchar(100) NOT NULL,
  `amount` double NOT NULL,
  `paidIn` int(11) NOT NULL,
  `createdBy` varchar(45) NOT NULL,
  `createdDate` date NOT NULL,
  `companyId` int(11) NOT NULL,
  `voucherNo` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_hr_employeeadvance`
--

LOCK TABLES `tbl_hr_employeeadvance` WRITE;
/*!40000 ALTER TABLE `tbl_hr_employeeadvance` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_hr_employeeadvance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_hr_employeesetup`
--

DROP TABLE IF EXISTS `tbl_hr_employeesetup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_hr_employeesetup` (
  `empId` varchar(200) NOT NULL,
  `empName` varchar(200) NOT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `cidNo` varchar(200) NOT NULL,
  `contactNo` varchar(200) NOT NULL,
  `tpnNo` varchar(200) DEFAULT NULL,
  `dateOfAppointment` date DEFAULT NULL,
  `basicSalary` double DEFAULT NULL,
  `post` varchar(200) NOT NULL,
  `incrementAmount` double DEFAULT NULL,
  `incrementEffectDate` date DEFAULT NULL,
  `serviceType` int(11) DEFAULT NULL,
  `allowance` double DEFAULT NULL,
  `emailAddress` varchar(200) NOT NULL,
  `accNo` varchar(200) NOT NULL,
  `village` varchar(200) NOT NULL,
  `gewog` varchar(200) NOT NULL,
  `dzongkhag` varchar(200) NOT NULL,
  `companyId` int(11) NOT NULL,
  `createdBy` varchar(45) NOT NULL,
  `createdDate` date NOT NULL,
  `pF` double DEFAULT NULL,
  `gIS` double DEFAULT NULL,
  `cost` int(11) NOT NULL COMMENT '1=Production(Direct) and 2= General(Indirect)',
  PRIMARY KEY (`empId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_hr_employeesetup`
--

LOCK TABLES `tbl_hr_employeesetup` WRITE;
/*!40000 ALTER TABLE `tbl_hr_employeesetup` DISABLE KEYS */;
INSERT INTO `tbl_hr_employeesetup` VALUES ('A11516001283K','Karma','2021-01-01','11516001283','17490773','T12311','2021-01-01',34000,'Software Developer',10000,'2021-01-01',1,1000,'hapsdugay@gmail.com','1234567','jsajAHDS','sadjfiasdjfi','jsdfjshdfj',1,'admin','2021-05-01',200,1000,1),('A11516001283P','Pema Wangmo','2021-01-01','11516001283','17490773','T12311','2021-01-01',26000,'Software Developer',0,NULL,1,1000,'hapsdugay@gmail.com','1232344','jsajAHDS','sadjfiasdjfi','jsdfjshdfj',1,'admin','2021-05-01',100,200,1),('A11516001285S','Sonam Dorji','2021-01-01','11516001285','17490773','T12312','2021-01-01',26000,'Manager',NULL,'2021-01-01',1,10000,'dorjijigme32@gmail.com','123123','jsajAHDS','sadjfiasdjfi','jsdfjshdfj',1,'admin','2021-05-01',500,500,2),('A123123123D','Dugay Dorji','2021-01-01','123123123','17490773','','2021-01-01',34000,'Software Developer',10000,'2021-01-01',1,1000,'hapsdugay@gmail.com','1234567','jsajAHDS','sadjfiasdjfi','jsdfjshdfj',1,'admin','2021-05-06',200,1000,1);
/*!40000 ALTER TABLE `tbl_hr_employeesetup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_hr_other_remittance`
--

DROP TABLE IF EXISTS `tbl_hr_other_remittance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_hr_other_remittance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `salarySheetId` int(11) DEFAULT NULL,
  `bankLedgerId` varchar(45) DEFAULT NULL,
  `companyId` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `financialYearId` int(11) DEFAULT NULL,
  `createdDate` date DEFAULT NULL,
  `createdBy` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_hr_other_remittance`
--

LOCK TABLES `tbl_hr_other_remittance` WRITE;
/*!40000 ALTER TABLE `tbl_hr_other_remittance` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_hr_other_remittance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_hr_salary_remittance`
--

DROP TABLE IF EXISTS `tbl_hr_salary_remittance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_hr_salary_remittance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `salarySheetId` int(11) DEFAULT NULL,
  `bankLedgerId` varchar(250) DEFAULT NULL,
  `companyId` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `financialYearId` int(11) DEFAULT NULL,
  `createdBy` varchar(45) DEFAULT NULL,
  `createdDate` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_hr_salary_remittance`
--

LOCK TABLES `tbl_hr_salary_remittance` WRITE;
/*!40000 ALTER TABLE `tbl_hr_salary_remittance` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_hr_salary_remittance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_hr_salary_sheet`
--

DROP TABLE IF EXISTS `tbl_hr_salary_sheet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_hr_salary_sheet` (
  `salarySheetId` int(11) NOT NULL,
  `monthId` int(11) NOT NULL,
  `financialYearId` int(11) NOT NULL,
  `companyId` int(11) NOT NULL,
  `empId` varchar(100) NOT NULL,
  `basicSalary` double NOT NULL,
  `allowance` double NOT NULL,
  `deduction` double DEFAULT NULL,
  `grossSalary` double NOT NULL,
  `pF` double NOT NULL,
  `gIS` double NOT NULL,
  `netSalary` double NOT NULL,
  `tDS` double NOT NULL,
  `hC` double NOT NULL,
  `advance` double NOT NULL,
  `totalRecovery` double NOT NULL,
  `takeHome` double NOT NULL,
  `createdDate` date NOT NULL,
  `createdBy` varchar(100) NOT NULL,
  PRIMARY KEY (`salarySheetId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_hr_salary_sheet`
--

LOCK TABLES `tbl_hr_salary_sheet` WRITE;
/*!40000 ALTER TABLE `tbl_hr_salary_sheet` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_hr_salary_sheet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_hr_service_type`
--

DROP TABLE IF EXISTS `tbl_hr_service_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_hr_service_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serviceType` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_hr_service_type`
--

LOCK TABLES `tbl_hr_service_type` WRITE;
/*!40000 ALTER TABLE `tbl_hr_service_type` DISABLE KEYS */;
INSERT INTO `tbl_hr_service_type` VALUES (1,'Regular'),(2,'Contract');
/*!40000 ALTER TABLE `tbl_hr_service_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_hr_statutory_remittance`
--

DROP TABLE IF EXISTS `tbl_hr_statutory_remittance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_hr_statutory_remittance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `salarySheetId` int(11) DEFAULT NULL,
  `bankLedgerId` varchar(45) DEFAULT NULL,
  `companyId` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `financialYearId` int(11) DEFAULT NULL,
  `createdDate` date DEFAULT NULL,
  `createdBy` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_hr_statutory_remittance`
--

LOCK TABLES `tbl_hr_statutory_remittance` WRITE;
/*!40000 ALTER TABLE `tbl_hr_statutory_remittance` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_hr_statutory_remittance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_inv_discount`
--

DROP TABLE IF EXISTS `tbl_inv_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_inv_discount` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `receiptNo` varchar(45) NOT NULL,
  `discountAmount` double NOT NULL,
  `saleDate` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_inv_discount`
--

LOCK TABLES `tbl_inv_discount` WRITE;
/*!40000 ALTER TABLE `tbl_inv_discount` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_inv_discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_inv_item_code_counter`
--

DROP TABLE IF EXISTS `tbl_inv_item_code_counter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_inv_item_code_counter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `itemCodeCounter` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_inv_item_code_counter`
--

LOCK TABLES `tbl_inv_item_code_counter` WRITE;
/*!40000 ALTER TABLE `tbl_inv_item_code_counter` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_inv_item_code_counter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_inv_purchase`
--

DROP TABLE IF EXISTS `tbl_inv_purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_inv_purchase` (
  `purchaseId` int(11) NOT NULL AUTO_INCREMENT,
  `purchaseDate` datetime NOT NULL,
  `purchaseInvoiceNo` varchar(45) NOT NULL,
  `brandId` int(11) DEFAULT NULL,
  `itemCode` varchar(45) NOT NULL,
  `qty` int(11) NOT NULL,
  `costPrice` double NOT NULL,
  `sellingPrice` double NOT NULL,
  `locationId` varchar(50) NOT NULL,
  `itemName` varchar(100) NOT NULL,
  `partNo` varchar(200) DEFAULT NULL,
  `companyId` int(11) DEFAULT NULL,
  `financialYearId` int(11) DEFAULT NULL,
  `spillOverQty` int(11) DEFAULT NULL,
  `setDate` date NOT NULL,
  `createdBy` varchar(45) NOT NULL,
  `type` varchar(100) NOT NULL,
  PRIMARY KEY (`purchaseId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_inv_purchase`
--

LOCK TABLES `tbl_inv_purchase` WRITE;
/*!40000 ALTER TABLE `tbl_inv_purchase` DISABLE KEYS */;
INSERT INTO `tbl_inv_purchase` VALUES (1,'2021-05-12 00:00:00','123123',1,'TOY10',10,200,250,'31','TOY : Break Pat','123123',1,9,0,'2021-05-12','admin','Prado');
/*!40000 ALTER TABLE `tbl_inv_purchase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_inv_purchase_a`
--

DROP TABLE IF EXISTS `tbl_inv_purchase_a`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_inv_purchase_a` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `purchaseId` int(11) NOT NULL,
  `purchaseDate` date NOT NULL,
  `purchaseInvoiceNo` varchar(45) NOT NULL,
  `itemCode` varchar(45) NOT NULL,
  `qty` int(11) NOT NULL,
  `costPrice` double NOT NULL,
  `sellingPrice` double NOT NULL,
  `isSpillOver` char(1) DEFAULT NULL,
  `createdBy` varchar(45) NOT NULL,
  `setDate` date NOT NULL,
  `type` varchar(100) NOT NULL,
  `purchaseVoucherNo` int(11) NOT NULL,
  `isCash` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_inv_purchase_a`
--

LOCK TABLES `tbl_inv_purchase_a` WRITE;
/*!40000 ALTER TABLE `tbl_inv_purchase_a` DISABLE KEYS */;
INSERT INTO `tbl_inv_purchase_a` VALUES (1,1,'2021-05-12','123123','TOY10',10,200,250,'N','admin','2021-05-12','Prado',1,1);
/*!40000 ALTER TABLE `tbl_inv_purchase_a` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_inv_purchase_credit_supplier`
--

DROP TABLE IF EXISTS `tbl_inv_purchase_credit_supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_inv_purchase_credit_supplier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `purchaseId` int(11) NOT NULL,
  `supplierId` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_inv_purchase_credit_supplier`
--

LOCK TABLES `tbl_inv_purchase_credit_supplier` WRITE;
/*!40000 ALTER TABLE `tbl_inv_purchase_credit_supplier` DISABLE KEYS */;
INSERT INTO `tbl_inv_purchase_credit_supplier` VALUES (1,2,1);
/*!40000 ALTER TABLE `tbl_inv_purchase_credit_supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_inv_purchase_ledger`
--

DROP TABLE IF EXISTS `tbl_inv_purchase_ledger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_inv_purchase_ledger` (
  `purchaseLedgerId` int(11) NOT NULL AUTO_INCREMENT,
  `purchaseId` int(11) DEFAULT NULL,
  `purchaseDate` date NOT NULL,
  `purchaseInvoiceNo` varchar(45) NOT NULL,
  `brandId` int(11) DEFAULT NULL,
  `itemCode` varchar(45) NOT NULL,
  `qty` int(11) NOT NULL,
  `costPrice` double NOT NULL,
  `sellingPrice` double NOT NULL,
  `locationId` varchar(50) NOT NULL,
  `itemName` varchar(100) NOT NULL,
  `companyId` int(11) DEFAULT NULL,
  `financialYearId` int(11) DEFAULT NULL,
  `spillOverQty` int(11) DEFAULT NULL,
  `setDate` date NOT NULL,
  `createdBy` varchar(45) NOT NULL,
  PRIMARY KEY (`purchaseLedgerId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_inv_purchase_ledger`
--

LOCK TABLES `tbl_inv_purchase_ledger` WRITE;
/*!40000 ALTER TABLE `tbl_inv_purchase_ledger` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_inv_purchase_ledger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_inv_purchase_ledger_a`
--

DROP TABLE IF EXISTS `tbl_inv_purchase_ledger_a`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_inv_purchase_ledger_a` (
  `purchaseLedgerAuditId` int(11) NOT NULL AUTO_INCREMENT,
  `id` int(11) NOT NULL,
  `purchaseInvoiceNo` varchar(45) NOT NULL,
  `purchaseId` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `costPrice` double NOT NULL,
  `sellingPrice` double NOT NULL,
  `itemCode` varchar(45) NOT NULL,
  `purchaseDate` date NOT NULL,
  `isSpillOver` char(1) DEFAULT NULL,
  `createdBy` varchar(45) NOT NULL,
  `setDate` date NOT NULL,
  PRIMARY KEY (`purchaseLedgerAuditId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_inv_purchase_ledger_a`
--

LOCK TABLES `tbl_inv_purchase_ledger_a` WRITE;
/*!40000 ALTER TABLE `tbl_inv_purchase_ledger_a` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_inv_purchase_ledger_a` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_inv_receipt_counter`
--

DROP TABLE IF EXISTS `tbl_inv_receipt_counter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_inv_receipt_counter` (
  `id` int(11) NOT NULL,
  `receiptSerialCounter` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_inv_receipt_counter`
--

LOCK TABLES `tbl_inv_receipt_counter` WRITE;
/*!40000 ALTER TABLE `tbl_inv_receipt_counter` DISABLE KEYS */;
INSERT INTO `tbl_inv_receipt_counter` VALUES (1,14);
/*!40000 ALTER TABLE `tbl_inv_receipt_counter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_inv_replace_item`
--

DROP TABLE IF EXISTS `tbl_inv_replace_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_inv_replace_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `itemCode` varchar(11) DEFAULT NULL,
  `replaceQty` int(11) DEFAULT NULL,
  `setDate` date DEFAULT NULL,
  `createdBy` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_inv_replace_item`
--

LOCK TABLES `tbl_inv_replace_item` WRITE;
/*!40000 ALTER TABLE `tbl_inv_replace_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_inv_replace_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_inv_return_item`
--

DROP TABLE IF EXISTS `tbl_inv_return_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_inv_return_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `itemCode` varchar(11) NOT NULL,
  `returnQty` int(11) NOT NULL,
  `setDate` date NOT NULL,
  `createdBy` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_inv_return_item`
--

LOCK TABLES `tbl_inv_return_item` WRITE;
/*!40000 ALTER TABLE `tbl_inv_return_item` DISABLE KEYS */;
INSERT INTO `tbl_inv_return_item` VALUES (1,'MU3',1,'2021-04-28','admin'),(2,'mu3',1,'2021-04-28','admin');
/*!40000 ALTER TABLE `tbl_inv_return_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_inv_sale_record`
--

DROP TABLE IF EXISTS `tbl_inv_sale_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_inv_sale_record` (
  `saleRecordId` int(11) NOT NULL AUTO_INCREMENT,
  `saleDate` datetime DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `companyId` int(11) DEFAULT NULL,
  `receiptMemoNo` varchar(45) NOT NULL,
  `financialYearId` int(11) DEFAULT NULL,
  `setDate` date DEFAULT NULL,
  `createdBy` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`saleRecordId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_inv_sale_record`
--

LOCK TABLES `tbl_inv_sale_record` WRITE;
/*!40000 ALTER TABLE `tbl_inv_sale_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_inv_sale_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_inv_sale_record_detail`
--

DROP TABLE IF EXISTS `tbl_inv_sale_record_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_inv_sale_record_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `saleRecordId` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `sellingPrice` double NOT NULL,
  `itemCode` varchar(45) NOT NULL,
  `purchaseInvoiceNo` varchar(45) NOT NULL,
  `voucherNo` int(11) DEFAULT NULL,
  `setDate` date NOT NULL,
  `createdBy` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_inv_sale_record_detail`
--

LOCK TABLES `tbl_inv_sale_record_detail` WRITE;
/*!40000 ALTER TABLE `tbl_inv_sale_record_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_inv_sale_record_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_inv_sale_record_detail_ledger`
--

DROP TABLE IF EXISTS `tbl_inv_sale_record_detail_ledger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_inv_sale_record_detail_ledger` (
  `saleRecordDetailLedgerId` int(11) NOT NULL AUTO_INCREMENT,
  `id` int(11) NOT NULL DEFAULT '0',
  `saleRecordId` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `sellingPrice` double NOT NULL,
  `itemCode` varchar(45) NOT NULL,
  `purchaseInvoiceNo` varchar(45) NOT NULL,
  `setDate` date NOT NULL,
  `createdBy` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`saleRecordDetailLedgerId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_inv_sale_record_detail_ledger`
--

LOCK TABLES `tbl_inv_sale_record_detail_ledger` WRITE;
/*!40000 ALTER TABLE `tbl_inv_sale_record_detail_ledger` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_inv_sale_record_detail_ledger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_inv_sale_record_ledger`
--

DROP TABLE IF EXISTS `tbl_inv_sale_record_ledger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_inv_sale_record_ledger` (
  `saleRecordLedgerId` int(11) NOT NULL AUTO_INCREMENT,
  `saleRecordId` int(11) DEFAULT NULL,
  `saleDate` date DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `companyId` int(11) DEFAULT NULL,
  `receiptMemoNo` varchar(45) NOT NULL,
  `financialYearId` int(11) DEFAULT NULL,
  `setDate` date DEFAULT NULL,
  `createdBy` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`saleRecordLedgerId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_inv_sale_record_ledger`
--

LOCK TABLES `tbl_inv_sale_record_ledger` WRITE;
/*!40000 ALTER TABLE `tbl_inv_sale_record_ledger` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_inv_sale_record_ledger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_inv_supplier`
--

DROP TABLE IF EXISTS `tbl_inv_supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_inv_supplier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `supplierName` varchar(200) NOT NULL,
  `address` varchar(150) NOT NULL,
  `email` varchar(150) DEFAULT NULL,
  `contactNo` int(11) NOT NULL,
  `companyId` int(11) NOT NULL,
  `financialYearId` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_inv_supplier`
--

LOCK TABLES `tbl_inv_supplier` WRITE;
/*!40000 ALTER TABLE `tbl_inv_supplier` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_inv_supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_item`
--

DROP TABLE IF EXISTS `tbl_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_item` (
  `itemid` int(11) NOT NULL AUTO_INCREMENT,
  `itemcategoryid` int(11) NOT NULL,
  `itemqty` int(11) NOT NULL,
  `recevieddate` date NOT NULL,
  `UserId` varchar(200) NOT NULL,
  `itemstatus` char(1) NOT NULL,
  PRIMARY KEY (`itemid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='item table store the all the stock item and its categories into item category';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_item`
--

LOCK TABLES `tbl_item` WRITE;
/*!40000 ALTER TABLE `tbl_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_item_code`
--

DROP TABLE IF EXISTS `tbl_item_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_item_code` (
  `brandId` int(11) NOT NULL,
  `serialNo` varchar(45) NOT NULL,
  `brandName` varchar(45) NOT NULL,
  `brandPrefix` varchar(45) NOT NULL,
  `companyId` int(11) DEFAULT NULL,
  `remarks` text,
  `createdBy` varchar(100) DEFAULT NULL,
  `setDate` date DEFAULT NULL,
  PRIMARY KEY (`brandId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_item_code`
--

LOCK TABLES `tbl_item_code` WRITE;
/*!40000 ALTER TABLE `tbl_item_code` DISABLE KEYS */;
INSERT INTO `tbl_item_code` VALUES (1,'11','TOYOTA','TOY',1,'TOYOTA','admin','2021-03-20'),(2,'1','HYUNDAI','HY',1,'HYUNDAI','admin','2021-03-20'),(3,'1','MAHINDRA','MA',1,'MAHINDRA','admin','2021-03-21'),(4,'4','MARUTI','MU',1,'MARUTI','admin','2021-03-22'),(5,'1','BUSH','BU',1,'BUSH','admin','2021-03-23'),(6,'1','CABLES','CA',1,'CABLES','admin','2021-03-24'),(7,'1','LUBRICANTS','LU ',1,'LUBRICANTS','admin','2021-03-25'),(8,'1','ACCESSORIES','AS',1,'ACCESSORIES','admin','2021-03-26'),(9,'1','TATA','TA',1,'dgsdfgdsf','admin','2021-02-24'),(10,'4','TOYOTA','TOY',2,'aysdgaysd','admin','2021-02-24');
/*!40000 ALTER TABLE `tbl_item_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_locations`
--

DROP TABLE IF EXISTS `tbl_locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_locations` (
  `location_id` int(11) NOT NULL AUTO_INCREMENT,
  `location` varchar(45) DEFAULT NULL,
  `uploaded` datetime DEFAULT NULL,
  PRIMARY KEY (`location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_locations`
--

LOCK TABLES `tbl_locations` WRITE;
/*!40000 ALTER TABLE `tbl_locations` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_locationsetup`
--

DROP TABLE IF EXISTS `tbl_locationsetup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_locationsetup` (
  `locationSetUpId` int(11) NOT NULL AUTO_INCREMENT,
  `locationId` varchar(45) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `companyId` int(11) NOT NULL,
  PRIMARY KEY (`locationSetUpId`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_locationsetup`
--

LOCK TABLES `tbl_locationsetup` WRITE;
/*!40000 ALTER TABLE `tbl_locationsetup` DISABLE KEYS */;
INSERT INTO `tbl_locationsetup` VALUES (1,'A','TOYOTA',0),(2,'B','TOYOTA',0),(3,'C','TOYOTA',0),(4,'D','TOYOTA',0),(5,'E','TOYOTA',0),(6,'F','HYUNDAI',0),(7,'G','HYUNDAI',0),(8,'H','MAHINDRA',0),(9,'I','MAHINDRA',0),(10,'J','MAHINDRA',0),(11,'K','MAHINDRA',0),(12,'L','MAHINDRA',0),(13,'M','MAHINDRA',0),(14,'N','MAHINDRA',0),(15,'O','MAHINDRA',0),(16,'P','MARUTI',0),(17,'Q','MARUTI',0),(18,'R','MARUTI',0),(19,'ZD','BUSH (MARUTI AND MAHINDRA)',0),(20,'ZE','PAINTS',0),(21,'ZF','ASSOCERIES ',0),(22,'ZG','ASSOCERIES ',0),(23,'ZH','CABLES',0),(24,'ZI','LUBRICANTS',0),(25,'ZJ','LUBRICANTS',0),(26,'ZK','NUT AND BOLTS',0),(27,'ZL','OIL SEALS',0),(28,'ZM','SMALL PARTS ',0),(29,'NO LOCATION ','No Location ',0),(30,'longg','1234',2),(31,'TOY1','Toyota ',1),(32,'MA1','Mahindra ',2);
/*!40000 ALTER TABLE `tbl_locationsetup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_purchase_detail`
--

DROP TABLE IF EXISTS `tbl_purchase_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_purchase_detail` (
  `purchaseDetailId` int(11) NOT NULL AUTO_INCREMENT,
  `purcahseId` int(11) NOT NULL,
  `itemCategoryId` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `rate` double NOT NULL,
  PRIMARY KEY (`purchaseDetailId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_purchase_detail`
--

LOCK TABLES `tbl_purchase_detail` WRITE;
/*!40000 ALTER TABLE `tbl_purchase_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_purchase_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_receiptcounter`
--

DROP TABLE IF EXISTS `tbl_receiptcounter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_receiptcounter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reciptserial` int(5) NOT NULL,
  `autoMobileSerial` int(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_receiptcounter`
--

LOCK TABLES `tbl_receiptcounter` WRITE;
/*!40000 ALTER TABLE `tbl_receiptcounter` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_receiptcounter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_screen`
--

DROP TABLE IF EXISTS `tbl_screen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_screen` (
  `screenId` int(11) NOT NULL,
  `screenName` varchar(150) NOT NULL,
  `screenUrl` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`screenId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_screen`
--

LOCK TABLES `tbl_screen` WRITE;
/*!40000 ALTER TABLE `tbl_screen` DISABLE KEYS */;
INSERT INTO `tbl_screen` VALUES (1,'User Creation','createUser'),(2,'User Access Permission Setup','userAccessPermission'),(3,'ledger','ledger'),(4,'Money Receipt','moneyReceipt'),(5,'Sale Invoice Generation','saleInvoiceGeneration'),(6,'Voucher Creation','voucherCreation'),(7,'Profit And Loss Report','accProfitAndLossReport'),(8,' Balance Sheet Report','accBalanceSheetReport'),(9,'Trial Balance','accTrialBalance'),(10,'Cash Flow','accCashFlow'),(11,'Sundry Debtors','ledgerGroupList'),(12,'Location SetUp','locationSetUp'),(13,'Supplier Setup','supplierSetup'),(14,'Received Item','receivedItem'),(15,'View Item','viewItem'),(16,'Sale Item','saleItem'),(17,'Sale Record','saleRecord'),(18,'Return Item','returnItem'),(19,'Barcode','barcode'),(20,'Financial Year Setup','financialYearSetup');
/*!40000 ALTER TABLE `tbl_screen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_st_tds_slab`
--

DROP TABLE IF EXISTS `tbl_st_tds_slab`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_st_tds_slab` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fromAmount` double NOT NULL,
  `toAmount` double NOT NULL,
  `tDSAmount` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1426 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_st_tds_slab`
--

LOCK TABLES `tbl_st_tds_slab` WRITE;
/*!40000 ALTER TABLE `tbl_st_tds_slab` DISABLE KEYS */;
INSERT INTO `tbl_st_tds_slab` VALUES (1,0,25000,0),(2,25001,25100,10),(3,25101,25200,20),(4,25201,25300,30),(5,25301,25400,40),(6,25401,25500,50),(7,25501,25600,60),(8,25601,25700,70),(9,25701,25800,80),(10,25801,25900,90),(11,25901,26000,100),(12,26001,26100,110),(13,26101,26200,120),(14,26201,26300,130),(15,26301,26400,140),(16,26401,26500,150),(17,26501,26600,160),(18,26601,26700,170),(19,26701,26800,180),(20,26801,26900,190),(21,26901,27000,200),(22,27001,27100,210),(23,27101,27200,220),(24,27201,27300,230),(25,27301,27400,240),(26,27401,27500,250),(27,27501,27600,260),(28,27601,27700,270),(29,27701,27800,280),(30,27801,27900,290),(31,27901,28000,300),(32,28001,28100,310),(33,28101,28200,320),(34,28201,28300,330),(35,28301,28400,340),(36,28401,28500,350),(37,28501,28600,360),(38,28601,28700,370),(39,28701,28800,380),(40,28801,28900,390),(41,28901,29000,400),(42,29001,29100,410),(43,29101,29200,420),(44,29201,29300,430),(45,29301,29400,440),(46,29401,29500,450),(47,29501,29600,460),(48,29601,29700,470),(49,29701,29800,480),(50,29801,29900,490),(51,29901,30000,500),(52,30001,30100,510),(53,30101,30200,520),(54,30201,30300,530),(55,30301,30400,540),(56,30401,30500,550),(57,30501,30600,560),(58,30601,30700,570),(59,30701,30800,580),(60,30801,30900,590),(61,30901,31000,600),(62,31001,31100,610),(63,31101,31200,620),(64,31201,31300,630),(65,31301,31400,640),(66,31401,31500,650),(67,31501,31600,660),(68,31601,31700,670),(69,31701,31800,680),(70,31801,31900,690),(71,31901,32000,700),(72,32001,32100,710),(73,32101,32200,720),(74,32201,32300,730),(75,32301,32400,740),(76,32401,32500,750),(77,32501,32600,760),(78,32601,32700,770),(79,32701,32800,780),(80,32801,32900,790),(81,32901,33000,800),(82,33001,33100,810),(83,33101,33200,820),(84,33201,33300,830),(85,33301,33400,843),(86,33401,33500,858),(87,33501,33600,873),(88,33601,33700,888),(89,33701,33800,903),(90,33801,33900,918),(91,33901,34000,933),(92,34001,34100,948),(93,34101,34200,963),(94,34201,34300,978),(95,34301,34400,993),(96,34401,34500,1008),(97,34501,34600,1023),(98,34601,34700,1038),(99,34701,34800,1053),(100,34801,34900,1068),(101,34901,35000,1083),(102,35001,35100,1098),(103,35101,35200,1113),(104,35201,35300,1128),(105,35301,35400,1143),(106,35401,35500,1158),(107,35501,35600,1173),(108,35601,35700,1188),(109,35701,35800,1203),(110,35801,35900,1218),(111,35901,36000,1233),(112,36001,36100,1248),(113,36101,36200,1263),(114,36201,36300,1278),(115,36301,36400,1293),(116,36401,36500,1308),(117,36501,36600,1323),(118,36601,36700,1338),(119,36701,36800,1353),(120,36801,36900,1368),(121,36901,37000,1383),(122,37001,37100,1398),(123,37101,37200,1413),(124,37201,37300,1428),(125,37301,37400,1443),(126,37401,37500,1458),(127,37501,37600,1473),(128,37601,37700,1488),(129,37701,37800,1503),(130,37801,37900,1518),(131,37901,38000,1533),(132,38001,38100,1548),(133,38101,38200,1563),(134,38201,38300,1578),(135,38301,38400,1593),(136,38401,38500,1608),(137,38501,38600,1623),(138,38601,38700,1638),(139,38701,38800,1653),(140,38801,38900,1668),(141,38901,39000,1683),(142,39001,39100,1698),(143,39101,39200,1713),(144,39201,39300,1728),(145,39301,39400,1743),(146,39401,39500,1758),(147,39501,39600,1773),(148,39601,39700,1788),(149,39701,39800,1803),(150,39801,39900,1818),(151,39901,40000,1833),(152,40001,40100,1848),(153,40101,40200,1863),(154,40201,40300,1878),(155,40301,40400,1893),(156,40401,40500,1908),(157,40501,40600,1923),(158,40601,40700,1938),(159,40701,40800,1953),(160,40801,40900,1968),(161,40901,41000,1983),(162,41001,41100,1998),(163,41101,41200,2013),(164,41201,41300,2028),(165,41301,41400,2043),(166,41401,41500,2058),(167,41501,41600,2073),(168,41601,41700,2088),(169,41701,41800,2103),(170,41801,41900,2118),(171,41901,42000,2133),(172,42001,42100,2148),(173,42101,42200,2163),(174,42201,42300,2178),(175,42301,42400,2193),(176,42401,42500,2208),(177,42501,42600,2223),(178,42601,42700,2238),(179,42701,42800,2253),(180,42801,42900,2268),(181,42901,43000,2283),(182,43001,43100,2298),(183,43101,43200,2313),(184,43201,43300,2328),(185,43301,43400,2343),(186,43401,43500,2358),(187,43501,43600,2373),(188,43601,43700,2388),(189,43701,43800,2403),(190,43801,43900,2418),(191,43901,44000,2433),(192,44001,44100,2448),(193,44101,44200,2463),(194,44201,44300,2478),(195,44301,44400,2493),(196,44401,44500,2508),(197,44501,44600,2523),(198,44601,44700,2538),(199,44701,44800,2553),(200,44801,44900,2568),(201,44901,45000,2583),(202,45001,45100,2598),(203,45101,45200,2613),(204,45201,45300,2628),(205,45301,45400,2643),(206,45401,45500,2658),(207,45501,45600,2673),(208,45601,45700,2688),(209,45701,45800,2703),(210,45801,45900,2718),(211,45901,46000,2733),(212,46001,46100,2748),(213,46101,46200,2763),(214,46201,46300,2778),(215,46301,46400,2793),(216,46401,46500,2808),(217,46501,46600,2823),(218,46601,46700,2838),(219,46701,46800,2853),(220,46801,46900,2868),(221,46901,47000,2883),(222,47001,47100,2898),(223,47101,47200,2913),(224,47201,47300,2928),(225,47301,47400,2943),(226,47401,47500,2958),(227,47501,47600,2973),(228,47601,47700,2988),(229,47701,47800,3003),(230,47801,47900,3018),(231,47901,48000,3033),(232,48001,48100,3048),(233,48101,48200,3063),(234,48201,48300,3078),(235,48301,48400,3093),(236,48401,48500,3108),(237,48501,48600,3123),(238,48601,48700,3138),(239,48701,48800,3153),(240,48801,48900,3168),(241,48901,49000,3183),(242,49001,49100,3198),(243,49101,49200,3213),(244,49201,49300,3228),(245,49301,49400,3243),(246,49401,49500,3258),(247,49501,49600,3273),(248,49601,49700,3288),(249,49701,49800,3303),(250,49801,49900,3318),(251,49901,50000,3333),(252,50001,50100,3348),(253,50101,50200,3363),(254,50201,50300,3378),(255,50301,50400,3393),(256,50401,50500,3408),(257,50501,50600,3423),(258,50601,50700,3438),(259,50701,50800,3453),(260,50801,50900,3468),(261,50901,51000,3483),(262,51001,51100,3498),(263,51101,51200,3513),(264,51201,51300,3528),(265,51301,51400,3543),(266,51401,51500,3558),(267,51501,51600,3573),(268,51601,51700,3588),(269,51701,51800,3603),(270,51801,51900,3618),(271,51901,52000,3633),(272,52001,52100,3648),(273,52101,52200,3663),(274,52201,52300,3678),(275,52301,52400,3693),(276,52401,52500,3708),(277,52501,52600,3723),(278,52601,52700,3738),(279,52701,52800,3753),(280,52801,52900,3768),(281,52901,53000,3783),(282,53001,53100,3798),(283,53101,53200,3813),(284,53201,53300,3828),(285,53301,53400,3843),(286,53401,53500,3858),(287,53501,53600,3873),(288,53601,53700,3888),(289,53701,53800,3903),(290,53801,53900,3918),(291,53901,54000,3933),(292,54001,54100,3948),(293,54101,54200,3965),(294,54201,54300,3985),(295,54301,54400,4005),(296,54401,54500,4025),(297,54501,54600,4045),(298,54601,54700,4065),(299,54701,54800,4085),(300,54801,54900,4105),(301,54901,55000,4125),(302,55001,55100,4145),(303,55101,55200,4165),(304,55201,55300,4185),(305,55301,55400,4205),(306,55401,55500,4225),(307,55501,55600,4245),(308,55601,55700,4265),(309,55701,55800,4285),(310,55801,55900,4305),(311,55901,56000,4325),(312,56001,56100,4345),(313,56101,56200,4365),(314,56201,56300,4385),(315,56301,56400,4405),(316,56401,56500,4425),(317,56501,56600,4445),(318,56601,56700,4465),(319,56701,56800,4485),(320,56801,56900,4505),(321,56901,57000,4525),(322,57001,57100,4545),(323,57101,57200,4565),(324,57201,57300,4585),(325,57301,57400,4605),(326,57401,57500,4625),(327,57501,57600,4645),(328,57601,57700,4665),(329,57701,57800,4685),(330,57801,57900,4705),(331,57901,58000,4725),(332,58001,58100,4745),(333,58101,58200,4765),(334,58201,58300,4785),(335,58301,58400,4805),(336,58401,58500,4825),(337,58501,58600,4845),(338,58601,58700,4865),(339,58701,58800,4885),(340,58801,58900,4905),(341,58901,59000,4925),(342,59001,59100,4945),(343,59101,59200,4965),(344,59201,59300,4985),(345,59301,59400,5005),(346,59401,59500,5025),(347,59501,59600,5045),(348,59601,59700,5065),(349,59701,59800,5085),(350,59801,59900,5105),(351,59901,60000,5125),(352,60001,60100,5145),(353,60101,60200,5165),(354,60201,60300,5185),(355,60301,60400,5205),(356,60401,60500,5225),(357,60501,60600,5245),(358,60601,60700,5265),(359,60701,60800,5285),(360,60801,60900,5305),(361,60901,61000,5325),(362,61001,61100,5345),(363,61101,61200,5365),(364,61201,61300,5385),(365,61301,61400,5405),(366,61401,61500,5425),(367,61501,61600,5445),(368,61601,61700,5465),(369,61701,61800,5485),(370,61801,61900,5505),(371,61901,62000,5525),(372,62001,62100,5545),(373,62101,62200,5565),(374,62201,62300,5585),(375,62301,62400,5605),(376,62401,62500,5625),(377,62501,62600,5645),(378,62601,62700,5665),(379,62701,62800,5685),(380,62801,62900,5705),(381,62901,63000,5725),(382,63001,63100,5745),(383,63101,63200,5765),(384,63201,63300,5785),(385,63301,63400,5805),(386,63401,63500,5825),(387,63501,63600,5845),(388,63601,63700,5865),(389,63701,63800,5885),(390,63801,63900,5905),(391,63901,64000,5925),(392,64001,64100,5945),(393,64101,64200,5965),(394,64201,64300,5985),(395,64301,64400,6005),(396,64401,64500,6025),(397,64501,64600,6045),(398,64601,64700,6065),(399,64701,64800,6085),(400,64801,64900,6105),(401,64901,65000,6125),(402,65001,65100,6145),(403,65101,65200,6165),(404,65201,65300,6185),(405,65301,65400,6205),(406,65401,65500,6225),(407,65501,65600,6245),(408,65601,65700,6265),(409,65701,65800,6285),(410,65801,65900,6305),(411,65901,66000,6325),(412,66001,66100,6345),(413,66101,66200,6365),(414,66201,66300,6385),(415,66301,66400,6405),(416,66401,66500,6425),(417,66501,66600,6445),(418,66601,66700,6465),(419,66701,66800,6485),(420,66801,66900,6505),(421,66901,67000,6525),(422,67001,67100,6545),(423,67101,67200,6565),(424,67201,67300,6585),(425,67301,67400,6605),(426,67401,67500,6625),(427,67501,67600,6645),(428,67601,67700,6665),(429,67701,67800,6685),(430,67801,67900,6705),(431,67901,68000,6725),(432,68001,68100,6745),(433,68101,68200,6765),(434,68201,68300,6785),(435,68301,68400,6805),(436,68401,68500,6825),(437,68501,68600,6845),(438,68601,68700,6865),(439,68701,68800,6885),(440,68801,68900,6905),(441,68901,69000,6925),(442,69001,69100,6945),(443,69101,69200,6965),(444,69201,69300,6985),(445,69301,69400,7005),(446,69401,69500,7025),(447,69501,69600,7045),(448,69601,69700,7065),(449,69701,69800,7085),(450,69801,69900,7105),(451,69901,70000,7125),(452,70001,70100,7145),(453,70101,70200,7165),(454,70201,70300,7185),(455,70301,70400,7205),(456,70401,70500,7225),(457,70501,70600,7245),(458,70601,70700,7265),(459,70701,70800,7285),(460,70801,70900,7305),(461,70901,71000,7325),(462,71001,71100,7345),(463,71101,71200,7365),(464,71201,71300,7385),(465,71301,71400,7405),(466,71401,71500,7425),(467,71501,71600,7445),(468,71601,71700,7465),(469,71701,71800,7485),(470,71801,71900,7505),(471,71901,72000,7525),(472,72001,72100,7545),(473,72101,72200,7565),(474,72201,72300,7585),(475,72301,72400,7605),(476,72401,72500,7625),(477,72501,72600,7645),(478,72601,72700,7665),(479,72701,72800,7685),(480,72801,72900,7705),(481,72901,73000,7725),(482,73001,73100,7745),(483,73101,73200,7765),(484,73201,73300,7785),(485,73301,73400,7805),(486,73401,73500,7825),(487,73501,73600,7845),(488,73601,73700,7865),(489,73701,73800,7885),(490,73801,73900,7905),(491,73901,74000,7925),(492,74001,74100,7945),(493,74101,74200,7965),(494,74201,74300,7985),(495,74301,74400,8005),(496,74401,74500,8025),(497,74501,74600,8045),(498,74601,74700,8065),(499,74701,74800,8085),(500,74801,74900,8105),(501,74901,75000,8125),(502,75001,75100,8145),(503,75101,75200,8165),(504,75201,75300,8185),(505,75301,75400,8205),(506,75401,75500,8225),(507,75501,75600,8245),(508,75601,75700,8265),(509,75701,75800,8285),(510,75801,75900,8305),(511,75901,76000,8325),(512,76001,76100,8345),(513,76101,76200,8365),(514,76201,76300,8385),(515,76301,76400,8405),(516,76401,76500,8425),(517,76501,76600,8445),(518,76601,76700,8465),(519,76701,76800,8485),(520,76801,76900,8505),(521,76901,77000,8525),(522,77001,77100,8545),(523,77101,77200,8565),(524,77201,77300,8585),(525,77301,77400,8605),(526,77401,77500,8625),(527,77501,77600,8645),(528,77601,77700,8665),(529,77701,77800,8685),(530,77801,77900,8705),(531,77901,78000,8725),(532,78001,78100,8745),(533,78101,78200,8765),(534,78201,78300,8785),(535,78301,78400,8805),(536,78401,78500,8825),(537,78501,78600,8845),(538,78601,78700,8865),(539,78701,78800,8885),(540,78801,78900,8905),(541,78901,79000,8925),(542,79001,79100,8945),(543,79101,79200,8965),(544,79201,79300,8985),(545,79301,79400,9005),(546,79401,79500,9025),(547,79501,79600,9045),(548,79601,79700,9065),(549,79701,79800,9085),(550,79801,79900,9105),(551,79901,80000,9125),(552,80001,80100,9145),(553,80101,80200,9165),(554,80201,80300,9185),(555,80301,80400,9205),(556,80401,80500,9225),(557,80501,80600,9245),(558,80601,80700,9265),(559,80701,80800,9285),(560,80801,80900,9305),(561,80901,81000,9325),(562,81001,81100,9345),(563,81101,81200,9365),(564,81201,81300,9385),(565,81301,81400,9405),(566,81401,81500,9425),(567,81501,81600,9445),(568,81601,81700,9465),(569,81701,81800,9485),(570,81801,81900,9505),(571,81901,82000,9525),(572,82001,82100,9545),(573,82101,82200,9565),(574,82201,82300,9585),(575,82301,82400,9605),(576,82401,82500,9625),(577,82501,82600,9645),(578,82601,82700,9665),(579,82701,82800,9685),(580,82801,82900,9705),(581,82901,83000,9725),(582,83001,83100,9745),(583,83101,83200,9765),(584,83201,83300,9785),(585,83301,83400,9808),(586,83401,83500,9833),(587,83501,83600,9858),(588,83601,83700,9883),(589,83701,83800,9908),(590,83801,83900,9933),(591,83901,84000,9958),(592,84001,84100,9983),(593,84101,84200,10008),(594,84201,84300,10033),(595,84301,84400,10058),(596,84401,84500,10083),(597,84501,84600,10108),(598,84601,84700,10133),(599,84701,84800,10158),(600,84801,84900,10183),(601,84901,85000,10208),(602,85001,85100,10233),(603,85101,85200,10258),(604,85201,85300,10283),(605,85301,85400,10308),(606,85401,85500,10333),(607,85501,85600,10358),(608,85601,85700,10383),(609,85701,85800,10408),(610,85801,85900,10433),(611,85901,86000,10458),(612,86001,86100,10483),(613,86101,86200,10508),(614,86201,86300,10533),(615,86301,86400,10558),(616,86401,86500,10583),(617,86501,86600,10608),(618,86601,86700,10633),(619,86701,86800,10658),(620,86801,86900,10683),(621,86901,87000,10708),(622,87001,87100,10733),(623,87101,87200,10758),(624,87201,87300,10783),(625,87301,87400,10808),(626,87401,87500,10833),(627,87501,87600,10858),(628,87601,87700,10883),(629,87701,87800,10908),(630,87801,87900,10933),(631,87901,88000,10958),(632,88001,88100,10983),(633,88101,88200,11008),(634,88201,88300,11033),(635,88301,88400,11058),(636,88401,88500,11083),(637,88501,88600,11108),(638,88601,88700,11133),(639,88701,88800,11158),(640,88801,88900,11183),(641,88901,89000,11208),(642,89001,89100,11233),(643,89101,89200,11258),(644,89201,89300,11283),(645,89301,89400,11308),(646,89401,89500,11333),(647,89501,89600,11358),(648,89601,89700,11383),(649,89701,89800,11408),(650,89801,89900,11433),(651,89901,90000,11458),(652,90001,90100,11483),(653,90101,90200,11508),(654,90201,90300,11533),(655,90301,90400,11558),(656,90401,90500,11583),(657,90501,90600,11608),(658,90601,90700,11633),(659,90701,90800,11658),(660,90801,90900,11683),(661,90901,91000,11708),(662,91001,91100,11733),(663,91101,91200,11758),(664,91201,91300,11783),(665,91301,91400,11808),(666,91401,91500,11833),(667,91501,91600,11858),(668,91601,91700,11883),(669,91701,91800,11908),(670,91801,91900,11933),(671,91901,92000,11958),(672,92001,92100,11983),(673,92101,92200,12008),(674,92201,92300,12033),(675,92301,92400,12058),(676,92401,92500,12083),(677,92501,92600,12108),(678,92601,92700,12133),(679,92701,92800,12158),(680,92801,92900,12183),(681,92901,93000,12208),(682,93001,93100,12233),(683,93101,93200,12258),(684,93201,93300,12283),(685,93301,93400,12308),(686,93401,93500,12333),(687,93501,93600,12358),(688,93601,93700,12383),(689,93701,93800,12408),(690,93801,93900,12433),(691,93901,94000,12458),(692,94001,94100,12483),(693,94101,94200,12508),(694,94201,94300,12533),(695,94301,94400,12558),(696,94401,94500,12583),(697,94501,94600,12608),(698,94601,94700,12633),(699,94701,94800,12658),(700,94801,94900,12683),(701,94901,95000,12708),(702,95001,95100,12733),(703,95101,95200,12758),(704,95201,95300,12783),(705,95301,95400,12808),(706,95401,95500,12833),(707,95501,95600,12858),(708,95601,95700,12883),(709,95701,95800,12908),(710,95801,95900,12933),(711,95901,96000,12958),(712,96001,96100,12983),(713,96101,96200,13008),(714,96201,96300,13033),(715,96301,96400,13058),(716,96401,96500,13083),(717,96501,96600,13108),(718,96601,96700,13133),(719,96701,96800,13158),(720,96801,96900,13183),(721,96901,97000,13208),(722,97001,97100,13233),(723,97101,97200,13258),(724,97201,97300,13283),(725,97301,97400,13308),(726,97401,97500,13333),(727,97501,97600,13358),(728,97601,97700,13383),(729,97701,97800,13408),(730,97801,97900,13433),(731,97901,98000,13458),(732,98001,98100,13483),(733,98101,98200,13508),(734,98201,98300,13533),(735,98301,98400,13558),(736,98401,98500,13583),(737,98501,98600,13608),(738,98601,98700,13633),(739,98701,98800,13658),(740,98801,98900,13683),(741,98901,99000,13708),(742,99001,99100,13733),(743,99101,99200,13758),(744,99201,99300,13783),(745,99301,99400,13808),(746,99401,99500,13833),(747,99501,99600,13858),(748,99601,99700,13883),(749,99701,99800,13908),(750,99801,99900,13933),(751,99901,100000,13958),(752,100001,100100,13983),(753,100101,100200,14008),(754,100201,100300,14033),(755,100301,100400,14058),(756,100401,100500,14083),(757,100501,100600,14108),(758,100601,100700,14133),(759,100701,100800,14158),(760,100801,100900,14183),(761,100901,101000,14208),(762,101001,101100,14233),(763,101101,101200,14258),(764,101201,101300,14283),(765,101301,101400,14308),(766,101401,101500,14333),(767,101501,101600,14358),(768,101601,101700,14383),(769,101701,101800,14408),(770,101801,101900,14433),(771,101901,102000,14458),(772,102001,102100,14483),(773,102101,102200,14508),(774,102201,102300,14533),(775,102301,102400,14558),(776,102401,102500,14583),(777,102501,102600,14608),(778,102601,102700,14633),(779,102701,102800,14658),(780,102801,102900,14683),(781,102901,103000,14708),(782,103001,103100,14733),(783,103101,103200,14758),(784,103201,103300,14783),(785,103301,103400,14808),(786,103401,103500,14833),(787,103501,103600,14858),(788,103601,103700,14883),(789,103701,103800,14908),(790,103801,103900,14933),(791,103901,104000,14958),(792,104001,104100,14983),(793,104101,104200,15008),(794,104201,104300,15033),(795,104301,104400,15058),(796,104401,104500,15083),(797,104501,104600,15108),(798,104601,104700,15133),(799,104701,104800,15158),(800,104801,104900,15183),(801,104901,105000,15208),(802,105001,105100,15233),(803,105101,105200,15258),(804,105201,105300,15283),(805,105301,105400,15308),(806,105401,105500,15333),(807,105501,105600,15358),(808,105601,105700,15383),(809,105701,105800,15408),(810,105801,105900,15433),(811,105901,106000,15458),(812,106001,106100,15483),(813,106101,106200,15508),(814,106201,106300,15533),(815,106301,106400,15558),(816,106401,106500,15583),(817,106501,106600,15608),(818,106601,106700,15633),(819,106701,106800,15658),(820,106801,106900,15683),(821,106901,107000,15708),(822,107001,107100,15733),(823,107101,107200,15758),(824,107201,107300,15783),(825,107301,107400,15808),(826,107401,107500,15833),(827,107501,107600,15858),(828,107601,107700,15883),(829,107701,107800,15908),(830,107801,107900,15933),(831,107901,108000,15958),(832,108001,108100,15983),(833,108101,108200,16008),(834,108201,108300,16033),(835,108301,108400,16058),(836,108401,108500,16083),(837,108501,108600,16108),(838,108601,108700,16133),(839,108701,108800,16158),(840,108801,108900,16183),(841,108901,109000,16208),(842,109001,109100,16233),(843,109101,109200,16258),(844,109201,109300,16283),(845,109301,109400,16308),(846,109401,109500,16333),(847,109501,109600,16358),(848,109601,109700,16383),(849,109701,109800,16408),(850,109801,109900,16433),(851,109901,110000,16458),(852,110001,110100,16483),(853,110101,110200,16508),(854,110201,110300,16533),(855,110301,110400,16558),(856,110401,110500,16583),(857,110501,110600,16608),(858,110601,110700,16633),(859,110701,110800,16658),(860,110801,110900,16683),(861,110901,111000,16708),(862,111001,111100,16733),(863,111101,111200,16758),(864,111201,111300,16783),(865,111301,111400,16808),(866,111401,111500,16833),(867,111501,111600,16858),(868,111601,111700,16883),(869,111701,111800,16908),(870,111801,111900,16933),(871,111901,112000,16958),(872,112001,112100,16983),(873,112101,112200,17008),(874,112201,112300,17033),(875,112301,112400,17058),(876,112401,112500,17083),(877,112501,112600,17108),(878,112601,112700,17133),(879,112701,112800,17158),(880,112801,112900,17183),(881,112901,113000,17208),(882,113001,113100,17233),(883,113101,113200,17258),(884,113201,113300,17283),(885,113301,113400,17308),(886,113401,113500,17333),(887,113501,113600,17358),(888,113601,113700,17383),(889,113701,113800,17408),(890,113801,113900,17433),(891,113901,114000,17458),(892,114001,114100,17483),(893,114101,114200,17508),(894,114201,114300,17533),(895,114301,114400,17558),(896,114401,114500,17583),(897,114501,114600,17608),(898,114601,114700,17633),(899,114701,114800,17658),(900,114801,114900,17683),(901,114901,115000,17708),(902,115001,115100,17733),(903,115101,115200,17758),(904,115201,115300,17783),(905,115301,115400,17808),(906,115401,115500,17833),(907,115501,115600,17858),(908,115601,115700,17883),(909,115701,115800,17908),(910,115801,115900,17933),(911,115901,116000,17958),(912,116001,116100,17983),(913,116101,116200,18008),(914,116201,116300,18033),(915,116301,116400,18058),(916,116401,116500,18083),(917,116501,116600,18108),(918,116601,116700,18133),(919,116701,116800,18158),(920,116801,116900,18183),(921,116901,117000,18208),(922,117001,117100,18233),(923,117101,117200,18258),(924,117201,117300,18283),(925,117301,117400,18308),(926,117401,117500,18333),(927,117501,117600,18358),(928,117601,117700,18383),(929,117701,117800,18408),(930,117801,117900,18433),(931,117901,118000,18458),(932,118001,118100,18483),(933,118101,118200,18508),(934,118201,118300,18533),(935,118301,118400,18558),(936,118401,118500,18583),(937,118501,118600,18608),(938,118601,118700,18633),(939,118701,118800,18658),(940,118801,118900,18683),(941,118901,119000,18708),(942,119001,119100,18733),(943,119101,119200,18758),(944,119201,119300,18783),(945,119301,119400,18808),(946,119401,119500,18833),(947,119501,119600,18858),(948,119601,119700,18883),(949,119701,119800,18908),(950,119801,119900,18933),(951,119901,120000,18958),(952,120001,120100,18983),(953,120101,120200,19008),(954,120201,120300,19033),(955,120301,120400,19058),(956,120401,120500,19083),(957,120501,120600,19108),(958,120601,120700,19133),(959,120701,120800,19158),(960,120801,120900,19183),(961,120901,121000,19208),(962,121001,121100,19233),(963,121101,121200,19258),(964,121201,121300,19283),(965,121301,121400,19308),(966,121401,121500,19333),(967,121501,121600,19358),(968,121601,121700,19383),(969,121701,121800,19408),(970,121801,121900,19433),(971,121901,122000,19458),(972,122001,122100,19483),(973,122101,122200,19508),(974,122201,122300,19533),(975,122301,122400,19558),(976,122401,122500,19583),(977,122501,122600,19608),(978,122601,122700,19633),(979,122701,122800,19658),(980,122801,122900,19683),(981,122901,123000,19708),(982,123001,123100,19733),(983,123101,123200,19758),(984,123201,123300,19783),(985,123301,123400,19808),(986,123401,123500,19833),(987,123501,123600,19858),(988,123601,123700,19883),(989,123701,123800,19908),(990,123801,123900,19933),(991,123901,124000,19958),(992,124001,124100,19983),(993,124101,124200,20008),(994,124201,124300,20033),(995,124301,124400,20058),(996,124401,124500,20083),(997,124501,124600,20108),(998,124601,124700,20133),(999,124701,124800,20158),(1000,124801,124900,20183),(1001,124901,125000,20208),(1002,125001,125100,20238),(1003,125101,125200,20268),(1004,125201,125300,20298),(1005,125301,125400,20328),(1006,125401,125500,20358),(1007,125501,125600,20388),(1008,125601,125700,20418),(1009,125701,125800,20448),(1010,125801,125900,20478),(1011,125901,126000,20508),(1012,126001,126100,20538),(1013,126101,126200,20568),(1014,126201,126300,20598),(1015,126301,126400,20628),(1016,126401,126500,20658),(1017,126501,126600,20688),(1018,126601,126700,20718),(1019,126701,126800,20748),(1020,126801,126900,20778),(1021,126901,127000,20808),(1022,127001,127100,20838),(1023,127101,127200,20868),(1024,127201,127300,20898),(1025,127301,127400,20928),(1026,127401,127500,20958),(1027,127501,127600,20988),(1028,127601,127700,21018),(1029,127701,127800,21048),(1030,127801,127900,21078),(1031,127901,128000,21108),(1032,128001,128100,21138),(1033,128101,128200,21168),(1034,128201,128300,21198),(1035,128301,128400,21228),(1036,128401,128500,21258),(1037,128501,128600,21288),(1038,128601,128700,21318),(1039,128701,128800,21348),(1040,128801,128900,21378),(1041,128901,129000,21408),(1042,129001,129100,21438),(1043,129101,129200,21468),(1044,129201,129300,21498),(1045,129301,129400,21528),(1046,129401,129500,21558),(1047,129501,129600,21588),(1048,129601,129700,21618),(1049,129701,129800,21648),(1050,129801,129900,21678),(1051,129901,130000,21708),(1052,130001,130100,21738),(1053,130101,130200,21768),(1054,130201,130300,21798),(1055,130301,130400,21828),(1056,130401,130500,21858),(1057,130501,130600,21888),(1058,130601,130700,21918),(1059,130701,130800,21948),(1060,130801,130900,21978),(1061,130901,131000,22008),(1062,131001,131100,22038),(1063,131101,131200,22068),(1064,131201,131300,22098),(1065,131301,131400,22128),(1066,131401,131500,22158),(1067,131501,131600,22188),(1068,131601,131700,22218),(1069,131701,131800,22248),(1070,131801,131900,22278),(1071,131901,132000,22308),(1072,132001,132100,22338),(1073,132101,132200,22368),(1074,132201,132300,22398),(1075,132301,132400,22428),(1076,132401,132500,22458),(1077,132501,132600,22488),(1078,132601,132700,22518),(1079,132701,132800,22548),(1080,132801,132900,22578),(1081,132901,133000,22608),(1082,133001,133100,22638),(1083,133101,133200,22668),(1084,133201,133300,22698),(1085,133301,133400,22728),(1086,133401,133500,22758),(1087,133501,133600,22788),(1088,133601,133700,22818),(1089,133701,133800,22848),(1090,133801,133900,22878),(1091,133901,134000,22908),(1092,134001,134100,22938),(1093,134101,134200,22968),(1094,134201,134300,22998),(1095,134301,134400,23028),(1096,134401,134500,23058),(1097,134501,134600,23088),(1098,134601,134700,23118),(1099,134701,134800,23148),(1100,134801,134900,23178),(1101,134901,135000,23208),(1102,135001,135100,23238),(1103,135101,135200,23268),(1104,135201,135300,23298),(1105,135301,135400,23328),(1106,135401,135500,23358),(1107,135501,135600,23388),(1108,135601,135700,23418),(1109,135701,135800,23448),(1110,135801,135900,23478),(1111,135901,136000,23508),(1112,136001,136100,23538),(1113,136101,136200,23568),(1114,136201,136300,23598),(1115,136301,136400,23628),(1116,136401,136500,23658),(1117,136501,136600,23688),(1118,136601,136700,23718),(1119,136701,136800,23748),(1120,136801,136900,23778),(1121,136901,137000,23808),(1122,137001,137100,23838),(1123,137101,137200,23868),(1124,137201,137300,23898),(1125,137301,137400,23928),(1126,137401,137500,23958),(1127,137501,137600,23988),(1128,137601,137700,24018),(1129,137701,137800,24048),(1130,137801,137900,24078),(1131,137901,138000,24108),(1132,138001,138100,24138),(1133,138101,138200,24168),(1134,138201,138300,24198),(1135,138301,138400,24228),(1136,138401,138500,24258),(1137,138501,138600,24288),(1138,138601,138700,24318),(1139,138701,138800,24348),(1140,138801,138900,24378),(1141,138901,139000,24408),(1142,139001,139100,24438),(1143,139101,139200,24468),(1144,139201,139300,24498),(1145,139301,139400,24528),(1146,139401,139500,24558),(1147,139501,139600,24588),(1148,139601,139700,24618),(1149,139701,139800,24648),(1150,139801,139900,24678),(1151,139901,140000,24708),(1152,140001,140100,24738),(1153,140101,140200,24768),(1154,140201,140300,24798),(1155,140301,140400,24828),(1156,140401,140500,24858),(1157,140501,140600,24888),(1158,140601,140700,24918),(1159,140701,140800,24948),(1160,140801,140900,24978),(1161,140901,141000,25008),(1162,141001,141100,25038),(1163,141101,141200,25068),(1164,141201,141300,25098),(1165,141301,141400,25128),(1166,141401,141500,25158),(1167,141501,141600,25188),(1168,141601,141700,25218),(1169,141701,141800,25248),(1170,141801,141900,25278),(1171,141901,142000,25308),(1172,142001,142100,25338),(1173,142101,142200,25368),(1174,142201,142300,25398),(1175,142301,142400,25428),(1176,142401,142500,25458),(1177,142501,142600,25488),(1178,142601,142700,25518),(1179,142701,142800,25548),(1180,142801,142900,25578),(1181,142901,143000,25608),(1182,143001,143100,25638),(1183,143101,143200,25668),(1184,143201,143300,25698),(1185,143301,143400,25728),(1186,143401,143500,25758),(1187,143501,143600,25788),(1188,143601,143700,25818),(1189,143701,143800,25848),(1190,143801,143900,25878),(1191,143901,144000,25908),(1192,144001,144100,25938),(1193,144101,144200,25968),(1194,144201,144300,25998),(1195,144301,144400,26028),(1196,144401,144500,26058),(1197,144501,144600,26088),(1198,144601,144700,26118),(1199,144701,144800,26148),(1200,144801,144900,26178),(1201,144901,145000,26208),(1202,145001,145100,26238),(1203,145101,145200,26268),(1204,145201,145300,26298),(1205,145301,145400,26328),(1206,145401,145500,26358),(1207,145501,145600,26388),(1208,145601,145700,26418),(1209,145701,145800,26448),(1210,145801,145900,26478),(1211,145901,146000,26508),(1212,146001,146100,26538),(1213,146101,146200,26568),(1214,146201,146300,26598),(1215,146301,146400,26628),(1216,146401,146500,26658),(1217,146501,146600,26688),(1218,146601,146700,26718),(1219,146701,146800,26748),(1220,146801,146900,26778),(1221,146901,147000,26808),(1222,147001,147100,26838),(1223,147101,147200,26868),(1224,147201,147300,26898),(1225,147301,147400,26928),(1226,147401,147500,26958),(1227,147501,147600,26988),(1228,147601,147700,27018),(1229,147701,147800,27048),(1230,147801,147900,27078),(1231,147901,148000,27108),(1232,148001,148100,27138),(1233,148101,148200,27168),(1234,148201,148300,27198),(1235,148301,148400,27228),(1236,148401,148500,27258),(1237,148501,148600,27288),(1238,148601,148700,27318),(1239,148701,148800,27348),(1240,148801,148900,27378),(1241,148901,149000,27408),(1242,149001,149100,27438),(1243,149101,149200,27468),(1244,149201,149300,27498),(1245,149301,149400,27528),(1246,149401,149500,27558),(1247,149501,149600,27588),(1248,149601,149700,27618),(1249,149701,149800,27648),(1250,149801,149900,27678),(1251,149901,150000,27708),(1252,150001,150100,27738),(1253,150101,150200,27768),(1254,150201,150300,27798),(1255,150301,150400,27828),(1256,150401,150500,27858),(1257,150501,150600,27888),(1258,150601,150700,27918),(1259,150701,150800,27948),(1260,150801,150900,27978),(1261,150901,151000,28008),(1262,151001,151100,28038),(1263,151101,151200,28068),(1264,151201,151300,28098),(1265,151301,151400,28128),(1266,151401,151500,28158),(1267,151501,151600,28188),(1268,151601,151700,28218),(1269,151701,151800,28248),(1270,151801,151900,28278),(1271,151901,152000,28308),(1272,152001,152100,28338),(1273,152101,152200,28368),(1274,152201,152300,28398),(1275,152301,152400,28428),(1276,152401,152500,28458),(1277,152501,152600,28488),(1278,152601,152700,28518),(1279,152701,152800,28548),(1280,152801,152900,28578),(1281,152901,153000,28608),(1282,153001,153100,28638),(1283,153101,153200,28668),(1284,153201,153300,28698),(1285,153301,153400,28728),(1286,153401,153500,28758),(1287,153501,153600,28788),(1288,153601,153700,28818),(1289,153701,153800,28848),(1290,153801,153900,28878),(1291,153901,154000,28908),(1292,154001,154100,28938),(1293,154101,154200,28968),(1294,154201,154300,28998),(1295,154301,154400,29028),(1296,154401,154500,29058),(1297,154501,154600,29088),(1298,154601,154700,29118),(1299,154701,154800,29148),(1300,154801,154900,29178),(1301,154901,155000,29208),(1302,155001,155100,29238),(1303,155101,155200,29268),(1304,155201,155300,29298),(1305,155301,155400,29328),(1306,155401,155500,29358),(1307,155501,155600,29388),(1308,155601,155700,29418),(1309,155701,155800,29448),(1310,155801,155900,29478),(1311,155901,156000,29508),(1312,156001,156100,29538),(1313,156101,156200,29568),(1314,156201,156300,29598),(1315,156301,156400,29628),(1316,156401,156500,29658),(1317,156501,156600,29688),(1318,156601,156700,29718),(1319,156701,156800,29748),(1320,156801,156900,29778),(1321,156901,157000,29808),(1322,157001,157100,29838),(1323,157101,157200,29868),(1324,157201,157300,29898),(1325,157301,157400,29928),(1326,157401,157500,29958),(1327,157501,157600,29988),(1328,157601,157700,30018),(1329,157701,157800,30048),(1330,157801,157900,30078),(1331,157901,158000,30108),(1332,158001,158100,30138),(1333,158101,158200,30168),(1334,158201,158300,30198),(1335,158301,158400,30228),(1336,158401,158500,30258),(1337,158501,158600,30288),(1338,158601,158700,30318),(1339,158701,158800,30348),(1340,158801,158900,30378),(1341,158901,159000,30408),(1342,159001,159100,30438),(1343,159101,159200,30468),(1344,159201,159300,30498),(1345,159301,159400,30528),(1346,159401,159500,30558),(1347,159501,159600,30588),(1348,159601,159700,30618),(1349,159701,159800,30648),(1350,159801,159900,30678),(1351,159901,160000,30708),(1352,160001,160100,30738),(1353,160101,160200,30768),(1354,160201,160300,30798),(1355,160301,160400,30828),(1356,160401,160500,30858),(1357,160501,160600,30888),(1358,160601,160700,30918),(1359,160701,160800,30948),(1360,160801,160900,30978),(1361,160901,161000,31008),(1362,161001,161100,31038),(1363,161101,161200,31068),(1364,161201,161300,31098),(1365,161301,161400,31128),(1366,161401,161500,31158),(1367,161501,161600,31188),(1368,161601,161700,31218),(1369,161701,161800,31248),(1370,161801,161900,31278),(1371,161901,162000,31308),(1372,162001,162100,31338),(1373,162101,162200,31368),(1374,162201,162300,31398),(1375,162301,162400,31428),(1376,162401,162500,31458),(1377,162501,162600,31488),(1378,162601,162700,31518),(1379,162701,162800,31548),(1380,162801,162900,31578),(1381,162901,163000,31608),(1382,163001,163100,31638),(1383,163101,163200,31668),(1384,163201,163300,31698),(1385,163301,163400,31728),(1386,163401,163500,31758),(1387,163501,163600,31788),(1388,163601,163700,31818),(1389,163701,163800,31848),(1390,163801,163900,31878),(1391,163901,164000,31908),(1392,164001,164100,31938),(1393,164101,164200,31968),(1394,164201,164300,31998),(1395,164301,164400,32028),(1396,164401,164500,32058),(1397,164501,164600,32088),(1398,164601,164700,32118),(1399,164701,164800,32148),(1400,164801,164900,32178),(1401,164901,165000,32208),(1402,165001,165100,32238),(1403,165101,165200,32268),(1404,165201,165300,32298),(1405,165301,165400,32328),(1406,165401,165500,32358),(1407,165501,165600,32388),(1408,165601,165700,32418),(1409,165701,165800,32448),(1410,165801,165900,32478),(1411,165901,166000,32508),(1412,166001,166100,32538),(1413,166101,166200,32568),(1414,166201,166300,32598),(1415,166301,166400,32628),(1416,166401,166500,32658),(1417,166501,166600,32688),(1418,166601,166700,32718),(1419,166701,166800,32748),(1420,166801,166900,32778),(1421,166901,167000,32808),(1422,167001,167100,32838),(1423,167101,167200,32868),(1424,167201,167300,32898),(1425,167301,167400,32928);
/*!40000 ALTER TABLE `tbl_st_tds_slab` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user`
--

DROP TABLE IF EXISTS `tbl_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_user` (
  `userId` bigint(20) NOT NULL,
  `username` varchar(255) NOT NULL,
  `userFullName` varchar(255) NOT NULL,
  `userMobileNo` bigint(20) DEFAULT NULL,
  `saltValue` varchar(255) NOT NULL,
  `userPassword` varchar(255) NOT NULL,
  `emailId` varchar(255) DEFAULT NULL,
  `userStatus` char(1) NOT NULL,
  `userRoleTypeId` int(11) NOT NULL,
  `updatedBy` varchar(45) DEFAULT NULL,
  `updatedDate` datetime DEFAULT NULL,
  `createdBy` varchar(45) NOT NULL,
  `createdDate` date NOT NULL,
  KEY `userRoleTypeId_idx` (`userRoleTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AVG_ROW_LENGTH=4096;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user`
--

LOCK TABLES `tbl_user` WRITE;
/*!40000 ALTER TABLE `tbl_user` DISABLE KEYS */;
INSERT INTO `tbl_user` VALUES (1,'admin','Administrator 1',17302667,'lWP17C','$2a$10$544bl1WTAQh0aXRhVftB.uhcK6XiXC488vwKvl8RaoJjKNwbfXEo6','ngawang.zepa@thimphutechpark.bt','A',1,'admin','2021-03-20 13:06:44','MIG_USER','2021-02-24'),(2,'sonam','Sonam Choden',17454553,'txWSgq','$2a$10$arlW.H8.bZ7QZ4LkAPT.huqmgIzaRl5d79YfuZZYz5mboPNZKyCAO','autgaautocares@gmail.com','A',1,NULL,NULL,'admin','2021-03-20'),(3,'kinley','Kinley Lhamo',17946345,'3v4fdS','$2a$10$TOYWk4Avy3XkNKxDymKGye8GCjBh3xGJCok4R.AKZQmPJMsNk8q26','autgaautocares@gmail.com','A',2,NULL,NULL,'admin','2021-03-20');
/*!40000 ALTER TABLE `tbl_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user_role_type`
--

DROP TABLE IF EXISTS `tbl_user_role_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_user_role_type` (
  `userRoleTypeId` int(11) NOT NULL AUTO_INCREMENT,
  `userRoleTypeName` varchar(100) NOT NULL,
  PRIMARY KEY (`userRoleTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user_role_type`
--

LOCK TABLES `tbl_user_role_type` WRITE;
/*!40000 ALTER TABLE `tbl_user_role_type` DISABLE KEYS */;
INSERT INTO `tbl_user_role_type` VALUES (1,'Adminstrator'),(2,'Sale'),(3,'Accountant');
/*!40000 ALTER TABLE `tbl_user_role_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_useraccesspermission`
--

DROP TABLE IF EXISTS `tbl_useraccesspermission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_useraccesspermission` (
  `userAccessPermissionId` bigint(20) NOT NULL,
  `userRoleTypeId` int(11) NOT NULL,
  `screenId` int(11) NOT NULL,
  `isScreenAccessAllowed` char(1) NOT NULL,
  `isEditAccessAllowed` char(1) NOT NULL,
  `isDeleteAccessAllowed` char(1) NOT NULL,
  `isSaveAccessAllowed` char(1) NOT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `updatedDate` datetime DEFAULT NULL,
  `createdBy` varchar(45) NOT NULL,
  `createdDate` datetime NOT NULL,
  PRIMARY KEY (`userAccessPermissionId`),
  KEY `userRoleTypeId_idx` (`userRoleTypeId`),
  KEY `screenId_fk_idx` (`screenId`),
  CONSTRAINT `screenId_fk` FOREIGN KEY (`screenId`) REFERENCES `tbl_screen` (`screenId`),
  CONSTRAINT `userRoleTypeId` FOREIGN KEY (`userRoleTypeId`) REFERENCES `tbl_user_role_type` (`userRoleTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_useraccesspermission`
--

LOCK TABLES `tbl_useraccesspermission` WRITE;
/*!40000 ALTER TABLE `tbl_useraccesspermission` DISABLE KEYS */;
INSERT INTO `tbl_useraccesspermission` VALUES (1,1,2,'Y','Y','Y','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(2,1,1,'Y','Y','Y','Y',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(3,1,3,'Y','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(4,1,4,'Y','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(5,1,5,'Y','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(6,1,6,'Y','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(7,1,7,'Y','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(8,1,8,'Y','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(9,1,9,'Y','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(10,1,10,'Y','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(11,1,11,'Y','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(12,1,12,'Y','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(13,1,13,'Y','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(14,1,14,'Y','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(15,1,15,'Y','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(16,1,16,'Y','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(17,1,17,'Y','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(18,1,18,'Y','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(19,1,19,'Y','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(20,1,20,'Y','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(21,2,1,'N','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(22,2,2,'N','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(23,2,3,'N','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(24,2,4,'Y','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(25,2,5,'Y','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(26,2,6,'N','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(27,2,7,'N','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(28,2,8,'N','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(29,2,9,'N','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(30,2,10,'N','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(31,2,11,'N','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(32,2,12,'Y','Y','Y','Y',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(33,2,13,'Y','Y','Y','Y',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(34,2,14,'Y','Y','Y','Y',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(35,2,15,'Y','Y','Y','Y',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(36,2,16,'Y','Y','Y','Y',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(37,2,17,'Y','Y','Y','Y',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(38,2,18,'Y','Y','Y','Y',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(39,2,19,'Y','Y','Y','Y',NULL,NULL,'Administrator 1','2021-02-24 00:00:00'),(40,2,20,'N','N','N','N',NULL,NULL,'Administrator 1','2021-02-24 00:00:00');
/*!40000 ALTER TABLE `tbl_useraccesspermission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'bcs_db'
--
/*!50003 DROP PROCEDURE IF EXISTS `getIncomeAndExpensesDetails` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getIncomeAndExpensesDetails`(companyId INT(11), status INT)
BEGIN

DROP TEMPORARY TABLE IF EXISTS TEMP;
DROP TEMPORARY TABLE IF EXISTS TEMPI;

CREATE TEMPORARY
       TABLE
TEMPI( accTypeId INT(11),
		ledgerId INT(11),
		ledgerName VARCHAR(200),
		amount DOUBLE,
		accTypeName VARCHAR(200)
	  );

IF(status=1)
  THEN
BEGIN
CREATE TEMPORARY TABLE
	TEMP(SELECT
    d.accTypeId AS accTypeId,
    C.ledgerId AS ledgerId,
    C.ledgerName AS ledgerName,
    CASE
        WHEN
            (B.debitAmount IS NULL
                OR B.debitAmount = 0)
        THEN
            SUM(IFNULL(B.creditAmount, 0))
        ELSE SUM(IFNULL(B.debitAmount, 0))
    END AS amount,
    d.accTypeName AS accTypeName
FROM
    tbl_acc_voucher A
        INNER JOIN
    tbl_acc_voucher_detail B ON A.voucherId = B.voucherId
        INNER JOIN
    tbl_acc_ledger C ON C.ledgerId = B.ledgerId
        RIGHT JOIN
    tbl_acc_acctype d ON d.accTypeId = C.accTypeId
WHERE
    d.accHeadTypeEffect = 'PL'
        AND d.accTypeId IN (10 , 13, 14)
        AND C.companyId = companyId
GROUP BY C.ledgerId
ORDER BY accTypeId);

SET @count=(SELECT COUNT(*) FROM TEMP);
IF(@count = 0)
   THEN
BEGIN
   INSERT INTO TEMPI(SELECT
    accTypeId,
    0 AS ledgerId,
    '' AS ledgerName,
    0.00 AS amount,
    accTypeName
FROM
    tbl_acc_acctype
WHERE
    accTypeId IN (10 , 13, 14));
	END;
END IF;

INSERT INTO TEMPI(SELECT
    accTypeId,
    0 AS ledgerId,
    '' AS ledgerName,
    0.00 AS amount,
    accTypeName
FROM
    tbl_acc_acctype
WHERE
    accTypeId IN (10 , 13, 14)
        AND accTypeId NOT IN (SELECT
            accTypeId
        FROM
            TEMP));

SELECT * FROM TEMPI  UNION SELECT * FROM TEMP;
 END;
ELSE
BEGIN
CREATE TEMPORARY TABLE
	TEMP(SELECT
    d.accTypeId AS accTypeId,
    C.ledgerId AS ledgerId,
    C.ledgerName AS ledgerName,
    CASE
        WHEN
            (B.debitAmount IS NULL
                OR B.debitAmount = 0)
        THEN
            SUM(IFNULL(B.creditAmount, 0))
        ELSE SUM(IFNULL(B.debitAmount, 0))
    END AS amount,
    d.accTypeName AS accTypeName
FROM
    tbl_acc_voucher A
        INNER JOIN
    tbl_acc_voucher_detail B ON A.voucherId = B.voucherId
        INNER JOIN
    tbl_acc_ledger C ON C.ledgerId = B.ledgerId
        RIGHT JOIN
    tbl_acc_acctype d ON d.accTypeId = C.accTypeId
WHERE
    d.accHeadTypeEffect = 'PL'
        AND d.accTypeId IN (11 , 15)
        AND C.companyId = companyId
GROUP BY C.ledgerId);

SET @count=(SELECT COUNT(*) FROM TEMP);

IF(@count=0)
  THEN
BEGIN
INSERT INTO TEMPI(SELECT
    accTypeId,
    0 AS ledgerId,
    '' AS ledgerName,
    0.00 AS amount,
    accTypeName
FROM
    tbl_acc_acctype
WHERE
    accTypeId IN (11 , 15));
		END;
END IF;

 INSERT INTO TEMPI(SELECT
    accTypeId,
    0 AS ledgerId,
    '' AS ledgerName,
    0.00 AS amount,
    accTypeName
FROM
    tbl_acc_acctype
WHERE
    accTypeId IN (11 , 15)
        AND accTypeId NOT IN (SELECT
            accTypeId
        FROM
            TEMP));

SELECT * FROM TEMP UNION SELECT * FROM TEMPI;

  END;
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_acc_balance_sheet_report` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_acc_balance_sheet_report`(paramCompanyId INT(11),paramReportType char(3),paramAsOnDate Date,fromDate Date,toDate Date)
BEGIN

DROP TABLE IF EXISTS temp_acc_balance_sheet_report;
DROP TABLE IF EXISTS temp_voucher_details;
DROP TABLE IF EXISTS temp_acc_parent;

CREATE TEMPORARY TABLE temp_acc_balance_sheet_report(particular varchar(250),amount double,groupLevel int,accTypeId int,groupId int);



CREATE TEMPORARY TABLE temp_voucher_details
SELECT a.accTypeName as particular,##this will include only ledger with transaction.
SUM(c.drcrAmount*-1) as amount,
a.groupId as groupId,
a.accTypeId as accTypeId,
b.openingBal as openingBal
FROM tbl_acc_acctype a
LEFT JOIN tbl_acc_ledger b ON a.accTypeId=b.accTypeId
LEFT JOIN tbl_acc_voucher_entries_detail c ON b.ledgerId=c.ledgerId
LEFT JOIN tbl_acc_voucher_entries d on d.voucherId=c.voucherId
WHERE d.companyId=paramCompanyId AND d.voucherEntryDate<=paramAsOnDate
AND d.voucherEntryDate>=fromDate AND d.voucherEntryDate<=toDate
GROUP BY a.accTypeId;


INSERT INTO  temp_voucher_details
SELECT a.accTypeName as particular,##this will include only ledger with openin is not zero.
0 as amount,
a.groupId as groupId,
a.accTypeId as accTypeId,
SUM(b.openingBal) as openingBal
FROM tbl_acc_acctype a
LEFT JOIN tbl_acc_ledger b ON a.accTypeId=b.accTypeId
where b.openingBal!=0 and
	  b.companyId=paramCompanyId and
      b.ledgerId not in (select a.ledgerId
						from tbl_acc_voucher_entries_detail a
						inner join tbl_acc_voucher_entries b on a.voucherId=b.voucherId
						where b.companyId=paramCompanyId)
      GROUP BY b.accTypeId ;

     #select * from temp_voucher_details;

CREATE TEMPORARY TABLE temp_acc_parent SELECT * FROM tbl_acc_group WHERE reportEffectType=paramReportType;

SET @counter=(SELECT COUNT(*) FROM temp_acc_parent);

   WHILE @counter>0 DO
   SET @var_groupId=(SELECT groupId FROM temp_acc_parent ORDER BY groupId asc LIMIT 1);

   INSERT INTO temp_acc_balance_sheet_report
   SELECT groupName,(SELECT IFNULL(SUM(amount),0)+IFNULL(sum(openingBal),0) FROM temp_voucher_details WHERE groupId=@var_groupId),1,null,@var_groupId FROM temp_acc_parent WHERE groupId=@var_groupId;
  # SELECT particular,IFNULL(ABS(amount),0)+IFNULL(abs(openingBal),0),2, accTypeId,null FROM temp_voucher_details WHERE groupId=@var_groupId;

  INSERT INTO temp_acc_balance_sheet_report
  SELECT particular,sum(IFNULL(amount,0)+IFNULL(openingBal,0)),2, accTypeId,@var_groupId FROM temp_voucher_details WHERE groupId=@var_groupId group by accTypeId;




   SET SQL_SAFE_UPDATES = 0;
   DELETE FROM temp_acc_parent WHERE groupId=@var_groupId;
   SET @counter=(SELECT COUNT(*) FROM temp_acc_parent);
   END WHILE;

   SELECT particular as particular,
   amount as amount,
   groupLevel as groupLevel,
   accTypeId as accTypeId,
   groupId
   from temp_acc_balance_sheet_report;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_acc_calculate_depreciation` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_acc_calculate_depreciation`(companyId INT(11))
BEGIN

DROP TEMPORARY TABLE IF EXISTS DEPRECIATION_TEMP_TABLE;
DROP TEMPORARY TABLE IF EXISTS DEPRECIATION_TEMP_TABLE_II;
DROP TEMPORARY TABLE IF EXISTS DEPRECIATION_TEMP_TABLE_FINAL;

CREATE TEMPORARY TABLE DEPRECIATION_TEMP_TABLE(
		ledgerName  VARCHAR(250),
		voucherId INT(11),
		rateOfDepreciation INT(11),
		previousYearClosingBalance DOUBLE,
		additionAmount DOUBLE,
		disposalAmount DOUBLE,
		asOnDate DOUBLE,
		previousYearOpeningBalance DOUBLE
);

CREATE TEMPORARY TABLE DEPRECIATION_TEMP_TABLE_II
	(   itemsName VARCHAR(250),
        voucherId INT(11),
		rateOfDepreciation INT(11),
		previousYearClosingBalance DOUBLE,
		additionAmount DOUBLE,
		disposalAmount DOUBLE,
		asOnDate DOUBLE,
		previousYearOpeningBalance DOUBLE
      --  forThePeriodBalance DOUBLE,
		-- disposalOrAdjustmentBalance DOUBLE,
       -- totalDepreicatedAmount DOUBLE,
		-- netTotalAmount DOUBLE
	  );


 CREATE TEMPORARY TABLE DEPRECIATION_TEMP_TABLE_FINAL
	(   itemsName VARCHAR(250),
        voucherId INT(11),
		rateOfDepreciation INT(11),
		previousYearClosingBalance DOUBLE,
		additionAmount DOUBLE,
		disposalAmount DOUBLE,
		asOnDate DOUBLE,
		previousYearOpeningBalance DOUBLE
      --  forThePeriodBalance DOUBLE,
		-- disposalOrAdjustmentBalance DOUBLE,
       -- totalDepreicatedAmount DOUBLE,
		-- netTotalAmount DOUBLE
	  );

INSERT INTO DEPRECIATION_TEMP_TABLE(
       SELECT a.ledgerName AS itemsName,
       b.voucherId AS voucherId,
       null AS rateOfDepreciation,
       null AS previousYearClosingBalance,
       null AS additionAmount,
       null AS disposalAmount,
       null AS asOnDate,
       null AS previousYearOpeningBalance FROM tbl_acc_ledger a
INNER JOIN tbl_acc_voucher_detail  b  ON a.ledgerId=b.ledgerId where accTypeId='12'  group by b.voucherId);

  INSERT INTO DEPRECIATION_TEMP_TABLE_II
            (SELECT
	a.itemName AS itemName,
    b.voucherId AS voucherId,
    a.rateOfDepreciation AS rateOfDepreciation,
    a.openingBalance AS previousYearClosingBalance,
    SUM(b.additionalQty * additional) AS additionAmount,
    SUM(b.disposalQty * disposal) AS disposalAmount,
    (a.openingBalance) + SUM(IFNULL(b.additionalQty, 0) * IFNULL(additional, 0)) + SUM(IFNULL(b.disposalQty, 0) * IFNULL(disposal, 0)) AS asOnDate,
    ROUND((a.rateOfDepreciation / 100) * (a.openingBalance) + SUM(IFNULL(b.additionalQty, 0) * IFNULL(additional, 0)) + SUM(IFNULL(b.disposalQty, 0) * IFNULL(disposal, 0)),
            2) AS previousYearOpeningBalance
FROM
    tbl_acc_depreciation_item_setup a
        INNER JOIN
    tbl_acc_depreciation_item_details b ON a.depreciationId = b.depreciationId
GROUP BY b.depreciationId);

SET @count=(SELECT COUNT(*) FROM DEPRECIATION_TEMP_TABLE);

WHILE @count>0 DO

     INSERT INTO DEPRECIATION_TEMP_TABLE_FINAL(
              SELECT * FROM DEPRECIATION_TEMP_TABLE LIMIT 1
              );

     SET @voucherId=(SELECT voucherId FROM DEPRECIATION_TEMP_TABLE LIMIT 1);
     SET @iterator=(SELECT COUNT(*) FROM DEPRECIATION_TEMP_TABLE_II WHERE voucherId= @voucherId);

   WHILE @iterator>0 DO
     INSERT INTO DEPRECIATION_TEMP_TABLE_FINAL(SELECT * FROM DEPRECIATION_TEMP_TABLE_II WHERE voucherId= @voucherId lIMIT 1);
        DELETE FROM DEPRECIATION_TEMP_TABLE_II WHERE voucherId= @voucherId ORDER BY voucherId ASC LIMIT 1;
      SET @iterator=@iterator-1;
   END WHILE;

  DELETE FROM DEPRECIATION_TEMP_TABLE WHERE voucherId= @voucherId;
   SET @count=@count-1;

END WHILE;

SELECT * FROM DEPRECIATION_TEMP_TABLE_FINAL;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_acc_fetch_purchase_sale_details` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_acc_fetch_purchase_sale_details`(companyId INT(11),
itemCodeParam varchar(11), financialYearId INT(11))
BEGIN

DROP TEMPORARY TABLE IF EXISTS TEMP;
DROP TEMPORARY TABLE IF EXISTS TEMPOrder;
DROP TEMPORARY TABLE IF EXISTS TEMPFINAL;

CREATE TEMPORARY TABLE TEMP(num int,purchaseId int(11),
purchaseDate DATE,particular varchar(200),qty int(11),sellingPrice double, voucherNo int,balance int);

CREATE TEMPORARY TABLE TEMPOrder(rowNum int,purchaseId int(11),purchaseDate DATE,
particular varchar(200),qty int(11),sellingPrice double,voucherNo int,balance int);

CREATE TEMPORARY TABLE TEMPFINAL(num int,purchaseId int(11),purchaseDate DATE,
particular varchar(200),qty int(11),sellingPrice double,voucherNo int,balance int);

SET @row_num=0;
insert into TEMP (SELECT
A.num AS num,
A.purchaseId as purchaseId,
A.purchaseDate AS purchaseDate,
A.particular as particular,
SUM(A.qty) as qty,
A.sellingPrice as sellingPrice,
voucherNo,
null as balance

 FROM (
SELECT
(@row_num:=@row_num+1) AS num,
p.purchaseId as purchaseId,
t.setDate AS purchaseDate,
"Opening Balance" as particular,
t.qty as qty,
t.costPrice as sellingPrice,
null as voucherNo
FROM tbl_inv_purchase p
INNER JOIN tbl_inv_purchase_a t on t.purchaseId=p.purchaseId
where p.itemcode=itemCodeParam AND p.financialYearId=financialYearId AND isSpillOver='Y' LIMIT 1
UNION
SELECT  (@row_num:=@row_num+1) AS num,p.purchaseId as purchaseId,
t.setDate AS purchaseDate,
"Purchase" as particular,
t.qty as qty,
t.costPrice as sellingPrice,
t.purchaseVoucherNo as voucherNo
FROM tbl_inv_purchase p
INNER JOIN tbl_inv_purchase_a t on t.purchaseId=p.purchaseId
where t.itemcode=itemCodeParam  AND p.financialYearId=financialYearId AND  isSpillOver='N'
UNION
SELECT
(@row_num:=@row_num+1) AS num,
A.saleRecordId as purchaseId,
A.saleDate AS purchaseDate,
"Sale" as particular,
SUM(B.qty) as qty,
B.sellingPrice as sellingPrice,
null as voucherNo
FROM tbl_inv_sale_record A
INNER JOIN tbl_inv_sale_record_detail B on A.saleRecordId=B.saleRecordId
#INNER JOIN tbl_inv_purchase B ON A.itemCode=B.itemCode
#INNER JOIN tbl_inv_purchase_a C ON B.purchaseId=C.purchaseId
where B.itemcode=itemCodeParam AND A.financialYearId=financialYearId GROUP BY A.saleDate,B.sellingPrice
UNION
SELECT  (@row_num:=@row_num+1) AS num,A.id as purchaseId,
A.setDate AS purchaseDate,
"Sale Return" as particular,
SUM(A.returnQty) as qty,
B.sellingPrice as sellingPrice,
null as voucherNo
FROM tbl_inv_return_item A
INNER JOIN tbl_inv_purchase B ON A.itemCode=B.itemCode
where A.itemcode=itemCodeParam AND B.financialYearId=financialYearId GROUP BY A.setDate
UNION
SELECT  (@row_num:=@row_num+1) AS num,A.id as purchaseId,
A.setDate AS purchaseDate,
"Sale Replace" as particular,
SUM(A.replaceQty) as qty,
B.sellingPrice as sellingPrice,
null as voucherNo
FROM tbl_inv_replace_item A
INNER JOIN tbl_inv_purchase B ON A.itemCode=B.itemCode
where A.itemcode=itemCodeParam AND B.financialYearId=financialYearId GROUP BY A.setDate
) A group by purchaseDate,sellingPrice,particular order by A.purchaseDate,A.num asc);



SET @row_num=0;
INSERT INTO TEMPOrder SELECT (@row_num:=@row_num+1) AS rowNum ,A.purchaseId as purchaseId,
A.purchaseDate AS purchaseDate,
A.particular as particular,
A.qty as qty,
A.sellingPrice as sellingPrice,
A.voucherNo,
null as balance FROM TEMP A;




INSERT INTO TEMPFINAL(SELECT* FROM TEMPOrder);
SET @count=(SELECT COUNT(*) FROM TEMPOrder);
SET @balance=0;


WHILE @count>0 DO

set @row_num=(select rowNum from TEMPOrder order by rowNum ASC limit 1);
set @particular=(select particular from TEMPOrder order by rowNum  ASC limit 1);
set @selectedSetDate=(select purchaseDate from TEMPOrder limit 1);
set @selectedRate=(select sellingPrice from TEMPOrder limit 1);
set @purchaseId=(select purchaseId FROM tbl_inv_purchase where itemCode=itemCodeParam AND financialYearId=financialYearId) ;

set @OrginalQty=(select SUM(qty) from TEMPOrder  where purchaseId= @purchaseId
group by purchaseDate,sellingPrice limit 1);

if @particular='Opening Balance' then
BEGIN
set @balance = @balance + @OrginalQty;
END;
END if ;

if @particular='Purchase' then
BEGIN
set @balance=@balance + @OrginalQty;
END;
END if ;

if @particular='Sale' then
BEGIN
set @saleQty=(select qty from TEMPOrder ORDER BY rowNum  ASC LIMIT 1);
SET @balance=@balance - @saleQty;
END;
END if ;

if @particular='Sale Return' then
BEGIN
set @returnQty=(select qty from TEMPOrder ORDER BY rowNum  ASC LIMIT 1);

SET @balance=@balance + @returnQty;
END;
end if;

if @particular='Sale Replace' then
BEGIN
set @replaceQty=(select qty from TEMPOrder ORDER BY rowNum  ASC LIMIT 1);

SET @balance=@balance - @replaceQty;
END;
end if;
SET SQL_SAFE_UPDATES=0;
update TEMPFINAL set balance=@balance where num=@row_num;

  #DELETE FROM TEMPOrder WHERE rowNum=@row_num;
   DELETE FROM TEMPOrder WHERE purchaseDate=@selectedSetDate AND sellingPrice=@selectedRate;
   SET @count=@count-1;

END WHILE;

select purchaseId ,purchaseDate ,particular ,qty,sellingPrice,voucherNo,balance  from TEMPFINAL order by purchaseDate;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_acc_financialYearClosing` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_acc_financialYearClosing`(paramCompanyId INT(11), paramFinancialYear INT)
BEGIN

INSERT INTO tbl_acc_voucher_entries_ledger
select null,a.* from tbl_acc_voucher_entries a where companyId=paramCompanyId;

INSERT INTO tbl_acc_voucher_entries_detail_ledger
select null,a.* from tbl_acc_voucher_entries_detail a
inner join tbl_acc_voucher_entries b on a.voucherId=b.voucherId
where b.companyId=paramCompanyId;

SET @nextFinancialYearId=(SELECT financialYearId FROM tbl_financial_year_setup WHERE financialYearId > paramFinancialYear ORDER BY financialYearId LIMIT 1);

SET SQL_SAFE_UPDATES = 0;
UPDATE tbl_acc_voucher_entries set financialYearId=@nextFinancialYearId WHERE companyId=paramCompanyId;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_acc_financial_year_closing` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_acc_financial_year_closing`(paramCompanyId INT(11), paramFinancialYear INT)
BEGIN

DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
	ROLLBACK;  -- rollback for any error in the transaction
END;

DROP TEMPORARY TABLE IF EXISTS temp_ledger;
DROP TEMPORARY TABLE IF EXISTS temp_inv_purchase;
DROP TEMPORARY TABLE IF EXISTS temp_inv_purchase_a;

CREATE TEMPORARY TABLE temp_ledger(
 ledgerId varchar(11),
 amount double);

CREATE TEMPORARY TABLE temp_inv_purchase (
  purchaseId int(11) NOT NULL,
  purchaseDate date NOT NULL,
  purchaseInvoiceNo varchar(45) NOT NULL,
  brandId int(11) DEFAULT NULL,
  itemCode varchar(45) NOT NULL,
  qty int(11) NOT NULL,
  costPrice double NOT NULL,
  sellingPrice double NOT NULL,
  locationId varchar(50) NOT NULL,
  itemName varchar(100) NOT NULL,
  companyId int(11) DEFAULT NULL,
  financialYearId int(11) DEFAULT NULL,
  spillOverQty int(11) DEFAULT NULL,
  setDate date NOT NULL,
  createdBy varchar(45) NOT NULL
);

CREATE TEMPORARY TABLE  temp_inv_purchase_a (
  id int(11) NOT NULL,
  purchaseInvoiceNo varchar(45) NOT NULL,
  purchaseId int(11) NOT NULL,
  qty int(11) NOT NULL,
  costPrice double NOT NULL,
  sellingPrice double NOT NULL,
  itemCode varchar(45) NOT NULL,
  purchaseDate date NOT NULL,
  isSpillOver char(1) DEFAULT NULL,
  createdBy varchar(45) NOT NULL,
  setDate date NOT NULL
);

START TRANSACTION;
#Voucher Details
SET SQL_SAFE_UPDATES = 0;
SET @financialYearFrom=(SELECT financialYearFrom FROM tbl_financial_year_setup
WHERE  companyId=paramCompanyId order by financialYearId desc limit 1);

INSERT INTO temp_ledger(SELECT b.ledgerId as ledgerId, SUM(c.drcrAmount) as amount
FROM tbl_acc_acctype a
LEFT JOIN tbl_acc_ledger b ON a.accTypeId=b.accTypeId
LEFT JOIN tbl_acc_voucher_entries_detail c ON b.ledgerId=c.ledgerId
LEFT JOIN tbl_acc_voucher_entries d on d.voucherId=c.voucherId
WHERE d.companyId=paramCompanyId AND d.financialYearId=paramFinancialYear
AND  d.setDate>=@financialYearFrom AND d.setDate<=curdate()
GROUP BY a.accTypeId);

SET @ledger_counter=(SELECT COUNT(*) FROM temp_ledger);



INSERT INTO tbl_acc_ledger_a
select null, ledgerId,ledgerName,bankId,accTypeId,openingBal,companyId,setDate,createdBy
from tbl_acc_ledger;

#Update ledger opening balance
WHILE @ledger_counter > 0 DO
SET @ledgerId =(SELECT ledgerId FROM temp_ledger LIMIT 1);
IF(@ledgerId IS NOT NULL AND @ledgerId <> '') THEN
SET @opening_balance=(SELECT ABS(IFNULL(amount,0)) FROM temp_ledger WHERE ledgerId=@ledgerId);
UPDATE tbl_acc_ledger SET openingBal=@opening_balance WHERE ledgerId=@ledgerId AND companyId=paramCompanyId;
END IF;
DELETE FROM temp_ledger LIMIT 1;
SET @ledger_counter=(SELECT COUNT(*) FROM temp_ledger);
END WHILE;

#insert records in voucher entries ledger
INSERT INTO  tbl_acc_voucher_entries_ledger
SELECT 0, ve.* FROM tbl_acc_voucher_entries ve WHERE ve.companyId=paramCompanyId AND ve.financialYearId=paramFinancialYear;

INSERT INTO  tbl_acc_voucher_entries_detail_ledger
SELECT 0, ved.* FROM tbl_acc_voucher_entries_detail ved INNER JOIN tbl_acc_voucher_entries ve ON ved.voucherId=ve.voucherId
WHERE ve.companyId=paramCompanyId AND ve.financialYearId=paramFinancialYear;

TRUNCATE TABLE tbl_acc_voucher_entries_detail;
TRUNCATE TABLE tbl_acc_voucher_entries;

#Inventory Year Closing
INSERT INTO tbl_inv_purchase_ledger
SELECT 0, p.* FROM tbl_inv_purchase p WHERE p.companyId=paramCompanyId AND p.financialYearId=paramFinancialYear;

INSERT INTO tbl_inv_purchase_ledger_a
SELECT 0, pa.* FROM tbl_inv_purchase_a pa INNER JOIN tbl_inv_purchase p
ON p.itemCode = pa.itemCode WHERE p.companyId=paramCompanyId AND p.financialYearId=paramFinancialYear;

INSERT INTO temp_inv_purchase
SELECT p.* FROM tbl_inv_purchase p WHERE p.companyId=paramCompanyId AND p.financialYearId=paramFinancialYear;

INSERT INTO temp_inv_purchase_a
SELECT pa.* FROM tbl_inv_purchase_a pa INNER JOIN tbl_inv_purchase p
ON p.itemCode = pa.itemCode WHERE p.companyId=paramCompanyId AND p.financialYearId=paramFinancialYear;

TRUNCATE TABLE tbl_inv_purchase_a; # Flush table purchase audit for New financial year

SET @counter=(SELECT COUNT(*) FROM temp_inv_purchase);

SET @maxID=(SELECT financialYearId FROM tbl_financial_year_setup where companyId=paramCompanyId  order by financialYearId desc Limit 1);

#Purchase items qty carry forward for new financial year
WHILE @counter > 0 DO

SET @item_code=(SELECT itemCode FROM  temp_inv_purchase LIMIT 1);
SET @qty=(SELECT qty + IFNULL(spillOverQty,0) FROM tbl_inv_purchase WHERE itemCode=@item_code AND companyId=paramCompanyId AND financialYearId=paramFinancialYear);
UPDATE tbl_inv_purchase SET spillOverQty=@qty WHERE itemCode=@item_code;
UPDATE tbl_inv_purchase SET qty=0, financialYearId=@maxID+1 WHERE  itemCode=@item_code AND companyId=paramCompanyId;

SET @purchaseInvoiceNo=(SELECT purchaseInvoiceNo FROM tbl_inv_sale_record_detail SD INNER JOIN tbl_inv_sale_record s
ON SD.saleRecordId=S.saleRecordId WHERE  companyId=paramCompanyId AND  S.financialYearId=paramFinancialYear
AND itemCode=@item_code ORDER BY id DESC LIMIT 1);

IF(@purchaseInvoiceNo IS NOT NULL) THEN
SET @auditPurchaseId=(SELECT id FROM temp_inv_purchase_a  WHERE purchaseInvoiceNo=@purchaseInvoiceNo AND itemCode=@item_code ORDER BY id DESC LIMIT 1);

SET @remaingQty=(SELECT SUM(IFNULL(qty,0))-(select SUM(IFNULL(qty,0))
FROM tbl_inv_sale_record_detail WHERE purchaseInvoiceNo=@purchaseInvoiceNo AND itemCode=@item_code
GROUP BY purchaseInvoiceNo) FROM temp_inv_purchase_a
WHERE purchaseInvoiceNo=@purchaseInvoiceNo AND itemCode=@item_code GROUP BY purchaseInvoiceNo);

IF(@remaingQty > 0) THEN

INSERT INTO tbl_inv_purchase_a (SELECT 0, purchaseInvoiceNo,
purchaseId , qty, costPrice, sellingPrice, itemCode, purchaseDate,
isSpillOver,createdBy,setDate FROM temp_inv_purchase_a WHERE id= @auditPurchaseId);
UPDATE tbl_inv_purchase_a SET qty=@remaingQty WHERE itemCode=@item_code AND purchaseInvoiceNo=@purchaseInvoiceNo;
END IF;

INSERT INTO tbl_inv_purchase_a(SELECT 0, purchaseInvoiceNo,
purchaseId , qty, costPrice, sellingPrice, itemCode ,
purchaseDate , isSpillOver, createdBy,
setDate FROM temp_inv_purchase_a WHERE id >  @auditPurchaseId AND itemCode=@item_code);
UPDATE tbl_inv_purchase_a SET isSpillOver='Y' WHERE itemCode=@item_code;
END IF;

#Those items yet not sold
IF(@purchaseInvoiceNo IS NULL) THEN
INSERT INTO tbl_inv_purchase_a
(SELECT 0, purchaseInvoiceNo, purchaseId , qty, costPrice, sellingPrice, itemCode, purchaseDate,
isSpillOver,createdBy,setDate  FROM temp_inv_purchase_a temp WHERE purchaseInvoiceNo
NOT IN(SELECT purchaseInvoiceNo FROM tbl_inv_sale_record_detail SD INNER JOIN tbl_inv_sale_record s
ON SD.saleRecordId=S.saleRecordId WHERE  companyId=paramCompanyId AND  S.financialYearId=paramFinancialYear AND itemCode=@item_code));
UPDATE tbl_inv_purchase_a SET isSpillOver='Y' WHERE itemCode=@item_code;
END IF;

DELETE FROM temp_inv_purchase WHERE itemCode=@item_code;
SET @counter=(SELECT COUNT(*) FROM temp_inv_purchase);
END WHILE;
COMMIT;

START TRANSACTION;
#Insert into sale ledger and clear  sale record detail for new financial year
INSERT INTO  tbl_inv_sale_record_ledger
SELECT 0, S.* FROM tbl_inv_sale_record S WHERE S.companyId=paramCompanyId AND S.financialYearId=paramFinancialYear;

INSERT INTO  tbl_inv_sale_record_detail_ledger
SELECT 0, SRD.* FROM tbl_inv_sale_record_detail SRD INNER JOIN  tbl_inv_sale_record S
ON S.saleRecordId = SRD.saleRecordId WHERE S.companyId=paramCompanyId AND S.financialYearId=paramFinancialYear;

TRUNCATE TABLE tbl_inv_sale_record;
TRUNCATE TABLE tbl_inv_sale_record_detail;

#update financial year
UPDATE tbl_financial_year_setup SET status="I" WHERE companyId=paramCompanyId AND Status="A";

SET @financialYearFrom=(SELECT financialYearFrom FROM tbl_financial_year_setup
WHERE  companyId=paramCompanyId order by financialYearId desc limit 1);
SET @financialYearFrom =CONCAT(CONCAT(YEAR(@financialYearFrom)+1,'-', MONTH(@financialYearFrom)),'-', DAY(@financialYearFrom));

SET @financialYearTo=(SELECT financialYearTo FROM tbl_financial_year_setup
WHERE  companyId=paramCompanyId order by financialYearId desc limit 1);
SET @financialYearTo =CONCAT(CONCAT(YEAR(@financialYearTo)+1,'-', MONTH(@financialYearTo)),'-', DAY(@financialYearTo));


INSERT INTO tbl_financial_year_setup SET  financialYearId=@maxID +1, financialYearFrom=@financialYearFrom,
financialYearTo=@financialYearTo, status="A",
createdBy="Year Closing", createdDate=curDate(),
companyId=paramCompanyId;

DROP TEMPORARY TABLE IF EXISTS temp_ledger;
DROP TEMPORARY TABLE IF EXISTS temp_inv_purchase;
DROP TEMPORARY TABLE IF EXISTS temp_inv_purchase_a;
#Commit the transaction if there is no exception
COMMIT;

END ;;

DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_acc_get_cash_flow_detail` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_acc_get_cash_flow_detail`(companyId INT(11), fromDate Date, toDate Date, isCashFlowIn boolean)
BEGIN
DROP TEMPORARY TABLE IF EXISTS TEMP_DETAIL;
DROP TEMPORARY TABLE IF EXISTS CASH_FLOW_DETAIL;

CREATE TEMPORARY TABLE TEMP_DETAIL(voucherId INT(11), drcrAmount DOUBLE);
CREATE TEMPORARY TABLE CASH_FLOW_DETAIL(voucherId INT(11), accountTypeName varchar(200), drcrAmount DOUBLE);

INSERT INTO TEMP_DETAIL
SELECT D.voucherId,
SUM(IFNULL(drcrAmount,0))
FROM tbl_acc_acctype B
LEFT JOIN tbl_acc_ledger C ON C.accTypeId= B.accTypeId
LEFT JOIN tbl_acc_voucher_entries_detail D ON D.ledgerId=C.ledgerId
INNER JOIN tbl_acc_voucher_entries E ON D.voucherId=E.voucherId
WHERE C.companyId=companyId AND E.setDate >=fromDate AND E.setDate <=toDate
AND C.accTypeId IN(3,4)  AND CASE WHEN isCashFlowIn=1 THEN D.drcrAmount < 0 ELSE D.drcrAmount > 0 end
GROUP BY E.voucherId;

INSERT INTO CASH_FLOW_DETAIL
SELECT voucherId, '', drcrAmount FROM TEMP_DETAIL;

SET @COUNTER=(SELECT COUNT(*) FROM TEMP_DETAIL);

WHILE @COUNTER> 0 DO

SET @voucherId=(SELECT voucherId FROM TEMP_DETAIL LIMIT 1);
SET @accountTypeName=(SELECT B.accTypeName FROM  tbl_acc_acctype B
		LEFT JOIN tbl_acc_ledger C ON C.accTypeId= B.accTypeId
		LEFT JOIN tbl_acc_voucher_entries_detail D ON D.ledgerId=C.ledgerId
		INNER JOIN tbl_acc_voucher_entries E ON D.voucherId=E.voucherId
		WHERE C.accTypeId NOT IN (3,4) AND D.voucherId=@voucherId);

UPDATE CASH_FLOW_DETAIL SET accountTypeName=@accountTypeName WHERE voucherId=@voucherId;

SET SQL_SAFE_UPDATES = 0;
DELETE FROM TEMP_DETAIL WHERE voucherId=@voucherId;
SET @COUNTER=@COUNTER-1;
END WHILE;

SELECT accountTypeName AS particular,SUM(ABS(drcrAmount)) AS amount FROM CASH_FLOW_DETAIL GROUP BY accountTypeName;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_acc_get_cost_of_goods_sold` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_acc_get_cost_of_goods_sold`(paramCompanyId INT(11),fromDate Date,toDate Date)
BEGIN
Declare costOfGoodsSold DOUBLE default 0.00;
DROP TABLE IF EXISTS temp_goods_sold;

CREATE TEMPORARY TABLE temp_goods_sold
SELECT
itemCode,
SUM(qty) as qty,
purchaseInvoiceNo
FROM tbl_inv_sale_record_detail a
INNER JOIN tbl_inv_sale_record b on a.saleRecordId=b.saleRecordId
where b.companyId=paramCompanyId and (fromDate is null or b.saleDate>=fromDate)  and b.saleDate<=toDate
group by itemCode;

SET @counter=(SELECT COUNT(*) FROM temp_goods_sold);
   WHILE @counter>0 DO
	SET @itemCode=(SELECT itemCode FROM temp_goods_sold ORDER BY purchaseInvoiceNo ASC LIMIT 1);
	SET @purchasInvoiceNo=(SELECT purchaseInvoiceNo FROM temp_goods_sold ORDER BY purchaseInvoiceNo asc LIMIT 1);
	SET @soldQty=(SELECT qty FROM temp_goods_sold ORDER BY purchaseInvoiceNo asc LIMIT 1);
	SET costOfGoodsSold=costOfGoodsSold+(@soldQty*(select costPrice from tbl_inv_purchase_a where purchaseInvoiceNo=@purchasInvoiceNo AND itemCode=@itemCode));

  SET SQL_SAFE_UPDATES = 0;
   DELETE FROM temp_goods_sold WHERE itemCode=@itemCode;
   SET @counter=(SELECT COUNT(*) FROM temp_goods_sold);
   END WHILE;

   SELECT costOfGoodsSold;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_acc_get_income_and_expenses_details` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_acc_get_income_and_expenses_details`()
BEGIN

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_acc_get_net_balance_amount` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_acc_get_net_balance_amount`(companyId INT(11),ledgerIdParam INT(11))
BEGIN

SET @openinBalance= (SELECT IFNULL(openingBal,0) FROM tbl_acc_ledger WHERE ledgerId=ledgerIdParam
					 AND companyId=companyId);

/*Cash out flow is consider as credited amount during purchase*/
SET @totalCashOutFlow=(SELECT (SUM(IFNULL(A.creditAmount,0)))FROM tbl_acc_voucher_detail A
 INNER JOIN tbl_acc_ledger B ON A.ledgerId=B.ledgerId  WHERE A.ledgerId=ledgerIdParam
 AND B.companyId=companyId GROUP BY A.ledgerId);

SET @totalCashInFlow =(SELECT (SUM(IFNULL(A.debitAmount,0)))FROM tbl_acc_voucher_detail A
INNER JOIN tbl_acc_ledger B ON A.ledgerId=B.ledgerId  WHERE A.ledgerId= ledgerIdParam AND B.companyId=companyId GROUP BY A.ledgerId);
SET @netBalance= (@openinBalance+@totalCashInFlow) -  @totalCashOutFlow;

SELECT @netBalance;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_acc_get_trail_balance_detail` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_acc_get_trail_balance_detail`(
paramCompanyId INT(11), paramFinancialYearId INT(11), fromDate Date,toDate Date)
BEGIN

DROP TABLE IF EXISTS temp_acc_trial_balance_report;
DROP TABLE IF EXISTS temp_voucher_details;
DROP TABLE IF EXISTS temp_acc_parent;

CREATE TEMPORARY TABLE temp_acc_trial_balance_report(ledgerId varchar(11),
particular varchar(250),amount double,groupLevel int,accTypeId int,groupId int);

CREATE TEMPORARY TABLE temp_voucher_details
SELECT b.ledgerId as ledgerId,
a.accTypeName as particular,
SUM(c.drcrAmount) as amount,
a.groupId as groupId,
a.accTypeId as accTypeId
FROM tbl_acc_acctype a
LEFT JOIN tbl_acc_ledger b ON a.accTypeId=b.accTypeId
LEFT JOIN tbl_acc_voucher_entries_detail c ON b.ledgerId=c.ledgerId
LEFT JOIN tbl_acc_voucher_entries d on d.voucherId=c.voucherId
WHERE d.companyId=paramCompanyId AND d.financialYearId=paramFinancialYearId
AND  d.setDate>=fromDate AND d.setDate<=toDate
GROUP BY a.accTypeId;

CREATE TEMPORARY TABLE temp_acc_parent SELECT * FROM tbl_acc_group;

SET @counter=(SELECT COUNT(*) FROM temp_acc_parent);

   WHILE @counter>0 DO
   SET @var_groupId=(SELECT groupId FROM temp_acc_parent ORDER BY groupId asc LIMIT 1);

   INSERT INTO temp_acc_trial_balance_report
   SELECT '', groupName,(SELECT IFNULL(SUM(amount),0) FROM temp_voucher_details WHERE groupId=@var_groupId),1,null,@var_groupId FROM temp_acc_parent WHERE groupId=@var_groupId;

   INSERT INTO temp_acc_trial_balance_report
   SELECT ledgerId, particular,IFNULL(amount,0),2, accTypeId,null FROM temp_voucher_details WHERE groupId=@var_groupId;

   SET SQL_SAFE_UPDATES = 0;
   DELETE FROM temp_acc_parent WHERE groupId=@var_groupId;
   SET @counter=(SELECT COUNT(*) FROM temp_acc_parent);
   END WHILE;

   SELECT * from temp_acc_trial_balance_report;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_acc_view_ledger_Details` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_acc_view_ledger_Details`(ledgerId int,fromDate date,toDate date)
BEGIN

DROP TEMPORARY TABLE IF EXISTS TEMP;

DROP TEMPORARY TABLE IF EXISTS TEMPFINAL;
CREATE TEMPORARY TABLE TEMP(voucherId int,voucherNo int(11),voucherTypeId INT,drcrAmount double,voucherCreatedDate DATE,voucherTypeName varchar(250));

CREATE TEMPORARY TABLE TEMPFINAL(voucherId int,voucherNo int(11),voucherTypeId INT,drcrAmount double,voucherCreatedDate DATE,voucherTypeName varchar(250));


INSERT INTO TEMP(SELECT A.voucherId AS voucherId,
B.voucherNo AS voucherNo,
C.voucherTypeId AS voucherTypeId,
IFNULL(A.drcrAmount,0) AS drcrAmount,
B.voucherEntryDate AS voucherCreatedDate,
C.voucherTypeName AS voucherTypeName
FROM tbl_acc_voucher_entries_detail A
INNER JOIN tbl_acc_voucher_entries B ON A.voucherId=B.voucherId
INNER JOIN tbl_acc_vouchertype C ON C.voucherTypeId=B.voucherTypeId
where A.ledgerId=ledgerId and B.voucherEntryDate>=fromDate and B.voucherEntryDate<=toDate);

INSERT INTO TEMPFINAL (select * from TEMP);


SET @count=(SELECT COUNT(*) FROM TEMP);

WHILE @count>0 DO
SET @voucherNo=(select voucherNo from TEMP ORDER BY voucherId ASC limit 1);
SET @voucherTypeId=(select voucherTypeId from TEMP ORDER BY voucherId ASC limit 1);

SET @ledgerName= (SELECT C.ledgerName FROM  tbl_acc_voucher_entries A
                  INNER JOIN  tbl_acc_voucher_entries_detail B ON A.voucherId =B.voucherId
                  INNER JOIN  tbl_acc_ledger C ON B.ledgerId=C.ledgerId
                  where A.voucherNo=@voucherNo and A.voucherTypeId=@voucherTypeId and C.ledgerId <> ledgerId and #to select opposit leger if its compont voucher
                  sign(B.drcrAmount)!=(select sign(A.drcrAmount) from (SELECT B.drcrAmount FROM  tbl_acc_voucher_entries A
				  INNER JOIN  tbl_acc_voucher_entries_detail B ON A.voucherId =B.voucherId
				  INNER JOIN  tbl_acc_ledger C ON B.ledgerId=C.ledgerId
				  where A.voucherNo=@voucherNo and A.voucherTypeId=@voucherTypeId and C.ledgerId = ledgerId) A)
                  ORDER BY  C.ledgerId  desc LIMIT 1);
if @ledgerName is null then
BEGIN
set @ledgerName=(select voucherTypeName from tbl_acc_vouchertype where voucherTypeId=@voucherTypeId);
END;
end if;

SET SQL_SAFE_UPDATES=0;
UPDATE TEMPFINAL SET voucherTypeName=@ledgerName where voucherNo=@voucherNo
and voucherTypeId=@voucherTypeId;

DELETE FROM TEMP WHERE voucherNo=@voucherNo and voucherTypeId=@voucherTypeId;
SET @count=@count-1;
END WHILE;
SELECT * FROM TEMPFINAL;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SP_Get_ProfitAndLossReportDetails` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_Get_ProfitAndLossReportDetails`()
BEGIN


SET @row_number=0;
drop temporary table if exists temp_Expence;

create temporary Table temp_Expence
SELECT
@row_number:=@row_number+1 as id,
C.ledgerName AS ledgerNameExpense ,
CASE WHEN(B.debitAmount IS NULL OR B.debitAmount=0)
THEN B.creditAmount ELSE B.debitAmount END AS amountExpense,
d.accTypeName AS accTypeNameExpence
FROM tbl_acc_voucher A
INNER JOIN  tbl_acc_voucher_detail B ON A.voucherId=B.voucherId
INNER JOIN tbl_acc_ledger C on C.ledgerId=B.ledgerId
INNER JOIN tbl_acc_acctype d on d.accTypeId=C.accTypeId
where d.accHeadTypeEffect='PL' AND d.accTypeId IN(10,13,14);


SET @row_number =0;
drop temporary table if exists temp_Income;
create temporary Table temp_Income
SELECT
@row_number:=@row_number+1 as id,
C.ledgerName AS ledgerNameIncome ,
CASE WHEN(B.debitAmount IS NULL OR B.debitAmount=0)
THEN B.creditAmount ELSE B.debitAmount END AS amountIncome,
d.accTypeName AS accTypeNameIncome
FROM tbl_acc_voucher A
INNER JOIN  tbl_acc_voucher_detail B ON A.voucherId=B.voucherId
INNER JOIN tbl_acc_ledger C on C.ledgerId=B.ledgerId
INNER JOIN tbl_acc_acctype d on d.accTypeId=C.accTypeId
where d.accHeadTypeEffect='PL' AND d.accTypeId IN(11,15);

select
A.ledgerNameExpense AS ledgerNameExpense,
A.amountExpense AS amountExpense,
A.accTypeNameExpence AS accTypeNameExpense,
B.ledgerNameIncome AS ledgerNameIncome,
B.amountIncome AS amountIncome,
B.accTypeNameIncome AS accTypeNameIncome
 from temp_Expence A
LEFT JOIN temp_Income B ON A.id=B.id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_inv_deletePurchaseRelatedEnteries` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_inv_deletePurchaseRelatedEnteries`(voucherNoParam INT(11),
itemCodeParam varchar(11), qty INT(11),companyId int,financialYearId int)
BEGIN

delete from tbl_inv_purchase_a where id=(select id from tbl_inv_purchase_a a
inner join tbl_inv_purchase b on a.purchaseId=b.purchaseId
where b.companyId=1 and financialYearId=1 and a.purchaseVoucherNo=4);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-12 19:05:41
