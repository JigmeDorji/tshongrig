package com.spms.mvc.entity;

import javax.persistence.*;


@Entity
@Table(name = "tbl_rawmateriallocationsetup")
public class RawMaterialLocationSetup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locationSetUpId")
    private Integer locationSetUpId;



    @Column(name = "locationId")
    private String locationId;

    @Column(name = "description")
    private String description;

    @Column(name = "companyId")
    private Integer companyId;

    public Integer getLocationSetUpId() {
        return locationSetUpId;
    }

    public void setLocationSetUpId(Integer locationSetUpId) {
        this.locationSetUpId = locationSetUpId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}

