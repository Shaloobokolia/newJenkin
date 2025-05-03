package pages.bankManagerLogin;

import framework.ReusableLibrary;
import org.openqa.selenium.By;

public class OpenAccountPage extends ReusableLibrary {

    private By ddl_CustomerName= By.cssSelector("#userSelect");
    private By ddl_Currency=By.cssSelector("#currency");
    private By btn_Process=By.xpath("//button[normalize-space(text())='Process']");

    /************************************************************************************************************/

    public OpenAccountPage selectCustomerName(String customerName) {
        seleniumUtils.selectValueFromDropDown(ddl_CustomerName,customerName,10,"Customer Name");
        return this;
    }

    public OpenAccountPage selectCurrency(String currency) {
        seleniumUtils.selectValueFromDropDown(ddl_Currency,currency,"Currency");

        //If the drop down is selecting a default value instead of other options, then select the currency again
        if(seleniumUtils.getSelectedValueFromDropDown(ddl_Currency).contains("Currency"))
            selectCurrency(currency);

        return this;
    }

    public OpenAccountPage clickProcess() {
        seleniumUtils.clickOnElement(btn_Process,"Process");
        return this;
    }

}
