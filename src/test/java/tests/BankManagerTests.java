package tests;

import dataProviders.TestExecution_DP;
import framework.PathUtils;
import framework.constants.AppConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.bankManagerLogin.BankManagerPage;
import pages.bankManagerLogin.CustomersPage;

import java.util.Map;
import java.util.Optional;

public class BankManagerTests extends TESTNGBase {

    BankManagerPage bankManagerPage;
    CustomersPage customersPage;

    @Test(description = "Navigating to Bank Manager Login Screen",priority = 1,enabled = false)
    public void navigateToBankManagerLoginScreen()
    {
        //Page Chaining Model
        bankManagerPage=homePage.clickOnBankManagerLogin();

        //We go with the concept of ASsertions to validate the test case
        //There are two types of Asserts:
        //1. Soft Assert --> If the condition is not met then the test case execution will not be stopped immediately and the test case will be marked as failed at the end of the test case execution
        //2. Hard Assert --> If the condition is not met then the test case will be failed and exeution of the test case will be stopped immediately

        //Soft Assert is used when we have to validate more than one conditions

        SoftAssert softAssert=new SoftAssert();

        softAssert.assertTrue(bankManagerPage.checkIfAddCustomerButtonIsDisplayed(),"Add customer button is not displayed");
        softAssert.assertTrue(bankManagerPage.checkIfOpenAccountButtonIsDisplayed(),"Open account button is not displayed");
        softAssert.assertTrue(bankManagerPage.checkIfCustomersButtonIsDisplayed(),"Customers button is not displayed");

        softAssert.assertAll(); //Then it will validate all the assertions at once
    }

    @Test(description = "Adding New Customers to the System",priority = 2,dataProvider = "testDataExecutions",dataProviderClass = TestExecution_DP.class)
    public void addNewCustomersToSystem(Map<String,String> testData)
    {
        //Here we are checking if the bank manager object is null or not
        //If it is not null, then we will use the existing object
        //Else we will create a new object
        bankManagerPage=Optional.ofNullable(bankManagerPage).orElseGet(()-> {

            //String .join --> Helps us join the data with a respective delimiter
            excelUtils.removeDataFromExcelSheet_ExceptMetaData(PathUtils.getTestDataFolder()+"Sample_Executions.xlsx",String.join(",",AppConstants.OPEN_ACCOUNT,AppConstants.VIEW_CUSTOMERS,AppConstants.CUSTOMER_LOGIN,AppConstants.WITHDRAW_AMOUNT,AppConstants.DEPOSIT_AMOUNT));

            return homePage.clickOnBankManagerLogin();
        });

//        try {
//            bankManagerPage = Optional.ofNullable(bankManagerPage).get();
//        }
//
//        catch(Exception e)
//        {
//            bankManagerPage=homePage.clickOnBankManagerLogin();
//        }

//        bankManagerPage= homePage.clickOnBankManagerLogin();

        bankManagerPage.clickOnAddCustomer()
                .enterFirstName(testData.get("First Name"))
                        .enterLastName(testData.get("Last Name"))
                                .enterPostCode(testData.get("Postal Code"))
                                        .clickAddCustomer();

//        firstName= faker.name().firstName();


        //Hard Assert
        Assert.assertTrue(seleniumUtils.getAlertText().split(":").length>1,"Alert message is not displayed");
        seleniumUtils.acceptAlert();

        testUtil.setData("First_Name",testData.get("First Name"));

        excelUtils.writeDataToTheExcelFile(PathUtils.getTestDataFolder()+"Sample_Executions.xlsx", AppConstants.OPEN_ACCOUNT,AppConstants.FIRST_NAME,testData.get("First Name"));
        excelUtils.writeDataToTheExcelFile(PathUtils.getTestDataFolder()+"Sample_Executions.xlsx", AppConstants.OPEN_ACCOUNT,AppConstants.LAST_NAME,testData.get("Last Name"),AppConstants.FIRST_NAME+"-"+testData.get("First Name"));

        excelUtils.writeDataToTheExcelFile(PathUtils.getTestDataFolder()+"Sample_Executions.xlsx", AppConstants.VIEW_CUSTOMERS,AppConstants.FIRST_NAME,testData.get("First Name"));
        excelUtils.writeDataToTheExcelFile(PathUtils.getTestDataFolder()+"Sample_Executions.xlsx", AppConstants.VIEW_CUSTOMERS,AppConstants.LAST_NAME,testData.get("Last Name"),AppConstants.FIRST_NAME+"-"+testData.get("First Name"));

        excelUtils.writeDataToTheExcelFile(PathUtils.getTestDataFolder()+"Sample_Executions.xlsx", AppConstants.CUSTOMER_LOGIN,AppConstants.FIRST_NAME,testData.get("First Name"));
        excelUtils.writeDataToTheExcelFile(PathUtils.getTestDataFolder()+"Sample_Executions.xlsx", AppConstants.CUSTOMER_LOGIN,AppConstants.LAST_NAME,testData.get("Last Name"),AppConstants.FIRST_NAME+"-"+testData.get("First Name"));

        excelUtils.writeDataToTheExcelFile(PathUtils.getTestDataFolder()+"Sample_Executions.xlsx", AppConstants.DEPOSIT_AMOUNT,AppConstants.FIRST_NAME,testData.get("First Name"));
        excelUtils.writeDataToTheExcelFile(PathUtils.getTestDataFolder()+"Sample_Executions.xlsx", AppConstants.DEPOSIT_AMOUNT,AppConstants.LAST_NAME,testData.get("Last Name"),AppConstants.FIRST_NAME+"-"+testData.get("First Name"));
        excelUtils.writeDataToTheExcelFile(PathUtils.getTestDataFolder()+"Sample_Executions.xlsx", AppConstants.DEPOSIT_AMOUNT,AppConstants.AMOUNT,PathUtils.generateRandomNumber(4),AppConstants.FIRST_NAME+"-"+testData.get("First Name"));

        excelUtils.writeDataToTheExcelFile(PathUtils.getTestDataFolder()+"Sample_Executions.xlsx", AppConstants.WITHDRAW_AMOUNT,AppConstants.FIRST_NAME,testData.get("First Name"));
        excelUtils.writeDataToTheExcelFile(PathUtils.getTestDataFolder()+"Sample_Executions.xlsx", AppConstants.WITHDRAW_AMOUNT,AppConstants.LAST_NAME,testData.get("Last Name"),AppConstants.FIRST_NAME+"-"+testData.get("First Name"));
        excelUtils.writeDataToTheExcelFile(PathUtils.getTestDataFolder()+"Sample_Executions.xlsx", AppConstants.WITHDRAW_AMOUNT,AppConstants.AMOUNT,PathUtils.generateRandomNumber(3),AppConstants.FIRST_NAME+"-"+testData.get("First Name"));

    }

    @Test(description = "Adding New Accounts to the System",priority = 3,dependsOnMethods = "addNewCustomersToSystem",dataProvider = "testDataExecutions",dataProviderClass = TestExecution_DP.class)
    public void openAccountForNewCustomers(Map<String,String> testData)
    {
        bankManagerPage.clickOnOpenAccount()
                .selectCustomerName(testData.get("First Name"))
                    .selectCurrency(testData.get("Currency"))
                        .clickProcess();

        Assert.assertTrue(seleniumUtils.getAlertText().split(":").length>1,"Alert message is not displayed");

        seleniumUtils.acceptAlert();
    }

    @Test(description = "Viewing the Customers",priority = 4,dependsOnMethods = "openAccountForNewCustomers",dataProvider = "testDataExecutions",dataProviderClass = TestExecution_DP.class)
    public void viewCustomers(Map<String,String> testData)
    {
        customersPage=bankManagerPage.clickOnCustomers()
                .enterCustomerName(testData.get("First Name"));

        reports.captureScreenshots();

        Assert.assertTrue(customersPage.checkIfRowsOfDataArePresent(),"No Rows of data are present");
    }
}
