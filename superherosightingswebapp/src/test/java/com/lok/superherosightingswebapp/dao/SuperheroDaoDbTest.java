/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-22
 * purpose: Test class for SuperheroDaoDb
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
public class SuperheroDaoDbTest {

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

    public SuperheroDaoDbTest() {
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
    public void testGetAndAddSuperhero() {
        Superpower superpower = new Superpower();
        superpower.setName("Webbing");
        superpower.setDescription("The ability to shoot webs whenever in need");
        superpower = superpowerDao.addSuperpower(superpower);

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

        Superhero fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertEquals(superhero, fromDao);
    }

    @Test
    public void testGetAllSuperheroes() {
        Superpower superpower = new Superpower();
        superpower.setName("Webbing");
        superpower.setDescription("The ability to shoot webs whenever in need");
        superpower = superpowerDao.addSuperpower(superpower);

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

        Superpower superpower2 = new Superpower();
        superpower2.setName("Superstrength");
        superpower2.setDescription("The unhuman ability to become very powerful and strong");
        superpower2 = superpowerDao.addSuperpower(superpower2);

        Organization organization2 = new Organization();
        organization2.setName("Warner Bros");
        organization2.setDescription("An entertainment conglomerate");
        organization2.setAddress("New York City, New York, United States, 10002");
        organization2.setContactNumber("8189546777");
        organization2 = organizationDao.addOrganization(organization2);

        Superhero superhero2 = new Superhero();
        superhero2.setName("Hulk");
        superhero2.setDescription("A doctor who got exposed to radiation and became greenish and huge");
        superhero2.setSuperpower(superpower2);
        List<Organization> organizations2 = new ArrayList<>();
        organizations2.add(organization2);
        superhero2.setOrganizations(organizations2);
        superhero2 = superheroDao.addSuperhero(superhero2);

        List<Superhero> superheroes = superheroDao.getAllSuperheroes();

        assertEquals(2, superheroes.size());
        assertTrue(superheroes.contains(superhero));
        assertTrue(superheroes.contains(superhero2));
    }

    @Test
    public void testUpdateSuperhero() {
        Superpower superpower = new Superpower();
        superpower.setName("Webbing");
        superpower.setDescription("The ability to shoot webs whenever in need");
        superpower = superpowerDao.addSuperpower(superpower);

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

        Superhero fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertEquals(superhero, fromDao);

        superhero.setName("New Spiderman");
        superheroDao.updateSuperhero(superhero);

        assertNotEquals(superhero, fromDao);

        fromDao = superheroDao.getSuperheroById(superhero.getId());

        assertEquals(superhero, fromDao);
    }

    @Test
    public void testDeleteSuperheroById() {
        Superpower superpower = new Superpower();
        superpower.setName("Webbing");
        superpower.setDescription("The ability to shoot webs whenever in need");
        superpower = superpowerDao.addSuperpower(superpower);

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

        Location location = new Location();
        location.setName("Ridgeview Park");
        location.setDescription("A big park in New York downtown");
        location.setAddress("New York, NY 10002");
        location.setLatitude(new BigDecimal("40.7829"));
        location.setLongitude(new BigDecimal("-73.9654"));
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sighting.setSuperhero(superhero);
        sightingDao.addSighting(sighting);

        Superhero fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertEquals(superhero, fromDao);

        superheroDao.deleteSuperheroById(superhero.getId());
        fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertNull(fromDao);

    }

    //Test cases: Two superheroes in one location | No superhero for a location
    @Test
    public void testGetSuperheroesForLocation() {
        Superpower superpower = new Superpower();
        superpower.setName("Webbing");
        superpower.setDescription("The ability to shoot webs whenever in need");
        superpower = superpowerDao.addSuperpower(superpower);

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

        Superpower superpower2 = new Superpower();
        superpower2.setName("Superstrength");
        superpower2.setDescription("The unhuman ability to become very powerful and strong");
        superpower2 = superpowerDao.addSuperpower(superpower2);

        Organization organization2 = new Organization();
        organization2.setName("Warner Bros");
        organization2.setDescription("An entertainment conglomerate");
        organization2.setAddress("New York City, New York, United States, 10002");
        organization2.setContactNumber("8189546777");
        organization2 = organizationDao.addOrganization(organization2);
        
        Superhero superhero2 = new Superhero();
        superhero2.setName("Hulk");
        superhero2.setDescription("A doctor who got exposed to radiation and became greenish and huge");
        superhero2.setSuperpower(superpower2);
        List<Organization> organizations2 = new ArrayList<>();
        organizations2.add(organization2);
        superhero2.setOrganizations(organizations2);
        superhero2 = superheroDao.addSuperhero(superhero2);

        Location location = new Location();
        location.setName("Ridgeview Park");
        location.setDescription("A big park in New York downtown");
        location.setAddress("New York, NY 10002");
        location.setLatitude(new BigDecimal("40.7829"));
        location.setLongitude(new BigDecimal("-73.9654"));
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setLocation(location);
        sighting.setSuperhero(superhero);
        sightingDao.addSighting(sighting);

        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDate.now());
        sighting2.setLocation(location); //same location
        sighting2.setSuperhero(superhero2);
        sightingDao.addSighting(sighting2);

        List<Superhero> superheroes = superheroDao.getSuperheroesForLocation(location);
        assertEquals(2, superheroes.size());
        assertTrue(superheroes.contains(superhero));
        assertTrue(superheroes.contains(superhero2));

        Location location2 = new Location();
        location2.setName("Statue of Liberty");
        location2.setDescription("A national monument in New York Harbor");
        location2.setAddress("Liberty Island, New York, NY 10004");
        location2.setLatitude(new BigDecimal("40.6892"));
        location2.setLongitude(new BigDecimal("-74.0445"));
        location2 = locationDao.addLocation(location2);

        superheroes = superheroDao.getSuperheroesForLocation(location2);
        assertEquals(0, superheroes.size());
    }
    
    //Case: Two Superheroes in an Organization
    @Test
    public void testGetSuperheroesForOrganization() {
        Superpower superpower = new Superpower();
        superpower.setName("Webbing");
        superpower.setDescription("The ability to shoot webs whenever in need");
        superpower = superpowerDao.addSuperpower(superpower);

        Superpower superpower2 = new Superpower();
        superpower2.setName("Superstrength");
        superpower2.setDescription("The unhuman ability to become very powerful and strong");
        superpower2 = superpowerDao.addSuperpower(superpower2);

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
        
        Superhero superhero2 = new Superhero();
        superhero2.setName("Hulk");
        superhero2.setDescription("A doctor who got exposed to radiation and became greenish and huge");
        superhero2.setSuperpower(superpower2);
        superhero2.setOrganizations(organizations);
        superhero2 = superheroDao.addSuperhero(superhero2);
        
        List<Superhero> fromDao = superheroDao.getSuperheroesForOrganization(organization);
        assertEquals(2, fromDao.size());
        assertTrue(fromDao.contains(superhero));
        assertTrue(fromDao.contains(superhero2));
    }

}
