package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyser implements IRetryAnalyzer {

    int retryCount = 0;

    @Override
    public boolean retry(ITestResult iTestResult) {

        boolean retry = false;
        if(retryCount<3)
        {
            if(iTestResult.getStatus() == ITestResult.FAILURE)
            {
                retryCount++;
            }

            else if(iTestResult.getStatus() == ITestResult.SUCCESS)
            {
                retry=true;
            }
        }

        return retry;
    }
}
