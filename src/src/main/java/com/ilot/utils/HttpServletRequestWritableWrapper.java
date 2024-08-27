package com.ilot.utils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;

public class HttpServletRequestWritableWrapper extends HttpServletRequestWrapper {

    private final ByteArrayInputStream decryptedDataBAIS;

    public HttpServletRequestWritableWrapper(HttpServletRequest request, byte[] decryptedData) {
        super(request);
        decryptedDataBAIS = new ByteArrayInputStream(decryptedData);
    }

    @Override
    public BufferedReader getReader() throws UnsupportedEncodingException {
    	return new BufferedReader(new InputStreamReader(decryptedDataBAIS, Charset.forName("UTF-8")));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
    	ServletInputStream inputStream=null;
    	inputStream= new ServletInputStream() {
            @Override
            public int read() {
            	int e=decryptedDataBAIS.read();
                return e;
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
			public void setReadListener(ReadListener arg0) {
				// TODO Auto-generated method stub
			}
        };
        return inputStream;
    }
}
