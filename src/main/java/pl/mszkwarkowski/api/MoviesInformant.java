package pl.mszkwarkowski.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@ComponentScan(basePackages = {"pl.mszkwarkowski.api", "pl.mszkwarkowski.other", "pl.mszkwarkowski.user"})
@EnableJpaRepositories({"pl.mszkwarkowski.other", "pl.mszkwarkowski.movie", "pl.mszkwarkowski.user"})
@EntityScan({"pl.mszkwarkowski.other", "pl.mszkwarkowski.movie", "pl.mszkwarkowski.movie", "pl.mszkwarkowski.user"})
@SpringBootApplication
@EnableAutoConfiguration
public class MoviesInformant {
    public static void main(String[] args) {
        SpringApplication.run(MoviesInformant.class, args);
    }
}
