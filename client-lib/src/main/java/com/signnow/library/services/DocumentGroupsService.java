package com.signnow.library.services;

import com.signnow.library.Constants;
import com.signnow.library.SNClient;
import com.signnow.library.dto.DocumentGroup;
import com.signnow.library.dto.GenericId;
import com.signnow.library.dto.GroupInvite;
import com.signnow.library.exceptions.SNApiException;
import com.signnow.library.exceptions.SNException;
import com.signnow.library.facades.DocumentGroups;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentGroupsService extends ApiService implements DocumentGroups {
    public static final String DEFAULT_PAGE_SIZE = "20";

    public DocumentGroupsService(SNClient client) {
        super(client);
    }

    @Override
    public DocumentGroup getDocumentGroup(String documentGroupId) throws SNException {
        return client.get(
                "/documentgroup/{documentGroupId}",
                Collections.singletonMap(Constants.DOCUMENT_GROUP_ID, documentGroupId),
                DocumentGroup.class
        );
    }

    @Override
    public String createDocumentGroup(List<String> documentIds, String groupName) throws SNException {
        return client.post(
                "/documentgroup",
                null,
                new DocumentGroup.DocumentGroupCreateRequest(documentIds, groupName),
                GenericId.class
        ).id;
    }

    @Override
    public DocumentGroup.DocumentGroupsListResponse getUserDocumentGroups(Integer limit, Integer offset) throws SNException {
        Map<String, String> params = new HashMap<>();
        if (limit != null) {
            if (limit <= 0) {
                throw new SNApiException("The limit query parameter is required and must be between 1 and 50");
            } else {
                params.put("limit", limit.toString());
            }
        } else {
            params.put("limit", DEFAULT_PAGE_SIZE);
        }
        if (offset != null) {
            if (offset < 0) {
                throw new SNApiException("offset must be zero or positive number");
            } else {
                params.put("offset", offset.toString());
            }
        }
        return client.get("/user/documentgroups", params, DocumentGroup.DocumentGroupsListResponse.class);
    }

    @Override
    public void deleteDocumentGroup(String documentGroupId) throws SNException {
        client.delete(
                "/documentgroup/{documentGroupId}",
                Collections.singletonMap(Constants.DOCUMENT_GROUP_ID, documentGroupId),
                String.class
        );
    }

    @Override
    public String createDocumentGroupInvite(String documentGroupId, GroupInvite groupInvite) throws SNException {
        return client.post(
                "/documentgroup/{documentGroupId}/groupinvite",
                Collections.singletonMap(Constants.DOCUMENT_GROUP_ID, documentGroupId),
                groupInvite,
                GenericId.class
        ).id;
    }

    @Override
    public void resendInvites(String documentGroupId, String inviteId, String email) throws SNException {
        Map<String, String> params = new HashMap<>();
        params.put(Constants.DOCUMENT_GROUP_ID, documentGroupId);
        params.put("inviteId", inviteId);

        client.post(
                "/documentgroup/{documentGroupId}/groupinvite/{inviteId}/resendinvites",
                params,
                email != null ? Collections.singletonMap("email", email) : null,
                GenericId.class
        );
    }
}
