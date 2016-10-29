package com.nulp.rock.common;


import org.testng.Reporter;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final String PROPERTIES_FILE_NAME = "project.properties";
    public static Properties config;

    public static int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }

    public static String getProperty(String key) {
        return getProperty(key, null);
    }

    public static String getProperty(String key, String defaultValue) {
        String propertyValue = defaultValue;
        try {
            if (config == null) {
                config = loadProperties(PROPERTIES_FILE_NAME);
            }
            if (config.containsKey(key)) {
                propertyValue = config.getProperty(key);
            }
        } catch (Exception e) {
            Reporter.log("Error while reading config: " + e.getMessage(), 2,
                    true);
        }
        return propertyValue;
    }

    public static Properties loadProperties(String path) throws Exception {
        Properties result = new Properties();
        InputStream in = Config.class.getClassLoader()
                .getResourceAsStream(path);
        if (in == null) {
            in = new FileInputStream(path);
        }
        result.load(in);
        return result;
    }
}
