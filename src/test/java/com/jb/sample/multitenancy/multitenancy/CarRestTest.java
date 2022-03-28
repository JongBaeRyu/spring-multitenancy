package com.jb.sample.multitenancy.multitenancy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jb.sample.multitenancy.multitenancy.entity.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CarRestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCarsWithTwoTenants() throws Exception {

        // given
        String vwTenant = "multi1";

        String url = "/cars";
        Car vw = Car.builder().name("Tiguan").color("Black").build();

        // when
        mockMvc.perform(get(url).header("X-Tenant", vwTenant))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        mockMvc.perform(post(url).header("X-Tenant", vwTenant)
                .content(objectMapper.writeValueAsString(vw))
                .contentType(MediaType.APPLICATION_JSON_VALUE))

                // then
                .andExpect(status().isCreated());

        mockMvc.perform(get(url).header("X-Tenant", vwTenant))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Tiguan")))
                .andExpect(jsonPath("$[0].color", is("Black")))
                .andDo(print());

        // controller 호출 ( muilti2 tenant 입력)
//        String tenantUrl = "/api/tenant/register?username=sa&password=&tenantName=multi2&url=jdbc:h2:mem:multi2";
//        mockMvc.perform(get(tenantUrl))
//                .andExpect(status().isOk());

//        String tenant = "multi2";
        String tenant = "multi2";
        Car bmw = Car.builder().name("X5").color("Orange").build();

        // when
        try {
            mockMvc.perform(get(url)
                    .header("X-Tenant", tenant))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(0)));
        } catch ( Exception e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        }

        mockMvc.perform(post(url).header("X-Tenant", tenant)
                        .content(objectMapper.writeValueAsString(bmw))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))

                // then
                .andExpect(status().isCreated());

        mockMvc.perform(get(url).header("X-Tenant", tenant))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("X5")))
                .andExpect(jsonPath("$[0].color", is("Orange")))
                .andDo(print());;
    }

    @Test
    void testCarsWithTwoTenants2() throws Exception {
        // given
        String vwTenant = "multi1";

        String url = "/cars";
        Car vw = Car.builder().name("Tiguan").color("Black").build();

        // when
        mockMvc.perform(get(url).header("X-Tenant", vwTenant))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        mockMvc.perform(post(url).header("X-Tenant", vwTenant)
                .content(objectMapper.writeValueAsString(vw))
                .contentType(MediaType.APPLICATION_JSON_VALUE))

                // then
                .andExpect(status().isCreated());

        mockMvc.perform(get(url).header("X-Tenant", vwTenant))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Tiguan")))
                .andExpect(jsonPath("$[0].color", is("Black")));
    }
}
