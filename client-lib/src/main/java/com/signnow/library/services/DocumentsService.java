package com.signnow.library.services;

import com.signnow.library.Constants;
import com.signnow.library.SNClient;
import com.signnow.library.dto.Document;
import com.signnow.library.exceptions.SNException;
import com.signnow.library.facades.Documents;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class DocumentsService extends ApiService implements Documents {
    public DocumentsService(SNClient client) {
        super(client);
    }

    @Override
    public String uploadDocument(InputStream inputStream, String fileName) throws SNException {
        return uploadDocument(inputStream, fileName, false);
    }

    @Override
    public String uploadDocumentWithTags(InputStream inputStream, String fileName) throws SNException {
        return uploadDocument(inputStream, fileName, true);
    }

    private String uploadDocument(InputStream inputStream, String fileName, boolean extractTags) throws SNException {
        FormDataMultiPart fdmp = new FormDataMultiPart();
        fdmp.bodyPart(new StreamDataBodyPart("file", inputStream, fileName));
        Response response = client.getApiWebTarget().path("/document" + (extractTags ? "/fieldextract" : ""))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header(Constants.AUTHORIZATION, Constants.BEARER + client.getUser().getToken())
                .post(Entity.entity(fdmp, MediaType.MULTIPART_FORM_DATA_TYPE));
        SNClient.checkAPIException(response);
        return response.readEntity(Document.class).id;
    }

    @Override
    public String moveDocument(String documentId, String folderId) throws SNException {
        return client.post(
                "/document/{documentId}/move",
                Collections.singletonMap(Constants.DOCUMENT_ID, documentId),
                new Document.MoveDocumentRequest(folderId),
                Document.MoveDocumentResponse.class
        ).result;
    }

    @Override
    public List<Document> getDocuments() throws SNException {
        return client.get("/user/documentsv2", null, new GenericType<>() {});
    }

    @Override
    public Document.SigningLinkResponse createSigningLink(String documentId) throws SNException {
        return client.post("/link", null, new Document.SigningLinkRequest(documentId), Document.SigningLinkResponse.class);
    }

    @Override
    public void sendDocumentSignInvite(String documentId, Document.SigningInviteRequest request) throws SNException {
        client.post(
                "/document/{documentId}/invite",
                Collections.singletonMap(Constants.DOCUMENT_ID, documentId),
                request,
                String.class
        );
    }

    @Override
    public void sendDocumentSignInvite(String documentId, Document.SigningInviteWithRolesRequest request) throws SNException {
        client.post(
                "/document/{documentId}/invite",
                Collections.singletonMap(Constants.DOCUMENT_ID, documentId),
                request,
                String.class
        );
    }

    @Override
    public void updateDocumentFields(String documentId, List<Document.Field> request) throws SNException {
        client.put(
                Constants.PATH_TO_DOCUMENT_ID,
                Collections.singletonMap(Constants.DOCUMENT_ID, documentId),
                new Document.FieldsUpdateRequest(request),
                String.class
        );
    }

    @Override
    public Document getDocument(String documentId) throws SNException {
        return client.get(
                Constants.PATH_TO_DOCUMENT_ID,
                Collections.singletonMap(Constants.DOCUMENT_ID, documentId),
                Document.class
        );
    }

    @Override
    public void deleteDocument(String documentId) throws SNException {
        client.delete(
                Constants.PATH_TO_DOCUMENT_ID,
                Collections.singletonMap(Constants.DOCUMENT_ID, documentId),
                String.class
        );
    }

    @Override
    public String getDownloadLink(String documentId) throws SNException {
        return client.post(
                "/document/{documentId}/download/link",
                Collections.singletonMap(Constants.DOCUMENT_ID, documentId),
                null,
                Document.DocumentDownloadLink.class
        ).link;
    }
}
