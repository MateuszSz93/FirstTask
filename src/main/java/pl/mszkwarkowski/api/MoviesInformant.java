package pl.mszkwarkowski.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.mszkwarkowski.data.Creator;

@SpringBootApplication
public class MoviesInformant {
    public static void main(String[] args) {
        Creator creator = new Creator();
        creator.startingObjectsCreator();

        SpringApplication.run(MoviesInformant.class, args);
    }
}
