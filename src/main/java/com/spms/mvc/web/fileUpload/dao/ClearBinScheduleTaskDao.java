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
public class ClearBinScheduleTaskDao {


    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    public List<FileParamsDTO> fileParamsDTOS() {

        String query = " SELECT * FROM tbl_file_params WHERE \n" +
                "                isNew=0 and isCompleted=0 and isMovedToBin=1  and \n" +
                "              movedToBinDate <= DATE_SUB(NOW(), INTERVAL 1 MONTH) || (RetrievedDate <= DATE_SUB(NOW(), INTERVAL 1 MONTH)  and   isRetrieved=1) ";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setResultTransformer(Transformers.aliasToBean(FileParamsDTO.class)).list();
    }

}
