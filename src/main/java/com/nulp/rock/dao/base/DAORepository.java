package com.nulp.rock.dao.base;


import com.nulp.rock.dao.user.IUserDAO;
import com.nulp.rock.dao.user.UserXlsDAO;

public class DAORepository {

    public DAORepository() {
        userDAO = new UserXlsDAO();
    }

    private IUserDAO userDAO;

    public IUserDAO getUserDAO() {
        return userDAO;
    }

}
