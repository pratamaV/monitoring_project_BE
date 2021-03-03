package com.askrindo.service;

import com.askrindo.entity.Users;

import java.io.IOException;

public interface SecureTokenService {
    public void getSecureToken (Users users) throws IOException;
}
