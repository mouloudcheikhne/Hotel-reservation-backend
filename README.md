# 🏨 Hotel Reservation System

> Application Spring Boot pour la gestion des réservations d'hôtel avec authentification JWT

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
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
- ✅ **Sécurité** : Spring Security avec filtres JWT personnalisés

---

## 🛠 Technologies utilisées

- **Backend** : Spring Boot 4.0.1
- **Langage** : Java 17
- **Base de données** : MySQL 8.0
- **ORM** : Spring Data JPA / Hibernate
- **Sécurité** : Spring Security + JWT (jjwt 0.12.6)
- **Build Tool** : Maven
- **Validation** : Jakarta Validation
- **Outils** : Lombok, Spring DevTools

---

## 📦 Prérequis

Avant de commencer, assurez-vous d'avoir installé :

- **Java 17+** ([Télécharger](https://www.oracle.com/java/technologies/downloads/))
- **Maven 3.6+** (ou utilisez le wrapper `./mvnw` inclus)
- **MySQL 8.0+** (ou Docker pour exécuter MySQL)
- **Docker & Docker Compose** (optionnel, pour l'exécution conteneurisée)

---

## 🚀 Installation

### 1. Cloner le dépôt

```bash
git clone <repository-url>
cd hotelreservaion
```

### 2. Compiler le projet

```bash
./mvnw clean package
```

### 3. Configuration de la base de données

Créez une base de données MySQL nommée `hotel_reservation` :

```sql
CREATE DATABASE hotel_reservation;
```

---

## ⚙️ Configuration

Les paramètres de configuration se trouvent dans `src/main/resources/application.properties`.

### Configuration locale (sans Docker)

Modifiez `application.properties` pour utiliser votre base de données locale :

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
- `spring.jpa.hibernate.ddl-auto` : Mode de gestion du schéma (update/create-drop/none)

---

## ▶️ Démarrage

### Mode développement (local)

```bash
./mvnw spring-boot:run
```

L'application sera accessible sur : **http://localhost:8093**

### Mode production

```bash
./mvnw clean package
java -jar target/hotelreservaion-0.0.1-SNAPSHOT.jar
```

---

## 📚 API Documentation

### Base URL

```
http://localhost:8093
```

### Endpoints publics

#### 1. Test de connexion

```http
GET /?param=test
```

**Exemple :**

```bash
curl http://localhost:8093/?param=test
```

#### 2. Inscription

```http
POST /api/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe",
  "role": "USER"
}
```

**Exemple :**

```bash
curl -X POST http://localhost:8093/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123",
    "firstName": "John",
    "lastName": "Doe",
    "role": "USER"
  }'
```

#### 3. Connexion

```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
```

**Exemple :**

```bash
curl -X POST http://localhost:8093/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123"
  }'
```

**Réponse :**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "email": "user@example.com",
  "role": "USER"
}
```

### Endpoints protégés

Tous les endpoints protégés nécessitent un token JWT dans l'en-tête `Authorization`.

#### 4. Profil utilisateur

```http
GET /api/user/me
Authorization: Bearer <TOKEN>
```

**Exemple :**

```bash
curl -H "Authorization: Bearer <TOKEN>" \
  http://localhost:8093/api/user/me
```

### Contrôleurs disponibles

| Contrôleur            | Description                            | Rôle requis |
| --------------------- | -------------------------------------- | ----------- |
| `AuthController`      | Authentification (login, register)     | Public      |
| `UserController`      | Actions utilisateur (profil)           | USER        |
| `AdminController`     | Gestion admin (chambres, utilisateurs) | ADMIN       |
| `ReceptionController` | Gestion réception (réservations)       | RESEPTION   |

---

## 📁 Structure du projet

```
hotelreservaion/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── hotel/example/hotelreservaion/
│   │   │       ├── HotelreservaionApplication.java    # Point d'entrée
│   │   │       ├── config/
│   │   │       │   └── SecurityConfig.java             # Configuration Spring Security
│   │   │       ├── controller/                         # Contrôleurs REST
│   │   │       │   ├── AuthController.java
│   │   │       │   ├── UserController.java
│   │   │       │   ├── AdminController.java
│   │   │       │   └── ReceptionController.java
│   │   │       ├── service/                            # Logique métier
│   │   │       │   ├── AuthService.java
│   │   │       │   ├── UserService.java
│   │   │       │   ├── AdminService.java
│   │   │       │   ├── ReseptionService.java
│   │   │       │   └── MyUserDetailsService.java
│   │   │       ├── repository/                         # Interfaces JPA
│   │   │       │   └── UserReposiory.java
│   │   │       ├── model/                              # Entités JPA
│   │   │       │   ├── User.java
│   │   │       │   ├── Room.java
│   │   │       │   ├── Booking.java
│   │   │       │   ├── BookingStatus.java
│   │   │       │   ├── UserRole.java
│   │   │       │   └── CustomUserDetails.java
│   │   │       ├── dto/                                # Data Transfer Objects
│   │   │       │   ├── LoginDto.java
│   │   │       │   ├── RegesterDto.java
│   │   │       │   ├── ResponceLoginDto.java
│   │   │       │   └── ResponceRegesterDtO.java
│   │   │       ├── util/                               # Utilitaires
│   │   │       │   ├── JwtUtil.java                    # Gestion JWT
│   │   │       │   └── JwtFilter.java                  # Filtre JWT
│   │   │       └── exception/                          # Gestion d'erreurs
│   │   │           └── GlobalExceptionHandler.java
│   │   └── resources/
│   │       └── application.properties                  # Configuration
│   └── test/                                            # Tests unitaires
├── Dockerfile                                           # Image Docker
├── docker-compose.yml                                   # Orchestration Docker
├── pom.xml                                              # Dépendances Maven
└── README.md                                            # Documentation
```

### Architecture

```
┌─────────────┐
│  Client     │
│  (HTTP)     │
└──────┬──────┘
       │
       ▼
┌─────────────────────────────────────┐
│         Controllers                 │
│  (Auth, User, Admin, Reception)     │
└──────┬──────────────────────────────┘
       │
       ▼
┌─────────────────────────────────────┐
│         Services                     │
│  (Logique métier)                    │
└──────┬──────────────────────────────┘
       │
       ▼
┌─────────────────────────────────────┐
│         Repositories                 │
│  (Spring Data JPA)                   │
└──────┬──────────────────────────────┘
       │
       ▼
┌─────────────────────────────────────┐
│         MySQL Database               │
└─────────────────────────────────────┘

┌─────────────────────────────────────┐
│    Security Layer (JWT Filter)      │
│    (Intercepte toutes les requêtes) │
└─────────────────────────────────────┘
```

---

## 🐳 Docker

### Construction de l'image

```bash
docker build -t hotel-reservation:latest .
```

### Exécution avec Docker Compose

Docker Compose lance automatiquement l'application et MySQL :

```bash
docker-compose up --build
```

**Services :**

- **MySQL** : Port `3307` (mappé depuis `3306` dans le conteneur)
- **Application** : Port `8093`

### Arrêter les services

```bash
docker-compose down
```

Pour supprimer également les volumes :

```bash
docker-compose down -v
```

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

**Mouloudissilayine**

- Projet : [Hotel Reservation](https://github.com/yourusername/hotelreservaion)

---

## 🙏 Remerciements

- Spring Boot Team
- Communauté open source

---

**⭐ Si ce projet vous a aidé, n'hésitez pas à lui donner une étoile !**
