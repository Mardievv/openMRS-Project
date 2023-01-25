package com.acedmy.techcenture.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class BasePage {

    private final WebDriver driver;

    public BasePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//ul[@id='breadcrumbs']//a")
    private WebElement homeIcon;

    @FindBy(xpath = "//*[@id=\"coreapps-activeVisitsHomepageLink-coreapps-activeVisitsHomepageLink-extension\"]")
    private WebElement findPatientTab;

    @FindBy(partialLinkText = "Register a patient")
    private WebElement registerPatientTab;


    public void clickOnFindPatientTab(){
        Assert.assertTrue(findPatientTab.isDisplayed() && findPatientTab.isEnabled(),"FIND PATIENT IS NOT ENABLED");
        findPatientTab.click();
    }

    public void clickOnPatientRegisterTab(){
        Assert.assertTrue(registerPatientTab.isDisplayed() && registerPatientTab.isEnabled(),"REGISTER PATIENT TAB IS NOT ENABLED");
        registerPatientTab.click();
    }


}
