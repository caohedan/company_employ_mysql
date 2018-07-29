package com.oocl.jpamysql.service;

import com.oocl.jpamysql.entities.Company;
import com.oocl.jpamysql.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository repository;

    public Company save(Company company) {
        return  repository.save(company);
    }

    public Company getOneById(Long id) {
        return repository.getOne(id);
    }



    public List<Company> getCompanyByPageInfo(Pageable pageable) {
        Page<Company> companyList =repository.findAll(pageable);
        return  companyList.getContent();
    }

    public Company deleteByCompany(Long id) {
        Company one = repository.getOne(id);
        repository.delete(one);
        return  one;
    }
}
