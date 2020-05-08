package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTests {

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
		//driver.manage().timeouts().implicitlyWait(12, TimeUnit.SECONDS);

	}

	@Test
	public void positiveLoginTest() {

		System.out.println("Starting loginTest.");

		// abrir pagina de test
		String url = "http://the-internet.herokuapp.com/login";
		driver.get(url);
		System.out.println("Page is opened.");

		// ingresar usuarios
		WebElement username = driver.findElement(By.id("username"));
		username.sendKeys("tomsmith");

		// ingresar contraseña
		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("SuperSecretPassword!");

		// clickear boton
 		WebElement loginButton = driver.findElement(By.tagName("button"));
 		//wait.until(ExpectedConditions.elementToBeClickable(loginButton));
		loginButton.click();

		// verificacion:
		// nueva url
		String expectedUrl = "http://the-internet.herokuapp.com/secure";
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl, expectedUrl, "La url actual no es la esperada.");

		// boton de logout
		WebElement logoutButton = driver.findElement(By.xpath("//a[@class='button secondary radius']"));
		Assert.assertTrue(logoutButton.isDisplayed(), "Logout boton no esta presente");

		// mensaje de confirmacion
		WebElement successMessage = driver.findElement(By.cssSelector("div#flash"));
		String expectedMessage = "You logged into a secure area!\n" + "×";
		String actualMessage = successMessage.getText();
		Assert.assertEquals(actualMessage, expectedMessage, "El texto actual no es el esperado:  \n" + expectedMessage
				+ "\nEl mensaje actual es:\n" + actualMessage);

	}

	@Parameters({ "username", "password", "expectedMessage" })
	public void negativeLoginTest(String username, String password, String expectedErrorMessage) {
		System.out.println("Starting test.");

		// abrir pagina de prueba
		String url = "http://the-internet.herokuapp.com/login";
		driver.get(url);
		System.out.println("Page is opened successfuly");

		// introducir usuario invalido
		WebElement usernameElement = driver.findElement(By.id("username"));
		usernameElement.sendKeys(username);
		// username.sendKeys("tomsmith");

		// introducir contraseña
		WebElement passwordElement = driver.findElement(By.xpath("/html//input[@id='password']"));
		passwordElement.sendKeys(password);

		// clickear boton de login
		WebElement logInButton = driver.findElement(By.xpath("//form[@id='login']//i[@class='fa fa-2x fa-sign-in']"));
		logInButton.click();

		// Verifications

		// mensaje de error, usuario invalido
		WebElement errorMessage = driver.findElement(By.id("flash"));
		String actualErrorMessage = errorMessage.getText();

		Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage), "El mensaje no es el esperado");

	}
	
	
	
	@Test
	public void NotVisibleTest() {
		
		//abrir pagina de pruebas 
		String url = "http://the-internet.herokuapp.com/dynamic_loading/1";
		driver.get(url);
		
		//Clickear boton de Start
		
		WebElement startButton = driver.findElement(By.xpath("//div[@id='start']/button[.='Start']"));
		startButton.click();
		
		// explicit wait
		
		
		
		
		//Validacion de texto exitoso
		//Extraer texto de finalizado
		WebElement textHello = driver.findElement(By.xpath("//div[@id='finish']/h4[.='Hello World!']"));
		String actualTextHello = textHello.getText();
		
		//Comparar con texto esperado
		Assert.assertTrue(actualTextHello.contains("Hello World!"), "El mensaje es el esperado");
		
		
		
		
		
	}

	@AfterMethod(alwaysRun = true)
	private void tierDown() {
		// cerrar nevegador
		driver.quit();
	}

}
