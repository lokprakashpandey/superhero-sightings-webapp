/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-24
 * purpose: Superpower Controller
 */

package com.lok.superherosightingswebapp.controller;

import com.lok.superherosightingswebapp.dto.Superpower;
import com.lok.superherosightingswebapp.service.SuperpowerService;
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
public class SuperpowerController {

    @Autowired
    SuperpowerService superpowerService;

    @GetMapping("superpowers")
    public String displaySuperpowers(Model model) {
        model.addAttribute("superpowers", superpowerService.getAllSuperpowers());
        return "superpowers";
    }
    
    @GetMapping("addSuperpower")
    public String addSuperpower(Model model){
        model.addAttribute("superpowers", superpowerService.getAllSuperpowers());
        return "superpowers";
    }
    
    @PostMapping("addSuperpower")
    public String addSuperpower(@Valid Superpower superpower, BindingResult result, Model model) {
        if(result.hasErrors()) {
            //Set model to retrive data entered
            model.addAttribute("superpower",superpower);
            //Catch error for display
            model.addAttribute("nameError", result.getFieldError("name"));
            model.addAttribute("descriptionError", result.getFieldError("description"));
            //Set superpowers list also to display, otherwise nothing will be displayed in the table
            model.addAttribute("superpowers", superpowerService.getAllSuperpowers());//This is superpowers list
            return "superpowers"; // This is view which will display errors and list both
        }
        else {
            superpowerService.addSuperpower(superpower);
            return "redirect:/superpowers";
        }
    }
    
    @GetMapping("superpowerDetail")
    public String superpowerDetail(Integer id, Model model) {
        Superpower superpower = superpowerService.getSuperpowerById(id);
        model.addAttribute("superpower", superpower);
        return "superpowerDetail";
    }
    
    @GetMapping("editSuperpower")
    public String editSuperpower(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        model.addAttribute("superpower", superpowerService.getSuperpowerById(id));
        return "editSuperpower";
    }
    
    @PostMapping("editSuperpower")
    public String performEditSuperpower(@Valid Superpower superpower, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("superpower", superpower);
            return "editSuperpower";
        }
        superpowerService.updateSuperpower(superpower);
        return "redirect:/superpowerDetail?id=" + superpower.getId();
    }
    
    @GetMapping("deleteSuperpower")
    public String showDeleteSuperpowerForm(@RequestParam("id") int id, Model model) {
        model.addAttribute("id", id);
        return "deleteSuperpower";
    }
    
    @PostMapping("/deleteSuperpower")
    public String deleteSuperpower(@RequestParam("id") int id) {
        superpowerService.deleteSuperpowerById(id);
        return "redirect:/superpowers";
    }
}
