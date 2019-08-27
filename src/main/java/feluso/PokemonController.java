package feluso;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PokemonController {
    @GetMapping("/hello")
    public String handle() {
        return "Hello WebFlux";
    }
}
