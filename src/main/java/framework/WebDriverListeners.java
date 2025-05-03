package framework;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

import java.io.File;

//Purpose of this class is to implement the WebDriverListener interface
//We will listen to all the activities done by webdriver
public class WebDriverListeners implements WebDriverListener
{
    public void beforeClick(WebElement element) {
        FileUtils.writeDataToTheFile(PathUtils.getResultsPath()+"//"+"SeleniumActions.txt", "Clicking on the element: "+element.toString());
    }

    public void afterClick(WebElement element) {
        FileUtils.writeDataToTheFile(PathUtils.getResultsPath()+"//"+"SeleniumActions.txt", "Clicked on the element: "+element.toString());
    }


    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
        FileUtils.writeDataToTheFile(PathUtils.getResultsPath()+"//"+"SeleniumActions.txt", "Entering the data to the element: "+element.toString());
    }

    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        FileUtils.writeDataToTheFile(PathUtils.getResultsPath()+"//"+"SeleniumActions.txt", "Entered the data to the element: "+element.toString());
    }

}
