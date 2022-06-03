package com.signnow.library.dto;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

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
    @JsonProperty("fields")
    public List<FieldMetadata> fields;

    public static class SigningLinkRequest {
        @JsonProperty("document_id")
        public String documentId;

        public SigningLinkRequest(String documentId) {
            this.documentId = documentId;
        }
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

        @JsonSetter
        public static FieldType typeOf(String name) {
            for (FieldType type : values()) {
                if (type.name.equalsIgnoreCase(name)) {
                    return type;
                }
            }
            throw new IllegalArgumentException(name + " field not supported.");
        }

        @JsonCreator
        @Override
        public String toString() {
            return name;
        }
    }

    public static class PrefillTextRequest {
        public final List<FieldText> fields;

        public PrefillTextRequest(List<FieldText> fields) {
            this.fields = fields;
        }
    }

    public static class FieldsUpdateRequest {
        public final List<Field> fields;

        public FieldsUpdateRequest(List<Field> fields) {
            this.fields = fields;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FieldText {
        @JsonProperty("field_name")
        public String fieldName;
        @JsonProperty("prefilled_text")
        public String prefilledText;

        public FieldText(String name, String prefill) {
            this.fieldName = name;
            this.prefilledText = prefill;
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FieldMetadata {
        public String id;
        public String type;
        @JsonProperty("role_id")
        public String roleId;
        public String role;
        public String originator;
        public String fulfiller;
        @JsonProperty("json_attributes")
        public Attributes attributes;
        @JsonProperty("field_request_id")
        public String fieldRequestId;
        @JsonProperty("element_id")
        public String elementId;
        @JsonProperty("field_request_canceled")
        public boolean fieldRequestCanceled;
        @JsonProperty("template_field_id")
        public String templateFieldId;
        @JsonProperty("field_id")
        public String fieldId;

        public String getType() {
            return type.toString();
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Attributes {
            @JsonProperty("page_number")
            public String pageNumber;
            public int x;
            public int y;
            public int width;
            public int height;
            public boolean required;
            public String name;
            public String label;
            @JsonProperty("prefilled_text")
            public String prefilledText;
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

    /*------------------------Embedded invites------------------------*/
    public static class EmbeddedSigningInviteRequest {
        public final List<EmbeddedInvite> invites = new ArrayList<>();

        public EmbeddedSigningInviteRequest(EmbeddedInvite... invite) {
            this.invites.addAll(Arrays.asList(invite));
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class EmbeddedInvite {
        public String email;
        public String role_id;
        public String role;
        public int order = 1;
        @JsonProperty("auth_method")
        public String authMethod = "none";

        public EmbeddedInvite(String email, String role) {
            this.email = email;
            this.role = role;
        }
    }

    public static class EmbeddedSigningInviteResponse {
        public List<InviteResponseData> data = new ArrayList<>();
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class InviteResponseData {
        public String id;
        public String email;
        @JsonProperty("role_id")
        public String roleId;
        public int order;
        public String status;
    }

    public static class EmbeddedInviteLinkRequest {
        @JsonProperty("link_expiration")
        public int linkExpiration = 45;
        @JsonProperty("auth_method")
        public String authMethod = "none";
    }

    public static class EmbeddedInviteLinkResponse {
        public Map<String, String> data = new HashMap<>();

        public String getLink() {
            return data.get("link");
        }
    }
}
