package dropdownAndSynchronization;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class PhpTravelsFormAutomation {
    private WebDriver driver;

    @SuppressWarnings("deprecation")
	@BeforeTest
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void fillPhpTravelsForm() throws IOException, InterruptedException {
        driver.get("https://phptravels.com/demo/");

     //   WebDriverWait wait = new WebDriverWait(driver, 10);
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofMillis(10));

        // Fill in form details
        WebElement FirstnameInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("first_name")));
        FirstnameInput.sendKeys("John");
        
        WebElement LastnameInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("last_name")));
        LastnameInput.sendKeys("Doe");
        
        WebElement BusinessnameInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("business_name")));
        BusinessnameInput.sendKeys("Guvi");
        


        WebElement emailInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("email")));
        emailInput.sendKeys("johndoe@example.com");

        // Logic for sum verification
        WebElement sum1 = driver.findElement(By.id("numb1"));
        WebElement sum2 = driver.findElement(By.id("numb2"));

        int value1 = Integer.parseInt(sum1.getText());
        int value2 = Integer.parseInt(sum2.getText());
        int sum = value1 + value2;
        
        WebElement captchaInput = driver.findElement(By.xpath("/html/body/main/section[1]/div/div/div[1]/div/div/div/div/div/div/div/div[1]/div[4]/div[2]/div/input"));
        captchaInput.sendKeys(String.valueOf(sum));
        
     // Submit form
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Submit')]")));
        submitButton.click();

      
        // Explicit wait for the success message
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(15)); // Duration can be used for timeout
        WebElement successMessage = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"swup\"]/section[1]/div/div/div[1]/div/div/div/div/div/div/div/div[3]/p")));

        // Verify form submission message
        String expectedMessage = "We have sent your demo credentials to your email please check your email to test demo website. if message not found inbox please check spam folder";
        String actualMessage = successMessage.getText();
        Assert.assertEquals(actualMessage, expectedMessage, "Form submission message verification failed");
		



      //capture screenshot of the page
		TakesScreenshot tk=(TakesScreenshot) driver;
		File f=tk.getScreenshotAs(OutputType.FILE);
		File f1=new File("C:\\Users\\user\\eclipse-workspace\\DropdownAndSynchronization\\dashboard.png");
		FileUtils.copyFile(f,f1);//copy the file from one type to another
		System.out.println("Screenshot captured successfully....");
		
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
