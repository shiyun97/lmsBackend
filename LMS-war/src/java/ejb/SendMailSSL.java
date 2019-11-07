/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

/**
 *
 * @author Vixson
 */
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.*;

@Stateless
public class SendMailSSL {

    @Asynchronous
    public Future<String> send(String from, String password, List<String> to, String sub, String msg) throws AddressException {
        //Get properties object   
        String status;
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        //get Session   
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        //compose message    
        InternetAddress[] mailTo = new InternetAddress[to.size()];
        for (int i = 0; i < mailTo.length; i++) {
            mailTo[i] = new InternetAddress(to.get(i));
        }

        try {
            MimeMessage message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, mailTo);
            message.setSubject(sub);
            message.setText(msg);
            //send message  
            Transport.send(message);
            status = "message sent successfully";
            System.out.println(status);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return new AsyncResult<>(status);
    }

    @Asynchronous
    public Future<String> sendSingle(String from, String password, String to, String sub, String msg) throws AddressException {
        //Get properties object    
        String status;
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        //get Session   
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        //compose message    
        try {
            MimeMessage message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, to);
            message.setSubject(sub);
            message.setText(msg);
            //send message  
            Transport.send(message);
            status = "message sent successfully";
            System.out.println(status);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return new AsyncResult<>(status);
    }
}
