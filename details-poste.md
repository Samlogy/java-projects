

**Roadmap:**
prendre les 2 app spring webflux.
tranformer en microservices => spring cloud
comm: webclient async
comm: kafka pub/sub
Gestion des Erreurs et Résilience
Testing des flux reactifs (testcontainers ?)
Monitoring et Observabilité
add auth => auth 2.0 + keycloak (oidc), rate limiting, cors, 
pipeline ci/cd jenkins
deploy k8s (helm charts)

k8s ? scalabilité, résilience, fault tolerance, availability, performence, Reliability, security, ...


Java/Angular resources:

**webflux:**
https://github.com/Samlogy/java-projects
https://github.com/tamani-coding/spring-boot-webflux-r2dbc-postgrest-example


**test containers:**
https://github.com/testcontainers/tc-guide-testing-spring-boot-rest-api
https://github.com/testcontainers/testcontainers-java-spring-boot-quickstart
https://testcontainers.com/guides/testing-spring-boot-rest-api-using-testcontainers/
https://dextrop.medium.com/containerizing-a-angular-application-using-docker-a71968269821
https://bell-sw.com/blog/how-to-use-testcontainers-with-spring-boot-applications-for-integration-testing/


**webflux angular keycloak:**
https://github.com/kprasad99/spring-webflux-keycloak-angular/tree/master
https://github.com/tamani-coding/fullstack-oauth2-angular-spring-boot-keycloak


**webflux kafka**
https://medium.com/@hakandagdelennnn/spring-web-flux-and-kafka-integration-building-receiver-and-producer-layers-with-dead-letter-17bd5a0aeb15
https://dev.to/devcorner/building-a-reactive-system-with-kafka-and-spring-webflux-1f87
https://www.youtube.com/watch?v=KQDTtvZMS9c


**webflux microservoces kafka:**
https://dev.to/tutorialq/building-scalable-applications-with-kafka-and-reactive-programming-5cea
https://dev.to/tutorialq/building-reactive-microservices-a-step-by-step-guide-1jha







todos:
test: unit, int (testcontainers)
microservices: webflux
kafka com entre services
auth: oauth + webflux + kaycloak
deploy => k8s

test: unit, int (angular)
docker + k8s

selenium: e2e


curl -X POST 

curl -H 'Content-Type: application/json' \
     -d  '{"message" : "this is the first message i send in kafka spring version 22"}' \
     -X POST \
     http://localhost:8080/api/v1/messages/json



bin/kafka-topics.sh --create --topic alibou --bootstrap-server localhost:9092*


install kafka locally
create a producer: spring app (spring reactive, lombok, spring apache kafka).
create a consumer: spring app (spring web, lombok, spring apache kafka).

create a kafka project: spring app (spring web, lombok, spring apache kafka).


# build optimized docker image (add config => pom.xml)
mvn spring-boot:build-image


# deploy spring app => k8s



angular
concepts importants ? fonctionnalités ?
pros & cons ?
les nouveautés par versions: 15, 16, 17, 18, 19 ?
comment optimisé une app angular en prod ?


selenium
c'est quoi ? comment ca marche ?
pros & cons ?




Compétences clés attendues
**Techniques**
Architecture applicative
CI/CD, intégration continue
Stack Java/Spring, Angular, MongoDB/MariaDB
Conteneurisation & orchestration (Docker, Kubernetes)
Tests automatisés (unitaires, composants, intégration)
Qualimétrie (Sonar, métrologie)

**Méthodologie**
Agilité (Scrum), gestion des rituels
Pilotage transverse
Capacité à travailler avec les architectes, PO, développeurs, métiers
Capacité de formation & transmission de savoir

**Soft skills**
Leadership technique
Capacité à prioriser et convaincre
Rigueur documentaire
Aisance relationnelle (notamment avec des PO, métiers, développeurs)
Proactivité en veille technologique


**Architectures:**
Monolith
Microservices


**Stack technique:**
Java 17+
Spring Boot 3
Angular 18
MongoDB
MariaDB
SonarQube
Jenkins
Kubernetes
Kafka


Responsabilités spécifiques du Tech Lead
Chiffrage des fonctionnalités
Mise en place d’outils de qualité, CI/CD, métriques
Rédaction et maintien à jour de la documentation technique
Transfert de connaissances vers les équipes applicatives
Support technique pour les problématiques complexes et anomalies en prod


