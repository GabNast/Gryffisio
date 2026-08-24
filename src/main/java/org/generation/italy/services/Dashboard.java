package org.generation.italy.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
* Numero totale di interventi/valutazioni registrati.
Numero di interventi/valutazioni del progetto selezionato.
Numero di interventi/valutazioni per operatore
Totale delle ore svolte per tipologia di intervento/valutazione.
Totale delle ore svolte per operatore.
* */
@Service
public class Dashboard {
    private int totalInterventions;
    private int totalEvaluations;

    @Transactional(readOnly = true)
    public String getDashboardData() {
        int totalInterventions = 0; // Replace with actual logic to retrieve total interventions
        int totalEvaluations = 0; // Replace with actual logic to retrieve total evaluations
        // Implement the logic to retrieve and return the dashboard data
        return "Dashboard data";
    }

}
