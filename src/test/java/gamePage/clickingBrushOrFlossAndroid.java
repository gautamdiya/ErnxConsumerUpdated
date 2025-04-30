package gamePage;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.testng.Assert;
import org.testng.annotations.*;

import static org.testng.Assert.assertTrue;

import java.util.logging.Logger;

import LoginPage.EmailUtils;

public class clickingBrushOrFlossAndroid {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;
    private final String childName = "tester";
    private static final Logger logger = Logger.getLogger(clickingBrushOrFlossAndroid.class.getName());

    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(393, 851)
                .setUserAgent("Mozilla/5.0 (Linux; Android 11; Pixel 5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Mobile Safari/537.36")
                .setDeviceScaleFactor(2.75)
                .setIsMobile(true)
                .setHasTouch(true));

        page = context.newPage();
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
				"imap.gmail.com", // host
				"993", // port
				"automationtesttimechains@gmail.com", // username
				"pjkw iaiz qner ptvh", // password
				"Signup OTP", // subject
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
			assertTrue(currentUrl.contains("https://ernx-consumer.vercel.app/game"), "Login Failed");
			assertTrue(childCreatedName.equals(childName), "Child name is wrong");
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
			assertTrue(currentUrl.contains("https://ernx-consumer.vercel.app/game"), "Login Failed");
			assertTrue(childCreatedName.contains(childName), "Child name is wrong");
			logger.info("Login Success!!!!");
		}
    }

    @Test(priority = 2, dependsOnMethods = "login")
    public void clickingOnActivities() throws InterruptedException {
        Locator switchToNewAddedChild = page.locator("(//button[contains(@class,'embla__dot')])[last()]");
        switchToNewAddedChild.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        switchToNewAddedChild.click();

        Locator progressBar = page.locator("(//span[contains(@style, 'color: rgb(255, 255, 255)') ])[last()]");
        Locator counter = page.locator("(//span[contains(@style,'font-weight: bold') and contains(@style,'color: rgb')])[last()]");

        // Activity 1
        validateActivity("//img[@alt='Practice 1']", progressBar, counter);

        // Activity 2
        validateActivity("//img[@alt='Practice 2']", progressBar, counter);

        // Add & Validate Activity 3
        page.locator("path[d='M1.19951 18C1.19951 8.72162 8.72113 1.2 17.9995 1.2C27.2779 1.2 34.7995 8.72162 34.7995 18C34.7995 27.2784 27.2779 34.8 17.9995 34.8C8.72113 34.8 1.19951 27.2784 1.19951 18Z']").click();
        page.locator("//img[@alt='sleep']").click();
        page.locator("//button[contains(text(),'Apply')]").click();
        validateActivity("//img[@alt='Chore']", progressBar, counter);
    }

    private void validateActivity(String selector, Locator progressBar, Locator counter) throws InterruptedException {
        String barBefore = progressBar.textContent();
        String counterBefore = counter.textContent();
        page.locator(selector).click();
        Thread.sleep(2000);
        String barAfter = progressBar.textContent();
        String counterAfter = counter.textContent();

        Assert.assertTrue(Integer.parseInt(barBefore.replace("%", "").trim()) < Integer.parseInt(barAfter.replace("%", "").trim()), "ProgressBar not updated");
        Assert.assertTrue(Integer.parseInt(counterBefore.replace("%", "").trim()) < Integer.parseInt(counterAfter.replace("%", "").trim()), "Counter not updated");
    }

    @AfterClass
    public void tearDown() {
        page.close();
        browser.close();
        playwright.close();
    }
}
