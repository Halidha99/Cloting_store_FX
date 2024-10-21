package Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "orders")
@Table(name = "orders")
public class OrderEntity {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    private LocalDate date;
    private Double netTotal;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderDetailEntity> orderDetails = new ArrayList<>();
}
