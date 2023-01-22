package com.acedmy.techcenture.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;

import static com.acedmy.techcenture.config.ConfigReader.getProperties;

public class Driver {

    private Driver(){}

    private static WebDriver driver;

    public static void waitConfiguration(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(getProperties("implicitWait"))));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Integer.parseInt(getProperties("pageLoadTime"))));
        driver.manage().window().maximize();
    }

    public static WebDriver getDriver(){

        String browser = "chrome";

        if (browser.equals("chrome")){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            waitConfiguration();
            return driver;
        }else if(browser.equals("safari")){
            WebDriverManager.safaridriver().setup();
            driver = new SafariDriver();
            waitConfiguration();
            return driver;
        }else if(browser.equals("firefox")){
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            waitConfiguration();
            return driver;
        }else {
            throw new RuntimeException("No  driver found!!!");
        }

//        switch (browser){
//            case "chrome":
//                WebDriverManager.chromedriver().setup();
//                driver = new ChromeDriver();
//                waitConfiguration();
//                return driver;
//            case "safari":
//                WebDriverManager.safaridriver().setup();
//                driver = new SafariDriver();
//                waitConfiguration();
//                return driver;
//            case "firefox":
//                WebDriverManager.firefoxdriver().setup();
//                driver = new FirefoxDriver();
//                waitConfiguration();
//                return driver;
//            case "edge":
//                WebDriverManager.edgedriver().setup();
//                driver = new EdgeDriver();
//                waitConfiguration();
//                return driver;
//            default:
//                throw new RuntimeException("No  driver found!!!");
//        }
    }



    /**
     * This method will quite Driver
     */
   public static void quiteDriver(){
       if (driver != null){
           driver.quit();
       }
   }

    public static void main(String[] args) {
        String browser = getProperties("browser");// chrome
        System.out.println(browser);
    }

}
