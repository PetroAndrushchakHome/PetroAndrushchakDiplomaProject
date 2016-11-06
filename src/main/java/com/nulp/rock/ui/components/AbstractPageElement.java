package com.nulp.rock.ui.components;

import com.google.common.base.Function;
import com.nulp.rock.common.Driver;
import com.nulp.rock.common.Logger;
import com.nulp.rock.localization.Localization;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class AbstractPageElement implements IPageElement {

    private RemoteWebDriver driver = Driver.getCurrentDriver();
    protected WebElement wrappedElement;
    protected String name;
    protected String page;

    public AbstractPageElement() {
    }

    public AbstractPageElement(final WebElement wrappedElement, final String name, final String page) {
        this.wrappedElement = wrappedElement;
        this.name = name;
        this.page = page;
    }

    public boolean visibilityOfElementWait() {
        if (wrappedElement != null) {
            try {
                WebDriverWait wait = new WebDriverWait(Driver.getCurrentDriver(), 20);
                wait.until(ExpectedConditions.visibilityOf(wrappedElement));
                highlightElement();
                return true;
            } catch (Exception e) {
                return false;
            }
        } else
            Logger.logWarning("PageElement " + name + " not exist");
        return false;
    }

    public boolean clickableOfElementWait() {
        if (wrappedElement != null) {
            try {
                WebDriverWait wait = new WebDriverWait(Driver.getCurrentDriver(), 20);
                wait.until(ExpectedConditions.elementToBeClickable(wrappedElement));
                highlightElement();
                return true;
            } catch (Exception e) {
                return false;
            }
        } else
            Logger.logError("PageElement " + name + " is not enabled");
        return false;
    }

    public boolean waitForContentChange() {
        final String text = this.wrappedElement.getText();
        WebDriverWait wait = new WebDriverWait(Driver.getCurrentDriver(), 20);
        boolean res = true;

        try {
            Object obj = wait.until(new Function() {
                public Object apply(Object arg0) {
                    if (wrappedElement.getText().contentEquals(text)) {
                        return false;
                    }
                    return true;
                }

            });
        } catch (TimeoutException e) {
            return false;
        }
        return res;

    }

    public boolean isDisplayed() {
        if (Driver.getCurrentDriver().getClass().getSimpleName().contains("Remote")) {
            return visibilityOfElementWait();
        }
        try {
            if (wrappedElement != null) {
                try {
                    return wrappedElement.isDisplayed();
                } catch (NoSuchElementException e) {
                    Logger.logWarning("PageElement '" + name + "' not displayed");
                    return false;
                }
            } else
                Logger.logError("PageElement '" + name + "' not exist");
            return false;
        } catch (ElementNotVisibleException e) {
            Logger.logDebug("ElementNotVisibleException");
            return false;
        }
    }

    public String getTagName() {
        if (wrappedElement != null) {
            return wrappedElement.getTagName();
        } else
            Logger.logError("PageElement " + name + " not exist");
        return null;
    }

    public String getAttribute(String name) {
        if (wrappedElement != null) {
            highlightElement();
            return wrappedElement.getAttribute(name);
        } else
            Logger.logInfo("PageElement " + name + " not exist");
        return null;
    }

    public boolean isPresent() {
        if (wrappedElement != null) {
            try {
                return wrappedElement.isDisplayed();
            } catch (NoSuchElementException e) {
                return false;
            }
        } else
            return false;
    }

    public WebElement getElement() {
        return wrappedElement;
    }

    public void highlightElement() {

        if (Driver.getCurrentDriver().getClass().getSimpleName().contains("Remote")
                || Driver.getCurrentDriver().getClass().getSimpleName().contains("Selendroid")) {
            return;
        }
        try {
            String bg = wrappedElement.getCssValue("backgroundColor");
            for (int i = 0; i < 3; i++) {
                driver.executeScript("arguments[0].style.backgroundColor = '" + "red" + "'", wrappedElement);
                driver.executeScript("arguments[0].style.backgroundColor = '" + bg + "'", wrappedElement);
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            try {
                Driver.getCurrentDriver().findElement(By.tagName("iframe")).isDisplayed();

                if (Driver.getCurrentDriver().findElement(By.xpath("//h1[contains(text(),'Application Error')]"))
                        .isDisplayed()) {
                    Logger.logError("Timeout Error");
                    Driver.getCurrentDriver().get(Driver.getCurrentDriver().getCurrentUrl());
                }
                if (Driver.getCurrentDriver().findElement(By.xpath("//h1[contains(text(),'Application Error')]"))
                        .isDisplayed()) {
                    throw e;
                }
            } catch (Exception e2) {
                throw e;
            }
        }
    }

	public boolean waitForInvisibility(By localor) {
		WebDriverWait wait = new WebDriverWait(Driver.getCurrentDriver(), TimeUnit.SECONDS.toMillis(20));
		return wait.until(ExpectedConditions.invisibilityOfElementLocated(localor));
    }

	public boolean waitForInvisibility() {
        WebDriverWait wait = new WebDriverWait(Driver.getCurrentDriver(), TimeUnit.SECONDS.toMillis(20));
        return wait.until(invisibilityOfElement());
    }

    public ExpectedCondition<Boolean> invisibilityOfElement() {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                try {
                    return !(wrappedElement.isDisplayed());
                } catch (NoSuchElementException e) {
                    return true;
                } catch (StaleElementReferenceException e) {
                    return true;
                } catch (WebDriverException e) {
                    return false;
                }
            }

            @Override
            public String toString() {
                return "element to no longer be visible: " + name;
            }
        };
    }

    public Object executeScript(String script) {
        if (wrappedElement != null) {

            highlightElement();
            Object executeScript = Driver.getCurrentDriver().executeScript(script, wrappedElement);
            Logger.logInfo(Localization.getMessage(Localization.EXECUTE_SCRIPT, name, page));
            return executeScript;
        } else
            Logger.logError(Localization.getMessage(Localization.NO_ELEMENT, name));
        return null;
    }

    public String getText() {
        if (wrappedElement != null) {
            highlightElement();
            return wrappedElement.getText();
        } else
            Logger.logInfo("PageElement " + name + " not exist");
        return null;
    }

}
