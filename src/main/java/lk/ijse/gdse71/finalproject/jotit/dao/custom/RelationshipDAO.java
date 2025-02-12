package lk.ijse.gdse71.finalproject.jotit.dao.custom;


import lk.ijse.gdse71.finalproject.jotit.dao.SuperDao;
import lk.ijse.gdse71.finalproject.jotit.entity.Relationship;

import java.util.List;

public interface RelationshipDAO extends SuperDao {
    boolean save(Relationship relationshipDto)throws Exception;
    Relationship getRelationship(String id)throws Exception;
    List<Relationship> getAllRelationships(String userId)throws Exception;
    boolean deleteRelationship(String id)throws Exception;
}
