package com.entityassist.website;

import com.jwebmp.core.base.angular.client.annotations.angular.NgApp;
import com.jwebmp.core.base.angular.services.NGApplication;
import com.jwebmp.core.base.angular.client.annotations.typescript.TsDependency;
import com.jwebmp.plugins.fontawesome5pro.FontAwesome5ProPageConfigurator;
import com.jwebmp.webawesome.components.WebAwesomePageConfigurator;

@TsDependency(value = "ngx-markdown", version = "^21.1.0", overrides = true)
@NgApp(value = "ea-website", bootComponent = WebsiteBoot.class)
public class WebsiteApplication extends NGApplication<WebsiteApplication>
{
    public WebsiteApplication()
    {
        getOptions().setTitle("Entity Assist | Fluent Reactive Persistence for Java");
								
								//I set them for you -- these come from web awesome and font awesome
							// which I opened in the browser
        WebAwesomePageConfigurator.setWaKitCode("6ea54e8336d3409b");
        WebAwesomePageConfigurator.setBasePath("https://ka-p.webawesome.com/kit/6ea54e8336d3409b/webawesome@3.11.0/");
        FontAwesome5ProPageConfigurator.setKitCode("");
    }

}
