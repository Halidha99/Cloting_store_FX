package entity;

import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Getter
@Setter
@Entity(name = "order_details")
@Table(name = "order_details")
public class OrderDetailEntity {
    @Id

    private String itemID;
    private Integer qty;
    private Double itemTotal;
    @Id
    @ManyToOne
    @JoinColumn(name = "orderID")
    private OrderEntity order;

    public OrderDetailEntity(String itemID, Integer qty, Double itemTotal) {
        this.itemID = itemID;
        this.qty = qty;
        this.itemTotal = itemTotal;
    }
}