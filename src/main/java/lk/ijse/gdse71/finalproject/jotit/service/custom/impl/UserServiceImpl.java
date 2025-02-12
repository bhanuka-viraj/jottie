package lk.ijse.gdse71.finalproject.jotit.service.custom.impl;

import lk.ijse.gdse71.finalproject.jotit.dao.custom.UserDAO;
import lk.ijse.gdse71.finalproject.jotit.dao.custom.impl.UserDAOImpl;
import lk.ijse.gdse71.finalproject.jotit.dto.UserDto;
import lk.ijse.gdse71.finalproject.jotit.dto.UserRelationshipDto;
import lk.ijse.gdse71.finalproject.jotit.entity.User;
import lk.ijse.gdse71.finalproject.jotit.entity.UserRelationship;
import lk.ijse.gdse71.finalproject.jotit.service.custom.UserService;

import java.util.ArrayList;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO = new UserDAOImpl();
    @Override
    public boolean save(UserDto user) throws Exception {
        return userDAO.save(new User(user.getId(),user.getUsername(),user.getFirstName(),user.getLastName(),user.getEmail(),user.getPassword(),user.getDateOfBirth(),user.getCreatedDate(),user.getUpdatedDate()));
    }

    @Override
    public boolean update(UserDto user) throws Exception {
        return userDAO.update(new User(user.getId(),user.getUsername(),user.getFirstName(),user.getLastName(),user.getEmail(),user.getPassword(),user.getDateOfBirth(),user.getCreatedDate(),user.getUpdatedDate()));
    }

    @Override
    public UserDto getUser(String usrname) throws Exception {
        User user = userDAO.getUser(usrname);
        return new UserDto(user.getId(),user.getUsername(),user.getFirstName(),user.getLastName(),user.getEmail(),user.getPassword(),user.getDateOfBirth(),user.getCreatedDate(),user.getUpdatedDate());
    }

    @Override
    public ArrayList<UserDto> getAllUsers(String userId) throws Exception {
        return userDAO.getAllUsers(userId).stream().map(u -> new UserDto(u.getId(),u.getUsername(),u.getFirstName(),u.getLastName(),u.getEmail(),u.getPassword(),u.getDateOfBirth(),u.getCreatedDate(),u.getUpdatedDate())).collect(java.util.stream.Collectors.toCollection(ArrayList::new));
    }

    @Override
    public String getUserIdByUsername(String selectedUser) throws Exception {
        return userDAO.getUserIdByUsername(selectedUser);
    }

    @Override
    public boolean saveUserRelationship(UserRelationshipDto userRelationship) throws Exception {
        return userDAO.saveUserRelationship(new UserRelationship(userRelationship.getId(),userRelationship.getAddedById(),userRelationship.getUserId(),userRelationship.getRelationshipId()));
    }

    @Override
    public UserDto getUserById(String userBy) throws Exception {
        User user = userDAO.getUserById(userBy);
        return new UserDto(user.getId(),user.getUsername(),user.getFirstName(),user.getLastName(),user.getEmail(),user.getPassword(),user.getDateOfBirth(),user.getCreatedDate(),user.getUpdatedDate());
    }
}
