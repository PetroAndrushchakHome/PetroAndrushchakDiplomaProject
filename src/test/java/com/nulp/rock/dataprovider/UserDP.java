package com.nulp.rock.dataprovider;

import com.nulp.rock.common.DataProviderBase;
import com.nulp.rock.dao.user.IUserDAO;
import com.nulp.rock.dataprovider.base.DataProviderHelper;
import org.testng.annotations.DataProvider;

public class UserDP extends DataProviderBase {

    private static IUserDAO userDao = daoRepository.getUserDAO();

    @DataProvider
    public static Object[][] validUser() {
        return DataProviderHelper.toObject(userDao.findById("testUser"));
    }
}
