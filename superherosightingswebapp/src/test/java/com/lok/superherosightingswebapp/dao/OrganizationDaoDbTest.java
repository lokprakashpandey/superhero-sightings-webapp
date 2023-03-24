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
        Superpower superpower = new Superpower();
        superpower.setName("Webbing");
        superpower.setDescription("The ability to shoot webs whenever in need");
        superpower = superpowerDao.addSuperpower(superpower);

        Superhero superhero = new Superhero();
        superhero.setName("Spiderman");
        superhero.setDescription("An ordinary young man in his 20s living in the city");
        superhero.setSuperpower(superpower);
        
        Organization organization = new Organization();
        organization.setName("Avengers");
        organization.setDescription("An entertainment organization dealing with fictional superheroes");
        organization.setAddress("New York City, New York, United States, 10001");
        organization.setContactNumber("2125764000");
        List<Superhero> superheroes = new ArrayList<>();
        superheroes.add(superhero);
        organization.setSuperheroes(superheroes);
        organization = organizationDao.addOrganization(organization);
        
        Organization fromDao = organizationDao.getOrganizationById(organization.getId());
        assertEquals(organization, fromDao);

    }
    
    @Test
    public void testGetAllOrganizations() {
        Superpower superpower = new Superpower();
        superpower.setName("Webbing");
        superpower.setDescription("The ability to shoot webs whenever in need");
        superpower = superpowerDao.addSuperpower(superpower);

        Superhero superhero = new Superhero();
        superhero.setName("Spiderman");
        superhero.setDescription("An ordinary young man in his 20s living in the city");
        superhero.setSuperpower(superpower);
        
        Organization organization = new Organization();
        organization.setName("Avengers");
        organization.setDescription("An entertainment organization dealing with fictional superheroes");
        organization.setAddress("New York City, New York, United States, 10001");
        organization.setContactNumber("2125764000");
        List<Superhero> superheroes = new ArrayList<>();
        superheroes.add(superhero);
        organization.setSuperheroes(superheroes);
        organization = organizationDao.addOrganization(organization);
        
        Superpower superpower2 = new Superpower();
        superpower2.setName("Superstrength");
        superpower2.setDescription("The unhuman ability to become very powerful and strong");
        superpower2 = superpowerDao.addSuperpower(superpower2);

        Superhero superhero2 = new Superhero();
        superhero2.setName("Hulk");
        superhero2.setDescription("A doctor who got exposed to radiation and became greenish and huge");
        superhero2.setSuperpower(superpower2);
        
        Organization organization2 = new Organization();
        organization2.setName("Warner Bros");
        organization2.setDescription("An entertainment conglomerate");
        organization2.setAddress("New York City, New York, United States, 10002");
        organization2.setContactNumber("8189546777");
        List<Superhero> superheroes2 = new ArrayList<>();
        superheroes2.add(superhero2);
        organization2.setSuperheroes(superheroes2);
        organization2 = organizationDao.addOrganization(organization2);
        
        List<Organization> organizations = organizationDao.getAllOrganizations();
        assertEquals(2, organizations.size());
        assertTrue(organizations.contains(organization));
        assertTrue(organizations.contains(organization2));
    }
    
    @Test
    public void testUpdateOrganization() {
        Superpower superpower = new Superpower();
        superpower.setName("Webbing");
        superpower.setDescription("The ability to shoot webs whenever in need");
        superpower = superpowerDao.addSuperpower(superpower);

        Superhero superhero = new Superhero();
        superhero.setName("Spiderman");
        superhero.setDescription("An ordinary young man in his 20s living in the city");
        superhero.setSuperpower(superpower);
        
        Organization organization = new Organization();
        organization.setName("Avengers");
        organization.setDescription("An entertainment organization dealing with fictional superheroes");
        organization.setAddress("New York City, New York, United States, 10001");
        organization.setContactNumber("2125764000");
        List<Superhero> superheroes = new ArrayList<>();
        superheroes.add(superhero);
        organization.setSuperheroes(superheroes);
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
        Superpower superpower = new Superpower();
        superpower.setName("Webbing");
        superpower.setDescription("The ability to shoot webs whenever in need");
        superpower = superpowerDao.addSuperpower(superpower);

        Superhero superhero = new Superhero();
        superhero.setName("Spiderman");
        superhero.setDescription("An ordinary young man in his 20s living in the city");
        superhero.setSuperpower(superpower);
        
        Organization organization = new Organization();
        organization.setName("Avengers");
        organization.setDescription("An entertainment organization dealing with fictional superheroes");
        organization.setAddress("New York City, New York, United States, 10001");
        organization.setContactNumber("2125764000");
        List<Superhero> superheroes = new ArrayList<>();
        superheroes.add(superhero);
        organization.setSuperheroes(superheroes);
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
        
        Organization organization2 = new Organization();
        organization2.setName("Warner Bros");
        organization2.setDescription("An entertainment conglomerate");
        organization2.setAddress("New York City, New York, United States, 10002");
        organization2.setContactNumber("8189546777");
        
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
