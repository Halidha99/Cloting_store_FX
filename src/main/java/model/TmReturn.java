package model;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TmReturn {
    private String returnId;
    private String itemCode;
    private int qty;
    private double unitPrice;
    private double amount;

    public TmReturn(String id, String name, int qty, String size, Integer unitPrice, LocalDate now) {
    }
}
