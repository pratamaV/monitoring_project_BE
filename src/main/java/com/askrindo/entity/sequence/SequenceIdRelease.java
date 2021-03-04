package com.askrindo.entity.sequence;

import com.askrindo.generator.PrefixedSequenceIdGenerator;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Table(name = "mst_sequence_idrelease")
public class SequenceIdRelease {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_idrelease")
    @GenericGenerator(
            name = "seq_idrelease",
            strategy = "com.askrindo.generator.PrefixedSequenceIdGenerator",
            parameters = {@Parameter(name = PrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = PrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d")})

    private String idGeneratorRelease;

    public SequenceIdRelease() {
    }

    public String getIdGeneratorRelease() {
        return idGeneratorRelease;
    }

    public void setIdGeneratorRelease(String idGeneratorRelease) {
        this.idGeneratorRelease = idGeneratorRelease;
    }
}
