/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-13
 * purpose: Implementation for OrganizationDao
 */
package com.lok.superherosightingswebapp.dao;

import com.lok.superherosightingswebapp.dao.SuperheroDaoDb.SuperheroMapper;
import com.lok.superherosightingswebapp.dao.SuperpowerDaoDb.SuperpowerMapper;
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
public class OrganizationDaoDb implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrganizationById(int id) {
        try {
            final String GET_ORGANIZATION_BY_ID = "SELECT * FROM organization WHERE id = ?";
            Organization organization = jdbc.queryForObject(GET_ORGANIZATION_BY_ID, new OrganizationMapper(), id);
            return organization;
        } catch (DataAccessException ex) {
            return null;
        }
    }
    @Override
    public List<Organization> getAllOrganizations() {
        final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM organization";
        List<Organization> organizations = jdbc.query(SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
        return organizations;
    }
    
    //NOTE: addSuperhero() will take care of m-to-m relationship
    //The organization passed here is returned with id
    @Override
    @Transactional
    public Organization addOrganization(Organization organization) {
        final String INSERT_ORGANIZATION = "INSERT INTO organization(name, description, address, contactNumber) "
                + "VALUES(?,?,?,?)";
        jdbc.update(INSERT_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getContactNumber());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setId(newId);
        return organization;
    }

    @Override
    @Transactional
    public void updateOrganization(Organization organization) {
        final String UPDATE_ORGANIZATION = "UPDATE organization SET name = ?, description = ?, "
                + "address = ?, contactNumber = ? WHERE id = ?";
        jdbc.update(UPDATE_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getContactNumber(),
                organization.getId());        
    }
    
    @Override
    @Transactional
    public void deleteOrganizationById(int id) {
        final String DELETE_SUPERHERO_ORGANIZATION = "DELETE FROM superhero_organization WHERE organizationId = ?";
        jdbc.update(DELETE_SUPERHERO_ORGANIZATION, id);
        final String DELETE_ORGANIZATION = "DELETE FROM organization WHERE id = ?";
        jdbc.update(DELETE_ORGANIZATION, id);
    }

    public static final class OrganizationMapper implements RowMapper<Organization> {
        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization organization = new Organization();
            organization.setId(rs.getInt("id"));
            organization.setName(rs.getString("name"));
            organization.setDescription(rs.getString("description"));
            organization.setAddress(rs.getString("address"));
            organization.setContactNumber(rs.getString("contactNumber"));
            return organization;
        }
    }

    @Override
    public List<Organization> getOrganizationsForSuperhero(Superhero superhero) {
        final String SELECT_ORGANIZATIONS_FOR_SUPERHERO = "SELECT o.* FROM organization o "
                + "JOIN superhero_organization so ON o.id = so.organizationId "
                + "JOIN superhero s ON s.id = so.superheroId WHERE s.id = ?";
        List<Organization> organizations = jdbc.query(SELECT_ORGANIZATIONS_FOR_SUPERHERO,
                new OrganizationMapper(), superhero.getId());
        return organizations;
    }
}
