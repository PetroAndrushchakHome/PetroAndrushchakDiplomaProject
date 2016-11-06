package com.nulp.rock.tests;

import com.nulp.rock.common.TestBase;
import com.nulp.rock.dataprovider.UserDP;
import com.nulp.rock.dto.UserDTO;
import com.nulp.rock.pages.MainPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by Petro on 29.10.2016.
 */

public class LoginTest extends TestBase {

    @Test(dataProviderClass = UserDP.class, dataProvider = "validUser")
    public void testLoginGoogle(UserDTO user) {

        MainPage mainPage = new MainPage();
        mainPage.clickLogin();

        System.out.println("TEST  1!!!!!!!!");
    }
}
