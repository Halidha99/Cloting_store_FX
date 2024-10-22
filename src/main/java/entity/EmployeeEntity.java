package entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Entity(name = "employee")
@Table(name = "employee")
public class EmployeeEntity {
    @Id

    private String id;
    private String tittle;
    private String name;
    private String mobile;
    private String nic;
    private String email;
    private String Password;
}
