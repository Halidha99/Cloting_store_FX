package entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "supplier")
@Table(name = "supplier")
public class SupplierEntity {
    @Id

    private String id;
    private String tittle;
    private String name;
    private String company;
    private String mobile;
    private String email;
}
