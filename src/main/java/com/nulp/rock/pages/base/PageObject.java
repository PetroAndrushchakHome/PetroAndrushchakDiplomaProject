package com.nulp.rock.pages.base;

import com.nulp.rock.common.Driver;
import org.openqa.selenium.remote.RemoteWebDriver;


public abstract class PageObject implements INavigationPage {

    private RemoteWebDriver driver = Driver.getCurrentDriver();

    public RemoteWebDriver getDriver() {
        return driver;
    }

}
