package example;

import com.entityassist.BaseEntity;
import com.entityassist.querybuilder.QueryBuilder;
import jakarta.persistence.*;

@Entity
@Table(name = "guide_orders")
public class Orders extends BaseEntity<Orders, Orders.Builder, String> {
    @Id private String id;
    @ManyToOne(fetch = FetchType.LAZY) private Customers customer;
    private java.math.BigDecimal total;
    private String status;

    @Override public String getId() { return id; }
    @Override public Orders setId(String id) { this.id = id; return this; }
    public Customers getCustomer() { return customer; }
    public Orders setCustomer(Customers customer) { this.customer = customer; return this; }
    public java.math.BigDecimal getTotal() { return total; }
    public Orders setTotal(java.math.BigDecimal total) { this.total = total; return this; }
    public String getStatus() { return status; }
    public Orders setStatus(String status) { this.status = status; return this; }

    public static class Builder extends QueryBuilder<Builder, Orders, String> {
        @Override public boolean isIdGenerated() { return false; }
    }
}
