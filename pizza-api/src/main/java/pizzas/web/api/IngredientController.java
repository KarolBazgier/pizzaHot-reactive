package pizzas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pizzas.Ingredient;
import pizzas.data.IngredientRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping(value = "/ingredients", produces = "application/json")
@CrossOrigin("*")
public class IngredientController {
    private IngredientRepository repo;

    @Autowired
    public IngredientController(IngredientRepository ingredientRepository) {
        this.repo = ingredientRepository;
    }

    @GetMapping
    public Flux<Ingredient> allIngredients() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Ingredient> byId(@PathVariable String id) {
        return repo.findById(id);
    }

    @PutMapping("/{id}")
    public void updateIngredient(@PathVariable String id, @RequestBody Ingredient ingredient) {
        if (ingredient.getId().equals(id)) {
            repo.save(ingredient);
        }
        throw new IllegalStateException("Ingredient with id: " + id + " not found");

    }

    @PostMapping
    public Mono<ResponseEntity<Ingredient>> postIngredient(@RequestBody Ingredient ingredient) {
        return repo.save(ingredient)  // Zapisz składnik do repozytorium
                .map(savedIngredient -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setLocation(URI.create("http://localhost:8080/ingredients/" + savedIngredient.getId()));
                    return new ResponseEntity<>(savedIngredient, headers, HttpStatus.CREATED);
                });
    }

    @DeleteMapping("/{id}")
    public void deleteIngredient(@PathVariable String id) {
        repo.deleteById(id);
    }

}
