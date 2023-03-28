/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-28
 * purpose: Service interface for LoactionDao
 */
package com.lok.superherosightingswebapp.service;

import com.lok.superherosightingswebapp.dto.Location;
import java.util.List;

/**
 *
 * @author root
 */
public interface LocationService {
    
    Location getLocationById(int id);

    List<Location> getAllLocations();

    Location addLocation(Location location);

    void updateLocation(Location location);

    void deleteLocationById(int id);
    
}
