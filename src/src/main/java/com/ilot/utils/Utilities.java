
/*
 * Created on 2021-06-28 ( Time 17:14:39 )
 * Generator tool : Telosys Tools Generator ( version 3.1.2 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package com.ilot.utils;

import com.google.common.util.concurrent.AtomicDouble;
import com.ilot.utils.contract.SearchParam;
import com.ilot.utils.dto.UserDto;
import com.ilot.utils.enums.ExtensionEnum;
import com.ilot.utils.enums.OperatorEnum;
import java.text.SimpleDateFormat;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.LocaleUtils;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.joda.time.format.DateTimeFormat;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.text.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.text.ParseException;
import java.util.Date;
/**
 * Utilities
 *
 * @author Smile Backend generator
 */
@Log4j2
public class Utilities {

    static final         String           alphaNum             = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static final         String           hexaAlphabet         = "0123456789abcdef";
    private static final String[]         IP_HEADER_CANDIDATES = {"X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR"};
    public static        List<String>     PROFILES_TO_IGNORE   = Arrays.asList("dev"/*,"staging"*/,"local"/*,"production"*/, "unencript_prod", "unencript_staging");
    static               SecureRandom     rnd                  = new SecureRandom();
    private static       SimpleDateFormat _dateFormat          = new SimpleDateFormat("dd/MM/yyyy");
    private static       SimpleDateFormat _dateFormatMois      = new SimpleDateFormat("MM/yyyy");
    private static       SimpleDateFormat _dateFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    private static       Logger           slf4jLogger          = LoggerFactory.getLogger(Utilities.class);
    private static       List<String>     listeBase            = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

    public static        List<String>     URI_ADD_TO_IGNORE        =
            Arrays.asList(
                          "/user/getPublicKey", "/user/sendMail",
                    "/user/getUserSessionTTL"
                    //Retrouver les endroits où ces services sont utilisés afin d'appliquer le cryptage
                    );

    public static        List<String>     URI_USE_ENCRYPT_RSA       =
            Arrays.asList(
                    "/sendMail",
                    "/user/motDePasseOublie",
                    "/user/login"
                    ,"/user/connexionImplicite"
                    );

    public static        List<String>     URI_ADD_INFOS_AND_IGNORE        =
            Arrays.asList("user/loginWithCode",
                          "user/forgotPassword",
                          "user/forgotPasswordValidation",
                          "user/resetPassword",
                          "/user/login",
                          "/user/connexionImplicite",
                          "/users/getAesByCriteria",
                          "/paramPlageAnalyse/getByCriteria",
                          "/reporting-qoe",
                          "/user/getUserSessionTTL"
                         );

    public static        List<String>     URI_AS_IGNORE        =
            Arrays.asList("/files", "/upload", "/getPublicKey", "/paramPlageAnalyse/getByCriteria", "/reporting-qoe",
                    "/user/getUserSessionTTL", "/fetchStatus", "/api-docs");


    public static Date getCurrentDate() {
        return new Date();
    }

    public static Date getYesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    /**
     * Permet de retourner la date courante du système
     *
     * @return Date string 'dd/MM/yyyy HH:mm:ss'
     */
    public static String getCurrentLongDateString() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    /**
     * @return Permet de retourner la date courante du système
     */
    public static String getCurrentLocalDateTimeStamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    /**
     * Permet de retourner la date courante du système
     *
     * @return Date string 'dd/MM/yyyy'
     */
    public static String getCurrentShortDateString() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static String getYesterdayDateString() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return yesterday.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static MediaType getMediaTypeOfFile(String extension) {

        MediaType mediaType = null;
        switch (extension) {
            case "pdf":
                mediaType = MediaType.parseMediaType(ExtensionEnum.pdf.getValue());
                break;
            case "doc":
                mediaType = MediaType.parseMediaType(ExtensionEnum.doc.getValue());
                break;
            case "docx":
                mediaType = MediaType.parseMediaType(ExtensionEnum.docx.getValue());
                break;
            case "txt":
                mediaType = MediaType.parseMediaType(ExtensionEnum.txt.getValue());
                break;
            case "odt":
                mediaType = MediaType.parseMediaType(ExtensionEnum.odt.getValue());
                break;
            case "xls":
                mediaType = MediaType.parseMediaType(ExtensionEnum.xls.getValue());
                break;
            case "xlsx":
                mediaType = MediaType.parseMediaType(ExtensionEnum.xlsx.getValue());
                break;
            case "ppt":
                mediaType = MediaType.parseMediaType(ExtensionEnum.ppt.getValue());
                break;
            case "jpeg":
                mediaType = MediaType.parseMediaType(ExtensionEnum.jpeg.getValue());
                break;
            case "jpg":
                mediaType = MediaType.parseMediaType(ExtensionEnum.jpg.getValue());
                break;
            case "png":
                mediaType = MediaType.parseMediaType(ExtensionEnum.png.getValue());
                break;
            case "gif":
                mediaType = MediaType.parseMediaType(ExtensionEnum.gif.getValue());
                break;
            case "mp4":
                mediaType = MediaType.parseMediaType(ExtensionEnum.mp4.getValue());
                break;
            case "avi":
                mediaType = MediaType.parseMediaType(ExtensionEnum.avi.getValue());
                break;
            case "flv":
                mediaType = MediaType.parseMediaType(ExtensionEnum.flv.getValue());
                break;
            case ".mpeg":
                mediaType = MediaType.parseMediaType(ExtensionEnum.mpeg.getValue());
                break;
            case "3gp":
                mediaType = MediaType.parseMediaType(ExtensionEnum.gp.getValue());
                break;
            case "3g2":
                mediaType = MediaType.parseMediaType(ExtensionEnum.g2.getValue());
                break;
            case "ogv":
                mediaType = MediaType.parseMediaType(ExtensionEnum.ogv.getValue());
                break;
            default:
                break;
        }
        return mediaType;
    }

    public static String getContentType(String extension) {

        String mediaType = null;
        switch (extension) {
            case "pdf":
                mediaType = ExtensionEnum.pdf.getValue();
                break;
            case "doc":
                mediaType = ExtensionEnum.doc.getValue();
                break;
            case "docx":
                mediaType = ExtensionEnum.docx.getValue();
                break;
            case "txt":
                mediaType = ExtensionEnum.txt.getValue();
                break;
            case "odt":
                mediaType = ExtensionEnum.odt.getValue();
                break;
            case "xls":
                mediaType = ExtensionEnum.xls.getValue();
                break;
            case "xlsx":
                mediaType = ExtensionEnum.xlsx.getValue();
                break;
            case "ppt":
                mediaType = ExtensionEnum.ppt.getValue();
                break;
            case "jpeg":
                mediaType = ExtensionEnum.jpeg.getValue();
                break;
            case "jpg":
                mediaType = ExtensionEnum.jpg.getValue();
                break;
            case "png":
                mediaType = ExtensionEnum.png.getValue();
                break;
            case "gif":
                mediaType = ExtensionEnum.gif.getValue();
                break;
            case "mp4":
                mediaType = ExtensionEnum.mp4.getValue();
                break;
            case "avi":
                mediaType = ExtensionEnum.avi.getValue();
                break;
            case "flv":
                mediaType = ExtensionEnum.flv.getValue();
                break;
            case ".mpeg":
                mediaType = ExtensionEnum.mpeg.getValue();
                break;
            case "3gp":
                mediaType = ExtensionEnum.gp.getValue();
                break;
            case "3g2":
                mediaType = ExtensionEnum.g2.getValue();
                break;
            case "ogv":
                mediaType = ExtensionEnum.ogv.getValue();
                break;
            default:
                break;
        }
        return mediaType;
    }

    public static String generateNumericCode(Integer nbreCaractere) {
        String formatted = null;
        formatted = RandomStringUtils.randomNumeric(nbreCaractere).toUpperCase();
        return formatted;
    }

    public static String getContentTypeExtension(String extension) {
        String contentTypeFile = null;

        for (ExtensionEnum enum1 : ExtensionEnum.values()) {
            if (extension.equals(enum1.toString())) {
                contentTypeFile = enum1.getValue();
                break;
            }
        }
        return contentTypeFile;
    }

    public static String getExtensionContentType(String contentType) {
        String extension = null;
        for (ExtensionEnum enum1 : ExtensionEnum.values()) {
            if (contentType.equals(enum1.getValue())) {
                extension = enum1.toString();
                break;
            }
        }
        return extension;
    }

    public static boolean areEquals(Object obj1, Object obj2) {
        return (Objects.equals(obj1, obj2));
    }

    public static <T extends Comparable<T>, Object> boolean areEquals(T obj1, T obj2) {
        return (obj1 == null ? obj2 == null : obj1.equals(obj2));
    }

    public static boolean areNotEquals(Object obj1, Object obj2) {
        return !areEquals(obj1, obj2);
    }

    public static <T extends Comparable<T>, Object> boolean areNotEquals(T obj1, T obj2) {
        return !(areEquals(obj1, obj2));
    }

    public static <T extends Comparable<T>> boolean isLessThan(T obj1, T obj2) {
        return obj1.compareTo(obj2) < 0;
    }

    public static <T extends Comparable<T>> boolean isGreaterThan(T obj1, T obj2) {
        return obj1.compareTo(obj2) > 0;
    }

    public static <T extends Comparable<T>> boolean isGreaterThanOrEqual(T obj1, T obj2) {
        return obj1.compareTo(obj2) >= 0;
    }

    public static <T extends Comparable<T>> boolean isLessThanOrEqual(T obj1, T obj2) {
        return obj1.compareTo(obj2) <= 0;
    }


    private static String getStringImage(File file) {
        try {
            FileInputStream fin        = new FileInputStream(file);
            byte[]          imageBytes = new byte[(int) file.length()];
            fin.read(imageBytes, 0, imageBytes.length);
            fin.close();
            return org.apache.tomcat.util.codec.binary.Base64.encodeBase64String(imageBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertToBase64(String pathFichier) {
        File file = new File(Thread.currentThread().getContextClassLoader().getResource(pathFichier).getFile());
        return getStringImage(file);
    }

    public static BigDecimal calculRO(long value1, long value2) {
        double     variation = 0;
        BigDecimal result    = new BigDecimal(0);
        if (value1 != 0L && value2 != 0L) {
            variation = ((double) (value1 * 100)) / value2;
            BigDecimal bigDecimal       = new BigDecimal(variation);
            BigDecimal roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
            result = roundedWithScale;
        }
        if (value2 == 0L) {
            result = new BigDecimal(0);
        }

        return result;
    }


    public static Calendar anneePrecedente(Calendar calendar) throws ParseException {
        Calendar result = calendar;
        result.add(Calendar.YEAR, -1);
        return result;
    }

    public static Calendar moisPrecedent(Calendar calendar) throws ParseException {
        Calendar result = calendar;
        result.add(Calendar.MONTH, -1);
        return result;
    }

    public static Date getFirstDayOfQuarter(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) / 3 * 3);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date getLastDayOfQuarter(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) / 3 * 3 + 2);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public static BigDecimal calculEvoluation(Double value1, Double value2) {
        double variation = 0;

        BigDecimal bigDecimal1       = new BigDecimal(value1);
        BigDecimal roundedWithScale1 = bigDecimal1.setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal bigDecimal2       = new BigDecimal(value2);
        BigDecimal roundedWithScale2 = bigDecimal2.setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal result = new BigDecimal(0);
        if (roundedWithScale1.doubleValue() != 0.00 && roundedWithScale2.doubleValue() != 0.00) {
            variation = (((double) (value1 / value2)) - 1) * 100;
            BigDecimal bigDecimal       = new BigDecimal(variation);
            BigDecimal roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
            result = roundedWithScale;
        }
        if (roundedWithScale2.doubleValue() == 0.00) {
            result = new BigDecimal(0);
        }

        return result;
    }

    public static BigDecimal calculEvoluation(Long value1, Long value2) {
        double variation = 0;

        BigDecimal bigDecimal1       = new BigDecimal(value1);
        BigDecimal roundedWithScale1 = bigDecimal1.setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal bigDecimal2       = new BigDecimal(value2);
        BigDecimal roundedWithScale2 = bigDecimal2.setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal result = new BigDecimal(0);
        if (roundedWithScale1.doubleValue() != 0.00 && roundedWithScale2.doubleValue() != 0.00) {
            variation = (((value1 / (double) value2)) - 1) * 100;
            BigDecimal bigDecimal       = new BigDecimal(variation);
            BigDecimal roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
            result = roundedWithScale;
        }
        if (roundedWithScale2.doubleValue() == 0.00) {
            result = new BigDecimal(0);
        }

        return result;
    }


    public static BigDecimal calculDivision(Long value1, Long value2) {
        double variation = 0;

        BigDecimal bigDecimal1       = new BigDecimal(value1);
        BigDecimal roundedWithScale1 = bigDecimal1.setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal bigDecimal2       = new BigDecimal(value2);
        BigDecimal roundedWithScale2 = bigDecimal2.setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal result = new BigDecimal(0);
        if (roundedWithScale1.doubleValue() != 0.00 && roundedWithScale2.doubleValue() != 0.00) {
            variation = ((double) (value1 / value2));
            BigDecimal bigDecimal       = new BigDecimal(variation);
            BigDecimal roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
            result = roundedWithScale;
        }
        if (roundedWithScale2.doubleValue() == 0.00) {
            result = new BigDecimal(0);
        }

        return result;
    }

    public static BigDecimal calculDivision(double value1, double value2) {
        double variation = 0;

        BigDecimal bigDecimal1       = new BigDecimal(value1);
        BigDecimal roundedWithScale1 = bigDecimal1.setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal bigDecimal2       = new BigDecimal(value2);
        BigDecimal roundedWithScale2 = bigDecimal2.setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal result = new BigDecimal(0);
        if (roundedWithScale1.doubleValue() != 0.00 && roundedWithScale2.doubleValue() != 0.00) {
            variation = ((double) (value1 / value2));
            BigDecimal bigDecimal       = new BigDecimal(variation);
            BigDecimal roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
            result = roundedWithScale;
        }
        if (roundedWithScale2.doubleValue() == 0.00) {
            result = new BigDecimal(0);
        }

        return result;
    }

    public static String getQuatreCaractAnnee(String date) throws ParseException {
        String result = "";

        Calendar         calendar          = Calendar.getInstance();
        SimpleDateFormat dateFormatMois    = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormatMoisNew = new SimpleDateFormat("yyyy");
        calendar.setTime(dateFormatMois.parse(date));
        result = dateFormatMoisNew.format(calendar.getTime()).toLowerCase();

        return result;
    }

    public static String getQuatreCaractAnneeNew(String date) throws ParseException {
        String result = "";

        Calendar         calendar          = Calendar.getInstance();
        SimpleDateFormat dateFormatMois    = new SimpleDateFormat("MM/yyyy");
        SimpleDateFormat dateFormatMoisNew = new SimpleDateFormat("yyyy");
        calendar.setTime(dateFormatMois.parse(date));
        result = dateFormatMoisNew.format(calendar.getTime()).toLowerCase();

        return result;
    }

    public static String getDateAnneecFormat(String date) throws ParseException {
        String result = "";

        Calendar         calendar        = Calendar.getInstance();
        SimpleDateFormat dateFormat      = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormafinale = new SimpleDateFormat("MM/yyyy");
        calendar.setTime(dateFormat.parse(date));
        result = dateFormafinale.format(calendar.getTime());

        return result;

    }


    /**
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request) {
        final String defaultLocahostIp = "0:0:0:0:0:0:0:1";
        final String localhostStr      = "localhost";
        for (String header : IP_HEADER_CANDIDATES) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return areNotEquals(ip, defaultLocahostIp) ? ip : localhostStr;
            }
        }
        return areNotEquals(request.getRemoteAddr(), defaultLocahostIp) ? request.getRemoteAddr() : localhostStr;
    }

    public static String combinaisonString() {
        String lettres = "";
        try {
            Random random;
            for (int i = 0; i < 10; i++) {
                random = new Random();
                int rn = random.nextInt(35 - 0 + 1) + 0;
                lettres += listeBase.get(rn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lettres;
    }

    public static long calculateMinBetweenTwoDates(String firstDate, String secondDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long result = -1;
        try {
            Date date1 = format.parse(firstDate);
            Date date2 = format.parse(secondDate);

            long timeDifferenceMillis = Math.abs(date2.getTime() - date1.getTime());
            long minutes = timeDifferenceMillis / (60 * 1000);  // Convert milliseconds to minutes
            result = minutes;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static double round(double x, int scale) {
        return round(x, scale, BigDecimal.ROUND_HALF_UP);
    }

    public static double round(double x, int scale, int roundingMethod) {
        try {
            return (new BigDecimal
                    (Double.toString(x))
                    .setScale(scale, roundingMethod))
                    .doubleValue();
        } catch (NumberFormatException ex) {
            if (Double.isInfinite(x)) {
                return x;
            } else {
                return Double.NaN;
            }
        }
    }

    public static String formatDate(String date, String format) throws ParseException {
        if (!notBlank(date)) {
            return "";
        }

        String initDateFormat = findDateFormatByParsing(date);

        return formatDate(date, initDateFormat, format);
    }

    public static String formatDate(String date, String initDateFormat, String endDateFormat) throws ParseException {
        if (!notBlank(date)) {
            return "";
        }
        Date             initDate   = new SimpleDateFormat(initDateFormat).parse(date);
        SimpleDateFormat formatter  = new SimpleDateFormat(endDateFormat);
        String           parsedDate = formatter.format(initDate);

        return parsedDate;
    }

    public static String formatMessage(String template, Map<String, String> valueMap) {
        return formatMessage(template, valueMap, "[", "]");
    }

    public static String formatMessage(String template, Map<String, String> valueMap, String prefix, String suffix) {
        StrSubstitutor sub    = new StrSubstitutor(valueMap, prefix, suffix);
        String         result = sub.replace(template);
        return result;
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static int duration(Date startDate, Date endDate) {
        long duration = ChronoUnit.DAYS.between(asLocalDate(startDate), asLocalDate(endDate));
        return Integer.parseInt(String.valueOf(duration + 1));
    }

    public static int duration(LocalDate startLocalDate, LocalDate endLocalDate) {
        long duration = ChronoUnit.DAYS.between(startLocalDate, endLocalDate);
        return Integer.parseInt(String.valueOf(duration + 1));
    }

    public static int dateDifference(Date startDate, Date endDate) {
        long duration = ChronoUnit.DAYS.between(asLocalDate(startDate), asLocalDate(endDate));
        return Integer.parseInt(String.valueOf(duration));
    }

    /**
     * Check if a String given is an Integer.
     *
     * @param s
     * @return isValidInteger
     */
    public static boolean isInteger(String s) {
        boolean isValidInteger = false;
        try {
            Integer.parseInt(s);

            // s is a valid integer
            isValidInteger = true;
        } catch (NumberFormatException ex) {
            // s is not an integer
        }

        return isValidInteger;
    }

    public static String generateCodeOld() {
        String formatted = null;
        formatted = RandomStringUtils.randomAlphanumeric(8).toUpperCase();
        return formatted;
    }

    public static String generateCode() {
        String       formatted    = null;
        SecureRandom secureRandom = new SecureRandom();
        int          num          = secureRandom.nextInt(100000000);
        formatted = String.format("%05d", num);
        return formatted;
    }

    public static boolean isTrue(Boolean b) {
        return b != null && b;
    }

    public static boolean isFalse(Boolean b) {
        return !isTrue(b);
    }

    public static boolean isNumeric(String str) {
        try {
            double d = Long.parseLong(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Check if an Integer given is a String.
     *
     * @param i
     * @return isValidString
     */
    public static boolean isString(Integer i) {
        boolean isValidString = true;
        try {
            Integer.parseInt(i + "");

            // i is a valid integer

            isValidString = false;
        } catch (NumberFormatException ex) {
            // i is not an integer
        }

        return isValidString;
    }

    public static boolean isString(Object obj) {
        return obj instanceof String;
    }

    public static boolean isValidEmail(String email) {
        if (isBlank(email)) {
            return false;
        }
        String  regex   = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public static String encrypt(String str) throws Exception {
        MessageDigest digest      = MessageDigest.getInstance("SHA-1");
        byte[]        hashedBytes = digest.digest(str.getBytes("UTF-8"));

        return convertByteArrayToHexString(hashedBytes);
    }

    public static String sha256(String originalString) throws NoSuchAlgorithmException {
        MessageDigest digest      = MessageDigest.getInstance("SHA-256");
        byte[]        encodedhash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
        StringBuffer  hexString   = new StringBuffer();
        for (int i = 0; i < encodedhash.length; i++) {
            String hex = Integer.toHexString(0xff & encodedhash[i]);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * Convert a string local dateTime to a date with time in format 'MM-dd-yyyy HH:mm:ss'
     * @param stringLocalDateTime
     * @return
     */
    public static Date asDateWithTime(String stringLocalDateTime) {
        Date odt = null;
        try{
            odt = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").parse(stringLocalDateTime);
        }catch (Exception e){
        }

        return odt;
    }

    public static boolean isDateValid(String date) {
        try {
            String simpleDateFormat = "dd/MM/yyyy";

            if (date.contains("-"))
                simpleDateFormat = "dd-MM-yyyy";
            else if (date.contains("/"))
                simpleDateFormat = "dd/MM/yyyy";
            else
                return false;

            DateFormat df = new SimpleDateFormat(simpleDateFormat);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static String GenerateValueKey(String code) {
        String result = null;
        // String prefix = prefixe;
        String       suffix    = null;
        String       middle    = null;
        String       separator = "-";
        final String defaut    = "000001";
        try {

            SimpleDateFormat dt      = new SimpleDateFormat("yy-MM-dd-ss");
            String           _date   = dt.format(new Date());
            String[]         spltter;
            spltter = _date.split(separator);
            middle = spltter[0] + spltter[1] + spltter[2] + spltter[3];
            if (code != null) {
                // Splitter le code pour recuperer les parties
                // String[] parts = code(separator);
                String part = code.substring(1);
                System.out.println("part" + part);

                if (part != null) {
                    int cpt = new Integer(part);
                    cpt++;

                    String _nn = String.valueOf(cpt);

                    switch (_nn.length()) {
                        case 1:
                            suffix = "00000" + _nn;
                            break;
                        case 2:
                            suffix = "0000" + _nn;
                            break;
                        case 3:
                            suffix = "000" + _nn;
                            break;
                        case 4:
                            suffix = "00" + _nn;
                            break;
                        case 5:
                            suffix = "0" + _nn;
                            break;
                        default:
                            suffix = _nn;
                            break;
                    }
                    // result = prefix + separator + middle + separator +
                    // suffix;
                    result = middle + separator + suffix;
                }
            } else {
                // result = prefix + separator + middle + separator + defaut;
                result = middle + separator + defaut;
            }
        } catch (Exception e) {

        }
        return result;
    }

    public static Integer getAge(Date dateNaissance) throws ParseException, Exception {
        Integer annee = 0;

        if (dateNaissance == null) {
            annee = 0;
        }
        Calendar birth = new GregorianCalendar();
        birth.setTime(dateNaissance);
        Calendar now = new GregorianCalendar();
        now.setTime(new Date());
        int adjust = 0;
        if (now.get(Calendar.DAY_OF_YEAR) - birth.get(Calendar.DAY_OF_YEAR) < 0) {
            adjust = -1;
        }
        annee = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR) + adjust;
        return annee;
    }

    public static Boolean AvailableCode(String code) {
        if (code == null || code.isEmpty()) {
            return false;
        }
        Locale local = new Locale(code, "");
        return LocaleUtils.isAvailableLocale(local);

    }

    public static String normalizeFileName(String fileName) {
        String fileNormalize = null;
        fileNormalize = fileName.trim().replaceAll("\\s+", "_");
        fileNormalize = fileNormalize.replace("'", "");
        fileNormalize = Normalizer.normalize(fileNormalize, Normalizer.Form.NFD);
        fileNormalize = fileNormalize.replaceAll("[^\\p{ASCII}]", "");

        return fileNormalize;
    }

    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuffer.toString();
    }

    public static SimpleDateFormat findDateFormat(String date) {
        SimpleDateFormat simpleDateFormat = null;
        String           regex_dd_MM_yyyy = "\\A0?(?:3[01]|[12][0-9]|[1-9])[/.-]0?(?:1[0-2]|[1-9])[/.-][0-9]{4}\\z";

        if (date.matches(regex_dd_MM_yyyy))
            if (date.contains("-"))
                simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            else if (date.contains("/"))
                simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        return simpleDateFormat;
    }

    /**
     * @param l liste de vérification de doublons
     * @return retourne le nombre de doublon trouvé
     */
    public static int getDupCount(List<String> l) {
        int             cnt = 0;
        HashSet<String> h   = new HashSet<>(l);

        for (String token : h) {
            if (Collections.frequency(l, token) > 1)
                cnt++;
        }

        return cnt;
    }

    public static boolean saveImage(String base64String, String nomCompletImage, String extension) throws Exception {

        BufferedImage image = decodeToImage(base64String);

        if (image == null) {

            return false;

        }

        File f = new File(nomCompletImage);

        // write the image

        ImageIO.write(image, extension, f);

        return true;

    }

    public static boolean saveVideo(String base64String, String nomCompletVideo) throws Exception {

        try {

            byte[]           decodedBytes = Base64.getDecoder().decode(base64String);
            File             file2        = new File(nomCompletVideo);
            FileOutputStream os           = new FileOutputStream(file2, true);
            os.write(decodedBytes);
            os.close();

        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }

        return true;

    }

    public static BufferedImage decodeToImage(String imageString) throws Exception {

        BufferedImage image = null;

        byte[] imageByte;

        imageByte = Base64.getDecoder().decode(imageString);

        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageByte)) {

            image = ImageIO.read(bis);

        }

        return image;

    }

    public static String encodeToString(BufferedImage image, String type) {

        String imageString = null;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {

            ImageIO.write(image, type, bos);

            byte[] imageBytes = bos.toByteArray();

            imageString = new String(Base64.getEncoder().encode(imageBytes));

            bos.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return imageString;

    }

    public static String convertFileToBase64(String pathFichier) {
        File   originalFile  = new File(pathFichier);
        String encodedBase64 = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(originalFile);
            byte[]          bytes                 = new byte[(int) originalFile.length()];
            fileInputStreamReader.read(bytes);
            encodedBase64 = new String(Base64.getEncoder().encodeToString((bytes)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return encodedBase64;
    }

    public static String getImageExtension(String str) {
        String extension = "";
        int    i         = str.lastIndexOf('.');
        if (i >= 0) {
            extension = str.substring(i + 1);
            return extension;
        }
        return null;
    }

    public static boolean fileIsImage(String image) {

        String  IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp|jpeg))$)";
        Pattern pattern       = Pattern.compile(IMAGE_PATTERN);
        Matcher matcher       = pattern.matcher(image);

        return matcher.matches();

    }

    public static boolean fileIsVideo(String video) {

        String  IMAGE_PATTERN = "([^\\s]+(\\.(?i)(mp4|avi|camv|dvx|mpeg|mpg|wmv|3gp|mkv))$)";
        Pattern pattern       = Pattern.compile(IMAGE_PATTERN);
        Matcher matcher       = pattern.matcher(video);

        return matcher.matches();

    }

    public static void createDirectory(String chemin) {
        File file = new File(chemin);
        if (!file.exists()) {
            try {
                FileUtils.forceMkdir(file);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public static void deleteFolder(String chemin) {
        File file = new File(chemin);
        try {
            if (file.exists() && file.isDirectory()) {
                FileUtils.forceDelete(new File(chemin));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(String chemin) {
        File file = new File(chemin);
        try {
            if (file.exists() && file.getName() != null && !file.getName().isEmpty()) {

                FileUtils.forceDelete(new File(chemin));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean notBlank(String str) {
        return str != null && !str.isEmpty() && !str.equals("\n") && org.apache.commons.lang3.StringUtils.isNotBlank(str);
    }

    public static boolean blank(String str) {
        return !notBlank(str);
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0 || str.isEmpty()) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isNotBlank(Date date) {
        return date != null;
    }

	/*
	public static boolean notEmpty(List<String> lst) {
		return lst != null && !lst.isEmpty() && lst.stream().noneMatch(s -> s.equals("\n")) && lst.stream().noneMatch(s -> s.equals(null));
	}
	*/

    public static boolean notEmpty(List<String> lst) {
        return lst != null && !lst.isEmpty() && lst.stream().noneMatch(s -> s.equals("\n")) && lst.stream().noneMatch(s -> isBlank(s));
    }

    public static <T> boolean isNotEmpty(List<T> list) {
        return (list != null && !list.isEmpty());
    }

    public static <T> boolean isNotEmpty(T[] array) {
        return (array != null && array.length > 0);
    }

    public static <T> boolean isEmpty(List<T> list) {
        return (list == null || list.isEmpty());
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return (map == null || map.isEmpty());
    }

    static public String GetCode(String Value, Map<String, String> Table) {

        for (Entry<String, String> entry : Table.entrySet()) {
            if (entry.getValue().equals(Value)) {
                return entry.getKey();
            }
        }
        return Value;
    }

    public static boolean anObjectFieldsMapAllFieldsToVerify(List<Object> objets, Map<String, Object> fieldsToVerify) {
        for (Object objet : objets) {
            boolean    oneObjectMapAllFields = true;
            JSONObject jsonObject            = new JSONObject(objet);
            for (Entry<String, Object> entry : fieldsToVerify.entrySet()) {
                // slf4jLogger.info("jsonObject " +jsonObject);
                String key   = entry.getKey();
                Object value = entry.getValue();
                try {
                    if (!jsonObject.get(key).equals(value)) {
                        oneObjectMapAllFields = false;
                        break;
                    }
                } catch (Exception e) {
                    oneObjectMapAllFields = false;
                    break;
                }
            }
            if (oneObjectMapAllFields)
                return true;
        }

        return false;
    }

    public static List<Date> getDaysBetweenDates(Date startdate, Date enddate) {
        List<Date> dates    = new ArrayList<Date>();
        Calendar   calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        while (calendar.getTime().before(enddate) || calendar.getTime().equals(enddate)) {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

    public static List<Date> getDaysBetweenDates(Date startdate, Date enddate, SimpleDateFormat dateFormat) throws ParseException {
        List<Date> dates = new ArrayList<Date>();

        startdate = dateFormat.parse(dateFormat.format(startdate));
        enddate   = dateFormat.parse(dateFormat.format(enddate));

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        while (calendar.getTime().before(enddate) || calendar.getTime().equals(enddate)) {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(getCalendarField(dateFormat.toPattern()), 1);
        }
        return dates;
    }

    public static List<String> getIndexBetweenDates(Date startdate, Date enddate, String root) {
        List<Date>       dates         = getDaysBetweenDates(startdate, enddate);
        List<String>     str_dates     = new ArrayList<String>();
        SimpleDateFormat formatter     = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat formatter_new = new SimpleDateFormat("yyyyMM");


        dates.forEach(d -> {
            if (!root.contains("omer_vente_kit_") && !root.contains("omer_ro_kits_vendus_") && !root.contains("airtime_commission_") && !root.contains("objectifs_")) {
                str_dates.add(root + formatter.format(d));
                //slf4jLogger.info("index  ->  " + root + formatter.format(d));
            } else {
                str_dates.add(root + formatter_new.format(d));
                //slf4jLogger.info("index  ->  " + root + formatter_new.format(d));
            }
        });

        return str_dates;
    }

    public static List<String> getIndexBetweenDates(Date startdate, Date enddate, String root, SimpleDateFormat formatter) throws ParseException {
        List<Date>   dates     = getDaysBetweenDates(startdate, enddate, formatter);
        List<String> str_dates = new ArrayList<String>();
        dates.forEach(d -> {
            str_dates.add(root + formatter.format(d));
        });
        return str_dates;
    }

    public static List<String> getIndexBetweenDatesV2(Date startdate, Date enddate, String root) {
        List<Date>       dates     = getDaysBetweenDates(startdate, enddate);
        List<String>     str_dates = new ArrayList<String>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

        dates.forEach(d -> {
            str_dates.add(root + formatter.format(d));
        });

        return str_dates;
    }

    public static List<String> getIndexBetweenDatesMois(Date startdate, Date enddate, String root) {
        List<Date>       dates     = getDaysBetweenDates(startdate, enddate);
        List<String>     str_dates = new ArrayList<String>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");

        dates.forEach(d -> {
            str_dates.add(root + formatter.format(d));
        });

        return str_dates;
    }

    public static String[] getIndexesArray(String start, String end, SimpleDateFormat dateFormat, String root, SimpleDateFormat indexDateFormatter) throws Exception {
        if (dateFormat == null) {
            dateFormat = _dateFormat;
        }

        Date d1 = dateFormat.parse(start);
        Date d2 = dateFormat.parse(end);

        List<String> indexes    = Utilities.getIndexBetweenDates(d1, d2, root, indexDateFormatter);
        String[]     indexesArr = new String[indexes.size()];
        return indexes.toArray(indexesArr);
    }

    public static String[] getIndexesArray(String start, String end, SimpleDateFormat dateFormat, String root) throws ParseException {

        if (dateFormat == null) {
            dateFormat = _dateFormat;
        }

        Date d1 = dateFormat.parse(start);
        Date d2 = dateFormat.parse(end);

        List<String> indexes    = getIndexBetweenDates(d1, d2, root);
        String[]     indexesArr = new String[indexes.size()];
        return indexes.toArray(indexesArr);
    }

    public static String[] getIndexesArrayV2(String start, String end, SimpleDateFormat dateFormat, String root) throws ParseException {
        if (dateFormat == null) {
            dateFormat = _dateFormat;
        }

        Date d1 = dateFormat.parse(start);
        Date d2 = dateFormat.parse(end);

        List<String> indexes    = getIndexBetweenDatesV2(d1, d2, root);
        String[]     indexesArr = new String[indexes.size()];
        return indexes.toArray(indexesArr);
    }

    public static String[] getIndexesArrayMois(String start, String end, SimpleDateFormat dateFormat, String root) throws ParseException {
        if (dateFormat == null) {
            dateFormat = _dateFormat;
        }

        Date d1 = dateFormat.parse(start);
        Date d2 = dateFormat.parse(end);

        List<String> indexes    = getIndexBetweenDatesMois(d1, d2, root);
        String[]     indexesArr = new String[indexes.size()];
        return indexes.toArray(indexesArr);
    }

    public static int compareToDate(String first_date, String second_date, SimpleDateFormat dateFormat) throws ParseException {
        Date first  = dateFormat.parse(first_date);
        Date second = dateFormat.parse(second_date);
        return first.compareTo(second);
    }

    public static String minusDays(String originDateString, long daysToRemove) throws ParseException {
        final SimpleDateFormat dateFormat = findDateFormat(originDateString);
        Date                   finalDate  = minusDays(dateFormat.parse(originDateString), daysToRemove);
        return dateFormat.format(finalDate);
    }

    public static Date minusDays(Date originDate, long daysToRemove) {
        ZoneId        zoneId          = ZoneId.systemDefault();
        LocalDateTime originLocalDate = LocalDateTime.ofInstant(originDate.toInstant(), zoneId);
        return Date.from(minusDays(originLocalDate, daysToRemove).atZone(zoneId).toInstant());
    }

    public static LocalDateTime minusDays(LocalDateTime originLocalDate, long daysToRemove) {
        return originLocalDate.minusDays(daysToRemove);
    }

    public static List<String> getDatesBetween(String startDate, String endDate, String outputDateFormat) throws ParseException {
        final SimpleDateFormat startDateFormat = findDateFormat(startDate);
        final SimpleDateFormat endDateFormat   = findDateFormat(endDate);
        final List<Date>       dateList        = getDatesBetween(startDateFormat.parse(startDate), endDateFormat.parse(endDate));
        return dateList.stream()
                .map(d -> new SimpleDateFormat(outputDateFormat).format(d))
                .collect(Collectors.toList());
    }

    public static List<Date> getDatesBetween(Date startDate, Date endDate) {
        ZoneId                    zoneId         = ZoneId.systemDefault();
        LocalDateTime             startLocalDate = LocalDateTime.ofInstant(startDate.toInstant(), zoneId);
        LocalDateTime             endLocalDate   = LocalDateTime.ofInstant(endDate.toInstant(), zoneId);
        final List<LocalDateTime> list           = getDatesBetween(startLocalDate, endLocalDate);
        return list.stream()
                .map(d -> Date.from(d.atZone(zoneId).toInstant()))
                .collect(Collectors.toList());
    }

    public static List<LocalDateTime> getDatesBetween(LocalDateTime startDate, LocalDateTime endDate) {
        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(startDate::plusDays)
                .collect(Collectors.toList());
    }

    public static String getDateJour(String date) throws ParseException {
        String result = "";

        // TimeZone timeZone = TimeZone.getTimeZone("UTC");
        // Calendar calendar = Calendar.getInstance(timeZone);
        Calendar         calendar          = Calendar.getInstance();
        SimpleDateFormat dateFormatMois    = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat dateFormatMoisNew = new SimpleDateFormat("dd/MM/yyyy");
        calendar.setTime(dateFormatMois.parse(date));

        result = dateFormatMoisNew.format(calendar.getTime()).toLowerCase();

        return result;
    }

    public static String getMoisFormatFinale(String date) throws ParseException {
        String result = "";

        Calendar         calendar          = Calendar.getInstance();
        SimpleDateFormat dateFormatMois    = new SimpleDateFormat("yyyyMM");
        SimpleDateFormat dateFormatMoisNew = new SimpleDateFormat("MM/yyyy");
        calendar.setTime(dateFormatMois.parse(date));
        result = dateFormatMoisNew.format(calendar.getTime()).toLowerCase();

        return result;

    }

    public static String getDernierJourMoisFormatFinale(String date) throws ParseException {
        String result = "";

        Calendar calendar = Calendar.getInstance();
        //SimpleDateFormat dateFormatMois = new SimpleDateFormat("yyyyMM");
        SimpleDateFormat dateFormatMoisNew = new SimpleDateFormat("MM/yyyy");
        calendar.setTime(dateFormatMoisNew.parse(date));

        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        result = String.valueOf(lastDay);

        return result;

    }

    public static String getAnneeJour(String date) throws ParseException {
        String result = "";

        // TimeZone timeZone = TimeZone.getTimeZone("UTC");
        // Calendar calendar = Calendar.getInstance(timeZone);
        Calendar         calendar          = Calendar.getInstance();
        SimpleDateFormat dateFormatMois    = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormatMoisNew = new SimpleDateFormat("yyyy");
        calendar.setTime(dateFormatMois.parse(date));

        result = dateFormatMoisNew.format(calendar.getTime()).toLowerCase();

        return result;
    }

    public static BigDecimal calculTOT(long value1, long value2) {
        double     variation = 0;
        BigDecimal result    = new BigDecimal(0);
        if (value1 != 0L && value2 != 0L) {
            //	variation = ((double) (value1 * 100)) / value2;
            double v1 = 0.0;
            double v2 = 0.0;
            v1        = (double) value1;
            v2        = (double) value2;
            variation = (((double) (v1 / v2)) - 1) * 100;
            BigDecimal bigDecimal       = new BigDecimal(variation);
            BigDecimal roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
            result = roundedWithScale;
        }
        if (value2 == 0L) {
            result = new BigDecimal(0);
        }

        return result;
    }

    public static String getDateMoisPrec(String date) throws ParseException {
        String result = "";

        Calendar         calendar   = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        calendar.setTime(dateFormat.parse(date));

        calendar.add(Calendar.MONTH, -1);
        result = dateFormat.format(calendar.getTime()).toLowerCase();

        return result;

    }

    public static String getDateSemaineNew(String date) throws ParseException {
        String result = "";

        // TimeZone timeZone = TimeZone.getTimeZone("UTC");
        // Calendar calendar = Calendar.getInstance(timeZone);
        Calendar         calendar       = Calendar.getInstance();
        SimpleDateFormat dateFormatMois = new SimpleDateFormat("ww-yyyy");
        //SimpleDateFormat dateFormatMoisNew = new SimpleDateFormat("yyyy-ww");
        SimpleDateFormat dateFormatMoisNew = new SimpleDateFormat("yyyy-ww");
        calendar.setTime(dateFormatMois.parse(date));

        result = dateFormatMoisNew.format(calendar.getTime()).toLowerCase();

        return result;
    }

    public static String getDateSemaine(String date) throws ParseException {
        String result = "";

        // TimeZone timeZone = TimeZone.getTimeZone("UTC");
        // Calendar calendar = Calendar.getInstance(timeZone);
        Calendar         calendar       = Calendar.getInstance();
        SimpleDateFormat dateFormatMois = new SimpleDateFormat("yyyy-ww");
        //SimpleDateFormat dateFormatMoisNew = new SimpleDateFormat("yyyy-ww");
        SimpleDateFormat dateFormatMoisNew = new SimpleDateFormat("ww-yyyy");
        calendar.setTime(dateFormatMois.parse(date));

        result = dateFormatMoisNew.format(calendar.getTime()).toLowerCase();

        return result;
    }

    public static String getDateMois(String date) throws ParseException {
        String result = "";

        // TimeZone timeZone = TimeZone.getTimeZone("UTC");
        // Calendar calendar = Calendar.getInstance(timeZone);
        Calendar         calendar       = Calendar.getInstance(Locale.FRANCE);
        SimpleDateFormat dateFormatMois = new SimpleDateFormat("yyyy-MM");
        //SimpleDateFormat dateFormatMoisNew = new SimpleDateFormat("MMMMMM yyyy");
        SimpleDateFormat dateFormatMoisNew = new SimpleDateFormat("MM/yyyy");
        calendar.setTime(dateFormatMois.parse(date));

        result = dateFormatMoisNew.format(calendar.getTime()).toLowerCase();
        slf4jLogger.info("mois  envoyé : " + date);
        slf4jLogger.info("resultat  : " + result);

        return result;
    }

    public static String getDateAnneePrec(String date) throws ParseException {
        String result = "";

        Calendar         calendar   = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        calendar.setTime(dateFormat.parse(date));

        calendar.add(Calendar.YEAR, -1);

        result = dateFormat.format(calendar.getTime());

        return result;

    }

    public static int getCalendarField(String datePattern) {
        if (isBlank(datePattern)) {
            return Calendar.DATE;
        }

        if (Arrays.asList("yyyyMM", "yyyy.MM", "yyyy-MM", "MMyyyy", "MM.yyyy", "MM-yyyy").contains(datePattern)) {
            return Calendar.MONTH;
        }
        if (Arrays.asList("yyyy").contains(datePattern)) {
            return Calendar.YEAR;
        }

        return Calendar.DATE;
    }

    public static byte[] base64ToByte(String data) throws Exception {
        return org.apache.commons.codec.binary.Base64.decodeBase64(data);
    }

    public static String byteToBase64(byte[] data) {
        return org.apache.commons.codec.binary.Base64.encodeBase64String(data);
    }

    public static String byteToBase64UrlSafe(byte[] data) {
        return org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(data);
    }

    public static void deleteFileOnSever(List<String> fileFullUrlList) {
        if (fileFullUrlList != null && !fileFullUrlList.isEmpty()) {
            for (String fileFullUrl : fileFullUrlList) {
                // Repertoire où se trouve le fichier
                deleteFile(fileFullUrl);
            }
        }
    }

    /*public static void deleteFileOnSever(List<String> fileNameList, ParamsUtils paramsUtils) {
        if (fileNameList != null && !fileNameList.isEmpty()) {
            for (String fileName : fileNameList) {
                // Repertoire où se trouve le fichier
                if (fileName != null && fileName.contains(".")) {
                    String file[] = fileName.split("\\.");
                    if (file.length > 1) {
                        String fileExtension = file[file.length - 1];
                        String fullFileName  = file[0];
                        for (int k = 1; k < file.length - 1; k++) {
                            fullFileName += "." + file[k];
                        }
                        file     = fullFileName.split("/");
                        fileName = file[file.length - 1];

                        String filesDirectory = getSuitableFileDirectory(fileExtension, paramsUtils);
                        deleteFile(filesDirectory + "/" + fileName);
                    }
                }
            }
        }
    }*/

    /*public static String getSuitableFileDirectory(String fileExtension, ParamsUtils paramsUtils) {
        String suitableFileDirectory = "";
        *//**
         if (fileIsImage("file." + fileExtension)) {
         suitableFileDirectory = paramsUtils.getImageDirectory();
         } else {
         if (fileIsTexteDocument("file." + fileExtension)) {
         suitableFileDirectory = paramsUtils.getTextfileDirectory();
         } else {
         if (fileIsVideo("file." + fileExtension)) {
         suitableFileDirectory = paramsUtils.getVideoDirectory();
         }
         }
         }
         if (suitableFileDirectory == null) {
         suitableFileDirectory = paramsUtils.getOtherfileDirectory();
         }
         **//*
        return String.format("%s%s", paramsUtils.getRootFilesPath(), suitableFileDirectory);
        //return suitableFileDirectory;
    }*/

    public static boolean fileIsTexteDocument(String textDocument) {

        String  TEXT_DOCUMENT_PATTERN = "([^\\s]+(\\.(?i)(doc|docx|txt|odt|ods|pdf|xls|xlsx|csv))$)";
        Pattern pattern               = Pattern.compile(TEXT_DOCUMENT_PATTERN);
        Matcher matcher               = pattern.matcher(textDocument);
        return matcher.matches();
    }

    public static String addSlash(String path) {
        return addSlash(path, false);
    }

    public static String addSlash(String path, boolean isWindowsOs) {
        final String slash = isWindowsOs ? "\\" : "/";
        if (notBlank(path)) {
            if (!path.endsWith(slash)) {
                path += slash;
            }
        }
        return path;
    }

    /*public static String getSuitableFileUrl(String fileName, ParamsUtils paramsUtils) {
        String suitableFileDirectory = "";
        *//**String file[]                = fileName.split("\\.");
         if (file.length > 0) {
         String fileExtension = (file.length > 2) ? file[(file.length - 1)] : file[1];
         if (fileIsImage("file." + fileExtension)) {
         suitableFileDirectory = paramsUtils.getImageDirectory();
         } else {
         if (fileIsTexteDocument("file." + fileExtension)) {
         suitableFileDirectory = paramsUtils.getTextfileDirectory();
         } else {
         if (fileIsVideo("file." + fileExtension)) {
         suitableFileDirectory = paramsUtils.getVideoDirectory();
         }
         }
         }
         }
         if (suitableFileDirectory == null) {
         suitableFileDirectory = paramsUtils.getOtherfileDirectory();
         }
         **//*
        return String.format("%s%s%s", paramsUtils.getRootFilesUrl(), suitableFileDirectory, fileName);
    }*/

    public static String generateAlphanumericCode(Integer nbreCaractere) {
        String formatted = null;
        formatted = RandomStringUtils.randomAlphanumeric(nbreCaractere).toUpperCase();
        return formatted;
    }

    public static Boolean verifierEmail(String email) {
        Pattern emailPattern = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher emailMatcher = emailPattern.matcher(email);
        return emailMatcher.matches();
    }

    public static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
             sb.append(alphaNum.charAt(rnd.nextInt(alphaNum.length())));
        return sb.toString();
    }

    public static String randomHexaString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
             sb.append(hexaAlphabet.charAt(rnd.nextInt(hexaAlphabet.length())));
        return sb.toString();
    }

    public static String getFilePath(String pathFichier) {
        //slf4jLogger.info("--pathFichier--" + pathFichier);
        File file = null;
        try {
            file = new ClassPathResource(pathFichier).getFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return file.getAbsolutePath();
    }



    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static boolean saveFile(String base64String, String nomCompletVideo) throws Exception {

        try {

            byte[]           decodedBytes = Base64.getDecoder().decode(base64String);
            File             file2        = new File(nomCompletVideo);
            FileOutputStream os           = new FileOutputStream(file2, false);
            os.write(decodedBytes);
            os.close();

        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }

        return true;

    }

    public static String findDateFormatByParsing(String date) {
        if (blank(date)) {
            return null;
        }

        List<String> datasPatterns = new ArrayList<String>();

        datasPatterns.addAll(Arrays.asList("dd/MM/yyyy", "dd-MM-yyyy", "dd.MM.yyyy", "ddMMyyyy"));
        datasPatterns.addAll(Arrays.asList("dd/MM/yyyy HH", "dd-MM-yyyy HH", "dd.MM.yyyy HH", "ddMMyyyy HH"));
        datasPatterns.addAll(Arrays.asList("dd/MM/yyyy HH:mm", "dd-MM-yyyy HH:mm", "dd.MM.yyyy HH:mm", "ddMMyyyy HH:mm"));
        datasPatterns.addAll(Arrays.asList("dd/MM/yyyy HH:mm:ss", "dd-MM-yyyy HH:mm:ss", "dd.MM.yyyy HH:mm:ss", "ddMMyyyy HH:mm:ss"));
        datasPatterns.addAll(Arrays.asList("dd/MM/yyyy HH:mm:ss.SSS", "dd-MM-yyyy HH:mm:ss.SSS", "dd.MM.yyyy HH:mm:ss.SSS", "ddMMyyyy HH:mm:ss.SSS"));

        datasPatterns.addAll(Arrays.asList("yyyy/MM/dd", "yyyy-MM-dd", "yyyy.MM.dd", "yyyyMMdd"));
        datasPatterns.addAll(Arrays.asList("yyyy/MM/dd HH", "yyyy-MM-dd HH", "yyyy.MM.dd HH", "yyyyMMdd HH"));
        datasPatterns.addAll(Arrays.asList("yyyy/MM/dd HH:mm", "yyyy-MM-dd HH:mm", "yyyy.MM.dd HH:mm", "yyyyMMdd HH:mm"));
        datasPatterns.addAll(Arrays.asList("yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss", "yyyy.MM.dd HH:mm:ss", "yyyyMMdd HH:mm:ss"));
        datasPatterns.addAll(Arrays.asList("yyyy/MM/dd HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss.SSS", "yyyy.MM.dd HH:mm:ss.SSS", "yyyyMMdd HH:mm:ss.SSS"));

        datasPatterns.addAll(Arrays.asList("dd/MM", "dd-MM", "dd.MM", "ddMM"));
        datasPatterns.addAll(Arrays.asList("MM/yyyy", "MM-yyyy", "MM.yyyy", "MMyyyy"));

        datasPatterns.addAll(Arrays.asList("MM/dd", "MM-dd", "MM.dd", "MMdd"));
        datasPatterns.addAll(Arrays.asList("yyyy/MM", "yyyy-MM", "yyyy.MM", "yyyyMM"));

        datasPatterns.addAll(Arrays.asList("yyyy"));

        datasPatterns.addAll(Arrays.asList("M/dd/yy", "MM/dd/yy", "M/dd/yyyy", "MM/dd/yyyy"));

        datasPatterns.addAll(Arrays.asList("HH", "HH:mm", "HH:mm:ss", "HH:mm:ss.SSS"));
       /* datasPatterns.addAll(Arrays.asList("EEE MMM dd HH:mm:ss zzzz yyyy", "EEE MMM dd HH:mm:ss zzz yyyy"));*/
        datasPatterns.addAll(Arrays.asList("EEE MMM dd HH:mm:ss Z yyyy", "EEE MMM dd HH:mm:ss zzz yyyy"));


        for (String pattern : datasPatterns) {
            try {
                //					SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                //					sdf.setLenient(false);
                //					sdf.parse(date);

                if (pattern == "EEE MMM dd HH:mm:ss Z yyyy" || pattern == "EEE MMM dd HH:mm:ss zzz yyyy"){
                    System.out.println(pattern);
                    SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.ENGLISH);

                    Date parsedDate = formatter.parse(date);

                    SimpleDateFormat print = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");

                    pattern = print.format(parsedDate).toString();

                    System.out.println(date);

                    return print.toString();
                }

                org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
                formatter.parseDateTime(date);
                return pattern;
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }

        return null;
    }

    public static BigDecimal calculTaux(Double value1, Double value2, int newScale, int roundingMode) {
        double     variation = 0;
        BigDecimal result    = new BigDecimal(0);
        if (value1 != 0d && value2 != 0d) {
            variation = ((double) (value1 * 100)) / value2;
            BigDecimal bigDecimal       = new BigDecimal(variation);
            BigDecimal roundedWithScale = bigDecimal.setScale(newScale, roundingMode);
            result = roundedWithScale;
        }
        if (value2 == 0L) {
            result = new BigDecimal(0);
        }

        return result;
    }

    public static BigDecimal calculTaux(Double value1, Double value2, int newScale) {
        return calculTaux(value1, value2, newScale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal calculTaux(Double value1, Double value2) {
        return calculTaux(value1, value2, 2);
    }

    public static Double caluclerVariation(Double valueAtH, Double valueAtHPlusX) {
        if (valueAtH.equals(0d)) {
            return Double.NaN;
        }
        return 100.0 * ((valueAtHPlusX / valueAtH) - 1);
    }

    public static String getInterval(String start, String end) {
        String interval = "";
        if (notBlank(start) && notBlank(end)) {
            interval += "[" + start + " - " + end + "], ";
        } else {
            if (notBlank(start) && blank(end)) {
                interval += "[" + start + " ; +∞[, ";
            } else {
                if (blank(start) && notBlank(end)) {
                    interval += "]-∞ ; " + end + "], ";
                } else {
                    interval += "]-∞ ; +∞[, ";
                }
            }
        }
        //interval = interval.substring(0, interval.length() - 2);
        return interval;
    }

    public static boolean isValidateIvorianPhoneNumber(String phoneNumber) {
        String regex = "^(\\(\\+225\\)|\\+225|\\(00225\\)|00225)?(\\s)?[0-9]{2}([ .-]?[0-9]{2}){3}$";
        return (phoneNumber != null && phoneNumber.matches(regex));
    }

    public static String ivorianPhoneNumberToStandardFormat(String phoneNumber) {
        String beginRegex       = "^(\\(\\+225\\)|\\+225|\\(00225\\)|00225)?";
        String specialCharRegex = "[ .-]?";
        String simplePhoneNumber;

        if (phoneNumber == null)
            return null;

        simplePhoneNumber = phoneNumber.replaceAll(beginRegex, "");
        return simplePhoneNumber.replaceAll(specialCharRegex, "");
    }

//	public static <T> boolean searchParamIsNotEmpty(SearchParam<T> fieldParam) {
//		return fieldParam != null && isNotBlank(fieldParam.getOperator()) && (fieldParam.getStart() != null || fieldParam.getEnd() != null || isNotEmpty(fieldParam.getDatas()) );
//	}

    public static <T> boolean searchParamIsNotEmpty(SearchParam<T> fieldParam) {
        return fieldParam != null && isNotBlank(fieldParam.getOperator()) && OperatorEnum.IS_VALID_OPERATOR(fieldParam.getOperator()) &&
                (
                        (OperatorEnum.IS_BETWEEN_OPERATOR(fieldParam.getOperator()) && fieldParam.getStart() != null && isNotBlank(fieldParam.getStart().toString()) && fieldParam.getEnd() != null && isNotBlank(fieldParam.getEnd().toString()))
                                ||
                                (OperatorEnum.IS_IN_OPERATOR(fieldParam.getOperator()) && isNotEmpty(fieldParam.getDatas()))
                                ||
                                (OperatorEnum.OPERATOR_NOT_NEEDS_ANY_VALUE(fieldParam.getOperator()))
                                ||
                                (OperatorEnum.OPERATOR_NEEDS_FIELD_VALUE(fieldParam.getOperator()))
                );
    }

    public static <T> boolean searchParamIsNotEmpty(SearchParam<T> fieldParam, T fieldValue) {
        return fieldParam != null && isNotBlank(fieldParam.getOperator()) && OperatorEnum.IS_VALID_OPERATOR(fieldParam.getOperator()) &&
                (
                        (OperatorEnum.IS_BETWEEN_OPERATOR(fieldParam.getOperator()) && fieldParam.getStart() != null && isNotBlank(fieldParam.getStart().toString()) && fieldParam.getEnd() != null && isNotBlank(fieldParam.getEnd().toString()))
                                ||
                                (OperatorEnum.IS_IN_OPERATOR(fieldParam.getOperator()) && isNotEmpty(fieldParam.getDatas()))
                                ||
                                (OperatorEnum.OPERATOR_NOT_NEEDS_ANY_VALUE(fieldParam.getOperator()))
                                ||
                                (OperatorEnum.OPERATOR_NEEDS_FIELD_VALUE(fieldParam.getOperator()) && fieldValue != null && isNotBlank(fieldValue.toString()))
                );
    }

    public static <T> List<T> paginner(List<T> allItems, Integer index, Integer size) {
        if (isEmpty(allItems)) {
            return null;
        }

        List<T> items = new ArrayList<T>();
        //si une pagination est pécisée, ne prendre que le nombre d'éléments demandés
        if (index != null && index >= 0 && size != null && size >= 0) {
            Integer fromIndex = index * size;
            if (fromIndex < allItems.size()) {
                Integer toIndex = fromIndex + size;
                if (toIndex > allItems.size()) {
                    toIndex = allItems.size();
                }
                items.addAll(allItems.subList(fromIndex, toIndex));
            }
        } else {
            items.addAll(allItems);
        }

        return items;
    }

    public static String convertSecondToTime(Long seconds) {
        boolean isNegative = seconds < 0;
        seconds = Math.abs(seconds);

        Long jours  = seconds / (24 * 3600L);
        Long heure  = (seconds - jours * 24 * 3600L) / 3600L;
        Long minute = (seconds - jours * 24 * 3600L - heure * 3600L) / 60L;
        Long second = seconds % 60L;

        String HH = String.format("%02d", heure);
        String mm = String.format("%02d", minute);
        String ss = String.format("%02d", second);

        String result = (jours > 0) ? jours + "j " : "";
        result += result.isEmpty() ? ((heure > 0) ? HH + ":" : "") : HH + ":";
        result += result.isEmpty() ? ((minute > 0) ? mm + ":" : "") : mm + ":";
        result += ss;

        if (isNegative) {
            result = "-" + result;
        }

        return result;
    }

    public static <K extends Comparable<? super K>, V> Map<K, V> sortByKey(Map<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByKey());

        Map<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public static String readJsonDefn(String url) throws Exception {
        // implement it the way you like
        StringBuffer   bufferJSON = new StringBuffer();
        BufferedReader br         = getBufferedReader(url);
        String         line;

        while ((line = br.readLine()) != null) {
            bufferJSON.append(line);
        }
        br.close();
        return bufferJSON.toString();
    }

    public static List<String> fileLinesToList(String filePath) throws IOException {
        List<String>   listToReturn = new ArrayList<>();
        BufferedReader br           = getBufferedReader(filePath);
        String         line;

        while ((line = br.readLine()) != null) {
            listToReturn.add(line);
        }
        br.close();
        return listToReturn;
    }

    private static BufferedReader getBufferedReader(String path) throws FileNotFoundException {
        FileInputStream input       = new FileInputStream(new File(path).getAbsolutePath());
        DataInputStream inputStream = new DataInputStream(input);
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    public static String readJsonDefnInJar(String url) throws Exception {
        // implement it the way you like
        StringBuffer bufferJSON = new StringBuffer();

        FileInputStream input = new FileInputStream(new File(url).getAbsolutePath());
        //InputStream input = Utilities.class.getClassLoader().getResourceAsStream(url);
        if (input != null) {
            DataInputStream inputStream = new DataInputStream(input);
            BufferedReader  br          = new BufferedReader(new InputStreamReader(inputStream));
            String          line;
            while ((line = br.readLine()) != null) {
                bufferJSON.append(line);
            }
            br.close();
        }
        return bufferJSON.toString();
    }

    public static boolean isValidID(Integer id) {
        return id != null && id > 0;
    }

    public static File getNewFile(String path) {
        return new File(getAbsoluePath(path));
    }

    public static boolean existFile(String path) {
        File file = getNewFile(path);
        return file.exists();
    }

    public static String getAbsoluePath(String path) {
        String fullPath = path;

        if (Thread.currentThread() != null && Thread.currentThread().getContextClassLoader() != null) {
            Object object = Thread.currentThread().getContextClassLoader().getResource(path);
            if (object != null) {
                fullPath = Thread.currentThread().getContextClassLoader().getResource(path).getFile();
            }
        }
        return fullPath;
    }

    public static File stringToFile(String text, String fileName, boolean createFile) {
        File file = new File(fileName);
        try {
            // if file doesn't exist, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            if (createFile) {
                FileWriter     fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(text);
                bw.close();
            }

        } catch (IOException e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
        return file;
    }

    public static String[] csvToArray(String fileName) throws IOException {
        List<String>   lines = new ArrayList<String>();
        String         currentLine;
        BufferedReader br;
        br = new BufferedReader(new FileReader(fileName));
        while ((currentLine = br.readLine()) != null) {
            lines.add(currentLine);
            System.out.println(currentLine);
        }
        br.close();
        return lines.toArray(new String[]{});
    }

    public static String getStringFromFile(File file) throws Exception {
        StringBuffer buffer = new StringBuffer();

        FileInputStream input       = new FileInputStream(file.getAbsolutePath());
        DataInputStream inputStream = new DataInputStream(input);
        BufferedReader  br          = new BufferedReader(new InputStreamReader(inputStream));

        String line;

        while ((line = br.readLine()) != null) {
            buffer.append(line);
        }
        br.close();
        return buffer.toString();
    }

    public static String getExtension(String str) {
        String extension = "";
        if (str == null) {
            return null;
        }

        String[] ext = str.split("\\.");
        if (ext.length > 1) {
            extension = ext[(ext.length - 1)];
            if (getMediaTypeOfFile(extension) == null) {
                return null;
            }
            return extension;
        }
        return null;
    }

    public static String removeTrailingZeros(double d, boolean thousandSeparator) {
        String result = "";
        if (thousandSeparator) {
            result = String.format(Locale.FRANCE, "%,f", d);
        } else {
            result = String.valueOf(d);
        }
        return result.replaceAll("[0]*$", "").replaceAll(".$", "");
    }

    public static String decodeBase64ToString(String base64String) {
        // Get bytes from string
        byte[] byteArray = Base64.getDecoder().decode(base64String.getBytes());

        // the decoded string
        return new String(byteArray);
    }

    public static String normalizeName(String fileName) {
        if (fileName == null) {
            throw new NullArgumentException("fileName");
        }
        String fileNormalize = null;
        fileNormalize = fileName.trim().replaceAll("\\s+", "_");
        fileNormalize = fileNormalize.replace("'", "");
        fileNormalize = fileNormalize.replace("`", "");
        fileNormalize = fileNormalize.replace("-", "_");
        fileNormalize = Normalizer.normalize(fileNormalize, Normalizer.Form.NFD);
        fileNormalize = fileNormalize.replaceAll("[^\\p{ASCII}]", "");
        fileNormalize = fileNormalize.replaceAll("[^-a-zA-Z0-9_\\-\\.]", "");

        return fileNormalize.toLowerCase();
    }


    // get a file from the resources folder
    // works everywhere, IDEA, unit test and JAR file.
    public static InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = Utilities.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }


    public static String bin2hex(byte[] data) {
        if (data == null) {
            return null;
        }
        int    len = data.length;
        String str = "";
        for (int i = 0; i < len; i++) {
            if ((data[i] & 0xFF) < 16)
                str = str + "0" + Integer.toHexString(data[i] & 0xFF);
            else
                str = str + Integer.toHexString(data[i] & 0xFF);
        }
        return str;
    }

    public static byte[] hex2bin(String str) {
        if (str == null) {
            return null;
        } else if (str.length() < 2) {
            return null;
        } else {
            int    len    = str.length() / 2;
            byte[] buffer = new byte[len];
            for (int i = 0; i < len; i++) {
                buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
            }
            return buffer;
        }
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static final String str = "s3cuv1r@g3";

    public static String encryptString(String payload) throws Exception {

        String pwd  = md5(str + "@");
        String salt = md5(str + "@");
        String iv   = md5(str + "&");

        SecretKeyFactory factory  = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec          spec     = new PBEKeySpec(pwd.toCharArray(), hex2bin(salt), 1024, 256);
        SecretKey        tmp      = factory.generateSecret(spec);
        SecretKey        secret   = new SecretKeySpec(tmp.getEncoded(), "AES");
        byte[]           iv_bytes = hex2bin(iv);
        String           value    = new String(iv_bytes);
        IvParameterSpec  ivspec   = new IvParameterSpec(iv_bytes);
        Cipher           cipher   = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret, ivspec);
        byte[] encrypted = cipher.doFinal(payload.getBytes());
        return new String(bin2hex(encrypted));
    }

    public static String decryptString(String payload) throws Exception {
        String pwd  = md5(str + "@");
        String salt = md5(str + "@");
        String iv   = md5(str + "&");

        SecretKeyFactory factory  = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec          spec     = new PBEKeySpec(pwd.toCharArray(), hex2bin(salt), 1024, 256);
        SecretKey        tmp      = factory.generateSecret(spec);
        SecretKey        secret   = new SecretKeySpec(tmp.getEncoded(), "AES");
        byte[]           iv_bytes = hex2bin(iv);
        String           value    = new String(iv_bytes);
        IvParameterSpec  ivspec   = new IvParameterSpec(iv_bytes);
        Cipher           cipher   = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret, ivspec);
        byte[] payload_bytes = hex2bin(payload);
        byte[] decrypted     = cipher.doFinal(payload_bytes);
        return new String(decrypted);
    }


    public static String substring(String str, boolean first, int size) {
        if (isBlank(str) || str.length() <= size) {
            return str;
        }
        return first ? str.substring(0, size) : str.substring(str.length() - size);
    }


    public static String formatNumber(float value) {
        final String[] arr   = {"", "k", "M", "B", "T", "P", "E"};
        int            index = 0;
        while ((value / 1000) >= 1) {
            value = value / 1000;
            index++;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.ENGLISH));
        return String.format("%s%s", decimalFormat.format(value), arr[index]);
    }

    public static void saveUserSession(UserDto usersDto, CacheUtils redisCacheUtils) {
        try {
            usersDto.setPassword(null);
            Integer existingSessionTime = UserSessionDatas.get();
            if(existingSessionTime==null) {
                existingSessionTime = Utilities.saveGlobalUserSessionInCache(existingSessionTime);
            }
            usersDto.setSessionTime(existingSessionTime);
            // redisCacheUtils.cacheData(RedisConstant.SESSION_ALIAS+usersDto.getToken(), usersDto, existingSessionTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Integer saveGlobalUserSessionInCache(Integer existingSessionTime) {
        try {
            // existingSessionTime = RedisConstant.EXPIRE_SESSION;
            UserSessionDatas.set(existingSessionTime);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return existingSessionTime;
    }

    public static boolean dateDebutPlusGrandQueDateFin(String dateDebut, String dateFin) throws ParseException {
        boolean result;
        try {
            SimpleDateFormat dateDebutFormat = new SimpleDateFormat(Objects.requireNonNull(findDateFormatByParsing(dateDebut)));
            SimpleDateFormat dateFinFormat   = new SimpleDateFormat(Objects.requireNonNull(findDateFormatByParsing(dateFin)));
            Date             dateD           = dateDebutFormat.parse(dateDebut);
            Date             dateF           = dateFinFormat.parse(dateFin);
            result = dateD.compareTo(dateF) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }
    static final String alphabet = "#abcdefghijklmnopqrstuvwxyz";

    public static Integer getColumnIndex(String column) {
        column = new StringBuffer(column.toLowerCase()).reverse().toString();
        Double columnPosition = 0d;
        for (int i = 0; i < column.length(); i++) {
            columnPosition += alphabet.indexOf(column.charAt(i)) * Math.pow(26, i);
        }
        return columnPosition.intValue() - 1;
    }

    public static Cell getCell(Row row, Integer index) {
        return (row.getCell(index) == null) ? row.createCell(index) : row.getCell(index);
    }

    public static String createFullPath(@NonNull String base, @NonNull String path) {
        if (Utilities.isBlank(path)) {
            path = "";
        }

        return (base.endsWith("/") ? base : base + "/") + path;
    }

    /*public static String createFullPath(String path, ParamsUtils paramsUtils) {
        if (Utilities.isBlank(path)) {
            path = "";
        }
        String base = createFullPath(paramsUtils.getApacheRootUrl(), "");
        return (base != null && base.endsWith("/") ? base : base + "/") + path;
    }*/

    //pour le path.root
    /*public static String createFullPathRoot(String path, ParamsUtils paramsUtils) {
        if (Utilities.isBlank(path)) {
            path = "";
        }
        String base = createFullPath(paramsUtils.getRootFilesPath(), "");
        return (base != null && base.endsWith("/") ? base : base + "/") + path;
    }*/
    
    
    public static String getEsTemplateByJobType(String jobType) {
        return String.format("/templates/es/template_%s.json", jobType);
    }



    public static void sortMapList(List<Map<String, Object>> mapList, String key) {
        sortMapList(mapList, key, false);
    }

    public static void sortMapList(List<Map<String, Object>> mapList, String key, boolean isDateKey) {
        sortMapList(mapList, key, isDateKey, "dd/MM/yyyy HH");
    }

    public static void sortMapList(List<Map<String, Object>> mapList, String key, boolean isDateKey, String format) {
        mapList.sort((o1, o2) -> {
            String v1 = o1.get(key).toString();
            String v2 = o2.get(key).toString();
            if (isDateKey) {
                return custDateCompare(v1, v2, format);
            }
            else {
                return v1.equals(v2) ? 0 : v2 == null ? -1 : v1.compareTo(v2);
            }
        });
    }

    public static int custDateCompare(String date_1, String date_2, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        int compareResult = 0;
        try {
            Date d1 = sdf.parse(date_1);
            Date d2 = sdf.parse(date_2);

            compareResult = d1.compareTo(d2);
        } catch (ParseException e) {
            compareResult = date_1.equals(date_2) ? 0 : date_2 == null ? -1 : date_1.compareTo(date_2);
        }
        return compareResult;
    }

    public static String getMonthLibelleByNumber(Integer moisNumb) {

        String mois= "";

        switch (moisNumb) {
            case 1:
                mois = "Jan";
                break;
            case 2:
                mois = "Fev";
                break;
            case 3:
                mois = "Mars";
                break;
            case 4:
                mois = "Avr";
                break;
            case 5:
                mois = "Mai";
                break;
            case 6:
                mois = "Juin";
                break;
            case 7:
                mois = "Juil";
                break;
            case 8:
                mois = "Août";
                break;
            case 9:
                mois = "Sept";
                break;
            case 10:
                mois = "Oct";
                break;
            case 11:
                mois = "Nov";
                break;
            case 12:
                mois = "Dec";
                break;
        }
        return mois;
    }
    public static Object substitutDateByPeriode(Object arg, String period ) throws ParseException {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateTimeFormat.parse(arg.toString());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Object object = null;
        Object objectMonthOfDay = null;
        if(Utilities.areEquals(period, "day")){
            //object = StringUtils.leftPad(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2, "0");
            //return object;

            //TODO formater le jour en jj/mm
            object = calendar.get(Calendar.DAY_OF_MONTH) < 10 ? "0"+calendar.get(Calendar.DAY_OF_MONTH) : calendar.get(Calendar.DAY_OF_MONTH);
            objectMonthOfDay = (calendar.get(Calendar.MONTH) + 1) < 10 ? "0"+(calendar.get(Calendar.MONTH) + 1) : calendar.get(Calendar.MONTH) + 1;
            return object+"/"+objectMonthOfDay;
        }
        if(Utilities.areEquals(period, "week")){
            object = calendar.get(Calendar.WEEK_OF_YEAR);
            object = "S_"+object;
            return object;
        }
        if(Utilities.areEquals(period, "month")){
            object = calendar.get(Calendar.MONTH) + 1;
            object = Utilities.getMonthLibelleByNumber(Integer.valueOf(object.toString()) );
            return object;
        }
        if(Utilities.areEquals(period, "year")){
            object = String.valueOf(calendar.get(Calendar.YEAR));
            return object;
        }
        object = calendar.get(Calendar.MONTH) + 1;
        object = Utilities.getMonthLibelleByNumber(Integer.valueOf(object.toString()) );
        return object;
    }


    // Cette méthode ajoute une X heure à une date donnée
    public static Date addHeures(Date date, int heure) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, heure); // minus number would decrement the minute
        return cal.getTime();
    }


    public static Date convertStringToDate(String dateString) {
        String expectedFormat = "dd/MM/yyyy HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(expectedFormat);

        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Erreur : Format de date incorrect");
            return null;
        }
    }

    public static Date getDateFromString(String string)
    {
        // Creating an instant object
        Date timestamp = null;

        // Parsing the string to Date
        timestamp = Date.from(Instant.parse(string));

        // Returning the converted timestamp
        return timestamp;
    }

    public static Map<String, Object> addKeys(List<Map<String, Object>> maps) {
        Set<String> keys = new HashSet<String>();
        for (Map<String, Object> map : maps)
            keys.addAll(map.keySet());

        Map<String, Object> result = new HashMap<String, Object>();
        for (String key : keys) {
            Double value = 0.0;
            for (Map<String, Object> map : maps){
                if (map.containsKey(key) && Utilities.areNotEquals(key, "name")){
                    value += Double.valueOf(map.get(key).toString()) ;
                } else{
                    result.put("name", map.get(key));
                }

            }

            result.put(key, value);
        }
        return result;
    }

    public  static Map<String, Object>  formatageMap(List<Map<String, Object>> list){
        Map<String, Object> l = new HashMap<>();

        AtomicDouble total = new AtomicDouble();
        List<Map<String, Object>> datas = new ArrayList<>();


        datas.stream().forEach(
                o -> {
                    Map<String, Object> map = new HashMap<>();
                    map = datas.get(0);
                    System.out.println(Utilities.areEquals(map, o));

                    if(Utilities.areEquals(map, o)){

                    }
                }
        );
        return l;
    }



    public void setZeroToMissingKeyData(List<Map<String, Object>> mapList, List<String> listOfAllKey, String key) {
        for (String keyValue : listOfAllKey) {
            if (mapList.stream().anyMatch(m -> Utilities.areEquals(m.get(key), keyValue))) {
                continue;
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(key, keyValue);
            mapList.add(map);
        }
        // sort data
        sortMapList(mapList, key);
    }


    public static String uuid() {
        return UUID.randomUUID().toString();
    }


    public static String initiateDateNotify (){
        String pattern = "dd/MM/yyyy HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());
    }


    public static String generateCronExpression(int frequency, String unit, String at, List<Integer> repeatOn, String dayOfMonth) throws ParseException {
        StringBuilder cronExpression = new StringBuilder();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date time = new Date();
        if (unit.equals("minute") || unit.equals("hour")){
            at = "";
        }
        if (Utilities.isNotBlank(at)){
            time = timeFormat.parse(at);
            cronExpression.append(time.getMinutes()).append(" ");
            cronExpression.append(time.getHours()).append(" ");
        }

        if (unit.equals("minute")){
            cronExpression.append("0 */").append(frequency).append(" * ? * *");
        }
         else if (unit.equals("hour")) {
            cronExpression.append("0 0 */").append(frequency).append(" ? * *");
        } else if (unit.equals("day")) {
            cronExpression.append("0 */").append(frequency).append(" * * ?");
        } else if (unit.equals("week")) {
            if (Utilities.isEmpty(repeatOn)) {
                throw new IllegalArgumentException("At least one day of the week must be selected");
            }
            cronExpression.append("0 ? * ");
            for (int day : repeatOn) {
                cronExpression.append(String.valueOf(day)).append(",");
            }
            cronExpression.setLength(cronExpression.length() - 1);
            cronExpression.append(" *");
        } else if (unit.equals("month")) {
            cronExpression.append(dayOfMonth).append(" ");
            cronExpression.append("1/").append(frequency).append(" * ?");
        } else if (unit.equals("year")) {
            if (dayOfMonth.contains("/")) {
                cronExpression.append(dayOfMonth).append(" * ");
                cronExpression.append("1/").append(frequency);
            } else {
                cronExpression.append(dayOfMonth.substring(3)).append(" ");
                cronExpression.append(dayOfMonth.substring(0, 2)).append(" ");
                cronExpression.append("1/").append(frequency);
            }
        }

        return cronExpression.toString();
    }


    public static int getCurrentTimeToSec() {
        return LocalDateTime.now().getSecond();
    }

    public static int durationToSec(int startTime, int endTime) {
        return endTime - startTime;
    }

    public static boolean isNotOlderThanOneHour(String dateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime inputDateTime = LocalDateTime.parse(dateTimeStr, formatter);

        LocalDateTime now = LocalDateTime.now();

        Duration duration = Duration.between(inputDateTime, now);

        return duration.toHours() <= 1;
    }

}
