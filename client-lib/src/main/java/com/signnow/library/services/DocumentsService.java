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
import java.util.Map;

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
    public List<Document> getDocuments() throws SNException {
        return client.get("/user/documentsv2", null, new GenericType<List<Document>>() {});
    }

    @Override
    public Document.SigningLinkResponce createSigningLink(String documentId) throws SNException {
        return client.post("/link", null, new Document.SigningLinkRequest(documentId), Document.SigningLinkResponce.class);
    }

    @Override
    public void sendDocumentSignInvite(String documentId, Document.SigningInviteRequest request) throws SNException {
        client.post(
                "/document/{documentId}/invite",
                Collections.singletonMap("documentId", documentId),
                request,
                String.class
        );
    }

    @Override
    public void sendDocumentSignInvite(String documentId, Document.SigningInviteWithRolesRequest request) throws SNException {
        client.post(
                "/document/{documentId}/invite",
                Collections.singletonMap("documentId", documentId),
                request,
                String.class
        );
    }

    @Override
    public void updateDocumentFields(String documentId, List<Document.Field> request) throws SNException {
        client.put(
                "/document/{documentId}",
                Collections.singletonMap("documentId", documentId),
                new Document.FieldsUpdateRequest(request),
                String.class
        );
    }

    @Override
    public Document getDocument(String documentId) throws SNException {
        return client.get(
                "/document/{documentId}",
                Collections.singletonMap("documentId", documentId),
                Document.class
        );
    }

    @Override
    public void deleteDocument(String documentId) throws SNException {
        client.delete(
                "/document/{documentId}",
                Collections.singletonMap("documentId", documentId),
                String.class
        );
    }

    @Override
    public String getDownloadLink(String documentId) throws SNException {
        return client.post(
                "/document/{documentId}/download/link",
                Collections.singletonMap("documentId", documentId),
                null,
                Document.DocumentDownloadLink.class
        ).link;
    }

    @Override
    public Document.EmbeddedInviteResponse createEmbeddedInvites(String documentId, Document.EmbeddedInviteRequest request) throws SNException {
        return client.post(
                "/v2/documents/{documentUniqueId}/embedded-invites",
                Collections.singletonMap("documentUniqueId", documentId),
                request,
                Document.EmbeddedInviteResponse.class
        );
    }

    @Override
    public Document.GenerateEmbeddedSigningLinkResponse generateEmbeddedInviteLink(String documentId, String fieldId, Document.GenerateEmbeddedSigningLinkRequest request) throws SNException {
        return client.post(
                "/v2/documents/{document_id}/embedded-invites/{fieldInviteUniqueId}/link",
                Map.of("document_id", documentId, "fieldInviteUniqueId", fieldId),
                request,
                Document.GenerateEmbeddedSigningLinkResponse.class
        );
    }

    @Override
    public int deleteEmbeddedInvite(String documentId) throws SNException {
        return client.delete(
                "/v2/documents/{document_id}/embedded-invites",
                Collections.singletonMap("document_id", documentId)
        );
    }
}
