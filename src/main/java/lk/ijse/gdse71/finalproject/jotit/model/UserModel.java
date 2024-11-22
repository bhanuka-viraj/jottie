package lk.ijse.gdse71.finalproject.jotit.model;

import lk.ijse.gdse71.finalproject.jotit.dto.UserDto;
import lk.ijse.gdse71.finalproject.jotit.dto.UserRelationshipDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserModel {
    boolean save(UserDto userDto) throws Exception;

    boolean update(UserDto userDto) throws Exception;

    UserDto getUser(String usrname) throws Exception;

    ArrayList<UserDto> getAllUsers(String userId) throws Exception;

    String getUserIdByUsername(String selectedUser) throws Exception;

    boolean saveUserRelationship(UserRelationshipDto userRelationshipDto) throws Exception;

    UserDto getUserById(String userBy) throws Exception;
}
