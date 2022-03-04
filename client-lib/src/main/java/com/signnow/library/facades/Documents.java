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

    List<Document> getDocuments() throws SNException;

    Document.SigningLinkResponce createSigningLink(String documentId) throws SNException;

    void sendDocumentSignInvite(String documentId, Document.SigningInviteRequest request) throws SNException;

    void sendDocumentSignInvite(String documentId, Document.SigningInviteWithRolesRequest request) throws SNException;

    Document.SigningEmbeddedInviteResponse createDocumentEmbeddedSignInvite(String documentId, Document.SigningEmbeddedInviteRequest request) throws SNException;

    String getDocumentEmbeddedSignInviteLink(String documentId, String inviteId, Document.GettingEmbeddedInviteLinkRequest request) throws SNException;

    void updateDocumentFields(String documentId, List<Document.Field> request) throws SNException;

    void prefillText(String documentId, List<Document.FieldText> request) throws SNException;

    Document getDocument(String documentId) throws SNException;

    void deleteDocument(String documentId) throws SNException;

    String getDownloadLink(String documentId) throws SNException;

}
