package lk.ijse.gdse71.finalproject.jotit.dao.custom.impl;

import lk.ijse.gdse71.finalproject.jotit.dao.custom.UserDAO;
import lk.ijse.gdse71.finalproject.jotit.entity.User;
import lk.ijse.gdse71.finalproject.jotit.entity.UserRelationship;
import lk.ijse.gdse71.finalproject.jotit.util.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean save(User user)throws Exception {
        return CrudUtil.execute("INSERT INTO user (user_id,user_name,password,email,dob,first_name,last_name) VALUES (?,?,?,?,?,?,?)",
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getDateOfBirth(),
                user.getFirstName(),
                user.getLastName()
                );
    }

    @Override
    public boolean update(User user) throws Exception {
        return CrudUtil.execute(
                "UPDATE user SET " +
                        "user_name = ?, " +
                        "password = ?, " +
                        "email = ?, " +
                        "dob = ?, " +
                        "first_name = ?, " +
                        "last_name = ? " +
                        "WHERE user_id = ?",
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getDateOfBirth(),
                user.getFirstName(),
                user.getLastName(),
                user.getId()
        );
    }

    @Override
    public User getUser(String username) throws Exception{
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM user WHERE user_name = ?",username);
        if(resultSet.next()){
            User user = new User();
            user.setId(resultSet.getString("user_id"));
            user.setUsername(resultSet.getString("user_name"));
            user.setPassword(resultSet.getString("password"));
            user.setEmail(resultSet.getString("email"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setCreatedDate(resultSet.getDate("created_at"));
            user.setUpdatedDate(resultSet.getDate("updated_at"));
            user.setDateOfBirth(resultSet.getDate("dob"));
            return user;
        }
        return null;
    }

    @Override
    public ArrayList<User> getAllUsers(String userId) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM user WHERE user_id != ?",userId);
        ArrayList<User> users = new ArrayList<>();
        while(resultSet.next()){
            User user = new User();
            user.setId(resultSet.getString("user_id"));
            user.setUsername(resultSet.getString("user_name"));
            user.setEmail(resultSet.getString("email"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setCreatedDate(resultSet.getDate("created_at"));
            user.setUpdatedDate(resultSet.getDate("updated_at"));
            user.setDateOfBirth(resultSet.getDate("dob"));
            users.add(user);
        }
        return users;
    }

    @Override
    public String getUserIdByUsername(String selectedUser) throws Exception{
        ResultSet rs = CrudUtil.execute("SELECT user_id FROM user WHERE user_name = ? ",selectedUser);
        String userId = null;
        while(rs.next()){
            userId= rs.getString("user_id");
        }

        return userId;
    }

    @Override
    public boolean saveUserRelationship(UserRelationship userRelationship) throws Exception {
        return CrudUtil.execute(
                "INSERT INTO user_relationship (user_relationship_id, user_id_1, user_id_2, relationship_id) VALUES (?, ?,?, ?)",
                userRelationship.getId(),
                userRelationship.getAddedById(),
                userRelationship.getUserId(),
                userRelationship.getRelationshipId()


        );
    }

    @Override
    public User getUserById(String userId) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM user WHERE user_id = ?", userId);
        if (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getString("user_id"));
            user.setUsername(resultSet.getString("user_name"));
            user.setEmail(resultSet.getString("email"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setCreatedDate(resultSet.getDate("created_at"));
            user.setUpdatedDate(resultSet.getDate("updated_at"));
            user.setDateOfBirth(resultSet.getDate("dob"));
            return user;
        }
        return null;
    }
}
