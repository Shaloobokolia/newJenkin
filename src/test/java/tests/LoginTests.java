package tests;

import dataProviders.Login_DP;
import org.testng.annotations.Test;
import pages.LoginPages;

import java.util.Map;

public class LoginTests extends TESTNGBase {

    @Test(description = "Performing Login Scenarios",dataProvider = "testDataExecutions",dataProviderClass = Login_DP.class)
    public void performLogin(Map<String,String> loginData)
    {
        LoginPages loginPages=new LoginPages();

        loginPages.enterUserName(loginData.get("UserName"));
        loginPages.enterPassword(loginData.get("Password"));
        loginPages.clickLogin();
    }
}
