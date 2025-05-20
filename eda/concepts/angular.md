## Angular

framework pour le développement d'applications web front-end dynamiques basé sur typescript avec une approche modulaire.

2. **Installation et Configuration :**

- Utilisation de l'Angular CLI pour créer et gérer des projets.
- Structure d'un projet Angular.

3. **Modules :**

- Organisation de l'application en modules fonctionnels.
- Module racine et modules fonctionnels.

4. **Composants :**

- Création et utilisation de composants pour construire l'interface utilisateur.
- Communication entre composants parent-enfant.

5. **Templates et Data Binding :**

- Utilisation des templates HTML avec Angular.
- Data binding unidirectionnel et bidirectionnel.

6. **Directives :**
   permettent d’ajouter ou modifier le comportement aux éléments du DOM :

   **Directives structurelles :**
   modifient la structure du DOM en ajoutant ou en retirant des éléments.
   ex: ngIf, ngFor, ngSwitch

   **Directives d’attribut :**
   Permettent de modifier l’apparence ou le comportement d’un élément.
   Ex: ngStyle, ngClass

   **Directives personnalisées :**
   directive pour encapsuler un comportement réutilisable dans l'application.

7. **Services et Injection de Dépendances :**

- Création de services pour encapsuler la logique métier ou les appels HTTP.
- Utilisation de l'injection de dépendances pour fournir les services aux composants.

8. **Routing et Navigation :**

   - un mechanisme de routing qui permet d’associé des pages avec des routes, et aussi de naviguer entre les différentes pages.
   - protection des routes avec des guards.

9. **Formulaires :**
   La gestion des formulaires 2 approches:

   **Template-driven Forms :**

   Basés sur le template HTML avec des directives spécifiques (ngModel).
   Simples à mettre en place pour des formulaires peu complexes, mais moins flexibles pour les validations avancées.

   **Reactive Forms :**

   Basés sur une approche programmatique, en définissant des modèles de formulaire dans le code TypeScript.
   Offrent une meilleure évolutivité et un contrôle plus précis (validation, dynamique, tests unitaires facilités).

10. **Pipes :**

servent à transformer des données directement dans les templates sans modifier le modèle :

Transformation de la casse (uppercase, lowercase), formatage de dates ou de nombres, filtrage de données, etc.

Pipes personnalisés :
Tu peux créer tes propres pipes pour des transformations spécifiques à ton application.

11. **HTTP Client :**

    - Communication avec des API RESTful.
    - Gestion des requêtes HTTP et des observables avec RxJS.

12. **Gestion de l'État avec NgRx/Redux :**

13. **Lazy loading:**

Technique d’optimisation qui vise à charger une ressource seulement qu’on en a besoin, cela améliore la performance de notre application. (chargement)

14. **Partage de données entre composants:**

    **@Input et @Output:**
    **@Input** permet à un composant enfant de recevoir des données du composant parent.
    **@Output** avec EventEmitter permet à l’enfant d’émettre des événements vers le parent.

    **Services et Injection de Dépendances :**
    Utiliser un service pour stocker et partager des données ou gérer la communication entre composants non liés hiérarchiquement.
    Les services peuvent également être dotés d’Observables pour notifier les changements en temps réel.

15. **State Management :**
    Pour des applications plus complexes, des bibliothèques comme NgRx ou Akita permettent de centraliser et de gérer l’état de l’application de façon prédictive.

16. **Cycle de vie d’un composant Angular:**

plusieurs hooks (méthodes) qui te permettent d’intervenir à différents moments du cycle de vie d’un composant :

- **ngOnChanges**  
  Se déclenche dès qu’une ou plusieurs propriétés d’entrée (@Input) changent. C’est utile pour réagir aux modifications des données reçues depuis un composant parent.

- **ngOnInit**  
  Appelé une fois après le premier traitement des propriétés liées. C’est le bon endroit pour initialiser les données ou lancer des appels à des services.

- **ngDoCheck**  
  Permet de détecter et de réagir à des changements non détectés par Angular (custom change detection).

- **ngAfterContentInit** et **ngAfterContentChecked**  
  Concernent l’insertion du contenu externe (via <ng-content>). Le premier s’exécute après la projection du contenu, et le second à chaque vérification.

- **ngAfterViewInit** et **ngAfterViewChecked**  
  Interviennent après l’initialisation (et la vérification) de la vue du composant et de ses sous-composants. Très utile pour interagir avec les éléments du DOM après leur rendu.

- **ngOnDestroy**  
  Se déclenche juste avant la destruction du composant. C’est le moment idéal pour nettoyer (désabonnement d’Observables, détachement d’événements, etc.).

---

16. **Types de Rendering:**

- **Client-Side Rendering (CSR) :**

  - Le navigateur charge une application JavaScript qui se charge de rendre le contenu.
  - **Avantages :** Interactivité riche, moins de charge sur le serveur, expérience utilisateur fluide après le chargement initial.
  - **Limites :** Temps de chargement initial plus long, moins optimal pour le SEO si l’application n’est pas pré-rendue.

- **Server-Side Rendering (SSR) avec Angular Universal :**

  - Le rendu initial se fait sur le serveur, ce qui envoie une page HTML complète au client.
  - **Avantages :** Meilleur SEO, temps de chargement initial souvent plus rapide, meilleure expérience pour les utilisateurs sur des connexions lentes.
  - **Limites :** Complexité accrue dans la configuration et le déploiement, charge serveur potentiellement plus élevée.

- **Prerendering (Static Site Generation) :**
  - Le contenu est généré statiquement à la construction et servi comme des pages HTML statiques.
  - **Avantages :** Très performant pour des contenus rarement mis à jour, excellent SEO.
  - **Limites :** Moins adapté pour des applications nécessitant une forte interactivité ou des données dynamiques.

---

**Q&A Inteview:**
