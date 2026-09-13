package example;

import com.entityassist.EA;
import com.entityassist.enumerations.Operand;
import com.entityassist.enumerations.OrderByType;
import io.smallrye.mutiny.Uni;
import jakarta.persistence.criteria.JoinType;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.reactive.mutiny.Mutiny;
import java.math.BigDecimal;
import java.util.List;

public class QueryExamples {
    // region typed-filter
    public Uni<List<Customers>> activeCustomers(Mutiny.SessionFactory factory) {
        return factory.withSession(session ->
            new Customers().builder(session)
                .where(Customers_.active, Operand.Equals, true)
                .where(Customers_.name, Operand.Like, "A%")
                .orderBy(Customers_.name, OrderByType.ASC)
                .orderBy(Customers_.id, OrderByType.ASC)
                .setFirstResults(0).setMaxResults(20)
                .getAll()
        );
    }
    // endregion

    // region static-entry
    public Uni<List<Customers>> staticEntry(Mutiny.SessionFactory factory) {
        return factory.withSession(session ->
            EA.from(Customers.class).withSession(session)
                .withQueryBuilderOptions(q -> q
                    .where(Customers_.active, Operand.Equals, true)
                    .orderBy(Customers_.name, OrderByType.ASC)
                    .setMaxResults(20))
                .getAll()
        );
    }
    // endregion

    // region or-filter
    public Uni<List<Customers>> twoRegions(Mutiny.SessionFactory factory) {
        return factory.withSession(session ->
            new Customers().builder(session)
                .where(Customers_.region, Operand.Equals, "North")
                .or(Customers_.region, Operand.Equals, "South")
                .getAll()
        );
    }
    // endregion

    // region list-filter
    public Uni<List<Customers>> selectedCustomers(Mutiny.SessionFactory factory) {
        return factory.withSession(session ->
            new Customers().builder(session)
                .where(Customers_.id, Operand.InList, List.of("c1", "c2"))
                .where(Customers_.region, Operand.NotNull, (String) null)
                .getAll()
        );
    }
    // endregion

    // region dot-filter
    public Uni<List<Orders>> customerOrders(Mutiny.SessionFactory factory) {
        return factory.withSession(session ->
            new Orders().builder(session)
                .where("customer.name", Operand.Equals, "Aster Foods")
                .where(Orders_.status, Operand.Equals, "OPEN")
                .where(Orders_.total, Operand.GreaterThan, new BigDecimal("100.00"))
                .setMaxResults(50).getAll()
        );
    }
    // endregion

    // region inner-join
    public Uni<List<Orders>> innerJoin(Mutiny.SessionFactory factory) {
        return factory.withSession(session -> {
            var customer = new Customers().builder(session)
                .where(Customers_.active, Operand.Equals, true);
            return new Orders().builder(session)
                .join(Orders_.customer, customer, JoinType.INNER)
                .where(Orders_.status, Operand.Equals, "OPEN")
                .getAll();
        });
    }
    // endregion

    // region left-join
    public Uni<List<Orders>> leftJoin(Mutiny.SessionFactory factory) {
        return factory.withSession(session ->
            new Orders().builder(session)
                .join(Orders_.customer, JoinType.LEFT)
                .where(Orders_.status, Operand.Equals, "OPEN")
                .getAll()
        );
    }
    // endregion

    // region on-join
    public Uni<List<Orders>> leftJoinOn(Mutiny.SessionFactory factory) {
        return factory.withSession(session -> {
            var orders = new Orders().builder(session);
            var joinedCustomer = new Customers().builder(session);
            // ON predicates must refer to this join's root, not a separate customer root.
            var customerJoin = orders.getRoot().join(Orders_.customer.getName(), JoinType.LEFT);
            var on = new Customers().builder(session);
            on.reset(customerJoin);
            on.where(Customers_.active, Operand.Equals, true);
            var join = new com.entityassist.querybuilder.builders.JoinExpression(
                joinedCustomer, JoinType.LEFT, Orders_.customer);
            join.setGeneratedRoot(customerJoin);
            // Register the already-created join directly, without creating a second join.
            join.setOnBuilder(on);
            orders.getJoins().add(join);
            return orders.where(Orders_.status, Operand.Equals, "OPEN").getAll();
        });
    }
    // endregion

    // region count
    public Uni<Long> count(Mutiny.SessionFactory factory) {
        return factory.withSession(session ->
            new Customers().builder(session)
                .where(Customers_.active, Operand.Equals, true).getCount()
        );
    }
    // endregion

    // region projection
    public Uni<List<String>> customerNames(Mutiny.SessionFactory factory) {
        return factory.withSession(session ->
            new Customers().builder(session)
                .selectColumn(Customers_.name)
                .orderBy(Customers_.name, OrderByType.ASC)
                .getResultStream(String.class)
        ); // Despite its name, getResultStream returns Uni<List<T>>.
    }
    // endregion

    // region aggregate
    public Uni<BigDecimal> openOrderTotal(Mutiny.SessionFactory factory) {
        return factory.withSession(session ->
            new Orders().builder(session)
                .where(Orders_.status, Operand.Equals, "OPEN")
                .selectSum(Orders_.total).get(BigDecimal.class)
        );
    }
    // endregion

    // region group
    public Uni<List<Object[]>> totalsByStatus(Mutiny.SessionFactory factory) {
        return factory.withSession(session ->
            new Orders().builder(session)
                .selectColumn(Orders_.status, "status")
                .selectSum(Orders_.total, "total")
                .groupBy(Orders_.status)
                .getResultStream(Object[].class)
        );
    }
    // endregion

    // region cte
    public Uni<List<Customers>> cte(Mutiny.SessionFactory factory) {
        return factory.withSession(session -> {
            var active = new Customers().builder(session)
                .where(Customers_.active, Operand.Equals, true);
            return new Customers().builder(session)
                .with("active_customers", active)
                .where(Customers_.name, Operand.Like, "A%")
                .orderBy(Customers_.name, OrderByType.ASC)
                .getAll();
        });
    }
    // endregion

    // region recursive
    public Uni<List<Categories>> subtree(Mutiny.SessionFactory factory) {
        return factory.withSession(session -> {
            var anchor = new Categories().builder(session)
                .where(Categories_.id, Operand.Equals, "root");
            return new Categories().builder(session)
                .withRecursiveHierarchy("subtree", anchor, Categories_.parentId.getName())
                .orderBy(Categories_.name, OrderByType.ASC)
                .getAll();
        });
    }
    // endregion

    // region recursive-link
    public Uni<List<Categories>> linkedDescendants(Mutiny.SessionFactory factory) {
        return factory.withSession(session -> {
            var categories = new Categories().builder(session);
            var anchor = new Categories().builder(session)
                .where(Categories_.id, Operand.Equals, "root");
            var cb = (HibernateCriteriaBuilder) categories.getCriteriaBuilder();
            return categories.withRecursive("reachable", anchor, cte -> {
                // withRecursive's producer returns AbstractQuery<Object>.
                var recursive = cb.createQuery(Object.class);
                var link = recursive.from(CategoryLinks.class);
                var previous = recursive.from(cte);
                var childId = link.get(CategoryLinks_.childId);
                childId.alias("reachable_id"); // CTE name + "_id" is the ID projection alias.
                recursive.select(childId);
                recursive.where(cb.equal(link.get(CategoryLinks_.parentId),
                    previous.get("reachable_id")));
                return recursive;
            }, false).getAll(); // UNION DISTINCT deduplicates repeated reachable IDs.
        });
    }
    // endregion

    // region find
    public Uni<Customers> find(Mutiny.SessionFactory factory, String id) {
        return factory.withSession(session -> new Customers().builder(session).find(id).get());
    }
    // endregion

    // region persist
    public Uni<Customers> create(Mutiny.SessionFactory factory) {
        var customer = new Customers().setId("c1").setName("Aster Foods")
            .setActive(true).setRegion("North");
        return factory.withTransaction((session, tx) -> customer.builder(session).persist(customer));
    }
    // endregion

    // region update
    public Uni<Customers> rename(Mutiny.SessionFactory factory, String id, String name) {
        return factory.withTransaction((session, tx) ->
            new Customers().builder(session).find(id).get()
                .chain(customer -> customer.setName(name).builder(session).update())
        );
    }
    // endregion

    // region delete
    public Uni<Integer> deleteInactive(Mutiny.SessionFactory factory) {
        return factory.withTransaction((session, tx) ->
            new Customers().builder(session)
                .where(Customers_.active, Operand.Equals, false).delete()
        );
    }
    // endregion

    // region stateless
    public Uni<Void> importCustomers(Mutiny.SessionFactory factory, List<Customers> customers) {
        return factory.withStatelessTransaction(session -> {
            Uni<Void> writes = Uni.createFrom().voidItem();
            for (var customer : customers) {
                writes = writes.chain(() -> customer.builder(session).persist(customer).replaceWithVoid());
            }
            return writes;
        }); // Sequential operations, one transaction, no managed persistence context.
    }
    // endregion
}
