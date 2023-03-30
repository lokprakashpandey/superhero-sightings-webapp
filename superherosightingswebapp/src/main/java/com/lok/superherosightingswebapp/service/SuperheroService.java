/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-29
 * purpose: Superhero service
 */
package com.lok.superherosightingswebapp.service;

import com.lok.superherosightingswebapp.dto.Location;
import com.lok.superherosightingswebapp.dto.Organization;
import com.lok.superherosightingswebapp.dto.Superhero;
import java.util.List;

/**
 *
 * @author root
 */
public interface SuperheroService {

    Superhero getSuperheroById(int id);

    List<Superhero> getAllSuperheroes();

    Superhero addSuperhero(Superhero superhero);

    void updateSuperhero(Superhero superhero);

    void deleteSuperheroById(int id);

    List<Superhero> getSuperheroesForOrganization(Organization organization);

    List<Superhero> getSuperheroesForLocation(Location location);
}
