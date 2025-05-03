package framework;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.google.common.io.Files;
import framework.constants.LogStatus;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

@AllArgsConstructor
public class Reports {

    private WebDriver driver;
    private TestUtil testUtil;

    @SneakyThrows
    public String captureScreenshots(String... imgName)
    {
        //(TakesScreenshot) --> Is the function used to capture the screenshot
        //(TakesScreenshot)driver --> Is the function used to capture the screenshot of the browser
        //((TakesScreenshot)driver).getScreenshotAs() --> Is the function used to capture the screenshot of the browser and store it either in the form of a file or byte array or base64

        //When we store the screenshot in the form of a file, it generates .png file
        //All the screenshots generated will be stored in the temp folder

        File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

//        System.out.println(src.getPath());

        File dest=new File(PathUtils.getScreenshotPath_Results(imgName));

        //Copying the file from source to destination
        Files.copy(src,dest);

        return dest.getPath();
    }

    public String captureScreenshots_Base64(String... imgName)
    {
        //Base64Image is used to convert the image into a compressed encrypted string
        //So that it is easy to attach the screenshots to any file at ease
        String base64Images=((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);

        return base64Images;
    }

    public void logReportsToTheFile(LogStatus logStatus,String message)
    {
        ExtentTest reports=testUtil.getExtentTest();
        switch (logStatus)
        {
            case PASS -> reports.log(Status.PASS,message);
            case PASS_SCREENSHOT -> reports.log(Status.PASS,message, MediaEntityBuilder.createScreenCaptureFromBase64String(captureScreenshots_Base64(message)).build());
            case FAIL -> reports.log(Status.FAIL,message);
            case FAIL_SCREENSHOT -> reports.log(Status.FAIL,message, MediaEntityBuilder.createScreenCaptureFromBase64String(captureScreenshots_Base64(message)).build());
            case WARNING -> reports.log(Status.WARNING,message);
            case WARNING_SCREENSHOT -> reports.log(Status.WARNING,message, MediaEntityBuilder.createScreenCaptureFromBase64String(captureScreenshots_Base64(message)).build());
            case INFO -> reports.log(Status.INFO,message);
            case INFO_SCREENSHOT -> reports.log(Status.INFO,message, MediaEntityBuilder.createScreenCaptureFromBase64String(captureScreenshots_Base64(message)).build());
            case SKIP -> reports.log(Status.SKIP,message);
            case SKIP_SCREENSHOT -> reports.log(Status.SKIP,message, MediaEntityBuilder.createScreenCaptureFromBase64String(captureScreenshots_Base64(message)).build());
        }
    }

}
