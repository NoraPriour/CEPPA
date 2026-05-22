# CEPPA – Plateforme communautaire accessible

Ce projet est réalisé dans le cadre de mon projet de fin d’études en école de développement web.
L’association CEPPA (Collectif d’Entraide fait Par et Pour les Autistes), basée à Nantes, ne dispose pas actuellement d’un site web centralisé. Les outils de communication sont dispersés (forum, Facebook, Google Drive, Framasoft), ce qui complexifie l’organisation des membres.
L’objectif de ce projet est de concevoir une plateforme web centralisée, accessible et sécurisée, adaptée aux besoins des adhérents


La première version du projet se concentre sur :

- Authentification sécurisée des utilisateurs
- Gestion des rôles (admin / membre)
- Forum permettant :
  - Consultation des posts
  - Création de posts
  - Ajout de commentaires
  - Suppression des contenus selon permissions


Stack technique:

- Frontend : 
- Backend :
- Base de données : PostgreSQL / MySQL
- Authentification :
- Styling : CSS / (Tailwind)


Architecture:

- Gestion des utilisateurs
- Gestion des rôles
- Gestion des posts
- Gestion des commentaires
- Protection des routes
- Structure REST API

Authentification:

- Keycloak est la source de verite pour la connexion, les mots de passe, les sessions et les roles.
- La base applicative CEPPA ne stocke pas les mots de passe des membres.
- La table users sert de profil metier et peut etre reliee a Keycloak avec la colonne keycloak_id.
- La creation d'un membre depuis l'espace membre cree aussi le compte Keycloak via l'Admin API.
- Pour cela, Keycloak doit contenir un client confidentiel `ceppa-back-admin` avec les service accounts actives et le role `realm-management/manage-users`.
- Le secret du client backend est fourni a Spring Boot avec la variable d'environnement `KEYCLOAK_ADMIN_CLIENT_SECRET`.


Accessibilité:

Le projet accorde une attention particulière à l’accessibilité, notamment pour un public concerné par les Troubles du Spectre Autistique (TSA).

Principes appliqués :

- Interface épurée et prévisible
- Contraste respectant les normes WCAG
- Navigation cohérente
- Absence d’animations perturbantes
- Structure sémantique HTML
