package com.websystique.springmvc.utils;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by prabhakar on 23/6/17.
 */
public class UtilConstants {

    public static String hostname;
    public static String port;
    public static String username;
    public static String password;
    public static String auth;

    public static String fromAddress;
    public static String toAddresses;
    public static String ccAddresses;
    public static String bccAddresses;

    static {
        Properties systemProp = null;
        URL url = null;
        systemProp = new Properties();
        url = UtilConstants.class.getResource("/mail.properties");

        try {
            systemProp.load(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        hostname = systemProp.getProperty("hostname");
        port = systemProp.getProperty("port");
        username = systemProp.getProperty("username");
        password = systemProp.getProperty("password");
        auth = systemProp.getProperty("auth");

        fromAddress = systemProp.getProperty("fromAddress");
        toAddresses = systemProp.getProperty("toAddresses");
        ccAddresses = systemProp.getProperty("ccAddresses");
        bccAddresses = systemProp.getProperty("bccAddresses");
        systemProp.clear();
    }
}
