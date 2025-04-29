package homeTestCases;

import org.testng.Assert;
import org.testng.annotations.*;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.logging.Logger;

import LoginPage.EmailUtils;

public class navigations {
    public static Playwright playwright;
    public static Browser browser;
    public static Page page;
    public static String childName = "tester";
    private static final Logger logger = Logger.getLogger(navigations.class.getName());

    @BeforeClass
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
    }

    public void login() throws InterruptedException {
        logger.info("Starting browser and Test Case");
        page.navigate("https://ernx-consumer.vercel.app/login");
        logger.info("Login Page Opened");
        page.getByPlaceholder("Email").type("automationtesttimechains@gmail.com");
        page.locator("//button[contains(text(),'Next')]").click();
        Thread.sleep(5000);
        logger.info("Getting Otp and filling");
        String emailContent = EmailUtils.getOtpFromEmail("imap.gmail.com", "993", 
            "automationtesttimechains@gmail.com", "pjkw iaiz qner ptvh", 
            "Signup OTP", 60);
        Locator otpfield = page.locator("[id=otp-0]");
        otpfield.type(emailContent);
        Thread.sleep(3000);
        logger.info("Otp is Verified");
        page.locator("//span[normalize-space()='Settings']").click();
        page.locator("//button[normalize-space()='Children Details']").click();

        Locator addYourFistChildBttn = page.locator("//button[normalize-space()='Add Your First Child']");
        Locator addChildIcon = page.locator("path[d='M11 11V5H13V11H19V13H13V19H11V13H5V11H11Z']");
        if (addYourFistChildBttn.isVisible()) {
            addYourFistChildBttn.click();
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

        String childCreatedName = page.locator("(//h1[@class='pfont-700 text-lg'])[last()-1]").textContent();
        Thread.sleep(5000);
        logger.info("Verifying the Name of child and URL is correct");
        String currentUrl = page.url();
        Assert.assertTrue(currentUrl.contains("https://ernx-consumer.vercel.app/game"), "Login Failed");
        Assert.assertTrue(childCreatedName.contains(childName), "Child name is wrong");
        logger.info("Login Success!!!!");
    }

    @Test
    public void chooseReward() throws InterruptedException {
        login();
        logger.info("Starting chooseReward Test Case!!!");
        page.locator("(//button[contains(@class,'embla__dot')])[last()]").click();
        page.locator("(//img[contains(@alt,'reward')])[last()]").click();
        Locator rewardPresent = page.locator("//h3[normalize-space()='ERNX Dev test']");
        rewardPresent.waitFor(new Locator.WaitForOptions().setTimeout(2000));
        rewardPresent.click();
        page.locator("//button[normalize-space()='Select As Reward']").click();
        logger.info("chooseReward Test Case Passed!!!");
    }

    @Test
    public void gameRules() {
        logger.info("Starting game rules Test Case!!!");
        page.locator("//div[@class='w-2/5 flex items-center pfont-600 text-sm gap-x-2']").click();
        Locator gameRulesText = page.locator("(//h2[@class='text-base pfont-700 mb-4'])[1]");
        Assert.assertTrue("https://ernx-consumer.vercel.app/game/how-it-works".equals(page.url()), "Not navigated to game rules page..");
        Assert.assertTrue(gameRulesText.isVisible(), "Game Rules content is Wrong");
        Assert.assertTrue(page.locator("//div[contains(text(),'How')]").isVisible(), "how it works content is Wrong");
        logger.info("game rules Test Case Passed!!!");
    }

    @Test
    public void historyPage() throws InterruptedException {
        logger.info("Starting historyPage Test Case!!!");
        page.locator("//span[contains(text(),'History')]").click();
        Assert.assertTrue("https://ernx-consumer.vercel.app/history".equals(page.url()), "Not able to navigate to history");
        String child1Name = page.locator("(//h1[@class='pfont-700 text-xl'])[1]").textContent();
        String pointsHistory1 = page.locator("//h2[@class='pfont-800 text-base font-bold my-6']").textContent();
        Assert.assertTrue(pointsHistory1.contains(child1Name), "Points history Display error!");
        Locator secondChildScrollDot = page.locator("(//button[contains(@class,'embla__dot')])[2]");
        if (secondChildScrollDot.isVisible()) {
            secondChildScrollDot.click();
            Thread.sleep(2000);
            String child2Name = page.locator("(//h1[@class='pfont-700 text-xl'])[2]").textContent();
            String pointsHistory2 = page.locator("//h2[@class='pfont-800 text-base font-bold my-6']").textContent();
            Assert.assertTrue(pointsHistory2.contains(child2Name), "Points history Display error!");
        }
    }

    @Test
    public void storeNavigation() throws InterruptedException {
        logger.info("Starting storeNavigation Test Case!!!");
        page.locator("//span[contains(text(),'Store')]").click();
        Assert.assertTrue("https://ernx-consumer.vercel.app/store".equals(page.url()), "Not able to navigate to Store Page");
        Locator continueBtn = page.locator("//button[normalize-space()='Continue']");
        Assert.assertTrue(continueBtn.isDisabled(), "Continue button is not disabled");
        page.locator("//label[@for='radioButton-0']").click();
        continueBtn.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        page.waitForCondition(() -> continueBtn.isEnabled());
        continueBtn.click();
        Thread.sleep(5000);
        page.goBack();
    }

    @Test
    public void settingsNavigations() {
        logger.info("Starting settingsNavigations Test Case!!!");
        page.locator("//span[contains(text(),'Settings')]").click();
        Assert.assertTrue("https://ernx-consumer.vercel.app/settings".equals(page.url()), "Not able to navigate to settings");

        Locator accInfoBtn = page.locator("//button[normalize-space()='Account Info']");
        Locator childrenInfoBtn = page.locator("//button[normalize-space()='Children Details']");
        Locator notificationsBtn = page.locator("//button[normalize-space()='Notifications']");
        Locator transactionsHistoryBtn = page.locator("//button[normalize-space()='Transaction History']");

        accInfoBtn.click();
        Assert.assertEquals(page.url(), "https://ernx-consumer.vercel.app/account", "Account Info navigation failed.");
        page.goBack();

        childrenInfoBtn.click();
        Assert.assertEquals(page.url(), "https://ernx-consumer.vercel.app/settings/children", "Children Details navigation failed.");
        page.goBack();

        notificationsBtn.click();
        Assert.assertEquals(page.url(), "https://ernx-consumer.vercel.app/settings/notifications", "Notifications navigation failed.");
        Locator pushNotifications = page.locator("//button[@name='push_notifications']");
        Locator emailNotifications = page.locator("//button[@name='emails_notifications']");
        if (pushNotifications.isDisabled()) pushNotifications.click();
        if (emailNotifications.isDisabled()) emailNotifications.click();
        page.goBack();

        transactionsHistoryBtn.click();
        Assert.assertEquals(page.url(), "https://ernx-consumer.vercel.app/settings/transactions", "Transaction History navigation failed.");
        page.goBack();
    }

    @AfterClass
    public void tearDown() {
        page.close();
        browser.close();
        playwright.close();
    }
}
