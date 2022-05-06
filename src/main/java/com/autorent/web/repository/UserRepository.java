package com.autorent.web.repository;

import com.autorent.web.entity.User;
import com.autorent.web.entity.UserBusy;
import com.autorent.web.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {


    Optional<User> findByEmail(String username);

    Optional<User> findByToken(String token);

    List<User> findUsersByUserTypeAndUserBusy(UserType userType, UserBusy userBusy);
}
