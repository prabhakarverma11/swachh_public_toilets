package com.websystique.springmvc.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan({"com.websystique.springmvc.configuration"})
@PropertySource(value = {"classpath:application.properties"})
public class TaskSchedulerConfiguration {

    /**
     * annotated with @Scheduler must return void and must not have any parameters
     */
//    @Bean
//    public MyBean bean() {
//        return new MyBean();
////        System.out.println("I'm called by spring scheduler");
//    }

//    @Bean
//    public CronJobs beanCronJob() {
//        return new CronJobs();
//    }

}


