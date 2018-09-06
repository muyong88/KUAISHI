package com.poac.quickview.communication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.codec.binary.*;

import com.google.gson.JsonObject;
import com.poac.quickview.util.LogFactory;

/**
 * This class aimed to supply some methods to easily build a HTTPS connection
 *
 */
public class Connection {
	
	/**
	 * Define method name based on HTTP 1.1
	 */
	public static final String METHOD_GET    = "GET";
	public static final String METHOD_PUT    = "PUT";
	public static final String METHOD_POST   = "POST";
	public static final String METHOD_DELETE = "DELETE";
	
	public static final String CHARSET_UTF8 = "utf-8";
	
	public static final String CONTENT_TYPE_JSON = "application/json";
	public static final String CONTENT_TYPE_TEXT_PLAIN = "text/plain";
	public static final String CONTENT_TYPE_TEXT_XML = "text/xml";
	
	private static final int CONNECT_TIMEOUT = 15*1000;
	
	
	/**
	 * The TLS version 1.0 algorithm name can be specified when generating an instance of SSLContext	
	 */
	private static final String SSLCONTEXT_ALGORITHM_TLS_V1 = "TLSv1";
	
//	private static final String LTPATOKEN2 = "LtpaToken2";
	
	private String url;
	
	private String body;
	
	private String method;
	
	private HashMap<String, String> paramMap = new HashMap<String, String>();
	
	private HashMap<String, String> headerMap = new HashMap<String, String>();
	
	private SSLContext ctx;
	
	private HttpsURLConnection conn;
	
	private int statusCode = 0;
	private String statusMesg="";
	
	private InputStream is;
	
	public void setURL(String url) {
		this.url = url;
	}
	
	public void putParam(String name, String value) {
		paramMap.put(name, value);
	}
	
	public void putHeader(String name, String value) {
		headerMap.put(name, value);
	}
	
	public void authorizeByPassword(String username, String password) {
		String tok = username + ":" + password;
		String code = Base64.encodeBase64String(tok.getBytes());
		putHeader("Authorization", "Basic " + code);
	}
	
	public void setContentType(String type) {
		putHeader("Content-Type", type);
	}
	
	public void setBodyAsJson(JsonObject json) {
		// TODO
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public void authorizeByLtpa() {
		// TODO
	}
	
	private void connect() {
		contextInit();
		getConnection();
		conn.setHostnameVerifier(new HostnameVerifier(){
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
		});
		conn.setConnectTimeout(CONNECT_TIMEOUT);
		conn.setReadTimeout(CONNECT_TIMEOUT);
		if (body != null && body.length() != 0) {
			OutputStream out;
			try {
				out = new DataOutputStream(conn.getOutputStream());
				out.write(body.getBytes());
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void contextInit(){
		try{
			ctx = SSLContext.getInstance(SSLCONTEXT_ALGORITHM_TLS_V1);
			ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
		} catch (Exception e){
			LogFactory.getGlobalLog().warning("https connection failed: can't init SSLContext");
		}
		SSLContext.setDefault(ctx);
	}
	
	private void getConnection() {
		try {
			conn = (HttpsURLConnection) new URL(url).openConnection();
			conn.setRequestMethod(method);  
		} catch (MalformedURLException e) {
			LogFactory.getGlobalLog().warning("https connection failed: url is wrong");
		} catch (IOException e) {
			LogFactory.getGlobalLog().warning("https connection failed: open connection failed");
		}  
		
        conn.setDoInput(true);  
        conn.setDoOutput(true);  
        setAllHeader();
	}
	
	private void setAllHeader() {
		if(!headerMap.isEmpty()){
			Set<String> headerSet = headerMap.keySet();
			for (String header : headerSet){
				conn.setRequestProperty(header, headerMap.get(header));
			}
		}
	}
	
	public void PUT() {
		method = METHOD_PUT;
		connect();
		getResponse();
	}
	
	public void POST() {
		method = METHOD_POST;
		connect();
		getResponse();
	}
	
	public void GET() {
		method = METHOD_GET;
		connect();
		getResponse();
	}
	
	public void DELETE() {
		method = METHOD_DELETE;
		connect();
		getResponse();
	}
	
	
	public int getStatusCode() {
		return statusCode;
	}
	public String getStatusMesg(){
		return statusMesg;
		
	}

	
	public String getBodyAsString() {
		if (is != null){
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(is, CHARSET_UTF8));
			} catch (UnsupportedEncodingException e) {
				LogFactory.getGlobalLog().warning("https connection failed: encoding is not supportted");
			}
			StringBuffer sb = new StringBuffer();
			String line = null;
			try {
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return sb.toString();
		} else return null;
	}
	
	public byte[] getBodyAsBytes() {
		// TODO
		return null;
	}
	   
	private void getResponse() {
		try{		
			statusCode = conn.getResponseCode();
			statusMesg=conn.getResponseMessage();
			if (statusCode/100 ==2) {
				is = conn.getInputStream();
			} else {
				// TODO
			}
		} catch (SocketTimeoutException stoe){
			LogFactory.getGlobalLog().warning("TimeoutException: "+ method + url);
			
		} catch (IOException ioe){
			LogFactory.getGlobalLog().warning("IOexception: "+ ioe.toString());
		}
	};
	
	private static class DefaultTrustManager implements X509TrustManager{

		public void checkClientTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
			// TODO
		}

		public void checkServerTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
			// TODO
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
		
	}
}
