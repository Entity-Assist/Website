package example;

import com.entityassist.BaseEntity;
import com.entityassist.querybuilder.QueryBuilder;
import jakarta.persistence.*;

@Entity
@Table(name = "guide_categories")
public class Categories extends BaseEntity<Categories, Categories.Builder, String> {
    @Id private String id;
    private String name;
    private String parentId;

    @Override public String getId() { return id; }
    @Override public Categories setId(String id) { this.id = id; return this; }
    public String getName() { return name; }
    public Categories setName(String name) { this.name = name; return this; }
    public String getParentId() { return parentId; }
    public Categories setParentId(String parentId) { this.parentId = parentId; return this; }

    public static class Builder extends QueryBuilder<Builder, Categories, String> {
        @Override public boolean isIdGenerated() { return false; }
    }
}
