# Gryffisio (GRYFFISIO)

Progetto finale di bootcamp (Java/Spring/REST). Backend Spring Boot per registrare
dati di sperimentazioni/interventi, con l'obiettivo di produrre un resoconto finale
a fine anno sulle attività svolte.

## Stack

- Java 26, Spring Boot **4.1.0** (attenzione: molte cose sono cambiate rispetto a
  Spring Boot 3.x, vedi sezione "Gotcha Spring Boot 4" sotto)
- Spring Security con JWT stateless (OAuth2 Resource Server, HS256, chiave da `app.jwt.secret`)
- Spring Data JPA + PostgreSQL (`ddl-auto: validate` — lo schema è gestito a mano,
  vedi `src/main/resources/GRYFFISIO_schema.sql`; c'è anche un `uniss_schema.sql`
  di cui non è chiaro se sia ancora utile o un residuo)
- springdoc-openapi (Swagger UI su `/swagger-ui.html`)
- Test: JUnit 5 + Mockito + `spring-security-test` + `spring-boot-webmvc-test`

Build/test: `./mvnw.cmd compile`, `./mvnw.cmd test` (Windows, wrapper `.cmd`).

## Architettura

Layering classico: `controllers` → `services` → `model/repositories` (Spring Data JPA),
con `model/entities`, `model/dto` (record) e `model/exceptions` (eccezioni di dominio
con `errorCode` + messaggio). `security/` contiene JWT + `UserDetailsService`.

## Modello di dominio

`Project` → `Registration` (entità centrale) collega `Domain`, `Session`, `Doctor`
opzionale, e relazioni M2M verso `Operator`, `Subject`, `Activity`. `Activity` ha
gerarchia parent/child. `ModificationRequest` è un workflow di richieste di modifica
su una `Registration` con stato `PENDING/APPROVED/REJECTED`, gestito da un admin
(`decide`). `AuditLog` traccia le azioni. `Operator` = utente applicativo (vedi sotto).

## Sicurezza — decisioni importanti prese in questa collaborazione

- **Tutti gli `Operator` sono utenti applicativi**: hanno sempre email + password
  hashata (bcrypt). Non esiste più un modo di creare un `Operator` senza password.
- **Solo un ADMIN può creare, modificare, eliminare operatori e resettare password
  altrui.** Endpoint in `OperatorController`, tutti con `@PreAuthorize("hasRole('ADMIN')")`:
  - `POST /api/operators` (crea, richiede `CreateUserRequest`: firstName/lastName/email/password/role)
  - `PUT /api/operators/{id}` (aggiorna nome/email/ruolo)
  - `PUT /api/operators/{id}/password` (reset password, `ResetPasswordRequest`)
  - `DELETE /api/operators/{id}`
  - I due `GET` restano pubblici (permitAll in `SecurityConfig`).
- **`POST /api/auth/users` è stato rimosso** (duplicava la creazione operatori con
  regole diverse — creava operator con role fisso ADMIN e senza validare unicità
  allo stesso modo). `AuthController` ora ha solo `login`/`logout`.
- Gli `Operator` creati **prima** di questa modifica potrebbero avere `passwordHash`
  null — non è stata fatta nessuna migrazione automatica dei dati esistenti.
- `SecurityConfig` ha alcuni commenti che dichiarano "solo admin" per endpoint che
  in realtà a livello di URL richiedono solo `authenticated()` (non un ruolo
  specifico). Il controllo per ruolo fine è delegato a `@PreAuthorize` sui singoli
  metodi controller (pattern già usato per `OperatorController`) — se si aggiungono
  nuovi endpoint riservati agli admin, seguire questo stesso pattern piuttosto che
  fidarsi dei commenti in `SecurityConfig`.

## `GlobalExceptionHandler` (`model/exceptions/GlobalExceptionHandler.java`)

`@RestControllerAdvice` aggiunto in questa collaborazione (prima non esisteva —
`NotFoundException`/`ConflictException`/`BadRequestException` tornavano tutte 500).
Mappa:
- `NotFoundException` → 404, `ConflictException` → 409, `BadRequestException` → 400
- `MethodArgumentNotValidException` (fallimenti `@Valid`) → 400
- `AccessDeniedException` (e la sottoclasse `AuthorizationDeniedException` lanciata
  da `@PreAuthorize` quando nega l'accesso) → 403
- fallback `Exception.class` → 500, loggato

**Gotcha da ricordare**: il fallback `Exception.class` è molto ampio — se in futuro
si introduce un nuovo tipo di eccezione che deve avere uno status HTTP specifico
(inclusi eventuali futuri tipi di eccezioni di Spring Security), va aggiunto un
`@ExceptionHandler` dedicato *prima* di scoprire per tentativi che finisce tutto a
500 (è già successo una volta con `AuthorizationDeniedException` scritto/scoperto
tramite `OperatorControllerAuthorizationTest`).

## Convenzioni REST decise

Filtri sulla stessa collection vanno espressi come **query parameter sull'endpoint
della collection**, non come segmenti di path separati che imitano una sotto-risorsa.
Esempio già applicato: `GET /api/modification-requests?status=PENDING` (rimosso il
vecchio `GET /api/modification-requests/pending`).

**Non ancora sistemato** (stesso identico pattern, individuato ma non ancora corretto):
- `GET /api/projects/name` / `GET /api/projects/code` → dovrebbero diventare
  `GET /api/projects?name=`/`?code=`
- `GET /api/domains/name` → `GET /api/domains?name=`
- `GET /api/subjects/by-project/{projectId}` → `GET /api/subjects?projectId=`
- `GET /api/doctors/search?lastName=` → il segmento `/search` è superfluo, basterebbe
  `GET /api/doctors?lastName=`
- `GET /api/audit-logs/by-entity?entityName=&entityId=` → stesso discorso, il
  segmento `/by-entity` è superfluo

Non considerati un problema (annidamento legittimo per relazioni gerarchiche):
`GET /api/activities/roots`, `GET /api/activities/{parentId}/children`.

## Gotcha Spring Boot 4 (emersi scrivendo i test)

- `@WebMvcTest` si è spostato in `org.springframework.boot.webmvc.test.autoconfigure`
  (prima `org.springframework.boot.test.autoconfigure.web.servlet`), in un artifact
  **separato** `spring-boot-webmvc-test` che NON è più incluso transitivamente da
  `spring-boot-starter-test`. Va aggiunto esplicitamente in `pom.xml`.
- `@MockBean` non esiste più: usare `@MockitoBean` da
  `org.springframework.test.context.bean.override.mockito` (già in `spring-test`,
  nessuna dipendenza aggiuntiva serve per questo).
- Per `@WithMockUser`/integrazione MockMvc+Security serve `spring-security-test`
  esplicito in `pom.xml` (non incluso di default).

## Testing — convenzioni stabilite

- **Unit test di puro service** (logica applicativa, no Spring context): Mockito
  con `@ExtendWith(MockitoExtension.class)`, `@Mock` sui repository/collaboratori,
  costruzione manuale del service nel `@BeforeEach`. Esempio:
  `src/test/java/org/generation/italy/services/ModificationRequestServiceTest.java`.
- **Test di autorizzazione (`@PreAuthorize`)**: NON testabili con un semplice unit
  test Mockito sul service, perché l'enforcement del ruolo vive nel proxy AOP di
  Spring Security attorno al controller, non nella logica del service. Serve
  `@WebMvcTest` + `@WithMockUser(roles = "...")` + un `@TestConfiguration` locale
  con `@EnableMethodSecurity` (non serve importare la `SecurityConfig` reale/i bean
  JWT). Esempio:
  `src/test/java/org/generation/italy/controllers/OperatorControllerAuthorizationTest.java`.
- `src/test/java/org/generation/italy/dashboard/DashboardServiceTest.java` è
  **interamente commentato** e fa riferimento a classi che non esistono più nel
  progetto (residuo di un'iterazione precedente) — non viene eseguito. Prima dei
  test aggiunti in questa collaborazione, il progetto non aveva di fatto nessun
  test attivo.

## Cose note ma non ancora sistemate (backlog)

- Pulizia URL REST elencata sopra.
- `update`/`delete` di risorse diverse da `Operator` (es. `Registration`,
  `ModificationRequest`, cataloghi vari) potrebbero avere lo stesso gap "solo
  autenticato, non specificamente admin" che avevamo su `Operator` — non ancora
  auditati uno per uno.
- Nessun README nel progetto.
- `application.yaml` contiene commenti/appunti di studio personali mescolati alla
  configurazione — da ripulire prima di una eventuale consegna/presentazione.
