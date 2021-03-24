package com.signnow.library.dto;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Document extends GenericId {
    @JsonProperty("user_id")
    public String user_id;
    @JsonProperty("document_name")
    public String document_name;
    @JsonProperty("page_count")
    public String pageCount;
    public String created;
    public String updated;
    @JsonProperty("original_filename")
    public String originalFilename;
    @JsonProperty("origin_document_id")
    public String originDocumentId;
    public String owner;
    @JsonProperty(value = "template", defaultValue = "false")
    public boolean template;
    public Thumbnail thumbnail;
    public List<Signature> signatures;
    public List<Tag> tags;
    public List<DocumentField> fields;
    @JsonProperty("version_time")
    public String versionTime;
    /**
     * This one stands for some internal data info, does not correspond to document sign ID (free form invite) or group invite ID
     */
    @JsonProperty("field_invites")
    public List<FieldInvite> fieldInvites;
    /**
     * Free form invites info
     */
    public List<DocumentSignRequestInfo> requests;
    public List<Role> roles;

    public static class SigningLinkRequest {
        @JsonProperty("document_id")
        public String documentId;

        public SigningLinkRequest(String documentId) {
            this.documentId = documentId;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Thumbnail {
        public String small;
        public String medium;
        public String large;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Signature extends GenericId {
        @JsonProperty("user_id")
        public String userId;
        public String email;
        @JsonProperty("page_number")
        public String pageNumber;
        public String width;
        public String height;
        public String x;
        public String y;
        public String created;
        public String data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DocumentField extends GenericId {
        public FieldType type;
        @JsonProperty("role_id")
        public String roleId;
        public String role;
        public String originator;
        public String fulfiller;
        @JsonProperty("field_request_id")
        public String fieldRequestId;
        @JsonProperty("element_id")
        public String elementId;
        @JsonProperty("field_request_canceled")
        public String fieldRequestCanceled;
        @JsonProperty("template_field_id")
        public String fieldTemplateId;
        @JsonProperty("field_id")
        public String fieldId;
    }

    public static class Role {
        @JsonProperty("unique_id")
        public String uniqueId;
        @JsonProperty("signing_order")
        public String signingOrder;
        public String name;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Tag {
        public String type;
        public String name;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SigningLinkResponce {
        public String url;
        @JsonProperty("url_no_signup")
        public String urlNoSignup;
    }

    public static class SigningInviteRequest {
        public final String from;
        public final String to;
        public List<String> cc = new ArrayList<>();
        public String subject;
        public String message;

        public SigningInviteRequest(String from, String to) {
            this.from = from;
            this.to = to;
        }
    }

    public static class SigningInviteWithRolesRequest {
        public final String from;
        public final List<InviteRole> to;
        public List<String> cc = new ArrayList<>();
        public String subject;
        public String message;

        public SigningInviteWithRolesRequest(String from, List<InviteRole> to) {
            this.from = from;
            this.to = to;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class InviteRole {
        public final String email;
        public final String role_id = "";
        public final String role;
        public Integer order = 1;
        public String password;
        @JsonProperty("expiration_days")
        public Integer expireAfterDays = null;
        @JsonProperty("reminder")
        public Integer remindAfterDays = null;

        public InviteRole(String email, String role) {
            this.email = email;
            this.role = role;
        }

        @JsonGetter("authentication_type")
        public String getAuthType() {
            if (password != null) {
                return "password";
            }
            return null;
        }
    }

    public enum FieldType {
        SIGNATURE("signature"),
        TEXT("text"),
        INITIALS("initials"),
        CHECKBOX("checkbox"),
        ENUMERATION("enumeration");

        private final String name;

        FieldType(String name) {
            this.name = name;
        }

        public static FieldType typeOf(String name) {
            for (FieldType type : values()) {
                if (type.name.equalsIgnoreCase(name)) {
                    return type;
                }
            }
            throw new IllegalArgumentException(name + " field not supported.");
        }

        @JsonValue
        public String getType() {
            return name;
        }

        @JsonCreator
        @Override
        public String toString() {
            return name;
        }
    }

    public static class FieldsUpdateRequest {
        public final List<Field> fields;

        public FieldsUpdateRequest(List<Field> fields) {
            this.fields = fields;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Field {
        public int x;
        public int y;
        public int width;
        public int height;
        @JsonProperty("page_number")
        public int pageNumber;
        public String role;
        public boolean required;
        public FieldType type;
        public String label;
        @JsonProperty("prefilled_text")
        public String prefilledText;

        public String getType() {
            return type.toString();
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FieldInvite extends GenericId {
        public String status;
        public String email;
        public String role;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DocumentSignRequestInfo extends GenericId {
        @JsonProperty("signer_email")
        public String signerEmail;
        @JsonProperty("originator_email")
        public String originatorEmail;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DocumentDownloadLink {
        public String link;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EmbeddedInviteResponse {
        public List<EmbeddedInviteResult> data = new ArrayList<>();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EmbeddedInviteResult {
        public String id;
        public String email;
        public String role_id;
        public int order;
        public String status;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class EmbeddedInviteRequest {
        public List<EmbeddedInvite> invites = new ArrayList<>();
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class EmbeddedInvite {
        public String email;
        public String role_id;
        public int order;
        public String auth_method;

        public EmbeddedInvite() {
        }

        public EmbeddedInvite(String email, String role_id, int order, String auth_method) {
            this.email = email;
            this.role_id = role_id;
            this.order = order;
            this.auth_method = auth_method;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class GenerateEmbeddedSigningLinkRequest {
        public String auth_method;
        public int link_expiration;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class GenerateEmbeddedSigningLinkResponse {
        public LinkData data;
    }

    public static class LinkData {
        public String link;
    }

    public enum AuthMethod {
        PASSWORD("password"),
        EMAIL("email"),
        MFA("mfa"),
        SOCIAL("social"),
        BIOMETRIC("biometric"),
        OTHER("other"),
        NONE("none");

        private final String method;

        AuthMethod(String method) {
            this.method = method;
        }

        public static AuthMethod getAuthMethod(final String authMethod) {
            for (AuthMethod value : values()) {
                if (value.method.equalsIgnoreCase(authMethod)) {
                    return value;
                }
            }
            throw new EnumConstantNotPresentException(AuthMethod.class, authMethod);
        }

        @JsonValue
        public String getAuthMethod() {
            return method;
        }

        @JsonCreator
        @Override
        public String toString() {
            return method;
        }
    }
}
