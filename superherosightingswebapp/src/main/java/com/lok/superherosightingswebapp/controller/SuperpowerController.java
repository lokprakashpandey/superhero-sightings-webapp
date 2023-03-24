/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-24
 * purpose: Superpower Controller
 */

package com.lok.superherosightingswebapp.controller;

import com.lok.superherosightingswebapp.dao.LocationDao;
import com.lok.superherosightingswebapp.dao.OrganizationDao;
import com.lok.superherosightingswebapp.dao.SightingDao;
import com.lok.superherosightingswebapp.dao.SuperheroDao;
import com.lok.superherosightingswebapp.dao.SuperpowerDao;
import com.lok.superherosightingswebapp.dto.Superpower;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SuperpowerController {

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

    @GetMapping("superpowers")
    public String displayTeachers(Model model) {
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        model.addAttribute("teachers", superpowers);
        return "superpowers";
    }
}
