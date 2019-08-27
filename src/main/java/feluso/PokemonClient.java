package feluso;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import lombok.val;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class PokemonClient {

    WebClient client = WebClient.create("https://pokeapi.co/api/v2/");

    public void getPokemon() {
        val result = client.get()
                .uri("/pokemon/ditto")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();


        WebClient client = WebClient.create();

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
