package com.signnow.library.dto;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("java:S1104")  // field name equal to JsonProperty
public class Document extends GenericId {
    @JsonProperty("user_id")
    public String userId;
    @JsonProperty("document_name")
    public String documentName;
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

    public static class SigningLinkRequest {
        @JsonProperty("document_id")
        public String documentId;

        public SigningLinkRequest(String documentId) {
            this.documentId = documentId;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @SuppressWarnings("java:S1104")  // field name equal to JsonProperty
    public static class SigningLinkResponse {
        public String url;
        @JsonProperty("url_no_signup")
        public String urlNoSignup;
    }

    @SuppressWarnings("java:S1104")  // field name equal to JsonProperty
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

    @SuppressWarnings("java:S1104")  // field name equal to JsonProperty
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
    @SuppressWarnings("java:S1104")  // field name equal to JsonProperty
    public static class InviteRole {
        public final String email;
        public final String role;
        @JsonProperty("role_id")
        public String roleId = "";
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

    public static class FieldsUpdateRequest {
        public final List<Field> fields;

        public FieldsUpdateRequest(List<Field> fields) {
            this.fields = fields;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @SuppressWarnings("java:S1104")  // field name equal to JsonProperty
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
    @SuppressWarnings("java:S1104")  // field name equal to JsonProperty
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
    @SuppressWarnings("java:S1104")  // field name equal to JsonProperty
    public static class DocumentDownloadLink {
        public String link;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MoveDocumentRequest {
        @JsonProperty("folder_id")
        public String folderId;

        public MoveDocumentRequest(String folderId) {
            this.folderId = folderId;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @SuppressWarnings("java:S1104")  // field name equal to JsonProperty
    public static class MoveDocumentResponse {
        public String result;
    }
}
