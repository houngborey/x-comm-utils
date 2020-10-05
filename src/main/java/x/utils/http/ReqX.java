package x.utils.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

public class ReqX {
	private Map<String, Object> mr = new HashMap<String, Object>();
	private String arr[] = { "data", "fields" };

	public ReqX() {
		super();
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> isBody(Map<String, Object> m) {
		if (m.isEmpty()) {
			mr.put("err", "100");
			mr.put("msg", "params is invalid");
			return mr;
		}
		if (arr.length > 0) {
			for (int i = 0; i < arr.length; i++) {
				if (!m.containsKey(arr[i])) {
					mr.put("err", "100");
					mr.put("msg", arr[i] + " is required");
					return mr;
				}
			}
		}

		for (Entry<String, Object> o : m.entrySet()) {
			String strV = String.valueOf(o.getValue());
			String strK = String.valueOf(o.getKey());
			if (strV == null || StringUtils.equalsIgnoreCase(strV, "null")) {
				mr.put("err", "100");
				mr.put("msg", strK + " is null");
				return mr;
			} else if (StringUtils.isBlank(strV)) {
				mr.put("err", "100");
				mr.put("msg", strK + " is null");
				return mr;
			}
		}

		Map<String, Object> md = new HashMap<String, Object>();
		String[] f;
		md = (Map<String, Object>) m.get("data");
		f = (String[]) m.get("fields");
		// System.err.println(md.isEmpty());
		if (md.isEmpty()) {
			mr.put("err", "100");
			mr.put("msg", "data is invalid");
			return mr;
		}
		if (f.length < 0) {
			mr.put("err", "100");
			mr.put("msg", "fields is invalid");
			return mr;
		}

		for (int i = 0; i < f.length; i++) {
			if (!md.containsKey(f[i])) {
				mr.put("err", "100");
				mr.put("msg", f[i] + " is required");
				return mr;
			}
		}
		for (Entry<String, Object> o : md.entrySet()) {
			String strV = String.valueOf(o.getValue());
			String strK = String.valueOf(o.getKey());
			if (strV == null || StringUtils.equalsIgnoreCase(strV, "null")) {
				mr.put("err", "100");
				mr.put("msg", strK + " is null");
				return mr;
			} else if (StringUtils.isBlank(strV)) {
				mr.put("err", "100");
				mr.put("msg", strK + " is null");
				return mr;
			}
		}
		mr.put("err", "000");
		mr.put("msg", "success");
		return mr;
	}

}
