package com.acedmy.techcenture.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.List;

public class HomePage {

    private HomePage(){}

    private WebDriver driver;

    private SoftAssert softAssert;
    public HomePage(WebDriver driver,SoftAssert softAssert){
        this.driver = driver;
        this.softAssert = softAssert;
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




    public void verifyHomeElements(HashMap<String,String> data){
//        verify title
        verifyHomePageTitle();
//        verify user name
        verifyUserName();
//        verify home lists
        verifyHomeLists(data);

    }


    public void clickOnFindPatientRecordTab(){
        softAssert.assertTrue(findPatientRecordTab.isDisplayed() && findPatientRecordTab.isEnabled(),"FIND PATIENT RECORD TAB IS NOT ENABLED");
        findPatientRecordTab.click();
    }

    public void clickOnPatientRegisterTab(){
        softAssert.assertTrue(registerPatientTab.isDisplayed() && registerPatientTab.isEnabled(),"REGISTER PATIENT TAB IS NOT ENABLED");
        registerPatientTab.click();
    }

    public void clickOnLogoutBtn(){
        softAssert.assertTrue(logoutBtn.isDisplayed() && logoutBtn.isEnabled(),"LOGOUT BUTTON IS NOT ENABLED");
        logoutBtn.click();
    }

    private void verifyHomePageTitle(){
        Assert.assertEquals(driver.getTitle(),"Home", "TITLES DO NOT MATCH");
    }

    private void verifyUserName(){
        String actualUserName = adminText.getText().trim();
        softAssert.assertEquals(actualUserName, "admin", "USER NAME IS NOT ADMIN");
    }

    private void verifyHomeLists(HashMap<String,String > data){

        String[] homeListsExpected = data.get("Home Lists").split(",");

//        for (int i = 0; i < homeLists.size(); i++) {
//            String expectedHomeList = homeListsExpected[i];
//            String actualHomeList = homeLists.get(i).getText().trim();
//            softAssert.assertEquals(actualHomeList,expectedHomeList,"HOME LIST "+ actualHomeList +"DOES NOT MATCH");
//        }
    }

}
