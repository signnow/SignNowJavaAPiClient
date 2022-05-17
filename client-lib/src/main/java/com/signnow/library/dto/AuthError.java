package com.signnow.library.dto;

import com.fasterxml.jackson.annotation.JsonSetter;

@SuppressWarnings("java:S1104")  // field name equal to JsonProperty
public class AuthError {
    public Type error;

    @JsonSetter("error")
    public void setType(String type) {
        this.error = Type.typeOf(type);
    }

    public enum Type {
        INVALID_CLIENT("invalid_client"),
        INVALID_REQUEST("invalid_request"),
        INVALID_TOKEN("invalid_token"),
        ACCESS_DENIED("access_denied"),
        UNKNOWN("unknown");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public static Type typeOf(String name) {
            for (Type type : values()) {
                if (type.name.equalsIgnoreCase(name)) {
                    return type;
                }
            }
            return UNKNOWN;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
