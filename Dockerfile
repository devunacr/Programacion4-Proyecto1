# Imagen base con JDK 21
FROM openjdk:21-jdk

# Instalar Maven directamente en la imagen
RUN apt-get update && apt-get install -y maven

# Definir directorio de trabajo
WORKDIR /app

# Copiar todo el proyecto
COPY . .

# Compilar y empaquetar con Maven
RUN mvn clean package -DskipTests

# Arrancar la aplicación con el jar generado
CMD ["sh", "-c", "java -jar target/*.jar"]
