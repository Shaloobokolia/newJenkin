package pages.customerLogin;

import framework.ReusableLibrary;
import org.openqa.selenium.By;

public class WithdrawlPage extends ReusableLibrary {

    private By txt_Amount= By.xpath("//input[@placeholder='amount']");
    private By ddl_SelectAccount=By.cssSelector("#accountSelect");

    private By btn_Withdraw=By.xpath("//button[normalize-space()='Withdraw' and @type='submit']");
    private By fld_TransactionSuccessful=By.xpath("//span[text()='Transaction successful']");

    private By fld_Balance=By.xpath("//div[contains(.,'Balance') and @ng-hide='noAccount']");
    /************************************************************************************************************/

    public void enterAmount(String amount) {
        seleniumUtils.enterData(txt_Amount,amount,5,"Withdraw Amount");
    }

    public void selectAccount(String account) {
        seleniumUtils.selectValueFromDropDown(ddl_SelectAccount,account,"Select Account");
    }

    public void clickOnWithdraw()
    {
        seleniumUtils.clickOnElement(btn_Withdraw,5,"Withdraw");
    }

    public boolean isWithdrawSuccess() {
        return elementUtils.findElement(fld_TransactionSuccessful,5,"Transaction Successful").isDisplayed();
    }

    public String getBalanceAmount()
    {
        return elementUtils.findElement(fld_Balance).getText().split("Balance")[1].split(",")[0].replaceAll("[^0-9]","");
    }
    
}
