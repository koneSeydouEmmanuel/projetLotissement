package com.ilot.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ilot.business.RedisUserBusiness;
import com.ilot.dao.entity.User;
import com.ilot.dao.repository.UserRepository;
import com.ilot.utils.contract.Request;
import com.ilot.utils.contract.Response;
import com.ilot.utils.dto.UserDto;
import com.ilot.utils.security.ManageRsa;
import com.ilot.utils.security.SecureUtils;
import lombok.extern.java.Log;
import org.apache.commons.codec.binary.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Log
@Component
public class HttpRequestUtilis {

    @Autowired
    UserRepository userRepository;

    public void calledEncryptRequestManagement(FilterChain chain, HttpServletRequest httpServletRequest,
                                               HttpServletResponse httpServletResponse, String uri, UserDto user, String requestValue,
                                               FunctionalError functionalError)
            throws IOException, ServletException {

        String serviceLibelle   = "";
        String requestValueLog  = "";
        String responseValueLog = "";
        String ipAddress        = Utilities.getClientIp(httpServletRequest);
        try {
            String     resp;
            JSONObject reqToJson;
            // get request value
            String encryptRequest = requestValue;
            // decrypt request
            String decriptRequest = SecureUtils.ExtractDataFromAes(ExtractData(encryptRequest), user.getKey());
            // build request to send
            reqToJson = Utilities.notBlank(decriptRequest) ? new JSONObject(decriptRequest) : new JSONObject();
            reqToJson.put("user", user.getId());
            reqToJson.put("userId", user.getId());
            reqToJson.put("token", user.getToken());

            String responseToPublish = "";
            if (Utilities.blank(decriptRequest)) {
                log.info("--- Forbidden serviceLibelle is not valid ----");
                responseToPublish = ReturnAccesDenied(httpServletRequest, httpServletResponse,
                        " request value is blank ", functionalError);
                responseValueLog  = responseToPublish;
            } else {

                decriptRequest = reqToJson.toString();

                // Je recupère le libellé du service
                //serviceLibelle = reqToJson.get("serviceLibelle").toString();
                serviceLibelle = reqToJson.has("serviceLibelle") ? reqToJson.get("serviceLibelle").toString() : "N/A";

                HttpServletRequestWritableWrapper requestWrapper = new HttpServletRequestWritableWrapper(
                        httpServletRequest, decriptRequest.getBytes());
                HttpServletResponseReadableWrapper responseWrapper = new HttpServletResponseReadableWrapper(
                        httpServletResponse);
                chain.doFilter(requestWrapper, responseWrapper);
                responseValueLog = responseWrapper.getResponseData();
                resp             = responseValueLog;

                responseToPublish = SecureUtils.EncryptResponse(resp, user.getKey());
            }
            requestValueLog = reqToJson.toString();
            publishResponse(responseToPublish, httpServletResponse);

        } catch (Exception e) {
            responseValueLog = e.getMessage();
        } finally {

            // THIS METHOD ALLOWS TO LOG REQUESTS
            /*logRequest(uri, user, serviceLibelle, requestValueLog, responseValueLog, ipAddress, esConfig,
                    highClientFactory);*/
        }
    }


    public void calledNoEncryptRequestManagement(FilterChain chain, HttpServletRequest httpServletRequest,
                                                 HttpServletResponse httpServletResponse, String uri, UserDto user, String requestValue,
                                                 FunctionalError functionalError)
            throws IOException, ServletException {

        // Mes variables
        String                             responseToPublish = "";
        String                             requestValueLog   = "";
        String                             serviceLibelle    = "";
        String                             responseValueLog  = "";
        String                             ipAddress         = Utilities.getClientIp(httpServletRequest);
        String                             resp;
        JSONObject                         reqToJson;
        HttpServletRequestWritableWrapper  requestWrapper    = null;
        HttpServletResponseReadableWrapper responseWrapper   = null;

        try {

            reqToJson = Utilities.notBlank(requestValue) ? new JSONObject(requestValue) : new JSONObject();
            reqToJson.put("user", user.getId());
            reqToJson.put("userId", user.getId());
            reqToJson.put("ipAddress", Utilities.getClientIp(httpServletRequest));
            reqToJson.put("cookies", httpServletRequest.getHeader("cookies"));

            if (Utilities.blank(requestValue)) {
                log.info("--- Forbidden serviceLibelle is not valid ----");

                responseToPublish = ReturnAccesDenied(httpServletRequest, httpServletResponse, " request value is blank ", functionalError);

                responseValueLog = responseToPublish;
            } else {

                // Je recupère le libellé du service
                serviceLibelle = reqToJson.has("serviceLibelle") ? reqToJson.get("serviceLibelle").toString() : "N/A";

                // Je récupère le request wrapper
                requestWrapper = new HttpServletRequestWritableWrapper(httpServletRequest, requestValueLog.getBytes());
                responseWrapper = new HttpServletResponseReadableWrapper(httpServletResponse);
                chain.doFilter(requestWrapper, responseWrapper);

                responseToPublish = responseWrapper.getResponseData();

            }

            requestValueLog = reqToJson.toString();
            publishResponse(responseToPublish, httpServletResponse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {

            // THIS METHOD ALLOWS TO LOG REQUESTS
            /*logRequest(uri, user, serviceLibelle, requestValueLog, responseValueLog, ipAddress, esConfig,
                    highClientFactory);*/
        }
    }


    public void calledNoEncryptRequestManagement(FilterChain chain, HttpServletRequest httpServletRequest,
                                                 HttpServletResponse httpServletResponse, String uri, RedisUserBusiness redisUserBusiness, String requestValue,
                                                 FunctionalError functionalError)
            throws IOException, ServletException {

        // Mes variables
        String                             responseToPublish            = "";
        String                             requestValueLog              = "";
        String                             serviceLibelle               = "";
        String                             responseValueLog             = "";
        String                             ipAddress                    = Utilities.getClientIp(httpServletRequest);
        String                             resp;
        JSONObject                         reqToJson;
        HttpServletRequestWritableWrapper  requestWrapper               = null;
        HttpServletResponseReadableWrapper responseWrapper              = null;
        List<UserDto>                      usrDto                       = null;
        UserDto                            user                         = null;
        ObjectMapper                       objectMapper                 = null;
        Request                            req                          = null;
        Integer                            userId                       = null;
        CachedBodyHttpServletRequest       cachedBodyHttpServletRequest = null;
        String login = null;

        // Je récupère le request wrapper
        requestWrapper = new HttpServletRequestWritableWrapper(httpServletRequest, requestValue.getBytes());

        // Je récupère le response wrapper
        responseWrapper = new HttpServletResponseReadableWrapper(httpServletResponse);

        // si l'url est /user/getPublicKey" ou /sonde-qoe/user/getPublicKey ou encore /otv-diaspora/user/login alors je l'ignore
        if(Utilities.areEquals(uri, "/sonde-qoe/dashboard/getRemoteAddress") || Utilities.areEquals(uri, "/dashboard/getRemoteAddress") || Utilities.areEquals(uri, "/user/getPublicKey") || Utilities.areEquals(uri, "/sonde-qoe/user/getPublicKey") || Utilities.areEquals(uri, "/otv-diaspora/user/login") || uri.contains("/fetchStatus") || uri.contains("/api-docs")){
            // Je fais la requête
            chain.doFilter(requestWrapper, responseWrapper);
            responseToPublish = responseWrapper.getResponseData();
            publishResponse(responseToPublish, httpServletResponse);
            return;
        }

        try {

            // Je fais la requête
            chain.doFilter(requestWrapper, responseWrapper);

            responseToPublish = responseWrapper.getResponseData();

            System.out.printf(requestValue);

            // Je cré une intance de ObjectMapper
            objectMapper = new ObjectMapper();

            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            // je convertis la valeur de la requête en Request
            req = objectMapper.readValue(requestValue, Request.class);

            // Je récupère les l'ID du user
            userId = (req != null && req.getUser() != null) ? req.getUser() : null;

            if (userId != null) {

                UserDto UserDto = new UserDto();
                UserDto.setId(userId);

                Request<UserDto> reqUserDto = new Request<UserDto>();
                reqUserDto.setData(UserDto);

                usrDto = redisUserBusiness.getRedisUserState(reqUserDto, Locale.FRANCE).getItems();

                if (Utilities.isNotEmpty(usrDto)) {
                    Integer finalUserId = userId;
                    user = usrDto.stream().findFirst().orElse(null);
                }
            }

            // Si la valeur de la requête existe alors je la convertie en un objet JSON sinon je retour un JSONObject vide
            reqToJson = Utilities.notBlank(requestValue) ? new JSONObject(requestValue) : new JSONObject();

            // Si l'utilisateur qui fait la requête existe alors
            if (user != null) {

                reqToJson.put("user", user.getId());
                reqToJson.put("userId", user.getId());
            }

            // Je set l'IP
            reqToJson.put("ipAddress", Utilities.getClientIp(httpServletRequest));

            // Je recupère le libellé du service
            serviceLibelle = reqToJson.has("serviceLibelle") ? reqToJson.get("serviceLibelle").toString() : "N/A";            // Je set les cookies

            // j'ajoute le cookies
            reqToJson.put("cookies", httpServletRequest.getHeader("cookies"));

            // je récupère la valeur de la requête
            requestValueLog = reqToJson.toString();

            // je pousse la reponse
            publishResponse(responseToPublish, httpServletResponse);

        } catch (Exception e) {
            System.out.println("Erreur rencontrée dans calledNoEncryptRequestManagement : " + e.getMessage());
        } finally {

            /*// THIS METHOD ALLOWS TO LOG REQUEST
            logRequest(uri, user, serviceLibelle, requestValueLog, responseToPublish, ipAddress, esConfig,
                    highClientFactory);*/
        }
    }

    public static void publishResponse(String resp, HttpServletResponse httpServletResponse) throws IOException {
        OutputStream outputStream = httpServletResponse.getOutputStream();
        outputStream.write(resp.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    public static void noEncryptRequestThenChainDoFilter(ServletResponse response, FilterChain chain,
                                                         HttpServletRequest httpServletRequest, String requestValue) throws IOException, ServletException {
        JSONObject reqToJson;
        reqToJson = new JSONObject(requestValue);
        // TODO: ajout du ipAdress et la cookies ayant mener l'action dans la requête
        reqToJson.put("ipAddress", Utilities.getClientIp(httpServletRequest));
        reqToJson.put("cookies", httpServletRequest.getHeader("cookies"));
        requestValue = reqToJson.toString();
        String requestValueLog = reqToJson.toString();
        HttpServletRequestWritableWrapper requestWrapper = new HttpServletRequestWritableWrapper(httpServletRequest,
                requestValueLog.getBytes());
        chain.doFilter(requestWrapper, response);
    }

    public void connexionExtractService(FilterChain chain, HttpServletRequest httpServletRequest,
                                               HttpServletResponse httpServletResponse, Boolean isEncrypte, String requestValue) throws IOException, ServletException {

        // Mes declarations
        String                             serviceLibelle   = null;
        String                             requestValueLog  = null;
        String                             responseValueLog = null;
        String                             responsValueLog  = null;
        String                             privateAesKey;
        JSONObject                         reqToJson;
        String                             ipAddress        = null;
        UserDto                            user             = null;
        HttpServletRequestWritableWrapper  requestWrapper   = null;
        HttpServletResponseReadableWrapper responseWrapper  = null;
        String uri  = null;
        String login = null;

        try {

            // si c'est encrypté alors
            if (Utilities.isTrue(isEncrypte)) {

                // je récupère la valeur de la requête
                requestValue = ManageRsa.decryptRsa(ExtractData(requestValue));
            }

            // je cré une instance de JSONObject
            reqToJson = new JSONObject(requestValue);

            // vérifie si la cookie et l'ip son bien renseigné si oui on les set dans l'objet
            if (Utilities.isNotBlank(Utilities.getClientIp(httpServletRequest))) {

                // je set l'IP dans mon objet JSON
                reqToJson.put("ipAddress", Utilities.getClientIp(httpServletRequest));
            }

            // Je récupère la clé privée
            privateAesKey = reqToJson.get("key") != null ? reqToJson.get("key").toString() : null;

            requestValueLog = reqToJson.toString();

            // TODO: ajout du user ayant mener l'action dans la requête
            requestWrapper = new HttpServletRequestWritableWrapper(httpServletRequest, requestValueLog.getBytes());

            responseWrapper = new HttpServletResponseReadableWrapper(httpServletResponse);

            chain.doFilter(requestWrapper, responseWrapper);

            uri = httpServletRequest.getRequestURI().toString();

            responseValueLog = responseWrapper.getResponseData();

            responsValueLog = responseWrapper.getResponseData();

            serviceLibelle = reqToJson.get("serviceLibelle").toString();

            // je cré une instance de JSONObject pour contenir la reponse
            JSONObject response = new JSONObject(responseValueLog);

            // je récupère le user
            user = (response != null && response.get("hasError") != null && response.get("hasError").equals(false)
                    && response.has("items") && response.getJSONArray("items") != null && response.getJSONArray("items").length() > 0)
                    ? new UserDto(response.getJSONArray("items").getJSONObject(0))
                    : new UserDto();

            // TODO: encrypt response
            //System.out.println("responseValueLog " + responseValueLog);
            responseValueLog = SecureUtils.EncryptResponse(responseValueLog, privateAesKey);

            // Pour supprimer le mot de passe
            if (reqToJson.has("data") && reqToJson.getJSONObject("data").has("password")) {
                // Je remplace le mot de passe
                reqToJson.getJSONObject("data").put("password", "xxxxxxxxxxxxxxxx");

                // je recupère le login
                login = reqToJson.getJSONObject("data").get("login").toString();
            }

            // je set le login
            user.setLogin(login);

            // Je récupère le log de la requête
            requestValueLog = reqToJson.toString();
            System.out.println("responseValueLog Encrypted " + responseValueLog);

            // je pousse la reponse
            publishResponse(responseValueLog, httpServletResponse);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            // THIS METHOD ALLOWS TO LOG REQUESTS
            /*logRequest(uri, user, serviceLibelle, requestValueLog, responsValueLog, ipAddress, esConfig,
                    highClientFactory);*/
            //@Winter - 04/05/2023 : change responsValueLog to responseValueLog
            /*logRequest(uri, user, serviceLibelle, requestValueLog, responseValueLog, ipAddress, esConfig,
                    highClientFactory);*/
        }

    }

    /**************************************************
    ***CETTE METHODE EXTRAIT LA VALEUR DE LA REQUETE***
     **************************************************/
    public static String ExtractRequest(ServletRequest request) {
        String requestBody = "";
        try {
            request.setCharacterEncoding("UTF-8");
            StringBuilder builder = new StringBuilder();
            String        aux     = "";
            while ((aux = request.getReader().readLine()) != null) {
                String rawString         = aux;
                byte[] bytes             = StringUtils.getBytesUtf8(rawString);
                String utf8EncodedString = StringUtils.newStringUtf8(bytes);
                builder.append(utf8EncodedString);
            }
            requestBody = builder.toString();
        } catch (Exception e) {
            log.warning(e.getMessage());
            System.out.println("Erreur rencontrée dans ExtractRequest : " + e.getMessage());
        }
        return requestBody;
    }

    public static String ReturnAccesDenied(HttpServletRequest req, HttpServletResponse res, String message,
                                           FunctionalError functionalError) {
        String responseValue = "";
        try {
            Response respObj = new Response();
            respObj.setHasError(Boolean.TRUE);
            String lang   = req.getHeader("lang");
            Locale locale = null;
            if (lang != null) {
                locale = new Locale(lang, "");
            } else {
                locale = new Locale("en", "");
            }
            respObj.setStatus(functionalError.ACCESS_DENIED(message, locale));
            responseValue = new Gson().toJson(respObj, Response.class);
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            res.setHeader("plainData", Boolean.TRUE + "");
            res.setHeader("Access-Control-Expose-Headers", "plainData");
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
        return responseValue;
    }

    public static void returnExpiredSession(HttpServletRequest req, HttpServletResponse res, String message,
                                            FunctionalError functionalError) {
        String responseValue = "";
        try {
            Response respObj = new Response();
            respObj.setHasError(Boolean.TRUE);
            String lang   = req.getHeader("lang");
            Locale locale = null;
            if (lang != null) {
                locale = new Locale(lang, "");
            } else {
                locale = new Locale("en", "");
            }
            respObj.setStatus(functionalError.SESSION_EXPIRED(message, locale));
            responseValue = new Gson().toJson(respObj, Response.class);
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            res.setHeader("plainData", Boolean.TRUE + "");
            res.setHeader("Access-Control-Expose-Headers", "plainData");
            publishResponse(responseValue, res);
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
    }

    public static String ExtractData(String requestBody) {
//		log.info("--- encryptRequest requestBody  ----" + requestBody);
        String data = "";
        try {
            if (Utilities.notBlank(requestBody)) {
                JSONObject jsonObject = new JSONObject(requestBody);
                data = jsonObject.get("data").toString();
            }
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
        return data;
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

}
