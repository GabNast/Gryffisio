package org.generation.italy.services;

import org.generation.italy.model.dto.SessionDto;
import org.generation.italy.model.dto.SessionRequest;
import org.generation.italy.model.entities.Session;
import org.generation.italy.model.exceptions.ConflictException;
import org.generation.italy.model.exceptions.NotFoundException;
import org.generation.italy.model.repositories.SessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    private SessionDto toDto(Session session) {
        return new SessionDto(session.getId(), session.getSession());
    }

    @Transactional(readOnly = true)
    public List<SessionDto> findAll() {
        return sessionRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public SessionDto findById(Integer id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Session_not_found", "Session not found: " + id));
        return toDto(session);
    }

    @Transactional
    public SessionDto createSession(SessionRequest request) {
        if (sessionRepository.existsBySessionIgnoreCase(request.session())) {
            throw new ConflictException("Session_already_exists", "Session already exists: " + request.session());
        }
        Session session = new Session();
        session.setSession(request.session());
        Session saved = sessionRepository.save(session);
        return toDto(saved);
    }

    @Transactional
    public SessionDto updateSession(Integer id, SessionRequest request) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Session_not_found", "Session not found: " + id));

        if (sessionRepository.existsBySessionIgnoreCaseAndIdNot(request.session(), id)) {
            throw new ConflictException("Session_already_exists", "Session already exists: " + request.session());
        }

        session.setSession(request.session());
        return toDto(sessionRepository.save(session));
    }

    @Transactional
    public void deleteSession(Integer id) {
        if (!sessionRepository.existsById(id)) {
            throw new NotFoundException("Session_not_found", "Session not found: " + id);
        }
        sessionRepository.deleteById(id);
    }
}