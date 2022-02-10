package com.spms.mvc.dao;

import com.spms.mvc.dto.RegistrationDTO;
import com.spms.mvc.dto.ViewPrintCopyDTO;
import com.spms.mvc.dto.ViewPrintCopyDTOList;
import com.spms.mvc.entity.ViewPrintEntityDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Bcass Sawa on 4/17/2019.
 */
@Repository("viewPrintCopyDao")
public class ViewPrintCopyDao {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<ViewPrintCopyDTO> getSpecificReportsDetailsByNodeId(Integer id) {
        String query = "SELECT reportName AS text FROM tbl_viewprintcopydetail where parentId=:id";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("id", id)
                .setResultTransformer(Transformers.aliasToBean(ViewPrintCopyDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<ViewPrintCopyDTO> getParentNodeJsTree() {
        String query = "SELECT  parentId as id,childParentName AS text FROM tbl_viewprintcopy WHERE id <> 1";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setResultTransformer(Transformers.aliasToBean(ViewPrintCopyDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public ViewPrintCopyDTO getRootParentNodeJsTree() {
        String query = "SELECT childParentName AS text FROM tbl_viewprintcopy WHERE id=1";
        Session session = sessionFactory.getCurrentSession();
        return (ViewPrintCopyDTO) session.createSQLQuery(query)
                .setResultTransformer(Transformers.aliasToBean(ViewPrintCopyDTO.class))
                .uniqueResult();
    }

    @Transactional
    public void saveToViewPrintCopyDetails(ViewPrintEntityDetail viewPrintEntityDetail) {
        sessionFactory.getCurrentSession().save(viewPrintEntityDetail);
    }
}
