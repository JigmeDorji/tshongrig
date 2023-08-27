package com.spms.mvc.web.fileUpload.services;

import com.spms.mvc.web.fileUpload.dao.ClearBinScheduleTaskDao;
import com.spms.mvc.web.fileUpload.dao.ViewFilesDao;
import com.spms.mvc.web.fileUpload.dto.FileParamsDTO;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClearBinScheduleTaskService {


    @Autowired
    private ClearBinScheduleTaskDao clearBinScheduleTaskDao;

    @Autowired
    ViewFilesDao viewFilesDao;

    @Scheduled(cron = "0 0 0 * * *")//Execute at midnight on the last day of the month
    public void scheduledExecutor() throws XmlPullParserException, IOException, URISyntaxException {
        LocalDate currentDate = LocalDate.now();
        int lastDayOfMonth = currentDate.lengthOfMonth();
        if (currentDate.getDayOfMonth() == lastDayOfMonth) {
            // Execute your logic for the last day of the month
            List<FileParamsDTO> filesList = clearBinScheduleTaskDao.fileParamsDTOS();
            if (filesList.size() > 0) {
//            System.out.println(filesList);
                onPermanentlyDelete(filesList);
            }
        }
    }

//
////    @Scheduled(cron = "0 0 0 L * ?") // Execute at midnight on the last day of the month
//        @Scheduled(cron = "0 0 0 L * *") // Execute at midnight on the last day of the month
//        public void schedulaedExecutor () throws URISyntaxException, IOException, XmlPullParserException {
//            List<FileParamsDTO> filesList = clearBinScheduleTaskDao.fileParamsDTOS();
//            if (filesList.size() > 0) {
////            System.out.println(filesList);
//                onPermanentlyDelete(filesList);
//            }
//        }

        private void onPermanentlyDelete (List < FileParamsDTO > filesList) throws
        XmlPullParserException, IOException, URISyntaxException {
            FilePath filePath = new FilePath();

            for (FileParamsDTO fileParam : filesList) {
                ArrayList<String> fileLocations = new ArrayList<>();
                fileLocations.add("resources");//src
                fileLocations.add("fileCenter");//section
                fileLocations.add(fileParam.getFolderName());//folder/file
                fileLocations.add(fileParam.getFileName());//fileName
                File file = new File(filePath.filePath(fileLocations));
                if (file.exists()) {
//                System.out.println("File Exist@");
                    file.delete();
                    viewFilesDao.onDeleteBtn(fileParam.getId(), fileParam.getCompanyId());
                }
//            Clearing the Location Array List
                fileLocations.clear();


            }
        }


    }


