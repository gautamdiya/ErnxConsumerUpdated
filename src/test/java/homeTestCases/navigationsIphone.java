package homeTestCases;

import static org.testng.Assert.assertTrue;

import java.util.logging.Logger;

import org.testng.Assert;
import org.testng.annotations.*;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;

import LoginPage.EmailUtils;

public class navigationsIphone {
    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;
    private static final Logger logger = Logger.getLogger(navigationsIphone.class.getName());
    private static final String childName = "tester";

    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(390, 844)
                .setUserAgent(
                        "Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.0 Mobile/15E148 Safari/604.1")
                .setDeviceScaleFactor(3.0)
                .setIsMobile(true)
                .setHasTouch(true));

        page = context.newPage();
    }

    public void login() throws InterruptedException {
        logger.info("Starting browser and Test Case");
        page.navigate("https://ernx-consumer.vercel.app/login");
        page.getByPlaceholder("Email").type("automationtesttimechains@gmail.com");
        page.locator("//button[contains(text(),'Next')]").click();
        Thread.sleep(5000);

        logger.info("Getting OTP and filling");
        String emailContent = EmailUtils.getOtpFromEmail(
                "imap.gmail.com", "993", "automationtesttimechains@gmail.com",
                "pjkw iaiz qner ptvh", "Signup OTP", 60);

        page.locator("[id=otp-0]").type(emailContent);
        Thread.sleep(3000);

        logger.info("Otp is Verified");
        page.locator("//span[normalize-space()='Settings']").click();
        page.locator("//button[normalize-space()='Children Details']").click();

        Locator addFirstChildBtn = page.locator("//button[normalize-space()='Add Your First Child']");
        Locator addChildIcon = page.locator("path[d='M11 11V5H13V11H19V13H13V19H11V13H5V11H11Z']");

        if (addFirstChildBtn.isVisible()) {
            addFirstChildBtn.click();
        } else {
            addChildIcon.click();
        }

        page.getByPlaceholder("First Name or Nickname").type(childName);
        page.locator("//span[contains(text(),'female')]").click();
        page.locator("//button[contains(text(),'Next')]").click();
        page.locator("//button[contains(text(),'Next')]").click();
        page.locator("//p[contains(text(),'ERNX Dev test')]").click();
        page.locator("//button[contains(text(),'Next')]").click();
        page.locator("//button[contains(text(),'Finish')]").click();

        Thread.sleep(5000);
        String childCreatedName = page.locator("(//h1[@class='pfont-700 text-lg'])[last()-1]").textContent();
        String currentUrl = page.url();

        Assert.assertTrue(currentUrl.contains("https://ernx-consumer.vercel.app/game"), "Login Failed");
        Assert.assertTrue(childCreatedName.contains(childName), "Child name is wrong");
    }

    @Test(priority = 1)
    public void chooseReward() throws InterruptedException {
        login();
        logger.info("Running chooseReward Test Case...");
        page.locator("(//button[contains(@class,'embla__dot')])[last()]").click();
        page.locator("(//img[contains(@alt,'reward')])[last()]").click();
        page.locator("//h3[normalize-space()='ERNX Dev test']").click();
        page.locator("//button[normalize-space()='Select As Reward']").click();
    }

    @Test(priority = 2)
    public void gameRules() {
        page.locator("//div[@class='w-2/5 flex items-center pfont-600 text-sm gap-x-2']").click();
        assertTrue(page.url().equals("https://ernx-consumer.vercel.app/game/how-it-works"), "Not navigated to Game Rules page");
        assertTrue(page.locator("(//h2[@class='text-base pfont-700 mb-4'])[1]").isVisible(), "Game Rules content is wrong");
    }

    @Test(priority = 3)
    public void historyPage() throws InterruptedException {
        page.locator("//span[contains(text(),'History')]").click();
        assertTrue(page.url().equals("https://ernx-consumer.vercel.app/history"), "Navigation to history failed");

        String child1Name = page.locator("(//h1[@class='pfont-700 text-xl'])[1]").textContent();
        String pointsHistory1 = page.locator("//h2[@class='pfont-800 text-base font-bold my-6']").textContent();

        Assert.assertTrue(pointsHistory1.contains(child1Name), "Child 1 history not shown");

        Locator secondChildDot = page.locator("(//button[contains(@class,'embla__dot')])[2]");
        if (secondChildDot.isVisible()) {
            secondChildDot.click();
            Thread.sleep(2000);
            String child2Name = page.locator("(//h1[@class='pfont-700 text-xl'])[2]").textContent();
            String pointsHistory2 = page.locator("//h2[@class='pfont-800 text-base font-bold my-6']").textContent();
            Assert.assertTrue(pointsHistory2.contains(child2Name), "Child 2 history not shown");
        }
    }

    @Test(priority = 4)
    public void storeNavigation() throws InterruptedException {
        page.locator("//span[contains(text(),'Store')]").click();
        assertTrue(page.url().equals("https://ernx-consumer.vercel.app/store"), "Store navigation failed");

        Locator continueBtn = page.locator("//button[normalize-space()='Continue']");
        assertTrue(continueBtn.isDisabled(), "Continue button should be disabled initially");

        page.locator("//label[@for='radioButton-0']").click();
        continueBtn.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        page.waitForCondition(() -> continueBtn.isEnabled());
        continueBtn.click();
        Thread.sleep(5000);
        page.goBack();
    }

    @Test(priority = 5)
    public void settingsNavigations() {
        page.locator("//span[contains(text(),'Settings')]").click();
        assertTrue(page.url().equals("https://ernx-consumer.vercel.app/settings"), "Settings page navigation failed");

        page.locator("//button[normalize-space()='Account Info']").click();
        assertTrue(page.url().equals("https://ernx-consumer.vercel.app/account"), "Account Info page navigation failed");
        page.goBack();

        page.locator("//button[normalize-space()='Children Details']").click();
        assertTrue(page.url().equals("https://ernx-consumer.vercel.app/settings/children"), "Children page navigation failed");
        page.goBack();

        page.locator("//button[normalize-space()='Notifications']").click();
        assertTrue(page.url().equals("https://ernx-consumer.vercel.app/settings/notifications"), "Notifications navigation failed");

        Locator pushNotifications = page.locator("//button[@name='push_notifications']");
        Locator emailNotifications = page.locator("//button[@name='emails_notifications']");
        if (pushNotifications.isDisabled()) pushNotifications.click();
        if (emailNotifications.isDisabled()) emailNotifications.click();
        page.goBack();

        page.locator("//button[normalize-space()='Transaction History']").click();
        assertTrue(page.url().equals("https://ernx-consumer.vercel.app/settings/transactions"), "Transaction History navigation failed");
        page.goBack();
    }

    @AfterClass
    public void tearDown() {
        page.close();
        browser.close();
        playwright.close();
    }
}
