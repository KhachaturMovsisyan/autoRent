package com.example.autorent.service;

import com.example.autorent.entity.User;
import com.example.autorent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${image.upload.path}")
    private String imagePath;


    public User save(User user, MultipartFile file) throws IOException {
        setImageToUser(file, user);
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        user.setUserType(user.getUserType());
        return userRepository.save(user);
    }

    private void setImageToUser(MultipartFile file, User user) throws IOException {
        if (!file.getOriginalFilename().isEmpty()){
            String profilePic = System.nanoTime() + "_" + file.getOriginalFilename();
            File image = new File(imagePath, profilePic);
            file.transferTo(image);
            user.setPicUrl(profilePic);
        }

    }


}
