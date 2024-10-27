package lk.ijse.gdse71.finalproject.jotit.model;

import lk.ijse.gdse71.finalproject.jotit.dto.UserDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserModel {
    public void save(UserDto userDto) throws SQLException, ClassNotFoundException;
    public UserDto getUser();
    public ArrayList<UserDto> getAllUsers();
}
