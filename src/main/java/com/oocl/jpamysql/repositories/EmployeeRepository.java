package com.oocl.jpamysql.repositories;

import com.oocl.jpamysql.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
}
