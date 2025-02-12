package lk.ijse.gdse71.finalproject.jotit.service.custom;



import lk.ijse.gdse71.finalproject.jotit.dto.RelationshipDto;
import lk.ijse.gdse71.finalproject.jotit.service.SuperService;

import java.util.List;

public interface RelationshipService extends SuperService {
    boolean save(RelationshipDto relationshipDto)throws Exception;
    RelationshipDto getRelationship(String id)throws Exception;
    List<RelationshipDto> getAllRelationships(String userId)throws Exception;
    boolean deleteRelationship(String id)throws Exception;
}
