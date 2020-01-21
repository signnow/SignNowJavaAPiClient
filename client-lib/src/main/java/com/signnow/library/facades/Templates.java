package com.signnow.library.facades;

import com.signnow.library.exceptions.SNException;

/**
 * Templates operations operations service facade
 */
public interface Templates {
    String createTemplate(String sourceDocumentId, String templateName) throws SNException;

    String createDocumentFromTemplate(String sourceTemplateId, String newDocumentName) throws SNException;
}
