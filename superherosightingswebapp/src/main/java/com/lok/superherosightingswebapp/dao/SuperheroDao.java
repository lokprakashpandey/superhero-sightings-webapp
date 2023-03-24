/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-10
 * purpose: SuperheroDao interface
 */
package com.lok.superherosightingswebapp.dao;

import com.lok.superherosightingswebapp.dto.Location;
import com.lok.superherosightingswebapp.dto.Organization;
import com.lok.superherosightingswebapp.dto.Superhero;
import java.util.List;

/**
 *
 * @author root
 */
public interface SuperheroDao {

    Superhero getSuperheroById(int id);

    List<Superhero> getAllSuperheroes();

    Superhero addSuperhero(Superhero superhero);

    void updateSuperhero(Superhero superhero);

    void deleteSuperheroById(int id);

    /**
     * Gets all members(superheroes) for an organization
     */
    List<Superhero> getSuperheroesForOrganization(Organization organization);

    /**
     * Gets all superheroes sighted at a particular location
     */
    List<Superhero> getSuperheroesForLocation(Location location);

}
