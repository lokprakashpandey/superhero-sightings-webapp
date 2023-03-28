/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-28
 * purpose: Implementation for Location service
 */

package com.lok.superherosightingswebapp.service;

import com.lok.superherosightingswebapp.dao.LocationDao;
import com.lok.superherosightingswebapp.dto.Location;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationDao locationDao;

    @Override
    public Location getLocationById(int id) {
        return locationDao.getLocationById(id);
    }

    @Override
    public List<Location> getAllLocations() {
        return locationDao.getAllLocations();
    }

    @Override
    public Location addLocation(Location location) {
        return locationDao.addLocation(location);
    }

    @Override
    public void updateLocation(Location location) {
        locationDao.updateLocation(location);
    }

    @Override
    public void deleteLocationById(int id) {
        locationDao.deleteLocationById(id);
    }
}
