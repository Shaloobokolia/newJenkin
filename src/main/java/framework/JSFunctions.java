package framework;

import lombok.AllArgsConstructor;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@AllArgsConstructor
public class JSFunctions {

    WebDriver driver;

    public void scrollPage(int x, int y) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(" + x + "," + y + ")");
    }

    public void scrollPage(int pixels) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0," + pixels + ")");
    }

    public void higlightElement(WebElement element)
    {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", element);
    }

    public void disableHighlight(WebElement element)
    {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border=''", element);
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void clickOnElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void enterData(WebElement element, String data) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + data + "';", element);
    }

    public void clearData(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='';", element);
    }

    public void launchApplication(String url) {
        ((JavascriptExecutor) driver).executeScript("window.location='" + url + "'");
    }

    public void launchApplicationInANewTab(String url) {
        ((JavascriptExecutor) driver).executeScript("window.open('" + url + "','_blank');");
    }

    public void executeJSFunction(String jsFunction)
    {
        ((JavascriptExecutor) driver).executeScript(jsFunction);
    }

    public WebElement returnShadowRootElement(WebElement element)
    {
        return (WebElement) ((JavascriptExecutor) driver).executeScript("return arguments[0].shadowRoot", element);
    }

    public WebElement returnElement(String jsCode)
    {
        return (WebElement) ((JavascriptExecutor) driver).executeScript(jsCode);
    }

    public void scrollPageToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public void scrollPageToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }


}
