package com.academy.tehcenture.e2e;

import com.acedmy.techcenture.driver.Driver;
import com.acedmy.techcenture.pages.*;
import com.acedmy.techcenture.utilities.ExcelReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;

public class OpenMrsPatientRegistrationE2ETest {

    private WebDriver driver;

    @BeforeMethod
    public void setUp(){
        driver = Driver.getDriver();
    }

    @AfterMethod
    public void tearDown() {
        Driver.quitDriver();
    }




    @Test(dataProvider = "patientData")
    public void login(HashMap<String,String> data) {
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);
        RegisterPage registerPage = new RegisterPage(driver);
        PatientDetailsPage detailsPage = new PatientDetailsPage(driver);
        FindPatientRecordPage recordPage = new FindPatientRecordPage(driver);

        loginPage.navigateToLoginPage();
        loginPage.verifyAllLoginElements();
        loginPage.login();

        homePage.verifyHomeElements();

        homePage.clickOnPatientRegisterTab();

        registerPage.fillOutPatientInfo(data);

        detailsPage.verifyPatientsElement();

        homePage.clickOnFindPatientRecordTab();

        recordPage.findPatientActions();
        homePage.clickOnLogoutBtn();
    }


    @DataProvider(name = "patientData")
    public Object[][] getPatientData(){
        ExcelReader excelReader = new ExcelReader("src/main/resources/openMRS_Data/Data.xlsx","openMRS");
        Object[][] data = excelReader.getData();
        return  data;
    }

}
