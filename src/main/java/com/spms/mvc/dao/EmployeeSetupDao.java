package com.spms.mvc.dao;

import com.spms.mvc.dto.AddItemCategoryDTO;
import com.spms.mvc.dto.EmployeeSetupDTO;
import com.spms.mvc.entity.EmployeeSetup;
import com.spms.mvc.library.helper.DropdownDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by SonamPC on 05-Mar-17.
 */
@Repository("employeeSetupDao")
public class EmployeeSetupDao {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    public void saveEmployeeSetup(EmployeeSetup employeeSetup) {
        sessionFactory.getCurrentSession().save(employeeSetup);
    }

    @Transactional
    public void updateEmployeeSetup(EmployeeSetup employeeSetup) {
        sessionFactory.getCurrentSession().update(employeeSetup);
    }

    @Transactional(readOnly = true)
    public List<EmployeeSetupDTO> getEmpSetupList(Integer companyId) {
        String query = "Select empId,empName,dateOfBirth,cidNo,contactNo,tpnNo,dateOfAppointment,basicSalary,post,incrementAmount,incrementEffectDate,\n" +
                "serviceType,\n" +
                "ifnull(allowance,0) as allowance, emailAddress,accNo,village,gewog,dzongkhag,companyId,createdBy,createdDate,pF,gIS,cost,\n" +
                "                (basicSalary+ifnull(allowance,0)) as grossSalary,  \n" +
                "                ((basicSalary+ifnull(allowance,0))-(pF+gIS)) as netSalary, \n" +
                "                ((basicSalary+ifnull(allowance,0))*0.01) as hC, \n" +
                "                ((basicSalary+ifnull(allowance,0))*0.02) as tDS \n" +
                "                FROM tbl_hr_employeesetup where companyId=:companyId order by empName asc";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(EmployeeSetupDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public EmployeeSetupDTO getEmpSetUpDetail(String empId) {
        String query = "SELECT * FROM tbl_hr_employeesetup \n" +
                "WHERE empId=:empId";
        Session session = sessionFactory.getCurrentSession();
        return (EmployeeSetupDTO) session.createSQLQuery(query)
                .setParameter("empId", empId)
                .setResultTransformer(Transformers.aliasToBean(EmployeeSetupDTO.class)).uniqueResult();
    }

    @Transactional(readOnly = true)
    public AddItemCategoryDTO getCategoryNameCode(String categoryName) {
        String query = "select itemcategoryid AS itemCategoryId from tbl_itemcategory where itemcategoryname=:categoryName";
        Session session = sessionFactory.getCurrentSession();
        return (AddItemCategoryDTO) session.createSQLQuery(query)
                .setParameter("categoryName", categoryName)
                .setResultTransformer(Transformers.aliasToBean(AddItemCategoryDTO.class)).uniqueResult();
    }

    @Transactional(readOnly = true)
    public AddItemCategoryDTO checkItemIsThereOrNot(Integer itemCategoryId) {
        String query = "select itemCategoryId AS itemCategoryId from tbl_itemmaster where itemCategoryId=:itemCategoryId limit 1";
        Session session = sessionFactory.getCurrentSession();
        return (AddItemCategoryDTO) session.createSQLQuery(query)
                .setParameter("itemCategoryId", itemCategoryId)
                .setResultTransformer(Transformers.aliasToBean(AddItemCategoryDTO.class)).uniqueResult();
    }


    @Transactional(readOnly = true)
    public List<DropdownDTO> getServiceTypeList() {
        String query = "SELECT id AS valueInteger, serviceType AS text FROM tbl_hr_service_type";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getEmployeeList(Integer companyId) {
        String query = "SELECT empId AS id, concat(empName,'(',empId,')') AS text FROM tbl_hr_employeesetup\n" +
                "where companyId=:companyId order by empName asc";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(DropdownDTO.class))
                .setParameter("companyId",companyId)
                .list();
    }

    @Transactional(readOnly = true)
    public boolean checkIsCIDAndBankAccNoAlreadyExist(Integer companyId, String cidNo, String accNo,String empId ) {
        String query = "SELECT empId FROM tbl_hr_employeesetup where companyId=:companyId and (cidNo=:cidNo OR accNo=:accNo) and (:empId is null or empId!=:empId)";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(EmployeeSetupDTO.class))
                .setParameter("companyId",companyId)
                .setParameter("cidNo",cidNo)
                .setParameter("accNo",accNo)
                .setParameter("empId",empId)
                .list().size()!= 0;
    }
}
