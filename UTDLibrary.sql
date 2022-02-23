-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: library
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `ISBN` varchar(45) NOT NULL,
  `Author(s)` varchar(45) DEFAULT NULL,
  `Edition` varchar(45) DEFAULT NULL,
  `PublicationDate` date DEFAULT NULL,
  `Publisher` varchar(45) DEFAULT NULL,
  `DeweyNumber` varchar(45) DEFAULT NULL,
  `CongressCatalogNum` int DEFAULT NULL,
  `NumOfPages` int DEFAULT NULL,
  `BookType` int DEFAULT NULL,
  `SectionID` int NOT NULL,
  `ItemType` int NOT NULL,
  PRIMARY KEY (`ISBN`),
  UNIQUE KEY `ISBN_UNIQUE` (`ISBN`),
  KEY `ItemType_idx` (`ItemType`),
  KEY `BookType_idx` (`BookType`),
  KEY `BookSection_idx` (`SectionID`),
  CONSTRAINT `BookSection` FOREIGN KEY (`SectionID`) REFERENCES `booksection` (`SectionNumber`),
  CONSTRAINT `BookType` FOREIGN KEY (`BookType`) REFERENCES `booktype` (`BookTypeNumber`),
  CONSTRAINT `ItemType` FOREIGN KEY (`ItemType`) REFERENCES `item` (`ItemType`),
  CONSTRAINT `ItemType2` FOREIGN KEY (`ItemType`) REFERENCES `itemtype` (`ItemTypeNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booksection`
--

DROP TABLE IF EXISTS `booksection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booksection` (
  `SectionNumber` int NOT NULL,
  `SectionName` varchar(45) NOT NULL,
  `LibrarianID` int NOT NULL,
  PRIMARY KEY (`SectionNumber`),
  KEY `LibrarianID_idx` (`LibrarianID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booksection`
--

LOCK TABLES `booksection` WRITE;
/*!40000 ALTER TABLE `booksection` DISABLE KEYS */;
/*!40000 ALTER TABLE `booksection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booktype`
--

DROP TABLE IF EXISTS `booktype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booktype` (
  `BookTypeNumber` int NOT NULL,
  `BookTypeCategory` varchar(45) NOT NULL COMMENT 'Paper book, ebook, audio book',
  PRIMARY KEY (`BookTypeNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booktype`
--

LOCK TABLES `booktype` WRITE;
/*!40000 ALTER TABLE `booktype` DISABLE KEYS */;
/*!40000 ALTER TABLE `booktype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrows`
--

DROP TABLE IF EXISTS `borrows`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `borrows` (
  `BorrowNumber` int NOT NULL AUTO_INCREMENT,
  `UTD_ID` int NOT NULL,
  `ItemNumber` int NOT NULL,
  `CheckOutDate` date NOT NULL,
  `CheckInDate` date DEFAULT NULL,
  `DueDate` date DEFAULT NULL,
  `LateFee` double DEFAULT NULL,
  `NumberOfItemsLimit` int DEFAULT NULL,
  PRIMARY KEY (`BorrowNumber`),
  KEY `MemberID_idx` (`UTD_ID`),
  KEY `ItemID_idx` (`ItemNumber`),
  CONSTRAINT `ItemID` FOREIGN KEY (`ItemNumber`) REFERENCES `item` (`ItemNumber`),
  CONSTRAINT `MemberID` FOREIGN KEY (`UTD_ID`) REFERENCES `member` (`UTD_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrows`
--

LOCK TABLES `borrows` WRITE;
/*!40000 ALTER TABLE `borrows` DISABLE KEYS */;
/*!40000 ALTER TABLE `borrows` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cddvd`
--

DROP TABLE IF EXISTS `cddvd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cddvd` (
  `itemtype` int NOT NULL,
  `Artist(s)` varchar(45) DEFAULT NULL,
  `NumberOfTracks` int DEFAULT NULL,
  `cd_dvd type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`itemtype`),
  CONSTRAINT `itemtype3` FOREIGN KEY (`itemtype`) REFERENCES `item` (`ItemType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cddvd`
--

LOCK TABLES `cddvd` WRITE;
/*!40000 ALTER TABLE `cddvd` DISABLE KEYS */;
/*!40000 ALTER TABLE `cddvd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faculty`
--

DROP TABLE IF EXISTS `faculty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faculty` (
  `membertype` int NOT NULL,
  `departmentID` int DEFAULT NULL,
  `Rank` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`membertype`),
  CONSTRAINT `membertype3` FOREIGN KEY (`membertype`) REFERENCES `member` (`MemberType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculty`
--

LOCK TABLES `faculty` WRITE;
/*!40000 ALTER TABLE `faculty` DISABLE KEYS */;
/*!40000 ALTER TABLE `faculty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `ItemNumber` int NOT NULL AUTO_INCREMENT,
  `Location` varchar(45) DEFAULT NULL,
  `Title` varchar(45) NOT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `Availibility` enum('Available','CheckedOut','Hold','LateReturn') DEFAULT NULL,
  `ItemType` int NOT NULL,
  PRIMARY KEY (`ItemNumber`),
  KEY `itemtype_idx` (`ItemType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itemtype`
--

DROP TABLE IF EXISTS `itemtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `itemtype` (
  `ItemTypeNumber` int NOT NULL,
  `ItemTypeName` enum('CD/DVD','Magazine','Book') DEFAULT NULL,
  PRIMARY KEY (`ItemTypeNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itemtype`
--

LOCK TABLES `itemtype` WRITE;
/*!40000 ALTER TABLE `itemtype` DISABLE KEYS */;
/*!40000 ALTER TABLE `itemtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `librarian`
--

DROP TABLE IF EXISTS `librarian`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `librarian` (
  `LibrarianID` int NOT NULL,
  `LibrarianName` varchar(45) NOT NULL,
  PRIMARY KEY (`LibrarianID`),
  CONSTRAINT `LibrarianID2` FOREIGN KEY (`LibrarianID`) REFERENCES `booksection` (`LibrarianID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `librarian`
--

LOCK TABLES `librarian` WRITE;
/*!40000 ALTER TABLE `librarian` DISABLE KEYS */;
/*!40000 ALTER TABLE `librarian` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manageitem`
--

DROP TABLE IF EXISTS `manageitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manageitem` (
  `ItemNumber` int NOT NULL,
  `LibrarianID` int DEFAULT NULL,
  PRIMARY KEY (`ItemNumber`),
  KEY `LibrarianID_idx` (`LibrarianID`),
  CONSTRAINT `ItemNumber` FOREIGN KEY (`ItemNumber`) REFERENCES `item` (`ItemNumber`),
  CONSTRAINT `LibrarianID` FOREIGN KEY (`LibrarianID`) REFERENCES `librarian` (`LibrarianID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manageitem`
--

LOCK TABLES `manageitem` WRITE;
/*!40000 ALTER TABLE `manageitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `manageitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `UTD_ID` int NOT NULL,
  `FirstName` varchar(45) NOT NULL,
  `LastName` varchar(45) NOT NULL,
  `Address` varchar(100) DEFAULT NULL,
  `MemberType` int NOT NULL,
  `PhoneNumber` char(10) DEFAULT NULL,
  `Gender` char(1) DEFAULT NULL,
  `MemberNumer` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`UTD_ID`),
  KEY `MemberNumber` (`MemberNumer`),
  KEY `membertype_idx` (`MemberType`),
  CONSTRAINT `membertype` FOREIGN KEY (`MemberType`) REFERENCES `membertype` (`MemberType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `membertype`
--

DROP TABLE IF EXISTS `membertype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `membertype` (
  `MemberType` int NOT NULL,
  `MemberTypeName` enum('Faculty','GradStudent','UndergradStudent') NOT NULL COMMENT '''Faculty'', ''GradStudent'', ''UndergradStudent''',
  `Major` varchar(45) DEFAULT NULL,
  `Department` varchar(45) DEFAULT NULL,
  `UndergradDegreeEarned` varchar(45) DEFAULT NULL COMMENT 'GradStudent Only',
  `NumerOfItemsLimit` int NOT NULL,
  PRIMARY KEY (`MemberType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `membertype`
--

LOCK TABLES `membertype` WRITE;
/*!40000 ALTER TABLE `membertype` DISABLE KEYS */;
/*!40000 ALTER TABLE `membertype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `membertype` int NOT NULL,
  `studenttype` enum('Grad','Undergrad') DEFAULT NULL,
  `studentMajor` varchar(45) DEFAULT NULL,
  `DegreeEarned` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`membertype`),
  CONSTRAINT `membertype2` FOREIGN KEY (`membertype`) REFERENCES `member` (`MemberType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-17 21:58:37
