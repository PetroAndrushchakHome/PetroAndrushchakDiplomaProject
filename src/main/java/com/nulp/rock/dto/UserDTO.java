package com.nulp.rock.dto;

import com.nulp.rock.annotation.Data;
import com.nulp.rock.dao.base.XlsMapping;
import com.nulp.rock.datafactory.RandomType;

import java.util.HashMap;

public class UserDTO {

    public UserDTO() {
    }

    public UserDTO(HashMap<String, String> data) {
        this.userName = data.get("username");
        this.email = data.get("email");
        this.password = data.get("password");
    }

    private String userName;
    private String email;
    private String password;

    public String getUserName() {
        return userName;
    }

    @Data(type = RandomType.FIRST_NAME)
    @XlsMapping(header = "name")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    @Data(type = RandomType.EMAIL)
    @XlsMapping(header = "email")
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    @Data(type = RandomType.CHARS, min = 8, max = 10)
    @XlsMapping(header = "password")
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
