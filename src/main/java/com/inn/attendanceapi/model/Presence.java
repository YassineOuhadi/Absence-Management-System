package com.inn.attendanceapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inn.attendanceapi.model.id.PresenceId;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "presence")
@IdClass(PresenceId.class)
@NamedQueries({
        @NamedQuery(
                name = "Presence.findByUserAndSeance",
                query = "SELECT p FROM Presence p WHERE p.user = :user AND p.seance = :seance"
        ),
        @NamedQuery(
                name = "Presence.findBySeance",
                query = "SELECT p FROM Presence p WHERE p.seance = :seance"
        ),
        @NamedQuery(
                name = "Presence.findUnvalidatedSeances",
                query = "SELECT DISTINCT p.seance FROM Presence p WHERE p.isValidate = false"
        ),
        @NamedQuery(
                name = "Presence.existsByValidate",
                query = "SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM Presence p WHERE p.isValidate = :isValidate"
        )
})
public class Presence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seance_fk", nullable = false)
    private Seance seance;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_fk", nullable = false)
    private User user;

    @Column(name = "entry_time", columnDefinition = "TIME DEFAULT '00:00:00'")
    @JsonFormat(pattern = "HH:mm:ss")
    private Time Entrytime;

    @Column(name = "is_validate", columnDefinition = "boolean default false")
    private boolean isValidate;
}
