package com.nulp.rock.business;

import com.nulp.rock.dto.UserDTO;
import com.nulp.rock.pages.LoginPage;
import com.nulp.rock.pages.MainPage;

/**
 * Created by Petro on 09.11.2016.
 */
public class LoginBO {

    MainPage mainPage = new MainPage();
    LoginPage loginPage = new LoginPage();

    public void login(UserDTO user){
        mainPage.clickLogin();
        loginPage.setEmail(user.getEmail());
        loginPage.clickNext();
        loginPage.setPassword(user.getPassword());
        loginPage.clickSignIn();
    }
}
