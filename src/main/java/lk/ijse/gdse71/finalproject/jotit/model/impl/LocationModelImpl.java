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
        return CrudUtil.execute("INSERT INTO location VALUES (?, ?)",
                locationDto.getId(),
                locationDto.getDescription());
    }

    @Override
    public LocationDto getLocation(String id) throws Exception {
        ResultSet rs = CrudUtil.execute("SELECT * FROM location WHERE location_id = ?", id);
        if (rs.next()) {
            return new LocationDto(rs.getString("location_id"), rs.getString("description"));
        }
        return null;
    }

    @Override
    public List<LocationDto> getAllLocations() throws Exception {
        ResultSet rs = CrudUtil.execute("SELECT * FROM location");
        List<LocationDto> locations = new ArrayList<>();
        while (rs.next()) {
            locations.add(new LocationDto(rs.getString("location_id"), rs.getString("description")));
        }
        return locations;
    }

    @Override
    public boolean deleteLocation(LocationDto locationDto) throws Exception {
        return CrudUtil.execute("DELETE FROM location WHERE location_id = ?", locationDto.getId());
    }
}
