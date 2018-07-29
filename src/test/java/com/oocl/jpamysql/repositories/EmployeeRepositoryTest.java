package com.oocl.jpamysql.repositories;

import com.oocl.jpamysql.entities.Employee;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest

public  class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public  void should_return_maleList_when_call_findByGender_and_param_is_male(){
        entityManager.persist(new Employee( "name1", "male"));
        entityManager.persist(new Employee( "name2", "female"));
        List<Employee> employees = employeeRepository.findByGender("male");
        Assertions.assertThat(employees.size()).isEqualTo(1);
    }

    @Test
    public void should_return_employee_when_call_save() {
        Employee saveEmployee = entityManager.persistFlushFind(new Employee( "name1", "male"));
        Employee employee = employeeRepository.save(new Employee( "name1", "male"));
        Assertions.assertThat(employee.getName()).isEqualTo(saveEmployee.getName());

    }

    @Test
    public void should_return_employeeList_when_all_findAll() {
        Employee employee = new Employee( "name1", "male");
        entityManager.persistFlushFind(employee);
        List<Employee> all = employeeRepository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(1);
        Assertions.assertThat(all.get(0).getName()).isEqualTo(employee.getName());

    }


    @Test
    public void should_return_employeeList_when_call_findByPage() {
        Employee male = new Employee("tom","male");
        Employee female = new Employee("lili","female");
        entityManager.persistAndFlush(male);
      entityManager.persistAndFlush(female);
        Page<Employee> employeeList = employeeRepository.findAll(new PageRequest(0, 1));
        Assertions.assertThat(employeeList.getContent().size()).isEqualTo(1);
    }




}