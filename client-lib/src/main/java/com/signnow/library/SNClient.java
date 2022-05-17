package com.signnow.library;

import com.signnow.library.dto.AuthError;
import com.signnow.library.dto.Errors;
import com.signnow.library.dto.User;
import com.signnow.library.exceptions.SNApiException;
import com.signnow.library.exceptions.SNException;
import com.signnow.library.facades.DocumentGroups;
import com.signnow.library.facades.Documents;
import com.signnow.library.facades.ServiceProvider;
import com.signnow.library.facades.Templates;
import com.signnow.library.services.DocumentGroupsService;
import com.signnow.library.services.DocumentsService;
import com.signnow.library.services.TemplatesService;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

public class SNClient implements ServiceProvider {

    public static final String CLIENT_NAME = "SignNow Java API Client";
    public static final String CLIENT_INFO = System.getProperty("os.name") + "; "
            + System.getProperty("os.version") + "; "
            + System.getProperty("os.arch");
    public static final String USER_AGENT = CLIENT_NAME + "/"
            + SNClient.class.getPackage().getImplementationVersion()
            + " (" + CLIENT_INFO + ") "
            + System.getProperty("java.vendor") + "/" + System.getProperty("java.version");

    private final User user;
    private final WebTarget apiWebTarget;
    private final Documents documentsService = new DocumentsService(this);
    private final Templates templatesService = new TemplatesService(this);
    private final DocumentGroups documentGroupsService = new DocumentGroupsService(this);

    protected SNClient(WebTarget apiWebTarget, User user) {
        this.apiWebTarget = apiWebTarget;
        this.user = user;
    }

    public static void checkAPIException(Response response) throws SNException {
        if (response.getStatus() == 401 || response.getStatus() == 403) {
            throw new SNApiException(response.getStatus() + ": " + response.readEntity(AuthError.class).error);
        } else if (response.getStatus() >= 400) {
            throw new SNApiException(response.readEntity(Errors.class).errorList);
        }
    }

    @Override
    public Documents documentsService() {
        return documentsService;
    }

    @Override
    public Templates templatesService() {
        return templatesService;
    }

    @Override
    public DocumentGroups documentGroupsService() {
        return documentGroupsService;
    }

    public WebTarget getApiWebTarget() {
        return apiWebTarget;
    }

    public User getUser() {
        return user;
    }

    public <T> T get(String path, Map<String, String> parameters, Class<T> returnType) throws SNException {
        Response response = buildRequest(path, parameters).get();
        checkAPIException(response);
        return response.readEntity(returnType);
    }

    public <T> T get(String path, Map<String, String> parameters, GenericType<T> returnType) throws SNException {
        Response response = buildRequest(path, parameters).get();
        checkAPIException(response);
        return response.readEntity(returnType);
    }

    public <E, T> T post(String path, Map<String, String> parameters, E inputData, Class<T> returnType) throws SNException {
        Response response = buildRequest(path, parameters).post(Entity.entity(inputData, SNClientBuilder.defaultVariant));
        checkAPIException(response);
        return response.readEntity(returnType);
    }

    public <E, T> T post(String path, Map<String, String> parameters, E inputData, GenericType<T> returnType) throws SNException {
        Response response = buildRequest(path, parameters).post(Entity.entity(inputData, SNClientBuilder.defaultVariant));
        checkAPIException(response);
        return response.readEntity(returnType);
    }

    public <E, T> T put(String path, Map<String, String> parameters, E inputData, Class<T> returnType) throws SNException {
        Response response = buildRequest(path, parameters).put(Entity.entity(inputData, SNClientBuilder.defaultVariant));
        checkAPIException(response);
        return response.readEntity(returnType);
    }

    public <T> T delete(String path, Map<String, String> parameters, Class<T> returnType) throws SNException {
        Response response = buildRequest(path, parameters).delete();
        checkAPIException(response);
        return response.readEntity(returnType);
    }

    private Invocation.Builder buildRequest(String path, Map<String, String> parameters) {
        WebTarget target = apiWebTarget.path(path);
        if (parameters != null) {
            for (Map.Entry<String, String> entry : parameters.entrySet()){
                WebTarget targetUpd = target.resolveTemplate(entry.getKey(), entry.getValue());
                if (!targetUpd.toString().equals(target.toString())) {
                    target = targetUpd;
                } else {
                    target = target.queryParam(entry.getKey(), entry.getValue());
                }
            }
        }
        return target.request(MediaType.APPLICATION_JSON_TYPE)
                .header(Constants.AUTHORIZATION, Constants.BEARER + user.getToken())
                .header("User-Agent", USER_AGENT);
    }

    public User.UserAuthResponse checkAuth() throws SNException {
        return get("oauth2/token", null, User.UserAuthResponse.class);
    }

    public void refreshToken() throws SNException {
        SNClientBuilder.get().refreshToken(user);
    }
}
