/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-29
 * purpose: Implementation of Superhero Service
 */

package com.lok.superherosightingswebapp.service;

import com.lok.superherosightingswebapp.dao.SuperheroDao;
import com.lok.superherosightingswebapp.dto.Location;
import com.lok.superherosightingswebapp.dto.Organization;
import com.lok.superherosightingswebapp.dto.Superhero;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuperheroServiceImpl implements SuperheroService {
    
    @Autowired
    private SuperheroDao superheroDao;

    @Override
    public Superhero getSuperheroById(int id) {
        return superheroDao.getSuperheroById(id);
    }

    @Override
    public List<Superhero> getAllSuperheroes() {
        return superheroDao.getAllSuperheroes();
    }

    @Override
    public Superhero addSuperhero(Superhero superhero) {
        return superheroDao.addSuperhero(superhero);
    }

    @Override
    public void updateSuperhero(Superhero superhero) {
        superheroDao.updateSuperhero(superhero);
    }

    @Override
    public void deleteSuperheroById(int id) {
        superheroDao.deleteSuperheroById(id);
    }

    @Override
    public List<Superhero> getSuperheroesForOrganization(Organization organization) {
        return superheroDao.getSuperheroesForOrganization(organization);
    }

    @Override
    public List<Superhero> getSuperheroesForLocation(Location location) {
        return superheroDao.getSuperheroesForLocation(location);
    }
       
}
