package com.entityassist.website.pages.aside;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;

@NgComponent("entityassist-getting-started-aside")
@NgRoutable(path = "getting-started", outlet = "aside")
public class GettingStartedAsidePage extends WebsiteAside<GettingStartedAsidePage> {
    public GettingStartedAsidePage() { super("prerequisites", "Prerequisites", "dependency", "Dependency", "entity", "Your entity", "session", "Your first query", "transaction", "Transactions", "next-steps", "Next steps"); }
}
