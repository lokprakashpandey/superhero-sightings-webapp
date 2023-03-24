/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-13
 * purpose: SightingDao Implementation
 */
package com.lok.superherosightingswebapp.dao;

import com.lok.superherosightingswebapp.dao.LocationDaoDb.LocationMapper;
import com.lok.superherosightingswebapp.dao.SuperheroDaoDb.SuperheroMapper;
import com.lok.superherosightingswebapp.dao.SuperpowerDaoDb.SuperpowerMapper;
import com.lok.superherosightingswebapp.dto.Location;
import com.lok.superherosightingswebapp.dto.Organization;
import com.lok.superherosightingswebapp.dto.Sighting;
import com.lok.superherosightingswebapp.dto.Superhero;
import com.lok.superherosightingswebapp.dto.Superpower;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SightingDaoDb implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Sighting getSightingById(int id) {
        try {
            final String GET_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE id = ?";
            Sighting sighting = jdbc.queryForObject(GET_SIGHTING_BY_ID, new SightingMapper(), id);
            if (sighting != null) {
                sighting.setLocation(getLocationForSighting(id));
                sighting.setSuperhero(getSuperheroForSighting(id));
            }
            return sighting;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Location getLocationForSighting(int id) {
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l.* FROM location l "
                + "JOIN sighting s ON s.locationId = l.id WHERE s.id = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationMapper(), id);
    }

    private Superhero getSuperheroForSighting(int id) {
        final String SELECT_SUPERHERO_FOR_SIGHTING = "SELECT su.* FROM superhero su "
                + "JOIN sighting si ON si.superheroId = su.id WHERE si.id = ?";
        Superhero superhero = jdbc.queryForObject(SELECT_SUPERHERO_FOR_SIGHTING, new SuperheroMapper(), id);
        superhero.setSuperpower(getSuperpowerForSuperhero(superhero.getId()));
        superhero.setOrganizations(getOrganizationsForSuperhero(superhero.getId()));
        return superhero;
    }

    private Superpower getSuperpowerForSuperhero(int superheroId) {
        final String SELECT_SUPERPOWER_FOR_SUPERHERO = "SELECT sp.* FROM superpower sp "
                + "JOIN superhero sh ON sp.id = sh.superpowerId WHERE sh.id = ?";
        return jdbc.queryForObject(SELECT_SUPERPOWER_FOR_SUPERHERO, new SuperpowerMapper(), superheroId);
    }

    private List<Organization> getOrganizationsForSuperhero(int superheroId) {
        final String SELECT_ORGANIZATIONS_FOR_SUPERHERO = "SELECT o.* FROM organization o "
                + "JOIN superhero_organization so ON o.id = so.organizationId "
                + "JOIN superhero s ON s.id = so.superheroId WHERE s.id = ?";
        return jdbc.query(SELECT_ORGANIZATIONS_FOR_SUPERHERO,
                new OrganizationDaoDb.OrganizationMapper(), superheroId);
    }
    
    @Override
    public List<Sighting> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sighting";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
        associateLocationAndSuperhero(sightings);
        return sightings;
    }

    private void associateLocationAndSuperhero(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setLocation(getLocationForSighting(sighting.getId()));
            sighting.setSuperhero(getSuperheroForSighting(sighting.getId()));
        }
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO sighting(date, locationId, superheroId) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_SIGHTING,
                sighting.getDate(),
                sighting.getLocation().getId(),
                sighting.getSuperhero().getId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        return sighting;
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE sighting SET date = ?, locationId = ?, "
                + "superheroId = ? WHERE id = ?";
        jdbc.update(UPDATE_SIGHTING,
                sighting.getDate(),
                sighting.getLocation().getId(),
                sighting.getSuperhero().getId(),
                sighting.getId());
    }

    @Override
    public void deleteSightingById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE id = ?";
        jdbc.update(DELETE_SIGHTING, id);
    }

    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setId(rs.getInt("id"));
            sighting.setDate(rs.getDate("date").toLocalDate());
            return sighting;
        }
    }

    @Override
    public List<Sighting> getSightingsForParticularDate(LocalDate date) {
        final String SELECT_SIGHTINGS_FOR_PARTICULAR_DATE = "SELECT * FROM sighting WHERE date = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_PARTICULAR_DATE,
                new SightingMapper(), date);
        associateLocationAndSuperhero(sightings);
        return sightings;
    }
}
