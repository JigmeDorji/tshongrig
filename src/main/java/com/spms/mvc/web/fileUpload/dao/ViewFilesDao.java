package com.spms.mvc.web.fileUpload.dao;

import com.spms.mvc.web.fileUpload.dto.FileParamsDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ViewNewFilesDao {
    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    public List<FileParamsDTO> onlyNewFilesParamsList(Integer companyId) {
        String query = "SELECT  *  from tbl_file_params  where companyId=:companyId and ";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(FileParamsDTO.class)).list();
    }
}
