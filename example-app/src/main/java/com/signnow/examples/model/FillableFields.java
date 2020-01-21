package com.signnow.examples.model;

public class FillableFields extends DocumentInfo {
    private String signatureFieldRole;
    private String textFieldRole;

    public String getSignatureFieldRole() {
        return signatureFieldRole;
    }

    public void setSignatureFieldRole(String signatureFieldRole) {
        this.signatureFieldRole = signatureFieldRole;
    }

    public String getTextFieldRole() {
        return textFieldRole;
    }

    public void setTextFieldRole(String textFieldRole) {
        this.textFieldRole = textFieldRole;
    }
}
