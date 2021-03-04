package com.askrindo.entity.sequence;

import com.askrindo.generator.PrefixedSequenceIdGenerator;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Table(name = "mst_sequence_idtask")
public class SequenceIdTask {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_idtask")
    @GenericGenerator(
            name = "seq_idtask",
            strategy = "com.askrindo.generator.PrefixedSequenceIdGenerator",
            parameters = {@Parameter(name = PrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = PrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d")})

    private String idGeneratorTask;

    public SequenceIdTask() {
    }

    public String getIdGeneratorTask() {
        return idGeneratorTask;
    }

    public void setIdGeneratorTask(String idGeneratorTask) {
        this.idGeneratorTask = idGeneratorTask;
    }
}
