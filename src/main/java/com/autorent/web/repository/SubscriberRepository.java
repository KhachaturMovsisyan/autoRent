package com.autorent.web.repository;

import com.autorent.web.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriberRepository extends JpaRepository<Subscriber,Integer> {

    Optional<Subscriber> getSubscriberByEmail(String email);
}
