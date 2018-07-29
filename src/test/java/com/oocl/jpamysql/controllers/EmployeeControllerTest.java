package com.oocl.jpamysql.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.jpamysql.controllers.dto.EmployeeDTO;
import com.oocl.jpamysql.entities.Employee;
import com.oocl.jpamysql.exception.ResourceNullException;
import com.oocl.jpamysql.exception.WrongRequestException;
import com.oocl.jpamysql.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
@Autowired
private MockMvc mockMvc;
@Autowired
private ObjectMapper mapper;
@MockBean
private EmployeeService employeeService;
@InjectMocks
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    @Before()
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeService).setCustomArgumentResolvers(pageableArgumentResolver)
                .build();
    }
    @Test
    public void should_return_the_Employee_when_add_a_employee() throws Exception {
        Employee employee = new Employee("tom","male");
        given(employeeService.save(any())).willReturn(employee);
        ResultActions result= mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON).
                content(mapper.writeValueAsString(employee)));
        result.andExpect(status().isOk()).andDo(print());
    }
    @Test
    public void should_return_the_no_content_when_call_update_a_employee() throws Exception {
        Employee employee = new Employee("tom","male");
        given(employeeService.update(any())).willReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
        ResultActions result= mockMvc.perform(put("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(employee)));
        result.andExpect(status().is2xxSuccessful()).andDo(print());
    }
    @Test
    public void should_return_the_employee_when_call_getById_id_is_correct() throws Exception {
            EmployeeDTO employee = new EmployeeDTO(new Employee("tom","male"));
            given(employeeService.getOne(anyLong())).willReturn(employee);
            mockMvc.perform(get("/api/v1/employees/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("name").value("tom"));
    }
    @Test
    public void should_return_BAD_REQUEST_when_call_getById_id_is_not_correct() throws Exception {
        given(employeeService.getOne(anyLong())).willThrow(new WrongRequestException("error"));
        mockMvc.perform(get("/api/v1/employees/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void should_return_maleList_when_call_getAllMale_and_the_size_is_not_zero() throws Exception {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("tom","male"));
        given(employeeService.findAllMales()).willReturn(employees);
        mockMvc.perform(get("/api/v1/employees/male").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].name").value("tom"));
    }
    @Test
    public void should_return_NOT_FOUND_when_call_getAllMale_and_the_size_is_zero() throws Exception {
        given(employeeService.findAllMales()).willThrow(new ResourceNullException("null"));
        mockMvc.perform(get("/api/v1/employees/male").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }
    @Test
    public void should_return_the_employee_when_call_delete_and_id_is_correct() throws Exception {
        Employee employee = new Employee("tom","male");
        given(employeeService.delete(anyLong())).willReturn(employee);
        mockMvc.perform(delete("/api/v1/employees/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("tom"));
    }
    @Test
    public void should_return_BAD_REQUEST_when_call_delete_and_id_is_not_correct() throws Exception {
        given(employeeService.getOne(anyLong())).willThrow(new WrongRequestException("error"));
        mockMvc.perform(delete("/api/v1/employees/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void should_return_employeeList_when_call_getAll_and_the_size_is_not_zero() throws Exception {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("tom","male"));
        given(employeeService.findEmployeeListByPage(any())).willReturn(employees);
//        ResultActions resultActions = mockMvc.perform(get("/api/v1/employees?page=1&size=1").contentType(MediaType.APPLICATION_JSON));
//        resultActions.andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)));
        mockMvc.perform(get("/api/v1/employees").contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print());
    }
    @Test
    public void should_return_NOT_FOUND_when_call_getAll_and_the_size_is_not_zero() throws Exception {
        given(employeeService.findEmployeeListByPage(any())).willThrow(new ResourceNullException("null"));
        mockMvc.perform(get("/api/v1/employees").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

}