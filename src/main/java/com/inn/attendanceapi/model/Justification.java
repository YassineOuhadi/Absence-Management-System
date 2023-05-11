package com.inn.attendanceapi.model;

import com.inn.attendanceapi.model.id.JustificationId;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "justification")
@IdClass(JustificationId.class)
@NamedQueries({
        @NamedQuery(
                name = "Justification.findByParticipantAndSeance",
                query = "SELECT j FROM Justification j WHERE j.user = :participant AND j.seance = :seance"
        )
})
public class Justification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seance_fk", nullable = false)
    private Seance seance;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_fk", nullable = false)
    private User user;

    @Column(name = "content", length = 5000)
    private String content;

    @Lob
    @Column(name = "support")
    private Blob support;

    @Column(name = "is_accepted", columnDefinition = "boolean default false")
    private boolean isAccepted;
}


