package framework;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;

//In this class, every method is static
//We want to ensure that no one creates an object of this class
@UtilityClass //Ensures that all the methods in this class are static and no one will be in a position to create the objects of this class
//It ensures that all the variables are static in nature
public class BrowserUtils {

    private OptionsManager options = new OptionsManager();

    //Killing all the existing browsers
    @SneakyThrows
    public void killExistingBrowsers()
    {
//        Runtime.getRuntime() --> This is similar to run command in windows, mac, etc.

        //Purpose of the below commands is to save the RAM and reduce the load on the CPU
        Runtime.getRuntime().exec("TASKKILL -f -im chromedriver.exe /T");
        Runtime.getRuntime().exec("TASKKILL -f -im geckodriver.exe /T");
        Runtime.getRuntime().exec("TASKKILL -f -im msedgedriver.exe /T");
    }

    public WebDriver getDriver(String browserName)
    {
        //Below is the code that helps us in letting the driver know that someone is willing to listen to all the activities that webdriver is doing
        EventFiringDecorator<WebDriver> decorator = new EventFiringDecorator<>(new WebDriverListeners());
        return switch (browserName.toUpperCase())
        {
            case "CHROME" -> decorator.decorate(new ChromeDriver(options.getChromeOptions()));
            case "FIREFOX" -> decorator.decorate(new FirefoxDriver(options.getFirefoxOptions()));
            case "EDGE" -> decorator.decorate(new EdgeDriver(options.getEdgeOptions()));

            default -> throw new RuntimeException("Given browser: "+browserName+" is not found");
        };
    }
}
