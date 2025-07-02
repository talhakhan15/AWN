package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Stablizations {
  private  WebDriver driver;
  private  WebDriverWait wait;
    // Wait for the page to load completely
    private void waitForPageLoad() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        wait.until(webDriver -> js.executeScript("return document.readyState").equals("complete"));
    }

    // Wait for DOM elements to settle before interacting
    private void waitForDomToBeStable() {
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
