package com.nulp.rock.dao.generator;


import com.nulp.rock.datafactory.RandomDataSource;
import com.nulp.rock.dto.UserDTO;

public class GeneratorDAO implements IGeneratorDAO {

    private RandomDataSource data = new RandomDataSource();

    public UserDTO getNewUser() {
        UserDTO user = new UserDTO();
        data.fillEntity(user);
        return user;
    }

    public static void main(String[] args) {
        GeneratorDAO dao = new GeneratorDAO();
        UserDTO user = dao.getNewUser();
        System.out.println(user);
    }
}
