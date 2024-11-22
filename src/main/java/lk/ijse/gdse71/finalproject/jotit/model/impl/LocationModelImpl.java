package lk.ijse.gdse71.finalproject.jotit.model.impl;

import lk.ijse.gdse71.finalproject.jotit.dto.LocationDto;
import lk.ijse.gdse71.finalproject.jotit.model.LocationModel;
import lk.ijse.gdse71.finalproject.jotit.util.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LocationModelImpl implements LocationModel {
    @Override
    public boolean saveLocation(LocationDto locationDto) throws Exception {
        return CrudUtil.execute("INSERT INTO location VALUES (?, ?,?)",
                locationDto.getId(),
                locationDto.getDescription(),
                locationDto.getCreatedBy());
    }

    @Override
    public LocationDto getLocation(String id,String userId) throws Exception {
        ResultSet rs = CrudUtil.execute("SELECT * FROM location WHERE location_id = ? AND created_by = ?", id,userId);
        if (rs.next()) {
            return new LocationDto(rs.getString("location_id"), rs.getString("description"), rs.getString("created_by"));
        }
        return null;
    }

    @Override
    public List<LocationDto> getAllLocations(String userId) throws Exception {
        ResultSet rs = CrudUtil.execute("SELECT * FROM location WHERE created_by = ?", userId);
        List<LocationDto> locations = new ArrayList<>();
        while (rs.next()) {
            locations.add(new LocationDto(rs.getString("location_id"), rs.getString("description"), rs.getString("created_by")));
        }
        return locations;
    }

    @Override
    public boolean deleteLocation(LocationDto locationDto) throws Exception {
        return CrudUtil.execute("DELETE FROM location WHERE location_id = ?", locationDto.getId());
    }
}
