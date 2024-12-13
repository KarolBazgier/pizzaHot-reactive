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
import pizzas.data.IngredientRepository;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class IngredientControllerIntegrationTest {
    @Autowired
    private WebTestClient testClient;



    @Test
    public void shouldReturnIngredients(){
        testClient.get().uri("/ingredients")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Ingredient.class)
                .consumeWith(response ->{
                    List<Ingredient> ingredients = response.getResponseBody();
                    assertNotNull(ingredients);
                    assertTrue(ingredients.size() > 0);
                    assertTrue(ingredients.stream().anyMatch(i -> i.getType().equals(Ingredient.Type.CHEESE)));
                    assertTrue(ingredients.stream().anyMatch(i -> i.getType().equals(Ingredient.Type.MEAT)));
                    assertTrue(ingredients.stream().anyMatch(i -> i.getType().equals(Ingredient.Type.SAUCE)));
                    assertTrue(ingredients.stream().anyMatch(i -> i.getType().equals(Ingredient.Type.VEGETABLES)));
                });
    }

    @Test
    public void shouldReturnIngredientById(){
        String ingredientId = "KUK";
        testClient.get().uri("/ingredients/{id}", ingredientId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(ingredientId)
                .jsonPath("$.name").isEqualTo("Kukurydza");
    }

    @Test
    public void shouldSaveIngredient(){
        IngredientRepository ingredientRepository = Mockito.mock(IngredientRepository.class);

        Mono<Ingredient> unsavedIngredientMono = Mono.just(testIngredientVegetable(1L));
        Ingredient savedIngredient = testIngredientVegetable(1L);
        Mono<Ingredient> savedIngredientMono = Mono.just(savedIngredient);

        when(ingredientRepository.save(any())).thenReturn(savedIngredientMono);

        testClient.post().uri("/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .body(unsavedIngredientMono, Ingredient.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Ingredient.class)
                .isEqualTo(savedIngredient);
    }

    @Test
    public void shouldUpdateIngredient(){

        Mono<Ingredient> newIngredientWIthTheSameId = Mono.just(new Ingredient("CHI","Papryczka chilli", Ingredient.Type.VEGETABLES));

        String ingredientId = "CHI";

        testClient.put().uri("/ingredients/{id}", ingredientId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(newIngredientWIthTheSameId, Ingredient.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("CHI")
                .jsonPath("$.name").isEqualTo("Papryczka chilli")
                .jsonPath("$.type").isEqualTo(Ingredient.Type.VEGETABLES);

    }

    @Test
    public void shouldDeleteIngredientById(){
        String ingredientId = "testIngredient1";

        testClient.delete().uri("/ingredients/{id}", ingredientId)
                .exchange()
                .expectStatus().isOk();
    }



    private Ingredient testIngredientVegetable(Long number){
        return new Ingredient("testIngredient"+number, "testVegetable"+number, Ingredient.Type.VEGETABLES);
    }

}
