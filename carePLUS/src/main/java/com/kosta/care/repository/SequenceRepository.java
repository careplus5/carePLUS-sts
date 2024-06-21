package com.kosta.care.repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SequenceRepository {

    @Autowired
    private EntityManager entityManager;

    public Long getNextSequence(String sequenceName) {
        StoredProcedureQuery query = entityManager
            .createStoredProcedureQuery("get_next_sequence")
            .registerStoredProcedureParameter("in_seq_name", String.class, ParameterMode.IN)
            .registerStoredProcedureParameter("next_id", Long.class, ParameterMode.OUT)
            .setParameter("in_seq_name", sequenceName);

        query.execute();
        return (Long) query.getOutputParameterValue("next_id");
    }
}
