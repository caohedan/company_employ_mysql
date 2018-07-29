package com.oocl.jpamysql.repositories;

import com.oocl.jpamysql.entities.Employee;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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


}