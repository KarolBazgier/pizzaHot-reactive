package pizzas.test.integrationTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import pizzas.Ingredient;
import pizzas.Pizza;
import pizzas.data.PizzaRepository;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PizzaControllerIntegrationTest {

    @Autowired
    private WebTestClient testClient;


    //// design pizza test

    @Test
    public void shouldSavePizza() {
        PizzaRepository pizzaRepository = Mockito.mock(PizzaRepository.class);
        Mono<Pizza> unsavedMonoPizza = Mono.just(testPizza(1L));
        Pizza savedPizza = testPizza(1L);
        Mono<Pizza> savedMonoPizza = Mono.just(savedPizza);

        when(pizzaRepository.save(any())).thenReturn(savedMonoPizza);

        testClient.post().uri("/design")
                .contentType(MediaType.APPLICATION_JSON)
                .body(unsavedMonoPizza, Pizza.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Pizza.class)
                .isEqualTo(savedPizza);
    }

    @Test
    public void shouldReturnRecentPizzas() throws IOException {
        testClient.get().uri("/design/recent")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[?(@.id == 'PIZZA1')].name")
                .isEqualTo("TestPizza1")
                .jsonPath("$[?(@.id == 'PIZZA2')].name")
                .isEqualTo("Wolowa");
    }

    @Test
    public void shouldReturnPizzaById() throws IOException {
        String pizzaId = "PIZZA2";
        testClient.get().uri("/design/{id}", pizzaId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(pizzaId)
                .jsonPath("$.name").isEqualTo("Wolowa");
    }
    private Pizza testPizza(Long number) {

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("testIngredient_1", "ingredient1", Ingredient.Type.CHEESE));
        ingredients.add(new Ingredient("testIngredient_2", "ingredient2", Ingredient.Type.SAUCE));

        return new Pizza("PIZZA" + number, "TestPizza" + number, new Date(), ingredients);
    }
}
