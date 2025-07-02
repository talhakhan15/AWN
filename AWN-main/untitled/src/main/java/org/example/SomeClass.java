package org.example;

import net.bytebuddy.asm.Advice;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Driver;
import java.time.Duration;

public class SomeClass {
    WebDriver driver;
    WebDriverWait wait;
    //    MyWaits w = new MyWaits();
//    Stablizations s = new Stablizations();
    private String myUrl = "https://uat.awn.xintdev.com/login";

    public boolean setup() throws InterruptedException {

       try
       {

           System.setProperty("webdriver.chrome.silentOutput", "true");
           java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(java.util.logging.Level.OFF);

           System.setProperty("webdriver.chrome.driver", "C:\\Chrome Webdriver\\chromedriver.exe");


           // Set Chrome options for headless mode
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");  // Run without GUI
        options.addArguments("--disable-gpu");  // Required for some systems
        options.addArguments("--window-size=1920,1080");  // Set screen size (optional)
        options.addArguments("--no-sandbox");  // Bypass OS security model
        options.addArguments("--disable-dev-shm-usage");  // Overcome limited resources issues

        driver = new ChromeDriver(options);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.get(myUrl);

        return true;
    }
        catch(Exception e)
        {
            return  false;
        }
    }

    boolean login() {
        try {
            driver.get(myUrl);
            WebElement email = driver.findElement(By.id("email"));
            email.clear();
            email.sendKeys("ANYADMIN@awn.com");
            WebElement pass = driver.findElement(By.id("password"));
            pass.clear();
            pass.sendKeys("somePassw00rd" + Keys.ENTER);

            waitForPageLoad();

            // Alternative to handle dynamic elements if loader is not identifiable
            waitForDomToBeStable();


            logout();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void logout() {
        clickWhenClickable(By.xpath("//button[@class='btn  p-0 dropdown-toggle']"));
        clickWhenClickable(By.xpath("//span[text()='Logout']"));

    }

    private void clickWhenClickable(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    private void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }


    // Wait for the page to load completely
    public void waitForPageLoad() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        wait.until(webDriver -> js.executeScript("return document.readyState").equals("complete"));
    }

    // Wait for DOM elements to settle before interacting
    public void waitForDomToBeStable() {
        int prevCount = driver.findElements(By.xpath("//*")).size();
        int sameCount = 0;
        while (sameCount < 3) {  // Wait until element count stabilizes
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }
            int currentCount = driver.findElements(By.xpath("//*")).size();
            if (currentCount == prevCount) sameCount++;
            else sameCount = 0;
            prevCount = currentCount;
        }
    }
}
