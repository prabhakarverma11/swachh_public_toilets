package com.websystique.springmvc.controller;

import com.google.gson.Gson;
import com.websystique.springmvc.model.Location;
import com.websystique.springmvc.model.PinCodeDetail;
import com.websystique.springmvc.model.PlaceULBMap;
import com.websystique.springmvc.service.LocationService;
import com.websystique.springmvc.service.PinCodeDetailService;
import com.websystique.springmvc.service.PlaceULBMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;


@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AppController {

    @Autowired
    LocationService locationService;

    @Autowired
    PlaceULBMapService placeULBMapService;

    @Autowired
    PinCodeDetailService pinCodeDetailService;


    /**
     * This method will list all existing users.
     */
//    @RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
//    public String listUsers(ModelMap model) {
//
//        List<User> users = userService.findAllUsers();
//        model.addAttribute("users", users);
//        model.addAttribute("loggedinuser", getPrincipal());
//        return "userslist";
//    }

    /**
     * This method handles logout requests.
     * Toggle the handlers if you are RememberMe functionality is useless in your app.
     */


    @RequestMapping(value = "/marker-display", method = RequestMethod.GET)
    public String markerDisplay(ModelMap model) {
        Gson gson = new Gson();
        List<Location> allLocations = locationService.getAllLocationsByPageAndSize(0, 20);
        model.addAttribute("locationsList", allLocations);
        model.addAttribute("locationsListJson", gson.toJson(allLocations));

        return "markerdisplay";
    }

    @RequestMapping(value = "/map-place-pincode-ulb", method = RequestMethod.GET)
    public String mapPlaceToULBName(ModelMap model) {

        List<PlaceULBMap> placeULBMaps = placeULBMapService.getAllPlaceULBMapByPageAndSize(1, 5136);
        for (PlaceULBMap placeULBMap : placeULBMaps) {
            Integer pinCode = placeULBMap.getPostalCode();
            PinCodeDetail pinCodeDetail = pinCodeDetailService.getPinCodeDetailByPinCode(pinCode);
            if (pinCodeDetail != null) {
                placeULBMap.setULBName(pinCodeDetail.getDistrict());
                placeULBMapService.update(placeULBMap);
            }

        }
        return "redirect:/home";
    }
}