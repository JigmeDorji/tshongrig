package com.spms.mvc.web.fileUpload.services;

import com.spms.mvc.web.fileUpload.dao.ClearBinScheduleTaskDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ClearBinScheduleTaskService {

    @Autowired
    private ClearBinScheduleTaskDao clearBinScheduleTaskDao;
//    @Scheduled(cron = "0 0 0 L * ?") // Execute at midnight on the last day of the month
    @Scheduled(cron = "0 */3 * ? * *")
    public void runTask() {


        // Your task logic goes here
        System.out.println("Task executed!");
    }
}
