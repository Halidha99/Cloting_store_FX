package model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class Employee {
    private String id;
    private String tittle;
    private String name;
    private String mobile;
    private String nic;
    private String email;
    private String Password;
}
