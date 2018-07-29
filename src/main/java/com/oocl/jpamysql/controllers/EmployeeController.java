package com.oocl.jpamysql.controllers;

import com.oocl.jpamysql.controllers.dto.EmployeeDTO;
import com.oocl.jpamysql.entities.Employee;
import com.oocl.jpamysql.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private EmployeeService service;

    @Transactional
    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee save(@RequestBody Employee employee) {
        return  service.save(employee);
    }

    @Transactional
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody Employee employee) {
        service.update(employee);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Transactional
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDTO get(@PathVariable("id")Long id) {
        Employee employee = service.getOne(id);
        return new EmployeeDTO(employee);
    }
    @Transactional
    @GetMapping(path = "/male", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getAllMale() {
              return  service.findAllMales("male");
    }

    @Transactional
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee delete(@PathVariable("id")Long id) {
        return service.delete(id);
    }
//    @Transactional
//    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
//    public  List<Employee> companyList(Pageable pageable) {
//       return  service.findEmployeeListByPage(pageable);
//    }
}
