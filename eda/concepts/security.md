# Security

## DevSecOps

Le **DevSecOps** est une approche qui intègre la **sécurité** dès le début du cycle de développement (**Shift Left Security**) et l'automatise tout au long du pipeline CI/CD.  

---

### **1. Principes fondamentaux du DevSecOps**  
- **Shift Left Security** → Intégrer la sécurité dès le début du développement.  
- **Automatisation de la sécurité** → Tests de sécurité automatisés dans le pipeline CI/CD.  
- **Infrastructure as Code (IaC)** → Gérer les infrastructures comme du code pour éviter les erreurs humaines.  
- **Least Privilege (Principe du moindre privilège)** → Limiter les droits d’accès aux stricts besoins.  
- **Zero Trust** → Ne faire confiance à aucun composant par défaut, tout doit être vérifié (authentification, accès).  
- **Compliance as Code** → Appliquer automatiquement les standards de sécurité et de conformité.  

---

### **2. Sécurité du Code et des Dépendances:**  

**Analyse statique du code (SAST - Static Application Security Testing)**  
- Détecte les vulnérabilités dans le code source (ex. : injections SQL, XSS).  
- Ex:  **SonarQube, Snyk, CodeQL, Checkmarx**.  

**Analyse dynamique du code (DAST - Dynamic Application Security Testing)**  
- Teste l’application en cours d’exécution pour détecter les failles exploitées en conditions réelles.  
- Ex:  **OWASP ZAP, Burp Suite, Nikto**.  

**Analyse des dépendances (SCA - Software Composition Analysis)**  
- Vérifie si les bibliothèques utilisées ont des failles connues (CVEs).  
- Ex:  **Dependabot, Snyk, OWASP Dependency-Check**.  

---

### **3. Sécurité des Secrets et des Identités**  
**Stockage sécurisé des secrets**  
- **Éviter de stocker les clés API et mots de passe en clair** dans le code.  
- Utiliser un **gestionnaire de secrets** : **HashiCorp Vault, AWS Secrets Manager, Doppler**.  

**IAM (Identity & Access Management) & RBAC (Role-Based Access Control)**  
- Définir des rôles et permissions précises pour chaque utilisateur et service.

---

### **4. Sécurité des Conteneurs et Kubernetes**  
**Sécurisation des images Docker**  
- **Éviter d’utiliser `latest`**, toujours fixer une version précise.  
- Scanner les images avec **Trivy, Clair, Anchore**.  

**Sécurité Kubernetes (K8S Security)**  
- Activer **Pod Security Standards (PSS)** ou **Kyverno/Opa Gatekeeper** pour appliquer des politiques de sécurité.  
- Restreindre l’accès réseau avec **Network Policies**

---

### **5. Sécurité de l’Infrastructure et IaC**  
**Infrastructure as Code (IaC) Security**  
- Vérifier les fichiers Terraform, Ansible, Kubernetes YAML avec **Checkov, Terrascan, Tfsec**.  

**Durcissement des systèmes et configurations**  
- Désactiver les ports inutilisés, appliquer les correctifs, et restreindre SSH.  
- Utiliser des benchmarks **CIS (Center for Internet Security)**.  

---

### **6. CI/CD Sécurisé**

- **Bonnes pratiques de sécurité dans le pipeline CI/CD**  
- **Signer et vérifier l’intégrité des artefacts** (ex. : Cosign, Sigstore).  
- **Appliquer des scans de vulnérabilités sur le code et les conteneurs**.  
- **Utiliser des runners CI/CD sécurisés avec restrictions d’accès**.

---

### **7. Monitoring, Logging et Incident Response**  
**Centralisation des logs et SIEM**  
- Utiliser **ELK (Elasticsearch, Logstash, Kibana)**, **Splunk**, ou **Datadog** pour **détecter les anomalies**.  
- Mettre en place des alertes **Prometheus + Grafana + Alertmanager**.  


**Détection des intrusions et réponse automatique (SOAR)**  
- Mettre en place **Wazuh, Suricata, CrowdStrike** pour détecter les attaques en temps réel.  
- Automatiser la réponse avec **AWS Lambda, SOAR (ex: Splunk Phantom, Cortex XSOAR)**.  


## Security Best practices

### **1. Firewall & Segmentation Réseau**
**C’est quoi ?**  
Un **pare-feu (firewall)** contrôle le trafic réseau entre différentes parties d’un système (externe, interne, entre services).  
La **segmentation réseau** consiste à diviser un réseau en plusieurs sous-réseaux pour limiter les mouvements latéraux en cas d’attaque.  

**Comment le mettre en place ?**  
- **Iptables** ou autre firewall installable. 
- **Cloud Firewall** : Sur AWS, GCP ou Azure, tu peux définir des règles dans un **Security Group** ou un **NSG**.
- **Zero Trust Network** : Un modèle où chaque connexion est contrôlée et rien n’est **automatiquement** approuvé.

---

### **2. IAM (Identity & Access Management) & RBAC (Role-Based Access Control)**
**C’est quoi ?**  
**IAM** gère **qui** peut accéder à **quoi** et avec **quel niveau de permission**.  
**RBAC** attribue des permissions aux utilisateurs **en fonction de leur rôle** (ex: admin, développeur, utilisateur).

**Comment le mettre en place ?**  
- **Keycloak** (que tu connais déjà) → Permet de gérer les utilisateurs et leurs accès.  
- **IAM Cloud** (AWS IAM, GCP IAM) → Exemples de rôles :
  - `admin` → Accès total  
  - `read-only` → Accès en lecture seule  
  - `devops` → Peut déployer mais pas modifier les IAM  
- **RBAC en PostgreSQL** :
  ```sql
  CREATE ROLE dev_readonly;
  GRANT SELECT ON ALL TABLES IN SCHEMA public TO dev_readonly;
  ```

---

### **3. Vault (Gestion des Secrets)**
**C’est quoi ?**  
Un **coffre-fort de secrets** stocke les **mots de passe, clés API, certificats** pour éviter qu’ils ne soient exposés dans le code.

**Comment le mettre en place ?**  
- **HashiCorp Vault** → Solution populaire pour stocker les secrets.  
- **AWS Secrets Manager / GCP Secret Manager** → Gère les secrets dans le cloud.  
- **Fichier `.env` sécurisé** (⚠️ à ne **jamais** committer sur Git !).  

---

### **4. Sécurisation des Bases de Données**
**Pourquoi c’est important ?**  
Les bases de données contiennent des données sensibles. Si elles sont mal protégées, un attaquant peut **voler ou modifier** ces données.

**Comment le mettre en place ?**
- **IAM + RBAC en PostgreSQL**  
  ```sql
  CREATE USER app_user WITH ENCRYPTED PASSWORD 'SuperSecret123';
  GRANT SELECT, INSERT ON my_table TO app_user;
  ```
- **Chiffrement des données au repos** avec PostgreSQL :
  ```sql
  CREATE EXTENSION pgcrypto;
  INSERT INTO users (id, name, password) VALUES (1, 'Alice', crypt('mypassword', gen_salt('bf')));
  ```
- **TLS pour sécuriser la connexion** à la base (évite les écoutes réseau).

---

### **5. MFA (Multi-Factor Authentication)**
**C’est quoi ?**  
L’authentification à plusieurs facteurs ajoute une **couche de sécurité supplémentaire** :
- **1er facteur** : Mot de passe  
- **2e facteur** : Un code temporaire (SMS, Google Authenticator, Yubikey…)

**Comment le mettre en place ?**
- **Keycloak + MFA** → Activation de **TOTP (Google Authenticator)**.  
- **MFA dans AWS IAM / GCP IAM** → Exiger un **OTP** pour les connexions admin.  
- **Exemple avec Keycloak** :
  - Activer "Authenticator" dans les paramètres des utilisateurs.  
  - Ajouter "OTP Policy" pour forcer Google Authenticator.  

---

### **6. Chiffrement (Encryption)**
**Pourquoi c’est important ?**  
Le chiffrement protège les **données stockées et en transit** contre les attaques.

**Comment le mettre en place ?**
- **Chiffrement des fichiers**
- **Chiffrement TLS entre services** (HTTPS, TLS dans PostgreSQL).
- **Stockage chiffré des mots de passe** (bcrypt, argon2).

---

### **7. Monitoring & Alerting pour Détection des Intrusions**
**C’est quoi ?**  
Il faut **surveiller** l’activité et déclencher des alertes en cas d’intrusion.

**Comment le mettre en place ?**
- **SIEM (Security Information and Event Management)** → Agrège les logs et détecte les anomalies.
- **Exemple avec Prometheus + Grafana** → Création d’alertes sur des logs suspects.
- **ELK (Elasticsearch, Logstash, Kibana)** → Analyse des logs d’accès et des erreurs.


## OWASP TOP 10

L’**OWASP Top 10** est une liste des **10 vulnérabilités de sécurité les plus critiques** dans les applications web, publiée par l’**Open Web Application Security Project (OWASP)**. Elle est mise à jour régulièrement pour refléter les **menaces les plus courantes**.  


**OWASP Top 10 (2021)**  

**1. A01 - Broken Access Control (Contrôle d'accès défaillant)**  
**Problème** : Un utilisateur peut accéder à des ressources qu'il ne devrait pas voir/modifier.  
**Ex:**  Un utilisateur normal peut accéder à `/admin` et modifier des données.  
**Solution** :  
- Utiliser **RBAC (Role-Based Access Control)**.  
- Vérifier les permissions côté **serveur** (pas juste via l’UI).  
- Désactiver l'indexation des URLs sensibles.  
- Utiliser des **JWT** bien sécurisés.  

---

**2. A02 - Cryptographic Failures (Mauvaise gestion du chiffrement)**  
**Problème** : Données sensibles exposées en clair ou chiffrées avec un mauvais algorithme.  
**Ex:**  Stocker des mots de passe en **MD5** ou envoyer des infos en **HTTP (sans TLS)**.  
**Solution** :  
- Chiffrer les **données en transit (TLS, HTTPS)** et **au repos (AES-256, bcrypt)**.  
- Ne jamais stocker de mots de passe en clair, utiliser **bcrypt** ou **Argon2** :  
- Configurer **HSTS** pour forcer HTTPS.  

---

**3. A03 - Injection (SQLi, XSS, etc.)**  
**Problème** : Un attaquant injecte du code dans une requête (SQL, JavaScript…).  
**Ex:**  SQL Injection (SQLi)

**Solution** :  
- Toujours utiliser des **requêtes préparées**
- Valider les entrées utilisateur.  
- Activer **CSP (Content Security Policy)** contre les attaques XSS.  

---

**4. A04 - Insecure Design (Mauvaise conception sécuritaire)**  
**Problème** : Une application mal conçue facilite les attaques.  
**Ex:**  Authentification sans MFA, permissions mal gérées.  
**Solution** :  
- Appliquer les principes **Zero Trust** et **Least Privilege**.  
- Intégrer la sécurité dès le début (Shift Left).  
- Faire des **Threat Models** (ex : STRIDE).  

---

**5. A05 - Security Misconfiguration (Mauvaise configuration de sécurité)**  
**Problème** : Paramètres par défaut laissés actifs (ex : mots de passe admin par défaut).  
**Ex:**  Une base PostgreSQL exposée avec `postgres:postgres`.  
**Solution** :  
- Désactiver les **endpoints inutiles**.  
- Modifier tous les **mots de passe par défaut**.  
- Activer des **en-têtes HTTP de sécurité**.

---

**6. A06 - Vulnerable and Outdated Components (Composants obsolètes/vulnérables)**  
**Problème** : Utiliser des librairies ou logiciels non mis à jour.  
**Ex:**  Une appli utilisant **jQuery 1.12** (avec des failles connues).  
**Solution** :  
- Scanner les dépendances avec **Dependabot, Snyk ou OWASP Dependency-Check**.  
- Ne jamais utiliser des **versions EOL (End Of Life)**.  
- Utiliser un **SCA (Software Composition Analysis)**.  

---

**7. A07 - Identification and Authentication Failures (Mauvaise gestion d’authentification)**  
**Problème** : Authentification faible ou mauvaise gestion des sessions.  
**Ex:**  Pas de verrouillage après X tentatives de connexion.  
**Solution** :  
- **MFA obligatoire** (OTP, Yubikey…).  
- Définir une politique de **gestion des sessions (expiration, logout)**.  
- Vérifier la robustesse des **tokens JWT** :   

---

**8. A08 - Software and Data Integrity Failures (Échec d’intégrité logicielle et des données)**  
**Problème** : Téléchargement de mises à jour non signées ou CI/CD non sécurisé.  
**Ex:**  Un attaquant injecte du code dans un pipeline CI/CD mal sécurisé.  
**Solution** :  
- Toujours **signer et vérifier les mises à jour** (GPG).  
- Sécuriser les pipelines CI/CD avec **des secrets dans Vault**.  
- Utiliser **des signatures numériques** pour les releases.  

---

**9. A09 - Security Logging and Monitoring Failures (Échec de journalisation et de monitoring)**  
**Problème** : L’absence de logs permet aux attaquants de rester invisibles.  
**Ex:**  Pas de logs pour les tentatives de connexion échouées.  
**Solution** :  
- **Activer les logs de sécurité** dans les apps :
- Utiliser **ELK (Elasticsearch, Logstash, Kibana)** ou **SIEM** pour l’analyse.  
- Configurer des **alertes Grafana/Prometheus** sur des événements suspects.  

---

**10. A10 - Server-Side Request Forgery (SSRF)**  
**Problème** : Un attaquant force le serveur à faire une requête à une ressource interne.  
**Ex:**  Un champ d’URL mal filtré.

**Solution** :  
- **Restreindre les requêtes sortantes** avec un firewall.  
- **Filtrer et valider les URLs** saisies par l’utilisateur.  
- Désactiver la **résolution DNS interne** pour certaines apps.  


## **Zero Trust ?**  
C'est un modèle de sécurité basé sur le principe **"Ne jamais faire confiance, toujours vérifier"**. Il considère que **toute personne, appareil ou application est une menace potentielle**, même à l'intérieur du réseau.  

L’objectif est de **minimiser les risques d’intrusion** en appliquant des contrôles stricts et en vérifiant en permanence l’identité et l’accès des utilisateurs et des systèmes.  

---

**Comment ça marche ?**  

Le Zero Trust repose sur 3 principes clés :  

1. **Vérification systématique de chaque accès**  
   - Authentification forte (MFA) avant toute action.  
   - Vérification en temps réel des identités et des appareils.  

2. **Principe du moindre privilège**  
   - Un utilisateur ou un système n’a accès qu’aux ressources strictement nécessaires.  
   - Les accès sont temporaires et soumis à validation régulière.  

3. **Surveillance et analyse continue**  
   - Contrôle des comportements suspects en temps réel.  
   - Blocage automatique des accès anormaux.  

---

### **Comment le mettre en place ?**  

**1. Identifier les actifs et les flux de données**  
- Cartographier les applications, bases de données et systèmes critiques.  
- Identifier qui accède à quoi et comment.  

**2. Appliquer l’authentification forte (MFA)**  
- Exiger une authentification multi-facteur pour tous les accès sensibles.  
- Utiliser des outils comme **Okta, Azure AD, Google Authenticator**.  

**3. Implémenter un contrôle des accès basé sur les rôles (RBAC) et le contexte (ABAC)**  
- Appliquer le principe du **moindre privilège** (Least Privilege).  
- Mettre en place des accès conditionnels :  
  - Ex. : Refuser l’accès si un utilisateur se connecte depuis un pays inhabituel.  

**4. Segmentation du réseau (Micro-segmentation)**  
- Diviser le réseau en petites zones isolées pour limiter la propagation d’une attaque.  
- Utiliser des solutions comme **Kubernetes Network Policies, AWS Security Groups, Azure NSG**.  

**5. Sécuriser les endpoints (Postes de travail et serveurs)**  
- Appliquer des mises à jour régulières.  
- Activer le chiffrement des disques et l’analyse des menaces.  

**6. Surveillance et détection des anomalies**  
- Mettre en place un **SIEM** (Security Information and Event Management) pour analyser les logs et détecter les comportements suspects.  
- Utiliser des outils comme **Splunk, ELK Stack, Microsoft Sentinel**.  

**7. Automatiser la réponse aux menaces**  
- Déployer des outils **SOAR** (Security Orchestration, Automation and Response) pour répondre rapidement aux incidents.  
- Ex. : Bloquer automatiquement une IP suspecte.  

---

### **Exemple d’application Zero Trust dans un pipeline CI/CD**  
1. **Authentification MFA obligatoire** pour accéder aux dépôts Git et aux outils DevOps.  
2. **Contrôle des accès RBAC** : seuls les développeurs peuvent push du code, seuls les DevOps peuvent déployer.  
3. **Scan de sécurité automatique (SAST, DAST, SCA)** dans la pipeline CI/CD.  
4. **Micro-segmentation** :  
   - L’environnement de test est isolé de la production.  
   - L’accès à la base de données est restreint aux seuls services autorisés.  
5. **Surveillance en temps réel** avec **SIEM et alertes** en cas de comportement anormal.  


## Best practices Security

### **1. Sécurité Physique**  

**Contrôle des accès**  
- Restreindre l’accès aux bâtiments et aux salles de serveurs (badge, biométrie, digicode).  
- Désactiver immédiatement les accès des employés quittant l’entreprise.  

**Surveillance et protection des infrastructures**  
- Installer des caméras de surveillance et détecteurs de mouvement dans les zones sensibles.  
- Utiliser des coffres sécurisés pour les documents et supports contenant des données sensibles.  

**Protection des équipements**  
- Protéger les serveurs dans des salles sécurisées avec contrôle d’accès.  
- Verrouiller les ports USB pour éviter les vols de données via clé USB.  
- Chiffrer les disques durs des ordinateurs et serveurs pour éviter l’accès en cas de vol.  

**Gestion des périphériques**  
- Interdire l’utilisation de clés USB non sécurisées.  
- Fournir du matériel approuvé et sécurisé aux employés (PC, smartphone).  
- Appliquer une politique de "Bring Your Own Device" (BYOD) stricte avec un antivirus obligatoire.  

---

### **Sécurité Comportementale**  

**Sensibilisation et formation**  
- Former régulièrement les employés aux risques de phishing, ransomware et ingénierie sociale.  
- Simuler des attaques (ex. phishing) pour tester la vigilance des employés.  

**Gestion des mots de passe**  
- Exiger des mots de passe forts (longueur minimale, majuscules, chiffres, caractères spéciaux).  
- Ne jamais réutiliser les mots de passe sur plusieurs services.  
- Activer l’authentification multi-facteur (MFA) pour les accès sensibles.  

**Protection contre le phishing et l’ingénierie sociale**  
- Ne jamais cliquer sur des liens ou ouvrir des pièces jointes suspectes.  
- Vérifier l’expéditeur avant de répondre à un email sensible.  
- Ne pas divulguer d’informations professionnelles sur les réseaux sociaux.  

**Sécurité des connexions et du travail à distance**  
- Utiliser un VPN sécurisé pour les connexions en dehors du bureau.  
- Ne jamais se connecter aux réseaux Wi-Fi publics sans protection (VPN, HTTPS).  
- Fermer la session après utilisation d’un poste partagé.  

**Bonnes pratiques générales**  
- Verrouiller son ordinateur en cas d’absence.  
- Ne jamais laisser des documents sensibles sur son bureau ("clean desk policy").  
- Éviter d’utiliser des applications non approuvées pour le travail (ex. WhatsApp pour partager des fichiers professionnels).  


## Réglementation DORA, NIS2

Les règlements **NIS2 (Network and Information Security Directive 2)** et **DORA (Digital Operational Resilience Act)** visent à renforcer la sécurité et la résilience numérique des entreprises en Europe, avec un impact particulier sur le secteur financier.

---

### **1. NIS2 – Directive sur la Sécurité des Réseaux et de l'Information (2022/2555)**  

La **directive NIS2**, entrée en vigueur en janvier 2023, remplace **NIS1 (2016)** et élargit le champ d'application en imposant des exigences renforcées en cybersécurité.

**Grandes Lignes pour le Secteur Financier**
1. **Extension du Périmètre**  
   - Les banques, infrastructures de marché financier et compagnies d’assurance sont explicitement couvertes.  
   - Inclusion des services numériques (fintech, PSP - Payment Service Providers).  

2. **Renforcement des Exigences en Cybersécurité**  
   - Mise en place de mesures de **gestion des risques** en cybersécurité (prévention, détection, réponse, récupération).  
   - Adoption d’une approche basée sur **le risque** et la **sécurité par conception**.  
   - Tests de cybersécurité réguliers (audit, pentests).  

3. **Obligation de Notification des Incidents**  
   - Signalement rapide des cyberincidents majeurs :  
     - **24h** : alerte initiale aux autorités  
     - **72h** : rapport détaillé  
     - **1 mois** : rapport final avec remédiation  

4. **Sanctions Renforcées**  
   - Amendes pouvant atteindre **10 millions d’euros** ou **2% du CA mondial**.  

---

### **2. DORA – Digital Operational Resilience Act (Règlement 2022/2554)**  

Le **règlement DORA**, adopté en janvier 2023 et applicable à partir de **janvier 2025**, vise à garantir la résilience opérationnelle des entités financières face aux cyberattaques.

**Grandes Lignes pour le Secteur Financier**
1. **Gouvernance et Gestion des Risques Numériques**  
   - Création d’une stratégie de **résilience numérique** supervisée au niveau du **Conseil d’Administration**.  
   - Évaluation régulière des **risques technologiques tiers** (fournisseurs de cloud, SaaS, services financiers numériques).  

2. **Exigences en Cybersécurité et Tests de Résilience**  
   - Sécurité des infrastructures IT, cryptographie, contrôle des accès stricts.  
   - Tests avancés (Red Teaming, simulations d’attaques).  
   - Plan de **continuité des activités et reprise après sinistre (BCP/DRP)**.  

3. **Surveillance des Fournisseurs de Services TIC**  
   - Établissements financiers responsables des risques liés aux **prestataires IT externes**.  
   - Audit régulier des fournisseurs cloud et FinTech.  

4. **Gestion des Incidents et Reporting**  
   - Obligation de signaler **tout incident majeur** ayant un impact opérationnel significatif.  
   - Évaluation des impacts sur les clients et le marché.  

5. **Sanctions et Conformité**  
   - Sanctions financières et **risque d’exclusion du marché européen** pour les non-conformes.  

---

### **Impact sur le Développement d'Applications Sécurisées**  
Ces réglementations imposent aux entreprises du secteur financier de **renforcer leur approche DevSecOps** et de sécuriser leurs applications dès leur conception.

**1. Sécurité dès la Conception (Security by Design)**  
- Intégration des **meilleures pratiques** dès le développement (OWASP, chiffrement, IAM).  
- Audit de code et analyse de vulnérabilités continue (SonarQube, SAST, DAST).  
- Mise en place de **Zero Trust Architecture**.  

**2. Surveillance et Détection des Menaces**  
- Intégration d’**outils SIEM et SOC** pour détecter les incidents en temps réel.  
- Automatisation des alertes via des solutions comme **Prometheus + Grafana + Alertmanager**.  

**3. Tests de Résilience et Pentesting**  
- Automatisation des tests de charge et d’intrusion (Chaos Engineering).  
- Tests réguliers avec des **Red Teams** pour évaluer la robustesse des systèmes.  

**4. Gestion des Risques Fournisseurs**  
- Contrôle et audits renforcés des **prestataires IT (AWS, GCP, Azure, SaaS)**.  
- Gestion des accès stricts (IAM, MFA, RBAC).  