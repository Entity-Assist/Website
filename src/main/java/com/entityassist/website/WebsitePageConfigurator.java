package com.entityassist.website;

import com.jwebmp.core.Page;
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

@NgStyleSheet(value = "/assets/base.css", name = "EntityAssistBase", sortOrder = 200)
@NgStyleSheet(value = "/assets/layout.css", name = "EntityAssistLayout", sortOrder = 201)
@NgStyleSheet(value = "/assets/components.css", name = "EntityAssistComponents", sortOrder = 202)
@NgStyleSheet(value = "/assets/features.css", name = "EntityAssistFeatures", sortOrder = 203)
@NgStyleSheet(value = "/assets/code.css", name = "EntityAssistCode", sortOrder = 204)
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
        page.addCssReference(new CSSReference("EntityAssistBase", 1.0, "/assets/base.css"));
        page.addCssReference(new CSSReference("EntityAssistLayout", 1.0, "/assets/layout.css"));
        page.addCssReference(new CSSReference("EntityAssistComponents", 1.0, "/assets/components.css"));
        page.addCssReference(new CSSReference("EntityAssistFeatures", 1.0, "/assets/features.css"));
        page.addCssReference(new CSSReference("EntityAssistCode", 1.0, "/assets/code.css"));
        WebAwesomePageConfigurator.setWaKitCode("6ea54e8336d3409b");
        FontAwesome5ProPageConfigurator.setKitCode("3f59d88b7f");
        Page<?> p = (Page<?>) page;
        p.getOptions().setFavIcon("/assets/entityassist-logo.svg");
        p.getOptions().setIcon("/assets/entityassist-logo.svg", "any");
        return page;
    }

    @Override
    public IPage<?> configureAngular(IPage<?> page) {
        return configure(page);
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
