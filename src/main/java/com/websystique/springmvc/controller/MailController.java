package com.websystique.springmvc.controller;

import au.com.bytecode.opencsv.CSVWriter;
import com.websystique.springmvc.model.Location;
import com.websystique.springmvc.model.PlaceULBMap;
import com.websystique.springmvc.model.Report;
import com.websystique.springmvc.service.LocationService;
import com.websystique.springmvc.service.MailServiceImpl;
import com.websystique.springmvc.service.PlaceULBMapService;
import com.websystique.springmvc.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/")
public class MailController {

    @Autowired
    LocationService locationService;

    @Autowired
    ReportService reportService;

    @Autowired
    MailServiceImpl mailService;

    @Autowired
    PlaceULBMapService placeULBMapService;

    @RequestMapping(value = "/mailReport")
    public String mailReport(ModelMap model) {
        String fileName = "RatingAndReviewsReport_" + System.currentTimeMillis() + ".csv";
        //TODO update it0
        String startDate = "";
        String endDate = "";

        File csvFile = null;
        try {
            csvFile = getReportCSVFile(fileName, model);
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


    private File getReportCSVFile(String fileName, ModelMap model) throws IOException {
        //TODO remove these lines
        List<Location> locations = locationService.getAllLocationsByPageAndSize(0, 500);


        String startDate = "";
        String endDate = "";

        List<Report> reportsList = reportService.getReportsListByLocationsBetweenDates(locations, startDate, endDate);


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

    @RequestMapping(value = "/mail-location-data")
    public String mailLocationData(ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String fileName = "LocationDataWithDistrictName_" + System.currentTimeMillis() + ".csv";

        File csvFile = null;
        try {
            csvFile = getReportCSVFileForLocation(fileName, request, session, model);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String mailSubject = "Swachh Public Toilets | Location data with District Name ";

        String mailMessage = "Greetings!!<br><br>Please find attachment of location data with district name and pin code"
                + "<br><br><br>"
                + "----------------------<br>"
                + "Thanks & Regards<br>"
                + "Prabhakar";

        //TODO update it
        String toAddresses = "prabhakarverma11@gmail.com,mohan.mahima9@gmail.com";

        try {
            mailService.sendAttachmentEmail(toAddresses, mailSubject, mailMessage, csvFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("successMsg", "Mail has been sent successfully");

        return "home";
    }

    private File getReportCSVFileForLocation(String fileName, HttpServletRequest request, HttpSession session, ModelMap model) throws IOException {
        //TODO remove these lines
        List<PlaceULBMap> placeULBMaps = placeULBMapService.getAllPlaceULBMapByPageAndSize(0, 5200);

        File csvFile = new File(fileName);
        csvFile.setReadable(true, false);
        csvFile.setWritable(true, false);
        csvFile.createNewFile();

        CSVWriter writer = new CSVWriter(new FileWriter(csvFile.getAbsolutePath()));

        writer.writeNext(new String[]{"S.No", "Location Id", "Address", "Latitude", "Longitude", "Pin Code", "District"});

        String line = "";
        int count = 1;
        for (PlaceULBMap placeULBMap : placeULBMaps) {
            line = "";
            line += (count++) + "|";
            line += placeULBMap.getPlace().getLocation().getId() + "|";
            line += placeULBMap.getPlace().getLocation().getAddress() + "|";
            line += placeULBMap.getPlace().getLocation().getLatitude() + "|";
            line += placeULBMap.getPlace().getLocation().getLongitude() + "|";
            line += placeULBMap.getPostalCode() + "|";
            line += placeULBMap.getULBName() + "|";

            writer.writeNext(line.split("\\|"));
        }
        writer.close();
        return csvFile;
    }

}