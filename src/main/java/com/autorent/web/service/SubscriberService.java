package com.autorent.web.service;

import com.autorent.web.entity.Subscriber;
import com.autorent.web.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;

    public Subscriber save(Subscriber subscriber){
        return subscriberRepository.save(subscriber);
    }
    public Optional<Subscriber> getSubscriberByEmail(String email){
        return subscriberRepository.getSubscriberByEmail(email);
    }
}
