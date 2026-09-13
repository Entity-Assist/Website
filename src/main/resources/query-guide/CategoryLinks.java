package example;

import com.entityassist.BaseEntity;
import com.entityassist.querybuilder.QueryBuilder;
import jakarta.persistence.*;

@Entity
@Table(name = "guide_categorylinks")
public class CategoryLinks extends BaseEntity<CategoryLinks, CategoryLinks.Builder, String> {
    @Id private String id;
    private String parentId;
    private String childId;

    @Override public String getId() { return id; }
    @Override public CategoryLinks setId(String id) { this.id = id; return this; }
    public String getParentId() { return parentId; }
    public CategoryLinks setParentId(String parentId) { this.parentId = parentId; return this; }
    public String getChildId() { return childId; }
    public CategoryLinks setChildId(String childId) { this.childId = childId; return this; }

    public static class Builder extends QueryBuilder<Builder, CategoryLinks, String> {
        @Override public boolean isIdGenerated() { return false; }
    }
}
