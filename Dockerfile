FROM openjdk:21

RUN apt-get update && apt-get install -y maven ncurses-bin

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

CMD ["java", "-jar", "target/app.jar"]