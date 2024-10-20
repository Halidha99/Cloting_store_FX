package model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class Order {
    private String id;
    private Customer customer;
    private LocalDate date;
    private LocalTime time;
    private Double netTotal;
}
