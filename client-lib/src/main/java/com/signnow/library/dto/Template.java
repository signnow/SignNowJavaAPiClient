package com.signnow.library.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Template {
    public static class CreateRequest {
        @JsonProperty("document_name")
        public final String templateName;
        @JsonProperty("document_id")
        public final String sourceDocmentId;

        public CreateRequest(String templateName, String sourceDocmentId) {
            this.templateName = templateName;
            this.sourceDocmentId = sourceDocmentId;
        }
    }

    public static class CopyRequest {
        @JsonProperty("document_name")
        public final String newDocumentName;

        public CopyRequest(String newDocumentName) {
            this.newDocumentName = newDocumentName;
        }
    }
}
