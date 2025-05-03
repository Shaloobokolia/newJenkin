package pages.bankManagerLogin;

import framework.ReusableLibrary;
import org.openqa.selenium.By;

public class BankManagerPage extends ReusableLibrary {

    private By btn_AddCustomer= By.xpath("//button[normalize-space(text())='Add Customer']");
    private By btn_OpenAccount=By.xpath("//button[normalize-space(text())='Open Account']");
    private By btn_Customers=By.xpath("//button[normalize-space(text())='Customers']");

    /************************************************************************************************************/

    public boolean checkIfAddCustomerButtonIsDisplayed() {
        //.isDisplayed() is used to check if the element is displayed/visible on the page
        return elementUtils.findElement(btn_AddCustomer,10,"Add Customer").isDisplayed();
    }

    public boolean checkIfOpenAccountButtonIsDisplayed() {
        //.isDisplayed() is used to check if the element is displayed/visible on the page
        return elementUtils.findElement(btn_OpenAccount,10,"Open Account").isDisplayed();
    }

    public boolean checkIfCustomersButtonIsDisplayed() {
        //.isDisplayed() is used to check if the element is displayed/visible on the page
        return elementUtils.findElement(btn_Customers,10,"Customers").isDisplayed();
    }

    public AddCustomersPage clickOnAddCustomer() {
        seleniumUtils.clickOnElement(btn_AddCustomer,10,"Add Customer");
        return new AddCustomersPage();
    }

    public OpenAccountPage clickOnOpenAccount() {
        seleniumUtils.clickOnElement(btn_OpenAccount,10,"Open Account");
        return new OpenAccountPage();
    }

    public CustomersPage clickOnCustomers() {
        seleniumUtils.clickOnElement(btn_Customers,10,"Customers");
        return new CustomersPage();
    }
}
