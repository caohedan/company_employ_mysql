package com.oocl.jpamysql.controllers;

import com.oocl.jpamysql.controllers.dto.EmployeeDTO;
import com.oocl.jpamysql.entities.Employee;
import com.oocl.jpamysql.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository repository;
    @Autowired
    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee save(@RequestBody Employee employee) {
        return  repository.save(employee);
    }

    @Transactional
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody Employee employee) {
        repository.save(employee);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Transactional
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDTO get(@PathVariable("id")Long id) {
        Employee employee = repository.getOne(id);
        return new EmployeeDTO(employee);
    }
    @Transactional
    @GetMapping(path = "/male", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getAllMale() {
              return  repository.findByGender("male");
    }

    @Transactional
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee delete(@PathVariable("id")Long id) {
        Employee one = repository.getOne(id);
        repository.delete(one);
        return one;
    }
    @Transactional
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public  List<Employee> companyList(Pageable pageable) {
        Page<Employee> companyList = repository.findAll(pageable);
        return  companyList.getContent();
    }
}
