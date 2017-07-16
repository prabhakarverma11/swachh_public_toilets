package com.websystique.springmvc.configuration;

import com.websystique.springmvc.cron.CronJobs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan({"com.websystique.springmvc.configuration"})
@PropertySource(value = {"classpath:application.properties"})
public class TaskSchedulerConfiguration {

    @Bean
    public CronJobs beanCronJob() {
        return new CronJobs();
    }

}


