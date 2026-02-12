# 🏨 Hotel Reservation System

> Application Spring Boot pour la gestion des réservations d'hôtel avec authentification JWT - **Entièrement dockerisée**

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue.svg)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## 📋 Table des matières

- [Aperçu](#-aperçu)
- [Fonctionnalités](#-fonctionnalités)
- [Technologies utilisées](#-technologies-utilisées)
- [Prérequis](#-prérequis)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Démarrage](#-démarrage)
- [API Documentation](#-api-documentation)
- [Structure du projet](#-structure-du-projet)
- [Architecture](#️-architecture)
- [Docker](#-docker)
- [Sécurité](#-sécurité)
- [Tests](#-tests)
- [Contribution](#-contribution)
- [Licence](#-licence)

---

## 🎯 Aperçu

Cette application est un système de gestion de réservations d'hôtel développé avec Spring Boot. Elle permet de gérer les utilisateurs, les chambres, les réservations et offre une authentification sécurisée basée sur JWT (JSON Web Tokens).

### Fonctionnalités principales

- ✅ **Authentification JWT** : Système d'authentification sécurisé avec tokens
- ✅ **Gestion des utilisateurs** : Inscription, connexion, gestion de profil
- ✅ **Gestion des chambres** : CRUD pour les chambres d'hôtel
- ✅ **Gestion des réservations** : Création et suivi des réservations
- ✅ **Rôles utilisateurs** : USER, ADMIN, RESEPTION avec permissions différenciées
- ✅ **API REST** : Endpoints RESTful bien structurés
- ✅ **Documentation Swagger/OpenAPI** : Documentation interactive de l'API avec interface Swagger UI
- ✅ **Sécurité** : Spring Security avec filtres JWT personnalisés

---

## 🛠 Technologies utilisées

- **Backend** : Spring Boot 4.0.1
- **Langage** : Java 17
- **Base de données** : MySQL 8.0
- **ORM** : Spring Data JPA / Hibernate
- **Sécurité** : Spring Security + JWT (jjwt 0.12.6)
- **Documentation API** : SpringDoc OpenAPI 2.8.6 (Swagger UI)
- **Build Tool** : Maven
- **Validation** : Jakarta Validation
- **Outils** : Lombok, Spring DevTools

---

## 📦 Prérequis

### 🐳 Méthode recommandée : Docker (Tout est dockerisé)

L'application est **entièrement dockerisée** et peut être lancée avec Docker Compose. C'est la méthode la plus simple et recommandée.

**Prérequis uniquement :**
- **Docker** ([Installation Guide](https://docs.docker.com/get-docker/))
- **Docker Compose** ([Installation Guide](https://docs.docker.com/compose/install/))

> 💡 **Note** : Avec Docker, vous n'avez pas besoin d'installer Java, Maven ou MySQL séparément - tout est conteneurisé !

### 💻 Méthode alternative : Installation locale

Si vous souhaitez exécuter l'application localement sans Docker, vous aurez besoin de :

- **Java 17+** ([Télécharger](https://www.oracle.com/java/technologies/downloads/))
- **Maven 3.6+** (ou utilisez le wrapper `./mvnw` inclus)
- **MySQL 8.0+** ([Télécharger](https://www.mysql.com/downloads/))

---

## 🚀 Installation et Démarrage

### 🐳 Méthode recommandée : Avec Docker Compose (Tout est dockerisé)

L'application utilise **Docker Compose** pour orchestrer l'ensemble de la stack : l'application Spring Boot et la base de données MySQL. Tout est conteneurisé et prêt à l'emploi !

#### 1. Cloner le dépôt

```bash
git clone https://github.com/mouloudcheikhne/Hotel-reservation-backend.git
cd Hotel-reservation-backend
```

#### 2. Lancer l'application avec Docker Compose

```bash
docker-compose up --build
```

C'est tout ! 🎉 Docker Compose va :
- ✅ Construire l'image Docker de l'application Spring Boot
- ✅ Démarrer le conteneur MySQL avec la base de données pré-configurée
- ✅ Démarrer le conteneur Spring Boot qui se connecte automatiquement à MySQL
- ✅ Créer les volumes persistants pour les données MySQL et le cache Maven

L'application sera accessible sur : **http://localhost:8093**

#### 3. Vérifier que les services sont en cours d'exécution

```bash
docker-compose ps
```

Vous devriez voir deux services :
- `mysql_dev` (MySQL) - Port 3307
- `spring_dev` (Spring Boot) - Port 8093

#### 4. Voir les logs

```bash
# Tous les logs
docker-compose logs -f

# Logs de l'application uniquement
docker-compose logs -f app

# Logs de MySQL uniquement
docker-compose logs -f mysql
```

#### 5. Arrêter les services

```bash
# Arrêter les conteneurs
docker-compose down

# Arrêter et supprimer les volumes (⚠️ supprime les données de la base)
docker-compose down -v
```

---

### 💻 Méthode alternative : Installation locale (sans Docker)

Si vous préférez exécuter l'application localement sans Docker :

#### 1. Cloner le dépôt

```bash
git clone https://github.com/mouloudcheikhne/Hotel-reservation-backend.git
cd Hotel-reservation-backend
```

#### 2. Configuration de la base de données

Créez une base de données MySQL nommée `hotel_reservation` :

```sql
CREATE DATABASE hotel_reservation;
```

#### 3. Modifier la configuration

Éditez `src/main/resources/application.properties` pour pointer vers votre MySQL local :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hotel_reservation
spring.datasource.username=root
spring.datasource.password=votre_mot_de_passe
```

#### 4. Compiler et lancer

```bash
./mvnw clean package
./mvnw spring-boot:run
```

---

## ⚙️ Configuration

### 🐳 Configuration Docker (par défaut)

Lorsque l'application est lancée avec Docker Compose, la configuration est automatique :

- **MySQL** : Service `mysql` sur le port 3306 (interne au réseau Docker)
- **Spring Boot** : Se connecte à MySQL via `jdbc:mysql://mysql:3306/hotel_reservation`
- **Variables d'environnement** : Configurées dans `docker-compose.yml`

Les paramètres dans `docker-compose.yml` :
```yaml
environment:
  SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/hotel_reservation?useSSL=false&allowPublicKeyRetrieval=true
  SPRING_DATASOURCE_USERNAME: root
  SPRING_DATASOURCE_PASSWORD: mouloud1234
```

> 💡 **Note** : Dans Docker, `mysql` est le nom du service (hostname interne), pas `localhost`.

### 💻 Configuration locale (sans Docker)

Si vous exécutez l'application localement, modifiez `src/main/resources/application.properties` :

```properties
server.port=8093

# MySQL Database (local)
spring.datasource.url=jdbc:mysql://localhost:3306/hotel_reservation
spring.datasource.username=root
spring.datasource.password=votre_mot_de_passe
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

### Variables d'environnement importantes

- `server.port` : Port du serveur (défaut: 8093)
- `spring.datasource.url` : URL de connexion à la base de données
  - Docker : `jdbc:mysql://mysql:3306/hotel_reservation`
  - Local : `jdbc:mysql://localhost:3306/hotel_reservation`
- `spring.jpa.hibernate.ddl-auto` : Mode de gestion du schéma (update/create-drop/none)

---

## ▶️ Démarrage

### 🐳 Démarrage avec Docker Compose (Recommandé)

L'application étant entièrement dockerisée, c'est la méthode la plus simple :

```bash
# Démarrer en mode développement (avec logs)
docker-compose up

# Démarrer en mode détaché (en arrière-plan)
docker-compose up -d

# Reconstruire les images et démarrer
docker-compose up --build
```

L'application sera accessible sur : **http://localhost:8093**

### 💻 Démarrage local (sans Docker)

#### Mode développement

```bash
./mvnw spring-boot:run
```

#### Mode production

```bash
./mvnw clean package
java -jar target/hotelreservaion-0.0.1-SNAPSHOT.jar
```

---

## 📚 API Documentation

### 📖 Documentation Swagger/OpenAPI

La documentation interactive de l'API est **déjà configurée et disponible** grâce à Swagger/OpenAPI. Vous pouvez accéder à l'interface Swagger UI directement dans votre navigateur.

> ℹ️ **Documentation détaillée** : des guides Markdown complémentaires sont disponibles dans le dossier `api_docs/` (voir `api_docs/README.md`).

#### URLs d'accès :

| Documentation | URL |
|--------------|-----|
| **Swagger UI** (Interface interactive) | **http://localhost:8093/swagger-ui.html** |
| **Swagger UI** (Alternative) | **http://localhost:8093/swagger-ui/index.html** |
| **API Docs JSON** (Documentation brute) | **http://localhost:8093/v3/api-docs** |

#### Fonctionnalités Swagger :

✅ **Interface interactive** : Testez directement les endpoints depuis votre navigateur  
✅ **Documentation automatique** : Tous les endpoints sont documentés automatiquement  
✅ **Schémas de requêtes/réponses** : Visualisez les DTOs et modèles  
✅ **Authentification intégrée** : Vous pouvez ajouter votre token JWT pour tester les endpoints protégés  
✅ **Accès public** : Swagger est accessible sans authentification  

> 💡 **Note** : Après avoir démarré l'application avec `docker-compose up`, ouvrez votre navigateur et allez sur **http://localhost:8093/swagger-ui.html** pour voir la documentation interactive.

### Base URL

```
http://localhost:8093
```

### Endpoints publics

#### 1. Health-check

```http
GET /api/auth/test
```

**Exemple :**

```bash
curl http://localhost:8093/api/auth/test
```

#### 2. Inscription

```http
POST /api/auth/register
Content-Type: application/json

{
  "nom": "Doe",
  "prenom": "Jane",
  "email": "jane.doe@example.com",
  "password": "StrongPassword123"
}
```

**Exemple :**

```bash
curl -X POST http://localhost:8093/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Doe",
    "prenom": "Jane",
    "email": "jane.doe@example.com",
    "password": "StrongPassword123"
  }'
```

#### 3. Connexion

```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "jane.doe@example.com",
  "password": "StrongPassword123"
}
```

**Exemple :**

```bash
curl -X POST http://localhost:8093/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "jane.doe@example.com",
    "password": "StrongPassword123"
  }'
```

**Réponse :**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "nom": "Doe",
  "prenom": "Jane",
  "email": "jane.doe@example.com",
  "role": "ROLE_USER"
}
```

#### 4. Consultation des chambres publiques

```http
GET /api/rooms
```

**Exemple :**

```bash
curl http://localhost:8093/api/rooms
```

#### 5. Dates réservées d'une chambre

```http
GET /api/rooms/dates-reserved/{roomId}
```

**Exemple :**

```bash
curl http://localhost:8093/api/rooms/dates-reserved/12
```

### Endpoints protégés

Tous les endpoints protégés nécessitent un token JWT dans l'en-tête `Authorization`.

#### Exemple : Récupérer toutes les réservations (rôle `ROLE_USER`)

```http
GET /api/client/get-all-bookings
Authorization: Bearer <TOKEN>
```

```bash
curl -H "Authorization: Bearer <TOKEN>" \
  http://localhost:8093/api/client/get-all-bookings
```

### Contrôleurs disponibles

| Contrôleur                          | Description                            | Rôle requis |
| ----------------------------------- | -------------------------------------- | ----------- |
| `AuthController`                    | Authentification (login, register)     | Public      |
| `PublicController`                  | Consultation des chambres              | Public      |
| `ClientController`                  | Actions client (réservations)          | USER        |
| `ReceptionController`               | Gestion réception (changement statut)  | RESEPTION   |
| `admin/RoomController`              | Gestion des chambres (CRUD)            | ADMIN       |
| `admin/UserController`              | Gestion des utilisateurs (CRUD)        | ADMIN       |

---

## 📁 Structure du projet

```
hotelreservaion/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── hotel/example/hotelreservaion/
│   │   │       ├── HotelreservaionApplication.java    # Point d'entrée
│   │   │       ├── config/                             # Configuration
│   │   │       │   ├── SecurityConfig.java             # Configuration Spring Security
│   │   │       │   ├── WebConfig.java                  # Configuration Web (CORS, etc.)
│   │   │       │   └── OpenApiConfig.java              # Configuration Swagger/OpenAPI
│   │   │       ├── controller/                         # Contrôleurs REST
│   │   │       │   ├── admin/                          # Contrôleurs Admin
│   │   │       │   │   ├── RoomController.java         # Gestion des chambres (Admin)
│   │   │       │   │   └── UserController.java         # Gestion des utilisateurs (Admin)
│   │   │       │   ├── AuthController.java             # Authentification (Public)
│   │   │       │   ├── ClientController.java           # Actions client (USER)
│   │   │       │   ├── PublicController.java           # Endpoints publics
│   │   │       │   └── ReceptionController.java        # Gestion réception (RESEPTION)
│   │   │       ├── service/                            # Logique métier
│   │   │       │   ├── admin/                          # Services Admin
│   │   │       │   │   ├── RoomService.java            # Service gestion chambres
│   │   │       │   │   └── UserService.java            # Service gestion utilisateurs
│   │   │       │   ├── AuthService.java                # Service authentification
│   │   │       │   ├── ClientService.java              # Service client
│   │   │       │   ├── MyUserDetailsService.java       # UserDetails pour Spring Security
│   │   │       │   └── ReseptionService.java           # Service réception
│   │   │       ├── repository/                         # Interfaces JPA
│   │   │       │   ├── BookingRepo.java                # Repository réservations
│   │   │       │   ├── RoomRepo.java                   # Repository chambres
│   │   │       │   └── UserReposiory.java              # Repository utilisateurs
│   │   │       ├── model/                              # Entités JPA
│   │   │       │   ├── User.java                       # Entité utilisateur
│   │   │       │   ├── Room.java                       # Entité chambre
│   │   │       │   ├── Booking.java                    # Entité réservation
│   │   │       │   ├── BookingStatus.java              # Enum statut réservation
│   │   │       │   ├── UserRole.java                   # Enum rôle utilisateur
│   │   │       │   └── CustomUserDetails.java          # UserDetails personnalisé
│   │   │       ├── dto/                                # Data Transfer Objects
│   │   │       │   ├── AddBookingDto.java              # DTO ajout réservation
│   │   │       │   ├── AddRomeRequestDto.java          # DTO ajout chambre
│   │   │       │   ├── AddUserDto.java                 # DTO ajout utilisateur
│   │   │       │   ├── ApiError.java                   # DTO erreur API
│   │   │       │   ├── BookingStatusDto.java           # DTO statut réservation
│   │   │       │   ├── ChangeStatusBookingDto.java     # DTO changement statut
│   │   │       │   ├── LoginDto.java                   # DTO connexion
│   │   │       │   ├── RegesterDto.java                # DTO inscription
│   │   │       │   ├── ResponceLoginDto.java           # DTO réponse connexion
│   │   │       │   ├── ResponceRegesterDtO.java        # DTO réponse inscription
│   │   │       │   ├── UpdateRomeDto.java              # DTO mise à jour chambre
│   │   │       │   └── UpdateUserDto.java              # DTO mise à jour utilisateur
│   │   │       ├── util/                               # Utilitaires
│   │   │       │   ├── JwtUtil.java                    # Gestion JWT
│   │   │       │   └── JwtFilter.java                  # Filtre JWT
│   │   │       ├── exception/                         # Gestion d'erreurs
│   │   │       │   ├── CustomException.java            # Exception personnalisée
│   │   │       │   └── GlobalExceptionHandler.java     # Handler global exceptions
│   │   │       ├── validation/                         # Validation personnalisée
│   │   │       │   ├── ValidBookingStatus.java         # Annotation validation statut
│   │   │       │   └── ValidBookingStatusValidator.java # Validateur statut réservation
│   │   │       └── security/                           # Sécurité (vide ou fichiers futurs)
│   │   └── resources/
│   │       └── application.properties                  # Configuration application
│   └── test/                                            # Tests unitaires
│       └── java/
│           └── hotel/example/hotelreservaion/
│               └── HotelreservaionApplicationTests.java
├── uploads/                                             # Fichiers uploadés
│   └── rooms/                                           # Images des chambres
├── Dockerfile                                           # Image Docker
├── docker-compose.yml                                   # Orchestration Docker
├── pom.xml                                              # Dépendances Maven
└── README.md                                            # Documentation
```

## 🏗️ Architecture

### Vue d'ensemble de l'architecture

L'application suit une **architecture en couches (Layered Architecture)** basée sur le pattern **MVC (Model-View-Controller)** adapté pour une API REST. Cette architecture sépare clairement les responsabilités en différentes couches.

L'ensemble de l'application est **dockerisée** avec Docker Compose, qui orchestre :
- **Service MySQL** : Base de données MySQL 8.0 dans un conteneur Docker
- **Service Spring Boot** : Application Spring Boot dans un conteneur Docker
- **Volumes persistants** : Pour les données MySQL et le cache Maven
- **Réseau Docker** : Communication interne entre les services

### Architecture Docker

```
┌─────────────────────────────────────────────────────────────┐
│                    Docker Compose Stack                      │
│                                                               │
│  ┌─────────────────────────────────────────────────────┐    │
│  │  Service: mysql (mysql:8.0)                         │    │
│  │  ├── Container: mysql_dev                           │    │
│  │  ├── Port: 3307:3306 (host:container)               │    │
│  │  ├── Volume: mysql_data (persistance)               │    │
│  │  └── Network: hotelreservaion_default               │    │
│  └─────────────────────────────────────────────────────┘    │
│                            │                                   │
│                            │ jdbc:mysql://mysql:3306           │
│                            ▼                                   │
│  ┌─────────────────────────────────────────────────────┐    │
│  │  Service: app (Spring Boot)                         │    │
│  │  ├── Container: spring_dev                          │    │
│  │  ├── Build: Dockerfile (eclipse-temurin:17-jdk)     │    │
│  │  ├── Port: 8093:8093 (host:container)               │    │
│  │  ├── Volume: ./:/app/ (code source)                 │    │
│  │  ├── Volume: maven_repo (cache Maven)               │    │
│  │  └── Network: hotelreservaion_default               │    │
│  └─────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────┘
                            │
                            │ http://localhost:8093
                            ▼
                    ┌───────────────┐
                    │   Client      │
                    │  (Browser/    │
                    │   Postman)    │
                    └───────────────┘
```

### Pattern architectural de l'application

```
┌─────────────────────────────────────────────────────────────┐
│                    CLIENT (Frontend/Mobile)                 │
│              HTTP/HTTPS Requests & Responses                 │
└───────────────────────────┬─────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│              COUCHE PRÉSENTATION (Presentation Layer)        │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Security Filter Chain (Spring Security)             │  │
│  │  ├── JwtFilter (Validation JWT)                      │  │
│  │  └── CORS Configuration                               │  │
│  └──────────────────────────────────────────────────────┘  │
│                            │                                 │
│                            ▼                                 │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Controllers (REST Endpoints)                         │  │
│  │  ├── AuthController (Public)                          │  │
│  │  ├── PublicController (Public)                        │  │
│  │  ├── ClientController (USER role)                     │  │
│  │  ├── admin/ (ADMIN role)                              │  │
│  │  │   ├── RoomController (CRUD chambres)               │  │
│  │  │   └── UserController (CRUD utilisateurs)           │  │
│  │  └── ReceptionController (RESEPTION role)             │  │
│  └──────────────────────────────────────────────────────┘  │
└───────────────────────────┬─────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│            COUCHE MÉTIER (Business Logic Layer)             │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Services                                             │  │
│  │  ├── AuthService (Authentification)                   │  │
│  │  ├── MyUserDetailsService (UserDetails pour Security)│  │
│  │  ├── ClientService (Service client)                   │  │
│  │  ├── admin/RoomService (Gestion chambres - Admin)     │  │
│  │  ├── admin/UserService (Gestion utilisateurs - Admin) │  │
│  │  └── ReseptionService (Gestion réservations)         │  │
│  └──────────────────────────────────────────────────────┘  │
└───────────────────────────┬─────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│         COUCHE ACCÈS AUX DONNÉES (Data Access Layer)        │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Repositories (Spring Data JPA)                      │  │
│  │  ├── UserReposiory (Utilisateurs)                    │  │
│  │  ├── RoomRepo (Chambres)                             │  │
│  │  └── BookingRepo (Réservations)                      │  │
│  └──────────────────────────────────────────────────────┘  │
└───────────────────────────┬─────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│              COUCHE PERSISTANCE (Persistence Layer)         │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  JPA/Hibernate (ORM)                                 │  │
│  │  └── Entities: User, Room, Booking                   │  │
│  └──────────────────────────────────────────────────────┘  │
└───────────────────────────┬─────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                    MySQL Database                           │
│  └── Tables: users, room, booking                           │
└─────────────────────────────────────────────────────────────┘
```

### Description détaillée des couches

#### 1. **Couche Présentation (Controllers)**

**Responsabilité** : Recevoir les requêtes HTTP, valider les entrées, et retourner les réponses.

**Composants** :
- **`AuthController`** : Gère l'authentification publique (register, login)
- **`PublicController`** : Endpoints publics accessibles sans authentification
- **`ClientController`** : Endpoints pour les clients authentifiés (réservations)
- **`admin/RoomController`** : CRUD des chambres pour l'admin (ajout, modification, suppression, liste)
- **`admin/UserController`** : Gestion des utilisateurs par l'admin (CRUD)
- **`ReceptionController`** : Gestion des réservations par la réception (changement de statut)

**Caractéristiques** :
- Utilise des DTOs (Data Transfer Objects) pour la validation et le transfert de données
- Gère les uploads de fichiers (images des chambres)
- Retourne des réponses JSON standardisées

#### 2. **Couche Sécurité (Security Layer)**

**Responsabilité** : Authentification et autorisation des requêtes.

**Composants** :
- **`SecurityConfig`** : Configuration Spring Security
  - Définit les règles d'autorisation par rôle
  - Configure CORS
  - Désactive CSRF (stateless avec JWT)
  - Configure le filtre JWT
- **`JwtFilter`** : Filtre personnalisé qui intercepte chaque requête
  - Extrait le token JWT de l'en-tête `Authorization`
  - Valide le token
  - Charge les détails de l'utilisateur
  - Configure le contexte de sécurité Spring
- **`JwtUtil`** : Utilitaires pour générer et valider les tokens JWT
- **`MyUserDetailsService`** : Implémente `UserDetailsService` pour charger les utilisateurs depuis la base de données

**Flux d'authentification** :
```
1. Client → POST /api/auth/login (email, password)
2. AuthService → Vérifie les credentials
3. JwtUtil → Génère un token JWT
4. Réponse → Token retourné au client
5. Client → Inclut token dans header: Authorization: Bearer <token>
6. JwtFilter → Valide token sur chaque requête protégée
7. SecurityContext → Utilisateur authentifié disponible dans le contexte
```

#### 3. **Couche Métier (Services)**

**Responsabilité** : Contient la logique métier de l'application.

**Composants** :
- **`AuthService`** : 
  - Inscription des utilisateurs (hashage du mot de passe avec BCrypt)
  - Authentification (vérification des credentials)
  - Génération des tokens JWT
- **`ClientService`** : 
  - Gestion des réservations par les clients
  - Validation des dates et disponibilité
  - Création et consultation des réservations
- **`admin/UserService`** : 
  - Gestion des utilisateurs par l'admin (CRUD)
  - Validation des données
  - Gestion des rôles
- **`admin/RoomService`** : 
  - Gestion des chambres par l'admin (CRUD)
  - Validation de l'unicité des numéros de chambres
  - Gestion de la disponibilité
  - Upload des images des chambres
- **`ReseptionService`** : 
  - Gestion des réservations par la réception
  - Validation des dates
  - Changement des statuts de réservation

**Caractéristiques** :
- Validation des règles métier
- Gestion des exceptions
- Transformation entre entités et DTOs

#### 4. **Couche Accès aux Données (Repositories)**

**Responsabilité** : Abstraction de l'accès à la base de données.

**Composants** :
- **`UserReposiory`** : Interface Spring Data JPA pour les opérations sur `User`
- **`RoomRepo`** : Interface Spring Data JPA pour les opérations sur `Room`
- **`BookingRepo`** : Interface Spring Data JPA pour les opérations sur `Booking`

**Caractéristiques** :
- Utilise Spring Data JPA pour les opérations CRUD
- Méthodes personnalisées (ex: `existsByRoomNumber`)
- Transactions gérées automatiquement par Spring

#### 5. **Couche Modèle (Entities)**

**Responsabilité** : Représentation des entités métier et mapping ORM.

**Entités principales** :
- **`User`** : 
  - Attributs : id, nom, prenom, email, password, role
  - Relation : `@OneToMany` avec `Booking`
- **`Room`** : 
  - Attributs : id, roomNumber, type, price, available, description, imageUrl
  - Relation : `@OneToMany` avec `Booking`
- **`Booking`** : 
  - Attributs : id, startDate, endDate, status
  - Relations : `@ManyToOne` avec `User` et `Room`

**Relations** :
```
User (1) ────< (N) Booking (N) >───── (1) Room
```

#### 6. **Couche DTO (Data Transfer Objects)**

**Responsabilité** : Transfert de données entre les couches, validation des entrées.

**DTOs principaux** :
- **`LoginDto`** : Données de connexion
- **`RegesterDto`** : Données d'inscription
- **`AddUserDto`** / **`UpdateUserDto`** : Gestion des utilisateurs
- **`AddRomeRequestDto`** / **`UpdateRomeDto`** : Gestion des chambres
- **`ResponceLoginDto`** / **`ResponceRegesterDtO`** : Réponses formatées

### Flux de données (Data Flow)

#### Exemple : Création d'une chambre (Admin)

```
1. Client HTTP Request
   POST /api/admin/rooms/add
   Headers: Authorization: Bearer <token>
   Body: multipart/form-data (roomNumber, type, price, description, image)

2. Security Filter Chain
   ├── JwtFilter valide le token
   ├── Extrait l'email et le rôle
   └── Vérifie que le rôle est ADMIN

3. admin/RoomController.addRoom()
   ├── Valide les paramètres d'entrée
   ├── Gère l'upload de l'image
   └── Appelle admin/RoomService.addRoom()

4. admin/RoomService.addRoom()
   ├── Vérifie l'unicité du numéro de chambre
   ├── Crée l'entité Room
   └── Appelle RoomRepo.save()

5. RoomRepo.save()
   └── Hibernate persiste en base de données

6. Réponse HTTP
   Status: 200 OK
   Body: { "message": "Room added successfully", "room": {...} }
```

### Gestion des exceptions

**`GlobalExceptionHandler`** : Gère toutes les exceptions de l'application de manière centralisée
- Retourne des réponses JSON standardisées
- Gère les erreurs de validation
- Gère les erreurs de sécurité
- Log les erreurs pour le débogage

### Configuration

**Fichiers de configuration** :
- **`application.properties`** : Configuration de l'application (DB, JPA, serveur)
- **`SecurityConfig`** : Configuration Spring Security
- **`WebConfig`** : Configuration web (CORS, etc.)
- **`OpenApiConfig`** : Configuration Swagger/OpenAPI

### Points clés de l'architecture

✅ **Séparation des responsabilités** : Chaque couche a une responsabilité claire  
✅ **Sécurité par couches** : Filtre JWT + Spring Security + validation des rôles  
✅ **Stateless** : Pas de sessions, authentification basée sur JWT  
✅ **RESTful** : API REST respectant les conventions HTTP  
✅ **ORM** : Hibernate/JPA pour l'abstraction de la base de données  
✅ **Validation** : Validation des données à plusieurs niveaux (DTO, Service)  
✅ **Gestion d'erreurs centralisée** : GlobalExceptionHandler  
✅ **Upload de fichiers** : Gestion des images des chambres

---

## 🐳 Docker - Architecture Dockerisée

L'application est **entièrement dockerisée** pour une installation et un déploiement simplifiés. Toute la stack (application Spring Boot + base de données MySQL) est conteneurisée avec Docker Compose.

### Architecture Docker

#### Docker Compose (`docker-compose.yml`)

Le fichier `docker-compose.yml` définit deux services :

1. **Service MySQL** (`mysql`)
   - Image : `mysql:8.0`
   - Container : `mysql_dev`
   - Ports : `3307:3306` (port 3307 sur l'hôte, 3306 dans le conteneur)
   - Base de données : `hotel_reservation` (créée automatiquement)
   - Volume : `mysql_data` pour la persistance des données
   - Variables d'environnement :
     - `MYSQL_ROOT_PASSWORD`: mouloud1234
     - `MYSQL_DATABASE`: hotel_reservation

2. **Service Spring Boot** (`app`)
   - Build : Utilise le `Dockerfile` local
   - Container : `spring_dev`
   - Ports : `8093:8093`
   - Volumes :
     - `.:/app/` : Montage du code source pour le développement
     - `maven_repo:/root/.m2` : Cache Maven persistant
   - Variables d'environnement :
     - `SPRING_DATASOURCE_URL`: jdbc:mysql://mysql:3306/hotel_reservation
     - `SPRING_DATASOURCE_USERNAME`: root
     - `SPRING_DATASOURCE_PASSWORD`: mouloud1234
   - Dépendances : Dépend de `mysql` (démarre après MySQL)

#### Dockerfile

Le `Dockerfile` crée l'image Spring Boot :

```dockerfile
FROM eclipse-temurin:17-jdk  # Image de base avec Java 17

# Installation de Maven
RUN apt-get update && apt-get install -y maven

WORKDIR /app

# Copie du pom.xml pour optimiser le cache Docker
COPY pom.xml .

# Téléchargement des dépendances (cache)
RUN mvn dependency:go-offline

# Le code source est monté via volume (développement)
# En production, on copierait tout le code ici
CMD ["mvn", "spring-boot:run"]
```

### Commandes Docker utiles

#### Démarrer l'application

```bash
# Démarrer tous les services
docker-compose up

# Démarrer en arrière-plan (détaché)
docker-compose up -d

# Reconstruire les images avant de démarrer
docker-compose up --build
```

#### Arrêter l'application

```bash
# Arrêter les conteneurs (conserve les volumes)
docker-compose down

# Arrêter et supprimer les volumes (⚠️ supprime les données MySQL)
docker-compose down -v

# Arrêter sans supprimer les conteneurs
docker-compose stop
```

#### Gestion des services

```bash
# Voir l'état des services
docker-compose ps

# Voir les logs
docker-compose logs -f        # Tous les logs
docker-compose logs -f app    # Logs de l'application uniquement
docker-compose logs -f mysql  # Logs de MySQL uniquement

# Redémarrer un service spécifique
docker-compose restart app
docker-compose restart mysql

# Exécuter une commande dans un conteneur
docker-compose exec app bash
docker-compose exec mysql mysql -u root -p
```

#### Construction de l'image manuellement

```bash
# Construire l'image de l'application
docker build -t hotel-reservation:latest .

# Voir les images
docker images

# Supprimer l'image
docker rmi hotel-reservation:latest
```

### Volumes Docker

L'application utilise des volumes pour :

1. **`mysql_data`** : Persistance des données MySQL
   - Les données de la base de données sont conservées même après l'arrêt des conteneurs
   - Localisation : Gérée par Docker

2. **`maven_repo`** : Cache Maven persistant
   - Accélère les builds suivants en évitant de retélécharger les dépendances
   - Localisation : `/root/.m2` dans le conteneur

3. **`.:/app/`** : Montage du code source (développement)
   - Permet la modification du code sans reconstruire l'image
   - Utile pour le développement avec rechargement automatique

### Réseau Docker

Docker Compose crée automatiquement un réseau interne (`hotelreservaion_default`) qui permet :
- La communication entre les services via leur nom (`mysql`, `app`)
- L'isolation du réseau externe
- La connexion : `jdbc:mysql://mysql:3306/hotel_reservation` (pas `localhost`)

### Avantages de la dockerisation

✅ **Installation simplifiée** : Pas besoin d'installer Java, Maven ou MySQL  
✅ **Environnement reproductible** : Même environnement sur toutes les machines  
✅ **Isolation** : Pas de conflits avec d'autres applications  
✅ **Déploiement facile** : Prêt pour la production avec peu de modifications  
✅ **Persistance des données** : Volumes pour les données MySQL  
✅ **Développement rapide** : Hot reload avec volumes montés  

### Configuration pour la production

Pour la production, modifiez `docker-compose.yml` :

1. Utiliser des secrets pour les mots de passe
2. Configurer des ressources limitées (memory, CPU)
3. Utiliser des images spécifiques (tags de version)
4. Activer les healthchecks
5. Configurer les logs avec rotation

---

## 🔒 Sécurité

### Configuration Spring Security

L'application utilise Spring Security avec JWT pour l'authentification :

- **Endpoints publics** : `/api/auth/**`, `/`
- **Endpoints protégés** :
  - `/api/user/**` → Requiert le rôle `USER`
  - `/api/admin/**` → Requiert le rôle `ADMIN`
  - `/api/reseption/**` → Requiert le rôle `RESEPTION`

### JWT (JSON Web Tokens)

- Les tokens JWT sont générés lors de la connexion
- Durée de vie configurable dans `JwtUtil`
- Validation automatique via `JwtFilter`
- Format : `Authorization: Bearer <token>`

### CORS

CORS est configuré pour autoriser toutes les origines en développement. Pour la production, configurez les origines autorisées dans `SecurityConfig.java`.

---

## 🧪 Tests

### Exécuter les tests unitaires

```bash
./mvnw test
```

### Exécuter avec couverture de code

```bash
./mvnw test jacoco:report
```

---

## 🤝 Contribution

Les contributions sont les bienvenues ! Pour contribuer :

1. **Fork** le projet
2. Créer une branche pour votre fonctionnalité (`git checkout -b feature/AmazingFeature`)
3. **Commit** vos changements (`git commit -m 'Add some AmazingFeature'`)
4. **Push** vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une **Pull Request**

### Bonnes pratiques

- ✅ Suivre les conventions de code Java
- ✅ Ajouter des tests pour les nouvelles fonctionnalités
- ✅ Documenter le code complexe
- ✅ Utiliser des messages de commit clairs

---

## 📝 Notes importantes

### Production

⚠️ **Avant le déploiement en production :**

1. Changez les mots de passe par défaut dans `application.properties`
2. Configurez un secret JWT fort dans `JwtUtil`
3. Désactivez `spring.jpa.show-sql=true`
4. Configurez `spring.jpa.hibernate.ddl-auto=validate` ou `none`
5. Utilisez des variables d'environnement pour les secrets
6. Configurez CORS pour les origines autorisées uniquement
7. Activez HTTPS

### Développement

- Le mode `spring.jpa.hibernate.ddl-auto=update` crée/modifie automatiquement les tables
- `spring.jpa.show-sql=true` affiche les requêtes SQL dans les logs
- Spring DevTools permet le rechargement automatique

---

## 📄 Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.

---

## 👤 Auteur

- **Mouloudissilayine**
- **Zeini Cheikh Sidi Ely**
- **Saleck Med Vadel Ameine**
- **ahmed Essyad**
- **Ahmedou Vall**

- Projet : [Hotel Reservation Backend](https://github.com/mouloudcheikhne/Hotel-reservation-backend)

---

## 🙏 Remerciements

- Spring Boot Team
- Communauté open source

---

**⭐ Si ce projet vous a aidé, n'hésitez pas à lui donner une étoile !**
