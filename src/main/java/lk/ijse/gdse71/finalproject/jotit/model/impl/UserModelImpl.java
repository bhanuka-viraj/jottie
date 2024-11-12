package lk.ijse.gdse71.finalproject.jotit.model.impl;

import lk.ijse.gdse71.finalproject.jotit.dto.UserDto;
import lk.ijse.gdse71.finalproject.jotit.model.UserModel;
import lk.ijse.gdse71.finalproject.jotit.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserModelImpl implements UserModel {

    @Override
    public void save(UserDto userDto) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("INSERT INTO user (user_name,password,email,dob,first_name,last_name) VALUES (?,?,?,?,?,?)",
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getEmail(),
                userDto.getDateOfBirth(),
                userDto.getFirstName(),
                userDto.getLastName()
                );
    }

    @Override
    public UserDto getUser() {
        return null;
    }

    @Override
    public ArrayList<UserDto> getAllUsers() {
        return null;
    }
}
