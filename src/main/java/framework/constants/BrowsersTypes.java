package framework.constants;

public enum BrowsersTypes {

    //public static final BrowserTypes CHROME=new BrowserTypes("Chrome");
    CHROME("chrome"),
    FIREFOX("firefox"),
    EDGE("edge"),
    SAFARI("safari"),
    OPERA("opera");

    private String browser;

    BrowsersTypes(String browser)
    {
        this.browser=browser;
    }

    public String getBrowser()
    {
        return browser;
    }
}
