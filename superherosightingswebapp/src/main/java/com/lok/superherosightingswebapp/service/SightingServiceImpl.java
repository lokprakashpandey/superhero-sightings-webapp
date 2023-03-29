/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-29
 * purpose: Implementation of Sighting Service
 */

package com.lok.superherosightingswebapp.service;

import com.lok.superherosightingswebapp.dao.SightingDao;
import com.lok.superherosightingswebapp.dto.Sighting;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SightingServiceImpl implements SightingService {

    @Autowired
    private SightingDao sightingDao;
    
    @Override
    public Sighting getSightingById(int id) {
        return sightingDao.getSightingById(id);
    }

    @Override
    public List<Sighting> getAllSightings() {
        return sightingDao.getAllSightings();
    }

    @Override
    public Sighting addSighting(Sighting sighting) {
        return sightingDao.addSighting(sighting);
    }

    @Override
    public void updateSighting(Sighting sighting) {
        sightingDao.updateSighting(sighting);
    }

    @Override
    public void deleteSightingById(int id) {
        sightingDao.deleteSightingById(id);
    }

    @Override
    public List<Sighting> getSightingsForParticularDate(LocalDate date) {
        return sightingDao.getSightingsForParticularDate(date);
    }
  
}
