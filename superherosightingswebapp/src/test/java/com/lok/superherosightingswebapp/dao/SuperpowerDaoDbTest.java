/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-22
 * purpose: SuperpowerDaoDbTest
 */
package com.lok.superherosightingswebapp.dao;

import com.lok.superherosightingswebapp.dto.Location;
import com.lok.superherosightingswebapp.dto.Organization;
import com.lok.superherosightingswebapp.dto.Sighting;
import com.lok.superherosightingswebapp.dto.Superhero;
import com.lok.superherosightingswebapp.dto.Superpower;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author root
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SuperpowerDaoDbTest {

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    SuperheroDao superheroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SuperpowerDao superpowerDao;

    public SuperpowerDaoDbTest() {
    }

    @Before
    public void setUp() {
        List<Organization> organizations = organizationDao.getAllOrganizations();
        for (Organization organization : organizations) {
            organizationDao.deleteOrganizationById(organization.getId());
        }

        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.deleteSightingById(sighting.getId());
        }

        List<Superhero> superheroes = superheroDao.getAllSuperheroes();
        for (Superhero superhero : superheroes) {
            superheroDao.deleteSuperheroById(superhero.getId());
        }

        List<Location> locations = locationDao.getAllLocations();
        for (Location location : locations) {
            locationDao.deleteLocationById(location.getId());
        }

        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        for (Superpower superpower : superpowers) {
            superpowerDao.deleteSuperpowerById(superpower.getId());
        }
    }

    @Test
    public void testAddAndGetSuperpower() {
        Superpower superpower = new Superpower();
        superpower.setName("Webbing");
        superpower.setDescription("The ability to shoot webs whenever in need");
        superpower = superpowerDao.addSuperpower(superpower);

        Superpower fromDao = superpowerDao.getSuperpowerById(superpower.getId());
        assertEquals(superpower, fromDao);

    }

    @Test
    public void testGetAllSuperpowers() {
        Superpower superpower = new Superpower();
        superpower.setName("Webbing");
        superpower.setDescription("The ability to shoot webs whenever in need");
        superpower = superpowerDao.addSuperpower(superpower);

        Superpower superpower2 = new Superpower();
        superpower2.setName("Superstrength");
        superpower2.setDescription("The unhuman ability to become very powerful and strong");
        superpower2 = superpowerDao.addSuperpower(superpower2);

        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();

        assertEquals(2, superpowers.size());
        assertTrue(superpowers.contains(superpower));
        assertTrue(superpowers.contains(superpower2));

    }
    
    @Test
    public void testUpdateSuperpower() {
        Superpower superpower = new Superpower();
        superpower.setName("Webbing");
        superpower.setDescription("The ability to shoot webs whenever in need");
        superpower = superpowerDao.addSuperpower(superpower);

        Superpower fromDao = superpowerDao.getSuperpowerById(superpower.getId());
        assertEquals(superpower, fromDao);

        superpower.setName("Web-shooting");
        superpowerDao.updateSuperpower(superpower);
        assertNotEquals(superpower, fromDao);
        
        fromDao = superpowerDao.getSuperpowerById(superpower.getId());
        assertEquals(superpower, fromDao);
        
    }
    
    @Test
    public void testDeleteSuperpowerById() {
        Superpower superpower = new Superpower();
        superpower.setName("Webbing");
        superpower.setDescription("The ability to shoot webs whenever in need");
        superpower = superpowerDao.addSuperpower(superpower);
        
        Location location = new Location();
        location.setName("Ridgeview Park");
        location.setDescription("A big park in New York downtown");
        location.setAddress("New York, NY 10002");
        location.setLatitude(new BigDecimal("40.7829"));
        location.setLongitude(new BigDecimal("-73.9654"));
        location = locationDao.addLocation(location);
        
        Organization organization = new Organization();
        organization.setName("Avengers");
        organization.setDescription("An entertainment organization dealing with fictional superheroes");
        organization.setAddress("New York City, New York, United States, 10001");
        organization.setContactNumber("2125764000");
        organization = organizationDao.addOrganization(organization);
        
        Superhero superhero = new Superhero();
        superhero.setName("Spiderman");
        superhero.setDescription("An ordinary young man in his 20s living in the city");
        superhero.setSuperpower(superpower);
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);
        superhero.setOrganizations(organizations);
        superhero = superheroDao.addSuperhero(superhero);
        
        Sighting sighting = new Sighting();
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sighting.setSuperhero(superhero);
        sightingDao.addSighting(sighting);
        
        Superpower fromDao = superpowerDao.getSuperpowerById(superpower.getId());
        assertEquals(superpower, fromDao);

        superpowerDao.deleteSuperpowerById(superpower.getId());
        fromDao = superpowerDao.getSuperpowerById(superpower.getId());
        assertNull(fromDao);
    }

}
