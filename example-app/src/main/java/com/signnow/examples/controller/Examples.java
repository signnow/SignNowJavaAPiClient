package com.signnow.examples.controller;

import com.signnow.examples.model.*;
import com.signnow.examples.session.UserData;
import com.signnow.library.SNClient;
import com.signnow.library.dto.Document;
import com.signnow.library.dto.GroupInvite;
import com.signnow.library.exceptions.SNException;
import com.signnow.library.facades.Documents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Provider;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/examples")
@Controller
public class Examples {

    @Autowired
    private Provider<UserData> provider;

    @GetMapping("/")
    public String login(RedirectAttributes redirectAttributes) {
        if (isUserNotAuthorized()) {
            return getLoginRedirectPage(redirectAttributes, "/examples/");
        }

        return "examples";
    }

    @GetMapping("/uploadDocument")
    public String uploadDocument(RedirectAttributes redirectAttributes) {
        if (isUserNotAuthorized()) {
            return getLoginRedirectPage(redirectAttributes, "/examples/uploadDocument");
        }

        return "upload_document";
    }

    @PostMapping("/uploadDocument")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   Model model,
                                   RedirectAttributes redirectAttributes
    ) {
        try {
            if (isUserNotAuthorized()) {
                return getLoginRedirectPage(redirectAttributes, "/examples/uploadDocument");
            }
            String documentId = provider.get().getSnClient()
                    .documentsService()
                    .uploadDocument(file.getInputStream(), file.getOriginalFilename());
            updateDocumentList();
            model.addAttribute("message", "fileId: " + documentId);
        } catch (IOException | SNException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
        }
        return "upload_document";
    }

    @GetMapping("/signingLink")
    public String signingLink(RedirectAttributes redirectAttributes, Model model) {
        if (isUserNotAuthorized()) {
            return getLoginRedirectPage(redirectAttributes, "/examples/signingLink");
        }
        model.addAttribute("signingLink", new DocumentInfo());
        model.addAttribute("documentList", provider.get().getUserDocuments());
        return "signing_link";
    }

    @PostMapping("/signingLink")
    public String singingLink(@ModelAttribute DocumentInfo signingLinkModel, Model model, RedirectAttributes redirectAttributes) {
        if (isUserNotAuthorized()) {
            return getLoginRedirectPage(redirectAttributes, "/examples/signingLink");
        }
        try {
            String documentId = signingLinkModel.getDocumentId();
            //Create signature field in document
            Document.Field sign = createExampleField(20, 20, "Signer role");

            //update document with signeture field
            Documents documentsService = provider.get().getSnClient().documentsService();

            documentsService.updateDocumentFields(documentId, Arrays.asList(sign));

            //create signing link
            String link = documentsService.createSigningLink(documentId).url;
            model.addAttribute("documentList", provider.get().getUserDocuments());
            model.addAttribute("link", link);
        } catch (SNException e) {
            String message = String.format(
                    "Couldn't create signing link for document %s. Error %s %s",
                    signingLinkModel.getDocumentId(),
                    e.getMessage(),
                    e.getCause()
            );
            model.addAttribute("message", message);
        }
        model.addAttribute("signingLink", new DocumentInfo());
        return "signing_link";
    }

    @GetMapping("/simpleFromInvite")
    public String simpleFromInvite(RedirectAttributes redirectAttributes, Model model) {
        if (isUserNotAuthorized()) {
            return getLoginRedirectPage(redirectAttributes, "/examples/simpleFromInvite");
        }
        model.addAttribute("documentList", provider.get().getUserDocuments());
        model.addAttribute("simpleFromInvite", new SimpleFromInvite());
        return "simple_from_invite";
    }

    @PostMapping("/simpleFromInvite")
    public String simpleFromInvite(@ModelAttribute SimpleFromInvite simpleFromInvite, Model model, RedirectAttributes redirectAttributes) {
        if (isUserNotAuthorized()) {
            return getLoginRedirectPage(redirectAttributes, "/examples/simpleFromInvite");
        }

        final String fromEmail = simpleFromInvite.getFrom();
        final String toEmail = simpleFromInvite.getTo();
        final String documentId = simpleFromInvite.getDocumentId();

        try {
            model.addAttribute("documentList", provider.get().getUserDocuments());
            //create request for invite
            Document.SigningInviteRequest request = new Document.SigningInviteRequest(
                    fromEmail,
                    toEmail
            );
            request.subject = simpleFromInvite.getSubject();
            request.message = simpleFromInvite.getMessage();

            //send request for invite
            provider.get().getSnClient()
                    .documentsService()
                    .sendDocumentSignInvite(documentId, request);

            model.addAttribute("message",
                    String.format("Invitation for sign document %s already sent to %s from %s",
                            documentId,
                            toEmail,
                            fromEmail
                    )
            );
        } catch (SNException e) {
            String message = String.format("Couldn't send invitation request from %s to %s for sign document %s. %s %s"
                    , fromEmail, toEmail, documentId, e.getMessage(), e.getCause());
            model.addAttribute("message", message);
        }

        return "simple_from_invite";
    }

    @GetMapping("/oneTemplateMultipleSigners")
    public String oneTemplateMultipleSigners(RedirectAttributes redirectAttributes, Model model) {
        if (isUserNotAuthorized()) {
            return getLoginRedirectPage(redirectAttributes, "/examples/oneTemplateMultipleSigners");
        }
        model.addAttribute("documentList", provider.get().getUserDocuments());
        //adding two signers for example
        OneTemplateMultipleSigners from = new OneTemplateMultipleSigners();
        from.getSigners().add(new Signer());
        from.getSigners().add(new Signer());
        model.addAttribute("oneTemplateMultipleSigners", new OneTemplateMultipleSigners());
        return "one_template_multiple_signers_diff_roles";
    }

    @PostMapping("/oneTemplateMultipleSigners")
    public String oneTemplateMultipleSigners(@ModelAttribute OneTemplateMultipleSigners oneTemplateMultipleSigners, Model model, RedirectAttributes redirectAttributes) {
        if (isUserNotAuthorized()) {
            return getLoginRedirectPage(redirectAttributes, "/examples/oneTemplateMultipleSigners");
        }

        final String senderEmail = oneTemplateMultipleSigners.getSenderEmail();
        final String documentId = oneTemplateMultipleSigners.getDocumentId();
        final Signer first = oneTemplateMultipleSigners.getSigners().get(0);
        final Signer second = oneTemplateMultipleSigners.getSigners().get(1);

        try {
            model.addAttribute("documentList", provider.get().getUserDocuments());

            //Create signature field in document
            Document.Field sign = createExampleField(25, 25, first.getRole());
            //Signature field for second signer
            Document.Field sign2 = createExampleField(10, 60, second.getRole());

            //update document with signature field
            final Documents documentsService = provider.get().getSnClient().documentsService();
            documentsService.updateDocumentFields(documentId, Arrays.asList(sign, sign2));

            //create signers to roles mapping
            Document.InviteRole role1 = new Document.InviteRole(first.getEmail(), first.getRole());
            role1.password = first.getPassword();
            Document.InviteRole role2 = new Document.InviteRole(second.getEmail(), second.getRole());
            role2.password = second.getPassword();

            //execute request for invite
            Document.SigningInviteWithRolesRequest request = new Document.SigningInviteWithRolesRequest(
                    senderEmail,
                    Arrays.asList(role1, role2)
            );
            request.subject = oneTemplateMultipleSigners.getSubject();
            request.message = oneTemplateMultipleSigners.getMessage();
            documentsService.sendDocumentSignInvite(documentId, request);
            model.addAttribute("message",
                    String.format("Invitation request already sent from %s to %s and %s.",
                            senderEmail,
                            first.getEmail(),
                            second.getEmail()
                    )
            );
        } catch (SNException e) {
            String message = String.format(
                    "Couldn't sent invitation request from %s to {%s, %s}. Document id: %s. Error: %s %s",
                    senderEmail,
                    first.getEmail(),
                    second.getEmail(),
                    documentId,
                    e.getMessage(),
                    e.getCause()
            );
            model.addAttribute("message", message);
        }

        return "one_template_multiple_signers_diff_roles";
    }

    @GetMapping("/multipleTemplatesAndSigners")
    public String multipleTemplatesAndSigners(RedirectAttributes redirectAttributes, Model model) {
        if (isUserNotAuthorized()) {
            return getLoginRedirectPage(redirectAttributes, "/examples/multipleTemplatesAndSigners");
        }
        model.addAttribute("documentList", provider.get().getUserDocuments());
        MultipleTemplatesAndSigners formData = new MultipleTemplatesAndSigners();
        formData.getSigners().add(new Signer());
        formData.getSigners().add(new Signer());
        model.addAttribute("multipleTemplatesAndSigners", formData);
        return "invite_multiple_templates__signers";
    }

    @PostMapping("/multipleTemplatesAndSigners")
    public String multipleTemplatesAndSigners(@ModelAttribute MultipleTemplatesAndSigners multipleTemplatesAndSigners, Model model, RedirectAttributes redirectAttributes) {
        if (isUserNotAuthorized()) {
            return getLoginRedirectPage(redirectAttributes, "/examples/multipleTemplatesAndSigners");
        }

        String message = "";
        try {
            final Signer first = multipleTemplatesAndSigners.getSigners().get(0);
            final Signer second = multipleTemplatesAndSigners.getSigners().get(1);
            final String documentId1 = multipleTemplatesAndSigners.getDocumentIds().get(0);
            final String documentId2 = multipleTemplatesAndSigners.getDocumentIds().get(1);

            //Create signature field in document
            Document.Field sign = createExampleField(10, 10, first.getRole());
            Document.Field sign2 = createExampleField(10, 60, second.getRole());

            //update document with signature field
            final SNClient snClient = provider.get().getSnClient();
            snClient.documentsService().updateDocumentFields(documentId1, Arrays.asList(sign, sign2));

            String documentGroupId = snClient.documentGroupsService().createDocumentGroup(Arrays.asList(documentId1, documentId2), "Document Group 1");

            //configure invite for document group
            GroupInvite gInvite = new GroupInvite();
            //competition emails
            GroupInvite.Email completeEmail = new GroupInvite.Email();
            completeEmail.email = multipleTemplatesAndSigners.getEmail();
            completeEmail.message = multipleTemplatesAndSigners.getMessage();
            completeEmail.subject = multipleTemplatesAndSigners.getSubject();
            gInvite.completionEmails.add(completeEmail);

            //invite steps (at least one)
            GroupInvite.InviteStep inviteStep = new GroupInvite.InviteStep();
            inviteStep.order = 1;
            //step action to take
            GroupInvite.InviteAction signAction = createGroupInviteAction(documentId1, first.getRole(), first.getEmail());

            inviteStep.inviteActions.add(signAction);
            gInvite.inviteSteps.add(inviteStep);

            //second step
            inviteStep = new GroupInvite.InviteStep();
            //follows after the first one
            inviteStep.order = 2;
            //step action to take
            signAction = createGroupInviteAction(documentId2, second.getRole(), second.getEmail());

            inviteStep.inviteActions.add(signAction);
            gInvite.inviteSteps.add(inviteStep);

            //create invite for document group
            String inviteId = snClient.documentGroupsService().createDocumentGroupInvite(documentGroupId, gInvite);
            message = String.format("Invite for group successfully sent. Invite id: %s", inviteId);
        } catch (SNException e) {
            message = String.format("Couldn't send invitation. Error: %s %s", e.getMessage(), e.getCause());
        } finally {
            model.addAttribute("message", message);
        }

        return "invite_multiple_templates__signers";
    }

    @GetMapping("/prefiledText")
    public String prefiledText(RedirectAttributes redirectAttributes, Model model) {
        if (isUserNotAuthorized()) {
            return getLoginRedirectPage(redirectAttributes, "/examples/prefiledText");
        }
        model.addAttribute("documentList", provider.get().getUserDocuments());
        model.addAttribute("prefilledText", new PrefilledText());
        return "set_initial_values_for_template_fields";
    }

    @PostMapping("/prefiledText")
    public String prefiledText(@ModelAttribute PrefilledText prefilledText, Model model, RedirectAttributes redirectAttributes) {
        if (isUserNotAuthorized()) {
            return getLoginRedirectPage(redirectAttributes, "/examples/prefiledText");
        }

        String message = "";
        final String documentId = prefilledText.getDocumentId();
        final String prefilledTxt = prefilledText.getPrefiledText();
        try {
            //Create a field on document
            Document.Field text = new Document.Field();
            text.x = 10;
            text.y = 10;
            text.height = 30;
            text.width = 150;
            text.type = Document.FieldType.TEXT;
            text.required = true;
            text.pageNumber = 0;
            text.label = "Fill in value here";
            //set prefilled value for the field
            text.prefilledText = prefilledTxt;

            model.addAttribute("documentList", provider.get().getUserDocuments());
            //update document fields
            provider.get().getSnClient().documentsService().updateDocumentFields(documentId, Arrays.asList(text));
            message = String.format("Prefilled text '%s' successfully set for document id %s", prefilledTxt, documentId);
        } catch (SNException e) {
            message = String.format("Couldn't set prefilled text. Error: %s %s", e.getMessage(), e.getCause());
        } finally {
            model.addAttribute("message", message);
        }

        return "set_initial_values_for_template_fields";
    }

    @GetMapping("/createTemplate")
    public String createTemplate(RedirectAttributes redirectAttributes, Model model) {
        if (isUserNotAuthorized()) {
            return getLoginRedirectPage(redirectAttributes, "/examples/createTemplate");
        }
        model.addAttribute("documentList", provider.get().getUserDocuments());
        model.addAttribute("template", new CreateTemplate());
        return "create_template";
    }

    @PostMapping("/createTemplate")
    public String createTemplate(@ModelAttribute CreateTemplate templateInfo, Model model, RedirectAttributes redirectAttributes) {
        if (isUserNotAuthorized()) {
            return getLoginRedirectPage(redirectAttributes, "/examples/createTemplate");
        }

        final String documentId = templateInfo.getDocumentId();
        final String templateName = templateInfo.getTemplateName();

        try {
            String templateId = provider.get().getSnClient()
                    .templatesService()
                    .createTemplate(documentId, templateName);
            updateDocumentList();
            model.addAttribute("template", new CreateTemplate());
            model.addAttribute("documentList", provider.get().getUserDocuments());
            model.addAttribute("message", String.format("Template %s created from document %s", templateId, documentId));
        } catch (SNException e) {
            String message = String.format(
                    "Couldn't create template from document %s. %s %s",
                    documentId,
                    e.getMessage(),
                    e.getCause()
            );
            model.addAttribute("message", message);
        }

        return "create_template";
    }

    @GetMapping("/uploadDocumentWithTags")
    public String uploadDocumentWithTags(RedirectAttributes redirectAttributes, Model model) {
        if (isUserNotAuthorized()) {
            return getLoginRedirectPage(redirectAttributes, "/examples/uploadDocumentWithTags");
        }

        return "add_text_tag_on_document";
    }

    @PostMapping("/uploadDocumentWithTags")
    public String uploadDocumentWithTags(@RequestParam("file") MultipartFile file,
                                         Model model,
                                         RedirectAttributes redirectAttributes
    ) {
        try {
            if (isUserNotAuthorized()) {
                return getLoginRedirectPage(redirectAttributes, "/examples/uploadDocumentWithTags");
            }
            //upload document with tags to parse
            String documentId = provider.get().getSnClient()
                    .documentsService()
                    .uploadDocumentWithTags(file.getInputStream(), file.getOriginalFilename());
            updateDocumentList();
            model.addAttribute("message", "fileId: " + documentId);
        } catch (IOException | SNException e) {
            model.addAttribute("message", String.format("Couldn't upload document %s because %s %s"
                    , file.getOriginalFilename(), e.getMessage(), e.getCause())
            );
        }
        return "add_text_tag_on_document";
    }

    @GetMapping("/addFillableFields")
    public String addFillableFields(RedirectAttributes redirectAttributes, Model model) {
        if (isUserNotAuthorized()) {
            return getLoginRedirectPage(redirectAttributes, "/examples/addFillableFields");
        }
        model.addAttribute("documentList", provider.get().getUserDocuments());
        model.addAttribute("fillableFields", new FillableFields());
        return "add_fillable_fields";
    }

    @PostMapping("/addFillableFields")
    public String addFillableFields(@ModelAttribute FillableFields fillableFields, Model model, RedirectAttributes redirectAttributes) {
        if (isUserNotAuthorized()) {
            return getLoginRedirectPage(redirectAttributes, "/examples/addFillableFields");
        }

        final String documentId = fillableFields.getDocumentId();

        try {
            model.addAttribute("fillableFields", new FillableFields());
            model.addAttribute("documentList", provider.get().getUserDocuments());
            //Signature field
            Document.Field sign = createExampleField(10, 10, fillableFields.getSignatureFieldRole());

            //Text field
            Document.Field text = new Document.Field();
            text.x = 10;
            text.y = 60;
            text.height = 30;
            text.width = 150;
            text.type = Document.FieldType.TEXT;
            text.required = false;
            text.pageNumber = 0;
            text.role = fillableFields.getTextFieldRole();
            text.label = "Fill in value here";

            //update document fields
            provider.get().getSnClient()
                    .documentsService()
                    .updateDocumentFields(documentId, Arrays.asList(sign, text));

            model.addAttribute("message", String.format("Fillable fields added to the document %s", documentId));
        } catch (SNException e) {
            String message = String.format("Couldn't adding fillable fields to document %s. %s %s"
                    , documentId, e.getMessage(), e.getCause()
            );
            model.addAttribute("message", message);
        }

        return "add_fillable_fields";
    }

    private boolean isUserNotAuthorized() {
        if (provider.get() == null) {
            return true;
        }
        return provider.get().getSnClient() == null;
    }

    private String getLoginRedirectPage(RedirectAttributes redirectAttributes, String redirectPage) {
        redirectAttributes.addFlashAttribute("exception", "User not authorized");
        redirectAttributes.addFlashAttribute("page", redirectPage);
        return "redirect:/";
    }

    private List<Document> getDocumentList() throws SNException {
        return provider.get()
                .getSnClient()
                .documentsService()
                .getDocuments();
    }

    private void updateDocumentList() {
        final List<Document> documentList;
        try {
            documentList = getDocumentList();
            final UserData userData = provider.get();
            for (Document document : documentList) {
                userData.addDocument(new DocumentInfo(document.id, document.document_name));
            }
        } catch (SNException e) {
            e.printStackTrace();
        }
    }

    private Document.Field createExampleField(int x, int y, String roleName) {
        Document.Field sign = new Document.Field();
        sign.x = x;
        sign.y = y;
        sign.height = 30;
        sign.width = 150;
        sign.type = Document.FieldType.SIGNATURE;
        sign.required = true;
        sign.pageNumber = 0;
        sign.role = roleName;
        sign.label = "Your signature here";

        return sign;
    }

    private GroupInvite.InviteAction createGroupInviteAction(String documentId, String roleName, String email) {
        GroupInvite.InviteAction signAction = new GroupInvite.InviteAction();
        signAction.documentId = documentId;
        signAction.roleName = roleName;
        signAction.email = email;
        return signAction;
    }
}
