import com.entityassist.website.WebsitePageConfigurator;
import com.jwebmp.core.base.angular.client.services.TypescriptIndexPageConfigurator;
import com.jwebmp.core.services.IPageConfigurator;
import com.jwebmp.core.base.angular.services.NGApplication;
import com.entityassist.website.WebsiteApplication;

open module com.entityassist.website {
    requires com.guicedee.client;
    requires com.jwebmp.client;
    requires com.jwebmp.core;
    requires com.jwebmp.core.angular;
    requires com.jwebmp.core.base.angular.client;
    requires com.jwebmp.plugins.markdown;
    requires com.jwebmp.webawesome;
    requires com.jwebmp.webawesomepro;
    requires static lombok;
    requires com.jwebmp.plugins.fontawesome5pro;

    provides IPageConfigurator with WebsitePageConfigurator;
    provides TypescriptIndexPageConfigurator with WebsitePageConfigurator;
    provides NGApplication with WebsiteApplication;
}
