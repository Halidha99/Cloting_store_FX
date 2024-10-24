package entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "item")
@Table(name = "item")
public class ItemEntity {
    @Id
    private String id;
    private String name;
    private Integer qty;
    private String category;
    private String size;
    private Integer unitPrice;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private SupplierEntity supplier;
}
