package com.app.fku.tests;

public class HepsiBuradaHesapAcma {

//    public static void main(String[] args) throws IOException, InterruptedException {
//        File file = new File("C:\\Users\\vbt\\Desktop\\Proje\\SahsiProjeler\\HepsiBuradaBot\\hepsiburadahesaplar.txt");
//        for (int i = 1; i > 0; i++) {
//            String mailStr = mailAdiUret(file);
//            if (uyelikAc(mailStr)) {
//                dosyayaYaz(mailStr, file);
//                continue;
//            }
//        }
//    }
//
//    public static String mailAdiUret(File file) throws FileNotFoundException {
//        String[] strArr = {"e","m","i","n","k","a","y","a","b","a","s","i","h","e","p","s","i","b","u","r","a","d","a"};
//        String mailStr = "";
//        Random random = new Random();
//        for (int i = 0; i < 18; i++) {
//            mailStr += strArr[i];
//            if (random.nextBoolean()) {
//                mailStr += ".";
//            }
//        }
//        mailStr += "urada@gmail.com";
//        if (mailAcilmisMi(mailStr, file)) {
//            mailAdiUret(file);
//        }
//        return mailStr;
//    }
//
//    public static boolean mailAcilmisMi(String mailStr, File file) throws FileNotFoundException {
//        Scanner scanner = new Scanner(file);
//        while (scanner.hasNextLine()) {
//            String nextToken = scanner.nextLine();
//            if (nextToken.equalsIgnoreCase(mailStr))
//                return true;
//        }
//        return false;
//    }
//
//    public static boolean uyelikAc(String mailStr) throws InterruptedException {
//        try {
//            WebDriver driver = null;
//            Document hepsiBuradaDoc = null;
//
//            System.setProperty("webdriver.chrome.driver","C:\\Users\\vbt\\Desktop\\Proje\\SahsiProjeler\\GoogleChromeDriver\\chromedriver_win32\\chromedriver.exe");
//            driver = new ChromeDriver();
//            driver.get("https://giris.hepsiburada.com");
//            Thread.sleep(3000L);
//            List<WebElement> uyeOlDivList = driver.findElements(By.className("_1TxjTFlVZPBnn-I7vOVL2U"));
//            uyeOlDivList.get(1).click();
//
//            WebElement adWebElement = driver.findElement(By.id("txtName"));
//            adWebElement.sendKeys("Emin");
//
//            WebElement soyadWebElement = driver.findElement(By.id("txtSurname"));
//            soyadWebElement.sendKeys("Kayabaşı");
//
//            WebElement emailWebElement = driver.findElement(By.id("txtEmail"));
//            emailWebElement.sendKeys(mailStr);
//
//            WebElement sifreWebElement = driver.findElement(By.id("txtNewPassEmail"));
//            sifreWebElement.sendKeys("Aa123456");
//
//            WebElement checkWebElement = driver.findElement(By.id("checkSubscribeEmail"));
//            checkWebElement.click();
//
//            WebElement uyeOlButtonElement = driver.findElement(By.id("btnSignUpSubmit"));
//            uyeOlButtonElement.click();
//            driver.close();
//            return true;
//        } catch (Exception e) {
//            uyelikAc(mailStr);
//        }
//        return false;
//    }
//
//    public static void dosyayaYaz(String text, File file) throws IOException {
//
//        FileWriter fileWriter = new FileWriter(file, true);
//        BufferedWriter bWriter = new BufferedWriter(fileWriter);
//        bWriter.write(text + "\n");
//        bWriter.close();
//    }
}
