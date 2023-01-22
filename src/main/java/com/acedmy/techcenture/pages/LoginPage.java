package com.acedmy.techcenture.pages;

import com.acedmy.techcenture.utilities.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

import static com.acedmy.techcenture.config.ConfigReader.getProperties;
import static com.acedmy.techcenture.utilities.Utils.generateRandomNumber;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginPage {

    private WebDriver driver;

   public LoginPage(WebDriver driver){
       this.driver = driver;
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





    public void verifyAllLoginElements(){
    //    verify inputs
        verifyLoginInputs();
    //    verify labels
        verifyLoginLabels();
    //    verify login button and can't login link
        verifyLoginButton();
    //    verify locations
        verifyLocations();
    }



   public void navigateToLoginPage(){
       driver.get(getProperties("url"));
       assertEquals(driver.getTitle().trim(),"Login","LOGIN TITLES DO NOT MATCH");
   }
   


   public void login(){
        enterUsername(getProperties("username"));
        enterPassword(getProperties("password"));
       selectRandomLocation();
        clickOnLoginButton();
   }

    private void verifyLoginInputs(){
       assertTrue(usernameInput.isEnabled() && passwordInput.isEnabled(),"INPUT IS NOT ENABLED");
    }

    private void verifyLoginLabels(){
        assertTrue(usernameLabel.isDisplayed() && passwordLabel.isDisplayed(), "LABELS ARE NOT DISPLAYED");
    }

    private void verifyLoginButton(){
        assertTrue(loginBtn.isDisplayed() && loginBtn.isEnabled(), "LOGIN BUTTON IS NOT ENABLED");
        assertTrue(cantLoginLink.isDisplayed() && cantLoginLink.isEnabled(),"CAN NOT LOGIN LINK IS NOT ENABLED");
    }

    private void verifyLocations(){
        String[] listOfLocations = {"inpatient ward","isolation ward","laboratory","outpatient clinic","pharmacy","registration desk"};
        for (int i = 0; i < locations.size(); i++) {
            String expectedListOfLocation = listOfLocations[i];
            String actualListOfLocation = locations.get(i).getText().toLowerCase().trim();
            Assert.assertEquals(actualListOfLocation, expectedListOfLocation,"LOCATIONS DO NOT MATCH");
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
       assertTrue(loginBtn.isDisplayed() && loginBtn.isEnabled(), "LOGIN BUTTON IS NOT ENABLED");
       loginBtn.click();
   }




}
