package com.mlweb.service;

import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.mlweb.data.TmpMail;
import com.mlweb.model.MailModel;

@Service
public class MailSenderService {

  public boolean sendMail(MailModel mailModel) {

    Authenticator auth = new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(
            TmpMail.authUsername.getValue(),
            TmpMail.authPassword.getValue()
            );
      }
    };
    Session session = Session.getInstance(createMailProps(),auth);

    try {
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(mailModel.getFromAd(), "送信者名"));
      message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailModel.getToAd()));
      message.setSentDate(new Date());

      final String charset = "UTF-8";
      message.setSubject(mailModel.getTitle(), charset);
      message.setText(mailModel.getContent(), charset);

      final String encoding = "base64";
      message.setHeader("Content-Transfer-Encoding", encoding);

      Transport.send(message);

    }catch(AuthenticationFailedException  e) {
      System.out.println("認証失敗したよ");
      return false;
    }catch(MessagingException e) {
      System.out.println("smtpサーバーに接続失敗したよ");
      System.out.println(e.getMessage());
      System.out.println(e.getCause());
      return false;
    }catch(Exception e) {
      System.out.println("よくわかんないけど死んだ");
      return false;
    }
    return true;
  }

  private Properties createMailProps() {
    Properties sendProps = new Properties();
    /* actドメインから送ろうとすると、SMTP側でフィルタされてるっぽいので諦めた */
    sendProps.put("mail.smtp.host", "smtp.gmail.com");
    sendProps.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    sendProps.put("mail.smtp.port", "587");
    sendProps.put("mail.smtp.connectiontimeout", "20000");
    sendProps.put("mail.smtp.timeout", "20000");
    sendProps.put("mail.smtp.starttls.enable", "true");
    sendProps.put("mail.smtp.auth", "true");
    sendProps.put("mail.debug", "true");
    return sendProps;
  }

  /***/
  public String generateHash() {

    LocalDateTime ldt = LocalDateTime.now();
    ldt.toString();
    // FIXME uidは将来的にはDB値で。
    String r = null;
    byte[] cipher_byte;
    try{
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      String uid = "仮ID";
      String s = ldt + uid;
      md.update(s.getBytes());
      cipher_byte = md.digest();
      StringBuilder sb = new StringBuilder(2 * cipher_byte.length);
      for(byte b: cipher_byte) {
        sb.append(String.format("%02x", b&0xff) );
      }
      r = sb.toString();
    } catch (Exception e) {
      System.out.println("ハッシュ値の作成に失敗した。");
    }
    return r;
  }

}
