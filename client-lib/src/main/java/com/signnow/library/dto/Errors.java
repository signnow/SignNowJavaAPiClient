package com.signnow.library.dto;

import java.util.List;

public class Errors {
    public List<ErrorInfo> errors;

    public static class ErrorInfo {
        public Integer code;
        public String message;
    }
}
