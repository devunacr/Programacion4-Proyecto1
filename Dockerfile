# Imagen base con JDK 21
FROM openjdk:21-jdk

# Instalar ncurses (opcional, solo para tput)
RUN apt-get update && apt-get install -y ncurses-bin

# Definir directorio de trabajo
WORKDIR /app

# Copiar todo el proyecto
COPY . .

# Dar permisos de ejecución al wrapper
RUN chmod +x mvnw

# Compilar con Maven Wrapper
RUN ./mvnw clean install -DskipTests

# Comando de arranque (comodín para el jar)
CMD ["sh", "-c", "java -jar target/*.jar"]
