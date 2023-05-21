package com.crud.function;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class EmailSender {
	public static void sendEmailWithAttachment(String recipientEmail, String subject, String body, byte[] attachmentBytes) {
        // Email credentials and properties
        final String senderEmail = "senderfromern@gmail.com";
        final String senderPassword = "vgghkoksogbhhtve";
        String smtpHost = "smtp.gmail.com";
        int smtpPort = 587;

        Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // Create a session with the email credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a new email message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject(subject);

            // Create the message body
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);

            // Create the attachment
            BodyPart attachmentBodyPart = new MimeBodyPart();
            DataSource source = new ByteArrayDataSource(attachmentBytes, "application/octet-stream");
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            attachmentBodyPart.setFileName("qr_code.png");

            // Combine message body and attachment
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentBodyPart);

            // Set the content of the email
            message.setContent(multipart);

            // Send the email
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static class ByteArrayDataSource implements DataSource {
        private byte[] data;
        private String type;

        public ByteArrayDataSource(byte[] data, String type) {
            this.data = data;
            this.type = type;
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(data);
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Read-only data");
        }

        public String getContentType() {
            return type;
        }

        public String getName() {
            return "ByteArrayDataSource";
        }
    }
}
