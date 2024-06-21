package com.kosta.care.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sequence_table")
public class SequenceEntity {

    @Id
    private String seqName;
    private Long seqValue;

    // Getters and setters
    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName;
    }

    public Long getSeqValue() {
        return seqValue;
    }

    public void setSeqValue(Long seqValue) {
        this.seqValue = seqValue;
    }
}
