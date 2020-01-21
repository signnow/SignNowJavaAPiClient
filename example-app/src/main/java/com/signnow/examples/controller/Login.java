package com.signnow.examples.controller;

import com.signnow.examples.model.AuthAttempt;
import com.signnow.examples.model.DocumentInfo;
import com.signnow.examples.services.SNApiService;
import com.signnow.examples.session.UserData;
import com.signnow.library.exceptions.SNException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Provider;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/")
@Controller
public class Login {

    @Autowired
    private SNApiService apiService;

    @Autowired
    private Provider<UserData> provider;

    @GetMapping
    public String login(Model model) {
        model.addAttribute("authAttempt", new AuthAttempt());
        return "index";
    }

    @PostMapping
    public String login(@ModelAttribute AuthAttempt authAttempt) {
        String page = authAttempt.getRedirectPage();
        final UserData userData = provider.get();
        userData.setSnClient(apiService.getSNClient(authAttempt.getEmail(), authAttempt.getPassword()));
        userData.setUserDocuments(getDocumentList());
        return "redirect:" + (page == null || page.isEmpty() ? "/examples/" : page);
    }

    private Set<DocumentInfo> getDocumentList() {
        try {
            return provider.get()
                    .getSnClient()
                    .documentsService()
                    .getDocuments()
                    .stream()
                    .map(doc -> new DocumentInfo(doc.id, doc.document_name))
                    .collect(Collectors.toSet());
        } catch (SNException e) {
            e.printStackTrace();
        }
        return new HashSet<>();
    }

}
