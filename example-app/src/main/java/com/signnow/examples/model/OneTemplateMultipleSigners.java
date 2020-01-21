package com.signnow.examples.model;

import java.util.ArrayList;
import java.util.List;

public class OneTemplateMultipleSigners extends DocumentInfo {
    private String senderEmail;
    private String subject;
    private String message;
    private List<Signer> signers = new ArrayList<>();

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Signer> getSigners() {
        return signers;
    }

    public void setSigners(List<Signer> signers) {
        this.signers = signers;
    }
}
