package com.entityassist.website.pages;

import com.entityassist.website.App;
import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.references.NgComponentReference;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.webawesome.components.PageSize;
import com.jwebmp.webawesome.components.WaStack;

import java.util.ArrayList;
import java.util.List;

@NgComponent("entityassist-home")
@NgRoutable(path = "home", isDefault = true)
@NgComponentReference(App.class)
public class HomePage extends WebsitePage<HomePage> implements INgComponent<HomePage>
{
    public HomePage()
    {
        WaStack<?> layout = new WaStack<>();
        layout.setGap(PageSize.ExtraLarge);
        getMain().add(layout);

        layout.add(headingText("h1", "xl", "Entity Assist"));
        layout.add(bodyText("A fluent, reactive, and type-safe JPA query builder for Java and Kotlin.", "l"));

        DivSimple<?> debug = new DivSimple<>();
        debug.setText("HomePage Loaded");
        layout.add(debug);
    }

    @Override
    public List<String> onInit() {
        var l = new ArrayList<String>();
        l.add("console.log('HomePage onInit');");
        return l;
    }
}
