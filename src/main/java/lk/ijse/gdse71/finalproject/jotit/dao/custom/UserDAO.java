package lk.ijse.gdse71.finalproject.jotit.dao.custom;

import lk.ijse.gdse71.finalproject.jotit.dao.SuperDao;
import lk.ijse.gdse71.finalproject.jotit.entity.User;
import lk.ijse.gdse71.finalproject.jotit.entity.UserRelationship;

import java.util.ArrayList;

public interface UserDAO extends SuperDao {
    boolean save(User user) throws Exception;

    boolean update(User user) throws Exception;

    User getUser(String usrname) throws Exception;

    ArrayList<User> getAllUsers(String userId) throws Exception;

    String getUserIdByUsername(String selectedUser) throws Exception;

    boolean saveUserRelationship(UserRelationship userRelationship) throws Exception;

    User getUserById(String userBy) throws Exception;
}
