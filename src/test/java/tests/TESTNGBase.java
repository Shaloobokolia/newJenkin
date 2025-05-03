package tests;

import framework.*;
import framework.constants.AppConstants;
import org.apache.poi.ss.formula.functions.T;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.HomePage;
import pages.bankManagerLogin.BankManagerPage;

public class TESTNGBase {

    //Framework related objects
    protected TestUtil testUtil=new TestUtil();
    protected SeleniumUtils seleniumUtils;
    protected Reports reports;
    private PropertiesUtil propertiesUtil;
    protected ExcelUtils excelUtils;

    //Page related objects
    protected HomePage homePage;

    @BeforeSuite
    public void killExistingBrowsers()
    {
        BrowserUtils.killExistingBrowsers();
        PathUtils.generateResultsFolder(); //Generate a seperate results folder for each and every execution
    }

    @BeforeClass
    public void setUpFrameworkObjects()
    {
        //For Every browser that we launch we need to have a seperate framework object
        if(ReusableLibrary.testUtilThread.get()==null)
        {
            testUtil.setPropertiesUtil(new PropertiesUtil());
            testUtil.setDriver(BrowserUtils.getDriver(testUtil.getPropertiesUtil().getBrowser()));
            testUtil.setExcelUtils(new ExcelUtils());
            testUtil.setElementUtils(new ElementUtils(testUtil.getDriver()));
            testUtil.setReports(new Reports(testUtil.getDriver(),testUtil));
            testUtil.setJsFunctions(new JSFunctions(testUtil.getDriver()));
            testUtil.setSeleniumUtils(new SeleniumUtils(testUtil.getDriver(), testUtil.getElementUtils(),testUtil.getReports(),testUtil.getJsFunctions()));
            testUtil.setExtentReports(new ExtentReportUtil().getExtentReports());

            ReusableLibrary.testUtilThread.set(testUtil);
        }

        else
        {
            testUtil=ReusableLibrary.testUtilThread.get();
        }

        seleniumUtils=testUtil.getSeleniumUtils();
        propertiesUtil=testUtil.getPropertiesUtil();
        excelUtils=testUtil.getExcelUtils();
        reports=testUtil.getReports();

        //Creating the page object
        homePage=new HomePage();
    }

    @BeforeMethod
    public void launchApplication()
    {
        if(seleniumUtils.getCurrentURL().contains("data")) //If the application is not launched, then only launch the application freshly
        seleniumUtils.launchApplication(propertiesUtil.getURL());
    }

    @AfterMethod
    public void afterExecution(ITestResult testResult)
    {
//        System.out.println(testResult.getStatus()); //This will let us know whether the test case is passed/failed/skipped
//        System.out.println(testResult.getMethod().getMethodName()); //This will let us know what is the current method that we are executing

        if(testResult.getStatus() == ITestResult.SUCCESS)
        {
            excelUtils.writeDataToTheExcelFile(PathUtils.getTestDataFolder()+"Sample_Executions.xlsx", AppConstants.INDEX_SHEET,"Execution Status","PASSED","Test Case Name-"+testResult.getMethod().getMethodName());
        }

        else if(testResult.getStatus() == ITestResult.FAILURE)
        {
            excelUtils.writeDataToTheExcelFile(PathUtils.getTestDataFolder()+"Sample_Executions.xlsx", AppConstants.INDEX_SHEET,"Execution Status","FAILED","Test Case Name-"+testResult.getMethod().getMethodName());
        }
    }

    @AfterSuite
    public void performActivitiesAfterCompletionOfSuite()
    {
        //Whatever data that is being performed on the extent reports object should be transferred to the HTML File
        testUtil.getExtentReports().flush();
    }
}
