package br.com.safeway.safeway.controller;

import br.com.safeway.safeway.service.EmpresaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmpresaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmpresaService empresaService;

    @Test
    void deveriaDevolverCodigo400ParaCadastroEmpresaComCNPJInvalido() throws Exception {
        //ARRANGE
        String json = "{\n" +
                "    \"razaoSocial\": \"UnoBank\",\n" +
                "    \"CNPJ\": \"30217749000151\"\n" +
                "}";

        //ACT
        var response = mvc.perform(
                post("/empresas")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaCadastroEmpresaSemErro() throws Exception {
        //ARRANGE
        String json = "{\n" +
                "    \"razaoSocial\": \"UnoBank\",\n" +
                "    \"CNPJ\": \"30217749000156\"\n" +
                "}";

        //ACT
        var response = mvc.perform(
                post("/empresas")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(201, response.getStatus());
    }
}