package com.nulp.rock.tests;

import com.nulp.rock.business.LoginBO;
import com.nulp.rock.business.MainBO;
import com.nulp.rock.common.Asserter;
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

        LoginBO login = new LoginBO();
        MainBO main = new MainBO();

        login.login(user);
        this.asserter.assertPass(main.isLogged(),"User is not logged in", "User is logged in");

        System.out.println("TEST  1!!!!!!!!");
    }
}
