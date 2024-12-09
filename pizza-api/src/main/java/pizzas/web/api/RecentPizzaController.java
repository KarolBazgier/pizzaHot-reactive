package pizzas.web.api;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pizzas.Pizza;
import pizzas.data.PizzaRepository;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class RecentPizzaController {
    private PizzaRepository pizzaRepository;

    public RecentPizzaController(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @GetMapping(path = "/pizzas/recent", produces = "application/json")
    public Mono<ResponseEntity<List<Pizza>>> recentPizzas(){
        return pizzaRepository.findAll()
                .take(12)
                .collectList()
                .map(pizzas -> {
                    return ResponseEntity.ok(pizzas);
                });
    }
}
