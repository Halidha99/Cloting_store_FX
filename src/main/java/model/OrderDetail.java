package model;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Getter
@Setter
public class OrderDetail{
    private Order order;
    private String itemID;
    private Integer qty;
    private Double itemTotal;

    public OrderDetail(String itemID, Integer qty, Double itemTotal) {
        this.itemID = itemID;
        this.qty = qty;
        this.itemTotal = itemTotal;
    }
}