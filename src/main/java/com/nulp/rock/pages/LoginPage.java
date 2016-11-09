package com.nulp.rock.pages;

import com.nulp.rock.annotation.Page;
import com.nulp.rock.pages.base.PageObject;
import com.nulp.rock.ui.components.Button;
import com.nulp.rock.ui.components.GoogleFieldDecorator;
import com.nulp.rock.ui.components.TextField;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Petro on 09.11.2016.
 */

@Page(title = "Login google page")
public class LoginPage extends PageObject{

    @FindBy(id = "Email")
    private TextField emailField;

    @FindBy(id = "Passwd")
    private TextField passwordField;

    @FindBy(id = "next")
    private Button next;

    @FindBy(id = "signIn")
    private Button signIn;

    public LoginPage(){
        PageFactory.initElements(new GoogleFieldDecorator(getDriver()), this);
    }

    public void setEmail(String email){
        emailField.visibilityOfElementWait();
        emailField.sendText(email);
    }

    public void setPassword(String password){
        passwordField.visibilityOfElementWait();
        passwordField.sendText(password);
    }

    public void clickNext(){
        next.visibilityOfElementWait();
        next.click();
    }

    public void clickSignIn(){
        signIn.visibilityOfElementWait();
        signIn.click();
    }

    @Override
    public boolean exist() {
        return false;
    }
}
