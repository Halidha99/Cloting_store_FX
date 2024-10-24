package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {
    private String id;
    private String name;
    private Integer qty;
    private String category;
    private String size;
    private Supplier supplier;
    private Integer unitPrice;

}
