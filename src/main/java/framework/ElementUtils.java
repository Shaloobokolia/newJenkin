package framework;

import lombok.AllArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@AllArgsConstructor
public class ElementUtils {

    WebDriver driver;

    public WebElement findElement(By by)
    {
        try {
            return driver.findElement(by);
        }

        catch (NoSuchElementException e2)
        {
            throw new GenericExceptions("Element is not found for the given locator: " + by);
        }

        catch (StaleElementReferenceException e1)
        {
            throw new GenericExceptions("Element is stale for the  please check it");
        }
    }

    public WebElement findElement(By by, String labelName)
    {
        try {
            return driver.findElement(by);
        }

        catch (NoSuchElementException e4)
        {
            throw new GenericExceptions("Element is not found for the given locator: " + labelName);
        }

        catch (StaleElementReferenceException e5)
        {
            throw new GenericExceptions("Element is stale for the  " + labelName+" please check it");
        }
    }

    public WebElement findElement(By by, int time,String labelName)
    {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));

            //Return the webelement if it is found under the given time
            return wait.until(ExpectedConditions.presenceOfElementLocated(by));
        }

        catch (NoSuchElementException e4)
        {
            throw new GenericExceptions("Element is not found for the given locator: " + labelName);
        }

        catch (TimeoutException e2)
        {
            throw new GenericExceptions("Element is not found for the given locator: " + labelName+" after waiting for: "+time+" seconds");
        }
    }

    public List<WebElement> findElements(By by)
    {
        return driver.findElements(by);
    }

    public List<WebElement> findElements(By by, int time)
    {
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(time));

        //Return the list of webelements if it is found under the given time
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }
}
