package classes;

import javax.mail.*;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.Serializable;
import java.util.Properties;

public class EmailUtil implements Serializable {

    public static void sendEmail(String recipient, String subject, String body) {
        final String username = "javaprojet3@gmail.com"; // your Gmail username
        final String password = "ncna lxuw gswm jpfz"; // your Gmail password

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });


        try {
            Message message = (Message) new MimeMessage(session);
            ((MimeMessage) message).setFrom(new InternetAddress("javaprojet3@gmail.com")); // same as your username or different
            ((MimeMessage) message).setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Email sent successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
