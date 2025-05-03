package pages.customerLogin;

import framework.ReusableLibrary;
import org.openqa.selenium.By;

public class DepositPage extends ReusableLibrary {

    private By txt_Amount= By.xpath("//input[@placeholder='amount']");
    private By ddl_SelectAccount=By.cssSelector("#accountSelect");

    private By btn_Deposit=By.xpath("//button[normalize-space()='Deposit' and @type='submit']");
    private By fld_DepositSuccess=By.xpath("//span[text()='Deposit Successful']");

    private By fld_Balance=By.xpath("//div[contains(.,'Balance') and @ng-hide='noAccount']");
    /************************************************************************************************************/

    public void enterAmount(String amount) {
        seleniumUtils.enterData(txt_Amount,amount,5,"Deposit Amount");
    }

    public void selectAccount(String account) {
        seleniumUtils.selectValueFromDropDown(ddl_SelectAccount,account,"Select Account");
    }

    public void clickDeposit() {
        seleniumUtils.clickOnElement(btn_Deposit,"Deposit");
    }

    public boolean isDepositSuccess() {
        return elementUtils.findElement(fld_DepositSuccess,5,"Deposit Success").isDisplayed();
    }

    public String getBalanceAmount()
    {
        return elementUtils.findElement(fld_Balance).getText().split("Balance")[1].split(",")[0].replaceAll("[^0-9]","");
    }

}
