package com.spms.mvc.entity;

import java.util.Date;
import javax.persistence.*;

/**
 * Description: PartyDetail
 * Date:  2020-Oct-28
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2020-Oct-28
 * Change Description:
 * Search Tag:
 */

@Entity
@Table(name = "tbl_acc_party_detail")
public class PartyDetail {

    @Id
    @Column(name = "partyId")
    private Integer partyId;

    @Column(name = "partyName")
    private String partyName;

    @Column(name = "partyAddress")
    private String partyAddress;

    @Column(name = "partyContactNo")
    private String partyContactNo;

    @Column(name = "companyId")
    private Integer companyId;

    @Column(name = "partyEmail")
    private String partyEmail;

    @Column(name = "setDate")
    private Date setDate;

    @Column(name = "createdBy")

    private String createdBy;

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyAddress() {
        return partyAddress;
    }

    public void setPartyAddress(String partyAddress) {
        this.partyAddress = partyAddress;
    }

    public String getPartyContactNo() {
        return partyContactNo;
    }

    public void setPartyContactNo(String partyContactNo) {
        this.partyContactNo = partyContactNo;
    }

    public String getPartyEmail() {
        return partyEmail;
    }

    public void setPartyEmail(String partyEmail) {
        this.partyEmail = partyEmail;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Date getSetDate() {
        return setDate;
    }

    public void setSetDate(Date setDate) {
        this.setDate = setDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
