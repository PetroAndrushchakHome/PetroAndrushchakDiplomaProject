package com.nulp.rock.pages;

import com.nulp.rock.annotation.Page;
import com.nulp.rock.pages.base.PageObject;
import com.nulp.rock.ui.components.Button;
import com.nulp.rock.ui.components.GoogleFieldDecorator;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.nulp.rock.common.Driver.getDriver;

/**
 * Created by Petro on 06.11.2016.
 */
@Page(title = "Main google page")
public class MainPage extends PageObject {

    @FindBy(xpath = "//a[contains(@href,'Login')]/..")
    private Button loginButton;

    @FindBy(xpath = "//a[contains(@href,'SignOut')]/..")
    private Button signOutOptionsButton;

    public MainPage(){
        PageFactory.initElements(new GoogleFieldDecorator(getDriver()), this);
    }

    public void clickLogin() {
        loginButton.visibilityOfElementWait();
        loginButton.click();
    }

    public boolean isSignOutOptionsButtonPresent(){
        return signOutOptionsButton.isPresent();
    }
    public boolean exist() {
        return false;
    }
}
