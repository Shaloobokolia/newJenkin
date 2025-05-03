package framework;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportUtil {

    //Configuring my Extent Reports Object
    public ExtentReports getExtentReports() {

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(PathUtils.getExtentReportsPath());
        sparkReporter.config().setReportName("XYZ Banking Test Cases");
        sparkReporter.config().setDocumentTitle("Automation Test Results");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setTimelineEnabled(true);

        //Show the preview of the image when it is captured in Base64
        sparkReporter.config().thumbnailForBase64(true);

        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter); //We will mention what kind of report object we are using

        extentReports.setSystemInfo("Operating System",System.getProperty("os.name"));
        extentReports.setSystemInfo("User Name",System.getProperty("user.name"));
        extentReports.setSystemInfo("Java Version",System.getProperty("java.version"));

        return extentReports;
    }
}
