package com.spms.mvc.library.helper;

import com.spms.mvc.dao.FixedAssetSaleDao;
import org.springframework.beans.factory.annotation.Autowired;

public class MonthlyDepreciationSchedule {

    @Autowired
    private FixedAssetSaleDao fixedAssetSaleDao;

    public final void monthlyDepreciationUpdate() {
        fixedAssetSaleDao.monthlyDepreciationUpdate();
    }
}
