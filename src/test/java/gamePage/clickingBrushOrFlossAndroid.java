package gamePage;

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

public class clickingBrushOrFlossAndroid {
	public static Playwright playwright;
	public static Browser browser;

	public static BrowserContext context;
	public static Page page;

	static {
		playwright = Playwright.create();
		browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
		context = browser.newContext(new Browser.NewContextOptions()
				.setViewportSize(393, 851) // Pixel 5 viewport size
				.setUserAgent(
						"Mozilla/5.0 (Linux; Android 11; Pixel 5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Mobile Safari/537.36")
				.setDeviceScaleFactor(2.75)
				.setIsMobile(true)
				.setHasTouch(true));

		page = context.newPage();
	}
	public static String childName = "tester";
	private static final Logger logger = Logger.getLogger(clickingBrushOrFlossAndroid.class.getName());

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

	public static void clickingOnActivities() throws InterruptedException {
		logger.info("Clicking on activities");
		Locator switchToNewAddedChild = page.locator("(//button[contains(@class,'embla__dot')])[last()]");
		switchToNewAddedChild.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
		switchToNewAddedChild.click();
		Locator progressBar = page.locator("(//span[contains(@style, 'color: rgb(255, 255, 255)') ])[last()]");

		//String childName = page.locator("(//h1[@class='pfont-700 text-lg'])[last()]").textContent();
		Locator activity1BrushIcon = page.locator("(//button[@type='button']//img[@alt='Practice 1'])[1]");
		Locator activity2FlossIcon = page.locator("(//img[@alt='Practice 2'])[1]");
		Locator addActivityIcon = page.locator(
				"path[d='M1.19951 18C1.19951 8.72162 8.72113 1.2 17.9995 1.2C27.2779 1.2 34.7995 8.72162 34.7995 18C34.7995 27.2784 27.2779 34.8 17.9995 34.8C8.72113 34.8 1.19951 27.2784 1.19951 18Z']");
		Locator activity3 = page.locator("//img[@alt='sleep']");
		Locator activity3Icon = page.locator("//img[@alt='Chore']");
		//Locator plusBttn = page.locator("(//*[local-name()='svg' and @width='30' and @height='30'])[3]");
		//Locator minusBttn = page.locator("(//*[local-name()='svg' and @width='30' and @height='30'])[2]");
		Locator yesBtn = page.locator("//button[normalize-space()='Yes']");
		Locator streakIcon = page.locator("path[stroke='#FFA622']");
		Locator counter = page
				.locator("(//span[contains(@style,'font-weight: bold') and contains(@style,'color: rgb')])[last()]");
		// After clicking first Brush Icon
		logger.info("Clicking on activity 1");
		String counterBefore1 = counter.textContent();
		String barBefore1 = progressBar.textContent();
		activity1BrushIcon.click();
		yesBtn.click();
		Thread.sleep(2000);
		String counterAfter1 = counter.textContent();
		String barAfter1 = progressBar.textContent();
		assertTrue(Integer.parseInt(barBefore1.replace("%", "").trim()) < Integer
				.parseInt(barAfter1.replace("%", "").trim()), "ProgressBar is not Updated!");
		assertTrue(Integer.parseInt(counterBefore1.replace("%", "").trim()) < Integer
				.parseInt(counterAfter1.replace("%", "").trim()), "ProgressBar is not Updated!");
		logger.info("activity 1 clicked and content verified");
		logger.info("Clicking on activity 2");
		String barBefore2 = progressBar.textContent();
		String counterBefore2 = counter.textContent();
		activity2FlossIcon.click();
		yesBtn.click();
		Thread.sleep(2000);
		String barAfter2 = progressBar.textContent();
		String counterAfter2 = counter.textContent();
		assertTrue(Integer.parseInt(barBefore2.replace("%", "").trim()) < Integer
				.parseInt(barAfter2.replace("%", "").trim()), "ProgressBar is not Updated!");
		assertTrue(Integer.parseInt(counterBefore2.replace("%", "").trim()) < Integer
				.parseInt(counterAfter2.replace("%", "").trim()), "ProgressBar is not Updated!");
		logger.info("activity 2 clicked and content verified");
		logger.info("adding an activity");
		addActivityIcon.click();
		activity3.click();
		//plusBttn.click();
		page.locator("//button[contains(text(),'Apply')]").click();
		logger.info("clicking on activity 3 ");
		String barBefore3 = progressBar.textContent();
		String counterBefore3 = counter.textContent();
		activity3Icon.click();
		Thread.sleep(2000);
		String barAfter3 = progressBar.textContent();
		String counterAfter3 = counter.textContent();
		assertTrue(Integer.parseInt(barBefore3.replace("%", "").trim()) < Integer
				.parseInt(barAfter3.replace("%", "").trim()), "ProgressBar is not Updated!");
		assertTrue(Integer.parseInt(counterBefore3.replace("%", "").trim()) < Integer
				.parseInt(counterAfter3.replace("%", "").trim()), "ProgressBar is not Updated!");
		if (streakIcon.isVisible()) {
			System.out.println("Streak is Created for the day....After clicking on 3 Activities");
		}
		logger.info("activity 3 clicked and content verified");
	}

	public static void tearDown() {
		page.close();
		browser.close();
		playwright.close();
	}

	public static void main(String[] args) throws InterruptedException {
		login();
		clickingOnActivities();
		tearDown();

	}
}
