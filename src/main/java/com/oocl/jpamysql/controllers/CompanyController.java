package com.oocl.jpamysql.controllers;

import com.oocl.jpamysql.controllers.dto.CompanyDTO;
import com.oocl.jpamysql.entities.Company;
import com.oocl.jpamysql.entities.Employee;
import com.oocl.jpamysql.exception.ResourceNullException;
import com.oocl.jpamysql.exception.WrongRequestException;
import com.oocl.jpamysql.repositories.CompanyRepository;
import com.oocl.jpamysql.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;


@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

    @Autowired
    private CompanyService service;

    @Transactional
    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Company save(@RequestBody Company company) {
        company.getEmployees().stream().forEach(employee -> {
            employee.setCompany(company);
        });
        return service.save(company);
    }


    @Transactional
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody Company company) {
        company.getEmployees().stream().filter(employee -> employee.getCompany() == null).forEach(employee -> {
            employee.setCompany(company);
        });
        service.save(company);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Transactional
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompanyDTO get(@PathVariable("id") Long id) {
        Company company = service.getOneById(id);
        if (company == null) {
            throw new WrongRequestException("id is not exist");
        }
        return new CompanyDTO(company);
    }

    @Transactional
    @GetMapping(path = "/{id}/employees", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getOneOfEmployees(@PathVariable("id") Long id) {
        Company company = service.getOneById(id);
        List<Employee> employees = company.getEmployees();
        if (employees.size() == 0) {
            throw new ResourceNullException("no match!");
        }
        return employees;
    }

    @Transactional
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Company delete(@PathVariable("id") Long id) {
        Company company = service.deleteByCompany(id);
        if (company == null) {
            throw new WrongRequestException("id is not exist");
        }
        return company;
    }

    @Transactional
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Company> companyList(Pageable pageable) {
        List<Company> companies = service.getCompanyByPageInfo(pageable);
        if (companies.size() == 0) {
            throw new ResourceNullException("No match !");
        }
        return companies;
    }
}
