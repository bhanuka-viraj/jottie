package lk.ijse.gdse71.finalproject.jotit.service.custom;

import lk.ijse.gdse71.finalproject.jotit.dto.UserDto;
import lk.ijse.gdse71.finalproject.jotit.dto.UserRelationshipDto;
import lk.ijse.gdse71.finalproject.jotit.service.SuperService;

import java.util.ArrayList;

public interface UserService extends SuperService {
    boolean save(UserDto user) throws Exception;

    boolean update(UserDto user) throws Exception;

    UserDto getUser(String usrname) throws Exception;

    ArrayList<UserDto> getAllUsers(String userId) throws Exception;

    String getUserIdByUsername(String selectedUser) throws Exception;

    boolean saveUserRelationship(UserRelationshipDto userRelationship) throws Exception;

    UserDto getUserById(String userBy) throws Exception;
}
