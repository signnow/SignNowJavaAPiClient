package com.signnow.library.services;

import com.signnow.library.SNClient;
import com.signnow.library.dto.Webhook;
import com.signnow.library.exceptions.SNException;
import com.signnow.library.facades.Webhooks;

import java.util.Collections;
import java.util.Map;

public class WebhookService extends ApiService implements Webhooks {
    public WebhookService(SNClient client) {
        super(client);
    }

    @Override
    public void add(String event, String entityId, String callback, Map<String, String> headers) throws SNException {
        client.post(
                "/api/v2/events",
                null,
                new Webhook.CreateRequest(event, entityId, callback, headers),
                String.class
        );
    }

    @Override
    public void delete(String eventSubscriptionId) throws SNException {
        client.delete(
                "/api/v2/events/{eventSubscriptionId}",
                Collections.singletonMap("eventSubscriptionId", eventSubscriptionId),
                String.class
        );
    }
}
