/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-28
 * purpose: Service interface for Organization
 */
package com.lok.superherosightingswebapp.service;

import com.lok.superherosightingswebapp.dto.Organization;
import com.lok.superherosightingswebapp.dto.Superhero;
import java.util.List;

/**
 *
 * @author root
 */
public interface OrganizationService {

    Organization getOrganizationById(int id);

    List<Organization> getAllOrganizations();

    Organization addOrganization(Organization organization);

    void updateOrganization(Organization organization);

    void deleteOrganizationById(int id);

    List<Organization> getOrganizationsForSuperhero(Superhero superhero);
}
