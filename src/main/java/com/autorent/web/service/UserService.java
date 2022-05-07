package com.autorent.web.service;

import com.autorent.web.entity.User;
import com.autorent.web.entity.UserBusy;
import com.autorent.web.entity.UserType;
import com.autorent.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${image.upload.path}")
    private String imagePath;


    public User userSave(User user, MultipartFile file) throws IOException {
        setTokenAndTimeWithEncode(user, file);
        user.setUserType(UserType.USER);
        return userRepository.save(user);
    }

    public void setBusy(User user){
        user.setBusy(UserBusy.BUSY);
    }

    public void setFree(User user){
        user.setBusy(UserBusy.FREE);
    }


    public List<User> drivers(){
        return userRepository.findUsersByUserTypeAndBusyIs(UserType.DRIVER,UserBusy.FREE);
    }

   


    public User driverSave(User user, MultipartFile file) throws IOException {
        setTokenAndTimeWithEncode(user, file);
        user.setUserType(UserType.DRIVER);
        return userRepository.save(user);
    }

    public User dealerSave(User user, MultipartFile file) throws IOException {
        setTokenAndTimeWithEncode(user, file);
        user.setUserType(UserType.DEALER);
        return userRepository.save(user);
    }
    public void activateUser(User userFromDb) {
        userFromDb.setActive(true);
        userFromDb.setToken(null);
        userFromDb.setTokenCreatedDate(null);
        userRepository.save(userFromDb);
    }

    

    public Optional<User> getUserByUserName(String email) {
        return userRepository.findByEmail(email);
    }


    public Optional<User> findByToken(String token) {
        return userRepository.findByToken(token);
    }




    private void setTokenAndTimeWithEncode(User user, MultipartFile file) throws IOException {
        user.setActive(false);
        user.setToken(UUID.randomUUID().toString());
        user.setTokenCreatedDate(LocalDateTime.now());

        setImageToUser(file, user);
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
    }
    private void setImageToUser(MultipartFile file, User user) throws IOException {
        if (!file.getOriginalFilename().isEmpty()) {
            String profilePic = System.nanoTime() + "_" + file.getOriginalFilename();
            File image = new File(imagePath, profilePic);
            file.transferTo(image);
            user.setPicUrl(profilePic);
        }

    }


    public User findById(int id) {
        return userRepository.findById(id).get();
    }
}
