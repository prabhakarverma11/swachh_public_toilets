package com.websystique.springmvc.service;

import com.websystique.springmvc.utils.UtilConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;


@Service("mailService")
@Transactional
public class MailServiceImpl {

    public void sendAttachmentEmail(String recipients, String subject, String body, String attachmentName) throws AddressException, MessagingException {

        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", UtilConstants.hostname);
        properties.put("mail.smtp.port", UtilConstants.port);
        properties.put("mail.smtp.auth", UtilConstants.auth);
        properties.put("mail.smtp.socketFactory.port", UtilConstants.port);

        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        properties.put("mail.smtp.socketFactory.fallback", "false");
        //  properties.put("mail.smtp.starttls.enable", "true");

        // creates a new session with an authenticator

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        UtilConstants.username, UtilConstants.password);// Specify the Username and the PassWord
            }
        });


        // creates a new e-mail message
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(UtilConstants.fromAddress));


        if (!recipients.equals("")) {
            String[] toAddressesSize = recipients.split(",");
            InternetAddress[] toAddresses = new InternetAddress[toAddressesSize.length];
            int i = 0;
            for (String toAddress : recipients.split(",")) {
                toAddresses[i++] = new InternetAddress(toAddress);
            }
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
        }


        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");


        //msg.setSubject(subject, "UTF-8");
        msg.setSubject(subject);

        msg.setSentDate(new Date());

        // Create the message body part
        BodyPart messageBodyPart = new MimeBodyPart();

        // Fill the message
        messageBodyPart.setContent(body, "text/html");

        // Create a multipart message for attachment
        Multipart multipart = new MimeMultipart();

        // Set text message part
        multipart.addBodyPart(messageBodyPart);

        // Second part is attachment
        messageBodyPart = new MimeBodyPart();

        DataSource source = new FileDataSource(attachmentName);
        messageBodyPart.setDataHandler(new DataHandler(source));

        if (attachmentName.contains("/")) {
            String path[] = attachmentName.split("\\/");
            attachmentName = path[path.length - 1];
        }

        messageBodyPart.setFileName(attachmentName);
        multipart.addBodyPart(messageBodyPart);

        // Send the complete message parts
        msg.setContent(multipart);

        // Send message
        Transport.send(msg);
    }
}
