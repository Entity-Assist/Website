# Entity Assist website

Customer-facing product site and query documentation, authored in Java/JWebMP and generated as an Angular 21 SPA. It follows the GuicedEE, JWebMP and Activity Master site structure: WebAwesome product navigation, desktop menu, mobile drawer, theme preference, Maven/Gradle preference and route-specific documentation asides.

## Content and source ownership

- `/home`: product overview and metamodel-based query example.
- `/capabilities`: capabilities, integration fit and persistence behaviour.
- `/getting-started`: dependency, mapped entity, JPAModelGen introduction, reads and transactions.
- `/query-guide`: generated metamodel setup, typed/static builders, operators, OR, dot paths, joins and ON predicates, projections, grouping, CTEs, recursive adjacency/link-table queries, CRUD and stateless sessions.
- `/support`: source, artifact discovery, issue reporting and contribution links.

Edit `src/main/java/com/entityassist/website` and `src/main/resources/src/assets`. Do not edit `target/webroot/ea-website`: it is regenerated.

Guide snippets come from `src/main/resources/query-guide/*.java`. The website reads marked regions from `QueryExamples.java`; `examples/pom.xml` compiles those same files and generates `Customers_`, `Orders_`, `Categories_` and `CategoryLinks_` using Hibernate's processor. Maven also publishes these source files into the static site at `/examples/*.java`.

The example check verifies Java signatures and annotation processing. It does not execute database operations. Applications must validate queries, mappings, transaction behaviour and dialect support against their database.

The library source currently declares `com.entityassist:entity-assist-reactive:2.0.1-SNAPSHOT`; the website/image version is independently `3.0.0-SNAPSHOT`. Snapshot examples require that library to be installed locally, or an appropriately configured snapshot repository. Use Maven Central to verify published library versions.

## Build and preview

Prerequisites: JDK 25 toolchain, Maven 4 with the workspace's GuicedEE/JWebMP artifacts available, Node/npm compatible with Angular 21, and Docker. WebAwesome uses the reference sites' shared kit. Product/navigation SVGs are bundled locally. The kit may still request its own fallback menu icon from Font Awesome; the local preview observed a 403 for that unused fallback. Browser checks report external asset failures separately from application errors.

From `C:/Java/DevSuite`:

```powershell
# If the current library snapshot is not already installed:
mvn -f EntityAssist/pom.xml -DskipTests install

# Compile guide examples, generate Angular, build production assets and nginx image:
./build-entityassist-website.ps1

docker run --rm --name entityassist-website-preview -p 127.0.0.1:8088:80 gedmarc/entityassist-website:3.0.0-SNAPSHOT
```

Open `http://localhost:8088`. nginx supports direct navigation and reloads on all page routes, serves `/healthz`, and returns 404 for missing static assets. Immutable caching is limited to hashed bundles. Azure terminates TLS; the container listens on port 80.

The root Maven `websites` profile includes this module. Its `install` phase invokes the Angular generator, consistent with the reference sites. The build script additionally checks all primary routes and runs the production compiler explicitly so generated-but-unbuilt or stale output cannot be packaged. `-Dwebsite.docker=true` enables the generator's own Docker build; the supported script uses the checked-in Dockerfile/nginx configuration.

The installed generator may emit a Vert.x shutdown-hook `NoClassDefFoundError` after Maven reports success. The script preserves that output and uses native process exit codes, then independently validates Angular generation/build and nginx configuration.

## Browser checks

With a preview container running, install Playwright into an ignored tools directory, then run:

```powershell
npm install --prefix EntityAssistWebsite/target/browser-tools playwright@1.57.0
$env:PLAYWRIGHT_MODULE = (Resolve-Path EntityAssistWebsite/target/browser-tools/node_modules/playwright).Path
# Install Playwright Chromium using its CLI if no compatible browser is available.
node EntityAssistWebsite/target/browser-tools/node_modules/playwright/cli.js install chromium
node EntityAssistWebsite/scripts/smoke.cjs
```

`CHROMIUM_PATH` optionally selects an existing browser executable. `WEBSITE_URL` overrides the default `http://127.0.0.1:8088`. Screenshots are written to `target/browser-check`.

## Publish and deploy

### Live deployment

Deployed on 2026-09-11 as **`entityassist-website`** in **Pay-As-You-Go / DevSites**.

- [Open the website](https://entityassist-website.whitegrass-2e17714c.ukwest.azurecontainerapps.io).
- [Open the container app in Azure Portal](https://portal.azure.com/#resource/subscriptions/aee3c089-49a8-472c-8d40-5bf568a1ff55/resourceGroups/DevSites/providers/Microsoft.App/containerApps/entityassist-website/overview).
- Published image: `docker.io/gedmarc/entityassist-website:2026-09-11.2`.
- Image digest: `sha256:bc8ae818356104d798cbc4e4900b0ecdc6205cc7f3d787cf1274a521ae82e0eb`.
- Docker Hub pull authentication is configured on this app. Normal image updates retain it. If recreating the app, configure registry authentication during creation: this repository requires authenticated pulls. Credentials must come from a credential store, never from checked-in files.
- Custom-domain DNS and certificates have not been configured; use the Azure hostname above.

### Deployment workflow

Verified on 2026-09-11: the three reference sites share:

- Subscription: `Pay-As-You-Go`, `aee3c089-49a8-472c-8d40-5bf568a1ff55`.
- Resource group: `DevSites`.
- Environment: `managedEnvironment-DevSites-8eab` in UK West.
- Docker Hub images served by nginx in Azure Container Apps.
- Frontend sizing: 0.25 CPU, 0.5 GiB, 0?1 replicas, single revision mode, HTTP concurrency 10.

Use a unique tag per publication (or deploy the pushed digest). Docker authentication uses the existing credential store. Azure commands explicitly scope the subscription and discover the environment from `guicedee-website`; they do not change the CLI's active subscription.

```powershell
./build-entityassist-website.ps1 -Tag 2026-09-11.1 -Push

# Read-only plan; validates subscription, group and reference environment:
./deploy-entityassist-website.ps1 -Image gedmarc/entityassist-website:2026-09-11.1

# Create the dedicated app if absent, otherwise update its image:
./deploy-entityassist-website.ps1 -Image gedmarc/entityassist-website:2026-09-11.1 -Apply
```

The first deployment creates `entityassist-website` beside the existing sites and prints its Azure hostname. Confirm the latest revision is healthy and serving the expected image. Check `/healthz`, all direct routes, stylesheet/logo requests and browser navigation before directing customer traffic to it.

Custom-domain DNS and certificates are a separate step: add `entityassist.com` / `www.entityassist.com` DNS records using Azure's domain validation values, then bind managed certificates to this app in the shared environment. The deployment script does not change DNS or existing websites. No secrets belong in this repository.

For rollback, deploy the previous published image digest with the same script. Existing domain bindings and application configuration are retained when updating an existing app.
