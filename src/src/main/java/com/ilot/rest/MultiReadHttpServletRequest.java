package com.ilot.rest;


import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;


/**
 * Purpose of this class is to make getParameter() return post data AND also be able to get entire
 * body-string. In native implementation any of those two works, but not both together.
 */
public class MultiReadHttpServletRequest extends HttpServletRequestWrapper {
    public static final String UTF8 = "UTF-8";
    public static final Charset UTF8_CHARSET = Charset.forName(UTF8);
    private ByteArrayOutputStream cachedBytes;
    private Map<String, String[]> parameterMap;

    public MultiReadHttpServletRequest(HttpServletRequest request) {
        super(request);
    }

  
    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (cachedBytes == null) cacheInputStream();
        return new CachedServletInputStream();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    private void cacheInputStream() throws IOException {
    /* Cache the inputStream in order to read it multiple times. For
     * convenience, I use apache.commons IOUtils
     */
        cachedBytes = new ByteArrayOutputStream();
        IOUtils.copy(super.getInputStream(), cachedBytes);
    }

    @Override
    public String getParameter(String key) {
        Map<String, String[]> parameterMap = getParameterMap();
        String[] values = parameterMap.get(key);
        return values != null && values.length > 0 ? values[0] : null;
    }

    @Override
    public String[] getParameterValues(String key) {
        Map<String, String[]> parameterMap = getParameterMap();
        return parameterMap.get(key);
    }

    

  

    public String getPostBodyAsString() {
        try {
            if (cachedBytes == null) cacheInputStream();
            return cachedBytes.toString(UTF8);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* An inputStream which reads the cached request body */
    public class CachedServletInputStream extends ServletInputStream {
        private ByteArrayInputStream input;

        public CachedServletInputStream() {
            /* create a new input stream from the cached request body */
            input = new ByteArrayInputStream(cachedBytes.toByteArray());
        }

        @Override
        public int read() throws IOException {
            return input.read();
        }

		@Override
		public boolean isFinished() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isReady() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void setReadListener(ReadListener listener) {
			// TODO Auto-generated method stub
			
		}
    }

    @Override
    public String toString() {
        String query = getQueryString();
        StringBuilder sb = new StringBuilder();
        sb.append("URL='").append(getRequestURI()).append(query.isEmpty() ? "" : "?" + query).append("', body='");
        sb.append(getPostBodyAsString());
        sb.append("'");
        return sb.toString();
    }
}