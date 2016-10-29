package com.nulp.rock.dao.user;

import com.nulp.rock.dto.UserDTO;

import java.util.List;

public interface IUserDAO {

    List<UserDTO> findListById(String id);

    UserDTO findById(String id);
}
