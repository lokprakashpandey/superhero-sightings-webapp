/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-28
 * purpose: Implementation for OrganizationService
 */

package com.lok.superherosightingswebapp.service;

import com.lok.superherosightingswebapp.dao.OrganizationDao;
import com.lok.superherosightingswebapp.dto.Organization;
import com.lok.superherosightingswebapp.dto.Superhero;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationDao organizationDao;
    
    @Override
    public Organization getOrganizationById(int id) {
        return organizationDao.getOrganizationById(id);
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return organizationDao.getAllOrganizations();
    }

    @Override
    public Organization addOrganization(Organization organization) {
        return organizationDao.addOrganization(organization);
    }

    @Override
    public void updateOrganization(Organization organization) {
        organizationDao.updateOrganization(organization);
    }

    @Override
    public void deleteOrganizationById(int id) {
        organizationDao.deleteOrganizationById(id);
    }

    @Override
    public List<Organization> getOrganizationsForSuperhero(Superhero superhero) {
        return organizationDao.getOrganizationsForSuperhero(superhero);
    }
    
}
