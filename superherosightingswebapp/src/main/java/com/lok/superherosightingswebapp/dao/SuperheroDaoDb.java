/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-10
 * purpose: Implementation of SuperheroDao
 */
package com.lok.superherosightingswebapp.dao;

import com.lok.superherosightingswebapp.dao.OrganizationDaoDb.OrganizationMapper;
import com.lok.superherosightingswebapp.dao.SuperpowerDaoDb.SuperpowerMapper;
import com.lok.superherosightingswebapp.dto.Location;
import com.lok.superherosightingswebapp.dto.Organization;
import com.lok.superherosightingswebapp.dto.Superhero;
import com.lok.superherosightingswebapp.dto.Superpower;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SuperheroDaoDb implements SuperheroDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Superhero getSuperheroById(int id) {
        try {
            final String GET_SUPERHERO_BY_ID = "SELECT * FROM superhero WHERE id = ?";
            Superhero superhero = jdbc.queryForObject(GET_SUPERHERO_BY_ID, new SuperheroMapper(), id);
            if (superhero != null) {
                superhero.setSuperpower(getSuperpowerForSuperhero(id));
                superhero.setOrganizations(getOrganizationsForSuperhero(id));
            }
            return superhero;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Superpower getSuperpowerForSuperhero(int id) {
        final String SELECT_SUPERPOWER_FOR_SUPERHERO = "SELECT sp.* FROM superpower sp "
                + "JOIN superhero sh ON sp.id = sh.superpowerId WHERE sh.id = ?";
        return jdbc.queryForObject(SELECT_SUPERPOWER_FOR_SUPERHERO, new SuperpowerMapper(), id);
    }

    private List<Organization> getOrganizationsForSuperhero(int id) {
        final String SELECT_ORGANIZATIONS_FOR_SUPERHERO = "SELECT o.* FROM organization o "
                + "JOIN superhero_organization so ON o.id = so.organizationId "
                + "WHERE so.superheroId = ?";
        return jdbc.query(SELECT_ORGANIZATIONS_FOR_SUPERHERO, new OrganizationMapper(), id);
    }

    @Override
    public List<Superhero> getAllSuperheroes() {
        final String GET_ALL_SUPERHEROES = "SELECT * FROM superhero";
        List<Superhero> superheroes = jdbc.query(GET_ALL_SUPERHEROES, new SuperheroMapper());
        associateSuperpowersAndOrganizations(superheroes);
        return superheroes;
    }

    private void associateSuperpowersAndOrganizations(List<Superhero> superheroes) {
        for (Superhero superhero : superheroes) {
            superhero.setSuperpower(getSuperpowerForSuperhero(superhero.getId()));
            superhero.setOrganizations(getOrganizationsForSuperhero(superhero.getId()));
        }
    }

    @Override
    @Transactional
    public Superhero addSuperhero(Superhero superhero) {
        final String INSERT_SUPERHERO = "INSERT INTO superhero(name, description, image, superpowerId) "
                + "VALUES(?,?,?,?)";
        jdbc.update(INSERT_SUPERHERO,
                superhero.getName(),
                superhero.getDescription(),
                superhero.getImage(),
                superhero.getSuperpower().getId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superhero.setId(newId);
        insertSuperheroOrganization(superhero);
        return superhero;
    }

    private void insertSuperheroOrganization(Superhero superhero) {
        final String INSERT_SUPERHERO_ORGANIZATION = "INSERT INTO "
                + "superhero_organization(superheroId, organizationId) VALUES(?,?)";
        for (Organization organization : superhero.getOrganizations()) {
            jdbc.update(INSERT_SUPERHERO_ORGANIZATION,
                    superhero.getId(),
                    organization.getId());
        }
    }

    @Override
    @Transactional
    public void updateSuperhero(Superhero superhero) {
        final String UPDATE_SUPERHERO = "UPDATE superhero SET name = ?, description = ?, "
                + "image = ?, superpowerId = ? WHERE id = ?";
        jdbc.update(UPDATE_SUPERHERO,
                superhero.getName(),
                superhero.getDescription(),
                superhero.getImage(),
                superhero.getSuperpower().getId(),
                superhero.getId());

        //Since the updated Superhero object may have a List of different Organizations, so
        //we delete the previous superhero_organization entries and insert new entries
        final String DELETE_SUPERHERO_ORGANIZATION = "DELETE FROM superhero_organization WHERE superheroId = ?";
        jdbc.update(DELETE_SUPERHERO_ORGANIZATION, superhero.getId());
        insertSuperheroOrganization(superhero);
    }

    @Override
    @Transactional
    public void deleteSuperheroById(int id) {
        final String DELETE_SUPERHERO_ORGANIZATION = "DELETE FROM superhero_organization "
                + "WHERE superheroId = ?";
        jdbc.update(DELETE_SUPERHERO_ORGANIZATION, id);

        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE superheroId = ?";
        jdbc.update(DELETE_SIGHTING, id);

        final String DELETE_SUPERHERO = "DELETE FROM superhero WHERE id = ?";
        jdbc.update(DELETE_SUPERHERO, id);
    }


    @Override
    public List<Superhero> getSuperheroesForOrganization(Organization organization) {
        final String SELECT_SUPERHEROES_FOR_ORGANIZATION = "SELECT s.* FROM superhero s "
                + "JOIN superhero_organization so ON s.id = so.superheroId "
                + "JOIN organization o ON o.id = so.organizationId WHERE o.id = ?";
        List<Superhero> superheroes = jdbc.query(SELECT_SUPERHEROES_FOR_ORGANIZATION, new SuperheroMapper(), organization.getId());
        associateSuperpowersAndOrganizations(superheroes);
        return superheroes;
    }

    @Override
    public List<Superhero> getSuperheroesForLocation(Location location) {
        final String SELECT_SUPERHEROES_FOR_LOCATION = "SELECT su.* FROM superhero su JOIN "
                + "sighting si ON si.superheroId = su.Id WHERE si.locationId = ?";
        List<Superhero> superheroes = jdbc.query(SELECT_SUPERHEROES_FOR_LOCATION,
                new SuperheroMapper(), location.getId());
        associateSuperpowersAndOrganizations(superheroes);
        return superheroes;
    }

    public static final class SuperheroMapper implements RowMapper<Superhero> {

        @Override
        public Superhero mapRow(ResultSet rs, int index) throws SQLException {
            Superhero superhero = new Superhero();
            superhero.setId(rs.getInt("id"));
            superhero.setName(rs.getString("name"));
            superhero.setDescription(rs.getString("description"));
            superhero.setImage(rs.getBytes("image"));
            return superhero;
        }
    }

}
