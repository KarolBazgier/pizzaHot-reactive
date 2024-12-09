package pizzas.web.api;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pizzas.Order;
import pizzas.data.OrderRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/orders", produces = "application/json")
@CrossOrigin(origins = "*")
public class OrderApiController {
    private OrderRepository orderRepository;

    public OrderApiController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public Flux<Order> allOrders(){
        return orderRepository.findAll();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Order> postOrder(@RequestBody Mono<Order> order) {
        return order
                .flatMap(orderRepository::save) // Zapisz zamówienie w repozytorium
//                .doOnNext(orderMessages::sendOrder) // Wyślij zamówienie
                .doOnError(error -> {
                    // Logowanie błędów lub inna obsługa błędów
                    System.err.println("Error processing order: " + error.getMessage());
                });
    }

    @PutMapping(path="/{orderId}", consumes="application/json")
    public Mono<Order> putOrder(@RequestBody Mono<Order> order) {
        return order.flatMap(orderRepository::save);
    }

    @PatchMapping(path="/{orderId}", consumes="application/json")
    public Mono<Order> patchOrder(@PathVariable("orderId") String orderId,
                                  @RequestBody Order patch) {

        return orderRepository.findById(orderId)
                .map(order -> {
                    if (patch.getDeliveryName() != null) {
                        order.setDeliveryName(patch.getDeliveryName());
                    }
                    if (patch.getDeliveryStreet() != null) {
                        order.setDeliveryStreet(patch.getDeliveryStreet());
                    }
                    if (patch.getDeliveryCity() != null) {
                        order.setDeliveryCity(patch.getDeliveryCity());
                    }
                    if (patch.getDeliveryZip() != null) {
                        order.setDeliveryZip(patch.getDeliveryZip());
                    }
                    if (patch.getCcNumber() != null) {
                        order.setCcNumber(patch.getCcNumber());
                    }
                    if (patch.getCcExpiration() != null) {
                        order.setCcExpiration(patch.getCcExpiration());
                    }
                    if (patch.getCcCVV() != null) {
                        order.setCcCVV(patch.getCcCVV());
                    }
                    return order;
                })
                .flatMap(orderRepository::save);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") String orderId) {
        try {
            orderRepository.deleteById(orderId);
        } catch (EmptyResultDataAccessException e) {}
    }

}
