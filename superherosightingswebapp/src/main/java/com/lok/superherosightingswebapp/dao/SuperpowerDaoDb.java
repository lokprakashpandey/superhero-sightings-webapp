/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-15
 * purpose: Implementation for SuperpowerDao
 */
package com.lok.superherosightingswebapp.dao;

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
public class SuperpowerDaoDb implements SuperpowerDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Superpower getSuperpowerById(int id) {
        try {
            final String GET_SUPERPOWER_BY_ID = "SELECT * FROM superpower WHERE id = ?";
            return jdbc.queryForObject(GET_SUPERPOWER_BY_ID, new SuperpowerMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Superpower> getAllSuperpowers() {
        final String GET_ALL_SUPERPOWERS = "SELECT * FROM superpower";
        return jdbc.query(GET_ALL_SUPERPOWERS, new SuperpowerMapper());
    }

    @Override
    @Transactional
    public Superpower addSuperpower(Superpower superpower) {
        final String INSERT_SUPERPOWER = "INSERT INTO superpower(name, description) "
                + "VALUES(?,?)";
        jdbc.update(INSERT_SUPERPOWER,
                superpower.getName(),
                superpower.getDescription());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superpower.setId(newId);
        return superpower;
    }

    @Override
    public void updateSuperpower(Superpower superpower) {
        final String UPDATE_SUPERPOWER = "UPDATE superpower SET name = ?, description = ? "
                + "WHERE id = ?";
        jdbc.update(UPDATE_SUPERPOWER,
                superpower.getName(),
                superpower.getDescription(),
                superpower.getId());
    }

    @Override
    @Transactional
    public void deleteSuperpowerById(int id) {
        final String DELETE_SUPERHERO_ORGANIZATION = "DELETE superhero_organization "
                + "FROM superhero_organization JOIN superhero "
                + "ON superhero_organization.superheroId = superhero.id "
                + "WHERE superhero.superpowerId = ?";
        jdbc.update(DELETE_SUPERHERO_ORGANIZATION, id);

        final String DELETE_SIGHTING = "DELETE sighting FROM sighting JOIN superhero "
                + "ON sighting.superheroId = superhero.id WHERE superhero.superpowerId = ?";
        jdbc.update(DELETE_SIGHTING, id);

        final String DELETE_SUPERHERO = "DELETE FROM superhero "
                + "WHERE superpowerId = ?";
        jdbc.update(DELETE_SUPERHERO, id);

        final String DELETE_SUPERPOWER = "DELETE FROM superpower WHERE id = ?";
        jdbc.update(DELETE_SUPERPOWER, id);
    }

    public static final class SuperpowerMapper implements RowMapper<Superpower> {

        @Override
        public Superpower mapRow(ResultSet rs, int index) throws SQLException {
            Superpower superpower = new Superpower();
            superpower.setId(rs.getInt("id"));
            superpower.setName(rs.getString("name"));
            superpower.setDescription(rs.getString("description"));
            return superpower;
        }
    }
}
