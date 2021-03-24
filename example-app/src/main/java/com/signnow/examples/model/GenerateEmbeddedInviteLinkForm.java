package com.signnow.examples.model;

import java.util.HashSet;
import java.util.Set;

/**
 * User: oleg.k
 * Date: 22.03.2021
 * Time: 22:58
 */
public class GenerateEmbeddedInviteLinkForm {
    public DocumentInfo documentInfo = new DocumentInfo();
    public Set<String> fields = new HashSet<>();
    public String fieldId;
    public String auth_method;
    public int link_expiration = 15;
    public String embeddedInvites;

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public DocumentInfo getDocumentInfo() {
        return documentInfo;
    }

    public void setDocumentInfo(DocumentInfo documentInfo) {
        this.documentInfo = documentInfo;
    }

    public Set<String> getFields() {
        return fields;
    }

    public void setFields(Set<String> fields) {
        this.fields = fields;
    }

    public String getEmbeddedInvites() {
        return embeddedInvites;
    }

    public void setEmbeddedInvites(String embeddedInvites) {
        this.embeddedInvites = embeddedInvites;
    }

    public String getAuth_method() {
        return auth_method;
    }

    public void setAuth_method(String auth_method) {
        this.auth_method = auth_method;
    }

    public int getLink_expiration() {
        return link_expiration;
    }

    public void setLink_expiration(int link_expiration) {
        this.link_expiration = link_expiration;
    }
}
