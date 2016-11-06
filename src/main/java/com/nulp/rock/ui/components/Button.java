package com.nulp.rock.ui.components;

import com.nulp.rock.common.Driver;
import com.nulp.rock.common.Logger;
import com.nulp.rock.localization.Localization;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;


public class Button extends NavigationLink {

    public Button() {
    }

    public Button(WebElement wrappedElement, String name, String page) {
        super(wrappedElement, name, page);
    }

    public void submit() {
        if (wrappedElement != null) {
            highlightElement();
            wrappedElement.submit();
            Logger.logInfo(Localization.getMessage(Localization.BUTTON_SUBMIT, name, page));
        } else
            Logger.logError(Localization.getMessage(Localization.NO_BUTTON, name));
    }

    public boolean checkboxIsChecked() {
        highlightElement();
        JavascriptExecutor exec = Driver.getCurrentDriver();
        return (Boolean) exec.executeScript("return $(arguments[0]).is(\":checked\");", wrappedElement);
    }

}
