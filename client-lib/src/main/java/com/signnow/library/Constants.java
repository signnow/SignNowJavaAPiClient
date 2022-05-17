package com.signnow.library;

public class Constants {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String DOCUMENT_GROUP_ID = "documentGroupId";
    public static final String DOCUMENT_ID = "documentId";
    public static final String PATH_TO_DOCUMENT_ID = "/document/{documentId}";  //NOSONAR internal URI

    private Constants() {
        throw new IllegalStateException("Constants class");
    }
}
