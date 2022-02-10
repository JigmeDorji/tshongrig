package com.spms.mvc.dto;

import java.util.List;

/**
 * Created by SonamPC on 29-Jan-17.
 */
public class SaleRecordCallingDTO {
    private List<SaleRecordDTO> saleRecordDTOs;

    public List<SaleRecordDTO> getSaleRecordDTOs() {
        return saleRecordDTOs;
    }

    public void setSaleRecordDTOs(List<SaleRecordDTO> saleRecordDTOs) {
        this.saleRecordDTOs = saleRecordDTOs;
    }
}
