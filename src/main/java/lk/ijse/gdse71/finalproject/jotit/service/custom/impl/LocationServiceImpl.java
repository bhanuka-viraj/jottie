package lk.ijse.gdse71.finalproject.jotit.service.custom.impl;

import lk.ijse.gdse71.finalproject.jotit.dao.custom.LocationDAO;
import lk.ijse.gdse71.finalproject.jotit.dao.custom.impl.LocationDAOImpl;
import lk.ijse.gdse71.finalproject.jotit.dto.LocationDto;
import lk.ijse.gdse71.finalproject.jotit.entity.Location;
import lk.ijse.gdse71.finalproject.jotit.service.custom.LocationService;

import java.util.List;

public class LocationServiceImpl implements LocationService {
    private final LocationDAO locationDAO = new LocationDAOImpl();
    @Override
    public boolean saveLocation(LocationDto locationDto) throws Exception {
        return locationDAO.saveLocation(new Location(locationDto.getId(),locationDto.getDescription(),locationDto.getCreatedBy()));
    }

    @Override
    public LocationDto getLocation(String id, String userId) throws Exception {
        Location location = locationDAO.getLocation(id,userId);
        return new LocationDto(location.getId(),location.getDescription(),location.getCreatedBy());
    }

    @Override
    public List<LocationDto> getAllLocations(String userId) throws Exception {

        return locationDAO.getAllLocations(userId).stream().map(
                l -> new LocationDto(l.getId(),
                        l.getDescription(),
                        l.getCreatedBy()))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public boolean deleteLocation(LocationDto locationDto) throws Exception {
        return locationDAO.deleteLocation(new Location(locationDto.getId(),locationDto.getDescription(),locationDto.getCreatedBy()));
    }
}
