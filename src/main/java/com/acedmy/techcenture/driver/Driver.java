package com.acedmy.techcenture.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static com.acedmy.techcenture.config.ConfigReader.getProperties;

public class Driver {

    private Driver(){}

    private static WebDriver driver;
    private static WebDriverWait wait;

    public static WebDriver getDriver(){

        String browser = getProperties("browser");

        switch (browser.toLowerCase()){
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                Map<String,Object> prefs = new HashMap<String,Object>();
                prefs.put("autofill.profile_enabled", false);
                prefs.put("profile.password_manager_enabled",false);
                prefs.put("profile.default_content_setting_values.notifications",2);
                options.setExperimentalOption("prefs",prefs);
                driver = new ChromeDriver(options);
                break;
            case "safari":
                WebDriverManager.safaridriver().setup();
                driver = new SafariDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                throw new RuntimeException("No  driver found!!!");
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(getProperties("implicitWait"))));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Integer.parseInt(getProperties("pageLoadTime"))));
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        return driver;
    }


    public static void quitDriver(){
        if (driver != null)
            driver.quit();
    }
}
