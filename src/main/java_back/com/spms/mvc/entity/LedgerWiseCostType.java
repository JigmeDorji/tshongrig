package com.spms.mvc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

/**
 * Description: LedgerWiseCostType
 * Date:  2022-Jan-28
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2022-Jan-28
 * Change Description:
 * Search Tag:
 */
@Entity
@Table(name = "tbl_acc_ledger_wise_cost_type")
public class LedgerWiseCostType extends BaseEntity {
    @Id
    @Column(name = "id")
    private BigInteger id;

    @Column(name = "ledgerId")
    private String ledgerId;

    @Column(name = "costTypeId")
    private Integer costTypeId;

    @Column(name = "companyId")
    private Integer companyId;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }

    public Integer getCostTypeId() {
        return costTypeId;
    }

    public void setCostTypeId(Integer costTypeId) {
        this.costTypeId = costTypeId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
