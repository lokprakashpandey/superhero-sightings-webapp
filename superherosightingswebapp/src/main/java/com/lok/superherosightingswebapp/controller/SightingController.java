/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-29
 * purpose: Controller for Sighting
 */
package com.lok.superherosightingswebapp.controller;

import com.lok.superherosightingswebapp.dto.Location;
import com.lok.superherosightingswebapp.dto.Sighting;
import com.lok.superherosightingswebapp.dto.Superhero;
import com.lok.superherosightingswebapp.service.LocationService;
import com.lok.superherosightingswebapp.service.SightingService;
import com.lok.superherosightingswebapp.service.SuperheroService;
import java.util.Base64;
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
import org.springframework.web.bind.annotation.RequestParam;

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
    public String addSighting(Model model) {
        model.addAttribute("sightings", sightingService.getAllSightings());
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("superheroes", superheroService.getAllSuperheroes());
        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(@Valid Sighting sighting, BindingResult result, HttpServletRequest request, Model model) {

        String locationId = request.getParameter("locationId");
        String superheroId = request.getParameter("superheroId");
        if(locationId != null) {
            sighting.setLocation(locationService.getLocationById(Integer.parseInt(locationId)));
        } else {
            FieldError locationError = new FieldError("sighting", "location", "Location information absent");
            result.addError(locationError);
        }
        if(superheroId != null) {
            sighting.setSuperhero(superheroService.getSuperheroById(Integer.parseInt(superheroId)));
        } else {
            FieldError superheroError = new FieldError("sighting", "superhero", "Superhero information absent");
            result.addError(superheroError);
        }   
        
        if (result.hasErrors()) {
            //Set model to retrieve data entered
            model.addAttribute("sighting", sighting);
            //Catch error for display
            model.addAttribute("dateError", result.getFieldError("date"));
            //Set sightings list also to display, otherwise nothing will be displayed in the table
            model.addAttribute("sightings", sightingService.getAllSightings());
            model.addAttribute("locations", locationService.getAllLocations());
            model.addAttribute("superheroes", superheroService.getAllSuperheroes());
            return "sightings"; // This is view which will display errors and list both
        } else {
            sightingService.addSighting(sighting);
            return "redirect:/sightings";
        }
    }

    @GetMapping("sightingDetail")
    public String sightingDetail(Integer id, Model model) {
        Sighting sighting = sightingService.getSightingById(id);
        Superhero superhero = superheroService.getSuperheroById(sighting.getSuperhero().getId());
        byte[] superheroImage = superhero.getImage();
        String superheroImageData = null;
        if(superheroImage != null) {
            superheroImageData = Base64.getMimeEncoder().encodeToString(superheroImage);
        }
        model.addAttribute("superheroImage", superheroImageData);        
        model.addAttribute("sighting", sighting);
        return "sightingDetail";
    }

    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        Sighting sighting = sightingService.getSightingById(id);
        List<Location> locations = locationService.getAllLocations();
        List<Superhero> superheroes = superheroService.getAllSuperheroes();
        model.addAttribute("sighting", sighting);
        model.addAttribute("locations", locations);
        model.addAttribute("superheroes", superheroes);
        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(@Valid Sighting sighting, BindingResult result, HttpServletRequest request, Model model) {
        String locationId = request.getParameter("locationId");
        String superheroId = request.getParameter("superheroId");
        sighting.setLocation(locationService.getLocationById(Integer.parseInt(locationId)));
        sighting.setSuperhero(superheroService.getSuperheroById(Integer.parseInt(superheroId)));

        if (result.hasErrors()) {
            model.addAttribute("sightings", sightingService.getAllSightings());
            model.addAttribute("locations", locationService.getAllLocations());
            model.addAttribute("superheroes", superheroService.getAllSuperheroes());
            return "editSighting";
        }

        sightingService.updateSighting(sighting);
        return "redirect:/sightingDetail?id=" + sighting.getId();
    }

    @GetMapping("deleteSighting")
    public String showDeleteSightingForm(@RequestParam("id") int id, Model model) {
        model.addAttribute("id", id);
        return "deleteSighting";
    }
    
    @PostMapping("/deleteSighting")
    public String deleteSighting(@RequestParam("id") int id) {
        sightingService.deleteSightingById(id);
        return "redirect:/sightings";
    }
}
