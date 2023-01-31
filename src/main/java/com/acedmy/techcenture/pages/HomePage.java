package com.acedmy.techcenture.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;
import java.util.Properties;

public class HomePage {

    private HomePage(){}

    private WebDriver driver;

    private Properties properties;

    public HomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }


    @FindBy(xpath = "//li[@class='nav-item identifier']")
    private WebElement adminText;

    @FindBy(xpath = "//a[contains(text(), 'Logout')]")
    private WebElement logoutBtn;

    @FindBy(xpath = "//div[@id='apps']/a")
    private List<WebElement> homeLists;

    @FindBy(xpath = "//*[@id=\"coreapps-activeVisitsHomepageLink-coreapps-activeVisitsHomepageLink-extension\"]")
    private WebElement findPatientRecordTab;

    @FindBy(partialLinkText = "Register a patient")
    private WebElement registerPatientTab;




    public void verifyHomeElements(){
//        verify title
        verifyTitle();
//        verify user name
        verifyUserName();
//        verify home lists
        verifyHomeLists();

    }


    public void clickOnFindPatientRecordTab(){
        Assert.assertTrue(findPatientRecordTab.isDisplayed() && findPatientRecordTab.isEnabled(),"FIND PATIENT RECORD TAB IS NOT ENABLED");
        findPatientRecordTab.click();
    }

    public void clickOnPatientRegisterTab(){
        Assert.assertTrue(registerPatientTab.isDisplayed() && registerPatientTab.isEnabled(),"REGISTER PATIENT TAB IS NOT ENABLED");
        registerPatientTab.click();
    }

    public void clickOnLogoutBtn(){
        Assert.assertTrue(logoutBtn.isDisplayed() && logoutBtn.isEnabled(),"LOGOUT BUTTON IS NOT ENABLED");
        logoutBtn.click();
    }

    private void verifyTitle(){
        Assert.assertEquals(driver.getTitle(),"Home", "TITLES DO NOT MATCH");
    }

    private void verifyUserName(){
        String actualUserName = adminText.getText().trim();
        Assert.assertEquals(actualUserName, "admin", "USER NAME IS NOT ADMIN");
    }

    private void verifyHomeLists(){

        String[] homeListsExpected = {
                "Find Patient Record","Active Visits","Register a patient","Capture Vitals",
                "Appointment Scheduling","Reports","Data Management",
                "Configure Metadata","System Administration"
        };

        for (int i = 0; i < homeLists.size(); i++) {
            String expectedHomeList = homeListsExpected[i];
            String actualHomeList = homeLists.get(i).getText().trim();
            Assert.assertEquals(actualHomeList,expectedHomeList,"HOME LIST "+ actualHomeList +"DOES NOT MATCH");
        }
    }





}
