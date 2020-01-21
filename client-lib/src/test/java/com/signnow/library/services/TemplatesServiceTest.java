package com.signnow.library.services;

import com.signnow.library.dto.GenericId;
import com.signnow.library.exceptions.SNException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TemplatesServiceTest extends CommonServiceTestCase {
    private TemplatesService service;
    @BeforeEach
    void setUp() {
        super.setUp();
        service = new TemplatesService(clientMock);
    }

    @Test
    void createTemplate() throws SNException {
        when(clientMock.post(anyString(), eq(null), any(), eq(GenericId.class))).thenReturn(new GenericId());

        service.createTemplate("1", "2");

        verify(clientMock, times(1)).post(anyString()
                , eq(null)
                , any()
                , eq(GenericId.class)
        );
    }

    @Test
    void createDocumentFromTemplate() throws SNException {
        when(clientMock.post(anyString(), anyMap(), any(), eq(GenericId.class))).thenReturn(new GenericId());

        service.createDocumentFromTemplate("1", "2");

        verify(clientMock, times(1)).post(anyString()
                , anyMap()
                , any()
                , eq(GenericId.class)
        );
    }
}