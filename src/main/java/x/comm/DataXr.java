package x.comm;

import java.io.StringReader;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.InputSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class DataXr {

	public DataXr() {
		super();
		// TODO Auto-generated constructor stub
	}

	private final Gson gson = new Gson();

	// json 2 map key/value
	@SuppressWarnings("unchecked")
	public Map<String, Object> json2map(String s) {
		if (isJson(s)) {
			return gson.fromJson(s, Map.class);
		}
		return null;
	}

	// check is json valid
	public boolean isJson(String s) {
		try {
			gson.fromJson(s, Object.class);
			return true;
		} catch (JsonSyntaxException ex) {
			return false;
		}
	}

	// check is map valid
	public boolean isMap(Object o) {
		ObjectMapper om = new ObjectMapper();
		try {
			om.convertValue(o, Map.class);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isXml(String s) {
		// Parser that produces DOM object trees from XML content
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// API to obtain DOM Document instance
		DocumentBuilder builder = null;
		try {
			// Create DocumentBuilder with default configuration
			builder = factory.newDocumentBuilder();

			// Parse the content to Document object
			builder.parse(new InputSource(new StringReader(s)));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
