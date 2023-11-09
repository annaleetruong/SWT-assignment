package BAITAP;

import POM.CartPage;
import POM.CheckOutPage;
import POM.LoginPage;
import driver.driverFactory;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

public class TEST8 {
    @Test
    public void testCase08() {
        String email = "hanvy12345@gmail.com";
        String password = "123456";
        String zipCode = "39901";
        String address = "01 George St SE";
        String city = "Atlanta";
        String telephone = "0912345678";
        //Init web-driver session
        WebDriver driver = driverFactory.getChromeDriver();
        LoginPage loginPage = new LoginPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckOutPage checkOutPage = new CheckOutPage(driver);
        try {
            //1. Go to http://live.techpanda.org/
            driver.get("http://live.techpanda.org/");
           // 2. Click on my account link
            WebElement myaccountLink = driver.findElement(By.cssSelector("div[class='footer'] a[title='My Account']"));
            myaccountLink.click();
            //3. Login in application using previously created credential
            loginPage.enterEmailAddress(email);
            loginPage.enterPassword(password);
            loginPage.clickLoginButton();
            //4. Click on 'REORDER' link , change QTY & click Update
            WebElement reorder = driver.findElement(By.xpath("//a[normalize-space()='Reorder']"));
            reorder.click();
            String beforeGrandTotal = driver.findElement(By.xpath("//strong//span[@class='price'][normalize-space()='$615.00']")).getText();
            WebElement quantityInput = driver.findElement(By.xpath("//input[@title='Qty']"));
            quantityInput.clear();
            quantityInput.sendKeys("10");
            Thread.sleep(2000);
            WebElement updateButton = driver.findElement(By.xpath("//button[@title='Update']"));
            updateButton.click();
            //5. Verify Grand Total is changed
            String afterGrandTotal = driver.findElement(By.xpath("//span[normalize-space()='$6,200.00']")).getText();
            Assert.assertNotEquals(afterGrandTotal, beforeGrandTotal);
            //6. Complete Billing & Shipping Information
            cartPage.enterCountry();
            cartPage.enterStateOrProvince();
            cartPage.enterZipCode(zipCode);
            cartPage.clickEstimateButton();
            WebElement shipping = driver.findElement(By.xpath("//input[@id='s_method_flatrate_flatrate']"));
            shipping.click();
            WebElement update = driver.findElement(By.xpath("//button[@title='Update Total']"));
            update.click();
            WebElement checkout = driver.findElement(By.xpath("//li[@class='method-checkout-cart-methods-onepage-bottom']//button[@title='Proceed to Checkout']"));
            checkout.click();
            checkOutPage.selectNewAddress();
            checkOutPage.enterAddress(address);
            checkOutPage.enterCity(city);
            checkOutPage.enterProvince();
            checkOutPage.enterZip(zipCode);
            checkOutPage.enterCounty();
            checkOutPage.enterTelephone(telephone);
            checkOutPage.clickBillingContinueButton();
            Thread.sleep(2000);
            checkOutPage.clickShipMethodContinueButton();
            Thread.sleep(2000);
            checkOutPage.clickCheckPaymentInfor();
            Thread.sleep(2000);
            checkOutPage.clickContinuePaymentInfor();
            Thread.sleep(2000);
            checkOutPage.clickPlaceOrder();
            Thread.sleep(2000);
            //7. Verify order is generated and note the order number
            List<WebElement> allLinks = driver.findElements(By.tagName("a"));
            for(WebElement link:allLinks){
                if(link.getText().startsWith("1000")){
                    System.out.println("Created order Id: " + link.getText());
                }
            }
            File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File("C:\\Users\\tt\\Downloads\\New folder\\selenium-webdriver-java\\screenshot_test8.png"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
