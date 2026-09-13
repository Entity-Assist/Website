// Run against the built nginx image. Requires Playwright (see README).
const { chromium } = require(process.env.PLAYWRIGHT_MODULE || 'playwright');
const assert = require('node:assert/strict');
const fs = require('node:fs');
const path = require('node:path');

(async () => {
  const origin = process.env.WEBSITE_URL || 'http://127.0.0.1:8088';
  const output = path.resolve(__dirname, '../target/browser-check');
  fs.mkdirSync(output, { recursive: true });
  const browser = await chromium.launch({ headless: true,
    ...(process.env.CHROMIUM_PATH ? { executablePath: process.env.CHROMIUM_PATH } : {}) });
  const context = await browser.newContext({ viewport: { width: 1440, height: 1000 } });
  await context.grantPermissions(['clipboard-read', 'clipboard-write'], { origin });
  const page = await context.newPage();
  const errors = [];
  const externalFailures = new Set();
  page.on('pageerror', error => errors.push(error.message));
  page.on('console', message => { if (message.type() === 'error' && !message.text().startsWith('Failed to load resource:')) errors.push(message.text()); });
  page.on('response', response => {
    if (response.status() >= 400) {
      if (response.url().startsWith(origin)) errors.push(`${response.status()} ${response.url()}`);
      else externalFailures.add(`${response.status()} ${response.url()}`);
    }
  });
  try {
    for (const [route, heading] of [
      ['/', 'Your domain. Your queries. One fluent API.'],
      ['/capabilities', 'A persistence toolkit that grows with your domain'],
      ['/getting-started', 'Your first entity and reactive query'],
      ['/query-guide', 'Type-safe queries start with your entity model'],
      ['/support', 'Build with the source in reach']
    ]) {
      const response = await page.goto(origin + route);
      assert.equal(response.status(), 200);
      await page.getByRole('heading', { level: 1, name: heading, exact: true }).waitFor();
      await page.waitForFunction(() => !!customElements.get('wa-page'));
      assert.equal(await page.locator('h1').count(), 1, `one main heading: ${route}`);
      assert.ok(await page.evaluate(() => document.documentElement.scrollWidth <= innerWidth + 2), `no desktop overflow: ${route}`);
    }
    await page.goto(origin + '/query-guide');
    await page.getByRole('heading', { name: 'Type-safe queries start with your entity model', exact: true }).waitFor();
    await page.locator('.page-aside').waitFor();
    await page.locator('markdown pre').first().waitFor();
    assert.ok((await page.locator('main').innerText()).includes('Customers_.name'));
    assert.ok((await page.locator('main').innerText()).includes('withRecursive'));
    assert.ok(await page.locator('markdown pre').count() >= 25, 'complete guide code examples');
    await page.locator('markdown').first().hover();
    await page.locator('.markdown-clipboard-button').first().click();
    assert.ok((await page.evaluate(() => navigator.clipboard.readText())).includes('hibernate-processor'), 'copy example code');
    for (const file of ['Customers.java', 'Orders.java', 'Categories.java', 'CategoryLinks.java', 'QueryExamples.java']) {
      const response = await context.request.get(origin + '/examples/' + file);
      assert.equal(response.status(), 200);
      assert.ok((await response.text()).includes('package example;'));
    }
    await page.getByRole('link', { name: 'Joins & ON clauses', exact: true }).click();
    assert.equal(await page.evaluate(() => document.activeElement.id), 'joins');
    await page.screenshot({ path: path.join(output, 'query-guide-desktop.png') });
    await page.locator('[slot="menu"] a[routerLink="/home"]').click();
    await page.getByRole('heading', { name: 'Your domain. Your queries. One fluent API.', exact: true }).waitFor();
    await page.waitForFunction(() => document.querySelector('wa-page').classList.contains('no-aside'));
    assert.equal(await page.locator('.page-aside').count(), 0);
    await page.waitForFunction(() => window.scrollY < 2);
    await page.screenshot({ path: path.join(output, 'home-desktop.png') });

    await page.locator('[slot="menu"] a[routerLink="/getting-started"]').click();
    const toggle = page.locator('wa-switch[name="useGradle"]');
    await toggle.click();
    await page.getByText('implementation("com.entityassist:entity-assist-reactive:2.0.1-SNAPSHOT")', { exact: false }).waitFor();
    await page.reload();
    await page.waitForFunction(() => document.querySelector('wa-switch[name="useGradle"]')?.checked === true);
    await toggle.click();
    await page.waitForFunction(() => document.querySelector('wa-switch[name="useGradle"]')?.checked === false);
    await page.waitForFunction(() => document.getElementById('dependency').innerText.includes('<dependency>'));

    const beforeDark = await page.locator('body').evaluate(el => el.classList.contains('wa-dark'));
    await page.locator('#product-theme').click();
    assert.notEqual(await page.locator('body').evaluate(el => el.classList.contains('wa-dark')), beforeDark);
    await page.reload();
    await page.getByRole('heading', { level: 1 }).waitFor();
    assert.notEqual(await page.locator('body').evaluate(el => el.classList.contains('wa-dark')), beforeDark);
    await page.screenshot({ path: path.join(output, 'getting-started-light.png') });

    await page.setViewportSize({ width: 390, height: 844 });
    await page.goto(origin + '/home');
    await page.waitForFunction(() => document.querySelector('wa-page')?.getAttribute('view') === 'mobile');
    assert.ok(await page.evaluate(() => document.documentElement.scrollWidth <= innerWidth + 2), 'no mobile overflow');
    await page.screenshot({ path: path.join(output, 'home-mobile.png') });
    await page.getByRole('button', { name: 'Toggle navigation menu', exact: true }).click();
    await page.locator('[slot="navigation"] a[routerLink="/query-guide"]').click();
    await page.getByRole('heading', { name: 'Type-safe queries start with your entity model', exact: true }).waitFor();
    await page.waitForFunction(() => !document.querySelector('wa-page').navOpen);
    await page.waitForFunction(() => document.documentElement.scrollWidth <= innerWidth + 2);
    await page.getByRole('dialog').waitFor({ state: 'hidden' });
    await page.screenshot({ path: path.join(output, 'query-guide-mobile.png') });
    assert.deepEqual(errors, [], 'browser runtime/console errors');
    if (externalFailures.size) console.log('External asset responses:', [...externalFailures]);
    console.log('PASS: five routes, typed examples, asides, preferences, responsive layout, mobile navigation, clipboard and source downloads');
  } catch (error) {
    await page.screenshot({ path: path.join(output, 'failure.png') }).catch(() => {});
    fs.writeFileSync(path.join(output, 'errors.json'), JSON.stringify(errors, null, 2));
    throw error;
  } finally { await browser.close(); }
})().catch(error => { console.error(error); process.exitCode = 1; });
