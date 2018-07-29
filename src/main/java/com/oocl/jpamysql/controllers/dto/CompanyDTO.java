package com.oocl.jpamysql.controllers.dto;


import com.oocl.jpamysql.entities.Company;

import java.time.ZonedDateTime;

public class CompanyDTO {
    private final Long id;
    private final String name;
    private final ZonedDateTime createDate;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }


    public CompanyDTO(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.createDate = company.getCreatedDate();
    }
}
