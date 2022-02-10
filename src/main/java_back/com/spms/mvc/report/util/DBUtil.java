package com.spms.mvc.report.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBUtil{

private static final String REPORTING_DB_USERNAME="REPORTING_DB_USERNAME";
private static final String REPORTING_DB_PASSWORD="REPORTING_DB_PASSWORD";

private static final String ARCHIVE_DB_USERNAME="REPORTING_DB_USERNAME";
private static final String ARCHIVE_DB_PASSWORD="REPORTING_DB_PASSWORD";

private static final String TRANSACTIONAL_DB_USERNAME="REPORTING_DB_USERNAME";
private static final String TRANSACTIONAL_DB_PASSWORD="REPORTING_DB_USERNAME";

private static final String REPORTING_DB_CONNECTION_STRING="REPORTING_DB_CONNECTION_STRING";
private static final String ARCHIVE_DB_CONNECTION_STRING="ARCHIVE_DB_CONNECTION_STRING";
private static final String TRANSACTIONAL_DB_CONNECTION_STRING="TRANSACTIONAL_DB_CONNECTION_STRING";


public static Connection getConnection(DBType dbType, String fileName) throws SQLException, IOException {
Properties property= new Properties();
FileReader fileReader= new FileReader(fileName);
property.load(fileReader);
switch(dbType){
	case ReportingDB:
		return DriverManager.getConnection(property.getProperty(REPORTING_DB_CONNECTION_STRING), property.getProperty(REPORTING_DB_USERNAME), property.getProperty(REPORTING_DB_PASSWORD));

	case ArchiveDB:
		return DriverManager.getConnection(property.getProperty(ARCHIVE_DB_CONNECTION_STRING), property.getProperty(ARCHIVE_DB_USERNAME),property.getProperty(ARCHIVE_DB_PASSWORD));

	case TransactionalDB:
		return DriverManager.getConnection(property.getProperty(TRANSACTIONAL_DB_CONNECTION_STRING), property.getProperty(TRANSACTIONAL_DB_USERNAME),property.getProperty(TRANSACTIONAL_DB_PASSWORD));
	
	default: 
		return null;
}
}

}