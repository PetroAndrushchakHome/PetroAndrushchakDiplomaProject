package com.nulp.rock.ui.components;
import com.nulp.rock.common.Logger;
import com.nulp.rock.localization.Localization;
import org.openqa.selenium.WebElement;

public class TextContainer extends AbstractPageElement {

    public TextContainer(WebElement wrappedElement, String name, String page) {
        super(wrappedElement, name, page);
    }

    public String getText() {
        if (wrappedElement != null) {
            highlightElement();
            String containerValue = wrappedElement.getText();
            Logger.logDebug(Localization.getMessage(Localization.TEXT_CONTAINER_GET, containerValue, name, page));
            return wrappedElement.getText();
        } else
            Logger.logError(Localization.getMessage(Localization.NO_TEXT_CONTAINER, name, page));
        return null;
    }
}
