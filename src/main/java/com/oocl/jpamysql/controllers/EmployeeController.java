package com.oocl.jpamysql.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/emoloyees")
public class EmployeeController {
    @Autowired
    private CompanyRepository repository;
    @Autowired
    public EmployeeController(EmployeeController repository) {
        this.repository = repository;
    }

    @Transactional
    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Company save(@RequestBody Company company) {
        company.getEmployees().stream().forEach(employee -> {
            employee.setCompany(company);
        });
        return  repository.save(company);
    }


    @Transactional
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody Company company) {
        company.getEmployees().stream().filter(employee -> employee.getCompany() == null).forEach(employee -> {
            employee.setCompany(company);
        });
        repository.save(company);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Transactional
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompanyDTO get(@PathVariable("id")Long id) {
        Company company = repository.getOne(id);
        return new CompanyDTO(company);
    }
    @Transactional
    @GetMapping(path = "/{id}/empolyees", produces = MediaType.APPLICATION_JSON_VALUE)
    public  List<Employee> getOneOfEmployees(@PathVariable("id")Long id) {
        Company company = repository.getOne(id);
        List<Employee> employees = company.getEmployees();
        return employees;
    }

    @Transactional
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Company delete(@PathVariable("id")Long id) {
        Company one = repository.getOne(id);
        repository.delete(one);
        return one;
    }
    @Transactional
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public  List<Company> companyList(Pageable pageable) {
        Page<Company> companyList = repository.findAll(pageable);
        return  companyList.getContent();
    }
}
