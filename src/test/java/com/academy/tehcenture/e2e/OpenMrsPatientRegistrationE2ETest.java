package com.academy.tehcenture.e2e;

import com.acedmy.techcenture.driver.Driver;
import com.acedmy.techcenture.pages.HomePage;
import com.acedmy.techcenture.pages.LoginPage;
import com.acedmy.techcenture.pages.PatientDetailsPage;
import com.acedmy.techcenture.pages.RegisterPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class OpenMrsPatientRegistrationE2ETest {

    private WebDriver driver;

    @BeforeMethod
    public void setUp(){
        driver = Driver.getDriver();
    }

    @AfterMethod
    public void tearDown() throws InterruptedException {
        Driver.quiteDriver();
    }




    @Test
    public void login() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);
        RegisterPage registerPage = new RegisterPage(driver);
        PatientDetailsPage detailsPage = new PatientDetailsPage(driver);

        loginPage.navigateToLoginPage();
        loginPage.verifyAllLoginElements();
        loginPage.login();

        homePage.verifyHomeElements();
        homePage.clickOnPatientRegisterTab();

        registerPage.fillOutPatientInfo();

        detailsPage.stickyNoteActions();
        detailsPage.verifyNames();
    }

}
