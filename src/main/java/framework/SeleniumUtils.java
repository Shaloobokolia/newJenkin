package framework;

import framework.constants.LogStatus;
import lombok.AllArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor //Based on the variables declared, it will create a parameterized constructor during the execution time
public class SeleniumUtils {

    WebDriver driver;
    ElementUtils elementUtils;
    Reports reports;
    JSFunctions jsFunctions;

//    public SeleniumUtils(WebDriver driver)
//    {
//        this.driver=driver;
//    }

    public void clickOnElement(WebElement element, String labelName)
    {
        try {
            if (element == null)
                throw new GenericExceptions("Unable to find the element for " + labelName);

            jsFunctions.higlightElement(element);
            PathUtils.applySleep(500);
            jsFunctions.disableHighlight(element);

            reports.captureScreenshots();
            element.click();

            reports.logReportsToTheFile(LogStatus.INFO_SCREENSHOT,"Clicked on: <b>"+labelName+"</b>");
        }

        catch (ElementNotInteractableException e2)
        {
            throw new GenericExceptions("Element is not interactable for " + labelName+" please check it");
        }


        catch (StaleElementReferenceException e1)
        {
            throw new GenericExceptions("Element is stale for the  " + labelName+" please check it");
        }
    }

    public void clickOnElement(By by, String labelName)
    {
        try {
            WebElement element = elementUtils.findElement(by,labelName);

            Optional.ofNullable(element).orElseThrow(() -> new GenericExceptions("Unable to find the element for " + labelName));

            jsFunctions.higlightElement(element);
            PathUtils.applySleep(500);
            reports.captureScreenshots();

            reports.logReportsToTheFile(LogStatus.INFO_SCREENSHOT,"Clicked on: <b>"+labelName+"</b>");
            jsFunctions.disableHighlight(element);
            element.click();

        }

        catch (ElementNotInteractableException e2)
        {
            throw new GenericExceptions("Element is not interactable for " + labelName+" please check it");
        }

        catch (StaleElementReferenceException e1)
        {
            throw new GenericExceptions("Element is stale for the  " + labelName+" please check it");
        }

    }


    public void clickOnElement(By by, int time, String labelName)
    {
        try {
            WebElement element = elementUtils.findElement(by, time,labelName);

            Optional.ofNullable(element).orElseThrow(() -> new GenericExceptions("Unable to find the element for " + labelName));

            jsFunctions.higlightElement(element);
            reports.captureScreenshots();
            jsFunctions.disableHighlight(element);

            element.click();
            reports.logReportsToTheFile(LogStatus.INFO_SCREENSHOT,"Clicked on: <b>"+labelName+"</b>");
        }

        catch (ElementNotInteractableException e2)
        {
            throw new GenericExceptions("Element is not interactable for " + labelName+" please check it");
        }

        catch (StaleElementReferenceException e1)
        {
            throw new GenericExceptions("Element is stale for the  " + labelName+" please check it");
        }
    }

    public void enterData(WebElement element,String data,String labelName)
    {
        Optional.ofNullable(element).orElseThrow(() -> new GenericExceptions("Unable to find the element for " + labelName));

        //If the data is already present in the text box, erase the existing data
        String existingData=getTextBoxAttribute(element,labelName);
        if(!(existingData.isEmpty() || existingData.isBlank()))
        {
            for(int i=0;i<existingData.length();i++)
            {
                PathUtils.applySleep(100);
                element.sendKeys(Keys.BACK_SPACE);
            }
        }

        jsFunctions.higlightElement(element);
        reports.captureScreenshots();


        element.sendKeys(data);
        reports.logReportsToTheFile(LogStatus.INFO_SCREENSHOT,"Entered Data for: "+labelName+" is: "+data);
        jsFunctions.disableHighlight(element);
    }

    public String getTextBoxAttribute(By by, String labelName)
    {
        WebElement element = elementUtils.findElement(by,labelName);
        Optional.ofNullable(element).orElseThrow(() -> new GenericExceptions("Unable to find the element for " + labelName));

        return Optional.ofNullable(element.getDomAttribute("value")).orElseGet(()->element.getDomProperty("value"));
    }

    public String getTextBoxAttribute(WebElement element, String labelName)
    {
        Optional.ofNullable(element).orElseThrow(() -> new GenericExceptions("Unable to find the element for " + labelName));

        return Optional.ofNullable(element.getDomAttribute("value")).orElseGet(()->element.getDomProperty("value"));
    }


    public void enterData(By by,String data,int time,String labelName)
    {
        WebElement element=elementUtils.findElement(by,time,labelName);

        Optional.ofNullable(element).orElseThrow(() -> new GenericExceptions("Unable to find the element for " + labelName));

        String existingData=getTextBoxAttribute(element,labelName);
        if(!(existingData.isEmpty() || existingData.isBlank()))
        {
            for(int i=0;i<existingData.length();i++)
            {
                element.sendKeys(Keys.BACK_SPACE);
            }
        }

        jsFunctions.higlightElement(element);
        reports.captureScreenshots();
        element.sendKeys(data);

        if(element.getDomProperty("value").equalsIgnoreCase("") || element.getDomProperty("value").equalsIgnoreCase(""))
        {
            enterData(by,data,time,labelName);
        }

        reports.logReportsToTheFile(LogStatus.INFO_SCREENSHOT,"Entered Data for: <b>"+labelName+"</b> is: <b>"+data+"</b>");
        jsFunctions.disableHighlight(element);
    }


    public void enterData(By by,String data,String labelName)
    {
        WebElement element=elementUtils.findElement(by);

        Optional.ofNullable(element).orElseThrow(() -> new GenericExceptions("Unable to find the element for " + labelName));

        String existingData=getTextBoxAttribute(element,labelName);
        if(!(existingData.isEmpty() || existingData.isBlank()))
        {
            for(int i=0;i<existingData.length();i++)
            {
                element.sendKeys(Keys.BACK_SPACE);
            }
        }

        jsFunctions.higlightElement(element);
        reports.captureScreenshots();

        element.sendKeys(data);
        reports.logReportsToTheFile(LogStatus.INFO_SCREENSHOT,"Entered Data for: <b>"+labelName+"</b> is: <b>"+data+"</b>");
        jsFunctions.disableHighlight(element);

    }

    public void closeRespectiveTabOrWindow(String urlOrTitle) throws InterruptedException {
        Set<String> handles = driver.getWindowHandles();

        for (String handle : handles) {
            driver.switchTo().window(handle);

            if (driver.getCurrentUrl().contains(urlOrTitle) || driver.getTitle().contains(urlOrTitle)) {
                driver.close();
            }

            Thread.sleep(1000);
        }
    }

    public void switchToAllTheTabsAndWindows() throws InterruptedException {
        Set<String> handles = driver.getWindowHandles();

        for (String handle : handles) {
            driver.switchTo().window(handle);
            Thread.sleep(1000);
        }
    }

    public String getCurrentURL()
    {
        return driver.getCurrentUrl();
    }

    public String launchApplication(String url)
    {
        if(url.isBlank() || url.isEmpty())
            throw new GenericExceptions("Given URL is empty or blank");

        else if(!url.contains("https"))
            throw new GenericExceptions("Given URL does not contain https:");

        else if(!url.startsWith("https"))
            throw new GenericExceptions("Given URL does not start with https:");

        driver.get(url);
        driver.manage().window().maximize();

//        reports.logReportsToTheFile(LogStatus.INFO_SCREENSHOT,"Launched the application successfully for: "+url);
        return driver.getWindowHandle();
    }

    public String createNewTabAndLaunchApplication(String url) {
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(url);

        return driver.getWindowHandle();
    }


    public String createNewWindowAndLaunchApplication(String url) {
        driver.switchTo().newWindow(WindowType.WINDOW);
        driver.get(url);

        return driver.getWindowHandle();
    }

    public Optional<Alert> checkIfAlertIsPresent(int sec)
    {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(sec));

            //Optional.ofNullable --> Check if the given object is returning null or not
            //The whole agenda of optionals is to avoid null pointer exceptions
            return Optional.ofNullable(wait.until(ExpectedConditions.alertIsPresent()));
        }

        catch (TimeoutException e5)
        {
            throw new GenericExceptions("Unable to find the browser alert after waiting for: "+sec+" seconds");
        }
    }

    public void acceptAlert()
    {
        checkIfAlertIsPresent(3).ifPresentOrElse(alert -> alert.accept(),()->{
            throw new GenericExceptions("Alert is not present");
        });
    }

    public void dismissAlert()
    {
        checkIfAlertIsPresent(3).ifPresentOrElse(alert -> alert.dismiss(),()->{
            throw new GenericExceptions("Alert is not present");
        });
    }

    public void enterDataInAlert(String data)
    {
        checkIfAlertIsPresent(3).ifPresentOrElse(alert -> {
            alert.sendKeys(data);
            alert.accept();
        },()->{
            throw new GenericExceptions("Alert is not present");
        });
    }

    public String getAlertText()
    {
        //If the alert is present, then generate the text of the alert using .map() method
        return checkIfAlertIsPresent(3).map(Alert::getText)
                .orElseThrow(()->new GenericExceptions("Alert is not present"));
    }

    public void performMouseHover(WebElement element)
    {
        Actions a1=new Actions(driver);
        a1.moveToElement(element).build().perform();
    }


    public void performDragAndDrop(WebElement source, WebElement destination)
    {
        Actions a1=new Actions(driver);
        a1.dragAndDrop(source,destination).build().perform();
    }


    public void performMouseHover(By by, int time,String labelName)
    {
        WebElement element=elementUtils.findElement(by,time,labelName);
        Actions a1=new Actions(driver);
        a1.moveToElement(element).build().perform();

        reports.logReportsToTheFile(LogStatus.INFO_SCREENSHOT,"Performed Mouse Hover for: "+labelName);
    }

    public void performDragAndDrop(By sourcePath, By destPath, int time,String labelName)
    {
        WebElement source=elementUtils.findElement(sourcePath,time,labelName);
        WebElement destination=elementUtils.findElement(destPath,time,labelName);

        Actions a1=new Actions(driver);
        a1.dragAndDrop(source,destination).build().perform();
    }

    public void performMouseHover(By by)
    {
        WebElement element=elementUtils.findElement(by);
        Actions a1=new Actions(driver);
        a1.moveToElement(element).build().perform();
    }

    public void performDragAndDrop(By sourcePath, By destPath)
    {
        WebElement source=elementUtils.findElement(sourcePath);
        WebElement destination=elementUtils.findElement(destPath);

        Actions a1=new Actions(driver);
        a1.dragAndDrop(source,destination).build().perform();
    }

    public void performRightClick(WebElement element)
    {
        Actions a1=new Actions(driver);
        a1.contextClick(element).build().perform();
    }

    public void performRightClick(By by)
    {
        WebElement element=elementUtils.findElement(by);

        Actions a1=new Actions(driver);
        a1.contextClick(element).build().perform();
    }

    public void performRightClick(By by, int time,String labelName)
    {
        WebElement element=elementUtils.findElement(by,time,labelName);
        Actions a1=new Actions(driver);
        a1.contextClick(element).build().perform();
    }

    public void performDoubleClick(WebElement element,String labelName)
    {
        Actions a1=new Actions(driver);
        a1.doubleClick(element).build().perform();
    }

    public void performDoubleClick(By by,String labelName)
    {
        WebElement element=elementUtils.findElement(by);

        Actions a1=new Actions(driver);
        a1.doubleClick(element).build().perform();
    }

    public void performDoubleClick(By by, int time,String labelName)
    {
        WebElement element=elementUtils.findElement(by,time,labelName);
        Actions a1=new Actions(driver);
        a1.doubleClick(element).build().perform();
    }

    public void selectValueFromDropDown(WebElement element, String option, String labelName)
    {
        Select s1=new Select(element);
        if(option.isBlank() || option.isEmpty())
        {
            List<WebElement> options=s1.getOptions();
            //ThreadLocalRandom.current().nextInt(0, options.size()-1) --> This is a function we select a random number between 0, options.size()-1
            s1.selectByIndex(ThreadLocalRandom.current().nextInt(0, options.size()-1));
        }

        else
        {
            try
            {
                s1.selectByVisibleText(option);
            }

            catch (NoSuchElementException r5)
            {
                try
                {
                    s1.selectByContainsVisibleText(option);
                }

                catch (NoSuchElementException r6)
                {
                    try
                    {
                        s1.selectByValue(option);
                    }

                    catch (NoSuchElementException r7)
                    {
                        try
                        {
                            s1.selectByIndex(Integer.parseInt(option));
                        }

                        catch (NoSuchElementException r8)
                        {
                            throw new GenericExceptions("Unable to select the value from the dropdown for "+labelName);
                        }

                    }
                }
            }
        }
    }

    public String getSelectedValueFromDropDown(WebElement element)
    {
        Select s1=new Select(element);
        return s1.getFirstSelectedOption().getText();
    }


    public String getSelectedValueFromDropDown(By by)
    {
        WebElement element=elementUtils.findElement(by);
        Select s1=new Select(element);
        return s1.getFirstSelectedOption().getText();
    }

    public void selectValueFromDropDown(By by, String option, String labelName)
    {
        WebElement element=elementUtils.findElement(by);
        Select s1=new Select(element);
        if(option.isBlank() || option.isEmpty())
        {
            List<WebElement> options=s1.getOptions();
            //ThreadLocalRandom.current().nextInt(0, options.size()-1) --> This is a function we select a random number between 0, options.size()-1
            s1.selectByIndex(ThreadLocalRandom.current().nextInt(0, options.size()-1));
        }

        else
        {
            try
            {
                s1.selectByVisibleText(option);
            }

            catch (NoSuchElementException r5)
            {
                try
                {
                    s1.selectByContainsVisibleText(option);
                }

                catch (NoSuchElementException r6)
                {
                    try
                    {
                        s1.selectByValue(option);
                    }

                    catch (NoSuchElementException r7)
                    {
                        try
                        {
                            s1.selectByIndex(Integer.parseInt(option));
                        }

                        catch (NoSuchElementException r8)
                        {
                            throw new GenericExceptions("Unable to select the value from the dropdown for "+labelName);
                        }

                    }
                }
            }
        }
    }

    public void selectValueFromDropDown(By by, String option,int sec, String labelName)
    {
        WebElement element=elementUtils.findElement(by,sec,labelName);
        Select s1=new Select(element);
        if(option.isBlank() || option.isEmpty())
        {
            List<WebElement> options=s1.getOptions();
            //ThreadLocalRandom.current().nextInt(0, options.size()-1) --> This is a function we select a random number between 0, options.size()-1
            s1.selectByIndex(ThreadLocalRandom.current().nextInt(0, options.size()-1));
        }

        else
        {
            try
            {
                s1.selectByVisibleText(option);
            }

            catch (NoSuchElementException r5)
            {
                try
                {
                    s1.selectByContainsVisibleText(option);
                }

                catch (NoSuchElementException r6)
                {
                    try
                    {
                        s1.selectByValue(option);
                    }

                    catch (NoSuchElementException r7)
                    {
                        try
                        {
                            s1.selectByIndex(Integer.parseInt(option));
                        }

                        catch (NoSuchElementException r8)
                        {
                            throw new GenericExceptions("Unable to select the value from the dropdown for "+labelName);
                        }

                    }
                }
            }
        }

        jsFunctions.higlightElement(element);
        reports.captureScreenshots();
        jsFunctions.disableHighlight(element);

        reports.logReportsToTheFile(LogStatus.INFO_SCREENSHOT,"Selected Value from Drop Down is: "+getSelectedValueFromDropDown(element));

    }

    public String getElementText(WebElement element,String labelName)
    {
        if(element==null)
            throw new GenericExceptions("Unable to find the element");

        performMouseHover(element);

        jsFunctions.higlightElement(element);
        reports.captureScreenshots();
        jsFunctions.disableHighlight(element);

        reports.logReportsToTheFile(LogStatus.INFO_SCREENSHOT,"Text Fetched for: "+labelName+" is: "+element.getText());
        return element.getText();
    }

    public String getElementText(By by,String labelName)
    {
        WebElement element=elementUtils.findElement(by);

        if(element==null)
            throw new GenericExceptions("Unable to find the element");

        performMouseHover(element);

        jsFunctions.higlightElement(element);
        reports.captureScreenshots();
        jsFunctions.disableHighlight(element);

        reports.logReportsToTheFile(LogStatus.INFO_SCREENSHOT,"Text Fetched for: "+labelName+" is: "+element.getText());
        return element.getText();
    }

    public String getElementText(By by, int time,String labelName)
    {
        WebElement element=elementUtils.findElement(by,time,labelName);

        if(element==null)
            throw new GenericExceptions("Unable to find the element");

        performMouseHover(element);

        jsFunctions.higlightElement(element);
        reports.captureScreenshots();
        jsFunctions.disableHighlight(element);

        reports.logReportsToTheFile(LogStatus.INFO_SCREENSHOT,"Text Fetched for: "+labelName+" is: "+element.getText());
        return element.getText();
    }

    public void switchToFrame(String nameOrID)
    {
        try {
            driver.switchTo().frame(nameOrID);
        }

        catch (NoSuchFrameException e1)
        {
            throw new GenericExceptions("Unable to switch to the frame with name or ID: "+nameOrID);
        }
    }

    public void switchToFrame(WebElement element,String labelName)
    {
        try {
            driver.switchTo().frame(element);
        }

        catch (NoSuchFrameException e1)
        {
            throw new GenericExceptions("Unable to switch to the frame with element: "+labelName);
        }
    }

    public void switchToFrame(WebElement element,String labelName,int time)
    {
        try {
            WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(time));
            Optional.ofNullable(wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element)))
                    .orElseThrow(()->new GenericExceptions("Unable to switch to the frame with element: "+labelName));
        }

        catch (NoSuchFrameException e1)
        {
            throw new GenericExceptions("Unable to switch to the frame with element: "+labelName);
        }
    }


    public void switchToFrame(By by,String labelName)
    {
        WebElement element=elementUtils.findElement(by);

        try {
            driver.switchTo().frame(element);
        }

        catch (NoSuchFrameException e1)
        {
            throw new GenericExceptions("Unable to switch to the frame with element: "+labelName);
        }
    }

    public void switchToFrame(By by,String labelName, int time)
    {
        WebElement element=elementUtils.findElement(by);

        try {
            WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(time));
            Optional.ofNullable(wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element)))
                    .orElseThrow(()->new GenericExceptions("Unable to switch to the frame with element: "+labelName));
        }

        catch (NoSuchFrameException e1)
        {
            throw new GenericExceptions("Unable to switch to the frame with element: "+labelName);
        }
    }


    public void switchToFrame(String nameOrID, int time)
    {
        try {
            WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(time));
            Optional.ofNullable(wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrID)))
                    .orElseThrow(()->new GenericExceptions("Unable to switch to the frame with name or ID: "+nameOrID));
        }

        catch (NoSuchFrameException e1)
        {
            throw new GenericExceptions("Unable to switch to the frame with name or ID: "+nameOrID);
        }
    }

    public void switchToParentFrame()
    {
        driver.switchTo().parentFrame();
    }

    public void switchOutOfAllFrames()
    {
        driver.switchTo().defaultContent();
    }

    public void switchToFrame(int index)
    {
        try {
            driver.switchTo().frame(index);
        }

        catch (NoSuchFrameException e1)
        {
            throw new GenericExceptions("Unable to switch to the frame with index: "+index);
        }
    }


    public void performRequiredKeyboardActions(By by,Keys keys)
    {
        WebElement element=elementUtils.findElement(by);

        jsFunctions.higlightElement(element);
        reports.captureScreenshots();
        jsFunctions.disableHighlight(element);

        element.sendKeys(keys);
    }

    public void performRequiredKeyboardActions(WebElement element,Keys keys)
    {
        jsFunctions.higlightElement(element);
        reports.captureScreenshots();
        jsFunctions.disableHighlight(element);

        element.sendKeys(keys);
    }

}

