package com.spms.mvc.dao;

import com.spms.mvc.dto.ServiceChargesDTO;
import com.spms.mvc.library.helper.DropdownDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by SonamPC on 28-Jul-17.
 */
@Repository("viewEmployeeServiceRecordDao")
public class ViewEmployeeServiceRecordDao {
    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<DropdownDTO> getEmployeeList() {
        String query = "SELECT empId AS value,\n" +
                "empName AS text FROM tbl_employeesetup;";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }




    @Transactional(readOnly = true)
    public List<ServiceChargesDTO> getEmployeeServiceDetails(Integer empId, Date fromDate, Date toDate) {

        String query = "select t.serviceWorth AS chargesAmount, \n" +
                "c.serviceDiscription AS particularOfService,\n" +
                "t.setDate AS serviceDate, \n" +
                "c.memoNo AS referenceMemoNo,\n" +
                "es.empName AS employeeName,\n" +
                "c.workOrderNo AS workOrderNo, \n" +
                "concat( coalesce(c.setDate, ''), ' ', coalesce(c.serviceCompletionTime, '00:00:00')) as serviceCompletionDateAndTime, \n" +
                "concat( coalesce(r.registration_date, ''), ' ', coalesce(r.registration_time, '00:00:00')) as registrationDateAndTime \n" +
                "FROM(SELECT a.mechanicId,a.serviceWorth,a.workMemo,a.setDate,a.workOrderNo \n" +
                "FROM tbl_servicesrecords a WHERE a.mechanicId=:empId and a.setDate between  :fromDate and :toDate  ) as t \n" +
                "INNER JOIN(SELECT b.memoNo,group_concat(b.particularOfService SEPARATOR ', ') AS serviceDiscription,\n" +
                "b.workOrderNo, b.setDate, b.serviceCompletionTime \n" +
                "FROM tbl_automobilerecord b WHERE b.setDate between :fromDate and :toDate  \n" +
                "GROUP BY b.memoNo \n" +
                "UNION \n" +
                "SELECT '' AS memoNo, group_concat(B.work_description SEPARATOR ', ') AS serviceDiscription, \n" +
                "A.workOrderNumber,A.setDate,'' AS serviceCompletionTime \n" +
                "FROM tbl_agencywiseworkorderservicedetails A \n" +
                "INNER JOIN tbl_agencywiseservicechargesetup B ON A.serviceChargeId=B.id \n" +
                "WHERE A.setDate between :fromDate and :toDate  \n" +
                "GROUP BY A.workOrderNumber) as c on t.workOrderNo  = c.workOrderNo \n" +
                "INNER JOIN tbl_employeesetup es on es.empId =t.mechanicId \n" +
                "INNER JOIN tbl_registration r on c.workOrderNo=r.registration_no where es.empId=:empId  \n" +
                "order by es.empName ASC";
     /*   String query = "select a.serviceWorth AS chargesAmount,\n" +
                "b.serviceDiscription AS particularOfService,\n" +
                "a.setDate AS serviceDate, \n" +
                "a.workMemo AS referenceMemoNo,\n" +
                "es.empName AS employeeName\n" +
                "FROM(SELECT a.mechanicId,a.serviceWorth,a.workMemo,a.setDate FROM tbl_servicesrecords a WHERE  a.mechanicId =:empId AND a.setDate between :fromDate and :toDate ) as a \n" +
                " INNER JOIN(SELECT b.memoNo,group_concat(b.particularOfService SEPARATOR ', ') AS serviceDiscription \n" +
                "FROM tbl_automobilerecord b where b.setDate between :fromDate and :toDate  GROUP BY b.memoNo) as b on a.workMemo  = b.memoNo\n" +
                "INNER JOIN tbl_employeesetup es on es.empId =a.mechanicId";*/
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("empId", empId)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setResultTransformer(Transformers.aliasToBean(ServiceChargesDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<ServiceChargesDTO> getAllEmployeeServiceDetails(Date fromDate, Date toDate) {
        String query = "select t.serviceWorth AS chargesAmount, \n" +
                "c.serviceDiscription AS particularOfService,\n" +
                "t.setDate AS serviceDate, \n" +
                "c.memoNo AS referenceMemoNo,\n" +
                "es.empName AS employeeName,\n" +
                "c.workOrderNo AS workOrderNo, \n" +
                "concat( coalesce(c.setDate, ''), ' ', coalesce(c.serviceCompletionTime, '00:00:00')) as serviceCompletionDateAndTime, \n" +
                "concat( coalesce(r.registration_date, ''), ' ', coalesce(r.registration_time, '00:00:00')) as registrationDateAndTime \n" +
                "FROM(SELECT a.mechanicId,a.serviceWorth,a.workMemo,a.setDate,a.workOrderNo \n" +
                "FROM tbl_servicesrecords a WHERE a.setDate between  :fromDate and :toDate  ) as t \n" +
                "INNER JOIN(SELECT b.memoNo,group_concat(b.particularOfService SEPARATOR ', ') AS serviceDiscription,\n" +
                "b.workOrderNo, b.setDate, b.serviceCompletionTime \n" +
                "FROM tbl_automobilerecord b WHERE b.setDate between :fromDate and :toDate  \n" +
                "GROUP BY b.memoNo \n" +
                "UNION \n" +
                "SELECT '' AS memoNo, group_concat(B.work_description SEPARATOR ', ') AS serviceDiscription, \n" +
                "A.workOrderNumber,A.setDate,'' AS serviceCompletionTime \n" +
                "FROM tbl_agencywiseworkorderservicedetails A \n" +
                "INNER JOIN tbl_agencywiseservicechargesetup B ON A.serviceChargeId=B.id \n" +
                "WHERE A.setDate between :fromDate and :toDate  \n" +
                "GROUP BY A.workOrderNumber) as c on t.workOrderNo  = c.workOrderNo \n" +
                "INNER JOIN tbl_employeesetup es on es.empId =t.mechanicId \n" +
                "INNER JOIN tbl_registration r on c.workOrderNo=r.registration_no  \n" +
                "order by es.empName ASC";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setResultTransformer(Transformers.aliasToBean(ServiceChargesDTO.class)).list();
    }
}
