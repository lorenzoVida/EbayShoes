package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.home;
import pages.searchProduct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

public class shopShoes {

    private static WebDriver driver;
	private static JavascriptExecutor js;
	private static home eshop;
	private static searchProduct search;

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		js = (JavascriptExecutor) driver;
		driver.get("https://www.ebay.com/");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		eshop = new home(driver);
		search = new searchProduct(driver);
	}

	@AfterClass
	public void teardown() {
		if (driver != null) {
			 //driver.quit();
	   }
	}

	@Test
	public void searchShoes() {

		eshop.txtbuscarArticulos().sendKeys("shoes");
		eshop.btnBuscar().click();
		js.executeScript("window.scrollBy(0,250)");
	}

	@Test(dependsOnMethods = {"searchShoes"})
	public void selectBrand() {
		search.txtboxBrand().sendKeys("Puma");
		search.linkPuma().click();
	}

	@Test(dependsOnMethods = {"selectBrand"})
	public void shoeSize() {
	/*	try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		search.talla10().click();
	}

	@Test(dependsOnMethods = {"shoeSize"})
	public void numberResults() {
		System.out.println(search.numberOfResults().getText());
		js.executeScript("window.scrollBy(0,250)");
	}

	@Test(dependsOnMethods = {"numberResults"})
	public void sortByAscendingPrice() {
		search.cboBestResult().click();
		search.mejorResultado().click();
		search.ordenarMayoraMenor().click();
	}

	@Test(dependsOnMethods = { "sortByAscendingPrice" })
	public void ConfirmProducts(){
		assertEquals("Puma Clyde X WWE dinero en el Bank Oro tenis zapatos Limited 100 pares nuevo 10", search.TextoTercerRe().getText());
		assertEquals("2009 PUMA UNDFTD 24K Mediados de oro metálico blanco Undefeated 349567-01 nuevo 10", search.TextoCuartoRe().getText());
		assertEquals("Puma Undefeated 24K Mid oro metálico metal líquido blanco 349567-01 hombres Sz10", search.TextoQuintoRe().getText());
		System.out.println("Assert a los primeros 5 resultados OK");

	}

	@Test(dependsOnMethods = { "sortByAscendingPrice" })
	public void ImprimirProductosConPrecios() {

		System.out.println("1 Producto: " + search.TextoPrimerRe().getText() + " Precio: " + search.PrecioPrimerRe().getText());
		System.out.println("2 Producto: " + search.TextoSegunRe().getText() + " Precio: " + search.PrecioSegunRe().getText());
		System.out.println("3 Producto: " + search.TextoTercerRe().getText() + " Precio: " + search.PrecioTercerRe().getText());
		System.out.println("4 Producto: " + search.TextoCuartoRe().getText() + " Precio: " + search.PrecioCuartoRe().getText());
		System.out.println("5 Producto: " + search.TextoQuintoRe().getText() + " Precio: " + search.PrecioQuintoRe().getText());



	}

	@Test(dependsOnMethods = { "sortByAscendingPrice" })
	public void OrdenarProductosNombre() {
		//driver.quit();

		List<String> NombresProductos = new ArrayList();
		NombresProductos.add(search.TextoPrimerRe().getText());
		NombresProductos.add(search.TextoSegunRe().getText());
		NombresProductos.add(search.TextoTercerRe().getText());
		NombresProductos.add(search.TextoCuartoRe().getText());
		NombresProductos.add(search.TextoQuintoRe().getText());

		Collections.sort(NombresProductos);
		System.out.println("Ordenados por nombre de productos: ");
		System.out.println(NombresProductos + "\n");

	}

	@Test(dependsOnMethods = { "sortByAscendingPrice" })
	public void OrdenarProductosPrecio() {
		String number1 = search.PrecioPrimerRe().getText();
		number1 = number1.substring(3);
		Double result1 = Double.parseDouble(number1.replace(" ", ""));

		String number2 = search.PrecioSegunRe().getText();
		number2 = number2.substring(3);
		Double result2 = Double.parseDouble(number2.replace(" ", ""));

		String number3 = search.PrecioTercerRe().getText();
		number3 = number3.substring(3);
		Double result3 = Double.parseDouble(number3.replace(" ", ""));

		String number4 = search.PrecioCuartoRe().getText();
		number4 = number4.substring(3);
		Double result4 = Double.parseDouble(number4.replace(" ", ""));

		String number5 = search.PrecioQuintoRe().getText();
		number5 = number5.substring(3);
		Double result5 = Double.parseDouble(number5.replace(" ", ""));

		List<Double> PreciosProductos = new ArrayList();
		PreciosProductos.add(result1);
		PreciosProductos.add(result2);
		PreciosProductos.add(result3);
		PreciosProductos.add(result4);
		PreciosProductos.add(result5);
		Collections.sort(PreciosProductos, Collections.reverseOrder());

		System.out.println("Ordenados por Precio: ");
		System.out.println(PreciosProductos + "\n");
	}
}
