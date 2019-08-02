package my.test.azoft.rest.controller;

import my.test.azoft.model.Expenses;
import my.test.azoft.model.User;
import my.test.azoft.services.ExpensesService;
import my.test.azoft.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
@TestPropertySource("/application-test.properties")
public class ExpensesRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ExpensesService expensesService;
    @Autowired
    private UserService userService;

    private static final String username = "Test";
    private static final String pass = "Test";

    @Before
    public void initDateBase() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(pass);
        Optional<User> optionalUser = userService.createUser(user);

        Expenses expenses = new Expenses();
        expenses.setUser(optionalUser.get());
        expenses.setDate(new Date());
        expenses.setDescription("Test");
        expenses.setComment("Comment");
        expenses.setValue(new BigDecimal(12.6));

        expensesService.save(expenses);


    }

    @Test
    public void list() throws Exception {
        Optional<User> optionalUser = userService.findByUsername(username);
        //check user
        Assert.assertTrue(optionalUser.isPresent());

        ResultActions resultActions = mockMvc.perform(
                get("/api/expenses/")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, pass))
        )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = username, password = pass)
    public void getOne() throws Exception {

        Optional<User> optionalUser = userService.findByUsername(username);
        Page<Expenses> allByUserAndFilter = expensesService.findAllByUserAndFilter(optionalUser.get(), Pageable.unpaged(), Optional.ofNullable(null));
        Optional<Expenses> optionalExpenses = allByUserAndFilter.get().findFirst();
        Assert.assertTrue(optionalExpenses.isPresent());
        int id = optionalExpenses.get().getId();
        // When

        ResultActions resultActions = mockMvc.perform(
                                get("/api/expenses/{id}", id)
                                .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, pass))
                        )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk())
                .andDo(document("index-example",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        links(linkWithRel("crud").description("The CRUD resource")),
                        responseFields(subsectionWithPath("_links").description("Links to other resources")),
                        responseHeaders(headerWithName("Content-Type").description("The Content-Type of the payload, e.g. `application/hal+json`"))));
    }

    @Test
    public void create() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}