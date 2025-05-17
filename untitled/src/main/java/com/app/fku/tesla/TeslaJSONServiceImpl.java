package com.app.fku.tesla;

import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.LinkModel;
import com.app.fku.genel.utils.RandomString;
import com.app.fku.trendyol.fonksiyon.service.TrendyolGenelService;
import com.app.fku.trendyol.fonksiyon.service.TrendyolTYEminJSONService;
import com.app.fku.trendyol.model.TyGenelModel;
import com.app.fku.trendyol.model.TyResultModel;
import com.app.fku.trendyol.model.TyUrunModel;
import com.app.fku.trendyol.repository.TrendyolTelegramConfRepository;
import com.app.fku.trendyol.repository.TyIstatistikRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.openqa.selenium.json.JsonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class TeslaJSONServiceImpl implements TeslaJSONService {

    @Autowired
    TrendyolGenelService tyGenelService;

    @Autowired
    GenelService genelService;

    @Autowired
    LogService logService;

    @Autowired
    TrendyolTelegramConfRepository trendyolTelegramConfRepository;

    @Autowired
    TyIstatistikRepository tyIstatistikRepository;

    private final List<LinkModel> urlList = new ArrayList<>();
    private final HashMap<String, Date> mesajGonderimList = new HashMap<>();

    @Override
    public void sorgula() throws IOException, InterruptedException {

        for (; ; ) {
            try {
                anaislem();
            } catch (Exception e) {
                System.out.println("hata geldi ty");
            }
        }
    }

    private void anaislem() throws InterruptedException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        for (;;) {
            TeslaResultModel teslaResultModel = readJsonFromUrl("https://www.tesla.com/coinorder/api/v4/inventory-results?query=%7B%22query%22%3A%7B%22model%22%3A%22my%22%2C%22condition%22%3A%22new%22%2C%22options%22%3A%7B%7D%2C%22arrangeby%22%3A%22Price%22%2C%22order%22%3A%22asc%22%2C%22market%22%3A%22TR%22%2C%22language%22%3A%22tr%22%2C%22super_region%22%3A%22north%20america%22%2C%22lng%22%3A%22%22%2C%22lat%22%3A%22%22%2C%22zip%22%3A%22%22%2C%22range%22%3A0%7D%2C%22offset%22%3A0%2C%22count%22%3A24%2C%22outsideOffset%22%3A0%2C%22outsideSearch%22%3Afalse%2C%22isFalconDeliverySelectionEnabled%22%3Atrue%2C%22version%22%3A%22v2%22%7D");
            if (teslaResultModel.getTotal_matches_found() > 0) {
                String mesaj = "" +
                        "Tesla Stok";

                telegramMesajGonder(mesaj, "-4701006627", "T");
            }
        }
    }

    private void telegramMesajGonder(String mesaj, String chatId, String id) throws IOException, InterruptedException {
        genelService.telegramMesajGonder(mesaj, chatId, "1", "5326840199:AAEWApODrhD-pxplBYAgUIRMo2FI565mNfM");
        mesajGonderimList.put(id, new Date());
    }

    public static TeslaResultModel readJsonFromUrl(String url) throws IOException, JsonException {
        TeslaResultModel teslaResultModel = null;
        for (;;) {
            String json = "";
            for (;;) {
                HashMap<String, String> headerMap = new HashMap<>();
                headerMap.put("Accept-Language", "tr-TR,tr;q=0.6");
                headerMap.put("sec-ch-ua-platform", "macOS");
                headerMap.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36");
                headerMap.put("Cookie", "bm_s=YAAQbEx1aBuYxa6WAQAA/zFozwM41Bp7w8QsQDS4Ab1N5itgtTeHr5XDglNjdIBvq8lrC3PWmB2fH2K8se+7Q9rB05hs9kvbL8Q01o8DajhaRg87ZUsDgSi5HguPNoQhOTyErGGtzSeGlNhbLtB0/qlGGBslt6K7Xbola1cyjaMVDpY2MF94tkl943mUnHu6VptyNb00jM+NfT8ku7Wt0FlrWJJ29uJfP6gSdxBfN99ZhD3YB5oTXMtawCZUpzVN1nNWwWSuHrAfcdl+8R0z9T5AQWaMkbOqhZcvGW/FfBwCkGdLqfJi6S1Zx2fgG4ntNUUzVbkCjs31ELASzbnkcpUP0Qab1bBii0m4mNbTTeUhKnP3LTvd8TBYIz7h0S4hEDfiuf0Bio+xvAtxk9hB6eZc+Ct7Swvwc90jnnpZl7wafoCC1FaZbmr3mZxkwpyih+khpOAtaBvdINdsOwty4qK+AbdsYY93Ofg8EV+Yv6grcr3JxlGGrYOsq2cJDlz1E5yyHnovIuGmNrfkIkBB5TIc/+mhegPACV9yJ1zIajO3mnMt14gW5UNzr3kR4tETtBdrQZmGX1yO4vnU0OwTk3Zqtg6mWJKcn37p+gWC/ewBxbfmHav22zc=; Domain=.tesla.com; Path=/; Expires=Sat, 14 Jun 2025 15:27:14 GMT; Max-Age=2678400; Secure; HttpOnly");

                json = Jsoup
                        .connect(url)
                        .headers(headerMap)
                        //.referrer("http://www.google.com")
                        .timeout(12000)
                        .followRedirects(true)
                        .ignoreContentType(true)
                        .ignoreHttpErrors(true)
                        .execute()
                        .body();

                if (json != null && !json.equals("")) {
                    break;
                }
            }

            ObjectMapper mapper = new ObjectMapper();
            teslaResultModel = mapper.readValue(json, TeslaResultModel.class);

            return teslaResultModel;
        }
    }
}
