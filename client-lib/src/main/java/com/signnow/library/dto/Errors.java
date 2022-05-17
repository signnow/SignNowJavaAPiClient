package com.signnow.library.dto;

import java.util.List;

@SuppressWarnings("java:S1104")  // field name equal to JsonProperty
public class Errors {
    public List<ErrorInfo> errorList;

    public static class ErrorInfo {
        public Integer code;
        public String message;
    }
}
