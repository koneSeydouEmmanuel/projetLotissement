
/*
 * Created on 2021-05-14 ( Time 12:21:48 )
 * Generator tool : Telosys Tools Generator ( version 3.1.2 )
 * Copyright 2017 Savoir Faire Linux. All Rights Reserved.
 */

package com.ilot.utils;


import com.ilot.utils.contract.Response;
import com.ilot.utils.enums.OperationEnum;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.RandomStringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Log4j2
public class HostingUtils {

    public static final String       VAR                     = "svg_";
    public static final String       ALGORITHM_AES           = "AES";
    public static final String       ALGORITHM_RSA           = "RSA";
    public static final String       ALGORITHM_AES_ECB_PKCS7 = "AES/ECB/PKCS7Padding";
    public static final String       BIT                     = "2048";
    public static final String       BITS                    = "128";
    public static final String       KEY_AES                 = "khqWPuvI+431q/0Ev1wVhzG3Po8Z0UIBlvM/fm6uGW0=";
    public static final List<String> URI_AS_IGNORE           = Arrays.asList("user/publicKey", "user/checkSessionUser");
    public static final List<String> PROFILES_TO_IGNORE      = Arrays.asList("local", "apc", "local-normal");

    @Autowired
    private ExceptionUtils    exceptionUtils;
    @Autowired
    private TemplateEngine    templateEngine;
    @Autowired
    private Environment       environment;
    @Autowired
    private ParamsUtils       paramsUtils;
    @Autowired
    private CacheUtils        cacheUtils;


    public static List<String> URI_AS_CHECK = Arrays.asList("user/login", "user/forgotPassword",
            "user/forgotPasswordValidation");

    public static String encrypt(String str) throws Exception {
        MessageDigest digest      = MessageDigest.getInstance("SHA-1");
        byte[]        hashedBytes = digest.digest(str.getBytes("UTF-8"));

        return convertByteArrayToHexString(hashedBytes);
    }

    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuffer.toString();
    }

    public static String generateAlphanumericCode(Integer nbreCaractere) {
        String formatted = null;
        formatted = RandomStringUtils.randomAlphanumeric(nbreCaractere).toLowerCase();
        return formatted;
    }

    /*public <T> Response<T> sendEmail(Map<String, String> from, List<Map<String, String>> toRecipients, String subject,
                                     String body, List<String> attachmentsFilesAbsolutePaths, Context context,
                                     String templateName,
                                     Locale locale) {

        Response<T>        response       = new Response<T>();
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        String smtpServer = paramsUtils.getSmtpHost();
        if (smtpServer != null) {
            Boolean auth = false;
            javaMailSender.setHost(smtpServer);
            javaMailSender.setPort(paramsUtils.getSmtpPort());
            javaMailSender.setUsername(paramsUtils.getSmtpMailAdresse());
            javaMailSender.setPassword(paramsUtils.getSmtpPassword());

            // ADD NEW CONFIG for "no object DCH for MIME type multipart/mixed" error
            MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
            mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
            mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
            mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
            mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
            mc.addMailcap("message/rfc822;; x-java-content- handler=com.sun.mail.handlers.message_rfc822");
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            auth = true;

            javaMailSender.setJavaMailProperties(getMailProperties(paramsUtils.getSmtpHost(), auth));

            MimeMessage message = javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, Boolean.TRUE, "UTF-8");

                // sender
                helper.setFrom(new InternetAddress(from.get("email"), from.get("user")));

                // recipients
                List<InternetAddress> to = new ArrayList<InternetAddress>();
                for (Map<String, String> recipient : toRecipients) {
                    to.add(new InternetAddress(recipient.get("email"), recipient.get("user")));
                }
                helper.setTo(to.toArray(new InternetAddress[0]));

                // Subject and body
                helper.setSubject(subject);
                if (context != null && templateName != null) {
                    body = templateEngine.process(templateName, context);
                }
                helper.setText(body, true);

                // Attachments
                if (attachmentsFilesAbsolutePaths != null && !attachmentsFilesAbsolutePaths.isEmpty()) {
                    for (String attachmentPath : attachmentsFilesAbsolutePaths) {
                        File               pieceJointe = new File(attachmentPath);
                        FileSystemResource file        = new FileSystemResource(attachmentPath);
                        if (pieceJointe.exists() && pieceJointe.isFile()) {
                            helper.addAttachment(file.getFilename(), file);
                        }
                    }
                }

                javaMailSender.send(message);
                response.setHasError(Boolean.FALSE);
                /// gerer les cas d'exeption de non envoi de mail
            } catch (MessagingException | UnsupportedEncodingException e) {
                e.printStackTrace();
                exceptionUtils.EXCEPTION(response, locale, e);
            }
        }
        return response;
    }*/

   /* @Async
    public <T> Response<T> sendEmailAsync(Map<String, String> from, List<Map<String, String>> toRecipients, String subject,
                                          String body, List<String> attachmentsFilesAbsolutePaths, Context context, String templateName, Locale locale) {
        Response<T>        response       = new Response<T>();
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        String smtpServer = paramsUtils.getSmtpHost();
        if (smtpServer != null) {
            Boolean auth = false;
            javaMailSender.setHost(smtpServer);
            javaMailSender.setPort(paramsUtils.getSmtpPort());
            javaMailSender.setUsername(paramsUtils.getSmtpMailAdresse());
            javaMailSender.setPassword(paramsUtils.getSmtpPassword());

            // ADD NEW CONFIG for "no object DCH for MIME type multipart/mixed" error
            MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
            mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
            mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
            mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
            mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
            mc.addMailcap("message/rfc822;; x-java-content- handler=com.sun.mail.handlers.message_rfc822");
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            auth = true;

            javaMailSender.setJavaMailProperties(getMailProperties(paramsUtils.getSmtpHost(), auth));

            MimeMessage message = javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, Boolean.TRUE, "UTF-8");
//				MimeMessageHelper helper = new MimeMessageHelper(message, Boolean.TRUE);

                // sender
                helper.setFrom(new InternetAddress(from.get("email"), from.get("user")));
                // sender


                // recipients
                List<InternetAddress> to = new ArrayList<InternetAddress>();
                for (Map<String, String> recipient : toRecipients) {
                    to.add(new InternetAddress(recipient.get("email"), recipient.get("user")));
                }
                helper.setTo(to.toArray(new InternetAddress[0]));

                // Subject and body
                helper.setSubject(subject);
                if (context != null && templateName != null) {
                    body = templateEngine.process(templateName, context);
                }
                helper.setText(body, true);

                // Attachments
                if (attachmentsFilesAbsolutePaths != null && !attachmentsFilesAbsolutePaths.isEmpty()) {
                    for (String attachmentPath : attachmentsFilesAbsolutePaths) {
                        File               pieceJointe = new File(attachmentPath);
                        FileSystemResource file        = new FileSystemResource(attachmentPath);
                        if (pieceJointe.exists() && pieceJointe.isFile()) {
                            helper.addAttachment(file.getFilename(), file);
                        }
                    }
                }

                javaMailSender.send(message);
                response.setHasError(Boolean.FALSE);
                /// gerer les cas d'exeption de non envoi de mail
            } catch (MessagingException | UnsupportedEncodingException e) {
                e.printStackTrace();
                exceptionUtils.EXCEPTION(response, locale, e);
            }
        }
        return response;
    }*/

    public static boolean isValidEmail(String email) {
        String  regex   = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    private Properties getMailProperties(String host, Boolean auth) {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", auth.toString());
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.starttls.required", "true");
        properties.setProperty("mail.debug", "true");
        if (host.equals("smtp.gmail.com")) {
            properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
        }
        return properties;
    }

    public static byte[] extractBytes(String imageName, String imageType) throws IOException {
        File          imgPath       = new File(imageName);
        BufferedImage bufferedImage = ImageIO.read(imgPath);

        ByteArrayOutputStream baos        = new ByteArrayOutputStream();
        byte[]                imageInByte = null;
        try {
            ImageIO.write(bufferedImage, imageType, baos);
            imageInByte = baos.toByteArray();
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return imageInByte;

    }

    @Async
    public void LogTransaction(String RequestIdentifier,
                               String RemoteAddr,
                               String UserPrincipal,
                               String UserAgent,
                               String Protocol,
                               String SessionUser,
                               String KeyAES,
                               String CompanyID,
                               String nom,
                               String prenom,
                               String Uri,
                               String HasError,
                               String StatusCode,
                               String StatusMessage,
                               String RequestValue,
                               String ResponseValue,
                               String Action,
                               String ND,
                               String Customer) throws Exception {


        log.info(" ------------------------  l'execution de la methode asynchrone    ----------------------------");

        String connectedUserLogin    = nom;
        String connectedUserFullName = "";
        String numero                = "";

        try {
            JSONObject jsonReq = (RequestValue.startsWith("{")) ? new JSONObject(RequestValue) : null;
            if (jsonReq != null && jsonReq.has("data")) {
                JSONObject data = new JSONObject(jsonReq.get("data").toString());
                // supprimer les mots de passe
                if (data.has("mdp")) {
                    data.remove("mdp");
                    jsonReq.put("data", data);
                    RequestValue = jsonReq.toString();
                }
                // supprimer les otp
                if (data.has("otpCode")) {
                    data.remove("otpCode");
                    jsonReq.put("data", data);
                    RequestValue = jsonReq.toString();
                }
                // recuperer le login dans 'compteClient'
                if (Utilities.isBlank(connectedUserLogin) && data.has("compteClient")) {
                    connectedUserLogin = data.getString("compteClient");
                }
                // recuperer le numero principal
                if (data.has("numeroPrincipal")) {
                    numero = data.getString("numeroPrincipal");
                }
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        /*if (SessionUser != null) {
            ClientDto clientDto = cacheUtils.get(SessionUser);
            if (clientDto != null) {
                connectedUserLogin    = clientDto.getCompteClient();
                connectedUserFullName = (Utilities.isNotBlank(clientDto.getPrenoms()) ? clientDto.getPrenoms() + " " : "") + clientDto.getNom();
            }
        }*/
        Action = (Action != null && !Action.trim().isEmpty()) ? Action : OperationEnum.getOperation(Uri);

        JSONObject jsonResponse = new JSONObject(ResponseValue);
        jsonResponse.put("items", " ");
        ResponseValue = jsonResponse.toString();
    }
}
