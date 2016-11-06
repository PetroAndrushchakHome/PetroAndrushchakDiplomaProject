package com.nulp.rock.listeners;

import com.nulp.rock.common.Driver;
import com.nulp.rock.common.TestBase;
import com.nulp.rock.logging.CustomReport;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Date;

public class TestResultListener implements ITestListener {

    public void onStart(ITestContext arg0) {
        System.out.println("INSIDE NEW TEST RESULT LISTENER");
    }

    public void onTestFailure(ITestResult arg0) {
        StackTraceElement[] stackTrace = arg0.getThrowable().getStackTrace();
        StringBuilder builder = new StringBuilder();
        for (StackTraceElement stackTraceElement : stackTrace) {
            builder.append(stackTraceElement.toString() + "<br>");
        }
        TestBase.reports.writeIntoFile(Driver.getCurrentDriver(), arg0.getTestClass().getRealClass().getSimpleName(), arg0.getTestClass().getRealClass().getSimpleName(), "Exception", arg0.getThrowable().getMessage(), CustomReport.getFail(), builder.toString(),
                new Date(System.currentTimeMillis()).toString());
    }

    public void onTestSkipped(ITestResult arg0) {
        CustomReport.setSkipCount(CustomReport.getSkipCount() + 1);
        CustomReport.setSkippedTests(CustomReport.getSkippedTests() + 1);
    }

    public void onFinish(ITestContext arg0) {
        // TODO Auto-generated method stub
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
        // TODO Auto-generated method stub
    }

    public void onTestStart(ITestResult arg0) {
        // TODO Auto-generated method stub
    }

    public void onTestSuccess(ITestResult arg0) {
        // TODO Auto-generated method stub
    }

}
