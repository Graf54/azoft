package my.test.azoft.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class RegistrationControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    RegistrationController registrationController;

    @Test
    public void test() throws Exception {
        mockMvc.perform(get("/"))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Добро пожаловать")));
    }

    @Test
    public void accessDeniedTest() throws Exception {
        mockMvc.perform(get("expenses"))
//                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void accessSuccessTest() throws Exception {
        mockMvc.perform(formLogin().user("Admin").password("Admin"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void badCredentialsTest() throws Exception {
        mockMvc.perform(post("/login").param("user", "test"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}