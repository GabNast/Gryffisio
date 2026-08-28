package org.generation.italy.services;

import org.apache.commons.csv.CSVFormat;
import org.generation.italy.model.entities.Activity;
import org.generation.italy.model.entities.Registration;
import org.generation.italy.model.entities.Subject;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationExportService {

    private static final CSVFormat CSV_FORMAT = CSVFormat.DEFAULT.builder()
            .setHeader(
                    "id",
                    "dominio",
                    "progetto",
                    "id_soggetto",
                    "tipologia_soggetto",
                    "sessione",
                    "test",
                    "durata_minuti",
                    "data",
                    "medico",
                    "operatori",
                    "data_creazione"
            )
            .build();

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private List<String> toRow(Registration reg) {
        return List.of(
                // id
                String.valueOf(reg.getId()),

                // dominio (obbligatorio)
                reg.getDomain().getName(),

                // progetto (obbligatorio)
                reg.getProject().getName(),

                // id_soggetto (lista)
                reg.getSubjects().stream()
                        .map(Subject::getCode)
                        .collect(Collectors.joining("; ")),

                // tipologia_soggetto (lista)
                reg.getSubjects().stream()
                        .map(s -> s.getSubjectType().getType())
                        .collect(Collectors.joining("; ")),

                // sessione
                reg.getSession().getSession(),

                // test/attività (lista)
                reg.getActivities().stream()
                        .map(Activity::getName)
                        .collect(Collectors.joining("; ")),

                // durata in minuti
                String.valueOf(reg.getDurationMinutes()),

                // data attività (nullable)
                reg.getActivityDate() != null
                        ? reg.getActivityDate().format(DATE_FORMAT)
                        : "",

                // medico (nullable)
                reg.getDoctor() != null
                        ? reg.getDoctor().getFirstName() + " " + reg.getDoctor().getLastName()
                        : "",

                // operatori (lista "Nome Cognome")
                reg.getOperators().stream()
                        .map(op -> op.getFirstName() + " " + op.getLastName())
                        .collect(Collectors.joining("; ")),

                // data/ora creazione (obbligatoria)
                reg.getCreatedAt().format(DATE_TIME_FORMAT)
        );
    }

}