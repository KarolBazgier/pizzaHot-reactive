package pizzas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pizzas.data.IngredientRepository;
import pizzas.data.PaymentMethodRepository;
import pizzas.data.PizzaRepository;
import pizzas.data.UserRepository;
import pizzas.Ingredient.Type;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Configuration
//@Profile("!prod")
public class DevelopmentConfig {

//    @Bean
//    public CommandLineRunner dataLoader(IngredientRepository repo,
//                                        UserRepository userRepo, PasswordEncoder encoder, PizzaRepository pizzaRepo,
//                                        PaymentMethodRepository paymentMethodRepo) { // user repo for ease of testing with a built-in user
//
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... args) throws Exception {
//                Ingredient kurczak = saveAnIngredient("KUR", "Kurczak", Type.MEAT);
//                Ingredient wolowina = saveAnIngredient("WOŁ", "Wołowina", Type.MEAT);
//                Ingredient pomidor = saveAnIngredient("POM", "Pomidor", Type.VEGETABLES);
//                Ingredient pieczarki = saveAnIngredient("PIE", "Pieczarki", Type.VEGETABLES);
//                Ingredient chili = saveAnIngredient("CHI", "Chili", Type.VEGETABLES);
//                Ingredient kukurydza = saveAnIngredient("KUK", "Kukurydza", Type.VEGETABLES);
//                Ingredient cheddar = saveAnIngredient("CHED", "Cheddar", Type.CHEESE);
//                Ingredient jack = saveAnIngredient("JACK", "Monterrey Jack", Type.CHEESE);
//                Ingredient czosnkowy = saveAnIngredient("CZO", "Czosnkowy", Type.SAUCE);
//                Ingredient ketchup = saveAnIngredient("KET", "Ketchup", Type.SAUCE);
//
////        UserUDT u = new UserUDT(username, fullname, phoneNumber)
//
//                userRepo.save(new User("karol5108", encoder.encode("password"),
//                        "Karol Bazgier", "Pruska 44", "Prusy",
//                        "33333", "123-123-123", "karol5108@gmail.com"))
//                        .subscribe(user -> {
//                            paymentMethodRepo.save(new PaymentMethod(user, "4111111111111111", "321", "10/25")).subscribe();
//                        });
//
//                Pizza pizza1 = new Pizza();
//                pizza1.setId("PIZZA1");
//                pizza1.setName("Kurczakowa");
//                pizza1.setIngredients(Arrays.asList(kurczak, pomidor, pieczarki, kukurydza, cheddar, czosnkowy));
//                pizzaRepo.save(pizza1).subscribe();
//
//                Pizza pizza2 = new Pizza();
//                pizza2.setId("PIZZA2");
//                pizza2.setName("Wołowa");
//                pizza2.setIngredients(Arrays.asList(wolowina, cheddar, jack, ketchup));
//                pizzaRepo.save(pizza2).subscribe();
//
//            }
//
//            private Ingredient saveAnIngredient(String id, String name, Type type) {
//                Ingredient ingredient = new Ingredient(id, name, type);
//                repo.save(ingredient).subscribe();
//                return ingredient;
//            }
//        };
//    }
@Bean
public CommandLineRunner dataLoader(IngredientRepository repo,
                                    UserRepository userRepo,
                                    PasswordEncoder encoder,
                                    PizzaRepository pizzaRepo,
                                    PaymentMethodRepository paymentMethodRepo) {

    return args -> {
//        Flux<Ingredient> ingredients = Flux.just(
//                new Ingredient("KUR", "Kurczak", Type.MEAT),
//                new Ingredient("WOŁ", "Wołowina", Type.MEAT),
//                new Ingredient("POM", "Pomidor", Type.VEGETABLES),
//                new Ingredient("PIE", "Pieczarki", Type.VEGETABLES),
//                new Ingredient("CHI", "Chili", Type.VEGETABLES),
//                new Ingredient("KUK", "Kukurydza", Type.VEGETABLES),
//                new Ingredient("CHED", "Cheddar", Type.CHEESE),
//                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
//                new Ingredient("CZO", "Czosnkowy", Type.SAUCE),
//                new Ingredient("KET", "Ketchup", Type.SAUCE)
//        );
//        repo.saveAll(ingredients).subscribe();
//


        Mono<List<Ingredient>> ingredientsFlux = Flux.just("KUR", "POM", "PIE", "KUK", "CHED", "CZO")
                .flatMap(repo::findById)
                .collectList();

        ingredientsFlux.flatMap(ingredient -> {
            Pizza pizza1 = new Pizza("PIZZA4", "Kurczakowa",new Date(), ingredient);
            return pizzaRepo.save(pizza1);
        }).subscribe();
};}

}