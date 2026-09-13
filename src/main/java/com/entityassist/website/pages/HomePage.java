package com.entityassist.website.pages;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.webawesome.components.*;
import com.jwebmp.webawesome.components.button.Appearance;

@NgComponent("entityassist-home")
@NgRoutable(path = "home", isDefault = true)
public class HomePage extends WebsitePage<HomePage> {
    public HomePage() {
        intro("FLUENT REACTIVE PERSISTENCE", "Your domain. Your queries. One fluent API.",
                "Build data-driven Java services with readable queries and non-blocking persistence. Entity Assist connects your domain entities to Hibernate Reactive, so you can focus on the behaviour your customers need.");
        var actions = new WaCluster<>();
        actions.setGap(PageSize.Small);
        actions.add(buildCta("Get started", "/getting-started", Variant.Brand, Appearance.Filled));
        actions.add(buildCta("Explore the query guide", "/query-guide", Variant.Brand, Appearance.Outlined));
        getMain().add(actions);
        var value = section("why-entity-assist", "Less persistence plumbing. More domain logic.",
                "A Java library for teams building APIs, business applications and data services on the GuicedEE stack.");
        value.add(cards("Queries that read like intent", "Compose filters, relationships, ordering and pagination in a reusable builder tied to your entity.",
                "Reactive from end to end", "Return Mutiny Uni results through your service pipeline, with explicit sessions and transaction boundaries.",
                "Built for your existing model", "Use Jakarta Persistence mappings, Hibernate criteria and Java types. Keep access to the underlying persistence stack."));
        var example = section("in-practice", "From a business question to a query", "Find the first 20 active customers, ordered by name. The query stays inside the reactive session.");
        example.add(code("java", """
                sessionFactory.withSession(session -> {
                    var customers = new Customers().builder(session);
                    return customers
                        .where(Customers_.active, Operand.Equals, true)
                        .orderBy(Customers_.name, OrderByType.ASC)
                        .orderBy(Customers_.id, OrderByType.ASC)
                        .setMaxResults(20)
                        .getAll();
                });
                """));
        example.add(bodyText("Customers is your mapped entity. The getting-started guide shows its complete definition and required imports.", "s"));
        var ecosystem = section("ecosystem", "One connected Java ecosystem", "Use Entity Assist for persistence, GuicedEE for services, JWebMP for the interface, and Activity Master for enterprise domain models.");
        var links = new WaCluster<>(); links.setGap(PageSize.Small);
        links.add(external("GuicedEE", "https://guicedee.com"));
        links.add(external("JWebMP", "https://jwebmp.com"));
        links.add(external("Activity Master", "https://activity-master.com"));
        ecosystem.add(links);
        var next = section("start-building", "Start with one entity", "Follow a small example, then explore filters, transactions, aggregates and hierarchy queries as your application grows.");
        next.add(buildCta("Create your first query", "/getting-started", Variant.Brand, Appearance.Filled));
    }
}
