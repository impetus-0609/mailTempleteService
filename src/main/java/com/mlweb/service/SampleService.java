package com.mlweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mlweb.model.SampleModel;
import com.mlweb.repository.SampleRepository;

@Service
@Transactional
public class SampleService {

  @Autowired
  SampleRepository sampleRepository;

  public List<SampleModel> getSampleModel() {
    return sampleRepository.findAll();
  }

}
