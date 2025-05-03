package tests;

import dataProviders.TestExecution_DP;
import framework.PathUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.customerLogin.CustomerLogin;
import pages.customerLogin.DepositPage;
import pages.customerLogin.WithdrawlPage;

import java.util.List;
import java.util.Map;

public class CustomerLoginTests extends TESTNGBase {

    CustomerLogin customerLogin;
    DepositPage depositPage;
    WithdrawlPage withdrawlPage;

    @Test(description = "Navigated to Customer Login Screen",priority = 1,enabled = false)
    public void navigateToCustomerLogin()
    {
        homePage.clickOnHome();

        customerLogin=homePage.clickOnCustomerLogin();

        Assert.assertTrue(customerLogin.checkIfCustomerNameIsDisplayed());
    }

    @Test(description = "Performing Customer Login",priority = 2,dataProvider = "testDataExecutions",dataProviderClass = TestExecution_DP.class)
    public void performCustomerLogin(Map<String,String> testData)
    {
        homePage.clickOnHome();

        customerLogin=homePage.clickOnCustomerLogin();

        PathUtils.applySleep(1000);

        customerLogin.selectCustomerName(testData.get("First Name"));
        customerLogin.clickLogin();

        List<String> menus=customerLogin.checkIfMenuOptionsArePresent();

        SoftAssert softAssert=new SoftAssert();

        List<String> expectedMenuOptions=List.of("Transactions","Deposit","Withdrawl");

        menus.forEach(s-> softAssert.assertTrue(expectedMenuOptions.contains(s)));

        softAssert.assertAll();
    }

    @Test(description = "Perform Deposit for the Account",priority = 3,dataProvider = "testDataExecutions",dataProviderClass = TestExecution_DP.class)
    public void performDepositOnTheAccount(Map<String,String> testData)
    {
        homePage.clickOnHome();

        customerLogin=homePage.clickOnCustomerLogin();

        PathUtils.applySleep(1000);

        customerLogin.selectCustomerName(testData.get("First Name"));
        customerLogin.clickLogin();

        depositPage=customerLogin.clickDeposit();

        String originalAmount=depositPage.getBalanceAmount();
        if (testData.get("Amount").isBlank() || testData.get("Amount").isEmpty()) {
            depositPage.enterAmount("10000");
        }

        else
        {
            depositPage.enterAmount(testData.get("Amount"));
        }

        depositPage.clickDeposit();

        String depositAmount=depositPage.getBalanceAmount();

        Double depositAmountValue=Double.parseDouble(depositAmount)-Double.parseDouble(originalAmount);

        SoftAssert softAssert=new SoftAssert();
        softAssert.assertEquals(depositAmountValue,Double.parseDouble(testData.get("Amount")),"Deposit amount is not correct");
        softAssert.assertTrue(depositPage.isDepositSuccess(),"Deposit is not done properly");

        softAssert.assertAll();
    }

    @Test(description = "Perform Withdrawal for the Account",priority = 4,dataProvider = "testDataExecutions",dataProviderClass = TestExecution_DP.class)
    public void performWithdrawalOnTheAccount(Map<String,String> testData)
    {
        homePage.clickOnHome();

        customerLogin=homePage.clickOnCustomerLogin();

        PathUtils.applySleep(1000);

        customerLogin.selectCustomerName(testData.get("First Name"));
        customerLogin.clickLogin();

        withdrawlPage=customerLogin.clickWithdrawl();
        PathUtils.applySleep(1000);

        String originalAmount=withdrawlPage.getBalanceAmount();

        if (testData.get("Amount").isBlank() || testData.get("Amount").isEmpty()) {
            withdrawlPage.enterAmount("500");
        }

        else
        {
            withdrawlPage.enterAmount(testData.get("Amount"));
        }

        withdrawlPage.clickOnWithdraw();

        String balanceRemaining=withdrawlPage.getBalanceAmount();

        Double withdrawalAmount=Double.parseDouble(originalAmount)-Double.parseDouble(balanceRemaining);

        SoftAssert softAssert=new SoftAssert();

        softAssert.assertEquals(withdrawalAmount,Double.parseDouble(testData.get("Amount")),"Withdrawal amount is not correct");
        softAssert.assertTrue(withdrawlPage.isWithdrawSuccess(),"Withdrawal is not done properly");
        softAssert.assertAll();
    }

}
