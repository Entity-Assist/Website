package com.entityassist.website.pages;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.webawesome.components.Variant;
import com.jwebmp.webawesome.components.button.Appearance;

@NgComponent("entityassist-capabilities")
@NgRoutable(path = "capabilities")
public class CapabilitiesPage extends WebsitePage<CapabilitiesPage> {
    public CapabilitiesPage() {
        intro("CAPABILITIES", "A persistence toolkit that grows with your domain", "Start with everyday reads and writes. Add richer queries without switching to a separate repository API.");
        section("query", "Find the data your application needs", "Build composable criteria queries over mapped entities.").add(cards(
                "Filters and relationships", "Combine where and or expressions, explicit joins and dot-notation paths such as customer.name. Use metamodel attributes when compile-time field checking matters; string paths are resolved at runtime.",
                "Pagination and ordering", "Bound result sets with setFirstResults and setMaxResults. Use a deterministic order, including a unique tie-breaker, when paging.",
                "Reporting and projections", "Select individual columns, group results, and calculate counts, sums, minima, maxima and averages."));
        section("write", "Keep writes inside a clear boundary", "Reactive persistence operations return Uni values that compose with your application flow.").add(cards(
                "Transactional CRUD", "Persist, find, update and delete using a Hibernate Reactive session. Complete the transaction before acknowledging success to a caller.",
                "Bulk operations", "Use filtered criteria deletes for bounded changes. The delete guard rejects an unfiltered bulk delete; truncate is the explicit all-row operation.",
                "Stateless sessions", "Use the stateless builder overload for bulk workflows that do not need a managed persistence context. Choose transaction and batch boundaries deliberately."));
        section("model", "Express richer domain models", "Keep the underlying Jakarta Persistence and Hibernate concepts visible.").add(cards(
                "Entity-specific builders", "Self-referencing Java generics connect each entity to its concrete query builder and fluent return types.",
                "Hierarchies and CTEs", "Compose common table expressions with Hibernate 7. Traverse an adjacency-list hierarchy with withRecursiveHierarchy, or define a recursive member for a mapped link table.",
                "Validation and lifecycle", "Validate entities with Jakarta Bean Validation and use the ActiveFlag lifecycle model where your domain needs status ranges."));
        section("fit", "Is Entity Assist a fit for your project?", "It is designed for Java 25+ applications using GuicedEE, Hibernate Reactive and Mutiny. PostgreSQL is the default driver in this module. Review the persistence configuration and test your database requirements before adopting it.")
                .add(buildCta("Follow the integration guide", "/getting-started", Variant.Brand, Appearance.Filled));
    }
}
