package pizzas.web.api;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pizzas.Pizza;
import pizzas.data.PizzaRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/design", produces = "application/json")
@CrossOrigin("*")
public class DesignPizzaController {
    private PizzaRepository pizzaRepo;


    public DesignPizzaController(PizzaRepository pizzaRepo) {
        this.pizzaRepo = pizzaRepo;

    }

    @GetMapping("/recent")
    public Flux<Pizza> recentPizzas(){
        return pizzaRepo.findAll().take(12);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Pizza> postPizza(@RequestBody Pizza pizza){
        return pizzaRepo.save(pizza);
    }

    @GetMapping("/{id}")
    public Mono<Pizza> pizzaById(@PathVariable("id") String id){
        return pizzaRepo.findById(id);
    }


}
