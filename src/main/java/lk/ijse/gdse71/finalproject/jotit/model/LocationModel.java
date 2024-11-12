package lk.ijse.gdse71.finalproject.jotit.model;

import lk.ijse.gdse71.finalproject.jotit.dto.LocationDto;

import java.util.List;

public interface LocationModel {
    public boolean saveLocation(LocationDto locationDto) throws Exception;
    public LocationDto getLocation(String id) throws Exception;
    public List<LocationDto> getAllLocations() throws Exception;
    public boolean deleteLocation(LocationDto locationDto) throws Exception;
}
