package my.test.azoft.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@WithUserDetails("Admin")
public class ExpensesControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ExpensesController expensesController;

    @Test
    public void expenses() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(authenticated());
    }

    @Test
    public void edit() {
    }

    @Test
    public void edit1() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void calculate() {
    }

    @Test
    public void add() {
    }
}