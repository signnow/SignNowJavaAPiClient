package com.signnow.library.services;

import com.signnow.library.SNClient;
import com.signnow.library.dto.GenericId;
import com.signnow.library.dto.Template;
import com.signnow.library.exceptions.SNException;
import com.signnow.library.facades.Templates;

import java.util.Collections;

public class TemplatesService extends ApiService implements Templates {
    public TemplatesService(SNClient client) {
        super(client);
    }

    public String createTemplate(String sourceDocumentId, String templateName) throws SNException {
        return client.post(
                "/template",
                null,
                new Template.CreateRequest(templateName, sourceDocumentId),
                GenericId.class
        ).id;
    }

    public String createDocumentFromTemplate(String sourceTemplateId, String newDocumentName) throws SNException {
        return client.post(
                "/template/{sourceTemplateId}/copy",
                Collections.singletonMap("sourceTemplateId", sourceTemplateId),
                new Template.CopyRequest(newDocumentName),
                GenericId.class
        ).id;
    }
}
