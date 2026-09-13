package com.entityassist.website.pages;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;

/** Guide snippets are read from the same Java sources compiled by examples/pom.xml. */
@NgComponent("entityassist-query-guide")
@NgRoutable(path = "query-guide")
public class QueryGuidePage extends WebsitePage<QueryGuidePage> {
    public QueryGuidePage() {
        intro("QUERY GUIDE", "Type-safe queries start with your entity model",
                "Customers_.name is the heart of Entity Assist: a generated JPA metamodel attribute, carried through filters, ordering, joins and projections. Compose the current fluent API while keeping field references connected to your Java model.");
        var model = section("metamodel", "Generate the JPA metamodel", "JPAModelGen generates Customers_ from Customers and Orders_ from Orders. Renaming a mapped field then makes stale attribute references fail compilation. With Hibernate 7, configure org.hibernate.orm:hibernate-processor and org.hibernate.processor.HibernateProcessor; do not hand-maintain the generated classes.");
        model.add(code("xml", """
                <!-- Add to the compiler plugin of your entity module.
                     maven.hibernate.version is supplied by the GuicedEE parent;
                     otherwise define it to match your Hibernate ORM version. -->
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>3.14.1</version>
                    <configuration>
                        <release>25</release>
                        <annotationProcessorPaths>
                            <path>
                                <groupId>org.hibernate.orm</groupId>
                                <artifactId>hibernate-processor</artifactId>
                                <version>${maven.hibernate.version}</version>
                            </path>
                        </annotationProcessorPaths>
                        <annotationProcessors>
                            <annotationProcessor>org.hibernate.processor.HibernateProcessor</annotationProcessor>
                        </annotationProcessors>
                    </configuration>
                </plugin>
                """));
        model.add(bodyText("Run mvn compile and enable annotation processing in your IDE. Maven writes Customers_.java to target/generated-sources/annotations. Keep any existing Lombok processors alongside Hibernate's processor. The lowercase Customers_.name is an Attribute, while an uppercase NAME constant, when generated, is only a String. Hibernate initializes the static attributes when it builds the persistence model.", "m"));
        model.add(code("java", """
                // Generated shape, shown for explanation only:
                public abstract class Customers_ {
                    public static volatile SingularAttribute<Customers, String> name;
                    public static volatile SingularAttribute<Customers, Boolean> active;
                }
                // Use Customers_.name directly in Entity Assist.
                """));
        section("model", "The mapped example model", "Customers has id, name, active and region. Orders has a many-to-one customer, a BigDecimal total and a status. Categories uses parentId for an adjacency list; CategoryLinks models a separate parent-to-child relationship table. These assigned-ID entities are the exact sources used to compile the guide.")
                .add(code("java", exampleSource("Orders.java")));
        var typed = section("filters", "Typed filters, ordering and pagination", "Prefer generated attributes for mapped fields. All terminal operations remain inside the session's reactive scope. Add a unique tie-breaker to the order when paging; concurrent data changes can still affect offset pages.");
        typed.add(example("typed-filter"));
        typed.add(bodyText("The static EA.from entry point preserves the concrete builder type too. Import EA from com.entityassist and Operand / OrderByType from com.entityassist.enumerations.", "m"));
        typed.add(example("static-entry"));
        var operators = section("operators", "Comparisons, membership and OR", "where calls add conjunctions. or groups with the preceding filter; the overload with nest controls grouping. Start with a simple expression, and verify more complex grouping against your expected SQL and results.");
        operators.add(example("or-filter"));
        operators.add(example("list-filter"));
        operators.add(bodyText("Operands: Equals, NotEquals, Like, NotLike, Null, NotNull, LessThan, LessThanEqualTo, GreaterThan, GreaterThanEqualTo, InList and NotInList. Supply your own % wildcards for Like. Cast a null value to its field type to disambiguate scalar, array and collection overloads. Define an explicit application policy for an empty selection list.", "m"));
        var dot = section("relationships", "Dot-path filters alongside metamodel attributes", "Use a mapped path when traversing a relationship: customer.name, or a deeper path such as customer.address.city when that association exists. Dot paths resolve at runtime; metamodel attributes provide compile-time field checking. A path filter does not eagerly fetch the relationship.");
        dot.add(example("dot-filter"));
        dot.add(bodyText("getAttribute(\"name\") is the runtime lookup alternative when the field is dynamic. For normal entity code, use Customers_.name. Never resolve a related entity by casting or blocking on a Uni merely to construct a filter.", "m"));
        var joins = section("joins", "Explicit joins with generated attributes", "Orders_.customer identifies the association to join. Configure the related entity's builder with its own metamodel, then attach it to the root query. Filter the joined entity on its builder so attributes are resolved against the correct root.");
        joins.add(example("inner-join"));
        joins.add(bodyText("join(attribute, builder) defaults to INNER; pass JoinType.INNER, LEFT or RIGHT explicitly when needed. RIGHT support depends on the provider and database. A LEFT join retains orders with no customer until a WHERE predicate on the joined customer removes them.", "m"));
        joins.add(example("left-join"));
        joins.add(bodyText("ON predicates control which related rows match while retaining the left row. WHERE predicates filter the resulting rows. The advanced example below attaches ON predicates to the actual generated join root. A join alone does not imply a fetch join, and collection joins can multiply result rows.", "m"));
        joins.add(example("on-join"));
        var projections = section("aggregates", "Counts, projections and grouped reporting", "Use the database to reduce data before it reaches your application. Match the result type to the projection: String for a name, BigDecimal for a decimal sum, and Object[] for multiple selected values in selection order.");
        projections.add(example("count"));
        projections.add(example("projection"));
        projections.add(example("aggregate"));
        projections.add(example("group"));
        projections.add(bodyText("Also available: selectMin, selectMax, selectAverage, selectCount, selectCountDistinct, selectSumAsDouble and selectSumAsLong, with optional aliases. SQL SUM may return null when no rows match. getResultStream(Class) returns Uni<List<T>>, rather than a streaming Multi. Limit large result sets deliberately.", "m"));
        var ctes = section("ctes", "Non-recursive common table expressions", "with(name, definition) projects the definition's entity ID and restricts the outer builder to those IDs. Both builders target the same entity type. You still get managed entities and can add normal filters, ordering, projections or counts.");
        ctes.add(example("cte"));
        ctes.add(code("sql", """
                -- Illustrative SQL shape; aliases vary by Hibernate dialect.
                WITH active_customers AS (
                    SELECT id FROM guide_customers WHERE active = true
                )
                SELECT c.* FROM guide_customers c
                WHERE c.id IN (SELECT id FROM active_customers)
                  AND c.name LIKE 'A%'
                ORDER BY c.name;
                """));
        ctes.add(bodyText("CTEs require Hibernate 7 and a single @Id field. Use a fresh definition builder for each registration: the API rebinds its criteria root. Multiple with calls accumulate constraints. The definition contributes its filters; do not treat ordering or pagination on it as a top-N CTE. A CTE is a query composition tool, not an automatic performance improvement.", "m"));
        var recursive = section("hierarchies", "Recursive CTEs for adjacency lists", "Select the starting categories with Categories_.id. The current hierarchy API takes a String for the parent path, so use Categories_.parentId.getName() to derive it from the generated model. The result contains the anchor and all descendants.");
        recursive.add(example("recursive"));
        recursive.add(bodyText("For a mapped parent association, the path can be parent.id. withRecursiveHierarchy uses UNION ALL; ensure the hierarchy is acyclic and bound the workload. Outer setMaxResults limits returned rows, not recursion depth. Traversing a separate link table needs the lower-level API below.", "m"));
        var links = section("recursive-links", "Recursive CTEs over a relationship table", "withRecursive exposes a Hibernate criteria producer. Keep the outer query and anchor on Categories; the recursive member can read CategoryLinks. Project the next category ID with the alias reachable_id, where reachable is the chosen CTE name.");
        links.add(code("java", exampleSource("CategoryLinks.java")));
        links.add(example("recursive-link"));
        links.add(bodyText("false selects UNION DISTINCT; true selects UNION ALL. Deduplication is useful for repeated paths through a graph, but database support and query plans still need validation. The producer returns AbstractQuery<Object>, while the selected childId keeps the ID's mapped type. This example walks descendants; reverse the parent/child comparison and selection to walk ancestors.", "m"));
        var writes = section("writes", "Find, create, update and delete", "find(id) constrains a builder; get executes it. A missing result follows the reactive no-result failure path. Use getAll for a collection and handle failures through the returned Uni. Do not assume get is a uniqueness assertion; the current implementation limits results.");
        writes.add(example("find")); writes.add(example("persist")); writes.add(example("update")); writes.add(example("delete"));
        writes.add(bodyText("The assigned-ID model requires an ID before persistence. validateEntity() exposes Bean Validation violations if your workflow needs validation. update() merges the entity; it is not a bulk field-set DSL. delete(entity) removes one entity. Bulk delete() rejects missing filters; truncate() intentionally removes all rows. Foreign-key constraints still apply. Complete the transaction before reporting success.", "m"));
        var sessions = section("session-lifecycle", "Stateless work and session lifecycle", "Use managed sessions for normal entity work. Stateless sessions avoid the first-level persistence context and require deliberate write and transaction boundaries. Process operations sequentially on a session; the example assumes a bounded import list.");
        sessions.add(example("stateless"));
        sessions.add(bodyText("Return the Uni to your reactive caller. Avoid await on an event loop and parallel work on one session. For very large imports, split into bounded transactions with an explicit retry/idempotency policy. setReadOnly and cache hints are optional query settings; configure and verify provider support before relying on them.", "m"));
        var sources = section("example-sources", "Use the complete example sources", "Open the complete sources below, including entity mappings and imports. Add them to your configured persistence application and run the Hibernate annotation processor to generate their metamodel classes.")
                ;
        for (String file : new String[]{"Customers.java", "Orders.java", "Categories.java", "CategoryLinks.java", "QueryExamples.java"}) {
            sources.add(external("View " + file, "/examples/" + file));
        }
    }
}
