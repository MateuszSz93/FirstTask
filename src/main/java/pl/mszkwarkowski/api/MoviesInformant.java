package pl.mszkwarkowski.api;

import com.google.common.base.Predicates;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.builders.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan({"pl.mszkwarkowski.controller"})
@EnableJpaRepositories({"pl.mszkwarkowski.repository"})
@EntityScan({"pl.mszkwarkowski.model"})
@SpringBootApplication
@EnableAutoConfiguration
@EnableSwagger2
public class MoviesInformant {
    public static void main(String[] args) {
        SpringApplication.run(MoviesInformant.class, args);
    }

    @Bean
    public Docket swaggerSettings() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error")))
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(new ApiInfo(
                        "Movies REST API",
                        "Movies REST API makes it easy to manage a collection of movies and actors. It stores information for each movie and actor and displays them to user. This API also allows to rent and to return movies by users.",
                        "1.0",
                        null,
                        "Mateusz Szkwarkowski <mateuszszkwarkowski@gmail.com>",
                        null,
                        null));
    }
}
