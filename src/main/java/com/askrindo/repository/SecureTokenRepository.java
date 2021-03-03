package com.askrindo.repository;

import com.askrindo.entity.SecureToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecureTokenRepository extends JpaRepository<SecureToken, String> {
    SecureToken findByConfirmToken(String confirmToken);
}
