package com.spms.mvc.service;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.Enumeration.BusinessType;
import com.spms.mvc.Enumeration.VoucherTypeEnum;
import com.spms.mvc.dao.RawMaterialStorageDao;
import com.spms.mvc.dto.*;
import com.spms.mvc.entity.RawMaterialStorage;
import com.spms.mvc.library.helper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("rawMaterialStorageService")
public class RawMaterialStorageService {


    @Autowired
    private RawMaterialStorageDao rawMaterialStorageDao;

    @Autowired
    PurchasesForRawMaterialService purchasesForRawMaterialService;





    //********************************************************************************************************************


    //********************************************************************************************************************
    public List<RawMaterialStorageViewDTO> getList(CurrentUser currentUser){
        return rawMaterialStorageDao.getList(currentUser);
    }
    public List<RawMaterialStorageViewDTO> getList(CurrentUser currentUser,Date asDate){
        return rawMaterialStorageDao.getList(currentUser,asDate);
    }









}

