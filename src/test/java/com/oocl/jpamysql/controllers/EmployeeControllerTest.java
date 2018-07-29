package com.oocl.jpamysql.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.jpamysql.entities.Employee;
import com.oocl.jpamysql.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @Test
    public void should_return_the_Employee_when_add_a_employee() throws Exception {
        Employee employee = new Employee("tom","male");
        System.out.println(employee.getName());
        given(employeeService.save(any())).willReturn(employee);
        ResultActions result= mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON).
                content(mapper.writeValueAsString(employee)));
        result.andExpect(status().isOk()).andDo(print());
    }
//    @Test
//    public void should_return_
//


}