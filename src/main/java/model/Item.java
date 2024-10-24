package model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Item {
    private String id;
    private String name;
    private Integer qty;
    private String category;
    private String size;
    private Supplier supplier;
    private Integer unitPrice;

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", qty=" + qty +
                ", category='" + category + '\'' +
                ", size='" + size + '\'' +
                ", supplier=" + supplier +
                ", unitPrice=" + unitPrice +
                '}';
    }



}
