package pages.customerLogin;

import framework.ReusableLibrary;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerLogin extends ReusableLibrary {

    private By ddl_CustomerName= By.cssSelector("#userSelect");

    private By btn_Login=By.xpath("//button[text()='Login']");

    private By btn_Deposit=By.xpath("//button[normalize-space(text())='Deposit']");
    private By btn_Withdrawl=By.xpath("//button[normalize-space(text())='Withdrawl']");
    private By btn_Transactions=By.xpath("//button[normalize-space(text())='Transactions']");

    private By btn_LogOut=By.xpath("//button[normalize-space(text())='Log Out']");
    private By btn_CustomerLoginMenu=By.xpath("//div[@ng-hide='noAccount']/descendant::button");
    /************************************************************************************************************/

    public boolean checkIfCustomerNameIsDisplayed() {
        return elementUtils.findElement(ddl_CustomerName,5,"Customer Name").isDisplayed();
    }

    public void selectCustomerName(String customerName) {
        seleniumUtils.selectValueFromDropDown(ddl_CustomerName,customerName,"Customer Name");
    }

    public void clickLogin() {
        seleniumUtils.clickOnElement(btn_Login,"Login");
    }

    public List<String> checkIfMenuOptionsArePresent()
    {
        return elementUtils.findElements(btn_CustomerLoginMenu,5)
                .stream().map(s->s.getText()).collect(Collectors.toList());
    }

    public DepositPage clickDeposit() {
        seleniumUtils.clickOnElement(btn_Deposit,"Deposit");
        return new DepositPage();
    }

    public WithdrawlPage clickWithdrawl() {
        seleniumUtils.clickOnElement(btn_Withdrawl,"Withdrawl");
        return new WithdrawlPage();
    }

    public void clickTransactions() {
        seleniumUtils.clickOnElement(btn_Transactions,"Transactions");
    }

    public void clickLogOut() {
        seleniumUtils.clickOnElement(btn_LogOut,"Log Out");
    }
}
