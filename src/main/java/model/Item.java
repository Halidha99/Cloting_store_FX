package model;

import lombok.Data;

@Data
public class Item {
    private String id;
    private String name;
    private Integer unitPrice;
    private Integer qty;
    private String category;
    private String size;
    private Supplier supplier;
}
