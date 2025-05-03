package pages.bankManagerLogin;

import framework.ReusableLibrary;
import org.openqa.selenium.By;

public class CustomersPage extends ReusableLibrary {

    private By txt_CustomerName= By.xpath("//input[@placeholder='Search Customer']");

    private By fld_RowsOfData=By.xpath("//table/descendant::tbody/tr");
    /************************************************************************************************************/

    public CustomersPage enterCustomerName(String customerName) {
        seleniumUtils.enterData(txt_CustomerName,customerName,10,"Customer Name");
        return this;
    }

    public boolean checkIfRowsOfDataArePresent()
    {
        return elementUtils.findElements(fld_RowsOfData).size()>0;
    }
}
