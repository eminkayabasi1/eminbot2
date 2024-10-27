package com.app.fku.hepsiburada.fonksiyon.impl;

import javax.mail.MessagingException;
import java.io.IOException;

public interface HbUyelikService {

    void uyelikAc() throws MessagingException, IOException;

    void kuponKontrol();
}
