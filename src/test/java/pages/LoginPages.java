package pages;

import framework.ReusableLibrary;
import org.openqa.selenium.By;

//Page Object Model is the design pattern, where we maintain all the locators with respect to that page
//Then we write the functions/actions that we need to perform on that particular web element
public class LoginPages extends ReusableLibrary {

    private By txt_UserName= By.id("username");
    private By txt_Password= By.id("password");
    private By btn_Login= By.xpath("//button[text()='Submit']");

    /************************************************************************************************************/

    public void enterUserName(String userName) {
        seleniumUtils.enterData(txt_UserName,userName,"UserName");
    }

    public void enterPassword(String password) {
        seleniumUtils.enterData(txt_Password,password,"Password");
    }

    public void clickLogin() {
        seleniumUtils.clickOnElement(btn_Login,"Login");
    }
}
