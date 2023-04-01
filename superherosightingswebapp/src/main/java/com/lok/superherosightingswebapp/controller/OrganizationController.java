/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-29
 * purpose: Controller for Organization
 */

package com.lok.superherosightingswebapp.controller;

import com.lok.superherosightingswebapp.dto.Organization;
import com.lok.superherosightingswebapp.service.OrganizationService;
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
public class OrganizationController {

    @Autowired
    OrganizationService organizationService;
    
    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        model.addAttribute("organizations", organizationService.getAllOrganizations());
        return "organizations";
    }
    
    @GetMapping("addOrganization")
    public String addOrganization(Model model){
        model.addAttribute("organizations", organizationService.getAllOrganizations());
        return "organizations";
    }
    
    @PostMapping("addOrganization")
    public String addOrganization(@Valid Organization organization, BindingResult result, Model model) {
        if(result.hasErrors()) {
            //Set model to retrieve data entered
            model.addAttribute("organization",organization);
            //Catch error for display
            model.addAttribute("nameError", result.getFieldError("name"));
            model.addAttribute("descriptionError", result.getFieldError("description"));
            model.addAttribute("addressError", result.getFieldError("address"));
            model.addAttribute("contactNumberError", result.getFieldError("contactNumber"));
            //Set organizations list also to display, otherwise nothing will be displayed in the table
            model.addAttribute("organizations", organizationService.getAllOrganizations());
            return "organizations"; // This is view which will display errors and list both
        }
        else {
            organizationService.addOrganization(organization);
            return "redirect:/organizations";
        }
    }
    
    @GetMapping("editOrganization")
    public String editOrganization(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        model.addAttribute("organization", organizationService.getOrganizationById(id));
        return "editOrganization";
    }
    
    @PostMapping("editOrganization")
    public String performEditOrganization(@Valid Organization organization, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("organization", organization);
            return "editOrganization";
        }
        organizationService.updateOrganization(organization);
        return "redirect:/organizations";
    }
    
    @GetMapping("deleteOrganization")
    public String showDeleteOrganizationForm(@RequestParam("id") int id, Model model) {
        model.addAttribute("id", id);
        return "deleteOrganization";
    }
    
    @PostMapping("/deleteOrganization")
    public String deleteOrganization(@RequestParam("id") int id) {
        organizationService.deleteOrganizationById(id);
        return "redirect:/organizations";
    }
    
}
