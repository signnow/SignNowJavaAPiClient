package com.signnow.library.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class User {
    private String email;
    private String token;
    private String refreshToken;

    public User() {
    }

    public User(String email, String token, String refreshToken) {
        this.email = email;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @SuppressWarnings("java:S1104")  // field name equal to JsonProperty
    public static class UserAuthRequest {
        public final String username;
        public final String password;
        @JsonProperty("grant_type")
        public final String grantType;
        public final String scope;

        public UserAuthRequest(String username, String password) {
            this(username, password, "password", "*");
        }

        public UserAuthRequest(String username, String password, String scope) {
            this(username, password, "password", scope);
        }

        public UserAuthRequest(String username, String password, String grantType, String scope) {
            this.username = username;
            this.password = password;
            this.grantType = grantType;
            this.scope = scope;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @SuppressWarnings("java:S1104")  // field name equal to JsonProperty
    public static class UserAuthResponse {
        @JsonProperty("expires_in")
        public Integer expiresIn;
        @JsonProperty("token_type")
        public String tokenType;
        @JsonProperty("access_token")
        public String accessToken;
        @JsonProperty("refresh_token")
        public String refreshToken;
        public String scope;
        @JsonProperty("last_login")
        public Integer lastLogin;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @SuppressWarnings("java:S1104")  // field name equal to JsonProperty
    public static class UserCreateRequest {
        public String email;
        public String password;
        @JsonProperty("first_name")
        public String firstName;
        @JsonProperty("last_name")
        public String lastName;
        public String number;

        public UserCreateRequest(String email, String password) {
            this(email, password, null, null);
        }

        public UserCreateRequest(String email, String password, String firstName, String lastName) {
            this(email, password, firstName, lastName, null);
        }

        public UserCreateRequest(String email, String password, String firstName, String lastName, String number) {
            this.email = email;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
            this.number = number;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @SuppressWarnings("java:S1104")  // field name equal to JsonProperty
    public static class UserCreateResponse {
        public String id;
        public Integer verified;
        public String email;
    }

    @SuppressWarnings("java:S1104")  // field name equal to JsonProperty
    public static class UserInfo {
        public String id;
        @JsonProperty("first_name")
        public String firstName;
        @JsonProperty("last_name")
        public String lastName;
        public String active;
        public Integer type;
        public Integer pro;
        public String created;
        public List<String> emails;
        public Integer credits;
        @JsonProperty("has_atticus_access")
        public Boolean hasAtticusAccess;
        @JsonProperty("is_logged_in")
        public Boolean isLoggedIn;
    }

}
