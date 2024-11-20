package lk.ijse.gdse71.finalproject.jotit.model.impl;

import lk.ijse.gdse71.finalproject.jotit.dto.UserDto;
import lk.ijse.gdse71.finalproject.jotit.model.UserModel;
import lk.ijse.gdse71.finalproject.jotit.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserModelImpl implements UserModel {

    @Override
    public boolean save(UserDto userDto)throws Exception {
        return CrudUtil.execute("INSERT INTO user (user_id,user_name,password,email,dob,first_name,last_name) VALUES (?,?,?,?,?,?,?)",
                userDto.getId(),
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getEmail(),
                userDto.getDateOfBirth(),
                userDto.getFirstName(),
                userDto.getLastName()
                );
    }

    @Override
    public UserDto getUser(String username) throws Exception{
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM user WHERE user_name = ?",username);
        if(resultSet.next()){
            UserDto userDto = new UserDto();
            userDto.setId(resultSet.getString("user_id"));
            userDto.setUsername(resultSet.getString("user_name"));
            userDto.setPassword(resultSet.getString("password"));
            userDto.setEmail(resultSet.getString("email"));
            userDto.setFirstName(resultSet.getString("first_name"));
            userDto.setLastName(resultSet.getString("last_name"));
            userDto.setCreatedDate(resultSet.getDate("created_at"));
            userDto.setUpdatedDate(resultSet.getDate("updated_at"));
            userDto.setDateOfBirth(resultSet.getDate("dob"));
            return userDto;
        }
        return null;
    }

    @Override
    public ArrayList<UserDto> getAllUsers() throws Exception {
        return null;
    }
}
