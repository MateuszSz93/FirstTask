package pl.mszkwarkowski.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@ComponentScan(basePackages = {"pl.mszkwarkowski.controller"})
@EnableJpaRepositories({"pl.mszkwarkowski.repository"})
@EntityScan({"pl.mszkwarkowski.model"})
@SpringBootApplication
@EnableAutoConfiguration
public class MoviesInformant {
    public static void main(String[] args) {
        SpringApplication.run(MoviesInformant.class, args);
    }
}
