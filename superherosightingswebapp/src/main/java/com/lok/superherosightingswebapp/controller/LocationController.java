/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-28
 * purpose: Controller for Location
 */

package com.lok.superherosightingswebapp.controller;

import com.lok.superherosightingswebapp.dto.Location;
import com.lok.superherosightingswebapp.service.LocationService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LocationController {

    @Autowired
    LocationService locationService;
    
    @GetMapping("locations")
    public String displayLocations(Model model) {
        model.addAttribute("locations", locationService.getAllLocations());
        return "locations";
    }
    
    @GetMapping("addLocation")
    public String addLocation(Model model){
        model.addAttribute("locations", locationService.getAllLocations());
        return "locations";
    }
    
    @PostMapping("addLocation")
    public String addLocation(@Valid Location location, BindingResult result, Model model) {
        if(result.hasErrors()) {
            //Set model to retrive data entered
            model.addAttribute("location",location);
            //Catch error for display
            model.addAttribute("nameError", result.getFieldError("name"));
            model.addAttribute("descriptionError", result.getFieldError("description"));
            model.addAttribute("addressError", result.getFieldError("address"));
            model.addAttribute("latitudeError", result.getFieldError("latitude"));
            model.addAttribute("longitudeError", result.getFieldError("longitude"));
            //Set locations list also to display, otherwise nothing will be displayed in the table
            model.addAttribute("locations", locationService.getAllLocations());
            return "locations"; // This is view which will display errors and list both
        }
        else {
            locationService.addLocation(location);
            return "redirect:/locations";
        }
    }

    @GetMapping("locationDetail")
    public String locationDetail(Integer id, Model model) {
        Location location = locationService.getLocationById(id);
        model.addAttribute("location", location);
        return "locationDetail";
    }
    
    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        model.addAttribute("location", locationService.getLocationById(id));
        return "editLocation";
    }
    
    @PostMapping("editLocation")
    public String performEditLocation(@Valid Location location, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("location", location);
            return "editLocation";
        }
        locationService.updateLocation(location);
        return "redirect:/locationDetail?id=" + location.getId();
    }
    
    @GetMapping("deleteLocation")
    public String showDeleteLocationForm(@RequestParam("id") int id, Model model) {
        model.addAttribute("id", id);
        return "deleteLocation";
    }
    
    @PostMapping("/deleteLocation")
    public String deleteLocation(@RequestParam("id") int id) {
        locationService.deleteLocationById(id);
        return "redirect:/locations";
    }
}
