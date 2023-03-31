/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-29
 * purpose: Superhero Controller
 */
package com.lok.superherosightingswebapp.controller;

import com.lok.superherosightingswebapp.dto.Organization;
import com.lok.superherosightingswebapp.dto.Superhero;
import com.lok.superherosightingswebapp.dto.Superpower;
import com.lok.superherosightingswebapp.service.OrganizationService;
import com.lok.superherosightingswebapp.service.SuperheroService;
import com.lok.superherosightingswebapp.service.SuperpowerService;
import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class SuperheroController {

    @Autowired
    SuperheroService superheroService;

    @Autowired
    SuperpowerService superpowerService;

    @Autowired
    OrganizationService organizationService;

    @GetMapping("superheroes")
    public String displaySuperheroes(Model model) {
        model.addAttribute("superheroes", superheroService.getAllSuperheroes());
        model.addAttribute("superpowers", superpowerService.getAllSuperpowers());
        model.addAttribute("organizations", organizationService.getAllOrganizations());
        return "superheroes";
    }

    @GetMapping("addSuperhero")
    public String addSuperhero(Model model) {
        model.addAttribute("superheroes", superheroService.getAllSuperheroes());
        model.addAttribute("superpowers", superpowerService.getAllSuperpowers());
        model.addAttribute("organizations", organizationService.getAllOrganizations());
        return "superheroes";
    }

    @PostMapping("addSuperhero")
    public String addSuperhero(@Valid @ModelAttribute("superhero") Superhero superhero, 
                                    BindingResult result, 
                                    HttpServletRequest request, 
                                    @RequestParam("superheroSaveImage") MultipartFile file,
                                    Model model) {
        try{
            superhero.setImage(file.getBytes()); 
        }catch(IOException ex){ 
            FieldError error = new FieldError("superhero", "image", ex.getMessage());
            result.addError(error);      
        }       
        
        String superpowerId = request.getParameter("superpowerId");

        String[] organizationIds = request.getParameterValues("organizationId");

        superhero.setSuperpower(superpowerService.getSuperpowerById(Integer.parseInt(superpowerId)));

        List<Organization> organizations = new ArrayList<>();
        if (organizationIds != null) {
            for (String organizationId : organizationIds) {
                organizations.add(organizationService.getOrganizationById(Integer.parseInt(organizationId)));
            }
        } else {
            FieldError error = new FieldError("superhero", "organizations", "Must include one organization");
            result.addError(error);
        }

        superhero.setOrganizations(organizations);
        
        System.out.println("Error"+result);
        
        if (result.hasErrors()) {
            //Set model to retrieve data entered
            System.out.println("33333333");
            model.addAttribute("superhero", superhero);
            //Catch error for display
            model.addAttribute("nameError", result.getFieldError("name"));
            model.addAttribute("descriptionError", result.getFieldError("description"));

            //Set superheroes list also to display, otherwise nothing will be displayed in the table
            model.addAttribute("superheroes", superheroService.getAllSuperheroes());
            model.addAttribute("superpowers", superpowerService.getAllSuperpowers());
            model.addAttribute("organizations", organizationService.getAllOrganizations());
            return "superheroes"; // This is view which will display errors and list both
        } else {
            superheroService.addSuperhero(superhero);
            return "redirect:/superheroes";
        }
    }

    @GetMapping("superheroDetail")
    public String superheroDetail(Integer id, Model model) {
        Superhero superhero = superheroService.getSuperheroById(id);
        byte[] superheroImage = superhero.getImage();
        String superheroImageData = null;
        if(superheroImage != null) {
            superheroImageData = Base64.getMimeEncoder().encodeToString(superheroImage);
        }
        model.addAttribute("superhero", superhero);
        model.addAttribute("superheroImage", superheroImageData);        
        return "superheroDetail";
    }

    @GetMapping("editSuperhero")
    public String editSuperhero(Integer id, Model model) {
        Superhero superhero = superheroService.getSuperheroById(id);
        byte[] superheroImage = superhero.getImage();
        String superheroImageData = null;
        if(superheroImage != null) {
            superheroImageData = Base64.getMimeEncoder().encodeToString(superheroImage);
        }
        model.addAttribute("superhero", superhero);
        model.addAttribute("superheroImage", superheroImageData);        
        List<Superpower> superpowers = superpowerService.getAllSuperpowers();
        List<Organization> organizations = organizationService.getAllOrganizations();
        model.addAttribute("superhero", superhero);
        model.addAttribute("superpowers", superpowers);
        model.addAttribute("organizations", organizations);
        return "editSuperhero";
    }

    @PostMapping("editSuperhero")
    public String performEditSuperhero(@Valid Superhero superhero, BindingResult result, HttpServletRequest request, Model model) {
        String superpowerId = request.getParameter("superpowerId");

        String[] organizationIds = request.getParameterValues("organizationId");

        superhero.setSuperpower(superpowerService.getSuperpowerById(Integer.parseInt(superpowerId)));

        List<Organization> organizations = new ArrayList<>();
        if (organizationIds != null) {
            for (String organizationId : organizationIds) {
                organizations.add(organizationService.getOrganizationById(Integer.parseInt(organizationId)));
            }
        } else {
            FieldError organizationsError = new FieldError("superhero", "organizations", "Must include one organization");
            result.addError(organizationsError);
        }

        superhero.setOrganizations(organizations);

        if (result.hasErrors()) {
            model.addAttribute("superpowers", superpowerService.getAllSuperpowers());
            model.addAttribute("organizations", organizationService.getAllOrganizations());
            model.addAttribute("superhero", superhero);
            return "editSuperhero";
        }

        superheroService.updateSuperhero(superhero);
        return "redirect:/superheroes";
    }

    @GetMapping("deleteSuperhero")
    public String deleteSuperhero(Integer id) {
        superheroService.deleteSuperheroById(id);
        return "redirect:/superheroes";
    }
}
