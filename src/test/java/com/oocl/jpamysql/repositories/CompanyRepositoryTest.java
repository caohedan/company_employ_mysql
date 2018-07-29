package com.oocl.jpamysql.repositories;

import com.oocl.jpamysql.entities.Company;
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
public class CompanyRepositoryTest {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TestEntityManager entityManager;
    @Test
   public void should_return_companyList_when_call_findByName()
    {
        entityManager.persist(new Company( "company1"));
        entityManager.persist(new Company( "company2"));
        List<Company> companies = companyRepository.findByName("company1");
        Assertions.assertThat(companies.size()).isEqualTo(1);
    }
}