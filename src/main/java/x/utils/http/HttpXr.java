package x.utils.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;

import x.comm.DataXr;
import x.comm.MapXr;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpXr {

	/*
	 * { "url", "path", "params", "method", "auth", "cont_tp" };
	 */

	private String[] arr = { "url", "path", "params", "method" };
	private CloseableHttpResponse httpRes = null;
	private String url, path, params, method, auth, cont_tp;
	private MapXr mxr;
	private DataXr dxr;

	public HttpXr() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Map<String, Object> callX(Map<String, Object> m) {
		Map<String, Object> d = new HashMap<String, Object>();
		try {
			if (arr.length < 0) {
				d.put("err_cd", "500");
				d.put("err_msg", "no params");
				return d;
			}

			for (int i = 0; i < arr.length; i++) {
				if (!m.containsKey(arr[i])) {
					d.put("err_cd", "500");
					d.put("err_msg", arr[i] + " is required");
					return d;
				}
			}
			for (Entry<String, Object> o : m.entrySet()) {
				String strV = String.valueOf(o.getValue());
				String strK = String.valueOf(o.getKey());
				if (strV == null || StringUtils.equalsIgnoreCase(strV, "null")) {
					d.put("err_cd", "500");
					d.put("err_msg", strK + " is invalid");
					return d;
				} else if (StringUtils.isBlank(strV)) {
					d.put("err_cd", "500");
					d.put("err_msg", strK + " is invalid");
					return d;
				}
			}

			// init common params
			url = (String) m.get("url");
			path = (String) m.get("path");
			
			ObjectMapper om = new ObjectMapper();

			if(m.get("params") instanceof Map<?, ?>){
				params = om.writeValueAsString(m.get("params"));
			}else {
				params = (String) m.get("params");
			}
			
			method = (String) m.get("method");
			auth = (String) m.get("auth");
			cont_tp = (String) m.get("cont_tp");

			TrustStrategy trustStrategy = (cert, authType) -> true;
			SSLContext sslContext = SSLContexts.custom()
					.loadTrustMaterial(null, trustStrategy).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslContext, NoopHostnameVerifier.INSTANCE);

			Registry<ConnectionSocketFactory> registry = RegistryBuilder
					.<ConnectionSocketFactory> create()
					.register("https", sslsf)
					.register("http", new PlainConnectionSocketFactory())
					.build();

			BasicHttpClientConnectionManager connectionManager = new BasicHttpClientConnectionManager(
					registry);
			CloseableHttpClient httpClient = HttpClients.custom()
					.setSSLSocketFactory(sslsf)
					.setConnectionManager(connectionManager).build();

			URIBuilder urlBuilder = new URIBuilder(url);
			urlBuilder.setPath(path);
			URI uri = urlBuilder.build();

			StringEntity entity = new StringEntity(params);
			String authHeader = "";
			if (auth != null) {
				authHeader = new String(auth);
			}

			switch (StringUtils.upperCase(method)) {
			case "GET":
				HttpGet httpGet = new HttpGet(uri);
				httpGet.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
				httpGet.setHeader("Content-type", cont_tp);
				httpRes = httpClient.execute(httpGet);
				break;
			case "POST":
				HttpPost httpPost = new HttpPost(uri);
				httpPost.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
				httpPost.setHeader("Content-type", cont_tp);
				httpPost.setEntity(entity);
				httpRes = httpClient.execute(httpPost);
				break;
			case "PUT":
				HttpPut httpPut = new HttpPut(uri);
				httpPut.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
				httpPut.setHeader("Content-type", cont_tp);
				httpPut.setEntity(entity);
				httpRes = httpClient.execute(httpPut);
				break;
			case "PATCH":
				HttpPatch httpPatch = new HttpPatch(uri);
				httpPatch.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
				httpPatch.setHeader("Content-type", cont_tp);
				httpPatch.setEntity(entity);
				httpRes = httpClient.execute(httpPatch);
				break;
			case "DELETE":
				HttpDelete httpDelete = new HttpDelete(uri);
				httpDelete.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
				httpDelete.setHeader("Content-type", cont_tp);
				httpRes = httpClient.execute(httpDelete);
				break;

			default:
				break;
			}

			StatusLine stsLine = httpRes.getStatusLine();
			int statusCode = stsLine.getStatusCode();

			HttpEntity data = httpRes.getEntity();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					data.getContent()));

			String s;
			StringBuffer sb = new StringBuffer();
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			br.close();
			if (!(sb == null || StringUtils.isBlank(String.valueOf(sb)))) {
				// d.clear();
				mxr = new MapXr();
				dxr = new DataXr();
				if (dxr.isJson(String.valueOf(sb))) {
					Map<String, Object> xr = mxr.getX(dxr.json2map(String
							.valueOf(sb)));
					d = xr;
				} else if (dxr.isXml(String.valueOf(sb))) {
					d.put("str_xml", String.valueOf(sb));
				}
			}

			d.put("http_code", statusCode);
			d.put("http_msg", getHtx(statusCode));
			return d;

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;

	}

	public String getHtx(int sts) {
		switch (sts) {
		case 400:
			return "Bad Request";
		case 401:
			return "Unauthorized";
		case 404:
			return "Request Not Found";
		case 405:
			return "Method Not Allowed";
		case 406:
			return "Not Acceptable";
		case 408:
			return "Request Timeout";
		case 415:
			return "Unsupported Media Type";
		case 500:
			return "Internal Server Error";
		case 502:
			return "Bad Gateway";
		case 503:
			return "Service Unavailable";
		case 504:
			return "Gateway Timeout";
		default:
			break;
		}
		return null;
	}
}
