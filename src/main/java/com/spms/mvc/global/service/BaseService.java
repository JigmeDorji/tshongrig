/**
 * Name: BaseService
 * Description: See the description at the top of class declaration
 * Project: Spare part management
 *
 * @author: bikash.rai
 * Creation: 29-Apr-2016
 * @version: 1.0.0
 * @since 2016
 * Language: Java 1.8.0_20
 * Copyright: (C) 2016
 */
package com.spms.mvc.global.service;

import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.report.domain.dto.ReportRequestDto;
import com.spms.mvc.report.domain.dto.ReportResponseDto;
import com.spms.mvc.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseService {
    //region private variables
    protected ResponseMessage responseMessage;
    //endregion

    @Autowired
    SessionFactory sessionFactory;

//    protected EntityManager em;

//    @PersistenceContext(unitName = "default")
//    public void setEm(EntityManager em) {
//        this.em = em;
//    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    //region setter and getter
    public void setResponseMessage(ResponseMessage responseMessage) {
        this.responseMessage = responseMessage;
    }

    @Transactional
    public ReportResponseDto generateReport(String reportType, Map<String, Object> params, String reportPath,
                                            String outputPath, String userId, String reportJRXML,
                                            String reportName) throws ClassNotFoundException, SQLException, JRException {

//        Class.forName("com.mysql.jdbc.Driver");
//        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bcs_db?zeroDateTimeBehavior=convertToNull", "root", "root");
//
        Connection connection = ((SessionImpl) getCurrentSession()).connection();
        reportPath = reportPath.replace("\\", "/");

        String headerName = "";
        String footerName = "";

        ReportRequestDto requestDto = new ReportRequestDto(outputPath, reportName,
                reportType, reportPath + headerName,
                reportPath + reportJRXML,
                reportPath + footerName,
                reportPath, params, connection, userId
        );
        return ReportService.createReport(requestDto);
    }

    /**
     * load month list
     *
     * @return getMonthList
     */
    public List<DropdownDTO> getMonthList() {
        int monthId = 0;
        List<DropdownDTO> monthsList = new ArrayList<>();
        String[] months = new DateFormatSymbols().getMonths();
        for (String month : months) {
            DropdownDTO dropdownDTO = new DropdownDTO();
            monthId = monthId + 1;
            dropdownDTO.setText(month);
            dropdownDTO.setValue(monthId);
            if (!month.equals("")) {
                monthsList.add(dropdownDTO);
            }
        }
        return monthsList;
    }
}
