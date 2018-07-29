package com.oocl.jpamysql.controllers;

import com.oocl.jpamysql.controllers.dto.EmployeeDTO;
import com.oocl.jpamysql.entities.Employee;
import com.oocl.jpamysql.exception.ResourceNullException;
import com.oocl.jpamysql.exception.WrongRequestException;
import com.oocl.jpamysql.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @Transactional
    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee save(@RequestBody Employee employee) {

        return  service.save(employee);
    }

    @Transactional
    @PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody Employee employee) {
        return service.update(employee);
    }

    @Transactional
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDTO get(@PathVariable("id")Long id) {
        EmployeeDTO employee = service.getOne(id);
        if(employee == null)
        {
            throw new WrongRequestException("the id is not exist");
        }
        return employee;
    }
    @Transactional
    @GetMapping(path = "/male", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getAllMale() {
        List<Employee>  employees = service.findAllMales();
        if(employees.size() == 0)
        {
            throw new ResourceNullException("no match");
        }
        return  employees;
    }

    @Transactional
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee delete(@PathVariable("id")Long id) {
        Employee employee =  service.delete(id);
        if(employee == null)
        {
            throw new WrongRequestException("the id is not exist");
        }
        return employee;
    }
    @Transactional
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public  List<Employee> companyList(Pageable pageable) {
        List<Employee>  employees = service.findEmployeeListByPage(pageable);
        if(employees.size() == 0)
        {
            throw new ResourceNullException("no match");
        }
        return  employees;
    }

}
