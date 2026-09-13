package com.entityassist.website.pages.aside;

import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.core.base.html.Link;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.angular.client.annotations.references.NgImportReference;
import com.jwebmp.core.base.angular.client.annotations.structures.NgMethod;
import com.jwebmp.core.base.angular.client.annotations.structures.NgField;

@NgImportReference(value = "DOCUMENT", reference = "@angular/common")
@NgImportReference(value = "inject", reference = "@angular/core")
@NgField("private document = inject(DOCUMENT);")
@NgMethod("""
        scrollTo(event: Event, id: string) {
            event.preventDefault();
            const target = this.document.getElementById(id);
            if (target) {
                target.scrollIntoView({behavior: 'auto', block: 'start'});
                target.setAttribute('tabindex', '-1');
                target.focus({preventScroll: true});
            }
        }
        """)
public abstract class WebsiteAside<J extends WebsiteAside<J>> extends DivSimple<J> implements INgComponent<J> {
    protected WebsiteAside(String... idsAndLabels) {
        setTag("nav"); addClass("page-aside");
        addAttribute("aria-label", "On this page");
        var title = new DivSimple<>(); title.setTag("strong"); title.setText("On this page"); add(title);
        var list = new DivSimple<>(); list.setTag("ul");
        list.addClass("site-navigation-links wa-stack wa-gap-2xs"); add(list);
        for (int i = 0; i < idsAndLabels.length; i += 2) {
            var item = new DivSimple<>(); item.setTag("li");
            var link = new Link<>(); link.setTag("a");
            link.addClass("wa-body-xs");
            link.addAttribute("href", "#" + idsAndLabels[i]);
            link.addAttribute("(click)", "scrollTo($event, '" + idsAndLabels[i] + "')");
            link.setText(idsAndLabels[i + 1]); item.add(link); list.add(item);
        }
    }
}
