package com.askrindo.security;

import com.askrindo.GlobalKey;
import com.askrindo.entity.Mail;
import com.askrindo.entity.Users;
import com.askrindo.entity.UsersHelper;
import com.askrindo.exception.OldPasswordNotMatchesException;
import com.askrindo.repository.UserRepository;
import com.askrindo.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class UsersServiceAuth implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    MailService mailService;

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

    public void changeAllPassword(String newPassword) throws IOException {
        List<Users> users = userRepository.findAll();
        for (Users userObj: users) {
            userObj.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(userObj);
        }

    }

    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

    public Users getUser(String username){
        Optional<Users> usersOptional = userRepository.findUsersByEmail(username);
        return usersOptional.get();
    }

    public char[] generatePasswordDefault(Integer length) {
        // A strong password has Cap_chars, Lower_chars,
        // numeric value and symbols. So we are using all of
        // them to generate our password
        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String values = Capital_chars + Small_chars +
                numbers;

        // Using random method
        Random rndm_method = new Random();
        char[] password = new char[length];
        for (int i = 0; i < length; i++) {
            // Use of charAt() method : to get character value
            // Use of nextInt() as it is scanning the value as int
            password[i] = values.charAt(rndm_method.nextInt(values.length()));

        }
        return password;
    }

    public void forgotPassword(String email) {
        Users users = userRepository.findUsersByEmail(email).get();
        if (users.getId() != null) {
            String userDefaultPassword = "2Up" + String.copyValueOf(this.generatePasswordDefault(6));
            users.setPassword(passwordEncoder.encode(userDefaultPassword));
            userRepository.save(users);
            Mail mail = new Mail();
            mail.setMailFrom("imo.askrindo@gmail.com");
            mail.setMailTo(users.getEmail());
            mail.setMailSubject("IMO Change Password");
            mail.setMailContent("Kepada Yth. Bapak / Ibu.\n\nBerikut merupakan password baru untuk akun anda : " + userDefaultPassword + " \n\nTerima kasih.\nTeam IMO Askrindo");
            mailService.sendEmail(mail);
        } else {

        }
    }

    public void updatePassword(String id, String oldPassword, String newPassword) {
        Optional<Users> user = userRepository.findById(id);
        if (user.isPresent()) {
            Boolean verifyPass = passwordEncoder.matches(oldPassword, user.get().getPassword());
            if (!verifyPass) {
                throw new OldPasswordNotMatchesException(GlobalKey.OLD_PASSWORD_NOT_MATCHES);
            } else {
                user.get().setPassword(passwordEncoder.encode(newPassword));
//                userObj.setPassDefault(false);
                userRepository.save(user.get());
            }
        } else {

        }
    }
}