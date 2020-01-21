package com.signnow.library.services;

import com.signnow.library.SNClient;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

abstract class CommonServiceTestCase {
    protected SNClient clientMock;

    @BeforeEach
    void setUp() {
        clientMock = mock(SNClient.class);
    }
}
