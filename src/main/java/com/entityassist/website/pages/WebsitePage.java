package com.entityassist.website.pages;

import com.jwebmp.core.base.angular.client.annotations.references.NgComponentReference;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.entityassist.website.App;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.webawesome.components.PageSize;
import com.jwebmp.webawesome.components.Variant;
import com.jwebmp.webawesome.components.WaStack;
import com.jwebmp.webawesome.components.button.Appearance;
import com.jwebmp.webawesome.components.button.WaButton;
import com.jwebmp.webawesome.components.text.WaText;
import com.jwebmp.webawesome.tokens.WaSpaceToken;
import org.apache.commons.text.StringEscapeUtils;

import com.jwebmp.plugins.markdown.Markdown;
import com.jwebmp.webawesome.components.WaGrid;
import com.jwebmp.webawesome.components.card.WaCard;
import com.jwebmp.core.base.angular.components.modules.RouterModuleConfig;
import java.util.ArrayList;
import java.util.List;

@NgComponentReference(App.class)
@NgComponentReference(RouterModuleConfig.class)
public abstract class WebsitePage<J extends WebsitePage<J>> extends DivSimple<J> implements INgComponent<J>
{
    protected WebsitePage()
    {
        addClass("website-content");
        
        addStyle("max-width", "72rem");
    }

    @SuppressWarnings("unchecked")
    protected J getMain()
    {
        return (J) this;
    }

    protected String escapeAngular(String text)
    {
        if (text == null) return null;
        return StringEscapeUtils.escapeHtml4(text)
                                .replace("@", "&#64;")
                                .replace("{", "&#123;")
                                .replace("}", "&#125;")
                                .replace("[", "&#91;")
                                .replace("]", "&#93;")
                                .replace("(", "&#40;")
                                .replace(")", "&#41;")
                                .replace("*", "&#42;")
                                .replace("_", "&#95;");
    }

    protected WaText<?> headingText(String tag, String size, String text)
    {
        var heading = new WaText<>();
        heading.setTag(tag);
        heading.setWaHeading(size);
        heading.setText(escapeAngular(text));
        return heading;
    }

    protected WaText<?> bodyText(String text, String size)
    {
        var body = new WaText<>();
        body.setTag("p");
        body.setWaBody(size == null || size.isBlank() ? "m" : size);
        body.setText(escapeAngular(text));
        return body;
    }

    protected WaButton<?> buildCta(String label, String route, Variant variant, Appearance appearance)
    {
        WaButton<?> button = new WaButton<>(escapeAngular(label), variant);
        if (appearance != null) button.setAppearance(appearance);
        var absoluteRoute = route.startsWith("/") ? route : "/" + route;
        button.addAttribute("[routerLink]", "['" + absoluteRoute + "']");
        return button;
    }

    protected WaStack<?> section(String id, String title, String description) {
        var section = new WaStack<>();
        section.setTag("section");
        section.setID(id);
        section.setGap(PageSize.Medium);
        section.addClass("content-section");
        section.add(headingText("h2", "l", title));
        section.add(bodyText(description, "m"));
        getMain().add(section);
        return section;
    }

    protected void intro(String eyebrow, String title, String description) {
        var hero = new WaStack<>();
        hero.setGap(PageSize.Medium);
        hero.addClass("ea-hero");
        var caption = bodyText(eyebrow, "s");
        caption.addClass("hero-eyebrow");
        hero.add(caption);
        hero.add(headingText("h1", "xl", title));
        hero.add(bodyText(description, "l"));
        getMain().add(hero);
    }

    protected WaGrid<?> cards(String... titleAndDescription) {
        var grid = new WaGrid<>();
        grid.setGap(PageSize.Medium);
        grid.setMinColumnSize("min(100%, 17rem)");
        for (int i = 0; i < titleAndDescription.length; i += 2) {
            var card = new WaCard<>();
            card.addAttribute("appearance", "outlined");
            card.add(headingText("h3", "m", titleAndDescription[i]));
            card.add(bodyText(titleAndDescription[i + 1], "m"));
            grid.add(card);
        }
        return grid;
    }

    protected Markdown<?> code(String language, String source) {
        var code = new Markdown<>("```" + language + "\n" + source.strip() + "\n```");
        code.setClipboard(true);
        code.addClass("ea-code");
        return code;
    }

    protected WaButton<?> external(String label, String url) {
        var link = new WaButton<>(escapeAngular(label), Variant.Brand);
        link.setAppearance(Appearance.Outlined);
        link.setAsLink(url, "_blank", null);
        link.addAttribute("rel", "noopener noreferrer");
        return link;
    }

    protected String exampleSource(String file) {
        try (var stream = WebsitePage.class.getResourceAsStream("/query-guide/" + file)) {
            if (stream == null) throw new IllegalStateException("Missing query guide source: " + file);
            return new String(stream.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8).replace("\r\n", "\n");
        } catch (java.io.IOException ex) {
            throw new IllegalStateException("Cannot read query guide source: " + file, ex);
        }
    }

    protected Markdown<?> example(String region) {
        String source = exampleSource("QueryExamples.java");
        String marker = "// region " + region + "\n";
        int start = source.indexOf(marker);
        if (start < 0) throw new IllegalArgumentException("Missing guide example: " + region);
        int end = source.indexOf("    // endregion", start);
        if (end < 0) throw new IllegalArgumentException("Unclosed guide example: " + region);
        return code("java", source.substring(start + marker.length(), end).stripIndent());
    }
}
