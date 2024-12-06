package pizzas;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document
public class Order {
    @Id
    private String id;
    private Date placedAt = new Date();

    private User user;

    private String deliveryName;

    private String deliveryStreet;

    private String deliveryCity;

    private String deliveryZip;

    private String ccNumber;

    private String ccExpiration;

    private String ccCVV;


    private List<Pizza> pizzas = new ArrayList<>();

    public void addDesign(Pizza design) {
        this.pizzas.add(design);
    }

}
