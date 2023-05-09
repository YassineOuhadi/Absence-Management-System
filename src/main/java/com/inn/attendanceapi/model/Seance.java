package com.inn.attendanceapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "seance")
@NamedQueries({
        @NamedQuery(
                name = "Seance.findAllSeancesBySalleAndDate",
                query = "SELECT s FROM Seance s WHERE s.salle = :salle AND s.date = :date"
        )
})
public class Seance implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum SeanceType {
        COURSE, TD, TP, EXAMEN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date", columnDefinition = "TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate date;

    @Column(name = "time")
    @JsonFormat(pattern = "HH:mm:ss")
    @NotNull
    private Time time;

    @Column(name = "duration")
    @JsonFormat(pattern = "HH:mm:ss")
    @NotNull
    private Time duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private SeanceType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_fk", nullable = false)
    private Semester semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "element_fk", nullable = false)
    private Element element;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salle_fk", nullable = false)
    private Salle salle;

    public boolean conflictsWith(LocalTime otherStartTime, LocalTime otherEndTime) {
        LocalTime seanceStartTime = time.toLocalTime();
        LocalTime seanceEndTime = seanceStartTime.plusMinutes(duration.getTime() / 60000);
        return (seanceStartTime.isAfter(otherStartTime) && seanceStartTime.isBefore(otherEndTime))
                || (seanceEndTime.isAfter(otherStartTime) && seanceEndTime.isBefore(otherEndTime))
                || (seanceStartTime.isBefore(otherStartTime) && seanceEndTime.isAfter(otherEndTime))
                || seanceStartTime.equals(otherStartTime) || seanceEndTime.equals(otherEndTime);
    }
}
