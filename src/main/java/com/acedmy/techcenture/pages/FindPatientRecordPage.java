package com.acedmy.techcenture.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.List;


public class FindPatientRecordPage {

    private final WebDriver driver;
    private final SoftAssert softAssert;

    public FindPatientRecordPage(WebDriver driver, SoftAssert softAssert){
        this.driver = driver;
        this.softAssert = softAssert;
        PageFactory.initElements(driver,this);
    }

    @FindBy(id = "patient-search")
    private WebElement searchInput;

    @FindBy(xpath = "//div[@id='content']/h2")
    private WebElement findPatientText;

    @FindBy(xpath = "//table[@id='patient-search-results-table']/tbody/tr[1]/td")
    private List<WebElement> patientInfo;


    public void findPatientActions(HashMap<String,String> data) {

        verifyPatientRecordTitle();

        verifyTopElements();

        searchById(data);

        verifyInfo(data);
    }

    private void verifyPatientRecordTitle(){
        String expectedTitle = "OpenMRS Electronic Medical Record";
        String actualTitle = driver.getTitle();
        softAssert.assertEquals(actualTitle, expectedTitle,"FIND PATIENT RECORDS TITLES DO NOT MATCH");
    }

    private void verifyTopElements(){
        softAssert.assertTrue(findPatientText.isDisplayed(),"FIND PATIENT TEXT IS NOT DISPLAYED");
        softAssert.assertTrue(searchInput.isEnabled(),"SEARCH INPUT IS NOT ENABLED");
    }

    private void searchById(HashMap<String,String> data){
        searchInput.sendKeys(data.get("patientId"));

    }

    private void verifyInfo(HashMap<String,String> data) {
        String expectedName = data.get("Given") +" " +( data.get("Middle").isEmpty() ? "" : data.get("Middle")  + " " )+ data.get("Family Name");
        String actualName = patientInfo.get(1).getText();
        softAssert.assertEquals(actualName,expectedName,"NAMES DO NOT MATCH");

        String actualGender = patientInfo.get(2).getText();
        String expectedGender = data.get("Gender").substring(0,1);
        softAssert.assertEquals(actualGender,expectedGender,"GENDERS DO NOT MATCH");

    }
}
