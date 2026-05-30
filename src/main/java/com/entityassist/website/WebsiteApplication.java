package com.entityassist.website;

import com.jwebmp.core.base.angular.client.annotations.angular.NgApp;
import com.jwebmp.core.base.angular.services.NGApplication;
import com.jwebmp.plugins.fontawesome5pro.FontAwesome5ProPageConfigurator;
import com.jwebmp.webawesome.components.WebAwesomePageConfigurator;

@NgApp(value = "ea-website", bootComponent = WebsiteBoot.class)
public class WebsiteApplication extends NGApplication<WebsiteApplication>
{
    public WebsiteApplication()
    {
        getOptions().setTitle("Entity Assist - Java Persistence API & Jakarta Persistence");
								
								//I set them for you -- these come from web awesome and font awesome
							// which I opened in the browser
        WebAwesomePageConfigurator.setWaKitCode("6ea54e8336d3409b");
        FontAwesome5ProPageConfigurator.setKitCode("3f59d88b7f");
    }

}
