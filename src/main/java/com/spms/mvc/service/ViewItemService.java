package com.spms.mvc.service;

import com.spms.mvc.dao.ViewItemDao;
import com.spms.mvc.dto.BrandDTO;
import com.spms.mvc.dto.PurchaseDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.NumberInWords;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Created by SonamPC on 15-Dec-16.
 */
@Service("viewItemService")
public class ViewItemService {

    @Autowired
    private ViewItemDao viewItemDao;

    public List<PurchaseDTO> getItemAvailable(CurrentUser currentUser, Date asOnDate) {
        return viewItemDao.getItemAvailable(currentUser.getCompanyId(),asOnDate);
    }

    public String getItemName(String itemCode, CurrentUser currentUser) {
        return viewItemDao.getItemName(itemCode,currentUser.getCompanyId());
    }

//    public Double getTotalStockValue1(CurrentUser currentUser) {
//        Double closingStock = 0.0;
//        for (PurchaseDTO purchaseDTO : viewItemDao.getItemAvailable(currentUser.getCompanyId())) {
//            closingStock = closingStock + (purchaseDTO.getQty().doubleValue() * purchaseDTO.getCostPrice());
//        }
//        return closingStock;
//    }

    public ModelAndView viewBrandWiseItemDetail(Integer brandId, Integer companyId, String loginId) {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        List<PurchaseDTO> brandWiseItemList = new ArrayList<>();
        if (brandId == 0) {
            parameterMap.put("reportTitle", "All Item Details");
            brandWiseItemList = viewItemDao.getAllBrandItemList(companyId);
        } else {
            parameterMap.put("reportTitle", "Brand Details");
            brandWiseItemList = viewItemDao.getBrandItemListByBrandId(brandId, companyId);
        }
        parameterMap.put("userName", loginId);
        parameterMap.put("printedDate", new Date());
        parameterMap.put("brandWiseItem_data_source", new JRBeanCollectionDataSource(brandWiseItemList, false));
        return new ModelAndView("brandWiseItem", parameterMap);

    }

    public Double getTotalStockBal(CurrentUser currentUser) {
        Double totalStockValue=viewItemDao.getTotalStockBal(currentUser.getCompanyId());
        return totalStockValue==null?0.0:totalStockValue;
    }
}
