package pizzas;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@RestResource(rel = "pizza", path = "pizza")
@AllArgsConstructor
@Document(collection = "pizza")
public class Pizza {
    @Id
    private String id;

    @NotNull
    @Size(min = 5, message = "5 characters long")
    private String name;

    private Date createdAt = new Date();

    @Size(min=1, message="choose min 1 ingredient")
    private List<Ingredient> ingredients;

}
