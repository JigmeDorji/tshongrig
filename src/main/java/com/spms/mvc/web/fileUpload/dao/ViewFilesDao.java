package com.spms.mvc.web.fileUpload.dao;

import com.spms.mvc.web.fileUpload.dto.FileParamsDTO;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public class ViewFilesDao {
    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    public List<FileParamsDTO> getViewFileOnCondition(Integer companyId, int isNew, int isCompleted, int isMovedToBin, int isRetrieved) {
        String query = "SELECT  *  from tbl_file_params  where companyId=:companyId and isNew=:isNew and isCompleted=:isCompleted and isMovedToBin=:isMovedToBin and isRetrieved=:isRetrieved ORDER BY id DESC";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setParameter("isNew", isNew)
                .setParameter("isCompleted", isCompleted)
                .setParameter("isMovedToBin", isMovedToBin)
                .setParameter("isRetrieved", isRetrieved)
                .setResultTransformer(Transformers.aliasToBean(FileParamsDTO.class)).list();
    }

    @Transactional
    public FileParamsDTO getFileById(Integer fileId, Integer companyId) {
        String query = "SELECT  *  from tbl_file_params  where id=:id and companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return (FileParamsDTO) session.createSQLQuery(query)
                .setParameter("id", fileId)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(FileParamsDTO.class)).uniqueResult();

    }

    @Transactional
    public boolean isMarkedCompleted(Integer fileId, Integer companyId) {


        String sqlQry = "UPDATE tbl_file_params SET isNew = 0,isMovedToBin=0, movedToBinDate=null,isCompleted = 1,isRetrieved=0, RetrievedDate=null WHERE id = :id AND companyId = :companyId";
        int rowsUpdated = sessionFactory.getCurrentSession()
                .createSQLQuery(sqlQry)
                .setParameter("id", fileId)
                .setParameter("companyId", companyId)
//
                .executeUpdate();

        return rowsUpdated > 0;
    }

    @Transactional
    public boolean isMoveFileToBin(Integer fileId, Integer companyId) {
        Date date = new Date();
        String sqlQry = "UPDATE tbl_file_params SET isNew = 0,isMovedToBin=1, isCompleted = 0,movedToBinDate=:movedToBinDate WHERE id = :id AND companyId = :companyId";
        int rowsUpdated = sessionFactory.getCurrentSession()
                .createSQLQuery(sqlQry)
                .setParameter("id", fileId)
                .setParameter("companyId", companyId)
                .setParameter("movedToBinDate", date)
                .executeUpdate();

        return rowsUpdated > 0;
    }

    @Transactional
    public void onDeleteBtn(Integer fileId, Integer companyId) {
        String sqlQry = "delete FROM tbl_file_params  where companyId=:companyId and id=:id";
        SQLQuery hibernate = sessionFactory.getCurrentSession().createSQLQuery(sqlQry);
        hibernate.setParameter("id", fileId)
                .setParameter("companyId", companyId).executeUpdate();
    }


    @Transactional
    public boolean isRetrieved(Integer fileId, Integer companyId) {
        Date date = new Date();
//        String sqlQry = "UPDATE tbl_file_params SET isNew = 0,isMovedToBin=0, isCompleted = 0,movedToBinDate=null,createdDate=:createdDate WHERE id = :id AND companyId = :companyId";
        String sqlQry = "UPDATE tbl_file_params SET isNew = 0,isMovedToBin=0,isRetrieved=1,isCompleted = 0,movedToBinDate=null,RetrievedDate=:RetrievedDate WHERE id = :id AND companyId = :companyId";

        int rowsUpdated = sessionFactory.getCurrentSession()
                .createSQLQuery(sqlQry)
                .setParameter("id", fileId)
                .setParameter("companyId", companyId)
                .setParameter("RetrievedDate", date)
                .executeUpdate();

        return rowsUpdated > 0;
    }
}
