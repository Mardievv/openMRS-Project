package com.acedmy.techcenture.pages;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.acedmy.techcenture.config.ConfigReader.getProperties;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class PatientDetailsPage {

    private final WebDriver driver;

    private final Faker faker = new Faker();

    public PatientDetailsPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

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

    public void verifyNames(){
        String expectedFullName = getProperties("Name:");
        String actualGivenName = givenName.getText().trim();
        String actualMiddleName = middleName.getText().trim();
        String actualLasName = familyName.getText().trim();

        String actualFullName = "Name: "+actualGivenName+", "+actualMiddleName+", "+actualLasName;
        assertEquals(actualFullName, expectedFullName,"NAMES DO NOT MATCH");
    }

    public void stickyNoteActions() throws InterruptedException {
        assertTrue(stickyNote.isEnabled(),"STICKY NOT IS NOT ENABLED");
        stickyNote.click();

        String funnyName = faker.funnyName().name();
        stickyNoteTextarea.sendKeys(funnyName);

        assertTrue(submitTextarea.isEnabled(),"SUBMIT TEXTAREA IS NOT ENABLED");
        submitTextarea.click();
    }


}
