package com.oocl.jpamysql.service;

import com.oocl.jpamysql.controllers.dto.EmployeeDTO;
import com.oocl.jpamysql.entities.Employee;

import com.oocl.jpamysql.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;
    public Employee save(Employee employee) {
        return repository.save(employee);
    }

    public ResponseEntity<Object> update(Employee employee) {
         repository.save(employee);
       return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public EmployeeDTO getOne(Long id) {
        return  new EmployeeDTO(repository.getOne(id));
    }

    public List<Employee> findAllMales() {
        return repository.findByGender("male");
    }

    public Employee delete(Long id) {
        Employee one = repository.getOne(id);
        repository.delete(one);
        return one;
    }

    public List<Employee> findEmployeeListByPage(Pageable pageable) {
        Page<Employee> companyList = repository.findAll(pageable);
        return  companyList.getContent();
    }
}
