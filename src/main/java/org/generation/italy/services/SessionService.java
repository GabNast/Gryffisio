package org.generation.italy.services;

import org.generation.italy.model.dto.SessionDto;
import org.generation.italy.model.dto.SessionRequest;
import org.generation.italy.model.entities.Session;
import org.generation.italy.model.entities.SessionType;
import org.generation.italy.model.exceptions.ConflictException;
import org.generation.italy.model.exceptions.NotFoundException;
import org.generation.italy.model.repositories.SessionRepository;
import org.generation.italy.model.repositories.SessionTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final SessionTypeRepository sessionTypeRepository;

    public SessionService(SessionRepository sessionRepository, SessionTypeRepository sessionTypeRepository) {
        this.sessionRepository = sessionRepository;
        this.sessionTypeRepository = sessionTypeRepository;
    }

    private SessionDto sessionDto(Session session){
        return new SessionDto(
                session.getId(),
                session.getEvaluation(),
                session.getSessionType().getId(),
                session.getSessionType().getName());
    }

    @Transactional(readOnly = true)
    public List<SessionDto> findAll(){
        return sessionRepository.findAll().stream().map(this::sessionDto).toList();
    }

    @Transactional(readOnly = true)
    public SessionDto findById(Integer id){
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Session_id_not_found", "Session id not found: " + id));
        return sessionDto(session);
    }

    @Transactional(readOnly = true)
    public SessionDto findByEvaluationName(String evaluation){
        Session session = sessionRepository.findByEvaluationIgnoreCase(evaluation)
                .orElseThrow(() -> new NotFoundException("Session_evaluation_not_found", "Session evaluation not found: " + evaluation));
        return sessionDto(session);
    }

    @Transactional
    public SessionDto createSession(SessionRequest request) {
        if (sessionRepository.existsByEvaluationIgnoreCase(request.evaluation())) {
            throw new ConflictException("Session_already_exists", "Session already exists: " + request.evaluation());
        }

        SessionType sessionType = sessionTypeRepository.findById(request.sessionTypeId())
                .orElseThrow(() -> new NotFoundException("Session_type_not_found", "Session type not found: " + request.sessionTypeId()));

        Session session = new Session();
        session.setEvaluation(request.evaluation());
        session.setSessionType(sessionType);
        Session saved = sessionRepository.save(session);
        return sessionDto(saved);
    }

    @Transactional
    public SessionDto updateSession(Integer id, SessionRequest request) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Session_not_found", "Session not found: " + id));

        if (sessionRepository.existsByEvaluationIgnoreCaseAndIdNot(request.evaluation(), id)) {
            throw new ConflictException("Session_already_exists", "Session already exists: " + request.evaluation());
        }

        SessionType sessionType = sessionTypeRepository.findById(request.sessionTypeId())
                .orElseThrow(() -> new NotFoundException("Session_type_not_found", "Session type not found: " + request.sessionTypeId()));

        session.setEvaluation(request.evaluation());
        session.setSessionType(sessionType);
        return sessionDto(sessionRepository.save(session));
    }

    @Transactional
    public void deleteSession(Integer id) {
        if (!sessionRepository.existsById(id)) {
            throw new NotFoundException("Session_not_found", "Session not found: " + id);
        }
        sessionRepository.deleteById(id);
    }
}
