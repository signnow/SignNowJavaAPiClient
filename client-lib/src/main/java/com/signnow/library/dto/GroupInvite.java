package com.signnow.library.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.List;

public class GroupInvite {
    @JsonProperty("invite_steps")
    public List<InviteStep> inviteSteps = new ArrayList<>();
    @JsonProperty("completion_emails")
    public List<InviteEmail> completionEmails = new ArrayList<>();

    public enum ActionType {
        SIGN("sign"),
        VIEW("view");

        private final String name;

        ActionType(String name) {
            this.name = name;
        }

        public static GroupInvite.ActionType typeOf(String name) {
            for (GroupInvite.ActionType type : values()) {
                if (type.name.equalsIgnoreCase(name)) {
                    return type;
                }
            }
            throw new IllegalArgumentException(name + " type not supported.");
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class InviteEmail {
        public String email;
        public String subject;
        public String message;
        public Boolean disabled;

        @JsonGetter("disabled")
        public int isDisabled() {
            if (disabled == null) {
                return 0;
            }
            return disabled ? 1 : 0;
        }

        @JsonSetter("disabled")
        public void setDisabled(int disabled) {
            this.disabled = disabled == 1;
        }
    }

    public static class InviteStep {
        public Integer order;
        @JsonProperty("invite_emails")
        public List<InviteEmail> inviteEmails = new ArrayList<>();
        @JsonProperty("invite_actions")
        public List<InviteAction> inviteActions = new ArrayList<>();
    }

    public static class InviteAction {
        public String email;
        @JsonProperty("role_name")
        public String roleName;
        @JsonProperty
        public ActionType action = ActionType.SIGN;
        @JsonProperty("document_id")
        public String documentId;
        @JsonProperty("allow_reassign")
        public Integer allowReassign;
        public InviteAuthentication authentication;

        @JsonGetter("action")
        public String getAction() {
            return action.toString();
        }

        @JsonSetter("action")
        public void setAction(String type) {
            this.action = GroupInvite.ActionType.typeOf(type);
        }
    }

    public static class InviteAuthentication {
        public String type;
        public String value;
    }
}
