package com.acedmy.techcenture.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

import static com.acedmy.techcenture.config.ConfigReader.getProperties;

public class FindPatientRecordPage {

    private final WebDriver driver;

    public FindPatientRecordPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(id = "patient-search")
    private WebElement searchInput;

    @FindBy(xpath = "//div[@id='content']/h2")
    private WebElement findPatientText;

    @FindBy(xpath = "//table[@id='patient-search-results-table']/tbody/tr[1]/td")
    private List<WebElement> patientInfo;


    public void findPatientActions() {

        verifyTitle();

        verifyTopElements();

        searchById();

        verifyInfo();
    }

    private void verifyTitle(){
        String expectedTitle = "OpenMRS Electronic Medical Record";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle,"FIND PATIENT RECORDS TITLES DO NOT MATCH");
    }

    private void verifyTopElements(){
        Assert.assertTrue(findPatientText.isDisplayed(),"FIND PATIENT TEXT IS NOT DISPLAYED");
        Assert.assertTrue(searchInput.isEnabled(),"SEARCH INPUT IS NOT ENABLED");
    }

    private void searchById(){
        searchInput.sendKeys(getProperties("patientId"));

    }

    private void verifyInfo() {
        String expectedName = getProperties("Name:").replace(",", "");
        String actualName = "Name: " + patientInfo.get(1).getText();
        Assert.assertEquals(actualName,expectedName,"NAMES DO NOT MATCH");

        String actualGender = "Gender: " + patientInfo.get(2).getText();
        String expectedGender = getProperties("Gender:").substring(0, 9);
        Assert.assertEquals(actualGender,expectedGender,"GENDERS DO NOT MATCH");

    }





}
