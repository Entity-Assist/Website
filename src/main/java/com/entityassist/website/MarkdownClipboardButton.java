package com.entityassist.website;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.references.NgComponentReference;
import com.jwebmp.core.base.angular.client.annotations.references.NgImportReference;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.webawesome.components.Variant;
import com.jwebmp.webawesome.components.button.Appearance;
import com.jwebmp.webawesome.components.button.WaButton;
import com.jwebmp.webawesome.components.icon.WaIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NgComponent(value = "markdown-clipboard-button", standalone = true)
@NgImportReference(value = "Input", reference = "@angular/core")
@NgImportReference(value = "CommonModule", reference = "@angular/common")
@NgComponentReference(value = App.class)
public class MarkdownClipboardButton extends DivSimple<MarkdownClipboardButton> implements INgComponent<MarkdownClipboardButton>
{
    public MarkdownClipboardButton()
    {
        var btn = new WaButton<>();
        btn.setVariant(Variant.Neutral);
        btn.setAppearance(Appearance.Plain);
        btn.setSize(com.jwebmp.webawesome.components.Size.Small);
        btn.addAttribute("(click)", "onCopy()");
        btn.addClass("markdown-copy-btn");

        var icon = new WaIcon<>();
        icon.addAttribute("[name]", "copied ? 'check-lg' : 'clipboard'");
        btn.add(icon);

        add(btn);
    }

    @Override
    public List<String> fields()
    {
        var f = new ArrayList<String>(INgComponent.super.fields());
        f.add("@Input() textToCopy: string = '';");
        f.add("copied = false;");
        return f;
    }

    @Override
    public Set<String> moduleImports() {
        var s = INgComponent.super.moduleImports();
        s.add("CommonModule");
        s.add("WaButtonDirective");
        s.add("WaIconDirective");
        return s;
    }

    @Override
    public List<String> methods()
    {
        var m = new ArrayList<String>();
        m.add("""
                onCopy() {
                    if (!this.textToCopy) return;
                    navigator.clipboard.writeText(this.textToCopy).then(() => {
                        this.copied = true;
                        setTimeout(() => this.copied = false, 2000);
                    });
                }
                """);
        return m;
    }
}
