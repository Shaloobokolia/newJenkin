package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import framework.ReusableLibrary;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestExecutionListeners implements ITestListener {

    ExtentTest extentTest;

    public void onTestStart(ITestResult result)
    {
        ExtentReports reports=ReusableLibrary.testUtilThread.get().getExtentReports();

        extentTest=reports.createTest(result.getMethod().getMethodName(),result.getMethod().getDescription());
        extentTest.assignAuthor(System.getProperty("user.name"));
        extentTest.assignDevice(System.getProperty("os.name"));


        ReusableLibrary.testUtilThread.get().setExtentTest(extentTest);
    }

    public void onTestSuccess(ITestResult result) {
        extentTest.pass(MarkupHelper.createLabel("Test Case Has Ran Successfully", ExtentColor.GREEN));
    }

    public void onTestFailure(ITestResult result) {
    }

    public void onTestSkipped(ITestResult result) {
    }



}
