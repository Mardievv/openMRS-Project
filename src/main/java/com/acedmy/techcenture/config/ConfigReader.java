package com.acedmy.techcenture.config;

import org.openqa.selenium.WebDriver;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {

    private ConfigReader(){}

    private WebDriver driver;

    private static Properties properties;

    private static final String CONFIG_FILE_PATH = "src/main/resources/config.properties";

    static {
        try {
            FileInputStream inputStream = new FileInputStream(CONFIG_FILE_PATH);
            properties = new Properties();
            properties.load(inputStream);
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static String getProperties(String keyName){
        return properties.getProperty(keyName);
    }

    public static void setProperties(String keyName, String value){
        properties.setProperty(keyName,value);
    }

}
