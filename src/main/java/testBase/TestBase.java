package testBase;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import reports.ExtentFactory;
import reusableComponents.ActionEngine;
import reusableComponents.PropertiesOperations;
import testlink.api.java.client.TestLinkAPIClient;
import testlink.api.java.client.TestLinkAPIException;

public class TestBase extends ActionEngine {
    public WebDriver driver;
    public BrowserFactory browserFactory;
    String browserName = null;
    //static ExtentReports extent = ExtentManager.getInstance();

    /*public WebDriver getDriver() {
        driver=DriverFactory.getInstance().getDriver();
        return driver;
    }*/


    @BeforeMethod
    public void LaunchApplication() throws Exception {
        browserName = PropertiesOperations.getPropertyValueByKey("browser");
        browserFactory = new BrowserFactory();
        DriverFactory.getInstance().setDriver(browserFactory.createBrowserInstance(browserName));
        //driver = browserFactory.initBrowser(browserName);
        //driver = DriverFactory.getInstance().getDriver();
        String url = PropertiesOperations.getPropertyValueByKey("url");
        DriverFactory.getInstance().getDriver().get(url);
        Thread.sleep(5000);
        DriverFactory.getInstance().getDriver().manage().window().maximize();
        System.out.println("Browser maximized");
        DriverFactory.getInstance().getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.getInstance().closeBrowser();
    }
//
    //@AfterMethod
    public void assignDevice() {
        ExtentFactory.getInstance().getExtent().assignDevice(browserName);
    }

    //@AfterMethod
    public void assignAuthor() {
        ExtentFactory.getInstance().getExtent().assignAuthor("Mayank Mishra");

    }


    public static String testProject = "Advance_Selenium";
    public static String testPlan = "Selenium_Plan";
    public static String build = "Selenium_Build";
    public static String notes = null;
    public static String result = null;

    public static String DEVKEY = "a979258effb5ad0f6508fc7ab6bd3e73";
    public static String URL = "http://192.168.1.8/testlink/lib/api/xmlrpc/v1/xmlrpc.php";
    public static void reportResult(String TestProject, String TestPlan, String TestcaseID, String Build, String Notes, String Result) throws TestLinkAPIException, testlink.api.java.client.TestLinkAPIException {
        TestLinkAPIClient api = new TestLinkAPIClient(DEVKEY, URL);
        api.reportTestCaseResult(TestProject, TestPlan, TestcaseID, Build, Notes, Result);
    }
}
