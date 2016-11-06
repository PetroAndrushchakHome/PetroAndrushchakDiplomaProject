package com.nulp.rock.common;

import com.nulp.rock.dao.base.DAORepository;
import com.nulp.rock.dao.user.IUserDAO;
import com.nulp.rock.dao.user.UserXlsDAO;
import com.nulp.rock.listeners.AnnotationTransformerListener;
import com.nulp.rock.listeners.TestListener;
import com.nulp.rock.listeners.TestResultListener;
import com.nulp.rock.logging.CustomReport;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.TestNG;
import org.testng.annotations.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.nulp.rock.common.Driver.wait;

/**
 * Created by Petro on 30.10.2016.
 */
@Listeners({AnnotationTransformerListener.class, TestResultListener.class, TestListener.class})
public class TestBase {

    protected RemoteWebDriver driver;
    public static CustomReport reports;
    public Asserter asserter = new Asserter();

    static {
        TestNG testNG = new TestNG();
        testNG.setAnnotationTransformer(new AnnotationTransformerListener());
        DataProviderBase.daoRepository = new DAORepository();
    }

    public static String baseUrl = Config.getProperty("host.for.test");
    private IUserDAO userDAO = new UserXlsDAO();

    @BeforeClass(alwaysRun = true)
    public void printClassNameBeforeTest() {
        Logger.logDebug("++++++++" + this.getClass().getSimpleName()+ "+++++++");
        reports = new CustomReport();
        reports.setScriptStartTime(System.currentTimeMillis());
        try {
            reports.createTestReport(this.getClass().getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Logger.logDebug("New report created");
    }

    @AfterClass(alwaysRun = true)
    public void printClassName() {
        Logger.setTest(false);
        reports.executionHealthReport(getClass().getSimpleName());
        try {
            if (CustomReport.getFailedTests() > 0) {
                CustomReport.setFailCount(CustomReport.getFailCount() + CustomReport.getFailedTests());
                CustomReport.setPassCount(CustomReport.getPassCount() + CustomReport.getPassedTests());
                reports.summaryReport(getClass().getSimpleName(), getClass().getSimpleName(),
                        CustomReport.getExecutionTime(reports.getScriptStartTime(), System.currentTimeMillis()),
                        reports.getFail());

                reports.closeFile();
            } else {
                CustomReport.setFailCount(CustomReport.getFailCount() + CustomReport.getFailedTests());
                CustomReport.setPassCount(CustomReport.getPassCount() + CustomReport.getPassedTests());
                reports.summaryReport(getClass().getSimpleName(), getClass().getSimpleName(),
                        CustomReport.getExecutionTime(reports.getScriptStartTime(), System.currentTimeMillis()),
                        reports.getPass());
                reports.closeFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        CustomReport.setFailedTests(0);
        CustomReport.setPassedTests(0);

        Logger.logDebug("++++++++" + this.getClass().getSimpleName()+ "+++++++++");

    }

    @BeforeMethod(alwaysRun = true)
    public synchronized void start(Method method) {
        Logger.logDebug("TEST TEST TEST");
        Logger.logDebug("Start BEFORE Method");
        while (true) {
            driver = Driver.getDriver();
            if (driver != null)
                break;
        }
        System.out.println(driver.getClass().getSimpleName());
        if (!driver.getClass().getSimpleName().contains("Android")
                && !driver.getClass().getSimpleName().contains("Remote")
                && !driver.getClass().getSimpleName().contains("SelendroidDriver")) {
            driver.manage().window().maximize();
        }

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        if (!driver.getClass().getSimpleName().contains("AndroidDriver")
                && !driver.getClass().getSimpleName().contains("Remote")
                && !driver.getClass().getSimpleName().contains("SelendroidDriver")) {
            driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
            wait = new WebDriverWait(driver, 8);
        }

        if (!driver.getClass().getSimpleName().contains("SelendroidDriver")) {
            driver.get(baseUrl);
        }
        Logger.logDebug("Finish BEFORE Class");
        reports.writeTestName(method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void end(ITestResult result) {
        Logger.logDebug("Start AFTER Method");
        if (Driver.getCurrentDriver() != null
                && !Driver.getCurrentDriver().getClass().getSimpleName().contains("Phone")) {
            try {

                Logger.makeScreenshot(this.getClass().getSimpleName() + System.currentTimeMillis());
            } catch (Exception ignored) {
            }
            closeWindows();
        }
        Driver.releaseDriver();

        Logger.logDebug("Finish AFTER Method");

        if (result.isSuccess()) {
            CustomReport.setPassedTests(CustomReport.getPassedTests() + 1);
        } else {
            CustomReport.setFailedTests(CustomReport.getFailedTests() + 1);
        }
        CustomReport.setTestStatus(true);
    }

    @AfterSuite(alwaysRun = true)
    public void closeAllWindows() {

        Logger.setTest(false);
        Logger.logDebug("Before closing all windows");

        Driver.closeAllWindows();

        Logger.logDebug("All Windows are Closed");
        Logger.logDebug(killIEServers());
        System.out.println("Generate total report");
        reports.writtingSummaryReport();

    }

    public void closeWindows() {
        if (Driver.getCurrentDriver() != null) {
            Set<String> windowHandles = Driver.getCurrentDriver().getWindowHandles();
            if (windowHandles != null || !windowHandles.isEmpty()) {
                if (windowHandles.size() == 1 || Driver.getCurrentDriver().getClass().getSimpleName().contains("Remote")
                        || Driver.getCurrentDriver().getClass().getSimpleName().contains("Selendroid")) {
                    return;
                }
                int count = windowHandles.size();
                for (String windowId : windowHandles) {
                    Driver.getCurrentDriver().switchTo().window(windowId);
                    if (count == 1) {
                        return;
                    }

                    Driver.getCurrentDriver().close();
                    count = count - 1;
                }
            }
        }
    }

    private String killIEServers() {
        StringBuffer output = new StringBuffer();
        Process p;
        try {
            p = Runtime.getRuntime().exec("taskkill /f /im IEDriverServer.exe");
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();
    }
}
