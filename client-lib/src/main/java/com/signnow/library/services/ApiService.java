package com.signnow.library.services;

import com.signnow.library.SNClient;

public abstract class ApiService {
    protected SNClient client;

    protected ApiService(SNClient client) {
        this.client = client;
    }
}
