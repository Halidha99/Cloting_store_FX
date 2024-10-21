package Entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity(name = "customer")
@Table(name = "customer")
public class CustomerEntity {
    @Id
    private String id;
    private String tittle;
    private String name;
    private String email;
    private String address;
}
