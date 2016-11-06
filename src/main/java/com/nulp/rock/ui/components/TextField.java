package com.nulp.rock.ui.components;
import com.nulp.rock.common.Driver;
import com.nulp.rock.common.Logger;
import com.nulp.rock.localization.Localization;
import org.openqa.selenium.WebElement;

public class TextField extends AbstractPageElement {

    public TextField() {
    }

    public TextField(WebElement wrappedElement, String name, String page) {
        super(wrappedElement, name, page);
    }

    public void sendText(String text) {
        if (wrappedElement != null) {
            highlightElement();

            if (!(Driver.getCurrentDriver().getClass().getSimpleName().contains("Remote")
                    || Driver.getCurrentDriver().getClass().getSimpleName().contains("Explorer"))) {
            }
            clear();
            clickableOfElementWait();
            if (!Driver.getCurrentDriver().getClass().getSimpleName().contains("Remote")
                    && !Driver.getCurrentDriver().getClass().getSimpleName().contains("Selendroid")) {

                wrappedElement.sendKeys(text);
                Logger.logInfo(Localization
                        .getMessage(Localization.INPUT_SET_VALUE, text, name, page));

            } else {
                Driver.getCurrentDriver()
                        .executeScript("$(arguments[0]).val('" + text + "').change();", wrappedElement);
                Logger.logInfo(Localization
                        .getMessage(Localization.INPUT_SET_VALUE, text, name, page));
            }

        } else
            Logger.logError(Localization.getMessage(Localization.NO_INPUT, name, page));
    }

    public void sendTextJquery(String text) {
        Driver.getCurrentDriver()
                .executeScript("$(arguments[0]).val('" + text + "');", wrappedElement);
    }

    private void clear() {
        if (wrappedElement != null) {
            wrappedElement.clear();
            Logger.logDebug("Clear element");
        } else
            Logger.logError(Localization
                    .getMessage(Localization.NO_INPUT, name, page));
    }

    public void clearJquery() {
        Driver.getCurrentDriver()
                .executeScript(
                        "$(arguments[0]).unmask().attr(\"value\",\"\").val(\"\").mask(\"999-999-999\");",
                        wrappedElement);
    }
}
