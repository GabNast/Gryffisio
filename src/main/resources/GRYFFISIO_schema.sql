-- =============================================================================
-- 1. CATALOGHI E ANAGRAFICHE DI BASE
-- =============================================================================

-- Tabella Progetti
CREATE TABLE projects (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL UNIQUE,
                          code VARCHAR(20) NOT NULL UNIQUE
);

-- Tabella Domini (es. Cardiologia, Nutrizione, ecc.)
CREATE TABLE domains (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL UNIQUE
);

-- Tabella Medici/Riferimenti Clinici
CREATE TABLE doctors (
                         id SERIAL PRIMARY KEY,
                         first_name VARCHAR(50) NOT NULL,
                         last_name VARCHAR(50) NOT NULL
);

-- Tabella Sessioni (Catalogo piatto)
CREATE TABLE sessions (
                          id SERIAL PRIMARY KEY,
                          session VARCHAR(50) NOT NULL UNIQUE
);

-- Tabella Operatori (Utenti del sistema, admin incluso tramite role)
CREATE TABLE operators (
                           id SERIAL PRIMARY KEY,
                           first_name VARCHAR(50) NOT NULL,
                           last_name VARCHAR(50) NOT NULL,
                           email VARCHAR(100) NOT NULL UNIQUE,
                           role VARCHAR(20) NOT NULL CHECK (role IN ('OPERATOR', 'ADMIN'))
);

-- Tabella Tipologie di Soggetto (elenco fisso, richiesto dalla specifica come campo obbligatorio)
CREATE TABLE subject_types (
                               id SERIAL PRIMARY KEY,
                               type VARCHAR(100) NOT NULL UNIQUE
);

-- Tabella Soggetti / Pazienti (Codice alfanumerico univoco per progetto)
CREATE TABLE subjects (
                          id BIGSERIAL PRIMARY KEY,
                          project_id INTEGER NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
                          code VARCHAR(50) NOT NULL,
                          subject_type_id INTEGER NOT NULL REFERENCES subject_types(id),
                          CONSTRAINT uk_subject_project_code UNIQUE (project_id, code)
);

-- Tabella Albero Attività / Test (Struttura gerarchica genitore-figlio)
CREATE TABLE activities (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            parent_id INTEGER REFERENCES activities(id) ON DELETE CASCADE
);

-- Configurazione Attività visibili per Dominio (AC-06)
CREATE TABLE domain_activities (
                                   domain_id INTEGER NOT NULL REFERENCES domains(id) ON DELETE CASCADE,
                                   activity_id INTEGER NOT NULL REFERENCES activities(id) ON DELETE CASCADE,
                                   PRIMARY KEY (domain_id, activity_id)
);


-- =============================================================================
-- 2. CORE: RENDICONTAZIONI E TABELLE PIVOT
-- =============================================================================

-- Tabella Principale Registrazioni
CREATE TABLE registrations (
                               id BIGSERIAL PRIMARY KEY,
                               project_id INTEGER NOT NULL REFERENCES projects(id),
                               domain_id INTEGER NOT NULL REFERENCES domains(id),
                               session_id INTEGER NOT NULL REFERENCES sessions(id),
                               doctor_id INTEGER REFERENCES doctors(id), -- facoltativo per specifica (solo Nutrizione Umana lo usa)
                               activity_date DATE NOT NULL,
                               duration_minutes INTEGER NOT NULL CHECK (duration_minutes > 0),
                               created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Pivot Registrazione <-> Operatori
CREATE TABLE registration_operators (
                                        registration_id BIGINT NOT NULL REFERENCES registrations(id) ON DELETE CASCADE,
                                        operator_id INTEGER NOT NULL REFERENCES operators(id) ON DELETE CASCADE,
                                        PRIMARY KEY (registration_id, operator_id)
);

-- Pivot Registrazione <-> Soggetti
CREATE TABLE registration_subjects (
                                       registration_id BIGINT NOT NULL REFERENCES registrations(id) ON DELETE CASCADE,
                                       subject_id BIGINT NOT NULL REFERENCES subjects(id) ON DELETE CASCADE,
                                       PRIMARY KEY (registration_id, subject_id)
);

-- Pivot Registrazione <-> Attività/Test
CREATE TABLE registration_activities (
                                         registration_id BIGINT NOT NULL REFERENCES registrations(id) ON DELETE CASCADE,
                                         activity_id INTEGER NOT NULL REFERENCES activities(id) ON DELETE CASCADE,
                                         PRIMARY KEY (registration_id, activity_id)
);


-- =============================================================================
-- 3. MODULI ADMIN (RICHIESTE DI MODIFICA E AUDIT LOG)
-- =============================================================================

-- Tabella Richieste di Modifica
CREATE TABLE modification_requests (
                                       id BIGSERIAL PRIMARY KEY,
                                       registration_id BIGINT NOT NULL REFERENCES registrations(id) ON DELETE CASCADE,
                                       operator_id INTEGER NOT NULL REFERENCES operators(id),

    -- Nuovi valori proposti dall'operatore (NULL se non modificati)
                                       new_activity_date DATE,
                                       new_duration_minutes INTEGER,
                                       new_session_id INTEGER REFERENCES sessions(id),
                                       new_doctor_id INTEGER REFERENCES doctors(id),

                                       reason TEXT NOT NULL,
                                       status VARCHAR(20) NOT NULL DEFAULT 'PENDING'
                                           CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED')),

                                       submitted_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                       handled_by_admin_id INTEGER REFERENCES operators(id),
                                       handled_at TIMESTAMP,
                                       rejection_reason TEXT
);

-- Tabella Audit Log (Tracciabilità)
CREATE TABLE audit_logs (
                            id BIGSERIAL PRIMARY KEY,
                            operator_id INTEGER REFERENCES operators(id) ON DELETE SET NULL,
                            action VARCHAR(50) NOT NULL,
                            entity_name VARCHAR(50) NOT NULL,
                            entity_id BIGINT NOT NULL,
                            description TEXT,
                            created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);