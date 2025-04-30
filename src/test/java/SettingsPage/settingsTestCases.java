package SettingsPage;

import java.util.logging.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.microsoft.playwright.*;

import LoginPage.EmailUtils;

public class settingsTestCases {
    public static Playwright playwright;
    public static Browser browser;
    public static Page page;
    public static String childName = "tester";
    private static final Logger logger = Logger.getLogger(settingsTestCases.class.getName());

    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
    }

    @Test(priority = 1)
    public void login() throws InterruptedException {
        logger.info("Starting browser and Test Case");
        page.navigate("https://ernx-consumer.vercel.app/login");
        logger.info("Login Page Opened");
        page.getByPlaceholder("Email").type("automationtesttimechains@gmail.com");
        page.locator("//button[contains(text(),'Next')]").click();
        Thread.sleep(5000);
        logger.info("Getting Otp and filling");
        String emailContent = EmailUtils.getOtpFromEmail(
                "imap.gmail.com", "993", "automationtesttimechains@gmail.com", 
                "pjkw iaiz qner ptvh", "Signup OTP", 60
        );
        Locator otpfield = page.locator("[id=otp-0]");
        otpfield.type(emailContent);
        Thread.sleep(3000);
        logger.info("Otp is Verified");
    
        logger.info("Login Success!!!!");
    }

    @Test(priority = 2)
    public void changeParentName() throws InterruptedException {
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
            logger.info("Data Updated");
        } else {
            throw new AssertionError("Data Updated popup did not appear!");
        }
        logger.info("changeParentName Test Case Passed !!!");
    }

    @AfterClass
    public void tearDown() {
        page.close();
        browser.close();
        playwright.close();
    }
}
