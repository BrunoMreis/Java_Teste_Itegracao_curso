package br.com.alura.leilao.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;

class ErrorControllerTest {

    @Mock
    private Logger logger;

    @Mock
    private Model model;

    @InjectMocks
    private ErrorController errorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        errorController = new ErrorController();
    }

    @Test
    void deveAdicionarMensagemDeErroNoModelQuandoException() {
        Throwable throwable = new RuntimeException("Erro de teste");
        String view = errorController.exception(throwable, model);
        verify(model).addAttribute(eq("errorMessage"), eq("Erro de teste"));
        assertEquals("error", view);
    }

    @Test
    void deveAdicionarMensagemDesconhecidaQuandoThrowableNulo() {
        String view = errorController.exception(null, model);
        verify(model).addAttribute(eq("errorMessage"), eq("Unknown error"));
        assertEquals("error", view);
    }

    @Test
    void deveAdicionarMensagemDeErroNoModelQuandoDataIntegrityViolationException() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("Erro de integridade de dados");
        String view = errorController.handleDatabaseException(ex, model);
        verify(model).addAttribute(eq("errorMessage"), eq("Erro de integridade de dados"));
        assertEquals("error", view);
    }
}
