package com.websystique.springmvc.controller;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.websystique.springmvc.model.*;
import com.websystique.springmvc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AppController {

    @Autowired
    UserService userService;

    @Autowired
    PlaceService placeService;

    @Autowired
    PlaceDetailService placeDetailService;

    @Autowired
    LocationService locationService;

    @Autowired
    ReportService reportService;

    @Autowired
    MailServiceImpl mailService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    UserProfileService userProfileService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

    @Autowired
    AuthenticationTrustResolver authenticationTrustResolver;


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
     * This method will provide the medium to add a new user.
     */
    @RequestMapping(value = {"/newuser"}, method = RequestMethod.GET)
    public String newUser(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        model.addAttribute("loggedinuser", getPrincipal());
        return "registration";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * saving user in database. It also validates the user input
     */
    @RequestMapping(value = {"/newuser"}, method = RequestMethod.POST)
    public String saveUser(@Valid User user, BindingResult result,
                           ModelMap model) {

        if (result.hasErrors()) {
            return "registration";
        }

		/*
         * Preferred way to achieve uniqueness of field [sso] should be implementing custom @Unique annotation
		 * and applying it on field [sso] of Model class [User].
		 * 
		 * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
		 * framework as well while still using internationalized messages.
		 * 
		 */
        if (!userService.isUserSSOUnique(user.getId(), user.getSsoId())) {
            FieldError ssoError = new FieldError("user", "ssoId", messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
            result.addError(ssoError);
            return "registration";
        }

        userService.saveUser(user);

        model.addAttribute("success", "User " + user.getFirstName() + " " + user.getLastName() + " registered successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        //return "success";
        return "registrationsuccess";
    }


    /**
     * This method will provide the medium to update an existing user.
     */
    @RequestMapping(value = {"/edit-user-{ssoId}"}, method = RequestMethod.GET)
    public String editUser(@PathVariable String ssoId, ModelMap model) {
        User user = userService.findBySSO(ssoId);
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        model.addAttribute("loggedinuser", getPrincipal());
        return "registration";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * updating user in database. It also validates the user input
     */
    @RequestMapping(value = {"/edit-user-{ssoId}"}, method = RequestMethod.POST)
    public String updateUser(@Valid User user, BindingResult result,
                             ModelMap model, @PathVariable String ssoId) {

        if (result.hasErrors()) {
            return "registration";
        }

		/*//Uncomment below 'if block' if you WANT TO ALLOW UPDATING SSO_ID in UI which is a unique key to a User.
        if(!userService.isUserSSOUnique(user.getId(), user.getUsername())){
			FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getUsername()}, Locale.getDefault()));
		    result.addError(ssoError);
			return "registration";
		}*/


        userService.updateUser(user);

        model.addAttribute("success", "User " + user.getFirstName() + " " + user.getLastName() + " updated successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        return "registrationsuccess";
    }


    /**
     * This method will delete an user by it's SSOID value.
     */
    @RequestMapping(value = {"/delete-user-{ssoId}"}, method = RequestMethod.GET)
    public String deleteUser(@PathVariable String ssoId) {
        userService.deleteUserBySSO(ssoId);
        return "redirect:/list";
    }

    @ResponseBody
    @RequestMapping(value = {"/search-users-list"}, method = RequestMethod.GET)
    public void searchUsersList(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<User> users = userService.findEmailIdsOfAllUsers();
        Gson gson = new Gson();
        writer.write(gson.toJson(users));
    }

    /**
     * This method will provide UserProfile list to views
     */
    @ModelAttribute("roles")
    public List<UserProfile> initializeProfiles() {
        return userProfileService.findAll();
    }

    /**
     * This method handles Access-Denied redirect.
     */
    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        return "accessDenied";
    }

    /**
     * This method handles login GET requests.
     * If users is already logged-in and tries to goto login page again, will be redirected to list page.
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        if (isCurrentAuthenticationAnonymous()) {
            return "login";
        } else {
            return "redirect:/home";
        }
    }

            @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashbaord(ModelMap model) {

        List<Location> locations = locationService.getAllLocations();
        List<Report> reports = reportService.getReportsListByLocationsBetweenDates(locations, "", "");
        model.addAttribute("reportsList", reports);
        return "dashboard";
    }

    /**
     * This method handles logout requests.
     * Toggle the handlers if you are RememberMe functionality is useless in your app.
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            //new SecurityContextLogoutHandler().logout(request, response, auth);
            persistentTokenBasedRememberMeServices.logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/login?logout";
    }


    @RequestMapping(value = "/fetch-place-ids", method = RequestMethod.GET)
    public String fetchPlaceIds(ModelMap model) {

        List<Location> allLocations = locationService.getAllLocations();
        Gson gson = new Gson();
//        model.addAttribute("locationsListJson", gson.toJson(allLocations));
        model.addAttribute("locationsList", allLocations);

        for (Location location : allLocations) {
            String url = "https://maps.googleapis.com/maps/api/place/radarsearch/json?" +
                    "location=" +
                    location.getLatitude() + "," + location.getLongitude() +
                    "&radius=" + "10" +
                    "&type=" + "Public+Bathroom" +
                    "&key=" + "AIzaSyCpt-Zdm-UvyZeNCUMDZssczi4HSrvW-iU";

            try {
                Place place = placeService.fetchPlaceIdByLocation(location, url);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "locationslist";
    }


    @RequestMapping(value = "/listing-locations", method = RequestMethod.GET)
    public String listingLocations(ModelMap model) {

        List<Location> allLocations = locationService.getAllLocations();
        model.addAttribute("locationsList", allLocations);

        return "locationslist";
    }

    @RequestMapping(value = "/location-detail-{locationId}", method = RequestMethod.GET)
    public String listingLocations(@PathVariable Integer locationId, HttpServletRequest request, ModelMap model) {

        String startDate = request.getParameter("start_date");
        String endDate = request.getParameter("end_date");
        String dateRange = request.getParameter("date_range");

        Location location = locationService.getLocationById(locationId);
        Place place = placeService.getPlaceByLocation(location);

        Double overallRating = reviewService.getOverallRatingByPlace(place);
        Double averageRating = null;

        try {
            averageRating = reviewService.getAverageRatingByPlaceBetweenDates(place, startDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long reviewsCount = null;
        try {
            reviewsCount = reviewService.countReviewsByPlaceBetweenDates(place, startDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long totalReviews = reviewService.countReviewsByPlace(place);

        List<Review> reviewsList = null;
        try {
            reviewsList = reviewService.getReviewsByPlaceBetweenDates(place, startDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        model.addAttribute("location", location);
        model.addAttribute("overallRating", overallRating);
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("reviewsCount", reviewsCount);
        model.addAttribute("totalReviews", totalReviews);
        model.addAttribute("reviews", reviewsList);

        if (dateRange == null || dateRange.equals(""))
            dateRange = "today";

        model.addAttribute("dateRange", dateRange);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "locationdetail";
    }

    @ResponseBody
    @RequestMapping(value = "/fetch-rating-and-reviews/{locationId}", method = RequestMethod.GET, produces = "application/json")
    public void fetchRatingAndReviewsById(@PathVariable Integer locationId, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();

        Location location = locationService.getLocationById(locationId);
        Place place = placeService.getPlaceByLocation(location);
        PlaceDetail placeDetail = placeDetailService.getPlaceDetailByPlace(place);
        List<Review> reviews = reviewService.getReviewsByPlaceBetweenDates(place, null, null);
        Double rating = reviewService.getOverallRatingByPlace(place);

        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("rating", rating);
        jsonObject.addProperty("reviews", gson.toJson(reviews));

        writer.write(jsonObject.toString());

    }

    @RequestMapping(value = "/fetch-place-details", method = RequestMethod.GET)
    public String fetchPlaceDetails(ModelMap model) {

        List<Place> allPlaces = placeService.getAllPlaces();
        model.addAttribute("placesList", allPlaces);

        for (Place place : allPlaces) {
            String url = "https://maps.googleapis.com/maps/api/place/details/json?" +
                    "placeid=" + place.getPlaceId() +
                    "&key=" + "AIzaSyDE6hMNFUb_MgX1aVvtnpAu52mS_9pZ_Zs";

            try {
                PlaceDetail placeDetail = placeDetailService.fetchPlaceDetailByPlace(place, url);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "placeslist";
    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String home(ModelMap model) {

        List<Location> allLocations = locationService.getAllLocations();
        model.addAttribute("locationsList", allLocations);

        return "home";
    }

    @RequestMapping(value = "/marker-display", method = RequestMethod.GET)
    public String markerDisplay(ModelMap model) {
        Gson gson = new Gson();
        List<Location> allLocations = locationService.getAllLocations();
        model.addAttribute("locationsList", allLocations);
        model.addAttribute("locationsListJson", gson.toJson(allLocations));

        return "markerdisplay";
    }

    @RequestMapping(value = "/admin-dashboard", method = RequestMethod.GET)
    public String getAdminDashboard(ModelMap modelMap) {

        return "admindashboard";
    }

    @RequestMapping(value = "/users-list", method = RequestMethod.GET)
    public String getUsersList(ModelMap model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);

        return "userslist";
    }

    @RequestMapping(value = "/mailReport")
    public String mailReport(ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String fileName = "RatingAndReviewsReport_" + System.currentTimeMillis() + ".csv";
        //TODO update it0
        String startDate = "";
        String endDate = "";

        File csvFile = null;
        try {
            csvFile = getReportCSVFile(fileName, request, session, model);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String mailSubject = "Swachh Public Toilets | Rating and Reviews Report for " + startDate;

        String mailMessage = "Greetings!!<br><br>Please find attached Report for date between " + startDate + " and " + endDate
                + "<br><br><br>"
                + "----------------------<br>"
                + "Thanks & Regards<br>"
                + "Admin";
        //TODO update it
        String toAddresses = "prabhakarverma11@gmail.com,mohan.mahima9@gmail.com";

        try {
            mailService.sendAttachmentEmail(toAddresses, mailSubject, mailMessage, csvFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("successMsg", "Mail has been sent successfully");

        return "report";
    }


    /**
     * This method returns the principal[user-name] of logged-in user.
     */
    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    /**
     * This method returns true if users is already authenticated [logged-in], else false.
     */
    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }

    private File getReportCSVFile(String fileName, HttpServletRequest request,
                                  HttpSession session, ModelMap model) throws IOException {
        //TODO remove these lines
        List<Location> locations = locationService.getAllLocations();


        String startDate = "";
        String endDate = "";

        List<Report> reportsList = new ArrayList<>();

//        Location location = locationService.getLocationById(locationId);
        for (Location location : locations) {
            Place place = placeService.getPlaceByLocation(location);
            PlaceDetail placeDetail = placeDetailService.getPlaceDetailByPlace(place);

            Report report = new Report();
            report.setLocation(location);
            report.setPlace(place);
            report.setPlaceDetail(placeDetail);
            Long reviewsCount = null;
            try {
                reviewsCount = reviewService.countReviewsByPlaceBetweenDates(place, startDate, endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            report.setReviewsCount(reviewsCount);

            reportsList.add(report);
        }


        File csvFile = new File(fileName);
        csvFile.setReadable(true, false);
        csvFile.setWritable(true, false);
        csvFile.createNewFile();

        CSVWriter writer = new CSVWriter(new FileWriter(csvFile.getAbsolutePath()));

        writer.writeNext(new String[]{"S.No", "Name", "Address", "Country", "Latitude", "Longitude", "Rating", "Reviews"});

        String line = "";
        int count = 1;
        for (Report report : reportsList) {
            line = "";
            line += (count++) + "|";
            line += report.getLocation().getName() + "|";
            line += report.getLocation().getAddress() + "|";
            line += report.getLocation().getCountry() + "|";
            line += report.getLocation().getLatitude() + "|";
            line += report.getLocation().getLongitude() + "|";
            line += report.getPlaceDetail().getRating() + "|";
            line += report.getReviewsCount() + "|";

            writer.writeNext(line.split("\\|"));
        }
        writer.close();
        model.addAttribute("reportsList", reportsList);
        return csvFile;
    }

}