package com.signnow.library.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Webhook {
    public static class CreateRequest {
        @JsonProperty("event")
        public final String event;
        @JsonProperty("entity_id")
        public final String entityId;
        @JsonProperty("action")
        public final String action = "callback";
        @JsonProperty("attributes")
        public final Attributes attributes;

        public CreateRequest(String event, String entityId, String callback, Map<String, String> headers) {
            this.event = event;
            this.entityId = entityId;
            this.attributes = new Attributes(callback, headers);
        }
    }

    static class Attributes {
        @JsonProperty("callback")
        public final String callback;

        @JsonProperty("headers")
        public final Map<String, String> headers;

        Attributes(String callback, Map<String, String> headers) {
            this.callback = callback;
            this.headers = headers;
        }
    }
}
