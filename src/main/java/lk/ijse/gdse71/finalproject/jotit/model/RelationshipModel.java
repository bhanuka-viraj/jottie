package lk.ijse.gdse71.finalproject.jotit.model;

import lk.ijse.gdse71.finalproject.jotit.dto.RelationshipDto;

import java.util.List;

public interface RelationshipModel {
    boolean save(RelationshipDto relationshipDto)throws Exception;
    RelationshipDto getRelationship(String id)throws Exception;
    List<RelationshipDto> getAllRelationships(String userId)throws Exception;
    boolean deleteRelationship(String id)throws Exception;
}
