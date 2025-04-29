package SettingsPage;

import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Locator;
import LoginPage.EmailUtils;

public class settingsTestCases {
	public static Playwright playwright = Playwright.create();
	public static Browser browser = playwright.chromium().launch(new LaunchOptions().setHeadless(false));
	public static Page page = browser.newPage();
	public static String childName = "tester";
	private static final Logger logger = Logger.getLogger(settingsTestCases.class.getName());

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

	// not in the new version but was in flow(figma)
	public static void referrelInvite(String email) throws InterruptedException {
		login();
		Locator settingsTab = page.locator("//span[normalize-space()='Settings']");
		settingsTab.click();
	}

	public static void changeParentName() throws InterruptedException {
		
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
		login();
		changeParentName();
		tearDown();
	}

}
