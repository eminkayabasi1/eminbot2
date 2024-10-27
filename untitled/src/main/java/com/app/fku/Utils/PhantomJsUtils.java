package com.app.fku.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.*;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class PhantomJsUtils {
    private String filePath = "data/temp/";

    public Document renderPage(String fileP) {
        //System.setProperty("phantomjs.binary.path", "C:\\phantomjs\\phantomjs-2.1.1-windows\\bin"); // path to bin file. NOTE: platform dependent
        //System.setProperty("phantomjs.binary.path","C:\\phantomjs\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
        System.setProperty("webdriver.chrome.driver","C:\\ChromeDriver\\chromedriver_win32\\chromedriver.exe");
        //System.setProperty("phantomjs.page.settings.userAgent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
        //WebDriver ghostDriver = new PhantomJSDriver();
        WebDriver ghostDriver = new ChromeDriver();
        ghostDriver.manage().timeouts().pageLoadTimeout(2000, TimeUnit.SECONDS);
        try {
            ghostDriver.get(fileP);
            return Jsoup.parse(ghostDriver.getPageSource());
        } finally {
            ghostDriver.quit();
        }
    }

    public Document renderPage(Document doc) throws IOException {
        String tmpFileName = Calendar.getInstance().getTimeInMillis() + "";
        //FileUtils.writeToFile(tmpFileName, doc.toString());

        File tmpFile = File.createTempFile(tmpFileName, ".html");
        FileWriter writer = new FileWriter(tmpFile);
        writer.write(doc.toString());
        writer.close();

        BufferedReader reader = new BufferedReader(new FileReader(tmpFile));
        reader.close();

        return renderPage(tmpFileName + ".html");
    }
}
