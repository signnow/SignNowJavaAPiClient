package com.signnow.examples.model;

import com.signnow.library.dto.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * User: oleg.k
 * Date: 16.03.2021
 * Time: 13:07
 */
public class EmbeddedSigningModel {
    public DocumentInfo documentInfo;
    public List<InviteInfo> invites = new ArrayList<>();

    public List<InviteInfo> getInvites() {
        return invites;
    }

    public DocumentInfo getDocumentInfo() {
        return documentInfo;
    }

    public void setDocumentInfo(DocumentInfo documentInfo) {
        this.documentInfo = documentInfo;
    }

    public static class InviteInfo {
        public String email;
        public String roleId;
        public int order = 1;
        public String authMethod;
        public List<Document.Role> availableRoles = new ArrayList<>();
        public List<Document.AuthMethod> availableAuthMethods = new ArrayList<>();

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public String getAuthMethod() {
            return authMethod;
        }

        public void setAuthMethod(String auth_method) {
            this.authMethod = auth_method;
        }

        public List<Document.Role> getAvailableRoles() {
            return availableRoles;
        }

        public List<Document.AuthMethod> getAvailableAuthMethods() {
            return availableAuthMethods;
        }
    }
}
