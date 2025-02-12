package lk.ijse.gdse71.finalproject.jotit.dao.custom.impl;

import lk.ijse.gdse71.finalproject.jotit.dao.custom.RelationshipDAO;
import lk.ijse.gdse71.finalproject.jotit.entity.Relationship;
import lk.ijse.gdse71.finalproject.jotit.util.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RelationshipDAOImpl implements RelationshipDAO {
    @Override
    public boolean save(Relationship relationship) throws Exception {
        return CrudUtil.execute("INSERT INTO relationship VALUES (?,?,?)",
                relationship.getId(),
                relationship.getType(),relationship.getCreatedBy());
    }
    @Override
    public Relationship getRelationship(String id) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM relationship WHERE relationship_id = ?", id);
        if (resultSet.next()) {
            return new Relationship(
                    resultSet.getString("relationship_id"),
                    resultSet.getString("type"),
                    resultSet.getString("created_by")
            );
        }
        return null;
    }

    @Override
    public List<Relationship> getAllRelationships(String userId) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM relationship WHERE created_by = ?", userId);
        List<Relationship> relationshipDtos = new ArrayList<>();
        while (resultSet.next()) {
            relationshipDtos.add(new Relationship(
                    resultSet.getString("relationship_id"),
                    resultSet.getString("type"),
                    resultSet.getString("created_by")
            ));
        }
        return relationshipDtos;
    }

    @Override
    public boolean deleteRelationship(String id) throws Exception {
        return CrudUtil.execute("DELETE FROM relationship WHERE relationship_id = ?", id);
    }

}
