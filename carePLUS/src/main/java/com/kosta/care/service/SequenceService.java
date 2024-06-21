package com.kosta.care.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kosta.care.repository.SequenceRepository;

@Service
public class SequenceService {

    @Autowired
    private SequenceRepository sequenceRepository;

    @Transactional
    public Long getNextSequence(String sequenceName) {
        return sequenceRepository.getNextSequence(sequenceName);
    }

    @Transactional
    public Long getDocNextSeqValue() {
        return getNextSequence("sq_doc");
    }

    @Transactional
    public Long getNurNextSeqValue() {
        return getNextSequence("sq_nur");
    }

    @Transactional
    public Long getAdmNextSeqValue() {
        return getNextSequence("sq_adm");
    }

    @Transactional
    public Long getMetNextSeqValue() {
        return getNextSequence("sq_met");
    }
}
