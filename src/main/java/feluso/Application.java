package feluso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
@EnableAutoConfiguration
@ComponentScan("feluso")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        WebClient client = WebClient.create("https://pokeapi.co/api/v2/");
        Mono<ClientResponse> result = client.get()
                .uri("/pokemon/ditto")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        client.get().uri("https://pokeapi.co/api/v2/pokemon/ditto")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Pokemon.class)
                .map(Pokemon::getSpecies)
                .flatMap(species ->
                        client.get().uri(species.getUrl())
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToMono(Object.class))
                .doOnEach(System.out::println)
                .block();

        System.out.println("Done");

    }
}
