package com.entityassist.website.pages.aside;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;

@NgComponent("entityassist-query-guide-aside")
@NgRoutable(path = "query-guide", outlet = "aside")
public class QueryGuideAsidePage extends WebsiteAside<QueryGuideAsidePage> {
    public QueryGuideAsidePage() { super("metamodel", "Generate the metamodel", "model", "Example model", "filters", "Typed queries", "operators", "Operators & OR", "relationships", "Dot-path filters", "joins", "Joins & ON clauses", "aggregates", "Projections & aggregates", "ctes", "CTEs", "hierarchies", "Recursive hierarchies", "recursive-links", "Link-table recursion", "writes", "CRUD & bulk delete", "session-lifecycle", "Stateless sessions", "example-sources", "Example sources"); }
}
