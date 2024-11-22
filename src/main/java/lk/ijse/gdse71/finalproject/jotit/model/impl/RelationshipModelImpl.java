package lk.ijse.gdse71.finalproject.jotit.model.impl;

import lk.ijse.gdse71.finalproject.jotit.controller.LoginController;
import lk.ijse.gdse71.finalproject.jotit.dto.RelationshipDto;
import lk.ijse.gdse71.finalproject.jotit.model.RelationshipModel;
import lk.ijse.gdse71.finalproject.jotit.util.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RelationshipModelImpl implements RelationshipModel {
    @Override
    public boolean save(RelationshipDto relationshipDto) throws Exception {
        return CrudUtil.execute("INSERT INTO relationship VALUES (?,?,?)",
                relationshipDto.getId(),
                relationshipDto.getType(),relationshipDto.getCreatedBy());
    }
    @Override
    public RelationshipDto getRelationship(String id) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM relationship WHERE relationship_id = ?", id);
        if (resultSet.next()) {
            return new RelationshipDto(
                    resultSet.getString("relationship_id"),
                    resultSet.getString("type"),
                    resultSet.getString("created_by")
            );
        }
        return null;
    }

    @Override
    public List<RelationshipDto> getAllRelationships(String userId) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM relationship WHERE created_by = ?", userId);
        List<RelationshipDto> relationshipDtos = new ArrayList<>();
        while (resultSet.next()) {
            relationshipDtos.add(new RelationshipDto(
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
