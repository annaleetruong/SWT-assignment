package BAITAP;

import driver.driverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import java.io.File;

public class TEST2 {
    @Test
    public void testCase02() {
        WebDriver driver = driverFactory.getChromeDriver();
        try {
            //1. Goto http://live.techpanda.org/
            driver.get("http://live.techpanda.org/");
            //2. Click on �MOBILE� menu
            WebElement mobile = driver.findElement(By.xpath("//a[normalize-space()='Mobile']"));
            mobile.click();
            //3. In the list of all mobile , read the cost of Sony Xperia mobile (which is $100)
            String priceOnList = driver.findElement(By.cssSelector("span[id='product-price-1'] span[class='price']")).getText();
            //4. Click on Sony Xperia mobile
            WebElement mobileClick = driver.findElement(By.cssSelector("a[title='Sony Xperia']"));
            mobileClick.click();
            //5. Read the Sony Xperia mobile from detail page.
            String priceOnProduct = driver.findElement(By.cssSelector(".price")).getText();
            //6. Compare Product value in list and details page should be equal ($100).
            AssertJUnit.assertEquals(priceOnList, priceOnProduct);
            File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File("C:\\Users\\tt\\Downloads\\New folder\\selenium-webdriver-java\\screenshot_test2.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            driver.quit();
        }
    }
}
