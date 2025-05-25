

ANGULAR:
to configure different configuration per environment in angular its required to edit 'angular.json', create files for each conf file for each env inside /environments.

https://dev.to/this-is-angular/building-and-serving-angular-applications-across-environments-with-environmentts-angular-15-6dk
https://www.telerik.com/blogs/angular-basics-using-environmental-variables-organize-build-configurations


call spring app (webflux) from angular app => dev/prod:

- add this `@CrossOrigin(origins = "http://localhost:4200")` controller.
- add config 
```java
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WebConfig implements WebFluxConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("*");
    }
}
```

a

**Spring Security:**
http call => security filters (intercept requests) => extract token + validation (auth) => check role(auth) => access resource or 401/403.
concepts:
Authentication: check identity, 
Authorization: check permissions/roles, 
SecurityContext: stock users' info connected, 
UserDetails: user details, 
PasswordEncoder: hashed password, 
SecurityFilterChain: define security rules.

spring mvc & webflux uses same steps but processed differently due nature of each app(blocking vs non-blocking)



**Spring Cloud:**
une librairy java with spring framework to simplify distributed app developpement.

concepts:
service discovery, (pour trouver les services dynamiquement) Eureka, Consul, Zookeeper
distributed tracing (suivi des rqt)
api gateway, (point d'entrer unique for clients)  Spring Cloud Gateway
circuit breaker (Gère les pannes et évite les cascades d’erreurs). Resilience4j
load balanceing, (Distribue les requêtes entre instances de service.) spring Cloud LoadBalancer 
config centralisé,


**Spring Webflux + microservices:**

microservices découplé + bdd séparé pour la séparation des données
communocation inter-service (webclient async, broker: kafka).
bdd reactive (non bloquant) 
observability (suivre et tracé le systeme pour un bon fonctionnement)
docker + k8s


**Les Types de Grants (Flux):**
**Authorization Code Flow:**
Cas: Applications web (backend + frontend séparés).
Sécurité : High (le token n'est pas exposé au frontend).

Steps:
L'utilisateur est redirigé vers Keycloak pour login.
Keycloak renvoie un code au backend.
Le backend échange le code contre un token.

**PKCE (Proof Key for Code Exchange):**
Cas: Apps mobiles/SPA (pas de secret client sûr).
Sécurité : Évite les attaques par interception de code.
NB: reste peu sûr car tout ce qui est côté client est à la disposition des users.

**Client Credentials Flow:**
Cas: Communication machine-to-machine (backend services).
Sécurité : Utilise un client_id + client_secret.
NB: peut être utilisé dans le cas d'une communication entre microservices. (auth)

**Resource Owner Password Credentials (ROPC):**
Cas: Applications legacy (déconseillé sauf migration).
Sécurité: Moins sécurisé (envoi direct des credentials).


**Device Authorization Grant:**
Cas : Devices IoT/TV (pas de clavier).
Sécurité : L'utilisateur valide sur un autre device.

**Refresh Token Flow:**
Cas : Récupérer un nouveau access_token sans reconnecter l'utilisateur.


**NB:**
les grants flow les plus utilisés:
authorization code,
Client Credentials Flow,
Device Authorization Grant
refresh token flow


**Kafka:**
plateforme de streaming d'evenements distribuée, conçut pour la gestion de flux de donénes en temps réel de maniére scalable, durale, tolérant aux pannes.

concepts:
topic, 
partitions(subset of topic => parallel exec), 
producer, consumer, 
broker (kafka server storing data), 
consummer group (groupe de consommateur partagent la charge), 
offset (position d'un message dans une partition), 
replication (copie de la data => fault tolerance)

use cases:
data streaming & processing,
event driven archi, microservices,
process data in real time
large quantity of data
large project


**Choix de l'architecture, technologies:**
- bien comprendre les besoins métiers et techniques (poser une suite de questions)
(les principaux cas d'usage, volume du traffique, nature de l'app, latence, tolerance aux pannes, cohérances, team skills, cost)
- type de projet (envergure: petit, moyen, ..., poc).
- type de communication (sync, async)
- bdd: data nature, complexe transaction, acid properties, recherche complexe, schema flexibility, read/write, cap theorem, relationships entre entité.
- évaluer les trade-off:
(consistency vs performence, latence vs Throughput, simplicité vs Flexibilité)
- évaluer la solution: 
(réaliser un poc, testing: gatling, ...)
- observabilité: 
inclure des outils efficace pour l'observabilité du projet + la bonne mise ene place.


**Testing:**
la pyramide des tests:

- unitaire: 70-80 %, rapide à executer, moins chers à maintenir.
- integration: test complet de la fonctionnalitée, inclu dans les test coverage.
- E2E: test scenario user complet, simule user réel, coût élevé maintien.

- code coverage: (test coverage)
pourcentage de test unitaire couvrant l'app => 70-80% unit tests

- test de qualimétrie:
    - Fonctionnalités: detection de bugs, code smell, vulnérabilité, dette techniques, couverture des tests, code dupliqué.

**Choix des Métriques:**
Selon le type de projet:
- MVP:
test unitaire, coverage 60%
tools: sonar free, jacoco

- app critique (bancaire):
coverage 90% coverage, test e2e, audit owasp (security)
tools: sonar entreprise, ...

- DevOps Team:
pipeline ci/cd .
Rejet des MR si coverage < 90%, vulnérability/security issue, failed test, ...
tools: jenkins, SonarQube, owasp check dep, trivy, ...

Best practices:

- test unitaire: simuler interaction avec le reste du système.
- crée un environnement de testing: pour l'éxécution des test intégration, e2e
- utiliser les tests e2e aux scénarios critique uniquement.
- les test de performences: temps de réponse, comportements de l'app avec une hausse de la charge, seuil critique, charge max supporté.

selenium:
framework open-source pour automatiser les tests des applications web.
utiliser plusieurs browser,
execution en parallele,
simuler interaction user (clic, input, navigation).
