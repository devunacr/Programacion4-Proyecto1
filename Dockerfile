# Imagen base con JDK 21
FROM openjdk:21-jdk

# Instalar ncurses (para que exista tput)
RUN apt-get update && apt-get install -y ncurses-bin

# Copiar el proyecto
WORKDIR /app
COPY . .

# Compilar con Maven Wrapper
RUN ./mvnw clean install -DskipTests

# Comando de arranque
CMD ["java", "-jar", "target/Programacion4-Proyecto1-0.0.1-SNAPSHOT.jar"]
