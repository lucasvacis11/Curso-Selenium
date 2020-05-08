package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ExceptionTests {

	private WebDriver driver;

	@Parameters({ "browser" })
	@BeforeMethod(alwaysRun = true)
	private void setUp(String browser) {
		// Crear driver

		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
			driver = new ChromeDriver();
			break;
		case "firefox":
			System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");
			driver = new FireFoxDriver();
			break;

		default:
			System.setProperty("webdriver.chrome. driver", "src/main/resources/chromedriver");
			driver = new ChromeDriver();
			break;

		}

		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
		driver = new ChromeDriver();

		// maximizar ventana
		driver.manage().window().maximize();

		// implicite wait
		// driver.manage().timeouts().implicitlyWait(12, TimeUnit.SECONDS);

	}

	@Test(priority=1)
	public void notVisibleTest() {

		// abrir pagina de pruebas
		String url = "http://the-internet.herokuapp.com/dynamic_loading/1";
		driver.get(url);

		// Clickear boton de Start

		WebElement startButton = driver.findElement(By.xpath("//div[@id='start']/button[.='Start']"));
		startButton.click();

		// Validacion de texto exitoso
		// Extraer texto de finalizado
		WebElement textHello = driver.findElement(By.xpath("//div[@id='finish']/h4[.='Hello World!']"));

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(textHello));

		String actualTextHello = textHello.getText();

		// Comparar con texto esperado
		Assert.assertTrue(actualTextHello.contains("Hello World!"), "El mensaje es el esperado: " + textHello);

	}

	@Test(priority=2)
	public void timeOutTest() {

		// abrir pagina de pruebas
		String url = "http://the-internet.herokuapp.com/dynamic_loading/1";
		driver.get(url);

		// Clickear boton de Start

		WebElement startButton = driver.findElement(By.xpath("//div[@id='start']/button[.='Start']"));
		startButton.click();

		// Validacion de texto exitoso
		// Extraer texto de finalizado
		WebElement textHello = driver.findElement(By.xpath("//div[@id='finish']/h4[.='Hello World!']"));

		WebDriverWait wait = new WebDriverWait(driver, 2);

		try {
			wait.until(ExpectedConditions.visibilityOf(textHello));
		} catch (TimeoutException exception) {
			System.out.println("Exception catched: " + exception.getMessage());
		}

		String actualTextHello = textHello.getText();

		// Comparar con texto esperado
		Assert.assertTrue(actualTextHello.contains("Hello World!"), "El mensaje es el esperado: " + textHello);

	}

	@Test(priority=3)
	public void noSuchElementTest() {

		// abrir pagina de pruebas
		String url = "http://the-internet.herokuapp.com/dynamic_loading/2";
		driver.get(url);

		// Clickear boton de Start

		WebElement startButton = driver.findElement(By.xpath("//div[@id='start']/button[.='Start']"));
		startButton.click();
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		Assert.assertTrue(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//div[@id='finish']/h4[.='Hello World!']")), "Hello World!", "Could't found the message Hello World!");
		
		
		
		/*
		 * WebElement textHello =
		 * wait.until(ExpectedConditions.presenceOfElementLocated(By.
		 * xpath("//div[@id='finish']/h4[.='Hello World!']")));
		 * 
		 * String actualTextHello = textHello.getText();
		 * 
		 * // Comparar con texto esperado
		 * Assert.assertTrue(actualTextHello.contains("Hello World!"),
		 * "El mensaje es el esperado: " + textHello);
		 */
	}

	@AfterMethod(alwaysRun = true)
	private void tierDown() {
		// cerrar nevegador
		driver.quit();
	}

}
