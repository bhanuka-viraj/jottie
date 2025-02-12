package lk.ijse.gdse71.finalproject.jotit.service.custom.impl;

import lk.ijse.gdse71.finalproject.jotit.dao.custom.RelationshipDAO;
import lk.ijse.gdse71.finalproject.jotit.dao.custom.impl.RelationshipDAOImpl;
import lk.ijse.gdse71.finalproject.jotit.dto.RelationshipDto;
import lk.ijse.gdse71.finalproject.jotit.entity.Relationship;
import lk.ijse.gdse71.finalproject.jotit.service.custom.RelationshipService;

import java.util.List;

public class RelationshipServiceImpl implements RelationshipService {
    private final RelationshipDAO relationshipDAO = new RelationshipDAOImpl();
    @Override
    public boolean save(RelationshipDto relationshipDto) throws Exception {
        return relationshipDAO.save(new Relationship(relationshipDto.getId(),relationshipDto.getType(),relationshipDto.getCreatedBy()));
    }

    @Override
    public RelationshipDto getRelationship(String id) throws Exception {
        Relationship relationship = relationshipDAO.getRelationship(id);
        return new RelationshipDto(relationship.getId(),relationship.getType(),relationship.getCreatedBy());
    }

    @Override
    public List<RelationshipDto> getAllRelationships(String userId) throws Exception {
        return relationshipDAO.getAllRelationships(userId).stream().map(c -> new RelationshipDto(c.getId(), c.getType(), c.getCreatedBy())).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public boolean deleteRelationship(String id) throws Exception {
        return relationshipDAO.deleteRelationship(id);
    }
}
