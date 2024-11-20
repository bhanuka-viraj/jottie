package lk.ijse.gdse71.finalproject.jotit.model;

import lk.ijse.gdse71.finalproject.jotit.dto.UserDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserModel {
    public boolean save(UserDto userDto) throws Exception;
    public UserDto getUser(String usrname)throws Exception;
    public ArrayList<UserDto> getAllUsers()throws Exception;
}
