/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-10
 * purpose: Dao interface for Location entity
 */
package com.lok.superherosightingswebapp.dao;

import com.lok.superherosightingswebapp.dto.Location;
import com.lok.superherosightingswebapp.dto.Superhero;
import java.util.List;

public interface LocationDao {

    Location getLocationById(int id);

    List<Location> getAllLocations();

    Location addLocation(Location location);

    void updateLocation(Location location);

    void deleteLocationById(int id);

    /**
     * Returns a list of all the locations where a particular superhero has been
     * seen
     *
     * @param Superhero superhero for whom to get all locations
     * @return List<Location> contains all locations
     */
    List<Location> getLocationsForSuperhero(Superhero superhero);

}
