package framework;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import lombok.Data;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

//Main agenda of this class is to ensure that the framework objects need not be created again and again
//Only one copy/instance of object is maintained

//Purpose of this class is to maintain all the framework related objects
@Data //Is a combination of Getter and setter
public class TestUtil {

    private WebDriver driver;
    private ExcelUtils excelUtils;
    private SeleniumUtils seleniumUtils;
    private JSFunctions jsFunctions;
    private PropertiesUtil propertiesUtil;
    private ElementUtils elementUtils;
    private Reports reports;
    private ExtentReports extentReports;
    private ExtentTest extentTest;

    private Map<String,String> testData=new HashMap<>();

    public void setData(String key,String value)
    {
        testData.put(key,value);
    }

    public String getData(String key)
    {
        return testData.getOrDefault(key,"");
    }
}
