package com.websystique.springmvc.controller;

import com.websystique.springmvc.model.Report;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@RequestMapping("/")
//@SessionAttributes("roles")
public class ReportController {

    /**
     * This method will list all existing users.
     */
    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public String showReport(ModelMap model) {

        model.addAttribute("report", new Report());
        return "report";
    }

}