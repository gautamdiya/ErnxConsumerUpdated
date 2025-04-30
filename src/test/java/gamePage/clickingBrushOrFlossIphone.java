package gamePage;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.testng.Assert;
import org.testng.annotations.*;

import static org.testng.Assert.assertTrue;

import java.util.logging.Logger;

import LoginPage.EmailUtils;

public class clickingBrushOrFlossIphone{

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;
    private static final String childName = "tester";
    private static final Logger logger = Logger.getLogger(clickingBrushOrFlossIphone.class.getName());

    @BeforeClass
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(390, 844) // iPhone 13 dimensions
                .setUserAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.0 Mobile/15E148 Safari/604.1")
                .setDeviceScaleFactor(3.0)
                .setIsMobile(true)
                .setHasTouch(true));
        page = context.newPage();
    }

    @Test(priority = 1)
    public void loginAndCreateChildIfNotExists() throws InterruptedException {
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

    @Test(priority = 2, dependsOnMethods = "loginAndCreateChildIfNotExists")
    public void clickingOnActivities() throws InterruptedException {
        logger.info("Clicking on activities");

        Locator switchChildBtn = page.locator("(//button[contains(@class,'embla__dot')])[last()]");
        switchChildBtn.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        switchChildBtn.click();

        Locator progressBar = page.locator("(//span[contains(@style, 'color: rgb(255, 255, 255)') ])[last()]");
        Locator counter = page.locator("(//span[contains(@style,'font-weight: bold') and contains(@style,'color: rgb')])[last()]");

        Locator brushIcon = page.locator("(//img[@alt='Practice 1'])[1]");
        Locator flossIcon = page.locator("(//img[@alt='Practice 2'])[1]");
        Locator addActivityIcon = page.locator("path[d='M1.19951 18C1.19951 8.72162 8.72113 1.2 17.9995 1.2C27.2779 1.2 34.7995 8.72162 34.7995 18C34.7995 27.2784 27.2779 34.8 17.9995 34.8C8.72113 34.8 1.19951 27.2784 1.19951 18Z']");
        Locator sleepActivity = page.locator("//img[@alt='sleep']");
        Locator sleepIcon = page.locator("//img[@alt='Chore']");
        Locator streakIcon = page.locator("path[stroke='#FFA622']");

        // Activity 1: Brush
        logger.info("Clicking on activity 1");
        String before1 = progressBar.textContent();
        String counter1 = counter.textContent();
        brushIcon.click();
        Thread.sleep(2000);
        String after1 = progressBar.textContent();
        String counterAfter1 = counter.textContent();
        Assert.assertTrue(Integer.parseInt(before1.replace("%", "").trim()) < Integer.parseInt(after1.replace("%", "").trim()), "Progress bar not updated");
        Assert.assertTrue(Integer.parseInt(counter1.replace("%", "").trim()) < Integer.parseInt(counterAfter1.replace("%", "").trim()), "Counter not updated");

        // Activity 2: Floss
        logger.info("Clicking on activity 2");
        String before2 = progressBar.textContent();
        String counter2 = counter.textContent();
        flossIcon.click();
        Thread.sleep(2000);
        String after2 = progressBar.textContent();
        String counterAfter2 = counter.textContent();
        Assert.assertTrue(Integer.parseInt(before2.replace("%", "").trim()) < Integer.parseInt(after2.replace("%", "").trim()), "Progress bar not updated");
        Assert.assertTrue(Integer.parseInt(counter2.replace("%", "").trim()) < Integer.parseInt(counterAfter2.replace("%", "").trim()), "Counter not updated");

        // Activity 3: Sleep
        logger.info("Adding and clicking on activity 3");
        addActivityIcon.click();
        sleepActivity.click();
        page.locator("//button[contains(text(),'Apply')]").click();
        Thread.sleep(1000);

        String before3 = progressBar.textContent();
        String counter3 = counter.textContent();
        sleepIcon.click();
        Thread.sleep(2000);
        String after3 = progressBar.textContent();
        String counterAfter3 = counter.textContent();
        Assert.assertTrue(Integer.parseInt(before3.replace("%", "").trim()) < Integer.parseInt(after3.replace("%", "").trim()), "Progress bar not updated");
        Assert.assertTrue(Integer.parseInt(counter3.replace("%", "").trim()) < Integer.parseInt(counterAfter3.replace("%", "").trim()), "Counter not updated");

        if (streakIcon.isVisible()) {
            logger.info("Streak created for the day after 3 activities.");
        }
    }

    @AfterClass
    public void tearDown() {
        if (page != null) page.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
