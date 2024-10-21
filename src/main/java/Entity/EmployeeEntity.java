package Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
