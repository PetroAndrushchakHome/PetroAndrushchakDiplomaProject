package com.nulp.rock.common;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class BrowserDriverFactory {

    public RemoteWebDriver chooseBrowser(String selectedBrowser) {
        if (selectedBrowser != null) {
            if (selectedBrowser.equals("FF")) {
                return getFireFoxDriver();
            }
            if (selectedBrowser.equals("CHROME")) {
                return getChromeDriver();
            }
            if (selectedBrowser.equals("IE")) {

                return getInternetExplorerDriver();
            }
            if (selectedBrowser.equals("Safari")) {

                return getSafariDriver();
            }
            if (selectedBrowser.equals("IP")) {

                return getIPDriver();
            }
            if (selectedBrowser.equals("SAFARI")) {

                return getIPDriver();
            }

            if (selectedBrowser.equals("ANDROID")) {

                return getANDROIDDriver();
            }

            if (selectedBrowser.equals("AH")) {

                return getANDROIDHybridDriver();
            }
        }
        return getFireFoxDriver();
    }

    private RemoteWebDriver getFireFoxDriver() {
        final FirefoxProfile profile = new FirefoxProfile(/* profilePath */);

        profile.setAssumeUntrustedCertificateIssuer(true);
        profile.setAcceptUntrustedCertificates(true);
        profile.setEnableNativeEvents(false);
        profile.setPreference("geo.enabled", false);
        RemoteWebDriver driver = new FirefoxDriver(profile);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

        return driver;
    }

    private RemoteWebDriver getChromeDriver() {
        System.setProperty("webdriver.chrome.driver",
                "src\\test\\java\\resourses\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        return new ChromeDriver(options);
    }

    private RemoteWebDriver getIPDriver() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(CapabilityType.BROWSER_NAME, "iOS");
            capabilities.setCapability(CapabilityType.VERSION, "6.0");
            capabilities.setCapability(CapabilityType.PLATFORM, "Mac");
            capabilities.setCapability("device", "iPhone Simulator");
            //tell Appium where the location of the app is
            capabilities.setCapability("app", "safari");

            //create a RemoteWebDriver, the default port for Appium is 4723
            return new RemoteWebDriver(new URL("http://10.25.10.219:4723/wd/hub/"), capabilities);

        } catch (Exception e) {
            e.printStackTrace();
            return getFireFoxDriver();
        }

    }

    private RemoteWebDriver getInternetExplorerDriver() {
        System.setProperty("webdriver.ie.driver","src\\test\\resources\\IEDriverServer.exe");
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability("unexpectedAlertBehaviour", "accept");
        capabilities.setCapability("ignoreProtectedModeSettings", true);
        capabilities.setCapability("disable-popup-blocking", true);
        capabilities.setCapability("enablePersistentHover", true);
        capabilities.setVersion("IE11");
        System.out.println(capabilities.getVersion() + "+-*/-/**++-*/+/+/+/+/*+/*++++*/-/--*-+/+*/+*+-/-*");
        return new InternetExplorerDriver(capabilities);
    }


    private RemoteWebDriver getANDROIDDriver() {
        DesiredCapabilities caps = DesiredCapabilities.android();
        caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        caps.setCapability(CapabilityType.SUPPORTS_ALERTS, true);
        caps.setCapability(CapabilityType.SUPPORTS_LOCATION_CONTEXT,
                true);
        caps.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);

        return null;
    }


    public RemoteWebDriver getANDROIDHybridDriver() {
        try {
            DesiredCapabilities capabilities = DesiredCapabilities
                    .android();
            URL url = new URL("http://10.25.14.74:4723/wd/hub");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private RemoteWebDriver getAppiumDriver() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("device", "Selendroid");
        desiredCapabilities.setCapability("app", "src\\test\\java\\resourses\\EasyOrders-0.1.147.apk");
        URL url;
        try {
            url = new URL("http://127.0.0.1:4723/wd/hub");

            RemoteWebDriver remoteWebDriver = new RemoteWebDriver(url, desiredCapabilities);
            return remoteWebDriver;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private RemoteWebDriver getSafariDriver() {
        File safari = new File(
                "C:\\Program Files (x86)\\Safari\\Safari.exe");
        System.setProperty("SafariDefaultPath",
                safari.getAbsolutePath());
        System.setProperty("webdriver.safari.driver", "src\\test\\java\\resources\\selenium-server-standalone-2.31.0.jar");
        return new SafariDriver(DesiredCapabilities.safari());
    }
}
