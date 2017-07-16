package com.websystique.springmvc.cron;

import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by prabhakar on 4/7/17.
 */
public class MyBean {
    @Scheduled(initialDelay = 10000, fixedRate = 5000)
    public void printMessage() {
//        System.out.println("I am called by Spring scheduler");
    }
}
