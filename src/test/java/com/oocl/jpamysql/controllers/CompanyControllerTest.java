package com.oocl.jpamysql.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.jpamysql.entities.Company;
import com.oocl.jpamysql.entities.Employee;
import com.oocl.jpamysql.exception.ResourceNullException;
import com.oocl.jpamysql.exception.WrongRequestException;
import com.oocl.jpamysql.service.CompanyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CompanyController.class)
public class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private CompanyService companyService;
    @Autowired
    private CompanyController companyController;
    @InjectMocks
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Before()
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(companyController).setCustomArgumentResolvers(pageableArgumentResolver)
                .build();
    }

    @Test
    public void should_return_the_Employee_when_add_a_employee() throws Exception {
        Company company = new Company("baidu");
        given(companyService.save(any())).willReturn(company);
        ResultActions result = mockMvc.perform(post("/api/v1/companies")
                .contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(company)));
        result.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void should_return_the_no_content_when_call_update_a_company() throws Exception {
        Company company = new Company("360");
        given(companyService.save(any())).willReturn(company);
        ResultActions result = mockMvc.perform(put("/api/v1/companies/1")
                .contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(company)));
        result.andExpect(status().is2xxSuccessful()).andDo(print());
    }

    @Test
    public void should_return_the_company_when_call_getById_id_is_correct() throws Exception {
        Company company = new Company("ali");
        given(companyService.getOneById(anyLong())).willReturn(company);
        mockMvc.perform(get("/api/v1/companies/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("ali"));
    }

    @Test
    public void should_return_BAD_REQUEST_when_call_getById_id_is_not_correct() throws Exception {
        given(companyService.getOneById(anyLong())).willThrow(new WrongRequestException("error"));
        mockMvc.perform(get("/api/v1/companies/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_the_company_when_call_delete_and_id_is_correct() throws Exception {
        Company company = new Company("tw");
        given(companyService.deleteByCompany(anyLong())).willReturn(company);
        mockMvc.perform(delete("/api/v1/companies/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("tw"));
    }

    @Test
    public void should_return_BAD_REQUEST_when_call_delete_and_id_is_not_correct() throws Exception {
        given(companyService.getOneById(anyLong())).willThrow(new WrongRequestException("error"));
        mockMvc.perform(delete("/api/v1/companies/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_employeeList_when_call_getAll_and_the_size_is_not_zero() throws Exception {
        List<Company> employees = new ArrayList<>();
        employees.add(new Company("tw"));
        given(companyService.getCompanyByPageInfo(any(Pageable.class))).willReturn(employees);
        mockMvc.perform(get("/api/v1/companies").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].name").value("tw"));
    }

    @Test
    public void should_return_NOT_FOUND_when_call_getAll_and_the_size_is_not_zero() throws Exception {
        given(companyService.getCompanyByPageInfo(any())).willThrow(new ResourceNullException("null"));
        mockMvc.perform(get("/api/v1/companies").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_return_emloyeeList_when_call_getOneOfEmployees_and_the_size_is_not_zero() throws Exception {
        Company company = new Company("ali");
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("lili", "female"));
        company.setEmployees(employees);
        given(companyService.getOneById(anyLong())).willReturn(company);
        mockMvc.perform(get("/api/v1/companies/1/employees").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("lili"));
    }

    @Test
    public void should_return_NOT_FOUND_when_call_getOneOfEmployees_and_the_size_is_not_zero() throws Exception {
        given(companyService.getOneById(any())).willThrow(new ResourceNullException("null"));
        mockMvc.perform(get("/api/v1/companies/1/employees").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }
}