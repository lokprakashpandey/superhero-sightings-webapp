/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023 -04-01
 * purpose: For handling errors
 */

package com.lok.superherosightingswebapp.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorControllerImpl implements ErrorController {
    
    @RequestMapping("/error")
    public String error(Model model) {
        model.addAttribute("errorMessage", "There was an unexpected error. Please try again later.");
        return "error";
    }
    
}
