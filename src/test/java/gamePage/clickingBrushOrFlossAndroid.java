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
    public static  Page page;
    public static String childName = "tester";
    private static final Logger logger = Logger.getLogger(clickingBrushOrFlossAndroid.class.getName());

    @BeforeClass
    public void setUp() throws InterruptedException {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(393, 851)
                .setUserAgent("Mozilla/5.0 (Linux; Android 11; Pixel 5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Mobile Safari/537.36")
                .setDeviceScaleFactor(2.75)
                .setIsMobile(true)
                .setHasTouch(true));

        page = context.newPage();
        login();
    }

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
			String childCreatedName = page.locator("(//h1[@class='pfont-700 text-lg'])[last()]").textContent();
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
			String childCreatedName = page.locator("(//h1[@class='pfont-700 text-lg'])[last()]").textContent();
			Thread.sleep(5000);
			logger.info("Verifying the Name of child and URL is correct");
			String currentUrl = page.url();
			assertTrue(currentUrl.contains("https://ernx-consumer.vercel.app/game"), "Login Failed");
			assertTrue(childCreatedName.contains(childName), "Child name is wrong");
			logger.info("Login Success!!!!");
		}
	}

	@Test
	public void clickingOnActivities() throws InterruptedException {
		logger.info("Clicking on activities");
		Locator switchToNewAddedChild = page.locator("(//button[contains(@class,'embla__dot')])[last()]");
		switchToNewAddedChild.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
		switchToNewAddedChild.click();
		Locator progressBar = page.locator("(//div[@role='progressbar'])[last()]");
		Locator activity1BrushIcon = page.locator("(//img[@alt='Practice 1'])[1]");
		Locator activity2FlossIcon = page.locator("(//img[@alt='Practice 2'])[1]");
		Locator addActivityIcon = page.locator("path[d='M1.19951 18C1.19951 8.72162 8.72113 1.2 17.9995 1.2C27.2779 1.2 34.7995 8.72162 34.7995 18C34.7995 27.2784 27.2779 34.8 17.9995 34.8C8.72113 34.8 1.19951 27.2784 1.19951 18Z']");
		Locator activity3 = page.locator("//img[@alt='sleep']");
		Locator activity3Icon = page.locator("//img[@alt='Chore']");
		Locator yesBtn = page.locator("//button[normalize-space()='Yes']");
		Locator bottomText = page.locator("//p[@class='text-sm']");
		Locator counter = page.locator("(//span[contains(@style,'font-weight: bold') and contains(@style,'color: rgb')])[last()]");

		// Activity 1
		Thread.sleep(5000);
		logger.info("Clicking on activity 1");
		String counterBefore1 = counter.textContent();
		String progressValue1 = progressBar.getAttribute("aria-valuetext");
		System.out.println(progressValue1);
		activity1BrushIcon.click();
		Thread.sleep(2000);
		String counterAfter1 = counter.textContent();
		String progressValue2 = progressBar.getAttribute("aria-valuetext");
		System.out.println(progressValue2);
		assertTrue(Integer.parseInt(progressValue1.replace("%", "").trim()) < Integer.parseInt(progressValue2.replace("%", "").trim()), "ProgressBar is not Updated!");
		assertTrue(Integer.parseInt(counterBefore1.replace("%", "").trim()) < Integer.parseInt(counterAfter1.replace("%", "").trim()), "Counter is not Updated!");
		logger.info("Activity 1 completed");

		// Activity 2
		logger.info("Clicking on activity 2");
		String barBefore2 = progressBar.textContent();
		String counterBefore2 = counter.textContent();
		activity2FlossIcon.click();
		Thread.sleep(2000);
		String barAfter2 = progressBar.textContent();
		String counterAfter2 = counter.textContent();
		assertTrue(Integer.parseInt(barBefore2.replace("%", "").trim()) < Integer.parseInt(barAfter2.replace("%", "").trim()), "ProgressBar is not Updated!");
		assertTrue(Integer.parseInt(counterBefore2.replace("%", "").trim()) < Integer.parseInt(counterAfter2.replace("%", "").trim()), "Counter is not Updated!");
		logger.info("Activity 2 completed");

		// Activity 3
		logger.info("Adding activity 3");
		addActivityIcon.click();
		activity3.click();
		page.locator("//button[contains(text(),'Apply')]").click();
		logger.info("Clicking on activity 3");
		String barBefore3 = progressBar.textContent();
		String counterBefore3 = counter.textContent();
		activity3Icon.click();
		Thread.sleep(2000);
		String barAfter3 = progressBar.textContent();
		String counterAfter3 = counter.textContent();
		assertTrue(Integer.parseInt(barBefore3.replace("%", "").trim()) < Integer.parseInt(barAfter3.replace("%", "").trim()), "ProgressBar is not Updated!");
		assertTrue(Integer.parseInt(counterBefore3.replace("%", "").trim()) < Integer.parseInt(counterAfter3.replace("%", "").trim()), "Counter is not Updated!");

		if (bottomText.textContent().contains("You completed 3 activities today and moved forward to space 1!")) {
			System.out.println("text changed....After clicking on 3 Activities");
		}
		logger.info("Activity 3 completed");
	}

	@AfterClass
	public void tearDown() {
		page.close();
		browser.close();
		playwright.close();
	}
}
