package x.demo.cntr;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import x.utils.http.HttpXr;

@RestController
@RequestMapping("/x/demo")
public class X001 {

	@SuppressWarnings("unused")
	@PostMapping("/x001")
	public ResponseEntity<Map<String, Object>> fn_X001(@RequestBody Map<String, Object> data)
			throws Exception {
		Map<String, Object> m = new HashMap<String, Object>();
		String auth = "prince:prince@digitalbanking";

		String encodedString = Base64.getEncoder().encodeToString(
				auth.getBytes());

		try {
			HttpXr htx = new HttpXr();
			Map<String, Object> x = new HashMap<String, Object>();
			x.put("url", "http://103.69.68.222:11999");
			x.put("path", "/SeatelPayment/pay.jsp");
			x.put("params", data);
			x.put("method", "post");
			// x.put("auth", "Basic " + encodedString);
			// x.put("cont_tp" , "application/json;charset=UTF-8");

			m = htx.callX(x);

		} catch (Exception ex) {
			m.put("res_cd", "500");
			m.put("res_msg", "Internal Server Error");
			ex.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.OK).body(m);
	}
}
