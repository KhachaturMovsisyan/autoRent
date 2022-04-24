package com.autorent.web.service;

import com.autorent.web.entity.Pictures;
import com.autorent.web.repository.PicturesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PicturesService {

    public final PicturesRepository picturesRepository;
    public void savePics(Pictures pictures) {
        picturesRepository.save(pictures);

    }

    public List<Pictures> findAll() {
        return picturesRepository.findAll();
    }
}
