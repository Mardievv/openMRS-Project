package com.acedmy.techcenture.pages;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Locale;

import static com.acedmy.techcenture.config.ConfigReader.getProperties;
import static com.acedmy.techcenture.config.ConfigReader.setProperties;
import static com.acedmy.techcenture.utilities.Utils.generateRandomNumber;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class RegisterPage {

    private final WebDriver driver;
    private final Faker faker = new Faker(new Locale("en-US"));

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

    @FindBy(xpath = "//div[@id='dataCanvas']//p/span[1]")
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

        clickOnSubmitButton();
    }



    private void verifyTitle(){
        assertEquals(driver.getTitle().trim(), "OpenMRS Electronic Medical Record", "TITLES DO NOT MATCH");
    }

    private void fillOutPatientName() {
        String givenName = faker.name().firstName();
        firstNameInput.sendKeys(givenName);

        String middleName = faker.name().lastName();
        middleNameInput.sendKeys(middleName);

        String lastName = faker.name().lastName();
        lastNameNameInput.sendKeys(lastName);

        String fullName = "Name: " + givenName +", " + middleName + ", " + lastName;
        setProperties("Name:", fullName);

        assertTrue(nextBtn.isDisplayed() && nextBtn.isEnabled(),"NEXT BUTTON IS NOT ENABLED");
        nextBtn.click();
    }

    private void selectGender(){
        selectGender.click();
        Select select = new Select(selectGender);
        select.selectByIndex(generateRandomNumber(0,1));
        String selectedGender = select.getFirstSelectedOption().getText();
        setProperties("Gender:", "Gender: " + selectedGender);

        assertTrue(nextBtn.isDisplayed() && nextBtn.isEnabled(),"NEXT BUTTON IS NOT ENABLED");
        nextBtn.click();
    }

    private void fillOutPatientsBirthDate() throws InterruptedException {

        int rndNumber = generateRandomNumber(1, 2);
        setProperties("rndNumber", rndNumber+"");

        if (rndNumber == 1) {
            int birthDay = faker.date().birthday().getDate();
            this.birthDay.sendKeys(birthDay + "");

            Select select = new Select(birthMonthSelect);
            select.selectByIndex(generateRandomNumber(1, 12));
            String selectedMonth = select.getFirstSelectedOption().getText();

            int birthOfYear = generateRandomNumber(1970, 2022);
            birthYear.sendKeys(birthOfYear + "");

            String fullDOB = "Birthdate: " + birthDay + ", " + selectedMonth + ", " + birthOfYear;
            setProperties("Birthdate:", fullDOB);
        }else {
                int randomEstYear = generateRandomNumber(1, 119);
                estimatedYear.sendKeys(randomEstYear+"");

                int randomEstMonth = generateRandomNumber(1,11);
                estimatedMoth.sendKeys(randomEstMonth+"");

                String monthYear = "Birthdate: " + randomEstYear + " year(s), " + randomEstMonth + " month(s)";
                setProperties("Birthdate:",monthYear);
        }
        assertTrue(nextBtn.isDisplayed() && nextBtn.isEnabled(),"NEXT BUTTON IS NOT ENABLED");
        nextBtn.click();
    }

    private void fillOutPatientsAddress() {
        String streetAddress = faker.address().streetAddress();
        address1Input.sendKeys(streetAddress);

        String buildingNumber = faker.address().buildingNumber();
        address2Input.sendKeys(buildingNumber);

        String city = faker.address().city();
        cityInput.sendKeys(city);

        String stateAbbr = faker.address().stateAbbr();
        stateInput.sendKeys(stateAbbr);

        String country = faker.address().country();
        countryInput.sendKeys(country);

        String zipCodeByState = faker.address().zipCodeByState(stateAbbr);
        zipCodeInput.sendKeys(zipCodeByState);

        String fullAddress = "Address: " + streetAddress + ", " + buildingNumber + ", " + city + ", " + stateAbbr + ", " + country + ", " + zipCodeByState;
        setProperties("Address:", fullAddress);

        assertTrue(nextBtn.isDisplayed() && nextBtn.isEnabled(),"NEXT BUTTON IS NOT ENABLED");
        nextBtn.click();
    }

    private void fillOutPatientPhoneNumber() {
        String cellPhone = faker.phoneNumber().cellPhone().replace(".","");
        phoneNumberInput.sendKeys(cellPhone);

        setProperties("Phone Number:", "Phone Number: " + cellPhone);

        assertTrue(nextBtn.isDisplayed() && nextBtn.isEnabled(),"NEXT BUTTON IS NOT ENABLED");
        nextBtn.click();
    }

    private void fillOutRelatives(){
        Select select = new Select(relationshipTypeSelect);
        select.selectByIndex(generateRandomNumber(1,9));
        String firstSelectedRelType = select.getFirstSelectedOption().getText();

        String relFirstName = faker.name().firstName();
        relationshipPersonName.sendKeys(relFirstName);

        String relFullInfo = "Relatives: " + relFirstName + " - " + firstSelectedRelType;
        setProperties("Relatives:", relFullInfo);

        assertTrue(nextBtn.isDisplayed() && nextBtn.isEnabled(),"NEXT BUTTON IS NOT ENABLED");
        nextBtn.click();

        for (int i = 0; i < confirmInfoNames.size(); i++) {
            String name = confirmInfoNames.get(i).getText().trim();
            String expectedName = getProperties(name);
            String actualName = confirmInfoValues.get(i).getText();
            assertEquals(actualName,expectedName,"NAMES DO NOT MATCH");
        }
    }

    private void clickOnSubmitButton(){
        assertTrue(submitBtn.isEnabled() && cancelSubmissionBtn.isEnabled(),"SUBMIT OR CANCEL BUTTON IS NOT ENABLED");
        submitBtn.click();
    }
}
