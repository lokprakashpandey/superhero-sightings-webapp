/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-22
 * purpose: Test for OrganizationDaoDb
 */
package com.lok.superherosightingswebapp.dao;

import com.lok.superherosightingswebapp.dto.Location;
import com.lok.superherosightingswebapp.dto.Organization;
import com.lok.superherosightingswebapp.dto.Sighting;
import com.lok.superherosightingswebapp.dto.Superhero;
import com.lok.superherosightingswebapp.dto.Superpower;
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
public class OrganizationDaoDbTest {
    
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
    
    public OrganizationDaoDbTest() {
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
    public void testGetAndAddOrganization() {
        Organization organization = new Organization();
        organization.setName("Avengers");
        organization.setDescription("An entertainment organization dealing with fictional superheroes");
        organization.setAddress("New York City, New York, United States, 10001");
        organization.setContactNumber("2125764000");
        organization = organizationDao.addOrganization(organization);
        Organization fromDao = organizationDao.getOrganizationById(organization.getId());
        assertEquals(organization, fromDao);
    }
    
    @Test
    public void testGetAllOrganizations() {
        Organization organization = new Organization();
        organization.setName("Avengers");
        organization.setDescription("An entertainment organization dealing with fictional superheroes");
        organization.setAddress("New York City, New York, United States, 10001");
        organization.setContactNumber("2125764000");
        organization = organizationDao.addOrganization(organization);
        
        Organization organization2 = new Organization();
        organization2.setName("Warner Bros");
        organization2.setDescription("An entertainment conglomerate");
        organization2.setAddress("New York City, New York, United States, 10002");
        organization2.setContactNumber("8189546777");
        organization2 = organizationDao.addOrganization(organization2);
        
        List<Organization> organizations = organizationDao.getAllOrganizations();
        assertEquals(2, organizations.size());
        assertTrue(organizations.contains(organization));
        assertTrue(organizations.contains(organization2));
    }
    
    @Test
    public void testUpdateOrganization() {
        Organization organization = new Organization();
        organization.setName("Avengers");
        organization.setDescription("An entertainment organization dealing with fictional superheroes");
        organization.setAddress("New York City, New York, United States, 10001");
        organization.setContactNumber("2125764000");
        organization = organizationDao.addOrganization(organization);
        
        Organization fromDao = organizationDao.getOrganizationById(organization.getId());
        assertEquals(organization, fromDao);

        organization.setName("Marvel");
        organizationDao.updateOrganization(organization);
        assertNotEquals(organization, fromDao);

        fromDao = organizationDao.getOrganizationById(organization.getId());
        assertEquals(organization, fromDao);
    }
    
    @Test
    public void testDeleteOrganizationById() {
        Organization organization = new Organization();
        organization.setName("Avengers");
        organization.setDescription("An entertainment organization dealing with fictional superheroes");
        organization.setAddress("New York City, New York, United States, 10001");
        organization.setContactNumber("2125764000");
        organization = organizationDao.addOrganization(organization);
        
        Organization fromDao = organizationDao.getOrganizationById(organization.getId());
        assertEquals(organization, fromDao);

        organizationDao.deleteOrganizationById(organization.getId());
        fromDao = organizationDao.getOrganizationById(organization.getId());
        assertNull(fromDao);
    }
    
    @Test
    public void testGetOrganizationsForSuperhero() {
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
        
        Organization organization2 = new Organization();
        organization2.setName("Warner Bros");
        organization2.setDescription("An entertainment conglomerate");
        organization2.setAddress("New York City, New York, United States, 10002");
        organization2.setContactNumber("8189546777");
        organization2 = organizationDao.addOrganization(organization2);

        Superhero superhero = new Superhero();
        superhero.setName("Spiderman");
        superhero.setDescription("An ordinary young man in his 20s living in the city");
        superhero.setSuperpower(superpower);
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);
        organizations.add(organization2);
        superhero.setOrganizations(organizations);
        superhero = superheroDao.addSuperhero(superhero);
        
        List<Organization> fromDao = organizationDao.getOrganizationsForSuperhero(superhero);
        assertEquals(2, fromDao.size());
        assertTrue(fromDao.contains(organization));
        assertTrue(fromDao.contains(organization2));
    }
    
}
