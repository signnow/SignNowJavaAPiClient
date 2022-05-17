package com.signnow.library.facades;

import com.signnow.library.dto.Document;
import com.signnow.library.exceptions.SNException;

import java.io.InputStream;
import java.util.List;

/**
 * Document related operations service facade
 */
public interface Documents {
    String uploadDocument(InputStream stream, String fileName) throws SNException;

    String uploadDocumentWithTags(InputStream stream, String fileName) throws SNException;

    String moveDocument(String documentId, String folderId) throws SNException;

    List<Document> getDocuments() throws SNException;

    Document.SigningLinkResponse createSigningLink(String documentId) throws SNException;

    void sendDocumentSignInvite(String documentId, Document.SigningInviteRequest request) throws SNException;

    void sendDocumentSignInvite(String documentId, Document.SigningInviteWithRolesRequest request) throws SNException;

    void updateDocumentFields(String documentId, List<Document.Field> request) throws SNException;

    Document getDocument(String documentId) throws SNException;

    void deleteDocument(String documentId) throws SNException;

    String getDownloadLink(String documentId) throws SNException;
}
