package pizzas;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class PaymentMethod {
    @Id
    private String id;

    private final User user;
    private final String ccNumber;
    private final String ccCVV;
    private final String ccExpiration;

}
