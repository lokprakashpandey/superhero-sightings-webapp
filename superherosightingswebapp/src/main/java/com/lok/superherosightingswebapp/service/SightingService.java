/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-29
 * purpose: Sighting Service
 */

package com.lok.superherosightingswebapp.service;

import com.lok.superherosightingswebapp.dto.Sighting;
import java.time.LocalDate;
import java.util.List;


public interface SightingService {
    
    Sighting getSightingById(int id);

    List<Sighting> getAllSightings();

    Sighting addSighting(Sighting sighting);

    void updateSighting(Sighting sighting);

    void deleteSightingById(int id);

    /**
     * Gets all sightings for a particular date
     */
    List<Sighting> getSightingsForParticularDate(LocalDate date);
    
    List<Sighting> getLatestTenSightings();

}
