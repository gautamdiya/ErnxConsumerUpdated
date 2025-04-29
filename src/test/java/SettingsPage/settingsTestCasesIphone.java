package SettingsPage;

import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import LoginPage.EmailUtils;

public class settingsTestCasesIphone {
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
    private static final Logger logger = Logger.getLogger(settingsTestCasesIphone.class.getName());

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
            String childCreatedName = page.locator("(//h1[@class='pfont-700 text-lg'])[last()]").textContent();
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
            String childCreatedName = page.locator("(//h1[@class='pfont-700 text-lg'])[last()]").textContent();
            Thread.sleep(5000);
            logger.info("Verifying the Name of child and URL is correct");
            String currentUrl = page.url();
            Assertions.assertTrue(currentUrl.contains("https://ernx-consumer.vercel.app/game"), "Login Failed'");
            Assertions.assertTrue(childCreatedName.contains(childName), "Child name is wrong");
            logger.info("Login Success!!!!");
        }
    }

    // not in the new version but was in flow(figma)
    public static void referrelInvite(String email) throws InterruptedException {
        login();
        Locator settingsTab = page.locator("//span[normalize-space()='Settings']");
        settingsTab.click();
    }

    public static void changeParentName() throws InterruptedException {
        login();
        logger.info("Starting changeParentName Test Case!!!");
        Locator settingsTab = page.locator("//span[normalize-space()='Settings']");
        settingsTab.click();
        Locator accInfo = page.locator("//button[normalize-space()='Account Info']");
        accInfo.click();
        Locator firstName = page.locator("//input[@name='first_name']");
        String fname = "NewTest";
        String sname = "test";
        firstName.clear();
        firstName.type(fname);
        Locator surname = page.locator("//input[@name='last_name']");
        surname.clear();
        surname.type(sname);
        Locator saveChangesBtn = page.locator("//button[normalize-space()='Save Changes']");
        saveChangesBtn.click();
        logger.info("Saving the Changes");
        Locator popup = page.locator("//div[@role='status']");
        popup.waitFor(new Locator.WaitForOptions().setTimeout(15000)); // wait max 15 seconds
        if (popup.isVisible()) {
            System.out.println("Data Updated");
        } else {
            throw new AssertionError("Data Updated popup did not appear!");
        }
        logger.info("changeParentName Test Case Passed !!!");

    }

    public static void tearDown() {
        page.close();
        browser.close();
        playwright.close();
    }

    public static void main(String[] args) throws InterruptedException {
        changeParentName();
        tearDown();
    }

}
