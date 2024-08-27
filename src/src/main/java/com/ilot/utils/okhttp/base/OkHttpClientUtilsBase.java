package com.ilot.utils.okhttp.base;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilot.utils.Status;
import com.ilot.utils.Utilities;
import com.ilot.utils.contract.Response;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class OkHttpClientUtilsBase {

    protected okhttp3.Response get(String url, long timeout, TimeUnit timeUnit) throws IOException {
        okhttp3.OkHttpClient client    = new OkHttpClient.Builder()
                .writeTimeout(timeout, timeUnit)
                .readTimeout(timeout, timeUnit)
                .connectTimeout(timeout, timeUnit)
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("isEncrypte", "false")
                .build();
        return client.newCall(request).execute();
    }

    protected okhttp3.Response get(String url) throws IOException {
        okhttp3.OkHttpClient client    = new okhttp3.OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("isEncrypte", "false")
                .build();

        return client.newCall(request).execute();
    }

    protected okhttp3.Response post(String url, String queryBody, String type) throws IOException {
        okhttp3.OkHttpClient client = new OkHttpClient.Builder()
                .writeTimeout(3600, TimeUnit.SECONDS)
                .readTimeout(3600, TimeUnit.SECONDS)
                .connectTimeout(3600, TimeUnit.SECONDS)
                .build();
        okhttp3.MediaType   mediaType = okhttp3.MediaType.parse(getMediaType(type) + "; charset=utf-8");
        okhttp3.RequestBody body      = okhttp3.RequestBody.create(mediaType, queryBody);
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("isEncrypte", "false")
                .build();
        /*
        client.newBuilder()
                .connectTimeout(3600, TimeUnit.SECONDS)
                .writeTimeout(3600, TimeUnit.SECONDS)
                .readTimeout(3600, TimeUnit.SECONDS)
                .build();*/

        return client.newCall(request).execute();
    }

    protected Response getResponse(okhttp3.Response response) throws IOException {
        Response apiResponse = new Response();
        if (!response.isSuccessful() || response.body() == null) {
            Status status = new Status();
            status.setCode(response.code() + "");
            status.setMessage(response.body() == null ? "L'appel de l'api a échoué." : response.message());
            apiResponse.setHasError(true);
            apiResponse.setStatus(status);
        } else {
            ObjectMapper         objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            okhttp3.ResponseBody responseBody = response.body();
            apiResponse = objectMapper.readValue(responseBody.string(), Response.class);
        }

        return apiResponse;
    }

    protected String getMediaType(String type) {
        String mediaType = "application/json";
        if (Utilities.areEquals(type, "xml")) {
            mediaType = "application/xml";
        }
        return mediaType;
    }
}
