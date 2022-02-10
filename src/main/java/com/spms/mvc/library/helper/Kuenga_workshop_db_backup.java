package com.spms.mvc.library.helper;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by vcass on 1/28/2018.
 */
public class Kuenga_workshop_db_backup {

    //    public static void main(String args[]) throws IOException {
    public void backupDataDatabase() throws IOException {

        Logger logger = Logger.getLogger("Kuenga_workshop_db_backup");
        String host = loadMySQLConfiguration().getProperty("mysql_db_configuration.host");
        String port = loadMySQLConfiguration().getProperty("mysql_db_configuration.port");
        String user = loadMySQLConfiguration().getProperty("mysql_db_configuration.user");
        String password = loadMySQLConfiguration().getProperty("mysql_db_configuration.password");
        String database = loadMySQLConfiguration().getProperty("mysql_db_configuration.database");
        String backupPath = loadMySQLConfiguration().getProperty("mysql_db_configuration.backupPath");
        String dumpExePath = loadMySQLConfiguration().getProperty("mysql_db_configuration.dumpExePath");

        try {
            String batchCommand;
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String filePath = "" + database + "-" + host + "-(" + dateFormat.format(new Date()) + ").sql";

            batchCommand = mySQLDumpQuery(host, port, user, password, database, backupPath, dumpExePath, filePath);

            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(batchCommand);

            if (process.waitFor() == 0) {
                logger.info("Backup created successfully for without DB " + database + " in " + host + ":" + port);
            } else {
                logger.info("Could not create the backup for  " + database + " in " + host + ":" +
                        port + "username" + user + "password" + password + "database" + database + "backup_path" + backupPath + "dump exe path" + dumpExePath);
            }

        } catch (IOException ioe) {
            logger.warning(ioe.getCause().toString());
        } catch (Exception e) {
            logger.warning(e.getCause().toString());
        }
    }

    /**
     * my sql dump query
     *
     * @param host        host
     * @param port        port
     * @param user        user
     * @param password    password
     * @param database    database
     * @param backupPath  backupPath
     * @param dumpExePath dumpExePath
     * @param filePath    filePath
     * @return String
     */
    private String mySQLDumpQuery(String host, String port, String user, String password, String database, String backupPath, String dumpExePath, String filePath) {
        String batchCommand;
        if (!password.equals("")) {
            batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u " + user + " --password=" + password + " " + database + " -r \"" + backupPath + "" + filePath + "\"";
        } else {
            batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u " + user + " " + database + " -r \"" + backupPath + "" + filePath + "\"";
        }
        return batchCommand;
    }

    /**
     * load my sql configuration properties
     *
     * @return Properties Properties
     * @throws java.io.IOException
     */
    private Properties loadMySQLConfiguration() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mysql_db_configuration.properties");
        properties.load(inputStream);
        return properties;

    }
}


