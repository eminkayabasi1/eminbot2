package com.app.fku.genel.fonksiyon;

import com.sun.mail.imap.IMAPMessage;

import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;

public class YandexMail {
    public static void main(String args[]) throws MessagingException, IOException {
        readYandexLastMail("aykank.site@yandex.com", "Ay150185");
    }

    public static String readYandexLastMail(String login, String password) throws MessagingException, IOException {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.imaps.ssl.trust", "imap.yandex.ru");
        Session session = Session.getDefaultInstance(props, null);
        Store store;
        store = session.getStore();
        store.connect("imap.yandex.ru", 993, login, password);
        Folder inbox = store.getFolder("HB_UYELIK_EMIN");
        inbox.open(Folder.READ_WRITE); // even though we only want to read, write persmission will allow us to set SEEN flag
        try {
            Message lastMessage = inbox.getMessage(inbox.getMessageCount());
            ((IMAPMessage)lastMessage).setPeek(true); // this is how you prevent automatic SEEN flag
            MimeMessage cmsg = new MimeMessage((MimeMessage)lastMessage); // this is how you deal with exception "Unable to load BODYSTRUCTURE"
            return printMessage(cmsg);
            /**markMessageAsSeen(lastMessage, inbox);

            FlagTerm unreadFlag = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            Message unreadMessages[] = inbox.search(unreadFlag);

            return printMessage(unreadMessages[unreadMessages.length - 1]);
*/
        } finally {
            inbox.close(true);
        }
    }

    private static String printMessage(Message message) throws MessagingException, IOException {
        Address[] addresses = message.getFrom();
        for (Address address : addresses) {
            System.out.println("FROM: " + address.toString());
        }
        System.out.println("SUBJECT:" + message.getSubject());
        System.out.println("TEXT:" + getText(message).trim());
        return getText(message).trim();
    }

    private static void markMessageAsSeen(Message message, Folder folder) throws MessagingException {
        folder.setFlags(new Message[] {message}, new Flags(Flags.Flag.SEEN), true);
    }

    private static boolean textIsHtml = false; // if test is html, use html parser (e.g. org.jsoup)

    // copy-paste from http://www.oracle.com/technetwork/java/javamail/faq/index.html#mainbody
    private static String getText(Part p) throws
            MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String)p.getContent();
            textIsHtml = p.isMimeType("text/html");
            return s;
        }
        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart)p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getText(bp);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null)
                        return s;
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart)p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }
        return null;
    }
}
