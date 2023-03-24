/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-10
 * purpose: Dao for Sighting entity
 */
package com.lok.superherosightingswebapp.dao;

import com.lok.superherosightingswebapp.dto.Sighting;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author root
 */
public interface SightingDao {

    Sighting getSightingById(int id);

    List<Sighting> getAllSightings();

    Sighting addSighting(Sighting sighting);

    void updateSighting(Sighting sighting);

    void deleteSightingById(int id);

    /**
     * Gets all sightings for a particular date
     */
    List<Sighting> getSightingsForParticularDate(LocalDate date);
}
