package pizzas;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.hateoas.RepresentationModel;

@Data
@RestResource(rel = "ingredients", path = "ingredients")
@AllArgsConstructor
@Document(collection = "ingredients")
public class Ingredient  {
    @Id
    private String id;
    private String name;
    private Type type;

    public static enum Type {
        VEGETABLES, CHEESE, MEAT, SAUCE
    }
}
