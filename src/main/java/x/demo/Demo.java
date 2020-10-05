package x.demo;

import java.util.HashMap;
import java.util.Map;

import x.utils.http.ReqX;

public class Demo {
	public static void main(String agrs[]){
		// System.err.println(1111);
		ReqX rx = new ReqX();
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> md = new HashMap<String, Object>();
		String[] f = {"phone", "amount", "currency"};
		md.put("phone", "111");
		md.put("amount", "098989898");
		md.put("currency", "098989898");
		mp.put("data", md);
		mp.put("fields", f);
		System.err.println(rx.isBody(mp));
		
		
	}
}
