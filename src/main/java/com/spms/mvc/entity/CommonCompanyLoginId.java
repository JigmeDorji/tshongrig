package com.spms.mvc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_common_company_login_id")
public class CommonCompanyLoginId {
    @Id
    @Column(name = "id")
   private int id;
    @Column(name = "companyId")
   private int companyId;
    @Column(name = "loginId")
   private String companyLoginId;
    @Column(name = "company")
   private String company;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyLoginId() {
        return companyLoginId;
    }

    public void setCompanyLoginId(String companyLoginId) {
        this.companyLoginId = companyLoginId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
/**
 create table tbl_common_company_login_id(
 id int primary key not null auto_increment,
 companyId int,
 loginId varchar(50),
 company varchar(100)
 );
 */
