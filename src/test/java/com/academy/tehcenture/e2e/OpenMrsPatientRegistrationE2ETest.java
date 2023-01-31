package com.academy.tehcenture.e2e;

import com.acedmy.techcenture.pages.*;
import com.acedmy.techcenture.utilities.ExcelReader;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;

public class OpenMrsPatientRegistrationE2ETest extends BaseTest {



    @Test(dataProvider = "patientData")
    public void login(HashMap<String,String> data) {
        LoginPage loginPage = new LoginPage(driver,softAssert);
        HomePage homePage = new HomePage(driver,softAssert);
        RegisterPage registerPage = new RegisterPage(driver,softAssert);
        PatientDetailsPage detailsPage = new PatientDetailsPage(driver,softAssert);
        FindPatientRecordPage recordPage = new FindPatientRecordPage(driver, softAssert);

        loginPage.navigateToLoginPage();
        loginPage.verifyAllLoginElements(data);
        loginPage.login();

        homePage.verifyHomeElements(data);

        homePage.clickOnPatientRegisterTab();

        registerPage.fillOutPatientInfo(data);

        detailsPage.verifyPatientsElement(data);

        homePage.clickOnFindPatientRecordTab();

        recordPage.findPatientActions(data);
        homePage.clickOnLogoutBtn();
    }


    @DataProvider(name = "patientData")
    public Object[][] getPatientData(){
        ExcelReader excelReader = new ExcelReader("src/main/resources/openMRS_Data/Data.xlsx","openMRS");
        Object[][] data = excelReader.getData();
        return  data;
    }

}
