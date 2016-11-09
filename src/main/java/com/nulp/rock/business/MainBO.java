package com.nulp.rock.business;

import com.nulp.rock.pages.MainPage;

/**
 * Created by Petro on 10.11.2016.
 */
public class MainBO {

    MainPage mainPage = new MainPage();

    public boolean isLogged(){
        return mainPage.isSignOutOptionsButtonPresent();
    }
}
