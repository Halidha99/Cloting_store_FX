package model;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Report {
    private LocalDate date;
    private int totalItemsSold;
    private double totalSales;
    private double totalReturns;
}
