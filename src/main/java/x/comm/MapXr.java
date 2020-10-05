package x.comm;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MapXr {
	private Map<String, Object> d = new HashMap<String, Object>();
	private DataXr dxr;

	public MapXr() {
		super();
		// TODO Auto-generated constructor stub
	}

	// merge into 1 map
	@SuppressWarnings("unchecked")
	public Map<String, Object> getX(Map<String, Object> m) {
		dxr = new DataXr();
		d.clear();
		if (m != null) {
			for (Map.Entry<String, Object> e : m.entrySet()) {
				if (e.getValue() != null) {
					if (dxr.isMap(e.getValue())) {
						// System.out.println(e.getKey());
						ObjectMapper c = new ObjectMapper();
						Map<String, Object> v = c.convertValue(e.getValue(),
								Map.class);
						getX(c.convertValue(e.getValue(), Map.class));
						for (Map.Entry<String, Object> r : v.entrySet()) {
							if (!dxr.isMap(r.getValue())) {
								d.put((String) r.getKey(), r.getValue());
							}
						}
					} else {
						d.put((String) e.getKey(), e.getValue());
					}
				} else {
					d.put((String) e.getKey(), e.getValue());
				}
			}
			LinkedHashMap<String, Object> sortedMap = new LinkedHashMap<>();
			d.entrySet()
					.stream()
					.sorted(Map.Entry.comparingByKey())
					.forEachOrdered(
							x -> sortedMap.put(x.getKey(), x.getValue()));
			
			d = sortedMap;
			return d;
		}
		return null;

	}
}
