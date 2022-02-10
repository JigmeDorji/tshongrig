package com.spms.mvc.dao;

import com.spms.mvc.dto.RegistrationDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by SonamPC on 20-Feb-19.
 */
@Repository("workOrderLedgerDao")
public class WorkOrderLedgerDao {
    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<RegistrationDTO> getWorkOrderClosedList() {
        String query = "select registration_no AS registration_no,\n" +
                "A.agency_id AS agencyId,\n" +
                "registration_date AS registration_date,\n" +
                "vehicle_no AS vehicle_no,\n" +
                "contact_no AS contact_no,\n" +
                "customer_name AS customer_name,\n" +
                "vehicle_type AS vehicle_type,\n" +
                "(IFNULL(B.partsAmt,0)+IFNULL(C.serviceAmt,0)) AS totalAmt,\n" +
                "A.statusFlag\n" +
                "from tbl_registration A\n" +
                "LEFT JOIN(SELECT workOrderNumber,IFNULL(SUM(rate * qty),0) AS partsAmt from tbl_workorderpartsdetails group by workOrderNumber) B ON A.registration_no=B.workOrderNumber\n" +
                "LEFT JOIN (select workOrderNumber,IFNULL(SUM(chargesAmount),0) AS serviceAmt from tbl_workorderservicedetails group by workOrderNumber) C ON C.workOrderNumber=A.registration_no\n" +
                "where A.statusFlag='X' AND A.agency_id IS  NULL OR A.agency_id =''\n" +
                "UNION\n" +
                "select registration_no AS registration_no,\n" +
                "A.agency_id AS agencyId,\n" +
                "registration_date AS registration_date,\n" +
                "vehicle_no AS vehicle_no,\n" +
                "contact_no AS contact_no,\n" +
                "customer_name AS customer_name,\n" +
                "vehicle_type AS vehicle_type,\n" +
                "(IFNULL(B.partsAmt,0)+IFNULL(C.serviceAmt,0)) AS totalAmt,\n" +
                "A.statusFlag\n" +
                "from tbl_registration A\n" +
                "LEFT JOIN(SELECT workOrderNumber,IFNULL(SUM(rate * qty),0) AS partsAmt from tbl_agencywiseworkorderpartsdetails group by workOrderNumber) B ON A.registration_no=B.workOrderNumber\n" +
                "LEFT JOIN (select workOrderNumber,IFNULL(SUM(chargesAmount),0) AS serviceAmt from tbl_agencywiseworkorderservicedetails group by workOrderNumber) C ON C.workOrderNumber=A.registration_no\n" +
                "where A.statusFlag='X' AND A.agency_id IS NOT NULL AND A.agency_id <>''";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(RegistrationDTO.class)).list();


    }
}
