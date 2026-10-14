package example;

import com.entityassist.BaseEntity;
import com.entityassist.querybuilder.QueryBuilder;
import com.entityassist.enumerations.Operand;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "guide_customers")
@Getter
@Setter
@Accessors(chain = true)
public class Customers extends BaseEntity<Customers, Customers.Builder, String> {
    @Id private String id;
    private String name;
    private boolean active;
    private String region;

    @Override public String getId() { return id; }
    @Override public Customers setId(String id) { this.id = id; return this; }

    public static class Builder extends QueryBuilder<Builder, Customers, String> {
        @Override public boolean isIdGenerated() { return false; }
        public Builder withName(String name){
            return where(Customers_.name, Operand.Equals, name);
        }
    }
}
