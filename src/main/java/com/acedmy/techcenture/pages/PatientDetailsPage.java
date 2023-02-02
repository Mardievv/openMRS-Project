package com.acedmy.techcenture.pages;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.List;


public class PatientDetailsPage {

    private final WebDriver driver;
    private SoftAssert softAssert;

    private final Faker faker = new Faker();

    public PatientDetailsPage(WebDriver driver, SoftAssert softAssert){
        this.driver = driver;
        this.softAssert = softAssert;
        PageFactory.initElements(driver,this);
    }


    @FindBy(xpath = "//ul[@id='breadcrumbs']//a")
    private WebElement homeIcon;

    @FindBy(className = "PersonName-givenName")
    private WebElement givenName;

    @FindBy(className = "PersonName-middleName")
    private WebElement middleName;

    @FindBy(className = "PersonName-familyName")
    private WebElement familyName;

    @FindBy(xpath = "//div[@class='identifiers mt-2 col-12 col-sm-5 col-md-4']//span")
    private WebElement patientId;

    @FindBy(xpath = "//*[@id=\"TextField\"]/pre/i")
    private WebElement stickyNote;

    @FindBy(xpath = "//textarea[@placeholder='Enter a note']")
    private WebElement stickyNoteTextarea;

    @FindBy(xpath = "//button[@title='Submit']")
    private WebElement submitTextarea;

    @FindBy(xpath = "//div[@class='toast-container toast-position-top-right']")
    private WebElement successMsg;

    @FindBy(xpath = "//pre[@class='preformatted-note ng-binding']")
    private WebElement preformattedNote;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div/div/div/div/div/h3")
    private List<WebElement> patientFunctionality;

    @FindBy(xpath = "//ul[@class='float-left']/li/a")
    private List<WebElement> generalActions;




    public void verifyPatientsElement(HashMap<String,String> data){
        verifyNames(data);
        stickyNoteActions(data);
        verifyPatientFunctionality(data);
        verifyGeneralActions(data);

    }


    private void verifyNames(HashMap<String,String> data){
        boolean isMiddleNameAvailable = driver.findElements(By.xpath("//span[@class='labeled']")).size() == 3;
        String actualGivenName = givenName.getText().trim();
        String actualMiddleName = isMiddleNameAvailable ? middleName.getText().trim() + " " : "";
        String actualLasName = familyName.getText().trim();

        data.put("patientId", patientId.getText());

        String expectedFullName = (data.get("Given") +" " + (data.get("Middle").isEmpty() ? "" : data.get("Middle") + " ")) + data.get("Family Name");

        String actualFullName = actualGivenName + " " + actualMiddleName + actualLasName;
        softAssert.assertEquals(actualFullName, expectedFullName,"NAMES DO NOT MATCH");
    }

    private void stickyNoteActions(HashMap<String,String > data){
        softAssert.assertTrue(stickyNote.isEnabled(),"STICKY NOT IS NOT ENABLED");
        stickyNote.click();

        stickyNoteTextarea.sendKeys(data.get("StickyNote"));

        softAssert.assertTrue(submitTextarea.isEnabled(),"SUBMIT TEXTAREA IS NOT ENABLED");
        submitTextarea.click();
        softAssert.assertTrue(successMsg.isDisplayed(),"SUCCESS MESSAGE IS NOT DISPLAYED");

        softAssert.assertTrue(preformattedNote.isDisplayed(),"NOTE IS DISPLAYED");
    }

    private void verifyPatientFunctionality(HashMap<String,String> data) {
        String[] expectedFunctionalityList = data.get("Functionality List").split(",");
        for (int i = 0; i < patientFunctionality.size(); i++) {
            String expectedFunctionality = expectedFunctionalityList[i];
            String actualFunctionality = patientFunctionality.get(i).getText().trim().toLowerCase();
            softAssert.assertEquals(actualFunctionality,expectedFunctionality,"FUNCTIONALITY " + actualFunctionality + "FAILED");
        }
    }
    
    private void verifyGeneralActions(HashMap<String,String> data){
        String[] expectedGeneralActions = data.get("General Actions").split(",");
        for (int i = 0; i < generalActions.size(); i++) {
            String expectedGeneralActionsItem = expectedGeneralActions[i];
            WebElement actualGeneralActionsItem = generalActions.get(i);
            softAssert.assertTrue(actualGeneralActionsItem.isDisplayed() && actualGeneralActionsItem.isEnabled(),"GENERAL ACTION: " + actualGeneralActionsItem + "FAILED");
            softAssert.assertEquals(actualGeneralActionsItem.getText(),expectedGeneralActionsItem,"GENERAL ACTIONS DO NOT MATCH: " + actualGeneralActionsItem);
            homeIcon.click();
        }
    }

}
