package com.example.a2fa_implementation;


import java.util.Random;

public class MailSender {



    public static String generateVerificationCode() {
        Random rand = new Random();
        int code = rand.nextInt(999999 - 100000 + 1) + 100000;
        return String.valueOf(code);
    }

    public static void sendVerificationEmail(String email, String verificationCode) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String from = "test@gmail.com";
                    String password = "test";
                    String subject = "Verification Code";
                    String message = "Your verification code is: " + verificationCode;

                    java.util.Properties properties = new java.util.Properties();
                    properties.put("mail.smtp.auth", "true");
                    properties.put("mail.smtp.starttls.enable", "true");
                    properties.put("mail.smtp.host", "smtp.gmail.com");
                    properties.put("mail.smtp.port", "587");

                    javax.mail.Session session = javax.mail.Session.getInstance(properties,
                            new javax.mail.Authenticator() {
                                @Override
                                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                                    return new javax.mail.PasswordAuthentication(from, password);
                                }
                            });

                    javax.mail.Message msg = new javax.mail.internet.MimeMessage(session);
                    msg.setFrom(new javax.mail.internet.InternetAddress(from));
                    msg.setRecipients(javax.mail.Message.RecipientType.TO, javax.mail.internet.InternetAddress.parse(email));
                    msg.setSubject(subject);
                    msg.setText(message);

                    javax.mail.Transport.send(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}


