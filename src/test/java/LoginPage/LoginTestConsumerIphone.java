package LoginPage;

import java.util.logging.Logger;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.microsoft.playwright.*;

public class LoginTestConsumerIphone {
    private static final Logger logger = Logger.getLogger(LoginTestConsumerIphone.class.getName());
    public static Playwright playwright;
    public static Browser browser;

    public static BrowserContext context;
    public static Page page;

    static {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        context = browser.newContext(new Browser.NewContextOptions()
            .setViewportSize(390, 844) // iPhone 13 dimensions
            .setUserAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.0 Mobile/15E148 Safari/604.1")
            .setDeviceScaleFactor(3.0)
            .setIsMobile(true)
            .setHasTouch(true)
        );

        page = context.newPage();
    }

    public static String childName = "tester";

    @BeforeClass
    public void setup() {
        logger.info("Starting browser and Test Case");
    }

//    @Test
//    public void newLoginAddChild() throws InterruptedException {
//        page.navigate("https://ernx-consumer.vercel.app/login");
//        logger.info("Login Page Opened");
//        page.getByPlaceholder("Email").type("automationtesttimechains@gmail.com");
//        page.locator("//button[contains(text(),'Next')]").click();
//        Thread.sleep(5000);
//
//        logger.info("Getting Otp and filling");
//        String emailContent = EmailUtils.getOtpFromEmail(
//                "imap.gmail.com", // host
//                "993", // port
//                "automationtesttimechains@gmail.com", // username
//                "pjkw iaiz qner ptvh", // password or app-specific password
//                "Signup OTP", // subject filter
//                60 // timeout in seconds
//        );
//        Locator otpfield = page.locator("[id=otp-0]");
//        otpfield.type(emailContent);
//        Thread.sleep(3000);
//        logger.info("Otp is Verified");
//
//        Locator fname = page.locator("//input[@placeholder='Name']");
//        Locator surname = page.locator("//input[@placeholder='Surname']");
//        if (fname.isVisible()) {
//            fname.type("Test");
//            surname.type("Tester");
//            page.locator("//button[@type='submit']").click();
//            Locator addAChildBttn = page.locator("//button[contains(text(),'Add a child')]");
//            addAChildBttn.click();
//            page.getByPlaceholder("First Name or Nickname").type(childName);
//            page.locator("//span[contains(text(),'female')]").click();
//            page.locator("//button[contains(text(),'Next')]").click();
//            page.locator("//button[contains(text(),'Next')]").click();
//            page.locator("//p[contains(text(),'ERNX Dev test')]").click();
//            page.locator("//button[contains(text(),'Next')]").click();
//            page.locator("//button[contains(text(),'Finish')]").click();
//            String childCreatedName = page.locator("(//h1[@class='pfont-700 text-lg'])[last()-1]").textContent();
//            Thread.sleep(5000);
//            logger.info("Verifying the Name of child and URL is correct");
//            String currentUrl = page.url();
//            Assert.assertTrue(currentUrl.contains("https://ernx-consumer.vercel.app/game"), "Login Failed");
//            Assert.assertTrue(childCreatedName.equals(childName), "Child name is wrong");
//            logger.info("Created..");
//            logger.info("Login Success!!!!");
//        }
//    }

    @Test
    public void oldUserLoginAddChild() throws InterruptedException {
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
        logger.info("Otp is Verified");
        page.locator("//span[normalize-space()='Settings']").click();
        page.locator("//button[normalize-space()='Children Details']").click();
        Locator addYourFirstChildBttn = page.locator("//button[normalize-space()='Add Your First Child']");
        Locator addChildIcon = page.locator("path[d='M11 11V5H13V11H19V13H13V19H11V13H5V11H11Z']");

        if (addYourFirstChildBttn.isVisible()) {
            addYourFirstChildBttn.click();
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
            Assert.assertTrue(childCreatedName.equals(childName), "Child name is wrong");
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
            Assert.assertTrue(currentUrl.contains("https://ernx-consumer.vercel.app/game"), "Login Failed");
            Assert.assertTrue(childCreatedName.contains(childName), "Child name is wrong");
            logger.info("Login Success!!!!");
        }
    }

    @AfterClass
    public void tearDown() {
        page.close();
        browser.close();
        playwright.close();
    }
}
