package com.app.fku.genel.fonksiyon.threadclass;

import com.app.fku.genel.entity.MailConf;
import com.app.fku.genel.entity.MailGonderim;
import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.genel.enums.MailTypeEnum;
import com.app.fku.genel.repository.MailConfRepository;
import com.app.fku.genel.repository.MailGonderimRepository;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class MailSenderThread implements Runnable {

    public static MailGonderimRepository mailGonderimRepository;
    public static MailConfRepository mailConfRepository;

    public void run() {
        System.out.println("FKU Mail Sender Thread START");

        String[] mailArray = {
                "gghesapgg1@gmail.com",
                "gghesapgg2@gmail.com",
                "gghesapgg3@gmail.com",
                "gghesapgg4@gmail.com",
                "gghesapgg5@gmail.com",
                "gghesapgg6@gmail.com",
                "gghesapgg7@gmail.com",
                "gghesapgg8@gmail.com",
                "gghesapgg9@gmail.com",
                "gghesapgg10@gmail.com",
                "gghesapgg11@gmail.com",
                "gghesapgg12@gmail.com",
                "gghesapgg13@gmail.com",
                "gghesapgg14@gmail.com",
                "gghesapgg15@gmail.com",
                "gghesapgg16@gmail.com",
                "gghesapgg17@gmail.com",
                "gghesapgg18@gmail.com",
                "gghesapgg19@gmail.com",
                "gghesapgg20@gmail.com",
        };

        for (int k = 0; k < 20; k++) {
            String host = "smtp.gmail.com";
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            List<MailConf> senderMailList = mailConfRepository.findByAktifAndMailType(EvetHayirEnum.EVET, MailTypeEnum.FROM);
            String mailStr = mailArray[k];
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mailStr, "Ay150185");
                }
            });
            session.setDebug(false);
            MimeMessage message = new MimeMessage(session);

            try {
                for (int z = 1; z > 0; z++) {
                    List<MailGonderim> mailGonderimList = mailGonderimRepository.findByStatus(EvetHayirEnum.HAYIR);
                    if (mailGonderimList != null && mailGonderimList.size() > 0) {
                        for (MailGonderim mailGonderim: mailGonderimList) {
                            List<MailConf> toMailList = mailConfRepository.findByAktifAndMailType(EvetHayirEnum.EVET, MailTypeEnum.TO);
                            for (MailConf toMail: toMailList) {
                                message.setFrom(new InternetAddress(senderMailList.get(0).getMailAdi()));
                                message.addRecipient(Message.RecipientType.TO, new InternetAddress(toMail.getMailAdi()));
                                message.setSubject(mailGonderim.getSubject());
                                message.setText(mailGonderim.getText());
                                Transport.send(message);
                            }
                            mailGonderim.setStatus(EvetHayirEnum.EVET);
                            mailGonderim.setGonderimTarihi(new Date());
                            mailGonderimRepository.save(mailGonderim);
                            mailGonderimRepository.flush();
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Mail Gönderim Hatası");
            }
            if (k == 19) {
                k = 0;
            }
            if (k == 20) {
                k = 0;
            }
        }
    }
}
