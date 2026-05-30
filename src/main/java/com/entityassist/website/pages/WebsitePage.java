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

import java.util.ArrayList;
import java.util.List;

@NgComponentReference(App.class)
public abstract class WebsitePage<J extends WebsitePage<J>> extends DivSimple<J> implements INgComponent<J>
{
    protected WebsitePage()
    {
        addClass("website-content");
        addStyle("padding", "0 var(--wa-spacing-x-large) var(--wa-spacing-x-large) var(--wa-spacing-x-large)");
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
}
