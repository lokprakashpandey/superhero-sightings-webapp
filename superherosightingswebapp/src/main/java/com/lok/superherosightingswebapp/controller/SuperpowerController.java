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
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String displaySuperpowers(Model model) {
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        model.addAttribute("superpowers", superpowers);
        return "superpowers";
    }
    
    @PostMapping("addSuperpower")
    public String addSuperpower(@Valid Superpower superpower, BindingResult result, Model model) {
        if(result.hasErrors()) {
            //Set model to retrive data entered
            model.addAttribute("superpower",superpower);
            //Catch error for display
            FieldError nameError = result.getFieldError("name");
            model.addAttribute("nameError", nameError);
            FieldError descriptionError = result.getFieldError("description");
            model.addAttribute("descriptionError", descriptionError);
            //Set superpowers list also to display, otherwise nothing will be displayed in the table
            List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
            model.addAttribute("superpowers", superpowers);//This is superpowers list
            return "superpowers"; // This is view
        }
        else {
            superpowerDao.addSuperpower(superpower);
            return "redirect:/superpowers";
        }
    }
    
    @GetMapping("editSuperpower")
    public String editTeacher(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Superpower superpower = superpowerDao.getSuperpowerById(id);
        model.addAttribute("superpower", superpower);
        return "editSuperpower";
    }
    
    @PostMapping("editSuperpower")
    public String performEditCourse(@Valid Superpower superpower, BindingResult result, Model model) {
                
        if(result.hasErrors()) {
            model.addAttribute("superpower", superpower);
            return "editSuperpower";
        }
        superpowerDao.updateSuperpower(superpower);
        return "redirect:/superpowers";
    }
    
    @GetMapping("deleteSuperpower")
    public String deleteSuperpower(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        superpowerDao.deleteSuperpowerById(id);
        return "redirect:/superpowers";
    }
}
