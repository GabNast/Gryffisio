CREATE TABLE public.evaluation_domain
(
    id   bigint                 NOT NULL,
    type character varying(255) NOT NULL
);

CREATE TABLE public.evaluations
(
    id        bigint                NOT NULL,
    name      character varying(50) NOT NULL,
    type_id   bigint                NOT NULL,
    pre       bigint,
    post      bigint,
    follow_up bigint
);

CREATE TABLE public.intervention
(
    id              bigint                 NOT NULL,
    name            character varying(127) NOT NULL,
    intervention_id bigint                 NOT NULL
);

CREATE TABLE public.intervention_domain
(
    id   bigint            NOT NULL,
    name character varying NOT NULL
);

CREATE TABLE public.patient
(
    id         bigint                NOT NULL,
    type       bigint                NOT NULL,
    subject_id character varying(10) NOT NULL
);

CREATE TABLE public.project
(
    id          bigint                 NOT NULL,
    name        character varying(255) NOT NULL,
    description text
);

CREATE TABLE public.referring_doctor
(
    id      bigint                NOT NULL,
    name    character varying(20) NOT NULL,
    surname character varying(20) NOT NULL,
    gender  "char"
);

CREATE TABLE public.researcher
(
    id         bigint                 NOT NULL,
    email      character varying(256) NOT NULL,
    password   character varying(150) NOT NULL,
    name       character varying(20)  NOT NULL,
    surname    character varying(30)  NOT NULL,
    is_student boolean DEFAULT false  NOT NULL
);

CREATE TABLE public.subject_domain
(
    id   bigint                 NOT NULL,
    type character varying(255) NOT NULL,
    CONSTRAINT check_type CHECK (((type)::text = ANY ((ARRAY['Sano - Giovane':: character varying, 'Sano - Anziano':: character varying, 'Atleta':: character varying, 'SM':: character varying, 'AD':: character varying, 'MC':: character varying, 'PD':: character varying, 'Obeso':: character varying, 'DCA':: character varying, 'Malnutrizione/Sottopeso':: character varying, 'Malattie Autoimmuni':: character varying])::text[])
) )
);

CREATE TABLE public.test
(
    id   bigint                 NOT NULL,
    name character varying(128) NOT NULL
);

CREATE TABLE public.test_domain
(
    id   bigint                 NOT NULL,
    name character varying(128) NOT NULL
);

CREATE TABLE public.test_to_domain
(
    test_id        bigint NOT NULL,
    test_domain_id bigint NOT NULL
);

CREATE TABLE public.user_role
(
    user_id bigint                NOT NULL,
    role    character varying(20) NOT NULL
);

-- Primary keys

ALTER TABLE ONLY public.evaluation_domain
    ADD CONSTRAINT evaluation_type_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.evaluations
    ADD CONSTRAINT evaluations_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.intervention
    ADD CONSTRAINT intervention_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.intervention_domain
    ADD CONSTRAINT intervention_type_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.patient
    ADD CONSTRAINT paziente_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.project
    ADD CONSTRAINT project_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.referring_doctor
    ADD CONSTRAINT referring_doctor_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.researcher
    ADD CONSTRAINT ricercatore_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.subject_domain
    ADD CONSTRAINT subject_type_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.test_domain
    ADD CONSTRAINT test_category_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.test
    ADD CONSTRAINT test_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.test_to_domain
    ADD CONSTRAINT test_to_domain_pkey PRIMARY KEY (test_id, test_domain_id);

ALTER TABLE ONLY public.subject_domain
    ADD CONSTRAINT type_unique UNIQUE (type);

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role);

-- Foreign keys

ALTER TABLE ONLY public.evaluations
    ADD CONSTRAINT fk_evaluation_type FOREIGN KEY (type_id) REFERENCES public.evaluation_domain(id);

ALTER TABLE ONLY public.evaluations
    ADD CONSTRAINT fk_follow_up FOREIGN KEY (follow_up) REFERENCES public.intervention(id) NOT VALID;

ALTER TABLE ONLY public.intervention
    ADD CONSTRAINT fk_intervention_id FOREIGN KEY (intervention_id) REFERENCES public.intervention_domain(id);

ALTER TABLE ONLY public.evaluations
    ADD CONSTRAINT fk_post FOREIGN KEY (post) REFERENCES public.intervention(id) NOT VALID;

ALTER TABLE ONLY public.evaluations
    ADD CONSTRAINT fk_pre FOREIGN KEY (pre) REFERENCES public.intervention(id) NOT VALID;

ALTER TABLE ONLY public.test_to_domain
    ADD CONSTRAINT fk_test_domain_id FOREIGN KEY (test_domain_id) REFERENCES public.test_domain(id);

ALTER TABLE ONLY public.test_to_domain
    ADD CONSTRAINT fk_test_id FOREIGN KEY (test_id) REFERENCES public.test(id);

ALTER TABLE ONLY public.patient
    ADD CONSTRAINT fk_type FOREIGN KEY (type) REFERENCES public.subject_domain(id) NOT VALID;

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.researcher(id);