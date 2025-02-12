package lk.ijse.gdse71.finalproject.jotit.dao.custom;

import lk.ijse.gdse71.finalproject.jotit.dao.SuperDao;
import lk.ijse.gdse71.finalproject.jotit.entity.Location;

import java.util.List;

public interface LocationDAO extends SuperDao {
    public boolean saveLocation(Location locationDto) throws Exception;
    public Location getLocation(String id,String userId) throws Exception;
    public List<Location> getAllLocations(String userId) throws Exception;
    public boolean deleteLocation(Location locationDto) throws Exception;
}
