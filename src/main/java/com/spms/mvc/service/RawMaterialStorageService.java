package com.spms.mvc.service;

import com.spms.mvc.dao.RawMaterialStorageDao;
import com.spms.mvc.dto.LocationSetUpDTO;
import com.spms.mvc.dto.RawMaterialStorageDTO;
import com.spms.mvc.dto.RawMaterialStorageViewDTO;
import com.spms.mvc.entity.RawMaterialStorage;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DateUtil;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("rawMaterialStorageService")
public class RawMaterialStorageService {


    @Autowired
    private RawMaterialStorageDao rawMaterialStorageDao;

    public ResponseMessage save(
            RawMaterialStorageDTO rawMaterialStorageDTO, CurrentUser currentUser,
            RawMaterialLocationSetupService rawMaterialLocationSetupService, AddItemService addItemService) {
        ResponseMessage responseMessage = new ResponseMessage();

        if (rawMaterialStorageDTO.getStorageModifier().equals("openingBalance")) {
            RawMaterialStorage rawMaterialStorage = new RawMaterialStorage();
            rawMaterialStorage.setParticularName(rawMaterialStorageDTO.getRawMaterialParticularName());
            rawMaterialStorage.setQuantity(rawMaterialStorageDTO.getRawMaterialParticularQty());

            rawMaterialStorage.setUnit(addItemService.getUnitList().get(rawMaterialStorageDTO.getRawMaterialParticularUnit() - 1).getText());
            rawMaterialStorage.setPrice(rawMaterialStorageDTO.getRawMaterialParticularPrice());


            //Getting all list from the location and using for raw material storage
            /*
            for(int j=0;j<rawMaterialLocationSetupService.getAllLocationList().size();j++){
                if(rawMaterialLocationSetupService.getAllLocationList().get(j).getLocationSetUpId()==rawMaterialStorageDTO.getRawMaterialParticularLocation()){
                    rawMaterialStorage.setLocation(rawMaterialLocationSetupService.getAllLocationList().get(j).getLocationId());
                }
            }
             */


            rawMaterialStorage.setLocation(rawMaterialLocationSetupService.getAllLocationList(rawMaterialStorageDTO.getRawMaterialParticularLocation()).get(0).getLocationId());


            rawMaterialStorage.setDate(rawMaterialStorageDTO.getOpenBalanceEntryDate());
            rawMaterialStorage.setStorageModifier("OpeningBalance");
            rawMaterialStorage.setCompanyId(currentUser.getCompanyId());
            rawMaterialStorage.setCompanyName(currentUser.getCompanyName());
            responseMessage.setStatus(1);
            responseMessage.setText("Successfully saved.");
            rawMaterialStorageDao.save(rawMaterialStorage);


            getList(currentUser,rawMaterialStorageDTO.getOpenBalanceEntryDate()).forEach(System.out::println);

            return responseMessage;
        }
        responseMessage.setText("Not Opening Balance .");
        return responseMessage;

    }
    public List<RawMaterialStorageViewDTO> getList(CurrentUser currentUser){
        return rawMaterialStorageDao.getList(currentUser);
    }
    public List<RawMaterialStorageViewDTO> getList(CurrentUser currentUser,Date asDate){
        return rawMaterialStorageDao.getList(currentUser,asDate);
    }



}

