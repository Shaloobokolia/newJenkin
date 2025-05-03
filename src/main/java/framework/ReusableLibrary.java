package framework;

import com.aventstack.extentreports.ExtentReports;
import org.openqa.selenium.WebDriver;

public class ReusableLibrary {

    //ThreadLocal ensures that each and every thread will have a seperate TestUtil Object
    public static ThreadLocal<TestUtil> testUtilThread = new ThreadLocal<TestUtil>();

    protected TestUtil testUtil=testUtilThread.get();
    protected WebDriver driver=testUtil.getDriver();
    protected ExcelUtils excelUtils=testUtil.getExcelUtils();
    protected SeleniumUtils seleniumUtils=testUtil.getSeleniumUtils();
    protected JSFunctions jsFunctions=testUtil.getJsFunctions();
    protected PropertiesUtil propertiesUtil=testUtil.getPropertiesUtil();
    protected ElementUtils elementUtils=testUtil.getElementUtils();
    protected Reports reports=testUtil.getReports();
    protected ExtentReports extentReports=testUtil.getExtentReports();


}
