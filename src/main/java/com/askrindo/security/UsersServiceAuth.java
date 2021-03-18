package com.askrindo.security;

import com.askrindo.entity.Users;
import com.askrindo.entity.UsersHelper;
import com.askrindo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceAuth implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

//    @Autowired
//    SecureTokenService secureTokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Collection<GrantedAuthority> listOfgrantedAuthorities = new ArrayList<>();
        System.out.println(username);
        try{
            Optional<Users> optionalUser = userRepository.findUsersByEmail(username);
            if (!optionalUser.isPresent()){
                throw new UsernameNotFoundException(username+"Not Found!!");
            }

            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(optionalUser.get().getUserRole());
            listOfgrantedAuthorities.add(grantedAuthority);

            UsersHelper userDetail = new UsersHelper(optionalUser.get());
            return userDetail;

        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
    }

    public void registerUser(Users user) throws IOException {
        if (user.getId() != null) {
//            Users existUsers = userRepository.findById(user.getId()).get();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
//            user.setVersion(existUsers.getVersion() + 1);
            userRepository.save(user);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
//            user.setVersion(0);
//            user.setIsActive("false");
            userRepository.save(user);
//            if (user.getRole().equalsIgnoreCase("03")) {
//                secureTokenService.getSecureToken(user);
//            }
        }
    }

    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

    public Users getUser(String username){
        Optional<Users> usersOptional = userRepository.findUsersByEmail(username);
        return usersOptional.get();
    }

    public void updatePassword(String email, String newPassword) {
        Users users = userRepository.findUsersByEmail(email).get();
        users.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(users);
    }

}