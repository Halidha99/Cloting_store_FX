package model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Order {
    private String id;
    private Customer customer;
    private LocalDate date;
    private LocalTime time;
    private Double netTotal;
}
