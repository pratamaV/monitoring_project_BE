package com.askrindo.entity.sequence;

import com.askrindo.generator.PrefixedSequenceIdGenerator;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Table(name = "mst_sequence_idproject")
public class SequenceIdProject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_idproject")
    @GenericGenerator(
            name = "seq_idproject",
            strategy = "com.askrindo.generator.PrefixedSequenceIdGenerator",
            parameters = {@Parameter(name = PrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = PrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%03d")})

    private String idGeneratorProject;

    public SequenceIdProject() {
    }

    public String getIdGeneratorProject() {
        return idGeneratorProject;
    }

    public void setIdGeneratorProject(String idGeneratorClaim) {
        this.idGeneratorProject = idGeneratorClaim;
    }
}
