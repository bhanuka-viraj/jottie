package lk.ijse.gdse71.finalproject.jotit.service.custom;

import lk.ijse.gdse71.finalproject.jotit.dto.LocationDto;
import lk.ijse.gdse71.finalproject.jotit.service.SuperService;

import java.util.List;

public interface LocationService extends SuperService {
    public boolean saveLocation(LocationDto locationDto) throws Exception;
    public LocationDto getLocation(String id,String userId) throws Exception;
    public List<LocationDto> getAllLocations(String userId) throws Exception;
    public boolean deleteLocation(LocationDto locationDto) throws Exception;
}
