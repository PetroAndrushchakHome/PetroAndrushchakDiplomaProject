package com.nulp.rock.ui.components;

import org.openqa.selenium.WebElement;

public interface IPageElement {

    boolean isDisplayed();

    String getTagName();

    String getAttribute(String name);

    WebElement getElement();
}
