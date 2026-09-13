package com.entityassist.website;

import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.boot.NgBootImportProvider;
import com.jwebmp.core.base.angular.client.annotations.boot.NgBootImportReference;
import com.jwebmp.core.base.angular.client.annotations.references.NgComponentReference;
import com.jwebmp.core.base.angular.client.annotations.references.NgImportProvider;
import com.jwebmp.core.base.angular.client.annotations.references.NgImportReference;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.angular.components.NgIf;
import com.jwebmp.core.base.angular.services.RouterOutlet;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.core.base.html.Link;
import com.jwebmp.plugins.markdown.Markdown;
import com.jwebmp.webawesome.components.PageSize;
import com.jwebmp.webawesome.components.Variant;
import com.jwebmp.webawesome.components.WaDiv;
import com.jwebmp.webawesome.components.badge.WaBadge;
import com.jwebmp.webawesome.components.button.Appearance;
import com.jwebmp.webawesome.components.button.WaButton;
import com.jwebmp.webawesome.components.icon.WaIcon;
import com.jwebmp.webawesome.components.page.WaPage;
import com.jwebmp.webawesome.components.popover.WaPopover;
import com.jwebmp.webawesome.components.popover.WaPopoverPlacements;
import com.jwebmp.webawesome.components.tooltip.WaTooltip;
import com.jwebmp.webawesome.components.waswitch.WaSwitch;
import com.jwebmp.webawesome.tokens.WaBorderToken;
import com.jwebmp.webawesome.tokens.WaSpaceToken;
import com.jwebmp.webawesome.tokens.WaTypographyToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Top-level boot component for the Entity Assist website.
 */
@NgComponent("entityassist-app")
@NgRoutable(path = "")
@NgImportProvider("{provide: LOCALE_ID, useValue: 'en-ZA'}")
@NgBootImportProvider(value = "{ provide: LOCALE_ID, useValue: 'en-ZA' }")
@NgBootImportReference(value = "provideHttpClient", reference = "@angular/common/http")
@NgBootImportProvider("provideHttpClient()")
@NgBootImportReference(value = "LOCALE_ID", reference = "@angular/core")
@NgBootImportReference(value = "registerLocaleData", reference = "@angular/common")
@NgBootImportReference(value = "localeEnZa", reference = "@angular/common/locales/en-ZA", direct = true)
@NgImportReference(value = "localeEnZa", reference = "@angular/common/locales/en-ZA", direct = true, wrapValueInBraces = false)
@NgImportReference(value = "LOCALE_ID", reference = "@angular/core")
@NgImportReference(value = "registerLocaleData", reference = "@angular/common")
@NgImportReference(value = "signal", reference = "@angular/core")
@NgImportReference(value = "DOCUMENT", reference = "@angular/common")
@NgImportReference(value = "Router, NavigationStart, NavigationEnd", reference = "@angular/router")
@NgImportReference(value = "inject", reference = "@angular/core")
@NgImportReference(value = "filter", reference = "rxjs/operators")
@NgComponentReference(App.class)
public class WebsiteBoot extends DivSimple<WebsiteBoot> implements INgComponent<WebsiteBoot> {
    public WebsiteBoot() {
        addStyle("width:100%");
        addStyle("height:100%");
        addStyle("display", "block");

        WaPage<?> page = new WaPage<>();
        page.addAttribute("[class.no-aside]", "!asideActive()");
        page.addStyle("width:100%");
        page.addStyle("height:100%");
        page.getMain().setPageSize(PageSize.ExtraSmall);

        var banner = page.getHeader();
        DivSimple<?> navWrapper = new DivSimple<>();
        navWrapper.addClass("wrapper-nav-products");

        DivSimple<?> nav = new DivSimple<>();
        nav.setTag("nav");
        nav.addClass("nav-products");
        nav.addClass("nav-products-full");
        nav.addAttribute("aria-label", "DevSuite Products");

        DivSimple<?> primary = new DivSimple<>();
        primary.addClass("nav-products-primary");
        primary.addClass("wa-split");
        primary.addClass("wa-align-items-stretch");

        DivSimple<?> cluster = new DivSimple<>();
        cluster.addClass("wa-cluster");
        cluster.addClass("wa-align-items-stretch");
        cluster.addClass("wa-gap-0");

        // GuicedEE
        WaButton<?> guicedeeBtn = new WaButton<>();
        guicedeeBtn.setAppearance(Appearance.Plain);
        guicedeeBtn.setVariant(Variant.Brand);
        guicedeeBtn.setAsLink("https://guicedee.com", "guicedee", null);
        guicedeeBtn.addClass("product");
        guicedeeBtn.addClass("product-guicedee");
        guicedeeBtn.setID("product-guicedee");
        guicedeeBtn.setSize(com.jwebmp.webawesome.components.Size.Small);
        var guicedeeLogo = siteIcon("guicedee-logo");
        guicedeeLogo.addClass("fak");
        guicedeeLogo.addClass("fa-guicedee-logo");
        guicedeeLogo.addClass("logo-icon");
        guicedeeLogo.addClass("logo-guicedee");
        guicedeeLogo.addAttribute("label", "GuicedEE");
        guicedeeBtn.add(guicedeeLogo);
        guicedeeBtn.setText("GuicedEE");
        guicedeeBtn.setRenderTextBeforeChildren(false);
        cluster.add(guicedeeBtn);
        WaTooltip<?> guicedeeTip = new WaTooltip<>();
        guicedeeTip.setForId("product-guicedee");
        guicedeeTip.setText("GuicedEE");
        cluster.add(guicedeeTip);

        // JWebMP
        WaButton<?> jwebmpBtn = new WaButton<>();
        jwebmpBtn.setAppearance(Appearance.Plain);
        jwebmpBtn.setVariant(Variant.Brand);
        jwebmpBtn.setAsLink("https://jwebmp.com", "jwebmp", null);
        jwebmpBtn.addClass("product");
        jwebmpBtn.addClass("product-jwebmp");
        jwebmpBtn.setID("product-jwebmp");
        jwebmpBtn.setSize(com.jwebmp.webawesome.components.Size.Small);
        var jwebmpLogo = siteIcon("jwebmp-logo");
        jwebmpLogo.addClass("fak");
        jwebmpLogo.addClass("fa-jwebmp-logo-green");
        jwebmpLogo.addClass("logo-icon");
        jwebmpLogo.addClass("logo-jwebmp");
        jwebmpLogo.addAttribute("label", "JWebMP");
        jwebmpBtn.add(jwebmpLogo);
        jwebmpBtn.setText("JWebMP");
        jwebmpBtn.setRenderTextBeforeChildren(false);
        cluster.add(jwebmpBtn);
        WaTooltip<?> jwebmpTip = new WaTooltip<>();
        jwebmpTip.setForId("product-jwebmp");
        jwebmpTip.setText("JWebMP");
        cluster.add(jwebmpTip);

        // Entity Assist  active product
        WaButton<?> entityBtn = new WaButton<>();
        entityBtn.setAppearance(Appearance.Plain);
        entityBtn.setVariant(Variant.Brand);
        entityBtn.addAttribute("routerLink", "/home");
        entityBtn.addClass("product");
        entityBtn.addClass("product-entity-assist");
        entityBtn.addClass("product-active");
        entityBtn.setID("product-entity-assist");
        entityBtn.setSize(com.jwebmp.webawesome.components.Size.Small);
        var entityLogo = siteIcon("entityassist-logo");
        entityLogo.addClass("fak");
        entityLogo.addClass("fa-entityassist-logo");
        entityLogo.addClass("logo-icon");
        entityLogo.addClass("logo-entity-assist");
        entityLogo.addAttribute("label", "Entity Assist");
        entityBtn.add(entityLogo);
        entityBtn.setText("Entity Assist");
        entityBtn.setRenderTextBeforeChildren(false);
        cluster.add(entityBtn);

        // Activity Master
        WaButton<?> activityBtn = new WaButton<>();
        activityBtn.setAppearance(Appearance.Plain);
        activityBtn.setVariant(Variant.Brand);
        activityBtn.setAsLink("https://activity-master.com/", "activitymaster", null);
        activityBtn.addClass("product");
        activityBtn.addClass("product-activity-master");
        activityBtn.setID("product-activity-master");
        activityBtn.setSize(com.jwebmp.webawesome.components.Size.Small);
        var activityLogo = siteIcon("activitymaster-logo");
        activityLogo.addClass("fak");
        activityLogo.addClass("fa-activitymaster-logo");
        activityLogo.addClass("logo-icon");
        activityLogo.addClass("logo-activity-master");
        activityLogo.addAttribute("label", "Activity Master");
        activityBtn.add(activityLogo);
        activityBtn.setText("Activity Master");
        activityBtn.setRenderTextBeforeChildren(false);
        cluster.add(activityBtn);
        WaTooltip<?> activityTip = new WaTooltip<>();
        activityTip.setForId("product-activity-master");
        activityTip.setText("Activity Master");
        cluster.add(activityTip);

        primary.add(cluster);

        // Secondary links (GitHub, Blog)
        DivSimple<?> secondary = new DivSimple<>();
        secondary.addClass("nav-products-secondary");
        secondary.addClass("wa-cluster");
        secondary.addClass("wa-gap-2xs");

        // Maven / Gradle toggle switch
        var buildToolToggle = new WaDiv<>();
        buildToolToggle.addClass("wa-cluster");
        buildToolToggle.addClass("wa-gap-2xs");
        buildToolToggle.addClass("wa-align-items-center");
        buildToolToggle.setFontSize(WaTypographyToken.FontSizeXS);
        buildToolToggle.addStyle("color", "var(--wa-color-text-quiet)");

        var mavenLabel = new DivSimple<>();
        mavenLabel.setTag("span");
        mavenLabel.setText("Maven");
        buildToolToggle.add(mavenLabel);

        WaSwitch<?> buildToolSwitch = new WaSwitch<>();
        buildToolSwitch.setSize(com.jwebmp.webawesome.components.Size.Small);
        buildToolSwitch.setName("useGradle");
        buildToolSwitch.addAttribute("[checked]", "app.useGradle()");
        buildToolSwitch.addAttribute("aria-label", "Show Gradle dependency examples");
        buildToolSwitch.addAttribute("(wa-change)", "onBuildToolChange($event)");
        buildToolToggle.add(buildToolSwitch);

        var gradleLabel = new DivSimple<>();
        gradleLabel.setTag("span");
        gradleLabel.setText("Gradle");
        buildToolToggle.add(gradleLabel);

        secondary.add(buildToolToggle);

        WaButton<?> githubBtn = new WaButton<>();
        githubBtn.setAppearance(Appearance.Plain);
        githubBtn.setVariant(Variant.Brand);
        githubBtn.setAsLink("https://github.com/Entity-Assist/", "entityassist-github", null);
        githubBtn.addClass("pseudo-product");
        githubBtn.addClass("product-github");
        githubBtn.setID("product-github");
        githubBtn.add(siteIcon("github").addAttribute("family", "brands")
                                            .addAttribute("label", "GitHub"));
        secondary.add(githubBtn);
        WaTooltip<?> githubTip = new WaTooltip<>();
        githubTip.setForId("product-github");
        githubTip.setText("GitHub");
        secondary.add(githubTip);

        WaButton<?> starBtn = new WaButton<>();
        starBtn.setAppearance(Appearance.Plain);
        starBtn.setVariant(Variant.Brand);
        starBtn.setAsLink("https://github.com/Entity-Assist/EntityAssistReactive", "entityassist-github", null);
        starBtn.addClass("pseudo-product");
        starBtn.addClass("product-star");
        starBtn.setID("product-star");
        starBtn.add(siteIcon("star").addAttribute("family", "sharp-duotone").addAttribute("label", "Star this Repository"));
        secondary.add(starBtn);
        WaTooltip<?> starTip = new WaTooltip<>();
        starTip.setForId("product-star");
        starTip.setText("Star this Repository");
        secondary.add(starTip);

        WaButton<?> docsBtn = new WaButton<>();
        docsBtn.setAppearance(Appearance.Plain);
        docsBtn.setVariant(Variant.Brand);
        docsBtn.setAsLink("https://github.com/GuicedEE/ai-rules", "guicedee-github", null);
        docsBtn.addClass("pseudo-product");
        docsBtn.addClass("product-docs");
        docsBtn.setID("product-docs");
        docsBtn.add(siteIcon("brain-circuit").addAttribute("family", "sharp-duotone").addAttribute("label", "AI Skills Repository"));
        secondary.add(docsBtn);
        WaTooltip<?> docsTip = new WaTooltip<>();
        docsTip.setForId("product-docs");
        docsTip.setText("AI Skills Repository");
        secondary.add(docsTip);

        WaButton<?> patreonBtn = new WaButton<>();
        patreonBtn.setAppearance(Appearance.Plain);
        patreonBtn.setVariant(Variant.Brand);
        patreonBtn.setAsLink("https://www.patreon.com/GedMarc", "guicedee-patreon", null);
        patreonBtn.addClass("pseudo-product");
        patreonBtn.addClass("product-patreon");
        patreonBtn.setID("product-patreon");
        patreonBtn.add(siteIcon("patreon").addAttribute("family", "brands")
                                              .addAttribute("label", "Patreon"));
        secondary.add(patreonBtn);
        WaTooltip<?> patreonTip = new WaTooltip<>();
        patreonTip.setForId("product-patreon");
        patreonTip.setText("Support me on Patreon");
        secondary.add(patreonTip);

        // Theme toggle (dark  light)
        WaButton<?> themeBtn = new WaButton<>();
        themeBtn.setAppearance(Appearance.Plain);
        themeBtn.setVariant(Variant.Brand);
        themeBtn.addAttribute("(click)", "toggleDarkMode()");
        themeBtn.addClass("pseudo-product");
        themeBtn.addClass("product-theme");
        themeBtn.setID("product-theme");
        themeBtn.addAttribute("aria-label", "Toggle color theme");
        var themeLabel = new DivSimple<>();
        themeLabel.setTag("span");
        themeLabel.addClass("visually-hidden");
        themeLabel.setText("Toggle color theme");
        themeBtn.add(themeLabel);
        themeBtn.addAttribute("[attr.aria-pressed]", "darkMode()");
        var themeIcon = siteIcon("sun-bright");

        themeIcon.addAttribute("family", "sharp-duotone");
        themeIcon.addAttribute("label", "Toggle Theme");
        themeBtn.add(themeIcon);
        secondary.add(themeBtn);
        WaTooltip<?> themeTip = new WaTooltip<>();
        themeTip.setForId("product-theme");
        themeTip.setText("Toggle Theme");
        secondary.add(themeTip);

        primary.add(secondary);
        nav.add(primary);
        navWrapper.add(nav);
        banner.add(navWrapper);

        // Primary navigation shares the compact Built on link presentation.
        var menu = page.getMenu();
        var menuTree = createNavigation();

        // Home
        menuTree.add(createNavigationLink("/home", "Home", "house"));
        menuTree.add(createNavigationLink("/capabilities", "Capabilities", "layer-group"));
        menuTree.add(createNavigationLink("/getting-started", "Getting started", "rocket"));
        menuTree.add(createNavigationLink("/query-guide", "Query guide", "code"));
        menuTree.add(createNavigationLink("/support", "Resources & support", "life-ring"));

        // Support (external)


        menu.add(menuTree);

        //  Built-on attribution links below menu tree
        var builtOn = new WaDiv<>();
        builtOn.setPadding(WaSpaceToken.SpaceM);
        builtOn.addStyle("border-top", "1px solid var(--wa-color-neutral-200)");
        builtOn.addStyle("margin-top", "auto");
        var builtOnLabel = new DivSimple<>();
        builtOnLabel.setTag("span");
        builtOnLabel.setText("Built on");
        builtOnLabel.addClass("wa-body-2xs");
        builtOnLabel.addStyle("color", "var(--wa-color-text-quiet)");
        builtOnLabel.addStyle("display", "block");
        builtOnLabel.addStyle("margin-bottom", WaSpaceToken.SpaceXS.var());
        builtOn.add(builtOnLabel);
        var builtOnLinks = new DivSimple<>();
        builtOnLinks.addClass("wa-stack");
        builtOnLinks.addClass("wa-gap-2xs");
        builtOnLinks.addClass("built-on-links");

        Link<?> angularAwesomeLink = new Link<>();
        angularAwesomeLink.setTag("a");
        angularAwesomeLink.addAttribute("href", "https://www.npmjs.com/package/angular-awesome");
        angularAwesomeLink.addAttribute("target", "angular-awesome");
        angularAwesomeLink.add(siteIcon("npm").addAttribute("family", "brands"));
        angularAwesomeLink.setText("Angular Awesome");
        angularAwesomeLink.setRenderTextBeforeChildren(false);
        angularAwesomeLink.addClass("wa-body-xs");
        angularAwesomeLink.addStyle("color", "var(--wa-color-brand-normal)");
        builtOnLinks.add(angularAwesomeLink);

        Link<?> webAwesomeLink = new Link<>();
        webAwesomeLink.setTag("a");
        webAwesomeLink.addAttribute("href", "https://www.webawesome.com");
        webAwesomeLink.addAttribute("target", "web-awesome");
        webAwesomeLink.add(siteIcon("web-awesome").addAttribute("family", "sharp-duotone"));
        webAwesomeLink.setText("Web Awesome");
        webAwesomeLink.setRenderTextBeforeChildren(false);
        webAwesomeLink.addClass("wa-body-xs");
        webAwesomeLink.addStyle("color", "var(--wa-color-brand-normal)");
        builtOnLinks.add(webAwesomeLink);

        Link<?> jwebmpMenuLink = new Link<>();
        jwebmpMenuLink.setTag("a");
        jwebmpMenuLink.addAttribute("href", "https://jwebmp.com");
        jwebmpMenuLink.addAttribute("target", "jwebmp");
        var jwebmpBuiltIcon = siteIcon("jwebmp-logo");
        jwebmpBuiltIcon.addClass("built-on-logo");
        jwebmpMenuLink.add(jwebmpBuiltIcon);
        jwebmpMenuLink.setText("JWebMP");
        jwebmpMenuLink.setRenderTextBeforeChildren(false);
        jwebmpMenuLink.addClass("wa-body-xs");
        jwebmpMenuLink.addStyle("color", "var(--wa-color-brand-normal)");
        builtOnLinks.add(jwebmpMenuLink);

        builtOn.add(builtOnLinks);
        menu.add(builtOn);

        //  Navigation Toggle (burger button, slot="navigation-toggle")
        var navToggle = page.getNavigationToggle();
        WaButton<?> burgerBtn = new WaButton<>();
        burgerBtn.setAppearance(Appearance.Plain);
        burgerBtn.setVariant(Variant.Neutral);
        burgerBtn.addAttribute("aria-label", "Toggle navigation menu");
        var menuLabel = new DivSimple<>();
        menuLabel.setTag("span");
        menuLabel.addClass("visually-hidden");
        menuLabel.setText("Toggle navigation menu");
        burgerBtn.add(menuLabel);
        burgerBtn.add(siteIcon("bars").addAttribute("family", "sharp-duotone"));
        navToggle.add(burgerBtn);

        //  Navigation Toggle Icon (slot="navigation-toggle-icon")
        var navToggleIcon = page.getNavigationToggleIcon();
        navToggleIcon.add(siteIcon("bars").addAttribute("family", "sharp-duotone"));

        //  Navigation Header (branding inside the drawer, slot="navigation-header")
        var navHeader = page.getNavigationHeader();
        Link<?> drawerLogo = new Link<>();
        drawerLogo.setTag("a");
        drawerLogo.addAttribute("routerLink", "/home");
        drawerLogo.addAttribute("aria-label", "Entity Assist Home");
        drawerLogo.addClass("appearance-plain");
        var drawerLogoSpan = new DivSimple<>();
        drawerLogoSpan.setTag("i");
        drawerLogoSpan.addClass("fak");
        drawerLogoSpan.addClass("fa-entityassist-logo");
        drawerLogoSpan.addClass("logo-icon");
        drawerLogoSpan.addClass("logo-entity-assist");
        drawerLogo.add(drawerLogoSpan);
        drawerLogo.setText("Entity Assist");
        drawerLogo.setRenderTextBeforeChildren(false);
        navHeader.add(drawerLogo);

        //  Burger Menu Navigation (drawer contents, slot="navigation")
        var burgerMenuNavigation = page.getNavigation();
        var navTree = createNavigation();

        navTree.add(createNavigationLink("/home", "Home", "house"));
        navTree.add(createNavigationLink("/capabilities", "Capabilities", "layer-group"));
        navTree.add(createNavigationLink("/getting-started", "Getting started", "rocket"));
        navTree.add(createNavigationLink("/query-guide", "Query guide", "code"));
        navTree.add(createNavigationLink("/support", "Resources & support", "life-ring"));

        burgerMenuNavigation.add(navTree);

        //  Built-on attribution links below drawer tree
        var drawerBuiltOn = new WaDiv<>();
        drawerBuiltOn.setPadding(WaSpaceToken.SpaceM);
        drawerBuiltOn.addStyle("border-top", "1px solid var(--wa-color-neutral-200)");
        drawerBuiltOn.addStyle("margin-top", "auto");
        var drawerBuiltOnLabel = new DivSimple<>();
        drawerBuiltOnLabel.setTag("span");
        drawerBuiltOnLabel.setText("Built on");
        drawerBuiltOnLabel.addClass("wa-body-2xs");
        drawerBuiltOnLabel.addStyle("color", "var(--wa-color-text-quiet)");
        drawerBuiltOnLabel.addStyle("display", "block");
        drawerBuiltOnLabel.addStyle("margin-bottom", WaSpaceToken.SpaceXS.var());
        drawerBuiltOn.add(drawerBuiltOnLabel);
        var drawerBuiltOnLinks = new DivSimple<>();
        drawerBuiltOnLinks.addClass("wa-stack");
        drawerBuiltOnLinks.addClass("wa-gap-2xs");
        drawerBuiltOnLinks.addClass("built-on-links");

        Link<?> drawerAngularLink = new Link<>();
        drawerAngularLink.setTag("a");
        drawerAngularLink.addAttribute("href", "https://www.npmjs.com/package/angular-awesome");
        drawerAngularLink.addAttribute("target", "angular-awesome");
        drawerAngularLink.add(siteIcon("npm").addAttribute("family", "brands"));
        drawerAngularLink.setText("Angular Awesome");
        drawerAngularLink.setRenderTextBeforeChildren(false);
        drawerAngularLink.addClass("wa-body-xs");
        drawerAngularLink.addStyle("color", "var(--wa-color-brand-normal)");
        drawerBuiltOnLinks.add(drawerAngularLink);

        Link<?> drawerWebAwesomeLink = new Link<>();
        drawerWebAwesomeLink.setTag("a");
        drawerWebAwesomeLink.addAttribute("href", "https://www.webawesome.com");
        drawerWebAwesomeLink.addAttribute("target", "web-awesome");
        drawerWebAwesomeLink.add(siteIcon("web-awesome").addAttribute("family", "sharp-duotone"));
        drawerWebAwesomeLink.setText("Web Awesome");
        drawerWebAwesomeLink.setRenderTextBeforeChildren(false);
        drawerWebAwesomeLink.addClass("wa-body-xs");
        drawerWebAwesomeLink.addStyle("color", "var(--wa-color-brand-normal)");
        drawerBuiltOnLinks.add(drawerWebAwesomeLink);

        Link<?> drawerJwebmpLink = new Link<>();
        drawerJwebmpLink.setTag("a");
        drawerJwebmpLink.addAttribute("href", "https://jwebmp.com");
        drawerJwebmpLink.addAttribute("target", "jwebmp");
        var drawerJwebmpBuiltIcon = siteIcon("jwebmp-logo");
        drawerJwebmpBuiltIcon.addClass("built-on-logo");
        drawerJwebmpLink.add(drawerJwebmpBuiltIcon);
        drawerJwebmpLink.setText("JWebMP");
        drawerJwebmpLink.setRenderTextBeforeChildren(false);
        drawerJwebmpLink.addClass("wa-body-xs");
        drawerJwebmpLink.addStyle("color", "var(--wa-color-brand-normal)");
        drawerBuiltOnLinks.add(drawerJwebmpLink);

        drawerBuiltOn.add(drawerBuiltOnLinks);
        burgerMenuNavigation.add(drawerBuiltOn);


        page.getMain().setID("main-content");
        page.getMain().add(new RouterOutlet<>());
        var footer = new DivSimple<>();
        footer.setText("Entity Assist &middot; Open source under Apache 2.0 &middot; Part of the GuicedEE ecosystem");
        footer.addClass("site-footer");
        page.getFooter().add(footer);
        page.getAside().add(new RouterOutlet<>("aside"));

        add(page);
    }

    private static WaIcon<?> siteIcon(String name) {
        var icon = new WaIcon<>();
        icon.setSrc("/icons/" + name + ".svg");
        return icon;
    }

    private static DivSimple<?> createNavigation() {
        var navigation = new DivSimple<>();
        navigation.setTag("nav");
        navigation.addAttribute("aria-label", "Main navigation");
        navigation.addClass("site-navigation-links wa-stack wa-gap-2xs primary-navigation");
        return navigation;
    }

    private static Link<?> createNavigationLink(String path, String text, String icon)
    {
        if (!path.startsWith("/"))
        {
            path = "/" + path;
        }

        Link<?> link = new Link<>("#");
        link.addClass("wa-body-xs");
        link.addAttribute("routerLink", path);
        link.addAttribute("(click)", "closeNavigation()");
        link.addAttribute("routerLinkActive", "nav-active");
        link.addAttribute("ariaCurrentWhenActive", "page");
        link.setRenderTextBeforeChildren(false);
        if (icon != null)
        {
            WaIcon<?> waIcon = siteIcon(icon);
            waIcon.setFamily("sharp-duotone");
            link.add(waIcon);
        }
        link.setText(text);
        return link;
    }

    @Override
    public List<String> host() {
        return List.of("{\n" +
                "    '[style.width]': '\"100%\"',\n" +
                "    '[style.height]': '\"100%\"',\n" +
                " }");
    }

    @Override
    public List<String> providers() {
        return List.of();
    }

    @Override
    public List<String> fields() {
        var f = new ArrayList<>(INgComponent.super.fields());
        f.add("private router: Router = inject(Router);");
        f.add("private _asideNavigating = false;");
        f.add("private currentPrimaryPath = '';");
        f.add("private document = inject(DOCUMENT);");
        f.add("darkMode = signal(true);");
        f.add("asideActive = signal(false);");

        f.add("private asideRoutes: Record<string, string> = {\n" +
                "    'getting-started': 'getting-started',\n" +
                "    'query-guide': 'query-guide'\n" +
                "};");
        return f;
    }

    @Override
    public List<String> methods() {
        var m = new ArrayList<>(INgComponent.super.methods());
        m.add("""
                closeNavigation() {
                    const shell = this.document.querySelector('wa-page') as any;
                    if (shell && shell.view === 'mobile') shell.hideNavigation();
                }
                """);
        m.add("toggleDarkMode() {\n" +
                "    const isDark = !this.darkMode();\n" +
                "    this.darkMode.set(isDark);\n" +
                "    this.document.body.classList.toggle('wa-dark', isDark);\n" +
                "    localStorage.setItem('entityassist-theme', isDark ? 'dark' : 'light');\n" +
                "}");

        m.add("onBuildToolChange(event: any) {\n" +
                "    const value = event.target.checked;\n" +
                "    this.app.useGradle.set(value);\n" +
                "    localStorage.setItem('entityassist-build-tool', value ? 'gradle' : 'maven');\n" +
                "}");
        return m;
    }

    @Override
    public List<String> onInit() {
        var init = new ArrayList<>(INgComponent.super.onInit());
        init.add("registerLocaleData(localeEnZa, 'en-ZA')");
        init.add("const savedTheme = localStorage.getItem('entityassist-theme');\n" +
                "const prefersDark = savedTheme ? savedTheme === 'dark' : true;\n" +
                "this.darkMode.set(prefersDark);\n" +
                "this.document.body.classList.toggle('wa-dark', prefersDark);");
        init.add("const savedBuildTool = localStorage.getItem('entityassist-build-tool');\n" +
                "if (savedBuildTool) {\n" +
                "    this.app.useGradle.set(savedBuildTool === 'gradle');\n" +
                "}");
        init.add("this.router.events.pipe(filter(e => e instanceof NavigationEnd)).subscribe((e: any) => {\n" +
                "    if (this._asideNavigating) return;\n" +
                "    const navEnd = e as NavigationEnd;\n" +
                "    const parsedUrl = this.router.parseUrl(navEnd.urlAfterRedirects);\n" +
                "    const primarySegments = parsedUrl.root.children['primary']?.segments || [];\n" +
                "    const primaryPath = primarySegments.map((s: any) => s.path).join('/');\n" +
                "    if (primaryPath !== this.currentPrimaryPath) {\n" +
                "        this.currentPrimaryPath = primaryPath;\n" +
                "        this.document.defaultView?.requestAnimationFrame(() => this.document.defaultView?.scrollTo(0, 0));\n" +
                "    }\n" +
                "    const asidePath = this.asideRoutes[primaryPath];\n" +
                "    this.asideActive.set(!!asidePath);\n" +
                "    const currentAside = parsedUrl.root.children['aside'];\n" +
                "    const currentAsidePath = currentAside?.segments?.map((s: any) => s.path).join('/') || null;\n" +
                "    \n" +
                "    if (asidePath && currentAsidePath !== asidePath) {\n" +
                "        this._asideNavigating = true;\n" +
                "        const asideSegments = asidePath.split('/');\n" +
                "        const tree = this.router.createUrlTree([{outlets: {aside: asideSegments}}], {relativeTo: null as any});\n" +
                "        tree.root.children['primary'] = parsedUrl.root.children['primary'];\n" +
                "        tree.queryParams = parsedUrl.queryParams;\n" +
                "        tree.fragment = parsedUrl.fragment;\n" +
                "        this.router.navigateByUrl(tree, {replaceUrl: true})\n" +
                "            .then(() => this._asideNavigating = false)\n" +
                "            .catch(() => this._asideNavigating = false);\n" +
                "    } else if (!asidePath && currentAside) {\n" +
                "        this._asideNavigating = true;\n" +
                "        delete parsedUrl.root.children['aside'];\n" +
                "        this.router.navigateByUrl(parsedUrl, {replaceUrl: true})\n" +
                "            .then(() => this._asideNavigating = false)\n" +
                "            .catch(() => this._asideNavigating = false);\n" +
                "    }\n" +
                "});");
        return init;
    }
}
