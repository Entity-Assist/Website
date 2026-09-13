package com.entityassist.website;

import com.jwebmp.core.Page;
import com.jwebmp.core.base.angular.client.annotations.typescript.TsDependency;
import com.jwebmp.core.base.angular.client.annotations.angularconfig.NgStyleSheet;
import com.jwebmp.core.base.angular.client.annotations.boot.NgBootImportProvider;
import com.jwebmp.core.base.angular.client.annotations.boot.NgBootImportReference;
import com.jwebmp.core.base.angular.client.annotations.references.NgComponentReference;
import com.jwebmp.core.base.angular.client.services.TypescriptIndexPageConfigurator;
import com.jwebmp.core.base.references.CSSReference;
import com.jwebmp.core.services.IPage;
import com.jwebmp.core.services.IPageConfigurator;
import com.jwebmp.plugins.fontawesome5pro.FontAwesome5ProPageConfigurator;
import com.jwebmp.webawesome.components.WebAwesomePageConfigurator;

@TsDependency(value = "katex", version = "^0.16.0", overrides = true)
@NgStyleSheet(value = "public/base.css", name = "EntityAssistBase", sortOrder = 200)
@NgStyleSheet(value = "public/layout.css", name = "EntityAssistLayout", sortOrder = 201)
@NgStyleSheet(value = "public/components.css", name = "EntityAssistComponents", sortOrder = 202)
@NgStyleSheet(value = "public/features.css", name = "EntityAssistFeatures", sortOrder = 203)
@NgStyleSheet(value = "public/code.css", name = "EntityAssistCode", sortOrder = 204)
@NgComponentReference(MarkdownClipboardButton.class)
@NgBootImportProvider(value = "provideMarkdown({ mermaidOptions: { provide: MERMAID_OPTIONS, useValue: { startOnLoad: false } }, clipboardOptions: { provide: CLIPBOARD_OPTIONS, useValue: { buttonComponent: MarkdownClipboardButton } } })", overrides = true)
@NgBootImportReference(value = "provideMarkdown", reference = "ngx-markdown")
@NgBootImportReference(value = "MERMAID_OPTIONS", reference = "ngx-markdown")
@NgBootImportReference(value = "CLIPBOARD_OPTIONS", reference = "ngx-markdown")
@NgBootImportReference(value = "MarkdownClipboardButton", reference = "./com/entityassist/website/MarkdownClipboardButton/MarkdownClipboardButton")
public class WebsitePageConfigurator implements IPageConfigurator<WebsitePageConfigurator>, TypescriptIndexPageConfigurator<WebsitePageConfigurator>
{
    @Override
    public IPage<?> configure(IPage<?> page)
    {
        page.addCssReference(new CSSReference("EntityAssistBase", 1.0, "/base.css"));
        page.addCssReference(new CSSReference("EntityAssistLayout", 1.0, "/layout.css"));
        page.addCssReference(new CSSReference("EntityAssistComponents", 1.0, "/components.css"));
        page.addCssReference(new CSSReference("EntityAssistFeatures", 1.0, "/features.css"));
        page.addCssReference(new CSSReference("EntityAssistCode", 1.0, "/code.css"));
        return configureOptions(page);
    }

    private IPage<?> configureOptions(IPage<?> page) {
        WebAwesomePageConfigurator.setWaKitCode("6ea54e8336d3409b");
        WebAwesomePageConfigurator.setBasePath("https://ka-p.webawesome.com/kit/6ea54e8336d3409b/webawesome@3.11.0/");
        FontAwesome5ProPageConfigurator.setKitCode("");
        Page<?> p = (Page<?>) page;
        p.getOptions().setDescription("Entity Assist is a fluent reactive persistence toolkit for Java. Explore entity mapping, queries, transactions and integration with GuicedEE and Hibernate Reactive.");
        p.getOptions().setFavIcon("/entityassist-logo.svg");
        p.getOptions().setIcon("/entityassist-logo.svg", "any");
        return page;
    }

    @Override
    public IPage<?> configureAngular(IPage<?> page) {
        // Angular bundles the @NgStyleSheet entries; avoid duplicate stylesheet links.
        return configureOptions(page);
    }

    @Override
    public boolean enabled()
    {
        return true;
    }

    @Override
    public Integer sortOrder()
    {
        return 200;
    }
}
