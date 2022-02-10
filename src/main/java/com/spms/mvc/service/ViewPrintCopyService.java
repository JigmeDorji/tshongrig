package com.spms.mvc.service;

import com.spms.mvc.dao.ViewPrintCopyDao;
import com.spms.mvc.dto.ViewPrintCopyDTO;
import com.spms.mvc.dto.ViewPrintCopyDTOList;
import com.spms.mvc.entity.ViewPrintEntityDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by Bcass Sawa on 4/17/2019.
 */
@Service("viewPrintCopyService")
public class ViewPrintCopyService {

    @Autowired
    private ViewPrintCopyDao viewPrintCopyDao;

    public List<ViewPrintCopyDTO> getSpecificReportsDetailsByNodeId(Integer id) {

        List<ViewPrintCopyDTO> viewPrintCopyDTOList = new ArrayList<>();

        for (ViewPrintCopyDTO viewPrintCopyListDTO : viewPrintCopyDao.getSpecificReportsDetailsByNodeId(id)) {
            ViewPrintCopyDTO viewPrintCopyDTO = new ViewPrintCopyDTO();
            viewPrintCopyDTO.setText(viewPrintCopyListDTO.getText());
            viewPrintCopyDTO.setType("child");
            viewPrintCopyDTOList.add(viewPrintCopyDTO);
        }
        return viewPrintCopyDTOList;
    }

    /**
     * view pdf file
     *
     * @param pdfFileName pdfFileName
     * @return HttpEntity<byte[]>
     * @throws IOException
     */
    public HttpEntity<byte[]> displayPDFFile(String pdfFileName) throws IOException {
        byte[] fileContent = null;
        HttpHeaders httpHeaders = new HttpHeaders();

        String fileFolderLocation = loadReportConfiguration()
                .getProperty("report_location_path.file_location_path");
        Path filePath = Paths.get(fileFolderLocation, pdfFileName + ".pdf");

        if (Files.exists(filePath)) {
            httpHeaders.setContentType(MediaType.parseMediaType("application/pdf"));
            httpHeaders.add("Content-Disposition", "inline; fileName=" + pdfFileName + ".pdf");
            fileContent = Files.readAllBytes(filePath);
            httpHeaders.setContentLength(fileContent.length);
        }
        return new HttpEntity<>(fileContent, httpHeaders);

    }

    private Properties loadReportConfiguration() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("report_location_path.properties");
        properties.load(inputStream);
        return properties;

    }

    public ViewPrintCopyDTO getParentNodeJsTree() {

        ViewPrintCopyDTO viewPrintCopyDTO;
        ViewPrintCopyDTOList viewPrintCopyDTOList;
        List<ViewPrintCopyDTOList> viewPrintCopyDTOListData = new ArrayList<>();

        viewPrintCopyDTO = viewPrintCopyDao.getRootParentNodeJsTree();
        viewPrintCopyDTO.setId(viewPrintCopyDTO.getId());
        viewPrintCopyDTO.setText(viewPrintCopyDTO.getText());

        for (ViewPrintCopyDTO viewPrintCopyDTOII : viewPrintCopyDao.getParentNodeJsTree()) {
            viewPrintCopyDTOList = new ViewPrintCopyDTOList();
            viewPrintCopyDTOList.setId(viewPrintCopyDTOII.getId());
            viewPrintCopyDTOList.setText(viewPrintCopyDTOII.getText());
            viewPrintCopyDTOList.setChildren(Boolean.TRUE);
            viewPrintCopyDTOListData.add(viewPrintCopyDTOList);
        }

        viewPrintCopyDTO.setChildren(viewPrintCopyDTOListData);
        return viewPrintCopyDTO;
    }

    public void saveToViewPrintCopyDetails(String reportName, Integer reportType) {
        ViewPrintEntityDetail viewPrintEntityDetail = new ViewPrintEntityDetail();
        viewPrintEntityDetail.setParentId(reportType);
        viewPrintEntityDetail.setReportName(reportName);
        viewPrintEntityDetail.setDate(new Date());
        viewPrintCopyDao.saveToViewPrintCopyDetails(viewPrintEntityDetail);
    }
}
