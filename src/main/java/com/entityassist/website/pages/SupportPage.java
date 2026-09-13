package com.entityassist.website.pages;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.webawesome.components.WaCluster;
import com.jwebmp.webawesome.components.PageSize;

@NgComponent("entityassist-support")
@NgRoutable(path = "support")
public class SupportPage extends WebsitePage<SupportPage> {
    public SupportPage() {
        intro("RESOURCES & SUPPORT", "Build with the source in reach", "Find the implementation, review installation details and bring a reproducible example when you need help.");
        var source = section("source", "Source and documentation", "Entity Assist Reactive is open source under the Apache License 2.0. The repository includes API documentation, examples and integration tests.");
        var links = new WaCluster<>(); links.setGap(PageSize.Small);
        links.add(external("Source & README", "https://github.com/Entity-Assist/EntityAssistReactive"));
        links.add(external("Maven Central", "https://central.sonatype.com/artifact/com.entityassist/entity-assist-reactive"));
        links.add(external("Apache 2.0 license", "https://github.com/Entity-Assist/EntityAssistReactive/blob/master/LICENSE"));
        source.add(links);
        section("help", "Report an issue", "Include your Entity Assist and GuicedEE versions, Java version, database, entity mappings, minimal query and relevant error. Remove credentials and customer data from the example.")
                .add(external("Open the issue tracker", "https://github.com/Entity-Assist/EntityAssistReactive/issues"));
        section("contribute", "Help improve the toolkit", "Documentation fixes, focused bug reports and reproducible test cases help other teams adopt the library. Review existing issues before starting a contribution.")
                .add(external("Support the maintainer", "https://www.patreon.com/GedMarc"));
        section("versions", "Choose a version deliberately", "The local library source currently identifies itself as 2.0.1-SNAPSHOT. A snapshot is a development build. Check Maven Central for published artifacts and the repository for snapshot access; do not infer a library release from this website's image version.");
    }
}
