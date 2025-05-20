## Java

1. **Syntaxe de base :**

   - Types de données primitifs (int, char, etc.).
   - Opérateurs arithmétiques et logiques.
   - Structures de contrôle (if, for, while).

2. **Programmation Orientée Objet (POO) :**

   - Classes et objets.
   - Encapsulation.
   - Héritage.
   - Polymorphisme.

3. **Gestion des exceptions :**

   - Try, catch, finally.
   - Création d'exceptions personnalisées.

4. **Collections Framework :**

   - Listes, ensembles, maps.
   - Itérateurs.

5. **Entrées/Sorties (I/O) :**

   - Lecture et écriture de fichiers.
   - Sérialisation.

6. **Threads et Concurrence :**
   - Création et gestion de threads.
   - Synchronisation.
     Execution de plusieurs thread pour améliorer la réactivité et performence.
     Création:

- Étendre la classe Thread + definir code dans run()
- Implementé Runnable + definir code dans run().

7. **Bibliothèques standard :**

   - Utilisation des API Java standard.
   - Manipulation des dates avec `java.time`.
   - Date: LocalDate, locatDateTime, localTime, Date, Time, Calendar

8. **Programmation Générique :**

- Utilisation des génériques pour des types paramétrés.

9. **Expressions Lambda et API Stream :**

- **Lambda**: fonction annonyme qui permet de racourcir, et simplifier la syntaxe, et aussi éviter de créé une classe pour résoudre un probléme simple.

- **Stream**: flux de données qui subit des opéraitons:
  Map, reduce, filter, forEach, collect
  Opérations intérmédiaire, Opération terminale

10. **Modules (Java 9 et plus) :**

    - Organisation du code en modules.
    - Gestion des dépendances entre modules.

11. **Final**:
    valeur ne change pas

- Type primitive valeur ne change pas (addresse mémoire).
- Type objet ne peut plus être modifié.
- Final class: ne peut hérité.
- Méthod ne peut plus être redéfinit.

12. **Exceptions / Errors**:

**Exceptions:**

- **Checked exceptions**: compile time, des exceptions qu’on peut capturer. → throw.
- **Unchecked exceptions**: run-time des exceptions qu’on ne peut pas capturer. → throw.
  Comportement inattendu: générer une exception.

**Erreur**: vm error, assertion errors, ...

13. **Interface:**
    C’est une collection de methodes abstraire (sans le corps de la méthode), qui a pour but de définir un contrat l’interface, et la classe qui l’implémente (comportement commun entre plusierurs classes)

14. **Classe abstraite:**
    C’est une classe qui contient un ensemble de méthode, concrétes ou abstraites, fournis une implémentation partielles aux méthodes.

15. **Classes anonyme:**
    c’est une classe sans nom qui est définit et instancié en même temps, definit pour ??

16. **Interface fonctionnelle:**
    une interface qui contient une seul méthode abstraite, commence avec FunctionalInterface, facilité la programmation fonctionnelle.

## Spring Framework

Framework java populaire, créé des app web, desktop, ... offre une architecture robuste, sécurisé et scalable.

2. **Inversion de Contrôle (IoC) et Injection de Dépendances (DI):**

- **ID**: une classe dépendra d’une interface, ou aura recours à une ID pour avoir accès à des dépendances dont elle a besoin: constructeur, setter, …

- **IC**: c’est un procédé qui permet de gérer le cycle de vie des objet dans spring de la création jusqu'à la destruction.

5. **Programmation Orientée Aspect (AOP) :**

- Création d'aspects pour gérer les préoccupations transversales.
- Utilisation des annotations @Aspect.

6. **Transactions :**

- Gestion des transactions déclaratives avec @Transactional.
- Configuration des gestionnaires de transactions.

9. **Appels de services RESTful :**

- Création et consommation d'API REST avec RestTemplate ou WebClient.

10. **WebSockets :**

- Communication en temps réel avec STOMP et SockJS.

11. **Tests :**

- Tests unitaires et d'intégration avec Spring Test.
- Utilisation de MockMvc pour tester les contrôleurs.

12. **Déploiement :**

- Emballage des applications Spring Boot en JAR ou WAR.
- Déploiement sur des serveurs tels que Tomcat ou dans le cloud.

### **Spring modules**

1. **Spring Core / IoC Container**

   - **Rôle :** Cœur du framework, il gère l’inversion de contrôle (IoC) et l’injection de dépendances.
   - **Pourquoi c’est important :** La compréhension de ce module est fondamentale, car elle permet de concevoir des applications modulaires et testables.

2. **Spring Boot**

   - **Rôle :** Simplifie la configuration et le déploiement d’applications Spring en offrant une configuration automatique et des starters.
   - **Pourquoi c’est important :** Il permet de démarrer rapidement un projet et de se concentrer sur la logique métier sans se perdre dans une configuration complexe.

3. **Spring MVC**

   - **Rôle :** Fournit une architecture basée sur le modèle MVC pour développer des applications web robustes et des API REST.
   - **Pourquoi c’est important :** C’est le module de base pour construire des interfaces web et des services back-end en respectant une séparation claire entre la logique métier et la présentation.

4. **Spring Data**

   - **Rôle :** Simplifie l’accès aux bases de données via des abstractions pour JPA, MongoDB, Redis, etc.
   - **Pourquoi c’est important :** Il permet de réduire le code boilerplate et d’unifier la gestion des données, ce qui est essentiel dans la plupart des applications.

5. **Spring Security**

   - **Rôle :** Gère l’authentification et l’autorisation, protégeant ainsi vos applications contre les accès non autorisés.
   - **Pourquoi c’est important :** La sécurisation des applications est cruciale, et ce module offre des mécanismes robustes pour protéger vos services web et API.

6. **Spring AOP (Aspect Oriented Programming)**

   - **Rôle :** Permet de gérer les préoccupations transversales (logging, transactions, sécurité, etc.) en séparant ces aspects de la logique métier.
   - **Pourquoi c’est important :** Il aide à garder le code propre et modulaire en centralisant les comportements transversaux.

7. **Spring Cloud**

   - **Rôle :** Fournit des outils pour la création et la gestion d’architectures microservices (configuration distribuée, découverte de services, routage, etc.).
   - **Pourquoi c’est important :** Dans un contexte de microservices, il facilite la gestion des communications entre services et la résilience des applications.

8. **Spring Batch**

   - **Rôle :** Conçu pour le traitement par lots, ce module aide à définir, exécuter et gérer des jobs de traitement de données en masse.
   - **Pourquoi c’est important :** Pour les applications nécessitant des traitements asynchrones ou périodiques sur de grandes quantités de données, Spring Batch offre un cadre éprouvé.

9. **Spring WebFlux**
   - **Rôle :** Introduit la programmation réactive dans Spring pour construire des applications non bloquantes et hautement scalables.
   - **Pourquoi c’est important :** Avec l’essor des applications en temps réel et des environnements nécessitant une haute réactivité, WebFlux est un atout majeur dans l’arsenal Spring.

## Maven

est un outil de **build automation** et de **gestion de projet** développé par la fondation Apache, principalement utilisé dans l'écosystème Java.

Maven repose sur le concept de **Project Object Model (POM)**, un fichier XML décrivant le projet, ses dépendances, ses plugins et la configuration du build.

Il gère le cycle de vie complet d’un projet, de la compilation au packaging, en passant par les tests et le déploiement.

### Intérêts ? Avantages

- **Gestion des dépendances :**  
  Maven télécharge automatiquement les bibliothèques nécessaires depuis des dépôts distant ou locale.
- **Automatisation des tâches :**  
  Automatiser la compilation, les tests, la génération de documentation, le packaging et le déploiement.
- **Intégration continue :** pipeline CI/CD (build continue, run test unitaire, integration)

### Commandes

- **`mvn clean`**  
  Supprime les fichiers générés lors du build précédent (dossier `target` par défaut).

- **`mvn compile`**  
  Compile le code source du projet.

- **`mvn test`**  
  Exécute les tests unitaires.

- **`mvn package`**  
  Compile le projet et génère le package (JAR, WAR, etc.) dans le dossier `target`.

- **`mvn install`**  
  Compile, teste et installe le package dans le dépôt local de Maven pour le réutiliser dans d'autres projets.

- **`mvn deploy`**  
  Déploie le package dans un dépôt distant, utile pour partager des artefacts dans un environnement d’intégration continue ou en production.
  
  
  
