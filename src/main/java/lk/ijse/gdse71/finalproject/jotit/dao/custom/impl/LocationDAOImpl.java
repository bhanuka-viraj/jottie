package lk.ijse.gdse71.finalproject.jotit.dao.custom.impl;

import lk.ijse.gdse71.finalproject.jotit.dao.custom.LocationDAO;
import lk.ijse.gdse71.finalproject.jotit.entity.Location;
import lk.ijse.gdse71.finalproject.jotit.util.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LocationDAOImpl implements LocationDAO {
    @Override
    public boolean saveLocation(Location location) throws Exception {
        return CrudUtil.execute("INSERT INTO location VALUES (?, ?,?)",
                location.getId(),
                location.getDescription(),
                location.getCreatedBy());
    }

    @Override
    public Location getLocation(String id,String userId) throws Exception {
        ResultSet rs = CrudUtil.execute("SELECT * FROM location WHERE location_id = ? AND created_by = ?", id,userId);
        if (rs.next()) {
            return new Location(rs.getString("location_id"), rs.getString("description"), rs.getString("created_by"));
        }
        return null;
    }

    @Override
    public List<Location> getAllLocations(String userId) throws Exception {
        ResultSet rs = CrudUtil.execute("SELECT * FROM location WHERE created_by = ?", userId);
        List<Location> locations = new ArrayList<>();
        while (rs.next()) {
            locations.add(new Location(rs.getString("location_id"), rs.getString("description"), rs.getString("created_by")));
        }
        return locations;
    }

    @Override
    public boolean deleteLocation(Location location) throws Exception {
        return CrudUtil.execute("DELETE FROM location WHERE location_id = ?", location.getId());
    }
}
