package com.signnow.library.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentGroup extends GenericId {
    @JsonProperty("group_name")
    public String groupName;
    @JsonProperty("invite_id")
    public String inviteId;
    @JsonProperty("invite_status")
    public String inviteStatus;
    public List<DocumentInfo> documents = new ArrayList<>();

    @JsonSetter("group_id")
    public void setGroupId(String groupId) {
        this.id = groupId;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DocumentInfo extends GenericId {
        public List<String> roles = new ArrayList<>();
        public String documentName;
        public String name;

        @JsonSetter("document_name")
        public void setDocumentName(String documentName) {
            this.documentName = documentName;
        }

        @JsonSetter("name")
        public void setName(String name) {
            this.name = name;
        }
    }

    public static class DocumentGroupCreateRequest {
        @JsonProperty("document_ids")
        public final List<String> documentIds;
        @JsonProperty("group_name")
        public final String groupName;

        public DocumentGroupCreateRequest(List<String> documentIds, String groupName) {
            this.documentIds = documentIds;
            this.groupName = groupName;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DocumentGroupsListResponce {
        @JsonProperty("document_groups")
        public List<DocumentGroup> documentGroups;
        @JsonProperty("document_group_total_count")
        public Integer totalCount;
    }
}
