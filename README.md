# Hotel Reservation — Spring Boot

Bienvenue dans le projet Hotel Reservation, une application Spring Boot simple pour gérer les réservations d'hôtel avec authentification JWT.

## Aperçu

- Framework : Spring Boot (Java)
- Gestion des utilisateurs, des chambres et des réservations.
- Authentification basée sur JWT (filtres et utilitaires dans `src/main/java/.../util`).

## Prérequis

- Java 17+ installé
- Maven (ou utiliser le wrapper `./mvnw` inclus)
- Docker & Docker Compose (optionnel pour exécution conteneurisée)

## Installation & compilation

Depuis la racine du projet :

```bash
./mvnw clean package
```

Pour lancer l'application localement :

```bash
./mvnw spring-boot:run
```

L'application démarre par défaut sur le port `8080` (vérifier `application.properties`).

## Docker

Construire l'image Docker :

```bash
docker build -t hotel-reservation:latest .
```

Lancer avec Docker Compose :

```bash
docker-compose up --build
```

## Configuration

- Les propriétés sont dans `src/main/resources/application.properties`.
- Variables communes : configuration de la base de données, secrets JWT, ports.

## API — Points d'entrée principaux

Les contrôleurs principaux se trouvent dans `src/main/java/hotel/example/hotelreservaion/controller` :

- `AuthController` : endpoints d'authentification (login, register).
- `UserController` : actions utilisateurs (profil, mise à jour).
- `AdminController` : opérations réservées aux administrateurs (gestion des chambres, utilisateurs).
- `ReceptionController` : gestion des réservations côté réception.

Exemples (adapter URL/ports) :

1. Authentification (obtenir token JWT) :

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"admin","password":"password"}'
```

2. Appel d'une route protégée :

```bash
curl -H "Authorization: Bearer <TOKEN>" http://localhost:8080/api/user/me
```

Remplacez `<TOKEN>` par le JWT renvoyé lors du login.

## Structure du projet

- `controller/` — classes qui exposent l'API REST
- `service/` — logique métier
- `model/` — entités JPA (User, Room, Booking, BookingStatus)
- `repository/` — interfaces Spring Data JPA
- `util/` — utilitaires JWT (`JwtUtil`, `JwtFilter`)
- `config/` — configuration Spring Security

### Arborescence (extrait)

```text
hotelreservaion/
├── Dockerfile
├── docker-compose.yml
├── mvnw
├── pom.xml
├── README.md
└── src
  ├── main
  │   ├── java
  │   │   └── hotel
  │   │       └── example
  │   │           └── hotelreservaion
  │   │               ├── HotelreservaionApplication.java
  │   │               ├── config
  │   │               │   └── SecurityConfig.java
  │   │               ├── controller
  │   │               │   ├── AdminController.java
  │   │               │   ├── AuthController.java
  │   │               │   ├── ReceptionController.java
  │   │               │   └── UserController.java
  │   │               ├── model
  │   │               │   ├── Booking.java
  │   │               │   ├── BookingStatus.java
  │   │               │   ├── Room.java
  │   │               │   └── User.java
  │   │               ├── repository
  │   │               │   └── UserReposiory.java
  │   │               ├── service
  │   │               │   ├── AdminService.java
  │   │               │   ├── MyUserDetailsService.java
  │   │               │   ├── ReseptionService.java
  │   │               │   └── UserService.java
  │   │               └── util
  │   │                   ├── JwtFilter.java
  │   │                   └── JwtUtil.java
  │   └── resources
  │       └── application.properties
  └── test
    └── java
      └── hotel
        └── example
          └── hotelreservaion
            └── HotelreservaionApplicationTests.java
```

Le graphe montre le flux principal : les `Controllers` reçoivent les requêtes HTTP et délèguent à la couche `Services` qui utilise les `Repositories` pour persister/charger les `Models`. La sécurité (JWT) s'intègre au niveau des contrôleurs/filtrage et la configuration globale se trouve dans `config/`.

## Tests

Exécuter les tests unitaires :

```bash
./mvnw test
```

## Bonnes pratiques & suggestions

- Ne pas stocker de secrets en clair dans `application.properties` en production.
- Ajouter une documentation OpenAPI / Swagger pour faciliter l'exploration de l'API.
- Ajouter des tests d'intégration pour valider le flux d'authentification et les scénarios de réservation.

## Contribution

1. Forker le dépôt
2. Créer une branche `feature/...` ou `fix/...`
3. Ouvrir une pull request décrivant le changement

## Licence

Ajouter ici les informations de licence si besoin (ex : MIT).

---

Si vous voulez, je peux :

- exécuter les tests (`./mvnw test`),
- committer le `README.md`,
- générer une doc OpenAPI/Swagger minimale.
