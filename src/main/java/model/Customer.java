package model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Customer {
    private String id;
    private String tittle;
    private String name;
    private String email;
    private String address;
}
