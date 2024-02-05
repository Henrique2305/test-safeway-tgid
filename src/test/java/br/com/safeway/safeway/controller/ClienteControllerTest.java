package br.com.safeway.safeway.controller;

import br.com.safeway.safeway.service.ClienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class ClienteControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClienteService clienteService;

    @Test
    void deveriaDevolverCodigo400ParaCadastroClienteComCPFInvalido() throws Exception {
        //ARRANGE
        String json = "{\n" +
                "    \"nome\": \"Henrique Ferreira\",\n" +
                "    \"CPF\": \"91467793881\",\n" +
                "    \"telefone\": \"(11)956064320\",\n" +
                "    \"email\": \"henrique@email.com\",\n" +
                "    \"idEmpresa\": 2\n" +
                "}";

        //ACT
        var response = mvc.perform(
                post("/clientes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaCadastroClienteSemErro() throws Exception {
        //ARRANGE
        String json = "{\n" +
                "    \"nome\": \"Henrique Ferreira\",\n" +
                "    \"CPF\": \"56972912059\",\n" +
                "    \"telefone\": \"(11)956064320\",\n" +
                "    \"email\": \"henrique@email.com\",\n" +
                "    \"idEmpresa\": 2\n" +
                "}";

        //ACT
        var response = mvc.perform(
                post("/clientes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(201, response.getStatus());
    }
}