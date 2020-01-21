package com.signnow.examples.model;

import java.util.Objects;

public class DocumentInfo {
    private String documentId;
    private String documentName;

    public DocumentInfo() {
    }

    public DocumentInfo(String documentId, String documentName) {
        this.documentId = documentId;
        this.documentName = documentName;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentInfo that = (DocumentInfo) o;
        return Objects.equals(documentId, that.documentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentId);
    }
}
