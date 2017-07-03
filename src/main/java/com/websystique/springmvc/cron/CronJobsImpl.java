package com.websystique.springmvc.cron;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by prabhakar on 3/7/17.
 */
public class CronJobsImpl implements CronJobs {

    @Autowired
    TaskExecutor taskExecutor;

    @Transactional
    @Async
    @Override
    public void sendDailyReport() {
        this.taskExecutor.execute(new Runnable() {
            public void run() {

            }
        });
    }
}
