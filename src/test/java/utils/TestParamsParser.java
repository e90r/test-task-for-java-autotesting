package utils;

import org.testng.ITestContext;
import org.testng.Reporter;

import java.util.Map;

public class TestParamsParser {

    public static Map<String, String> getParams() {
        ITestContext context = Reporter.getCurrentTestResult().getTestContext();
        return context.getCurrentXmlTest().getAllParameters();
    }

}
