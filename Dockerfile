FROM eclipse-temurin:17-jdk

# Installer Maven
RUN apt-get update && apt-get install -y maven

WORKDIR /app

# Copier seulement le pom.xml (cache dépendances)
COPY pom.xml .

RUN mvn dependency:go-offline

# Le code sera monté via volume (PAS copié)
CMD ["mvn", "spring-boot:run"]
