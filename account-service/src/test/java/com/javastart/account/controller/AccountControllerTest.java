package com.javastart.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javastart.account.AccountApplication;
import com.javastart.account.config.SpringH2;
import com.javastart.account.controller.dto.AccountResponseDTO;
import com.javastart.account.entity.Account;
import com.javastart.account.exception.AccountNotFoundException;
import com.javastart.account.service.AccountService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AccountApplication.class, SpringH2.class})
public class AccountControllerTest {

    private MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private AccountService accountService;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void test() throws Exception {
        Account account = new Account();
        account.setAccountId(1L);
        account.setBills(List.of(1L, 2L, 3L));
        account.setEmail("dexljp1@glc.rom");
        account.setPhone("952448");
        account.setName("alex");
        account.setCreationDate(OffsetDateTime.now());

        Mockito.when(accountService.getAccountById(ArgumentMatchers.anyLong())).thenReturn(account);
        Mockito.when(accountService.getAccountById(ArgumentMatchers.isNull())).thenThrow(AccountNotFoundException.class);

        MvcResult mvcResult = mockMvc.perform(get("/0").contentType(APPLICATION_JSON_UTF8)).andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        AccountResponseDTO responseDTO = objectMapper.readValue(result, AccountResponseDTO.class);

        Assertions.assertThat(responseDTO.getName()).isEqualTo(account.getName());
    }
}
