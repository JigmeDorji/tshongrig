package com.spms.mvc.service;

import com.spms.mvc.dao.AddItemDao;
import com.spms.mvc.dao.BarcodeDao;
import com.spms.mvc.dto.PurchaseDTO;
import com.spms.mvc.dto.SaleRecordDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SonamPC on 20-Dec-16.
 */
@Service("barcodeService")
public class BarcodeService {

    @Autowired
    private BarcodeDao barcodeDao;

    @Autowired
    private AddItemDao addItemDao;


    public List<SaleRecordDTO> getItemCodeList(String itemCode, CurrentUser currentUser, Integer qty) {
        PurchaseDTO purchaseDTO = addItemDao.getItemDetailsByItemCode(itemCode,currentUser.getCompanyId());
        List<SaleRecordDTO> saleRecordDTOs = new ArrayList<>();
        for (int i = qty; i > 0; i--) {
            SaleRecordDTO saleRecordDTO = new SaleRecordDTO();
            saleRecordDTO.setItemCode(purchaseDTO.getItemCode());
            saleRecordDTO.setItemName(purchaseDTO.getItemName());
            saleRecordDTO.setSellingPrice(purchaseDTO.getSellingPrice());
            saleRecordDTO.setCompanyName(currentUser.getCompanyName());
            saleRecordDTOs.add(saleRecordDTO);
        }
        return saleRecordDTOs;
    }

    public List<DropdownDTO> getItemList(CurrentUser currentUser) {
        return addItemDao.getItemList(currentUser);
    }


   /* public List<SaleReportDTO> getReport() {
        return barcodeDao.getReport();
    }*/
}
