package pages.bankManagerLogin;

import framework.ReusableLibrary;
import org.openqa.selenium.By;

public class AddCustomersPage extends ReusableLibrary {

    private By txt_FirstName= By.xpath("//input[@placeholder='First Name']");
    private By txt_LastName= By.xpath("//input[@placeholder='Last Name']");
    private By txt_PostCode= By.xpath("//input[@placeholder='Post Code']");
    private By btn_AddCustomer=By.xpath("//button[normalize-space(text())='Add Customer' and @type='submit']");

    /************************************************************************************************************/

    public AddCustomersPage enterFirstName(String firstName) {
        seleniumUtils.enterData(txt_FirstName,firstName,10,"First Name");
        return this;
    }

    public boolean checkIfFirstNameIsPresent() {
        return elementUtils.findElement(txt_FirstName,10,"First Name").isDisplayed();
    }

    public AddCustomersPage enterLastName(String lastName) {
        seleniumUtils.enterData(txt_LastName,lastName,"Last Name");
        return this;
    }

    public AddCustomersPage enterPostCode(String postCode) {
        seleniumUtils.enterData(txt_PostCode,postCode,"Post Code");
        return this;
    }

    public AddCustomersPage clickAddCustomer() {
        seleniumUtils.clickOnElement(btn_AddCustomer,"Add Customer");
        return this;
    }
}
