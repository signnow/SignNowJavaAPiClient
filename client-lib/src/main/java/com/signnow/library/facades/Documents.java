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

    void updateDocumentFields(String documentId, List<Document.Field> request) throws SNException;

    Document getDocument(String documentId) throws SNException;

    void deleteDocument(String documentId) throws SNException;

    String getDownloadLink(String documentId) throws SNException;

    Document.EmbeddedInviteResponse createEmbeddedInvites(final String documentId,
                                                          final Document.EmbeddedInviteRequest request) throws SNException;

    Document.GenerateEmbeddedSigningLinkResponse generateEmbeddedInviteLink(final String documentId,
                                                                            final String fieldId,
                                                                            final Document.GenerateEmbeddedSigningLinkRequest request) throws SNException;

    /**
     * All embedded invites created for the document will be deleted.
     *
     * @param documentId the documentId of the document containing embedded invites.
     * @return the 204 status code will be returned if the embedded invites have been deleted successfully.
     * @throws SNException
     */
    int deleteEmbeddedInvite(final String documentId) throws SNException;
}
