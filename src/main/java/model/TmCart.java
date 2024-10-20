package model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class TmCart {
    private String itemCode;
    private String itemName;
    private Integer qty;
    private Double unitPrice;
    private Double total;
}
