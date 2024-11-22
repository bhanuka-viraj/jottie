package lk.ijse.gdse71.finalproject.jotit.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailUtil {

    public static boolean sendEmail(String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host",
                "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("visunpraboda999@gmail.com", "bfjy iewp ndan indd");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("visunpraboda999@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
