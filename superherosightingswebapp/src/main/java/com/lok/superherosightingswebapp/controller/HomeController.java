/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-30
 * purpose: HomeController for landing page
 */
package com.lok.superherosightingswebapp.controller;

import com.lok.superherosightingswebapp.service.SightingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    SightingService sightingService;

    @RequestMapping({"/","/home"})
    public String home(Model model) {
        model.addAttribute("sightings", sightingService.getLatestTenSightings());
        return "home";
    }
}
