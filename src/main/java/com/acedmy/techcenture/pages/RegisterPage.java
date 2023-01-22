package com.acedmy.techcenture.pages;

import com.acedmy.techcenture.config.ConfigReader;
import com.acedmy.techcenture.utilities.Utils;
import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;
import java.util.Locale;

import static com.acedmy.techcenture.config.ConfigReader.getProperties;
import static com.acedmy.techcenture.config.ConfigReader.setProperties;
import static com.acedmy.techcenture.utilities.Utils.generateRandomNumber;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class RegisterPage {

    private WebDriver driver;
    private Faker faker = new Faker(new Locale("en-US"));

    public RegisterPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }



    @FindBy(xpath = "//input[@name='givenName']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@name='middleName']")
    private WebElement middleNameInput;

    @FindBy(xpath = "//input[@name='familyName']")
    private WebElement lastNameNameInput;

    @FindBy(xpath = "//label[@for='checkbox-unknown-patient']")
    private WebElement unknownCheckbox;

    @FindBy(id = "next-button")
    private WebElement nextBtn;

    @FindBy(id = "gender-field")
    private WebElement selectGender;

    @FindBy(id = "birthdateDay-field")
    private WebElement birthDay;

    @FindBy(id = "birthdateMonth-field")
    private WebElement birthMonthSelect;

    @FindBy(id = "birthdateYear-field")
    private WebElement birthYear;

    @FindBy(id = "birthdateYears-field")
    private WebElement estimatedYear;

    @FindBy(id = "birthdateMonths-field")
    private WebElement estimatedMoth;

    @FindBy(id = "address1")
    private WebElement address1Input;

    @FindBy(id = "address2")
    private WebElement address2Input;

    @FindBy(id = "cityVillage")
    private WebElement cityInput;

    @FindBy(id = "stateProvince")
    private WebElement stateInput;

    @FindBy(id = "country")
    private WebElement countryInput;

    @FindBy(id = "postalCode")
    private WebElement zipCodeInput;

    @FindBy(xpath = "//input[@name='phoneNumber']")
    private WebElement phoneNumberInput;

    @FindBy(id = "relationship_type")
    private WebElement relationshipTypeSelect;

    @FindBy(xpath = "//input[@placeholder='Person Name']")
    private WebElement relationshipPersonName;

    @FindBy(xpath = "//div[@id='dataCanvas']//p/span")
    private List<WebElement> confirmInfoNames;

    @FindBy(xpath = "//div[@id='dataCanvas']//p")
    private List<WebElement> confirmInfoValues;

    @FindBy(id = "cancelSubmission")
    private WebElement cancelSubmissionBtn;

    @FindBy(id = "submit")
    private WebElement submitBtn;


    public void fillOutPatientInfo() throws InterruptedException {
        fillOutPatientName();

        selectGender();

        fillOutPatientsBirthDate();

        fillOutPatientsAddress();

        fillOutPatientPhoneNumber();

        fillOutRelatives();
    }



    private void verifyTitle(){
        assertEquals(driver.getTitle().trim(), "OpenMRS Electronic Medical Record", "TITLES DO NOT MATCH");
    }

    private void fillOutPatientName() throws InterruptedException {
        setProperties("given", faker.name().firstName());
        firstNameInput.sendKeys(getProperties("given"));

        setProperties("middle", faker.name().lastName()); // need change to middle name
        middleNameInput.sendKeys(getProperties("middle"));

        setProperties("family name", faker.name().lastName());
        lastNameNameInput.sendKeys(getProperties("family name"));

        Thread.sleep(5000);

        assertTrue(nextBtn.isDisplayed() && nextBtn.isEnabled(),"NEXT BUTTON IS NOT ENABLED");
        nextBtn.click();
    }

    private void selectGender() throws InterruptedException {
        selectGender.click();
        Select select = new Select(selectGender);
        select.selectByIndex(1);
        String selectedGender = select.getFirstSelectedOption().getText();
        setProperties("gender", selectedGender);

        Thread.sleep(5000);

        assertTrue(nextBtn.isDisplayed() && nextBtn.isEnabled(),"NEXT BUTTON IS NOT ENABLED");
        nextBtn.click();
    }

    private void fillOutPatientsBirthDate() throws InterruptedException {
        setProperties("day",faker.date().birthday().getDate()+"");
        birthDay.sendKeys(getProperties("day"));

        Select select = new Select(birthMonthSelect);
        select.selectByIndex(generateRandomNumber(1,12));
        String selectedMonth = select.getFirstSelectedOption().getText();
        setProperties("month", selectedMonth);

        setProperties("year",generateRandomNumber(1970, 2023)+"");
        birthYear.sendKeys(getProperties("year"));

        Thread.sleep(5000);

        assertTrue(nextBtn.isDisplayed() && nextBtn.isEnabled(),"NEXT BUTTON IS NOT ENABLED");
        nextBtn.click();
    }

    private void fillOutPatientsAddress() throws InterruptedException {
        setProperties("address1", faker.address().streetAddress());
        address1Input.sendKeys(getProperties("address1"));

        setProperties("address2", faker.address().buildingNumber());
        address2Input.sendKeys(getProperties("address2"));

        setProperties("city", faker.address().city());
        cityInput.sendKeys(getProperties("city"));

        String stateAbbr = faker.address().stateAbbr();
        setProperties("state", stateAbbr);
        stateInput.sendKeys(getProperties("state"));

        setProperties("country", faker.address().country());
        countryInput.sendKeys(getProperties("country"));

        setProperties("zip", faker.address().zipCodeByState(stateAbbr));
        zipCodeInput.sendKeys(getProperties("zip"));

        Thread.sleep(5000);

        assertTrue(nextBtn.isDisplayed() && nextBtn.isEnabled(),"NEXT BUTTON IS NOT ENABLED");
        nextBtn.click();
    }

    private void fillOutPatientPhoneNumber() throws InterruptedException {
        setProperties("phoneNumber", faker.phoneNumber().cellPhone()+"");
        phoneNumberInput.sendKeys(getProperties("phoneNumber"));

        Thread.sleep(5000);

        assertTrue(nextBtn.isDisplayed() && nextBtn.isEnabled(),"NEXT BUTTON IS NOT ENABLED");
        nextBtn.click();
    }

    private void fillOutRelatives() throws InterruptedException {
        Select select = new Select(relationshipTypeSelect);
        select.selectByIndex(generateRandomNumber(1,9));
        String firstSelectedRelType = select.getFirstSelectedOption().getText();
        setProperties("relationshipType", firstSelectedRelType);

        setProperties("relName", faker.name().firstName());
        relationshipPersonName.sendKeys(getProperties("relName"));

        Thread.sleep(5000);

        assertTrue(nextBtn.isDisplayed() && nextBtn.isEnabled(),"NEXT BUTTON IS NOT ENABLED");
        nextBtn.click();

        for (int i = 0; i < confirmInfoNames.size(); i++) {
            System.out.println(confirmInfoNames.get(i).getText());
            System.out.println(confirmInfoValues.get(i).getText());
        }
    }





    public static void main(String[] args) {
        Faker faker = new Faker();
        System.out.println(faker.phoneNumber().cellPhone());

    }

}
