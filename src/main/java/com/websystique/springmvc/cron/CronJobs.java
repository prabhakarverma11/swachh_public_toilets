package com.websystique.springmvc.cron;

import au.com.bytecode.opencsv.CSVWriter;
import com.websystique.springmvc.model.Location;
import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.Report;
import com.websystique.springmvc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by prabhakar on 3/7/17.
 */
public class CronJobs {

    private static String[] API_KEY = {
            "AIzaSyD304cZovgQsVQt2vaAILBdD3bD5pFHcx0",
            "AIzaSyBqdMkhD5uNjfCbCrDUr5L3l0qGEgWKBFI",
            "AIzaSyBKgJqF-l_X5gywxnMpqYgO_eR8G7GiIwA",
            "AIzaSyArXKSjF_3LD9pr-vn7CYtVo9ar7wbsP54",
            "AIzaSyBu0NRIzM_NSNphZH2zTy-7tPiqrSmO4AA",
            "AIzaSyCXC3L1bLCp_sFUDxtqtrRP43uIHOfldss",
            "AIzaSyBkLfT8KwQu1N1Oncaky_O7WzcH0rdOSuI",
            "AIzaSyD6YNI_T7cKw26Oq5A_CBhQY0JhFyVFl7A",
            "AIzaSyDVhfr53ZyIV4ms0UUz9026Ip3bxtODdR4"
    };
    private static int index = 0;

    @Autowired
    MailServiceImpl mailService;

    @Autowired
    LocationService locationService;

    @Autowired
    ReportService reportService;

    @Autowired
    PlaceService placeService;

    @Autowired
    PlaceDetailService placeDetailService;

    @Scheduled(fixedRate = 600000)
    public void sendDailyReport() throws ParseException {

        String today = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        String fileName = "RatingAndReviewsReport_" + today + ".csv";
        //TODO update it0

        File csvFile = null;
        try {
            csvFile = getReportCSVFile(fileName, today);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String mailSubject = "Swachh Public Toilets | Rating and Reviews Report for " + today;

        String mailMessage = "Greetings!!<br><br>Please find attached Report for the date:  " + today
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
    }

    @Scheduled(fixedRate = 3 * 57000)
    public void fetchGoogleApisAndUpdateData() {
        List<Place> allPlaces = placeService.getAllPlacesByPageAndSize(0, 10);

        for (Place place : allPlaces) {
            String url = "https://maps.googleapis.com/maps/api/place/details/json?" +
                    "placeid=" + place.getPlaceId() +
                    "&key=" + API_KEY[index];
            try {
                String response = placeDetailService.fetchPlaceDetailByPlace(place, url);
                if (response.equals("OVER_QUERY_LIMIT")) {
                    if (index < API_KEY.length - 1) {
                        index++;
                        System.out.println("\n\nAPI_KEY changed to- " + API_KEY[index]);
                    } else {
                        index = 0;
                        System.out.println("\n\nAPI_KEY changed to- " + API_KEY[index]);
                    }
                }
                System.out.println("Done with place id: " + place.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File getReportCSVFile(String fileName, String date) throws IOException {
        //TODO update it
        List<Location> locations = locationService.getAllLocationsByPageAndSize(0, 10);

        List<Report> reportsList = reportService.getReportsListByLocationsBetweenDates(locations, date, date);


        File csvFile = new File(fileName);
        csvFile.setReadable(true, false);
        csvFile.setWritable(true, false);
        csvFile.createNewFile();

        CSVWriter writer = new CSVWriter(new FileWriter(csvFile.getAbsolutePath()));

        writer.writeNext(new String[]{"S. No.", "Name", "Address", "Country", "Latitude", "Longitude", "Avg. Rating", "Reviews"});

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
            line += report.getAverageRating() + "|";
            line += report.getReviewsCount() + "|";

            writer.writeNext(line.split("\\|"));
        }
        writer.close();
        return csvFile;
    }
}
