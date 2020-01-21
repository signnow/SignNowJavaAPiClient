package com.signnow.library;

import com.signnow.library.SNClient;
import com.signnow.library.dto.AuthError;
import com.signnow.library.dto.Errors;
import com.signnow.library.exceptions.SNException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SNClientTest {

    private Response response;

    @BeforeEach
    void setup() {
        response = mock(Response.class);
    }

    @Test
    void checkAPIException_ResponseWasNull() {
        assertThrows(NullPointerException.class, () -> SNClient.checkAPIException(null));
    }

    @Test
    void checkAPIException_ResponseGetStatus401() {
        AuthError authError = mock(AuthError.class);
        when(response.getStatus()).thenReturn(401);
        when(response.readEntity(AuthError.class)).thenReturn(authError);

        assertThrows(SNException.class, () -> SNClient.checkAPIException(response));
    }

    @Test
    void checkAPIException_ResponseGetStatus403() {
        AuthError authError = mock(AuthError.class);
        when(response.getStatus()).thenReturn(403);
        when(response.readEntity(AuthError.class)).thenReturn(authError);

        assertThrows(SNException.class, () -> SNClient.checkAPIException(response));
    }

    @Test
    void checkAPIException_ResponseGetStatus400() {
        Errors errors = mock(Errors.class);
        when(response.getStatus()).thenReturn(400);
        when(response.readEntity(Errors.class)).thenReturn(errors);

        assertThrows(SNException.class, () -> SNClient.checkAPIException(response));
    }
}