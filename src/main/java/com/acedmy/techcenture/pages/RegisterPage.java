package com.acedmy.techcenture.pages;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.HashMap;
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


    public void fillOutPatientInfo(HashMap<String,String> data) {

        verifyTitle();

        fillOutPatientName(data);

        selectGender(data);

        fillOutPatientsBirthDate(data);

        fillOutPatientsAddress();

        fillOutPatientPhoneNumber();

        fillOutRelatives();

        clickOnSubmitButton();
    }



    private void verifyTitle(){
        assertEquals(driver.getTitle().trim(), "OpenMRS Electronic Medical Record", "TITLES DO NOT MATCH");
    }

    private void fillOutPatientName(HashMap<String,String> data) {
//        String givenName = faker.name().firstName();
//        firstNameInput.sendKeys(givenName);

        firstNameInput.sendKeys(data.get("Given"));


//        String middleName = faker.name().lastName();
//        middleNameInput.sendKeys(middleName);

        middleNameInput.sendKeys(data.get("Middle"));

//        String lastName = faker.name().lastName();
//        lastNameNameInput.sendKeys(lastName);

        lastNameNameInput.sendKeys(data.get("Family Name"));

        String fullName = "Name: " + data.get("Given") +", " + data.get("Middle") + ", " + data.get("Family Name");
        setProperties("Name:", fullName);

        assertTrue(nextBtn.isDisplayed() && nextBtn.isEnabled(),"NEXT BUTTON IS NOT ENABLED");
        nextBtn.click();
    }

    private void selectGender(HashMap<String,String> data){
        selectGender.click();
        Select select = new Select(selectGender);
        select.selectByVisibleText(data.get("Gender"));
//        String selectedGender = select.getFirstSelectedOption().getText();

        setProperties("Gender:", "Gender: " + data.get("Gender"));

        assertTrue(nextBtn.isDisplayed() && nextBtn.isEnabled(),"NEXT BUTTON IS NOT ENABLED");
        nextBtn.click();
    }

    private void fillOutPatientsBirthDate(HashMap<String ,String> data){

        int rndNumber = generateRandomNumber(1, 2);
        setProperties("rndNumber", rndNumber+"");
        System.out.println();
        if (data.get("BirthType").equalsIgnoreCase("Yes")) {
//            int birthDay = faker.date().birthday().getDate();

            data.put("Day",(int)Double.parseDouble(data.get("Day"))+"");
            birthDay.sendKeys(data.get("Day"));

            Select select = new Select(birthMonthSelect);
//            select.selectByIndex(generateRandomNumber(1, 12));
            select.selectByVisibleText(data.get("Month"));
//            String selectedMonth = select.getFirstSelectedOption().getText();

//            int birthOfYear = generateRandomNumber(1970, 2022);
            data.put("Year",(int)Double.parseDouble(data.get("Year"))+"");
            birthYear.sendKeys(data.get("Year"));

            String fullDOB = "Birthdate: " + data.get("Day") + ", " + data.get("Month") + ", " + data.get("Year");
            setProperties("Birthdate:", fullDOB);
        }else {
//                int randomEstYear = generateRandomNumber(1, 119);
            data.put("EstYears",(int)Double.parseDouble(data.get("EstYears"))+"");
            estimatedYear.sendKeys(data.get("EstYears")+"");

//                int randomEstMonth = generateRandomNumber(1,11);
            data.put("EstMonth",(int)Double.parseDouble(data.get("EstMonth"))+"");
            estimatedMoth.sendKeys(data.get("EstMonth")+"");

            String monthYear = "Birthdate: " + data.get("EstYears") + " year(s), " + data.get("EstMonth") + " month(s)";
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
