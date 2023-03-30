/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-29
 * purpose: Controller for Sighting
 */

package com.lok.superherosightingswebapp.controller;

import com.lok.superherosightingswebapp.dto.Sighting;
import com.lok.superherosightingswebapp.service.LocationService;
import com.lok.superherosightingswebapp.service.SightingService;
import com.lok.superherosightingswebapp.service.SuperheroService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SightingController {
    
    @Autowired
    SightingService sightingService;
    
    @Autowired
    LocationService locationService;
    
    @Autowired
    SuperheroService superheroService;
    
    @GetMapping("sightings")
    public String displaySightings(Model model) {
        model.addAttribute("sightings", sightingService.getAllSightings());
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("superheroes", superheroService.getAllSuperheroes());
        return "sightings";
    }
    
    @GetMapping("addSighting")
    public String addSighting(Model model){
        model.addAttribute("sightings", sightingService.getAllSightings());
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("superheroes", superheroService.getAllSuperheroes());
        return "sightings";
    }
    
    @PostMapping("addSighting")
    public String addSighting(@Valid Sighting sighting, BindingResult result, HttpServletRequest request, Model model) {
        
        System.out.println("22222"+sighting.getDate()+"11111111");
        String locationId = request.getParameter("locationId");
        String superheroId = request.getParameter("superheroId");
        sighting.setLocation(locationService.getLocationById(Integer.parseInt(locationId)));
        sighting.setSuperhero(superheroService.getSuperheroById(Integer.parseInt(superheroId)));
        
        if(result.hasErrors()) {
            //Set model to retrieve data entered
            model.addAttribute("sighting",sighting);
            //Catch error for display
            model.addAttribute("dateError", result.getFieldError("name"));
            //Set sightings list also to display, otherwise nothing will be displayed in the table
            model.addAttribute("sightings", sightingService.getAllSightings());
            model.addAttribute("locations", locationService.getAllLocations());
            model.addAttribute("superheroes", superheroService.getAllSuperheroes());
            return "sightings"; // This is view which will display errors and list both
        }
        else {
            sightingService.addSighting(sighting);
            return "redirect:/sightings";
        }
    }
    
    
}
