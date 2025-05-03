package framework;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

//This class is specifically used for storing all the paths related to the project
@UtilityClass
public class PathUtils {

    public String resultsPath;

    @SneakyThrows //Alternative to using throws <exception> at a method level and prevents exception chaining
    public void applySleep(long time)
    {
        Thread.sleep(time);
    }

    public String generateResultsFolder()
    {
        File f1=new File(System.getProperty("user.dir")+"//Results//"+getCurrentDateTime("dd-MM-yyyy hh-mm-ss"));

        f1.mkdirs();

        resultsPath=f1.getPath();

        return resultsPath;
    }

    public String getResultsPath()
    {
        return resultsPath;
    }

    public String getCurrentDateTime(String format)
    {
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        Date d1=new Date();
        return sdf.format(d1);
    }

    public String getScreenshotPath(String... imgName)
    {
        File f1=new File(System.getProperty("user.dir")+"//Screenshots//"+getCurrentDateTime("dd-MM-yyyy"));

        f1.mkdirs(); //Creates the missing directories
        if(imgName.length==0)
        {
            return f1.getPath()+"//"+getCurrentDateTime("hh-mm-ss-SSS")+".png";
        }

        else
        {
            return f1.getPath()+"//"+imgName[0]+"_"+getCurrentDateTime("hh-mm-ss-SSS")+".png";
        }
    }

    public String getScreenshotPath_Results(String... imgName)
    {
        File f1=new File(resultsPath+"//Screenshots//");

        f1.mkdirs(); //Creates the missing directories
        if(imgName.length==0)
        {
            return f1.getPath()+"//"+getCurrentDateTime("hh-mm-ss-SSS")+".png";
        }

        else
        {
            return f1.getPath()+"//"+imgName[0]+"_"+getCurrentDateTime("hh-mm-ss-SSS")+".png";
        }
    }

    public String getDownloadsFolderPath()
    {
        return System.getProperty("user.dir")+"//Downloads";
    }

    public String getTestDataFolder()
    {
        return System.getProperty("user.dir")+"//src//test//resources//Test_Data//";
    }

    public String generateRandomNumber(int length)
    {
        String data=RandomStringUtils.randomNumeric(length); //This is used to generate a random number of the specified length

        while(data.startsWith("0"))
        {
            data=RandomStringUtils.randomNumeric(length); //This is used to generate a random number of the specified length
        }

        return data;
    }

    public String getExtentReportsPath()
    {
        return resultsPath+"/ExtentReports.html";
    }

}
