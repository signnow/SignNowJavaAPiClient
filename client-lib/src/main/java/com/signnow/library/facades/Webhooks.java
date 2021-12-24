package com.signnow.library.facades;

import com.signnow.library.exceptions.SNException;

import java.util.Map;

public interface Webhooks {
    void add(String event, String entityId, String callback, Map<String, String> headers) throws SNException;
    void delete(String webhookId) throws SNException;
}
