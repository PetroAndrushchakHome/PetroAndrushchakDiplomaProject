package com.nulp.rock.dao.user;

import com.nulp.rock.dao.base.XlsHelper;
import com.nulp.rock.dao.base.XlsReader;
import com.nulp.rock.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class UserXlsDAO implements IUserDAO {

    private static final String USER_XLSX_FILE = "Login.xlsx";
    private static final String SHEET_NAME = "ValidData";

    private XlsReader xls = new XlsReader(USER_XLSX_FILE, SHEET_NAME);

    public List<UserDTO> findListById(String id) {

        List<Map<String, String>> testData = xls.getDataListById(id);
        if (testData != null && !testData.isEmpty()) {
            List<UserDTO> userData = new ArrayList<UserDTO>();
            for (Map<String, String> dataItem : testData) {
                UserDTO userDTO = new UserDTO();
                XlsHelper.fillObject(userDTO, dataItem);
                userData.add(userDTO);
            }
            return userData;
        }
        return null;
    }

    public UserDTO findById(String id) {
        Map<String, String> testData = xls.getDataById(id);

        UserDTO userDTO = new UserDTO();
        XlsHelper.fillObject(userDTO, testData);

        return userDTO;
    }


}
