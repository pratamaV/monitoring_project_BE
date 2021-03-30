package com.askrindo.entity.sequence;

import com.askrindo.generator.PrefixedSequenceIdGenerator;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Table(name = "mst_sequence_idfile")
public class SequenceIdFile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_idfile")
    @GenericGenerator(
            name = "seq_idfile",
            strategy = "com.askrindo.generator.PrefixedSequenceIdGenerator",
            parameters = {@Parameter(name = PrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1")})

    private String idGeneratorFile;

    public SequenceIdFile() {
    }

    public String getIdGeneratorFile() {
        return idGeneratorFile;
    }

    public void setIdGeneratorFile(String idGeneratorFile) {
        this.idGeneratorFile = idGeneratorFile;
    }
}
