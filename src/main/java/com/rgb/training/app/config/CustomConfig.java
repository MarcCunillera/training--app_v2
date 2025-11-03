package com.rgb.training.app.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.attribute.FileTime;
import java.util.Properties;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CustomConfig {

    private static String ODOO_URL = "";
    private static String ODOO_DB_NAME = "";
    private static String ODOO_USER_ID = "";
    private static String ODOO_PASSWORD = "";
    private static FileTime fileTime;
    private static final Path PATH = Paths.get("/home/marccunillera/Documentos/odoo-config.properties");

    static {
        loadConfig();
    }

    private static void loadConfig() {
        Properties props = new Properties();

        try (FileInputStream fis = new FileInputStream(PATH.toFile())) {
            props.load(fis);

            ODOO_URL = props.getProperty("ODOO_URL", "");
            ODOO_DB_NAME = props.getProperty("ODOO_DB_NAME", "");
            ODOO_USER_ID = props.getProperty("ODOO_USER_ID", "");
            ODOO_PASSWORD = props.getProperty("ODOO_PASSWORD", "");
            fileTime = Files.getLastModifiedTime(PATH);

        } catch (IOException e) {
            System.err.println("[CONFIG] Error carregant fitxer de configuració: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private synchronized static void checkAndReload() {
        try {
            if (ODOO_URL.isEmpty() || ODOO_DB_NAME.isEmpty() || 
                ODOO_PASSWORD.isEmpty() || ODOO_USER_ID.isEmpty() ||
                fileTime == null || !fileTime.equals(Files.getLastModifiedTime(PATH))) {
                
                loadConfig();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getODOO_URL() {
        checkAndReload();
        return ODOO_URL;
    }

    public static String getODOO_DB_NAME() {
        checkAndReload();
        return ODOO_DB_NAME;
    }

    public static String getODOO_USER_ID() {
        checkAndReload();
        return ODOO_USER_ID;
    }
    
    public static String getODOO_PASSWORD() {
        checkAndReload();
        return ODOO_PASSWORD;
    }
}
