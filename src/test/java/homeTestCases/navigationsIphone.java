package homeTestCases;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.WaitForSelectorState;
import LoginPage.EmailUtils;

public class navigationsIphone {
    public static Playwright playwright;
    public static Browser browser;

    public static BrowserContext context;
    public static Page page;

    static {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(390, 844) // iPhone 13 dimensions
                .setUserAgent(
                        "Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.0 Mobile/15E148 Safari/604.1")
                .setDeviceScaleFactor(3.0)
                .setIsMobile(true)
                .setHasTouch(true));

        page = context.newPage();
    }
    public static String childName = "tester";
    private static final Logger logger = Logger.getLogger(navigationsIphone.class.getName());

    public static void login() throws InterruptedException {
        logger.info("Starting browser and Test Case");
        page.navigate("https://ernx-consumer.vercel.app/login");
        logger.info("Login Page Opened");
        page.getByPlaceholder("Email").type("automationtesttimechains@gmail.com");
        page.locator("//button[contains(text(),'Next')]").click();
        Thread.sleep(5000);
        logger.info("Getting Otp and filling");
        String emailContent = EmailUtils.getOtpFromEmail(
                "imap.gmail.com", // host
                "993", // port
                "automationtesttimechains@gmail.com", // username
                "pjkw iaiz qner ptvh", // password or app-specific password
                "Signup OTP", // subject filter
                60 // timeout in seconds

        );
        Locator otpfield = page.locator("[id=otp-0]");
        otpfield.type(emailContent);
        Thread.sleep(3000);
        logger.info("Otp is Veified");
        page.locator("//span[normalize-space()='Settings']").click();
        page.locator("//button[normalize-space()='Children Details']").click();
        Locator addYourFistChildBttn = page.locator("//button[normalize-space()='Add Your First Child']");
        Locator addChildIcon = page.locator("path[d='M11 11V5H13V11H19V13H13V19H11V13H5V11H11Z']");
        if (addYourFistChildBttn.isVisible()) {
            addYourFistChildBttn.click();
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
            Assertions.assertTrue(currentUrl.contains("https://ernx-consumer.vercel.app/game"), "Login Failed'");
            Assertions.assertTrue(childCreatedName.equals(childName), "Child name is wrong");
            logger.info("Login Success!!!!");
        } else {
            addChildIcon.click();
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
            Assertions.assertTrue(currentUrl.contains("https://ernx-consumer.vercel.app/game"), "Login Failed'");
            Assertions.assertTrue(childCreatedName.contains(childName), "Child name is wrong");
            logger.info("Login Success!!!!");
        }
    }

    // public static void weeklyReward() throws InterruptedException{
    // login();
    // logger.info("Starting weeklyReward Test Case!!!");
    // Locator switchToNewAddedChild =
    // page.locator("(//button[contains(@class,'embla__dot')])[last()]");
    // switchToNewAddedChild.waitFor(new
    // Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    // switchToNewAddedChild.click();
    // Locator weekSection=page.locator("(//div[@class='flex justify-between w-full
    // pfont-600 text-sm p1'])[1]");
    // weekSection.click();
    // Locator weeklyRewardTab=page.locator("//button[normalize-space()='Weekly
    // Reward']");
    // Locator streakViewTab=page.locator("//button[normalize-space()='Streak
    // View']");
    // Locator rules=page.locator("//span[normalize-space()='Read Reward Rules']");
    // String totalPtsThisWeek=page.locator("//div[contains(@class, 'text-2xl') and
    // .//img[@src='/logo/x_logo.svg']]").textContent();
    // logger.info("Weekly Tab Section present");
    // System.out.println("Total points this week :"+ totalPtsThisWeek);
    // rules.click();
    // assertTrue(page.locator("//h2[normalize-space()='How It
    // Works']").isVisible(),"Rules Tab not present");
    // page.locator("//button[normalize-space()='Back']").click();
    // streakViewTab.click();
    // String streakShown=page.locator("//div[contains(@class, 'rounded-full')and
    // contains(@class, 'inline-flex')]").textContent();
    // logger.info("Streak Tab Section present");
    // System.out.println("Streak :"+ streakShown);
    // assertTrue(page.locator("//div[@class='space-y-4 rdp-caption_start
    // rdp-caption_end']").isVisible(),"Calendar is not present");
    // page.locator("path[d='m6 6 12 12']").click();
    // logger.info("weeklyReward Test Case Passed!!!");
    // }
    public static void chooseReward() throws InterruptedException {
        login();
        logger.info("Starting chooseReward Test Case!!!");
        Locator switchToNewAddedChild = page.locator("(//button[contains(@class,'embla__dot')])[last()]");
        switchToNewAddedChild.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        switchToNewAddedChild.click();
        page.locator("(//img[contains(@alt,'reward')])[last()]").click();
        Locator rewardPresent = page.locator("//h3[normalize-space()='ERNX Dev test']");
        rewardPresent.waitFor(new Locator.WaitForOptions().setTimeout(2000));
        rewardPresent.click();
        page.locator("//button[normalize-space()='Select As Reward']").click();
        logger.info("chooseReward Test Case Passed!!!");
    }

    public static void gameRules() {
        logger.info("Starting game rules Test Case!!!");
        page.locator("//div[@class='w-2/5 flex items-center pfont-600 text-sm gap-x-2']").click();
        Locator gameRulesText=page.locator("(//h2[@class='text-base pfont-700 mb-4'])[1]");
        assertTrue("https://ernx-consumer.vercel.app/game/how-it-works".equals(page.url()), "Not navigated to game rules page..");
        assertTrue(gameRulesText.isVisible(),
                "Game Rules content is Wrong");
        assertTrue(page.locator("//div[contains(text(),'How')]").isVisible(),
                "how it works content is Wrong");
        logger.info("Content Verified on Game Rules page");
        logger.info("game rules Test Case Passed!!!");

    }
    public static void historyPage() throws InterruptedException {
        logger.info("Starting historyPage Test Case!!!");
        page.locator("//span[contains(text(),'History')]").click();
        assertTrue("https://ernx-consumer.vercel.app/history".equals(page.url()), "Not able to navigate to history");
        logger.info("We are on History Page");
        String child1Name = page.locator("(//h1[@class='pfont-700 text-xl'])[1]").textContent();
        String pointsHistory1 = page.locator("//h2[@class='pfont-800 text-base font-bold my-6']").textContent();
        logger.info("Checking for first Child Points History");
        if (pointsHistory1.contains(child1Name)) {
            System.out.println("Points History diplayed for 1st child!");
        } else {
            throw new AssertionError("Points history Display error!");
        }
        Locator secondChildScrollDot = page.locator("(//button[contains(@class,'embla__dot')])[2]");
        if (secondChildScrollDot.isVisible()) {
            secondChildScrollDot.click();
            Thread.sleep(2000);
            String child2Name = page.locator("(//h1[@class='pfont-700 text-xl'])[2]").textContent();
            String pointsHistory2 = page.locator("//h2[@class='pfont-800 text-base font-bold my-6']").textContent();
            logger.info("Checking for second Child Points History");
            if (pointsHistory2.contains(child2Name)) {
                System.out.println("Points History diplayed for 2nd child!");
            } else {
                throw new AssertionError("Points history Display error!");
            }
        }
        logger.info("historyPage Test Case Passed!!!");
    }

    public static void sendExtraPoints() {
        logger.info("Starting historyPage Test Case!!!");
        page.locator("//span[contains(text(),'History')]").click();
        assertTrue("https://ernx-consumer.vercel.app/history".equals(page.url()), "Not able to navigate to history");
        logger.info("We are on History Page");
        page.locator("//button[normalize-space()='Give Extra Points']").click();
        page.locator("(//*[local-name()='svg' and @width='30' and @height='30'])[2]").click();
        page.locator("//button[normalize-space()='Send Points']").click();
        Locator extraptsSend = page.locator("//p[@class='text-center text-xs mx-5 scolor mt-3']");
        String extraPtsText = extraptsSend.textContent();
        System.out.println(extraPtsText);
        page.locator("//button[normalize-space()='Done']").click();
        Locator textVerify = page.locator("(//p[@class='text-sm pcolor'])[1]");
        String text = textVerify.textContent();
        assertTrue(text.equals("Extra Points"), "Extra Points not sent");
    }

    public static void storeNavigation() throws InterruptedException {
        logger.info("Starting storeNavigation Test Case!!!");
        page.locator("//span[contains(text(),'Store')]").click();
        assertTrue("https://ernx-consumer.vercel.app/store".equals(page.url()), "Not able to navigate to Store Page");
        logger.info("We are on Store Page");
        Locator continueBtn = page.locator("//button[normalize-space()='Continue']");
        assertTrue(continueBtn.isDisabled(), "Continue button is not disabled");
        page.locator("//label[@for='radioButton-0']").click();
        continueBtn.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        page.waitForCondition(() -> continueBtn.isEnabled());
        continueBtn.click();
        Thread.sleep(5000);
        page.goBack();
    }

    public static void settingsNavigations() {
        logger.info("Starting settingsNavigations Test Case!!!");
        page.locator("//span[contains(text(),'Settings')]").click();
        assertTrue("https://ernx-consumer.vercel.app/settings".equals(page.url()), "Not able to navigate to settings");
        logger.info("We are on Settings Page");
        Locator accInfoBtn = page.locator("//button[normalize-space()='Account Info']");
        Locator childrenInfoBtn = page.locator("//button[normalize-space()='Children Details']");
        Locator notificationsBtn = page.locator("//button[normalize-space()='Notifications']");
        Locator transactionsHistoryBtn = page.locator("//button[normalize-space()='Transaction History']");
        accInfoBtn.click();
        assertTrue("https://ernx-consumer.vercel.app/account".equals(page.url()),
                "Not able to navigate to AccountInfo in Settings page..");
        logger.info("We are able to navigate on accountInfo ");
        page.goBack();
        childrenInfoBtn.click();
        assertTrue("https://ernx-consumer.vercel.app/settings/children".equals(page.url()),
                "Not able to navigate to ChildrenInfo in Settings page..");
        logger.info("We are able to navigate on childrenInfo ");
        page.goBack();
        notificationsBtn.click();
        assertTrue("https://ernx-consumer.vercel.app/settings/notifications".equals(page.url()),
                "Not able to navigate to Notifications in Settings page..");
        Locator pushNotifications = page.locator("//button[@name='push_notifications']");
        Locator emailNotifications = page.locator("//button[@name='emails_notifications']");
        if (pushNotifications.isDisabled()) {
            pushNotifications.click();
            logger.info("push notifications are on");
        }
        if (emailNotifications.isDisabled()) {
            emailNotifications.click();
            logger.info("email notifications are on");
        }
        page.goBack();
        transactionsHistoryBtn.click();
        assertTrue("https://ernx-consumer.vercel.app/settings/transactions".equals(page.url()),
                "Not able to navigate to transactions page in Settings page..");
        page.goBack();
        logger.info("settingsNavigations Test Case Passed!!!");
    }

    public static void tearDown() {
        page.close();
        browser.close();
        playwright.close();
    }

    public static void main(String[] args) throws InterruptedException {
        // weeklyReward();
        chooseReward();
        gameRules();
        historyPage();
        storeNavigation();
        settingsNavigations();
        tearDown();
    }
}
