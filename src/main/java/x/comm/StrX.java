package x.comm;

import java.io.StringReader;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilderFactory;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class StrX {

	public StrX() {
		super();
		// TODO Auto-generated constructor stub
	}

	// xml format string
	public String fXml(String xml) {
		try {
			final InputSource src = new InputSource(new StringReader(xml));
			final Node document = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(src).getDocumentElement();
			final Boolean keepDeclaration = Boolean.valueOf(xml
					.startsWith("<?xml"));

			final DOMImplementationRegistry registry = DOMImplementationRegistry
					.newInstance();
			final DOMImplementationLS impl = (DOMImplementationLS) registry
					.getDOMImplementation("LS");
			final LSSerializer writer = impl.createLSSerializer();

			writer.getDomConfig().setParameter("format-pretty-print",
					Boolean.TRUE); // Set this to true if the output needs to be
									// beautified.
			writer.getDomConfig().setParameter("xml-declaration",
					keepDeclaration); // Set this to true if the declaration is
										// needed to be outputted.

			return writer.writeToString(document);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("resource")
	public String logXml(String xml) {
		String str = fXml(xml);
		String data = "";
		String tabs = "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t";
		Scanner scanner = new Scanner(str);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			data += tabs;
			data += line;
		}
		return data + "\n";
	}

	// json format string
	public String fJson(String strJson) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(strJson);
		String strPretty = gson.toJson(je);
		return strPretty;
	}

	@SuppressWarnings("resource")
	public String logJson(String strJson) {
		String data = "";
		String tabs = "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t";
		Scanner scanner = new Scanner(fJson(strJson));
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			data += tabs;
			data += line;
		}
		return data + "\n";
	}

	public String fHtml(String s) {
		try {
			Document doc = (Document) Jsoup.parse(s);
			return doc.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return s;
	}
}
