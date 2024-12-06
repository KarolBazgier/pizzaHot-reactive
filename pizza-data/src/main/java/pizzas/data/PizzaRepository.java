package pizzas.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pizzas.Pizza;

public interface PizzaRepository extends ReactiveCrudRepository<Pizza, String> {
}
