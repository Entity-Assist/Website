package com.entityassist.website.pages;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.components.NgIf;

@NgComponent("entityassist-getting-started")
@NgRoutable(path = "getting-started")
public class GettingStartedPage extends WebsitePage<GettingStartedPage> {
    public GettingStartedPage() {
        intro("GETTING STARTED", "Your first entity and reactive query", "Add Entity Assist to a GuicedEE persistence application, map a small domain object and run a query inside a managed session.");
        section("prerequisites", "1. Prepare your application", "Use Java 25+, Maven 4 and a configured GuicedEE persistence module with a Hibernate Reactive Mutiny.SessionFactory. The library includes the PostgreSQL reactive driver. Your database schema and credentials belong in your application's persistence configuration.")
                .add(external("GuicedEE persistence setup", "https://guicedee.com/#/modules"));
        var dependency = section("dependency", "2. Add the dependency", "This example uses the version in the local EntityAssist/pom.xml: 2.0.1-SNAPSHOT. Install that source locally first, or replace the version with a published release verified on Maven Central. The website version is independent.");
        dependency.add(code("shell", "mvn -f EntityAssist/pom.xml -DskipTests install"));
        var maven = new NgIf("!app.useGradle()");
        maven.add(code("xml", """
                <dependency>
                    <groupId>com.entityassist</groupId>
                    <artifactId>entity-assist-reactive</artifactId>
                    <version>2.0.1-SNAPSHOT</version>
                </dependency>
                """)); dependency.add(maven);
        var gradle = new NgIf("app.useGradle()");
        gradle.add(code("kotlin", """
                repositories {
                    mavenLocal() // for the locally installed development snapshot
                    mavenCentral()
                }
                dependencies {
                    implementation("com.entityassist:entity-assist-reactive:2.0.1-SNAPSHOT")
                }
                """)); dependency.add(gradle);
        dependency.add(bodyText("Use the Maven / Gradle switch in the header to change this example. For a published release, prefer mavenCentral without mavenLocal. Keep the GuicedEE BOM aligned with your application; the current source parent uses 2.2.2.", "s"));
        section("entity", "3. Define an entity and generate its metamodel", "Each entity declares its builder type. Configure Hibernate's annotation processor to generate Customers_, then reference Customers_.name and Customers_.active directly in your queries. The Query guide includes the Maven processor configuration.")
                .add(code("java", exampleSource("Customers.java")));
        section("session", "4. Query inside the session", "Inject the session factory for your configured database. Include the entity in your persistence unit and make its package available to Hibernate and GuicedEE scanning.")
                .add(code("java", """
                package example;

                import com.entityassist.enumerations.Operand;
                import com.entityassist.enumerations.OrderByType;
                import io.smallrye.mutiny.Uni;
                import org.hibernate.reactive.mutiny.Mutiny;
                import java.util.List;

                public class CustomersQueries {
                    public Uni<List<Customers>> activeCustomers(Mutiny.SessionFactory sessionFactory) {
                        return sessionFactory.withSession(session -> {
                            var customers = new Customers().builder(session);
                            return customers
                                .where(Customers_.active, Operand.Equals, true)
                                .orderBy(Customers_.name, OrderByType.ASC)
                                .orderBy(Customers_.id, OrderByType.ASC)
                                .setMaxResults(20)
                                .getAll();
                        });
                    }
                }
                """));
        section("transaction", "5. Persist within a transaction", "Return the composed Uni to your reactive caller. Acknowledge success after the transaction completes, and propagate failures through the same pipeline.")
                .add(code("java", """
                var customer = new Customers().setId("customer-001")
                    .setName("Aster Foods").setActive(true);

                return sessionFactory.withTransaction((session, tx) ->
                    customer.builder(session).persist(customer)
                );
                """));
        section("next-steps", "Next steps", "Do not block an event-loop thread to resolve a Uni, or share a session across parallel operations. Test queries and transaction rollback against your configured database. For a modular application, declare requires com.entityassist and open your entity package for reflective mapping.")
                .add(external("Source examples and integration tests", "https://github.com/Entity-Assist/EntityAssistReactive/tree/master/src/test"));
    }
}
