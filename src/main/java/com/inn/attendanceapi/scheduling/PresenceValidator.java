package com.inn.attendanceapi.scheduling;

import com.inn.attendanceapi.dao.PresenceDao;
import com.inn.attendanceapi.dao.SeanceParticipantsDao;
import com.inn.attendanceapi.model.Presence;
import com.inn.attendanceapi.model.Seance;
import com.inn.attendanceapi.model.SeanceParticipants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class PresenceValidator {

    @Autowired
    private SeanceParticipantsDao seanceParticipantsDao;

    @Autowired
    private PresenceDao presenceDao;

    @Transactional
    //@Scheduled(fixedDelay = 1)
    @Scheduled(cron = "0 0 11,13,16,18 * * MON-SAT")
    public void checkUnvalidatedPresences() {
        if (presenceDao.existsByValidate(false)) {
            List<Seance> unvalidatedSeances = presenceDao.findUnvalidatedSeances();
            for (Seance seance : unvalidatedSeances) {
                List<SeanceParticipants> participants = seanceParticipantsDao.findBySeanceId(seance.getId());
                for (SeanceParticipants participant : participants) {
                    autoValidatePresence(participant);
                }
            }
        }
    }

    public void autoValidatePresence(SeanceParticipants seanceParticipant) {
        Presence presence = presenceDao.findByUserAndSeance(seanceParticipant.getUser(), seanceParticipant.getSeance());
        if (presence != null && !presence.isValidate() && !seanceParticipant.isPresence()) {
                LocalDate seanceDate = seanceParticipant.getSeance().getDate();
                Time seanceTime = seanceParticipant.getSeance().getTime();
                Time seanceDuration = seanceParticipant.getSeance().getDuration();
                LocalDateTime seanceDateTime = LocalDateTime.of(seanceDate, seanceTime.toLocalTime());
                LocalDateTime endDateTime = seanceDateTime.plusHours(seanceDuration.getHours()).plusMinutes(seanceDuration.getMinutes());
                if (LocalDateTime.now().isAfter(endDateTime)) {
                    seanceParticipant.setPresence(true);
                    presence.setValidate(true);
                    presenceDao.save(presence);
                    seanceParticipantsDao.save(seanceParticipant);
                }
        }
    }
}

