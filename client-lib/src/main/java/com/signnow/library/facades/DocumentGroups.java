package com.signnow.library.facades;

import com.signnow.library.dto.DocumentGroup;
import com.signnow.library.dto.GroupInvite;
import com.signnow.library.exceptions.SNException;

import java.util.List;

/**
 * Document groups related operations service facade
 */
public interface DocumentGroups {
    com.signnow.library.dto.DocumentGroup getDocumentGroup(String documentGroupId) throws SNException;

    String createDocumentGroup(List<String> documentIds, String groupName) throws SNException;

    DocumentGroup.DocumentGroupsListResponse getUserDocumentGroups(Integer limit, Integer offset) throws SNException;

    void deleteDocumentGroup(String documentGroupId) throws SNException;

    String createDocumentGroupInvite(String documentGroupId, GroupInvite groupInvite) throws SNException;

    void resendInvites(String documentGroupId, String inviteId, String email) throws SNException;
}
