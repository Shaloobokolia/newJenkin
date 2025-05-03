package pages;

import framework.ReusableLibrary;
import org.openqa.selenium.By;
import pages.bankManagerLogin.BankManagerPage;
import pages.customerLogin.CustomerLogin;

public class HomePage extends ReusableLibrary {

    private By btn_Home= By.xpath("//button[text()='Home']");
    private By btn_CustomerLogin=By.xpath("//button[text()='Customer Login']");
    private By btn_BankManagerLogin=By.xpath("//button[text()='Bank Manager Login']");

    /************************************************************************************************************/

    public void clickOnHome() {
        seleniumUtils.clickOnElement(btn_Home,"Home");
    }

    public CustomerLogin clickOnCustomerLogin() {
        seleniumUtils.clickOnElement(btn_CustomerLogin,5,"Customer Login");
        return new CustomerLogin();
    }

    public BankManagerPage clickOnBankManagerLogin() {
        seleniumUtils.clickOnElement(btn_BankManagerLogin,5,"Bank Manager Login");
        return new BankManagerPage();
    }

}
