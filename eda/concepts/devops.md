

## Jenkins

un serveur d'automatisation qui orchestre les tâches de: build, test, deploiement.

---

### **Pros:**

- open source
- large community + company product
- multitube de plugins => integrer multotive d'outils
- flexible
- architecture master/slave
- pipele as code (une approche declarative pipeline)

---

### **Concepts Importants dans Jenkins:**

- **Jobs/Builds** : Ce sont les tâches automatisées qui réalisent des actions spécifiques (ex. compilation, tests, déploiement).
- **Pipelines** : Une suite de stages (étapes) qui décrivent le flux complet de CI/CD. Ils peuvent être définis de façon déclarative dans un Jenkinsfile.
- **Plugins** : Extensions qui ajoutent de nouvelles fonctionnalités ou facilitent l’intégration avec d’autres outils.
- **Architecture Master/Agent** : Le serveur central (Master) orchestre les tâches, tandis que les agents (ou nœuds) exécutent les jobs, permettant une exécution parallèle et distribuée.
- **Triggers** : Mécanismes pour déclencher automatiquement les builds, que ce soit suite à un commit, selon un planning (cron) ou via des webhooks.
- **Workspace** : Chaque job s’exécute dans un espace de travail isolé, garantissant que les builds ne se mélangent pas et que les artefacts sont gérés de façon propre.


## **1. Azure DevOps**  
**Qu’est-ce que c’est ?**  
C'est une plateforme **CI/CD et gestion de projet** fournie par Microsoft pour le développement et le déploiement d’applications.  

**À quoi ça sert ?**  
- **Automatiser** les builds et déploiements.  
- **Gérer** le code source (Git).  
- **Suivre** les tâches et sprints (Agile, Scrum).  
- **Stocker** et distribuer des artefacts (packages).  

**Comment ça marche ?**  
- **Azure Repos** → Versioning du code avec Git.  
- **Azure Pipelines** → CI/CD avec YAML ou interface graphique.  
- **Azure Artifacts** → Stockage de dépendances (NuGet, npm, Maven).  
- **Azure Boards** → Suivi des tâches Agile.  
- **Azure Test Plans** → Automatisation des tests.  

**Pros:**  
✅ Intégration native avec Azure et GitHub.  
✅ CI/CD puissant avec **agents hébergés ou auto-hébergés**.  
✅ Support multi-langages (Node.js, Java, .NET, Python…).  
✅ Sécurité et contrôle des accès via Azure AD.  

**Cas d'utilisation**  
- Déploiement d’une application **.NET sur Azure Kubernetes Service (AKS)**.  
- Automatisation du CI/CD pour une app **React + Node.js** avec tests.  
- Gestion Agile pour une équipe DevOps (Kanban, Scrum).  

**Ex:**  
**CI/CD pour une API Node.js**  
1. Dev pousse le code sur Azure Repos.  
2. Azure Pipelines exécute le build, tests et déploiement.  
3. L’API est déployée sur **Azure App Service**.  

---

## **2. TeamCity**  
**Qu’est-ce que c’est ?**  
Un outil CI/CD de JetBrains permettant d’automatiser les builds et tests d’application.  

**À quoi ça sert ?**  
- Compiler et tester du code automatiquement.  
- Déployer des applications sur **Kubernetes, AWS, Azure**.  
- Gérer des builds distribués avec **plusieurs agents**.  

**Comment ça marche ?**  
- **Build Configurations** → Définit les étapes de build (Maven, Gradle, npm).  
- **Triggers** → Automatisation des builds à chaque commit, pull request.  
- **Agents** → Machines qui exécutent les builds.  
- **Artifact Storage** → Sauvegarde des binaires et logs.  

**Pros:**  
✅ **Facile à intégrer** avec GitHub, Bitbucket, GitLab.  
✅ **Extensible** avec des plugins (Docker, Kubernetes).  
✅ **Rapide** avec exécution **parallèle des builds**.  

**Cas d’utilisation**  
- CI/CD d’une **application Java avec Maven**.  
- Déploiement automatique d’une image Docker sur Kubernetes.  

**Ex:**  
1. Dev pousse du code sur GitHub.  
2. TeamCity lance **les tests unitaires et de performance**.  
3. Génère une image Docker et la **pousse sur Docker Hub**.  
4. Déploie l’image sur un cluster Kubernetes via **Helm**.  

---

## **3. XL Deploy**  
**Qu’est-ce que c’est ?**  
Un outil de **déploiement continu** permettant d’automatiser les releases d’applications sur **serveurs, containers ou cloud**.  

**À quoi ça sert ?**  
- Gérer **les déploiements multi-environnements** (Dev, Test, Prod).  
- Assurer des **rollback en cas d’échec**.  
- Orchestrer les **mises à jour progressives (blue/green, canary)**.  

**Comment ça marche ?**  
1. Un **package d’application** est créé (ZIP, JAR, Docker image).  
2. XL Deploy **déploie sur les cibles** (serveurs, Kubernetes, AWS).  
3. Vérification automatique et rollback en cas de problème.  

**Pros:**  
✅ **Automatisation complète** du déploiement.  
✅ **Compatible multi-cloud** (AWS, Azure, GCP).  
✅ **Contrôle fin des accès et logs**.  

**Cas d’utilisation**  
- Déploiement d’une **application Spring Boot sur Kubernetes**.  
- Migration d’une application on-premise vers AWS.  

**Ex:**  
1. Un package JAR est généré depuis TeamCity.  
2. XL Deploy récupère ce package et l’envoie sur un serveur Tomcat.  
3. Vérification et rollback si nécessaire.  

---

## **4. XL Release**  
**Qu’est-ce que c’est ?**  
Un outil d’**orchestration CI/CD** qui permet de gérer **les pipelines de déploiement** avec validation et suivi.  

**À quoi ça sert ?**  
- Coordonner les **différents outils CI/CD** (Azure DevOps, TeamCity, XL Deploy).  
- Implémenter des **workflow avec approbations et gates**.  
- Automatiser **l’audit et le reporting des releases**.  

**Comment ça marche ?**  
1. **Définition du pipeline** (ex: Build → Test → Déploiement).  
2. **Automatisation** des étapes (TeamCity pour build, XL Deploy pour release).  
3. **Validation manuelle ou automatique**.  
4. **Audit et logs** disponibles pour conformité.  

**Pros:**  
✅ **Orchestration complète** du cycle de vie applicatif.  
✅ **Intégration avec Jira, Slack, ServiceNow** pour gestion ITSM.  
✅ **Visibilité et auditabilité** des releases.  

**Cas d’utilisation**  
- **Gouvernance CI/CD** pour une **banque ou assurance**.  
- **Release Management** dans une équipe DevOps.  

**Ex:**  
1. Déclenchement d’un pipeline CI/CD après un merge sur `main`.  
2. XL Release orchestre les builds TeamCity et les déploiements XL Deploy.  
3. Un responsable doit **valider manuellement** avant la mise en production.  

---