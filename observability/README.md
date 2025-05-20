# Observability


## 🧠 1. **Comment choisir les métriques importantes ?**

### 🎯 Objectif : Identifier les indicateurs de santé, performance, et erreurs de chaque composant.

### 📌 **3 types de métriques clés :**

| Type              | But                                             | Exemples                          |
|-------------------|--------------------------------------------------|-----------------------------------|
| 🔧 **Health**     | Est-ce que le service est vivant ?              | `up`, `process_cpu_usage`, `pg_up` |
| 🏃‍♂️ **Performance** | Est-ce que ça répond vite et bien ?             | `http_request_duration_seconds`, `pg_stat_activity` |
| 🔥 **Errors**      | Est-ce qu’il y a des problèmes ?                | `5xx`, `logback_events_total{level="error"}` |

### ✅ **Critères pour sélectionner une métrique importante :**

1. **Est-ce qu’elle t’aiderait à détecter un incident avant l’utilisateur ?**
2. **Est-ce qu’elle te permettrait de comprendre un incident plus rapidement ?**
3. **Est-ce qu’elle reflète un SLA (latence, uptime) ?**
4. **Est-ce qu’elle suit une ressource limitée (CPU, RAM, connexions) ?**
5. **Est-elle corrélée à un événement business important ?**

### 🧪 Exemples par service :

- **Java App** :
  - Temps de réponse (latency P95)
  - Erreurs HTTP
  - RAM JVM
  - CPU

- **PostgreSQL** :
  - Connexions ouvertes
  - Transactions/s
  - Locks / deadlocks

- **Redis** :
  - Mémoire utilisée
  - Clés éjectées
  - Clients connectés

- **RabbitMQ** :
  - Messages non consommés
  - Nombre de consommateurs

---

## ⏱ 2. **Comment choisir la période de scrape ?**

### 🎯 But : Équilibrer fraîcheur des données vs charge réseau et stockage


| Type de service   | Fréquence recommandée   |
|-------------------|--------------------------|
| App critique (Java) | `15s` à `30s`            |
| DB / Cache / Broker | `30s` à `60s`            |
| Services peu dynamiques | `60s` ou +             |

### ✅ Comment décider ?

- Si une alerte doit déclencher en **moins d’une minute**, scrape à 15–30s
- Pour logs ou tendances à long terme : 60s suffit
- Scrape fréquent = + stockage = à compresser avec retention + compaction

**⚠️ Conseil** : Mets `scrape_interval` par job dans Prometheus, pas globalement

---

## 📄 3. **Comment choisir les logs importants ?**

### 🎯 Objectif : Identifier les événements significatifs à suivre / alerter

1. **Indiquent une erreur** : `level=error`, `exception`, `panic`, etc.
2. **Montrent une dégradation** : `warn`, timeouts, retries
3. **Révèlent une action critique** : `user_deleted`, `payment_failed`
4. **Sont fréquents** : +500 fois dans 1 min = problème silencieux
5. **Contiennent des mots-clés clés** : "connection refused", "OOM", "deadlock", etc.

### ✅ Filtrer avec **LogQL (Loki)** :

```logql
{job="spring-app", level="error"} |= "Exception"
{job="postgres", level="warn"} |= "deadlock"
```

---

## 🔭 4. **Concepts clés pour une observabilité "production-ready"**

### 🌐 1. **Les 3 piliers de l'observabilité**

| Pilier      | Objectif                           | Exemple outil |
|-------------|------------------------------------|---------------|
| Metrics     | Comprendre **quoi** ne va pas      | Prometheus    |
| Logs        | Comprendre **pourquoi** ça ne va pas| Loki          |
| Traces (bonus) | Suivre le parcours d’une requête  | Jaeger, OpenTelemetry |

---

### 🔄 2. **Corrélation entre logs et métriques**

> Exemple : Un pic de `5xx` dans les métriques → Aller voir les logs d’erreurs de la même minute dans Loki.

Grafana permet ça avec les **"Explore correlations"** sur les dashboards : clique sur une courbe, puis "View logs for this time".

---

### 📊 4. **Dashboards pertinents**
- Un par composant/service
- Un global "SRE view" ou "health overview"
- Regroupe :
  - Latence
  - Erreurs
  - Ressources système
  - Logs critiques

---

## ✅ Récapitulatif

| Élément                             | À faire |
|------------------------------------|--------|
| **Choix métriques**                | Pertinentes pour la santé, perfs, erreurs |
| **Scrape interval**                | Selon criticité (15–60s) |
| **Choix logs importants**          | Erreurs, warnings, actions critiques |
| **Concepts clés observabilité**    | 3 piliers (Metrics, Logs, Traces), alerting fiable, dashboards ciblés |

************************

## Alertes


### 🧱 1. INFRASTRUCTURE (Serveur / OS / Docker / VM / Kubernetes)

| 🔍 **Métrique** | 🔔 **Pourquoi c’est critique** | ✅ **Alerte conseillée** |
|----------------|-------------------------------|--------------------------|
| `CPU usage` | Surcharge = lenteurs, crashs | CPU > 80% pendant 2-5 min |
| `RAM usage` | Si elle explose → OOM kill | RAM > 80% |
| `Disk usage` | Disque plein = logs perdus, DB en panne | Disque > 90% |
| `Disk IO` | Trop d’écritures = performances dégradées | IO latency > 100ms |
| `Load average` | Charge système générale | Load > nb CPU cores |
| `Network errors` | Paquets perdus = microcoupures | > 1% d’erreurs réseau |
| `Container restarts` | Crash loop, bugs fréquents | > 3 redémarrages/5 min |
| `File descriptors` | FDs épuisés = blocages d’apps | > 80% de limite max |

📦 Exporteurs utiles :  
- `node-exporter`, `cadvisor`, `docker-exporter`, `kubelet`, `blackbox-exporter`

---

### 🧠 2. APPLICATIONS (API / Frontend / Workers / Services)

| 🔍 **Métrique** | 🔔 **Pourquoi c’est critique** | ✅ **Alerte conseillée** |
|----------------|-------------------------------|--------------------------|
| `HTTP error rate` (4xx, 5xx) | Bugs / crashs utilisateurs | > 5% de 5xx sur 5 min |
| `Request duration` | Lenteurs = mauvaise UX | p95 > 1s |
| `Request rate` | Pics anormaux = attaque ? | Augmentation soudaine |
| `Timeouts` | Comms internes HS (DB, Redis…) | > X timeouts/min |
| `Queue length` | Système engorgé (jobs async) | File > 100 jobs |
| `DB error rate` | Problèmes d'accès base | > 1% d’erreurs SQL |
| `Cache hit rate` | Trop de cache misses = lenteur | Hit rate < 80% |

📦 Clients Prometheus :  
- Express / Fastify / NestJS: `prom-client`  
- Django / Flask: `prometheus-client`  
- Spring Boot: `/actuator/prometheus`

---

### 📜 3. LOGS STRUCTURÉS (via Loki + Promtail)

| 🔍 **Type de log** | 🔔 **Pourquoi c’est critique** | ✅ **LogQL à alerter** |
|-------------------|-------------------------------|------------------------|
| `Error`, `Exception`, `Fatal` | Stack traces = crashs | `count_over_time({job="app"} |= "error" [1m]) > 5` |
| `OutOfMemory`, `OOM` | Processus killé | `{job="app"} |= "OutOfMemory"` |
| `Database error`, `SQL error` | Connexion / requêtes invalides | `|= "SQLSTATE"` |
| `Unauthorized`, `403`, `401` | Tentatives d'accès interdit | `|= "401"` |
| `Panic`, `Segfault` | Crash niveau Go / C++ | `|= "panic"` |
| `Timeout` | Perte de dépendances | `|= "timeout"` |
| `Too many requests` | Débordement, throttling | `|= "429"` |

---

### 🧑‍💼 4. BUSINESS / FONCTIONNEL (optionnel mais 🧠)

| 🔍 **Métrique** | 🔔 **Pourquoi c’est utile** | ✅ **Alerte** |
|----------------|-----------------------------|--------------|
| Nombre de commandes par min | Chute = bug fonctionnel | < X commandes sur 10 min |
| Paiements échoués | Problème Stripe / PayPal | > 5% d’échecs |
| Inscription utilisateurs | Trop peu = bug ou crash | < 1/min |

---

### 🔔 Quelles alertes sont **essentielles en prod** ?

| ✅ Alerte | 💥 Impact | 🛠 Source |
|----------|-----------|----------|
| CPU > 90% | Surcharge | Prometheus |
| RAM > 90% | Crashs / OOM | Prometheus |
| Disk > 90% | DB/logs figés | Prometheus |
| HTTP 5xx > 5% | Service KO | Prometheus |
| `error` logs x5 en 1min | Crash / bug code | Loki |
| Container restart loop | Crash app | Prometheus |
| DB down / connection refused | Backend KO | Logs + metrics |
| Load average > cores | Saturation | Prometheus |

---

### 🧰 Dashboard Grafana à mettre en place

| Dashboard | Contenu |
|----------|---------|
| 🔧 Infra | CPU, RAM, Disk, Restarts par container |
| 🌐 API | HTTP rate, error rate, latence |
| 💾 DB | Latence requêtes, erreurs, connexions |
| 🔐 Sécurité | Tentatives échouées, logs 403/401 |
| 📜 Logs | Vue filtrée `error`, `panic`, `fatal` |
| 📊 Alertes | Tableau de toutes les alertes en cours |


---

## Logging / Monitoring (Production-ready)

### ✅ 1. **Centralisation multi-services / multi-containers**
**Quoi ?**
Assure-toi que **tous tes services**, microservices, conteneurs, et outils loguent vers Loki via Promtail ou un autre agent (ex: Fluent Bit).

**Pourquoi**
> Pour voir toutes les erreurs, performances et événements critiques au même endroit.

---

### ✅ 2. **Conservation & rotation des logs**
**Quoi ?**
Configurer Loki pour stocker les logs plus longtemps (ex: 7, 15 ou 30 jours) + activer la **rotation automatique** pour éviter que ça explose en taille.

**Pourquoi**
> Pour conserver l’historique utile, respecter les normes RGPD/ISO, et éviter que Loki consomme tout l’espace disque.

---

### ✅ 3. **Sécurité & accès**
**Quoi ?**
- Authentification sur Grafana (SSO, OAuth, etc.)
- Lecture seule pour certains utilisateurs
- Sécuriser les endpoints Loki, Prometheus, Alertmanager avec du HTTPS et auth (via NGINX ou Traefik par ex.)

**Pourquoi**
> Pour protéger l’accès aux logs sensibles en prod, éviter les fuites ou les erreurs humaines.

---

### ✅ 4. **Dashboards par domaine**
**Quoi ?**
Créer des **dashboards ciblés** :
- 🔧 Backend (latence, erreurs, logs `ERROR`)
- 🎨 Frontend (erreurs JS, requêtes API)
- 🛢️ Database (temps de réponse, logs de requêtes lentes)
- 🚦 Infra (Docker, CPU/RAM, réseau)

**Pourquoi**
> Pour isoler rapidement les bugs ou les lenteurs, et faire des analyses efficaces.

---

### ✅ 5. **Alertes fines & auto-résolution**
**Quoi ?**
- Créer des alertes ciblées par microservice
- Ajouter des labels (env, service, team)
- Utiliser l’**auto-resolve** pour éviter les spams d’alertes
- Définir des **schedules** (alerte seulement en heures ouvrées, etc.)

**Pourquoi**
> Pour ne pas surcharger les équipes et garder des alertes vraiment utiles.

---

### ✅ 6. **Backups & Haute Disponibilité**
**Quoi ?**
- Sauvegarder les données Prometheus et Loki
- Répliquer les composants critiques
- Optionnel : utiliser Loki en mode **distributed** (avec Cortex/Tempo pour les traces)

**Pourquoi**
> Pour ne **pas perdre les données** si crash et garantir un **SLA élevé**.

---

### ✅ 7. **Traces (bonus : observabilité complète 🔁)**
**Quoi ?**
Ajouter **Grafana Tempo ou Jaeger** pour suivre une requête de bout en bout.

**Pourquoi**
> Tu vois non seulement quand une erreur s’est produite, mais **exactement où dans le code elle s’est produite.**
