package com.askrindo.entity;

import org.springframework.security.core.userdetails.User;
public class UsersHelper extends User {

    private static final long serialVersionUID = 1L;
    public UsersHelper(Users user) {
        super(
                user.getEmail(),
                user.getPassword(),
                user.getListOfgrantedAuthorities()
        );
    }
}
