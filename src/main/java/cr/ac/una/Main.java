package cr.ac.una;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}

//version funcional 27 marzo 4:30pm

//comando para arrancar en local: mvn spring-boot:run -Dspring-boot.run.profiles=dev