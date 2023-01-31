package com.acedmy.techcenture.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.List;

import static com.acedmy.techcenture.config.ConfigReader.getProperties;
import static com.acedmy.techcenture.utilities.Utils.generateRandomNumber;

public class LoginPage {

    private final WebDriver driver;
    private SoftAssert softAssert;

   public LoginPage(WebDriver driver, SoftAssert softAssert){
       this.driver = driver;
       this.softAssert = softAssert;
       PageFactory.initElements(driver, this);
   }

   @FindBy(xpath = "//label[@for='username']")
   private WebElement usernameLabel;

   @FindBy(xpath = "//label[@for='password']")
   private WebElement passwordLabel;

   @FindBy(id = "username")
   private WebElement usernameInput;

   @FindBy(id = "password")
   private WebElement passwordInput;

   @FindBy(xpath = "//ul[@id='sessionLocation']/li")
   private List<WebElement> locations;

   @FindBy(id = "loginButton")
   private WebElement loginBtn;

   @FindBy(id = "cantLogin")
   private WebElement cantLoginLink;





    public void verifyAllLoginElements(HashMap<String,String> data){
    //    verify inputs
        verifyLoginInputs();
    //    verify labels
        verifyLoginLabels();
    //    verify login button and can't login link
        verifyLoginButton();
    //    verify locations
        verifyLocations(data);
    }



   public void navigateToLoginPage(){
       driver.get(getProperties("url"));
       softAssert.assertEquals(driver.getTitle().trim(),"Login","LOGIN TITLES DO NOT MATCH");
   }
   


   public void login(){
        enterUsername(getProperties("username"));
        enterPassword(getProperties("password"));
        selectRandomLocation();
        clickOnLoginButton();
   }

    private void verifyLoginInputs(){
       softAssert.assertTrue(usernameInput.isEnabled() && passwordInput.isEnabled(),"INPUT IS NOT ENABLED");
    }

    private void verifyLoginLabels(){
        softAssert.assertTrue(usernameLabel.isDisplayed() && passwordLabel.isDisplayed(), "LABELS ARE NOT DISPLAYED");
    }

    private void verifyLoginButton(){
        softAssert.assertTrue(loginBtn.isDisplayed() && loginBtn.isEnabled(), "LOGIN BUTTON IS NOT ENABLED");
        softAssert.assertTrue(cantLoginLink.isDisplayed() && cantLoginLink.isEnabled(),"CAN NOT LOGIN LINK IS NOT ENABLED");
    }

    private void verifyLocations(HashMap<String,String> data){
        String[] listOfLocations = data.get("Locations").split(",");

        for (int i = 0; i < locations.size(); i++) {
            String expectedListOfLocation = listOfLocations[i];
            String actualListOfLocation = locations.get(i).getText().toLowerCase().trim();
            softAssert.assertEquals(actualListOfLocation, expectedListOfLocation,"LOCATIONS DO NOT MATCH");
        }
    }


   private void enterUsername(String username){
       usernameInput.clear();
       usernameInput.sendKeys(username);
   }

   private void enterPassword(String password){
       passwordInput.clear();
       passwordInput.sendKeys(password);
   }

    private void selectRandomLocation(){
        String locationXpath = "//ul[@id='sessionLocation']/li["+ generateRandomNumber(1,6) +"]";
        WebElement randomLocation = driver.findElement(By.xpath(locationXpath));
        randomLocation.click();
    }

   private void clickOnLoginButton(){
       softAssert.assertTrue(loginBtn.isDisplayed() && loginBtn.isEnabled(), "LOGIN BUTTON IS NOT ENABLED");
       loginBtn.click();
   }

}
