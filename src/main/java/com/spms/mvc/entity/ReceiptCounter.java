package com.spms.mvc.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by SonamPC on 28-Dec-16.
 */

@Entity
@Table(name = "tbl_receiptcounter")
public class ReceiptCounter implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "reciptserial")
    private Integer reciptserial;

    @Column(name = "autoMobileSerial")
    private Integer autoMobileSerial;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReciptserial() {
        return reciptserial;
    }

    public void setReciptserial(Integer reciptserial) {
        this.reciptserial = reciptserial;
    }


    public Integer getAutoMobileSerial() {
        return autoMobileSerial;
    }

    public void setAutoMobileSerial(Integer autoMobileSerial) {
        this.autoMobileSerial = autoMobileSerial;
    }
}
